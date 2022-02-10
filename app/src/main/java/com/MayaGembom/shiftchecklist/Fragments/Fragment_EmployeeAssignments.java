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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.Activities.Activity_CreateAssignment;
import com.MayaGembom.shiftchecklist.Activities.Activity_Main;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.Objects.Employee;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.R;
import com.MayaGembom.shiftchecklist.Recycler.AdapterAssignment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Fragment_EmployeeAssignments extends Fragment {

    private RecyclerView main_LST_assignments;
    private View view;
    private Context context;
    private FloatingActionButton add_FAB_assignments;
    private Activity currentActivity;
    private String currentWorkerID;
    private String workerDepartment;
    private Assignment assignment;
    private String assignmentTitle;
    private AdapterAssignment adapterAssignment;
    private ArrayList<Assignment> assignments = new ArrayList<>();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser firebaseUser = MyFirebase.getInstance().getUser();
        String userId = firebaseUser.getUid();
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(userId).child(Constants.WORKER_DEPARTMENT_PATH);
        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    workerDepartment = String.valueOf(task.getResult().getValue());
                    assignmentsChangeListener();
                    recyclerView();
                }
            }
        });
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_assignments, container, false);
        context = view.getContext();
        findViews();
        currentWorkerID = Activity_Main.getCurrentWorkerID();
        if(currentWorkerID.equals(Constants.ShiftManager_ID))
            add_FAB_assignments.setVisibility(View.VISIBLE);

        currentActivity = getActivity();


        return view;
    }

    private void assignmentsChangeListener() {
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_PATH).child(workerDepartment);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String previousChildName) {
                assignmentTitle = dataSnapshot.getKey();
                assignment = dataSnapshot.getValue(Assignment.class); //assignment user from DB
                assignments.add(assignment);
                adapterAssignment.notifyItemInserted(assignments.size() -1);
                //adapterAssignment.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }
@Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                assignmentTitle = dataSnapshot.getKey();
                assignment = dataSnapshot.getValue(Assignment.class); //assignment user from DB
                assignment.setTitle(assignmentTitle);
                assignments.remove(assignment);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        myRef.addChildEventListener(childEventListener);

    }


    @Override
    public void onStart() {
        super.onStart();
        add_FAB_assignments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(currentActivity, Activity_CreateAssignment.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("department_name",workerDepartment);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        main_LST_assignments = view.findViewById(R.id.main_LST_assignments);
        add_FAB_assignments = view.findViewById(R.id.add_FAB_assignments);
    }

    private void recyclerView() {
        adapterAssignment = new AdapterAssignment(assignments);
        // Vertically
        main_LST_assignments.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        main_LST_assignments.setHasFixedSize(true);
        main_LST_assignments.setItemAnimator(new DefaultItemAnimator());
        main_LST_assignments.setAdapter(adapterAssignment);

        adapterAssignment.setAssignmentItemClickListener(new AdapterAssignment.AssignmentItemClickListener(){
            @Override
            public void assignmentItemClicked(Assignment assignment, int position) {
                if (currentWorkerID.equals(Constants.ShiftManager_ID)) {
                    assignment.setVisibility(true);
                }
                assignment.setVisibility(!assignment.isVisibility());
            }
        });

        adapterAssignment.setAssignmentItemDeleteListener(new AdapterAssignment.AssignmentItemDeleteListener() {
            @Override
            public void assignmentDeleteItemClicked(int position) {
                DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_PATH).child(workerDepartment);
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        myRef.child(assignments.get(position).getTitle()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                assignments.remove(position);
                                adapterAssignment.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }
}
