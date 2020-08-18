package com.newco.marketplace.business.businessImpl.powerbuyer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.constants.Constants.SO_QUEUE;
import com.newco.marketplace.dto.vo.BuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMBuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMQueueVO;
import com.newco.marketplace.dto.vo.WFMSOTasksVO;
import com.newco.marketplace.dto.vo.group.QueueTasksGroupVO;
import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBBuyerFilterSummaryVO;
import com.newco.marketplace.dto.vo.powerbuyer.PBFilterVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;
import com.newco.marketplace.persistence.iDao.powerbuyer.IPowerBuyerFilterDao;
import com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao;

/**
 * @author Michael J. Hayes, Sogeti USA, LLC
 * 
 * $Revision: 1.18 $ $Author: glacy $ $Date: 2008/04/26 00:40:35 $
 */
/*
 * Maintenance History: See bottom of file
 */
public class PowerBuyerBOImpl implements IPowerBuyerBO {
    private static final Logger logger = Logger.getLogger(PowerBuyerBOImpl.class.getName());
    private IPowerBuyerFilterDao pbfDao;
    private ISOClaimDao claimDao;

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#updatePowerBuyerFilterSummaryCounts()
     */
    public void updatePowerBuyerFilterSummaryCounts() throws BusinessServiceException {
        try {
            List<Integer> buyers = pbfDao.getBuyers();
            for (Integer buyerId : buyers) {
                pbfDao.loadFilterSummaryCounts(buyerId);
            }
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#claimSO(com.newco.marketplace.dto.vo.powerbuyer.ClaimVO)
     */
    public ClaimVO claimSO(ClaimVO toClaimVO) throws BusinessServiceException {
        ClaimVO claimVOToReturn = null;
        // check to see if the service order has already be claimed
        try {
            ClaimVO claimedVO = claimDao.getClaimedSerivceOrderByServiceOrderId(toClaimVO.getSoId());
            if (null != claimedVO) {
                // need to override this claim and then we can claim it for this
                // resource
                //claimedVO.setReasonCode(Constants.CLAIM_HISTORY_CODE.OVERRIDE_UNCLAIM);
                //this.unClaimSO(claimedVO);
            	return null;
            }
            
            // queue is not given: add so to 'claimed from search tab' queue
            // SLT-1892 START Code changes add SO to new standard queue for claimed
			Integer claimedQueue = null;
			if (toClaimVO.getQueueId() == null || toClaimVO.getQueueId().intValue() == 0) {
				if ((toClaimVO.getBuyerId().equals(Constants.FACILITIES_BUYER))
						|| (toClaimVO.getBuyerId().equals(Constants.ATnT_BUYER))) {
					claimedQueue = Constants.SO_QUEUE.SO_QUEUE_CLAIMED_FROM_SEARCH_TAB_FOR_FACILITIES;
				} else {
					claimedQueue = Constants.SO_QUEUE.SO_QUEUE_CLAIMED_FROM_SEARCH_TAB;
				}

				Integer standardClaimedQueue = Constants.SO_QUEUE.SO_QUEUE_STANDARD_CLAIMED;
				List<Integer> claimedQueues = Arrays.asList(standardClaimedQueue, claimedQueue);
				List<Integer> queueIds = pbfDao.checkClaimedQueue(toClaimVO.getBuyerId(), claimedQueues);
				if (queueIds == null) {
					toClaimVO.setQueueId(standardClaimedQueue);
				} else {
					if (queueIds.size() == 0) {
						toClaimVO.setQueueId(standardClaimedQueue);
					} else if (queueIds.contains(claimedQueue)) {
						toClaimVO.setQueueId(claimedQueue);
					} else if (queueIds.contains(standardClaimedQueue)) {
						toClaimVO.setQueueId(standardClaimedQueue);
					}
				}
				// SLT-1892 START Code changes add SO to new standard queue
				// for claimed.

				if (claimDao.getQueuedServiceOrder(toClaimVO) == null) {
					claimDao.addServiceOrderToQueue(toClaimVO);
				}
			}
            PBFilterVO queueVO = pbfDao.getFilterByFilterId(toClaimVO.getQueueId());
            String queueDestinationTab = queueVO.getDestinationTab();
            String queueDestinationSubTab = queueVO.getDestinationSubTab();
            
            // claim for current resource
            claimVOToReturn = (ClaimVO) claimDao.claimSerivceOrder(toClaimVO);
            claimVOToReturn.setBuyerId(toClaimVO.getBuyerId());
            claimVOToReturn.setQueueDestinationTab(queueDestinationTab);
            claimVOToReturn.setQueueDestinationSubTab(queueDestinationSubTab);
            
            // remove any re-queue record for this so
            //claimDao.removeReQueue(claimVOToReturn.getSoId());
        } catch (DataServiceException e) {
            logger.error("Unable to ClaimSO(" + toClaimVO.getSoId() + ") for resource: " + toClaimVO.getResourceId(), e);
        }
        return claimVOToReturn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#getBuyerFilterSummaryCounts(java.lang.Integer)
     */
    public List<PBBuyerFilterSummaryVO> getBuyerFilterSummaryCounts(Integer buyerId, Boolean slAdminInd, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException {
        List<PBBuyerFilterSummaryVO> results = new ArrayList<PBBuyerFilterSummaryVO>();
        try {
            results = pbfDao.getBuyerFilterSummary(buyerId, slAdminInd, buyerRefTypeId, buyerRefValue, searchBuyerId);
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#getClaimedSO(java.lang.Integer)
     */
    public List<ClaimVO> getClaimedSO(Integer resourceId) throws BusinessServiceException {
        List<ClaimVO> results;
        try {
            results = claimDao.getClaimedSerivceOrders(resourceId);
            for(ClaimVO claimVO:results) {
            	PBFilterVO queueVO;
            	if (claimVO.getSoId() != null) {
            		queueVO = pbfDao.getDestinationTabForSO(claimVO.getSoId());
            	} else {
            		queueVO = pbfDao.getDestinationTabForSO(claimVO.getParentGroupId());
            	}
            	if(queueVO != null) {
            		String queueDestinationTab = queueVO.getDestinationTab();
            		claimVO.setQueueDestinationTab(queueDestinationTab);
            	}
            }
            
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return results;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#claimNextSO(java.lang.Integer,
     *      java.lang.Integer, java.lang.Integer)
     */
    public ClaimVO claimNextSO(Integer filterId, Integer buyerId, Integer resourceId, Integer buyerRefTypeId, String buyerRefValue, String searchBuyerId) throws BusinessServiceException {
        ClaimVO toClaimVO = new ClaimVO();
        ClaimVO claimedVO = new ClaimVO();
        try {
            toClaimVO = pbfDao.getNextUnclaimedSOByFilterId(filterId, buyerId, buyerRefTypeId, buyerRefValue, searchBuyerId);
            if (toClaimVO == null) {
            	return null;
            }
            else if (toClaimVO != null && StringUtils.isNotEmpty(toClaimVO.getSoId())) {
               	toClaimVO.setResourceId(resourceId);
               	toClaimVO.setQueueId(filterId);
                claimedVO = claimSO(toClaimVO);
            }
            //If someone has already claimed the SO then from line number 156 calimVo would be null. We can return claimVO as null back to PBWorkflowTabAction in turns it will redirect to WORKFLOWMONIOTR default page 
            if(claimedVO != null) {
	            claimedVO.setQueueDestinationTab(toClaimVO.getQueueDestinationTab());
	            claimedVO.setQueueDestinationSubTab(toClaimVO.getQueueDestinationSubTab());
            }
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return claimedVO;
    }

    public IPowerBuyerFilterDao getPbfDao() {
        return pbfDao;
    }

    public void setPbfDao(IPowerBuyerFilterDao pbfDao) {
        this.pbfDao = pbfDao;
    }

    public ISOClaimDao getClaimDao() {
        return claimDao;
    }

    public void setClaimDao(ISOClaimDao claimDao) {
        this.claimDao = claimDao;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#requeSO(com.newco.marketplace.dto.vo.powerbuyer.RequeVO)
     */
    public boolean requeSO(RequeVO soReque) throws BusinessServiceException {
        try {
            ClaimVO unClaimVO = new ClaimVO();
            unClaimVO.setResourceId(soReque.getResourceId());
            unClaimVO.setSoId(soReque.getSoId());
            unClaimVO.setBuyerId(soReque.getBuyerId());
            unClaimVO.setReasonCode(Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_REQUED);
            if (unClaimSO(unClaimVO)) {
                soReque = claimDao.requeSO(soReque);
            } else
                return false;
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#requeSO(com.newco.marketplace.dto.vo.powerbuyer.RequeVO)
     */
    public boolean completedClaimTask(String soId, Integer resourceId, int resonCode) throws BusinessServiceException {
        ClaimVO claimVO;
        try {
        	claimVO = claimDao.getClaimedSerivceOrderByServiceOrderId(soId);
        }
        catch (DataServiceException e) {
        	throw new RuntimeException(e);
        }
        
        if(claimVO==null) {
        	logger.info("===***=== DEBUG_UNCLAIM=== User trying to unclaim already un-claimed SO, so_id: " +
        			soId + ", resourceId: " + resourceId + ", resonCode: " + resonCode);
        	return false;
        }
        claimVO.setReasonCode(resonCode);
        return unClaimSO(claimVO);
    }

    /*
     * Set the reasonCode with Constants.CLAIM_HISTORY_CODE values (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerrbuyer.IPowerBuyerBO#unClaimSO(java.lang.Integer,
     *      java.lang.String)
     */
    protected synchronized boolean unClaimSO(ClaimVO unClaimVO) throws BusinessServiceException {
        try {
            int reasonCode = unClaimVO.getReasonCode();
            if (reasonCode >= 1 && reasonCode <= 6) {
            } else {
                throw new BusinessServiceException("The reason code is not valid");
            }
            ClaimVO claimVO = claimDao.getClaimedSerivceOrderByServiceOrderId(unClaimVO.getSoId());
            switch (reasonCode) {
                case Constants.CLAIM_HISTORY_CODE.EXPIRED_CLAIM:
                case Constants.CLAIM_HISTORY_CODE.OVERRIDE_UNCLAIM:
                    if (claimVO == null) {
                        return true;
                    }
                    break;
                case Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_COMPLETE:
                case Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_INCOMPLETE:
                case Constants.CLAIM_HISTORY_CODE.SELF_UNCLAIM_REQUED:
                    if (claimVO == null) {
                        return false;
                    }
                    if (!claimVO.getResourceId().equals(unClaimVO.getResourceId())) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
//            ClaimHistoryVO claimHistoryVO = new ClaimHistoryVO();
//            claimHistoryVO.setSoId(claimVO.getSoId());
//            claimHistoryVO.setClaimByResourceId(claimVO.getResourceId());
//            claimHistoryVO.setClaimDate(claimVO.getClaimDate());
//            claimHistoryVO.setUnClaimByResourceId(unClaimVO.getResourceId());
//            claimHistoryVO.setReasonCode(unClaimVO.getReasonCode());
            int result = claimDao.unClaimSO(unClaimVO);
//            claimDao.logClaimHistory(claimHistoryVO);
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return true;
    }
    
    /* (non-Javadoc)
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#unClaimByResource(java.lang.Integer)
     */
    public void unClaimByResource(Integer resourceId) {
    	claimDao.unClaimByResource(resourceId);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#getRequeInfo(com.newco.marketplace.dto.vo.powerbuyer.RequeVO)
     */
    /*
    public RequeVO getRequeInfo(RequeVO requeVO) throws BusinessServiceException {
        try {
            requeVO = claimDao.getRequeInfo(requeVO);
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
        return requeVO;
    }
    */

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#processExpiredClaims()
     */
    public void processExpiredClaims() throws BusinessServiceException {
        try {
            List<ClaimVO> list = claimDao.getExpiredClaims();
            for (ClaimVO unClaimVO : list) {
                unClaimVO.setResourceId(null);
                unClaimVO.setReasonCode(Constants.CLAIM_HISTORY_CODE.EXPIRED_CLAIM);
                try {
                	unClaimSO(unClaimVO);
                } catch (Exception e) {
                	logger.error("Batch job unable to unclaim SO: " + unClaimVO.getSoId() + ", reason: " + e.getMessage());
                }
            }
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#claimSpecificSO(java.lang.String,
     *      java.lang.Integer, java.lang.Integer)
     */
    public ClaimVO claimSpecificSO(String soId, Integer buyerId, Integer resourceId) throws BusinessServiceException {
        ClaimVO toClaimVO = new ClaimVO();
        toClaimVO.setSoId(soId);
        toClaimVO.setBuyerId(buyerId);
        toClaimVO.setResourceId(resourceId);
        return claimSO(toClaimVO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#isCurrentUserTheClaimedUser(java.lang.String,
     *      java.lang.Integer)
     */
    public boolean isCurrentUserTheClaimedUser(String soId, Integer resourceId) throws BusinessServiceException {
        try {
            return claimDao.isCurrentUserTheClaimedUser(soId, resourceId);
        } catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }
    
  /*
   * (non-Javadoc)
   * 
   * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#getClaimedSO(java.lang.Integer)
   */
  public List<WFMBuyerQueueVO> getWFMQueueDetails(String buyerId, String soId) throws BusinessServiceException {
      
      List<WFMBuyerQueueVO> results = new ArrayList<WFMBuyerQueueVO>();
      try {
          results = pbfDao.getWFMQueueDetails(buyerId, soId);
      } catch (DataServiceException e) {
          throw new BusinessServiceException(e.getMessage(), e);
      }
      return results;
  }
  
 
  
  public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId, String groupId ) throws BusinessServiceException {
      
      QueueTasksGroupVO results = new QueueTasksGroupVO();
      try {
          results = pbfDao.getWFMQueueAndTasks(buyerId, soId, groupId);
      } catch (DataServiceException e) {
          throw new BusinessServiceException(e.getMessage(), e);
      }
      return results;
  }
  
  
  public QueueTasksGroupVO getWFMQueueAndTasks(String buyerId, String soId) throws BusinessServiceException {
      
      QueueTasksGroupVO results = new QueueTasksGroupVO();
      try {
          results = pbfDao.getWFMQueueAndTasks(buyerId, soId);
      } catch (DataServiceException e) {
          throw new BusinessServiceException(e.getMessage(), e);
      }
      return results;
  }

  
  public List<WFMSOTasksVO> getWFMCallBackQueueAndTasks(String buyerId) throws BusinessServiceException {
	  
	  List<WFMSOTasksVO> results = new ArrayList<WFMSOTasksVO>();
      try {
          results = pbfDao.getWFMCallBackQueueAndTasks(buyerId);
      } catch (DataServiceException e) {
          throw new BusinessServiceException(e.getMessage(), e);
      }
      return results;
	  
  }
	
  
  

   /*
    * (non-Javadoc)
    * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#updateCompleteIndicator(com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO)
    */
	public int updateCompleteIndicator(RequeueSOVO requeueSOVO)
			throws BusinessServiceException {
		int result =0;
		try {
			// Modifying logic for SL-17507
			if(requeueSOVO.getQueueId().equals(SO_QUEUE.SO_QUEUE_FOLLOWUP) || requeueSOVO.getQueueId().equals(SO_QUEUE.SO_QUEUE_FOLLOWUP_FACILITIES)){
				result = pbfDao.updateCompleteIndicatorForFollowUp(requeueSOVO); 

			}else{
				result = pbfDao.updateCompleteIndicator(requeueSOVO); 
			}
		} catch (DataServiceException e) {
	          throw new BusinessServiceException(e.getMessage(), e);
	      }
		return result;
		
	}
	
	public void updatePOSCancellationIndicator(String soId)
			throws BusinessServiceException {
		try {
			pbfDao.updatePOSCancellationIndicator(soId);
		} catch (DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#updateRequeueDateTime(com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO)
	 */
	public int updateRequeueDateTime(RequeueSOVO requeueSOVO)
			throws BusinessServiceException {
		int result =0;
		try {
			if(requeueSOVO.getQueueId().equals(SO_QUEUE.SO_QUEUE_FOLLOWUP) || requeueSOVO.getQueueId().equals(SO_QUEUE.SO_QUEUE_FOLLOWUP_FACILITIES)){
			result = pbfDao.updateRequeueDateTimeFollowUp(requeueSOVO);
			}
			else
			{
			result = pbfDao.updateRequeueDateTime(requeueSOVO);
	
			}
			} catch (DataServiceException e) {
		          throw new BusinessServiceException(e.getMessage(), e);
		      }
		return result;
	
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO#insertNewCallBackQueue(com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO)
	 */
	public int insertNewCallBackQueue(RequeueSOVO requeueSOVO) throws BusinessServiceException {
		int result = 0;
		try {
				result = pbfDao.insertNewCallBackQueue(requeueSOVO);
		} catch (DataServiceException e) {
		          throw new BusinessServiceException(e.getMessage(), e);
		          
		}
		return result;
		
	}
	
	
	
	public boolean checkIfPendingQueues(String soID, String groupSOID, Integer resourceId, Integer companyId) throws BusinessServiceException {
		try {
			return pbfDao.checkIfPendingQueues(soID, groupSOID, resourceId, companyId);
		} catch(Exception e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	
	
	public boolean primaryQueueActionTaken(String soID, String groupSOId, Integer resourceId) throws BusinessServiceException {
		try {
		     return pbfDao.primaryQueueActionTaken(soID, groupSOId, resourceId);
		} catch(Exception e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
    

    
	/**
     * Starts the backend job which synchronizes wfm_so_queues 
     */
    public void updateWFMQueues() throws BusinessServiceException {
    	try {
    		pbfDao.updateWFMQueues();
    	} catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
    }

	public boolean isMaxFollowUpCountReached(RequeueSOVO requeueSOVO) throws BusinessServiceException {
		// TODO Auto-generated method stub
		boolean result;
		try {
    		result = pbfDao.isMaxFollowUpCountReached(requeueSOVO);
    	} catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
		return result;
	}
	
	public PBFilterVO getDestinationTabForSO(String soId) throws BusinessServiceException {
		try {
			return pbfDao.getDestinationTabForSO(soId);
		} catch (DataServiceException e) {
            throw new BusinessServiceException(e.getMessage(), e);
        }
	}
	
	public String getGroupId(String soId) throws BusinessServiceException {
		try {
			return pbfDao.getGroupId(soId);
		} catch(DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	
	//Sl-19820
	public String getGroupedId(String soId) throws BusinessServiceException {
		try {
			return pbfDao.getGroupId(soId);
		} catch(DataServiceException e) {
			throw new BusinessServiceException(e.getMessage(), e);
		}
	}
	
	// SLT-1613 START
	@Override
	public Map<String, List<WFMQueueVO>> getWfmQueueDetails(Integer buyerId) throws BusinessServiceException {

		logger.debug("Inside getWfmQueueDetails method");
		// SLT-1894
		Map<String, List<WFMQueueVO>> queueMap = new HashMap<String, List<WFMQueueVO>>();
		try {
			List<WFMQueueVO> standardQueues = pbfDao.getWfmQueueDetails();
			List<WFMQueueVO> buyerQueues = pbfDao.getWfmBuyerQueueDetails(buyerId);

			if (buyerQueues != null && buyerQueues.size() != 0) {
				List<WFMQueueVO> wfmStandardQueues = buyerQueues.stream()
						.filter(t -> t.getIsStandard().equals("Y") && t.getIsGeneric().equals("Y"))
						.collect(Collectors.toList());
				//Updating checkInd=Y for standard queues already configured for the buyer
				if (wfmStandardQueues != null && wfmStandardQueues.size() != 0) {
					Set<Integer> queueIds = wfmStandardQueues.stream().map(WFMQueueVO::getQueueId)
							.collect(Collectors.toSet());

					standardQueues.removeIf(queue -> queueIds.contains(queue.getQueueId()));
					standardQueues.addAll(wfmStandardQueues);
					buyerQueues.removeIf(queue -> queueIds.contains(queue.getQueueId()));
				}
				queueMap.put(Constants.BUYER_QUEUES, buyerQueues);
			}
			if (standardQueues != null && standardQueues.size() != 0) {
				queueMap.put(Constants.STANDARD_QUEUES, standardQueues);
			}

		} catch (DataServiceException e) {
			logger.error(e.getMessage());
			throw new BusinessServiceException(e.getMessage(), e);
		}
		return queueMap;
	}

	@Override
	public void saveWfmBuyerQueues(List<BuyerQueueVO> wfmBuyerQueues, List<Integer> queueIdList, Integer buyerId)
			throws BusinessServiceException {

		logger.debug("Inside saveWfmBuyerQueues method");
		try {
			if (wfmBuyerQueues != null && wfmBuyerQueues.size() > 0) {
				pbfDao.saveWfmBuyerQueues(wfmBuyerQueues);
			}

			if (queueIdList != null && queueIdList.size() > 0) {
				pbfDao.deleteWfmBuyerQueues(buyerId, queueIdList);
			} else {
				pbfDao.deleteAllWfmBuyerQueues(buyerId);
			}

		} catch (DataServiceException e) {
			logger.error(e.getMessage());
			throw new BusinessServiceException(e.getMessage(), e);
		}

	}

	// SLT-1613 END
}



/*
 * Maintenance History $Log: PowerBuyerBOImpl.java,v $ Revision 1.18 2008/04/26
 * 00:40:35 glacy Shyam: Merged I18_Fin to HEAD.
 * 
 * Revision 1.16.20.1 2008/04/23 11:42:23 glacy Shyam: Merged HEAD to finance.
 * 
 * Revision 1.17 2008/04/23 05:01:43 hravi Reverting to build 247.
 * 
 * Revision 1.16 2008/01/25 18:13:00 rambewa added reasoncode
 * 
 * Revision 1.15 2008/01/25 00:11:33 rambewa added isCurrentUserTheClaimedUser
 * 
 * Revision 1.14 2008/01/24 02:14:51 mhaye05 added the logic needed unclaim and
 * remove reque entries when we claim a SO
 * 
 * Revision 1.13 2008/01/23 04:44:46 rambewa removed unclaim method
 * 
 * Revision 1.12 2008/01/23 04:30:40 rambewa refactor unclaim with new methods
 * 
 * Revision 1.11 2008/01/22 20:44:06 mhaye05 added method
 * claimSpecificSO(String, Integer, Integer)
 * 
 * Revision 1.10 2008/01/21 23:17:55 mhaye05 needed to add the buyer id to the
 * methods calls that are used when claiming the next service order
 * 
 * Revision 1.9 2008/01/21 23:01:57 rambewa expired claims
 * 
 * Revision 1.8 2008/01/19 04:02:05 rambewa *** empty log message ***
 * 
 * Revision 1.7 2008/01/18 22:39:25 mhaye05 updated to make sure we use the
 * buyer id when claiming the next so
 * 
 * Revision 1.6 2008/01/18 01:59:06 mhaye05 added logic to claim the next
 * service order
 * 
 * Revision 1.5 2008/01/17 22:10:09 rambewa *** empty log message ***
 * 
 * Revision 1.4 2008/01/16 15:53:26 mhaye05 added logic to retrieve the claimed
 * service orders
 * 
 * Revision 1.3 2008/01/15 17:05:14 mhaye05 changed parameter name from buyerId
 * to resourceId for the claim and unclaim methods as we need to be working with
 * resources at this level not the company
 * 
 * Revision 1.2 2008/01/14 20:38:26 mhaye05 check in point where filter summary
 * load complete
 * 
 */
