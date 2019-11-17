package com.vogella.android.retrofitgithub.services.servicesService;

public enum Status {
    //Open is when an elderly requests the service.
    OPEN,
    //Caregivers requests to do the task.
    REQUESTED,
    //Elderly accepts caregiver's request.
    ACCEPTED,
    //This will happen when the caregiver checks-in at the elerderlys'
    INPROGRESS,
    //After task is done, and check out has been performed.
    COMPLETED
}
