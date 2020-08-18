/**
 * 
 */
package com.newco.marketplace.test;

import com.newco.marketplace.dto.vo.powerbuyer.ClaimVO;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.exception.core.DataServiceException;

/**
 * @author rambewa
 *
 */
public class PBClaimTest extends SLBase {

	/**
	 * 
	 */
	public PBClaimTest() {
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void testRequeSo() throws DataServiceException, BusinessServiceException{
		String soId="198-2478-8084-81";
		ClaimVO claimVO= new ClaimVO();
		claimVO.setResourceId(new Integer(3));
		claimVO.setSoId(soId);
		int status=_iSOClaimDao.unClaimSO(claimVO);
		display("status is " + status);
		//this.assertEquals("true", status);
		//boolean bstatus=this._iPowerBuyerBO.unClaimSO(claimVO);
		display("status is " );
	}	
}
