package id.s1mple.ulanganfirebase.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.preferences.SharedPref

class SplashActivity : BaseActivity() {

    private val pref by lazy { SharedPref(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (pref.getInt("pref_is_login") == 0) startActivity(Intent(this, LoginActivity::class.java))
            else startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}