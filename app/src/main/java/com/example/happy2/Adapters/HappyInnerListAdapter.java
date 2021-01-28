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
import androidx.recyclerview.widget.RecyclerView;

import com.example.happy2.DataHandling.Room.HappyThing;
import com.example.happy2.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.happy2.R.color.colorHappyLight;
import static com.example.happy2.R.color.colorHappyLightTwo;

public class HappyInnerListAdapter extends RecyclerView.Adapter<HappyInnerListAdapter.MyViewHolder> {

    private List<HappyThing> happyThings;
    private Context context;
    private int background1, background2;
    private int showIndex, showAsTitle, showAsDesc, showOnClick1, showOnClick2;
    private String showValue;

    public HappyInnerListAdapter(Context context, int showIndex, String showValue, int showAsTitle){
        this.context = context;
        this.showIndex = showIndex;
        this.showValue = showValue;
        this.showAsTitle = showAsTitle;
        getShows();
        background1 = ContextCompat.getColor(context, colorHappyLight);
        background2 = ContextCompat.getColor(context, colorHappyLightTwo);
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
        holder.title.setText(happyThing.getX(showAsTitle));
        holder.description.setText(happyThing.getX(showAsDesc));
        holder.tvOnClick1.setText(happyThing.getX(showOnClick1));
        holder.tvOnClick2.setText(happyThing.getX(showOnClick2));

        // set background color
        if(position%2==1){
            holder.cardView.setBackgroundColor(background1);
        }else{
            holder.cardView.setBackgroundColor(background2);
        }
    }


    /* ***********************************************
    STUFF WE NEED FOR SWIPE, DELETE, ...
    *********************************************** */
    @Override
    public int getItemCount() {
        return happyThings.size();
    }

    public void setHappyThings(List<HappyThing> happyThings){
        this.happyThings = happyThings;
        notifyDataSetChanged();
    }

    public HappyThing getHappyThingAt(int position){
        return happyThings.get(position);
    }


    /* ***********************************************
    HELPERS
    *********************************************** */
    private void getShows() {
        List<Integer> indices = new ArrayList<Integer>();
        for (int i = 0; i<5; i++) {
            if (i != showIndex && i != showAsTitle) indices.add(i);
        }
        showAsDesc = indices.get(0);
        showOnClick1 = indices.get(1);
        showOnClick2 = indices.get(2);
    }

    public Integer getShowAsDescription() {
        return showAsDesc;
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
