package com.newco.marketplace.web.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.dto.vo.WFMBuyerQueueVO;
import com.newco.marketplace.dto.vo.WFMQueueVO;
import com.newco.marketplace.dto.vo.WFMSOTasksVO;
import com.newco.marketplace.dto.vo.group.QueueTasksGroupVO;
import com.newco.marketplace.dto.vo.powerbuyer.RequeueSOVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.web.dto.WFMBuyerQueueDTO;
import com.newco.marketplace.web.dto.WFMCategoryDTO;
import com.newco.marketplace.web.dto.WFMSOTasksDTO;
import com.newco.marketplace.web.dto.ajax.SOQueueNoteDTO;

/**
 * 
 * WFMBuyerQueueMapper.java
 * @author Munish Joshi
 * Jan 28, 2009
 */
public class WFMBuyerQueueMapper extends ObjectMapper implements OrderConstants {
	private static final Logger logger = Logger.getLogger(WFMBuyerQueueMapper.class.getName());
	private IPowerBuyerBO powerBuyerBO;

	public IPowerBuyerBO getPowerBuyerBO() {
		return powerBuyerBO;
	}

	public void setPowerBuyerBO(IPowerBuyerBO powerBuyerBO) {
		this.powerBuyerBO = powerBuyerBO;
	}

	/*
	 * @deprecated
	 * (non-Javadoc)
	 *
	 * @see com.newco.marketplace.web.utils.ObjectMapper#convertDTOtoVO(java.lang.Object,
	 *      java.lang.Object)
	 */
	public List<WFMBuyerQueueDTO> convertVOListtoDTOList(List<WFMBuyerQueueVO> wfmBuyerQueueVOList, List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList) {
		if (wfmBuyerQueueVOList != null) {
			List<WFMSOTasksDTO> wfmSoTaskList = new ArrayList<WFMSOTasksDTO>();
			WFMSOTasksDTO wfmSOTasksDTO = null;
			WFMBuyerQueueDTO wfmBuyerQueueDTO = new WFMBuyerQueueDTO();
			WFMBuyerQueueVO tmpWfmBuyerQueueVO = new WFMBuyerQueueVO();
			// Pulling out the unique queues from the queue list in wfmBuyerQueueVO
			// Very messy code. 
			// Need to clean this. Because same queue will have multiple tasks associated.

			// TODO : Refactor this - and Put the primary queue on the top 
			for (WFMBuyerQueueVO wfmBuyerQueueVO : wfmBuyerQueueVOList) {
				if (tmpWfmBuyerQueueVO.getQId() != null && !wfmBuyerQueueVO.getQId().equals(tmpWfmBuyerQueueVO.getQId())) {
					wfmBuyerQueueDTO.setWfmSOTasks(wfmSoTaskList);
					// Only add to the DTOList when its not same as the previous one.
					wfmBuyerQueueDTOList.add(wfmBuyerQueueDTO);
					wfmBuyerQueueDTO = new WFMBuyerQueueDTO();
					wfmSoTaskList = new ArrayList<WFMSOTasksDTO>();
				}
				createQueueDTOfromQueueVO(wfmBuyerQueueVO, wfmBuyerQueueDTO);

				wfmSOTasksDTO = new WFMSOTasksDTO();
				wfmSOTasksDTO.setSoTaskId(wfmBuyerQueueVO.getTaskId());
				wfmSOTasksDTO.setTaskCode(wfmBuyerQueueVO.getTaskCode());
				wfmSOTasksDTO.setTaskDesc(wfmBuyerQueueVO.getTaskDesc());
				wfmSOTasksDTO.setTaskState(wfmBuyerQueueVO.getTaskState());

				wfmSoTaskList.add(wfmSOTasksDTO);
				tmpWfmBuyerQueueVO = wfmBuyerQueueVO;
			}
			if (wfmBuyerQueueDTO.getQueueId() != null) {
				wfmBuyerQueueDTO.setWfmSOTasks(wfmSoTaskList);
				wfmBuyerQueueDTOList.add(wfmBuyerQueueDTO);
			}
		}
		return wfmBuyerQueueDTOList;
	}

	/**
	 * 
	 * convertDTOtoRequeueVO
	 * RequeueSOVO 
	 * @param sOQueueNoteDTO The {@link SOQueueNoteDTO} object.
	 * @return The  VO object to store in db {@link RequeueSOVO}
	 * @throws ParseException
	 */

	public RequeueSOVO convertDTOtoRequeueVO(SOQueueNoteDTO sOQueueNoteDTO) throws BusinessServiceException {

		RequeueSOVO requeueSOVO = new RequeueSOVO();

		if (sOQueueNoteDTO != null) {
			// set the note id just generated while inserting/creating note in so_notes.
			requeueSOVO.setNoteId(sOQueueNoteDTO.getNoteId());
			requeueSOVO.setBuyerId(new Integer(sOQueueNoteDTO.getBuyerId()));
			if (sOQueueNoteDTO.getTaskState() != null && sOQueueNoteDTO.getTaskState().equalsIgnoreCase(OrderConstants.ENDSTATE)) {
				requeueSOVO.setCompletedInd(OrderConstants.COMPELETE_INDICATOR_ONE);		
				logger.debug("updatePOSCancellationIndicator sOQueueNoteDTO.getSoId()="+sOQueueNoteDTO.getSoId());
				logger.debug("updatePOSCancellationIndicator Q ID ="+sOQueueNoteDTO.getQueueID());				
				if(OrderConstants.RESOLUTION_REQUIRED_QUEUE_ID.equals(sOQueueNoteDTO.getQueueID()))
				{	
					logger.debug("RESOLUTION_REQUIRED_QUEUE");				
					powerBuyerBO.updatePOSCancellationIndicator(sOQueueNoteDTO.getSoId());
				}
			} else {
				// Don't reset the complete indicator to zero if its re-queue.
				//requeueSOVO.setCompletedInd(OrderConstants.COMPELETE_INDICATOR_ZERO);
				setRequeueDateInVO(sOQueueNoteDTO, requeueSOVO);
			}
			// Check if we have SOid or GroupId
			if(StringUtils.isBlank(sOQueueNoteDTO.getSoId())){
				requeueSOVO.setGroupSOId(sOQueueNoteDTO.getGroupId());
			} else{
				requeueSOVO.setSoId(sOQueueNoteDTO.getSoId());
			}
			requeueSOVO.setQueueId(new Integer(sOQueueNoteDTO.getQueueID()));
			requeueSOVO.setQueueSeq(sOQueueNoteDTO.getQueueSeq());
		}

		return requeueSOVO;

	}

	/**
	 * setRequeueDateInVO
	 * void 
	 * @param sOQueueNoteDTO
	 * @param requeueSOVO
	 * @throws BusinessServiceException
	 */
	private void setRequeueDateInVO(SOQueueNoteDTO sOQueueNoteDTO, RequeueSOVO requeueSOVO) throws BusinessServiceException {
		if (sOQueueNoteDTO.getRequeueDate() != null && sOQueueNoteDTO.getRequeueTime() != null
				&& !sOQueueNoteDTO.getRequeueDate().trim().equals("") && !sOQueueNoteDTO.getRequeueTime().equalsIgnoreCase("[HH:MM]")) {
			// Make sure the date string is validated before converting DTO to VO
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			Date requeueDate = null;
			try {
				requeueDate = TimeUtils.combineDateTimeFromFormat(df.parse(sOQueueNoteDTO.getRequeueDate()), sOQueueNoteDTO.getRequeueTime(), "yyyy-MM-dd" ,  "hh:mm a");
			} catch (ParseException e) {
				throw new BusinessServiceException(e.getMessage()+" The date can not be parsed");
			}

			requeueSOVO.setRequeueDate(requeueDate);

		}
	}

	/**
	 * 
	 * convertQueueVOtoDTO This method combines the tasks and queue related
	 * data. since each queue can have many tasks, the task data is created as WFMSOTasksDTO object
	 * and then placed inside the WFMBuyerQueueDTO class object.
	 * 
	 * @param queueTasksGroupVO
	 * @param wfmBuyerQueueDTOList
	 * @return
	 */

	public List<WFMBuyerQueueDTO> convertQueueVOtoDTO(QueueTasksGroupVO queueTasksGroupVO, List<WFMBuyerQueueDTO> wfmBuyerQueueDTOList) {
		List<WFMBuyerQueueVO> wfmBuyerQueueVOList = null;
		List<WFMSOTasksVO> wfmSOTasksVO = null;
		List<WFMSOTasksDTO> wfmSoTaskList = null;
		WFMSOTasksDTO wfmSOTasksDTO = null;

		// Extract the queue details and tasks from grouped VO.
		if (queueTasksGroupVO != null) {
			wfmBuyerQueueVOList = queueTasksGroupVO.getWFMBuyerQueueVO();
			wfmSOTasksVO = queueTasksGroupVO.getWFMSOTasksVO();
		}

		// If we have list details and tasks, create association. Put each task
		// list in its respective queue.
		if (wfmBuyerQueueVOList != null && wfmSOTasksVO != null) {
			// For each single queue put the task list as an object in the queue
			// DTO.
			for (WFMBuyerQueueVO wfmBuyerQueueVO : wfmBuyerQueueVOList) {

				WFMBuyerQueueDTO wfmBuyerQueueDTO = new WFMBuyerQueueDTO();
				wfmSoTaskList = new ArrayList<WFMSOTasksDTO>();

				// Extract the tasks meeting the queue id.
				for (WFMSOTasksVO tasksVO : wfmSOTasksVO) {
						wfmSOTasksDTO = createSOTasksDTO(wfmBuyerQueueVO, tasksVO);
						// Add each task to the Task list, which is added in queue DTO later outside this for loop.
						wfmSoTaskList.add(wfmSOTasksDTO);
				}
				// Add the list of tasks to the WFMSOTasksDTO DTO class
				createQueueDTOfromQueueVO(wfmBuyerQueueVO, wfmBuyerQueueDTO);

				if (wfmBuyerQueueDTO.getQueueId() != null) {
					wfmBuyerQueueDTO.setWfmSOTasks(wfmSoTaskList);
					
					wfmBuyerQueueDTOList.add(wfmBuyerQueueDTO);
					// Set the unique random number in order to identify the spans on different tabs.
					wfmBuyerQueueDTO.setUniqueNumber(generateUniqueRandomNumber());
					
					
				}

			}

		}

		return wfmBuyerQueueDTOList;
	}

	/**
	 * 
	 * convertQueueVOtoDTO This method is only to display the new Call Back queue.
	 * It is different from other queues as This queue has to be displayed only once in jsp.
	 * WFMBuyerQueueDTO 
	 * @param wfmSOTasksVOList
	 * @param wfmBuyerQueueDTO
	 * @param buyerId
	 * @return
	 */
	
	public WFMBuyerQueueDTO convertQueueVOtoDTO( List<WFMSOTasksVO> wfmSOTasksVOList, WFMBuyerQueueDTO wfmBuyerQueueDTO, String buyerId) {
				
		wfmBuyerQueueDTO.setBuyerId(buyerId);
		// The query is returning queueid, queue name multiple times, so just add them once in code. use index 0
		if (wfmSOTasksVOList != null && wfmSOTasksVOList.size() > 0) {
			WFMSOTasksVO wfmtaksVO = wfmSOTasksVOList.get(0);
			wfmBuyerQueueDTO.setQueueId(wfmtaksVO.getQId());
			wfmBuyerQueueDTO.setQueueName(wfmtaksVO.getQName());
			wfmBuyerQueueDTO.setUniqueNumber(generateUniqueRandomNumber());
		}

		// Create task code and task desc list , add it to the DTO object.
		if (wfmSOTasksVOList != null && wfmSOTasksVOList.size() > 0) {
			List<WFMSOTasksDTO> wfmSoTaskList = new ArrayList<WFMSOTasksDTO>();
			for (WFMSOTasksVO tasksVO : wfmSOTasksVOList) {

				WFMSOTasksDTO wfmSOTasksDTO = new WFMSOTasksDTO();
				wfmSOTasksDTO.setSoTaskId(tasksVO.getTaskId());
				wfmSOTasksDTO.setTaskCode(StringEscapeUtils.escapeHtml(tasksVO.getTaskCode()));
				wfmSOTasksDTO.setTaskDesc(StringEscapeUtils.escapeHtml(tasksVO.getTaskDesc()));
				wfmSOTasksDTO.setTaskState(tasksVO.getTaskState());
				wfmSOTasksDTO.setRequeueHours(tasksVO.getRequeueHours());
				wfmSOTasksDTO.setRequeueMins(tasksVO.getRequeueMins());
				wfmSoTaskList.add(wfmSOTasksDTO);
			}
			wfmBuyerQueueDTO.setWfmSOTasks(wfmSoTaskList);
		}
		
	 return wfmBuyerQueueDTO;
	
	
	}
	
	
	/**
	 * createQueueDTOfromQueueVO void
	 * 
	 * @param wfmBuyerQueueVO
	 * @param wfmBuyerQueueDTO
	 */
	private void createQueueDTOfromQueueVO(WFMBuyerQueueVO wfmBuyerQueueVO, WFMBuyerQueueDTO wfmBuyerQueueDTO) {
		// Add the list of tasks to the WFMSOTasksDTO DTO class

		wfmBuyerQueueDTO.setBuyerId(wfmBuyerQueueVO.getBuyerId());
		wfmBuyerQueueDTO.setQueueId(wfmBuyerQueueVO.getQId());
		wfmBuyerQueueDTO.setQueueName(wfmBuyerQueueVO.getQName());
		wfmBuyerQueueDTO.setQueuedDate(wfmBuyerQueueVO.getQueuedDate());
		wfmBuyerQueueDTO.setSoQueueId(wfmBuyerQueueVO.getSoQueueId());
		wfmBuyerQueueDTO.setNote(wfmBuyerQueueVO.getNote());
		wfmBuyerQueueDTO.setQueueSeq(wfmBuyerQueueVO.getQueueSeq());
		wfmBuyerQueueDTO.setClaimedFromQueueId(wfmBuyerQueueVO.getClaimedFromQueueId());
		wfmBuyerQueueDTO.setActionTaken(wfmBuyerQueueVO.getActionTaken());
		wfmBuyerQueueDTO.setCompletedInd(wfmBuyerQueueVO.getCompletedInd());
		
		
	}

	/**
	 * createSOTasksDTO
	 * WFMSOTasksDTO 
	 * @param wfmBuyerQueueVO
	 * @param tasksVO
	 * @return
	 */
	private WFMSOTasksDTO createSOTasksDTO(WFMBuyerQueueVO wfmBuyerQueueVO, WFMSOTasksVO tasksVO) {
		WFMSOTasksDTO wfmSOTasksDTO;
		wfmSOTasksDTO = new WFMSOTasksDTO();
		tweakTasksVOForBuyer(wfmBuyerQueueVO, tasksVO);
		wfmSOTasksDTO.setSoTaskId(tasksVO.getTaskId());
		/*Added queue id in Dto to differentiate wfmSoTask based on qId 
		  to display in frond end for respective queues action drop down
		*/
		wfmSOTasksDTO.setQueueId(tasksVO.getQId());
		wfmSOTasksDTO.setTaskCode(StringEscapeUtils.escapeHtml(tasksVO.getTaskCode()));
		wfmSOTasksDTO.setTaskDesc(StringEscapeUtils.escapeHtml(tasksVO.getTaskDesc()));
		wfmSOTasksDTO.setTaskState(tasksVO.getTaskState());
		wfmSOTasksDTO.setRequeueHours(tasksVO.getRequeueHours());
		wfmSOTasksDTO.setRequeueMins(tasksVO.getRequeueMins());
		return wfmSOTasksDTO;
	}
	
	//This method is for changing any wfm task table level data like task codes 
	private void tweakTasksVOForBuyer(WFMBuyerQueueVO wfmBuyerQueueVO,
			WFMSOTasksVO tasksVO) {
		if(wfmBuyerQueueVO.buyerId.equals(OrderConstants.OFFICEMAX_BUYER)){
			if(tasksVO.getTaskCode() != null && tasksVO.getTaskDesc() != null){
				tasksVO.setTaskCode(tasksVO.getTaskCode().replaceAll("Assurant", "OfficeMax"));
				tasksVO.setTaskDesc(tasksVO.getTaskDesc().replaceAll("Assurance", "OfficeMax"));
				tasksVO.setTaskDesc(tasksVO.getTaskDesc().replaceAll("Assurant", "OfficeMax"));
			}
		}
		
	}

	/**
	 * TODO : add this method some place where its common
	 * generateUniqueRandomNumber
	 * Integer 
	 * @return
	 */
	private static Integer generateUniqueRandomNumber(){
		Random generator = new Random();
		return new Integer(generator.nextInt(99999999));
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(generateUniqueRandomNumber());
		System.out.println(generateUniqueRandomNumber());
		System.out.println(generateUniqueRandomNumber());
		
		
	}
	
	// SLT-1613 START
	public List<WFMCategoryDTO> convertVOListtoDTOList(List<WFMQueueVO> wfmQueues) {

		List<WFMCategoryDTO> wfmQueueDtos = null;
		if (wfmQueues != null && wfmQueues.size() > 0) {

			Map<String, List<WFMQueueVO>> queuesByCategory = null;
			queuesByCategory = wfmQueues.stream().collect(Collectors.groupingBy(WFMQueueVO::getCategory));

			if (queuesByCategory != null && queuesByCategory.size() > 0) {
				wfmQueueDtos = new ArrayList<WFMCategoryDTO>();
				for (String category : queuesByCategory.keySet()) {
					WFMCategoryDTO wfmCategoryDTO = new WFMCategoryDTO();
					wfmCategoryDTO.setCategory(category);
					wfmCategoryDTO.setWfmQueueVos(queuesByCategory.get(category));
					wfmQueueDtos.add(wfmCategoryDTO);
				}
			}
		}
		return wfmQueueDtos;
	}
	//SLT-1613 END
	
}
