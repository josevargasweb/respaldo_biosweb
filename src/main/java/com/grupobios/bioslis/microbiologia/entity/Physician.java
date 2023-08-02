package com.grupobios.bioslis.microbiologia.entity;

public class Physician {

    private String id;
    private String names;
    private String firstSurname;
    private String secondSurname;
    private String phoneNumber;
    private String email;

    public Physician(){}

    public Physician(
        String id,
        String names,
        String firstSurname,
        String secondSurname,
        String phoneNumber,
        String email
    ){
        this.id = id;
        this.names = names;
        this.firstSurname = firstSurname;
        this.secondSurname = secondSurname;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getFullName(){
        return this.names + " " + this.firstSurname + " " + this.secondSurname;
    }

    public String getId(){
        return this.id;
    }

    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    public String getEmail(){
        return this.email;
    }

}
