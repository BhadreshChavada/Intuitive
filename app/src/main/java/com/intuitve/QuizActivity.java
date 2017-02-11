package com.intuitve;

import android.app.Activity;
import android.app.Dialog;
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

        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);


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

        final Dialog d = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        d.setContentView(R.layout.image_dialog);
        ImageView imageView = (ImageView) d.findViewById(R.id.dialog_images);


        if (random == 1) {
            imageView.setImageResource(R.drawable.a);
        } else if (random == 2) {
            imageView.setImageResource(R.drawable.b);
        } else if (random == 3) {
            imageView.setImageResource(R.drawable.c);
        } else if (random == 4) {
            imageView.setImageResource(R.drawable.d);
        }

        d.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                d.dismiss();
            }
        }, 10000);

    }
}
