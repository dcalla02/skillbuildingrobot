package com.dreslab.www.drethan_newapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
/**
 * Created by DanCallahan on 4/11/17.
 */

public class studentdisplay extends AppCompatActivity{

    ImageView img_plate;
    ImageView img_fork;
    ImageView img_knife;
    ImageView img_spoon;

    Button btn_plate;
    Button btn_fork;
    Button btn_spoon;
    Button btn_knife;


    protected void onCreate(Bundle savedInstanceBundle){

        img_plate = (ImageView) findViewById(R.id.plate);
        img_fork = (ImageView) findViewById(R.id.fork);
        img_knife = (ImageView) findViewById(R.id.knife);
        img_spoon = (ImageView) findViewById(R.id.spoon);

        btn_plate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                img_plate.setVisibility(View.INVISIBLE);
            }


        });

    }




}



