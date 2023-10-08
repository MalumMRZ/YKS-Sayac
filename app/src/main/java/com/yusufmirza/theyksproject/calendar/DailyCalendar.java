package com.yusufmirza.theyksproject.calendar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yusufmirza.theyksproject.R;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DailyCalendar extends Fragment {

    Context context;
    public DailyCalendar(Context context){
     this.context=context;
    }

    FloatingActionButton floatingActionButton;
    RecyclerView recyclerView;
    Button backDay,forwardDay;
    TextView month_yearView,day_weekView;

    LocalDate localDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.daily_calendar,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //XML kod birleştirildi
        backDay = view.findViewById(R.id.day_backButton);
        forwardDay= view.findViewById(R.id.day_forwardButton);
        month_yearView = view.findViewById(R.id.month_yearView);
        day_weekView = view.findViewById(R.id.week_Day_View);
        recyclerView = view.findViewById(R.id.hourRecyclerView);
        floatingActionButton = view.findViewById(R.id.floatButtonNewEvent);


        localDate= LocalDate.now();
        setDailyCalendarFeatures();




        if(savedInstanceState==null) {
            setTextViews();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"YAKINDA GELECEK :) ",Toast.LENGTH_LONG).show();
            }
        });

        backDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                localDate=localDate.minusDays(1);
                setTextViews();
            }
        });

        forwardDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               localDate=localDate.plusDays(1);
                setTextViews();
            }
        });




    }

    public String monthYearFromDate(LocalDate localDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return localDate.format(dateTimeFormatter);
    }

    public String week_DayFromDate(LocalDate localDate){
        DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("d EEEE");
        return localDate.format(dateTimeFormatter2);
    }

    public void setTextViews() {
        month_yearView.setText(monthYearFromDate(localDate));
        day_weekView.setText(week_DayFromDate(localDate));
    }
    public void setDailyCalendarFeatures(){
        DailyCalendarAdapter dailyCalendarAdapter = new DailyCalendarAdapter(context);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setAdapter(dailyCalendarAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }


}
