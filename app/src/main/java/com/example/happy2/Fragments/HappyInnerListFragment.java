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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.Adapters.HappyInnerListAdapter;
import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.Dialogs.ShowCategoriesDialog;
import com.example.happy2.MainActivity;
import com.example.happy2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HappyInnerListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HappyInnerListFragment<list> extends Fragment implements ShowCategoriesDialog.ShowCategoriesDialogListener {

    public static final String TAG = "HappyInnerListFragment";
    public static final String SHOW_INDEX = "com.example.happy2.SHOW_INDEX";
    public static final String SHOW_VALUE = "com.example.happy2.SHOW_VALUE";
    public static final String SHOW_AS_TITLE = "com.example.happy2.SHOW_AS_TITLE";

    private RecyclerView recyclerView;
    private HappyViewModel happyViewModel;
    private HappyInnerListAdapter happyInnerListAdapter;

    private TextView tvTopShow;
    private FloatingActionButton btnAdd;
    private Button btnSort, btnSort2;

    private int showIndex, showAsTitle, showAsDescription;
    private String showValue;
    private boolean titleButtonPressed, recyclerViewMustBeUpdated;
    private String[] sort_by_list;



    public HappyInnerListFragment() {
        // Required empty public constructor
        showValue = "";
        showIndex = 0;
        showAsTitle = 1;
    }

    public static HappyInnerListFragment newInstance(int showIndex, String showValue, int showAsTitle) {
        Bundle args = new Bundle();
        args.putInt(SHOW_INDEX, showIndex);
        args.putString(SHOW_VALUE, showValue);
        args.putInt(SHOW_AS_TITLE, showAsTitle);

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
            showAsTitle = 1;
            Toast.makeText(getContext(),TAG + " something went wrong!", Toast.LENGTH_SHORT).show();
        } else {
            showValue = getArguments().getString(SHOW_VALUE, "");
            showIndex = getArguments().getInt(SHOW_INDEX, 0);
            showAsTitle = getArguments().getInt(SHOW_AS_TITLE, 1);
        }
        makeHappyInnerListAdapter();
        getShowAsDescription();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_happy_list, container, false);
        makeHappyViewModel();
        prepareButtons(view);
        prepareToolbarTopShow(view);
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



//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        happyViewModel = ViewModelProviders.of(getActivity()).get(HappyViewModel.class);
//        happyViewModel.getAllHappyThingsWhereXis(showIndex, showValue).observe(getViewLifecycleOwner(), new Observer<List<HappyThing>>() {
//            @Override
//            public void onChanged(List<HappyThing> happyThings) {
//                happyInnerListAdapter.setHappyThings(happyThings);
//            }
//        });
//    }




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
    showAsDescription needed before setSortButtonTexts()
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
        btnSort.setText(sort_by_list[showAsTitle]);
        btnSort2.setText(sort_by_list[showAsDescription]);
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


        ArrayList disableIndices = new ArrayList<Integer>();
        disableIndices.add(showIndex);
        ShowCategoriesDialog showCategoriesDialog = ShowCategoriesDialog.newInstance(disableIndices);
        showCategoriesDialog.setTargetFragment(this, 0);
        showCategoriesDialog.show(getParentFragmentManager(), TAG);
//        // TODO only show selectable entries (not showIndex!)
//        SortByDialogFragment dialog = new SortByDialogFragment();
//        dialog.setTargetFragment(this, 0);
//        dialog.show(getParentFragmentManager(), "sortByDialog");
    }

    @Override
    public void onDialogItemSelected(int itemSelected) {
        // TODO this is not what should happen in here
        if (titleButtonPressed) {
            recyclerViewMustBeUpdated = showAsTitle != itemSelected;
            if (showAsDescription == itemSelected) showAsDescription = showAsTitle;
            showAsTitle = itemSelected;
        } else {
            recyclerViewMustBeUpdated = showAsDescription != itemSelected;
            if (showAsTitle == itemSelected) showAsTitle = showAsDescription;
            showAsDescription = itemSelected;
        }
        if (recyclerViewMustBeUpdated) {
            setSortButtonTexts();
            updateRecyclerView();
        }
    }



    /* ************************************************
    HappyInnerListAdapter needed before retrieving showAsDescription from it
    ************************************************ */

    private void makeHappyInnerListAdapter() {
        happyInnerListAdapter = new HappyInnerListAdapter(getContext(), showIndex, showValue, showAsTitle);
    }

    private void getShowAsDescription() {
        showAsDescription = happyInnerListAdapter.getShowAsDescription();
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
        recyclerView.setAdapter(happyInnerListAdapter);

        happyViewModel = ViewModelProviders.of(this).get(HappyViewModel.class);
        happyViewModel.getAllHappyThingsWhereXis(showIndex, showValue).observe(getViewLifecycleOwner(), new Observer<List<HappyThing>>() {
            @Override
            public void onChanged(List<HappyThing> happyThings) {
                happyInnerListAdapter.setHappyThings(happyThings);
            }
        });

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
                    //deleteItem(happyThing);
                    Toast.makeText(getContext(), "Swiped left!", Toast.LENGTH_SHORT).show();
                }else{
                    //updateItem(happyThing);
                    Toast.makeText(getContext(), "Swiped right!", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

    }

    public void updateRecyclerView() {
        // TODO????
        happyInnerListAdapter.notifyDataSetChanged();
    }


}