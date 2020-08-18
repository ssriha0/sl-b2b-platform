package test.newco.test.marketplace.mockdb;

import java.util.ArrayList;


public interface IServiceOrderSubStatusDAO
{
	public ArrayList<ServiceOrderSubStatus> findAll();
	public String findById(Integer id);
}
