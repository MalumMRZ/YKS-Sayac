package com.yusufmirza.theyksproject.follow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.yusufmirza.theyksproject.R;

public class FollowFragment extends Fragment {




    Context context;
    public  FollowFragment(Context context){
        this.context=context;
    }

    Button instagram,youtube,email,wordpress,linkedin;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.follow_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(context,"BURADAKİ İLETİŞİM BİLGİLERİNİ SORU-ÖNERİLERİNİZ İÇİN KULLANABİLİRSİNİZ",Toast.LENGTH_LONG).show();
        instagram = view.findViewById(R.id.instagram);
        youtube = view.findViewById(R.id.youtube);
        email = view.findViewById(R.id.email);
        wordpress = view.findViewById(R.id.wordpress);
        linkedin = view.findViewById(R.id.linkedin);


        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.instagram.com/mrzsoftware/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:" + "yusufmrz222@gmail.com"));
                startActivity(intent);


            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.youtube.com/@yusufmirza4695/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        linkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://tr.linkedin.com/in/yusuf-mirza-%C3%A7oban-219053254";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        wordpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://preparatorystudy.wordpress.com/2023/06/22/website-uygulama-soru-oneri/";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });



    }
}