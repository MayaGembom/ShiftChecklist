package com.MayaGembom.shiftchecklist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.R;
import com.MayaGembom.shiftchecklist.Recycler.AdapterAssignment;

import java.util.ArrayList;

public class Fragment_Assignments extends Fragment {

    private RecyclerView main_LST_assignments;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        context = view.getContext();
        findViews();

        recyclerView();
        return view;
    }




    private void findViews() {
        main_LST_assignments = view.findViewById(R.id.main_LST_assignments);

    }

    private void recyclerView() {
        ArrayList<Assignment> assignments = generateAssignments();
        AdapterAssignment adapterAssignment = new AdapterAssignment(assignments);

        // Vertically
        main_LST_assignments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        main_LST_assignments.setHasFixedSize(true);
        main_LST_assignments.setItemAnimator(new DefaultItemAnimator());
        main_LST_assignments.setAdapter(adapterAssignment);

        adapterAssignment.setAssignmentItemClickListener(new AdapterAssignment.AssignmentItemClickListener(){
            @Override
            public void assignmentItemClicked(Assignment assignment, int position) {
                assignment.setVisibility(!assignment.isVisibility());
            }
        });
    }

    private ArrayList<Assignment> generateAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();

        assignments.add(new Assignment()
                .setDescription("בחירת תאריך משמרת")
        );

        assignments.add(new Assignment()
                .setDescription("הורדת הכיסאות בשני הברים")
        );
        assignments.add(new Assignment()
                .setDescription("הדלקת מכונת קפה + סאקה חם")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת מלצרים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור ציוד לוגיסטי")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת קוקטיילים")
        );
        assignments.add(new Assignment()
                .setDescription("ניקוי מסך מחשב")
        );
        assignments.add(new Assignment()
                .setDescription("השלמת סכו\"ם + קנקני סויה + ג'ינג'ר וווסאבי")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setDescription("סידור עמדת פינויים")
        );
        return assignments;
    }


}
