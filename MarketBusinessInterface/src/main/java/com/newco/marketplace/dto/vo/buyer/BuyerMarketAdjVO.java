package com.newco.marketplace.dto.vo.buyer;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.newco.marketplace.webservices.base.CommonVO;

/**
 * @author Mahmud Khair
 * This is the value object class for Buyer_Market_Adj_State view
 */
public class BuyerMarketAdjVO extends CommonVO{
    /**
	 * Serial Version UID for Serialization
	 */
	private static final long serialVersionUID = -5817714739757984882L;

	/**
     * This field corresponds to the database column vw_buyer_market_adj_state.buyer_id
     *
     */
    private Integer buyerId;

    /**
     * This field corresponds to the database column vw_buyer_market_adj_state.market_id
     *
     */
    private Integer marketId;

    /**
     * This field corresponds to the database column vw_buyer_market_adj_state.market_name
     *     
     */
    private String marketName;

    /**
     * This field corresponds to the database column vw_buyer_market_adj_state.state_name
     *     
     */
    private String stateName;

    /**
     * This field corresponds to the database column vw_buyer_market_adj_state.adjustment
     *     
     */
    private BigDecimal adjustment;
    
    /**
     * This field is for sorting the data
     *     
     */
    private String sortColumnName;
    
    /**
     * This field for sorting order
     *     
     */
    private String sortOrder;
    
    /**
     * This field corresponds to the database column buyer_market_adjustment.modified_by
     *     
     */
    private String modifiedBy;
    
    /**
     * This field corresponds to the database column buyer_market_adjustment.modified_date
     *     
     */
    private Timestamp modifiedDate;

    /**    
     * This method returns the value of the database column vw_buyer_market_adj_state.buyer_id
     *
     * @return the value of vw_buyer_market_adj_state.buyer_id
     *     
     */
    public Integer getBuyerId() {
        return buyerId;
    }

    /**    
     * This method sets the value of the database column vw_buyer_market_adj_state.buyer_id
     *
     * @param buyerId the value for vw_buyer_market_adj_state.buyer_id
     *     
     */
    public void setBuyerId(Integer buyerId) {
        this.buyerId = buyerId;
    }

    /**    
     * This method returns the value of the database column vw_buyer_market_adj_state.market_id
     *
     * @return the value of vw_buyer_market_adj_state.market_id
     *     
     */
    public Integer getMarketId() {
        return marketId;
    }

    /**    
     * This method sets the value of the database column vw_buyer_market_adj_state.market_id
     *
     * @param marketId the value for vw_buyer_market_adj_state.market_id
     *     
     */
    public void setMarketId(Integer marketId) {
        this.marketId = marketId;
    }

    /**    
     * This method returns the value of the database column vw_buyer_market_adj_state.market_name
     *
     * @return the value of vw_buyer_market_adj_state.market_name
     *     
     */
    public String getMarketName() {
        return marketName;
    }

    /**    
     * This method sets the value of the database column vw_buyer_market_adj_state.market_name
     *
     * @param marketName the value for vw_buyer_market_adj_state.market_name
     *     
     */
    public void setMarketName(String marketName) {
        this.marketName = marketName;
    }

    /** 
     * This method returns the value of the database column vw_buyer_market_adj_state.state_name
     *
     * @return the value of vw_buyer_market_adj_state.state_name
     *     
     */
    public String getStateName() {
        return stateName;
    }

    /**    
     * This method sets the value of the database column vw_buyer_market_adj_state.state_name
     *
     * @param stateName the value for vw_buyer_market_adj_state.state_name
     *     
     */
    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    /**    
     * This method returns the value of the database column vw_buyer_market_adj_state.adjustment
     *
     * @return the value of vw_buyer_market_adj_state.adjustment
     *     
     */
    public BigDecimal getAdjustment() {
    	return adjustment;
    }


    /**    
     * This method sets the value of the database column vw_buyer_market_adj_state.adjustment
     *
     * @param adjustment the value for vw_buyer_market_adj_state.adjustment
     *     
     */

    public void setAdjustment(BigDecimal adjustment) {
    	this.adjustment = adjustment;
    }

    /**
	 * @return the sorting column name
	 */
	public String getSortColumnName() {
		return sortColumnName;
	}
	
	/**    
     * This method sets the value for sorting the data
     *
     * @param sortColumnName
     *     
     */
	public void setSortColumnName(String sortColumnName) {
		this.sortColumnName = sortColumnName;
	}

	/**
	 * @return the order of the data
	 */
	public String getSortOrder() {
		return sortOrder;
	}
	
	/**    
     * This method sets the value for ordering the data
     *
     * @param sortOrder     
     */
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	/**
	 * @return the person who modified the data
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param sets the person who modified the data
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	/**
	 * @return the modified date
	 */
	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * Sets the modified date
	 * @param modifiedDate
	 */
	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}