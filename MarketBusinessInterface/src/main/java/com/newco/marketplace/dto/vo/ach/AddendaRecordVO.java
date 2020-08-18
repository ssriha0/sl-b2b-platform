package com.newco.marketplace.dto.vo.ach;


/**  
* AddendaRecordVO.java - This class is a template class for Addenda Record as specified by NACHA
* 
* @author  Siva
* @version 1.0  
*/  
public class AddendaRecordVO extends NachaGenericRecordVO implements Cloneable {
	

/**
	 * 
	 */
	private static final long serialVersionUID = 3923706484134169322L;

@Override
public Object clone() {
    try {
        return super.clone();
    }
    catch (CloneNotSupportedException e) {
        // This should never happen
        throw new InternalError(e.toString());
    }
}
}
