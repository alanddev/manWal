package com.alanddev.manwal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.alanddev.manwal.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ANLD on 24/11/2015.
 */
public class Utils {

    private static int wallet_id;

    private static Locale locale;
    private static Locale localDefault = new Locale("vi","VI");

    public static Locale getLocale() {
        if(locale==null){
            return localDefault;
        }
        return locale;
    }

    public static void setLocale(Locale locale) {
        Utils.locale = locale;
    }



    public static String changeDateStr2Str(String dateStr){
        DateFormat fromFormat = new SimpleDateFormat(Constant.DATE_FORMAT_PICKER);
        fromFormat.setLenient(false);
        DateFormat toFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DB);
        toFormat.setLenient(false);
        Date date = null;
        try {
            date = fromFormat.parse(dateStr);
        } catch (ParseException e) {
            date = new Date();
        }
        return toFormat.format(date);
    }

    public void createFile(String folder, String fileName){
        File directory = new File("/sdcard/manwal/" + folder);
        directory.mkdirs();
        File outputFile = new File(directory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(outputFile);
        }catch(IOException e){
            System.out.println(e);
        }
    }

    public void createFolder(String folder){
        File directory = new File(folder);
        directory.mkdirs();
    }


    public static void copyFile(String sourcePath, String destPath)
            throws IOException {
        File source = new File(sourcePath);
        File dest = new File(destPath);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            input.close();
            output.close();
        }
    }



    public String getRealPathFromURI(Context context,Uri contentURI) {
        String result;
        Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static String changeDate2Str(Date date){
        DateFormat toFormat = new SimpleDateFormat(Constant.DATE_FORMAT_DB);
        toFormat.setLenient(false);
        return toFormat.format(date);
    }

    public static String changeDate2Str(Date date,String dateformat){
        DateFormat toFormat = new SimpleDateFormat(dateformat);
        toFormat.setLenient(false);
        return toFormat.format(date);
    }

    public static Date changeStr2Date(String dateStr,String dateformat){
        DateFormat fromFormat = new SimpleDateFormat(dateformat);
        fromFormat.setLenient(false);
        try {
            return fromFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static Date changeDate2Date(Date date,String datefomat){
        String strDate = changeDate2Str(date,datefomat);
        return changeStr2Date(strDate,datefomat);
    }

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME,context.MODE_PRIVATE);
    }

    public void setSharedPreferencesValue(Context context, String name, int value){
        SharedPreferences sharedPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(name, value);
        editor.commit();
    }

    public void setSharedPreferencesValue(Context context, String name, String value){
        SharedPreferences sharedPref = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(name, value);
        editor.commit();
    }

    public int getSharedPreferencesValue(Context context,String name) {
        SharedPreferences sharedPref = getSharedPreferences(context);
        int defaultValue = 0;
        int getValue = sharedPref.getInt(name, defaultValue);
        return getValue;
    }


    public static int getWallet_id() {
        //return wallet_id;
        return 1;
    }

    public static void setWallet_id(int wallet_id) {
        Utils.wallet_id = wallet_id;
    }

    public static Date getYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        return changeDate2Date(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static Date getTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        return changeDate2Date(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static Date getToday(){
        Calendar calendar = Calendar.getInstance();
        return changeDate2Date(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getStrToday(){
        Calendar calendar = Calendar.getInstance();
        return changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getStrYesterday(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,-1);
        return changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getStrTomorrow(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE,1);
        return changeDate2Str(calendar.getTime(), Constant.DATE_FORMAT_DB);
    }

    public static String getDayView(Context context, Date date){
        String[] dayview = context.getResources().getStringArray(R.array.day_view);
        if(date.compareTo(getToday())==0){
            return dayview[1];
        }else if(date.compareTo(getYesterday())==0){
            return dayview[0];
        }else if(date.compareTo(getTomorrow())==0){
            return dayview[2];
        }else{
            return changeDate2Str(date,Constant.DATE_FORMAT_PICKER);
        }
    }

    public static String getDatefromDayView(Context context,String dateStr){
        String[] dayview = context.getResources().getStringArray(R.array.day_view);
        String strRes;
        if(dateStr.equals(dayview[0])){
            // Yesterday
            strRes = getStrYesterday();
        }else if(dateStr.equals(dayview[1])){
            //Today
            strRes = getStrToday();
        }else if(dateStr.equals(dayview[2])){
            //Tomorrow
            strRes = getStrTomorrow();
        }else{
            return changeDateStr2Str(dateStr);
        }
        return strRes;
    }



}
