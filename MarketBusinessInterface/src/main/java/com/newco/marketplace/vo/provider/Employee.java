package com.newco.marketplace.vo.provider;


public class Employee {
	
	private Integer employee_user_id;
	private boolean deleted;
	private String name_prefix;
	private String employee_name; 
	private String title;
	private String username;
	private String birthdate;	
	private Integer reports_to_user_id;
	private String reports_to_name;
	private Integer assistant_user_id; 
	private String assistant_name;
	private String employee_identifier;
	private String system_identifier;
	private String color;
	private String company_settings;
	private String team;
	private String divisions;
	private boolean warn_on_exit; 
	private String phone;
	private String other_phone;
	private String home_phone;
	private String mobile_phone;
	private String fax;
	private String email;
	private String website;
	private String time_zone;
	private String zones;
	private String page_permission;
	private String start_page;
	private String default_results_per_page;
	private String smtp_email_username;
	private Integer creator_user_id;
	private String created_by;
	private String date_created;
	private Integer modified_user_id;
	private String modified_by;
	private String date_modified;
	private String address_1;
	private String address_2;
	private String city;
	private String state;
	private Integer zip;
	private String country;
	
	
	public Employee(Integer employee_user_id, boolean deleted,
			String name_prefix, String employee_name, String title,
			String username, String birthdate, Integer reports_to_user_id,
			String reports_to_name, Integer assistant_user_id,
			String assistant_name, String employee_identifier,
			String system_identifier, String color, String company_settings,
			String team, String divisions, boolean warn_on_exit, String phone,
			String other_phone, String home_phone, String mobile_phone,
			String fax, String email, String website, String time_zone,
			String zones, String page_permission, String start_page,
			String default_results_per_page, String smtp_email_username,
			Integer creator_user_id, String created_by, String date_created,
			Integer modified_user_id, String modified_by, String date_modified,
			String address_1, String address_2, String city, String state,
			Integer zip, String country) {
		super();
		this.employee_user_id = employee_user_id;
		this.deleted = deleted;
		this.name_prefix = name_prefix;
		this.employee_name = employee_name;
		this.title = title;
		this.username = username;
		this.birthdate = birthdate;
		this.reports_to_user_id = reports_to_user_id;
		this.reports_to_name = reports_to_name;
		this.assistant_user_id = assistant_user_id;
		this.assistant_name = assistant_name;
		this.employee_identifier = employee_identifier;
		this.system_identifier = system_identifier;
		this.color = color;
		this.company_settings = company_settings;
		this.team = team;
		this.divisions = divisions;
		this.warn_on_exit = warn_on_exit;
		this.phone = phone;
		this.other_phone = other_phone;
		this.home_phone = home_phone;
		this.mobile_phone = mobile_phone;
		this.fax = fax;
		this.email = email;
		this.website = website;
		this.time_zone = time_zone;
		this.zones = zones;
		this.page_permission = page_permission;
		this.start_page = start_page;
		this.default_results_per_page = default_results_per_page;
		this.smtp_email_username = smtp_email_username;
		this.creator_user_id = creator_user_id;
		this.created_by = created_by;
		this.date_created = date_created;
		this.modified_user_id = modified_user_id;
		this.modified_by = modified_by;
		this.date_modified = date_modified;
		this.address_1 = address_1;
		this.address_2 = address_2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}


	public Integer getEmployee_user_id() {
		return employee_user_id;
	}


	public void setEmployee_user_id(Integer employee_user_id) {
		this.employee_user_id = employee_user_id;
	}


	public boolean isDeleted() {
		return deleted;
	}


	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public String getName_prefix() {
		return name_prefix;
	}


	public void setName_prefix(String name_prefix) {
		this.name_prefix = name_prefix;
	}


	public String getEmployee_name() {
		return employee_name;
	}


	public void setEmployee_name(String employee_name) {
		this.employee_name = employee_name;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getBirthdate() {
		return birthdate;
	}


	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}


	public Integer getReports_to_user_id() {
		return reports_to_user_id;
	}


	public void setReports_to_user_id(Integer reports_to_user_id) {
		this.reports_to_user_id = reports_to_user_id;
	}


	public String getReports_to_name() {
		return reports_to_name;
	}


	public void setReports_to_name(String reports_to_name) {
		this.reports_to_name = reports_to_name;
	}


	public Integer getAssistant_user_id() {
		return assistant_user_id;
	}


	public void setAssistant_user_id(Integer assistant_user_id) {
		this.assistant_user_id = assistant_user_id;
	}


	public String getAssistant_name() {
		return assistant_name;
	}


	public void setAssistant_name(String assistant_name) {
		this.assistant_name = assistant_name;
	}


	public String getEmployee_identifier() {
		return employee_identifier;
	}


	public void setEmployee_identifier(String employee_identifier) {
		this.employee_identifier = employee_identifier;
	}


	public String getSystem_identifier() {
		return system_identifier;
	}


	public void setSystem_identifier(String system_identifier) {
		this.system_identifier = system_identifier;
	}


	public String getColor() {
		return color;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public String getCompany_settings() {
		return company_settings;
	}


	public void setCompany_settings(String company_settings) {
		this.company_settings = company_settings;
	}


	public String getTeam() {
		return team;
	}


	public void setTeam(String team) {
		this.team = team;
	}


	public String getDivisions() {
		return divisions;
	}


	public void setDivisions(String divisions) {
		this.divisions = divisions;
	}


	public boolean isWarn_on_exit() {
		return warn_on_exit;
	}


	public void setWarn_on_exit(boolean warn_on_exit) {
		this.warn_on_exit = warn_on_exit;
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


	public String getTime_zone() {
		return time_zone;
	}


	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}


	public String getZones() {
		return zones;
	}


	public void setZones(String zones) {
		this.zones = zones;
	}


	public String getPage_permission() {
		return page_permission;
	}


	public void setPage_permission(String page_permission) {
		this.page_permission = page_permission;
	}


	public String getStart_page() {
		return start_page;
	}


	public void setStart_page(String start_page) {
		this.start_page = start_page;
	}


	public String getDefault_results_per_page() {
		return default_results_per_page;
	}


	public void setDefault_results_per_page(String default_results_per_page) {
		this.default_results_per_page = default_results_per_page;
	}


	public String getSmtp_email_username() {
		return smtp_email_username;
	}


	public void setSmtp_email_username(String smtp_email_username) {
		this.smtp_email_username = smtp_email_username;
	}


	public Integer getCreator_user_id() {
		return creator_user_id;
	}


	public void setCreator_user_id(Integer creator_user_id) {
		this.creator_user_id = creator_user_id;
	}


	public String getCreated_by() {
		return created_by;
	}


	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}


	public String getDate_created() {
		return date_created;
	}


	public void setDate_created(String date_created) {
		this.date_created = date_created;
	}


	public Integer getModified_user_id() {
		return modified_user_id;
	}


	public void setModified_user_id(Integer modified_user_id) {
		this.modified_user_id = modified_user_id;
	}


	public String getModified_by() {
		return modified_by;
	}


	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}


	public String getDate_modified() {
		return date_modified;
	}


	public void setDate_modified(String date_modified) {
		this.date_modified = date_modified;
	}


	public String getAddress_1() {
		return address_1;
	}


	public void setAddress_1(String address_1) {
		this.address_1 = address_1;
	}


	public String getAddress_2() {
		return address_2;
	}


	public void setAddress_2(String address_2) {
		this.address_2 = address_2;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public Integer getZip() {
		return zip;
	}


	public void setZip(Integer zip) {
		this.zip = zip;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}
	
	
	
}
