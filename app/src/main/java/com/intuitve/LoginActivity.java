package com.intuitve;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.intuitve.Model.QuizModel;
import com.intuitve.Utils.APIServices;
import com.intuitve.Utils.AppConstant;
import com.intuitve.Model.Loginmodel;
import com.intuitve.Utils.IntuitveDatabase;
import com.intuitve.Utils.SharedPreference;
import com.intuitve.Utils.Utils;
import com.intuitve.Utils.Validation;

import io.fabric.sdk.android.Fabric;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    EditText emailedt, passwordedt;
    TextView registertxt;
    Button submitbtn;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());

        if (new SharedPreference(LoginActivity.this).RetriveValue(Utils.UserName).equals("") || new SharedPreference(LoginActivity.this).RetriveValue(Utils.UserName).equals(null)) {
            setContentView(R.layout.activity_login);
            init();
        } else {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            this.finish();
        }


    }

    void init() {

        emailedt = (EditText) findViewById(R.id.login_email_edt);
        passwordedt = (EditText) findViewById(R.id.login_password_edt);
        registertxt = (TextView) findViewById(R.id.login_register_txt);
        submitbtn = (Button) findViewById(R.id.login_submit_btn);

        submitbtn.setOnClickListener(this);
        registertxt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.login_register_txt) {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            LoginActivity.this.finish();
        } else if (v.getId() == R.id.login_submit_btn) {

            if (AppConstant.isNetworkAvailable(LoginActivity.this)) {

                if (Validation.isValidEmail(emailedt.getText().toString()) && Validation.isValidPassword(passwordedt.getText().toString()))
                {    pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("loading");
                    pd.setCancelable(false);
                pd.show();

                    Retrofit_Login();}
                else
                    Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please connect to Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void Retrofit_Login() {
        Map<String, String> Loginmap = new HashMap<>();
        Loginmap.put("insecure", "cool");
        Loginmap.put("email", emailedt.getText().toString());
        Loginmap.put("password", passwordedt.getText().toString());

        APIServices loginservice = AppConstant.setupRetrofit(AppConstant.BASE_URL);
        Call<Loginmodel> loginCall = loginservice.LoginService(Loginmap);

        loginCall.enqueue(new Callback<Loginmodel>() {
            @Override
            public void onResponse(Call<Loginmodel> call, Response<Loginmodel> response) {
                pd.dismiss();

                    if (response.body() instanceof Loginmodel ) {

                        if(response.body().getStatus().equals("ok") && response.body().getStatus() != null) {
                            new SharedPreference(LoginActivity.this).SaveValue(Utils.UserName, response.body().getUser().getUsername());

                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Email and password mismatch", Toast.LENGTH_SHORT).show();
                    }
                }

            @Override
            public void onFailure(Call<Loginmodel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }


}
