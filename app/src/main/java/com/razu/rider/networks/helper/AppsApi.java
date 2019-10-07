package com.razu.rider.networks.helper;

public interface AppsApi {

    String IP = "192.168.0.104";

    String BASE_URL = "http://" + IP + ":8888/user/mobile/";

    String PHONE_EXIST = BASE_URL + "exist/phone";

    String LOGIN = BASE_URL + "login";

    String REGISTRATION = BASE_URL + "registration";
}