package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FrontActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front);
    }

    public void rating(View view) {
        Intent intent = new Intent(this, CheckRating.class);
        startActivity(intent);


    }


    public void addComplaint(View view) {
        Intent intent = new Intent(this, CRUDActivity.class);
        startActivity(intent);


    }

}
