package com.newco.marketplace.webservices.dao;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * ShcMerchandise entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "shc_merchandise", uniqueConstraints = { @UniqueConstraint(columnNames = { "shc_order_id" }) })
public class ShcMerchandise extends AbstractShcMerchandise implements
		java.io.Serializable {
	
	private static final long serialVersionUID = 1113838894705291321L;
	
	// Constructors

	/** default constructor */
	public ShcMerchandise() {
		// intentionally blank
	}

	/** minimal constructor */
	public ShcMerchandise(Integer shcMerchandiseId, String itemNo) {
		super(shcMerchandiseId, itemNo);
	}

	/** full constructor */
	public ShcMerchandise(Integer shcMerchandiseId, ShcOrder shcOrder,
			String itemNo, String code, String description, String specialty,
			String brand, String model,String serialNumber, String divisionCode, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		super(shcMerchandiseId, shcOrder, itemNo, code, description, specialty,
				brand, model, serialNumber, divisionCode, createdDate, modifiedDate, modifiedBy);
	}
 
}
