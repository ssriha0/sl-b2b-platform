/**
 *
 */
package com.newco.batch.vendor;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.newco.batch.ABatchProcess;
import com.newco.marketplace.dto.vo.CryptographyVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.MPConstants;
import com.newco.marketplace.persistence.iDao.provider.IVendorHdrDao;
import com.newco.marketplace.persistence.iDao.provider.IW9RegistrationDao;
import com.newco.marketplace.util.Cryptography;
import com.newco.marketplace.vo.provider.VendorHdr;

/**
 * @author hoza
 * This ONE Time run Batch Job would  fix ISSUE like Wrongly stored ENCrypted on W9 as well ans any unencrypted EIN found on vendo_hdr which are not fixed on W9
 * After this JOB run in order to replace the good values from the back up table into the W9 table.... please run the DB script 
 */
public class EncryptVendorEinBatchProcessor  extends ABatchProcess {

	private static final Logger logger = Logger
			.getLogger(EncryptVendorEinBatchProcessor.class);
	private IVendorHdrDao vhdrdao;
	private Cryptography cryptography;
	private IW9RegistrationDao w9RegistrationDao;


	@Override
	protected void setUp() throws Exception {
		 super.setUp();
		 vhdrdao = (IVendorHdrDao)ctx.getBean("vendorHdrDaoImpl");
		  cryptography = (Cryptography) ctx.getBean("cryptography");
		  w9RegistrationDao = (IW9RegistrationDao )ctx.getBean("w9RegistrationDaoImpl");
	}

	/**
	 * @return the vhdrdao
	 */
	public IVendorHdrDao getVhdrdao() {
		return vhdrdao;
	}

	/**
	 * @param vhdrdao the vhdrdao to set
	 */
	public void setVhdrdao(IVendorHdrDao vhdrdao) {
		this.vhdrdao = vhdrdao;
	}

	/**
	 * @return the cryptography
	 */
	public Cryptography getCryptography() {
		return cryptography;
	}

	/**
	 * @param cryptography the cryptography to set
	 */
	public void setCryptography(Cryptography cryptography) {
		this.cryptography = cryptography;
	}


	/* (non-Javadoc)
	 * @see com.newco.batch.ABatchProcess#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int execute() throws BusinessServiceException {
		int encCount = 0;
		try {
			
			VendorHdr vo = new VendorHdr();
			vo.setVendorId(null);
			List vendorList = vhdrdao.queryList(vo);

			for (int i = 0; i < vendorList.size(); i++) {
				VendorHdr vh = (VendorHdr) vendorList.get(i);

				String ein = vh.getEinNo();
				if(!StringUtils.isBlank(ein)) {

					try {
					CryptographyVO cryptographyVO = new CryptographyVO();
					cryptographyVO.setInput(ein);
					cryptographyVO.setKAlias(MPConstants.ENCRYPTION_KEY);
					cryptographyVO = cryptography.decryptKey(cryptographyVO);
					String einNo = cryptographyVO.getResponse();
					if(einNo == null) {
						//if exception means need to encrypt
						CryptographyVO resultCVO = new CryptographyVO();
						CryptographyVO cryptographyVO1 = new CryptographyVO();
						cryptographyVO1.setInput(ein);
						cryptographyVO1.setKAlias(MPConstants.ENCRYPTION_KEY);
						resultCVO = cryptography.encryptKey(cryptographyVO);
						if(resultCVO != null && resultCVO.getResponse() != null) {

							VendorHdr updatevo = new VendorHdr();
							updatevo.setEinNo(resultCVO.getResponse());
							updatevo.setVendorId(vh.getVendorId());
							vhdrdao.updateEIN(updatevo);
							encCount++;

						}
					}

					//einNo = ProviderConstants.COMPANY_TAX_PAYER_ID_MASK+einNo.substring(5);
					}catch(Exception e) {
						//swallow this exception and move on
						logger.error(" exception occurred.. swalloing it  you may need to look at if its sever "+e.getMessage());

					}


				}

			}
			logger.info("Total EIN encrypted are = " +  encCount);


		} catch (Exception e) {
			e.printStackTrace();
		}
		return encCount;
	}

	
	/* (non-Javadoc)
	 * @see com.newco.batch.ABatchProcess#setArgs(java.lang.String[])
	 */
	@Override
	public void setArgs(String[] args) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the w9RegistrationDao
	 */
	public IW9RegistrationDao getW9RegistrationDao() {
		return w9RegistrationDao;
	}

	/**
	 * @param registrationDao the w9RegistrationDao to set
	 */
	public void setW9RegistrationDao(IW9RegistrationDao registrationDao) {
		w9RegistrationDao = registrationDao;
	}



}
