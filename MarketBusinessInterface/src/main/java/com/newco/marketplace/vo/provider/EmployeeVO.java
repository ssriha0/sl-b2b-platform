package com.newco.marketplace.vo.provider;

import java.util.List;

public class EmployeeVO {
	
	private Integer id;
	private String external_id;
	private Integer billing_user_id;
	private String date_created;
	private Integer created_by_user_id;
	private String date_modified;
	private Integer modified_by_user_id;
	private boolean deleted; 
	private String description;
	private Integer contact_info_id;
	private Integer reports_to_employee_user_id;
	private String username;
	private String password;
	private Integer account_id;
	private Integer assistant_employee_user_id; 
	private List<String> licenses;
	private List<String> company_settings;
	private boolean admin;
	private Integer division_id;
	private List<Integer> division_ids;
	private String smtp_email_username;
	private String smtp_email_password;
	private Integer start_page_saved_view_id;
	private Integer zone_id;
	private Integer team_id; 
	private String event_reminder;
	private Integer default_results_per_page;
	private boolean warn_on_exit; 
	private boolean allow_metered_calls;
	private boolean can_mass_email;
	private boolean can_fax;
	private Integer billing_account_id;
	private Integer dm_billing_account_id; 
	private String nickname; 
	private Integer ratio_record;
	private Integer ratio_out_of; 
	private List<Integer> layout_view_ids; 
	private Integer perm_id;
	private Integer time_zone_id;
	private String color;
	private Integer layout_view_id;
	private String name_prefix;
	private String first_name; 
	private String last_name;
	private String title;
	private String phone;
	private String other_phone;
	private String home_phone;
	private String mobile_phone;
	private String fax;
	private String email;
	private String website;
	private String birthdate;
	private Integer minimum_record_time;
	private Integer session_timeout;
	
	private String city;
	private String daily_dial_attempts_1;
	private String daily_dial_attempts_2;
	private String daily_dial_attempt_limit_1;
	private String daily_dial_attempt_limit_2;	
	private String country_abbrev;	
	private String country;	
	private Integer ucn_contact_id;	
	private Integer description_id;	
	private String salesforce_is_token;	
	private String employee_identifier;
	private String agent_domain_port;	
	private String addr1;	
	private String addr2;
	private String caller_id;	
	private String state;	
	private String caller_id_type;	
	private Integer address_id;	
	private String zip;	
	private String state_abbrev;
	private Integer default_country_id;	
	private boolean remember_acds;
	private Integer name_prefix_id;
	
	public Integer getName_prefix_id() {
		return name_prefix_id;
	}
	public void setName_prefix_id(Integer name_prefix_id) {
		this.name_prefix_id = name_prefix_id;
	}
	public boolean isRemember_acds() {
		return remember_acds;
	}
	public void setRemember_acds(boolean remember_acds) {
		this.remember_acds = remember_acds;
	}
	public Integer getDefault_country_id() {
		return default_country_id;
	}
	public void setDefault_country_id(Integer default_country_id) {
		this.default_country_id = default_country_id;
	}
	public String getState_abbrev() {
		return state_abbrev;
	}
	public void setState_abbrev(String state_abbrev) {
		this.state_abbrev = state_abbrev;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public Integer getAddress_id() {
		return address_id;
	}
	public void setAddress_id(Integer address_id) {
		this.address_id = address_id;
	}
	public String getCaller_id_type() {
		return caller_id_type;
	}
	public void setCaller_id_type(String caller_id_type) {
		this.caller_id_type = caller_id_type;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCaller_id() {
		return caller_id;
	}
	public void setCaller_id(String caller_id) {
		this.caller_id = caller_id;
	}
	public String getAddr2() {
		return addr2;
	}
	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}
	public String getAddr1() {
		return addr1;
	}
	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}
	public String getAgent_domain_port() {
		return agent_domain_port;
	}
	public void setAgent_domain_port(String agent_domain_port) {
		this.agent_domain_port = agent_domain_port;
	}
	public String getEmployee_identifier() {
		return employee_identifier;
	}
	public void setEmployee_identifier(String employee_identifier) {
		this.employee_identifier = employee_identifier;
	}
	public String getSalesforce_is_token() {
		return salesforce_is_token;
	}
	public void setSalesforce_is_token(String salesforce_is_token) {
		this.salesforce_is_token = salesforce_is_token;
	}
	public Integer getDescription_id() {
		return description_id;
	}
	public void setDescription_id(Integer description_id) {
		this.description_id = description_id;
	}
	public Integer getUcn_contact_id() {
		return ucn_contact_id;
	}
	public void setUcn_contact_id(Integer ucn_contact_id) {
		this.ucn_contact_id = ucn_contact_id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry_abbrev() {
		return country_abbrev;
	}
	public void setCountry_abbrev(String country_abbrev) {
		this.country_abbrev = country_abbrev;
	}
	public String getDaily_dial_attempts_1() {
		return daily_dial_attempts_1;
	}
	public void setDaily_dial_attempts_1(String daily_dial_attempts_1) {
		this.daily_dial_attempts_1 = daily_dial_attempts_1;
	}
	public String getDaily_dial_attempts_2() {
		return daily_dial_attempts_2;
	}
	public void setDaily_dial_attempts_2(String daily_dial_attempts_2) {
		this.daily_dial_attempts_2 = daily_dial_attempts_2;
	}
	public String getDaily_dial_attempt_limit_1() {
		return daily_dial_attempt_limit_1;
	}
	public void setDaily_dial_attempt_limit_1(String daily_dial_attempt_limit_1) {
		this.daily_dial_attempt_limit_1 = daily_dial_attempt_limit_1;
	}
	public String getDaily_dial_attempt_limit_2() {
		return daily_dial_attempt_limit_2;
	}
	public void setDaily_dial_attempt_limit_2(String daily_dial_attempt_limit_2) {
		this.daily_dial_attempt_limit_2 = daily_dial_attempt_limit_2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getExternal_id() {
		return external_id;
	}
	public void setExternal_id(String external_id) {
		this.external_id = external_id;
	}
	public Integer getBilling_user_id() {
		return billing_user_id;
	}
	public void setBilling_user_id(Integer billing_user_id) {
		this.billing_user_id = billing_user_id;
	}
	public String getDate_created() {
		return date_created;
	}
	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}
	public Integer getCreated_by_user_id() {
		return created_by_user_id;
	}
	public void setCreated_by_user_id(Integer created_by_user_id) {
		this.created_by_user_id = created_by_user_id;
	}
	public String getDate_modified() {
		return date_modified;
	}
	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}
	public Integer getModified_by_user_id() {
		return modified_by_user_id;
	}
	public void setModified_by_user_id(Integer modified_by_user_id) {
		this.modified_by_user_id = modified_by_user_id;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getContact_info_id() {
		return contact_info_id;
	}
	public void setContact_info_id(Integer contact_info_id) {
		this.contact_info_id = contact_info_id;
	}
	public Integer getReports_to_employee_user_id() {
		return reports_to_employee_user_id;
	}
	public void setReports_to_employee_user_id(Integer reports_to_employee_user_id) {
		this.reports_to_employee_user_id = reports_to_employee_user_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getAccount_id() {
		return account_id;
	}
	public void setAccount_id(Integer account_id) {
		this.account_id = account_id;
	}
	public Integer getAssistant_employee_user_id() {
		return assistant_employee_user_id;
	}
	public void setAssistant_employee_user_id(Integer assistant_employee_user_id) {
		this.assistant_employee_user_id = assistant_employee_user_id;
	}
	public List<String> getLicenses() {
		return licenses;
	}
	public void setLicenses(List<String> licenses) {
		this.licenses = licenses;
	}
	public List<String> getCompany_settings() {
		return company_settings;
	}
	public void setCompany_settings(List<String> company_settings) {
		this.company_settings = company_settings;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public Integer getDivision_id() {
		return division_id;
	}
	public void setDivision_id(Integer division_id) {
		this.division_id = division_id;
	}
	public List<Integer> getDivision_ids() {
		return division_ids;
	}
	public void setDivision_ids(List<Integer> division_ids) {
		this.division_ids = division_ids;
	}
	public String getSmtp_email_username() {
		return smtp_email_username;
	}
	public void setSmtp_email_username(String smtp_email_username) {
		this.smtp_email_username = smtp_email_username;
	}
	public String getSmtp_email_password() {
		return smtp_email_password;
	}
	public void setSmtp_email_password(String smtp_email_password) {
		this.smtp_email_password = smtp_email_password;
	}
	public Integer getStart_page_saved_view_id() {
		return start_page_saved_view_id;
	}
	public void setStart_page_saved_view_id(Integer start_page_saved_view_id) {
		this.start_page_saved_view_id = start_page_saved_view_id;
	}
	public Integer getZone_id() {
		return zone_id;
	}
	public void setZone_id(Integer zone_id) {
		this.zone_id = zone_id;
	}
	public Integer getTeam_id() {
		return team_id;
	}
	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}
	public String getEvent_reminder() {
		return event_reminder;
	}
	public void setEvent_reminder(String event_reminder) {
		this.event_reminder = event_reminder;
	}
	public Integer getDefault_results_per_page() {
		return default_results_per_page;
	}
	public void setDefault_results_per_page(Integer default_results_per_page) {
		this.default_results_per_page = default_results_per_page;
	}
	public boolean isWarn_on_exit() {
		return warn_on_exit;
	}
	public void setWarn_on_exit(boolean warn_on_exit) {
		this.warn_on_exit = warn_on_exit;
	}
	public boolean isAllow_metered_calls() {
		return allow_metered_calls;
	}
	public void setAllow_metered_calls(boolean allow_metered_calls) {
		this.allow_metered_calls = allow_metered_calls;
	}
	public boolean isCan_mass_email() {
		return can_mass_email;
	}
	public void setCan_mass_email(boolean can_mass_email) {
		this.can_mass_email = can_mass_email;
	}
	public boolean isCan_fax() {
		return can_fax;
	}
	public void setCan_fax(boolean can_fax) {
		this.can_fax = can_fax;
	}
	public Integer getBilling_account_id() {
		return billing_account_id;
	}
	public void setBilling_account_id(Integer billing_account_id) {
		this.billing_account_id = billing_account_id;
	}
	public Integer getDm_billing_account_id() {
		return dm_billing_account_id;
	}
	public void setDm_billing_account_id(Integer dm_billing_account_id) {
		this.dm_billing_account_id = dm_billing_account_id;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getRatio_record() {
		return ratio_record;
	}
	public void setRatio_record(Integer ratio_record) {
		this.ratio_record = ratio_record;
	}
	public Integer getRatio_out_of() {
		return ratio_out_of;
	}
	public void setRatio_out_of(Integer ratio_out_of) {
		this.ratio_out_of = ratio_out_of;
	}
	public List<Integer> getLayout_view_ids() {
		return layout_view_ids;
	}
	public void setLayout_view_ids(List<Integer> layout_view_ids) {
		this.layout_view_ids = layout_view_ids;
	}
	public Integer getPerm_id() {
		return perm_id;
	}
	public void setPerm_id(Integer perm_id) {
		this.perm_id = perm_id;
	}
	public Integer getTime_zone_id() {
		return time_zone_id;
	}
	public void setTime_zone_id(Integer time_zone_id) {
		this.time_zone_id = time_zone_id;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Integer getLayout_view_id() {
		return layout_view_id;
	}
	public void setLayout_view_id(Integer layout_view_id) {
		this.layout_view_id = layout_view_id;
	}
	public String getName_prefix() {
		return name_prefix;
	}
	public void setName_prefix(String name_prefix) {
		this.name_prefix = name_prefix;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOther_phone() {
		return other_phone;
	}
	public void setOther_phone(String other_phone) {
		this.other_phone = other_phone;
	}
	public String getHome_phone() {
		return home_phone;
	}
	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}
	public String getMobile_phone() {
		return mobile_phone;
	}
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getBirthdate() {
		return birthdate;
	}
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}
	public Integer getMinimum_record_time() {
		return minimum_record_time;
	}
	public void setMinimum_record_time(Integer minimum_record_time) {
		this.minimum_record_time = minimum_record_time;
	}
	public Integer getSession_timeout() {
		return session_timeout;
	}
	public void setSession_timeout(Integer session_timeout) {
		this.session_timeout = session_timeout;
	}
	
	
}
