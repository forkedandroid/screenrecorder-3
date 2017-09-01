package com.appslist;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Adapts Views containing kittens to RecyclerView cells
 *
 * @author bherbst
 */
public class KittenGridAdapter extends RecyclerView.Adapter<KittenGridAdapter.KittenViewHolder> {
    private final int mSize;
    private final KittenClickListener mListener;

    /**
     * Constructor
     * @param size The number of kittens to show
     * @param listener A listener for kitten click events
     */
    public KittenGridAdapter(int size, KittenClickListener listener) {
        mSize = size;
        mListener = listener;
    }

    @Override
    public KittenGridAdapter.KittenViewHolder onCreateViewHolder(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        //View cell = inflater.inflate(R.layout.grid_item, container, false);
        View cell = inflater.inflate(R.layout.m_raw_cardview, container, false);
        //View cell = inflater.inflate(R.layout.m_raw_large_cardview, container, false);
        Log.i("--create view--","-position-22--"+position);
        return new KittenGridAdapter.KittenViewHolder(cell,container.getContext(),position);
    }

    @Override
    public void onBindViewHolder(final KittenGridAdapter.KittenViewHolder viewHolder, final int position) {
        switch (position % 6) {
            case 0:
                viewHolder.image.setImageResource(R.drawable.placekitten_1);
                break;
            case 1:
                viewHolder.image.setImageResource(R.drawable.placekitten_2);
                break;
            case 2:
                viewHolder.image.setImageResource(R.drawable.placekitten_3);
                break;
            case 3:
                viewHolder.image.setImageResource(R.drawable.placekitten_4);
                break;
            case 4:
                viewHolder.image.setImageResource(R.drawable.placekitten_5);
                break;
            case 5:
                viewHolder.image.setImageResource(R.drawable.placekitten_6);
                break;
        }

        // It is important that each shared element in the source screen has a unique transition name.
        // For example, we can't just give all the images in our grid the transition name "kittenImage"
        // because then we would have conflicting transition names.
        // By appending "_image" to the position, we can support having multiple shared elements in each
        // grid cell in the future.
        ViewCompat.setTransitionName(viewHolder.image, String.valueOf(position) + "_image");

        viewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onKittenClicked(viewHolder, position);
            }
        });


        viewHolder.person_name.setText("Some data %s"+position);



    }

    @Override
    public int getItemCount() {
        return mSize;
    }




    public class KittenViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        CardView cv;


        TextView person_name;
        public KittenViewHolder(View itemView, Context context,int position) {
            super(itemView);
            //image = (ImageView) itemView.findViewById(R.id.image);
            Log.i("--create view--","-position---"+position);

            image = (ImageView) itemView.findViewById(R.id.person_photo);
            cv = (CardView)itemView.findViewById(R.id.cv);
            person_name = (TextView)itemView.findViewById(R.id.person_name);
        }


    }





}
