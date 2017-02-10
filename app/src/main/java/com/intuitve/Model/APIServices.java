package com.intuitve.Model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by AMD21 on 10/2/17.
 */

public interface APIServices {

    @GET("get_nonce/?")
    Call<Nouncemodel> NounceService(
            @QueryMap(encoded = true) Map<String, String> webservice
    );

    @GET("user/register/?")
    Call<Registrationmodel> RegistrationService(
            @QueryMap(encoded = true) Map<String, String> webservice
    );

    @GET("user/generate_auth_cookie/?")
    Call<Loginmodel> LoginService(
            @QueryMap(encoded = true) Map<String, String> webservice
    );
}
