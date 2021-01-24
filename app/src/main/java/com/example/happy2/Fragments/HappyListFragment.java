package com.example.happy2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.Adapters.HappyListAdapter;
import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.Dialogs.SortByDialogFragment;
import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.MainActivity;
import com.example.happy2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HappyListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HappyListFragment<list> extends Fragment implements SortByDialogFragment.SortByDialogListener{

    public static final String TAG = "HappyListFragment";


    private HappyViewModel happyViewModel;
    private HappyListAdapter happyListAdapter;
    private RecyclerView recyclerView;

    private FloatingActionButton btnAdd;
    private Button btnSort, btnSort2;

    private String[] sortList = {"what", "with", "where", "date"};
    private String sortItem1, getSortItem2;



    public HappyListFragment() {
        // Required empty public constructor
    }


    public static HappyListFragment newInstance() {
        Bundle args = new Bundle();
        HappyListFragment fragment = new HappyListFragment();
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
        View view = getLayoutInflater().inflate(R.layout.fragment_happy_list, container, false);
        btnAdd = view.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddHappyThing);
        btnSort = view.findViewById(R.id.btnSortBy);
        btnSort.setOnClickListener(btnSortBy);
        btnSort2 = view.findViewById(R.id.btnSortBy2);
        btnSort2.setOnClickListener(btnSortBy);


        recyclerView = view.findViewById(R.id.recyclerViewInList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        happyListAdapter = new HappyListAdapter(getContext());
        recyclerView.setAdapter(happyListAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        happyViewModel = ViewModelProviders.of(getActivity()).get(HappyViewModel.class);
        happyViewModel.getAllHappyThings().observe(getViewLifecycleOwner(), new Observer<List<HappyThing>>() {
            @Override
            public void onChanged(List<HappyThing> happyThings) {
                happyListAdapter.setHappyThings(happyThings);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        int selectedItemId = ((MainActivity) getActivity()).bottomNavigationView.getSelectedItemId();
        if (selectedItemId != R.id.navigation_happy_list) {
            ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_happy_list);
        }
    }

    private View.OnClickListener btnAddHappyThing = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener btnSortBy= new View.OnClickListener() {
        @Override
        public void onClick(View v) {showSortByDialog();}
    };

    private void showSortByDialog(){
        SortByDialogFragment dialog = new SortByDialogFragment();
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "sortByDialog");
    }

    @Override
    public void onDialogItemSelected(String itemSelected) {
        String d = itemSelected;
        Toast.makeText(getContext(), itemSelected+" selected, nothing happening yet though", Toast.LENGTH_SHORT).show();

    }


}