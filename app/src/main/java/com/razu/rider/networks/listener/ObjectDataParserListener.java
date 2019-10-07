package com.razu.rider.networks.listener;

import com.razu.rider.entity.User;

public interface ObjectDataParserListener {

    void onDataLoadSuccessfully(User user);

    void onDataLoadFailed(User user);
}