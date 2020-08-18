package com.newco.marketplace.persistence.daoImpl.provider;

import java.util.List;

import org.apache.log4j.Logger;

import com.newco.marketplace.exception.DataServiceException;
import com.newco.marketplace.persistence.iDao.provider.IVendorNotesDao;
import com.newco.marketplace.vo.provider.VendorNotesVO;
import com.sears.os.dao.impl.ABaseImplDao;

public class VendorNotesDaoImpl extends ABaseImplDao implements IVendorNotesDao {

	private static final Logger localLogger = Logger.getLogger(VendorNotesDaoImpl.class.getName());
	
	public void insert(VendorNotesVO vo) throws DataServiceException{
		Integer result = null;

		try {
			localLogger.info("inserting the value of the resource values into vendor notes for vendor id--------"+vo.getVendorId());
			result =(Integer)insert("vendorNotes.insert", vo);
			localLogger.info("Done inserting into the table vendor notes !!!!!!!!!!!!!!!!");
			
		}
		catch (Exception ex) {
			localLogger.error("[VendorNotesImpl.insert - Exception] "
                + ex.getMessage());
			
			throw new DataServiceException("[VendorNotesImpl.insert - Exception] ", ex);
		}

	}

	public VendorNotesVO query(VendorNotesVO vo) throws DataServiceException{
		VendorNotesVO vNotes=null;
    	try {
    		localLogger.info("querring for the vendor notes table for note id ***************"+vo.getNoteId());
    		vNotes= (VendorNotesVO) queryForObject("vendorNotes.query", vo);
    		localLogger.info("Done querrying from the table vendor notes !!!!!!!!!!!!!!!!");
    	}
    	catch(Exception ex) {
			localLogger.error("[VendorNotesImpl.query - Exception] "+ex.getMessage());
			throw new DataServiceException("[VendorNotesImpl.query - Exception]", ex);  
			}
		return vNotes;
	}

	public List queryList(VendorNotesVO vo) throws DataServiceException{
		List list = null;
    	try {
    		localLogger.info("querrying for the vendor notes for the list of the vendor id--------"+vo.getVendorId());
    		list =  queryForList("vendorNotes.queryList", vo);
    		localLogger.info("Done querrying for list from the table vendor notes !!!!!!!!!!!!!!!!");
    	}
    	catch (Exception ex) {
    		localLogger.error("[VendorNotesImpl.queryList - Exception] "
                + ex.getMessage());       
    		throw new DataServiceException("[VendorNotesImpl.queryList - Exception] ", ex);
    	}
        
    	return list;
	}

	public void update(VendorNotesVO vo) throws DataServiceException{
		Integer result = null;
    	try {
    		
    		localLogger.info("Updating the vendor notes table where the value of the note id is----"+vo.getNoteId());
    		result = (Integer)update("vendorNotes.update", vo);
    		localLogger.info("Done updating into the table vendor notes !!!!!!!!!!!!!!!!");
    		 
		 } catch (Exception ex) 
		 {
			 localLogger.error("[VendorNotesImpl.update - Exception] "
                + ex.getMessage());
			
			 throw new DataServiceException("[VendorNotesImpl.update - Exception] ", ex);
		 }
	}

}
