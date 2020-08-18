package com.servicelive.domain.common;
// default package

/*import com.servicelive.domain.common.AuditTask;
import com.servicelive.domain.common.BuyerDocument;
import com.servicelive.domain.common.EntityDocument;
import com.servicelive.domain.common.ResourceCredentialsDocument;
import com.servicelive.domain.common.SoHdr;
import com.servicelive.domain.common.SpnNetworkDocument;
import com.servicelive.domain.common.VendorCredentialsDocument;
import com.servicelive.domain.common.VendorHdr;
import com.servicelive.domain.common.VendorResourceDocument;*/
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.servicelive.domain.BaseDomain;
import com.servicelive.domain.spn.network.SPNDocument;
import com.servicelive.domain.spn.network.SPNProviderFirmDocument;


/**
 * Document entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table( name="document" )
public class Document extends BaseDomain {


    // Fields    

	private static final long serialVersionUID = 20090127L;

	@Id @GeneratedValue(strategy=IDENTITY)
    @Column(name="document_id", unique=true, nullable=false)
     private Integer documentId;
	
	 @Column (name="doc_category_id",unique =false ,nullable =false)
	 private Integer docCategoryId;
	 
	 @Column(name="descr")
     private String descr;
	 
	 @Column(name="title")
     private String title;
	 
	 @Column(name="file_name")
     private String documentFileName;
	 
	 @Column(name="format", length=50)
     private String documentContentType;
	 
	 @Column(name="source", length=50)
     private String source;
	 
	 @Column(name="keywords")
     private String keywords;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name="expire_date", length=19)
     private Date expireDate;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name="purge_date", length=19)
     private Date purgeDate;
	 
	 @Lob
	 @Column(name="document")
     private byte[] blobBytes;
   
	 @Column(name="role_id")
     private Integer roleId;
     
     @Column(name="entity_id")
     private Integer entityId;
     
     @Column(name="delete_ind")
     private Byte deleteInd;
     
     @Column(name="doc_url")
     private String docUrl;
     
     
     @Column(name="doc_path")
     private String docPath;
     
     @Column(name="doc_size")
     private Integer docSize;

     @OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
     private List<SPNDocument> spnDocuments;

     @OneToMany( fetch=FetchType.LAZY, cascade= { CascadeType.ALL} )
     private List<SPNProviderFirmDocument> spnProviderFirmDocuments;

    /** default constructor */
    public Document() {
    	super();
    }
    
    // Property accessors
    
    /**
     * 
     * @return Integer
     */
    public Integer getDocumentId() {
        return this.documentId;
    }
    /**
     * 
     * @param documentId
     */
    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    /**
     * 
     * @return String
     */
   	public String getDescr() {
        return this.descr;
    }
   	/**
   	 * 
   	 * @param descr
   	 */
    public void setDescr(String descr) {
        this.descr = descr;
    }
    /**
     * 
     * @return String
     */
    public String getTitle() {
        return this.title;
    }
    /**
     * 
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return String
     */
    public String getSource() {
        return this.source;
    }
    /**
     * 
     * @param source
     */
    public void setSource(String source) {
        this.source = source;
    }
    /**
     * 
     * @return String
     */
    public String getKeywords() {
        return this.keywords;
    }
    /**
     * 
     * @param keywords
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    /**
     * 
     * @return Date
     */
    public Date getExpireDate() {
        return this.expireDate;
    }
    /**
     * 
     * @param expireDate
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
    /**
     * 
     * @return Date
     */
    public Date getPurgeDate() {
        return this.purgeDate;
    }
    /**
     * 
     * @param purgeDate
     */
    public void setPurgeDate(Date purgeDate) {
        this.purgeDate = purgeDate;
    }
    
 
    /**
	 * @return the document
	 */
	public byte[] getBlobBytes() {
		return blobBytes;
	}





	/**
	 * @param document the document to set
	 */
	public void setBlobBytes(byte[] document) {
		this.blobBytes = document;
	}





	/**
	 * 
	 * @return Integer
	 */
	public Integer getRoleId() {
        return this.roleId;
    }
    /**
     * 
     * @param roleId
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
    /**
     * 
     * @return Integer
     */
    public Integer getEntityId() {
        return this.entityId;
    }
    /**
     * 
     * @param entityId
     */
    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }
    
    /**
     * 
     * @return Byte
     */
    public Byte getDeleteInd() {
        return this.deleteInd;
    }
    /**
     * 
     * @param deleteInd
     */
    public void setDeleteInd(Byte deleteInd) {
        this.deleteInd = deleteInd;
    }
    /**
     * 
     * @return String
     */
    public String getDocUrl() {
        return this.docUrl;
    }
    /**
     * 
     * @param docUrl
     */
    public void setDocUrl(String docUrl) {
        this.docUrl = docUrl;
    }
    
    /**
     *  
     * @return String
     */
    public String getDocPath() {
        return this.docPath;
    }
    /**
     * 
     * @param docPath
     */
    public void setDocPath(String docPath) {
        this.docPath = docPath;
    }
    /**
     * 
     * @return Integer
     */
    public Integer getDocSize() {
        return this.docSize;
    }
    /**
     * 
     * @param docSize
     */
    public void setDocSize(Integer docSize) {
        this.docSize = docSize;
    }
    /**
     * 
     * @return String
     */
	public String getDocumentFileName() {
		return documentFileName;
	}
	/**
	 * 
	 * @param documentFileName
	 */
	public void setDocumentFileName(String documentFileName) {
		this.documentFileName = documentFileName;
	}
	/**
	 * 
	 * @return String
	 */
	public String getDocumentContentType() {
		return documentContentType;
	}
	/**
	 * 
	 * @param documentContentType
	 */
	public void setDocumentContentType(String documentContentType) {
		this.documentContentType = documentContentType;
	}


	/**
	 * @return the docCategoryId
	 */
	public Integer getDocCategoryId() {
		return docCategoryId;
	}

	/**
	 * @param docCategoryId the docCategoryId to set
	 */
	public void setDocCategoryId(Integer docCategoryId) {
		this.docCategoryId = docCategoryId;
	}

	/**
	 * @return the spnDocuments
	 */
	public List<SPNDocument> getSpnDocuments() {
		return spnDocuments;
	}

	/**
	 * @param spnDocuments the spnDocuments to set
	 */
	public void setSpnDocuments(List<SPNDocument> spnDocuments) {
		this.spnDocuments = spnDocuments;
	}

	/**
	 * @return the spnProviderFirmDocuments
	 */
	public List<SPNProviderFirmDocument> getSpnProviderFirmDocuments() {
		return spnProviderFirmDocuments;
	}

	/**
	 * @param spnProviderFirmDocuments the spnProviderFirmDocuments to set
	 */
	public void setSpnProviderFirmDocuments(
			List<SPNProviderFirmDocument> spnProviderFirmDocuments) {
		this.spnProviderFirmDocuments = spnProviderFirmDocuments;
	}
    
    /*
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<VendorCredentialsDocument> getVendorCredentialsDocuments() {
        return this.vendorCredentialsDocuments;
    }
    
    public void setVendorCredentialsDocuments(Set<VendorCredentialsDocument> vendorCredentialsDocuments) {
        this.vendorCredentialsDocuments = vendorCredentialsDocuments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<SoHdr> getSoHdrs() {
        return this.soHdrs;
    }
    
    public void setSoHdrs(Set<SoHdr> soHdrs) {
        this.soHdrs = soHdrs;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<SpnNetworkDocument> getSpnNetworkDocuments() {
        return this.spnNetworkDocuments;
    }
    
    public void setSpnNetworkDocuments(Set<SpnNetworkDocument> spnNetworkDocuments) {
        this.spnNetworkDocuments = spnNetworkDocuments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<AuditTask> getAuditTasks() {
        return this.auditTasks;
    }
    
    public void setAuditTasks(Set<AuditTask> auditTasks) {
        this.auditTasks = auditTasks;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<ResourceCredentialsDocument> getResourceCredentialsDocuments() {
        return this.resourceCredentialsDocuments;
    }
    
    public void setResourceCredentialsDocuments(Set<ResourceCredentialsDocument> resourceCredentialsDocuments) {
        this.resourceCredentialsDocuments = resourceCredentialsDocuments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<EntityDocument> getEntityDocuments() {
        return this.entityDocuments;
    }
    
    public void setEntityDocuments(Set<EntityDocument> entityDocuments) {
        this.entityDocuments = entityDocuments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<VendorResourceDocument> getVendorResourceDocuments() {
        return this.vendorResourceDocuments;
    }
    
    public void setVendorResourceDocuments(Set<VendorResourceDocument> vendorResourceDocuments) {
        this.vendorResourceDocuments = vendorResourceDocuments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<BuyerDocument> getBuyerDocuments() {
        return this.buyerDocuments;
    }
    
    public void setBuyerDocuments(Set<BuyerDocument> buyerDocuments) {
        this.buyerDocuments = buyerDocuments;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="document")

    public Set<BuyerDocument> getBuyerDocuments_1() {
        return this.buyerDocuments_1;
    }
    
    public void setBuyerDocuments_1(Set<BuyerDocument> buyerDocuments_1) {
        this.buyerDocuments_1 = buyerDocuments_1;
    }
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="vendor_id")

public VendorHdr getVendorHdr() {
    return this.vendorHdr;
}

public void setVendorHdr(VendorHdr vendorHdr) {
    this.vendorHdr = vendorHdr;
}



*/




}