package com.MayaGembom.shiftchecklist.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.R;

public class Fragment_ShiftManagerAssignments extends Fragment {

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

    private void recyclerView() {
    }

    private void findViews() {
    }

}
