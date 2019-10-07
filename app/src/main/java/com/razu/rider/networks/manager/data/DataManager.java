package com.razu.rider.networks.manager.data;

import com.razu.rider.entity.User;
import com.razu.rider.networks.listener.ObjectDataParserListener;
import com.razu.rider.networks.listener.StringDataParserListener;
import com.razu.rider.networks.task.ApiHitTask;
import com.razu.rider.networks.task.ObjectResponseTask;
import com.razu.rider.networks.task.StringResponseTask;

import org.json.JSONObject;

public class DataManager {

    /**************** object data task
     * **************
     * @param methodType
     * @param url
     * @param body
     * @param header
     * @param odpListener
     */
    public static void taskManager(String methodType, String url, JSONObject header, JSONObject body, final ObjectDataParserListener odpListener) {
        ObjectResponseTask task = new ObjectResponseTask(methodType, url, header, body);
        task.setTaskListener(new ObjectResponseTask.TaskListener() {
            @Override
            public void dataGetSuccessfully(User user) {
                if (user == null) {
                    odpListener.onDataLoadFailed(user);
                } else {
                    if (user.getResponseStatus()) {
                        odpListener.onDataLoadSuccessfully(user);
                    } else {
                        odpListener.onDataLoadFailed(user);
                    }
                }
            }

            @Override
            public void dataGetFailed(User user) {
                odpListener.onDataLoadFailed(user);
            }
        });
        task.execute();
    }


    /******************* string data task
     * ********************
     * @param methodType
     * @param url
     * @param body
     * @param header
     * @param sdpListener
     */
    public static void taskManager(String methodType, String url, JSONObject body, JSONObject header, final StringDataParserListener sdpListener) {
        StringResponseTask task = new StringResponseTask(methodType, url, body, header);
        task.setTaskListener(new StringResponseTask.TaskListener() {
            @Override
            public void dataGetSuccessfully(String response) {
                if (response == null) {
                    sdpListener.onDataLoadFailed(response);
                } else {
                    sdpListener.onDataLoadSuccessfully(response);
                }
            }

            @Override
            public void dataGetFailed(String response) {
                sdpListener.onDataLoadFailed(response);
            }
        });
        task.execute();
    }

    public static void apiHitTask(String methodType, final StringDataParserListener sdpListener) {
        ApiHitTask task = new ApiHitTask(methodType);
        task.setTaskListener(new ApiHitTask.TaskListener() {
            @Override
            public void dataGetSuccessfully(String response) {
                if (response == null) {
                    sdpListener.onDataLoadFailed(response);
                } else {
                    sdpListener.onDataLoadSuccessfully(response);
                }
            }

            @Override
            public void dataGetFailed(String response) {
                sdpListener.onDataLoadFailed(response);
            }
        });
        task.execute();
    }
}