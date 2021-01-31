package com.example.happy2.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
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
import com.example.happy2.MyHelperMethods.myDates;
import com.example.happy2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import static com.example.happy2.AddActivity.KEY_ADINFO;
import static com.example.happy2.AddActivity.KEY_EDIT_IDEA_HAPPY;
import static com.example.happy2.AddActivity.KEY_WHAT;
import static com.example.happy2.AddActivity.KEY_WHEN;
import static com.example.happy2.AddActivity.KEY_WHERE;
import static com.example.happy2.AddActivity.KEY_WITH;


public class AddHappyThingFragment extends Fragment {


    private static final String TEXTVIEW_UPDATED = "updateAddMore";
    private boolean updateAddMore, update;

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

    private String tmpWhat, tmpWith, tmpWhere, tmpAdInfo;

    public static final String TMP_WHAT = "tmpWhat";
    public static final String TMP_WITH = "tmpWith";
    public static final String TMP_WHERE = "tmpWhere";
    public static final String TMP_ADINFO = "tmpAdInfo";

    private SharedPreferences prefs;
    private Intent intent;

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
            updateAddMore = getArguments().getBoolean((TEXTVIEW_UPDATED), false);
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
        intent = getActivity().getIntent();

        getAllViews(v);
        prepareButtonSaveAndTextChangedListeners();
        getUpdateMode();
        fillTextViews();
        autoCompleteACTextViews();
        getCalendar();
        if (updateAddMore) updateTxtAddHappyThing();

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!update) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(TMP_WHAT, tmpWhat)
                    .putString(TMP_WITH, tmpWith)
                    .putString(TMP_WHERE, tmpWhere)
                    .putString(TMP_ADINFO, tmpAdInfo);
            editor.commit();
        }
    }




    /* ********************************************
    GET ALL VIEW-ELEMENTS AND IF IN UPDATE MODE
    *********************************************** */

    // get all needed TextViews and Buttons
    private void getAllViews(View v) {
        textViewAddHappyThing = v.findViewById(R.id.txtAddHappyThing);
        acTextViewWhat = v.findViewById(R.id.acTextViewWhat);
        acTextViewWith = v.findViewById(R.id.acTextViewWith);
        acTextViewWhere = v.findViewById(R.id.acTextViewWhere);
        editTextWhen = v.findViewById(R.id.editTextWhen);
        acTextViewAdInfo = v.findViewById(R.id.acTextViewAdInfo);
        buttonChangeDate = v.findViewById(R.id.btnChangeDate);
        buttonSave = v.findViewById(R.id.btnSave);
    }


    // check if we are in update mode
    private void getUpdateMode() {
        if (intent.hasExtra(KEY_EDIT_IDEA_HAPPY)) {
            // if we are in edit mode
            update = intent.getBooleanExtra(KEY_EDIT_IDEA_HAPPY, false);
        } else {
            update = false;
        }
    }



    /* ********************************************
    FILL TEXT VIEWS WITH CORRECT TEXT
    *********************************************** */

    // fill textViews with either text to update or unsaved text
    private void fillTextViews() {
        String what = null;
        String where = null;
        String with = null;
        String adInfo = null;
        String when = getString(R.string.text_today);
        if (update){
            // if we are in edit mode
            what = intent.getStringExtra(KEY_WHAT);
            with = intent.getStringExtra(KEY_WITH);
            where = intent.getStringExtra(KEY_WHERE);
            adInfo = intent.getStringExtra(KEY_ADINFO);
            when = intent.getStringExtra(KEY_WHEN);
        } else {
            // if there was unsaved data
            if (prefs.contains(TMP_WHAT)) what = prefs.getString(TMP_WHAT, "");
            if (prefs.contains(TMP_WITH)) with = prefs.getString(TMP_WITH, "");
            if (prefs.contains(TMP_WHERE)) where = prefs.getString(TMP_WHERE, "");
            if (prefs.contains(TMP_ADINFO)) adInfo = prefs.getString(TMP_ADINFO, "");
        }
        acTextViewWhat.setText(what);
        acTextViewWith.setText(with);
        acTextViewWhere.setText(where);
        acTextViewAdInfo.setText(adInfo);
        editTextWhen.setText(when);
    }

    // if add-more mode, set new text as title
    public void updateTxtAddHappyThing() {
        textViewAddHappyThing.setText(R.string.text_what_made_you_happy_what_else);
    }



    /* ********************************************
    SAVE BUTTON AND TEXTCHANGEDLISTENERS
    *********************************************** */

    private void prepareButtonSaveAndTextChangedListeners() {
        buttonChangeDate.setOnClickListener(btnChangeDate);

        // Save button enables only if all EditTexts contain input
        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);
        acTextViewWhat.addTextChangedListener(addHappyTextWatcher);
        acTextViewWith.addTextChangedListener(addHappyTextWatcher);
        acTextViewWhere.addTextChangedListener(addHappyTextWatcher);
        acTextViewAdInfo.addTextChangedListener(addHappyTextWatcher);
    }

    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tmpWhen = editTextWhen.getText().toString();
            if (tmpWhen.equals(getString(R.string.text_today))) tmpWhen = new myDates().todayAsString();
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
            buttonSave.setEnabled(!tmpWhat.isEmpty() && !tmpWith.isEmpty() && !tmpWhere.isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };





    /* ********************************************
    CALENDAR AND CHANGE DATE BUTTON
    *********************************************** */

    private void getCalendar() {
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
    }


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
                    showDate(arg1, arg2, arg3);
                }
            };

    private void showDate(int yearnew, int monthnew, int daynew) {
        String string;
        if (yearnew == year && monthnew == month && daynew == day){
            string = getString(R.string.text_today);
        } else {
            string = new myDates().dayMonthYearToString(daynew, monthnew, yearnew);
        }
        editTextWhen.setText(string);
    }



    /* ********************************************
    AUTOCOMPLETE TEXTVIEWS
    *********************************************** */

    // set all adapters and thresholds for the autocomplete textviews
    private void autoCompleteACTextViews() {

        acTextViewWhat.setThreshold(1);
        acTextViewWith.setThreshold(1);
        acTextViewWhere.setThreshold(1);
        acTextViewAdInfo.setThreshold(1);

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

    }
}