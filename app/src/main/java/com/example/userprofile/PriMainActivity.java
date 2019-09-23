package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class PriMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pri_main);
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.add:
                startActivity(new Intent(this, Card.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



    public void sendMessage1(View view) {
        Intent intent = new Intent(this, Cash.class);
        startActivity(intent);
    }

    public void sendMessage2(View view) {
        Intent intent = new Intent(this, Retrieve.class);
        startActivity(intent);
    }
}
