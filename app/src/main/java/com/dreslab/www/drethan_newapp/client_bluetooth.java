package com.dreslab.www.drethan_newapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.dreslab.www.drethan_newapp.R.id.animation;

public class client_bluetooth extends ActionBarActivity {

    ImageView img_plate;
    ImageView img_fork;
    ImageView img_knife;
    ImageView img_spoon;


    String hints[]={"Place the plate in the middle of the placemat.", "Place the fork to the left of plate.", "Place the knife to the right of the plate.", "Place the spoon to the right of the knife."};
    String instructions[]={"Place the plate.", "Place the fork.", "Place the knife.", "Place the spoon."};
    String steps[]={"Step 1:", "Step 2:", "Step 3:", "Step 4:"};

    private int counter;

    private static final int REQUEST_ENABLE_BT = 1;

    BluetoothAdapter bluetoothAdapter;

    ArrayList<BluetoothDevice> pairedDeviceArrayList;

    TextView textInfo, textStatus, instruction, step_number;
    ImageView bluetooth_indicate, picture_display;
    ListView listViewPairedDevice;
    ArrayAdapter<BluetoothDevice> pairedDeviceAdapter;
    private UUID myUUID;

    LinearLayout bluetoothpairing, studentdisplay;
    ImageButton home_back;
    //EditText inputField;
    //Button btnSend;
    //PopupWindow pwindow;
    RelativeLayout rl;

    AnimationDrawable flashplate, flashfork, flashspoon, flashknife;

    ThreadConnectBTdevice myThreadConnectBTdevice;
    ThreadConnected myThreadConnected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_bluetooth);
        textInfo = (TextView)findViewById(R.id.info);
        textStatus = (TextView)findViewById(R.id.status);
        listViewPairedDevice = (ListView)findViewById(R.id.pairedlist);

        counter = 0;

        img_plate = (ImageView) findViewById(R.id.plate);
        img_plate.setVisibility(View.INVISIBLE);
        img_fork = (ImageView) findViewById(R.id.fork);
        img_fork.setVisibility(View.INVISIBLE);
        img_knife = (ImageView) findViewById(R.id.knife);
        img_knife.setVisibility(View.INVISIBLE);
        img_spoon = (ImageView) findViewById(R.id.spoon);
        img_spoon.setVisibility(View.INVISIBLE);

        instruction = (TextView) findViewById(R.id.instruction);
        step_number = (TextView) findViewById(R.id.step_number);

        instruction.setText(instructions[counter]);
        step_number.setText(steps[counter]);

//        img_plate.setBackgroundResource(R.drawable.skillbuildingrobot_plate);
//        flashplate = (AnimationDrawable) img_plate.getBackground();
//
//        img_fork.setBackgroundResource(R.drawable.skillbuildingrobot_fork);
//        flashfork = (AnimationDrawable) img_fork.getBackground();
//
//        img_knife.setBackgroundResource(R.drawable.skillbuildingrobot_knife);
//        flashknife = (AnimationDrawable) img_knife.getBackground();
//
//        img_spoon.setBackgroundResource(R.drawable.skillbuildingrobot_spoon);
//        flashspoon = (AnimationDrawable) img_spoon.getBackground();



        bluetoothpairing = (LinearLayout)findViewById(R.id.bluetooth_pairing);
        studentdisplay = (LinearLayout)findViewById(R.id.studentdisplay);
        studentdisplay.setVisibility(View.GONE);


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
            Toast.makeText(this,
                    "FEATURE_BLUETOOTH NOT support",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        //generate UUID on web: http://www.famkruithof.net/uuid/uuidgen
        //have to match the UUID on the another device of the BT connection
        myUUID = UUID.fromString("7d5c8850-34e9-11e7-9598-0800200c9a66");

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            Toast.makeText(this,
                    "Bluetooth is not supported on this hardware platform",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        String stInfo = bluetoothAdapter.getName() + "\n" +
                bluetoothAdapter.getAddress();
        textInfo.setText(stInfo);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Turn ON BlueTooth if it is OFF
        if (!bluetoothAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }

        setup();
    }

    private void setup() {
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            pairedDeviceArrayList = new ArrayList<BluetoothDevice>();

            for (BluetoothDevice device : pairedDevices) {
                pairedDeviceArrayList.add(device);
            }

            pairedDeviceAdapter = new ArrayAdapter<BluetoothDevice>(this,
                    android.R.layout.simple_list_item_1, pairedDeviceArrayList);
            listViewPairedDevice.setAdapter(pairedDeviceAdapter);

            listViewPairedDevice.setOnItemClickListener(new OnItemClickListener(){

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    BluetoothDevice device =
                            (BluetoothDevice)parent.getItemAtPosition(position);
                    Toast.makeText(client_bluetooth.this,
                            "Name: " + device.getName() + "\n"
                                    + "Address: " + device.getAddress() + "\n"
                                    + "BondState: " + device.getBondState() + "\n"
                                    + "BluetoothClass: " + device.getBluetoothClass() + "\n"
                                    + "Class: " + device.getClass(),
                            Toast.LENGTH_LONG).show();

                    textStatus.setText("start ThreadConnectBTdevice");
                    myThreadConnectBTdevice = new ThreadConnectBTdevice(device);
                    myThreadConnectBTdevice.start();
                }});
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(myThreadConnectBTdevice!=null){
            myThreadConnectBTdevice.cancel();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==REQUEST_ENABLE_BT){
            if(resultCode == Activity.RESULT_OK){
                setup();
            }else{
                Toast.makeText(this,
                        "BlueTooth NOT enabled",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private class ThreadConnectBTdevice extends Thread {
        private BluetoothSocket bluetoothSocket = null;
        private final BluetoothDevice bluetoothDevice;


        public ThreadConnectBTdevice(BluetoothDevice device) {
            bluetoothDevice = device;

            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(myUUID);
                textStatus.setText("bluetoothSocket: \n" + bluetoothSocket);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            boolean success = false;
            try {
                bluetoothSocket.connect();
                success = true;
            } catch (IOException e) {
                e.printStackTrace();

                final String eMessage = e.getMessage();
                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        textStatus.setText("something wrong bluetoothSocket.connect(): \n" + eMessage);

                    }});

                try {
                    bluetoothSocket.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            if(success){
                //connect successful
                final String msgconnected = "connect successful:\n"
                        + "BluetoothSocket: " + bluetoothSocket + "\n"
                        + "BluetoothDevice: " + bluetoothDevice;

                runOnUiThread(new Runnable(){

                    @Override
                    public void run() {
                        textStatus.setText(msgconnected);
                        listViewPairedDevice.setVisibility(View.GONE);
                        bluetoothpairing.setVisibility(View.GONE);
                        studentdisplay.setVisibility(View.VISIBLE);
                    }});

                startThreadConnected(bluetoothSocket);

            }else{
                //fail
                startActivity(new Intent(getApplicationContext(), welcome_page.class));
                return;

            }
        }

        public void cancel() {

            Toast.makeText(getApplicationContext(),
                    "close bluetoothSocket",
                    Toast.LENGTH_LONG).show();

            try {
                bluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

    private void flash(int item) throws InterruptedException {
        switch (item){
            case 0:
                for (int i = 0; i < 3; i++) {
                    img_plate.setVisibility(View.VISIBLE);
                    Thread.sleep(500);
                    img_plate.setVisibility(View.INVISIBLE);
                    Thread.sleep(300);
                }
                break;

            case 1:
                //flashfork.start();
                break;
            case 2:
                //flashknife.start();
                break;
            case 3:
                //flashspoon.start();
                break;


        }
    }
    private void show(int item) {
        switch (item){
            case 0:
                img_plate.setVisibility(View.VISIBLE);
                break;
            case 1:
                img_fork.setVisibility(View.VISIBLE);
                break;
            case 2:
                img_knife.setVisibility(View.VISIBLE);
                break;
            case 3:
                img_spoon.setVisibility(View.VISIBLE);
                break;


        }
    }
    private void display_responds(String s){
        switch(s){
            case "text":
                //text
                instruction.setText(hints[counter]);
                System.out.println(s);
                break;
            case "audio":
                //audio
                break;
            case "flash":
                //flash
                try {
                    flash(counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            case "show":
                //show
                show(counter);
                break;
            case "next":
                //next
                show(counter);
                counter += 1;
                instruction.setText(instructions[counter]);
                step_number.setText(steps[counter]);
                break;
            case "END":
                //End of demo
                break;
        }

//        int i = Integer.parseInt(s.trim());
//        rl.setBackgroundColor(Color.WHITE);
//        picture_display.setImageResource(i);
//        switch (i) {
//            case R.drawable.client_r1_are_you_ready:
//                break;
//            case R.drawable.cc3:
//                rl.setBackgroundResource(R.drawable.gray2_color);
//                break;
//            case R.drawable.cc7:
//                rl.setBackgroundResource(R.drawable.gray2_color);
//                break;
//            case R.drawable.cc4:
//                rl.setBackgroundResource(R.drawable.gray1_color);
//                break;
//            case R.drawable.cc5:
//                rl.setBackgroundResource(R.drawable.gray1_color);
//                break;
//            case R.drawable.client_r2_goodjob:
//                rl.setBackgroundResource(R.drawable.black_color);
//                break;
//            case R.drawable.client_r3_try_again :
//                rl.setBackgroundResource(R.drawable.gray3_color);
//                break;
//            case R.drawable.client_r4_try_again2:
//               rl.setBackgroundResource(R.drawable.gray3_color);
//                break;
//            case R.drawable.client_r5_done :
//                rl.setBackgroundResource(R.drawable.black_color);
//                break;
//        }
        /*Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pwindow.dismiss();
            }
        }, 2000);*/
    }

    private void startThreadConnected(BluetoothSocket socket){
        /*inputPane.setVisibility(View.GONE);
        act_display.setVisibility(View.VISIBLE);*/
        myThreadConnected = new ThreadConnected(socket);
        myThreadConnected.start();
    }

    private class ThreadConnected extends Thread {
        private final BluetoothSocket connectedBluetoothSocket;
        private final InputStream connectedInputStream;
        private final OutputStream connectedOutputStream;

        public ThreadConnected(BluetoothSocket socket) {
            connectedBluetoothSocket = socket;
            InputStream in = null;
            OutputStream out = null;

            try {
                in = socket.getInputStream();
                out = socket.getOutputStream();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            connectedInputStream = in;
            connectedOutputStream = out;
        }

        @Override
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            while (true) {
                try {
                    bytes = connectedInputStream.read(buffer);
                    final String strReceived = new String(buffer, 0, bytes);
                    final String msgReceived = String.valueOf(bytes) +
                            " bytes received:\n"
                            + strReceived;

                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            textStatus.setText(msgReceived);
                            display_responds(strReceived);
                        }});

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                    final String msgConnectionLost = "Connection lost:\n"
                            + e.getMessage();
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            //textStatus.setText(msgConnectionLost);
                            ////photo.setImageDrawable(Drawable.createFromPath(path))
                            bluetooth_indicate.setImageResource(R.drawable.bluetooth_notconnected);
                            //
                        }});
                    break;
                }
            }
        }

        public void write(byte[] buffer) {
            try {
                connectedOutputStream.write(buffer);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                connectedBluetoothSocket.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    public void home(View v){
        startActivity(new Intent(this, welcome_page.class));
    }

}
