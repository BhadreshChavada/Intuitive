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
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intuitve.Model.QuizModel;
import com.intuitve.Model.QuizResult;
import com.intuitve.Utils.IntuitveDatabase;

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
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class QuizActivity extends Activity implements View.OnClickListener {

    ImageView a, b, c, d;
    String Activity;
    static ArrayList<QuizModel> arrayList = new ArrayList<>();
    int i = 1,temp=1;
    String GSR_2,GSR_5,GSR_10,GSR_12,GSR_15;
    TextView mTextField, headertxt;
    int ClickedButton;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 1 ;

    static ArrayList<QuizResult> quizResult = new ArrayList<>();
    private String address;

    private BluetoothAdapter btAdapter = null;
    private BluetoothSocket btSocket = null;
    private StringBuilder recDataString = new StringBuilder();

    private ConnectedThread mConnectedThread;
    Handler bluetoothIn;
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final int handlerState = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_images);

        Intent intent = getIntent();
        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        btAdapter = BluetoothAdapter.getDefaultAdapter();
        bluetoothIn = new Handler();

        if (ContextCompat.checkSelfPermission(QuizActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(QuizActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(QuizActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_WRITE_STORAGE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {
        }
        checkBTState();
        init();
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

//        a.setImageResource(R.drawable.ic_gray);
//        b.setImageResource(R.drawable.ic_gray);
//        c.setImageResource(R.drawable.ic_gray);
//        d.setImageResource(R.drawable.ic_gray);

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
//                mTextField.setText("done!");
                mTextField.setVisibility(View.INVISIBLE);
                a.setEnabled(true);
                b.setEnabled(true);
                c.setEnabled(true);
                d.setEnabled(true);
            }
        }.start();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


            }
        }, 10000);


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

                saveExcelFile(QuizActivity.this, "Intuitve_currencies.xls");
//                for (int i = 0; i < quizResult.size(); i++) {
//                    Toast.makeText(QuizActivity.this, quizResult.get(i).getSelectedPos() + "--" + quizResult.get(i).getRandomPos() + "--" + quizResult.get(i).isResult(), Toast.LENGTH_SHORT).show();
//                }
                onBackPressed();
            }

        } else if (Activity.equals("stocks")) {

//            a.setImageResource(R.drawable.ic_stocks1);
//            b.setImageResource(R.drawable.ic_stocks2);
//            c.setImageResource(R.drawable.ic_stocks3);
//            d.setImageResource(R.drawable.ic_stocks4);

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

                saveExcelFile(QuizActivity.this, "Intuitve_stocks.xls");
//                for (int i = 0; i < quizResult.size(); i++) {
//                    Toast.makeText(QuizActivity.this, quizResult.get(i).getSelectedPos() + "--" + quizResult.get(i).getRandomPos() + "--" + quizResult.get(i).isResult(), Toast.LENGTH_SHORT).show();
//                }
                onBackPressed();
            }

        } else if (Activity.equals("commodities")) {

//            a.setImageResource(R.drawable.ic_commodities_1);
//            b.setImageResource(R.drawable.ic_commodities_2);
//            c.setImageResource(R.drawable.ic_commodities_3);
//            d.setImageResource(R.drawable.ic_commodities_4);

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

                saveExcelFile(QuizActivity.this, "Intuitve_commodities.xls");
//                for (int i = 0; i < quizResult.size(); i++) {
//                    Toast.makeText(QuizActivity.this, quizResult.get(i).getSelectedPos() + "--" + quizResult.get(i).getRandomPos() + "--" + quizResult.get(i).isResult(), Toast.LENGTH_SHORT).show();
//                }
                onBackPressed();
            }

        } else if (Activity.equals("miscellaneous")) {

//            a.setImageResource(R.drawable.ic_miscellaneous_1);
//            b.setImageResource(R.drawable.ic_miscellaneous_2);
//            c.setImageResource(R.drawable.ic_miscellaneous_3);
//            d.setImageResource(R.drawable.ic_miscellaneous_4);

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

                saveExcelFile(QuizActivity.this, "Intuitve_miscellaneous.xls");
//                for (int i = 0; i < quizResult.size(); i++) {
//                    Toast.makeText(QuizActivity.this, quizResult.get(i).getSelectedPos() + "--" + quizResult.get(i).getRandomPos() + "--" + quizResult.get(i).isResult(), Toast.LENGTH_SHORT).show();
//                }
                onBackPressed();
            }

        }

//        if (Activity.equals("currencies")) {
//            arrayList = new IntuitveDatabase(QuizActivity.this).GetImage("2");
//        } else if (Activity.equals("stocks")) {
//            arrayList = new IntuitveDatabase(QuizActivity.this).GetImage("3");
//        } else if (Activity.equals("commodities")) {
//            arrayList = new IntuitveDatabase(QuizActivity.this).GetImage("1");
//        } else if (Activity.equals("miscellaneous")) {
//            arrayList = new IntuitveDatabase(QuizActivity.this).GetImage("4");
//        }
//
//        Log.d("Array", "" + arrayList.size());
////        int id = getResources().getIdentifier("(R.drawable." +arrayList.get(i).getIMAGE1(), null, null);
//
////        int id = getResources().getIdentifier(arrayList.get(i).getIMAGE1(),"id",  "com.intuitve");
////
////        Log.d("ID1",""+Integer.parseInt("R.drawable."+arrayList.get(i).getIMAGE1()));
//
////        int drawableID = getResources().getIdentifier(arrayList.get(i).getIMAGE1(), "drawable", "com.intuitve");
////        Toast.makeText(QuizActivity.this, arrayList.get(i).getIMAGE1() + " -- " + drawableID, Toast.LENGTH_SHORT).show();
////        view.setBackgroundResource(drawableID);
//
//
//        Log.d("ID1", "" + R.drawable.ic_miscellaneous_1);
//        a.setImageResource(getResources().getIdentifier(arrayList.get(i).getIMAGE1(), "drawable", "com.intuitve"));
//        b.setImageResource(getResources().getIdentifier(arrayList.get(i).getIMAGE2(), "drawable", "com.intuitve"));
//        c.setImageResource(getResources().getIdentifier(arrayList.get(i).getIMAGE3(), "drawable", "com.intuitve"));
//        d.setImageResource(getResources().getIdentifier(arrayList.get(i).getIMAGE4(), "drawable", "com.intuitve"));


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

    private void checkBTState() {

        if (btAdapter == null) {
            Toast.makeText(getBaseContext(), "Device does not support bluetooth", Toast.LENGTH_LONG).show();
        } else {
            if (btAdapter.isEnabled()) {
            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();


        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);


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
//                            txtreceive.setText(txtreceive.getText() + "\n\nAnother message received :" + readMessage);
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

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {

        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
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
        sheet1 = wb.createSheet("Gsr Reading");

        // Generate column headings

        Row row = sheet1.createRow(0);

        c = row.createCell(0);
        c.setCellValue("2");
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
        c.setCellValue("12");
        c.setCellStyle(cs);


        c = row.createCell(5);
        c.setCellValue("15");
        c.setCellStyle(cs);


        c = row.createCell(6);
        c.setCellValue("random");
        c.setCellStyle(cs);


        c = row.createCell(7);
        c.setCellValue("result");
        c.setCellStyle(cs);

        for (int i = 0; i < quizResult.size(); i++) {

            Row row_data = sheet1.createRow(0);

            c = row_data.createCell(0);
            c.setCellValue(quizResult.get(i).getGSR2());

            c = row_data.createCell(1);
            c.setCellValue(quizResult.get(i).getGSR5());

            c = row_data.createCell(2);
            c.setCellValue(quizResult.get(i).getSelectedPos());

            c = row_data.createCell(3);
            c.setCellValue(quizResult.get(i).getGAR10());

            c = row_data.createCell(4);
            c.setCellValue(quizResult.get(i).getGSR12());

            c = row_data.createCell(5);
            c.setCellValue(quizResult.get(i).getGSR15());

            c = row_data.createCell(6);
            c.setCellValue(quizResult.get(i).getRandomPos());

            c = row_data.createCell(7);
            c.setCellValue(quizResult.get(i).isResult());


        }
//        sheet1.setColumnWidth(0, (15 * 500));
//        sheet1.setColumnWidth(1, (15 * 500));
//        sheet1.setColumnWidth(2, (15 * 500));


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
            Toast.makeText(context, "xls file Create successfully", Toast.LENGTH_SHORT).show();
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


}
