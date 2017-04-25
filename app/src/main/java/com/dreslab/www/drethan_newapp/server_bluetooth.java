package com.dreslab.www.drethan_newapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class server_bluetooth extends Activity implements View.OnClickListener {
    //constants declaration
    Button b1,b2,b3,b4;
    ListView lv;
    LinearLayout bluetooth_connect_layout, main_nav, teacher_display;
    ImageView bluetooth_indicate;
    TextView textStatus;
    GridView gridview;
    ImageButton home_back;

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

    private UUID MY_UUID;
    private String myName;
    private BluetoothAdapter BA;
    private ImageAdapter imageAdapter;
    private ConnectedThread myThreadConnected;
    private Set<BluetoothDevice>pairedDevices;
    private static final String TAG = "DR_ETHAN_DEBUG_TAG";
    private static final int DISCOVER_DURATION = 300;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.server_bluetooth);

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);

        counter = 0;

        img_plate = (ImageView) findViewById(R.id.plate);
        img_fork = (ImageView) findViewById(R.id.fork);
        img_knife = (ImageView) findViewById(R.id.knife);
        img_spoon = (ImageView) findViewById(R.id.spoon);

        text = (Button) findViewById(R.id.add_text);
        audio = (Button) findViewById(R.id.play_audio);
        flash = (Button) findViewById(R.id.flash_image);
        show = (Button) findViewById(R.id.show_image);
        next = (Button) findViewById(R.id.next_step);

       // bluetooth_connect_layout =  (LinearLayout)  findViewById(R.id.bluetooth_connect_layout);
        //main_nav = (LinearLayout) findViewById(R.id.main_nav);
        //main_nav.setVisibility(View.GONE);
        teacher_display =(LinearLayout) findViewById(R.id.teacher_display);
        teacher_display.setVisibility(View.GONE);

        textStatus = (TextView) findViewById(R.id.textStatus);
        lv =         (ListView) findViewById(R.id.listView);
        bluetooth_indicate = (ImageView) findViewById(R.id.bluetooth_indicator_server);
        home_back = (ImageButton) findViewById(R.id.home_back_server);

        gridview = (GridView) findViewById(R.id.gridview);
        imageAdapter = new ImageAdapter(getApplicationContext());

        gridview.setAdapter(imageAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
                if(myThreadConnected!=null){
                    String numberAsString = Integer.toString(imageAdapter.get_address(position));
                    byte[] bytesToSend = numberAsString.getBytes();
                    myThreadConnected.write(bytesToSend);
                }
            }
        });


        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
            Toast.makeText(this,
                    "FEATURE_BLUETOOTH NOT support",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        BA = BluetoothAdapter.getDefaultAdapter();
        //generate UUID on web: http://www.famkruithof.net/uuid/uuidgen
        MY_UUID = UUID.fromString("1efb0fa0-d424-11e6-9598-0800200c9a66");
        myName = MY_UUID.toString();
    }


    @Override
    public void onClick(View view) {
        String output;
        byte[] bytesToSend;


        switch (view.getId()){
            case R.id.add_text:



                break;
            case R.id.play_audio:
                /*audio*/
                output = "audio";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                break;
            case R.id.flash_image:
                /*flash*/
                output = "flash";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                break;
            case R.id.show_image:
                /*show image*/
                output = "show";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                break;
            case R.id.next_step:
                /*next step*/
                output = "next";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                break;
        }
    }
    public void on(View v) {
        if (!BA.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 0);
            Toast.makeText(getApplicationContext(), "Turned on", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Already on", Toast.LENGTH_LONG).show();
        }

    }

    //Turns bluetoothclient_fragment off
    public void off(View v) {
        //myThreadConnected.cancel();
        BA.disable();
        Toast.makeText(getApplicationContext(), "Turned off", Toast.LENGTH_LONG).show();
    }

    public void visible(View v) {
        if (BA == null) {//check if bluetoothclient_fragment is supported
            Toast.makeText(this, "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
        } else {//make it visible
            enableBluetooth(v);
        }
    }

    public void enableBluetooth(View v) {
        Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        getVisible.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, DISCOVER_DURATION);
        startActivityForResult(getVisible, 1);
    }

    public void connect(View v){
        BluetoothServer bluetoothServer = new BluetoothServer();
        bluetoothServer.start();
    }

    public void list(View v) {
        pairedDevices = BA.getBondedDevices();

        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices) list.add(bt.getName());
        Toast.makeText(getApplicationContext(), "Showing Paired Devices", Toast.LENGTH_SHORT).show();

        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);

        lv.setAdapter(adapter);
    }
    private class BluetoothServer extends Thread {

        private BluetoothServerSocket mmServerSocket = null;

        public BluetoothServer() {
            try {
                // MY_UUID is the app's UUID string, also used by the client code.
                mmServerSocket  = BA.listenUsingRfcommWithServiceRecord(myName, MY_UUID);
                textStatus.setText("Waiting\n"
                        + "bluetoothServerSocket :\n"
                        + mmServerSocket);
            } catch (IOException e) {
                Log.e(TAG, "Socket's listen() method failed", e);
            }
            //mmServerSocket = tmp;
        }

        @Override
        public void run() {
            BluetoothSocket socket = null;
            // Keep listening until exception occurs or a socket is returned.
            if  (mmServerSocket != null) {
                try {
                    socket = mmServerSocket.accept();
                    BluetoothDevice remoteDevice = socket.getRemoteDevice();

                    final String strConnected = "Connected:\n" +
                            remoteDevice.getName() + "\n" +
                            remoteDevice.getAddress();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textStatus.setText(strConnected);
                            bluetooth_connect_layout.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),"CONNECTED",
                                    Toast.LENGTH_SHORT).show();
                            main_nav.setVisibility(View.VISIBLE);
                            teacher_display.setVisibility(View.VISIBLE);
                        }
                    });
                    manageMyConnectedSocket(socket);
                } catch (IOException e) {
                    Log.e(TAG, "Socket's accept() method failed", e);
                    final String eMessage = e.getMessage();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            textStatus.setText("something wrong: \n" + eMessage);
                        }
                    });

                    //break;
                }
            }
                else{
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            textStatus.setText("bluetoothServerSocket == null");
                        }});
                }
        }
    }

    private void manageMyConnectedSocket(BluetoothSocket socket) {

        myThreadConnected = new ConnectedThread(socket);
        myThreadConnected.start();
    }

    //connected
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        private final BluetoothSocket mmSocket;
        private byte[] mmBuffer; // mmBuffer store for the stream

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            mmSocket = socket;

            // Get the input and output streams; using temp objects because
            // member streams are final.
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();

            } catch (IOException e) {
                Log.e(TAG, "Error occurred when creating I/O stream", e);
            }
            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        @Override
        public void run() {
            mmBuffer = new byte[1024];
            int numBytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs.
            while (true) {
                try {
                    // Read from the InputStream.
                    numBytes = mmInStream.read(mmBuffer);
                    String strReceived = new String(mmBuffer, 0, numBytes);
                    final String msgReceived = String.valueOf(numBytes) +
                            " bytes received:\n"
                            + strReceived;

                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            textStatus.setText(msgReceived);
                        }});

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    Log.d(TAG, "Input stream was disconnected", e);
                    final String msgConnectionLost = "Connection lost:\n"
                            + e.getMessage();
                    runOnUiThread(new Runnable(){

                        @Override
                        public void run() {
                            textStatus.setText(msgConnectionLost);
                            bluetooth_indicate.setImageResource(R.drawable.bluetooth_notconnected);

                        }});
                    break;
                }
            }
        }
        // Call this from the main activity to send data to the remote device.
        //sending means
        public void write(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
                mmOutStream.flush();

            } catch (IOException e) {
                Log.e(TAG, "Error occurred when sending data", e);

            }
        }

        // Closes the connect socket and causes the thread to finish.
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "Could not close the connect socket", e);
            }
        }
    }
    public void home(View v){
        startActivity(new Intent(this, welcome_page.class));
    }
}
