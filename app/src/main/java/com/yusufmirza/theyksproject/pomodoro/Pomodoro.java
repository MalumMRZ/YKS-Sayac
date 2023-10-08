package com.yusufmirza.theyksproject.pomodoro;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yusufmirza.theyksproject.R;
import com.yusufmirza.theyksproject.TinyDB;

import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.TimeUnit;


public class Pomodoro extends Fragment {
    ProgressBar progressBar;
    boolean active;

    LinearLayout linearLayout;
    Button cancelButton,saveDataButton,setDataButton;
    TextView textPomodoro,pomodoroStatement;
    CountDownTimer countDownTimer1,countDownTimer2,countDownTimer3,activeTimer;
    EditText minWork, secWork, minBreak, secBreak, minLongBreak, secLongBreak,longBreakByStudy;
    FloatingActionButton floatingActionButton;
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<Integer> veriDizisi;
    Vibrator vibrator;
    TinyDB tinyDB;

    ToneGenerator toneGenerator;
    String getStartColor;




    public Pomodoro(Context context) {
        this.context = context;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pomodoro_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progressPomodoroBar);
        floatingActionButton = view.findViewById(R.id.addPomodoroClock);
        textPomodoro = view.findViewById(R.id.textPomodoro);
        pomodoroStatement = view.findViewById(R.id.pomodoroStatement);
        tinyDB = new TinyDB(context);

        sharedPreferences= context.getSharedPreferences("MyColorPreferences",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();




        getStartColor = sharedPreferences.getString("color","31");
        if(getStartColor.equals("31")){
            changePomodoroColor(R.color.blue);
            System.out.println("1");
            Toast.makeText(context,"VERİTABANINDA VERİ YOK",Toast.LENGTH_LONG).show();
        } else if (getStartColor.equals("blue")) {
            changePomodoroColor(getResources().getColor(R.color.blue));
            System.out.println("2");
        } else if (getStartColor.equals("red")) {
            changePomodoroColor(getResources().getColor(R.color.red));
            System.out.println("3");
        } else if (getStartColor.equals("yellow")) {
            changePomodoroColor(getResources().getColor(R.color.yellow));
            System.out.println("4");
        } else if (getStartColor.equals("green")) {
            changePomodoroColor(getResources().getColor(R.color.green));
            System.out.println("5");
        } else if (getStartColor.equals("purple")) {
            changePomodoroColor(getResources().getColor(R.color.purple));
            System.out.println("6");
        }


       cancelButton = view.findViewById(R.id.cancelButton);

        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);

        linearLayout = view.findViewById(R.id.linearLayoutPomodoro);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String  getCurrentColor = sharedPreferences.getString("color","31");

                Drawable progressDrawable = progressBar.getProgressDrawable();
                LayerDrawable layers = (LayerDrawable) progressDrawable;
                Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
                GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;

                Spinner spinner = new Spinner(context);
                spinner.setGravity(Gravity.CENTER);
                String[] colors ={"mavi","kırmızı","yeşil","sarı","mor"};
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,colors);
                spinner.setAdapter(arrayAdapter);

                if(getCurrentColor.equals("")){
                    spinner.setSelection(0);
                } else if(getCurrentColor.equals("blue")){
                    spinner.setSelection(0);
                }else if(getCurrentColor.equals("red")){
                    spinner.setSelection(1);
                }else if(getCurrentColor.equals("green")){
                    spinner.setSelection(2);
                }else if(getCurrentColor.equals("yellow")){
                    spinner.setSelection(3);
                }else if(getCurrentColor.equals("purple")){
                    spinner.setSelection(4);
                }

                spinner.setOnItemSelectedListener(
                        new AdapterView.OnItemSelectedListener() {
                                                      @Override
                                                      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                          if (position==0){
                                                              int newColor = getResources().getColor(R.color.blue);
                                                              gradientProgressShape.setColor(newColor);
                                                              editor.putString("color","blue").apply();
                                                          } else if (position==1){
                                                              int newColor = getResources().getColor(R.color.red);
                                                              gradientProgressShape.setColor(newColor);
                                                              editor.putString("color","red").apply();
                                                          } else if (position==2){
                                                              int newColor = getResources().getColor(R.color.green);
                                                              gradientProgressShape.setColor(newColor);
                                                              editor.putString("color","green").apply();
                                                          } else if (position==3){
                                                              int newColor = getResources().getColor(R.color.yellow);
                                                              gradientProgressShape.setColor(newColor);
                                                              editor.putString("color","yellow").apply();
                                                          } else if (position==4){
                                                              int newColor = getResources().getColor(R.color.purple);
                                                              gradientProgressShape.setColor(newColor);
                                                              editor.putString("color","purple").apply();
                                                          }
                                                      }

                                                      @Override
                                                      public void onNothingSelected(AdapterView<?> parent) {

                                                      }
                                                  });



                AlertDialog.Builder diyalogColorSelect = new AlertDialog.Builder(context);

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


                diyalogColorSelect.create();
                diyalogColorSelect.show();



        }});



        //Zamanlayıcı Silme Diyalogu
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setTitle("Bu zamanlayıcıyı sileceğinize emin misiniz?");

        alertBuilder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(countDownTimer1!= null){
                    countDownTimer1.cancel();
                }
                if (countDownTimer2!=null){
                    countDownTimer2.cancel();
                }
                if (countDownTimer3!=null){
                    countDownTimer3.cancel();
                }


                    floatingActionButton.setEnabled(true);
                    cancelButton.setEnabled(false);



            }
        });
        alertBuilder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context,"İptal edilmedi",Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });



       cancelButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertBuilder.create();
               alertBuilder.show();

           }
       });


        //AlertDiyalog kurulacak

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Pomodoro Ekle");

        builder.setPositiveButton("Onayla", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(minWork.getText().toString().equals("") || minBreak.getText().toString().equals("") || minLongBreak.getText().toString().equals("") || secWork.getText().toString().equals("") || secBreak.getText().toString().equals("") || secLongBreak.getText().toString().equals("") || longBreakByStudy.getText().toString().equals("") ){
                    Toast.makeText(context,"Lütfen boşluklarını dolurup tekrar deneyiniz !",Toast.LENGTH_LONG).show();
                } else {


                    int devir = 1;
                    int sanLongBreak = Integer.parseInt(secLongBreak.getText().toString());
                    int dakWork = Integer.parseInt(minWork.getText().toString());
                    int dakBreak = Integer.parseInt(minBreak.getText().toString());
                    int dakLongBreak = Integer.parseInt(minLongBreak.getText().toString());
                    int sanWork = Integer.parseInt(secWork.getText().toString());
                    int sanBreak = Integer.parseInt(secBreak.getText().toString());
                    int longBreakbyLesson = Integer.parseInt(longBreakByStudy.getText().toString());

                    floatingActionButton.setEnabled(false);
                    cancelButton.setEnabled(true);


                    runWorkClock(dakWork, dakBreak, dakLongBreak, sanWork, sanBreak, sanLongBreak, devir, longBreakbyLesson);
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater layoutInflater = LayoutInflater.from(context);
                View pomodoroView = layoutInflater.inflate(R.layout.add_pomodoro, null);

                builder.setView(pomodoroView);

                minWork = pomodoroView.findViewById(R.id.minWork);
                secWork = pomodoroView.findViewById(R.id.secWork);
                minBreak = pomodoroView.findViewById(R.id.minBreak);
                secBreak = pomodoroView.findViewById(R.id.secBreak);
                minLongBreak = pomodoroView.findViewById(R.id.minLongBreak);
                secLongBreak = pomodoroView.findViewById(R.id.secLongBreak);
                longBreakByStudy = pomodoroView.findViewById(R.id.longBreakByStudy);

                setDataButton = pomodoroView.findViewById(R.id.setDataButton);
                saveDataButton = pomodoroView.findViewById(R.id.saveDataButton);

                setDataButton.setOnClickListener(new View.OnClickListener() { //Verileri ayarla ve işle
                    @Override
                    public void onClick(View v) {
                        ArrayList<Integer> veriDizim= tinyDB.getListInt("pomodoroDizisi");
                        if(veriDizim.size()!=0){
                            minWork.setText(String.valueOf(veriDizim.get(0)));
                            minBreak.setText(String.valueOf(veriDizim.get(1)));
                            minLongBreak.setText(String.valueOf(veriDizim.get(2)));
                            secWork.setText(String.valueOf(veriDizim.get(3)));
                            secBreak.setText(String.valueOf(veriDizim.get(4)));
                            secLongBreak.setText(String.valueOf(veriDizim.get(5)));
                            longBreakByStudy.setText(String.valueOf(veriDizim.get(6)));
                        } else {
                            Toast.makeText(context,"Ayarladığınız bir kayıt yok",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                saveDataButton.setOnClickListener(new View.OnClickListener() { //Verileri kaydet TinyDB ye
                    @Override
                    public void onClick(View v) {
                        if (minWork.getText().toString().equals("") || minBreak.getText().toString().equals("") || minLongBreak.getText().toString().equals("") || secWork.getText().toString().equals("") || secBreak.getText().toString().equals("") || secLongBreak.getText().toString().equals("") || longBreakByStudy.getText().toString().equals("")) {
                            Toast.makeText(context, "Kaydetmeniz için boşluklar dolu olmalı !", Toast.LENGTH_LONG).show();
                        } else {

                            int sanLongBreak = Integer.parseInt(secLongBreak.getText().toString());
                            int dakWork = Integer.parseInt(minWork.getText().toString());
                            int dakBreak = Integer.parseInt(minBreak.getText().toString());
                            int dakLongBreak = Integer.parseInt(minLongBreak.getText().toString());
                            int sanWork = Integer.parseInt(secWork.getText().toString());
                            int sanBreak = Integer.parseInt(secBreak.getText().toString());
                            int longBreakbyLesson = Integer.parseInt(longBreakByStudy.getText().toString());


                            veriDizisi= new ArrayList<>();
                            veriDizisi.add(dakWork);
                            veriDizisi.add(dakBreak);
                            veriDizisi.add(dakLongBreak);
                            veriDizisi.add(sanWork);
                            veriDizisi.add(sanBreak);
                            veriDizisi.add(sanLongBreak);
                            veriDizisi.add(longBreakbyLesson);

                            tinyDB.putListInt("pomodoroDizisi", veriDizisi);
                            Toast.makeText(context, "Veriniz başarılı bir şekilde kaydedldi", Toast.LENGTH_LONG).show();

                        }
                    }
                });

                builder.create();
                builder.show();
            }
        });

    }
    public void changePomodoroColor(int color) {
        Drawable progressDrawable = progressBar.getProgressDrawable();
        LayerDrawable layers = (LayerDrawable) progressDrawable;
        Drawable progressShape = layers.getDrawable(1); // İlerleme çubuğunu temsil eden Drawable'ı alır
        GradientDrawable gradientProgressShape = (GradientDrawable) progressShape;
        gradientProgressShape.setColor(color);
    }



    public void runWorkClock(int dakWork, int dakBreak,int dakLongBreak,int sanWork,int sanBreak,int sanLongBreak,int devir,int longBreakbyLesson ) {


        long workMiliseconds = 1000 * (dakWork * 60 + sanWork);
        progressBar.setMax((int) workMiliseconds / 1000);
        int lessonCount = ((int)  (devir/2))+1;
        pomodoroStatement.setText("Ders : "+ lessonCount);


        countDownTimer2 = new CountDownTimer(workMiliseconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                floatingActionButton.setEnabled(false);
                cancelButton.setEnabled(true);

                long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long secondOfTime = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;
                if(secondOfTime<10){
                    String second = "0"+ secondOfTime;
                    textPomodoro.setText(minute + ":" +second);
                    progressBar.setProgress((int) millisUntilFinished / 1000);
                    progressBar.setMax((int) workMiliseconds / 1000);
                } else {
                    textPomodoro.setText(minute + ":" +secondOfTime);
                    progressBar.setProgress((int) millisUntilFinished / 1000);
                    progressBar.setMax((int) workMiliseconds / 1000);
                }


            }

            @Override
            public void onFinish() {
                vibrator.vibrate(2000);
                countDownTimer2.cancel();
                int devrem = devir + 1;
                if (devrem % (longBreakbyLesson*2)==0) {
                    runLongBreakClock(dakWork, dakBreak, dakLongBreak, sanWork, sanBreak, sanLongBreak, devrem,longBreakbyLesson);
                } else {
                    runBreakClock(dakWork, dakBreak, dakLongBreak, sanWork, sanBreak, sanLongBreak, devrem,longBreakbyLesson);
                }
            }
        }.start();
    }



    public void runBreakClock(int dakWork, int dakBreak,int dakLongBreak,int sanWork,int sanBreak,int sanLongBreak,int devir,int longBreakbyLesson){

        long breakMiliseconds = 1000 * (dakBreak * 60 + sanBreak);
        progressBar.setMax((int) breakMiliseconds / 1000);
        int lessonCount = devir/2;
        pomodoroStatement.setText("Mola : "+ lessonCount);

        countDownTimer3 = new CountDownTimer(breakMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                floatingActionButton.setEnabled(false);
                cancelButton.setEnabled(true);
                long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long secondOfTime = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

                if(secondOfTime<10){
                    String second = "0"+ secondOfTime;
                    textPomodoro.setText(minute + ":" +second);
                    progressBar.setProgress((int) millisUntilFinished / 1000);
                } else {
                    textPomodoro.setText(minute + ":" +secondOfTime);
                    progressBar.setProgress((int) millisUntilFinished / 1000);
                };
            }

            @Override
            public void onFinish() {
                vibrator.vibrate(2000);
                countDownTimer3.cancel();
                int devrem = devir+1;
                runWorkClock(dakWork,dakBreak,dakLongBreak,sanWork,sanBreak,sanLongBreak,devrem,longBreakbyLesson);
            }
        }.start();
    }


    public void runLongBreakClock(int dakWork, int dakBreak,int dakLongBreak,int sanWork,int sanBreak,int sanLongBreak,int devir,int longBreakbyLesson) {

        long longBreakMiliseconds = 1000 * (dakLongBreak * 60 + sanLongBreak);
        progressBar.setMax((int) longBreakMiliseconds / 1000);
        pomodoroStatement.setText("Uzun Mola");

        countDownTimer1 = new CountDownTimer(longBreakMiliseconds, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                floatingActionButton.setEnabled(false);
                cancelButton.setEnabled(true);
                long minute = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished);
                long secondOfTime = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60;

                if(secondOfTime<10){
                    String second = "0"+ secondOfTime;
                    textPomodoro.setText(minute + ":" +second);
                    progressBar.setProgress((int) millisUntilFinished / 1000);
                } else {
                    textPomodoro.setText(minute + ":" +secondOfTime);
                    progressBar.setProgress((int) millisUntilFinished / 1000);
                }
            }

            @Override
            public void onFinish() {

                playRhtym();
                countDownTimer1.cancel();
                Toast.makeText(context,"POMODORO BİTMİŞTİR",Toast.LENGTH_LONG).show();
                floatingActionButton.setEnabled(true);
                cancelButton.setEnabled(false);
                textPomodoro.setText("");
                pomodoroStatement.setText("Pomodoro Bitti");
            }
        }.start();
    }
     public void playRhtym() {
         int[] rhythm = {500,500,500};

         for (int duration : rhythm) {
             toneGenerator.startTone(ToneGenerator.TONE_DTMF_0, duration);
             try {
                 Thread.sleep(duration);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
     }

}

