package com.MayaGembom.shiftchecklist.Activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Employee;
import com.MayaGembom.shiftchecklist.Objects.Owner;
import com.MayaGembom.shiftchecklist.Objects.ShiftManager;
import com.github.drjacky.imagepicker.ImagePicker;

import com.MayaGembom.shiftchecklist.Objects.MyFirebase;

import com.MayaGembom.shiftchecklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.database.DataSnapshot;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class Activity_Register extends AppCompatActivity {
    private CircleImageView profile_IMG_user;
    private TextInputLayout register_EDT_first_name,register_EDT_last_name;
    private MaterialButton register_BTN_complete;
    private MaterialButtonToggleGroup toggle_BTN_user;
    private MaterialButtonToggleGroup toggle_BTN_department;

    private MaterialButton register_BTN_employee;
    private ProgressDialog progressDialog;
    private EditText token_EDT_password;
    private Uri imageUri;
    private String myDownloadUrl;
    private Employee employee;
    private ShiftManager shiftManager;
    private Owner owner;
    private String currentWorkerID;
    private String whichDepartment;


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

            StorageReference storeRef = MyFirebase.getInstance().getFst().getReference(Constants.PROFILE_FOLDER).child(MyFirebase.getInstance().getUser().getUid());
            storeRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    myDownloadUrl = uri.toString();
                }
            });
        }
    }

    private void initViews() {
        profile_IMG_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
        toggle_BTN_user.setSingleSelection(true);
        toggle_BTN_user.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.register_BTN_employee) {
                        currentWorkerID = "3";
                        token_EDT_password.setVisibility(View.INVISIBLE);
                    }
                    if (checkedId == R.id.register_BTN_shiftManager) {
                        token_EDT_password.setHint("קוד אימות אחמ\"ש");
                        token_EDT_password.setVisibility(View.VISIBLE);
                        currentWorkerID = "2";
                    }
                    if (checkedId == R.id.register_BTN_owner) {
                        token_EDT_password.setHint("קוד אימות בעלים");
                        token_EDT_password.setVisibility(View.VISIBLE);
                        currentWorkerID = "1";
                    }
                }
            }
        });
        toggle_BTN_department.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.register_BTN_restaurant) {
                        whichDepartment = Constants.RESTAURANT_DEPARTMENT;
                    }
                    if (checkedId == R.id.register_BTN_delivery) {
                        whichDepartment = Constants.DELIVERY_DEPARTMENT;

                    }
                    if (checkedId == R.id.register_BTN_bar) {
                        whichDepartment = Constants.BAR_DEPARTMENT;
                    }
                }
            }
        });

        // Adding Event Listener to Button Register
        register_BTN_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName_text = register_EDT_first_name.getEditText().getText().toString();
                String userLastName_text = register_EDT_last_name.getEditText().getText().toString();
                String userID = MyFirebase.getInstance().getUser().getUid();

                if (TextUtils.isEmpty(userName_text) ||TextUtils.isEmpty(userLastName_text)){
                    Toast.makeText(Activity_Register.this, "נא מלא/י תמונה ושם פרטי+משפחה", Toast.LENGTH_SHORT).show();
                }else
                    progressDialog.setMessage("מאחסן את המידע..");
                    progressDialog.show();
                    registerNow(userName_text, userLastName_text,userID);
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

    private void registerNow(final String userFirstName,final String userLastName, String userId){
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(userId);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    if(currentWorkerID.equals(Constants.Employee_ID))
                    {
                        employee = new Employee(myDownloadUrl, userFirstName, userLastName, currentWorkerID,whichDepartment);
                        myRef.setValue(employee);
                    }
                    else if(currentWorkerID.equals(Constants.ShiftManager_ID)){
                        shiftManager = new ShiftManager(myDownloadUrl, userFirstName, userLastName, currentWorkerID,whichDepartment);
                        shiftManager.setWorkerID(currentWorkerID);
                        myRef.setValue(shiftManager);
                    }
                    else if(currentWorkerID.equals(Constants.Owner_ID)){
                        owner = new Owner(myDownloadUrl, userFirstName, userLastName, currentWorkerID);
                        owner.setWorkerID(currentWorkerID);
                        myRef.setValue(owner);
                    }
                }
                Intent intent = new Intent(Activity_Register.this, Activity_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("key",currentWorkerID);
                startActivity(intent);
                finish();
            }
    });
    }

    private void findViews() {
        profile_IMG_user = findViewById(R.id.profile_IMG_user);
        register_EDT_first_name = findViewById(R.id.register_EDT_first_name);
        register_EDT_last_name = findViewById(R.id.register_EDT_last_name);
        register_BTN_complete = findViewById(R.id.register_BTN_register);
        toggle_BTN_user = findViewById(R.id.toggle_BTN_user);
        register_BTN_employee = findViewById(R.id.register_BTN_employee);
        token_EDT_password = findViewById(R.id.token_EDT_password);

        toggle_BTN_department = findViewById(R.id.toggle_BTN_department);
    }
}
