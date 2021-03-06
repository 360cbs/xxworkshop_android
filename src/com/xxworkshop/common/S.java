/*
 * Copyright (c) 2014 xxworkshop. All rights reserved.
 * Created by Broche Xu on 2/13/14 11:48 AM.
 */

package com.xxworkshop.common;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class S {
    public final static String getDeviceId(Context context) {
        android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        String device_id = tm.getDeviceId();

        android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        String mac = wifi.getConnectionInfo().getMacAddress();

        if (TextUtils.isEmpty(device_id)) {
            device_id = mac;
        }

        if (TextUtils.isEmpty(device_id)) {
            device_id = android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        }

        if (TextUtils.isEmpty(device_id)) {
            device_id = Installation.getID(context);
        }
        return device_id;
    }

    public final static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            return 0;
        }
    }

    public final static double getTimeStamp() {
        return java.lang.System.currentTimeMillis() / 1000.0;
    }

    public final static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();

            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            String mac = wifi.getConnectionInfo().getMacAddress();
            json.put("mac", mac);

            String device_id = getDeviceId(context);
            json.put("device_id", device_id);

            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //    Toast.makeText(MainActivity.this, S.getSystemProperty("ro.miui.ui.version.name"), 3000).show();
    public final static String getSystemProperty(String propName) {
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (IOException ex) {
            L.log("Unable to read sysprop " + propName);
            return null;
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    L.log("Exception while closing InputStream");
                }
            }
        }
        return line;
    }
}
