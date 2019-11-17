package com.vogella.android.retrofitgithub.common.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class UserDeserializer implements JsonDeserializer<User> {
    @Override
    public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        User user = new User();

        JsonObject userJsonObject = json.getAsJsonObject();
        user.setFirstName(userJsonObject.get("firstName").getAsString());
        user.setLastName(userJsonObject.get("lastName").getAsString());
        user.setEmail(userJsonObject.get("email").getAsString());
        user.setAddress(userJsonObject.get("address").getAsString());
        user.setCity(userJsonObject.get("city").getAsString());
        user.setZipcode(userJsonObject.get("zipcode").getAsString());
        user.setDateOfBirth(userJsonObject.get("dateOfBirth").getAsString());
        //user.setLastLocation(userJsonObject.get("lastLocation").getAsString());
        //user.setId(userJsonObject.get("id").getAsLong());
        //user.setRole(userJsonObject.get("role").getAsString());

        return user;

    }

    public User deserializeUser(JsonObject json) {
        JsonObject userJsonObject = json.getAsJsonObject();
        if (userJsonObject != null) {
            Gson userDeserializer = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserDeserializer())
                    .create();
            return userDeserializer.fromJson(userJsonObject, User.class);
        }
        return null;
    }
}
