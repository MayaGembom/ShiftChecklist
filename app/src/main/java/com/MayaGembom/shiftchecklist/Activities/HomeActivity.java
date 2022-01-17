package com.MayaGembom.shiftchecklist.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.R;
import com.MayaGembom.shiftchecklist.Recycler.AdapterAssignment;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView main_LST_assignments;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        recyclerView();
        toolbarView();

    }

    private void toolbarView() {
        toolbar = findViewById(R.id.assignment_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.assignment_LAY_drawer);
        navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void recyclerView() {
        main_LST_assignments = findViewById(R.id.main_LST_assignments);
        ArrayList<Assignment> assignments = generateAssignments();
        AdapterAssignment adapterAssignment = new AdapterAssignment(this, assignments);

        // Vertically
        main_LST_assignments.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        main_LST_assignments.setHasFixedSize(true);
        main_LST_assignments.setItemAnimator(new DefaultItemAnimator());
        main_LST_assignments.setAdapter(adapterAssignment);

        adapterAssignment.setAssignmentItemClickListener(new AdapterAssignment.AssignmentItemClickListener(){
            @Override
            public void assignmentItemClicked(Assignment assignment, int position) {
                Toast.makeText(HomeActivity.this, assignment.getTitle(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private ArrayList<Assignment> generateAssignments() {
        ArrayList<Assignment> assignments = new ArrayList<>();

        assignments.add(new Assignment()
                .setTitle("בחירת תאריך משמרת")
        );

        assignments.add(new Assignment()
                .setTitle("הורדת הכיסאות בשני הברים")
        );
        assignments.add(new Assignment()
                .setTitle("הדלקת מכונת קפה + סאקה חם")
        );
        assignments.add(new Assignment()
                .setTitle("סידור עמדת מלצרים")
        );
        assignments.add(new Assignment()
                .setTitle("סידור עמדת פינויים")
        );
        assignments.add(new Assignment()
                .setTitle("סידור ציוד לוגיסטי")
        );
        assignments.add(new Assignment()
                .setTitle("סידור עמדת קוקטיילים")
        );
        assignments.add(new Assignment()
                .setTitle("ניקוי מסך מחשב")
        );
        assignments.add(new Assignment()
                .setTitle("השלמת סכו\"ם + קנקני סויה + ג'ינג'ר וווסאבי")
        );
        assignments.add(new Assignment()
                .setTitle("וידוא מלאים")
        );
        assignments.add(new Assignment()
                .setTitle("הדלקת אורות")
        );
        assignments.add(new Assignment()
                .setTitle("הבאת קרח לשני הברים")
        );
        assignments.add(new Assignment()
                .setTitle("הבאת כל מה ששייך לברים מהשטיפה")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );
        assignments.add(new Assignment()
                .setTitle("משימה א")
        );

        return assignments;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}
