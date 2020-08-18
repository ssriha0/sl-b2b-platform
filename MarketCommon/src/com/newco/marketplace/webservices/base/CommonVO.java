package com.newco.marketplace.webservices.base;

/*
 * CommonVO.java
 *
 * Created on March 25, 2006, 10:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.sears.os.vo.SerializableBaseVO;

/**
 *
 * @author domiller
 */
public abstract class CommonVO extends SerializableBaseVO{
    
    /** Creates a new instance of CommonVO */
    public CommonVO() {
    }
    
    public String toString() 
    {
        Method[] methodList = getClass().getMethods();

        ToStringBuilder builder = new ToStringBuilder(this);
        for(int i = 0; i< methodList.length; i++)
        {
            Method aMethod = methodList[i];
            if(aMethod.getName().startsWith("get") && !(aMethod.getName().equals("getClass")))
            {
                try {
                    Object out = aMethod.invoke(this,new Object[0]);
                   
                    builder.append(aMethod.getName(), out);
                } catch (IllegalArgumentException ex) {
                    ex.printStackTrace();
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return builder.toString();
    }
    
}

