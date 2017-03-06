package com.intuitve;

import com.intuitve.Utils.SharedPreference;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView training_intuitve_btn, training_result_btn, live_reading_btn, trading_btn;
    TextView logoutTv;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = getIntent();


        address = intent.getStringExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS);


        // TODO: 09-02-2017 Call the Initialize method
        init();
    }

    // TODO: 09-02-2017 Initialize the widget
    void init() {
        training_intuitve_btn = (ImageView) findViewById(R.id.training_intuitve_btn);
        logoutTv = (TextView) findViewById(R.id.activity_main_logout);
        training_intuitve_btn.setOnClickListener(this);
        logoutTv.setOnClickListener(this);
    }

    // TODO: 09-02-2017 Set the Click Listener
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_logout:

                new SharedPreference(MainActivity.this).clearSharedPrefrence();
                Intent in = new Intent(MainActivity.this, LoginActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(in);
                finish();


                break;
            case R.id.training_intuitve_btn:

                Intent intent = new Intent(MainActivity.this, IntuitionTrainingActivity.class);
                intent.putExtra(DeviceListActivity.EXTRA_DEVICE_ADDRESS, address);
                startActivity(intent);
                break;
        }
    }


}


//    http://designyourworld.com.au/projects/quizweb/api/user/register/?insecure=cool&username=er&email=er@gmail.com&nonce=4c17315316&display_name=Er&first_name=Er&last_name=Dr&user_pass=12345
