package com.example.userprofile;


import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static android.text.InputType.TYPE_CLASS_NUMBER;
import static com.google.firebase.storage.FirebaseStorage.getInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ImageView avartarIv;
    TextView nameTv, emailTv, phoneTv;
    FloatingActionButton fab;

    ProgressBar progressBar;
    Button deleteAccount,mcomplaint,mpayment;
    //storage
    StorageReference storageReference;
    //pth where images of user saved
    String storagePath = "Users_Profile_Imgs/";

    ProgressDialog pd;

    //permissions
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;


    String cameraPermissions[];
    String storagePermissions[];

    Uri image_uri;

    //for checking profilepic
    String profilephoto;

    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = firebaseAuth.getInstance();

        //delete
        user = firebaseAuth.getCurrentUser();

        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference();//firebase storage ref
        //INt arrays of perm


        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //Views
        avartarIv = view.findViewById(R.id.avatarIv);
        nameTv = view.findViewById(R.id.nameTv);
        emailTv = view.findViewById(R.id.emailTv);
        phoneTv = view.findViewById(R.id.phoneTv);
        fab = view.findViewById(R.id.fab);
        deleteAccount = view.findViewById(R.id.btnDeleteAccount);
        mcomplaint=view.findViewById(R.id.complaint);
        mpayment=view.findViewById(R.id.pay);


        mpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pri =new Intent(getActivity(),PriMainActivity.class);
                startActivity(pri);
            }
        });


        mcomplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent niro=new Intent(getActivity(),FrontActivity.class);
                startActivity(niro);
            }
        });



         //DELETE


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                final AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());

                dialog.setTitle("Are You Sure?");

                dialog.setMessage("Deleting this account will remove the details of your Profile.Select delete to delete your Account.Hope to See You Again!");

                dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {


                                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {

                                            Toast.makeText(getActivity(), "Account Deleted", Toast.LENGTH_LONG).show();
                                            Intent delete=new Intent(getActivity(),LoginActivity.class);
                                            startActivity(delete);

                                        } else {
                                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                            }

                        });
                        dialog.setNegativeButton("Dimiss", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });

                        AlertDialog alertDialog= dialog.create();
                        alertDialog.show();
                    }
                });




























        //int pd
        pd = new ProgressDialog(getActivity());

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    //gtdata

                    String name = "" + ds.child("name").getValue();
                    String email = "" + ds.child("email").getValue();
                    String phone = "" + ds.child("phone").getValue();
                    String image = "" + ds.child("image").getValue();


                    //set
                    nameTv.setText(name);
                    emailTv.setText(email);
                    phoneTv.setText(phone);

                    try {
                        //if image is recived
                        Picasso.get().load(image).into(avartarIv);
                    } catch (Exception e) {
                        Picasso.get().load(R.drawable.ic_add).into(avartarIv);
                    }

                }


            }









            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //FAB
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileDialog();
            }
        });

        return view;


    }





    private boolean checkStoragePermission(){
      boolean result= ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==
              (PackageManager.PERMISSION_GRANTED);
      return result;
    }

    private void requestStoragePermission(){

        ActivityCompat.requestPermissions(getActivity(),storagePermissions,STORAGE_REQUEST_CODE);
    }


    private boolean checkCameraPermission(){

        boolean result= ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA)==
                (PackageManager.PERMISSION_GRANTED);

        boolean result1= ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==
                (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private void requestCameraPermission(){

        ActivityCompat.requestPermissions(getActivity(),cameraPermissions,CAMERA_REQUEST_CODE);
    }








    private void showEditProfileDialog() {

     String options[]={"Edit Profile Picture","Edit Name","Edit Phone"};

     //alert
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setTitle("Choose Action");
builder.setItems(options, new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
//handle
        if(i==0){
           //EDit profile click
            pd.setMessage("Updating Profile Picture");
            profilephoto="image";
           showImagePicDialog();

        }
        else if(i==1){
            pd.setMessage("Updating Name");

            //calling name and passing nam
            showNamePhoneUpdateDialog("name");
        }
        else if(i==2){
            pd.setMessage("Updating Phone number");
            showNamePhoneUpdateDialog("phone");
        }

    }
});

//create
        builder.create().show();
    }

    private void showNamePhoneUpdateDialog(final String key) {


        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Update"+key);  //update name or upldate phone

        //set layout
        LinearLayout linearLayout=new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);

        final EditText editText=new EditText(getActivity());
        editText.setInputType(TYPE_CLASS_NUMBER);
        editText.setHint("Enter,"+key);//hint

        linearLayout.addView(editText);


        builder.setView(linearLayout);

        //add bbytton to update

        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                        //input
                String value=editText.getText().toString().trim();

                if(editText.length()==10){



                   pd.show();
                   HashMap<String,Object>result=new HashMap<>();
                   result.put(key,value);

databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
    @Override
    public void onSuccess(Void aVoid) {
        //update dismmss
        pd.dismiss();
        Toast.makeText(getActivity(),"UPDATING...",Toast.LENGTH_SHORT).show();

    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        pd.dismiss();
        Toast.makeText(getActivity(),""+e.getMessage(),Toast.LENGTH_SHORT).show();


    }
});

                }else
                {
                    Toast.makeText(getActivity(),"Phone number must be 10 digits",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });


        //crate show
        builder.create().show();

    }

    private void showImagePicDialog() {

        String options[] = {"Camera", "Gallery "};

        //alert
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());

        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i1) {
//handle
                if(i1==0){
                    //camera

                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }
                    else{
                        pickFromCamera();
                    }

                }
                else if(i1==1){
                   //Gallery

                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }


            }
        });

//create
        builder.create().show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
         //USER ALLOW ANd DENY


      switch(requestCode){
          case CAMERA_REQUEST_CODE:{
             if(grantResults.length>0){
                 boolean cameraAccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                 boolean writeStorageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(cameraAccepted && writeStorageAccepted){
                        //permission enabled
                        pickFromCamera();

                    }else {
                        //denied

                        Toast.makeText(getActivity(),"PLEASE ENABLE PERMISSION FOR CAMERA AND STORAGE",Toast.LENGTH_SHORT).show();
                    }
             }
          }
          break;
          case STORAGE_REQUEST_CODE:{

//Picking from Gallery,first chk storage permission is allowed
              if(grantResults.length>0){
                  boolean writeStorageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                  if(writeStorageAccepted){
                      //permission enabled
                      pickFromGallery();

                  }else {
                      //denied

                      Toast.makeText(getActivity(),"PLEASE ENABLE PERMISSION FOR STORAGE",Toast.LENGTH_SHORT).show();
                  }
              }

          }
          break;
      }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode==RESULT_OK){

            if(requestCode==IMAGE_PICK_GALLERY_CODE){
                //IMAGES IS PICKED FROM AGLLERY,get uri of image
                image_uri=data.getData();

                uploadProfilePhoto(image_uri);

            }

            if(requestCode==IMAGE_PICK_CAMERA_CODE){
                //IMAGES IS PICKED FROM camera,get uri of image
                uploadProfilePhoto(image_uri);
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadProfilePhoto(Uri uri) {

pd.show();


        String filePathAndName=storagePath+""+profilephoto+"_"+user.getUid();

        StorageReference storageReference2nd=storageReference.child(filePathAndName);

        storageReference2nd.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//IMAGE IS UPLOADED GET THE USER

               Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
               while(!uriTask.isSuccessful());
               Uri downloadUri=uriTask.getResult();


               //CHeck if image is uploaded or not
                        if(uriTask.isSuccessful()){
                            //done uploading
                            HashMap<String,Object>results=new HashMap<>();



                            results.put(profilephoto,downloadUri.toString());

                             databaseReference.child(user.getUid()).updateChildren(results)
                                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                                         @Override
                                         public void onSuccess(Void aVoid) {

                                             //uri added sucessful to the database
                                    pd.dismiss();
                                             Toast.makeText(getActivity(),"Images updated..",Toast.LENGTH_SHORT).show();
                                         }
                                     }).addOnFailureListener(new OnFailureListener() {
                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     //error in adding
                                     pd.show();

                                     Toast.makeText(getActivity(),"Error in Changing image",Toast.LENGTH_SHORT).show();

                                 }
                             });

                        }
                        else{
                            //unscuesss
                            pd.dismiss();
                            Toast.makeText(getActivity(),"Some error occured",Toast.LENGTH_SHORT).show();
                        }


                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });





    }

    private void pickFromCamera() {

        ContentValues values=new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Temp PIC");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Temp PIC");

        image_uri =getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

  //INTENT TO START CAMERA


        Intent cameraintent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,image_uri);
        startActivityForResult(cameraintent,IMAGE_PICK_CAMERA_CODE);
    }


    private void pickFromGallery() {
        //pick from gallery

        Intent gallery=new Intent(Intent.ACTION_PICK);
        gallery.setType("image/*");
        startActivityForResult(gallery,IMAGE_PICK_GALLERY_CODE);



    }


}
