package id.s1mple.ulanganfirebase.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.databinding.AdapterCategoryBinding
import id.s1mple.ulanganfirebase.model.Category

class CategoryAdapter(
    val context: Context,
    val categories: ArrayList<Category>,
    val listener: AdapterListener?
): RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private var listButton: ArrayList<MaterialButton> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AdapterCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = categories[position]
        val btn = holder.binding.btnCategory
        btn.text = category.name
        btn.setOnClickListener {
            listener?.onClick(category)
            setButton(it as MaterialButton)
        }

        listButton.add(btn)
    }

    override fun getItemCount(): Int = categories.size

    class ViewHolder(val binding: AdapterCategoryBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<Category>) {
        categories.clear()
        categories.addAll(data)
        notifyDataSetChanged()
    }

    private fun setButton(buttonSelected: MaterialButton) {
        listButton.forEach { button ->
            button.setBackgroundColor(ContextCompat.getColor(context, android.R.color.darker_gray))
        }
        buttonSelected.setBackgroundColor(ContextCompat.getColor(context, R.color.teal_200))
    }

    interface AdapterListener {
        fun onClick(category: Category)
    }
}