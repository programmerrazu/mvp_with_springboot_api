package com.razu.rider.activity;

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
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.razu.rider.R;
import com.razu.rider.presenter.RegistrationPresenter;
import com.razu.rider.presenter.RegistrationPresenterImpl;
import com.razu.rider.view.RegistrationView;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

import static android.view.Gravity.LEFT;

public class RegistrationActivity extends AppCompatActivity implements RegistrationView {

    @BindView(R.id.fl_registration_container)
    FrameLayout flRegistrationContainer;

    @BindView(R.id.vf_registration)
    ViewFlipper vfRegistration;

    @BindView(R.id.iv_flag)
    ImageView ivFlag;

    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;

    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.iv_name_back)
    ImageView ivNameBack;

    @BindView(R.id.iv_email_back)
    ImageView ivEmailBack;

    @BindView(R.id.iv_password_back)
    ImageView ivPasswordBack;

    @BindView(R.id.tv_moving)
    TextView tvMoving;

    @BindView(R.id.tv_name_moving)
    TextView tvNameMoving;

    @BindView(R.id.tv_email_moving)
    TextView tvEmailMoving;

    @BindView(R.id.tv_password_moving)
    TextView tvPasswordMoving;

    @BindView(R.id.ll_phone)
    LinearLayout llPhone;

    @BindView(R.id.ll_name)
    LinearLayout llName;

    @BindView(R.id.ll_email)
    LinearLayout llEmail;

    @BindView(R.id.ll_password)
    LinearLayout llPassword;

    @BindView(R.id.fab_progress_circle)
    FABProgressCircle fabProgressCircle;

    @BindView(R.id.fab_pc_name)
    FABProgressCircle fabPCName;

    @BindView(R.id.fab_pc_email)
    FABProgressCircle fabPCEmail;

    @BindView(R.id.fab_pc_password)
    FABProgressCircle fabPCPassword;

    @BindView(R.id.et_phone_no)
    EditText etPhoneNo;

    @BindView(R.id.et_first_name)
    EditText etFirstName;

    @BindView(R.id.et_last_name)
    EditText etLastName;

    @BindView(R.id.et_email)
    EditText etEmail;

    @BindView(R.id.et_reg_password)
    EditText etRegPassword;

    private Animation slideLeftIn, slideLeftOut, slideRightIn, slideRightOut;
    private RegistrationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        presenter = new RegistrationPresenterImpl(this);

        ButterKnife.bind(this);
        setupWindowAnimations();

        slideLeftIn = AnimationUtils.loadAnimation(this, R.anim.push_left_in);
        slideLeftOut = AnimationUtils.loadAnimation(this, R.anim.push_left_out);
        slideRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_right_in);
        slideRightOut = AnimationUtils.loadAnimation(this, R.anim.slide_right_out);
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
            Animation animation = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.slide_right);
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
            Animation animation = AnimationUtils.loadAnimation(RegistrationActivity.this, R.anim.slide_left);
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
            flRegistrationContainer.setAlpha(1f);
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
    void onClickFabProgressCircle() {
        presenter.onValidPhone(etPhoneNo.getText().toString().trim());
    }

    @OnClick(R.id.fab_pc_name)
    void onClickFabPCName() {
        presenter.onValidFirstName(etFirstName.getText().toString().trim());
    }

    @OnClick(R.id.fab_pc_email)
    void onClickFabPCEmail() {
        presenter.onValidEmail(etEmail.getText().toString().trim());
    }

    @OnClick(R.id.fab_pc_password)
    void onClickFabPCPassword() {
        presenter.onRegistration(getRegistrationBodyJson());
    }

    private JSONObject getRegistrationBodyJson() {
        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("phone", etPhoneNo.getText().toString().trim());
            bodyJson.put("password", etRegPassword.getText().toString().trim());
            bodyJson.put("firstName", etFirstName.getText().toString().trim());
            bodyJson.put("lastName", etLastName.getText().toString().trim());
            bodyJson.put("email", etEmail.getText().toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bodyJson;
    }

    @OnClick({R.id.iv_name_back, R.id.iv_email_back, R.id.iv_password_back})
    void goBack() {
        vfRegistration.setInAnimation(slideRightIn);
        vfRegistration.setOutAnimation(slideRightOut);
        vfRegistration.showPrevious();
    }

    @OnClick({R.id.iv_back})
    void goWelcome() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        int index = vfRegistration.getDisplayedChild();
        if (index > 0) {
            goBack();
        } else {
            goWelcome();
        }
    }


    @Override
    public void showProgress() {
        etRegPassword.setCursorVisible(false);
        flRegistrationContainer.setAlpha(0.4f);
        fabPCPassword.show();
    }

    @Override
    public void hideProgress() {
        etRegPassword.setCursorVisible(true);
        flRegistrationContainer.setAlpha(1);
        fabPCPassword.hide();
    }

    @Override
    public void showPhoneProgress() {
        etPhoneNo.setCursorVisible(false);
        flRegistrationContainer.setAlpha(0.4f);
        fabProgressCircle.show();
    }

    @Override
    public void hidePhoneProgress() {
        etPhoneNo.setCursorVisible(true);
        flRegistrationContainer.setAlpha(1);
        fabProgressCircle.hide();
    }

    @Override
    public void showFirstNameProgress() {
        etFirstName.setCursorVisible(false);
        etLastName.setCursorVisible(false);
        flRegistrationContainer.setAlpha(0.4f);
        fabPCName.show();
    }

    @Override
    public void hideFirstNameProgress() {
        etFirstName.setCursorVisible(true);
        etLastName.setCursorVisible(true);
        flRegistrationContainer.setAlpha(1);
        fabPCName.hide();
    }

    @Override
    public void showLastNameProgress() {
        etFirstName.setCursorVisible(false);
        etLastName.setCursorVisible(false);
        flRegistrationContainer.setAlpha(0.4f);
        fabPCName.show();
    }

    @Override
    public void hideLastNameProgress() {
        etFirstName.setCursorVisible(true);
        etLastName.setCursorVisible(true);
        flRegistrationContainer.setAlpha(1);
        fabPCName.hide();
    }

    @Override
    public void showEmailProgress() {
        etEmail.setCursorVisible(false);
        flRegistrationContainer.setAlpha(0.4f);
        fabPCEmail.show();
    }

    @Override
    public void hideEmailProgress() {
        etEmail.setCursorVisible(true);
        flRegistrationContainer.setAlpha(1);
        fabPCEmail.hide();
    }

    @Override
    public void setRegistrationError() {
        snackBarMsg("Registration Error");
    }

    @Override
    public void showAlert(String message) {
        snackBarMsg(message);
    }

    @Override
    public void onPhoneNext() {
        vfRegistration.setInAnimation(slideLeftIn);
        vfRegistration.setOutAnimation(slideLeftOut);
        vfRegistration.showNext();
    }

    @Override
    public void onFirstNameNext() {
        presenter.onValidLastName(etLastName.getText().toString().trim());
    }

    @Override
    public void onLastNameNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vfRegistration.setInAnimation(slideLeftIn);
                vfRegistration.setOutAnimation(slideLeftOut);
                vfRegistration.showNext();
            }
        }, 500);
    }

    @Override
    public void onEmailNext() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vfRegistration.setInAnimation(slideLeftIn);
                vfRegistration.setOutAnimation(slideLeftOut);
                vfRegistration.showNext();
            }
        }, 500);
    }

    @Override
    public void onNavigateMain() {
        Toasty.success(getApplicationContext(), getString(R.string.reg_success, Toast.LENGTH_LONG, true)).show();
        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(RegistrationActivity.this);
        startActivity(intent, options.toBundle());
        finish();
    }

    private void snackBarMsg(String message) {
        Snackbar snackbar = Snackbar.make(flRegistrationContainer, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}