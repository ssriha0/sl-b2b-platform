package com.newco.batch.vendor;

import java.util.List;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.dto.vo.provider.VendorResource;
import com.newco.marketplace.persistence.iDao.provider.IUserProfileDao;
import com.newco.marketplace.persistence.iDao.vendor.VendorResourceDao;
import com.newco.marketplace.utils.CryptoUtil;
import com.newco.marketplace.vo.provider.UserProfile;

/**
 * $Revision: 1.4 $ $Author: glacy $ $Date: 2008/04/26 00:40:39 $
 */
public class EncryptVendorInfoBatchProcessor extends ABatchProcess {
	
    private VendorResourceDao vrDao;
    private IUserProfileDao upDao;

    @Override
	public int execute() {

        try {
        	
        	VendorResource vo = new VendorResource();
        	vo.setResourceId(null);
            List vendorList = vrDao.queryList(vo);

            for(int i=0; i< vendorList.size(); i++) {
            	VendorResource vr = (VendorResource) vendorList.get(i);

            	String ssn = vr.getSsn();
            	if (ssn != null && !ssn.equals("")) {
            		// encrypt ssn
            		String encryptedSSN = CryptoUtil.encryptKeyForSSNAndPlusOne(ssn);
            		vr.setSsn(encryptedSSN);
            		vrDao.update(vr);
            		vr = vrDao.query(vr);   
            	}
            }
            
        	
        	// update passwords
        	UserProfile profile = new UserProfile();
        	profile.setUserName(null);
            List profileList = upDao.queryList(profile);

            for(int i=0; i< profileList.size(); i++) {
            	UserProfile userProfile = (UserProfile) profileList.get(i);
            	String password = userProfile.getPassword();
            	if (password != null && !password.equals("")) {
            		// encrypt password
            		String encryptedPassword = CryptoUtil.encrypt(password);
            		userProfile.setPassword(encryptedPassword);
            		upDao.update(userProfile);
            		userProfile = upDao.query(userProfile);
            	}
            }        	
            
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return (0);
    }

    @Override
    public void setArgs(String[] args) {
    	// do nothing
    }

    
    @Override
	protected void setUp() throws Exception {
      super.setUp();
	  vrDao = (VendorResourceDao)ctx.getBean("vendorResourceDao");
	  upDao = (IUserProfileDao) ctx.getBean("iUserProfileDao");
    }

	/**
	 * @return the vrDao
	 */
	public VendorResourceDao getVrDao() {
		return vrDao;
	}

	/**
	 * @param vrDao the vrDao to set
	 */
	public void setVrDao(VendorResourceDao vrDao) {
		this.vrDao = vrDao;
	}

	/**
	 * @return the upDao
	 */
	public IUserProfileDao getUpDao() {
		return upDao;
	}

	/**
	 * @param upDao the upDao to set
	 */
	public void setUpDao(IUserProfileDao upDao) {
		this.upDao = upDao;
	}
    
}
/* Maintenance History
 * $Log: EncryptVendorInfoBatchProcessor.java,v $
 * Revision 1.4  2008/04/26 00:40:39  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.2.6.1  2008/04/23 11:42:23  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.3  2008/04/23 05:01:33  hravi
 * Reverting to build 247.
 *
 * Revision 1.2  2008/02/26 18:21:02  mhaye05
 * Merged Iteration 17 Branch into Head
 *
 * Revision 1.1.18.1  2008/02/25 15:50:10  mhaye05
 * removed System out println's
 *
 */
