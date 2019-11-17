package com.vogella.android.retrofitgithub.services.servicesService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vogella.android.retrofitgithub.common.user.User;
import com.vogella.android.retrofitgithub.common.user.UserDeserializer;
import com.vogella.android.retrofitgithub.services.ServiceControllerAPI;
import com.vogella.android.retrofitgithub.user.UserControllerAPI;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceApiService {

    public ServiceControllerAPI connectWithAddServiceApi() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapter(Service.class, new ServiceDeserializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServiceControllerAPI.ENDPOINT)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(ServiceControllerAPI.class);

    }

}
