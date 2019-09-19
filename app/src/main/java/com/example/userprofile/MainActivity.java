package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {


    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    //image
    ImageView avartarIv;
    TextView nameTv,emailTv,phoneTv;


    public MainActivity(){

    }
    protected View onCreate(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate(R.layout.activity_main,container,false);

      //firebase

        firebaseAuth=firebaseAuth.getInstance();
        user=firebaseAuth.getCurrentUser();
        firebaseDatabase=firebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Users");


        //Views
        avartarIv=view.findViewById(R.id.avatarIv);
        nameTv=view.findViewById(R.id.nameTv);
        emailTv=view.findViewById(R.id.emailTv);
        phoneTv=view.findViewById(R.id.phoneTv);


        Query query=databaseReference.orderByChild("email").equalTo(user.getEmail());
         query.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                 for(DataSnapshot ds:dataSnapshot.getChildren()){
//gtdata

                     String name=""+ds.child("name").getValue();
                     String email=""+ds.child("email").getValue();
                     String phone=""+ds.child("phone").getValue();
                     String image=""+ds.child("image").getValue();


                     //set
                     nameTv.setText(name);
                     emailTv.setText(email);
                     phoneTv.setText(phone);

                     try{
                         //if image is recived
                         Picasso.get().load(image).into(avartarIv);
                     }catch (Exception e){
                         Picasso.get().load(R.drawable.ic_add).into(avartarIv);
                     }

                 }


             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });

        return view;
    }
}
