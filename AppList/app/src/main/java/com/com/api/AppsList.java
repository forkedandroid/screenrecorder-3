package com.com.api;

import com.com.response.AppsModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Admin on 8/5/2016.
 */

public class AppsList extends Common
{


    @SerializedName("r")
    ArrayList<AppsModel> arrListAppsModel;


    public ArrayList<AppsModel> getArrListAppsModel() {
        return arrListAppsModel;
    }

    public void setArrListAppsModel(ArrayList<AppsModel> arrListAppsModel) {
        this.arrListAppsModel = arrListAppsModel;
    }
}
