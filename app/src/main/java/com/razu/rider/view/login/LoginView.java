package com.razu.rider.view.login;

public interface LoginView {

    void showProgress();

    void hideProgress();

    void setLoginError();

    void onNavigateMain();

    void showAlert(String message);
}