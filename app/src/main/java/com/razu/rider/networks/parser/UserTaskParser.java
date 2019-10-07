package com.razu.rider.networks.parser;

import com.razu.rider.Apps;
import com.razu.rider.R;
import com.razu.rider.entity.User;
import com.razu.rider.networks.helper.ApiConstants;

import org.json.JSONException;
import org.json.JSONObject;

public class UserTaskParser implements ApiConstants {

    public static User taskParse(String response) {
        User riderInfo = new User();
        JSONObject responseObj = null;
        try {
            if (response != null) {
                responseObj = new JSONObject(response);
                if (responseObj.has(STATUS_CODE)) {
                    if (responseObj.optInt(STATUS_CODE) == CODE_200) {
                        riderInfo.setStatusCode(CODE_200);
                        if (responseObj.optBoolean(STATUS)) {
                            riderInfo.setResponseStatus(true);
                            if (responseObj.has(MESSAGE)) {
                                riderInfo.setApiMessage(responseObj.optString(MESSAGE));
                            } else {
                                riderInfo.setApiMessage(Apps.getInstance().getString(R.string.something_msg));
                            }

                            if (responseObj.has(IS_EXIST)) {
                                riderInfo.setExist(responseObj.optBoolean(IS_EXIST));
                            }

                            if (responseObj.has(USER)) {
                                JSONObject userObj = responseObj.optJSONObject(USER);
                                if (userObj != null) {

                                    if (userObj.has(ACCESS_TOKEN)) {
                                        riderInfo.setAccessToken(userObj.optString(ACCESS_TOKEN));
                                    }
                                    if (userObj.has(REFRESH_TOKEN)) {
                                        riderInfo.setRefreshToken(userObj.optString(REFRESH_TOKEN));
                                    }

                                    if (userObj.has(USER_ID)) {
                                        riderInfo.setUserId(userObj.optString(USER_ID));
                                    }

                                    if (userObj.has(PHONE)) {
                                        riderInfo.setPhone(userObj.optString(PHONE));
                                    }

                                    if (userObj.has(FIRST_NAME)) {
                                        riderInfo.setFirstName(userObj.optString(FIRST_NAME));
                                    }

                                    if (userObj.has(LAST_NAME)) {
                                        riderInfo.setLastName(userObj.optString(LAST_NAME));
                                    }
                                    riderInfo.setFullName(riderInfo.getFirstName() + " " + riderInfo.getLastName());

                                    if (userObj.has(EMAIL)) {
                                        riderInfo.setEmail(userObj.optString(EMAIL));
                                    }

                                    if (userObj.has(FIRE_BASE_TOKEN)) {
                                        riderInfo.setRiderFireBaseToken(userObj.getString(FIRE_BASE_TOKEN));
                                    }

                                    if (userObj.has(PHOTO)) {
                                        riderInfo.setUserPhoto(userObj.optString(PHOTO));
                                    }
                                }
                            }
                        }
                    } else if (responseObj.optInt(STATUS_CODE) == CODE_201) {
                        riderInfo.setStatusCode(CODE_201);
                        riderInfo.setResponseStatus(true);
                        if (responseObj.has(MESSAGE)) {
                            riderInfo.setApiMessage(responseObj.optString(MESSAGE));
                        }
                    } else if (responseObj.optInt(STATUS_CODE) == CODE_400) {
                        riderInfo.setStatusCode(CODE_400);
                        riderInfo.setResponseStatus(responseObj.optBoolean(STATUS));
                        if (responseObj.has(MESSAGE)) {
                            riderInfo.setApiMessage(responseObj.optString(MESSAGE));
                        }
                    }
                } else {
                    riderInfo.setStatusCode(CODE_400);
                    riderInfo.setResponseStatus(false);
                    riderInfo.setApiMessage(Apps.getInstance().getString(R.string.something_msg));
                }
            } else {
                riderInfo.setStatusCode(CODE_400);
                riderInfo.setResponseStatus(false);
                riderInfo.setApiMessage(Apps.getInstance().getString(R.string.something_msg));
            }
        } catch (JSONException e) {
            riderInfo.setResponseStatus(false);
            e.printStackTrace();
        }
        return riderInfo;
    }
}