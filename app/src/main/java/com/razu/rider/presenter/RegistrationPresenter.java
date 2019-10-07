package com.razu.rider.presenter;

import org.json.JSONObject;

public interface RegistrationPresenter {

    void onValidPhone(String phone);

    void onValidFirstName(String firstName);

    void onValidLastName(String lastName);

    void onValidEmail(String email);

    void onRegistration(JSONObject body);

    void onDestroy();
}