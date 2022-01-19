package com.MayaGembom.shiftchecklist.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.github.drjacky.imagepicker.ImagePicker;

import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;

import com.MayaGembom.shiftchecklist.Objects.User;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.database.DatabaseReference;

import com.mikhaellopez.circularimageview.CircularImageView;


public class Activity_Register extends AppCompatActivity {

    private CircularImageView profile_IMG_user;
    private TextInputLayout register_EDT_first_name,register_EDT_last_name;
    private TextView profile_LBL_logout;
    private MaterialButton register_BTN_register;

    private User user;
    private String myUri;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        findViews();
        initViews();

    }

    public int checkPermissions() {
        int permissions = 3;
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return 0;
        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return 2;
        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return 1;
        }
        return permissions;
    }
    
    private void takePicture() {
        int permissions = checkPermissions(); // 0 - all, 1 - just camera, 2 - just gallery
        switch (permissions) {
            case 3:
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.MY_CAMERA_REQUEST_CODE);
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.READ_EXTERNAL_STORAGE_CODE);
                break;
            case 2:
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Constants.MY_CAMERA_REQUEST_CODE);
                break;
            case 1:
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.MY_CAMERA_REQUEST_CODE);
                break;
            case 0:
                break;
        }
        permissions = checkPermissions(); // 0 - all, 1 - just camera, 2 - just gallery
        switch (permissions) {
            case 2:
                ImagePicker.Companion.with(this)
                        .galleryOnly()
                        .cropOval()
                        .compress(200)
                        .start();
                break;
            case 1:
                ImagePicker.Companion.with(this)
                        .cameraOnly()
                        .cropOval()
                        .compress(200)
                        .start();
                break;
            case 0:
                ImagePicker.Companion.with(this)
                        .cropOval()
                        .compress(200)
                        .start();
                break;
        }
    }


    private void initViews() {
        profile_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });

        // Adding Event Listener to Button Register
        register_BTN_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName_text = register_EDT_first_name.getEditText().getText().toString();
                String userLastName_text = register_EDT_last_name.getEditText().getText().toString();
                String userID = MyFirebase.getInstance().getUser().getUid();
                String userPhone = MyFirebase.getInstance().getUser().getPhoneNumber();

                if (TextUtils.isEmpty(userName_text) ||TextUtils.isEmpty(userLastName_text) ){
                    Toast.makeText(Activity_Register.this, "נא מלא/י שם פרטי+משפחה", Toast.LENGTH_SHORT).show();
                }else
                    registerNow(userName_text, userLastName_text,userID, userPhone);

            }


        });

        profile_LBL_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void registerNow(final String userFirstName,final String userLastName, String userId, String userPhone){
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference("Users").child(userId);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    user = new User(userId, userLastName, userFirstName,userPhone, "default");
                    myRef.child(userPhone).setValue(user);

                    Intent myIntent = new Intent(Activity_Register.this,Activity_HomeAssignments.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(myIntent);
                    finish();
                }
                else {
                    Toast.makeText(Activity_Register.this,"נא מלא/י את כלל הפרטים", Toast.LENGTH_SHORT).show();
                }

            }
    });
    }


    private void findViews() {
        profile_IMG_user = findViewById(R.id.profile_IMG_user);

        register_EDT_first_name = findViewById(R.id.register_EDT_first_name);
        register_EDT_last_name = findViewById(R.id.register_EDT_last_name);
        register_BTN_register = findViewById(R.id.register_BTN_register);

        profile_LBL_logout = findViewById(R.id.profile_LBL_logout);
    }


}
