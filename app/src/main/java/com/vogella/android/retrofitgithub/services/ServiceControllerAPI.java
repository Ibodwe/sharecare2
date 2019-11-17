package com.vogella.android.retrofitgithub.services;

import com.vogella.android.retrofitgithub.services.servicesService.Service;
import com.vogella.android.retrofitgithub.services.servicesService.ServiceAddRequest;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceControllerAPI {
    String ENDPOINT = "http://80.114.130.214:8080/api/services/";

    @POST("add")
    Single<Service> addRequest (@Body ServiceAddRequest request);

    @GET("{id}")
    Single<Long> getService(@Path("id") String id);

    @GET("get")
    List<Service> getAllServices();

    @DELETE("{id}")
    Single<Void> deleteService(@Path("id") String id);

    @PUT("{id}")
    Single<Void> updateService(@Body Service service);


}
