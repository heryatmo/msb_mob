package com.konselingperkawinan;

import android.app.Application;
import android.content.Context;
import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Samuel JL on 03-May-18.
 */

public class GetTimeAgo extends Application {

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;
    private static final int WEEK_MILLIS = 7 * DAY_MILLIS;
    private static final int MONTH_MILLIS = 4 * WEEK_MILLIS;
    private static Locale indonesia;
    private static Calendar cal;



    public static String getTimeAgo(long time, Context ctx) {

        cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);

        int month  = cal.get(Calendar.MONTH);
        int dayName = cal.get(Calendar.DAY_OF_WEEK);
        int year = cal.get(Calendar.YEAR);
        int dayNumb = cal.get(Calendar.DAY_OF_MONTH);

        //String date = DateFormat.format("EEE, dd MMMM yyyy", cal).toString();

        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        long now = System.currentTimeMillis();
        if (time > now || time <= 0) {
            return "0 menit lalu";
        }

        // TODO: localize
        final long diff = now - time;
        if (diff < MINUTE_MILLIS) {
            return "0 menit lalu";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "1 menit lalu";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " menit lalu";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "1 jam lalu";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " jam lalu";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "kemarin";
        } else if (diff < 7 * DAY_MILLIS) {
            return diff / DAY_MILLIS + " hari lalu";
        } else if (diff < 2 * WEEK_MILLIS) {
            return "1 minggu lalu";
        } else if (diff < 4 * WEEK_MILLIS){
            return diff / WEEK_MILLIS + " minggu lalu";
        } else {
            return getDayName(dayName)+", "+dayNumb+" "+getMonthName(month)+" "+year;
        }

    }

    public static String getMonthName(int day){
        switch(day){
            case 0:
                return "Januari";
            case 1:
                return "Februari";
            case 2:
                return "Maret";
            case 3:
                return "April";
            case 4:
                return "Mei";
            case 5:
                return "Juni";
            case 6:
                return "Juli";
            case 7:
                return "Agustus";
            case 8:
                return "September";
            case 9:
                return "Oktober";
            case 10:
                return "November";
            case 11:
                return "Desember";
        }

        return "Salah";
    }

    public static String getDayName(int day){
        switch(day){
            case 1:
                return "Minggu";
            case 2:
                return "Senin";
            case 3:
                return "Selasa";
            case 4:
                return "Rabu";
            case 5:
                return "Kamis";
            case 6:
                return "Jumat";
            case 7:
                return "Sabtu";
        }

        return "Error";
    }
}
