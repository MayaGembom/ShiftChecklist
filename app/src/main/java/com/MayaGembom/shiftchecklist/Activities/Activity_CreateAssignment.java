package com.MayaGembom.shiftchecklist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

public class Activity_CreateAssignment extends AppCompatActivity {

    private MaterialButton panel_BTN_create;
    private TextInputEditText form_EDT_field;
    private String department;
    private String assignmentTitle;
    private String currentUserId;

    private Assignment currentAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
        Intent intent = getIntent();
        department = intent.getExtras().getString("department_name");
        Log.d("ptttttt", "onCreate: "+department );
        currentUserId = Activity_Main.getCurrentUserId();

        findViews();
        addNewAssignment();
    }


    private void findViews() {
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
        form_EDT_field = findViewById(R.id.form_EDT_name);
    }


    public void addNewAssignment() {
        panel_BTN_create.setOnClickListener(view -> {
            assignmentTitle = form_EDT_field.getText().toString();
            form_EDT_field.setText("");
            if (currentUserId.equals(Constants.ShiftManager_ID)) {
                DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_PATH).child(department).child(assignmentTitle);
                myRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentAssignment = new Assignment();
                        currentAssignment.setTitle(assignmentTitle);
                        myRef.setValue(currentAssignment);

                    }
                    finish();
                });
            }
            if (currentUserId.equals(Constants.Owner_ID)) {
                DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_SM_PATH).child(department).child(assignmentTitle);
                myRef.get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        currentAssignment = new Assignment();
                        currentAssignment.setTitle(assignmentTitle);
                        myRef.setValue(currentAssignment);

                    }
                    finish();
                });
            }

        });

    }

}
