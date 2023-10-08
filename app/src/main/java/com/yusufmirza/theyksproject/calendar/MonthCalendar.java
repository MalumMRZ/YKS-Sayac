package com.yusufmirza.theyksproject.calendar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.calendar.CalendarAdapter;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MonthCalendar extends Fragment {

    Button nextButton,backButton;
    RecyclerView recyclerView;
    TextView monthView;
    LocalDate localDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.month_calendar,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        nextButton = view.findViewById(R.id.button_next);
        backButton = view.findViewById(R.id.button_back);
        monthView = view.findViewById(R.id.monthView);
        recyclerView = view.findViewById(R.id.recyclerView);
        localDate= LocalDate.now();
        monthView.setText(setMonthViewTime(localDate));

         setCalendarView(localDate);



        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               localDate=localDate.plusMonths(1);
               monthView.setText(setMonthViewTime(localDate));
                setCalendarView(localDate);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               localDate=localDate.minusMonths(1);
               monthView.setText(setMonthViewTime(localDate));
                setCalendarView(localDate);
            }
        });

    }


    public void setCalendarView(LocalDate localDate) {
        ArrayList<String> monthsInArrayList = monthsInArray(localDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(monthsInArrayList);
        recyclerView.setAdapter(calendarAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),7));

    }

    public String setMonthViewTime(LocalDate localDate) { //Ortadaki çubuğun durumunu anlatır
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return localDate.format(dateTimeFormatter);
    }

      public ArrayList<String> monthsInArray(LocalDate localDate){
          ArrayList<String> monthdays = new ArrayList<String>();
          YearMonth yearMonth = YearMonth.from(localDate);
          int countDay = yearMonth.lengthOfMonth();
          LocalDate firstDay = localDate.withDayOfMonth(1);
          int dayOfFirstDay = firstDay.getDayOfWeek().getValue();

          for(int i =1; i<42; i++){
              if(i<=dayOfFirstDay || i>countDay+dayOfFirstDay){
                  monthdays.add("");
              } else{
                  monthdays.add(String.valueOf(i-dayOfFirstDay));
              }
          }
           return monthdays;
      }
}
