package com.MayaGembom.shiftchecklist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.Assignment;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.R;
import com.MayaGembom.shiftchecklist.Recycler.AdapterAssignment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class Activity_HomeAssignments extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private RecyclerView main_LST_assignments;
    private Toolbar main_TLB_toolbar;
    private DrawerLayout main_DRL_drawer;
    private NavigationView main_NAV_navigation;
    private FrameLayout main_FRL_container;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        findViews();
        getUser();
        recyclerView();
        toolbarView();

    }

    private void getUser() {
        FirebaseUser user = MyFirebase.getInstance().getUser();
        String uid = null;
        if (user != null)
            uid = user.getUid();
        if (uid != null) {
           //User admin = new User("Maya Gembom","",uid);
            MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(uid).setValue("");
        }
    }


    private void findViews() {
        main_LST_assignments = findViewById(R.id.main_LST_assignments);

        main_TLB_toolbar = findViewById(R.id.main_TLB_toolbar);
        main_DRL_drawer = findViewById(R.id.main_DRL_drawer);
        main_NAV_navigation = findViewById(R.id.main_NAV_navigation);
        main_FRL_container = findViewById(R.id.main_FRL_container);

    }

    private void toolbarView() {
        setSupportActionBar(main_TLB_toolbar);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                main_DRL_drawer,
                main_TLB_toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        main_DRL_drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        main_NAV_navigation.setNavigationItemSelectedListener(this);
    }

    private void recyclerView() {
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
                Toast.makeText(Activity_HomeAssignments.this, assignment.getDescription(), Toast.LENGTH_SHORT).show();

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
                .setDescription("וידוא מלאים")
        );
        assignments.add(new Assignment()
                .setDescription("הדלקת אורות")
        );
        assignments.add(new Assignment()
                .setDescription("הבאת קרח לשני הברים")
        );
        assignments.add(new Assignment()
                .setDescription("הבאת כל מה ששייך לברים מהשטיפה")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );
        assignments.add(new Assignment()
                .setDescription("משימה א")
        );

        return assignments;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        openFragment(item);
        main_DRL_drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_ITM_profile:
                Intent newIntent = new Intent(this, Activity_Register.class);
                startActivity(newIntent);
            case R.id.menu_ITM_assign:
                break;
            case R.id.menu_ITM_logout:
                signOut();
                break;
        }
    }

    private void signOut() {
        //MyFirebase.getInstance().getFdb().getReference(Constants.WORKER_PATH).child(currentWorker.getUid()).removeEventListener(workerChanged);
        FirebaseAuth auth = MyFirebase.getInstance().getAuth();
        if (auth != null) {
            FirebaseUser user = auth.getCurrentUser();
            auth.signOut();
        }
        Intent i = new Intent(Activity_HomeAssignments.this, Activity_Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}