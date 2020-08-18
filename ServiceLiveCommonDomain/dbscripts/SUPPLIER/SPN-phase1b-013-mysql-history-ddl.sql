drop trigger if exists spnpfsh_ins_after;
drop trigger if exists spnpfsh_upd_after;

drop trigger if exists spnspsh_ins_after;
drop trigger if exists spnspsh_upd_after;

alter table spnet_provider_firm_st_history add INDEX FK_SPNET_PF_ST_HIST_SPN_ID (spn_id);

alter table spnet_serviceprov_st_history add INDEX FK_SPNET_SP_ST_HIST_SPN_ID (spn_id);


DELIMITER $$
CREATE TRIGGER spnpfsh_ins_after AFTER INSERT ON spnet_provider_firm_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_provider_firm_st_history (
			spn_id,
			provider_firm_id,
			modified_date,
			created_date,
			modified_by,
			provider_wf_state,
			app_submission_date,
			agreement_ind,
			opt_out_reason_code,
			opt_out_comment,
			reviewed_date,
			reviewed_by,
			archive_date,
			comments
          )
          VALUES  (
				NEW.spn_id,
				NEW.provider_firm_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NEW.app_submission_date,
				NEW.agreement_ind,
				NEW.opt_out_reason_code,
				NEW.opt_out_comment,
				NEW.reviewed_date,
				NEW.reviewed_by,
				NOW(),
				NEW.comments
	        )  ;
END
$$


CREATE TRIGGER spnpfsh_upd_after AFTER UPDATE ON spnet_provider_firm_state
FOR EACH ROW
BEGIN
      IF (NEW.spn_id != OLD.spn_id OR NEW.provider_firm_id != OLD.provider_firm_id OR NEW.provider_wf_state != OLD.provider_wf_state)
      THEN
          INSERT INTO spnet_provider_firm_st_history (
			spn_id,
			provider_firm_id,
			modified_date,
			created_date,
			modified_by,
			provider_wf_state,
			app_submission_date,
			agreement_ind,
			opt_out_reason_code,
			opt_out_comment,
			reviewed_date,
			reviewed_by,
			archive_date,
			comments
          )
          VALUES  (
				NEW.spn_id,
				NEW.provider_firm_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NEW.app_submission_date,
				NEW.agreement_ind,
				NEW.opt_out_reason_code,
				NEW.opt_out_comment,
				NEW.reviewed_date,
				NEW.reviewed_by,
				NOW(),
			    NEW.comments
	        )  ;
	 END IF;
 END
 $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER spnspsh_ins_after AFTER INSERT ON spnet_serviceprovider_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_serviceprov_st_history (
		    spn_id,
		    service_provider_id,
		    modified_date,
		    created_date,
		    modified_by,
		    provider_wf_state,
		    archive_date
          )
          VALUES  (
				NEW.spn_id,
				NEW.service_provider_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NOW()
	        )  ;
END

$$

CREATE TRIGGER spnspsh_upd_after AFTER UPDATE ON spnet_serviceprovider_state
FOR EACH ROW
BEGIN
 	IF (NEW.spn_id != OLD.spn_id OR NEW.service_provider_id != OLD.service_provider_id OR NEW.provider_wf_state != OLD.provider_wf_state)
   	THEN
          INSERT INTO spnet_serviceprov_st_history (
		    spn_id,
		    service_provider_id,
		    modified_date,
		    created_date,
		    modified_by,
		    provider_wf_state,
		    archive_date
          )
          VALUES  (
				NEW.spn_id,
				NEW.service_provider_id,
				NEW.modified_date,
				NEW.created_date,
				NEW.modified_by,
				NEW.provider_wf_state,
				NOW()
	        )  ;
    END IF;
 END
 $$
DELIMITER ;