/**
 * 
 */
package com.newco.marketplace.vo.provider;

import java.sql.Date;

/**
 * @author sahmad7
 *
 */
public class VendorLicense extends BaseVO
{
    /**
	 * 
	 */
	private static final long serialVersionUID = -4017013789338871187L;
	private int vendorLicId = -1;
    private int vendorId = -1;
    private String licNo;
    private Date licExpDate;
    private String licCity;
    private String licCounty;
    private String licDesc;
    private Date createdDate;
    private Date modifiedDate;
    private String modifiedBy;
    
    public Date getCreatedDate()
    {
        return createdDate;
    }
    public void setCreatedDate(Date createdDate)
    {
        this.createdDate = createdDate;
    }
    public String getLicCity()
    {
        return licCity;
    }
    public void setLicCity(String licCity)
    {
        this.licCity = licCity;
    }
    public String getLicCounty()
    {
        return licCounty;
    }
    public void setLicCounty(String licCounty)
    {
        this.licCounty = licCounty;
    }
    public String getLicDesc()
    {
        return licDesc;
    }
    public void setLicDesc(String licDesc)
    {
        this.licDesc = licDesc;
    }
    public Date getLicExpDate()
    {
        return licExpDate;
    }
    public void setLicExpDate(Date licExpDate)
    {
        this.licExpDate = licExpDate;
    }
    public String getLicNo()
    {
        return licNo;
    }
    public void setLicNo(String licNo)
    {
        this.licNo = licNo;
    }
    public String getModifiedBy()
    {
        return modifiedBy;
    }
    public void setModifiedBy(String modifiedBy)
    {
        this.modifiedBy = modifiedBy;
    }
    public Date getModifiedDate()
    {
        return modifiedDate;
    }
    public void setModifiedDate(Date modifiedDate)
    {
        this.modifiedDate = modifiedDate;
    }
    public int getVendorId()
    {
        return vendorId;
    }
    public void setVendorId(int vendorId)
    {
        this.vendorId = vendorId;
    }
    public int getVendorLicId()
    {
        return vendorLicId;
    }
    public void setVendorLicId(int vendorLicId)
    {
        this.vendorLicId = vendorLicId;
    }
}

