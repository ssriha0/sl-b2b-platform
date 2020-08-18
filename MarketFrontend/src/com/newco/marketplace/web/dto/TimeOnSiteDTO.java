package com.newco.marketplace.web.dto;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.utils.TimeUtils;
import com.newco.marketplace.utils.DateUtils;

public class TimeOnSiteDTO extends SOWBaseTabDTO {

	private static final long serialVersionUID = -793456726353257445L;
	private String arrivalDepartureTimeHourString;
	private String arrivalDepartureTimeMinutesString;
	private Integer arrivalTimeHour;
	private Integer arrivalTimeMinutes;
	private String arrivalDepartureTimeAmPm;
	private String arrivalTime;
	private String departureTimeHourString;
	private String departureTimeMinutesString;
	private Integer departureTimeHour;
	private Integer departureTimeMinutes;
	private String departureTimeAmPm;
	private String departureTime;
	private String arrivalDepartureDate;
	private String departureDate;
	private Long visitId;
	private ArrayList<TimeOnSiteResultRowDTO> results;
	private boolean addEditEntryPanel;
	private boolean addMode;
	private Date arrivalTimestamp;
	private Date departureTimestamp;
	private String soId;
	private Integer roleId;
	private String arrivalDeparture;
	private String timeOnSiteReason;
	private String arrivalDate;
	private Integer tripNumber;
	private String timeZone;

	private static final Logger logger = Logger.getLogger(TimeOnSiteDTO.class);

	@Override
	public void validate() {

		setErrors(new ArrayList<IError>());

		if (StringUtils.isBlank(getArrivalDepartureDate())) {
			if(("Arrival").equals(getArrivalDeparture())){
				addError(getTheResourceBundle().getString("Arrival_Date"),
						getTheResourceBundle().getString("Arrival_validation"),
						OrderConstants.SOD_TAB_ERROR);
			}else{
				addError(getTheResourceBundle().getString("Departure_Date"),
						getTheResourceBundle().getString("Departure_validation"),
						OrderConstants.SOD_TAB_ERROR);
			}

		}
		if (StringUtils.isNotBlank(getArrivalDepartureDate())) {
			if (DateUtils.isValidDate(getArrivalDepartureDate(), "MM/dd/yyyy")) {
				if (getArrivalDepartureTimeHourString() != null && !getArrivalDepartureTimeHourString().equals("[HH]")) {
					if (getArrivalDepartureTimeMinutesString() != null && !getArrivalDepartureTimeMinutesString().equals("[MM]")) {
						setArrivalTimeHour(Integer.parseInt(arrivalDepartureTimeHourString));
						setArrivalTimeMinutes(Integer.parseInt(arrivalDepartureTimeMinutesString));
						if (getArrivalDepartureTimeAmPm() != null && !getArrivalDepartureTimeAmPm().equals("--")) {
							if (getArrivalDepartureTimeAmPm().equals(OrderConstants.PM) && getArrivalTimeHour() < 12) {
								setArrivalTimeHour(12 + getArrivalTimeHour());
							}
							if (getArrivalDepartureTimeAmPm().equals(OrderConstants.AM) && getArrivalTimeHour() == 12) {
								setArrivalTimeHour(0);
							}
							setArrivalTime("." + getArrivalTimeHour() + "." + getArrivalTimeMinutes() + "." + "00");
							try {
								SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy.HH.mm.ss");
								DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm a ");
								Date arrDepdate = (Date) formatter.parse(getArrivalDepartureDate() + "" + getArrivalTime());
								if(("Arrival").equals(getArrivalDeparture())){
									if(arrDepdate.after(fetchDateInGivenTimeZone(timeZone))){
										addError(getTheResourceBundle().getString("Arrival_Time"), getTheResourceBundle().getString("Arrival_DateTime_is_before_Todays_DateTime"), OrderConstants.SOD_TAB_ERROR);
									}
									setArrivalTimestamp(arrDepdate);
								}else{
									if(arrDepdate.after(fetchDateInGivenTimeZone(timeZone))){
										addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_DateTime_is_after_Todays_DateTime"), OrderConstants.SOD_TAB_ERROR);
									}
									Date arrDate = (Date) sdf.parse(getArrivalDate());
									if (arrDate != null && arrDepdate != null) {
										if (!(arrDate.before(arrDepdate))) {
											addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_Date_greater_than_Arrival_Date"), OrderConstants.SOW_TAB_ERROR);
										}
										Date newArrivalDate = fetchDateInGivenTimeZone(timeZone);
										newArrivalDate = DateUtils.addDaysToDate(arrDate, 1);
										if(arrDepdate.compareTo(newArrivalDate)>0){
											addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_Date_greater_than_Arrival_Date_plus_One_day"), OrderConstants.SOW_TAB_ERROR);
										}
									}
									setArrivalTimestamp(arrDate);
									setDepartureTimestamp(arrDepdate);
								}				
								if (logger.isInfoEnabled())
								  logger.info("Arrival Date = " + getArrivalTimestamp());
							} catch (ParseException e) {
								e.getStackTrace();
							}
						} else {
							if(("Arrival").equals(getArrivalDeparture())){
								addError(getTheResourceBundle().getString("Arrival_Time"), getTheResourceBundle().getString("Arrival_time_am_or_pm_validation"), OrderConstants.SOD_TAB_ERROR);
							}else{
								addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_time_am_or_pm_validation"), OrderConstants.SOD_TAB_ERROR);
							}
						}
					} else {
						if(("Arrival").equals(getArrivalDeparture())){
							addError(getTheResourceBundle().getString("Arrival_Time"), getTheResourceBundle().getString("Arrival_time_minutes_validation"), OrderConstants.SOD_TAB_ERROR);
						}else{
							addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_time_minutes_validation"), OrderConstants.SOD_TAB_ERROR);
						}
					}
				} else {
					if(("Arrival").equals(getArrivalDeparture())){
						addError(getTheResourceBundle().getString("Arrival_Time"), getTheResourceBundle().getString("Arrival_time_hours_validation"), OrderConstants.SOD_TAB_ERROR);
					}else{
						addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_time_hours_validation"), OrderConstants.SOD_TAB_ERROR);
					}
				}
			} else {
				if(("Arrival").equals(getArrivalDeparture())){
					addError(getTheResourceBundle().getString("Arrival_Date"), getTheResourceBundle().getString("Arrival_date_validation"), OrderConstants.SOD_TAB_ERROR);
				}else{
					addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_date_validation"), OrderConstants.SOD_TAB_ERROR);
				}
			}
		}

		/*if (getDepartureTimeHourString() != null && !getDepartureTimeHourString().equals("[HH]")) {
			if (StringUtils.isBlank(getDepartureDate())) {
				addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_validation"), OrderConstants.SOD_TAB_ERROR);
			} else if (!DateUtils.isValidDate(getDepartureDate(), "MM/dd/yyyy")) {
				addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_date_validation"), OrderConstants.SOD_TAB_ERROR);
			} else {
				if (getDepartureTimeMinutesString() != null && !getDepartureTimeMinutesString().equals("[MM]")) {
					setDepartureTimeHour(Integer.parseInt(departureTimeHourString));
					setDepartureTimeMinutes(Integer.parseInt(departureTimeMinutesString));
					if (getDepartureTimeAmPm() != null && !getDepartureTimeAmPm().equals("--")) {
						if (getDepartureTimeAmPm().equals(OrderConstants.PM) && getDepartureTimeHour() < 12) {
							setDepartureTimeHour(12 + getDepartureTimeHour());
						}
						if (getDepartureTimeAmPm().equals(OrderConstants.AM) && getDepartureTimeHour() == 12) {
							setDepartureTimeHour(0);
						}
						setDepartureTime("." + getDepartureTimeHour() + "." + getDepartureTimeMinutes() + "." + "00");
						try {
							SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy.HH.mm.ss");
							Date depaturedate = (Date) formatter.parse(getDepartureDate() + "" + getDepartureTime());
							if(depaturedate.after(new Date())){
								addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_DateTime_is_after_Todays_DateTime"), OrderConstants.SOD_TAB_ERROR);
							}
							setDepartureTimestamp(depaturedate);
							if (logger.isInfoEnabled())
							  logger.info("Departure Date = " + getDepartureTimestamp());
						} catch (ParseException e) {
							e.getStackTrace();
						}
					} else {
						addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_time_am_or_pm_validation"), OrderConstants.SOD_TAB_ERROR);
					}
				} else {
					addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_time_minutes_validation"), OrderConstants.SOD_TAB_ERROR);
				}
			}
		} else {
			if (StringUtils.isNotBlank(getDepartureDate())) {
				if (!DateUtils.isValidDate(getDepartureDate(), "MM/dd/yyyy")) {
					addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_date_validation"), OrderConstants.SOD_TAB_ERROR);
				}
			}
			if ((StringUtils.isNotBlank(getDepartureDate())) && (!TimeUtils.convertToDojoDateFormat(getDepartureDate()).equalsIgnoreCase(OrderConstants.INVALID_DATE))) {
				addError(getTheResourceBundle().getString("Departure_Time"), getTheResourceBundle().getString("Departure_time_hours_validation"), OrderConstants.SOD_TAB_ERROR);
			}
		}*/
		
		
		
		/*if (getArrivalTimestamp() != null && getDepartureTimestamp() != null) {
			if (!(getArrivalTimestamp().before(getDepartureTimestamp()))) {
				addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_Date_greater_than_Arrival_Date"), OrderConstants.SOW_TAB_ERROR);
			}
			Date newArrivalDate = new Date();
			newArrivalDate = DateUtils.addDaysToDate(getArrivalTimestamp(), 1);
			if(getDepartureTimestamp().compareTo(newArrivalDate)>0){
				addError(getTheResourceBundle().getString("Departure_Date"), getTheResourceBundle().getString("Departure_Date_greater_than_Arrival_Date_plus_One_day"), OrderConstants.SOW_TAB_ERROR);
			}
		}*/

		// setting for displaying back in the dojo calendar due to formating
		/*if (StringUtils.isNotBlank(getArrivalDate())) {
			if (DateUtils.isValidDate(getArrivalDate(), "MM/dd/yyyy")) {
				setArrivalDate(TimeUtils.convertToDojoDateFormat(getArrivalDate()));
			} else {
				setArrivalDate("");
			}
		}
		if (StringUtils.isNotBlank(getDepartureDate())) {
			if (DateUtils.isValidDate(getDepartureDate(), "MM/dd/yyyy")) {
				setDepartureDate(TimeUtils.convertToDojoDateFormat(getDepartureDate()));
			} else {
				setDepartureDate("");
			}
		}*/
	}

	public static String convertToDojoDateFormat(String date) {
		DateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String theDate = "";
		Date actualDate = null;
		try {
			actualDate = sdf1.parse(date);
			theDate = sdf2.format(actualDate);
		} catch (ParseException e) {
			return "invalid_date";
		}

		return theDate;
	}

	public static String formatDate(Date date) {
		DateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy");
		String theDate = "";
		theDate = sdf2.format(date);
		if (logger.isInfoEnabled()) {
			logger.info(" Inside if-theDate" + theDate);
			logger.info("theDate" + theDate);
		}
		return theDate;
	}
	
	public Date fetchDateInGivenTimeZone(String timeZone){
		Calendar calendar = Calendar.getInstance();
        TimeZone fromTimeZone = calendar.getTimeZone();
        TimeZone toTimeZone = TimeZone.getTimeZone(timeZone);

        calendar.setTimeZone(fromTimeZone);
        calendar.add(Calendar.MILLISECOND, fromTimeZone.getRawOffset() * -1);
        if (fromTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings() * -1);
        }
        calendar.add(Calendar.MILLISECOND, toTimeZone.getRawOffset());
        if (toTimeZone.inDaylightTime(calendar.getTime())) {
            calendar.add(Calendar.MILLISECOND, calendar.getTimeZone().getDSTSavings());
        }
        return calendar.getTime();
	}

	public ArrayList<TimeOnSiteResultRowDTO> getResults() {
		return results;
	}

	public void setResults(ArrayList<TimeOnSiteResultRowDTO> results) {
		this.results = results;
	}

	public String getArrivalDepartureDate() {
		return arrivalDepartureDate;
	}

	public void setArrivalDepartureDate(String arrivalDepartureDate) {
		this.arrivalDepartureDate = arrivalDepartureDate;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public Integer getArrivalTimeHour() {
		return arrivalTimeHour;
	}

	public void setArrivalTimeHour(Integer arrivalTimeHour) {
		this.arrivalTimeHour = arrivalTimeHour;
	}

	public Integer getArrivalTimeMinutes() {
		return arrivalTimeMinutes;
	}

	public void setArrivalTimeMinutes(Integer arrivalTimeMinutes) {
		this.arrivalTimeMinutes = arrivalTimeMinutes;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getDepartureTimeHour() {
		return departureTimeHour;
	}

	public void setDepartureTimeHour(Integer departureTimeHour) {
		this.departureTimeHour = departureTimeHour;
	}

	public Integer getDepartureTimeMinutes() {
		return departureTimeMinutes;
	}

	public void setDepartureTimeMinutes(Integer departureTimeMinutes) {
		this.departureTimeMinutes = departureTimeMinutes;
	}

	public String getDepartureTimeAmPm() {
		return departureTimeAmPm;
	}

	public void setDepartureTimeAmPm(String departureTimeAmPm) {
		this.departureTimeAmPm = departureTimeAmPm;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getTabIdentifier() {
		return "";
	}

	public boolean isAddEditEntryPanel() {
		return addEditEntryPanel;
	}

	public void setAddEditEntryPanel(boolean addEditEntryPanel) {
		this.addEditEntryPanel = addEditEntryPanel;
	}

	public boolean isAddMode() {
		return addMode;
	}

	public void setAddMode(boolean addMode) {
		this.addMode = addMode;
	}

	public Date getArrivalTimestamp() {
		return arrivalTimestamp;
	}

	public void setArrivalTimestamp(Date arrivalTimestamp) {
		this.arrivalTimestamp = arrivalTimestamp;
	}

	public Date getDepartureTimestamp() {
		return departureTimestamp;
	}

	public void setDepartureTimestamp(Date departureTimestamp) {
		this.departureTimestamp = departureTimestamp;
	}

	public Long getVisitId() {
		return visitId;
	}

	public void setVisitId(Long visitId) {
		this.visitId = visitId;
	}

	public String getSoId() {
		return soId;
	}

	public void setSoId(String soId) {
		this.soId = soId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getDepartureTimeHourString() {
		return departureTimeHourString;
	}

	public void setDepartureTimeHourString(String departureTimeHourString) {
		this.departureTimeHourString = departureTimeHourString;
	}

	public String getDepartureTimeMinutesString() {
		return departureTimeMinutesString;
	}

	public void setDepartureTimeMinutesString(String departureTimeMinutesString) {
		this.departureTimeMinutesString = departureTimeMinutesString;
	}

	public String getArrivalDeparture() {
		return arrivalDeparture;
	}

	public void setArrivalDeparture(String arrivalDeparture) {
		this.arrivalDeparture = arrivalDeparture;
	}

	public String getTimeOnSiteReason() {
		return timeOnSiteReason;
	}

	public void setTimeOnSiteReason(String timeOnSiteReason) {
		this.timeOnSiteReason = timeOnSiteReason;
	}

	public String getArrivalDepartureTimeHourString() {
		return arrivalDepartureTimeHourString;
	}

	public void setArrivalDepartureTimeHourString(
			String arrivalDepartureTimeHourString) {
		this.arrivalDepartureTimeHourString = arrivalDepartureTimeHourString;
	}

	public String getArrivalDepartureTimeMinutesString() {
		return arrivalDepartureTimeMinutesString;
	}

	public void setArrivalDepartureTimeMinutesString(
			String arrivalDepartureTimeMinutesString) {
		this.arrivalDepartureTimeMinutesString = arrivalDepartureTimeMinutesString;
	}

	public String getArrivalDepartureTimeAmPm() {
		return arrivalDepartureTimeAmPm;
	}

	public void setArrivalDepartureTimeAmPm(String arrivalDepartureTimeAmPm) {
		this.arrivalDepartureTimeAmPm = arrivalDepartureTimeAmPm;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Integer getTripNumber() {
		return tripNumber;
	}

	public void setTripNumber(Integer tripNumber) {
		this.tripNumber = tripNumber;
	}
	
	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
