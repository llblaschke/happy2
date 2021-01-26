package com.example.happy2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.Room.UnhappyDay;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.MainActivity;
import com.example.happy2.R;

import java.util.Calendar;
import java.util.List;


public class HomeFragment extends Fragment {

    private UnhappyDayViewModel unhappyDayViewModel;

    public HomeFragment(){
        // empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unhappyDayViewModel = ViewModelProviders.of(this).get(UnhappyDayViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        v.findViewById(R.id.btnHappy).setOnClickListener(btnClickHappy);
        v.findViewById(R.id.btnNotHappy).setOnClickListener(btnClickNotHappy);
        v.findViewById(R.id.btnAddIdea).setOnClickListener(btnAddIdea);

        unhappyDayViewModel.getAllUnhappyDaysDates().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> unhappyDays) {
                for(int i =0; i<unhappyDays.size(); i++) {
                    Log.d("HOME FRAGMENT", "Unhappy day: "+ unhappyDays.get(i));
                }
            }
        });
        return v;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs,
                          @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        int selectedItemId = ((MainActivity) getActivity()).bottomNavigationView.getSelectedItemId();
        if (selectedItemId != R.id.navigation_home) {
            ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }





    private View.OnClickListener btnClickNotHappy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Calendar calendar = Calendar.getInstance();
            String today = new StringBuilder().
                    append(calendar.get(Calendar.DAY_OF_MONTH)).append(".")
                    .append(calendar.get(Calendar.MONTH)+1).append(".")
                    .append(calendar.get(Calendar.YEAR))
                    .toString();
            unhappyDayViewModel.insert(new UnhappyDay(today));
            getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                    new NotHappyFragment()).commit();
        }
    };


    private View.OnClickListener btnClickHappy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener btnAddIdea = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            intent.putExtra(AddActivity.KEY_LOAD_IDEA_FRAGMENT, true);
            startActivity(intent);
        }
    };
}