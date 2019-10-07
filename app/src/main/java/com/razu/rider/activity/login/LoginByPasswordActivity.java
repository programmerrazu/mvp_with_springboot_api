package com.razu.rider.activity.login;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.jorgecastilloprz.FABProgressCircle;
import com.razu.rider.R;
import com.razu.rider.activity.MainActivity;
import com.razu.rider.presenter.login.LoginPresenter;
import com.razu.rider.presenter.login.LoginPresenterImpl;
import com.razu.rider.view.login.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.Gravity.RIGHT;

public class LoginByPasswordActivity extends AppCompatActivity implements LoginView {

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.fl_password_container)
    FrameLayout flPasswordContainer;

    @BindView(R.id.fab_progress_circle)
    FABProgressCircle fabProgressCircle;

    private String phoneNumber;
    private LoginPresenter presenter;

    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_by_password);

        presenter = new LoginPresenterImpl(this);
        ButterKnife.bind(this);

        Slide enterSlide = new Slide(RIGHT);
        enterSlide.setDuration(700);
        enterSlide.addTarget(R.id.fl_password_container);
        enterSlide.setInterpolator(new DecelerateInterpolator(2));
        getWindow().setEnterTransition(enterSlide);

        Slide returnSlide = new Slide(RIGHT);
        returnSlide.setDuration(700);
        returnSlide.addTarget(R.id.fl_password_container);
        returnSlide.setInterpolator(new DecelerateInterpolator());
        getWindow().setReturnTransition(returnSlide);

        if (getIntent() != null) {
            phoneNumber = getIntent().getStringExtra("phone_number");
        }
    }

    @OnClick(R.id.fab_progress_circle)
    void OnClickFabProgressCircle() {
        presenter.onLogin(phoneNumber, etPassword.getText().toString().trim());
    }

    @OnClick(R.id.iv_back)
    void startReturnLoginPhoneActivity() {
        onBackPressed();
    }

    @Override
    public void showProgress() {
        etPassword.setCursorVisible(false);
        flPasswordContainer.setAlpha(0.4f);
        fabProgressCircle.show();
    }

    @Override
    public void hideProgress() {
        etPassword.setCursorVisible(true);
        flPasswordContainer.setAlpha(1);
        fabProgressCircle.hide();
    }

    @Override
    public void setLoginError() {
        snackBarMsg("Login error");
    }

    @Override
    public void onNavigateMain() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPassword.getWindowToken(), 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoginByPasswordActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    @Override
    public void showAlert(String message) {
        snackBarMsg(message);
    }

    private void snackBarMsg(String message) {
        Snackbar snackbar = Snackbar.make(flPasswordContainer, message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(getResources().getColor(R.color.colorCompanyDark));
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }
}