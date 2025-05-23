package com.saptrishi.hotelkotapp.util;

public class IpServiceChecker {


    private static String ipaddress;

    private static String appName;



    public IpServiceChecker(String ipaddress, String appName) {
        this.ipaddress = ipaddress;
        this.appName = appName;
    }
    public static String getIpaddress()
    {
        return ipaddress;
    }

    public static String getAppName() {
        return appName;
    }
}
