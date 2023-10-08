package com.yusufmirza.theyksproject.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.theyksproject.R;


public class DailyCalendarAdapter extends RecyclerView.Adapter<DailyCalendarAdapter.DayHolder> {

    Context context;


    public DailyCalendarAdapter(Context context) {
         this.context=context;
    }



    @NonNull
    @Override
    public DayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_hourcell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight()/12);
        return new DayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayHolder holder, int position) {
          holder.hourView.setText("YAKINDA GELECEK :) ");

    }



    @Override
    public int getItemCount() {
        return 31;
    }

    public class DayHolder extends RecyclerView.ViewHolder {
        TextView hourView;

        public DayHolder(@NonNull View itemView) {
            super(itemView);
            hourView= itemView.findViewById(R.id.hourTimeView);



        }
    }



}

