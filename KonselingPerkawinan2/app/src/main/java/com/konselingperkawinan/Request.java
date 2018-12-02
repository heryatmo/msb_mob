package com.konselingperkawinan;

/**
 * Created by Samuel JL on 09-May-18.
 */

public class Request {

    private String name,status,thumb_image,role;

    public Request(){}

    public Request(String name, String status, String thumb_image, String role) {
        this.name = name;
        this.status = status;
        this.thumb_image = thumb_image;
        this.role = role;
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

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
