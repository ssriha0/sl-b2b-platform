-- provider seach solr index

SET  SESSION group_concat_max_len = 30720;


DROP TEMPORARY TABLE IF EXISTS tmp_solr_resource_ratings;
CREATE TEMPORARY TABLE tmp_solr_resource_ratings
SELECT
      vr.resource_id,
      sum(if(rs.question_id = 1, rs.rating, 0)) AS timeliness,
      sum(if(rs.question_id = 2, rs.rating, 0)) AS communication,
      sum(if(rs.question_id = 3, rs.rating, 0)) AS professionalism,
      sum(if(rs.question_id = 4, rs.rating, 0)) AS quality,
      sum(if(rs.question_id = 5, rs.rating, 0)) AS  mvalue,
      sum(if(rs.question_id = 6, rs.rating, 0)) AS cleanliness,
      avg(rs.rating) AS overall,
      sum(if(rs.question_id = 1, rs.survey_count, 0))  AS survey_count
	FROM vendor_resource vr
      left join vw_solr_ratingscore rs on rs.resource_id = vr.resource_id
   group by vr.resource_id;
CREATE INDEX index_1 ON tmp_solr_resource_ratings (resource_id);

DROP TEMPORARY TABLE IF EXISTS tmp_solr_skills;
CREATE TEMPORARY TABLE tmp_solr_skills
SELECT rs.resource_id, GROUP_CONCAT(CONCAT(rs.node_id, SUBSTRING(lstt.descr, 1, 3))) AS skills FROM  resource_skill rs , resource_skill_service_type rsst, lu_service_type_template lstt  WHERE
	lstt.service_type_template_id = rsst.service_type_template_id AND
	rs.resource_skill_id = rsst.resource_skill_id AND rs.root_node_ind = FALSE  GROUP BY rs.resource_id;
CREATE INDEX index_2 ON tmp_solr_skills (resource_id);

DROP TEMPORARY TABLE IF EXISTS tmp_solr_review_count;
CREATE TEMPORARY TABLE tmp_solr_review_count
 SELECT so.accepted_resource_id resource_id, IFNULL(COUNT(*), 0) AS review_count, IFNULL(AVG(r.overall_score), 0.0) review_rating
		FROM survey_response_so srs INNER JOIN so_hdr so ON (srs.so_id = so.so_id)
      	INNER JOIN survey_response_hdr r ON (srs.response_hdr_id = r.response_hdr_id)
	WHERE srs.entity_type_id = 10
      		AND (r.comments IS NOT NULL AND LENGTH(r.comments) > 0) GROUP BY so.accepted_resource_id;
CREATE INDEX index_3 ON tmp_solr_review_count (resource_id);

DROP TEMPORARY TABLE IF EXISTS tmp_solr_company;
CREATE TEMPORARY TABLE tmp_solr_company
SELECT vr.resource_id AS resource_id,
    vh.business_name AS business_name,
    vr.created_date AS member_since,
	if(length(trim(ifnull(vh.business_descr,'-')))=0,'-',ifnull(vh.business_descr,'-')) AS overview,
	IFNULL(round((TO_DAYS(CURRENT_DATE) -  TO_DAYS(vh.business_start_date)) / 365), 0) AS yrs_of_experience,
	lucsize.descr  AS company_size,
	lbt.descr AS business_structure,
	IFNULL((select lp.value_in_days from lu_warranty_periods lp where id=policy.warr_period_labor), 0) AS warr_period_labor,
	IFNULL((select lp.value_in_days from lu_warranty_periods lp where id=policy.warr_period_parts), 0) AS warr_period_parts,
	IFNULL(!policy.free_estimate, 0) AS free_estimate,
    IFNULL(vh.ins_vehicle_liability_amount,0.0) AS ins_vehicle,
    IFNULL(vh.ins_work_comp_amount,0.0) AS ins_workman,
    IFNULL(vh.ins_gen_liability_amount,0.0) AS ins_general
    FROM vendor_hdr vh
		LEFT OUTER  JOIN lu_business_type lbt ON vh.business_type_id = lbt.id
    LEFT OUTER JOIN lu_company_size lucsize on lucsize.id = vh.company_size_id
    LEFT OUTER JOIN vendor_policy policy on policy.vendor_id = vh.vendor_id
		JOIN vendor_resource vr on vr.vendor_id = vh.vendor_id
    WHERE vr.resource_ind = 1
    AND vr.mkt_place_ind = 1
		and vh.wf_state_id in (3,34,26)
    and vr.wf_state_id = 6;
CREATE INDEX index_4 ON tmp_solr_company (resource_id);

DROP TEMPORARY TABLE IF EXISTS tmp_solr_ins;
CREATE TEMPORARY TABLE tmp_solr_ins
SELECT v.resource_id,
       MAX(IF(vc.cred_category_id = 41, 1, 0)) AS ins_verified_general,
       MAX(IF(vc.cred_category_id = 41, vc.cred_issue_date , NULL)) AS ins_verified_date_general,
       MAX(IF(vc.cred_category_id = 42,1, 0)) AS ins_verified_auto,
       MAX(IF(vc.cred_category_id = 42, vc.cred_issue_date , NULL)) AS ins_verified_date_auto,
       MAX(IF(vc.cred_category_id = 43, 1, 0)) AS ins_verified_workman,
       MAX(IF(vc.cred_category_id = 43, vc.cred_issue_date , NULL)) AS ins_verified_date_workman
       FROM vendor_credentials vc,  vendor_resource  v
       WHERE vc.cred_type_id = 6 AND v.vendor_id = vc.vendor_id AND vc.wf_state_id='14'
       AND vc.wf_state_id IS NOT NULL
       GROUP BY v.resource_id;
CREATE INDEX index_41 ON tmp_solr_ins (resource_id);

DROP TEMPORARY TABLE IF EXISTS tmp_solr_matrix;
CREATE TEMPORARY TABLE tmp_solr_matrix
SELECT vr.resource_id AS resource_id,
	IFNULL(vr.total_so_completed, 0) AS total_so_completed,
	IFNULL(ROUND(vr.aggregate_rating_score), 0) AS provider_rating,
	round(rat.survey_count) survey_count,
	IFNULL(vwrcount.review_count, 0) AS review_count,
	IFNULL(vwrcount.review_rating, 0.0) AS review_rating,
        rat.timeliness, rat.communication,
        rat.professionalism, rat.quality,
        rat.mvalue, rat.cleanliness, rat.overall,
        COUNT(rc.resource_id) AS license_count,
        COUNT(vc.vendor_id) AS license_count_company,
	ifnull(ins.ins_verified_general, '0') as ins_verified_general,
	ins.ins_verified_date_general,
	ifnull(ins.ins_verified_auto, '0') as ins_verified_auto,
	ins.ins_verified_date_auto,
	ifnull(ins.ins_verified_workman, '0') as ins_verified_workman,
	ins.ins_verified_date_workman
        FROM  vendor_resource vr
	LEFT OUTER JOIN tmp_solr_review_count vwrcount ON  vr.resource_id = vwrcount.resource_id
        LEFT OUTER JOIN resource_credentials rc ON  (vr.resource_id = rc.resource_id and rc.wf_state_id IN (11, 12, 13, 14, 200, 210))
        LEFT OUTER JOIN vendor_credentials vc ON  (vr.vendor_id = vc.vendor_id and rc.wf_state_id IN (11, 12, 13, 14, 200, 210))
	LEFT OUTER JOIN tmp_solr_ins ins on (ins.resource_id = vr.resource_id),
        vendor_hdr vh,
        tmp_solr_resource_ratings rat
        WHERE vr.resource_ind = 1
        AND vr.mkt_place_ind = 1
        AND vr.vendor_id = vh.vendor_id
        AND rat.resource_id = vr.resource_id
	and vh.wf_state_id in (3,34,26)
        and vr.wf_state_id = 6
        GROUP BY  vr.resource_id;
CREATE INDEX index_5 ON tmp_solr_matrix (resource_id);


CREATE TEMPORARY TABLE tmp_solr_lang
SELECT rl.resource_id, GROUP_CONCAT(DISTINCT lls.descr) AS langs
	FROM lu_languages_spoken lls,  resource_languages rl
	WHERE lls.id = rl.language_id GROUP BY rl.resource_id;
CREATE INDEX index_6 ON tmp_solr_lang (resource_id);

DROP TEMPORARY TABLE  IF EXISTS tmp_resource_avail;
CREATE TEMPORARY TABLE tmp_resource_avail
select r.resource_id as resource_id, if(l.type is null, 'unavailable', l.type) as time_zone,
if(r.mon_24_ind=1,'AT', if(r.mon_na_ind=1, 'N/A', concat(time_format(r.mon_start, '%h:%i%p'), '-', time_format(r.mon_end, '%h:%i%p')))) as avail_mon,
if(r.tue_24_ind=1,'AT', if(r.tue_na_ind=1, 'N/A', concat(time_format(r.tue_start, '%h:%i%p'), '-', time_format(r.tue_end, '%h:%i%p')))) as avail_tue,
if(r.wed_24_ind=1,'AT', if(r.wed_na_ind=1, 'N/A', concat(time_format(r.wed_start, '%h:%i%p'), '-', time_format(r.wed_end, '%h:%i%p')))) as avail_wed,
if(r.thu_24_ind=1,'AT', if(r.thu_na_ind=1, 'N/A', concat(time_format(r.thu_start, '%h:%i%p'), '-', time_format(r.thu_end, '%h:%i%p')))) as avail_thu,
if(r.fri_24_ind=1,'AT', if(r.fri_na_ind=1, 'N/A', concat(time_format(r.fri_start, '%h:%i%p'), '-', time_format(r.fri_end, '%h:%i%p')))) as avail_fri,
if(r.sat_24_ind=1,'AT', if(r.sat_na_ind=1, 'N/A', concat(time_format(r.sat_start, '%h:%i%p'), '-', time_format(r.sat_end, '%h:%i%p')))) as avail_sat,
if(r.sun_24_ind=1,'AT', if(r.sun_na_ind=1, 'N/A', concat(time_format(r.sun_start, '%h:%i%p'), '-', time_format(r.sun_end, '%h:%i%p')))) as avail_sun
from resource_schedule r left outer join lu_time_zones l
on r.time_zone_id = l.id;
CREATE INDEX index_3 ON tmp_resource_avail (resource_id);


CREATE TEMPORARY TABLE tmp_name
SELECT IF(LENGTH(TRIM(CONCAT(c.first_name, ' ', SUBSTRING(c.last_name,1, 1))))=0, 'Unknown', CONCAT(c.first_name, ' ', SUBSTRING(c.last_name,1, 1)))
	AS NAME, vr.contact_id AS contact_id FROM  contact c,  vendor_resource vr WHERE vr.contact_id = c.contact_id;
CREATE INDEX index_4 ON tmp_name (contact_id);
SELECT  concat('P', vr.resource_id) as id, vr.resource_id AS resource_id,
        tname.name as name,
        l.zip, trim(LOWER(l.city)) as city, l.state_cd AS state,
        IFNULL(l.gis_latitude, 0.0) AS lat, IFNULL(l.gis_longitude, 0.0) AS lng, vh.vendor_id AS vendor_id,
        c.title title,
        img.image AS image,
        lang.langs,
        s.skills,
        lpi.descr as primary_industry,
        com.business_name, com.member_since, com.overview as overview, com.yrs_of_experience,
        com.company_size, com.business_structure, com.warr_period_labor, com.warr_period_parts,
        com.free_estimate, com.ins_vehicle, com.ins_workman, com.ins_general,
        mx.total_so_completed, mx.provider_rating, mx.survey_count, mx.review_count, round(mx.review_rating, 2) as review_rating,
        mx.timeliness, mx.communication, mx.professionalism, mx.quality, mx.mvalue,
        mx.cleanliness, mx.overall, mx.license_count,
        "provider" AS doctype,
        avail.avail_mon,
        avail.avail_tue,
        avail.avail_wed,
        avail.avail_thu,
        avail.avail_fri,
        avail.avail_sat,
        avail.avail_sun,
        avail.time_zone,
        mx.ins_verified_general, mx.ins_verified_date_general,
        mx.ins_verified_auto, mx.ins_verified_date_auto,
        mx.ins_verified_workman, mx.ins_verified_date_workman
        FROM  vendor_resource vr
        LEFT OUTER JOIN tmp_resource_avail avail on (avail.resource_id = vr.resource_id)
        LEFT OUTER JOIN tmp_solr_matrix mx ON (vr.resource_id = mx.resource_id)
        LEFT OUTER JOIN vw_solr_provider_image img ON (vr.resource_id = img.resource_id),
        tmp_solr_company com, location l,
        vendor_hdr vh LEFT OUTER JOIN lu_primary_industry lpi on vh.primary_industry_id = lpi.primary_industry_id,
        contact c, tmp_solr_lang lang,
        tmp_solr_skills s, tmp_name tname
        WHERE vr.resource_ind = 1
        AND vr.mkt_place_ind = 1
        AND com.resource_id = vr.resource_id
        AND c.contact_id = vr.contact_id
        AND vr.vendor_id = vh.vendor_id
        AND vr.locn_id = l.locn_id
        AND lang.resource_id = vr.resource_id
        and s.resource_id = vr.resource_id
				and vh.wf_state_id in (3,34,26)
        and vr.wf_state_id = 6
        and tname.contact_id = vr.contact_id;