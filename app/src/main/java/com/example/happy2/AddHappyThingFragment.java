package com.example.happy2;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Objects;


public class AddHappyThingFragment extends Fragment {

    private EditText editTextWhat;
    private EditText editTextWith;
    private EditText editTextWhere;
    private TextView editTextWhen;
    private Button buttonChangeDate;
    private Button buttonSave;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

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
        editTextWhen = v.findViewById(R.id.editTextWhen);
        buttonChangeDate = v.findViewById(R.id.btnChangeDate);
        buttonSave = v.findViewById(R.id.btnSave);

        buttonChangeDate.setOnClickListener(btnChangeDate);

        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);

        editTextWhat.addTextChangedListener(addHappyTextWatcher);
        editTextWith.addTextChangedListener(addHappyTextWatcher);
        editTextWhere.addTextChangedListener(addHappyTextWatcher);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        return v;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs,
                          @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }


    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ConstraintLayout parent = (ConstraintLayout) v.getParent();
            ((AppCompatTextView) parent.getViewById(R.id.txtAddHappyThing)).setText(R.string.text_what_made_you_happy_add_more);
            ((AppCompatEditText) parent.getViewById(R.id.edittextWhatDidYouDo)).setText("");
            ((AppCompatEditText) parent.getViewById(R.id.edittextWithWhom)).setText("");
            ((AppCompatEditText) parent.getViewById(R.id.edittextWhere)).setText("");

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



    private View.OnClickListener btnChangeDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), myDateListener, year, month, day);
            dialog.show();
            //showDialog(999);
        }
    };

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // arg1 = year, arg2 = month, arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        editTextWhen.setText(new StringBuilder().append(day).append(".")
                .append(month).append(".").append(year));
    }
}