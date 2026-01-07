package com.example.spring_data_jpa_demo.exceptions;

public class ApplicantNotFoundException extends RuntimeException{

    public ApplicantNotFoundException(String message){
        super(message);
    }
}
