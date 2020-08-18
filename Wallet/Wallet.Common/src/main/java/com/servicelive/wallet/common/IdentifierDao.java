package com.servicelive.wallet.common;

import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;

import com.servicelive.common.ABaseDao;

// TODO: Auto-generated Javadoc
/**
 * The Class IdentifierDao.
 */
public class IdentifierDao implements IIdentifierDao {

	/* (non-Javadoc)
	 * @see com.servicelive.wallet.common.IIdentifierDao#getNextIdentifier(java.lang.String)
	 */
	public Long getNextIdentifier(String key) {

		Long nextId = null;
		if (key != null && StringUtils.isNotEmpty(key)) {

			// Current time
			GregorianCalendar gc2 = new GregorianCalendar();
			// today at midnight
			GregorianCalendar gc1 = new GregorianCalendar(gc2.get(1), gc2.get(2), gc2.get(5));
			// Subtract the two. 
			java.util.Date d1 = gc1.getTime();
			java.util.Date d2 = gc2.getTime();
			long l1 = d1.getTime();
			long l2 = d2.getTime();
			nextId = l2 - l1;
			
			if (key.equals(LEDGER_TRANS_ID)) nextId = nextId + 100040000;
			else if (key.equals(LEDGER_ENTRY_ID)) nextId = nextId + 100040000;
			else if (STAN_ID.equals(key)) {
				long max = 99999 + 1;
				double next = nextId;
				nextId = (long) (next % max);
			}
		}
		return nextId;
	}
}
