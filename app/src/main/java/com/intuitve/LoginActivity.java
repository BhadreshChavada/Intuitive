package com.intuitve;

import com.crashlytics.android.Crashlytics;
import com.intuitve.Model.Loginmodel;
import com.intuitve.Utils.APIServices;
import com.intuitve.Utils.AppConstant;
import com.intuitve.Utils.SharedPreference;
import com.intuitve.Utils.Utils;
import com.intuitve.Utils.Validation;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import io.fabric.sdk.android.Fabric;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Bhadresh Chavada on 11-02-2017.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    EditText emailedt, passwordedt;
    TextView registertxt;
    TextView submitbtn;
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
        submitbtn = (TextView) findViewById(R.id.login_submit_btn);


//        emailedt.getBackground().mutate().setColorFilter(ContextCompat.getColor(this, android.R.color.white), PorterDuff.Mode.SRC_ATOP);

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

                if (Validation.isValidEmail(emailedt.getText().toString()) && Validation.isValidPassword(passwordedt.getText().toString())) {
                    pd = new ProgressDialog(LoginActivity.this);
                    pd.setMessage("loading");
                    pd.setCancelable(false);
                    pd.show();

                    Retrofit_Login();

//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    LoginActivity.this.finish();
                } else
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

                if (response.body() != null && response.body().getStatus() != null) {

                    if (response.body().getStatus().equals("ok")) {
                        if (response.body().getUser() != null && response.body().getUser().getUsername() != null) {

                            new SharedPreference(LoginActivity.this).SaveValue(Utils.UserName, response.body().getUser().getUsername());
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            LoginActivity.this.finish();

                        } else {

                            Toast.makeText(LoginActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();

                        }
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
