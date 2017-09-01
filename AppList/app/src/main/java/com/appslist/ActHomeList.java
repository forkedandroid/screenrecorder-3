package com.appslist;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.com.api.ApiService;
import com.com.api.AppsList;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Admin on 8/4/2016.
 */

public class ActHomeList extends AppCompatActivity {

    ApiService methods;
    Callback callback;

    ProgressDialog customProgressDialog;

    String TAG = "=ActHomeList=";


    Bundle savedInstanceState2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState2 = savedInstanceState;

        getWindow().addFlags(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);


        customProgressDialog = new ProgressDialog(this);
        getAsyncLogin();


    }




    private void getAsyncLogin()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(App.strBaseUrl)
                .build();
        methods = restAdapter.create(ApiService.class);
        callback = new Callback() {
            @SuppressLint("LongLogTag")
            @Override
            public void success(Object o, Response response) {
                Log.v(TAG, "Sucess---1");
                Log.v(TAG, "Response"+response.toString());
                AppsList curators = (AppsList) o;
                if (curators != null && curators.getArrListAppsModel() != null && curators.getArrListAppsModel().size() > 0) {
                    Log.v(TAG, "Sucess---1---getArrListAppsModel.size---" + curators.getArrListAppsModel().size());
                    App.arrayListHomeWishListModel = new ArrayList<>();
                    App.arrayListHomeWishListModel = curators.getArrListAppsModel();

                    if (savedInstanceState2 == null) {
                        getSupportFragmentManager()
                                .beginTransaction()
                                .add(R.id.container, new HomeWishListFragment())
                                .commit();
                    }
                }
                else
                {
                    Toast.makeText(ActHomeList.this,"There is no apps",Toast.LENGTH_LONG).show();
                }
                customProgressDialog.dismiss();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.v(TAG, "failure---1--");
                Log.e("-Error-","--failure--"+retrofitError.getMessage());
                Toast.makeText(ActHomeList.this,"Something went wrong please try after some time latter...!",Toast.LENGTH_LONG).show();
                customProgressDialog.dismiss();
            }
        };
        customProgressDialog.setMessage("Please wait.....");
        customProgressDialog.show();
        methods.getAppsList("1","as5d65a6s54d1a1s6d16516as1d161a6s1d6516", callback);
        Log.v(TAG, "Api cal---1--");
    }

}