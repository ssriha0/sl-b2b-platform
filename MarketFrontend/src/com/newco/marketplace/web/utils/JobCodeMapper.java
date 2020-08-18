package com.newco.marketplace.web.utils;

import java.util.ArrayList;
import java.util.List;

import com.newco.marketplace.dto.vo.buyerskutask.BuyerSkuTaskVO;
import com.newco.marketplace.dto.vo.jobcode.JobCodeMappingVO;
import com.newco.marketplace.web.dto.JobCodeMappingDTO;
import com.newco.marketplace.web.dto.SkuTaskDTO;

public class JobCodeMapper {

	private static final Integer SUB_CATEGORY_TYPE = 3;
	private static final Integer CATEGORY_TYPE = 2;
	
	public static BuyerSkuTaskVO mapSkuTaskDtoTOSkuTaskVO(SkuTaskDTO skuTaskDTO, String jobCode, 
									String specCode, Integer buyerId){
		BuyerSkuTaskVO buyerSkuTaskVO = null;		
		
		if(skuTaskDTO != null){
			buyerSkuTaskVO = new BuyerSkuTaskVO();
			
			if(skuTaskDTO.getSubCategoryId() != null
					&& skuTaskDTO.getSubCategoryId() > 0){
				buyerSkuTaskVO.setNodeId(skuTaskDTO.getSubCategoryId());
			}else if(skuTaskDTO.getCategoryId() !=null
					&& skuTaskDTO.getCategoryId() > 0){
				buyerSkuTaskVO.setNodeId(skuTaskDTO.getCategoryId());
			}		
			buyerSkuTaskVO.setSkillId(skuTaskDTO.getSkillId());
			buyerSkuTaskVO.setSku(jobCode);	
			buyerSkuTaskVO.setSpecCode(specCode);
			buyerSkuTaskVO.setBuyerId(buyerId);
			
		}
		return buyerSkuTaskVO;
	}
	
	public static BuyerSkuTaskVO mapSkuTaskDtoTOSkuTaskVO(SkuTaskDTO skuTaskDTO, String jobCode){
		return mapSkuTaskDtoTOSkuTaskVO(skuTaskDTO,jobCode, null,null);
	}
	
	public static BuyerSkuTaskVO mapSkuTaskDtoTOSkuTaskVO(SkuTaskDTO skuTaskDTO, String jobCode, String specCode){
		return mapSkuTaskDtoTOSkuTaskVO(skuTaskDTO,jobCode, specCode,null);
	}
	
	public static JobCodeMappingDTO mapJobCodeMappingVoToDto(JobCodeMappingVO jobCodeMappingVO){
		JobCodeMappingDTO jobCodeMappingDTO = null;
		if(jobCodeMappingVO != null){
			jobCodeMappingDTO = new JobCodeMappingDTO();
			
			jobCodeMappingDTO.setInclusionDescr(jobCodeMappingVO.getInclusionDescr());
			jobCodeMappingDTO.setJobCode(jobCodeMappingVO.getJobCode());
			jobCodeMappingDTO.setMajorHeadingDescr(jobCodeMappingVO.getMajorHeadingDescr());
			
			List<SkuTaskDTO> skuTaskDTOList = mapSkuTaskVoTOSkuTaskDto(jobCodeMappingVO.getSkuTaskList());
			jobCodeMappingDTO.setSkuTaskList(skuTaskDTOList);
			jobCodeMappingDTO.setSpecCode(jobCodeMappingVO.getSpecCode());
			jobCodeMappingDTO.setSubHeadingDescr(jobCodeMappingVO.getSubHeadingDescr());
			jobCodeMappingDTO.setTemplateId(jobCodeMappingVO.getTemplateId());
			jobCodeMappingDTO.setTaskComment(jobCodeMappingVO.getTaskComment());
			jobCodeMappingDTO.setTaskName(jobCodeMappingVO.getTaskName());
			jobCodeMappingDTO.setTemplateList(jobCodeMappingVO.getTemplateList());
		}
		return jobCodeMappingDTO;
	}
	
	public static JobCodeMappingVO mapJobCodeMappingDtoToVo(JobCodeMappingDTO jobCodeMappingDTO, Integer buyerId){
		JobCodeMappingVO jobCodeMappingVO = null;
		if(jobCodeMappingDTO != null){
			jobCodeMappingVO = new JobCodeMappingVO();
			
			String jobCode = jobCodeMappingDTO.getJobCode();
			String specCode = jobCodeMappingDTO.getSpecCode();
			Integer templateId = jobCodeMappingDTO.getTemplateId();
			
			jobCodeMappingVO.setInclusionDescr(jobCodeMappingDTO.getInclusionDescr());
			jobCodeMappingVO.setJobCode(jobCode);
			jobCodeMappingVO.setMajorHeadingDescr(jobCodeMappingDTO.getMajorHeadingDescr());
			List<SkuTaskDTO> skuTaskList = jobCodeMappingDTO.getSkuTaskList();
			 List<BuyerSkuTaskVO> skutTaskVOList = mapSkuTaskDtoListTOSkuTaskVO(skuTaskList, jobCode, specCode, buyerId, templateId);
			
			jobCodeMappingVO.setSkuTaskList(skutTaskVOList);
			jobCodeMappingVO.setSpecCode(specCode);
			jobCodeMappingVO.setSubHeadingDescr(jobCodeMappingDTO.getSubHeadingDescr());
			jobCodeMappingVO.setTaskComment(jobCodeMappingDTO.getTaskComment());
			jobCodeMappingVO.setTaskName(jobCodeMappingDTO.getTaskName());
			jobCodeMappingVO.setTemplateId(templateId);
			jobCodeMappingVO.setTemplateList(jobCodeMappingDTO.getTemplateList());
		}
		return jobCodeMappingVO;
	}
	
	public static List<BuyerSkuTaskVO> mapSkuTaskDtoListTOSkuTaskVO(List<SkuTaskDTO> skuTaskDtoList,
			 				String jobCode, String specCode, Integer buyerId, Integer templateId){
		List<BuyerSkuTaskVO> skuTaskVoList = null;
		
		if(skuTaskDtoList != null){
			skuTaskVoList = new ArrayList<BuyerSkuTaskVO>();
			
			for(SkuTaskDTO skuTaskDTO: skuTaskDtoList){
				BuyerSkuTaskVO buyerSkuTaskVO = new BuyerSkuTaskVO();
				
				buyerSkuTaskVO.setBuyerId(buyerId);
				buyerSkuTaskVO.setCategoryName(skuTaskDTO.getCategoryName());
				if(skuTaskDTO.getSubCategoryId() != null 
						&& skuTaskDTO.getSubCategoryId() > 0){
					buyerSkuTaskVO.setNodeId(skuTaskDTO.getSubCategoryId());
				}else{
					buyerSkuTaskVO.setNodeId(skuTaskDTO.getCategoryId());
				}
				
				buyerSkuTaskVO.setSkillId(skuTaskDTO.getSkillId());
				buyerSkuTaskVO.setSkillName(skuTaskDTO.getSkillName());
				buyerSkuTaskVO.setSku(jobCode);
				buyerSkuTaskVO.setSpecCode(specCode);
				buyerSkuTaskVO.setSubCategoryName(skuTaskDTO.getSubCategoryName());
				buyerSkuTaskVO.setTemplateId(templateId);
				
				skuTaskVoList.add(buyerSkuTaskVO);
			}
		}
		return skuTaskVoList;
	}
	
	public static List<SkuTaskDTO> mapSkuTaskVoTOSkuTaskDto(List<BuyerSkuTaskVO> skuTaskList){
		List<SkuTaskDTO> skuTaskDTOList = null;
		
		if(skuTaskList != null){
			skuTaskDTOList = new ArrayList<SkuTaskDTO>(); 
			for(BuyerSkuTaskVO skuTaskVo: skuTaskList){
				SkuTaskDTO skuTaskDTO = new SkuTaskDTO();
				
				if(skuTaskVo.getSkillLevel().equals(CATEGORY_TYPE)){
					skuTaskDTO.setCategoryId(skuTaskVo.getNodeId());
				}else if(skuTaskVo.getSkillLevel().equals(SUB_CATEGORY_TYPE)){
					skuTaskDTO.setSubCategoryId(skuTaskVo.getNodeId());
				}				
				skuTaskDTO.setSkillId(skuTaskVo.getSkillId());
				skuTaskDTO.setCategoryName(skuTaskVo.getCategoryName());
				skuTaskDTO.setSkillName(skuTaskVo.getSkillName());
				skuTaskDTO.setSubCategoryName(skuTaskVo.getSubCategoryName());
				
				skuTaskDTOList.add(skuTaskDTO);
			}
		}
		return skuTaskDTOList;
	}
}
