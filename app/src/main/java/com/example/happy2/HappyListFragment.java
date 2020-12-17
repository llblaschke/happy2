package com.example.happy2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class HappyListFragment<list> extends Fragment {

    public static final String TAG = "HappyListFragment";
    
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
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            RecyclerViewFragment fragment = new RecyclerViewFragment();
            transaction.replace(R.id.sample_content_fragment, fragment);
            transaction.commit();
        }
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

    /** Create a chain of targets that will receive log data */
    @Override
    public void initializeLogging() {
        // Wraps Android's native log framework.
        LogWrapper logWrapper = new LogWrapper();
        // Using Log, front-end to the logging chain, emulates android.util.log method signatures.
        Log.setLogNode(logWrapper);

        // Filter strips out everything except the message text.
        MessageOnlyLogFilter msgFilter = new MessageOnlyLogFilter();
        logWrapper.setNext(msgFilter);

        // On screen logging via a fragment with a TextView.
        LogFragment logFragment = (LogFragment) getSupportFragmentManager()
                .findFragmentById(R.id.log_fragment);
        msgFilter.setNext(logFragment.getLogView());

        Log.i(TAG, "Ready");
    }
}