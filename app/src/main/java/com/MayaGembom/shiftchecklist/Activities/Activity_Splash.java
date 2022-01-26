package com.MayaGembom.shiftchecklist.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.MayaGembom.shiftchecklist.Objects.Employee;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;
import com.MayaGembom.shiftchecklist.Objects.Owner;
import com.MayaGembom.shiftchecklist.Objects.ShiftManager;
import com.MayaGembom.shiftchecklist.R;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class Activity_Splash extends AppCompatActivity {

    public final int ANIMATION_DURATION = 4000;
    private LottieAnimationView splash_LAV_animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        findViews();
        startAnimation(splash_LAV_animation);

    }

    private void startAnimation(View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setScaleX(0.0f);
        view.setScaleY(0.0f);
        view.setAlpha(0.0f);
        view.animate()
                .alpha(1.0f)
                .scaleY(1.0f)
                .scaleX(1.0f)
                .translationY(0)
                .setDuration(ANIMATION_DURATION)
                .setInterpolator(new LinearInterpolator())
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationDone();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
    }


    private void animationDone() {
        FirebaseUser user = MyFirebase.getInstance().getUser();
        if(user != null){
            openHomeActivity();
        }else
            openLoginActivity();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, Activity_Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void openHomeActivity() {
        Intent intent = new Intent(this, Activity_Main.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void findViews() {

        splash_LAV_animation = findViewById(R.id.splash_LAV_animation);


    }
}