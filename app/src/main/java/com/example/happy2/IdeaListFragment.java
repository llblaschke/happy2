package com.example.happy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdeaListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdeaListFragment extends Fragment {

    public static final String TAG = "IdeaListFragment";

    private IdeaViewModel ideaViewModel;
    private IdeaListAdapter myListAdapter;

    private FloatingActionButton btnAdd;
    private RecyclerView recyclerView;



    public IdeaListFragment() {
        // Required empty public constructor
    }

    public static IdeaListFragment newInstance() {

        Bundle args = new Bundle();

        IdeaListFragment fragment = new IdeaListFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_idea_list, container, false);
        btnAdd = view.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddIdea);

        recyclerView = view.findViewById(R.id.recyclerViewInList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        myListAdapter = new IdeaListAdapter(getContext());
        recyclerView.setAdapter(myListAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ideaViewModel = ViewModelProviders.of(getActivity()).get(IdeaViewModel.class);
        ideaViewModel.getAllIdeas().observe(getViewLifecycleOwner(), new Observer<List<Idea>>() {
            @Override
            public void onChanged(List<Idea> ideas) {
                myListAdapter.setIdeas(ideas);
            }
        });
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



}