package com.MayaGembom.shiftchecklist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.MayaGembom.shiftchecklist.Fragments.Fragment_EmployeeAssignments;
import com.MayaGembom.shiftchecklist.Objects.Employee;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.Objects.Owner;
import com.MayaGembom.shiftchecklist.Objects.ShiftManager;
import com.MayaGembom.shiftchecklist.R;
import com.bumptech.glide.Glide;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Activity_Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar main_TLB_toolbar;
    private DrawerLayout main_DRL_drawer;
    private NavigationView main_NAV_navigation;
    private ImageView header_IMG_profile;
    private TextView header_LBL_name;
    private FrameLayout main_FRL_container;


    private Employee employee;
    private ShiftManager shiftManager;
    private Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
        initListener();
        toolbarView();
        Log.d("ptttttt", "onCreate: on");


    }

    private void findViews() {
        main_TLB_toolbar = findViewById(R.id.main_TLB_toolbar);
        main_DRL_drawer = findViewById(R.id.main_DRL_drawer);
        main_NAV_navigation = findViewById(R.id.main_NAV_navigation);
        main_FRL_container = findViewById(R.id.main_FRL_container);

        View header = main_NAV_navigation.getHeaderView(0);
        header_IMG_profile = header.findViewById(R.id.header_IMG_profile);
        header_LBL_name = header.findViewById(R.id.header_LBL_name);
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        openFragment(item);
        main_DRL_drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openFragment(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_ITM_profile:
                Intent newIntent = new Intent(this, Activity_Register.class);
                startActivity(newIntent);
            case R.id.menu_ITM_assign:
                getSupportFragmentManager().beginTransaction().replace(main_FRL_container.getId(), new Fragment_EmployeeAssignments()).commit();
                main_TLB_toolbar.setTitle("טופס משימות");
                break;
            case R.id.menu_ITM_logout:
                signOut();
                break;
        }
    }

    private void signOut() {
        FirebaseAuth auth = MyFirebase.getInstance().getAuth();
        if (auth != null) {
            FirebaseUser user = auth.getCurrentUser();
            auth.signOut();
        }
        Intent i = new Intent(Activity_Main.this, Activity_Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }



    @Override
    public void onBackPressed() {
        if (main_DRL_drawer.isDrawerOpen(GravityCompat.START)) {
            main_DRL_drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }




    private void initListener(){
        FirebaseUser firebaseUser = MyFirebase.getInstance().getUser();
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference("Users").child(firebaseUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int currentWorkerID = Activity_Splash.getWorkerID();
                if(currentWorkerID == 3)
                {
                    employee = dataSnapshot.getValue(Employee.class); //load user from DB
                    header_LBL_name.setText(employee.getUsername() + " " + employee.getUserLastName());
                    if (!employee.getImageURL().equals("default")) {
                        Glide.with(Activity_Main.this).load(employee.getImageURL()).into(header_IMG_profile);
                    }
                }

                if(currentWorkerID == 2){
                    shiftManager = dataSnapshot.getValue(ShiftManager.class); //load user from DB
                    header_LBL_name.setText(shiftManager.getUsername() + " " + shiftManager.getUserLastName());
                    if (!shiftManager.getImageURL().equals("default")) {
                        Glide.with(Activity_Main.this).load(shiftManager.getImageURL()).into(header_IMG_profile);
                    }
                }
                if(currentWorkerID == 1){
                    owner = dataSnapshot.getValue(Owner.class); //load user from DB
                    header_LBL_name.setText(owner.getUsername() + " " + owner.getUserLastName());
                    if (!owner.getImageURL().equals("default")) {
                        Glide.with(Activity_Main.this).load(owner.getImageURL()).into(header_IMG_profile);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

    //private void showItems() {
   //    Menu menu = main_NAV_navigation.getMenu();
   //    if (currentWorker != null) {
   //        hideItems();
   //        if (currentWorker.getIsAccepted()) {
   //            switch (currentWorker.getType()) {
   //                case Constants.MANAGER_ID:
   //                    menu.findItem(R.id.menu_ITM_stats).setVisible(true);
   //                    menu.findItem(R.id.menu_ITM_assign).setVisible(true);
   //                    menu.findItem(R.id.menu_ITM_profile).setVisible(true);
   //                    break;
   //                case Constants.WORKER_ID:
   //                    menu.findItem(R.id.menu_ITM_assign).setVisible(true);
   //                    menu.findItem(R.id.menu_ITM_profile).setVisible(true);
   //                    break;
   //                case Constants.HR_ID:
   //                    menu.findItem(R.id.menu_ITM_assign).setVisible(true);
   //                    menu.findItem(R.id.menu_ITM_requests).setVisible(true);
   //                    menu.findItem(R.id.menu_ITM_profile).setVisible(true);
   //                    break;
   //            }
   //        } else {
   //            getSupportFragmentManager().beginTransaction().replace(main_FRL_container.getId(), new Fragment_Unauthorized()).commitAllowingStateLoss();
   //        }
   //    }
   //}
