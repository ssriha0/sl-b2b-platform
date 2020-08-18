package com.servicelive.spn.buyer.membermanagement;


import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN;
import static com.servicelive.spn.constants.SPNActionConstants.NOT_LOGGED_IN_AS_BUYER;
import static com.servicelive.spn.constants.SPNActionConstants.ROLE_ID_PROVIDER;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.servicelive.domain.userprofile.User;
import com.servicelive.spn.buyer.common.SPNCreateAction;
import com.servicelive.spn.buyer.network.SPNMMNetworkDTO;
import com.servicelive.spn.buyer.network.SPNMemberManagerModel;
import com.servicelive.spn.buyer.network.SPNMemberManagerProviderDTO;
/**
 *
 * 
 *
 */

public class SPNMemberManagerAction extends SPNCreateAction implements Preparable, ModelDriven<SPNMemberManagerModel>
{
	private static final long serialVersionUID = -9086828179014834481L;
	
	private SPNMemberManagerModel model = new SPNMemberManagerModel();
	

	
	/**
	 *
	 * @return String
	 */
	@Override
	public String display()
	{
		User loggedInUser = getLoggedInUser();
		model = getModel();


		if (loggedInUser == null)
		{
			return NOT_LOGGED_IN;
		}
		else if (loggedInUser.getRole() == null || loggedInUser.getRole().getId() == null || loggedInUser.getRole().getId().intValue() == ROLE_ID_PROVIDER.intValue())
		{
			return NOT_LOGGED_IN_AS_BUYER;
		}


		initTabDisplay();
		
		initMockProviderResults();
		
		return SUCCESS;
	}


	private void initMockProviderResults()
	{
		List<SPNMemberManagerProviderDTO> providerResults = new ArrayList<SPNMemberManagerProviderDTO>();
		SPNMemberManagerProviderDTO dto;
		for(int i=0 ; i<12 ; i++)
		{
			dto = new SPNMemberManagerProviderDTO();
			dto.setCompanyStatus("Approved");
			dto.setProviderStatus("Market Ready");
			dto.setVendorResourceName("Reed Richards");
			dto.setVendorID(Integer.valueOf(1000 + i * 1000 + (i*100) + i*32));
			dto.setVendorResourceID(Integer.valueOf(1000 * i));
			
			List<SPNMMNetworkDTO> networkList = new ArrayList<SPNMMNetworkDTO>();
			for(int j=0 ; j<3 ; j++)
			{
				SPNMMNetworkDTO network = new SPNMMNetworkDTO();
				network.setGroupId(Integer.valueOf(j));
				network.setGroupName("Bronze" + j);
				network.setNetworkName("Compuer Repair" + j);
				network.setNetworkState("approved");
				networkList.add(network);
			}
			dto.setNetworkList(networkList);
			providerResults.add(dto);			
		}
		
		getModel().setProviderResults(providerResults);
	}
	
	public SPNMemberManagerModel getModel()
	{
		return model;
	}
	
	@Override
	public void prepare() throws Exception
	{
		//intentionally blank
	}
	

}
