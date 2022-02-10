package com.MayaGembom.shiftchecklist.Recycler;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.Activities.Activity_Main;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class AdapterAssignment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Assignment> assignments = new ArrayList<>();
    private AssignmentItemClickListener assignmentItemClickListener;
    private AssignmentItemDeleteListener assignmentItemDeleteListener;

    private String currentWorkerID;

    public AdapterAssignment(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public AdapterAssignment setAssignmentItemClickListener(AssignmentItemClickListener assignmentItemClickListener) {
        this.assignmentItemClickListener = assignmentItemClickListener;
        return this;
    }

    public AdapterAssignment setAssignmentItemDeleteListener(AssignmentItemDeleteListener assignmentItemDeleteListener) {
        this.assignmentItemDeleteListener = assignmentItemDeleteListener;
        return this;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder
    onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_assignment, viewGroup, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AssignmentViewHolder assignmentViewHolder = (AssignmentViewHolder) holder;
        Assignment assignment = getItem(position);
        assignmentViewHolder.assignment_LBL_title.setText(assignment.getTitle());
        assignmentViewHolder.assignment_TXT_number.setText(position+1 + ".");

        boolean isVisible = assignment.isVisibility();
        ((AssignmentViewHolder) holder).constraintLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    public Assignment getItem(int position) {
        return assignments.get(position);
    }

    public interface AssignmentItemClickListener {
        void assignmentItemClicked(Assignment assignment, int position,String notes);
    }

    public interface AssignmentItemDeleteListener {
        void assignmentDeleteItemClicked(int position);
    }

    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView assignment_TXT_number;
        public MaterialTextView assignment_LBL_title;
        public AppCompatImageView assignment_BTN_delete;
        public ConstraintLayout constraintLayout;
        public MaterialButtonToggleGroup assignment_TBTN_status;
        private TextInputLayout assignment_EDT_note;
        private Assignment assignment;


        public AssignmentViewHolder(final View itemView) {
            super(itemView);
            this.assignment_TXT_number = itemView.findViewById(R.id.assignment_TXT_number);
            this.assignment_LBL_title = itemView.findViewById(R.id.assignment_LBL_title);
            this.assignment_BTN_delete = itemView.findViewById(R.id.assignment_BTN_delete);
            this.constraintLayout = itemView.findViewById(R.id.assignment_LAY_expanded);
            this.assignment_TBTN_status = itemView.findViewById(R.id.assignment_TBTN_status);
            this.assignment_EDT_note = itemView.findViewById(R.id.assignment_EDT_note);

            currentWorkerID = Activity_Main.getCurrentWorkerID();
            if(currentWorkerID.equals(Constants.Employee_ID))
                assignment_BTN_delete.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String notes = assignment_EDT_note.getEditText().getText().toString();
                    assignmentItemClickListener.assignmentItemClicked(getItem(getAdapterPosition()), getAdapterPosition(),notes);
                    notifyItemChanged(getAdapterPosition());
                }
            });

            assignment_BTN_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    assignmentItemDeleteListener.assignmentDeleteItemClicked(getAdapterPosition());
                }
            });

            assignment_TBTN_status.setSingleSelection(true);
            assignment_TBTN_status.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (isChecked) {
                    assignment = getItem(getAdapterPosition());
                    FirebaseUser firebaseUser = MyFirebase.getInstance().getUser();
                    String userId = firebaseUser.getUid();
                    DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(userId).child(Constants.WORKER_DEPARTMENT_PATH);
                    myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            if(task.isSuccessful()){
                                String workerDepartment = String.valueOf(task.getResult().getValue());
                                DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_PATH).child(workerDepartment).child(assignment.getTitle());
                                myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                                        if(task.isSuccessful()) {
                                            if (checkedId == R.id.assignment_BTN_complete) {
                                                myRef.child("status").setValue("complete");
                                            }
                                            if (checkedId == R.id.assignment_BTN_incomplete) {
                                                myRef.child("status").setValue("incomplete");
                                            }

                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            });

        }
    }

}
