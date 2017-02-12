package com.intuitve;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.intuitve.Utils.APIServices;
import com.intuitve.Utils.AppConstant;
import com.intuitve.Model.Loginmodel;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

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
        } else if (v.getId() == R.id.login_submit_btn) {
            Retrofit_Login();
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



//                Toast.makeText(MainActivity.this, "--"+response.body().getStatus(), Toast.LENGTH_SHORT).show();

//                response.body().getUser().getUsername();

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }

            @Override
            public void onFailure(Call<Loginmodel> call, Throwable t) {

            }
        });
    }
}
