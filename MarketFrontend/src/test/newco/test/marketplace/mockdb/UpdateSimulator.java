package test.newco.test.marketplace.mockdb;

import java.util.ArrayList;
import java.util.Random;



public class UpdateSimulator extends Thread
{
	static final int ONE_SECOND = 1000;

	public void run()
	{
		while(true)
		{
			System.out.println(getName() + " update");
			try
			{
				sleep(ONE_SECOND);
				updateServiceOrders();
			} catch (InterruptedException e)
			{
			}
			
			//System.out.println("Done!!");
		}
	}
	
	synchronized private void updateServiceOrders() 
	{
		//System.out.println("updateServiceOrders()");
		ServiceOrderDAO dao = new ServiceOrderDAO();
		ArrayList<ServiceOrder> list = dao.findAll();

		Random rand = new Random();
		if(rand.nextInt(100) < 5)
			addNewOrder(dao);
		
		updateRandomSent(list);
	}

	private void updateRandomSent(ArrayList<ServiceOrder> list)
	{
		ArrayList<ServiceOrder> sentList = new ArrayList<ServiceOrder>();
		
		for(ServiceOrder so : list)
		{
			if(so.getStatus() == ServiceOrderStatusDAO.ROUTED_INDEX)
				sentList.add(so);
		}
		
		if(sentList.size() == 0)
			return;
		
		Random rand = new Random();
		int index = rand.nextInt(sentList.size() +1);
		index--;
		if(index < 0)
			index = 0;
		ServiceOrder sent = sentList.get(index);
		if(sent != null)
		{
			int val = rand.nextInt(100);
			if(val < 20)
			{
				sent.setProvidersSentTo(sent.getProvidersSentTo() + 1);
				System.out.println("updateRandomSent(): altering providersSentTo count of order#" + sent.getServiceOrderId());								
			}
			else if(val < 50)
			{
				int max = sent.getProvidersSentTo();
				if(max > (sent.getProvidersDeclined() + sent.getProvidersConditionalAccept()))
				{
					sent.setProvidersDeclined(sent.getProvidersDeclined() + 1);
					System.out.println("updateRandomSent(): altering providersDeclined count of order#" + sent.getServiceOrderId());									
				}
			}
			else
			{
				int max = sent.getProvidersSentTo();
				if(max > (sent.getProvidersConditionalAccept() + sent.getProvidersDeclined()))
				{
					sent.setProvidersConditionalAccept(sent.getProvidersConditionalAccept() + 1);
					System.out.println("updateRandomSent(): altering providersConditionallyAccepted count of order#" + sent.getServiceOrderId());														
				}
			}
			
			ServiceOrder copy = new ServiceOrder(sent);
			
			list.remove(sent);
			list.add(copy);
		}
		
	}
	
	void addNewOrder(ServiceOrderDAO dao)
	{
		ArrayList<ServiceOrder> list = dao.findAll();
		Random rand = new Random();
		// Create a new order
		int id = 11000 + rand.nextInt(400);
		ServiceOrder newOrder = dao.createRandomServiceOrder(id, id + "");
		dao.save(newOrder);		
	}
	
	void updateSent(ServiceOrder so, Random rand)
	{
		
		int total, sum, val;
		
		total = so.getProvidersSentTo();
		sum = so.getProvidersDeclined() + so.getProvidersConditionalAccept();
		if(sum == total)
			return;
		
		val = rand.nextInt(100);
		if(val < 50)
			so.setProvidersDeclined(so.getProvidersDeclined() + 1);
		else
			so.setProvidersConditionalAccept(so.getProvidersConditionalAccept() + 1);
		
	}
}
