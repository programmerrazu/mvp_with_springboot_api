package com.razu.rider.networks.listener;

public interface StringDataParserListener {

    void onDataLoadSuccessfully(String response);

    void onDataLoadFailed(String response);
}