package com.vogella.android.retrofitgithub.common.apiresponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.vogella.android.retrofitgithub.common.apiresponse.ApiResponse;
import com.vogella.android.retrofitgithub.common.user.User;
import com.vogella.android.retrofitgithub.common.user.UserDeserializer;

import java.lang.reflect.Type;

public class ApiResponseDeserializer implements JsonDeserializer<ApiResponse> {

    @Override
    public ApiResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        ApiResponse apiResponse = new ApiResponse();

        JsonObject apiResponseJsonObject = json.getAsJsonObject();
        apiResponse.setResult(apiResponseJsonObject.get("result"));
        apiResponse.setMessage(apiResponseJsonObject.get("message").getAsString());
        apiResponse.setStatus(apiResponseJsonObject.get("status").getAsInt());

        return apiResponse;
    }

}
