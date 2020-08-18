package com.newco.marketplace.web.delegatesImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.businessImpl.skillTree.MarketplaceSearchBean;
import com.newco.marketplace.business.iBusiness.jobcode.IJobCodeMappingBO;
import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.dto.vo.jobcode.JobCodeMappingVO;
import com.newco.marketplace.dto.vo.skillTree.SkillNodeVO;
import com.newco.marketplace.exception.BusinessServiceException;
import com.newco.marketplace.web.delegates.IJobCodeDelegate;
import com.newco.marketplace.web.dto.JobCodeMappingDTO;
import com.newco.marketplace.web.dto.SkuTaskDTO;
import com.newco.marketplace.web.utils.JobCodeMapper;

public class JobCodeDelegateImpl implements IJobCodeDelegate {

	private static final Logger logger = Logger.getLogger(JobCodeDelegateImpl.class.getName());
	private IJobCodeMappingBO jobCodeMappingBO;
	private MarketplaceSearchBean marketplaceSearchBean;

	public void addSkuTask(SkuTaskDTO skuTaskDTO, String jobCode, String specCode, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeDelegateImpl --> addSkuTask()");
		try {
			if (skuTaskDTO != null && StringUtils.isNotBlank(jobCode)) {
				BuyerSkuTaskVO buyerSkuTaskVO = JobCodeMapper.mapSkuTaskDtoTOSkuTaskVO(skuTaskDTO, jobCode, specCode, buyerId);

				getJobCodeMappingBO().addSkuTask(buyerSkuTaskVO);
			} else {
				logger.error("Exception in JobCodeDelegateImpl --> addSkuTask(). SkuTaskDTO is null" + " or job code is null");
				throw new BusinessServiceException("Illegal Argument. SkuTaskDTO is null");
			}
		} catch (BusinessServiceException e) {
			logger.error("Exception in JobCodeDelegateImpl --> addSkuTask() due to " + e.getMessage());
			throw e;
		}
		logger.debug("Leaving JobCodeDelegateImpl --> addSkuTask()");
	}

	public List<SkuTaskDTO> getSkuTasks(String jobCode, String specCode, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeDelegateImpl --> getSkuTasks()");
		List<SkuTaskDTO> skuTaskDtoList = null;
		try {
			if (StringUtils.isNotBlank(jobCode)) {
				List<BuyerSkuTaskVO> buyerSkuTaskList = getJobCodeMappingBO().getSkuTaskList(jobCode, specCode, buyerId);

				skuTaskDtoList = JobCodeMapper.mapSkuTaskVoTOSkuTaskDto(buyerSkuTaskList);
			} else {
				logger.error("Exception in JobCodeDelegateImpl --> addSkuTask(). SkuTaskDTO is null");
				throw new BusinessServiceException("Illegal Argument. jobCode is null or empty");
			}
		} catch (BusinessServiceException e) {
			logger.error("Exception in JobCodeDelegateImpl --> getSkuTasks() due to " + e.getMessage());
			throw e;
		}
		logger.debug("Leaving JobCodeDelegateImpl --> getSkuTasks()");
		return skuTaskDtoList;
	}

	public void deleteSkuTask(SkuTaskDTO skuTaskDTO, String jobCode, String specCode, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeDelegateImpl --> deleteSkuTask()");
		try {
			if (skuTaskDTO != null && StringUtils.isNotBlank(jobCode)) {
				BuyerSkuTaskVO buyerSkuTaskVO = JobCodeMapper.mapSkuTaskDtoTOSkuTaskVO(skuTaskDTO, jobCode, specCode, buyerId);

				getJobCodeMappingBO().deleteSkuTask(buyerSkuTaskVO);
			} else {
				logger.error("Exception in JobCodeDelegateImpl --> addSkuTask(). SkuTaskDTO is null" + " or job code is null");
				throw new BusinessServiceException("Illegal Argument. SkuTaskDTO is null");
			}
		} catch (BusinessServiceException bse) {
			logger.error("Exception in JobCodeDelegateImpl --> deleteSkuTask() due to " + bse.getMessage());
			throw bse;
		}
		logger.debug("Leaving JobCodeDelegateImpl --> deleteSkuTask()");
	}

	public JobCodeMappingDTO getJobCodeDetails(String jobCode, String specCode, Integer buyerId) throws BusinessServiceException {
		JobCodeMappingDTO jobCodeMappingDTO = null;
		logger.debug("Entering JobCodeDelegateImpl --> getJobCodeDetails()");
		Integer nodeId = null;
		Integer parentNodeId = null;
		try {
			if (StringUtils.isNotBlank(jobCode) && StringUtils.isNotBlank(specCode)) {
				JobCodeMappingVO jobCodeMappingVO = getJobCodeMappingBO().getJobCodeDetails(jobCode, specCode, buyerId);
				if (jobCodeMappingVO != null) {
					List<BuyerSkuTaskVO> buyerSkuList = jobCodeMappingVO.getSkuTaskList();
					if (buyerSkuList != null && buyerSkuList.size() > 0) {
						nodeId = buyerSkuList.get(0).getNodeId();

						ArrayList<SkillNodeVO> skillList = marketplaceSearchBean.getParentSkillStructById(nodeId);

						if (skillList != null) {
							for (int i = 0; i < skillList.size(); i++) {
								if (skillList.get(i).getParentNodeId() == 0) {
									parentNodeId = skillList.get(i).getNodeId();
									break;
								}
							}
						}
					}
				}
				jobCodeMappingDTO = JobCodeMapper.mapJobCodeMappingVoToDto(jobCodeMappingVO);
				jobCodeMappingDTO.setMainCategoryId(parentNodeId);
			}
		} catch (BusinessServiceException bse) {
			logger.error("Exception in JobCodeDelegateImpl --> getJobCodeDetails() due to " + bse.getMessage());
			throw bse;
		}
		logger.debug("Leaving JobCodeDelegateImpl --> getJobCodeDetails()");
		return jobCodeMappingDTO;
	}

	public Map<Integer, String> getSoTemplates(Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeDelegateImpl --> getSoTemplates()");
		Map<Integer, String> templateList = null;
		try {
			if (buyerId != null) {
				templateList = getJobCodeMappingBO().getSoTemplates(buyerId);
			}
		} catch (BusinessServiceException bse) {
			logger.error("Exception in JobCodeDelegateImpl --> getSoTemplates() due to " + bse.getMessage());
			throw bse;
		}
		logger.debug("Leaving JobCodeDelegateImpl --> getSoTemplates()");
		return templateList;
	}

	public void saveJobCode(JobCodeMappingDTO jobCodeMappingDTO, Integer buyerId) throws BusinessServiceException {
		logger.debug("Entering JobCodeDelegateImpl --> saveJobCode()");
		try {
			if (jobCodeMappingDTO != null) {
				JobCodeMappingVO jobCodeMappingVO = JobCodeMapper.mapJobCodeMappingDtoToVo(jobCodeMappingDTO, buyerId);

				if (jobCodeMappingVO != null) {
					getJobCodeMappingBO().saveJobCode(jobCodeMappingVO);
				}
			}
		} catch (BusinessServiceException bse) {
			logger.error("Exception in JobCodeDelegateImpl --> saveJobCode() due to " + bse.getMessage());
			throw bse;
		}
		logger.debug("Leaving JobCodeDelegateImpl --> saveJobCode()");
	}

	/**
	 * @return the jobCodeMappingBO
	 */
	public IJobCodeMappingBO getJobCodeMappingBO() {
		return jobCodeMappingBO;
	}

	/**
	 * @param jobCodeMappingBO
	 *            the jobCodeMappingBO to set
	 */
	public void setJobCodeMappingBO(IJobCodeMappingBO jobCodeMappingBO) {
		this.jobCodeMappingBO = jobCodeMappingBO;
	}

	/**
	 * @return the marketplaceSearchBean
	 */
	public MarketplaceSearchBean getMarketplaceSearchBean() {
		return marketplaceSearchBean;
	}

	/**
	 * @param marketplaceSearchBean
	 *            the marketplaceSearchBean to set
	 */
	public void setMarketplaceSearchBean(MarketplaceSearchBean marketplaceSearchBean) {
		this.marketplaceSearchBean = marketplaceSearchBean;
	}

}
