package com.dreslab.www.drethan_newapp.mainfragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.dreslab.www.drethan_newapp.R;


/**
 * Created by Michael on 7/26/2016.
 */
public class Intro_paragraph extends Fragment implements View.OnClickListener {
    ViewFlipper viewFlipper;
    View v;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment1, container, false);
       // viewFlipper = (ViewFlipper) v.findViewById(R.id.viewFlipper);
       // viewFlipper.setOnClickListener(this);
        return v;
    }
    @Override
    public void onClick(View v) {
        //viewFlipper.startFlipping();
        //viewFlipper.setFlipInterval(3000);
    }
}
