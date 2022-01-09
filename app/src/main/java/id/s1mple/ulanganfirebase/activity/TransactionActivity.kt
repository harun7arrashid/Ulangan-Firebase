package id.s1mple.ulanganfirebase.activity

import android.os.Bundle
import android.util.Log
import id.s1mple.ulanganfirebase.databinding.ActivityTransactionBinding
import id.s1mple.ulanganfirebase.fragment.DateFragment

class TransactionActivity : BaseActivity() {

    private val binding by lazy { ActivityTransactionBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupListener()
    }

    private fun setupListener() {
        binding.imgDate.setOnClickListener {
            DateFragment(object : DateFragment.DateListener {
                override fun onSuccess(dateStart: String, dateEnd: String) {
                    Log.d("TransactionActivity", "$dateStart $dateEnd")
                }
            }).apply {
                show(supportFragmentManager, "dateFragment")
            }
        }
    }
}