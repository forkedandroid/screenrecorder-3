package com.com.response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 8/4/2016.
 */

public class AppsModel
{
    @SerializedName("id")
    String id;

    @SerializedName("code")
    String code;

    @SerializedName("name")
    String name;

    @SerializedName("status")
    String status;

    @SerializedName("date")
    String date;


    String image;


    public AppsModel(String id, String image, String name, String code, String status,String date)
    {
        this.id = id;
        this.image = image;
        this.name = name;
        this.code = code;
        this.status = status;
        this.date = date;
    }


    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }
}
