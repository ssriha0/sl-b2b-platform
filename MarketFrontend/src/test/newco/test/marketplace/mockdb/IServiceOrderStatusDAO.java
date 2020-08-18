package test.newco.test.marketplace.mockdb;

import java.util.ArrayList;


public interface IServiceOrderStatusDAO {
	public ArrayList<ServiceOrderStatus> findAll();
	public ArrayList<ServiceOrderStatusAndSubStatus> findAllStatuses();
	public String getStatusStringById(Integer id);
}
