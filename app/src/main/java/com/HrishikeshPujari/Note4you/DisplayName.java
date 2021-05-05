package com.HrishikeshPujari.Note4you;

public class DisplayName {
    private String displayName;
    private String email;
    private String uid;
    public DisplayName(String displayName,String email,String uid){
        this.displayName=displayName;
        this.email=email;
        this.uid=uid;

    }
    public DisplayName(){

    }

    public String getUid() {
        return uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }
}

