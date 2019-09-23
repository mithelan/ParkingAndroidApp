package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DisplayCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment);



        Intent intent = getIntent();
        String message = intent.getStringExtra(CheckRating.EXTRA_MESSAGE);
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);

    }
}
