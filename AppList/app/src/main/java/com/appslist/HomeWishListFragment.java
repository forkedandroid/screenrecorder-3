package com.appslist;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.Switch;
import android.widget.Toast;

import com.com.api.ApiService;
import com.com.api.AppsList;
import com.com.response.AppsModel;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Displays a grid of pictures
 *
 * @author bherbst
 */
public class HomeWishListFragment extends Fragment implements HomeWishClickListener {

    FrameLayout flAdd,flLogout;
    RecyclerView recyclerView;


    private TransitionManager mTransitionManager;
    private Scene mScene1;
    private Scene mScene2;
    private PopupWindow popupWindow;



    ApiService methods;
    Callback callback;

    ProgressDialog customProgressDialog;

    String TAG = "=HomeWishListFragment=";


    EditText etCode,etName;
    Button btnAdd, btnCancel;





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = (ViewGroup) inflater.inflate(R.layout.fragment_grid, null);

       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.sub_header, new Sub_Header()).commit();
            getFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(R.animator.slide_in_left,
                            R.animator.slide_out_right, 0, 0)
                    .replace(R.id.product_frame, new Sub_Catagory_Grid()).commit();

            view.getWidth();
        }*/

        return view;
    //    return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customProgressDialog = new ProgressDialog(getActivity());
        customProgressDialog.setMessage("Please wait.....");

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        flLogout = (FrameLayout) view.findViewById(R.id.flLogout);
        flAdd = (FrameLayout) view.findViewById(R.id.flAdd);
        if(App.arrayListHomeWishListModel !=null && App.arrayListHomeWishListModel.size() > 0)
        {

            recyclerView.setAdapter(new HomeWishListAdapter(App.arrayListHomeWishListModel, this,getActivity()));
        }

        //recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2)); // like grid view 2 column
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        flLogout.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Log.i("--selected--","----Logout---");
                App.sharePrefrences.clearPrefValues();
                getActivity().finishAffinity();
                getActivity().finish();

                getActivity().startActivity(new Intent(getActivity(),ActLogin.class));
            }
        });

        flAdd.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                Log.i("--selected--","----Add start activity---");
                if(popupWindow !=null && popupWindow.isShowing()==true)
                {
                    popupWindow.dismiss();
                }
                openPopupAddApps();
            }
        });
    }

    @Override
    public void onKittenClicked(HomeWishListAdapter.HomeWishViewHolder holder, int position) {
        int kittenNumber = position;
                //(position % 6) + 1;
        Log.i("--selected--","-position---"+kittenNumber);
        WishDetailsFragment kittenDetails = WishDetailsFragment.newInstance(kittenNumber);

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            kittenDetails.setSharedElementEnterTransition(new DetailsTransition());
            kittenDetails.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            kittenDetails.setSharedElementReturnTransition(new DetailsTransition());
        }
        else {
            // write code for below api versions
        }

        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.image, "kittenImage")
                .replace(R.id.container, kittenDetails)
                .addToBackStack(null)
                .commit();
    }


















    String strCode = "",strName = "";
    //PopupWindow popupWindow;
    private void openPopupAddApps() {
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = layoutInflater.inflate(R.layout.popup_add_app, null);

        popupWindow  = new PopupWindow(popupView, ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);

        strCode = "";
        strName = "";

        etCode = (EditText) popupView.findViewById(R.id.etCode);
        etName = (EditText) popupView.findViewById(R.id.etName );

        btnAdd = (Button) popupView.findViewById(R.id.btnAdd);
        btnCancel = (Button) popupView.findViewById(R.id.btnCancel);

        etCode.requestFocus();

        popupWindow.showAtLocation(popupView, Gravity.CENTER, 5, 5);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.update();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strCode = etCode.getText().toString().trim();
                strName = etName.getText().toString().trim();

                if(strCode.length() > 2 && strName.length() > 2)
                {
                    // set api call here
                    popupWindow.dismiss();
                    addAppToList(strCode,strName);
                }
                else
                {
                    etCode.requestFocus();
                    App.showSnackBar(recyclerView,"Please fill app details");
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





    private void addAppToList(String strCode ,String strName)
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
                    Toast.makeText(getActivity(),"App added succesfully",Toast.LENGTH_LONG).show();

                    getActivity().finishAffinity();
                    getActivity().finish();
                    getActivity().startActivity(new Intent(getActivity(),ActHomeList.class));
                }
                else
                {
                    Toast.makeText(getActivity(),"No App added fail try again..!",Toast.LENGTH_LONG).show();
                }
                customProgressDialog.dismiss();
            }

            @SuppressLint("LongLogTag")
            @Override
            public void failure(RetrofitError retrofitError) {
                Log.v(TAG, "failure---1--");
                Log.e("-Error-","--failure--"+retrofitError.getMessage());
                Toast.makeText(getActivity(),"Something went wrong please try after some time latter...!",Toast.LENGTH_LONG).show();
                customProgressDialog.dismiss();
            }
        };
        methods.addAppToList(strCode,strName, callback);
        Log.v(TAG, "Api cal---1--");
    }



























}
