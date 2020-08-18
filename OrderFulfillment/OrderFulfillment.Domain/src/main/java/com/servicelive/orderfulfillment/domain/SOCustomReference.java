package com.servicelive.orderfulfillment.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Formula;

@Entity()
@Table(name = "so_custom_reference")
@XmlRootElement()
public class SOCustomReference extends SOChild {

    public static final String CREF_TEMPLATE_NAME = "TEMPLATE NAME";
    public static final String CREF_DIVISION = "DIVISION";
    public static final String CREF_SPECIALTY_CODE = "SPECIALTY CODE";
    public static final String CREF_PROCESSID = "ProcessID";
    public static final String CREF_STORE_NUMBER = "STORE NUMBER";
    public static final String CREF_MAIN_SVC_CATEGORY = "Main Service Category";
    public static final String CREF_CATEGORY = "CATEGORY";
    public static final String CREF_SUB_CATEGORY = "Sub-Category";
    public static final String CREF_SKILL = "Skill";
    public static final String CREF_DATE_CALC_METHOD = "DATE CALCULATION METHOD";
    public static final String CREF_MERCHANDISE_CODE = "MERCHANDISE CODE";
    public static final String CREF_INCIDENTID = "INCIDENTID";
    public static final String MANUAL_REVIEW = "Manual_Review";
    public static final String REPEAT_REPAIR ="RepeatRepair";

	private static final long serialVersionUID = -9074691139375635746L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "so_custom_reference_id")
	private Long soCustomRefId;
	
	@Column(name = "buyer_ref_type_id")
	private Integer buyerRefTypeId;
	
    @Formula(value = "(Select breftyp.ref_type from buyer_reference_type breftyp where breftyp.buyer_ref_type_id = buyer_ref_type_id)")
    @Column(insertable=false, updatable=false)
    private String buyerRefTypeName;
	
    @Formula(value = "(Select breftyp.updatable from buyer_reference_type breftyp where breftyp.buyer_ref_type_id = buyer_ref_type_id)")
    @Column(insertable=false, updatable=false)
    private boolean updatable;

    @Formula(value = "(Select breftyp.buyer_input from buyer_reference_type breftyp where breftyp.buyer_ref_type_id = buyer_ref_type_id)")
    @Column(insertable=false, updatable=false)
    private boolean buyerInput;

	@Column(name = "buyer_ref_value")
	private String buyerRefValue;

	public Long getSoCustomRefId() {
		return soCustomRefId;
	}

	public Integer getBuyerRefTypeId() {
		return buyerRefTypeId;
	}

    public String getBuyerRefTypeName() {
        return buyerRefTypeName;
    }

    public String getBuyerRefValue() {
		return buyerRefValue;
	}

	@XmlElement()
	public void setSoCustomRefId(Long soCustomRefId) {
		this.soCustomRefId = soCustomRefId;
	}

	@XmlElement()
	public void setBuyerRefTypeId(Integer buyerRefTypeId) {
		this.buyerRefTypeId = buyerRefTypeId;
	}

    @XmlElement()
    public void setBuyerRefTypeName(String buyerRefTypeName) {
        this.buyerRefTypeName = buyerRefTypeName;
    }

    @XmlElement()
	public void setBuyerRefValue(String buyerRefValue) {
		this.buyerRefValue = buyerRefValue;
	}

    @XmlElement()
    public void setUpdatable(boolean updatable) {
		this.updatable = updatable;
	}

	public boolean isUpdatable() {
		return updatable;
	}

	public boolean isBuyerInput() {
		return buyerInput;
}

	@XmlElement()
	public void setBuyerInput(boolean buyerInput) {
		this.buyerInput = buyerInput;
	}

}
