package com.example.happy2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.Fragments.HappyListFragment;
import com.example.happy2.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.happy2.R.color.colorHappyLight;
import static com.example.happy2.R.color.colorHappyLightTwo;

public class HappyListAdapter extends RecyclerView.Adapter<HappyListAdapter.MyViewHolder> {
    private final String TAG = "HappyListAdapter";

    private HappyViewModel happyViewModel;
    private HappyListFragment happyListFragment;
    private List<HappyThing> happyThings = new ArrayList<>();
    private Context context;
    private int background1, background2;
    private int showAsTitle, showAsDesc;
    private List<String> allTitles = new ArrayList<>();

    public HappyListAdapter(Context context, HappyListFragment happyListFragment, int showAsTitle, int showAsDesc){
        this.context = context;
        this.happyListFragment = happyListFragment;
        this.showAsTitle = showAsTitle;
        this.showAsDesc = showAsDesc;
        background1 = ContextCompat.getColor(context, colorHappyLight);
        background2 = ContextCompat.getColor(context, colorHappyLightTwo);
        happyViewModel = ViewModelProviders.of(happyListFragment).get(HappyViewModel.class);
        getAllTitles();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        String title = allTitles.get(position);
        holder.title.setText(title);

        happyViewModel
                .getXwhereYis(showAsDesc, showAsTitle, title)
                .observe(happyListFragment, new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        String tmpDescription = "";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            tmpDescription = String.join(", ", strings);
                        } else {
                            for(int i = 0; i<strings.size(); i++) {
                                tmpDescription += strings.get(i);
                                if(i < strings.size()-1) tmpDescription += ", ";
                            }
                        }
                        holder.description.setText(tmpDescription);
                    };
        });

        if(position%2==1){
            holder.cardView.setBackgroundColor(background1);
        }else{
            holder.cardView.setBackgroundColor(background2);
        }
    }

    public void getAllTitles() {
        happyViewModel.getDistinctX(showAsTitle).observe(happyListFragment, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                allTitles = strings;
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return allTitles.size();
    }

    public void setHappyThings(List<HappyThing> happyThings){
        this.happyThings = happyThings;
        // prepare dataset
        notifyDataSetChanged();
    }


    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRowTitle);
            description = itemView.findViewById(R.id.tvRowDescription);
            cardView = itemView.findViewById(R.id.cvRowWhole);

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                public void onClick(View v) {
                    // TODO implement loading of inner list happy fragment
                }
            });
        }
    }
}
