package com.intuitve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView training_intuitve_btn,training_result_btn,live_reading_btn,trading_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 09-02-2017 Call the Initialize method
        init();
    }

    // TODO: 09-02-2017 Initialize the widget
    void init(){
        training_intuitve_btn = (ImageView) findViewById(R.id.training_intuitve_btn);
        training_intuitve_btn.setOnClickListener(this);
    }

    // TODO: 09-02-2017 Set the Click Listener
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.training_intuitve_btn){
            Intent intent = new Intent(MainActivity.this,IntuitionTrainingActivity.class);
            startActivity(intent);
        }

    }
}
