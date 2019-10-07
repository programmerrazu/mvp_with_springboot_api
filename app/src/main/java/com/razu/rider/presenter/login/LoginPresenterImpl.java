package com.razu.rider.presenter.login;

import com.razu.rider.model.login.LoginModel;
import com.razu.rider.model.login.LoginModelImpl;
import com.razu.rider.view.login.LoginView;

public class LoginPresenterImpl implements LoginPresenter, LoginModel.OnLoginListener {

    LoginView loginView;
    LoginModel loginModel;

    public LoginPresenterImpl(LoginView loginView) {
        this.loginView = loginView;
        this.loginModel = new LoginModelImpl();
    }

    @Override
    public void onLogin(String phone, String password) {
        if (loginView != null) {
            loginView.showProgress();
            loginModel.onLoginPerform(phone, password, this);
        }
    }

    @Override
    public void onDestroy() {
        if (loginView != null) {
            loginView = null;
        }
    }

    @Override
    public void onLoginError() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.setLoginError();
        }
    }

    @Override
    public void onLoginSuccess() {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.onNavigateMain();
        }
    }

    @Override
    public void onLoginFailure(String message) {
        if (loginView != null) {
            loginView.hideProgress();
            loginView.showAlert(message);
        }
    }
}