package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reminderapp.R;
import com.example.reminderapp.room.RoomDB;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.viewHolder> {
 List<Item> itemList = new ArrayList<>();
 Context mContext;

    public AlarmAdapter(List<Item> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
        notifyDataSetChanged();
    }

    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
      holder.currentTV.setText(itemList.get(position).getCurrentTime());
      holder.cup_sizeTV.setText(itemList.get(position).getCupSize()+" ml");


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private String getCurrentTime(){
        return new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
    }
    class viewHolder extends RecyclerView.ViewHolder{
     TextView currentTV, cup_sizeTV;
        public viewHolder(@NonNull View itemView) {
            super(itemView);
            currentTV = itemView.findViewById(R.id.currTime_item);
            cup_sizeTV = itemView.findViewById(R.id.cup_size_item);
        }
    }
}
