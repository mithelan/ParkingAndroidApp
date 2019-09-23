package com.example.userprofile;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Details extends AppCompatActivity {

    EditText txtCardNo, txtExpiry, txtCvv;
    Button btnSubmit;
    AlertDialog.Builder alertBuilder;
    DatabaseReference dbRef;
    Payment pay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        txtCardNo = findViewById(R.id.EtCN);
        txtExpiry = findViewById(R.id.EtED);
        txtCvv = findViewById(R.id.EtCVV);

        btnSubmit = findViewById(R.id.BtnSubmit);

        pay = new Payment();

    }

    private void clearControls() {
        txtCardNo.setText("");
        txtExpiry.setText("");
        txtCvv.setText("");
    }


    public void click(View view) {

        if (pay.isValid(txtCardNo.getText().toString())) {
            if(pay.isValid1(txtExpiry.getText().toString())) {
                if (pay.isValid2(txtCvv.getText().toString())) {
                    alertBuilder = new AlertDialog.Builder(Details.this);
                    alertBuilder.setTitle("Confirm Submission");
                    alertBuilder.setIcon(R.mipmap.ic_launcher);

                    alertBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            alertBuilder.setMessage("Card number: " + pay.getCardNo() + "\n" +
                                    "Card expiry date: " + pay.getExpiry() + "\n" +
                                    "Card CVV: " + pay.getCvv());


                            dbRef = FirebaseDatabase.getInstance().getReference().child("Payment");

                            try {
                                if (TextUtils.isEmpty(txtCardNo.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Complete the Form", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txtExpiry.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Complete the Form", Toast.LENGTH_SHORT).show();
                                else if (TextUtils.isEmpty(txtCvv.getText().toString()))
                                    Toast.makeText(getApplicationContext(), "Complete the Form ", Toast.LENGTH_SHORT).show();
                                else {

                                    pay.setCardNo(Long.parseLong(txtCardNo.getText().toString().trim()));
                                    pay.setExpiry(txtExpiry.getText().toString().trim());
                                    pay.setCvv(txtCvv.getText().toString().trim());

                                    //dbRef.push().setValue(pay);
                                    dbRef.child("-LpP0WZJ7k6EP50JyG2B").setValue(pay);
                                    Toast.makeText(getApplicationContext(), "Successfully Submitted", Toast.LENGTH_SHORT).show();
                                    clearControls();

                                }
                            } catch (NumberFormatException e) {
                                Toast.makeText(getApplicationContext(), "Invalid Card No", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    AlertDialog alertDialog = alertBuilder.create();
                    alertDialog.show();


                } else {
                    Toast.makeText(getApplicationContext(), "Enter the valid Cvv", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Enter Expiry date in MM/YY Format", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Enter the entire Card no", Toast.LENGTH_SHORT).show();
        }

    }

}