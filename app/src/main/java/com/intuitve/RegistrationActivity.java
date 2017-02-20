package com.intuitve;

import com.intuitve.Model.Nouncemodel;
import com.intuitve.Model.Registrationmodel;
import com.intuitve.Utils.APIServices;
import com.intuitve.Utils.AppConstant;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class RegistrationActivity extends Activity implements View.OnClickListener {

    public EditText usernameedt, conPwdEdt, mobileNoEdt, emailedt, passedt;
    public Button sunbmitbtn;
    public String Nounce;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    void init() {
        usernameedt = (EditText) findViewById(R.id.register_user_edt);
        conPwdEdt = (EditText) findViewById(R.id.register_cpwd_edt);
        mobileNoEdt = (EditText) findViewById(R.id.register_mobile_edt);
        emailedt = (EditText) findViewById(R.id.register_email_edt);
        passedt = (EditText) findViewById(R.id.register_password_edt);
        sunbmitbtn = (Button) findViewById(R.id.register_submit_btn);

        sunbmitbtn.setOnClickListener(this);

        if (AppConstant.isNetworkAvailable(RegistrationActivity.this)) {
            Retrofit_Nounce();
        } else {
            Toast.makeText(this, getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.register_submit_btn) {
            if (AppConstant.isNetworkAvailable(RegistrationActivity.this)) {
                if (conPwdEdt.getText().toString().equals(passedt.getText().toString()))
                    Retrofir_Registartion();
                else
                    Toast.makeText(this, getString(R.string.passwordmismatch), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.nointernet), Toast.LENGTH_SHORT).show();
            }
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
                Nounce = response.body().getNonce();
            }

            @Override
            public void onFailure(Call<Nouncemodel> call, Throwable t) {

            }
        });
    }

    void Retrofir_Registartion() {

        pd = new ProgressDialog(RegistrationActivity.this);
        pd.setMessage("loading");
        pd.setCancelable(false);
        pd.show();


        Map<String, String> RegistrationMap = new HashMap<>();
        RegistrationMap.put("insecure", "cool");
        RegistrationMap.put("username", usernameedt.getText().toString());
        RegistrationMap.put("email", emailedt.getText().toString());
        RegistrationMap.put("nonce", Nounce);
        RegistrationMap.put("user_pass", passedt.getText().toString());
        RegistrationMap.put("display_name", usernameedt.getText().toString());
        RegistrationMap.put("mobile", mobileNoEdt.getText().toString());
//        RegistrationMap.put("last_name", mobileNoEdt.getText().toString());


        APIServices registrationservice = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<Registrationmodel> registrationCall = registrationservice.RegistrationService(RegistrationMap);

        registrationCall.enqueue(new Callback<Registrationmodel>() {
            @Override
            public void onResponse(Call<Registrationmodel> call, Response<Registrationmodel> response) {

                pd.dismiss();

                if (response.body() != null)
                    if (response.body().getStatus() != null)
                        if (response.body().getStatus().equalsIgnoreCase("ok")) {
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(RegistrationActivity.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                        }
                    else
                        Toast.makeText(RegistrationActivity.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(RegistrationActivity.this, getString(R.string.somethingwentwrong), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Registrationmodel> call, Throwable t) {

            }

        });
    }
}
