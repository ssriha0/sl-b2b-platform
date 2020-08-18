/**
 * 
 */
package com.newco.marketplace.util.spn;

import java.math.BigDecimal;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.newco.marketplace.dto.vo.spn.ApprovalModel;
import com.newco.marketplace.util.spn.CriteriaEnum;
import static com.newco.marketplace.interfaces.SPNConstants.CRITERIA_VALUE_TRUE;
import com.newco.marketplace.dto.vo.spn.ProviderMatchApprovalCriteriaVO;
import com.newco.marketplace.dto.vo.spn.SPNApprovalCriteriaVO;

public class ApprovalCriteriaHelper {
	
	private static final String SEPERATING_CHARACTER = "@";
	/**
	 * 
	 * @param <T>
	 * @param approvalCriterias
	 * @return ApprovalModel
	 * @throws Exception
	 */
	public static  ApprovalModel getApprovalModelFromCriteria( List<SPNApprovalCriteriaVO> approvalCriterias) {
		ApprovalModel model = new ApprovalModel(); 
		for(SPNApprovalCriteriaVO criteria : approvalCriterias) {
			String decription  = criteria.getCriteriaDesc() != null ? criteria.getCriteriaDesc().toString() : null;
			switch ( CriteriaEnum.toCriteriaEnum(decription)) 
			{
			case ENUM_CRITERIA_MAIN_SERVICES 				: model.getSelectedMainServices().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_SKILLS						: model.getSelectedSkills().add(new Integer(criteria.getCriteriaValue())); break;
			case ENUM_CRITERIA_CATEGORY						: model.getSelectedSubServices1().add(new Integer(criteria.getCriteriaValue())); break;
			case ENUM_CRITERIA_SUB_CATEGORY 				: model.getSelectedSubServices2().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_MINIMUM_RATING               : if(criteria.getCriteriaValue() != null)model.setSelectedMinimumRating(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_MINIMUM_RATING_NOTRATED      : model.setIsNotRated(new Boolean(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_LANGUAGE                     : model.getSelectedLanguages().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_MINIMUM_SO_COMPLETED         : model.setMinimumCompletedServiceOrders(criteria.getCriteriaValue()); break;
			case ENUM_CRITERIA_AUTO_LIABILITY_AMT           : model.setVehicleLiabilityAmt(criteria.getCriteriaValue());
															  model.setVehicleLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_AUTO_LIABILITY_VERIFIED      : model.setVehicleLiabilityVerified(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getCriteriaValue())));
															  model.setVehicleLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_WC_LIABILITY_VERIFIED        : model.setWorkersCompensationVerified(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getCriteriaValue())));
															  model.setWorkersCompensationSelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_COMMERCIAL_LIABILITY_AMT     : model.setCommercialGeneralLiabilityAmt(criteria.getCriteriaValue()); 
															  model.setCommercialGeneralLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_COMMERCIAL_LIABILITY_VERIFIED: model.setCommercialGeneralLiabilityVerified(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getCriteriaValue())));
			 												  model.setCommercialGeneralLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_COMPANY_CRED                 : model.getSelectedVendorCredTypes().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_COMPANY_CRED_CATEGORY        : model.getSelectedVendorCredCategories().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_COMPANY_CRED_VERIFIED        : break; //TODO find out what this is 
			case ENUM_CRITERIA_SP_CRED                      : model.getSelectedResCredTypes().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_SP_CRED_CATEGORY             : model.getSelectedResCredCategories().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_SP_CRED_VERIFIED             : break; //TODO find out what this is
			case ENUM_CRITERIA_MEETING_REQUIRED             : model.setMeetingRequired(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getCriteriaValue()))); break;
			case ENUM_CRITERIA_MARKET                       : model.getSelectedMarkets().add(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_STATE                        : model.getSelectedStates().add(criteria.getCriteriaValue());break;
			case ENUM_CRITERIA_MARKET_ALL                   : model.setIsAllMarketsSelected(new Boolean(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_STATE_ALL                    : model.setIsAllStatesSelected(new Boolean(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_COMPANY_SIZE                 : model.setSelectedCompanySize(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_SALES_VOLUME                 : model.setSelectedSalesVolume(new Integer(criteria.getCriteriaValue()));break;
			case ENUM_CRITERIA_NONE							: break;
			default 										:break; 
			}
		}		
		return model;		
	}
	
	public static String getREGEXPforSkillsAndCategory(ApprovalModel approvalModel) {
		String result = null;
		SortedSet<Integer> list = new TreeSet<Integer>();
		list.addAll(approvalModel.getSelectedMainServices());
		list.addAll(approvalModel.getSelectedSubServices1());
		list.addAll(approvalModel.getSelectedSubServices2());		
		result = ApprovalCriteriaHelper.getREGEXPForMatchALL(list);		
		return StringUtils.isBlank(result) ? null : result;
	}
	/**
	 * 
	 * @param approvalModel
	 * @return String
	 */
	public static String getREGEXPforServiceTypes(ApprovalModel approvalModel) {
		String result = null;
		SortedSet<Integer> list = new TreeSet<Integer>();
		list.addAll(approvalModel.getSelectedSkills());			
		result = ApprovalCriteriaHelper.getREGEXPForMatchALL(list);		
		return StringUtils.isBlank(result) ? null : result;
	}
	
	/**
	 * This methos returns the reg expression for the List of Langauges
	 * @param approvalModel
	 * @return
	 */
	public static String getREGEXPforLanguages(ApprovalModel approvalModel) {
		String result = null;
		SortedSet<Integer> list = new TreeSet<Integer>();
		list.addAll(approvalModel.getSelectedLanguages());			
		result = ApprovalCriteriaHelper.getREGEXPForMatchALL(list);		
		return StringUtils.isBlank(result) ? null : result;
	}
	
	/**
	 * 
	 * @param approvalModel
	 * @return ProviderMatchApprovalCriteriaVO
	 */
	public static ProviderMatchApprovalCriteriaVO getCriteriaVOFromModel(ApprovalModel approvalModel) {
		ProviderMatchApprovalCriteriaVO vo = new ProviderMatchApprovalCriteriaVO();
		vo.setModel(approvalModel);
		vo.setSkillsREGEXP(getREGEXPforSkillsAndCategory(approvalModel));
		vo.setServiceTypeREGEXP(getREGEXPforServiceTypes(approvalModel));
		vo.setLanguagesREGEXP(getREGEXPforLanguages(approvalModel));
		BigDecimal cbd = StringUtils.isNotBlank(approvalModel.getCommercialGeneralLiabilityAmt()) ?  new BigDecimal(approvalModel.getCommercialGeneralLiabilityAmt()) : null;
		BigDecimal vbd = StringUtils.isNotBlank(approvalModel.getVehicleLiabilityAmt()) ?  new BigDecimal(approvalModel.getVehicleLiabilityAmt()) : null;
		vo.setCommercialGeneralLiabilityAmtBD(cbd);
		vo.setVehicleLiabilityAmtBD(vbd);
		return vo;
	}
	/**
	 * 
	 * @param list
	 * @return String
	 */
	public static String getREGEXPForMatchALL(SortedSet<Integer> list) {
		String result = "";
		//assume  the list is Sorted by now.. now build the expression
		int count = 1 ;
		int size = list.size();
		for (Integer val : list) {
			if(count == 1 ) {
				result = "("+SEPERATING_CHARACTER;				
			}
			result = result + "(" + val + ")";			
			if(count < size) {
				result = result + SEPERATING_CHARACTER+".*";
			}
			else {
				result = result + SEPERATING_CHARACTER+")";
			}			
			count ++;
		}
		return result;
	}			
}
