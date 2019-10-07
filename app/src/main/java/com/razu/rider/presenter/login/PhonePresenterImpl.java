package com.razu.rider.presenter.login;

import com.razu.rider.model.login.PhoneModel;
import com.razu.rider.model.login.PhoneModelImpl;
import com.razu.rider.view.login.PhoneView;

public class PhonePresenterImpl implements PhonePresenter, PhoneModel.OnPhoneCheckListener {

    PhoneView phoneView;
    PhoneModel phoneModel;

    public PhonePresenterImpl(PhoneView phoneView) {
        this.phoneView = phoneView;
        this.phoneModel = new PhoneModelImpl();
    }

    @Override
    public void validatePhone(String phone) {
        if (phoneView != null) {
            phoneView.showProgress();
            phoneModel.onPhoneCheck(phone, this);
        }
    }

    @Override
    public void onDestroy() {
        if (phoneView != null) {
            phoneView = null;
        }
    }

    @Override
    public void onPhoneError() {
        if (phoneView != null) {
            phoneView.hideProgress();
            phoneView.setPhoneError();
        }
    }

    @Override
    public void onPhoneSuccess() {
        if (phoneView != null) {
            phoneView.hideProgress();
            phoneView.navigatePassword();
        }
    }

    @Override
    public void onPhoneFailure(String message) {
        if (phoneView != null) {
            phoneView.hideProgress();
            phoneView.showAlert(message);
        }
    }
}