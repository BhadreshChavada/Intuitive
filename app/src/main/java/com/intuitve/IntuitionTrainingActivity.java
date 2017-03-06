package com.intuitve;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Bhadresh Chavada on 09-02-2017.
 */

public class IntuitionTrainingActivity extends Activity implements View.OnClickListener {

    ImageView currency, stock, commodities, miscellaneous;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intuition_training);

        Intent intent = getIntent();

        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);

        currency = (ImageView) findViewById(R.id.currencies_intuition_training_btn);
        stock = (ImageView) findViewById(R.id.stocks_intuition_training_btn);
        commodities = (ImageView) findViewById(R.id.commodities_intuition_training_btn);
        miscellaneous = (ImageView) findViewById(R.id.miscellaneous_intuition_training_btn);

        currency.setOnClickListener(this);
        stock.setOnClickListener(this);
        commodities.setOnClickListener(this);
        miscellaneous.setOnClickListener(this);

//        ImageView imageView = (ImageView) findViewById(R.id.currencies_intuition_training_btn);
//        imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent i = new Intent(IntuitionTrainingActivity.this, QuizActivity.class);
//                i.putExtra("Activity", "currency");
//                startActivity(i);
//            }
//        });
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent(IntuitionTrainingActivity.this, GSRActivity.class);

        if (v.getId() == R.id.currencies_intuition_training_btn) {

            intent.putExtra("Activity", "currencies");
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
            startActivity(intent);
        } else if (v.getId() == R.id.stocks_intuition_training_btn) {
            intent.putExtra("Activity", "stocks");
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
            startActivity(intent);
        } else if (v.getId() == R.id.commodities_intuition_training_btn) {
            intent.putExtra("Activity", "commodities");
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
            startActivity(intent);
        } else if (v.getId() == R.id.miscellaneous_intuition_training_btn) {
            intent.putExtra("Activity", "miscellaneous");
            intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
            startActivity(intent);
        }

    }
}
