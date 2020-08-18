package com.servicelive.spn.buyer.membermanagement;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.servicelive.domain.buyer.Buyer;
import com.servicelive.domain.buyer.BuyerServiceProNote;
import com.servicelive.domain.provider.ServiceProvider;
import com.servicelive.spn.dao.buyer.BuyerServiceProviderNoteDao;
import com.servicelive.spn.services.buyer.BuyerNotesService;


public class BuyerNotesServiceTest {

	private BuyerServiceProviderNoteDao providerNoteDao;
	private BuyerServiceProNote expectedNote;
	 
	@Test
	public void testPersistServiceProNote(){
		Integer buyerId = 1000;
		Integer serviceProviderId = 10254;
		String comments = "Testing Add Note";
		String modifiedBy = "jizqui3@searshc.com";

		BuyerNotesService service = new BuyerNotesService();
		providerNoteDao = mock(BuyerServiceProviderNoteDao.class);
		service.setBuyerServiceProviderNoteDao(providerNoteDao);
		
		BuyerServiceProNote input = new BuyerServiceProNote();
		 try {
			 input.setId(null);
			 input.setBuyerId(new Buyer(buyerId));
			 input.setServiceProId(new ServiceProvider(serviceProviderId));
			 input.setComments(comments);
			 input.setModifiedBy(modifiedBy);
			 expectedNote = input;
			 
			 when(getBuyerServiceProviderNoteDao().persistServiceProNote(input)).thenReturn(expectedNote);
			
			 service.persistServiceProNote(buyerId, serviceProviderId, modifiedBy, comments);
		} catch (Exception e) {
			e.printStackTrace();
		}
				Assert.assertEquals(input, expectedNote);
	}
	
	
	private BuyerServiceProviderNoteDao getBuyerServiceProviderNoteDao() {
		return this.providerNoteDao;
	}

}