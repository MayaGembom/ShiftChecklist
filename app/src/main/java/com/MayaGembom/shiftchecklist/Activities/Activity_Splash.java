package com.MayaGembom.shiftchecklist.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.appcompat.app.AppCompatActivity;
import com.MayaGembom.shiftchecklist.More.Constants;
import com.MayaGembom.shiftchecklist.Objects.MyFirebase;

import com.MayaGembom.shiftchecklist.R;
import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class Activity_Splash extends AppCompatActivity {

    public final int ANIMATION_DURATION = 4000;
    private LottieAnimationView splash_LAV_animation;
    private String workerID;

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
                        animationDone();
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

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
        if(user != null) {
            readWorkerIdFromDB();
        }
        else
            openLoginActivity();
    }

    private void openLoginActivity() {
        Intent intent = new Intent(this, Activity_Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
    }

    private void readWorkerIdFromDB() {
        FirebaseUser firebaseUser = MyFirebase.getInstance().getUser();
        String userId = firebaseUser.getUid();
        DatabaseReference myRef = MyFirebase.getInstance().getFdb().getReference(Constants.USERS_PATH).child(userId).child(Constants.WORKER_ID_PATH);
        myRef.get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                workerID = String.valueOf(task.getResult().getValue());
                Intent intent = new Intent(Activity_Splash.this, Activity_Main.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("key",workerID);
                startActivity(intent);
                finish();
            }
        });
    }


    private void findViews() {
        splash_LAV_animation = findViewById(R.id.splash_LAV_animation);

    }


}