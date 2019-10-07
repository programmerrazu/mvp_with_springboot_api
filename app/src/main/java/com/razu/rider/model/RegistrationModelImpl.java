package com.razu.rider.model;

import android.text.TextUtils;
import android.util.Patterns;

import com.razu.rider.Apps;
import com.razu.rider.R;
import com.razu.rider.entity.User;
import com.razu.rider.networks.listener.ObjectDataParserListener;
import com.razu.rider.networks.manager.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.razu.rider.networks.helper.ApiConstants.CODE_200;
import static com.razu.rider.networks.helper.ApiConstants.POST;
import static com.razu.rider.networks.helper.AppsApi.PHONE_EXIST;
import static com.razu.rider.networks.helper.AppsApi.REGISTRATION;

public class RegistrationModelImpl implements RegistrationModel {

    @Override
    public void onPhoneCheck(String phone, OnRegistrationListener listener) {
        if (TextUtils.isEmpty(phone)) {
            listener.onPhoneError("Phone number empty");
        } else {
            connectToServer(phone, listener);
        }
    }

    @Override
    public void onFirstNameCheck(String firstName, OnRegistrationListener listener) {
        if (TextUtils.isEmpty(firstName)) {
            listener.onFirstNameError("First Name empty");
        } else {
            listener.onFirstNameSuccess();
        }
    }

    @Override
    public void onLastNameCheck(String lastName, OnRegistrationListener listener) {
        if (TextUtils.isEmpty(lastName)) {
            listener.onLastNameError("Last Name empty");
        } else {
            listener.onLastNameSuccess();
        }
    }

    @Override
    public void onEmailCheck(String email, OnRegistrationListener listener) {
        if (TextUtils.isEmpty(email)) {
            listener.onEmailError("Email empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            listener.onEmailError("Invalid email");
        } else {
            listener.onEmailSuccess();
        }
    }

    @Override
    public void onRegistrationPerform(JSONObject body, OnRegistrationListener listener) {
        try {
            if (TextUtils.isEmpty(body.getString("password"))) {
                listener.onPasswordError("Password empty");
            } else {
                onRegistration(body, listener);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void connectToServer(String phone, final OnRegistrationListener listener) {
        DataManager.taskManager(POST, PHONE_EXIST, getAuthHeader(), getPhoneCheckBodyJson(phone), new ObjectDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(User user) {
                if (!user.getExist()) {
                    listener.onPhoneSuccess();
                } else {
                    listener.onPhoneError(Apps.getInstance().getString(R.string.phone_exist));
                }
            }

            @Override
            public void onDataLoadFailed(User user) {
                listener.onPhoneError(user.getApiMessage());
            }
        });
    }

    private JSONObject getAuthHeader() {
        JSONObject headerJson = new JSONObject();
        try {
            headerJson.put("Authorization", "Basic YXV0by1yaWRlLWFkbWluLWNsaWVudC1zZWNyZXQtbmFtZTphdXRvLXJpZGUtYWRtaW4tY2xpZW50LXNlY3JldC1rZXk=");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headerJson;
    }

    private JSONObject getPhoneCheckBodyJson(String phone) {
        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("phone", phone);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bodyJson;
    }

    private void onRegistration(JSONObject bodyJson, final OnRegistrationListener listener) {
        DataManager.taskManager(POST, REGISTRATION, getAuthHeader(), bodyJson, new ObjectDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(User user) {
                if (user.getStatusCode() == CODE_200) {
                    Apps.saveToSession(user);
                    listener.onRegistrationSuccess();
                }
            }

            @Override
            public void onDataLoadFailed(User user) {
                listener.onRegistrationFailure(user.getApiMessage());
            }
        });
    }
}