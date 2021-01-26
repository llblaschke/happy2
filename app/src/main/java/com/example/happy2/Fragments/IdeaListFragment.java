package com.example.happy2.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.Adapters.IdeaListAdapter;
import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.IdeaViewModel;
import com.example.happy2.DataHandling.Room.Idea;
import com.example.happy2.MainActivity;
import com.example.happy2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IdeaListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IdeaListFragment extends Fragment {

    public static final String TAG = "IdeaListFragment";

    private IdeaViewModel ideaViewModel;
    private IdeaListAdapter ideaListAdapter;
    private RecyclerView recyclerView;

    private FloatingActionButton btnAdd;



    public IdeaListFragment() {
        // Required empty public constructor
    }

    public static IdeaListFragment newInstance() {
        Bundle args = new Bundle();
        IdeaListFragment fragment = new IdeaListFragment();
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
        View view = getLayoutInflater().inflate(R.layout.fragment_idea_list, container, false);
        btnAdd = view.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddIdea);

        recyclerView = view.findViewById(R.id.recyclerViewInList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ideaListAdapter = new IdeaListAdapter(getContext());
        recyclerView.setAdapter(ideaListAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Idea idea = ideaListAdapter.getIdeaAt(viewHolder.getAdapterPosition());
                if(direction == ItemTouchHelper.LEFT) {
                    deleteItem(idea);
                }else{
                    updateItem(idea);
                }
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    // delete item
    private void deleteItem(final Idea idea) {
        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(getContext());
        deleteDialog.setTitle(getString(R.string.sure_to_delete_this_idea));
        deleteDialog.setNegativeButton(
                getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ideaListAdapter.notifyDataSetChanged();
                    }
                });
        deleteDialog.setPositiveButton(
                getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ideaViewModel.delete(idea);
                        Toast.makeText(getContext(), getString(R.string.idea_deleted), Toast.LENGTH_SHORT).show();
                    }
                });
        deleteDialog.create().show();
    }



    // update item in new AddActivity
    private void updateItem(Idea idea) {
        Intent intent = new Intent(getContext(), AddActivity.class);
        intent.putExtra(AddActivity.KEY_LOAD_IDEA_FRAGMENT, true);
        intent.putExtra(AddActivity.KEY_EDIT_IDEA_HAPPY, true);
        intent.putExtra(AddActivity.KEY_WHAT, idea.getWhat());
        intent.putExtra(AddActivity.KEY_ADINFO, idea.getAdInfo());
        intent.putExtra(AddActivity.KEY_ID, idea.getId());
        startActivity(intent);
    }



    private View.OnClickListener btnAddIdea = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            intent.putExtra(AddActivity.KEY_LOAD_IDEA_FRAGMENT, true);
            startActivity(intent);
        }
    };


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ideaViewModel = ViewModelProviders.of(getActivity()).get(IdeaViewModel.class);
        ideaViewModel.getAllIdeas().observe(getViewLifecycleOwner(), new Observer<List<Idea>>() {
            @Override
            public void onChanged(List<Idea> ideas) {
                ideaListAdapter.setIdeas(ideas);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ideaListAdapter.notifyDataSetChanged();
        int selectedItemId = ((MainActivity) getActivity()).bottomNavigationView.getSelectedItemId();
        if (selectedItemId != R.id.navigation_ideas) {
            ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_ideas);
        }
    }

}