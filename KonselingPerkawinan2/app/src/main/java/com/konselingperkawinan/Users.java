package com.konselingperkawinan;

/**
 * Created by Samuel JL on 22-Apr-18.
 */

public class Users {

    public Users(String name, String status, String image, String role, String thumb_image, String online) {
        this.name = name;
        this.status = status;
        this.image = image;
        this.role = role;
        this.thumb_image = thumb_image;
        //this.online = online;
    }

    public Users(){

    }

    public String name;
    public String status;
    public String image;
    public String role;
    public String thumb_image;
    //public String online;

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

//    public String getOnline() {
//        return online;
//    }
//
//    public void setOnline(String online) {
//        this.online = online;
//    }


}
