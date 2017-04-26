package com.dreslab.www.drethan_newapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.TimeUnit;

/**
 * Created by DanCallahan on 4/25/17.
 */

public class teacherctrl extends Activity implements View.OnClickListener {

    ImageView img_plate;
    ImageView img_fork;
    ImageView img_knife;
    ImageView img_spoon;

    Button text;
    Button audio;
    Button flash;
    Button show;
    Button next;

    String hints[]={"Place the plate in the middle of the placemat.", "Place the fork to the left of plate.", "Place the knife to the right of the plate.", "Place the spoon to the right of the knife."};
    String instructions[]={"Place the plate.", "Place the fork.", "Place the knife.", "Place the spoon."};
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacherctrl);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_text:
                flash_image((ImageView) findViewById(R.id.plate));

                break;
            case R.id.play_audio:
                /*audio*/

                break;
            case R.id.flash_image:
                /*flash*/
                break;
            case R.id.show_image:
                /*show image*/
                break;
            case R.id.next_step:
                /*next step*/
                break;

        }
    }


    private void flash_image(ImageView v)  {


    }
}

