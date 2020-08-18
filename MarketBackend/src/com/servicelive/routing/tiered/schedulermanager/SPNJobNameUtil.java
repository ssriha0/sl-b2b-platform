/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.servicelive.routing.tiered.schedulermanager;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.JOB_NAME;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.JOB_GROUP_NAME;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.NAME_SEPERATOR;
import static com.servicelive.routing.tiered.constants.SPNTieredRoutingConstants.TRIGGER_NAME;

/**
 *
 * @author hoza
 */
public class SPNJobNameUtil {
 public static String getJOBName( String orderId) throws Exception {
     StringBuilder sb = new StringBuilder(JOB_NAME);
     sb.append(NAME_SEPERATOR);
      sb.append(orderId);
    /*  sb.append(NAME_SEPERATOR);
     sb.append(String.valueOf(tierId.intValue()));*/
    
    
     return sb.toString();
 }

 public static String getJOBGroupName(Integer tierId, String orderId) throws Exception {
     StringBuilder sb = new StringBuilder(JOB_GROUP_NAME);
     return sb.toString();
 }

  public static String getTriggerName(Integer tierId, String orderId) throws Exception {
     StringBuilder sb = new StringBuilder(TRIGGER_NAME);
     sb.append(NAME_SEPERATOR);
      sb.append(orderId);
      sb.append(NAME_SEPERATOR);
      sb.append(String.valueOf(tierId.intValue()));
     return sb.toString();
 }
}
