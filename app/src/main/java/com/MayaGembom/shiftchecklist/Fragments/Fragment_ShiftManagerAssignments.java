package com.MayaGembom.shiftchecklist.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.Activities.Activity_ChooseDepartment;
import com.MayaGembom.shiftchecklist.Activities.Activity_CreateAssignment;
import com.MayaGembom.shiftchecklist.Activities.Activity_Main;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.More.SendEmailService;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Fragment_ShiftManagerAssignments extends Fragment {
    private RecyclerView main_LST_assignments;
    private View view;
    private Context context;
    private FloatingActionButton add_FAB_assignments;
    private FloatingActionButton send_FAB_assignments;
    private Activity currentActivity;
    private String department;
    private String currentWorkerID;
    private boolean ownerFlag;

    public Fragment_ShiftManagerAssignments() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = getActivity();
        ownerFlag = Activity_Main.isOwnerFlag();

        if (ownerFlag == false) {
            Intent intent = new Intent(currentActivity, Activity_ChooseDepartment.class);
            startActivity(intent);
            Activity_Main.setOwnerFlag(true);
        } else {
            Bundle bundle = getArguments();
            department = bundle.getString("department");
            Activity_Main.setOwnerDepartment(department);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        department = Activity_Main.getOwnerDepartment();

        add_FAB_assignments.setOnClickListener(view -> {
            Intent intent = new Intent(currentActivity, Activity_CreateAssignment.class);
            intent.putExtra("department_name", department);
            startActivity(intent);
        });
    }



    @Override
    public void onStart() {
        super.onStart();

        // Send Email
        //send_FAB_assignments.setOnClickListener(view -> SendEmailService.getInstance(currentActivity).emailExecutor.execute(() -> SendEmailService.getInstance(currentActivity).SendEmail(assignments)));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        context = view.getContext();
        findViews();

        currentWorkerID = Activity_Main.getCurrentUserId();
        if (currentWorkerID.equals(Constants.Owner_ID))
            add_FAB_assignments.setVisibility(View.VISIBLE);
        if (currentWorkerID.equals(Constants.ShiftManager_ID))
            send_FAB_assignments.setVisibility(View.VISIBLE);



        recyclerView();

        return view;
    }


    private void recyclerView() {


    }

    private void findViews() {
        main_LST_assignments = view.findViewById(R.id.main_LST_assignments);
        add_FAB_assignments = view.findViewById(R.id.add_FAB_assignments);
        send_FAB_assignments = view.findViewById(R.id.send_FAB_assignments);

    }

}
