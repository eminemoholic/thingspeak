package aditya.thingspeak;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.VerificationListener;


public class RegisterActivity extends AppCompatActivity {

    FloatingActionButton fab;
    CardView cvAdd;
    Button registerButton;
    EditText etMobile,etPassword,etRepeatPassword,etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        fab = (FloatingActionButton)findViewById(R.id.fab);
        cvAdd = (CardView)findViewById(R.id.cv_add);
        registerButton= (Button)findViewById(R.id.bt_register);
        etMobile= (EditText)findViewById(R.id.et_mobile);
        etRepeatPassword= (EditText)findViewById(R.id.et_repeatpassword);
        etPassword= (EditText)findViewById(R.id.et_password);
        etEmail= (EditText)findViewById(R.id.et_email);



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }



        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validateData()){
                    Explode explode = new Explode();
                    explode.setDuration(500);

                    getWindow().setExitTransition(explode);
                    getWindow().setEnterTransition(explode);
                    ActivityOptionsCompat oc2 = ActivityOptionsCompat.makeSceneTransitionAnimation(RegisterActivity.this);
                    Intent intent = new Intent(RegisterActivity.this,OTPVerifyActivity.class);
                    intent.putExtra("INTENT_PHONENUMBER",etMobile.getText().toString().trim());
                    intent.putExtra("INTENT_EMAIL",etEmail.getText().toString().trim());
                    intent.putExtra("INTENT_PASSWORD",etPassword.getText().toString().trim());
                    startActivity(intent, oc2.toBundle());

                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateRevealClose();
            }
        });
    }

    boolean validateData(){

        if((etEmail.getText().toString().isEmpty())||
                (etMobile.getText().toString().isEmpty()) ||
                (etPassword.getText().toString().isEmpty()) ||
                (etRepeatPassword.getText().toString().isEmpty())
                ){
            Snackbar.make(registerButton,"One or more fields empty!",Snackbar.LENGTH_SHORT).show();
            return false;
        }else{
            if(!etPassword.getText().toString().trim().equals(etRepeatPassword.getText().toString().trim())){
                Snackbar.make(registerButton,"Passwords don't match!",Snackbar.LENGTH_SHORT).show();

                return false;
            }
        }
        return true;
    }

    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });
    }

    public void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth()/2,0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }

    public void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd,cvAdd.getWidth()/2,0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                RegisterActivity.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();
    }
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

}
