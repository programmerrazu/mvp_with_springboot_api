package com.razu.rider.presenter.login;

public interface LoginPresenter {

    void onLogin(String phone, String password);

    void onDestroy();
}