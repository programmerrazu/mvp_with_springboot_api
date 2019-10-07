package com.razu.rider.model.login;

import android.text.TextUtils;

import com.razu.rider.Apps;
import com.razu.rider.R;
import com.razu.rider.entity.User;
import com.razu.rider.networks.listener.ObjectDataParserListener;
import com.razu.rider.networks.manager.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.razu.rider.networks.helper.ApiConstants.CODE_200;
import static com.razu.rider.networks.helper.ApiConstants.CODE_201;
import static com.razu.rider.networks.helper.ApiConstants.POST;
import static com.razu.rider.networks.helper.AppsApi.LOGIN;

public class LoginModelImpl implements LoginModel {


    @Override
    public void onLoginPerform(String phone, String password, OnLoginListener listener) {
        if (TextUtils.isEmpty(password)) {
            listener.onLoginFailure("Password empty");
        } else {
            connectToServer(phone, password, listener);
        }
    }

    private void connectToServer(String phone, String password, final OnLoginListener listener) {
        DataManager.taskManager(POST, LOGIN, getAuthHeader(), getLoginBodyJson(phone, password), new ObjectDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(User user) {
                if (user.getStatusCode() == CODE_200) {
                    Apps.saveToSession(user);
                    listener.onLoginSuccess();
                } else if (user.getStatusCode() == CODE_201) {
                    listener.onLoginFailure(Apps.getInstance().getString(R.string.invalid_user));
                }
            }

            @Override
            public void onDataLoadFailed(User user) {
                listener.onLoginError();
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

    private JSONObject getLoginBodyJson(String phone, String password) {
        JSONObject bodyJson = new JSONObject();
        try {
            bodyJson.put("phone", phone);
            bodyJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bodyJson;
    }
}