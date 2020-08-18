/**
 * 
 */
package com.servicelive.spn.buyer.campaign;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

import com.servicelive.domain.spn.network.SPNHeader;

/**
 * @author hoza
 *
 */
public class SPNHeaderConverter extends StrutsTypeConverter {

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertFromString(java.util.Map, java.lang.String[], java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object convertFromString(Map arg0, String[] arg1, Class arg2) {
		//I need to do the type check here. but f$$k it .. I will do it l8r
		//get the first value
		SPNHeader hdr = new SPNHeader();
		hdr.setSpnId(Integer.valueOf(0));
		if(arg1 != null && arg1.length > 0){
			String val = arg1[0];
			hdr.setSpnId(Integer.valueOf(val));
		}
		
		return hdr;
	}

	/* (non-Javadoc)
	 * @see org.apache.struts2.util.StrutsTypeConverter#convertToString(java.util.Map, java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String convertToString(Map arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
