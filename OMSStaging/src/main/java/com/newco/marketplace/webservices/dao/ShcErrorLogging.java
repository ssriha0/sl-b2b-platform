package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * ShcErrorLogging entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_error_logging", uniqueConstraints = {})
public class ShcErrorLogging extends AbstractShcErrorLogging implements
		java.io.Serializable {
	
	private static final long serialVersionUID = -6389492939926007192L;
	
	//Constructors
	/** default constructor */
	public ShcErrorLogging() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcErrorLogging(Integer shcErrorLoggingId) {
		super(shcErrorLoggingId);
	}

	/** full constructor */
	public ShcErrorLogging(Integer shcErrorLoggingId,
			ShcOrderTransaction shcOrderTransaction, String errorCode,
			String errorMessage, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		super(shcErrorLoggingId, shcOrderTransaction, errorCode, errorMessage,
				createdDate, modifiedDate, modifiedBy);
	}

}
