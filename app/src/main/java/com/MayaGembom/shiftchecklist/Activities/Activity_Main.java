package com.MayaGembom.shiftchecklist.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
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
import com.MayaGembom.shiftchecklist.Fragments.Fragment_ShiftManagerAssignments;
import com.MayaGembom.shiftchecklist.More.Constants;
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
    private TextView header_LBL_role;
    private FrameLayout main_FRL_container;
    private static String currentWorkerID;
    private Employee employee;
    private ShiftManager shiftManager;
    private Owner owner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        currentWorkerID =intent.getExtras().getString("key");

        findViews();
        initListener();
        toolbarView();


    }

    private void findViews() {
        main_TLB_toolbar = findViewById(R.id.main_TLB_toolbar);
        main_DRL_drawer = findViewById(R.id.main_DRL_drawer);
        main_NAV_navigation = findViewById(R.id.main_NAV_navigation);
        main_FRL_container = findViewById(R.id.main_FRL_container);

        View header = main_NAV_navigation.getHeaderView(0);
        header_IMG_profile = header.findViewById(R.id.header_IMG_profile);
        header_LBL_name = header.findViewById(R.id.header_LBL_name);
        header_LBL_role= header.findViewById(R.id.header_LBL_role);
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
            case R.id.menu_ITM_employee_assign:
                getSupportFragmentManager().beginTransaction().replace(main_FRL_container.getId(), new Fragment_EmployeeAssignments()).commit();
                main_TLB_toolbar.setTitle("טופס משימות");
                break;
            case R.id.menu_ITM_manage_shiftmanager_assign:
                getSupportFragmentManager().beginTransaction().replace(main_FRL_container.getId(), new Fragment_ShiftManagerAssignments()).commit();
                main_TLB_toolbar.setTitle("טופס משימות אחמ\"ש");
                break;
            case R.id.menu_ITM_manage_employee_assign:
                getSupportFragmentManager().beginTransaction().replace(main_FRL_container.getId(), new Fragment_EmployeeAssignments()).commit();
                main_TLB_toolbar.setTitle("ניהול טופס משימות עובדים");
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


    private void initListener(){
        FirebaseUser firebaseUser = MyFirebase.getInstance().getUser();
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference("Users").child(firebaseUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                switch (currentWorkerID){
                    case Constants.Employee_ID:
                        employee = dataSnapshot.getValue(Employee.class); //load user from DB
                        header_LBL_role.setText("תפקיד: עובד");
                        header_LBL_name.setText(employee.getUsername() + " " + employee.getUserLastName());
                        if (!employee.getImageURL().equals("default")) {
                            Glide.with(Activity_Main.this).load(employee.getImageURL()).into(header_IMG_profile);
                        }
                        break;
                    case Constants.ShiftManager_ID:
                        shiftManager = dataSnapshot.getValue(ShiftManager.class); //load user from DB
                        header_LBL_role.setText("תפקיד: אחמ\"ש");
                        header_LBL_name.setText(shiftManager.getUsername() + " " + shiftManager.getUserLastName());
                        if (!shiftManager.getImageURL().equals("default")) {
                            Glide.with(Activity_Main.this).load(shiftManager.getImageURL()).into(header_IMG_profile);
                        }
                        break;
                    case Constants.Owner_ID:
                        owner = dataSnapshot.getValue(Owner.class); //load user from DB
                        header_LBL_role.setText("תפקיד: בעלים");
                        header_LBL_name.setText(owner.getUsername() + " " + owner.getUserLastName());
                        if (!owner.getImageURL().equals("default")) {
                            Glide.with(Activity_Main.this).load(owner.getImageURL()).into(header_IMG_profile);
                        }
                        break;
                }
                showItems();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showItems() {
        Menu menu = main_NAV_navigation.getMenu();
        switch (currentWorkerID) {
                    case Constants.Employee_ID:
                        menu.findItem(R.id.menu_ITM_profile).setVisible(true);
                        menu.findItem(R.id.menu_ITM_employee_assign).setVisible(true);
                        menu.findItem(R.id.menu_ITM_manage_employee_assign).setVisible(false);
                        menu.findItem(R.id.menu_ITM_manage_shiftmanager_assign).setVisible(false);
                        break;
                    case Constants.ShiftManager_ID:
                        menu.findItem(R.id.menu_ITM_employee_assign).setVisible(false);
                        menu.findItem(R.id.menu_ITM_manage_shiftmanager_assign).setVisible(true);
                        menu.findItem(R.id.menu_ITM_profile).setVisible(true);
                        menu.findItem(R.id.menu_ITM_manage_employee_assign).setVisible(true);
                        menu.findItem(R.id.menu_ITM_manage_shiftmanager_assign).setVisible(false);
                        break;
                    case Constants.Owner_ID:
                        menu.findItem(R.id.menu_ITM_employee_assign).setVisible(false);
                        menu.findItem(R.id.menu_ITM_shiftmanager_assign).setVisible(false);
                        menu.findItem(R.id.menu_ITM_profile).setVisible(true);
                        menu.findItem(R.id.menu_ITM_manage_employee_assign).setVisible(true);
                        menu.findItem(R.id.menu_ITM_manage_shiftmanager_assign).setVisible(true);
                       break;
        }
    }

    public static String getCurrentWorkerID() {
        return currentWorkerID;
    }

    public Activity_Main setCurrentWorkerID(String currentWorkerID) {
        this.currentWorkerID = currentWorkerID;
        return this;
    }
}


