package com.example.happy2;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class AddIdeaFragment extends Fragment {

    private final String TAG = "AddIdeaFragment";
    private TextView tvAddIdea;
    private EditText etIdea;
    private EditText etDescription;
    private Button buttonSave;



    public AddIdeaFragment() {
        // Required empty public constructor
    }

    public static AddIdeaFragment newInstance(boolean updateAddMore) {
        return new AddIdeaFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        return v;
    }


    private View.OnClickListener btnClickSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getContext(), "Currently not saving anything", Toast.LENGTH_LONG).show();
            Context ct;
            try {
                ct = getContext();
                if(ct != null) {((AddActivity) getActivity()).closeActivity(ct, v);}
            }catch (NullPointerException e){
                Log.d(TAG, "NullPointerException catched: getContext()");
            }
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