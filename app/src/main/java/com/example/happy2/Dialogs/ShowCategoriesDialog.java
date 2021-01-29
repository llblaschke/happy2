package com.example.happy2.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.happy2.Adapters.MyShowCategoriesDialogAdapter;
import com.example.happy2.R;

import java.util.ArrayList;

public class ShowCategoriesDialog extends DialogFragment {

    private final String TAG = "ShowCategoriesDialog";
    public static String SHOW_CATEGORIES_DIALOG_DISABLE = "com.example.happy2.ShowCategoriesDialog.SHOW_CATEGORIES_DIALOG_DISABLE";
    CharSequence[] CATEGORY_ITEMS;

    SortByDialogFragment.SortByDialogListener listener;
    private ArrayList<Integer> disableIndices;

    AlertDialog.Builder builder;


    public interface ShowCategoriesDialogListener {
        public void onDialogItemSelected(int sortBy);
    }

    public static ShowCategoriesDialog newInstance(ArrayList<Integer> disableList) {
        Bundle args = new Bundle();
        args.putIntegerArrayList(SHOW_CATEGORIES_DIALOG_DISABLE, disableList);

        ShowCategoriesDialog dialog = new ShowCategoriesDialog();
        dialog.setArguments(args);
        return dialog;
    }


    // Override the Fragment.onAttach() method to instantiate the NoticeDialogListener
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (SortByDialogFragment.SortByDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(getTargetFragment().toString()
                    + " must implement onDialogItemSelected");
        }
        builder = new AlertDialog.Builder(context);
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CATEGORY_ITEMS = getResources().getStringArray(R.array.selectable_items_sort);

        if (getArguments() != null) {
            disableIndices = getArguments().getIntegerArrayList(SHOW_CATEGORIES_DIALOG_DISABLE);
        } else {
            disableIndices = new ArrayList<Integer>();
        }

        builder.setTitle(R.string.sort_by_question);

        final MyShowCategoriesDialogAdapter adapter = new MyShowCategoriesDialogAdapter(CATEGORY_ITEMS, disableIndices, getContext());
        builder.setAdapter(adapter, null );
        builder.setItems(CATEGORY_ITEMS, new DialogInterface.OnClickListener() {
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
