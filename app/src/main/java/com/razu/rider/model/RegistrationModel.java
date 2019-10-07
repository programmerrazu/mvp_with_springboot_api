package com.razu.rider.model;

import org.json.JSONObject;

public interface RegistrationModel {

    interface OnRegistrationListener {

        void onPhoneError(String message);

        void onFirstNameError(String message);

        void onLastNameError(String message);

        void onEmailError(String message);

        void onPasswordError(String message);

        void onPhoneSuccess();

        void onFirstNameSuccess();

        void onLastNameSuccess();

        void onEmailSuccess();

        void onRegistrationSuccess();

        void onRegistrationError();

        void onRegistrationFailure(String message);
    }

    void onPhoneCheck(String phone, OnRegistrationListener listener);

    void onFirstNameCheck(String firstName, OnRegistrationListener listener);

    void onLastNameCheck(String lastName, OnRegistrationListener listener);

    void onEmailCheck(String email, OnRegistrationListener listener);

    void onRegistrationPerform(JSONObject jsonObject, OnRegistrationListener listener);
}