package com.example.happy2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddHappyThingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddHappyThingFragment extends Fragment {

    private EditText editTextWhat;
    private EditText editTextWith;
    private EditText editTextWhere;
    private Button buttonSave;

    public AddHappyThingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_happy_thing, container, false);

        editTextWhat = v.findViewById(R.id.edittextWhatDidYouDo);
        editTextWith = v.findViewById(R.id.edittextWithWhom);
        editTextWhere = v.findViewById(R.id.edittextWhere);
        buttonSave = v.findViewById(R.id.btnSave);

        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);

        editTextWhat.addTextChangedListener(addHappyTextWatcher);
        editTextWith.addTextChangedListener(addHappyTextWatcher);
        editTextWhere.addTextChangedListener(addHappyTextWatcher);

        // as soon as a nicer way of hiding the bottomnavigationview is found, delete that:
        // dont forget to delete the method hideKeyboardListener as well!
        //editTextWhat.setOnTouchListener(hideKeyboardListener);
        //editTextWith.setOnTouchListener(hideKeyboardListener);
        //editTextWhere.setOnTouchListener(hideKeyboardListener);


        // more comments!

        return v;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs,
                          @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }


    private View.OnTouchListener hideKeyboardListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            getActivity().findViewById(R.id.nav_view).setVisibility(View.GONE);
            return false;
        }
    };

    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            ((AppCompatTextView) parent.getViewById(R.id.txtAddHappyThing)).setText(R.string.text_what_made_you_happy_add_more);
            ((AppCompatEditText) parent.getViewById(R.id.edittextWhatDidYouDo)).setText("");
            ((AppCompatEditText) parent.getViewById(R.id.edittextWithWhom)).setText("");
            ((AppCompatEditText) parent.getViewById(R.id.edittextWhere)).setText("");

            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
            //getActivity().findViewById(R.id.nav_view).setVisibility(View.VISIBLE);

            Toast.makeText(getContext(), "Currently not saving anything", Toast.LENGTH_LONG).show();
        }
    };

    private TextWatcher addHappyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String textWhat = editTextWhat.getText().toString().trim();
            String textWith = editTextWith.getText().toString().trim();
            String textWhere = editTextWhere.getText().toString().trim();
            buttonSave.setEnabled(!textWhat.isEmpty() && !textWith.isEmpty() && !textWhere.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) { }
    };
}