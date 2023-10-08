package com.yusufmirza.theyksproject.timer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.yusufmirza.theyksproject.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerFragment extends Fragment {

    ProgressBar progressBarDay,progressBarHour,progressBarMinute,progressBarSecond;
    Button buttonAyt,buttonTyt,buttonYdt,buttonMsu;
    TextView textDay,textHour,textMinute,textSecond,textType;

    SharedPreferences sharedPreferences;
    Runnable runnable;
    Handler handler;
    Date theDate,currentDate;
    AlertDialog.Builder diyalogColorSelect;
    LinearLayout linearLayoutDay,linearLayoutHour,linearLayoutMinute,linearLayoutSecond;
    Context context;

    public TimerFragment(Context context){
        this.context=context;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timer_fragment,container,false);
        return inflater.inflate(R.layout.timer_fragment,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarDay=view.findViewById(R.id.progressBarDay);
        progressBarDay.setMax(365);
        progressBarHour=view.findViewById(R.id.progressBarHour);
        progressBarHour.setMax(24);
        progressBarMinute=view.findViewById(R.id.progressBarMinute);
        progressBarMinute.setMax(60);
        progressBarSecond=view.findViewById(R.id.progressBarSecond);
        progressBarSecond.setMax(60);

        sharedPreferences= context.getSharedPreferences("com.TimerFragment",Context.MODE_PRIVATE);
        String dayvalue = sharedPreferences.getString("day","");
        String hourvalue = sharedPreferences.getString("hour","");
        String minutevalue = sharedPreferences.getString("minute","");
        String secondvalue = sharedPreferences.getString("second","");

        GradientDrawable gradientProgressDay = findGradientShape(progressBarDay);
        GradientDrawable gradientProgressHour = findGradientShape(progressBarHour);
        GradientDrawable gradientProgressMinute = findGradientShape(progressBarMinute);
        GradientDrawable gradientProgressSecond = findGradientShape(progressBarSecond);

        if(dayvalue.equals("")){
           gradientProgressDay.setColor(getResources().getColor(R.color.blue));
        } else if(dayvalue.equals("blue")){
            gradientProgressDay.setColor(getResources().getColor(R.color.blue));
        }else if(dayvalue.equals("red")){
            gradientProgressDay.setColor(getResources().getColor(R.color.red));
        }else if(dayvalue.equals("green")){
            gradientProgressDay.setColor(getResources().getColor(R.color.green));
        }else if(dayvalue.equals("yellow")){
            gradientProgressDay.setColor(getResources().getColor(R.color.yellow));
        }else if(dayvalue.equals("purple")){
            gradientProgressDay.setColor(getResources().getColor(R.color.purple));
        }

        if(hourvalue.equals("")){
            gradientProgressHour.setColor(getResources().getColor(R.color.blue));
        } else if(hourvalue.equals("blue")){
            gradientProgressHour.setColor(getResources().getColor(R.color.blue));
        }else if(hourvalue.equals("red")){
            gradientProgressHour.setColor(getResources().getColor(R.color.red));
        }else if(hourvalue.equals("green")){
            gradientProgressHour.setColor(getResources().getColor(R.color.green));
        }else if(hourvalue.equals("yellow")){
            gradientProgressHour.setColor(getResources().getColor(R.color.yellow));
        }else if(hourvalue.equals("purple")){
            gradientProgressHour.setColor(getResources().getColor(R.color.purple));
        }

        if(minutevalue.equals("")){
            gradientProgressMinute.setColor(getResources().getColor(R.color.blue));
        } else if(minutevalue.equals("blue")){
            gradientProgressMinute.setColor(getResources().getColor(R.color.blue));
        }else if(minutevalue.equals("red")){
            gradientProgressMinute.setColor(getResources().getColor(R.color.red));
        }else if(minutevalue.equals("green")){
            gradientProgressMinute.setColor(getResources().getColor(R.color.green));
        }else if(minutevalue.equals("yellow")){
            gradientProgressMinute.setColor(getResources().getColor(R.color.yellow));
        }else if(minutevalue.equals("purple")){
            gradientProgressMinute.setColor(getResources().getColor(R.color.purple));
        }

        if(secondvalue.equals("")){
            gradientProgressSecond.setColor(getResources().getColor(R.color.blue));
        } else if(secondvalue.equals("blue")){
            gradientProgressSecond.setColor(getResources().getColor(R.color.blue));
        }else if(secondvalue.equals("red")){
            gradientProgressSecond.setColor(getResources().getColor(R.color.red));
        }else if(secondvalue.equals("green")){
            gradientProgressSecond.setColor(getResources().getColor(R.color.green));
        }else if(secondvalue.equals("yellow")){
            gradientProgressSecond.setColor(getResources().getColor(R.color.yellow));
        }else if(secondvalue.equals("purple")){
            gradientProgressSecond.setColor(getResources().getColor(R.color.purple));
        }




        linearLayoutDay = view.findViewById(R.id.linearProgressBarDay);
        linearLayoutHour = view.findViewById(R.id.linearProgressBarHour);
        linearLayoutMinute = view.findViewById(R.id.linearProgressBarMinute);
        linearLayoutSecond = view.findViewById(R.id.linearProgressBarSecond);

        linearLayoutDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable progressDrawable = progressBarDay.getProgressDrawable();
                LayerDrawable layers = (LayerDrawable) progressDrawable;
                Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
                GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;

                String dayvalue = sharedPreferences.getString("day","");


                    setAlertDialog(gradientProgressShape,"day",dayvalue);

                    diyalogColorSelect.create();
                    diyalogColorSelect.show();
                    // Elde edilen rengi kullanabilirsiniz


            }
        });


        linearLayoutHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable progressDrawable = progressBarHour.getProgressDrawable();
                LayerDrawable layers = (LayerDrawable) progressDrawable;
                Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
                GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;
                String hourvalue = sharedPreferences.getString("hour","");

                    setAlertDialog(gradientProgressShape,"hour",hourvalue);

                    diyalogColorSelect.create();
                    diyalogColorSelect.show();
                    // Elde edilen rengi kullanabilirsiniz


            }
        });

        linearLayoutMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable progressDrawable = progressBarMinute.getProgressDrawable();
                LayerDrawable layers = (LayerDrawable) progressDrawable;
                Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
                GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;
                String minutevalue = sharedPreferences.getString("minute","");


                    setAlertDialog(gradientProgressShape,"minute",minutevalue);

                    diyalogColorSelect.create();
                    diyalogColorSelect.show();
                    // Elde edilen rengi kullanabilirsiniz

            }
        });

        linearLayoutSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable progressDrawable = progressBarSecond.getProgressDrawable();
                LayerDrawable layers = (LayerDrawable) progressDrawable;
                Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
                GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;
                String secondvalue = sharedPreferences.getString("second","");


                    setAlertDialog(gradientProgressShape,"second",secondvalue);

                    diyalogColorSelect.create();
                    diyalogColorSelect.show();
                    // Elde edilen rengi kullanabilirsiniz





            }
        });


        textDay = view.findViewById(R.id.textDay);
        textHour = view.findViewById(R.id.textHour);
        textMinute = view.findViewById(R.id.textMinute);
        textSecond = view.findViewById(R.id.textSecond);
        textType = view.findViewById(R.id.textType);

        buttonAyt = view.findViewById(R.id.buttonAYT);
        buttonTyt = view.findViewById(R.id.buttonTYT);
        buttonYdt = view.findViewById(R.id.buttonYDT);
        buttonMsu = view.findViewById(R.id.button_msu);


        buttonMsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theDate = new Date(124,2,24,10,15,0);
                textType.setText("MSÜ");
                ThreadFunc();
            }
        });



       buttonAyt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               theDate = new Date(124,5,23,10,0,0); //AYT
               textType.setText("AYT");
               ThreadFunc();
           }
       });

        buttonTyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theDate = new Date(124,5,22,10,0,0); //TYT
                textType.setText("TYT");
                ThreadFunc();
            }
        });

        buttonYdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theDate = new Date(124,5,23,15,30,0); //YDT
                textType.setText("YDT");
                ThreadFunc();
            }
        });


         if(theDate==null){
             theDate = new Date(124,5,22,10,0,0); //TYT
             textType.setText("TYT");
             ThreadFunc();

         }

    }

    public void setAlertDialog(GradientDrawable gradientProgressShape,String key,String color){

        Spinner spinner = new Spinner(context);
        spinner.setGravity(Gravity.CENTER);
        String[] colors ={"mavi","kırmızı","yeşil","sarı","mor"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,colors);
        spinner.setAdapter(arrayAdapter);


        if(color.equals("")){
            spinner.setSelection(0);
        } else if(color.equals("blue")){
            spinner.setSelection(0);
        }else if(color.equals("red")){
            spinner.setSelection(1);
        }else if(color.equals("green")){
            spinner.setSelection(2);
        }else if(color.equals("yellow")){
            spinner.setSelection(3);
        }else if(color.equals("purple")){
            spinner.setSelection(4);
        }



        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (position==0){
                            int newColor = getResources().getColor(R.color.blue);
                            gradientProgressShape.setColor(newColor);
                            sharedPreferences.edit().putString(key,"blue").apply();
                        } else if (position==1){
                            int newColor = getResources().getColor(R.color.red);
                            gradientProgressShape.setColor(newColor);
                            sharedPreferences.edit().putString(key,"red").apply();
                        } else if (position==2){
                            int newColor = getResources().getColor(R.color.green);
                            gradientProgressShape.setColor(newColor);
                            sharedPreferences.edit().putString(key,"green").apply();
                        } else if (position==3){
                            int newColor = getResources().getColor(R.color.yellow);
                            gradientProgressShape.setColor(newColor);
                            sharedPreferences.edit().putString(key,"yellow").apply();
                        } else if (position==4){
                            int newColor = getResources().getColor(R.color.purple);
                            gradientProgressShape.setColor(newColor);
                            sharedPreferences.edit().putString(key,"purple").apply();
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

         diyalogColorSelect = new AlertDialog.Builder(context);

        diyalogColorSelect.setView(spinner);

        diyalogColorSelect.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    public GradientDrawable findGradientShape(ProgressBar myProgressBar){

        Drawable progressDrawable = myProgressBar.getProgressDrawable();
        LayerDrawable layers = (LayerDrawable) progressDrawable;
        Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
        GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;
        return gradientProgressShape;
    }

    public void ThreadFunc() {

        handler = new Handler();

        runnable= new Runnable() {


            @Override
            public void run() {

                currentDate = Calendar.getInstance().getTime();
                long miliseconds = theDate.getTime()-currentDate.getTime();


                int day = (int) ((miliseconds)/(1000*60*60*24));
                int remainingmilis1 = (int) (miliseconds-(day*1000*60*60*24));
                int hour = (remainingmilis1/ (1000*60*60));
                int remainingmilis2 = (remainingmilis1-(hour*1000*60*60));
                int minute = (remainingmilis2/ (1000*60));
                int remainingmilis3 = (remainingmilis2-(minute*1000*60));
                int second = (remainingmilis3/1000);

                textDay.setText("GÜN :" +day  );
                textHour.setText("SAAT : "+ hour);
                textMinute.setText("DAKİKA : "+ minute);
                textSecond.setText("SANİYE : "+ second);

                progressBarDay.setProgress(day);
                progressBarHour.setProgress(hour);
                progressBarMinute.setProgress(minute);
                progressBarSecond.setProgress(second);




                handler.postDelayed(runnable,1000);
            }
        };

        handler.post(runnable);
    }
}
