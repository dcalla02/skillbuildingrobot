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

import com.dreslab.www.drethan_newapp.R;

/**
 * Created by DanCallahan on 4/11/17.
 */

public class studentdisplay extends AppCompatActivity implements View.OnClickListener{

    ImageView img_plate;
    ImageView img_fork;
    ImageView img_knife;
    ImageView img_spoon;

    Button btn_plate;
    Button btn_fork;
    Button btn_spoon;
    Button btn_knife;


    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentdisplaynew);

        img_plate = (ImageView) findViewById(R.id.plate);
        img_fork = (ImageView) findViewById(R.id.fork);
        img_knife = (ImageView) findViewById(R.id.knife);
        img_spoon = (ImageView) findViewById(R.id.spoon);

        btn_plate = (Button) findViewById(R.id.btnplate);
        btn_fork = (Button) findViewById(R.id.btnfork);
        btn_knife = (Button) findViewById(R.id.btnknife);
        btn_spoon = (Button) findViewById(R.id.btnspoon);

        btn_plate.setOnClickListener(this);
        btn_fork.setOnClickListener(this);
        btn_knife.setOnClickListener(this);
        btn_spoon.setOnClickListener(this);


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnplate:
                if (img_plate.getVisibility() == img_plate.INVISIBLE){
                    img_plate.setVisibility(img_plate.VISIBLE);
                }
                else {
                    img_plate.setVisibility(img_plate.INVISIBLE);
                }
                break;

                case R.id.btnfork:
                if (img_fork.getVisibility() == img_fork.INVISIBLE){
                    img_fork.setVisibility(img_fork.VISIBLE);
                }
                else {
                    img_fork.setVisibility(img_fork.INVISIBLE);
                }
                break;

            case R.id.btnknife:
                if (img_knife.getVisibility() == img_knife.INVISIBLE){
                    img_knife.setVisibility(img_knife.VISIBLE);
                }
                else {
                    img_knife.setVisibility(img_knife.INVISIBLE);
                }
                break;

            case R.id.btnspoon:
                if (img_spoon.getVisibility() == img_spoon.INVISIBLE){
                    img_spoon.setVisibility(img_spoon.VISIBLE);
                }

                else {
                    img_spoon.setVisibility(img_spoon.INVISIBLE);
                }
                break;


        }
    }
}



