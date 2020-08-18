package com.servicelive.wallet.batch.mocks;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.servicelive.common.CommonConstants;
import com.servicelive.common.exception.DataServiceException;
import com.servicelive.wallet.batch.gl.dao.IGLDao;
import com.servicelive.wallet.batch.gl.vo.GLDetailVO;
import com.servicelive.wallet.batch.gl.vo.GLSummaryVO;
import com.servicelive.wallet.batch.gl.vo.GlProcessLogVO;

// TODO: Auto-generated Javadoc
/**
 * Class MockGLDao.
 */
public class MockGLDao implements IGLDao {

	/** glDao. */
	private IGLDao glDao;

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#createGLProcessLog(com.servicelive.wallet.batch.gl.vo.GlProcessLogVO)
	 */
	public Integer createGLProcessLog(GlProcessLogVO glProcessLogVO) {

		// return the dummy process id
		return 180;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getGLDetails(java.lang.Integer)
	 */
	public List<GLDetailVO> getGLDetails(Integer glProcessLogId) throws DataServiceException {


		GLDetailVO gldetail = new GLDetailVO();
		gldetail.setEntryId("1");
		gldetail.setNpsOrder("04037751");
		gldetail.setNpsUnit("04026");
		gldetail.setGlUnit("09930");
		gldetail.setGlDivision("0400");
		gldetail.setGlAccount("");
		return glDao.getGLDetails(glProcessLogId);
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#getLedgerSummary(java.util.Date, java.util.Date)
	 */
	public List<GLSummaryVO> getLedgerSummary(Date startDate, Date endDate) {

		ArrayList<GLSummaryVO> summaries = new ArrayList<GLSummaryVO>();
		GLSummaryVO summary = new GLSummaryVO();

		// this is just a dummy record to keep the flow moving
		summary.setGlDivision("Test Division");
		summary.setModifiedDate(Calendar.getInstance().getTime());
		summary.setCreatedDate(Calendar.getInstance().getTime());
		summary.setCreditInd("1");
		summary.setEntryTypeId(1);
		summary.setGlTAccountNumber("2828282828");
		summary.setTransactionAmount(24.00);
		summary.setLedgerEntityId(22L);
		summary.setLedgerEntityTypeId((int) CommonConstants.LEDGER_ENTITY_TYPE_BUYER);
		summary.setTransactionId(118288);
		summary.setLedgerEntryId(3737378829L);
		summaries.add(summary);

		return summaries;
	}

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.batch.gl.dao.IGLDao#runGLDetails(java.lang.Integer)
	 */
	public void runGLDetails(Integer glProcessLogId) throws DataServiceException {

	// TODO Auto-generated method stub

	}

	/**
	 * setGlDao.
	 * 
	 * @param glDao 
	 * 
	 * @return void
	 */
	public void setGlDao(IGLDao glDao) {

		this.glDao = glDao;
	}

	public List<GLSummaryVO> getLedgerSummary(Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean updateLedgerEntryGLProcessed(List<GLSummaryVO> glSummaryList) {
		// TODO Auto-generated method stub
		return false;
	}

}
