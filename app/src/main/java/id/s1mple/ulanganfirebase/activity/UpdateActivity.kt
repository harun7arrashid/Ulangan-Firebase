package id.s1mple.ulanganfirebase.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.s1mple.ulanganfirebase.databinding.ActivityCreateBinding

class UpdateActivity : AppCompatActivity() {

    private val binding by lazy { ActivityCreateBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.btnSave.text = "Simpan Perubahan"
        binding.btnSave.setOnClickListener {

        }
    }
}