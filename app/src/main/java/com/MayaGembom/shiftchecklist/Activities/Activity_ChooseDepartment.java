package com.MayaGembom.shiftchecklist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.MayaGembom.shiftchecklist.Fragments.Fragment_ShiftManagerAssignments;
import com.MayaGembom.shiftchecklist.More.Constants;

import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class Activity_ChooseDepartment extends AppCompatActivity {

    private MaterialButton panel_BTN_save;
    private MaterialButtonToggleGroup toggle_BTN_department;
    private String department;
    FragmentManager fragmentManager = getSupportFragmentManager();
    final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    final Fragment_ShiftManagerAssignments myFragment = new Fragment_ShiftManagerAssignments();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_department);

        findViews();
        initViews();

    }

    private void initViews() {


        toggle_BTN_department.setSingleSelection(true);
        toggle_BTN_department.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.register_BTN_restaurant) {
                    department = Constants.RESTAURANT_DEPARTMENT;
                }
                if (checkedId == R.id.register_BTN_delivery) {
                    department = Constants.DELIVERY_DEPARTMENT;

                }
                if (checkedId == R.id.register_BTN_bar) {
                    department = Constants.BAR_DEPARTMENT;
                }
            }
        });

        panel_BTN_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("department", department);
                myFragment.setArguments(b);
                fragmentTransaction.add(R.id.frameLayout, myFragment).commit();
                finish();
            }
        });

}



    private void findViews() {
        panel_BTN_save = findViewById(R.id.panel_BTN_save);
        toggle_BTN_department = findViewById(R.id.toggle_BTN_department);
    }


}
