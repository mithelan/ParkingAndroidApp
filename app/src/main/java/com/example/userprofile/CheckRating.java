package com.example.userprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CheckRating extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.complaints.MESSAGE";


    RatingBar ratingBar;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_rating);

        ratingBar = (RatingBar) findViewById(R.id.ratingBarr);
        button =(Button) findViewById(R.id.button);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(CheckRating.this,"Stars:" +(int ) v , Toast.LENGTH_SHORT).show();
            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CheckRating.this, "Stars:" + (int)ratingBar.getRating(), Toast.LENGTH_SHORT ).show();

            }
        });

    }

    public void submitComment(View view) {
        Intent intent = new Intent(this, DisplayCommentActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);


    }
}
