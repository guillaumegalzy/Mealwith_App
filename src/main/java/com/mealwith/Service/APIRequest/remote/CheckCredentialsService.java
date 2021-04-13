package com.mealwith.Service.APIRequest.remote;

import com.mealwith.Service.APIRequest.model.CheckCredentialsApiResponse;
import retrofit2.Call;
import retrofit2.http.*;

public interface CheckCredentialsService {

    // Create the GET method to make a proper request to the Symfony API
    @GET("CheckUserCredentials/{userLoginMail}_{userPlainPassword}")
    // Set the args used with this post to the API, names should matches thoses of the args of the endpoint in Symfony
    Call<CheckCredentialsApiResponse> checkCredentials(@Path("userLoginMail") String userLoginMail,
                                                       @Path("userPlainPassword") String userPlainPassword);
//
//    // Create the POST method to make a proper request to the Symfony API
//    @POST("CheckUserCredentials")
//    @FormUrlEncoded
//    // Set the args used with this post to the API, names should matches thoses of the args of the endpoint in Symfony
//    Call<CheckCredentialsApiResponse> checkCredentials(@Field("userLoginMail") String userLoginMail,
//                                                       @Field("userPlainPassword") String userPlainPassword);
}