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
import com.example.happy2.DataHandling.Room.Idea;
import com.example.happy2.Fragments.HappyInnerListFragment;
import com.example.happy2.R;

import java.util.List;

import static com.example.happy2.R.color.colorHappyLight;
import static com.example.happy2.R.color.colorHappyLightTwo;

public class HappyInnerListAdapter extends RecyclerView.Adapter<HappyInnerListAdapter.MyViewHolder> {

    private HappyViewModel happyViewModel;
    private List<HappyThing> happyThings;

    private HappyInnerListFragment happyInnerListFragment;
    private Context context;
    private int background1, background2;
    private int showIndex, showAsTitle, showAsDesc;
    private String showValue;

    public HappyInnerListAdapter(Context context, HappyInnerListFragment happyInnerListFragment, int showIndex, String showValue, int showAsTitle, int showAsDesc){
        this.context = context;
        this.happyInnerListFragment = happyInnerListFragment;
        this.showIndex = showIndex;
        this.showValue = showValue;
        this.showAsTitle = showAsTitle;
        this.showAsDesc = showAsDesc;
        background1 = ContextCompat.getColor(context, colorHappyLight);
        background2 = ContextCompat.getColor(context, colorHappyLightTwo);
        happyViewModel = ViewModelProviders.of(happyInnerListFragment).get(HappyViewModel.class);
        happyViewModel.getAllHappyThingsWhereXis(showIndex, showValue).observe(happyInnerListFragment, new Observer<List<HappyThing>>() {
            @Override
            public void onChanged(List<HappyThing> happyThings) {

            }
        });
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

        // put observer on the description
        happyViewModel
                .getAllwhereXis(showIndex, showValue)
                .observe(happyInnerListFragment, new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        // TODO set values on textviews
                    };
                });
        // set background color
        if(position%2==1){
            holder.cardView.setBackgroundColor(background1);
        }else{
            holder.cardView.setBackgroundColor(background2);
        }
    }


    /* ***********************************************
    STUFF WE NEED FOR SWIPE, DELETE, ...
     */
    @Override
    public int getItemCount() {
        // TODO
        return 0;
    }

    public void setHappyThings(List<HappyThing> happyThings){
        this.happyThings = happyThings;
        notifyDataSetChanged();
    }

    public HappyThing getHappyThingAt(int position){
        return happyThings.get(position);
    }


    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, description, tvOnClick1, tvOnClick2;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRowTitle);
            description = itemView.findViewById(R.id.tvRowDescription);
            tvOnClick1 = itemView.findViewById(R.id.tvOnClick1);
            tvOnClick2 = itemView.findViewById(R.id.tvOnClick2);

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
