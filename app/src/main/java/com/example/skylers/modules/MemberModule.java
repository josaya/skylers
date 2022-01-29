package com.example.skylers.modules;

public class MemberModule {

    String memberPhoto, memberName, memberNumber;

    public MemberModule(String photo, String name, String number){
        this.memberPhoto    =   photo;
        this.memberName     =   name;
        this.memberNumber   =   number;
    }

    public String getMemberPhoto() {
        return memberPhoto;
    }

    public String getMemberName() {
        return memberName;
    }

    public String getMemberNumber() {
        return memberNumber;
    }
}
