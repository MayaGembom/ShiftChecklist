package com.MayaGembom.shiftchecklist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.Activities.Activity_Main;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.R;
import com.MayaGembom.shiftchecklist.Recycler.AdapterAssignment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class Fragment_EmployeeAssignments extends Fragment {

    private RecyclerView main_LST_assignments;
    private View view;
    private Context context;
    private FloatingActionButton add_FAB_assignments;
    private String currentWorkerID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        context = view.getContext();
        findViews();
        recyclerView();

        currentWorkerID = Activity_Main.getCurrentWorkerID();

        if(currentWorkerID.equals(Constants.ShiftManager_ID))
            add_FAB_assignments.setVisibility(View.VISIBLE);

        return view;
    }




    private void findViews() {
        main_LST_assignments = view.findViewById(R.id.main_LST_assignments);
        add_FAB_assignments = view.findViewById(R.id.add_FAB_assignments);
    }

    private void recyclerView() {
        ArrayList<Assignment> assignments = generateAssignments();
        AdapterAssignment adapterAssignment = new AdapterAssignment(assignments);
        Assignment dateAssignment = adapterAssignment.getItem(0);
        dateAssignment.setDescription("בחירת תאריך משמרת");

        // Vertically
        main_LST_assignments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        main_LST_assignments.setHasFixedSize(true);
        main_LST_assignments.setItemAnimator(new DefaultItemAnimator());
        main_LST_assignments.setAdapter(adapterAssignment);


        adapterAssignment.setAssignmentItemClickListener(new AdapterAssignment.AssignmentItemClickListener(){
            @Override
            public void assignmentItemClicked(Assignment assignment, int position) {
                if(position == 0)
                {
                    assignment.setVisibility(true);
                }
                assignment.setVisibility(!assignment.isVisibility());
            }
        });
    }

    private ArrayList<Assignment> generateAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();

        assignments.add(new Assignment()
                .setDescription("בלהבלה")
        );

        assignments.add(new Assignment()
                .setDescription("הורדת הכיסאות בשני הברים")
        );
        assignments.add(new Assignment()
                .setDescription("הדלקת מכונת קפה + סאקה חם")
        );

        return assignments;
    }


}
