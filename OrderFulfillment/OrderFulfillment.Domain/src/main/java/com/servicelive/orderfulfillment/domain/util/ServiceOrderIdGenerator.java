/**
 * 
 */
package com.servicelive.orderfulfillment.domain.util;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Random;

import org.hibernate.HibernateException;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.servicelive.orderfulfillment.domain.SOGroup;

/**
 * @author Mustafa Motiwala
 *
 */
public class ServiceOrderIdGenerator implements IdentifierGenerator {

    /* (non-Javadoc)
     * @see org.hibernate.id.IdentifierGenerator#generate(org.hibernate.engine.SessionImplementor, java.lang.Object)
     */
    public Serializable generate(SessionImplementor arg0, Object entity)
            throws HibernateException {
        if (entity instanceof SOGroup) return getNextId("1");
    	return getNextId("5");//for simple buyer they use 5
    }

    private synchronized String getNextId(String sourceId){
                StringBuilder sb = new StringBuilder();
        StringBuilder sbOrder = new StringBuilder();
        Random rand = new Random();
        String ts = "";
        String random = "";

        // current time stamp is the 2nd part of the service order
        // it is at least 13 characters as of today...
        ts = String.valueOf(Calendar.getInstance().getTimeInMillis());
        // remove first 3 chars
        ts = ts.substring(3, 13);

        // a random number is the 3rd part of the key
        random = String.valueOf(Math.abs(rand.nextInt()));
        random = random.substring(0, 2);

        // put the 3 pieces together
        sb.append(sourceId);
        sb.append(ts);
        sb.append(random);

        // construct the service order format XXX-XXXX-XXXX-XXXX
        sbOrder.append(sb.substring(0, 3));
        sbOrder.append("-");
        sbOrder.append(sb.substring(3, 7));
        sbOrder.append("-");
        sbOrder.append(sb.substring(7, 11));
        sbOrder.append("-");
        sbOrder.append(sb.substring(11));

        return sbOrder.toString();
    }
}
