package com.MayaGembom.shiftchecklist.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

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

import com.MayaGembom.shiftchecklist.More.Constants;
import com.github.drjacky.imagepicker.ImagePicker;

import com.MayaGembom.shiftchecklist.Objects.MyFirebase;

import com.MayaGembom.shiftchecklist.Objects.User;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class Activity_Register extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";

    private CircleImageView profile_IMG_user;
    private TextInputLayout register_EDT_first_name,register_EDT_last_name;
    private TextView profile_LBL_logout;
    private MaterialButton register_BTN_complete;

    private ProgressDialog progressDialog;
    private User user;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        findViews();
        initViews();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            imageUri = data.getData();
            MyFirebase.getInstance().getFst().getReference(Constants.PROFILE_FOLDER).child(MyFirebase.getInstance().getUser().getUid()).putFile(imageUri);
            profile_IMG_user.setImageURI(imageUri);
           // header_IMG_profile.setImageURI(imageUri);
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
        register_BTN_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName_text = register_EDT_first_name.getEditText().getText().toString();
                String userLastName_text = register_EDT_last_name.getEditText().getText().toString();
                String userID = MyFirebase.getInstance().getUser().getUid();
                String userPhone = MyFirebase.getInstance().getUser().getPhoneNumber();

                if (TextUtils.isEmpty(userName_text) ||TextUtils.isEmpty(userLastName_text) || imageUri == null){
                    Toast.makeText(Activity_Register.this, "נא מלא/י תמונה ושם פרטי+משפחה", Toast.LENGTH_SHORT).show();
                }else
                    progressDialog.setMessage("מאחסן את המידע..");
                    progressDialog.show();
                    registerNow(userName_text, userLastName_text,userID, userPhone, imageUri);
            }
        });

        profile_LBL_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void takePicture() {
        ImagePicker.Companion.with(this)
                .galleryOnly()
                .crop()
                .cropOval()
                .compress(1024)
                .maxResultSize(1080,1080)
                .start();
    }

    private void registerNow(final String userFirstName,final String userLastName, String userId, String userPhone, Uri imageUri){

        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference("Users").child(userId);

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    user = new User(userId, userLastName, userFirstName,userPhone, imageUri.toString());
                    myRef.child(userPhone).setValue(user);

                    Intent myIntent = new Intent(Activity_Register.this,Activity_HomeAssignments.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(myIntent);
                    finish();
                }
            }
    });
    }


    private void findViews() {
        profile_IMG_user = findViewById(R.id.profile_IMG_user);
        register_EDT_first_name = findViewById(R.id.register_EDT_first_name);
        register_EDT_last_name = findViewById(R.id.register_EDT_last_name);
        register_BTN_complete = findViewById(R.id.register_BTN_register);
        profile_LBL_logout = findViewById(R.id.profile_LBL_logout);
    }

}
