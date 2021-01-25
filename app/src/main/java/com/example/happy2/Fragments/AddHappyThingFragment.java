package com.example.happy2.Fragments;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.Dialogs.AddMoreHappyThingsDiaglog;
import com.example.happy2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;


public class AddHappyThingFragment extends Fragment {


    private static final String TEXTVIEW_UPDATED = "updateAddMore";
    private boolean updateAddMore;

    private TextView textViewAddHappyThing;
    private AutoCompleteTextView acTextViewWhat;
    private AutoCompleteTextView acTextViewWith;
    private AutoCompleteTextView acTextViewWhere;
    private AutoCompleteTextView acTextViewAdInfo;
    private EditText editTextWhen;
    private Button buttonChangeDate;
    private Button buttonSave;

    private Calendar calendar;
    private int year, month, day;

    private String tmpWhat, tmpWith, tmpWhere, tmpAdInfo, tmpWhen;

    public static final String TMP_WHAT = "tmpWhat";
    public static final String TMP_WITH = "tmpWith";
    public static final String TMP_WHERE = "tmpWhere";
    public static final String TMP_ADINFO = "tmpAdInfo";
    public static final String TMP_WHEN = "tmpWhen";

    private SharedPreferences prefs;

    private HappyViewModel happyViewModel;
    private ArrayAdapter happyWhatAdapter;
    private ArrayAdapter happyWithAdapter;
    private ArrayAdapter happyWhereAdapter;
    private ArrayAdapter happyAdInfoAdapter;



    public AddHappyThingFragment() {
        // Required empty public constructor
    }

    public static AddHappyThingFragment newInstance(boolean updateAddMore) {
        AddHappyThingFragment fragment = new AddHappyThingFragment();
        Bundle args = new Bundle();
        args.putBoolean(TEXTVIEW_UPDATED, updateAddMore);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            updateAddMore = getArguments().getBoolean((TEXTVIEW_UPDATED));
        }else{
            updateAddMore = false;
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        happyViewModel = ViewModelProviders.of(this).get(HappyViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_add_happy, container, false);

        textViewAddHappyThing = v.findViewById(R.id.txtAddHappyThing);
        acTextViewWhat = v.findViewById(R.id.acTextViewWhat);
        acTextViewWith = v.findViewById(R.id.acTextViewWith);
        acTextViewWhere = v.findViewById(R.id.acTextViewWhere);
        editTextWhen = v.findViewById(R.id.editTextWhen);
        acTextViewAdInfo = v.findViewById(R.id.acTextViewAdInfo);
        buttonChangeDate = v.findViewById(R.id.btnChangeDate);
        buttonSave = v.findViewById(R.id.btnSave);

        if(updateAddMore) {
            updateTxtAddHappyThing();
        }

        buttonChangeDate.setOnClickListener(btnChangeDate);

        // Save button enables only if all EditTexts contain input
        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);
        acTextViewWhat.addTextChangedListener(addHappyTextWatcher);
        acTextViewWith.addTextChangedListener(addHappyTextWatcher);
        acTextViewWhere.addTextChangedListener(addHappyTextWatcher);
        acTextViewAdInfo.addTextChangedListener(addHappyTextWatcher);
        editTextWhen.addTextChangedListener(addHappyTextWatcher);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        if (prefs.contains(TMP_WHAT)){
            tmpWhat = prefs.getString(TMP_WHAT, "");
            acTextViewWhat.setText(tmpWhat);
        }
        if (prefs.contains(TMP_WITH)){
            tmpWith = prefs.getString(TMP_WITH, "");
            acTextViewWith.setText(tmpWith);
        }
        if (prefs.contains(TMP_WHERE)){
            tmpWhere = prefs.getString(TMP_WHERE, "");
            acTextViewWhere.setText(tmpWhere);
        }
        if (prefs.contains(TMP_ADINFO)){
            tmpAdInfo = prefs.getString(TMP_ADINFO, "");
            acTextViewAdInfo.setText(tmpAdInfo);
        }
        if (prefs.contains(TMP_WHEN)){
            tmpWhen = prefs.getString(TMP_WHEN, "");
            editTextWhen.setText(tmpWhen);
        }


        acTextViewWhat.setThreshold(1);
        acTextViewWith.setThreshold(1);
        acTextViewWhere.setThreshold(1);
        acTextViewAdInfo.setThreshold(1);

        ArrayList<String> emptyList = new ArrayList<>();
        emptyList.add("");

        happyWhatAdapter = new ArrayAdapter(getContext(), android.R.layout.select_dialog_item, new ArrayList<>());
        happyWithAdapter = new ArrayAdapter(getContext(), android.R.layout.select_dialog_item, new ArrayList<>());
        happyWhereAdapter = new ArrayAdapter(getContext(), android.R.layout.select_dialog_item, new ArrayList<>());
        happyAdInfoAdapter= new ArrayAdapter(getContext(), android.R.layout.select_dialog_item, new ArrayList<>());

        happyViewModel.getAllHappyWhat().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                happyWhatAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                acTextViewWhat.setAdapter(happyWhatAdapter);
            }
        });

        happyViewModel.getAllHappyWith().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                happyWithAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                acTextViewWith.setAdapter(happyWithAdapter);
            }
        });

        happyViewModel.getAllHappyWhere().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                happyWhereAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                acTextViewWhere.setAdapter(happyWhereAdapter);
            }
        });

        happyViewModel.getAllHappyAdInfo().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                happyAdInfoAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                acTextViewAdInfo.setAdapter(happyAdInfoAdapter);
            }
        });

        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TMP_WHAT, tmpWhat)
                .putString(TMP_WITH, tmpWith)
                .putString(TMP_WHERE, tmpWhere)
                .putString(TMP_ADINFO, tmpAdInfo)
                .putString(TMP_WHEN, tmpWhen);
        editor.commit();
    }



    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            happyViewModel.insert(new HappyThing(tmpWhat, tmpWith, tmpWhere, tmpAdInfo, tmpWhen));
            acTextViewWhat.setText("");
            acTextViewWith.setText("");
            acTextViewWhere.setText("");
            acTextViewAdInfo.setText("");
            editTextWhen.setText(getString(R.string.text_today));

            tmpWhat = "";
            tmpWith = "";
            tmpWhere = "";
            tmpAdInfo = "";
            tmpWhen = getString(R.string.text_today);

            new AddMoreHappyThingsDiaglog().show(getParentFragmentManager(), "addmorehappythingsdialog");
        }
    };


    private TextWatcher addHappyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            tmpWhat = acTextViewWhat.getText().toString().trim();
            tmpWith = acTextViewWith.getText().toString().trim();
            tmpWhere = acTextViewWhere.getText().toString().trim();
            tmpAdInfo = acTextViewAdInfo.getText().toString().trim();
            tmpWhen = editTextWhen.getText().toString().trim();
            buttonSave.setEnabled(!tmpWhat.isEmpty() && !tmpWith.isEmpty() && !tmpWhere.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };



    private View.OnClickListener btnChangeDate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            DatePickerDialog dialog = new DatePickerDialog(getContext(), myDateListener, year, month, day);
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            dialog.show();
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

    private void showDate(int yearnew, int monthnew, int daynew) {
        String string;
        if(yearnew == year && monthnew == month+1 && daynew == day){
            string = getString(R.string.text_today);
        }else{
            string = new StringBuilder().append(daynew).append(".").append(monthnew).append(".").append(yearnew).toString();
        }
        editTextWhen.setText(string);
    }

    public void updateTxtAddHappyThing() {
        textViewAddHappyThing.setText(R.string.text_what_made_you_happy_what_else);
    }
}