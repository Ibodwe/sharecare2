package com.vogella.android.retrofitgithub.services.servicesService;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ServiceAddRequest {
    private String title;
    private String description;
    private long cost;
    private long duration;
    private long requestedBy;
    private String address;
    private String city;
    private String zipcode;
}
