package com.newco.marketplace.web.action.financemanager;

import com.newco.marketplace.web.action.base.SLFinanceManagerBaseAction;
import com.newco.marketplace.web.dto.FMOverviewHistoryTabDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public class FMHistoryAction extends SLFinanceManagerBaseAction implements Preparable,ModelDriven<FMOverviewHistoryTabDTO>
{
	private static final long serialVersionUID = 1L;

	FMOverviewHistoryTabDTO historyTabDTO = new FMOverviewHistoryTabDTO();
	
	public void prepare() throws Exception
	{
		createCommonServiceOrderCriteria();		
	}

	public String execute() throws Exception
	{
		String role = get_commonCriteria().getRoleType();
		
		initTransactionTable();
		
		return role;
	}
	
	private void initTransactionTable()
	{
/*		ArrayList<TransactionDTO> list = new ArrayList<TransactionDTO>();
		
		TransactionDTO transaction;
		
		transaction = new TransactionDTO("00000000", "10/25/07", "3:15am", "Withdraw", "123-3333-4444-444", "$10.00", "Pending", false, false);
		list.add(transaction);
		
		transaction = new TransactionDTO("00000001", "10/26/07", "9:15am", "Withdraw", "123-4444-4444-444", "$10.00", "Pending", false, false);
		list.add(transaction);
		
		transaction = new TransactionDTO("00000003", "11/25/07", "12:15am", "Withdraw", "123-555-4444-444", "($10.00)", "Failed", true, true);
		list.add(transaction);
		
		setAttribute("transactionList", list);*/
	}
	
	public FMOverviewHistoryTabDTO getModel()
	{
		return historyTabDTO;
	}
	
	public String exportToFile()
	{
		FMOverviewHistoryTabDTO dto = getModel();
	
		String role = get_commonCriteria().getRoleType();		
		return role;
	}

}
