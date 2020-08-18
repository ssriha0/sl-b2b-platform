package com.newco.marketplace.vo.mobile;

import java.util.List;

import com.newco.marketplace.api.mobile.beans.sodetails.Document;
import com.newco.marketplace.api.mobile.beans.sodetails.PartCoverage;
import com.newco.marketplace.api.mobile.beans.sodetails.PartSource;
import com.newco.marketplace.dto.vo.LookupVO;

public class InvoiceDetailsVO {
	
	private List<InvoicePartsVO> invoicePartsVOs;
	private List<PartSource> partSourceTypes;
	private List<LookupVO> partStatusTypes;
	private List<Document> invoiceDocuments;	
	private List<Integer> invoiceDocumentIds;	

	private String partCoverage;
	private String partSource;
	private String nonSearsSource;
	private String invoiceNumber;

	public String getPartCoverage() {
		return partCoverage;
	}
	public void setPartCoverage(String partCoverage) {
		this.partCoverage = partCoverage;
	}
	public String getPartSource() {
		return partSource;
	}
	public void setPartSource(String partSource) {
		this.partSource = partSource;
	}
	public String getNonSearsSource() {
		return nonSearsSource;
	}
	public void setNonSearsSource(String nonSearsSource) {
		this.nonSearsSource = nonSearsSource;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public List<InvoicePartsVO> getInvoicePartsVOs() {
		return invoicePartsVOs;
	}
	public void setInvoicePartsVOs(List<InvoicePartsVO> invoicePartsVOs) {
		this.invoicePartsVOs = invoicePartsVOs;
	}
	public List<PartSource> getPartSourceTypes() {
		return partSourceTypes;
	}
	public void setPartSourceTypes(List<PartSource> partSourceTypes) {
		this.partSourceTypes = partSourceTypes;
	}
	
	public List<LookupVO> getPartStatusTypes() {
		return partStatusTypes;
	}
	public void setPartStatusTypes(List<LookupVO> partStatusTypes) {
		this.partStatusTypes = partStatusTypes;
	}
	public List<Document> getInvoiceDocuments() {
		return invoiceDocuments;
	}
	public void setInvoiceDocuments(List<Document> invoiceDocuments) {
		this.invoiceDocuments = invoiceDocuments;
	}
	public List<Integer> getInvoiceDocumentIds() {
		return invoiceDocumentIds;
	}
	public void setInvoiceDocumentIds(List<Integer> invoiceDocumentIds) {
		this.invoiceDocumentIds = invoiceDocumentIds;
	}
	
}
