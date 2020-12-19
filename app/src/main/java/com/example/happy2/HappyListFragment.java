package com.example.happy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HappyListFragment<list> extends Fragment {

    public static final String TAG = "HappyListFragment";
    private MyList myList = new MyList("Happy");
    private String[] titleList;
    private String[] descList;

    FloatingActionButton btnAdd;
    RecyclerView recyclerView;



    public HappyListFragment() {
        // Required empty public constructor
    }

    public static HappyListFragment newInstance() {
        return new HappyListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStringLists();
//        Fragment parent = getParentFragment();
//        Fragment p = parent;
//        View v = getView();
//        recyclerView = getView().findViewById(R.id.recyclerView);
//        ListAdapter listAdapter = new ListAdapter(getContext(), titleList, descList);
//        recyclerView.setAdapter(listAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getLayoutInflater().inflate(R.layout.fragment_happy_list, container, false);
        btnAdd = view.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddHappyThing);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewInList);
        MyListAdapter myListAdapter = new MyListAdapter(getContext(), titleList, descList);
        recyclerView.setAdapter(myListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    private View.OnClickListener btnAddHappyThing = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddHappyActivity.class);
            startActivity(intent);
        }
    };


    private void setStringLists(){
        titleList = myList.getList()[0];
        descList = myList.getList()[1];
    }

}