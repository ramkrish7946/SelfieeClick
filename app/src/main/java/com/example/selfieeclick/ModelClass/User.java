package com.example.selfieeclick.ModelClass;

public class User {
    String Name;
    String email;
    String imageuri;
    String Uid;
    String status;


    public User(String uid, String name, String email, String imageURI, String status) {
    }

    public User(String name, String email, String uid,String status) {
        this.Name = name;
        this.email = email;
        this.Uid = uid;
        this.imageuri=imageuri;
        this.status=status;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        this.Uid = uid;
    }
}
