package com.intuitve;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import java.util.Random;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class QuizActivity extends Activity implements View.OnClickListener {

    ImageView a, b, c, d;

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

        String Activity = getIntent().getStringExtra("Activity");

        if (Activity.equals("currencies")) {
            a.setImageResource(R.drawable.ic_forex_1);
            b.setImageResource(R.drawable.ic_forex_2);
            c.setImageResource(R.drawable.ic_forex_3);
            d.setImageResource(R.drawable.ic_forex_4);

        } else if (Activity.equals("stocks")) {

            a.setImageResource(R.drawable.ic_stocks1);
            b.setImageResource(R.drawable.ic_stocks2);
            c.setImageResource(R.drawable.ic_stocks3);
            d.setImageResource(R.drawable.ic_stocks4);

        } else if (Activity.equals("commodities")) {

            a.setImageResource(R.drawable.ic_commodities_1);
            b.setImageResource(R.drawable.ic_commodities_2);
            c.setImageResource(R.drawable.ic_commodities_3);
            d.setImageResource(R.drawable.ic_commodities_4);

        } else if (Activity.equals("miscellaneous")) {

            a.setImageResource(R.drawable.ic_miscellaneous_1);
            b.setImageResource(R.drawable.ic_miscellaneous_2);
            c.setImageResource(R.drawable.ic_miscellaneous_3);
            d.setImageResource(R.drawable.ic_miscellaneous_4);

        }

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
            }
        }, 10000);

    }
}
