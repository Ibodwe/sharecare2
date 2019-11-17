package com.vogella.android.retrofitgithub.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vogella.android.retrofitgithub.common.apiresponse.ApiResponse;
import com.vogella.android.retrofitgithub.common.apiresponse.ApiResponseDeserializer;
import com.vogella.android.retrofitgithub.common.user.User;
import com.vogella.android.retrofitgithub.common.user.UserDeserializer;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserApiService {

    public UserControllerAPI connectWithLoginApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(ApiResponse.class, new ApiResponseDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserControllerAPI.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(UserControllerAPI.class);

    }

    public UserControllerAPI connectWithSignUpApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(User.class, new UserDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UserControllerAPI.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(UserControllerAPI.class);

    }
}
