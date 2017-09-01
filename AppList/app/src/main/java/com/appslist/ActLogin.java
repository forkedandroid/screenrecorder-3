package com.appslist;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.demo.ExoPlayerActivity;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Admin on 8/4/2016.
 */

public class ActLogin extends Activity {
    private static int TIME = 5000;

    String TAG = "=ActLogin=";

    private FirebaseAnalytics mFirebaseAnalytics;
    private FirebaseAuth mFirebaseAuth;

    EditText etCode;
    Button btnSkip,btnLogin;


String strCode = "111";
    @SuppressLint("InlinedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);


            try {
                requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
                setContentView(R.layout.act_login);

                btnLogin = (Button)findViewById(R.id.btnLogin);
                btnSkip = (Button)findViewById(R.id.btnSkip);
                etCode = (EditText)findViewById(R.id.etCode);


                btnLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        strCode = etCode.getText().toString();
                        if(strCode.equalsIgnoreCase("31191") || strCode.equalsIgnoreCase("prince")) {
                            //getAsyncLogin();
                            App.sharePrefrences.setPref("isLogin","1");

                            Intent iv = new Intent(ActLogin.this, ActHomeList.class);
                            iv.putExtra(App.ITAG_FROM, "login");
                            startActivity(iv);
                            finish();
                        }
                        else
                        {
                            App.showSnackBar(etCode,"Please enter correct code.");
                        }

                    }
                });
                btnSkip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent iv = new Intent(ActLogin.this, ExoPlayerActivity.class);
                        iv.putExtra(App.ITAG_FROM, "login");
                        startActivity(iv);
                        finish();
                    }
                });



                Log.d(TAG, "----onCreate--------");
            } catch (Exception e) {
                // TODO: handle exceptione.
                e.printStackTrace();
            }
    }



}