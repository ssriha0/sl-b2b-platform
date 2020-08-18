/**
 * 
 */
package com.newco.marketplace.vo.provider;

import com.sears.os.vo.SerializableBaseVO;


/**
 * @author langara
 */
public class AuditProfileVO extends SerializableBaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = -8849677907932756635L;
	private String userId;
    private int vendorId = -1;

    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the vendorId
     */
    public int getVendorId() {
        return vendorId;
    }

    /**
     * @param vendorId
     *                the vendorId to set
     */
    public void setVendorId(int vendorId) {
        this.vendorId = vendorId;
    }

    @Override
	public String toString() {
        //return ("\n <AuditProfileRequest>" + userId +"|" + vendorId +"|" + apForm + "<AuditProfileRequest> \n");
    	return ("\n <AuditProfileRequest>" + userId +"|" + vendorId +"|" + "<AuditProfileRequest> \n");
    }
}
