package com.example.heryatmo.msb_mob;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.heryatmo.msb_mob.model.User;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class SessionManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE =  0;
    private static final String PREF_NAME = "Login";
    private static final String IS_LOGIN = "IsLoggedIn";


    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSession(String email){
        editor.putBoolean(IS_LOGIN,true);
        editor.commit();
    }
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(_context,LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }


    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN,false);
    }
}
