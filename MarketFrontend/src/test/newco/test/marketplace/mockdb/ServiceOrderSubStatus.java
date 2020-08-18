package test.newco.test.marketplace.mockdb;

public class ServiceOrderSubStatus {
	
	private Integer id;
	private String name;
	
	public static final Integer ABANDONED_WORK = 1;
	public static final Integer ADDITIONAL_PART_REQUIRED = 2;
	public static final Integer ADDITIONAL_WORK_REQUIRED= 3;
	public static final Integer CANCELLED_BY_BUYER_SIDE= 4;
	public static final Integer CANCELLED_BY_END_CUSTOMER= 5;
	public static final Integer CANCELLED_BY_PROVIDER_SIDE= 6;
	public static final Integer END_CUSTOMER_NO_SHOW= 7;
	public static final Integer FUNDS_TRANSFERRED= 8;
	public static final Integer INCOMPLETE_STATEMENT_OF_WORK= 9;
	public static final Integer JOB_DONE_TO_BE_COMPLETED_FOR_PAYMENT= 10;
	public static final Integer NO_COMMUNICATION_OR_NOTES= 11;
	public static final Integer OUT_OF_SCOPE= 12;
	public static final Integer PART_BACK_ORDERED= 13;
	public static final Integer PART_ON_ORDER= 14;
	public static final Integer PART_RECEIVED_HOLD_FOR_PICKUP= 15;
	public static final Integer PART_RECEIVED_BY_END_CUSTOMER= 16;
	public static final Integer PART_RECEIVED_BY_TEAM_MEMBER= 17;
	public static final Integer PART_SHIPPED= 18;
	public static final Integer PROPERTY_DAMAGE= 19;
	public static final Integer MAX_COUNT= 20;
	
	
	public ServiceOrderSubStatus(Integer id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
