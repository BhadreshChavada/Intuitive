package com.intuitve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Bhadresh Chavada on 09-02-2017.
 */

public class IntuitionTrainingActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intuition_training);

        ImageView imageView = (ImageView) findViewById(R.id.currencies_intuition_training_btn);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(IntuitionTrainingActivity.this, QuizActivity.class));
            }
        });
    }
}
