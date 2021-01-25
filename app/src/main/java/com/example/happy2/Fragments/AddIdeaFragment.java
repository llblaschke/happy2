package com.example.happy2.Fragments;

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


public class AddIdeaFragment extends Fragment {

    private final String TAG = "AddIdeaFragment";
    private TextView tvAddIdea;
    private AutoCompleteTextView acTextViewIdea;
    private AutoCompleteTextView acTextViewDesc;
    private Button buttonSave;


    public static final String TMP_IDEA = "tmpIdea";
    public static final String TMP_DESC = "tmpDesc";

    private SharedPreferences prefs;

    private IdeaViewModel ideaViewModel;
    private ArrayAdapter ideaWhatAdapter;
    private ArrayAdapter ideaDescAdapter;


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

        tvAddIdea = v.findViewById(R.id.txtAddHappyThing);
        acTextViewIdea = v.findViewById(R.id.acTextViewWhat);
        acTextViewDesc = v.findViewById(R.id.acTextViewAdInfo);
        v.findViewById(R.id.acTextViewWith).setVisibility(View.GONE);
        v.findViewById(R.id.acTextViewWhere).setVisibility(View.GONE);
        v.findViewById(R.id.editTextWhen).setVisibility(View.GONE);
        v.findViewById(R.id.btnChangeDate).setVisibility(View.GONE);
        buttonSave = v.findViewById(R.id.btnSave);

        tvAddIdea.setText(R.string.text_what_might_make_you_happy);
        acTextViewIdea.setHint(R.string.idea);

        // Save button enables only if all EditTexts contain input
        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);
        acTextViewIdea.addTextChangedListener(addIdeaTextWatcher);

        if (prefs.contains(TMP_IDEA)) acTextViewIdea.setText(prefs.getString(TMP_IDEA, ""));
        if (prefs.contains(TMP_DESC)) acTextViewDesc.setText(prefs.getString(TMP_DESC, ""));


        acTextViewIdea.setThreshold(1);
        acTextViewDesc.setThreshold(1);

        ArrayList<String> emptyList = new ArrayList<>();
        emptyList.add("");

        ideaWhatAdapter = new ArrayAdapter(getContext(), android.R.layout.select_dialog_item, new ArrayList<>());

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

        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TMP_IDEA, acTextViewIdea.getText().toString())
                .putString(TMP_DESC, acTextViewDesc.getText().toString());
        editor.commit();
    }





    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ideaViewModel.insert(new Idea(acTextViewIdea.getText().toString(), acTextViewDesc.getText().toString()));
            getActivity().finish();
        }
    };

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

}