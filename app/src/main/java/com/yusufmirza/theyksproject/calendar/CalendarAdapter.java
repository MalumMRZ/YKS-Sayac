package com.yusufmirza.theyksproject.calendar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.theyksproject.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> {

    ArrayList<String> monthDaysArray;



    CalendarAdapter(ArrayList<String> monthDaysArray) {
        this.monthDaysArray=monthDaysArray;
    }



    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell,parent,false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int)(parent.getHeight()/6);
        return new CalendarViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.cellView.setText(monthDaysArray.get(position));
    }


    public class CalendarViewHolder extends RecyclerView.ViewHolder{
            public final TextView cellView;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            cellView = itemView.findViewById(R.id.cell_View);



        }
    }

    @Override
    public int getItemCount() {
        return monthDaysArray.size();
    }
}



