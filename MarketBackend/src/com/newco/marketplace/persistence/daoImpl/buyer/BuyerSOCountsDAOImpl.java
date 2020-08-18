package com.newco.marketplace.persistence.daoImpl.buyer;

import static com.newco.marketplace.interfaces.OrderConstants.ACCEPTED_STATUS;
import static com.newco.marketplace.interfaces.OrderConstants.ACTIVE_STATUS;
import static com.newco.marketplace.interfaces.OrderConstants.DRAFT_STATUS;
import static com.newco.marketplace.interfaces.OrderConstants.PROBLEM_STATUS;
import static com.newco.marketplace.interfaces.OrderConstants.ROUTED_STATUS;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.buyer.BuyerPaginationSummaryVO;
import com.newco.marketplace.dto.vo.buyer.BuyerSOCountValueVO;
import com.newco.marketplace.dto.vo.buyer.BuyerTabSummaryVO;
import com.newco.marketplace.exception.DBException;
import com.newco.marketplace.persistence.iDao.buyer.IBuyerSOCountsDAO;
import com.sears.os.dao.impl.ABaseImplDao;

public class BuyerSOCountsDAOImpl extends ABaseImplDao implements IBuyerSOCountsDAO{

	private static final Logger logger = Logger.getLogger(BuyerSOCountsDAOImpl.class);

	private boolean debug = false;
	private int debugBuyerId = 1000;
	private long slownessThreshold = 100; //in milliseconds

	public BuyerSOCountsDAOImpl() {
	}

	@SuppressWarnings("unchecked")
	public void updateSOMCountsForBuyer() throws DBException {
		Date start = new Date();
		logger.info("starting somCountsForBuyer");
		try {
			Date start2 = new Date();

			// get a list of buyers
			List<BuyerSOCountValueVO> buyerSoCounts = queryForList("buyerSOCounts.getBuyerSOCountValues");
			Date end2 = new Date();
			long time2 = end2.getTime() - start2.getTime();
			if(time2 > slownessThreshold) {
				logger.info("buyerSOCounts.getSOCountValues took "+time2+" ms");
			}

			if(buyerSoCounts == null || buyerSoCounts.isEmpty()) {
				logger.warn("There are no buyerIds to calculate the buyer counts");
				return;
			}

			HashMap<Integer, List<BuyerSOCountValueVO>> buyerCountMap = new HashMap<Integer, List<BuyerSOCountValueVO>>();
			// loop through the buyer SO Counts List
			for(BuyerSOCountValueVO buyerSoCountValue:buyerSoCounts) {
				Integer buyerId = buyerSoCountValue.getBuyerId();
				List<BuyerSOCountValueVO> list = buyerCountMap.get(buyerId);
				
				if(list == null)
				{
					list = new ArrayList<BuyerSOCountValueVO>();
					list.add(buyerSoCountValue);
					buyerCountMap.put(buyerSoCountValue.getBuyerId(), list);
				}
				else
					list.add(buyerSoCountValue);
			}
			
			List<BuyerTabSummaryVO> buyerSummaryList = new ArrayList<BuyerTabSummaryVO>();
			List<BuyerPaginationSummaryVO> buyerPaginationSummaryList = new ArrayList<BuyerPaginationSummaryVO>();
			
			for(Integer buyerId : buyerCountMap.keySet()) {
				if(debug && buyerId.intValue() != debugBuyerId) {
					continue;
				}
				handleCounts(buyerId, buyerCountMap.get(buyerId),buyerSummaryList,buyerPaginationSummaryList );
			}
			
				try {
				getSqlMapClientTemplate().getSqlMapClient().startTransaction();
			
				//delete all records from buyer_tab_summary and buyer_pagination_summary
				deleteBuyerTabSummary();
				logger.info("deleted records from buyer_tab_summary");
				deleteBuyerPaginationSummary();
				logger.info("deleted records from buyer_pagination_summary");
							
				//insert updated service order counts in buyer_tab_summary and buyer_pagination_summary				
				batchInsert("buyerTabSummary.insert", buyerSummaryList);
				logger.info("inserted records in buyer_tab_summary");
				batchInsert("buyerPaginationSummary.insert", buyerPaginationSummaryList);
				logger.info("inserted records in buyer_pagination_summary");
				
					getSqlMapClientTemplate().getSqlMapClient().commitTransaction();
				} finally {
					getSqlMapClientTemplate().getSqlMapClient().endTransaction();
				}
		
			
		} catch (Exception ex) {
			errorMessage(ex, "updateSOMCountsForBuyer");
		} finally {
			Date end = new Date();
			logger.info("ending somCountsForBuyer.  it took "+(end.getTime() - start.getTime())+" ms");
		}
	}

	private void deleteBuyerTabSummary() {		
		BuyerTabSummaryVO vo = new BuyerTabSummaryVO();
    	delete("buyerTabSummary.deleteAll", vo);		
	    	}

	private void deleteBuyerPaginationSummary() {				
		BuyerPaginationSummaryVO vo = new BuyerPaginationSummaryVO();
	    delete("buyerPaginationSummary.deleteAll", vo);		
	    	}


	private void handleBuyerTabSummary(BuyerSOCountValueVO buyerSOCountValueVO, Map<Integer, Integer> calculatedBuyerTabSummary) {
	
    		// add individual count for wf_state to calculatedBuyerTabSummary
    		Integer key = buyerSOCountValueVO.getWfStateId();
    		Integer count = calculatedBuyerTabSummary.get(key);
    		if(count == null) {
    			count = new Integer(buyerSOCountValueVO.getSoCount());    		
    		}
    		else
    		{
    			count += buyerSOCountValueVO.getSoCount();
    	}
    		calculatedBuyerTabSummary.put(key, count);
	}

	private void append(BuyerTabSummaryVO vo, StringBuilder log) {
		if(log.length() > 0) {
			log.append(" || ");
		}
		log.append("wfStateId = ");
		log.append(vo.getWfStateId());
		log.append(", ");
		log.append("count = ");
		log.append(vo.getSoCount());
	}

   
    private void persistBuyerTabSummary(Integer buyerId, Map<Integer, Integer> calculatedBuyerTabSummary, List<BuyerTabSummaryVO> voList) {    	
    	StringBuilder insertChange = new StringBuilder();

	    for(Integer key : calculatedBuyerTabSummary.keySet()) {
	    	Integer count = calculatedBuyerTabSummary.get(key);

	    		BuyerTabSummaryVO vo = new BuyerTabSummaryVO();
	    		vo.setBuyerId(buyerId);
	    		vo.setWfStateId(key);
	    		vo.setSoCount(count);
	    		append(vo, insertChange);
	    		voList.add(vo);	    	
	    	}

		logger.info("prepared buyerTabSummary buyerId[" +buyerId+"] " + insertChange  + " for insert");
	    }

    private void append(BuyerPaginationSummaryVO vo, StringBuilder log) {
		if(log.length() > 0) {
			log.append(" || ");
		}
		log.append("wfStateId = ");
		log.append(vo.getWfStateId());
		log.append(", ");
		log.append("soSubstatusId = ");
		log.append(vo.getSoSubstatusId());
		log.append(", ");
		log.append("count = ");
		log.append(vo.getCount());
	}

    private void persistBuyerPaginationSummary(Integer buyerId, List<BuyerSOCountValueVO> calculatedBuyerPaginationSummary,
    	List<BuyerPaginationSummaryVO> buyerPaginationSummaryList) {
    
    	StringBuilder insertChange = new StringBuilder();

    	Iterator<BuyerSOCountValueVO> listBuyerPaginationSummary = calculatedBuyerPaginationSummary.iterator();	
	    while(listBuyerPaginationSummary.hasNext()) {

	    		BuyerSOCountValueVO buyerSOCountValueVO = listBuyerPaginationSummary.next();
	    		BuyerPaginationSummaryVO vo = new BuyerPaginationSummaryVO();
	    		vo.setBuyerId(buyerId);
	    		vo.setWfStateId(buyerSOCountValueVO.getWfStateId());
	    		if(vo.getWfStateId() == null) {
	    			vo.setWfStateId(Integer.valueOf(0));
	    		}
	    		vo.setSoSubstatusId(buyerSOCountValueVO.getSoSubstatusId());
	    		if(vo.getSoSubstatusId() == null) {
	    			vo.setSoSubstatusId(Integer.valueOf(0));
	    		}
	    		vo.setCount(buyerSOCountValueVO.getSoCount());
	    		append(vo, insertChange);
	    		buyerPaginationSummaryList.add(vo);
	    		  	
	    	}
	    logger.info("prepared buyerPaginationSummary buyerId[" +buyerId+"] " + insertChange + " for insert");	  
	    }
    
    
    private void handleCounts(Integer buyerId, 
    		List<BuyerSOCountValueVO> buyerSOCountList, 
    		List<BuyerTabSummaryVO> buyerSummaryList, 
    		List<BuyerPaginationSummaryVO> buyerPaginationSummaryList) {
		Date start = new Date();
		if(logger.isDebugEnabled()) {
			logger.debug("starting somCountsForBuyer.handleCounts for buyer["+buyerId+"]");
	    }

	 // summarize counts
	    Map<Integer, Integer> calculatedBuyerTabSummary = new HashMap<Integer, Integer>();
	    for(BuyerSOCountValueVO buyerSOCountValueVO: buyerSOCountList) {
	    	
	    	if(buyerSOCountValueVO.getWfStateId() == null) {
	    		continue;
	    }
	    	
	    	// buyerTabSummary
	    	handleBuyerTabSummary(buyerSOCountValueVO, calculatedBuyerTabSummary);

	    }
	
	    //persist changes for buyer_tab_summary
	    persistBuyerTabSummary(buyerId, calculatedBuyerTabSummary,buyerSummaryList);

	    //persist changes for buyer_pagination_summary
	    persistBuyerPaginationSummary(buyerId, buyerSOCountList,buyerPaginationSummaryList);

		Date end = new Date();
		long time = end.getTime() - start.getTime();
		if(time > slownessThreshold) {
			logger.info("ending somCountsForBuyer.handleCounts for buyer["+buyerId+"].  it took "+(time)+" ms");
	    }
	    }


	public void updateBuyerCompletedOrdersCount() throws DBException {
		try {
			update("updateBuyerTotalOrderComplete.update", null);
		} catch (Exception ex) {
			errorMessage(ex, "updateBuyerCompletedOrdersCount");		
		}		
	}
	
	/**
	 * Description: takes exception and method name and creates a log info message and 
	 * throws DBException.  Cuts down on repeating these lines in every DAO method. 
	 * @param ex
	 * @param methodName
	 * @throws DBException
	 */
	private void errorMessage(Exception ex, String methodName)
			throws DBException {
		logger.info("Exception @BuyerSOCountsDAOImpl." + methodName
				+ "() due to" + ex.getMessage());
		throw new DBException("Exception @BuyerSOCountsDAOImpl." + methodName
				+ "() due to " + ex.getMessage(), ex);
	}	

}
