package com.servicelive.integrationtest.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.servicelive.integrationtest.domain.SqlQuery;
import com.servicelive.integrationtest.domain.QueryCell;
import com.servicelive.integrationtest.domain.QueryResults;
import com.servicelive.integrationtest.domain.QueryRow;
import com.servicelive.integrationtest.rowMapper.GenericMapper;

public class ServiceOrderDao implements IServiceOrderDao {

	private NamedParameterJdbcTemplate jdbcSupplierTemplate;
	
	private static final String sqlSoHeader = "SELECT s.buyer_id, s.accepted_resource_id, " +
		"s.buyer_contact_id, s.price_model, s.creator_user_name, s.closer_user_name, s.assoc_so_id, " +
		"s.assoc_reason_id, s.act_arrival_start_date, s.act_arrival_end_date, s.pricing_type_id, " +
		"s.initial_price, s.spend_limit_incr_comment, s.access_fee, s.sow_title, s.sow_descr, " +
		"s.provider_instr, s.buyer_terms_cond, s.provider_terms_cond_resp, s.provider_terms_cond_date, " +
		"s.resolution_descr, s.wf_state_id, s.so_substatus_id, s.primary_skill_category_id, " +
		"s.service_date_type_id, s.service_date1, s.service_date2, s.service_time_start, " +
		"s.service_time_end, s.service_date_timezone_offset, s.provider_service_confirm_ind, " +
		"s.spend_limit_labor, s.spend_limit_parts, s.accepted_vendor_id, s.final_price_labor, " +
		"s.resched_service_date1, s.resched_service_date2, s.resched_service_time_start, " +
		"s.resched_service_time_end, s.last_status_id, s.parts_supplied_by_id, s.resched_date_type_id, " +
		"s.final_price_parts, s.so_terms_cond_id, s.doc_size_total, s.lock_edit_ind, " +
		"s.logo_document_id, s.buyer_resource_id, s.buyer_so_terms_cond_id, s.buyer_so_terms_cond_ind, " +
		"s.buyer_so_terms_cond_date, s.servicelive_bucks_date_accepted, s.servicelive_bucks_ind, " +
		"s.provider_so_terms_cond_id, s.provider_so_terms_cond_ind, s.provider_so_terms_cond_date, " +
		"s.funding_type_id, s.posting_fee, s.cancellation_fee, s.retail_price, " +
		"s.retail_cancellation_fee, s.service_date1_time, s.so_group_id, s.so_orig_group_id, " +
		"s.spn_id, s.pos_date, s.account_id, s.share_contact_ind FROM so_hdr s " +
		"WHERE s.so_id = :soId";
	
	private static final String sqlSoCustomRefs = "SELECT buyer_ref_type_id, buyer_ref_value " + 
		"FROM so_custom_reference s WHERE s.so_id = :soId ORDER BY s.buyer_ref_type_id";
	
	private static final String sqlSoAddOns = "SELECT sku, description, retail_price, qty " + 
		"coverage, margin, misc_ind, scope_of_work, service_fee_final " +
		"FROM so_addon s WHERE s.so_id = :soId ORDER BY s.sku";
	
	private static final String sqlSoContacts = "SELECT s.business_name, s.last_name, s.first_name, " +
		"s.mi, s.suffix, s.email, s.so_contact_type_id, s.honorific, s.entity_type_id, " +
		"s.entity_id, l_join.so_contact_locn_type_id, l.street_1, l.street_2, l.city, " +
		"l.state_cd, l.zip, l.zip4, l.apt_no, l.country, l.so_locn_type_id, " +
		"l.so_locn_class_id, l.locn_name, l.locn_notes FROM so_contact s " +
		"LEFT JOIN so_contact_locn l_join ON (s.so_contact_id = l_join.so_contact_id) " +
		"LEFT JOIN so_location l ON (l_join.so_locn_id = l.so_locn_id) " +
		"WHERE s.so_id = :soId ORDER BY l_join.so_contact_locn_type_id";
	
	private static final String sqlSoContactPhones = "SELECT s.phone_type_id, s.so_phone_class_id, " +
		"s.phone_no, s.phone_no_ext, s.primary_contact_ind, c.so_contact_type_id " +
		"FROM so_contact_phones s LEFT JOIN so_contact c ON (s.so_contact_id = c.so_contact_id)" +
		"WHERE s.so_id = :soId ORDER BY s.phone_no";
	
	private static final String sqlSoTasks = "SELECT s.skill_node_id, s.service_type_template_id, " +
		"s.task_name, s.task_comments, s.price, s.auto_injected_ind, s.sort_order, s.primary_task " + 
		"FROM so_tasks s WHERE so_id = :soId ORDER BY s.sort_order, s.task_name";
	
	private static final String sqlSoPrice = "SELECT s.condl_offer_accepted_price, " +
		"s.original_spend_limit_labor, s.original_spend_limit_parts, s.discounted_spend_limit_labor, " +
		"s.discounted_spend_limit_parts, s.initial_posted_labor_spend_limit, " + 
		"s.initial_posted_parts_spend_limit, s.final_service_fee " + 
		"FROM so_price s WHERE so_id = :soId";
	
	private static final String sqlSoNotes = "SELECT s.note_subject, s.role_id, s.note, " + 
		"s.note_type_id, s.created_by_name, s.entity_id, s.private_ind " + 
		"FROM so_notes s WHERE so_id = :soId ORDER BY s.note_subject, s.note";
	
	private static final String sqlSoParts = "SELECT s.part_descr, s.ship_carrier_id, " + 
		"s.ship_carrier_other, s.ship_track_no, s.return_carrier_id, s.return_carrier_other, " + 
		"s.return_track_no, s.core_return_carrier_id, s.core_return_track_no, " + 
		"s.core_return_carrier_other, s.provider_bring_part_ind, s.manufacturer, s.model_no, " + 
		"s.measurement_standard, s.length, s.width, s.height, s.weight, s.quantity, " + 
		"s.reference_part_id, s.ship_date, s.parts_file_generated_date, s.serial_number, " + 
		"s.manufacturer_part_number, s.vendor_part_number, s.product_line, s.additional_part_info, " + 
		"s.purchase_order_number, s.part_status_id, s.return_track_date, s.order_number, " + 
		"s.alt_part_ref1, s.alt_part_ref2 FROM so_parts s WHERE so_id = :soId " +
		"ORDER BY s.vendor_part_number, s.manufacturer_part_number, s.part_descr";
	
	private static final String sqlSoLogging = "SELECT s.action_id, s.old_value, " + 
		"s.new_value, s.chg_comment, s.role_id, s.created_by_name, s.entity_id " + 
		"FROM so_logging s WHERE so_id = :soId " +
		"ORDER BY s.new_value, s.old_value, s.chg_comment";
	
	
	public NamedParameterJdbcTemplate getJdbcSupplierTemplate() {
		return jdbcSupplierTemplate;
	}

	public void setJdbcSupplierTemplate(
			NamedParameterJdbcTemplate jdbcSupplierTemplate) {
		this.jdbcSupplierTemplate = jdbcSupplierTemplate;
	}

	
	@SuppressWarnings("unchecked")
	public List<QueryResults> runAllQueriesForServiceOrder(String soId) {
		
		List<QueryResults> allQueryResults = new ArrayList<QueryResults>();
		List<SqlQuery> queriesToRun = new ArrayList<SqlQuery>();
		
		Map<String, Object> namedParameters = new HashMap<String, Object>();
		namedParameters.put("soId", soId);
		
		queriesToRun.add(new SqlQuery("so_hdr", sqlSoHeader));
		queriesToRun.add(new SqlQuery("so_custom_references", sqlSoCustomRefs));
		queriesToRun.add(new SqlQuery("so_addon", sqlSoAddOns));
		queriesToRun.add(new SqlQuery("so_contact/so_contact_locn/so_location", sqlSoContacts));
		queriesToRun.add(new SqlQuery("so_contact_phones", sqlSoContactPhones));
		queriesToRun.add(new SqlQuery("so_tasks", sqlSoTasks));
		queriesToRun.add(new SqlQuery("so_price", sqlSoPrice));
		queriesToRun.add(new SqlQuery("so_notes", sqlSoNotes));
		queriesToRun.add(new SqlQuery("so_parts", sqlSoParts));
		queriesToRun.add(new SqlQuery("so_logging", sqlSoLogging));
		
		for (SqlQuery query : queriesToRun) {
			List<QueryRow> results = jdbcSupplierTemplate.query(
					query.getQuerySql(), namedParameters, new GenericMapper());
			QueryResults resultsForQuery = new QueryResults();
			
			for (QueryRow row : results) {
				for (QueryCell cell : row.getResultCells()) {
					cell.setQueryName(query.getQueryName());
				}
				resultsForQuery.addResult(row);
			}
			
			resultsForQuery.setQueryName(query.getQueryName());			
			allQueryResults.add(resultsForQuery);
		}
		
		return allQueryResults;
	}

}
