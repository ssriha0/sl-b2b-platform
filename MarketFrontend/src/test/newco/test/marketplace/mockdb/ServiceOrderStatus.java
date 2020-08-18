package test.newco.test.marketplace.mockdb;

public class ServiceOrderStatus {
	
	private Integer id;
	private String name;
	
	public static final Integer SENT = 1;
	public static final Integer ACCEPTED = 2;
	public static final Integer DRAFT = 3;
	public static final Integer PROBLEM = 4;
	public static final Integer INACTIVE = 5;
	
	public ServiceOrderStatus(Integer id, String name)
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
