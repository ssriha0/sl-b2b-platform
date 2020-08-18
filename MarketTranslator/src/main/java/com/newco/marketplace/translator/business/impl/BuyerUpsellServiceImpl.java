package com.newco.marketplace.translator.business.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import org.apache.log4j.Logger;

import com.newco.marketplace.oms.OMSStagingBridge;
import com.newco.marketplace.translator.business.BuyerUpsellService;
import com.newco.marketplace.translator.util.Constants;
import com.newco.marketplace.webservices.dao.AbstractShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcMerchandise;
import com.newco.marketplace.webservices.dao.ShcOrder;
import com.newco.marketplace.webservices.dao.ShcOrderAddOn;
import com.newco.marketplace.webservices.dao.SpecialtyAddOn;

//import com.newco.marketplace.webservices.sei.StageOrderSEILocator;


public class BuyerUpsellServiceImpl 
	implements BuyerUpsellService
{
	private Logger logger = Logger.getLogger(StagingService.class);
	private OMSStagingBridge omsStagingBridge;
	private BuyerSkuService buyerSKUService;

	public BuyerUpsellServiceImpl() {
		// intentionally blank
	}
	
	public BuyerUpsellServiceImpl(OMSStagingBridge omsStagingBridge) 
	{	
		this.omsStagingBridge = omsStagingBridge;
	}

	public OMSStagingBridge getOmsStagingBridge() {
		return omsStagingBridge;
	}

	public void setOmsStagingBridge(OMSStagingBridge omsStagingBridge) {
		this.omsStagingBridge = omsStagingBridge;
	}
	
 /*
	private void setStagingWebServiceURL(StageOrderSEILocator stagingServiceLocator) {
		String stagingServiceEndPointUrl = Constants.STAGING_WS_URL;
		logger.info("[[[[[[===== Staging URL in StagingService = {" + stagingServiceEndPointUrl + "} =====]]]]]]");
		stagingServiceLocator.setStageOrderSEIHttpPortEndpointAddress(stagingServiceEndPointUrl);
	}
	
	*/
	public void processAddOnMerchandise( ShcOrder shcOrder, int buyerID )
	{
		try
		{
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			
			ShcMerchandise[] s = shcOrder.getShcMerchandises().toArray(new ShcMerchandise[0]);
			
			//ShcMerchandise[] s = shcOrder.getShcMerchandises();
			String specialtyCode = s[0].getSpecialty();
			String divisionCode = s[0].getDivisionCode();
			List<SpecialtyAddOn> specialtyAddOnList = 
				getSpecialtyAddOnList(specialtyCode, divisionCode);
			if( specialtyAddOnList == null )
				specialtyAddOnList = new ArrayList<SpecialtyAddOn>();
			ShcOrderAddOn [] shcOrderAddOnArray = 
				new ShcOrderAddOn[specialtyAddOnList.size()];
			buildShcOrderAddOnList( shcOrder, specialtyAddOnList, shcOrderAddOnArray, buyerID );

			
			// Convert ShcOrderAddOn Array to ShcOrderAddOn Set
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			List<ShcOrderAddOn> list = Arrays.asList(shcOrderAddOnArray);
		    Set<ShcOrderAddOn> shcaddonsSet = new HashSet<ShcOrderAddOn>(list);
			
			shcOrder.setShcOrderAddOns( shcaddonsSet );
		}
		catch( Exception e )
		{
			logger.warn( "Problem processing AddOn SKUs: " + e.getMessage() , e );
		}
		return;
	}
	
	protected List<SpecialtyAddOn> getSpecialtyAddOnList(final String specialtyCode, final String divisionCode)
			throws NullPointerException, RemoteException, ServiceException {	
		List<SpecialtyAddOn> l = new ArrayList<SpecialtyAddOn>();
		
		// Convert SpecialtyAddOn list  to SpecialtyAddOn Array
		// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
		
		SpecialtyAddOn [] a = 
			omsStagingBridge.getMiscAddOn(	specialtyCode, divisionCode ).toArray(new SpecialtyAddOn[0]);
		
		
		for( int i = 0; i < a.length; i++ ) {if(!"CC".equalsIgnoreCase(a[i].getCoverage())) l.add( a[i] ); }
		return l;
	}

	protected void buildShcOrderAddOnList(	final ShcOrder shcOrder,
											final List<SpecialtyAddOn> specialtyAddOnList, 
											ShcOrderAddOn [] shcOrderAddOnArray, int buyerID )
	{
		for( int i = 0; i < specialtyAddOnList.size(); i++ )
		{
			shcOrderAddOnArray[i] = transForm( shcOrder, specialtyAddOnList.get(i), buyerID );
		}
		return;
	}
	protected ShcOrderAddOn transForm(	final ShcOrder shcOrder,
										final SpecialtyAddOn specialtyAddOn, int buyerID)
	{
		ShcOrderAddOn shcOrderAddOn = new ShcOrderAddOn();
		shcOrderAddOn.setSpecialtyAddOn(specialtyAddOn);
		// TODO FIX SPECIALTY ADD ON OBJECT & DAO TO
		//		HAVE MARGIN READ FROM DATABASE (INFILE)
		/*shcOrderAddOn.setMargin( 
				Constants.MARGIN_RATE_UNTIL_SPECIALTY_ADD_ON_TABLE_HAS_CORRECT_COLUMNS );*/
		shcOrderAddOn.setMargin( specialtyAddOn.getMarkUpPercentage() );
		shcOrderAddOn.setQty(Integer.valueOf(0) );
		shcOrderAddOn.setMiscInd(	getMiscSKUInd(specialtyAddOn, 
									getDivisionCode(shcOrder)) );
		Double retailPrice = 
			buyerSKUService.calculateRetailPrice(	specialtyAddOn.getStockNumber(), 
													shcOrder.getStoreNo(), 
													Integer.valueOf(buyerID));
		shcOrderAddOn.setRetailPrice(retailPrice);
		shcOrderAddOn.setCoverage(specialtyAddOn.getCoverage());
		return shcOrderAddOn;
	}
	
	protected Integer getMiscSKUInd(final SpecialtyAddOn specialtyAddOn, final String divisionCode) {
		String specialtyAddOnSKU = specialtyAddOn.getStockNumber();
		Integer miscSKUIndicator = null;
		if (specialtyAddOnSKU != null &&
				(specialtyAddOnSKU.substring(specialtyAddOnSKU.length()-3).equals(divisionCode) || specialtyAddOnSKU.equals(Constants.PERMIT_SKU))) {
			miscSKUIndicator = Constants.MISCELLANEOUS_SKU_INDICATOR;
		} else {
			miscSKUIndicator = Constants.NOT_MISCELLANEOUS_SKU_INDICATOR;
		}
		return miscSKUIndicator;
	}
	
	private String getDivisionCode( final ShcOrder shcOrder )
	{
		try
		{
			
			// casting it to  AbstractShcMerchandise object before calling getDivisionCode().
			// Getting rid of the conversion issues in effort to remove omsstagingserviceclient.jar web-service call.
			
			return((AbstractShcMerchandise) shcOrder.getShcMerchandises().toArray()[0]).getDivisionCode();
			//return shcOrder.getShcMerchandises()[0].getDivisionCode();
		}
		catch( NullPointerException e )
		{
			return "000";
		}
	}
	
	/**
	 * @return the buyerSKUService
	 */
	public BuyerSkuService getBuyerSKUService() {
		return buyerSKUService;
	}
	/**
	 * @param buyerSKUService the buyerSKUService to set
	 */
	public void setBuyerSKUService(BuyerSkuService buyerSKUService) {
		this.buyerSKUService = buyerSKUService;
	}
}
