package com.yusufmirza.theyksproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.yusufmirza.theyksproject.calendar.DailyCalendar;
import com.yusufmirza.theyksproject.follow.FollowFragment;
import com.yusufmirza.theyksproject.followsubject.FollowYourLessons;
import com.yusufmirza.theyksproject.pomodoro.Pomodoro;
import com.yusufmirza.theyksproject.settings.Settings;
import com.yusufmirza.theyksproject.timer.TimerFragment;

import premiumMode.PremiumActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    Pomodoro pomodoroFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         toolbar= findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar); //Action bar olarak toolbarımız ayarlandı.

        drawerLayout = findViewById(R.id.drawer_view);

        pomodoroFragment = new Pomodoro(MainActivity.this);

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(MainActivity.this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openNavigationView,R.string.closeNavigationView);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();

        if(savedInstanceState==null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place, new TimerFragment(MainActivity.this)).commit();
            navigationView.setCheckedItem(R.id.timer);
        }


    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }


    }

    public void reCreate(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.timer){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place,new TimerFragment(MainActivity.this)).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (item.getItemId()==R.id.Share){
            drawerLayout.closeDrawer(GravityCompat.START);
            String Uri= "https://play.google.com/store/apps/details?id=com.yusufmirza.theyksproject";
            Intent paylasIntent = new Intent(Intent.ACTION_SEND);
            paylasIntent.setType("text/plain");
            paylasIntent.putExtra(Intent.EXTRA_TEXT,Uri);
            startActivity(Intent.createChooser(paylasIntent, "Paylaş"));
        } else if(item.getItemId()==R.id.FollowUs){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place, new FollowFragment(MainActivity.this)).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        }  else if(item.getItemId()==R.id.day_calendar){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place, new DailyCalendar(MainActivity.this)).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(item.getItemId()==R.id.pomodoro){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place,pomodoroFragment).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(item.getItemId()==R.id.followLessonProgram){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place, new FollowYourLessons(MainActivity.this,this)).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if(item.getItemId()==R.id.settings){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmant_place, new Settings(MainActivity.this)).commit();
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (item.getItemId() == R.id.premiumMode){

            Toast.makeText(MainActivity.this,"Yakında ...",Toast.LENGTH_LONG).show();
          //  Intent intent = new Intent(MainActivity.this, PremiumActivity.class);
           // startActivity(intent);
        }
        return true;
    }

}