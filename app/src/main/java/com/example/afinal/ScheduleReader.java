package com.example.afinal;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ScheduleReader extends RecyclerView.Adapter<ScheduleReader.DateViewHolder>{

    private Context mCtx;
    private List<Date> dateList;

    public ScheduleReader(Context mCtx, List<Date> dateList){
        this.mCtx = mCtx;
        this.dateList = dateList;
    }


    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.list_item_layout, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = dateList.get(position);
        holder.textDate.setText(date.date);
        holder.textReason.setText(date.reason);
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    class DateViewHolder extends RecyclerView.ViewHolder{

        TextView textReason, textDate;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);

            textReason = itemView.findViewById(R.id.reason);
            textDate = itemView.findViewById(R.id.date);
        }
    }


}
