package com.example.happy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HappyListFragment<list> extends Fragment implements SortByDialogFragment.SortByDialogListener{

    public static final String TAG = "HappyListFragment";
    private MyList myList = new MyList("Happy");
    private String[] titleList;
    private String[] descList;

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;
    Button btnSort, btnSort2;

    private String[] sortList = {"what", "with", "where", "date"};
    private String sortItem1, getSortItem2;



    public HappyListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStringLists();
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
        MyListAdapter myListAdapter = new MyListAdapter(getContext(), titleList, descList);
        recyclerView.setAdapter(myListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
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


    private void setStringLists(){
        titleList = myList.getList()[0];
        descList = myList.getList()[1];
    }
}