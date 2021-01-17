package com.example.happy2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.happy2.R.color.colorHappyLight;
import static com.example.happy2.R.color.colorHappyLightTwo;

public class HappyListAdapter extends RecyclerView.Adapter<HappyListAdapter.MyViewHolder> {
    private final String TAG = "MyListAdapter";
    private String[] titleList, descList;
    private Context context;
    private int background1, background2;

    public HappyListAdapter(Context ct, String[] tl, String[] dl){
        context = ct;
        titleList = tl;
        descList = dl;
        background1 = ContextCompat.getColor(context, colorHappyLight);
        background2 = ContextCompat.getColor(context, colorHappyLightTwo);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_row, parent, false);
        final MyViewHolder holder = new MyViewHolder(view);

        holder.rowView.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                boolean singleLine = holder.title.isSingleLine();
                holder.title.setSingleLine(!singleLine);
                holder.description.setSingleLine(!singleLine);
            }
        });

        return new MyViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(titleList[position]);
        holder.description.setText(descList[position]);
        if(position%2==1){
            holder.cardView.setBackgroundColor(background1);
        }else{
            holder.cardView.setBackgroundColor(background2);
        }
    }

    @Override
    public int getItemCount() {
        if(titleList.length != descList.length){
            Toast.makeText(context, TAG+": List length differ!", Toast.LENGTH_LONG).show();
        }
        return titleList.length;
    }

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
