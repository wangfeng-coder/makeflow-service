package com.makeid.makeflow.workflow.utils;
/**
*@program makeflow-service
*@description 
*@author feng_wf
*@create 2023-05-30
*/
public class RedisKeyUtils {

   public static String genKey(String keyPre,String... end) {
        StringBuilder str = new StringBuilder(keyPre);
        for (String s : end) {
            str.append(":");
            str.append(s);
        }
        return str.toString();
    }
}
