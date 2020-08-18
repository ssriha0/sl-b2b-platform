package com.newco.marketplace.webservices.validation.so.draft;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.dto.vo.serviceorder.Part;
import com.newco.marketplace.dto.vo.serviceorder.PhoneVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderTask;
import com.newco.marketplace.dto.vo.serviceorder.SoLocation;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.webservices.base.ABaseValidator;
import com.newco.marketplace.webservices.response.WSErrorInfo;
import com.newco.marketplace.webservices.util.WSConstants;
import com.newco.marketplace.webservices.util.DraftValidatorUtil;
import com.newco.marketplace.webservices.util.WSConstants.WSErrors;

public class CreateDraftValidator extends ABaseValidator implements OrderConstants {

	private static final long serialVersionUID = 6349153530814843053L;

	private static final Logger logger = Logger.getLogger(CreateDraftValidator.class);

	public Map<String, List<WSErrorInfo>> validateCreateDraft(ServiceOrder so) {
		Map<String, List<WSErrorInfo>> map = new HashMap<String, List<WSErrorInfo>>();
		List<WSErrorInfo> err = new ArrayList<WSErrorInfo>();
		List<WSErrorInfo> war = new ArrayList<WSErrorInfo>();

		validateServiceContact(so, err, war);
		validateTask(so, err, war);
		validateMiscellaneous(so, err, war);
		validateBuyer(so, err, war);
		validateSchedule(so, err, war);
		validatLocation(so, err, war);
		validatePartInfo(so, err, war);

		DraftValidatorUtil.addWarningsAsNotes(so, war);

		map.put(OrderConstants.SOW_TAB_ERROR, err);
		map.put(OrderConstants.SOW_TAB_WARNING, war);

		return map;
	}

	private void validateServiceContact(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {

		if (so.getServiceContact() == null) {

			WSErrorInfo warning1 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_First_Name_Validation,
					WSConstants.WSWarnings.Messages.CR_DR_First_Name_Validation);
			WSErrorInfo warning2 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Last_Name_Validation,
					WSConstants.WSWarnings.Messages.CR_DR_Last_Name_Validation);

			WSErrorInfo warning5 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Phone_Validation_Msg_Req,
					WSConstants.WSWarnings.Messages.CR_DR_Phone_Validation_Msg_Req);
			war.add(warning1);
			war.add(warning2);
			war.add(warning5);
		} else if (so.getServiceContact() != null) {
			if (so.getServiceContact().getBusinessName() != null
					&& so.getServiceContact().getBusinessName().length() > 100) {
				WSErrorInfo error = getErrorInfo(
						WSConstants.WSErrors.Codes.CR_DR_Business_Name_Validation,
						WSConstants.WSErrors.Messages.CR_DR_Business_Name_Validation);
				err.add(error);
			}

			if (so.getServiceContact().getFirstName() == null
					|| so.getServiceContact().getFirstName().length() < 1) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_First_Name_Validation,
						WSConstants.WSWarnings.Messages.CR_DR_First_Name_Validation);
				war.add(warning);
			}
			if (so.getServiceContact().getFirstName() != null
					&& so.getServiceContact().getFirstName().trim().length() > 50) {

				WSErrorInfo error = getErrorInfo(
						WSConstants.WSErrors.Codes.CR_DR_First_Name_Length_Validation,
						WSConstants.WSErrors.Messages.CR_DR_First_Name_Length_Validation);
				err.add(error);
			}

			if (so.getServiceContact().getLastName() == null
					|| so.getServiceContact().getLastName().length() < 1) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_Last_Name_Validation,
						WSConstants.WSWarnings.Messages.CR_DR_Last_Name_Validation);
				war.add(warning);
			}
			if (so.getServiceContact().getLastName() != null
					&& so.getServiceContact().getLastName().trim().length() > 50) {
				WSErrorInfo error = getErrorInfo(
						WSConstants.WSErrors.Codes.CR_DR_Last_Name_Lenght_Validation,
						WSConstants.WSErrors.Messages.CR_DR_Last_Name_Lenght_Validation);
				err.add(error);
			}

			if (so.getServiceContact().getEmail() != null
					&& so.getServiceContact().getEmail().trim().length() > 255) {
				WSErrorInfo error = getErrorInfo(
						WSConstants.WSErrors.Codes.CR_DR_Email_Validation_Msg,
						WSConstants.WSErrors.Messages.CR_DR_Email_Validation_Msg);
				err.add(error);
			} else if (so.getServiceContact().getEmail() != null
					&& so.getServiceContact().getEmail().length() > 1) {
				Pattern p = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[_A-Za-z0-9-]+)");
				Matcher m = p.matcher(so.getServiceContact().getEmail());
				boolean valResult = m.matches();
				if (valResult == false) {
					WSErrorInfo error = getErrorInfo(
							WSConstants.WSErrors.Codes.CR_DR_Email_Pattern_Validation_Msg,
							WSConstants.WSErrors.Messages.CR_DR_Email_Pattern_Validation_Msg);
					err.add(error);
				}
			}

			/*
			 * Validating the Phones in Service Location Contact DTO
			 */
			if (so.getServiceContact().getPhones() != null) {
				List<PhoneVO> phones = so.getServiceContact().getPhones();

				for (int i = 0; i < phones.size(); i++) {
					PhoneVO iPhone = phones.get(i);
					if (iPhone.getPhoneNo() != null
							&& iPhone.getPhoneNo().trim().length() > 0
							&& !iPhone.getPhoneNo().trim().equalsIgnoreCase(
									"null")) {
						boolean valResult = false;
						String numPattern = "(\\d{10})";
						valResult = iPhone.getPhoneNo().matches(numPattern);
						// Check to see if the phone is empty. I.E. the user
						// never even entered phone
						if (i == 0) {
							if (valResult == false) {
								WSErrorInfo error = getErrorInfo(
										WSConstants.WSErrors.Codes.CR_DR_Phone_Missing_Number_Validation_Msg,
										WSConstants.WSErrors.Messages.CR_DR_Phone_Missing_Number_Validation_Msg);
								err.add(error);
							}
							if (iPhone.getClassId().equals(new Integer(-1))) {
								WSErrorInfo error = getErrorInfo(
										WSConstants.WSErrors.Codes.CR_DR_Phone_Type_Validation,
										WSConstants.WSErrors.Messages.CR_DR_Phone_Type_Validation);
								err.add(error);
							}
						}
						if (i == 1) {
							if (valResult == false) {
								WSErrorInfo error = getErrorInfo(
										WSConstants.WSErrors.Codes.CR_DR_Alternate_Phone_Missing_Number_Validation_Msg,
										WSConstants.WSErrors.Messages.CR_DR_Alternate_Phone_Missing_Number_Validation_Msg);
								err.add(error);
							}
							if (iPhone.getClassId().equals(new Integer(-1))) {
								WSErrorInfo error = getErrorInfo(
										WSConstants.WSErrors.Codes.CR_DR_Alternate_Phone_Type_Validation,
										WSConstants.WSErrors.Messages.CR_DR_Alternate_Phone_Type_Validation);
								err.add(error);
							}
						}
						if (valResult == false && i == 2) {
							WSErrorInfo error = getErrorInfo(
									WSConstants.WSErrors.Codes.CR_DR_Fax_Missing_Number_Validation_Msg,
									WSConstants.WSErrors.Messages.CR_DR_Fax_Missing_Number_Validation_Msg);
							err.add(error);
						}
					}
				}
				if (so.getServiceContact() != null) {
					if (so.getServiceContact().getPhones() != null
							&& so.getServiceContact().getPhones().size() > 0) {
						if (so.getServiceContact().getPhones().get(0) != null
								&& so.getServiceContact().getPhones().get(0)
										.getPhoneNo() == null
								|| so.getServiceContact().getPhones().get(0)
										.getPhoneNo().equalsIgnoreCase("")
								&& (so.getServiceContact().getPhones().get(1) != null
										&& so.getServiceContact().getPhones()
												.get(1).getPhoneNo() != null
										&& so.getServiceContact().getPhones()
												.get(1).getPhoneNo()
												.equalsIgnoreCase("") || so
										.getServiceContact().getPhones().get(1)
										.getPhoneNo() == null)) {
							WSErrorInfo error = getErrorInfo(
									WSConstants.WSErrors.Codes.CR_DR_Please_enter_a_contact_phone_no,
									WSConstants.WSErrors.Messages.CR_DR_Please_enter_a_contact_phone_no);
							err.add(error);
						}
					}
				}
			}
		}
	}

	public void validatLocation(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {
		if (so.getServiceLocation() == null) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_Service_Contact,
					WSConstants.WSErrors.Messages.CR_DR_Service_Contact);
			WSErrorInfo warning1 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Street_Name_Validation,
					WSConstants.WSWarnings.Messages.CR_DR_Street_Name_Validation);
			WSErrorInfo warning2 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_City_Validation,
					WSConstants.WSWarnings.Messages.CR_DR_City_Validation);
			WSErrorInfo warning = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_State_Validation_Msg,
					WSConstants.WSErrors.Messages.CR_DR_State_Validation_Msg);
			war.add(warning);
			war.add(warning1);
			war.add(warning2);
			err.add(error);
			return;
		}

		// New Business Rules for availability of street2 but not street1
		SoLocation soLocation = so.getServiceLocation();
		if (StringUtils.isBlank(soLocation.getStreet1())
				|| WSConstants.DASH_EMPTY_STREET_ADDRESS.equals(soLocation
						.getStreet1().trim())) {
			if (StringUtils.isBlank(soLocation.getStreet2())
					|| WSConstants.DASH_EMPTY_STREET_ADDRESS.equals(soLocation
							.getStreet2().trim())) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_Street_Name_Validation,
						WSConstants.WSWarnings.Messages.CR_DR_Street_Name_Validation);
				war.add(warning);
			} else {
				soLocation.setStreet1(soLocation.getStreet2());
				soLocation.setStreet2(null);
			}
		}

		if (StringUtils.isNotBlank(soLocation.getStreet1())
				&& soLocation.getStreet1().length() > WSErrors.FieldLength.STREET1) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_Street_Length_Validation_Msg,
					WSConstants.WSErrors.Messages.CR_DR_Street_Length_Validation_Msg);
			err.add(error);
		}

		if (StringUtils.isNotBlank(soLocation.getStreet2())
				&& soLocation.getStreet2().length() > WSErrors.FieldLength.STREET2) {
			WSErrorInfo warning = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Street2_Length_Validation_Msg,
					WSConstants.WSWarnings.Messages.CR_DR_Street2_Length_Validation_Msg);
			war.add(warning);
		}

		if (StringUtils.isBlank(soLocation.getCity())
				|| soLocation.getCity().length() < 1) {
			WSErrorInfo warning = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_City_Validation,
					WSConstants.WSWarnings.Messages.CR_DR_City_Validation);
			war.add(warning);
		}

		if (StringUtils.isNotBlank(soLocation.getCity())
				&& soLocation.getCity().length() > WSErrors.FieldLength.CITY) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_City_Length_Validation,
					WSConstants.WSErrors.Messages.CR_DR_City_Length_Validation);
			err.add(error);
		}

		if (StringUtils.isNotBlank(soLocation.getState())
				&& soLocation.getState().length() < 1
			 /* && "-1".equals(soLocation.getStateCd()) */) {
			WSErrorInfo warning = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_State_Validation_Msg,
					WSConstants.WSErrors.Messages.CR_DR_State_Validation_Msg);
			war.add(warning);
		}

		if (StringUtils.isNotBlank(soLocation.getZip())
				&& soLocation.getZip().trim().length() > 0) {
			boolean matchSuccess = false;
			String numPattern = "(\\d{5})";
			matchSuccess = soLocation.getZip().matches(numPattern);
			if (!matchSuccess) {
				WSErrorInfo error = getErrorInfo(
						WSConstants.WSErrors.Codes.CR_DR_Zip_Validation,
						WSConstants.WSErrors.Messages.CR_DR_Zip_Validation);
				err.add(error);
			}
		}
	}

	private void validateTask(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {
		if (so.getTasks() == null) {
			WSErrorInfo warning6 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Task_Name_Validation_Msg,
					WSConstants.WSWarnings.Messages.CR_DR_Task_Name_Validation_Msg);
			WSErrorInfo warning7 = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Skill_Validation_Msg,
					WSConstants.WSWarnings.Messages.CR_DR_Skill_Validation_Msg);
			war.add(warning6);
			war.add(warning7);
		} else if (so.getTasks() != null) {
			// Check if there are no tasks provided by User and show warning
			// if so.
			if (so.getTasks().size() == 0) { // No task provided. Show
				// the warning.
				WSErrorInfo error = getErrorInfo(
						WSConstants.WSErrors.Codes.CR_DR_Task_Not_Provided,
						WSConstants.WSErrors.Messages.CR_DR_Task_Not_Provided);
				err.add(error);
			} else { // Process the provided tasks
				for (int i = 0; i < so.getTasks().size(); i++) {
					ServiceOrderTask sotd = so.getTasks().get(i);
					if (sotd != null
							&& sotd.getTaskComments() != null
							&& sotd.getTaskComments().trim().replaceAll("\\n",
									"").replaceAll("\\t", "").length() > OrderConstants.SUMMARY_TAB_SCOPE_OF_WORK_TASK_COMMENTS_LENGTH) {
						WSErrorInfo error = getErrorInfo(
								WSConstants.WSErrors.Codes.CR_DR_Comment_Validation,
								WSConstants.WSErrors.Messages.CR_DR_Comment_Validation);
						err.add(error);
					}
					if (sotd != null && sotd.getTaskName() != null
							&& sotd.getTaskName().trim().length() == 0) {
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSWarnings.Codes.CR_DR_Task_Name_Validation_Msg,
								WSConstants.WSWarnings.Messages.CR_DR_Task_Name_Validation_Msg);
						war.add(warning);
					}
					if (sotd != null && sotd.getTaskName() != null
							&& sotd.getTaskName().length() > 255) {
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSWarnings.Codes.CR_DR_Task_Name_Validation_Length_Msg,
								WSConstants.WSWarnings.Messages.CR_DR_Task_Name_Validation_Length_Msg);
						war.add(warning);
					}
					if (sotd != null && sotd.getSkillNodeId() != null
							&& sotd.getSkillNodeId().intValue() > 0) {
						if (sotd.getSubCategoryName() != null
								&& sotd.getSubCategoryName().length() > 0) {
							WSErrorInfo error = getErrorInfo(
									WSConstants.WSErrors.Codes.CR_DR_Sub_Category_Validation_Msg,
									WSConstants.WSErrors.Messages.CR_DR_Sub_Category_Validation_Msg);
							err.add(error);
						}
					} else if (sotd != null && sotd.getSkillNodeId() != null
							&& sotd.getSkillNodeId().intValue() == 0) {
						// mapping required
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSWarnings.Codes.CR_DR_Skill_Validation_Msg,
								WSConstants.WSWarnings.Messages.CR_DR_Skill_Validation_Msg);
						war.add(warning);
					}

					if (sotd != null && sotd.getServiceTypeId() == null
							|| sotd.getServiceTypeId().intValue() == -1) {

						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSWarnings.Codes.CR_DR_Skill_Validation_Msg,
								WSConstants.WSWarnings.Messages.CR_DR_Skill_Validation_Msg);
						war.add(warning);
					}
				}
			}
		}
	}

	private void validateBuyer(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {
		if (so.getBuyer() == null) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_BUYERID_MISSING,
					WSConstants.WSErrors.Messages.CR_DR_BUYERID_MISSING);
			err.add(error);
		} else if (so.getBuyer() != null && so.getBuyer().getBuyerId() == null) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_BUYERID_MISSING,
					WSConstants.WSErrors.Messages.CR_DR_BUYERID_MISSING);
			err.add(error);
		}

		if (so.getBuyer() != null && so.getBuyer().getBuyerId() != null
				&& so.getBuyer().getBuyerId().toString().trim().length() < 1) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_BUYERID_MISSING,
					WSConstants.WSErrors.Messages.CR_DR_BUYERID_MISSING);
			err.add(error);
		}
	}

	private void validateMiscellaneous(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {

		if (so.getServiceContact() != null
				&& so.getServiceContact().getPhones() == null) {
			WSErrorInfo warning = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_Phone_Validation_Msg_Req,
					WSConstants.WSWarnings.Messages.CR_DR_Phone_Validation_Msg_Req);
			war.add(warning);
		}

		/*
		 * Validating General Info
		 */
		if (so.getRetailPrice() == null) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_RETAIL_PRICE_MISSING,
					WSConstants.WSErrors.Messages.CR_DR_RETAIL_PRICE_MISSING);
			err.add(error);
		}

		if (so.getSowTitle() == null || so.getSowTitle().trim().length() == 0) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_Title_Validation,
					WSConstants.WSErrors.Messages.CR_DR_Title_Validation);
			err.add(error);
		} else if (so.getSowTitle() != null
				&& so.getSowTitle().trim().length() > 255) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_Title_Length_Validation,
					WSConstants.WSErrors.Messages.CR_DR_Title_Length_Validation);
			err.add(error);
		}

		if (so.getProviderInstructions() != null
				&& so.getProviderInstructions().trim().replaceAll("\\n", "")
						.replaceAll("\\t", "").length() > OrderConstants.SUMMARY_TAB_GENERAL_INFO_SPECIAL_INSTRUCTION_LENGTH) {
			WSErrorInfo error = getErrorInfo(
					WSConstants.WSErrors.Codes.CR_DR_Special_Instruction_Validation_Msg,
					WSConstants.WSErrors.Messages.CR_DR_Special_Instruction_Validation_Msg);
			err.add(error);
		}
	}

	/**
	 * @param so
	 * @param err
	 * @param war
	 */
	private void validateSchedule(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {
		// if user dosnt enter any date and time
		if (so.getServiceDate1() == null && so.getServiceTimeStart() == null
				&& so.getServiceDate2() == null
				&& so.getServiceTimeEnd() == null) {
			WSErrorInfo warning = getErrorInfo(
					WSConstants.WSWarnings.Codes.CR_DR_SERVICE_DATE_VALIDATION,
					WSConstants.WSWarnings.Messages.CR_DR_SERVICE_DATE_VALIDATION);
			war.add(warning);
		} else {
			Date startDateGMT = new Date();
			Date endDateGMT = new Date();
			// if user dosnt enters end service date
			if (so.getServiceDate1() != null
					&& so.getServiceTimeStart() != null) {
				Date startDate = new Date(so.getServiceDate1().getTime());

				try {
					startDateGMT = TimeUtils.combineDateTime(TimeUtils
							.getServiceDateInGMT(startDate), so
							.getServiceTimeStart());
				} catch (ParseException px) {
					WSErrorInfo error = getErrorInfo(
							WSConstants.WSErrors.Codes.CR_DR_InvalidFormatDate_Validation,
							WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation);
					err.add(error);
				}
				if (so.getServiceDate2() == null
						&& so.getServiceTimeEnd() != null) {
					try {
						endDateGMT = TimeUtils.combineDateTime(TimeUtils
								.getServiceDateInGMT(startDate), so
								.getServiceTimeEnd());
					} catch (ParseException e) {
						WSErrorInfo error = getErrorInfo(
								WSConstants.WSErrors.Codes.CR_DR_InvalidFormatDate_Validation,
								WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation);
						err.add(error);
						e.printStackTrace();
					}
					if (endDateGMT.before(startDateGMT)) {
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSErrors.Codes.CR_DR_Service_Date2,
								WSConstants.WSErrors.Messages.CR_DR_InvalidDate_Validation);
						war.add(warning);
					}

					// endDateGMT =
					// TimeUtils.combineDateAndTime(TimeUtils.getServiceDateInGMT(startDate),
					// so.getServiceTimeEnd());
				}/*
					 * else{ endDateGMT = startDateGMT; }
					 */
			}

			/*
			 * if(so.getServiceDate2() != null && so.getServiceTimeEnd() !=
			 * null){ Date endDate = new Date(so.getServiceDate2().getTime());
			 * try{ endDateGMT =
			 * TimeUtils.combineDateTime(TimeUtils.getServiceDateInGMT(endDate),
			 * so.getServiceTimeEnd()); }catch(ParseException px){ WSErrorInfo
			 * error = getErrorInfo(
			 * WSConstants.WSErrors.Codes.CR_DR_InvalidFormatDate_Validation,
			 * WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation);
			 * err.add(error); } }
			 */
			// if user enters only end servicedate and end service time
			if (so.getServiceDate1() == null
					&& so.getServiceTimeStart() == null
					&& so.getServiceDate2() != null
					&& so.getServiceTimeEnd() != null) {
				Date endDate = new Date(so.getServiceDate2().getTime());
				try {
					startDateGMT = TimeUtils.combineDateTime(TimeUtils
							.getServiceDateInGMT(endDate), so
							.getServiceTimeEnd());
				} catch (ParseException ex) {
					WSErrorInfo error = getErrorInfo(
							WSConstants.WSErrors.Codes.CR_DR_InvalidFormatDate_Validation,
							WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation);
					err.add(error);
				}
				try {
					if (startDateGMT.before(TimeUtils.getCurrentGMT())) {
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSErrors.Codes.CR_DR_CurrentDate_Date1_Validation,
								WSConstants.WSErrors.Messages.CR_DR_CurrentDate_Date1_Validation);
						war.add(warning);
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}
				try {
					if (startDateGMT.compareTo(TimeUtils.getCurrentGMT()) == 0) {
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSErrors.Codes.CR_DR_CurrentDate_Date1_Validation,
								WSConstants.WSErrors.Messages.CR_DR_CurrentDate_Date1_Validation);
						war.add(warning);
					}
				} catch (ParseException e) {
					logger.error(e.getMessage(), e);
				}

			}
			// both the service dates equal to null and service time not equal
			// to null
			if (so.getServiceDate1() == null
					&& so.getServiceTimeStart() != null
					&& so.getServiceDate2() == null
					&& so.getServiceTimeEnd() != null) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_SERVICE_DATE_VALIDATION,
						WSConstants.WSWarnings.Messages.CR_DR_SERVICE_DATE_VALIDATION);
				war.add(warning);

			}
			// only start service date not equal to null
			if (so.getServiceDate1() != null
					&& so.getServiceTimeStart() == null
					&& so.getServiceDate2() == null
					&& so.getServiceTimeEnd() == null) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_SERVICE_DATE_VALIDATION,
						WSConstants.WSWarnings.Messages.CR_DR_SERVICE_DATE_VALIDATION);
				war.add(warning);

			}
			// only end service date not equal to null
			if (so.getServiceDate1() == null
					&& so.getServiceTimeStart() == null
					&& so.getServiceDate2() != null
					&& so.getServiceTimeEnd() == null) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_SERVICE_DATE_VALIDATION,
						WSConstants.WSWarnings.Messages.CR_DR_SERVICE_DATE_VALIDATION);
				war.add(warning);

			}

			// All date and time not equal to null
			if (so.getServiceDate1() != null
					&& so.getServiceTimeStart() != null
					&& so.getServiceDate2() != null
					&& so.getServiceTimeEnd() != null) {
				Date startDate = new Date(so.getServiceDate1().getTime());

				try {
					startDateGMT = TimeUtils.combineDateTime(TimeUtils
							.getServiceDateInGMT(startDate), so
							.getServiceTimeStart());
				} catch (ParseException px) {
					WSErrorInfo error = getErrorInfo(
							WSConstants.WSErrors.Codes.CR_DR_InvalidFormatDate_Validation,
							WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation);
					err.add(error);
				}
				Date endDate = new Date(so.getServiceDate2().getTime());
				try {
					endDateGMT = TimeUtils.combineDateTime(TimeUtils
							.getServiceDateInGMT(endDate), so
							.getServiceTimeEnd());
				} catch (ParseException ex) {
					WSErrorInfo error = getErrorInfo(
							WSConstants.WSErrors.Codes.CR_DR_InvalidFormatDate_Validation,
							WSConstants.WSErrors.Messages.CR_DR_InvalidFormatDate_Validation);
					err.add(error);
				}
				try {
					if (startDateGMT.before(TimeUtils.getCurrentGMT())) {
						WSErrorInfo warning = getErrorInfo(
								WSConstants.WSErrors.Codes.CR_DR_CurrentDate_Date1_Validation,
								WSConstants.WSErrors.Messages.CR_DR_CurrentDate_Date1_Validation);
						war.add(warning);
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (endDateGMT.before(startDateGMT)) {
					WSErrorInfo warning = getErrorInfo(
							WSConstants.WSErrors.Codes.CR_DR_Service_Date2,
							WSConstants.WSErrors.Messages.CR_DR_InvalidDate_Validation);
					war.add(warning);
				}
			}
			if (so.getServiceDate1() == null
					&& so.getServiceTimeStart() == null
					&& so.getServiceDate2() == null
					&& so.getServiceTimeEnd() != null) {
				WSErrorInfo warning = getErrorInfo(
						WSConstants.WSWarnings.Codes.CR_DR_SERVICE_DATE_VALIDATION,
						WSConstants.WSWarnings.Messages.CR_DR_SERVICE_DATE_VALIDATION);
				war.add(warning);
			}
		}
			
	}

	private void validatePartInfo(ServiceOrder so, List<WSErrorInfo> err, List<WSErrorInfo> war) {
		List<Part> partsList = so.getParts();
		WSErrorInfo w = null;
		for (Part p : partsList) {
			try {
				if (p.getManufacturer() == null
						|| p.getManufacturer().length() <= 0) {
					w = getErrorInfo(
							WSConstants.WSErrors.Codes.PART_INFO_MISSING_MANUFACTURER,
							WSConstants.WSErrors.Messages.PART_INFO_MISSING_MANUFACTURER);
					war.add(w);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			try {
				if (p.getModelNumber() == null
						|| p.getModelNumber().length() <= 0) {
					w = getErrorInfo(
							WSConstants.WSErrors.Codes.PART_INFO_MISSING_MODELNUMBER,
							WSConstants.WSErrors.Messages.PART_INFO_MISSING_MODELNUMBER);
					war.add(w);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private WSErrorInfo getErrorInfo(String code, String message) {
		WSErrorInfo error = new WSErrorInfo();
		error.setCode(code);
		error.setMessage(message);
		return error;
	}
}
