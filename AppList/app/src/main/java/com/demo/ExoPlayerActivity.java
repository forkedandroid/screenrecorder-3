package com.demo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import com.appslist.App;
import com.appslist.R;
import com.devbrackets.android.exomedia.listener.OnErrorListener;
import com.devbrackets.android.exomedia.listener.OnPreparedListener;
import com.devbrackets.android.exomedia.ui.widget.EMVideoView;



public class ExoPlayerActivity extends Activity {
    private static final String TAG = "==ExoPlayerActivity==";
    VideoView videoView;
    EMVideoView emVideoView;
    Button btnA,btnB,btnC;

    public static final String TAG_VIDEO_URL	= "video_url";
    String path = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
/*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/

        setContentView(R.layout.act_mypost_detail);
        //setupVideoView();

        initViews();
        setClickEvents();
    }


    private void initViews() {
        emVideoView = (EMVideoView)findViewById(R.id.emVideoView);
        videoView = (VideoView)findViewById(R.id.videoView);
        btnA = (Button)findViewById(R.id.btnA);
        btnB = (Button)findViewById(R.id.btnB);
        btnC = (Button)findViewById(R.id.btnC);

        btnA.setText("Canal 26");
        btnB.setText("FM Blue");
        btnC.setText("LA 100");

    }

    private void setClickEvents() {

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = "rtsp://live-edge01.telecentro.net.ar:80/live/26hd-360";
                emVideoView.stopPlayback();
                videoView.setVisibility(View.VISIBLE);
                emVideoView.setVisibility(View.GONE);
                playLiveLink();
              //  playData(path);

            }
        });
        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                path = "http://mp3.metroaudio1.stream.avstreaming.net:7200/bluefmaudio1";
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
                emVideoView.setVisibility(View.VISIBLE);
                playData(path);
            }
        });
        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                path = "http://buecrplb01.cienradios.com.ar/la100.aac";
                videoView.stopPlayback();
                videoView.setVisibility(View.GONE);
                emVideoView.setVisibility(View.VISIBLE);
                playData(path);
            }
        });

    }

    private void playData(String strPathUrl) {
        emVideoView.setVisibility(View.VISIBLE);

        emVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                emVideoView.start();
            }
        });
        emVideoView.setVideoURI(Uri.parse(strPathUrl));

        emVideoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError() {

                App.showLog("===path1111===ERROR===== emVideoView.setOnErrorListene======");
                return false;
            }
        });
    }


    private void playLiveLink()
    {

        final ProgressDialog progressDialog = new ProgressDialog(ExoPlayerActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
        //vitamio_videoView
        videoView.setVisibility(View.VISIBLE);
        emVideoView.setVisibility(View.GONE);


        path = "rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov";
        path = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4";

        path = "rtsp://live-edge01.telecentro.net.ar:80/live/26hd-360";

        //videoView.setVideoPath(path);

        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //Set the path of Video or URI
        videoView.setVideoURI(Uri.parse(path));

        videoView.setMediaController(new MediaController(this));
        videoView.requestFocus();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
             //   mediaPlayer.setPlaybackSpeed(1.0f);
                mediaPlayer.start();
                progressDialog.dismiss();
            }

        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressDialog.dismiss();
                return false;
            }
        });


/*
        //add controls to a MediaPlayer like play, pause.
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        //Set the path of Video or URI
        videoView.setVideoURI(Uri.parse(path));
        //

        //Set the focus
        videoView.requestFocus();
        videoView.start();
        videoView.setVisibility(View.VISIBLE);
        emVideoView.setVisibility(View.GONE);*/
    }


    private void setupVideoView() {

        emVideoView = (EMVideoView)findViewById(R.id.emVideoView);
        emVideoView.setVisibility(View.VISIBLE);
        emVideoView.setOnPreparedListener(new OnPreparedListener() {
            @Override
            public void onPrepared() {
                emVideoView.start();
            }
        });

        Bundle bundle;
        String path = "https://archive.org/download/Popeye_forPresident/Popeye_forPresident_512kb.mp4";
        if(getIntent().getExtras() !=null)
        {

            String streamId = getIntent().getStringExtra(TAG_VIDEO_URL);
            bundle = getIntent().getExtras();
            if(bundle !=null)
            {
                if(bundle.getString(TAG_VIDEO_URL) !=null && bundle.getString(TAG_VIDEO_URL).length() > 1)
                {
                    path = bundle.getString(TAG_VIDEO_URL);

                    App.showLog("===path1111===>"+path);
                }
            }
        }
        //For now we just picked an arbitrary item to play.  More can be found at
        //https://archive.org/details/more_animation
        emVideoView.setVideoURI(Uri.parse(path));

        emVideoView.setOnErrorListener(new OnErrorListener() {
            @Override
            public boolean onError() {

                App.showLog("===path1111===ERROR===== emVideoView.setOnErrorListene======");
                return false;
            }
        });
    }





    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}