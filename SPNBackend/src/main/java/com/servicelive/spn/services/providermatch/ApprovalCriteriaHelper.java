package com.servicelive.spn.services.providermatch;

import static com.servicelive.spn.common.SPNBackendConstants.CRITERIA_VALUE_TRUE;
import static com.servicelive.spn.common.SPNBackendConstants.DOC_TYPE_ELECTRONIC_AGREEMENT;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.servicelive.domain.ApprovalCriteria;
import com.servicelive.domain.spn.detached.ApprovalModel;
import com.servicelive.domain.spn.detached.SPNCreateNetworkDisplayDocumentsVO;
import com.servicelive.spn.common.CriteriaEnum;
import com.servicelive.spn.common.detached.ProviderMatchApprovalCriteriaVO;

/**
 * @author hoza
 *
 */
public class ApprovalCriteriaHelper {

	private static final String SEPERATING_CHARACTER = "@";
	/**
	 * 
	 * @param <T>
	 * @param approvalCriterias
	 * @return ApprovalModel
	 * @throws Exception
	 */
	public static  <T extends ApprovalCriteria>  ApprovalModel getApprovalModelFromCriteria( List<T> approvalCriterias) throws Exception {
		ApprovalModel model = new ApprovalModel();

		try{
		
		for(T criteria : approvalCriterias) {
			String decription  = criteria.getCriteriaId() != null ? criteria.getCriteriaId().getDescription().toString().trim() : null;
			//System.out.println("Description value while displaying"+decription);
			
			switch ( CriteriaEnum.toCriteriaEnum(decription)) 
			{
			case ENUM_CRITERIA_MAIN_SERVICES 				:model.getSelectedMainServices().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_SKILLS						:model.getSelectedSkills().add(new Integer(criteria.getValue())); break;
			case ENUM_CRITERIA_CATEGORY						: model.getSelectedSubServices1().add(new Integer(criteria.getValue())); break;
			case ENUM_CRITERIA_SUB_CATEGORY 				: model.getSelectedSubServices2().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_MINIMUM_RATING               : if(criteria.getValue() != null)model.setSelectedMinimumRating(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_MINIMUM_RATING_NOTRATED      : model.setIsNotRated(Boolean.valueOf(criteria.getValue()));break;
			case ENUM_CRITERIA_LANGUAGE                     : model.getSelectedLanguages().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_MINIMUM_SO_COMPLETED         : model.setMinimumCompletedServiceOrders(criteria.getValue()); break;
			case ENUM_CRITERIA_AUTO_LIABILITY_AMT           : model.setVehicleLiabilityAmt(criteria.getValue());
															  model.setVehicleLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_AUTO_LIABILITY_VERIFIED      : model.setVehicleLiabilityVerified(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getValue())));
															  model.setVehicleLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_WC_LIABILITY_VERIFIED        : model.setWorkersCompensationVerified(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getValue())));
															  model.setWorkersCompensationSelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_COMMERCIAL_LIABILITY_AMT     : model.setCommercialGeneralLiabilityAmt(criteria.getValue()); 
															  model.setCommercialGeneralLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_COMMERCIAL_LIABILITY_VERIFIED: model.setCommercialGeneralLiabilityVerified(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getValue())));
			 												  model.setCommercialGeneralLiabilitySelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_COMPANY_CRED                 : model.getSelectedVendorCredTypes().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_COMPANY_CRED_CATEGORY        : model.getSelectedVendorCredCategories().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_COMPANY_CRED_VERIFIED        : break; //What is this?
			case ENUM_CRITERIA_SP_CRED                      : model.getSelectedResCredTypes().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_SP_CRED_CATEGORY             : model.getSelectedResCredCategories().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_SP_CRED_VERIFIED             : break; //What is this?
			case ENUM_CRITERIA_MEETING_REQUIRED             : model.setMeetingRequired(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getValue()))); break;
			case ENUM_CRITERIA_MARKET                       : model.getSelectedMarkets().add(new Integer(criteria.getValue()));break;
			case ENUM_CRITERIA_STATE                        : model.getSelectedStates().add(criteria.getValue());break;
			case ENUM_CRITERIA_MARKET_ALL                   : model.setIsAllMarketsSelected(Boolean.valueOf(criteria.getValue()));break;
			case ENUM_CRITERIA_STATE_ALL                    : model.setIsAllStatesSelected(Boolean.valueOf(criteria.getValue()));break;
			case ENUM_CRITERIA_COMPANY_SIZE                 : model.setSelectedCompanySize(Integer.valueOf(criteria.getValue()));break;
			case ENUM_CRITERIA_SALES_VOLUME                 : model.setSelectedSalesVolume(Integer.valueOf(criteria.getValue()));break;
			case ENUM_CRITERIA_WC_LIABILITY_SELECTED		: model.setWorkersCompensationSelected(Boolean.TRUE);break;
			case ENUM_CRITERIA_NONE							: break;
			//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
			case ENUM_CRITERIA_PRIMARY_INDUSTRY				: model.getSelectedPrimaryIndustry().add(new Integer(criteria.getValue())); break;
			/* R11.0 SL-19387*/	
			case ENUM_CRITERIA_BACKGROUND_CHECK            	:model.setRecertificationInd(Boolean.valueOf(CRITERIA_VALUE_TRUE.equalsIgnoreCase(criteria.getValue().trim())));break;
			default 										: break; 
			}
		}
		}
		catch(Exception e){
			//System.out.println("Error in getApprovalModelFromCriteria: "+e.getMessage());
			//e.printStackTrace();
			}

		return model;
	}

	//----------------------------------------------------------------------------------------------------------
	//Do not modify any here below without talking with Himanshu or Steve VL
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
	// our regexp needed to be in this fashion
	// "(@(612)@.*(800)@.*(1300)@)"

	/**
	 * 
	 * @param approvalModel
	 * @return ProviderMatchApprovalCriteriaVO
	 */
	@SuppressWarnings("unchecked")
	public static ProviderMatchApprovalCriteriaVO getCriteriaVOFromModel(ApprovalModel approvalModel) {
		ProviderMatchApprovalCriteriaVO vo = new ProviderMatchApprovalCriteriaVO(approvalModel);
		//vo.setSkillsREGEXP(getGenericRegex(approvalModel.getSelectedMainServices()));
		//Stupid huh.. why cant i use the model.getMailServicesDirectly .. cuz in Model object is instanceiated with defualt Arraylist I need null if not exist
		//another reason I started using the SortedSet to remove any posisbility of the dups.. but IBTAIS is not supporting it at this moment.
		vo.setSkillsList(approvalModel.getSelectedMainServices()); 
		//vo.setSkillsCategoryREGEXP(getGenericRegex(approvalModel.getSelectedSubServices1(), approvalModel.getSelectedSubServices2()));
		vo.setSkillsCategoryList(approvalModel.getSelectedSubServices1());
		vo.getSkillsCategoryList().addAll(approvalModel.getSelectedSubServices2());
		//vo.setServiceTypeREGEXP(getGenericRegex(approvalModel.getSelectedSkills()));
		/*SortedSet <Integer>serviceTypeSet  = new TreeSet<Integer>();
		serviceTypeSet.addAll(approvalModel.getSelectedSkills());*/
		vo.setServiceTypeList(approvalModel.getSelectedSkills());
		
		vo.setLanguagesREGEXP(getGenericRegex(approvalModel.getSelectedLanguages()));
		vo.setLanguageIds(approvalModel.getSelectedLanguages());
		
		vo.setProviderFirmCredentialsREGEXP(getGenericRegex(approvalModel.getSelectedVendorCredTypes()));
		vo.setProviderFirmCredentialIds(approvalModel.getSelectedVendorCredTypes());
		
		vo.setProviderFirmCredCategoryREGEXP(getGenericRegex(approvalModel.getSelectedVendorCredCategories()));
		vo.setProviderFirmCredCategoryIds(approvalModel.getSelectedVendorCredCategories());
		
		vo.setServiceProviderCredentialsREGEXP(getGenericRegex(approvalModel.getSelectedResCredTypes()));
		vo.setServiceProviderCredentialIds(approvalModel.getSelectedResCredTypes());
		
		vo.setServiceProviderCategoryREGEXP(getGenericRegex(approvalModel.getSelectedResCredCategories()));
		vo.setServiceProviderCategoryIds(approvalModel.getSelectedResCredCategories());

		vo.setDocumentREGEXP(getRegexForDocument(approvalModel.getSpnDocumentList(), true));
		vo.setElectronicDocumentREGEXP(getRegexForDocument(approvalModel.getSpnDocumentList(), false));
		vo.setCommercialGeneralLiabilityAmtBD(createBigDecimal(approvalModel.getCommercialGeneralLiabilityAmt()));
		vo.setVehicleLiabilityAmtBD(createBigDecimal(approvalModel.getVehicleLiabilityAmt()));
		//R10.3 SL-19812 Introduce Primary Industry criteria for Campaign
		//get the selectedPrimaryIndustry from model and set it to vo 
		vo.setPrimaryIndustryList((approvalModel.getSelectedPrimaryIndustry())); 
		
		vo.setRecertification(approvalModel.getRecertificationInd());
		
		return vo;
	}

	private static BigDecimal createBigDecimal(String pBigDecimalString) {
		String bigDecimalString = stripCommaForBigDecimal(pBigDecimalString);
		BigDecimal result = StringUtils.isNotBlank(bigDecimalString) ?  new BigDecimal(bigDecimalString) : null;
		return result;
	}

	@SuppressWarnings("unchecked")
	private static String getRegexForDocument(List<SPNCreateNetworkDisplayDocumentsVO> docs, boolean isElectronicDoc) {
		if(docs == null) {
			return null;
		}
		List<Integer> list = new ArrayList<Integer>();
		for(SPNCreateNetworkDisplayDocumentsVO spnDocument:docs) {
			if(isElectronicDoc == isElectronicDoc(spnDocument.getTypeId())) {
				list.add(spnDocument.getDocumentId());
			}
		}
		return getGenericRegex(list);
	}

	private static boolean isElectronicDoc(Integer typeId) {
		if(typeId.intValue() == DOC_TYPE_ELECTRONIC_AGREEMENT.intValue()) {
			return true;
		}
		return false;
	}

	private static String stripCommaForBigDecimal(String pAmt) {
		String amt = pAmt;
		if (amt != null) {
			amt = amt.replaceAll(",", "");
		}
		return amt;
	}

	private static String getGenericRegex(List<Integer>... pListArr) {
		SortedSet<Integer> list = new TreeSet<Integer>();
		for(List<Integer>pList:pListArr) {
			list.addAll(pList);
		}
		String result = ApprovalCriteriaHelper.getRegexForMatchAll(list);
		return StringUtils.isBlank(result) ? null : result;
	}

	public static void main(String...args) {
		List<Integer> bob = new ArrayList<Integer>();
		bob.add(1);
		bob.add(2);
		bob.add(3);
		
		String result = getGenericRegex(bob);
		System.out.println(result);
	}

	/**
	 * 
	 * @param list
	 * @return String
	 */
	private static String getRegexForMatchAll(SortedSet<Integer> list) {
		StringBuilder result = new StringBuilder();
		//the set is Sorted...now build the expression
		int count = 1;
		int size = list.size();
		for (Integer val:list) {
			if(count == 1) {
				result.append("("+SEPERATING_CHARACTER);
			}
			result.append("(");
			result.append(val);
			result.append(")");

			if(count < size) {
				result.append(SEPERATING_CHARACTER+".*");
			} else {
				result.append(SEPERATING_CHARACTER+")");
			}

			count++;
		}
		return result.toString();
	}
}
