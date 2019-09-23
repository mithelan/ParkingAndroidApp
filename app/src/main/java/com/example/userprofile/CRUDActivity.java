package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CRUDActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.userprofile";


    EditText txtName,txtMobile,txtEmail,txtDate,txtAdd,txtComplaint;
    Button btnSave,btnShow,btnUpdate,btnDelete;
    DatabaseReference n_dbRef;
    Complaint ctd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);


        txtName =findViewById(R.id.nName);
        txtMobile=findViewById(R.id.nMobile);
        txtEmail=findViewById(R.id.nEmail);
        txtDate=findViewById(R.id.nDate);
        txtAdd=findViewById(R.id.nAddress);
        txtComplaint=findViewById(R.id.nComplaint);

        btnSave=findViewById(R.id.nBtnSave);
        btnShow=findViewById(R.id.nBtnShow);
        btnUpdate=findViewById(R.id.nBtnUpdate);
        btnDelete=findViewById(R.id.nBtnDelete);

        ctd= new Complaint();
//email validation

    }



    public void choosePhoto(View view) {
        Intent intent = new Intent(CRUDActivity.this, nUploadImage.class);
        startActivity(intent);


    }





    public void n_clearConsoles(){
        txtName.setText("");
        txtMobile.setText("");
        txtEmail.setText("");
        txtDate.setText("");
        txtAdd.setText("");
        txtComplaint.setText("");
    }

    //Save
    public void n_onclickSave(View view) {
        n_dbRef = FirebaseDatabase.getInstance().getReference().child("Complaint");
        if(ctd.isValid(txtMobile.getText().toString())) {
            if(ctd.isValid1(txtDate.getText().toString())) {
            try {
                if (TextUtils.isEmpty(txtName.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Name ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtEmail.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter an Email", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtDate.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Date ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtAdd.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter an Address ", Toast.LENGTH_SHORT).show();
                else if (TextUtils.isEmpty(txtComplaint.getText().toString()))
                    Toast.makeText(getApplicationContext(), "Please Enter a Complaint ", Toast.LENGTH_SHORT).show();

                else {
                    ctd.setName(txtName.getText().toString().trim());
                    ctd.setMobile(Integer.parseInt(txtMobile.getText().toString().trim()));
                    ctd.setEmail(txtEmail.getText().toString().trim());
                    ctd.setDate(txtDate.getText().toString().trim());
                    ctd.setAddress(txtAdd.getText().toString().trim());
                    ctd.setComplaint(txtComplaint.getText().toString().trim());

                    //insert into the database!!!
                    // n_dbRef.push().setValue(ctd);
                    n_dbRef.child("Ctd1").setValue(ctd);

                    Toast.makeText(getApplicationContext(), "Complaints Saved Successfully", Toast.LENGTH_SHORT).show();
                    n_clearConsoles();

                }

            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Enter 10 Numbers", Toast.LENGTH_SHORT).show();
        }}
            else{
                Toast.makeText(getApplicationContext(), "Enter  date in MM/YY Format", Toast.LENGTH_SHORT).show();
            }
    }




    //Show
    public void n_onlickShow(View view){
        DatabaseReference n_readRef= FirebaseDatabase.getInstance().getReference().child("Complaint").child("Ctd1");
        n_readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()) {
                    txtName.setText(dataSnapshot.child("name").getValue().toString());
                    txtMobile.setText(dataSnapshot.child("mobile").getValue().toString());
                    txtEmail.setText(dataSnapshot.child("email").getValue().toString());
                    txtDate.setText(dataSnapshot.child("date").getValue().toString());
                    txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                    txtComplaint.setText(dataSnapshot.child("complaint").getValue().toString());

                }
                else
                    Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    //Update
    public void n_onlickUpdate(View view){
        final DatabaseReference n_updRef=FirebaseDatabase.getInstance().getReference().child("Complaint");
        n_updRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Ctd1")) {
                    try{
                        ctd.setName(txtName.getText().toString().trim());
                        ctd.setMobile(Integer.parseInt(txtMobile.getText().toString().trim()));
                        ctd.setEmail(txtEmail.getText().toString().trim());
                        ctd.setDate(txtDate.getText().toString().trim());
                        ctd.setAddress(txtAdd.getText().toString().trim());
                        ctd.setAddress(txtComplaint.getText().toString().trim());

                        n_dbRef=FirebaseDatabase.getInstance().getReference().child("Complaint").child("Ctd1");
                        n_dbRef.setValue(ctd);
                        n_clearConsoles();

                        Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();

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




    //Delete
    public void n_onlickDelete(View view){
        final DatabaseReference n_delRef= FirebaseDatabase.getInstance().getReference().child("Complaint");
        n_delRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Ctd1")){
                    n_dbRef=FirebaseDatabase.getInstance().getReference().child("Complaint").child("Ctd1");
                    n_delRef.removeValue();
                    n_clearConsoles();
                    Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                }
                else
                    Toast.makeText(getApplicationContext(),"No SOurce to Delete",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}






