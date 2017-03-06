package com.intuitve;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intuitve.Model.QuizResult;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Bhadresh Chavada on 05-03-2017.
 */

public class GSRActivity extends Activity implements View.OnClickListener{

    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1 ;
    Handler bluetoothIn;

    final int handlerState = 0;                         //used to identify handler message
    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();
    static ArrayList<QuizResult> quizResult = new ArrayList<>();
    private GSRActivity.ConnectedThread mConnectedThread;

    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private static String address;
    int i = 1,temp=1;
    ImageView a, b, c, d;
    String GSR_2,GSR_5,GSR_10,GSR_12,GSR_15;
    TextView mTextField, headertxt;
    String Activity;
    int ClickedButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_images);

        bluetoothIn = new Handler();

        btAdapter = BluetoothAdapter.getDefaultAdapter();

        if (ContextCompat.checkSelfPermission(GSRActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(GSRActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(GSRActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {

            checkBTState();

        }

    }
        private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }

    @Override
    public void onResume() {
        super.onResume();

        Intent intent = getIntent();
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);
        Toast.makeText(getBaseContext(), address, Toast.LENGTH_LONG).show();

        BluetoothDevice device = btAdapter.getRemoteDevice(address);

        try {
            btSocket = createBluetoothSocket(device);
        } catch (IOException e) {
            Toast.makeText(getBaseContext(), "Socket creation failed", Toast.LENGTH_LONG).show();
        }

        try {
            btSocket.connect();
        } catch (IOException e) {
            try {
                btSocket.close();
            } catch (IOException e2) {

            }
        }
        mConnectedThread = new ConnectedThread(btSocket);
        mConnectedThread.start();
        mConnectedThread.write("x");
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            btSocket.close();
        } catch (IOException e2) {

        }
    }


    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            init();
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }


    void init() {

        a = (ImageView) findViewById(R.id.a);
        b = (ImageView) findViewById(R.id.b);
        c = (ImageView) findViewById(R.id.c);
        d = (ImageView) findViewById(R.id.d);

        headertxt = (TextView) findViewById(R.id.header);

        mTextField = (TextView) findViewById(R.id.layout_image_counter);
        Activity = getIntent().getStringExtra("Activity");

        if (Activity.equals("currencies")) {
            headertxt.setText("Forex and Commodities");
        } else if (Activity.equals("stocks")) {
            headertxt.setText("Stock and Indices");
        } else if (Activity.equals("commodities")) {
            headertxt.setText("Forex and Commodities");
            headertxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        } else if (Activity.equals("miscellaneous")) {
            headertxt.setText("Miscellaneous");
        }
        setGrayImage();

    }

    void setGrayImage() {

        mTextField.setVisibility(View.VISIBLE);
        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);

        setImageResource();


        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                mTextField.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                mTextField.setVisibility(View.INVISIBLE);
                a.setEnabled(true);
                b.setEnabled(true);
                c.setEnabled(true);
                d.setEnabled(true);
            }
        }.start();


//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//
//            }
//        }, 10000);


    }


    void setImageResource() {
        if (Activity.equals("currencies")) {

            if (i <= 31) {
                a.setImageResource(getResources().getIdentifier("ic_forex_" + i, "drawable", "com.intuitve"));
                i++;
                b.setImageResource(getResources().getIdentifier("ic_forex_" + i, "drawable", "com.intuitve"));
                i++;
                c.setImageResource(getResources().getIdentifier("ic_forex_" + i, "drawable", "com.intuitve"));
                i++;
                d.setImageResource(getResources().getIdentifier("ic_forex_" + i, "drawable", "com.intuitve"));
                i++;
            } else {

                saveExcelFile(GSRActivity.this, "Intuitve_currencies.xls");
                onBackPressed();
            }

        } else if (Activity.equals("stocks")) {


            if (i <= 55) {
                a.setImageResource(getResources().getIdentifier("ic_stocks" + i, "drawable", "com.intuitve"));
                i++;
                b.setImageResource(getResources().getIdentifier("ic_stocks" + i, "drawable", "com.intuitve"));
                i++;
                c.setImageResource(getResources().getIdentifier("ic_stocks" + i, "drawable", "com.intuitve"));
                i++;
                d.setImageResource(getResources().getIdentifier("ic_stocks" + i, "drawable", "com.intuitve"));
                i++;
            } else {

                saveExcelFile(GSRActivity.this, "Intuitve_stocks.xls");
                onBackPressed();
            }

        } else if (Activity.equals("commodities")) {


            if (i <= 11) {
                a.setImageResource(getResources().getIdentifier("ic_commodities_" + i, "drawable", "com.intuitve"));
                i++;
                b.setImageResource(getResources().getIdentifier("ic_commodities_" + i, "drawable", "com.intuitve"));
                i++;
                c.setImageResource(getResources().getIdentifier("ic_commodities_" + i, "drawable", "com.intuitve"));
                i++;
                d.setImageResource(getResources().getIdentifier("ic_commodities_" + i, "drawable", "com.intuitve"));
                i++;
            } else {

                saveExcelFile(GSRActivity.this, "Intuitve_commodities.xls");
                onBackPressed();
            }

        } else if (Activity.equals("miscellaneous")) {


            if (i <= 63) {
                a.setImageResource(getResources().getIdentifier("ic_miscellaneous_" + i, "drawable", "com.intuitve"));
                i++;
                b.setImageResource(getResources().getIdentifier("ic_miscellaneous_" + i, "drawable", "com.intuitve"));
                i++;
                c.setImageResource(getResources().getIdentifier("ic_miscellaneous_" + i, "drawable", "com.intuitve"));
                i++;
                d.setImageResource(getResources().getIdentifier("ic_miscellaneous_" + i, "drawable", "com.intuitve"));
                i++;
            } else {

                saveExcelFile(GSRActivity.this, "Intuitve_miscellaneous.xls");
                onBackPressed();
            }

        }



        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.a) {
            ClickedButton = 1;
            Random();
        } else if (v.getId() == R.id.b) {
            ClickedButton = 2;
            Random();
        } else if (v.getId() == R.id.c) {
            ClickedButton = 3;
            Random();
        } else if (v.getId() == R.id.d) {
            ClickedButton = 4;
            Random();
        }

    }

    void Random() {

//        int random = 0;
        Random rand = new Random();
        int random = rand.nextInt((4 - 1) + 1) + 1;

        final Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.image_dialog);
        ImageView imageView = (ImageView) dialog.findViewById(R.id.dialog_images);


        if (random == 1) {

            imageView.setImageDrawable(a.getDrawable());
//            imageView.setImageResource(a.getResources());
        } else if (random == 2) {
            imageView.setImageDrawable(b.getDrawable());
//            imageView.setImageResource(R.drawable.b);
        } else if (random == 3) {
            imageView.setImageDrawable(c.getDrawable());
//            imageView.setImageResource(R.drawable.c);
        } else if (random == 4) {
            imageView.setImageDrawable(d.getDrawable());
//            imageView.setImageDrawable(d.getDrawable());
//            imageView.setImageResource(R.drawable.d);
        }

        QuizResult quizResultmodel = new QuizResult();
        quizResultmodel.setSelectedPos(ClickedButton);
        quizResultmodel.setRandomPos(random);
        quizResultmodel.setGSR2(GSR_2);
        quizResultmodel.setGSR5(GSR_5);
        quizResultmodel.setGAR10(GSR_10);
        quizResultmodel.setGSR12(GSR_12);
        quizResultmodel.setGSR15(GSR_15);

        if (random == ClickedButton) {
//            Toast.makeText(this, "True", Toast.LENGTH_SHORT).show();
            quizResultmodel.setResult(true);

        } else {
//            Toast.makeText(this, "False", Toast.LENGTH_SHORT).show();
            quizResultmodel.setResult(false);
        }

        quizResult.add(quizResultmodel);

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
//                setImageResource();

                setGrayImage();
            }
        }, 10000);

    }


    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;


        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {

                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }


        public void run() {
            byte[] buffer = new byte[256];
            int bytes;


            while (true) {
                try {
                    bytes = mmInStream.read(buffer);
                    final String readMessage = new String(buffer, 0, bytes);

                    Log.i("message :", readMessage);

                    runOnUiThread(new Thread(new Runnable() {
                        public void run() {
//                            txtreceive.setText(txtreceive.getText() + "\n\nAnother message received :" + readMessage);

                            if(temp ==1){
                                GSR_2 = readMessage;
                                temp =2;
                            }else if(temp ==2){
                                GSR_5 = readMessage;
                                temp = 3;
                            }else if(temp ==3){
                                GSR_10 = readMessage;
                                temp =4;
                            }else if(temp == 4){
                                GSR_12 = readMessage;
                                temp = 5;
                            }else if(temp == 5){
                                GSR_15 = readMessage;
                                temp =1;
                            }

                        }
                    }));


                    bluetoothIn.obtainMessage(handlerState, bytes, -1, readMessage).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }


        public void write(String input) {
            byte[] msgBuffer = input.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(getBaseContext(), "Connection Failure", Toast.LENGTH_LONG).show();
                finish();

            }

        }
    }


    private static boolean saveExcelFile(Context context, String fileName) {

        // check if available and not read only
        if (!isExternalStorageAvailable() || isExternalStorageReadOnly()) {
            Log.e("TAG", "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        Cell c = null;

        //Cell style for header row
        CellStyle cs = wb.createCellStyle();
        cs.setFillForegroundColor(HSSFColor.LIME.index);
        cs.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet("myOrder");

        // Generate column headings

        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("0");
        c.setCellStyle(cs);

        c = row.createCell(1);
        c.setCellValue("5");
        c.setCellStyle(cs);

        c = row.createCell(2);
        c.setCellValue("choice");
        c.setCellStyle(cs);


        c = row.createCell(3);
        c.setCellValue("10");
        c.setCellStyle(cs);


        c = row.createCell(4);
        c.setCellValue("10");
        c.setCellStyle(cs);


        c = row.createCell(5);
        c.setCellValue("12");
        c.setCellStyle(cs);


        c = row.createCell(6);
        c.setCellValue("15");
        c.setCellStyle(cs);


        c = row.createCell(7);
        c.setCellValue("random");
        c.setCellStyle(cs);


        c = row.createCell(8);
        c.setCellValue("result");
        c.setCellStyle(cs);

        for (int i = 0; i < 3; i++) {

            Row row_data = sheet1.createRow(0);

            c = row_data.createCell(0);
            c.setCellValue("0.00189");

            c = row_data.createCell(1);
            c.setCellValue("0.00189");

            c = row_data.createCell(2);
            c.setCellValue("2");

            c = row_data.createCell(3);
            c.setCellValue("0.00189");

            c = row_data.createCell(4);
            c.setCellValue("0.00189");

            c = row_data.createCell(5);
            c.setCellValue("0.00189");

            c = row_data.createCell(6);
            c.setCellValue("0.00189");

            c = row_data.createCell(7);
            c.setCellValue("3");

            c = row_data.createCell(8);
            c.setCellValue("false");
        }
        sheet1.setColumnWidth(0, (15 * 500));
        sheet1.setColumnWidth(1, (15 * 500));
        sheet1.setColumnWidth(2, (15 * 500));


        // Create a path where we will place our List of objects on external storage
//        File file = new File(context.getExternalFilesDir(null), fileName);
        FileOutputStream os = null;


        File sdCard = Environment.getExternalStorageDirectory();
        File directory = new File(sdCard.getAbsolutePath() + "/intuitve");

        //create directory if not exist
        if (!directory.isDirectory()) {
            directory.mkdirs();
        }

        //file path
        File file = new File(directory, fileName);
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Toast.makeText(context, "Intuitve.xls file Create", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return success;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static boolean isExternalStorageAvailable() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    checkBTState();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}


