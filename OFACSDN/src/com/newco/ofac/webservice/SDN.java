/**
 * SDN.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.newco.ofac.webservice;

public class SDN  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String uniqueRecord;

    private java.lang.String nameOfSDN;

    private java.lang.String typeOfSDN;

    private java.lang.String sanctionsProgramName;

    private java.lang.String titleOfAnIndividual;

    private java.lang.String vesselCallSign;

    private java.lang.String vesselType;

    private java.lang.String vesselTonnage;

    private java.lang.String grossRegisteredTonnage;

    private java.lang.String vesselFlag;

    private java.lang.String vesselOwner;

    private java.lang.String remarksOnSDN;

    private java.lang.String streetAddress;

    private java.lang.String city;

    private java.lang.String country;

    private java.lang.String remarksOnAddress;

    private java.lang.String typeOfAlternateIdentity;

    private java.lang.String alternateIdentityName;

    private java.lang.String remarksOnAlternative;

    public SDN() {
    }

    public SDN(
           java.lang.String uniqueRecord,
           java.lang.String nameOfSDN,
           java.lang.String typeOfSDN,
           java.lang.String sanctionsProgramName,
           java.lang.String titleOfAnIndividual,
           java.lang.String vesselCallSign,
           java.lang.String vesselType,
           java.lang.String vesselTonnage,
           java.lang.String grossRegisteredTonnage,
           java.lang.String vesselFlag,
           java.lang.String vesselOwner,
           java.lang.String remarksOnSDN,
           java.lang.String streetAddress,
           java.lang.String city,
           java.lang.String country,
           java.lang.String remarksOnAddress,
           java.lang.String typeOfAlternateIdentity,
           java.lang.String alternateIdentityName,
           java.lang.String remarksOnAlternative) {
           this.uniqueRecord = uniqueRecord;
           this.nameOfSDN = nameOfSDN;
           this.typeOfSDN = typeOfSDN;
           this.sanctionsProgramName = sanctionsProgramName;
           this.titleOfAnIndividual = titleOfAnIndividual;
           this.vesselCallSign = vesselCallSign;
           this.vesselType = vesselType;
           this.vesselTonnage = vesselTonnage;
           this.grossRegisteredTonnage = grossRegisteredTonnage;
           this.vesselFlag = vesselFlag;
           this.vesselOwner = vesselOwner;
           this.remarksOnSDN = remarksOnSDN;
           this.streetAddress = streetAddress;
           this.city = city;
           this.country = country;
           this.remarksOnAddress = remarksOnAddress;
           this.typeOfAlternateIdentity = typeOfAlternateIdentity;
           this.alternateIdentityName = alternateIdentityName;
           this.remarksOnAlternative = remarksOnAlternative;
    }


    /**
     * Gets the uniqueRecord value for this SDN.
     * 
     * @return uniqueRecord
     */
    public java.lang.String getUniqueRecord() {
        return uniqueRecord;
    }


    /**
     * Sets the uniqueRecord value for this SDN.
     * 
     * @param uniqueRecord
     */
    public void setUniqueRecord(java.lang.String uniqueRecord) {
        this.uniqueRecord = uniqueRecord;
    }


    /**
     * Gets the nameOfSDN value for this SDN.
     * 
     * @return nameOfSDN
     */
    public java.lang.String getNameOfSDN() {
        return nameOfSDN;
    }


    /**
     * Sets the nameOfSDN value for this SDN.
     * 
     * @param nameOfSDN
     */
    public void setNameOfSDN(java.lang.String nameOfSDN) {
        this.nameOfSDN = nameOfSDN;
    }


    /**
     * Gets the typeOfSDN value for this SDN.
     * 
     * @return typeOfSDN
     */
    public java.lang.String getTypeOfSDN() {
        return typeOfSDN;
    }


    /**
     * Sets the typeOfSDN value for this SDN.
     * 
     * @param typeOfSDN
     */
    public void setTypeOfSDN(java.lang.String typeOfSDN) {
        this.typeOfSDN = typeOfSDN;
    }


    /**
     * Gets the sanctionsProgramName value for this SDN.
     * 
     * @return sanctionsProgramName
     */
    public java.lang.String getSanctionsProgramName() {
        return sanctionsProgramName;
    }


    /**
     * Sets the sanctionsProgramName value for this SDN.
     * 
     * @param sanctionsProgramName
     */
    public void setSanctionsProgramName(java.lang.String sanctionsProgramName) {
        this.sanctionsProgramName = sanctionsProgramName;
    }


    /**
     * Gets the titleOfAnIndividual value for this SDN.
     * 
     * @return titleOfAnIndividual
     */
    public java.lang.String getTitleOfAnIndividual() {
        return titleOfAnIndividual;
    }


    /**
     * Sets the titleOfAnIndividual value for this SDN.
     * 
     * @param titleOfAnIndividual
     */
    public void setTitleOfAnIndividual(java.lang.String titleOfAnIndividual) {
        this.titleOfAnIndividual = titleOfAnIndividual;
    }


    /**
     * Gets the vesselCallSign value for this SDN.
     * 
     * @return vesselCallSign
     */
    public java.lang.String getVesselCallSign() {
        return vesselCallSign;
    }


    /**
     * Sets the vesselCallSign value for this SDN.
     * 
     * @param vesselCallSign
     */
    public void setVesselCallSign(java.lang.String vesselCallSign) {
        this.vesselCallSign = vesselCallSign;
    }


    /**
     * Gets the vesselType value for this SDN.
     * 
     * @return vesselType
     */
    public java.lang.String getVesselType() {
        return vesselType;
    }


    /**
     * Sets the vesselType value for this SDN.
     * 
     * @param vesselType
     */
    public void setVesselType(java.lang.String vesselType) {
        this.vesselType = vesselType;
    }


    /**
     * Gets the vesselTonnage value for this SDN.
     * 
     * @return vesselTonnage
     */
    public java.lang.String getVesselTonnage() {
        return vesselTonnage;
    }


    /**
     * Sets the vesselTonnage value for this SDN.
     * 
     * @param vesselTonnage
     */
    public void setVesselTonnage(java.lang.String vesselTonnage) {
        this.vesselTonnage = vesselTonnage;
    }


    /**
     * Gets the grossRegisteredTonnage value for this SDN.
     * 
     * @return grossRegisteredTonnage
     */
    public java.lang.String getGrossRegisteredTonnage() {
        return grossRegisteredTonnage;
    }


    /**
     * Sets the grossRegisteredTonnage value for this SDN.
     * 
     * @param grossRegisteredTonnage
     */
    public void setGrossRegisteredTonnage(java.lang.String grossRegisteredTonnage) {
        this.grossRegisteredTonnage = grossRegisteredTonnage;
    }


    /**
     * Gets the vesselFlag value for this SDN.
     * 
     * @return vesselFlag
     */
    public java.lang.String getVesselFlag() {
        return vesselFlag;
    }


    /**
     * Sets the vesselFlag value for this SDN.
     * 
     * @param vesselFlag
     */
    public void setVesselFlag(java.lang.String vesselFlag) {
        this.vesselFlag = vesselFlag;
    }


    /**
     * Gets the vesselOwner value for this SDN.
     * 
     * @return vesselOwner
     */
    public java.lang.String getVesselOwner() {
        return vesselOwner;
    }


    /**
     * Sets the vesselOwner value for this SDN.
     * 
     * @param vesselOwner
     */
    public void setVesselOwner(java.lang.String vesselOwner) {
        this.vesselOwner = vesselOwner;
    }


    /**
     * Gets the remarksOnSDN value for this SDN.
     * 
     * @return remarksOnSDN
     */
    public java.lang.String getRemarksOnSDN() {
        return remarksOnSDN;
    }


    /**
     * Sets the remarksOnSDN value for this SDN.
     * 
     * @param remarksOnSDN
     */
    public void setRemarksOnSDN(java.lang.String remarksOnSDN) {
        this.remarksOnSDN = remarksOnSDN;
    }


    /**
     * Gets the streetAddress value for this SDN.
     * 
     * @return streetAddress
     */
    public java.lang.String getStreetAddress() {
        return streetAddress;
    }


    /**
     * Sets the streetAddress value for this SDN.
     * 
     * @param streetAddress
     */
    public void setStreetAddress(java.lang.String streetAddress) {
        this.streetAddress = streetAddress;
    }


    /**
     * Gets the city value for this SDN.
     * 
     * @return city
     */
    public java.lang.String getCity() {
        return city;
    }


    /**
     * Sets the city value for this SDN.
     * 
     * @param city
     */
    public void setCity(java.lang.String city) {
        this.city = city;
    }


    /**
     * Gets the country value for this SDN.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this SDN.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the remarksOnAddress value for this SDN.
     * 
     * @return remarksOnAddress
     */
    public java.lang.String getRemarksOnAddress() {
        return remarksOnAddress;
    }


    /**
     * Sets the remarksOnAddress value for this SDN.
     * 
     * @param remarksOnAddress
     */
    public void setRemarksOnAddress(java.lang.String remarksOnAddress) {
        this.remarksOnAddress = remarksOnAddress;
    }


    /**
     * Gets the typeOfAlternateIdentity value for this SDN.
     * 
     * @return typeOfAlternateIdentity
     */
    public java.lang.String getTypeOfAlternateIdentity() {
        return typeOfAlternateIdentity;
    }


    /**
     * Sets the typeOfAlternateIdentity value for this SDN.
     * 
     * @param typeOfAlternateIdentity
     */
    public void setTypeOfAlternateIdentity(java.lang.String typeOfAlternateIdentity) {
        this.typeOfAlternateIdentity = typeOfAlternateIdentity;
    }


    /**
     * Gets the alternateIdentityName value for this SDN.
     * 
     * @return alternateIdentityName
     */
    public java.lang.String getAlternateIdentityName() {
        return alternateIdentityName;
    }


    /**
     * Sets the alternateIdentityName value for this SDN.
     * 
     * @param alternateIdentityName
     */
    public void setAlternateIdentityName(java.lang.String alternateIdentityName) {
        this.alternateIdentityName = alternateIdentityName;
    }


    /**
     * Gets the remarksOnAlternative value for this SDN.
     * 
     * @return remarksOnAlternative
     */
    public java.lang.String getRemarksOnAlternative() {
        return remarksOnAlternative;
    }


    /**
     * Sets the remarksOnAlternative value for this SDN.
     * 
     * @param remarksOnAlternative
     */
    public void setRemarksOnAlternative(java.lang.String remarksOnAlternative) {
        this.remarksOnAlternative = remarksOnAlternative;
    }

    private java.lang.Object __equalsCalc = null;
    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SDN)) return false;
        SDN other = (SDN) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.uniqueRecord==null && other.getUniqueRecord()==null) || 
             (this.uniqueRecord!=null &&
              this.uniqueRecord.equals(other.getUniqueRecord()))) &&
            ((this.nameOfSDN==null && other.getNameOfSDN()==null) || 
             (this.nameOfSDN!=null &&
              this.nameOfSDN.equals(other.getNameOfSDN()))) &&
            ((this.typeOfSDN==null && other.getTypeOfSDN()==null) || 
             (this.typeOfSDN!=null &&
              this.typeOfSDN.equals(other.getTypeOfSDN()))) &&
            ((this.sanctionsProgramName==null && other.getSanctionsProgramName()==null) || 
             (this.sanctionsProgramName!=null &&
              this.sanctionsProgramName.equals(other.getSanctionsProgramName()))) &&
            ((this.titleOfAnIndividual==null && other.getTitleOfAnIndividual()==null) || 
             (this.titleOfAnIndividual!=null &&
              this.titleOfAnIndividual.equals(other.getTitleOfAnIndividual()))) &&
            ((this.vesselCallSign==null && other.getVesselCallSign()==null) || 
             (this.vesselCallSign!=null &&
              this.vesselCallSign.equals(other.getVesselCallSign()))) &&
            ((this.vesselType==null && other.getVesselType()==null) || 
             (this.vesselType!=null &&
              this.vesselType.equals(other.getVesselType()))) &&
            ((this.vesselTonnage==null && other.getVesselTonnage()==null) || 
             (this.vesselTonnage!=null &&
              this.vesselTonnage.equals(other.getVesselTonnage()))) &&
            ((this.grossRegisteredTonnage==null && other.getGrossRegisteredTonnage()==null) || 
             (this.grossRegisteredTonnage!=null &&
              this.grossRegisteredTonnage.equals(other.getGrossRegisteredTonnage()))) &&
            ((this.vesselFlag==null && other.getVesselFlag()==null) || 
             (this.vesselFlag!=null &&
              this.vesselFlag.equals(other.getVesselFlag()))) &&
            ((this.vesselOwner==null && other.getVesselOwner()==null) || 
             (this.vesselOwner!=null &&
              this.vesselOwner.equals(other.getVesselOwner()))) &&
            ((this.remarksOnSDN==null && other.getRemarksOnSDN()==null) || 
             (this.remarksOnSDN!=null &&
              this.remarksOnSDN.equals(other.getRemarksOnSDN()))) &&
            ((this.streetAddress==null && other.getStreetAddress()==null) || 
             (this.streetAddress!=null &&
              this.streetAddress.equals(other.getStreetAddress()))) &&
            ((this.city==null && other.getCity()==null) || 
             (this.city!=null &&
              this.city.equals(other.getCity()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.remarksOnAddress==null && other.getRemarksOnAddress()==null) || 
             (this.remarksOnAddress!=null &&
              this.remarksOnAddress.equals(other.getRemarksOnAddress()))) &&
            ((this.typeOfAlternateIdentity==null && other.getTypeOfAlternateIdentity()==null) || 
             (this.typeOfAlternateIdentity!=null &&
              this.typeOfAlternateIdentity.equals(other.getTypeOfAlternateIdentity()))) &&
            ((this.alternateIdentityName==null && other.getAlternateIdentityName()==null) || 
             (this.alternateIdentityName!=null &&
              this.alternateIdentityName.equals(other.getAlternateIdentityName()))) &&
            ((this.remarksOnAlternative==null && other.getRemarksOnAlternative()==null) || 
             (this.remarksOnAlternative!=null &&
              this.remarksOnAlternative.equals(other.getRemarksOnAlternative())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    @Override
	public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getUniqueRecord() != null) {
            _hashCode += getUniqueRecord().hashCode();
        }
        if (getNameOfSDN() != null) {
            _hashCode += getNameOfSDN().hashCode();
        }
        if (getTypeOfSDN() != null) {
            _hashCode += getTypeOfSDN().hashCode();
        }
        if (getSanctionsProgramName() != null) {
            _hashCode += getSanctionsProgramName().hashCode();
        }
        if (getTitleOfAnIndividual() != null) {
            _hashCode += getTitleOfAnIndividual().hashCode();
        }
        if (getVesselCallSign() != null) {
            _hashCode += getVesselCallSign().hashCode();
        }
        if (getVesselType() != null) {
            _hashCode += getVesselType().hashCode();
        }
        if (getVesselTonnage() != null) {
            _hashCode += getVesselTonnage().hashCode();
        }
        if (getGrossRegisteredTonnage() != null) {
            _hashCode += getGrossRegisteredTonnage().hashCode();
        }
        if (getVesselFlag() != null) {
            _hashCode += getVesselFlag().hashCode();
        }
        if (getVesselOwner() != null) {
            _hashCode += getVesselOwner().hashCode();
        }
        if (getRemarksOnSDN() != null) {
            _hashCode += getRemarksOnSDN().hashCode();
        }
        if (getStreetAddress() != null) {
            _hashCode += getStreetAddress().hashCode();
        }
        if (getCity() != null) {
            _hashCode += getCity().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getRemarksOnAddress() != null) {
            _hashCode += getRemarksOnAddress().hashCode();
        }
        if (getTypeOfAlternateIdentity() != null) {
            _hashCode += getTypeOfAlternateIdentity().hashCode();
        }
        if (getAlternateIdentityName() != null) {
            _hashCode += getAlternateIdentityName().hashCode();
        }
        if (getRemarksOnAlternative() != null) {
            _hashCode += getRemarksOnAlternative().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SDN.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.webservicex.net/", "SDN"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("uniqueRecord");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "UniqueRecord"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("nameOfSDN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "NameOfSDN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeOfSDN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "TypeOfSDN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sanctionsProgramName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "SanctionsProgramName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titleOfAnIndividual");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "TitleOfAnIndividual"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vesselCallSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "VesselCallSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vesselType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "VesselType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vesselTonnage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "VesselTonnage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("grossRegisteredTonnage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "GrossRegisteredTonnage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vesselFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "VesselFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vesselOwner");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "VesselOwner"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remarksOnSDN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "RemarksOnSDN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "StreetAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("city");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "City"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "Country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remarksOnAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "RemarksOnAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeOfAlternateIdentity");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "TypeOfAlternateIdentity"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("alternateIdentityName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "AlternateIdentityName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("remarksOnAlternative");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "RemarksOnAlternative"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
