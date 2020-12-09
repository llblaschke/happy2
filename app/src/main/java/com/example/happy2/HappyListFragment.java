package com.example.happy2;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;


public class HappyListFragment<list> extends Fragment {
    private static int leftMarginMain = 50;
    private static int leftMarginSmall = 80;
    private static int topMarginMain = 20;
    private static int getTopMarginSmall = 10;
    private static int textSizeMain = 30;
    private static int textSizeSmall = 20;
    private myList happyList = new myList("Happy");
    Object[][] list = happyList.getList();
    int length = happyList.length();

    public HappyListFragment() {
        // Required empty public constructor
    }

    public static HappyListFragment newInstance() {
        HappyListFragment fragment = new HappyListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String elementMain;
        String elementSmall;
        final FragmentManager fragmentManager = getParentFragmentManager();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_happy_list, container, false);
        ListViewFragment fragment = new ListViewFragment();
        for(int i=0;i<length;i++) {
            elementMain = (String)list[i][0];
            elementSmall = (String)list[i][1];
            fragment = ListViewFragment.newInstance(elementMain,elementSmall,i);
            fragmentManager.beginTransaction().add(R.id.layoutHappyList, fragment, "listViewFragment"+i).commit();
        }
        return view;
    }
}