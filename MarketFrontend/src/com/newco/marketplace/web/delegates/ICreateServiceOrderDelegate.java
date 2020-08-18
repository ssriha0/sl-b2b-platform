package com.newco.marketplace.web.delegates;

import java.util.List;
import java.util.Map;

import com.newco.marketplace.business.iBusiness.document.IDocumentBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.dto.vo.LocationVO;
import com.newco.marketplace.dto.vo.serviceorder.Contact;
import com.newco.marketplace.exception.DelegateException;
import com.newco.marketplace.exception.DuplicateUserException;
import com.newco.marketplace.web.dto.SODocumentDTO;
import com.newco.marketplace.web.dto.SOWContactLocationDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderAddFundsDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderCreateAccountDTO;
import com.newco.marketplace.web.dto.simple.CreateServiceOrderEditAccountDTO;
import com.newco.marketplace.web.dto.simple.CreditCardDTO;
import com.newco.marketplace.webservices.base.response.ProcessResponse;

public interface ICreateServiceOrderDelegate {
	
	/* get document List for given so Id*/
	public List<SODocumentDTO> getDocumentListBySOId(String soId, Integer categoryId, 
			                              Integer roleId, Integer userId) throws Exception;
	/* get photo document List for given so Id*/
	public List<SODocumentDTO> getPhotoDocListBySOId(String soId, Integer categoryId,
			                              Integer roleId, Integer userId) throws Exception;
	
	/* get temp document List for given simple buyer Id*/
	public List<SODocumentDTO> getTemporaryDocumentListBySimpleBuyerId(String simpleBuyerId, 
			                              Integer categoryId) throws Exception;
	
	/* insert so Document*/
	public ProcessResponse insertSODocument(DocumentVO documentVO) throws Exception;
	
	/* insert so Document temporarily*/
	public ProcessResponse insertTempSODocument(DocumentVO documentVO) throws Exception;
	
	/* delete so Document*/
	public ProcessResponse deleteSODocument(DocumentVO documentVO)throws Exception;
	
	/* delete temp so Document*/
	public ProcessResponse deleteTempSODocument(String simpleBuyerId, Integer docId) throws Exception;
	
	/* load account DTO for join now screen for simple buyer registration */
	public void loadAccount(CreateServiceOrderCreateAccountDTO accountDTO) throws DelegateException;
	
	/* Used to retrieve list of zip codes belonging to given state */
	public List<com.newco.marketplace.vo.provider.LocationVO> loadLocations(String stateCode) throws DelegateException;
	
	// used to check for blackout state and to save buyer leads info
	public boolean isBlackoutState(String stateCode) throws DelegateException;
	public void saveBuyerLead(CreateServiceOrderCreateAccountDTO buyerDTO) throws DelegateException;

	public SOWContactLocationDTO loadBuyerPrimaryLocation(Integer buyerId, Integer buyerResourceId) throws DelegateException;
	
	/* Used to persist simple buyer registration info */
	public CreateServiceOrderCreateAccountDTO saveSimpleBuyerRegistration(CreateServiceOrderCreateAccountDTO accountDTO) throws DelegateException, DuplicateUserException;
	
	
	public Map<String, Object> postSimpleBuyerServiceOrder(CreateServiceOrderAddFundsDTO addFundsDTO, Integer buyerId, String userName)throws DelegateException;
	
	public CreditCardDTO getActiveCreditCardDetails(Integer entityId) throws DelegateException;

	/**
	 * Description: Returns buyer resource info (including the resource's default address), when passed a buyer resource ID
	 * @param buyerResId
	 * @return <code>CreateServiceOrderEditAccountDTO</code>
	 */
	public CreateServiceOrderEditAccountDTO getBuyerResourceInfo(Integer buyerResId);

	/**
	 * Description: Updates buyer resource info (email, primary and secondary phone only), when passed a buyer resource ID
	 * @param buyerResId
	 */
	public void updateBuyerResourceInfo(CreateServiceOrderEditAccountDTO csoEditAccountDTO);
	
	public List<LocationVO> loadLocationsByResourceId(Integer buyerResId) throws DelegateException;
	public void saveLocation(LocationVO location, Integer buyerResId) throws DelegateException;
	public void deleteLocationByLocationId(Integer locId) throws DelegateException;	
	/**
	 * Loads the Contact object from the database and return it.
	 * 
	 * @param buyerId
	 * @param buyerResourceId
	 * @return The Contact object for the buyer
	 * @throws DelegateException
	 */
	public Contact loadBuyerPrimaryContact(Integer buyerId, Integer buyerResourceId) throws DelegateException;
	
	public boolean checkTaxPayerIdRequired(Integer buyerId, Double totalSpendLimit);
	
	public void saveTaxPayerId(Integer buyerId, String ein) throws DelegateException;
	
	public List<String> getBlackoutStates() throws DelegateException;
	
	public void saveDocuments(String simpleBuyerId, String soId, Integer entityId);
	
	public IDocumentBO getDocumentBO();
	
	public void saveSSN(Integer buyerId, String ssn) throws DelegateException;
	
	public void saveSSNId(Integer buyerId, String ssn, String dob) throws DelegateException;
	
	public void saveAltTaxId(Integer buyerId, String documentType, String documentNo, String country, String dob) throws DelegateException;

}
