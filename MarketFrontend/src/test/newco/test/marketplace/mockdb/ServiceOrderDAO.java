package test.newco.test.marketplace.mockdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;




public class ServiceOrderDAO
{
	private static ArrayList<ServiceOrder> serviceOrderList = null;

	private static UpdateSimulator sim=null;
	
	public ServiceOrderDAO()
	{
		initList();
		//startUpdateSimulator();
	}
	
	private void startUpdateSimulator()
	{
		if(sim != null)
			return;
		
		sim = new UpdateSimulator();
		sim.start();
	}
	private void initList()
	{
		if(serviceOrderList != null)
			return;
		
		serviceOrderList = new ArrayList<ServiceOrder>();
		
		int i;
		Integer id;
		String title;
		for(i=0 ; i<100 ; i++)
		{
			id = i;
			title = id + "";
			serviceOrderList.add(createRandomServiceOrder(id, title));
		}
	}
	
	public ServiceOrder createRandomServiceOrder(Integer id, String title)
	{
		Random rand = new Random();
		ServiceOrder order = new ServiceOrder();
		int tmp = 100000 + id;
		order.setServiceOrderId(tmp + "");
		order.setTitle("Install Ceiling fans " + title);
		
		String phone = "847123456";
		String randDigit = (0 + rand.nextInt(9)) + "";
		phone += randDigit;
		order.setPhoneNumber(phone);
		
		order.setCity("Chicago");

		
		order.setUserID(rand.nextInt(10) + "");
		
		order.setState("IL");
		
		int zip = 60100 + rand.nextInt(9);
		order.setZip(zip + "");
		
		order.setLocationId(rand.nextInt(10) + 1);
		
		order.setStatus(rand.nextInt(11) + 1);
		
		order.setBuyerName("SEARS");
		order.setProviderName("SEARS HOLDINGS");
		Date date = new Date();
		if(rand.nextInt(100) > 20)
		{
			int daysAhead = rand.nextInt(30);
			int dom = date.getDate();
			int range = 30 - dom;
			if(daysAhead < range)
			{
				date.setDate(dom + daysAhead);
			}
			else
			{
				date.setDate(daysAhead - range);
				date.setMonth(date.getMonth() + 1);			
			}
			date.setHours(rand.nextInt(24));			
		}
		order.setServiceOrderDate(date);
		
		ServiceOrderStatus status;
		
		if(rand.nextInt(2) == 1)
			setSubStatusForStatus(order);
	
		/*if(order.getStatus() == ServiceOrderStatus.ACCEPTED)
		{
			order.setSubStatus(rand.nextInt(ServiceOrderSubStatus.MAX_COUNT) + 1);
		}*/
		
		int limit = (rand.nextInt(30)) * 500;
		order.setSpendLimit(limit);
		
		// Note
		String note = "Note:" + order.getServiceOrderId();
		int numBlahs = rand.nextInt(20);
		for(int i=0 ; i<numBlahs ; i++)
			note += " blah";			
		order.setNote(note);
		
		// Provider responses
		int totalProviderCount = rand.nextInt(20);
		int numDeclined = (totalProviderCount * rand.nextInt(20)) /100;
		int numConditional = ((totalProviderCount - numDeclined) * rand.nextInt(50))/100;
		order.setProvidersSentTo(totalProviderCount);
		order.setProvidersDeclined(numDeclined);
		order.setProvidersConditionalAccept(numConditional);
		
		return order;
	}
	
	private void setSubStatusForStatus(ServiceOrder serviceOrder)
	{
		Random rand = new Random();
		Integer subStatus = null;
		switch(serviceOrder.getStatus().intValue())
		{
			case 1: subStatus = 1 + rand.nextInt(2);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 2:	subStatus = 3 + rand.nextInt(2);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 3:	subStatus = 3; 
					serviceOrder.setSubStatus(subStatus);
					break;
			case 4:	subStatus = 30 + rand.nextInt(2);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 5:	subStatus = 5 + rand.nextInt(9);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 6:	subStatus = 5 + rand.nextInt(11);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 7:	subStatus = 16 + rand.nextInt(11);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 8:	subStatus = 27 + rand.nextInt(2);
					serviceOrder.setSubStatus(subStatus);
					break;
			case 9:	// NO Sub Status for Closed
					break;
			case 10:subStatus = 29 + rand.nextInt(3);
					serviceOrder.setSubStatus(subStatus);	
					break;		
		}
	}
	public ArrayList<ServiceOrder> findAll()
	{
		return serviceOrderList;
	}
	
	public void save(ServiceOrder so)
	{
		if(so != null)
			serviceOrderList.add(so);
	}	
}
