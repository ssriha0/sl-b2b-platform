/*
 * Created on Feb 20, 2007
 *
 * Author: dgold1
 * 
 * Revisions:
 * 
 */
package com.sears.os.utils;

public class ProcessUtils {

    public static String getProcessId() {

        int id = Thread.currentThread().hashCode();
        return String.valueOf(id);
    }

}
