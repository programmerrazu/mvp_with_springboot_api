package com.razu.rider.model.login;

public interface PhoneModel {

    interface OnPhoneCheckListener {

        void onPhoneError();

        void onPhoneSuccess();

        void onPhoneFailure(String message);
    }

    void onPhoneCheck(String phone, OnPhoneCheckListener listener);
}