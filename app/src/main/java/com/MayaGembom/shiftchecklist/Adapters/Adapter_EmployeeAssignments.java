package com.MayaGembom.shiftchecklist.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.Activities.Activity_Main;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Objects;

public class Adapter_EmployeeAssignments extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Assignment> assignments;
    private AssignmentItemClickListener assignmentItemClickListener;
    private AssignmentItemDeleteListener assignmentItemDeleteListener;
    private String currentWorkerID;

    public Adapter_EmployeeAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public Adapter_EmployeeAssignments setAssignmentItemClickListener(AssignmentItemClickListener assignmentItemClickListener) {
        this.assignmentItemClickListener = assignmentItemClickListener;
        return this;
    }

    public Adapter_EmployeeAssignments setAssignmentItemDeleteListener(AssignmentItemDeleteListener assignmentItemDeleteListener) {
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

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        AssignmentViewHolder assignmentViewHolder = (AssignmentViewHolder) holder;
        Assignment assignment = getItem(position);
        assignmentViewHolder.assignment_LBL_title.setText(assignment.getTitle());
        assignmentViewHolder.assignment_TXT_number.setText(String.format("%d.", position + 1));

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

            currentWorkerID = Activity_Main.getCurrentUserId();
            if(currentWorkerID.equals(Constants.Employee_ID))
                assignment_BTN_delete.setVisibility(View.INVISIBLE);

            itemView.setOnClickListener(v -> {
                String notes = Objects.requireNonNull(assignment_EDT_note.getEditText()).getText().toString();
                assignmentItemClickListener.assignmentItemClicked(getItem(getAdapterPosition()), getAdapterPosition(),notes);
                notifyItemChanged(getAdapterPosition());
            });

            assignment_BTN_delete.setOnClickListener(view -> assignmentItemDeleteListener.assignmentDeleteItemClicked(getAdapterPosition()));

            assignment_TBTN_status.setSingleSelection(true);
            assignment_TBTN_status.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
                if (isChecked) {
                    assignment = getItem(getAdapterPosition());
                    FirebaseUser firebaseUser = MyFirebase.getInstance().getUser();
                    String userId = firebaseUser.getUid();
                    DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(userId).child(Constants.WORKER_DEPARTMENT_PATH);
                    myRef.get().addOnCompleteListener(task -> {
                        if(task.isSuccessful()){
                            String workerDepartment = String.valueOf(task.getResult().getValue());
                            DatabaseReference myRef1 = MyFirebase.getInstance().getFdb().getReference(Constants.ASSIGNMENTS_PATH).child(workerDepartment).child(assignment.getTitle());
                            myRef1.get().addOnCompleteListener(task1 -> {
                                if(task1.isSuccessful()) {
                                    if (checkedId == R.id.assignment_BTN_complete) {
                                        myRef1.child("status").setValue("complete");
                                    }
                                    if (checkedId == R.id.assignment_BTN_incomplete) {
                                        myRef1.child("status").setValue("incomplete");
                                    }

                                }
                            });

                        }
                    });
                }
            });

        }
    }

}
