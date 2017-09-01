package com.com.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;


/**
 * Created by Admin on 8/5/2016.
 */

public interface ApiService
{


    //http://ultratvbox.com/Admin/api/adminapp/api/wapps.php
    @GET("/wapps.php")
    void getAppsList
            (
                    @Query("uid") String uid,
                    @Query("did") String device_token,
                    Callback<AppsList> cb
            );


    @GET("/add_apps.php")
    void addAppToList
            (
                    @Query("code") String code,
                    @Query("name") String name,
                    Callback<AppsList> cb
            );


    @GET("/update_apps.php")
    void updateAppToList
            (
                    @Query("id") String id,
                    @Query("code") String code,
                    @Query("name") String name,
                    @Query("status") String status,
                    Callback<AppsList> cb
            );



    @GET("/delete_apps.php")
    void deleteAppToList
            (
                    @Query("id") String id,
                    @Query("code") String code,
                    Callback<AppsList> cb
            );


    @GET("/check_app.php")
    void checkAppStatus
            (
                    @Query("code") String uid,
                    @Query("name") String device_token,
                    Callback<AppsList> cb
            );

    //List ==http://ultratvbox.com/Admin/api/adminapp/api/wapps.php
    // Delete == http://ultratvbox.com/Admin/api/adminapp/api/delete_apps.php?code=2100&id=4
    //Update == http://ultratvbox.com/Admin/api/adminapp/api/update_apps.php?code=2100&name=samsung&status=1&id=4
    // check == http://ultratvbox.com/Admin/api/adminapp/api/check_app.php?code=202&name=lzwallpaper

}
