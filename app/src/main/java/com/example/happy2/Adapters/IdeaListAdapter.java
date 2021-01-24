package com.example.happy2.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
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

import com.example.happy2.R;
import com.example.happy2.DataHandling.Room.Idea;

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


    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener {

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

            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
        }

        /**
         * Method to handle a click on the item
         * @param v View to handle click on
         */
        @RequiresApi(api = Build.VERSION_CODES.Q)
        public void onClick(View v) {
            boolean singleLine = title.isSingleLine();
            title.setSingleLine(!singleLine);
            description.setSingleLine(!singleLine);
        }

        /**
         * TODO
         * Method to handle long click on the item
         * @param v View to handle click on
         * @return SOMETHING?
         */
        @Override
        public boolean onLongClick(View v) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);//,R.style.confirmdialog);
            alertDialog.setTitle(R.string.Change_or_delete_item_question);
            alertDialog.setPositiveButton(R.string.delete,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "delete pressed", Toast.LENGTH_SHORT).show();
                        }
                    });
            // on pressing cancel button
            alertDialog.setNegativeButton(R.string.change,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "change pressed", Toast.LENGTH_SHORT).show();
                        }
                    });

            alertDialog.show();
            Log.v(TAG, "Long click fired!");
            return false;
        }

        /*
         TODO see if we need the update view's elements function
         * Function to update view's elements
         * @param message Good data to be updated to
        public void bindData(Item message) {
            mData = message;
             * Set values of views here

        }*/
    }
}
