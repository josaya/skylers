package com.example.skylers.modules;

public class GenderModule {

    String genderId, genderName;

    public GenderModule(String id, String name){
        this.genderId   =   id;
        this.genderName =   name;
    }

    public String getGenderId() {
        return genderId;
    }

    public String getGenderName() {
        return genderName;
    }
}
