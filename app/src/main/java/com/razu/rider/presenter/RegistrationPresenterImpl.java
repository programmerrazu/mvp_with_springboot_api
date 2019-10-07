package com.razu.rider.presenter;

import com.razu.rider.model.RegistrationModel;
import com.razu.rider.model.RegistrationModelImpl;
import com.razu.rider.view.RegistrationView;

import org.json.JSONObject;

public class RegistrationPresenterImpl implements RegistrationPresenter, RegistrationModel.OnRegistrationListener {

    RegistrationView registrationView;
    RegistrationModel registrationModel;

    public RegistrationPresenterImpl(RegistrationView registrationView) {
        this.registrationView = registrationView;
        this.registrationModel = new RegistrationModelImpl();
    }

    @Override
    public void onValidPhone(String phone) {
        if (registrationView != null) {
            registrationView.showPhoneProgress();
            registrationModel.onPhoneCheck(phone, this);
        }
    }

    @Override
    public void onValidFirstName(String firstName) {
        if (registrationView != null) {
            registrationView.showFirstNameProgress();
            registrationModel.onFirstNameCheck(firstName, this);
        }
    }

    @Override
    public void onValidLastName(String lastName) {
        if (registrationView != null) {
            registrationView.showLastNameProgress();
            registrationModel.onLastNameCheck(lastName, this);
        }
    }

    @Override
    public void onValidEmail(String email) {
        if (registrationView != null) {
            registrationView.showEmailProgress();
            registrationModel.onEmailCheck(email, this);
        }
    }

    @Override
    public void onRegistration(JSONObject body) {
        if (registrationView != null) {
            registrationView.showProgress();
            registrationModel.onRegistrationPerform(body, this);
        }
    }

    @Override
    public void onDestroy() {
        if (registrationView != null) {
            registrationView = null;
        }
    }

    @Override
    public void onPhoneError(String message) {
        if (registrationView != null) {
            registrationView.hidePhoneProgress();
            registrationView.showAlert(message);
        }
    }

    @Override
    public void onFirstNameError(String message) {
        if (registrationView != null) {
            registrationView.hideFirstNameProgress();
            registrationView.showAlert(message);
        }
    }

    @Override
    public void onLastNameError(String message) {
        if (registrationView != null) {
            registrationView.hideLastNameProgress();
            registrationView.showAlert(message);
        }
    }

    @Override
    public void onEmailError(String message) {
        if (registrationView != null) {
            registrationView.hideEmailProgress();
            registrationView.showAlert(message);
        }
    }

    @Override
    public void onPasswordError(String message) {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.showAlert(message);
        }
    }

    @Override
    public void onPhoneSuccess() {
        if (registrationView != null) {
            registrationView.hidePhoneProgress();
            registrationView.onPhoneNext();
        }
    }

    @Override
    public void onFirstNameSuccess() {
        if (registrationView != null) {
            registrationView.hideFirstNameProgress();
            registrationView.onFirstNameNext();
        }
    }

    @Override
    public void onLastNameSuccess() {
        if (registrationView != null) {
            registrationView.hideLastNameProgress();
            registrationView.onLastNameNext();
        }
    }

    @Override
    public void onEmailSuccess() {
        if (registrationView != null) {
            registrationView.hideEmailProgress();
            registrationView.onEmailNext();
        }
    }

    @Override
    public void onRegistrationSuccess() {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.onNavigateMain();
        }
    }

    @Override
    public void onRegistrationError() {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.setRegistrationError();
        }
    }

    @Override
    public void onRegistrationFailure(String message) {
        if (registrationView != null) {
            registrationView.hideProgress();
            registrationView.showAlert(message);
        }
    }
}