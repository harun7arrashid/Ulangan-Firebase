package id.s1mple.ulanganfirebase.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.databinding.ActivityLoginBinding
import id.s1mple.ulanganfirebase.model.User
import id.s1mple.ulanganfirebase.preferences.SharedPref
import id.s1mple.ulanganfirebase.utils.timestampToString

class LoginActivity : BaseActivity() {

    private val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    private val db by lazy { Firebase.firestore }
    private val pref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.tvRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.btnLogin.setOnClickListener {
            if (isRequired()) login()
            else Toast.makeText(this, "Data Tidak Boleh Kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun login() {
        showProgress(true)
        db.collection("user")
            .whereEqualTo("username", binding.edtUsername.text.toString())
            .whereEqualTo("password", binding.edtPassword.text.toString())
            .get()
            .addOnSuccessListener { result ->
                showProgress(false)
                if (result.isEmpty) binding.textAlert.visibility = View.VISIBLE
                else {
                    Toast.makeText(this, "Berhasil Login", Toast.LENGTH_SHORT).show()
                    result.forEach {
                        saveSession(User(
                            it.data["name"].toString(),
                            it.data["username"].toString(),
                            it.data["password"].toString(),
                            it.data["created"] as Timestamp
                        ))
                    }
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
    }

    private fun saveSession(user: User) {
        pref.put("pref_is_login", 1)
        pref.put("pref_name", user.name)
        pref.put("pref_username", user.username)
        timestampToString(user.created)?.let { pref.put("pref_date", it) }
        if (pref.getInt("pref_avatar") == 0) pref.put("pref_avatar", R.drawable.avatar4)
    }

    private fun isRequired(): Boolean {
        return (
                binding.edtUsername.text.toString().isNotEmpty() &&
                        binding.edtPassword.text.toString().isNotEmpty()
                )
    }

    private fun showProgress(it: Boolean) {
        binding.textAlert.visibility = View.GONE // setiap kali manggil ini, di GONE
        when (it) {
            true -> {
                binding.btnLogin.text = "Loading.."
                binding.btnLogin.isEnabled = false
            }
            false -> {
                binding.btnLogin.text = "Login"
                binding.btnLogin.isEnabled = true
            }
        }
    }
}