package id.s1mple.ulanganfirebase.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.button.MaterialButton
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.adapter.CategoryAdapter
import id.s1mple.ulanganfirebase.databinding.ActivityCreateBinding
import id.s1mple.ulanganfirebase.model.Category
import id.s1mple.ulanganfirebase.model.Transaction
import id.s1mple.ulanganfirebase.preferences.SharedPref
import id.s1mple.ulanganfirebase.utils.PrefUtil

class CreateActivity : BaseActivity() {

    companion object {
        const val TAG = "CreateActivity"
    }

    private val pref by lazy { SharedPref(this) }

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }
    private val db by lazy { Firebase.firestore }
    private lateinit var categoryAdapter: CategoryAdapter
    private var category = ""
    private var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        initListener()
    }

    override fun onStart() {
        super.onStart()
        getCategory()
    }

    private fun setupRecyclerView() {
        categoryAdapter = CategoryAdapter(this, arrayListOf(), object : CategoryAdapter.AdapterListener {
            override fun onClick(category: Category) {
                this@CreateActivity.category = category.name ?:""
                Log.d(TAG, this@CreateActivity.category)
            }
        })
        binding.rvCategory.adapter = categoryAdapter
    }

    private fun getCategory() {
        val categories: ArrayList<Category> = arrayListOf()
        db.collection("category")
            .get()
            .addOnSuccessListener { result ->
                result.forEach { doc ->
                    categories.add(Category(doc.data["name"].toString()))
                }
                categoryAdapter.setData(categories)
            }
    }

    private fun initListener() {
        binding.btnIn.setOnClickListener {
            type = "IN"
            setButton(it as MaterialButton)
        }

        binding.btnOut.setOnClickListener {
            type = "OUT"
            setButton(it as MaterialButton)
        }

        binding.btnSave.setOnClickListener {
            showProgress(true)

            val transaction = Transaction(
                null,
                pref.getString(PrefUtil.pref_username) ?: "",
                category,
                type,
                binding.edtAmount.text.toString().toInt(),
                binding.edtNote.text.toString(),
                Timestamp.now()
            )

            db.collection("transaction")
                .add(transaction)
                .addOnSuccessListener {
                    showProgress(false)
                    Toast.makeText(this, "Berhasil nambah transaksi", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }

    private fun setButton(btnSelected: MaterialButton) {
        Log.d(TAG, type)
        listOf<MaterialButton>(binding.btnIn, binding.btnOut).forEach {
            it.setBackgroundColor(ContextCompat.getColor(this, android.R.color.darker_gray))
        }
        btnSelected.setBackgroundColor(ContextCompat.getColor(this, R.color.teal_200))
    }

    private fun showProgress(it: Boolean) {
        when (it) {
            true -> {
                binding.btnSave.text = "Loading.."
                binding.btnSave.isEnabled = false
            }
            false -> {
                binding.btnSave.text = "Simpan"
                binding.btnSave.isEnabled = true
            }
        }
    }
}