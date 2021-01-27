package com.example.happy2.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.R;
import com.example.happy2.DataHandling.Room.HappyThing;

import java.util.ArrayList;
import java.util.List;

import static com.example.happy2.R.color.colorHappyLight;
import static com.example.happy2.R.color.colorHappyLightTwo;

public class HappyListAdapter extends RecyclerView.Adapter<HappyListAdapter.MyViewHolder> {
    private final String TAG = "HappyListAdapter";

    private List<HappyThing> happyThings = new ArrayList<>();
    private Context context;
    private int background1, background2;
    private int showAsTitle, showAsDesc;

    public HappyListAdapter(Context ct, int showAsTitle, int showAsDesc){
        context = ct;
        background1 = ContextCompat.getColor(context, colorHappyLight);
        background2 = ContextCompat.getColor(context, colorHappyLightTwo);
        this.showAsTitle = showAsTitle;
        this.showAsDesc = showAsDesc;
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
        HappyThing happyThing = happyThings.get(position);

        holder.title.setText(getCorrectStringFromHappyThing(happyThing, showAsTitle));
        holder.description.setText(getCorrectStringFromHappyThing(happyThing, showAsDesc));
        if(position%2==1){
            holder.cardView.setBackgroundColor(background1);
        }else{
            holder.cardView.setBackgroundColor(background2);
        }
    }

    @Override
    public int getItemCount() {
        return happyThings.size();
    }

    public void setHappyThings(List<HappyThing> happyThings){
        this.happyThings = happyThings;
        notifyDataSetChanged();
    }


    private String getCorrectStringFromHappyThing(HappyThing happyThing, int show) {
        switch (show) {
            case 0: return happyThing.getWhat();
            case 1: return happyThing.getWith();
            case 2: return happyThing.getWhere();
            case 3: return happyThing.getWhen();
            default: return "";
        }

    }


    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, description;
        CardView cardView;
        ConstraintLayout rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRowTitle);
            description = itemView.findViewById(R.id.tvRowDescription);
            cardView = itemView.findViewById(R.id.cvRowWhole);
            rowView = itemView.findViewById(R.id.list_row);
        }
    }
}
