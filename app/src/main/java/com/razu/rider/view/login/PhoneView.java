package com.razu.rider.view.login;

public interface PhoneView {

    void showProgress();

    void hideProgress();

    void setPhoneError();

    void navigatePassword();

    void showAlert(String message);
}