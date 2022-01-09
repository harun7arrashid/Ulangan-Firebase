package id.s1mple.ulanganfirebase.utils

import com.google.firebase.Timestamp
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun timestampToString(timestamp: Timestamp): String? {
    return if (timestamp != null) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.format(timestamp.toDate())
    } else null
}

fun amountFormat(number: Int): String {
    val numberFormat: NumberFormat = DecimalFormat("#,###")
    return numberFormat.format(number)
}