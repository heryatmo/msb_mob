package com.example.heryatmo.msb_mob.remote;

public class APIUtils {

    private APIUtils(){
    };

    public static final String API_URL ="https://msb.bluebox2.com/";

    public static APIService getUserService(){
        return RetroClient.getClient(API_URL).create(APIService.class);
    }
}
