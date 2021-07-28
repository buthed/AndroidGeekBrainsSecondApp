package com.tematikhonov.androidgeekbrainssecondapp.ui

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.tematikhonov.androidgeekbrainssecondapp.R

class DialogBuilderFragment : DialogFragment() {
    interface DeleteDialogCaller {
        fun onDialogResult(result: String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val contentView = requireActivity().layoutInflater.inflate(R.layout.dialog_custom, null)
        val message = contentView.findViewById<TextView>(R.id.delete_dialog_message)
        message.text = "Delete the note?"
        val dialogCaller = targetFragment as DeleteDialogCaller?
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle(R.string.delete_dialog_title)
        builder.setView(contentView)
        builder.setPositiveButton(R.string.delete_dialog_pos_btn) { dialogInterface, i ->
            dismiss()
            dialogCaller?.onDialogResult("DELETE")
        }
        builder.setNegativeButton(R.string.delete_dialog_neg_btn) { dialogInterface, i ->
            dismiss()
            dialogCaller?.onDialogResult("CANCEL")
        }
        return builder.create()
    }
}