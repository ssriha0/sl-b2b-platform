package com.newco.marketplace.vo.provider;

import java.util.List;

public class CampaignVO {

	private Integer campaign_id;
	private String campaign_name;
	private List<Integer> attendee_user_ids;
	private Integer description_id;
	private String description;
	private Integer port_count;
	private Integer email_notice_id;
	private Integer fax_notice_id;
	private Integer ratio_record;
	private Integer ratio_out_of;
	private String caller_id;
	private Integer minimum_record_time;
	private Integer no_answer_timeout;
	private boolean detect_answer_machine;
	private Integer no_voice_timeout;
	private Integer max_silence_timeout;
	private boolean completed;
	private boolean deleted;
	private String start_date;
	private Integer minutes_between_calls;
	private Integer convert_and_route;
	private String campaign_category;
	private Integer campaign_category_id;
	private String voice_message;
	private Integer voice_message_id;
	private Integer id;
	public Integer getCampaign_id() {
		return campaign_id;
	}
	public void setCampaign_id(Integer campaign_id) {
		this.campaign_id = campaign_id;
	}
	public String getCampaign_name() {
		return campaign_name;
	}
	public void setCampaign_name(String campaign_name) {
		this.campaign_name = campaign_name;
	}
	public List<Integer> getAttendee_user_ids() {
		return attendee_user_ids;
	}
	public void setAttendee_user_ids(List<Integer> attendee_user_ids) {
		this.attendee_user_ids = attendee_user_ids;
	}
	public Integer getDescription_id() {
		return description_id;
	}
	public void setDescription_id(Integer description_id) {
		this.description_id = description_id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getPort_count() {
		return port_count;
	}
	public void setPort_count(Integer port_count) {
		this.port_count = port_count;
	}
	public Integer getEmail_notice_id() {
		return email_notice_id;
	}
	public void setEmail_notice_id(Integer email_notice_id) {
		this.email_notice_id = email_notice_id;
	}
	public Integer getFax_notice_id() {
		return fax_notice_id;
	}
	public void setFax_notice_id(Integer fax_notice_id) {
		this.fax_notice_id = fax_notice_id;
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
	public String getCaller_id() {
		return caller_id;
	}
	public void setCaller_id(String caller_id) {
		this.caller_id = caller_id;
	}
	public Integer getMinimum_record_time() {
		return minimum_record_time;
	}
	public void setMinimum_record_time(Integer minimum_record_time) {
		this.minimum_record_time = minimum_record_time;
	}
	public Integer getNo_answer_timeout() {
		return no_answer_timeout;
	}
	public void setNo_answer_timeout(Integer no_answer_timeout) {
		this.no_answer_timeout = no_answer_timeout;
	}
	public boolean isDetect_answer_machine() {
		return detect_answer_machine;
	}
	public void setDetect_answer_machine(boolean detect_answer_machine) {
		this.detect_answer_machine = detect_answer_machine;
	}
	public Integer getNo_voice_timeout() {
		return no_voice_timeout;
	}
	public void setNo_voice_timeout(Integer no_voice_timeout) {
		this.no_voice_timeout = no_voice_timeout;
	}
	public Integer getMax_silence_timeout() {
		return max_silence_timeout;
	}
	public void setMax_silence_timeout(Integer max_silence_timeout) {
		this.max_silence_timeout = max_silence_timeout;
	}
	public boolean isCompleted() {
		return completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public Integer getMinutes_between_calls() {
		return minutes_between_calls;
	}
	public void setMinutes_between_calls(Integer minutes_between_calls) {
		this.minutes_between_calls = minutes_between_calls;
	}
	public Integer getConvert_and_route() {
		return convert_and_route;
	}
	public void setConvert_and_route(Integer convert_and_route) {
		this.convert_and_route = convert_and_route;
	}
	public String getCampaign_category() {
		return campaign_category;
	}
	public void setCampaign_category(String campaign_category) {
		this.campaign_category = campaign_category;
	}
	public Integer getCampaign_category_id() {
		return campaign_category_id;
	}
	public void setCampaign_category_id(Integer campaign_category_id) {
		this.campaign_category_id = campaign_category_id;
	}
	public String getVoice_message() {
		return voice_message;
	}
	public void setVoice_message(String voice_message) {
		this.voice_message = voice_message;
	}
	public Integer getVoice_message_id() {
		return voice_message_id;
	}
	public void setVoice_message_id(Integer voice_message_id) {
		this.voice_message_id = voice_message_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}
