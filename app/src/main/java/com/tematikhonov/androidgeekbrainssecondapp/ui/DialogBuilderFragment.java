package com.tematikhonov.androidgeekbrainssecondapp.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.tematikhonov.androidgeekbrainssecondapp.MainActivity;
import com.tematikhonov.androidgeekbrainssecondapp.R;

public class DialogBuilderFragment extends DialogFragment {

    public static interface DeleteDialogCaller {
        void onDialogResult(String result);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View contentView = requireActivity().getLayoutInflater().inflate(R.layout.dialog_custom, null);
        TextView message = contentView.findViewById(R.id.delete_dialog_message);
        message.setText("Delete the note?");
        DeleteDialogCaller dialogCaller = (DeleteDialogCaller) getTargetFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle(R.string.delete_dialog_title);
        builder.setView(contentView);
        builder.setPositiveButton(R.string.delete_dialog_pos_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                if (dialogCaller != null) {
                    dialogCaller.onDialogResult("DELETE");
                }
            }
        });
        builder.setNegativeButton(R.string.delete_dialog_neg_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();
                if (dialogCaller != null) {
                    dialogCaller.onDialogResult("CANCEL");
                }
            }
        });
        return builder.create();
    }

}
