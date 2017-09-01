package com.appslist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.ActGeneratePDF;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Admin on 8/4/2016.
 */

public class ActSplash extends Activity
{
    private static int TIME = 2000;

    String TAG = "=ActSplacescreen=";

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mFirebaseAuth;

    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.act_splash);

            getDeviceToken();
            // Initialize FirebaseAuth
            mFirebaseAuth = FirebaseAuth.getInstance();
            // Obtain the FirebaseAnalytics instance.
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

            setSendDataAnalytics();
            setSendCrashData();
            displaySplash();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    private void getDeviceToken()
    {
        try{
            // [START subscribe_topics]
            //FirebaseMessaging.getInstance().subscribeToTopic("news");
            Log.d(TAG, "Subscribed to news topic");
            // [END subscribe_topics]
            Log.d(TAG, "InstanceID token: " + FirebaseInstanceId.getInstance().getToken());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }




    private void setSendDataAnalytics()
    {
        try{
            Log.d(TAG, "---FirebaseAnalytics.Event.SELECT_CONTENT------");
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "111");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Prince");
            bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);


            Log.d(TAG, "---FirebaseAnalytics.Event.SHARE------");
            Bundle bundle2 = new Bundle();
            bundle2.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "prince article");
            bundle2.putString(FirebaseAnalytics.Param.ITEM_ID, "p786");
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SHARE, bundle);

        }
        catch (Exception e)
        {
            Log.d(TAG, "---setSendDataAnalytics-Error send analytics--");
            e.printStackTrace();
        }
    }

    private void setSendCrashData()
    {
        Log.d(TAG, "---setSendCrashData--");
        FirebaseCrash.logcat(Log.ERROR, TAG, "crash caused");
        FirebaseCrash.report(new Exception("My first Android non-fatal error"));
        FirebaseCrash.log("Activity created");

        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id_a311");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name_prince");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    // splash screen set with timing
    private void displaySplash() {
        // TODO Auto-generated method stub b

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                String isLogin = App.sharePrefrences.getStringPref("isLogin");
              /*  if(isLogin !=null && isLogin.equalsIgnoreCase("1")) {
                    Intent iv = new Intent(ActSplash.this, ActHomeList.class);
                    iv.putExtra(App.ITAG_FROM, "splash");
                    startActivity(iv);
                    finish();
                }
                else*/
                {
                    Intent iv = new Intent(ActSplash.this, ActLogin.class);//ActLogin -- ActGeneratePDF
                    iv.putExtra(App.ITAG_FROM, "splash");
                    startActivity(iv);
                    finish();
                }
            }
        }, TIME);
    }

}
