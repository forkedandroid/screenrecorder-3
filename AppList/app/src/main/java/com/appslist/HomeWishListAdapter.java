package com.appslist;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
import com.com.api.ApiService;
import com.com.api.AppsList;
import com.com.response.AppsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Adapts Views containing kittens to RecyclerView cells
 *
 * @author bherbst
 */
public class HomeWishListAdapter extends RecyclerView.Adapter<HomeWishListAdapter.HomeWishViewHolder> {
    private ArrayList<AppsModel> mArrayListHomeWishListModel;
    private final HomeWishClickListener mListener;
    Context mContext;


    ApiService methods;
    Callback callback;
    ProgressDialog customProgressDialog;
    String TAG = "=HomeWishListAdapter=";
    Switch swAppStatus;
    EditText etCode,etName;
    Button btnAdd, btnCancel;
    String strCode = "111",strName = "aaa",strStatus="0";
    private PopupWindow popupWindow;



    /**
     * Constructor
     *
     * @param listener A listener for kitten click events
     */
    public HomeWishListAdapter(ArrayList<AppsModel> arrayListHomeWishListModel, HomeWishClickListener listener, Context context) {
        mArrayListHomeWishListModel = arrayListHomeWishListModel;
        mListener = listener;
        mContext = context;
        customProgressDialog = new ProgressDialog(mContext);
        customProgressDialog.setMessage("Please wait.....");
    }

    @Override
    public HomeWishListAdapter.HomeWishViewHolder onCreateViewHolder(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        //View cell = inflater.inflate(R.layout.grid_item, container, false);
        View cell = inflater.inflate(R.layout.raw_wish_cardview, container, false);
        //View cell = inflater.inflate(R.layout.m_raw_large_cardview, container, false);
        Log.i("--create view--","-position-22--"+position);
        return new HomeWishListAdapter.HomeWishViewHolder(cell,container.getContext(),position);
    }

    @Override
    public void onBindViewHolder(final HomeWishListAdapter.HomeWishViewHolder viewHolder, final int position) {

        ViewCompat.setTransitionName(viewHolder.image, String.valueOf(position) + "_image");

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onKittenClicked(viewHolder, position);
            }
        });

        viewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(popupWindow!=null && popupWindow.isShowing())
                {
                    popupWindow.dismiss();
                }
                openPopupEditApps(mArrayListHomeWishListModel.get(position));
            }
        });



        String url = mArrayListHomeWishListModel.get(position).getImage();
        Picasso.with(mContext).load(url).placeholder(R.drawable.app_icon_a).into(viewHolder.image);

      viewHolder.tvNotiName.setText(mArrayListHomeWishListModel.get(position).getName());
      viewHolder.tvDetail.setText("CODE = "+mArrayListHomeWishListModel.get(position).getCode() + "  ###  ID = "+mArrayListHomeWishListModel.get(position).getId());
        viewHolder.tvNotiTime.setText(mArrayListHomeWishListModel.get(position).getDate());



        if(mArrayListHomeWishListModel.get(position).getStatus().equalsIgnoreCase("1"))
        {
            viewHolder.tvNotiName.setAlpha(1f);
            viewHolder.vOnline.setSelected(true);
        }
        else
        {
            viewHolder.tvNotiName.setAlpha(0.6f);
            viewHolder.vOnline.setSelected(false);
        }
    }

    @Override
    public int getItemCount() {
        return mArrayListHomeWishListModel.size();
    }




    public class HomeWishViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CardView cv;
        TextView tvNotiName,tvNotiTime,tvDetail;
        View vOnline;
        public HomeWishViewHolder(View itemView, Context context,int position) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.image);
            Log.i("--create view--","-position---"+position);

            image = (ImageView) itemView.findViewById(R.id.person_photo);
            cv = (CardView)itemView.findViewById(R.id.cv);
            tvNotiName = (TextView)itemView.findViewById(R.id.tvNotiName);
            tvNotiTime = (TextView)itemView.findViewById(R.id.tvNotiTime);
            tvDetail = (TextView)itemView.findViewById(R.id.tvDetail);
            vOnline= (View)itemView.findViewById(R.id.vOnline);
            tvNotiName.setTypeface(App.getFontShortStack());
            tvNotiTime.setTypeface(App.getFontRoboto());
            tvDetail.setTypeface(App.getFontRoboto());
        }

    }









    private void openPopupEditApps(final AppsModel appsModel)
    {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_edit_app, null);

        popupWindow  = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        strCode =appsModel.getCode();
        strName = appsModel.getName();
        strStatus = appsModel.getStatus();

      /*  Switch swAppStatusU;
        EditText etCode,etName;
        Button btnAdd, btnCancel;*/
        swAppStatus = (Switch) popupView.findViewById(R.id.swAppStatus);
        etCode = (EditText) popupView.findViewById(R.id.etCode);
        etName = (EditText) popupView.findViewById(R.id.etName );

        btnAdd = (Button) popupView.findViewById(R.id.btnAdd);
        btnCancel = (Button) popupView.findViewById(R.id.btnCancel);

        etCode.requestFocus();

        etCode.setText(strCode);
        etName.setText(strName);

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 5, 5);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.update();



        swAppStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked == true)
                {
                    strStatus = "1";
                }
                else
                {
                    strStatus = "0";
                }
            }
        });

        if(appsModel.getStatus().equalsIgnoreCase("1")) {
            swAppStatus.setChecked(true);
        }
        else
        {
            swAppStatus.setChecked(false);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCode = etCode.getText().toString().trim();
                strName = etName.getText().toString().trim();

                if(strCode.length() > 2 && strName.length() > 2)
                {
                    // set api call here
                    popupWindow.dismiss();
                    updateAppToList(appsModel.getId(),strCode,strName,strStatus);
                }
                else
                {
                    etCode.requestFocus();
                    if(mContext !=null)
                        App.ToastShort(mContext,"Please fill app details");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });
    }




    private void updateAppToList(String strId,String strCode ,String strName ,String strStatus)
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(App.strBaseUrl)
                .build();
        methods = restAdapter.create(ApiService.class);
        callback = new Callback() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @SuppressLint("LongLogTag")
            @Override
            public void success(Object o, Response response) {
                Log.v(TAG, "Sucess---1");
                Log.v(TAG, "Response"+response.toString());
                AppsList curators = (AppsList) o;
                if (curators != null && curators.getStatus() != null &&curators.getStatus().equalsIgnoreCase("1")) {
                    Toast.makeText(mContext,"App updated succesfully",Toast.LENGTH_LONG).show();

                    ((Activity) mContext).finishAffinity();
                    ((Activity) mContext).finish();
                    mContext.startActivity(new Intent(mContext,ActHomeList.class));
                }
                else
                {
                    Toast.makeText(mContext,"No app updated fail try again..!",Toast.LENGTH_LONG).show();
                }
                customProgressDialog.dismiss();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.v(TAG, "failure---1--");
                Log.e("-Error-","--failure--"+retrofitError.getMessage());
                Toast.makeText(mContext,"Something went wrong please try after some time latter...!",Toast.LENGTH_LONG).show();
                customProgressDialog.dismiss();
            }
        };
        customProgressDialog.show();
        methods.updateAppToList(strId,strCode,strName,strStatus, callback);
        Log.v(TAG, "Api cal---1--");
    }


}
