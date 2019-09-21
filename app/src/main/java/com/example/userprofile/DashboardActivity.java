package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        firebaseAuth=firebaseAuth.getInstance();

        BottomNavigationView navigationView=findViewById(R.id.navigation);
        navigationView.setOnNavigationItemSelectedListener(selectedListener);


        //HOME

        HomeFragment fragment1=new HomeFragment();
        FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
        ft1.replace(R.id.content,fragment1,"");
        ft1.commit();

    }
    private BottomNavigationView.OnNavigationItemSelectedListener selectedListener= new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//H=handle item

            switch(menuItem.getItemId()){
                case R.id.nav_home:  //home

                    HomeFragment fragment1=new HomeFragment();
                    FragmentTransaction ft1=getSupportFragmentManager().beginTransaction();
                    ft1.replace(R.id.content,fragment1,"");
                    ft1.commit();
                    return true;


                case R.id.nav_profile: //profile



                    ProfileFragment fragment3=new ProfileFragment();
                    FragmentTransaction ft3=getSupportFragmentManager().beginTransaction();
                    ft3.replace(R.id.content,fragment3,"");
                    ft3.commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void checkUserStatus(){
       //current user
        FirebaseUser user=firebaseAuth.getCurrentUser();

        if(user !=null){
          //  mprofileTv.setText(user.getEmail());
        }
        else{
            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
            finish();
        }


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void onStart() {
    checkUserStatus();
        super.onStart();
    }

//


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
        int id=item.getItemId();
        if(id==R.id.action_logout){
          firebaseAuth.signOut();

          checkUserStatus();
        }

        return super.onOptionsItemSelected(item);
    }
}
