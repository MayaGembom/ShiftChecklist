package com.MayaGembom.shiftchecklist.Recycler;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AdapterAssignment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Assignment> assignments = new ArrayList<>();
    private AssignmentItemClickListener assignmentItemClickListener;

    public AdapterAssignment(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public AdapterAssignment setAssignmentItemClickListener(AssignmentItemClickListener assignmentItemClickListener) {
        this.assignmentItemClickListener = assignmentItemClickListener;
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
        assignmentViewHolder.assignment_LBL_title.setText(assignment.getDescription());
        assignmentViewHolder.assignment_TXT_number.setText(position + ".");
        if(position ==  0){
            assignmentViewHolder.assignment_LBL_title.setGravity(Gravity.CENTER);
            assignmentViewHolder.assignment_TXT_number.setText(" ");
        }
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
        void assignmentItemClicked(Assignment assignment, int position);
    }


    public class AssignmentViewHolder extends RecyclerView.ViewHolder {
        public MaterialTextView assignment_TXT_number;
        public MaterialTextView assignment_LBL_title;
        public ConstraintLayout constraintLayout;

        public AssignmentViewHolder(final View itemView) {
            super(itemView);
            this.assignment_TXT_number = itemView.findViewById(R.id.assignment_TXT_number);
            this.assignment_LBL_title = itemView.findViewById(R.id.assignment_LBL_title);
            this.constraintLayout = itemView.findViewById(R.id.assignment_LAY_expanded);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assignmentItemClickListener.assignmentItemClicked(getItem(getAdapterPosition()), getAdapterPosition());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }

}
