package com.vogella.android.retrofitgithub.services.servicesService;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class ServiceDeserializer implements JsonDeserializer<Service> {

    @Override
    public Service deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Service service = new Service();
        JsonObject requestJsonObject = json.getAsJsonObject();

        service.setTitle(requestJsonObject.get("title").getAsString());
        service.setHours(requestJsonObject.get("hour").getAsInt());
        service.setAddress(requestJsonObject.get("address").getAsString());
        service.setPostCode(requestJsonObject.get("postCode").getAsString());
        service.setDescription(requestJsonObject.get("description").getAsString());
        service.setPreference(requestJsonObject.get("preference").getAsString());
        //Service.setAccepted(requestJsonObject.getAsBoolean());
        return service;
    }
}
