/**
 *
 */
package com.servicelive.domain.spn.network;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

import com.servicelive.domain.LoggableBaseDomain;
import com.servicelive.domain.common.Document;
import com.servicelive.domain.lookup.LookupSPNDocumentType;

/**
 * @author hoza
 *
 */
@Entity
@Table ( name = "spnet_document")
public class SPNDocument extends LoggableBaseDomain {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy=IDENTITY)
	@Column ( name = "id" , unique= true,nullable = false, insertable = true, updatable = true )
	private Integer id;

//	 @ManyToOne
//	 @JoinColumn(name = "spn_id", unique = false, nullable = true, insertable = false, updatable = false)
	@ManyToOne
    @JoinColumn(name="spn_id", insertable=false, updatable=false)
    @ForeignKey (name = "FK_SPN_DOC_SPN_ID")
	 private SPNHeader spn;
	 
	@ManyToOne( fetch=FetchType.LAZY, cascade= { CascadeType.REFRESH} )
	@JoinColumn(name = "document_id", insertable=true, updatable=false,nullable = false)
	@ForeignKey (name = "FK_SPN_DOC_DOC_ID")
	 private Document document;
	
	@Column(name="inactive_ind")
	private Boolean isInActive;
	
	 @ManyToOne
	 @JoinColumn (name="doc_type_id",unique =false ,nullable =false)
	 @ForeignKey (name = "FK_SPN_DOC_TYPE_ID")
	 private LookupSPNDocumentType docTypeId;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the spn
	 */
	public SPNHeader getSpn() {
		return spn;
	}
	/**
	 * @param spn the spn to set
	 */
	public void setSpn(SPNHeader spn) {
		this.spn = spn;
	}
	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}
	/**
	 * @param document the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}
	/**
	 * @return the isInActive
	 */
	public Boolean getIsInActive() {
		return isInActive;
	}
	/**
	 * @param isInActive the isInActive to set
	 */
	public void setIsInActive(Boolean isInActive) {
		this.isInActive = isInActive;
	}
	/**
	 * @return the docTypeId
	 */
	public LookupSPNDocumentType getDocTypeId() {
		return docTypeId;
	}
	/**
	 * @param docTypeId the docTypeId to set
	 */
	public void setDocTypeId(LookupSPNDocumentType docTypeId) {
		this.docTypeId = docTypeId;
	}
}
