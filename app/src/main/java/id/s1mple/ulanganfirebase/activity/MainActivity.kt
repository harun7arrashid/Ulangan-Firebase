package id.s1mple.ulanganfirebase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.adapter.TransactionAdapter
import id.s1mple.ulanganfirebase.databinding.ActivityMainBinding
import id.s1mple.ulanganfirebase.databinding.HomeAvatarBinding
import id.s1mple.ulanganfirebase.databinding.HomeDashboardBinding
import id.s1mple.ulanganfirebase.model.Category
import id.s1mple.ulanganfirebase.model.Transaction
import id.s1mple.ulanganfirebase.preferences.SharedPref
import id.s1mple.ulanganfirebase.utils.PrefUtil
import id.s1mple.ulanganfirebase.utils.amountFormat

class MainActivity : BaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var bindingAvatar: HomeAvatarBinding
    private lateinit var bindingDashboard: HomeDashboardBinding
    private lateinit var mAdapter: TransactionAdapter

    private val db by lazy { Firebase.firestore }
    private val pref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupBinding()
        setupListener()
        setupRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        testFirestore()
        getAvatar()
        getBalance()
        getTransaction()
    }

    private fun getTransaction() {
        binding.progress.visibility =  View.VISIBLE
        val transactions: ArrayList<Transaction> = arrayListOf()
        db.collection("transaction")
            .whereEqualTo("username", pref.getString(PrefUtil.pref_username))
            .get()
            .addOnSuccessListener { result ->
                binding.progress.visibility =  View.GONE
                result.forEach { doc ->
                    transactions.add(
                        Transaction(
                            doc.reference.id,
                            doc.data["username"].toString(),
                            doc.data["category"].toString(),
                            doc.data["type"].toString(),
                            doc.data["amount"].toString().toInt(),
                            doc.data["note"].toString(),
                            doc.data["created"] as Timestamp,
                        )
                    )
                }
                mAdapter.setData(transactions)
            }
    }

    private fun setupRecyclerView() {
        mAdapter = TransactionAdapter(arrayListOf(), null)
        mAdapter.notifyDataSetChanged()
        binding.rvMain.adapter  = mAdapter
    }

    private fun getBalance() {
        var totalBalance = 0
        var totalIn  = 0
        var totalOut = 0

        db.collection("transaction")
            .whereEqualTo("username", pref.getString(PrefUtil.pref_username))
            .get()
            .addOnSuccessListener { result ->
                result.forEach { doc ->
                    totalBalance += doc.data["amount"].toString().toInt()
                    when(doc.data["type"].toString()) {
                        "IN" -> totalIn += doc.data["amount"].toString().toInt()
                        "OUT" -> totalOut += doc.data["amount"].toString().toInt()
                    }
                }
                bindingDashboard.tvBalance.text = amountFormat(totalBalance)
                bindingDashboard.tvIn.text  = amountFormat(totalIn)
                bindingDashboard.tvOut.text = amountFormat(totalOut)
            }
    }

    private fun getAvatar() {
        bindingAvatar.tvAvatar.text = pref.getString(PrefUtil.pref_name)
        bindingAvatar.imageAvatar.setImageResource(pref.getInt(PrefUtil.pref_avatar))
    }

    private fun testFirestore() {
        val categories: ArrayList<Category> = arrayListOf()
        db.collection("category")
            .get()
            .addOnSuccessListener {
                it.forEach { document ->
                    Log.d("MainActivity", document.data["name"].toString())
                    Log.d("MainActivity", document.data["pohon"].toString())
                }
            }
    }

    private fun setupBinding() {
        setContentView(binding.root)
        bindingAvatar = binding.includeAvatar
        bindingDashboard = binding.includeDashboard
    }

    private fun setupListener() {
        bindingAvatar.imageAvatar.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity::class.java)
                    .putExtra("balance", bindingDashboard.tvBalance.text.toString())
            )
        }

        binding.fabCreate.setOnClickListener {
            startActivity(Intent(this, CreateActivity::class.java))
        }

        binding.tvTransaction.setOnClickListener {
            startActivity(Intent(this, TransactionActivity::class.java))
        }
    }
}