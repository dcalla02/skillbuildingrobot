package com.dreslab.www.drethan_newapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.SystemClock;
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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class server_bluetooth extends Activity implements View.OnClickListener {
    //constants declaration
    Button b1,b2,b3,b4;
    ListView lv;
    LinearLayout bluetooth_connect_layout, l2,  main_nav, teacher_display;
    ImageView bluetooth_indicate;
    TextView textStatus, instruction, step;
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
    String steps[]={"Step 1:", "Step 2:", "Step 3:", "Step 4:"};
    int counter;

    private UUID MY_UUID;
    OutputStreamWriter outputStreamWriter;
    private String myName;
    private BluetoothAdapter BA;
    private ImageAdapter imageAdapter;
    private ConnectedThread myThreadConnected;
    private Set<BluetoothDevice>pairedDevices;
    private static final String TAG = "DR_ETHAN_DEBUG_TAG";
    private static final int DISCOVER_DURATION = 300;

    private File file;



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


        file = new File("EventLogger.txt");

        // creates the file
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }


        instruction = (TextView) findViewById(R.id.instruction);
        step = (TextView) findViewById(R.id.step_number);

        instruction.setText(instructions[counter]);
        step.setText(steps[counter]);

        text = (Button) findViewById(R.id.add_text);
        audio = (Button) findViewById(R.id.play_audio);
        flash = (Button) findViewById(R.id.flash_image);
        show = (Button) findViewById(R.id.show_image);
        next = (Button) findViewById(R.id.next_step);

        text.setOnClickListener(this);
        audio.setOnClickListener(this);
        flash.setOnClickListener(this);
        show.setOnClickListener(this);
        next.setOnClickListener(this);


        bluetooth_connect_layout =  (LinearLayout)  findViewById(R.id.layout1);
        l2 =  (LinearLayout)  findViewById(R.id.layout2);
        l2.setVisibility(View.GONE);
        //main_nav = (LinearLayout) findViewById(R.id.main_nav);
        //main_nav.setVisibility(View.GONE);
        teacher_display =(LinearLayout) findViewById(R.id.teacher_display);
        teacher_display.setVisibility(View.GONE);

        textStatus = (TextView) findViewById(R.id.textStatus);
        lv =         (ListView) findViewById(R.id.listView);
        bluetooth_indicate = (ImageView) findViewById(R.id.bluetooth_indicator_server);
        home_back = (ImageButton) findViewById(R.id.home_back_server);

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH)){
            Toast.makeText(this,
                    "FEATURE_BLUETOOTH NOT support",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        BA = BluetoothAdapter.getDefaultAdapter();
        //generate UUID on web: http://www.famkruithof.net/uuid/uuidgen
        MY_UUID = UUID.fromString("7d5c8850-34e9-11e7-9598-0800200c9a66");
        myName = MY_UUID.toString();
    }
    private void writeToFile(String input) {

        try {
            // creates a FileWriter Object
            FileWriter writer = new FileWriter(file, true);

            // Writes the content to the file
            writer.write(input);
            writer.flush();
            writer.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    private void flash(int item) {
        switch (item){
            case 0:
                for (int i = 0; i < 3; i++) {
//                    img_plate.setVisibility(View.VISIBLE);
//                    Thread.sleep(1000);
//                    img_plate.setVisibility(View.INVISIBLE);
////                    System.out.println("Plate shown");
////                    Thread.sleep(5000);
////                    img_fork.setVisibility(View.INVISIBLE);
////                    System.out.println("Plate hidden");
////                    Thread.sleep(3000);
//                }

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

    private String parse_log(Date now, int step, String button){
        String log = "";
        // Create an instance of SimpleDateFormat used for formatting
    // the string representation of date (month/day/year)
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


    // Using DateFormat format method we can create a string
    // representation of a date with the defined format.
        String reportDate = df.format(now);

        log = "Timestamp: " + reportDate + "; Step: " + Integer.toString(step) + "; Hint Used: " + button + "\n";

        return log;
    }

    @Override
    public void onClick(View view) {
        System.out.println("onClick event called");
        String output, log;
        byte[] bytesToSend;
        // Get the date today using Calendar object.
        Date today = Calendar.getInstance().getTime();


        switch (view.getId()){
            case R.id.add_text:
                System.out.println("Add Text Button pressed");
                output = "text";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                instruction.setText(hints[counter]);

                log = parse_log(today, counter, "text hint");
                writeToFile(log);

                break;
            case R.id.play_audio:
                /*audio*/
                output = "audio";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                log = parse_log(today, counter, "play audio hint");
                writeToFile(log);

                break;
            case R.id.flash_image:
                /*flash*/
                try {
                    flash(counter);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                output = "flash";
//                bytesToSend = output.getBytes();
//                myThreadConnected.write(bytesToSend);
                log = parse_log(today, counter, "flash image hint");
                writeToFile(log);
                break;
            case R.id.show_image:
                show(counter);
                /*show image*/
                output = "show";
                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);
                log = parse_log(today, counter, "show image hint");
                writeToFile(log);

                break;
            case R.id.next_step:
                /*next step*/
                log = parse_log(today, counter, "step completed");
                writeToFile(log);

                output = "next";
                counter += 1;

                if (counter > 3) {
                    output = "END";
                    bytesToSend = output.getBytes();
                    myThreadConnected.write(bytesToSend);
                    counter = 0;
                    //pop up, process file
                    startActivity(new Intent(this, welcome_page.class));
                }

                instruction.setText(instructions[counter]);
                step.setText(steps[counter]);

                bytesToSend = output.getBytes();
                myThreadConnected.write(bytesToSend);

                //Done

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
                            //main_nav.setVisibility(View.VISIBLE);
                            teacher_display.setVisibility(View.VISIBLE);
                            l2.setVisibility(View.VISIBLE);

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
