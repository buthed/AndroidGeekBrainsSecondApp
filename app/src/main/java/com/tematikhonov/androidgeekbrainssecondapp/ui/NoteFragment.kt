package com.tematikhonov.androidgeekbrainssecondapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.tematikhonov.androidgeekbrainssecondapp.MainActivity
import com.tematikhonov.androidgeekbrainssecondapp.R
import com.tematikhonov.androidgeekbrainssecondapp.data.Note
import com.tematikhonov.androidgeekbrainssecondapp.data.Publisher
import java.util.*

class NoteFragment : Fragment() {
    private var note: Note? = null
    private var publisher: Publisher? = null
    private var title: TextInputEditText? = null
    private var description: TextInputEditText? = null
    private var datePicker: DatePicker? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            note = arguments!!.getParcelable(ARG_CARD_DATA)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val activity = context as MainActivity
        publisher = activity.publisher
    }

    override fun onDetach() {
        publisher = null
        super.onDetach()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_note, container, false)
        initView(view)
        if (note != null) {
            populateView()
        }
        return view
    }

    override fun onStop() {
        super.onStop()
        note = collectNote()
    }

    override fun onDestroy() {
        super.onDestroy()
        publisher!!.notifySingle(note)
    }

    private fun collectNote(): Note {
        val title = title!!.text.toString()
        val description = description!!.text.toString()
        val date = dateFromDatePicker
        val favorite: Boolean
        favorite = if (note != null) {
            note!!.isFavorite
        } else {
            false
        }
        return Note(title, date, description, favorite)
    }

    private val dateFromDatePicker: Date
        private get() {
            val cal = Calendar.getInstance()
            cal[Calendar.YEAR] = datePicker!!.year
            cal[Calendar.MONTH] = datePicker!!.month
            cal[Calendar.DAY_OF_MONTH] = datePicker!!.dayOfMonth
            return cal.time
        }

    private fun initView(view: View) {
        title = view.findViewById(R.id.inputTitle)
        description = view.findViewById(R.id.inputDescription)
        datePicker = view.findViewById(R.id.inputDate)
    }

    private fun populateView() {
        title!!.setText(note!!.title)
        initDatePicker(note!!.date)
        description!!.setText(note!!.description)
    }

    private fun initDatePicker(date: Date) {
        val calendar = Calendar.getInstance()
        calendar.time = date
        datePicker!!.init(calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH],
                null)
    }

    companion object {
        private const val ARG_CARD_DATA = "Param_CardData"
        fun newInstance(note: Note?): NoteFragment {
            val fragment = NoteFragment()
            val args = Bundle()
            args.putParcelable(ARG_CARD_DATA, note)
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): NoteFragment {
            return NoteFragment()
        }
    }
}