package com.razu.rider.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Slide;
import android.transition.Transition;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.razu.rider.R;
import com.razu.rider.presenter.login.PhonePresenter;
import com.razu.rider.presenter.login.PhonePresenterImpl;
import com.razu.rider.view.login.PhoneView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.LEFT;

public class LoginByPhoneActivity extends AppCompatActivity implements PhoneView {

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_flag)
    ImageView ivFlag;

    @BindView(R.id.et_phone_no)
    EditText etPhoneNo;

    @BindView(R.id.tv_moving)
    TextView tvMoving;

    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;

    @BindView(R.id.fl_phone_container)
    FrameLayout flPhoneContainer;

    @BindView(R.id.ll_phone)
    LinearLayout llPhone;

    @BindView(R.id.fab_progress_circle)
    FABProgressCircle fabProgressCircle;

    private PhonePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_phone);

        presenter = new PhonePresenterImpl(this);

        ButterKnife.bind(this);
        setupWindowAnimations();
    }

    @SuppressLint("NewApi")
    private void setupWindowAnimations() {
        ChangeBounds enterTransition = new ChangeBounds();
        enterTransition.setDuration(1000);
        enterTransition.setInterpolator(new DecelerateInterpolator(4));
        enterTransition.addListener(enterTransitionListener);
        getWindow().setSharedElementEnterTransition(enterTransition);

        ChangeBounds returnTransition = new ChangeBounds();
        returnTransition.setDuration(1000);
        returnTransition.addListener(returnTransitionListener);
        getWindow().setSharedElementReturnTransition(returnTransition);

        Slide exitSlide = new Slide(LEFT);
        exitSlide.setDuration(700);
        exitSlide.addListener(exitTransitionListener);
        exitSlide.addTarget(R.id.ll_phone);
        exitSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setExitTransition(exitSlide);

        Slide reenterSlide = new Slide(LEFT);
        reenterSlide.setDuration(700);
        reenterSlide.addListener(reenterTransitionListener);
        reenterSlide.setInterpolator(new DecelerateInterpolator(2));
        reenterSlide.addTarget(R.id.ll_phone);
        getWindow().setReenterTransition(reenterSlide);
    }

    Transition.TransitionListener enterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            ivBack.setImageAlpha(0);
        }

        @Override
        public void onTransitionEnd(Transition transition) {
            ivBack.setImageAlpha(255);
            Animation animation = AnimationUtils.loadAnimation(LoginByPhoneActivity.this, R.anim.slide_right);
            ivBack.startAnimation(animation);
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
    };

    Transition.TransitionListener returnTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPhoneNo.getWindowToken(), 0);
            tvMoving.setText(null);
            tvMoving.setHint(getString(R.string.enter_no));
            ivFlag.setImageAlpha(0);
            tvPhoneCode.setAlpha(0);
            etPhoneNo.setVisibility(View.GONE);
            etPhoneNo.setCursorVisible(false);
            etPhoneNo.setBackground(null);
            Animation animation = AnimationUtils.loadAnimation(LoginByPhoneActivity.this, R.anim.slide_left);
            ivBack.startAnimation(animation);
        }

        @Override
        public void onTransitionEnd(Transition transition) {

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
    };

    Transition.TransitionListener exitTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {
            flPhoneContainer.setAlpha(1f);
            fabProgressCircle.hide();
            llPhone.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }

        @Override
        public void onTransitionEnd(Transition transition) {

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
    };

    Transition.TransitionListener reenterTransitionListener = new Transition.TransitionListener() {
        @Override
        public void onTransitionStart(Transition transition) {

        }

        @Override
        public void onTransitionEnd(Transition transition) {
            llPhone.setBackgroundColor(Color.parseColor("#FFFFFF"));
            etPhoneNo.setCursorVisible(true);
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
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
    };

    @OnClick(R.id.fab_progress_circle)
    void OnClickFabProgressCircle() {
        presenter.validatePhone(etPhoneNo.getText().toString().trim());
    }

    @OnClick(R.id.iv_back)
    void startReturnWelcomeActivity() {
        super.onBackPressed();
    }

    @Override
    public void showProgress() {
        etPhoneNo.setCursorVisible(false);
        flPhoneContainer.setAlpha(0.4f);
        fabProgressCircle.show();
    }

    @Override
    public void hideProgress() {
        etPhoneNo.setCursorVisible(true);
        flPhoneContainer.setAlpha(1);
        fabProgressCircle.hide();
    }

    @Override
    public void setPhoneError() {
        snackBarMsg("Phone error");
    }

    @Override
    public void navigatePassword() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginByPhoneActivity.this, LoginByPasswordActivity.class);
                intent.putExtra("phone_number", etPhoneNo.getText().toString().trim());
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(LoginByPhoneActivity.this);
                startActivity(intent, options.toBundle());
            }
        }, 1000);
    }

    @Override
    public void showAlert(String message) {
        snackBarMsg(message);
    }

    private void snackBarMsg(String message) {
        Snackbar snackbar = Snackbar.make(flPhoneContainer, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}