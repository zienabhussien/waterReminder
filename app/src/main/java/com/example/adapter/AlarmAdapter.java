package com.example.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.R;
import com.example.reminderapp.room.Util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.viewHolder> {
    List<Pair<Long,Integer>> dayLogList;
    Context mContext;

    public AlarmAdapter(List<Pair<Long,Integer>> dayLogList, Context mContext) {
        this.dayLogList = dayLogList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new viewHolder(view);
    }

    public void setDayLogList(List<Pair<Long,Integer>> dayLogList) {
        this.dayLogList = dayLogList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        holder.currentTV.setText(Util.getCurrentTime(dayLogList.get(position).first));
        holder.cup_sizeTV.setText(dayLogList.get(position).second + " ml");


    }

    @Override
    public int getItemCount() {
        return dayLogList.size();
    }



    class viewHolder extends RecyclerView.ViewHolder {
        TextView currentTV, cup_sizeTV;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            currentTV = itemView.findViewById(R.id.currTime_item);
            cup_sizeTV = itemView.findViewById(R.id.cup_size_item);
        }
    }
}
