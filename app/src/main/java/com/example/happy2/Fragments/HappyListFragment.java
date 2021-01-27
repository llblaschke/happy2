package com.example.happy2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.Adapters.HappyListAdapter;
import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.Dialogs.SortByDialogFragment;
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
    public static final String SHOW_AS_TITLE = "com.example.happy2.SHOW_AS_TITLE";
    public static final String SHOW_AS_DESCRIPTION = "com.example.happy2.SHOW_AS_DESCRIPTION";

    private HappyViewModel happyViewModel;
    private HappyListAdapter happyListAdapter;
    private RecyclerView recyclerView;

    private FloatingActionButton btnAdd;
    private Button btnSort, btnSort2;

    private int showItem1, showItem2;
    private boolean titleButtonPressed, recyclerViewMustBeUpdated;
    private String[] sort_by_list;



    public HappyListFragment() {
        // Required empty public constructor
        showItem1 = 0;
        showItem2 = 1;
    }

    public static HappyListFragment newInstance(int showAsTitle, int showAsDescription) {

        Bundle args = new Bundle();
        args.putInt(SHOW_AS_TITLE, showAsTitle);
        args.putInt(SHOW_AS_DESCRIPTION, showAsDescription);

        HappyListFragment fragment = new HappyListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sort_by_list = getResources().getStringArray(R.array.selectable_items_sort);

        if (getArguments() == null) {
            showItem1 = 0;
            showItem2 = 1;
        } else {
            showItem1 = getArguments().getInt(SHOW_AS_TITLE, 0);
            showItem2 = getArguments().getInt(SHOW_AS_DESCRIPTION, 1);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_list, container, false);
        prepareButtons(view);
        makeRecyclerView(view);
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

    /* ************************************************
    Buttons
    ************************************************ */
    private void prepareButtons(View view) {
        btnAdd = view.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddHappyThing);

        btnSort = view.findViewById(R.id.btnSortBy);
        btnSort.setOnClickListener(btnSortBy);

        btnSort2 = view.findViewById(R.id.btnSortBy2);
        btnSort2.setOnClickListener(btnSortBy);

        setSortButtonTexts();
    }

    private void setSortButtonTexts() {
        btnSort.setText(sort_by_list[showItem1]);
        btnSort2.setText(sort_by_list[showItem2]);
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
        public void onClick(View v) {
            boolean recyclerViewMustBeUpdated = false;
            titleButtonPressed = v == btnSort;
            showSortByDialog();
        }
    };

    private void showSortByDialog(){
        SortByDialogFragment dialog = new SortByDialogFragment();
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "sortByDialog");
    }

    @Override
    public void onDialogItemSelected(int itemSelected) {
        if (titleButtonPressed) {
            recyclerViewMustBeUpdated = showItem1 != itemSelected;
            if (showItem2 == itemSelected) showItem2 = showItem1;
            showItem1 = itemSelected;
        } else {
            recyclerViewMustBeUpdated = showItem2 != itemSelected;
            if (showItem1 == itemSelected) showItem1 = showItem2;
            showItem2 = itemSelected;
        }
        if (recyclerViewMustBeUpdated) {
            setSortButtonTexts();
            updateRecyclerView();
        }
    }



    /* ************************************************
    Recycler View
    ************************************************ */
    private void makeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewInList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        happyListAdapter = new HappyListAdapter(getContext(), showItem1, showItem2);
        recyclerView.setAdapter(happyListAdapter);
    }

    public void updateRecyclerView() {
        getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                HappyListFragment.newInstance(showItem1, showItem2)).commit();
    }


}