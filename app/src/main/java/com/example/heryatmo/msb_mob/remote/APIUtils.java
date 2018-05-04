package com.example.heryatmo.msb_mob.remote;

public class APIUtils {

    private APIUtils(){
    };


    public static APIService getUserService(){
        return RetroClient.getClient().create(APIService.class);
    }
}
