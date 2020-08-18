package com.newco.marketplace.vo.provider;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.sears.os.vo.SerializableBaseVO;


public class InsuranceTypesVO extends SerializableBaseVO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4461058131597060168L;
	private String typeName;
	private String categoryName;
	private String source;
	private String name;
	private java.sql.Date expirationDate;
	private int vendorCredentialId; 
	private int vendorId;
	private int currentDocumentId;
    private String editURL = null;
    private String docURL = null;
    private String buttonType; 
    private ArrayList insuranceList;
    private boolean addPolicy;
    private int wfStateId = -1;
    private String statusDesc;
        
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public java.sql.Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(java.sql.Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public int getVendorCredentialId() {
		return vendorCredentialId;
	}
	public void setVendorCredentialId(int vendorCredentialId) {
		this.vendorCredentialId = vendorCredentialId;
	}
	public int getVendorId() {
		return vendorId;
	}
	public void setVendorId(int vendorId) {
		this.vendorId = vendorId;
	}
	public int getCurrentDocumentId() {
		return currentDocumentId;
	}
	public void setCurrentDocumentId(int currentDocumentId) {
		this.currentDocumentId = currentDocumentId;
	}

        public String getEditURL() {
            return this.editURL;
        }
        public void setEditURL(String editURL) {
            this.editURL = editURL;
        }
        
        public String getDocURL() {
            return this.docURL;
        }
        public void setDocURL(String docURL) {
        	String val = docURL;
        	if(StringUtils.indexOf(docURL, '\'')> 0 ) {
        		val = StringUtils.replaceChars(docURL, '\'',  ' ');
        	}
            this.docURL = val;
        }
		public String getButtonType() {
			return buttonType;
		}
		public void setButtonType(String buttonType) {
			this.buttonType = buttonType;
		}
		public ArrayList getInsuranceList() {
			return insuranceList;
		}
		public void setInsuranceList(ArrayList insuranceList) {
			this.insuranceList = insuranceList;
		}
		public boolean isAddPolicy() {
			return addPolicy;
		}
		public void setAddPolicy(boolean addPolicy) {
			this.addPolicy = addPolicy;
		}
		public int getWfStateId() {
			return wfStateId;
		}
		public void setWfStateId(int wfStateId) {
			this.wfStateId = wfStateId;
		}
		public String getStatusDesc() {
			return statusDesc;
		}
		public void setStatusDesc(String statusDesc) {
			this.statusDesc = statusDesc;
		}
}
