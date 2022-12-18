package com.example.mymood;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MoodAdapter extends RecyclerView.Adapter<MoodAdapter.MoodsViewHolder> {
    private ArrayList<Mood> moods;
    private OnMoodClickListener onMoodClickListener;

    public MoodAdapter(ArrayList<Mood> moods) {
        this.moods = moods;
    }

    interface OnMoodClickListener {
        void onMoodClick(int position);
        void onLongClick(int position);
    }

    public void setOnMoodClickListener(OnMoodClickListener onMoodClickListener){
        this.onMoodClickListener = onMoodClickListener;
    }

    @NonNull
    @Override
    public MoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_card_view, viewGroup, false);
        return new MoodsViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MoodsViewHolder moodsViewHolder, int i){
        Mood mood = moods.get(i);
        moodsViewHolder.textViewNameMood.setText(mood.getNameMood());
        moodsViewHolder.textViewComment.setText(mood.getComment());
        moodsViewHolder.textViewDateTimeEntry.setText(mood.getDateTimeEntry());
        int colorId;
        int priority = mood.getPriority();
        switch (priority){
            case 1:
                colorId = moodsViewHolder.itemView.getResources().getColor(R.color.text_super);
                break;
            case 2:
                colorId = moodsViewHolder.itemView.getResources().getColor(R.color.text_good);
                break;
            case 3:
                colorId = moodsViewHolder.itemView.getResources().getColor(R.color.text_neutral);
                break;
            case 4:
                colorId = moodsViewHolder.itemView.getResources().getColor(R.color.text_bad);
                break;
            default:
                colorId = moodsViewHolder.itemView.getResources().getColor(R.color.text_terrible);
                break;
        }
        moodsViewHolder.constraintCardView.setBackgroundColor(colorId);
    }

    @Override
    public int getItemCount(){
        return moods.size();
    }

    class MoodsViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewNameMood;
        private TextView textViewComment;
        private TextView textViewDateTimeEntry;
        private ConstraintLayout constraintCardView;

        public MoodsViewHolder(@NonNull View itemView){
            super(itemView);
            constraintCardView = itemView.findViewById(R.id.constraintCardView);
            textViewNameMood = itemView.findViewById(R.id.textViewNameMood);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewDateTimeEntry = itemView.findViewById(R.id.textViewDateTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onMoodClickListener != null){
                        onMoodClickListener.onMoodClick(getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (onMoodClickListener != null){
                        onMoodClickListener.onLongClick(getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    public void filterList(ArrayList<Mood> filteredList){
        moods = filteredList;
        notifyDataSetChanged();
    }

}
