package com.razu.rider.model.login;

public interface LoginModel {

    interface OnLoginListener {

        void onLoginError();

        void onLoginSuccess();

        void onLoginFailure(String message);
    }

    void onLoginPerform(String phone, String password, OnLoginListener listener);
}