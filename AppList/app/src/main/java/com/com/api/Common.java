package com.com.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 8/5/2016.
 */

public class Common
{
    @SerializedName("s")
    String status;

    @SerializedName("m")
    String msg;



    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
