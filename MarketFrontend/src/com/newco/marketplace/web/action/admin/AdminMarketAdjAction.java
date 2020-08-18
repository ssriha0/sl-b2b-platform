package com.newco.marketplace.web.action.admin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Calendar;

import com.newco.marketplace.dto.vo.buyer.BuyerMarketAdjVO;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;
import com.newco.marketplace.web.delegates.IBuyerMarketAdjDelegate;
import com.newco.marketplace.web.dto.AdminMarketAdjFormDTO;
import com.newco.marketplace.web.dto.IError;
import com.newco.marketplace.web.dto.SOWError;
import com.newco.marketplace.web.utils.SLStringUtils;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import org.apache.log4j.Logger;

public class AdminMarketAdjAction extends SLBaseAction implements Preparable, ModelDriven<AdminMarketAdjFormDTO>
{
	private static final long serialVersionUID = 9152693792653909066L;
	private AdminMarketAdjFormDTO adminMarketAdjFormDTO = new AdminMarketAdjFormDTO();
	
	private IBuyerMarketAdjDelegate marketAdjDelegate;

	private static final Logger logger = Logger.getLogger(AdminMarketAdjAction.class.getName());
	public AdminMarketAdjAction(IBuyerMarketAdjDelegate delegate)
	{
		this.marketAdjDelegate = delegate;
	}
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();	
	}	

	
	public String displayPage() throws Exception
	{
		
		adminMarketAdjFormDTO = getModel();
		String buyerId = get_commonCriteria().getCompanyId() + "";
		String sortColumn = getRequest().getParameter("sortColumn");
		String sortOrder = getRequest().getParameter("sortOrder");
		List<String> sortColumnValues = Arrays.asList(
				OrderConstants.COLUMN_MARKETID,
				OrderConstants.COLUMN_MARKETNAME,
				OrderConstants.COLUMN_STATENAME,
				OrderConstants.COLUMN_ADJUSTMENT,
				"");
		if (sortColumn == null||!(sortColumnValues.contains(sortColumn))) {
			sortColumn = OrderConstants.COLUMN_MARKETID;
		}
		if (sortOrder == null||!(sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_ASC)||sortOrder.equalsIgnoreCase(OrderConstants.SORT_ORDER_DESC))) {
			sortOrder = OrderConstants.SORT_ORDER_ASC;
		}
		List<BuyerMarketAdjVO> marketList = marketAdjDelegate.getBuyerMarkets(buyerId, sortColumn, sortOrder);
		
		adminMarketAdjFormDTO.setMarketList(marketList);
		adminMarketAdjFormDTO.setSortColumn(sortColumn);
		adminMarketAdjFormDTO.setSortOrder(sortOrder);
		return SUCCESS;
	}

	public String save() throws Exception
	{
		List<IError> errors = new ArrayList<IError>();		
		
		String buyerId = get_commonCriteria().getCompanyId() + "";
		String columnName = "marketId";
		String sortOrder = "ASC";
		List<BuyerMarketAdjVO> marketList = marketAdjDelegate.getBuyerMarkets(buyerId, columnName, sortOrder);
		
		String strValue;
		for(BuyerMarketAdjVO vo : marketList)
		{
			strValue = (String)getParameter("adj_rate_" + vo.getMarketId() + "_edit" );
			if(isAdjustmentValid(vo.getMarketName(), strValue, errors))
			{
				if(isAdjustmentChanged(strValue, vo))
				{
					NumberFormat formatter = new DecimalFormat("#0.0000");
					vo.setAdjustment(new BigDecimal(formatter.format(Double.parseDouble(strValue))));
					vo.setModifiedBy(get_commonCriteria().getTheUserName());
					vo.setModifiedDate(new java.sql.Timestamp(Calendar.getInstance().getTimeInMillis()));
					marketAdjDelegate.updateAdjustmentRate(vo);
				}
			}
		}

		// Send errors to the front end
		if(errors.size() > 0)
		{
			setAttribute("errors", errors);
		}
		
		adminMarketAdjFormDTO.setMarketList(marketList);
		adminMarketAdjFormDTO.setSortColumn(columnName);
		adminMarketAdjFormDTO.setSortOrder(sortOrder);
		
		return SUCCESS;
	}
	
	private boolean isAdjustmentValid(String market, String adj, List<IError> errors)
	{
		if(SLStringUtils.isNullOrEmpty(adj))
		{
			SOWError error = new SOWError(market, "Adjustment is blank.", OrderConstants.FM_ERROR);
			errors.add(error);			
			return false;
		}
		
		if(SLStringUtils.IsParsableNumber(adj) == false)
		{
			SOWError error = new SOWError(market, "Adjustment is not a number.", OrderConstants.FM_ERROR);
			errors.add(error);			
			return false;			
		}

		Double dbleAdj = Double.parseDouble(adj);
		if(dbleAdj < 0 )
		{
			SOWError error = new SOWError(market, "Adjustment cannot be a negative number.", OrderConstants.FM_ERROR);
			errors.add(error);			
			return false;			
		}else if (dbleAdj > 2){
			SOWError error = new SOWError(market, "Please enter Adjustment rate less than or equal to 2.0000.", OrderConstants.FM_ERROR);
			errors.add(error);			
			return false;	
		}
		
		if(dbleAdj < 0.1){
			if (BigDecimal.valueOf(dbleAdj).scale() > 5) {
				SOWError error = new SOWError(market, "Adjustment cannot contain more than four decimals.", OrderConstants.FM_ERROR);
				errors.add(error);			
				return false;	
			}
		}else{		
			if (BigDecimal.valueOf(dbleAdj).scale() > 4) {
				SOWError error = new SOWError(market, "Adjustment cannot contain more than four decimals.", OrderConstants.FM_ERROR);
				errors.add(error);			
				return false;	
			}
		}

		return true;
	}
	
	private boolean isAdjustmentChanged(String adj, BuyerMarketAdjVO adjRow)
	{
		if(adjRow == null)
			return false;
		if(adj == null)
			return false;
		
		String originalAdjustment = adjRow.getAdjustment() + ""; 
		return !adj.equals(originalAdjustment);
	}

	
	public AdminMarketAdjFormDTO getModel()
	{
		return adminMarketAdjFormDTO;
	}

	public IBuyerMarketAdjDelegate getMarketAdjDelegate() {
		return marketAdjDelegate;
	}

	public void setMarketAdjDelegate(IBuyerMarketAdjDelegate marketAdjDelegate) {
		this.marketAdjDelegate = marketAdjDelegate;
	}



	
	
}
