package com.example.userprofile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText mEmailEt,mPasswordEt;
    Button mRegisterBtn,mHaveAccount;


//
    ProgressDialog progressDialog;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);





        mEmailEt=findViewById(R.id.emailEt);
        mPasswordEt=findViewById(R.id.passwordEt);
        mRegisterBtn=findViewById(R.id.register_btn);


        mHaveAccount=findViewById(R.id.have_accountTv);

        //
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        progressDialog =new ProgressDialog (this);
        progressDialog.setMessage("Registering user");


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=mEmailEt.getText().toString().trim();

                String password=mPasswordEt.getText().toString().trim();

                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mEmailEt.setError("Invalid Email Address");
                    mEmailEt.setFocusable(true);
                }
                else
                 if(password.length()<6){
                    mPasswordEt.setError("password not lenghty");
                    mPasswordEt.setFocusable(true);
                }
                else{
                    registerUser(email,password);
                }



            }
        });

        mHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
           finish();
            }
        });
    }

     private void registerUser(String email,String password){
        progressDialog.show();


         mAuth.createUserWithEmailAndPassword(email, password)
                 .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                     @Override
                     public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                          progressDialog.dismiss();
                             FirebaseUser user = mAuth.getCurrentUser();
                             //Get email and uid

                             String email=user.getEmail();
                             String uid=user.getUid();

                             HashMap<Object,String> hashmap=new HashMap<>();
                            hashmap.put("email",email);
                             hashmap.put("uid",uid);
                             hashmap.put("name","");
                             hashmap.put("phone","");
                             hashmap.put("image","");
                    //Firebase Database Instance

                             FirebaseDatabase database=FirebaseDatabase.getInstance();

                             //path to store the uuser info

                             DatabaseReference reference=database.getReference("Users");
                             //put data within hashmap in database

                             reference.child(uid).setValue(hashmap);



                             Toast.makeText(RegisterActivity.this, "Registered"+user.getEmail(), Toast.LENGTH_SHORT).show();

                             startActivity(new Intent(RegisterActivity.this, DashboardActivity.class));
                             finish();
                         } else {
                             // If sign in fails, display a message to the user.
                             progressDialog.dismiss();
                             Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                         }

                     }
                 }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {
                 progressDialog.dismiss();
                 Toast.makeText(RegisterActivity.this,"mmm"+e.getMessage(),Toast.LENGTH_SHORT).show();

             }
         });
     }


     public boolean onSupportNavigateUp(){
        onBackPressed();
        return super.onSupportNavigateUp();
     }
}
