package id.s1mple.ulanganfirebase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.s1mple.ulanganfirebase.R
import id.s1mple.ulanganfirebase.databinding.AdapterTransactionBinding
import id.s1mple.ulanganfirebase.model.Transaction
import id.s1mple.ulanganfirebase.utils.amountFormat
import id.s1mple.ulanganfirebase.utils.timestampToString

class TransactionAdapter(var transactions: ArrayList<Transaction>, var listener: AdapterListener?): RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionAdapter.ViewHolder {
        return ViewHolder(AdapterTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TransactionAdapter.ViewHolder, position: Int) {
        val transaction = transactions[position]

        if (transaction.type.toUpperCase() == "IN") holder.binding.imageType.setImageResource(R.drawable.ic_in)
        else holder.binding.imageType.setImageResource(R.drawable.ic_out)

        holder.binding.textNote.text     = transaction.note
        holder.binding.textCategory.text = transaction.category
        holder.binding.textAmount.text   = amountFormat(transaction.amount)
        holder.binding.textDate.text     = transaction.created?.let { timestampToString(it) }

        holder.binding.container.setOnClickListener {
            listener?.onClick(transaction)
        }
    }

    override fun getItemCount(): Int = transactions.size

    class ViewHolder(val binding: AdapterTransactionBinding): RecyclerView.ViewHolder(binding.root)

    fun setData(data: List<Transaction>) {
        transactions.clear()
        transactions.addAll(data)
        notifyDataSetChanged()
    }

    interface AdapterListener {
        fun onClick(transaction: Transaction)
    }
}