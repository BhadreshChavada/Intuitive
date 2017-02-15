package com.intuitve;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.intuitve.Model.QuizModel;
import com.intuitve.Utils.IntuitveDatabase;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class QuizActivity extends Activity implements View.OnClickListener {

    ImageView a, b, c, d;
    String Activity;
    ArrayList<QuizModel> arrayList = new ArrayList<>();
    int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_images);

        init();
    }

    void init() {

        a = (ImageView) findViewById(R.id.a);
        b = (ImageView) findViewById(R.id.b);
        c = (ImageView) findViewById(R.id.c);
        d = (ImageView) findViewById(R.id.d);

        Activity = getIntent().getStringExtra("Activity");

        setImageResource();

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


        a.setEnabled(false);
        b.setEnabled(false);
        c.setEnabled(false);
        d.setEnabled(false);

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                a.setEnabled(true);
                b.setEnabled(true);
                c.setEnabled(true);
                d.setEnabled(true);
            }
        }, 10000);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.a) {
            Random();
        } else if (v.getId() == R.id.b) {
            Random();
        } else if (v.getId() == R.id.c) {
            Random();
        } else if (v.getId() == R.id.d) {
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

        dialog.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
                setImageResource();
            }
        }, 10000);

    }
}
