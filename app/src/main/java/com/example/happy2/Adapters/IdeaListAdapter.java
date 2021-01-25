package com.example.happy2.Adapters;

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

import com.example.happy2.DataHandling.Room.Idea;
import com.example.happy2.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.happy2.R.color.colorHappyLight;
import static com.example.happy2.R.color.colorHappyLightTwo;

public class IdeaListAdapter extends RecyclerView.Adapter<IdeaListAdapter.MyViewHolder> {
    private final String TAG = "IdeaListAdapter";

    private List<Idea> ideas = new ArrayList<>();
    private Context context;
    private int background1, background2;

    public IdeaListAdapter(Context ct){
        context = ct;
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
        Idea idea = ideas.get(position);

        holder.title.setText(idea.getWhat());
        holder.description.setText(idea.getAdInfo());
        if(position%2==1){
            holder.cardView.setBackgroundColor(background1);
        }else holder.cardView.setBackgroundColor(background2);
    }


    @Override
    public int getItemCount() {
        return ideas.size();
    }

    public void setIdeas(List<Idea> ideas){
        this.ideas = ideas;
        notifyDataSetChanged();
    }

    public Idea getIdeaAt(int position){
        return ideas.get(position);
    }


    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final String TAG = "MyViewHolder_IdeaList";

        TextView title, description;
        CardView cardView;
        ConstraintLayout rowView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvRowTitle);
            description = itemView.findViewById(R.id.tvRowDescription);
            cardView = itemView.findViewById(R.id.cvRowWhole);
            rowView = itemView.findViewById(R.id.list_row);

            itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.Q)
                public void onClick(View v) {
                    boolean singleLine = title.isSingleLine();
                    title.setSingleLine(!singleLine);
                    description.setSingleLine(!singleLine);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(context,
                            context.getString(R.string.swipe_left_to_delete_and_right_to_change_this_entry),
                            Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }
    }
}
