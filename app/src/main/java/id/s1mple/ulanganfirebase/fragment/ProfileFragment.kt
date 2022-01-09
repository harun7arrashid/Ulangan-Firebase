package id.s1mple.ulanganfirebase.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.activity.LoginActivity
import id.s1mple.ulanganfirebase.databinding.FragmentProfileBinding
import id.s1mple.ulanganfirebase.preferences.SharedPref
import id.s1mple.ulanganfirebase.utils.PrefUtil

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val pref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        binding.textBalance.text = requireActivity().intent.getStringExtra("balance")
    }

    override fun onStart() {
        super.onStart()
        getAvatar()
    }

    private fun getAvatar() {
        binding.imgAvatar.setImageResource(pref.getInt(PrefUtil.pref_avatar))
        binding.tvName.text     = pref.getString(PrefUtil.pref_name)
        binding.tvUsername.text = pref.getString(PrefUtil.pref_username)
        binding.textDate.text   = pref.getString(PrefUtil.pref_date)
    }

    private fun setupListener() {
        binding.imgAvatar.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_avatarFragment)
        }

        binding.cardLogout.setOnClickListener { logout() }
    }

    private fun logout() {
        pref.clear()
        Toast.makeText(requireActivity(), "Berhasil Logout", Toast.LENGTH_SHORT).show()
        startActivity(
            Intent(requireContext(), LoginActivity::class.java)
                .addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )
        )
        requireActivity().finish()
    }
}