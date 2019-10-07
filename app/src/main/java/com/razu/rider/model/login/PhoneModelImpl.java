package com.razu.rider.model.login;

import android.text.TextUtils;

import com.razu.rider.Apps;
import com.razu.rider.R;
import com.razu.rider.entity.User;
import com.razu.rider.networks.listener.ObjectDataParserListener;
import com.razu.rider.networks.manager.data.DataManager;

import org.json.JSONException;
import org.json.JSONObject;

import static com.razu.rider.networks.helper.ApiConstants.POST;
import static com.razu.rider.networks.helper.AppsApi.PHONE_EXIST;

public class PhoneModelImpl implements PhoneModel {

    @Override
    public void onPhoneCheck(String phone, OnPhoneCheckListener listener) {
        if (TextUtils.isEmpty(phone)) {
            listener.onPhoneFailure("Phone empty");
        } else {
            connectToServer(phone, listener);
        }
    }

    private void connectToServer(String phone, final OnPhoneCheckListener listener) {
        DataManager.taskManager(POST, PHONE_EXIST, getAuthHeader(), getPhoneCheckBodyJson(phone), new ObjectDataParserListener() {
            @Override
            public void onDataLoadSuccessfully(User user) {
                if (user.getExist()) {
                    listener.onPhoneSuccess();
                } else {
                    listener.onPhoneFailure(Apps.getInstance().getString(R.string.invalid_phone));
                }
            }

            @Override
            public void onDataLoadFailed(User user) {
                listener.onPhoneError();
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
}