package com.dreslab.www.drethan_newapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Michael on 1/12/2017.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }
    public int get_address(int position){
        return mThumbIds[position];
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(600, 600));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            //add image by int ID--LIKE R.drawable.name
            R.drawable.cc1, R.drawable.cc2,
            R.drawable.cc3, R.drawable.cc4,
            R.drawable.cc5, R.drawable.cc6,
            R.drawable.cc7, R.drawable.cc8,
            R.drawable.client_r1_are_you_ready, R.drawable.client_r2_goodjob,
            R.drawable.client_r3_try_again, R.drawable.client_r4_try_again2,
            R.drawable.client_r5_done,R.drawable.cc9
    };
}