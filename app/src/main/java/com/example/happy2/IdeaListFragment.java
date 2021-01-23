package com.example.happy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdeaListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdeaListFragment extends Fragment {

    public static final String TAG = "IdeaListFragment";
    private MyList myList = new MyList("Idea");
    private String[] titleList;
    private String[] descList;

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;



    public IdeaListFragment() {
        // Required empty public constructor
    }

    public static IdeaListFragment newInstance() {
        return new IdeaListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStringLists();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_idea_list, container, false);
        btnAdd = view.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddIdea);

        recyclerView = view.findViewById(R.id.recyclerViewInList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        IdeaListAdapter myListAdapter = new IdeaListAdapter(getContext());
        recyclerView.setAdapter(myListAdapter);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        int selectedItemId = ((MainActivity) getActivity()).bottomNavigationView.getSelectedItemId();
        if (selectedItemId != R.id.navigation_ideas) {
            ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_ideas);
        }
    }

    private View.OnClickListener btnAddIdea = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            intent.putExtra("loadIdeaFragment", true);
            startActivity(intent);
        }
    };


    private void setStringLists(){
        titleList = myList.getList()[0];
        descList = myList.getList()[1];
    }

}