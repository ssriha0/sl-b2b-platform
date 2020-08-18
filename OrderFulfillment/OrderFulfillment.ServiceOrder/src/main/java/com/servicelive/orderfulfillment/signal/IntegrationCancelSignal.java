package com.servicelive.orderfulfillment.signal;

import com.servicelive.orderfulfillment.common.ServiceOrderNoteUtil;
import com.servicelive.orderfulfillment.domain.SOElement;
import com.servicelive.orderfulfillment.domain.SONote;
import com.servicelive.orderfulfillment.domain.ServiceOrder;

/**
 * User: Yunus Burhani
 * Date: Jun 18, 2010
 * Time: 2:34:26 PM
 */
public class IntegrationCancelSignal extends Signal {
    protected ServiceOrderNoteUtil noteUtil;

    @Override
	protected void update(SOElement soe, ServiceOrder so){
        SONote note = noteUtil.getNewNote(so, "WSCancel", null);
        serviceOrderDao.save(note);
    }
}
