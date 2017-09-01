package com.appslist;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

//import com.bumptech.glide.Glide;
import com.com.response.AppsModel;
import com.squareup.picasso.Picasso;


/**
 * Display details for a given kitten
 *
 * @author bherbst
 */
public class WishDetailsFragment extends Fragment {
    private static final String ARG_KITTEN_NUMBER = "argKittenNumber";

    Activity mActivity;

    ImageView image;
    TextView tvDetail,tvTime;
    AppsModel homeWishListModel;

    /**
     * Create a new DetailsFragment
     * @param kittenNumber The number (between 1 and 6) of the kitten to display
     */
    public static WishDetailsFragment newInstance(@IntRange(from = 1, to = 6) int kittenNumber) {
        Bundle args = new Bundle();
        args.putInt(ARG_KITTEN_NUMBER, kittenNumber);

        WishDetailsFragment fragment = new WishDetailsFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity  = getActivity();
        return inflater.inflate(R.layout.act_detail, container, false); //details_fragment
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        image = (ImageView) view.findViewById(R.id.image);

        tvDetail = (TextView) view.findViewById(R.id.tvDetail);
        tvTime = (TextView) view.findViewById(R.id.tvTime);

        tvDetail.setTypeface(App.getFontRoboto());
        tvTime.setTypeface(App.getFontRoboto());

        CollapsingToolbarLayout mCollapsingToolbarLayout = (CollapsingToolbarLayout) view.findViewById(R.id.collapsing_toolbar);

        //makeCollapsingToolbarLayoutLooksGood(collapsing_toolbar);

        mCollapsingToolbarLayout.setTitle("");
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        mCollapsingToolbarLayout.setExpandedTitleTypeface(App.getFontShortStack());
        mCollapsingToolbarLayout.setCollapsedTitleTypeface(App.getFontShortStack());


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(mActivity.getResources().getString(R.string.app_name));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);


        //mActionBar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_action_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked

                Log.e("===","sdfsd");
                getFragmentManager().popBackStack();
            }
        });

        Bundle args = getArguments();
        int kittenNumber = args.containsKey(ARG_KITTEN_NUMBER) ? args.getInt(ARG_KITTEN_NUMBER) : 1;

if(App.arrayListHomeWishListModel !=null && App.arrayListHomeWishListModel.size() >= kittenNumber  )
{
    homeWishListModel = App.arrayListHomeWishListModel.get(kittenNumber);
    Log.i("==111=Details==","---NO-Null model----");
    }
    else
    {
        homeWishListModel = null;
        Log.i("=111==Details==","----Null model----");
    }



    if(homeWishListModel !=null) {
        if (homeWishListModel.getName() != null) {
            mCollapsingToolbarLayout.setTitle(homeWishListModel.getName());
        }
        if (homeWishListModel.getImage() != null) {
            Picasso.with(getActivity()).load(homeWishListModel.getImage()).placeholder(R.drawable.app_icon_a).into(image);
        }
        if (homeWishListModel.getName() != null) {
            tvTime.setText(homeWishListModel.getName());
        }
        if (homeWishListModel.getCode() != null) {
            tvDetail.setText(homeWishListModel.getCode());
        }
        Log.i("===Details==","---NO-Null model----");
    }
        else
    {
        Log.i("===Details==","----Null model----");
    }
    }

}
