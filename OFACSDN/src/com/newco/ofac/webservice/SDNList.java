/**
 * SDNList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.newco.ofac.webservice;

public class SDNList  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int totalRecords;

    private com.newco.ofac.webservice.SDN[] SDNLists;

    public SDNList() {
    }

    public SDNList(
           int totalRecords,
           com.newco.ofac.webservice.SDN[] SDNLists) {
           this.totalRecords = totalRecords;
           this.SDNLists = SDNLists;
    }


    /**
     * Gets the totalRecords value for this SDNList.
     * 
     * @return totalRecords
     */
    public int getTotalRecords() {
        return totalRecords;
    }


    /**
     * Sets the totalRecords value for this SDNList.
     * 
     * @param totalRecords
     */
    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }


    /**
     * Gets the SDNLists value for this SDNList.
     * 
     * @return SDNLists
     */
    public com.newco.ofac.webservice.SDN[] getSDNLists() {
        return SDNLists;
    }


    /**
     * Sets the SDNLists value for this SDNList.
     * 
     * @param SDNLists
     */
    public void setSDNLists(com.newco.ofac.webservice.SDN[] SDNLists) {
        this.SDNLists = SDNLists;
    }

    private java.lang.Object __equalsCalc = null;
    @Override
	public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SDNList)) return false;
        SDNList other = (SDNList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.totalRecords == other.getTotalRecords() &&
            ((this.SDNLists==null && other.getSDNLists()==null) || 
             (this.SDNLists!=null &&
              java.util.Arrays.equals(this.SDNLists, other.getSDNLists())));
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
        _hashCode += getTotalRecords();
        if (getSDNLists() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getSDNLists());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getSDNLists(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SDNList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.webservicex.net/", "SDNList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRecords");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "TotalRecords"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SDNLists");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.webservicex.net/", "SDNLists"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.webservicex.net/", "SDN"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        elemField.setItemQName(new javax.xml.namespace.QName("http://www.webservicex.net/", "SDN"));
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
