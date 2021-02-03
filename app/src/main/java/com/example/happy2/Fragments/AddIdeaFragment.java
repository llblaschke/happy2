package com.example.happy2.Fragments;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;

import com.example.happy2.DataHandling.IdeaViewModel;
import com.example.happy2.DataHandling.Room.Idea;
import com.example.happy2.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.happy2.AddActivity.KEY_EDIT_IDEA_HAPPY;
import static com.example.happy2.AddActivity.KEY_ID;
import static com.example.happy2.AddActivity.KEY_IDEA;
import static com.example.happy2.AddActivity.KEY_IDEA_DESCRIPTION;


public class AddIdeaFragment extends Fragment {

    private final String TAG = "AddIdeaFragment";
    private TextView tvAddIdea;
    private AutoCompleteTextView acTextViewIdea;
    private AutoCompleteTextView acTextViewDesc;
    private Button buttonSave;


    public static final String TMP_IDEA = "com.example.happy2.tmpIdea";
    public static final String TMP_DESC = "com.example.happy2.tmpDesc";

    private SharedPreferences prefs;

    private IdeaViewModel ideaViewModel;
    private ArrayAdapter ideaWhatAdapter;
    private ArrayAdapter ideaDescAdapter;

    private boolean update;
    private Intent intent;


    public AddIdeaFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_add_happy, container, false);

        intent = getActivity().getIntent();
        getAllViews(v);
        v = adaptViewsToIdeaFragment(v);
        prepareButtonSave();
        getUpdateMode();
        fillTextViews();
        autoCompleteACTextViewIdea();
        autoCompleteACTextViewDesc();

        return v;
    }




    // get all needed TextViews and Buttons
    private void getAllViews(View v) {
        tvAddIdea = v.findViewById(R.id.txtAddHappyThing);
        acTextViewIdea = v.findViewById(R.id.acTextViewWhat);
        acTextViewDesc = v.findViewById(R.id.acTextViewAdInfo);
        buttonSave = v.findViewById(R.id.btnSave);
    }

    // make views from happy invisible and change text to idea-text
    private View adaptViewsToIdeaFragment(View v){
        v.findViewById(R.id.acTextViewWith).setVisibility(View.GONE);
        v.findViewById(R.id.acTextViewWhere).setVisibility(View.GONE);
        v.findViewById(R.id.editTextWhen).setVisibility(View.GONE);
        v.findViewById(R.id.btnChangeDate).setVisibility(View.GONE);
        tvAddIdea.setText(R.string.text_what_might_make_you_happy);
        acTextViewIdea.setHint(R.string.idea);
        return v;
    }

    // set buttonSave onClicklistener, enable it and set TextWatcher
    private void prepareButtonSave() {
        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);
        acTextViewIdea.addTextChangedListener(addIdeaTextWatcher);
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

    // fill textViews with either text to update or unsaved text
    private void fillTextViews() {
        String what = null;
        String adInfo = null;
        if (update){
            // if we are in edit mode
            what = intent.getStringExtra(KEY_IDEA);
            adInfo = intent.getStringExtra(KEY_IDEA_DESCRIPTION);
        } else {
            // if there was unsaved data
            if (prefs.contains(TMP_IDEA)) what = prefs.getString(TMP_IDEA, "");
            if (prefs.contains(TMP_DESC)) adInfo = prefs.getString(TMP_DESC, "");
        }
        acTextViewIdea.setText(what);
        acTextViewDesc.setText(adInfo);
    }

    // set adapter for acTextViewIdea to get autocompletion
    private void autoCompleteACTextViewIdea() {
        acTextViewIdea.setThreshold(1);
        ideaViewModel.getAllIdeasWhat().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ideaWhatAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                acTextViewIdea.setAdapter(ideaWhatAdapter);
            }
        });
    }


    // set adapter for acTextViewIdea to get autocompletion
    private void autoCompleteACTextViewDesc() {
        acTextViewDesc.setThreshold(1);
        ideaViewModel.getAllIdeasDesc().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ideaDescAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                acTextViewDesc.setAdapter(ideaDescAdapter);
            }
        });
    }

    // on Save button
    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Idea idea = new Idea(acTextViewIdea.getText().toString(), acTextViewDesc.getText().toString());
            if(update){
                int id = intent.getIntExtra(KEY_ID, -1);
                if(id == -1){
                    Toast.makeText(getContext(), "This should not happen", Toast.LENGTH_SHORT).show();
                } else {
                    idea.setId(id);
                    ideaViewModel.update(idea);
                }
                Toast.makeText(getContext(), getString(R.string.idea_updated), Toast.LENGTH_SHORT).show();
            } else {
                ideaViewModel.insert(idea);
                Toast.makeText(getContext(), getString(R.string.toast_saved), Toast.LENGTH_SHORT).show();
            }
            // clean the fields (otherwise this would be saved for next time)
            acTextViewIdea.setText("");
            acTextViewDesc.setText("");
            // this also closes the keyboard!
            getActivity().finish();
        }
    };

    // textwatcher to enable save button when textviews are not empty
    private TextWatcher addIdeaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonSave.setEnabled(!acTextViewIdea.getText().toString().trim().isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };



    // on destroy save data in fields if not in update mode
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(!update) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(TMP_IDEA, acTextViewIdea.getText().toString())
                    .putString(TMP_DESC, acTextViewDesc.getText().toString());
            editor.commit();
        }
    }

}