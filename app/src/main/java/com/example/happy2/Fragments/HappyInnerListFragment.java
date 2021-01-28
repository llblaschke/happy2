package com.example.happy2.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.Adapters.HappyInnerListAdapter;
import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.IdeaViewModel;
import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.DataHandling.Room.Idea;
import com.example.happy2.Dialogs.SortByDialogFragment;
import com.example.happy2.MainActivity;
import com.example.happy2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HappyInnerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HappyInnerListFragment<list> extends Fragment implements SortByDialogFragment.SortByDialogListener{

    public static final String TAG = "HappyInnerListFragment";
    public static final String SHOW_INDEX = "com.example.happy2.SHOW_INDEX";
    public static final String SHOW_VALUE = "com.example.happy2.SHOW_VALUE";
    public static final String SHOW_AS_TITLE = "com.example.happy2.SHOW_AS_TITLE";
    public static final String SHOW_AS_DESCRIPTION = "com.example.happy2.SHOW_AS_DESCRIPTION";

    private RecyclerView recyclerView;
    private HappyViewModel happyViewModel;
    private HappyInnerListAdapter happyInnerListAdapter;

    private TextView tvTopShow;
    private FloatingActionButton btnAdd;
    private Button btnSort, btnSort2;

    private int showIndex, showItem1, showItem2;
    private String showValue;
    private boolean titleButtonPressed, recyclerViewMustBeUpdated;
    private String[] sort_by_list;



    public HappyInnerListFragment() {
        // Required empty public constructor
        showItem1 = 0;
        showItem2 = 1;
    }

    public static HappyInnerListFragment newInstance(int showIndex, String showValue, int showAsTitle, int showAsDescription) {
        Bundle args = new Bundle();
        args.putInt(SHOW_INDEX, showIndex);
        args.putString(SHOW_VALUE, showValue);
        args.putInt(SHOW_AS_TITLE, showAsTitle);
        args.putInt(SHOW_AS_DESCRIPTION, showAsDescription);

        HappyInnerListFragment fragment = new HappyInnerListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sort_by_list = getResources().getStringArray(R.array.selectable_items_sort);

        if (getArguments() == null) {
            showValue = "";
            showIndex = 0;
            showItem1 = 1;
            showItem2 = 2;
            Toast.makeText(getContext(),TAG + " something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            showValue = getArguments().getString(SHOW_VALUE, "");
            showIndex = getArguments().getInt(SHOW_INDEX, 0);
            showItem1 = getArguments().getInt(SHOW_AS_TITLE, 1);
            showItem2 = getArguments().getInt(SHOW_AS_DESCRIPTION, 2);
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_list, container, false);
        prepareButtons(view);
        prepareToolbarTopShow(view);
        makeHappyViewModel();
        makeRecyclerView(view);
        return view;
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
    Toolbar Top Show
    ************************************************ */
    private void prepareToolbarTopShow(View view) {
        tvTopShow = view.findViewById(R.id.tvTopShow);
        tvTopShow.setVisibility(View.VISIBLE);
        tvTopShow.setText(showValue);
    }



    /* ************************************************
    Buttons
    ************************************************ */
    // TODO everything about the buttons
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
        // TODO only show selectable entries (not showIndex!)
        SortByDialogFragment dialog = new SortByDialogFragment();
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "sortByDialog");
    }

    @Override
    public void onDialogItemSelected(int itemSelected) {
        // TODO this is not what should happen in here
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
    HappyViewModel
    ************************************************ */
    private void makeHappyViewModel() {
        happyViewModel = ViewModelProviders.of(getActivity()).get(HappyViewModel.class);
        happyViewModel.getAllHappyThingsWhereXis(showIndex, showValue).observe(getViewLifecycleOwner(), new Observer<List<HappyThing>>() {
            @Override
            public void onChanged(List<HappyThing> happyThings) {
                happyInnerListAdapter.setHappyThings(happyThings);
            }
        });
    }



    /* ************************************************
    Recycler View
    ************************************************ */
    private void makeRecyclerView(View view) {
        recyclerView = view.findViewById(R.id.recyclerViewInList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        happyInnerListAdapter = new HappyInnerListAdapter(getContext(), this, showIndex, showValue, showItem1, showItem2);
        recyclerView.setAdapter(happyInnerListAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                HappyThing happyThing = happyInnerListAdapter.getHappyThingAt(viewHolder.getAdapterPosition());
                if(direction == ItemTouchHelper.LEFT) {
                    deleteItem(happyThing);
                }else{
                    updateItem(happyThing);
                }
            }
        }).attachToRecyclerView(recyclerView);

    }

    public void updateRecyclerView() {
        // TODO????
    }


}