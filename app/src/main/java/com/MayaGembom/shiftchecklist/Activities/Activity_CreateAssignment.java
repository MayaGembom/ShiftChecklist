package com.MayaGembom.shiftchecklist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.MayaGembom.shiftchecklist.Fragments.Fragment_EmployeeAssignments;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.Objects.User;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class Activity_CreateAssignment extends AppCompatActivity {

    private MaterialButton panel_BTN_create;
    private TextInputEditText form_EDT_field;
    private FrameLayout main_FRL_container;
    private String workerDepartment;
    private String assignmentTitle;
    private Assignment currentAssignment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);
        Intent intent = getIntent();
        workerDepartment =intent.getExtras().getString("department_name");

        findViews();
        addNewAssignment();
    }


    private void findViews() {
        panel_BTN_create = findViewById(R.id.panel_BTN_create);
        form_EDT_field = findViewById(R.id.form_EDT_name);
        main_FRL_container = findViewById(R.id.main_FRL_container);

    }


    public void addNewAssignment() {
        panel_BTN_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assignmentTitle = form_EDT_field.getText().toString();
                form_EDT_field.setText("");

                DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_PATH).child(workerDepartment).child(assignmentTitle);
                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if(task.isSuccessful()) {
                            currentAssignment = new Assignment();
                            myRef.setValue(currentAssignment);
                        }
                        finish();
                    }
                });

            }
        });

    }

}