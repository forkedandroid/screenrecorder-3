package com.appslist;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.com.response.AppsModel;
import com.utils.SharePrefrences;

import java.util.ArrayList;

/**
 * Created by Admin on 8/4/2016.
 */

public class App extends Application
{
    // share pref name
    public String PREF_NAME = "makeawish";

    // class for the share pref keys and valyes get set
    public static SharePrefrences sharePrefrences;

    // intent passing tags or string key names
    public static String ITAG_FROM = "from";

    // for the app context
    static Context mContext;

    // for the set app fontface or type face
    static Typeface tfRoboto,tfShortStack;


    // for the notification total list of items
    public static ArrayList<AppsModel>  arrayListHomeWishListModel;


    // for the  api call
    public static String strBaseUrl="http://ultratvbox.com/Admin/api/adminapp/api";


    // application on create methode for the create and int base values
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        sharePrefrences = new SharePrefrences(App.this);
        getFontRoboto();
        getFontShortStack();
        setDataArrayList();
    }

    private void setDataArrayList() {
        arrayListHomeWishListModel = new ArrayList<>();
        String id="",image="",name="",detail="",time="",date="";

        String strLongDesc = "" +
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam auctor mi commodo neque venenatis finibus. In cursus, nisi sit amet rutrum ullamcorper, nibh nulla sollicitudin justo, eu gravida nulla tortor eget sem. Proin ullamcorper dapibus mi vel fringilla. Mauris placerat elementum arcu, vitae gravida sem mollis ut. Proin finibus sit amet justo eu scelerisque. Nullam ac quam ac odio tincidunt ornare. Phasellus a ante placerat, venenatis odio ut, interdum ante.\n" +

                "";





        id="15";
        image="https://static1.artfire.com/uploads/products/2015/04/27/94/11776754/large/large_mickey_mouse_wall_decal_giant_disney_cartoon_popular_children_decor_wall_sticker_kid_designs_cartoon_peel_and_stick_wall_decal_9cf5a548_801303.jpg";
        name="disney_cartoon";
        detail=strLongDesc+"large_mickey_mouse_wall_decal_giant_disney_cartoon_popular_children_decor_wall_sticker_kid_designs_cartoon_peel_and_stick_wall_decal_9cf5a548_801303.";
        time="5 mints ago";
        date="0000-00-00 00:00:00";
        arrayListHomeWishListModel.add(new AppsModel(id,image,name,detail,time,date));



    }


    public static Typeface getFontRoboto()
    {
        tfRoboto = Typeface.createFromAsset(mContext.getAssets(),"fonts/Roboto-Regular.ttf");
        return tfRoboto;
    }

    public static Typeface getFontShortStack() {
        tfShortStack = Typeface.createFromAsset(mContext.getAssets(),"fonts/ShortStack-Regular.ttf");
        return tfShortStack;
    }


    public static  void showSnackBar(View view,String strMessage)
    {
        Snackbar.make(view,strMessage,Snackbar.LENGTH_SHORT).show();
    }

    public static void ToastShort(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
    public static void ToastLong(Context context,String msg)
    {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }




    public static  void sysOut(String strMessage)
    {
        System.out.println("====== "+strMessage+" ======");
    }

    public static  void showLog(String strMessage)
    {
        Log.v("==App==", "--Data--"+strMessage);
    }




    public static void setTaskBarColored(Activity context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
        }
    }
}
