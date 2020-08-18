package com.newco.marketplace.dto.vo.buyerUploadScheduler;

import com.sears.os.vo.SerializableBaseVO;

public class BuyerRefTypeVO extends SerializableBaseVO {
	
	private static final long serialVersionUID = -8891072421730156478L;
	
	private int buyer_ref_type_Id;
	private int buyer_id;
	private String ref_type;
	private String ref_descr;

	public int getBuyer_ref_type_Id() {
		return buyer_ref_type_Id;
	}

	public void setBuyer_ref_type_Id(int buyer_ref_type_Id) {
		this.buyer_ref_type_Id = buyer_ref_type_Id;
	}

	public int getBuyer_id() {
		return buyer_id;
	}

	public void setBuyer_id(int buyer_id) {
		this.buyer_id = buyer_id;
	}

	public String getRef_type() {
		return ref_type;
	}

	public void setRef_type(String ref_type) {
		this.ref_type = ref_type;
	}

	public String getRef_descr() {
		return ref_descr;
	}

	public void setRef_descr(String ref_descr) {
		this.ref_descr = ref_descr;
	}

}
