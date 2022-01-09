package id.s1mple.ulanganfirebase.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.adapter.AvatarAdapter
import id.s1mple.ulanganfirebase.databinding.FragmentAvatarBinding
import id.s1mple.ulanganfirebase.preferences.SharedPref
import id.s1mple.ulanganfirebase.utils.PrefUtil

class AvatarFragment : Fragment() {

    private lateinit var binding: FragmentAvatarBinding
    private lateinit var avatarAdapter: AvatarAdapter

    private val pref by lazy { SharedPref(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAvatarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupList()
    }

    private fun setupList() {
        val avatars = arrayListOf<Int>(
            R.drawable.avatar1,
            R.drawable.avatar2,
            R.drawable.avatar3,
            R.drawable.avatar4,
            R.drawable.avatar5,
            R.drawable.avatar6,
            R.drawable.avatar7,
            R.drawable.avatar8,
            R.drawable.avatar9,
            R.drawable.avatar10
        )
        avatarAdapter = AvatarAdapter(avatars, object : AvatarAdapter.AdapterListener {
            override fun onClick(avatar: Int) {
                pref.put(PrefUtil.pref_avatar, avatar)
                findNavController().navigateUp()
            }
        })
        binding.rvAvatar.adapter = avatarAdapter
    }
}