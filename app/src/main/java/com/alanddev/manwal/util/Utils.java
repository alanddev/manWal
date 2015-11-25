package com.alanddev.manwal.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ANLD on 24/11/2015.
 */
public class Utils {

    private static int wallet_id;

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

    public static SharedPreferences getSharedPreferences(Context context){
        return context.getSharedPreferences(Constant.SHAREDPREFERENCES_NAME,context.MODE_PRIVATE);
    }


    public static int getWallet_id() {
        //return wallet_id;
        return 1;
    }

    public static void setWallet_id(int wallet_id) {
        Utils.wallet_id = wallet_id;
    }

}
