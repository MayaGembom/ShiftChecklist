package com.MayaGembom.shiftchecklist.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Employee;
import com.MayaGembom.shiftchecklist.Objects.Owner;
import com.MayaGembom.shiftchecklist.Objects.ShiftManager;
import com.github.drjacky.imagepicker.ImagePicker;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.progressindicator.CircularProgressIndicator;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class Activity_Register extends AppCompatActivity {
    private CircleImageView header_IMG_profile;
    private CircularProgressIndicator header_BAR_progress;

    private TextInputLayout register_EDT_first_name,register_EDT_last_name;
    private MaterialButton register_BTN_complete;
    private MaterialButtonToggleGroup toggle_BTN_user;
    private MaterialButtonToggleGroup toggle_BTN_department;

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
            imageUri = data != null ? data.getData() : null;
            MyFirebase.getInstance().getFst().getReference(Constants.PROFILE_FOLDER).child(MyFirebase.getInstance().getUser().getUid()).putFile(imageUri);
            header_IMG_profile.setImageURI(imageUri);
            header_BAR_progress.setVisibility(View.VISIBLE);
            StorageReference storeRef = MyFirebase.getInstance().getFst().getReference(Constants.PROFILE_FOLDER).child(MyFirebase.getInstance().getUser().getUid());
            storeRef.getDownloadUrl().addOnSuccessListener(uri -> {
                myDownloadUrl = uri.toString();
                header_BAR_progress.setVisibility(View.INVISIBLE);
            });
        }
    }

    private void initViews() {
        header_IMG_profile.setOnClickListener(view -> takePicture());
        toggle_BTN_user.setSingleSelection(true);
        toggle_BTN_user.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.register_BTN_employee) {
                    currentWorkerID = "3";
                    token_EDT_password.setVisibility(View.INVISIBLE);
                    toggle_BTN_department.setVisibility(View.VISIBLE);
                }
                if (checkedId == R.id.register_BTN_shiftManager) {
                    token_EDT_password.setHint("קוד אימות אחמ\"ש");
                    token_EDT_password.setVisibility(View.VISIBLE);
                    currentWorkerID = "2";
                    toggle_BTN_department.setVisibility(View.VISIBLE);
                }
                if (checkedId == R.id.register_BTN_owner) {
                    token_EDT_password.setHint("קוד אימות בעלים");
                    token_EDT_password.setVisibility(View.VISIBLE);
                    currentWorkerID = "1";
                    toggle_BTN_department.setVisibility(View.INVISIBLE);
                }
            }
        });
        toggle_BTN_department.setSingleSelection(true);
        toggle_BTN_department.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
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
        });

        // Adding Event Listener to Button Register
        register_BTN_complete.setOnClickListener(v -> {
            String userName_text = Objects.requireNonNull(register_EDT_first_name.getEditText()).getText().toString();
            String userLastName_text = Objects.requireNonNull(register_EDT_last_name.getEditText()).getText().toString();
            String userID = MyFirebase.getInstance().getUser().getUid();
            String password = token_EDT_password.getText().toString();

            if (TextUtils.isEmpty(userName_text) || TextUtils.isEmpty(userLastName_text)|| TextUtils.isEmpty(password))
                Toast.makeText(Activity_Register.this, "נא מלא/י את כלל השדות", Toast.LENGTH_SHORT).show();
            else if (currentWorkerID.equals(Constants.Owner_ID)){
                if(!password.equals("31596"))
                    Toast.makeText(Activity_Register.this, "סיסמה שגויה", Toast.LENGTH_SHORT).show();
                else
                registerNow(userName_text, userLastName_text, userID);
            }else if (currentWorkerID.equals(Constants.ShiftManager_ID)){
                if(!password.equals("81866"))
                    Toast.makeText(Activity_Register.this, "סיסמה שגויה", Toast.LENGTH_SHORT).show();
                else
                registerNow(userName_text, userLastName_text, userID);
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
        progressDialog.setMessage("מאחסן את המידע..");
        progressDialog.show();
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(userId);
        myRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                switch (currentWorkerID) {
                    case Constants.Employee_ID:
                        employee = new Employee(myDownloadUrl, userFirstName, userLastName, currentWorkerID, whichDepartment);
                        myRef.setValue(employee);
                        break;
                    case Constants.ShiftManager_ID:
                        shiftManager = new ShiftManager(myDownloadUrl, userFirstName, userLastName, currentWorkerID, whichDepartment);
                        shiftManager.setWorkerID(currentWorkerID);
                        myRef.setValue(shiftManager);
                        break;
                    case Constants.Owner_ID:
                        owner = new Owner(myDownloadUrl, userFirstName, userLastName, currentWorkerID);
                        owner.setWorkerID(currentWorkerID);
                        myRef.setValue(owner);
                        break;
                }
            }
            Intent intent = new Intent(Activity_Register.this, Activity_Main.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("key",currentWorkerID);
            startActivity(intent);
            finish();
        });
    }

    private void findViews() {
        header_IMG_profile = findViewById(R.id.header_IMG_profile);
        register_EDT_first_name = findViewById(R.id.register_EDT_first_name);
        register_EDT_last_name = findViewById(R.id.register_EDT_last_name);
        register_BTN_complete = findViewById(R.id.register_BTN_register);
        toggle_BTN_user = findViewById(R.id.toggle_BTN_user);
        token_EDT_password = findViewById(R.id.token_EDT_password);
        toggle_BTN_department = findViewById(R.id.toggle_BTN_department);
        header_BAR_progress = findViewById(R.id.header_BAR_progress);
    }
}
