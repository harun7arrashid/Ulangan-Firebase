package id.s1mple.ulanganfirebase.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.s1mple.ulanganfirebase.databinding.ActivityRegisterBinding
import id.s1mple.ulanganfirebase.model.User

class RegisterActivity : BaseActivity() {

    private val binding by lazy { ActivityRegisterBinding.inflate(layoutInflater) }
    private val db by lazy { Firebase.firestore }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.btnRegister.setOnClickListener {
//            if (isRequired()) checkUsername()
//            else Toast.makeText(this, "Data tidak boleh kosong", Toast.LENGTH_SHORT).show()
            register()
        }
    }

    private fun checkUsername() {
        showProgress(true)
        db.collection("user")
            .whereEqualTo("username", binding.edtUsername.text.toString())
            .get()
            .addOnSuccessListener { result ->
                showProgress(false)
                if (result.isEmpty) register()
                else binding.textAlert.visibility = View.VISIBLE
            }
    }

    private fun register() {
        showProgress(true)
        val user = User(
            name = binding.edtName.text.toString(),
            username = binding.edtUsername.text.toString(),
            password = binding.edtPassword.text.toString(),
            created = Timestamp.now()
        )
        db.collection("user")
            .add(user)
            .addOnSuccessListener {
                showProgress(false)
                Toast.makeText(this, "USer ditambahkan", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
    }

    private fun isRequired(): Boolean {
        return (
                binding.edtName.text.toString().isNotEmpty() &&
                binding.edtUsername.text.toString().isNotEmpty() &&
                binding.edtPassword.text.toString().isNotEmpty()
                )
    }

    private fun showProgress(it: Boolean) {
        binding.textAlert.visibility = View.GONE
        when (it) {
            true -> {
                binding.btnRegister.text = "Loading.."
                binding.btnRegister.isEnabled = false
            }
            false -> {
                binding.btnRegister.text = "Register"
                binding.btnRegister.isEnabled = true
            }
        }
    }
}