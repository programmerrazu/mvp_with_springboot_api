package com.razu.rider.view;

public interface RegistrationView {

    void showProgress();

    void hideProgress();

    void showPhoneProgress();

    void hidePhoneProgress();

    void showFirstNameProgress();

    void hideFirstNameProgress();

    void showLastNameProgress();

    void hideLastNameProgress();

    void showEmailProgress();

    void hideEmailProgress();

    void setRegistrationError();

    void showAlert(String message);

    void onPhoneNext();

    void onFirstNameNext();

    void onLastNameNext();

    void onEmailNext();

    void onNavigateMain();
}