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
    private AutoCompleteTextView etIdea;
    private AutoCompleteTextView etDescription;
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
        etIdea = v.findViewById(R.id.edittextWhatDidYouDo);
        etDescription = v.findViewById(R.id.edittextInfo);
        v.findViewById(R.id.edittextWithWhom).setVisibility(View.GONE);
        v.findViewById(R.id.edittextWhere).setVisibility(View.GONE);
        v.findViewById(R.id.editTextWhen).setVisibility(View.GONE);
        v.findViewById(R.id.btnChangeDate).setVisibility(View.GONE);
        buttonSave = v.findViewById(R.id.btnSave);

        tvAddIdea.setText(R.string.text_what_might_make_you_happy);
        etIdea.setHint(R.string.idea);

        // Save button enables only if all EditTexts contain input
        buttonSave.setOnClickListener(btnClickSave);
        buttonSave.setEnabled(false);
        etIdea.addTextChangedListener(addIdeaTextWatcher);

        if (prefs.contains(TMP_IDEA)) etIdea.setText(prefs.getString(TMP_IDEA, ""));
        if (prefs.contains(TMP_DESC)) etDescription.setText(prefs.getString(TMP_DESC, ""));


        etIdea.setThreshold(1);
        etDescription.setThreshold(1);

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
                etIdea.setAdapter(ideaWhatAdapter);
            }
        });

        ideaViewModel.getAllIdeasDesc().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                ideaDescAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.select_dialog_item,
                        new ArrayList<>(new HashSet<>(strings)));
                etDescription.setAdapter(ideaDescAdapter);
            }
        });

        return v;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(TMP_IDEA, etIdea.getText().toString())
                .putString(TMP_DESC, etDescription.getText().toString());
        editor.commit();
    }





    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ideaViewModel.insert(new Idea(etIdea.getText().toString(), etDescription.getText().toString()));
            getActivity().finish();
        }
    };

    private TextWatcher addIdeaTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            buttonSave.setEnabled(!etIdea.getText().toString().trim().isEmpty());
        }
        @Override
        public void afterTextChanged(Editable s) { }
    };

}