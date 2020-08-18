alter table spnet_uploaded_document_state add column audited_by varchar(50) NULL ;

alter table spnet_uploaded_document_state add column audited_date timestamp NULL;

alter table spnet_uploaded_document_state add CONSTRAINT FK_SPN_UPL_STATE_AUDIT_BY FOREIGN KEY (audited_by) REFERENCES user_profile (user_name);   

alter table spnet_uploaded_doc_st_history add column audited_by varchar(50) NULL ;

alter table spnet_uploaded_doc_st_history add column audited_date timestamp NULL;

drop trigger spnwudsh_ins_after;

drop trigger spnwudsh_upd_after;

DELIMITER $$
CREATE TRIGGER spnwudsh_ins_after AFTER INSERT ON spnet_uploaded_document_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_uploaded_doc_st_history (
				spn_doc_id,
				prov_firm_upld_doc_id,
				buyer_id,
				prov_firm_id,
				doc_state_id,
				modified_by,
				created_date,
				modified_date,
				comments,
				page_no,
				archive_date
          )
          VALUES  (
				NEW	.spn_doc_id,
				NEW	.prov_firm_upld_doc_id,
				NEW	.buyer_id,
				NEW	.prov_firm_id,
				NEW	.doc_state_id,
				NEW	.modified_by,
				NEW	.created_date,
				NEW	.modified_date,
				NEW	.comments,
				NEW	.page_no,
				NOW()
	        )  ;
END
$$


CREATE TRIGGER spnwudsh_upd_after AFTER UPDATE ON spnet_uploaded_document_state
FOR EACH ROW
BEGIN
          INSERT INTO spnet_uploaded_doc_st_history (
				spn_doc_id,
				prov_firm_upld_doc_id,
				buyer_id,
				prov_firm_id,
				doc_state_id,
				modified_by,
				created_date,
				modified_date,
				comments,
				page_no,
				archive_date
          )
          VALUES  (
				NEW	.spn_doc_id,
				NEW	.prov_firm_upld_doc_id,
				NEW	.buyer_id,
				NEW	.prov_firm_id,
				NEW	.doc_state_id,
				NEW	.modified_by,
				NEW	.created_date,
				NEW	.modified_date,
				NEW	.comments,
				NEW	.page_no,
				NOW()
	        )  ;
 END
 $$
DELIMITER ;
