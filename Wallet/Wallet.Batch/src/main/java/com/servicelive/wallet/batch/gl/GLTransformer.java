package com.servicelive.wallet.batch.gl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.servicelive.common.exception.DataServiceException;
import com.servicelive.common.exception.SLBusinessServiceException;
import com.servicelive.common.util.DateUtils;
import com.servicelive.common.util.ServiceLiveStringUtils;
import com.servicelive.common.util.StackTraceHelper;
import com.servicelive.wallet.batch.gl.dao.IFiscalCalendarDao;
import com.servicelive.wallet.batch.gl.vo.FiscalCalendarVO;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLFeedVO;

// TODO: Auto-generated Javadoc
/**
 * The Class GLTransformer.
 */
public class GLTransformer {

	/** The Constant logger. */
	private static final Logger logger = Logger.getLogger(GLTransformer.class.getName());
	
	/** The fiscal calendar dao. */
	private IFiscalCalendarDao fiscalCalendarDao = null;
	
	/**
	 * Gets the sears fiscal calendar week.
	 *
	 * @return the sears fiscal calendar week
	 *
	 * @throws DataServiceException
	 */
	private FiscalCalendarVO getSearsFiscalCalendarWeek(Date startDate) throws DataServiceException {

		FiscalCalendarVO fiscalCalendarVO =
			createFiscalCalendarVOForCheckFiscalWeek(startDate);
		try {
			fiscalCalendarVO = fiscalCalendarDao.getFiscalCalendar(fiscalCalendarVO);
		} catch (Exception ex) {
			logger.info("[TimestampUtils - Exception] " + StackTraceHelper.getStackTrace(ex));
			throw new DataServiceException("Error", ex);
		}

		return fiscalCalendarVO;
	}

	private FiscalCalendarVO createFiscalCalendarVOForCheckFiscalWeek(
			Date startDate) {
		FiscalCalendarVO fiscalCalendarVO = new FiscalCalendarVO();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);

		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) +1;
		int day = calendar.get(Calendar.DATE);

		// Create a single date integer where 2010/05/01 would be 20100501
		Integer fiscalDate = (year * 10000) + (month * 100) + day;

		logger.info("The CheckFiscalDate " + fiscalDate);

		fiscalCalendarVO.setCheckFiscalWeek(fiscalDate);
		return fiscalCalendarVO;
	}

	/*
	 * This method will convert the data retrieved from the rpt_gl_detail table as List<GLDetailVO> to the format in
	 * GLFeedVO that can be used directly to write the GL file.
	 */
	/**
	 * Convert gl detail vo list to gl feed vo list.
	 * 
	 * @param glDetailVOList 
	 * @param fiscalVO 
	 * @param detailTranDate 
	 * 
	 * @return the array list< gl feed v o>
	 */
	public ArrayList<GLFeedVO> convertGLDetailVOListToGLFeedVOList(List<GLDetailVO> glDetailVOList,Date glProcessDate) throws SLBusinessServiceException{

		SimpleDateFormat sdf2 = new SimpleDateFormat("MMddyy");//Changed the date format to MMDDYY for SL-18249
		Calendar cal = Calendar.getInstance();
		
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		java.sql.Timestamp startDate = new java.sql.Timestamp(cal.getTime().getTime());
		
		FiscalCalendarVO fiscalVO=null;
		ArrayList<GLFeedVO> glFeedItemList = new ArrayList<GLFeedVO>();
		GLFeedVO glFeedVO = null;
		GLDetailVO glDetailVO = null;
		DecimalFormat partsDecimalFormat = new DecimalFormat("0.00");
		DecimalFormat decimalFormat = new DecimalFormat("0.000");
		for (int i = 0; i < glDetailVOList.size(); i++) {
			glFeedVO = new GLFeedVO();
			glDetailVO = glDetailVOList.get(i);
			if (glDetailVO.getGlUnit() != null) {
				glFeedVO.setLOCN(glDetailVO.getGlUnit().substring(0, 5));
			} else {
				glFeedVO.setLOCN("     ");
			}
			glFeedVO.setDIV(glDetailVO.getGlDivision().substring(0, 4));
			glFeedVO.setACCT(glDetailVO.getGlAccount().substring(0, 5));
			glFeedVO.setCATEGORY(glDetailVO.getGlCategory().substring(0, 4));
			if (glDetailVO.getTransactionAmount() != 0.0) { // If there is a +- amount
				glFeedVO.setCOST(ServiceLiveStringUtils.prefixString(String.valueOf(decimalFormat.format(glDetailVO.getTransactionAmount())), 17));
			} else if (glDetailVO.getTransactionAmount() == 0.0 || glDetailVO.getTransactionAmount() == null) { // If there is 0.0 or null
				glFeedVO.setCOST(ServiceLiveStringUtils.prefixString(String.valueOf(decimalFormat.format(0.0)), 17));
			}
			if(glDetailVO.getTransactionType()!=null){
				if (glDetailVO.getTransactionType().length() > 30){
					glFeedVO.setDESCRIP(StringUtils.left(glDetailVO.getTransactionType(), 30));
				}else{
					glFeedVO.setDESCRIP(ServiceLiveStringUtils.prefixString(glDetailVO.getTransactionType(), 30));
				}
			} else {
				glFeedVO.setDESCRIP(ServiceLiveStringUtils.prefixString(" ", 30));					
			}			
			
			if(null!=glDetailVO.getTransactionDate() && null!= glDetailVO.getLedgerRule() 
					&&("CEX".equalsIgnoreCase(glDetailVO.getLedgerRule())
					|| "COM".equalsIgnoreCase(glDetailVO.getLedgerRule()))){
				glFeedVO.setDET_TRAN_DATE(ServiceLiveStringUtils.prefixString(sdf2.format(glProcessDate), 6));
			} else if (null!=glDetailVO.getTransactionDate()){
			    glFeedVO.setDET_TRAN_DATE(ServiceLiveStringUtils.prefixString(sdf2.format
					(DateUtils.defaultFormatStringToDate(glDetailVO.getTransactionDate())), 6));
			} else {
				glFeedVO.setDET_TRAN_DATE(ServiceLiveStringUtils.prefixString(sdf2.format(startDate), 6));
			}
			
			if (null != glDetailVO.getProviderId()) {
				glFeedVO.setREF_NBR_1(ServiceLiveStringUtils.prefixString(String.valueOf(glDetailVO.getProviderId()), 10));
			} 
			else {
				glFeedVO.setREF_NBR_1(ServiceLiveStringUtils.prefixString(" ", 10));
			}
			
			if (null != glDetailVO.getNpsOrder()) {
				glFeedVO.setDOC_NBR(ServiceLiveStringUtils.prefixString(glDetailVO.getNpsOrder(), 15));
			} 
			else {
				glFeedVO.setDOC_NBR(ServiceLiveStringUtils.prefixString(" ", 15));
			}
			
			if (null != glDetailVO.getLedgerRule()) { 
				glFeedVO.setREF_NBR_2(ServiceLiveStringUtils.prefixString(glDetailVO.getLedgerRule(), 10));
			} else {
				glFeedVO.setREF_NBR_2(ServiceLiveStringUtils.prefixString(" ", 10));
			}
			
			if (null != glDetailVO.getOrderNumber()) { 
				glFeedVO.setMISC_1(ServiceLiveStringUtils.prefixString(glDetailVO.getOrderNumber(), 20));
			} else {
				glFeedVO.setMISC_1(ServiceLiveStringUtils.prefixString(" ", 20));
			}
			
			if (null != glDetailVO.getBuyerId()) { 
				glFeedVO.setMISC_2(ServiceLiveStringUtils.prefixString(String.valueOf(glDetailVO.getBuyerId()), 20));
			} else {
				glFeedVO.setMISC_2(ServiceLiveStringUtils.prefixString(" ", 20));
			}
			if (glDetailVO.getSellValue() != 0.0) { // If there is a +- amount
				glFeedVO.setSELL_VALUE(ServiceLiveStringUtils.prefixString(String.valueOf(partsDecimalFormat.format(glDetailVO.getSellValue())), 17));
			} else if (glDetailVO.getSellValue() == 0.0 || glDetailVO.getSellValue() == null) { // If there is 0.0 or null
				glFeedVO.setSELL_VALUE(ServiceLiveStringUtils.prefixString("0", 17));
			}			
			try{
				if(null!=glDetailVO.getTransactionDate() && null!= glDetailVO.getLedgerRule() 
					&& ("CEX".equalsIgnoreCase(glDetailVO.getLedgerRule()) || "COM".equalsIgnoreCase(glDetailVO.getLedgerRule())) ){
					fiscalVO = getSearsFiscalCalendarWeek(glProcessDate);
				}
				else if(null!=glDetailVO.getTransactionDate()){
					fiscalVO = getSearsFiscalCalendarWeek(DateUtils.defaultFormatStringToDate(glDetailVO.getTransactionDate()));
				} else{
					fiscalVO = getSearsFiscalCalendarWeek(startDate);
				}
			}
			catch(Exception ex){
				throw new SLBusinessServiceException("GLTransformer-->EXCEPTION-->", ex);
			}
			
			populateCommonGLPortion(fiscalVO, glFeedVO);
			glFeedItemList.add(glFeedVO);
		}
		return glFeedItemList;
	}

	/**
	 * Description: These elements are common to all of the GL records being
	 * created.
	 * 
	 * @param fiscalVO 
	 * @param glFeedItem 
	 */
	private void populateCommonGLPortion(FiscalCalendarVO fiscalVO, GLFeedVO glFeedItem) {

		glFeedItem.setYEAR(fiscalVO.getAccountingYear().toString());
		glFeedItem.setWEEK(fiscalVO.getYearFiscalWeek().toString());
		glFeedItem.setENTRY_SOURCE("SLC");
		glFeedItem.setCURR_CODE("USD");
		//glFeedItem.setSELL_VALUE(ServiceLiveStringUtils.prefixString("0", 17));
		glFeedItem.setSTAT_AMT(ServiceLiveStringUtils.prefixString("0", 17));
		glFeedItem.setSTAT_CODE(ServiceLiveStringUtils.prefixString(" ", 3));
		glFeedItem.setREV_FLAG(ServiceLiveStringUtils.prefixString(" ", 1));
		glFeedItem.setREV_YEAR("0000");
		glFeedItem.setREV_WEEK("00");
		glFeedItem.setBACKTFLAG(ServiceLiveStringUtils.prefixString(" ", 1));
		glFeedItem.setSYSTEM_SW(ServiceLiveStringUtils.prefixString(" ", 2));
		glFeedItem.setENTRY_TYPE("000");
		glFeedItem.setRECORD_TYPE("07");
		//	glFeedItem.setREF_NBR_1(ServiceLiveStringUtils.prefixString(" ", 10));
		//	glFeedItem.setDOC_NBR(ServiceLiveStringUtils.prefixString(" ", 15));
		//	glFeedItem.setREF_NBR_2(ServiceLiveStringUtils.prefixString(" ", 10));
		// glFeedItem.setMISC_1(ServiceLiveStringUtils.prefixString(" ", 20));
		// glFeedItem.setMISC_2(ServiceLiveStringUtils.prefixString(" ", 20));
		glFeedItem.setMISC_3(ServiceLiveStringUtils.prefixString(" ", 20));
		glFeedItem.setTO_FROM(ServiceLiveStringUtils.prefixString(" ", 6));
		glFeedItem.setDOC_DATE(ServiceLiveStringUtils.prefixString(" ", 8));
		glFeedItem.setEXP_CODE(ServiceLiveStringUtils.prefixString(" ", 3));
		glFeedItem.setEMP_NBR(ServiceLiveStringUtils.prefixString(" ", 7));
		// glFeedItem.setDET_TRAN_DATE(ServiceLiveStringUtils.prefixString(" ", 6));
		glFeedItem.setORIG_ENTRY(ServiceLiveStringUtils.prefixString(" ", 15));
		glFeedItem.setREP_FLG(ServiceLiveStringUtils.prefixString(" ", 1));
		glFeedItem.setORU(ServiceLiveStringUtils.prefixString(" ", 6));
		glFeedItem.setFILLER(ServiceLiveStringUtils.prefixString(" ", 24));
	}

	/**
	 * Sets the fiscal calendar dao.
	 *
	 * @param fiscalCalendarDao the new fiscal calendar dao
	 */
	public void setFiscalCalendarDao(IFiscalCalendarDao fiscalCalendarDao) {

		this.fiscalCalendarDao = fiscalCalendarDao;
}


}
