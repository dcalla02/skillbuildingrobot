package com.dreslab.www.drethan_newapp.subFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreslab.www.drethan_newapp.R;


public class otherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment2, container, false);
        //TextView txt = (TextView) v.findViewById(R.id.pliers);
       // txt.setSelected(true);
        // Inflate the activated for this fragment
        return v;

    }

}