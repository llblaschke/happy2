package com.example.happy2.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.happy2.R;

public class SortByDialogFragment extends DialogFragment {

    private final String TAG = "SortByDialog";


    public interface SortByDialogListener {
        public void onDialogItemSelected(int sortBy);
    }

    SortByDialogListener listener;

    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (SortByDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getTargetFragment().toString()
                    + " must implement NoticeDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.sort_by_question);        // add a list
        String[] items = getResources().getStringArray(R.array.selectable_items_sort);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // what
                    case 1: // with
                    case 2: // where
                    case 3: // date
                }
                listener.onDialogItemSelected(which);//SortByDialogFragment.this);
            }
        });        // create and show the alert dialog
        return builder.create();
    }

}

