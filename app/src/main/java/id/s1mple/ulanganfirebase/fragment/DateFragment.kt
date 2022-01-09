package id.s1mple.ulanganfirebase.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.s1mple.ulanganfirebase.databinding.FragmentDateBinding
import java.util.*

class DateFragment(var listener: DateListener) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentDateBinding
    private var clickDateStart: Boolean = false
    private var dateTemp: String = ""
    private var dateStart: String = ""
    private var dateEnd: String = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView("Tanggal mulai", "Pilih")
        setupListener()
    }

    private fun setupListener() {
        binding.calenderView.setOnDateChangeListener { view, year, month, day ->
            dateTemp = "$day/${month + 1}/$year"
        }

        binding.textApply.setOnClickListener {
            when(clickDateStart) {
                false -> {
                    clickDateStart = true
                    dateStart = dateTemp
                    binding.calenderView.date = Date().time
                    setView("Tanggal akhir", "Terapkan")
                }
                true -> {
                    dateEnd = dateTemp
                    listener.onSuccess(dateStart, dateEnd)
                    this.dismiss()
                }
            }
        }
    }

    private fun setView(title: String, apply: String) {
        binding.textTitle.text = title
        binding.textApply.text = apply
    }

    interface DateListener {
        fun onSuccess(dateStart: String, dateEnd: String)
    }
}