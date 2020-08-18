package com.servicelive.wallet.batch.ach.vo;

// TODO: Auto-generated Javadoc
/**
 * AddendaRecordVO.java - This class is a template class for Addenda Record as specified by NACHA
 * 
 * @author Siva
 * @version 1.0
 */
public class AddendaRecordVO extends NachaGenericRecordVO implements Cloneable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3923706484134169322L;

	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {

		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// This should never happen
			throw new RuntimeException(e.toString(),e);
		}
	}
}
