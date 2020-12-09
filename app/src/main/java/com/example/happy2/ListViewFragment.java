package com.example.happy2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListViewFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "elementMain";
    private static final String ARG_PARAM2 = "elementSmall";
    private static final String ARG_PARAM3 = "index";

    // TODO: Rename and change types of parameters
    private String elementMain;
    private String elementSmall;
    private int index;

    public ListViewFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ListViewFragment newInstance(String elementMain, String elementSmall, int index) {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, elementMain);
        args.putString(ARG_PARAM2, elementSmall);
        args.putInt(ARG_PARAM3, index);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            elementMain = getArguments().getString(ARG_PARAM1);
            elementSmall = getArguments().getString(ARG_PARAM2);
            index = getArguments().getInt(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);
        ((TextView) view.findViewById(R.id.textViewListElementMain)).setText(elementMain);
        ((TextView) view.findViewById(R.id.textViewListElementSmall)).setText("   " +elementSmall);
        if(index%2==1){
            view.setBackgroundColor(getResources().getColor(R.color.colorHappyLightTwo));
        }

        return view;
    }
}