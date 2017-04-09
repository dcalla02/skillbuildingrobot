package com.dreslab.www.drethan_newapp.subFragments.bluetooth;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dreslab.www.drethan_newapp.R;
import com.dreslab.www.drethan_newapp.client_bluetooth;

public class bluetoothClient_fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bluetoothclient_fragment, container, false);
        Button button = (Button) v.findViewById(R.id.client_connect);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(getContext(), client_bluetooth.class));
            }
        });
        return v;

    }

}