package com.newco.marketplace.dto.vo.spn;

import com.sears.os.vo.SerializableBaseVO;

/**
 * SPNCriteriaDBVO is used only for mapping SPN criteria
 * data to the database table.
 * 
 * @author Michael J. Hayes, Sogeti USA, LLC
 *
 * $Revision$ $Author$ $Date$
 */

/*
 * Maintenance History: See bottom of file.
 */
public class SPNCriteriaDBVO extends SerializableBaseVO {

	private static final long serialVersionUID = 6021076001337335232L;

	private Integer spnId;
	private Integer criteriaTypeId;
	private Integer criteriaId;
	private Integer field1;
	private Integer field2;
	private Integer field3;
	private Integer field4;
	private Integer field5;
	private Double field6;
	private Double field7;
	private Double field8;
	private Double field9;
	private Double field10;
	private boolean field11;
	private boolean field12;
	private boolean field13;
	private boolean field14;
	private boolean field15;
	private Integer field11Int;
	private Integer field12Int;
	private Integer field13Int;
	private Integer field14Int;
	private Integer field15Int;
	/**
	 * @return the spnId
	 */
	public Integer getSpnId() {
		return spnId;
	}
	/**
	 * @param spnId the spnId to set
	 */
	public void setSpnId(Integer spnId) {
		this.spnId = spnId;
	}
	/**
	 * @return the criteriaTypeId
	 */
	public Integer getCriteriaTypeId() {
		return criteriaTypeId;
	}
	/**
	 * @param criteriaTypeId the criteriaTypeId to set
	 */
	public void setCriteriaTypeId(Integer criteriaTypeId) {
		this.criteriaTypeId = criteriaTypeId;
	}
	/**
	 * @return the criteriaId
	 */
	public Integer getCriteriaId() {
		return criteriaId;
	}
	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(Integer criteriaId) {
		this.criteriaId = criteriaId;
	}
	/**
	 * @return the field1
	 */
	public Integer getField1() {
		return field1;
	}
	/**
	 * @param field1 the field1 to set
	 */
	public void setField1(Integer field1) {
		this.field1 = field1;
	}
	/**
	 * @return the field2
	 */
	public Integer getField2() {
		return field2;
	}
	/**
	 * @param field2 the field2 to set
	 */
	public void setField2(Integer field2) {
		this.field2 = field2;
	}
	/**
	 * @return the field3
	 */
	public Integer getField3() {
		return field3;
	}
	/**
	 * @param field3 the field3 to set
	 */
	public void setField3(Integer field3) {
		this.field3 = field3;
	}

	/**
	 * @return the field4
	 */
	public Integer getField4() {
		return field4;
	}
	/**
	 * @param field4 the field4 to set
	 */
	public void setField4(Integer field4) {
		this.field4 = field4;
	}
	/**
	 * @return the field5
	 */
	public Integer getField5() {
		return field5;
	}
	/**
	 * @param field5 the field5 to set
	 */
	public void setField5(Integer field5) {
		this.field5 = field5;
	}
	/**
	 * @return the field6
	 */
	public Double getField6() {
		return field6;
	}
	/**
	 * @param field6 the field6 to set
	 */
	public void setField6(Double field6) {
		this.field6 = field6;
	}
	/**
	 * @return the field7
	 */
	public Double getField7() {
		return field7;
	}
	/**
	 * @param field7 the field7 to set
	 */
	public void setField7(Double field7) {
		this.field7 = field7;
	}
	/**
	 * @return the field8
	 */
	public Double getField8() {
		return field8;
	}
	/**
	 * @param field8 the field8 to set
	 */
	public void setField8(Double field8) {
		this.field8 = field8;
	}
	/**
	 * @return the field9
	 */
	public Double getField9() {
		return field9;
	}
	/**
	 * @param field9 the field9 to set
	 */
	public void setField9(Double field9) {
		this.field9 = field9;
	}
	/**
	 * @return the field10
	 */
	public Double getField10() {
		return field10;
	}
	/**
	 * @param field10 the field10 to set
	 */
	public void setField10(Double field10) {
		this.field10 = field10;
	}
	/**
	 * @return the field11
	 */
	public boolean isField11() {
		return field11;
	}
	/**
	 * @param field11 the field11 to set
	 */
	public void setField11(boolean field11) {
		this.field11 = field11;
		if (field11) {
			field11Int = new Integer(1); 
		} else {
			field11Int = new Integer(0);
		}
	}
	/**
	 * @return the field12
	 */
	public boolean isField12() {
		return field12;
	}
	/**
	 * @param field12 the field12 to set
	 */
	public void setField12(boolean field12) {
		this.field12 = field12;
		if (field12) {
			field12Int = new Integer(1); 
		} else {
			field12Int = new Integer(0);
		}
	}
	/**
	 * @return the field13
	 */
	public boolean isField13() {
		return field13;
	}
	/**
	 * @param field13 the field13 to set
	 */
	public void setField13(boolean field13) {
		this.field13 = field13;
		if (field13) {
			field13Int = new Integer(1); 
		} else {
			field13Int = new Integer(0);
		}
	}
	/**
	 * @return the field14
	 */
	public boolean isField14() {
		return field14;
	}
	/**
	 * @param field14 the field14 to set
	 */
	public void setField14(boolean field14) {
		this.field14 = field14;
		if (field14) {
			field14Int = new Integer(1); 
		} else {
			field14Int = new Integer(0);
		}
	}
	/**
	 * @return the field15
	 */
	public boolean isField15() {
		return field15;
	}
	/**
	 * @param field15 the field15 to set
	 */
	public void setField15(boolean field15) {
		this.field15 = field15;
		if (field15) {
			field15Int = new Integer(1); 
		} else {
			field15Int = new Integer(0);
		}
	}
	/**
	 * @return the field12Int
	 */
	public Integer getField12Int() {
		return field12Int;
	}
	/**
	 * @param field12Int the field12Int to set
	 */
	public void setField12Int(Integer field12Int) {
		this.field12Int = field12Int;
		if (null != field12Int && field12Int.intValue() == 1) {
			field12 = true;
		} else {
			field12 = false;
		}
	}
	/**
	 * @return the field13Int
	 */
	public Integer getField13Int() {
		return field13Int;
	}
	/**
	 * @param field13Int the field13Int to set
	 */
	public void setField13Int(Integer field13Int) {
		this.field13Int = field13Int;
		if (null != field13Int && field13Int.intValue() == 1) {
			field13 = true;
		} else {
			field13 = false;
		}
	}
	/**
	 * @return the field14Int
	 */
	public Integer getField14Int() {
		return field14Int;
	}
	/**
	 * @param field14Int the field14Int to set
	 */
	public void setField14Int(Integer field14Int) {
		this.field14Int = field14Int;
		if (null != field14Int && field14Int.intValue() == 1) {
			field14 = true;
		} else {
			field14 = false;
		}
	}
	/**
	 * @return the field15Int
	 */
	public Integer getField15Int() {
		return field15Int;
	}
	/**
	 * @param field15Int the field15Int to set
	 */
	public void setField15Int(Integer field15Int) {
		this.field15Int = field15Int;
		if (null != field15Int && field15Int.intValue() == 1) {
			field15 = true;
		} else {
			field15 = false;
		}
	}
	/**
	 * @return the field11Int
	 */
	public Integer getField11Int() {
		return field11Int;
	}
	/**
	 * @param field11Int the field11Int to set
	 */
	public void setField11Int(Integer field11Int) {
		this.field11Int = field11Int;
		if (null != field11Int && field11Int.intValue() == 1) {
			field11 = true;
		} else {
			field11 = false;
		}
	}
	
	
}
/*
 * Maintenance History:
 * $Log: SPNCriteriaDBVO.java,v $
 * Revision 1.2  2008/05/02 21:23:58  glacy
 * Shyam: Merged I19_OMS branch to HEAD.
 *
 * Revision 1.1.2.2  2008/04/10 19:07:14  mhaye05
 * added additional attributes for skills
 *
 * Revision 1.1.2.1  2008/04/09 14:44:10  mhaye05
 * Initial check in
 *
 */