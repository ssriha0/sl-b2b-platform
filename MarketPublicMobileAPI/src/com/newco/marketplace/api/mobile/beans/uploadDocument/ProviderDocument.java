package com.newco.marketplace.api.mobile.beans.uploadDocument;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import com.thoughtworks.xstream.annotations.XStreamAlias;


@XmlRootElement(name = "document")
@XStreamAlias("document")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProviderDocument {
		
		@XmlElement(name="documentType")
		private String documentType;
		
		@XmlElement(name="documentTypeId")
		private Integer documentTypeId;
		
		@XmlElement(name="documentName")
		private String documentName;
		
		@XmlElement(name="mediaType")
		private String mediaType;
		
		@XmlElement(name="documentDescription")
		private String documentDescription;
		
		@XmlElement(name="baseEncodedDocument")
		private String baseEncodedDocument;
		
		@XmlElement(name="documentId")
		private String documentId;
		
		@XmlElement(name="uploadedTime")
		private String uploadedTime;
		
		public String getDocumentType() {
			return documentType;
		}
		
		public void setDocumentType(String documentType) {
			this.documentType = documentType;
		}
		
		public Integer getDocumentTypeId() {
			return documentTypeId;
		}
		
		public void setDocumentTypeId(Integer documentTypeId) {
			this.documentTypeId = documentTypeId;
		}
		
		public String getDocumentName() {
			return documentName;
		}
		
		public void setDocumentName(String documentName) {
			this.documentName = documentName;
		}
		
		public String getMediaType() {
			return mediaType;
		}
		
		public void setMediaType(String mediaType) {
			this.mediaType = mediaType;
		}
		
		public String getDocumentDescription() {
			return documentDescription;
		}
		
		public void setDocumentDescription(String documentDescription) {
			this.documentDescription = documentDescription;
		}
		
		public String getBaseEncodedDocument() {
			return baseEncodedDocument;
		}
		
		public void setBaseEncodedDocument(String baseEncodedDocument) {
			this.baseEncodedDocument = baseEncodedDocument;
		}
		
		public String getDocumentId() {
			return documentId;
		}
		
		public void setDocumentId(String documentId) {
			this.documentId = documentId;
		}
		
		public String getUploadedTime() {
			return uploadedTime;
		}
		
		public void setUploadedTime(String uploadedTime) {
			this.uploadedTime = uploadedTime;
		}
		
		
			
}
