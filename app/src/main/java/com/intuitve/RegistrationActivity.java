package com.intuitve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.intuitve.Model.APIServices;
import com.intuitve.Model.AppConstant;
import com.intuitve.Model.Nouncemodel;
import com.intuitve.Model.Registrationmodel;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class RegistrationActivity extends Activity implements View.OnClickListener {

    EditText usernameedt, fnameedt, lnameedt, emailedt, passedt;
    Button sunbmitbtn;
    String Nounce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    void init() {
        usernameedt = (EditText) findViewById(R.id.register_user_edt);
        fnameedt = (EditText) findViewById(R.id.register_fname_edt);
        lnameedt = (EditText) findViewById(R.id.register_lname_edt);
        emailedt = (EditText) findViewById(R.id.register_email_edt);
        passedt = (EditText) findViewById(R.id.register_password_edt);
        sunbmitbtn = (Button) findViewById(R.id.register_submit_btn);

        sunbmitbtn.setOnClickListener(this);

        Retrofit_Nounce();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_submit_btn) {
            Retrofir_Registartion();
        }
    }

    void Retrofit_Nounce() {
        Map<String, String> nonceMap = new HashMap<String, String>();
        nonceMap.put("controller", "user");
        nonceMap.put("method", "register");

        APIServices nonceservice = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<Nouncemodel> nonceCall = nonceservice.NounceService(nonceMap);
        Log.e("url", nonceCall.request().url().toString());
        nonceCall.enqueue(new Callback<Nouncemodel>() {
            @Override
            public void onResponse(Call<Nouncemodel> call, Response<Nouncemodel> response) {
//                Toast.makeText(MainActivity.this, "" + response.body().getStatus().toString(), Toast.LENGTH_SHORT).show();
                Nounce = response.body().getNonce();
            }

            @Override
            public void onFailure(Call<Nouncemodel> call, Throwable t) {

            }
        });
    }

    void Retrofir_Registartion() {
        Map<String, String> RegistrationMap = new HashMap<>();
        RegistrationMap.put("insecure", "cool");
        RegistrationMap.put("username", usernameedt.getText().toString());
        RegistrationMap.put("email", emailedt.getText().toString());
        RegistrationMap.put("nonce", Nounce);
        RegistrationMap.put("display_name", fnameedt.getText().toString());
        RegistrationMap.put("first_name", fnameedt.getText().toString());
        RegistrationMap.put("last_name", lnameedt.getText().toString());
        RegistrationMap.put("user_pass", passedt.getText().toString());


        APIServices registrationservice = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<Registrationmodel> registrationCall = registrationservice.RegistrationService(RegistrationMap);

        registrationCall.enqueue(new Callback<Registrationmodel>() {
            @Override
            public void onResponse(Call<Registrationmodel> call, Response<Registrationmodel> response) {

//                Toast.makeText(MainActivity.this, "--"+response.body().getStatus(), Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
            }

            @Override
            public void onFailure(Call<Registrationmodel> call, Throwable t) {

            }

        });
    }
}
