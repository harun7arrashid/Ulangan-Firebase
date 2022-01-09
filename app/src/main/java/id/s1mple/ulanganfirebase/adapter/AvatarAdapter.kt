package id.s1mple.ulanganfirebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.s1mple.ulanganfirebase.databinding.AdapterAvatarBinding

class AvatarAdapter(var avatars: ArrayList<Int>, var listener: AdapterListener): RecyclerView.Adapter<AvatarAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarAdapter.ViewHolder {
        return ViewHolder(AdapterAvatarBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AvatarAdapter.ViewHolder, position: Int) {
        val avatar: Int = avatars[position]
        holder.binding.imgAvatar.setImageResource( avatar )
        holder.binding.imgAvatar.setOnClickListener {
            listener.onClick(avatar)
        }
    }

    override fun getItemCount(): Int = avatars.size

    class ViewHolder(val binding: AdapterAvatarBinding): RecyclerView.ViewHolder(binding.root)

    interface AdapterListener {
        fun onClick(avatar: Int)
    }
}