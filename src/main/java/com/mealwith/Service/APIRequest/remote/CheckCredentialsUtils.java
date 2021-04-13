package com.mealwith.Service.APIRequest.remote;

public class CheckCredentialsUtils {

    private CheckCredentialsUtils() {}

    public static final String BASE_URL = "https://127.0.0.1:8000/api/";

    public static CheckCredentialsService getAPIService() {
        return CheckCredentialServiceClient.getClient(BASE_URL).create(CheckCredentialsService.class);
    }
}