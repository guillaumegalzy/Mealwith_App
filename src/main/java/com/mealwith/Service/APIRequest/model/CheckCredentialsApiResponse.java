package com.mealwith.Service.APIRequest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckCredentialsApiResponse {
    @SerializedName("allowed") //The @SerializedName annotation is needed for Gson to map the JSON keys to Java object fields. JSON key 'allowed' is mapped to the class field 'allowed'
    @Expose //The @Expose annotation indicates that the class member should be exposed for JSON serialization or deserialization.
    private Boolean allowed;

    public Boolean getAllowed() {
        return allowed;
    }

    public void setAllowed(Boolean allowed) {
            this.allowed = allowed;
        }

    @Override
    public String toString() {
        return String.valueOf(allowed);
    }
}