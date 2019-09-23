package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Retrieve extends AppCompatActivity {

    EditText txtCardNo, txtExpiry, txtCvv;
    Button btnSubmit,btnShow,btnUpdate,btnDelete;
    DatabaseReference dbRef;
    Payment pay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve);

        txtCardNo = findViewById(R.id.EtCN);
        txtExpiry = findViewById(R.id.EtED);
        txtCvv = findViewById(R.id.EtCVV);

        btnShow = findViewById(R.id.BtnShow);
        btnUpdate = findViewById(R.id.BtnUpdate);
        btnDelete = findViewById(R.id.BtnDelete);

        pay = new Payment();

    }

    private void clearControls() {
        txtCardNo.setText("");
        txtExpiry.setText("");
        txtCvv.setText("");
    }



    public void onClick1(View view){

        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Payment").child("-LpP0WZJ7k6EP50JyG2B");
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    txtCardNo.setText(dataSnapshot.child("cardNo").getValue().toString());
                    txtExpiry.setText(dataSnapshot.child("expiry").getValue().toString());
                    txtCvv.setText(dataSnapshot.child("cvv").getValue().toString());

                }
                else
                    Toast.makeText(getApplicationContext(),"No Source to Display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClick2(View view){

        DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("Payment");
        updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("-LpP0WZJ7k6EP50JyG2B")){
                    try{
                        pay.setCardNo(Long.parseLong(txtCardNo.getText().toString().trim()));
                        pay.setExpiry(txtExpiry.getText().toString().trim());
                        pay.setCvv(txtCvv.getText().toString().trim());

                        dbRef = FirebaseDatabase.getInstance().getReference().child("Payment").child("-LpP0WZJ7k6EP50JyG2B");
                        dbRef.setValue(pay);
                        clearControls();

                        Toast.makeText(getApplicationContext(),"Data updated Successfully",Toast.LENGTH_SHORT).show();
                    }
                    catch (NumberFormatException e){
                        Toast.makeText(getApplicationContext(),"Invalid Contact Number",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                    Toast.makeText(getApplicationContext(),"No Source to Update",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClick3(View view){
        DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Payment");
        delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("-LpP0WZJ7k6EP50JyG2B")){
                    dbRef = FirebaseDatabase.getInstance().getReference().child("Payment").child("-LpP0WZJ7k6EP50JyG2B");
                    dbRef.removeValue();
                    clearControls();
                    Toast.makeText(getApplicationContext(), "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "No Source Delete", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
