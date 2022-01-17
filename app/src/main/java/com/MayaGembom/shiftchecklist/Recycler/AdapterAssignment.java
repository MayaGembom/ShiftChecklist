package com.MayaGembom.shiftchecklist.Recycler;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class AdapterAssignment extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Activity activity;
    private ArrayList<Assignment> assignments = new ArrayList<>();
    private AssignmentItemClickListener assignmentItemClickListener;

    public AdapterAssignment(Activity activity, ArrayList<Assignment> assignments) {
        this.activity = activity;
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

    }

    @Override
    public int getItemCount() {
        return assignments.size();
    }

    private Assignment getItem(int position) {
        return assignments.get(position);
    }

    public interface AssignmentItemClickListener {
        void assignmentItemClicked(Assignment assignment, int position);
    }


    public class AssignmentViewHolder extends RecyclerView.ViewHolder {

        public MaterialTextView assignment_LBL_title;

        public AssignmentViewHolder(final View itemView) {
            super(itemView);
            this.assignment_LBL_title = itemView.findViewById(R.id.assignment_LBL_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    assignmentItemClickListener.assignmentItemClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }
            });

        }
    }

}
