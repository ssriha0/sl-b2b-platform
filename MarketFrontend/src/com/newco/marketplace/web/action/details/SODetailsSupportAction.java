package com.newco.marketplace.web.action.details;

import com.newco.marketplace.constants.Constants;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.web.action.base.SLDetailsBaseAction;
import com.newco.marketplace.web.delegates.ISODetailsDelegate;
import com.newco.marketplace.web.dto.ServiceOrderNoteDTO;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
import com.sears.os.service.ServiceConstants;

/**
 * 
 * $Revision: 1.8 $ $Author: glacy $ $Date: 2008/04/26 01:13:47 $
 */

/*
 * Maintenance History
 * $Log: SODetailsSupportAction.java,v $
 * Revision 1.8  2008/04/26 01:13:47  glacy
 * Shyam: Merged I18_Fin to HEAD.
 *
 * Revision 1.6.28.1  2008/04/23 11:41:34  glacy
 * Shyam: Merged HEAD to finance.
 *
 * Revision 1.7  2008/04/23 05:19:31  hravi
 * Shyam: Reverting to build 247.
 *
 * Revision 1.6  2007/12/13 23:53:22  mhaye05
 * replaced hard coded strings with constants
 *
 */
public class SODetailsSupportAction extends SLDetailsBaseAction implements Preparable, OrderConstants, ServiceConstants, ModelDriven {
	
	private static final long serialVersionUID = 10002;// arbitrary number to get rid
												// of warning
	
	private ServiceOrderNoteDTO soNoteDTO = new ServiceOrderNoteDTO();

	public SODetailsSupportAction(ISODetailsDelegate delegate) {
		this.detailsDelegate = delegate;
	}

	/**
	 * Description of the Method
	 * 
	 * @exception Exception
	 *                Description of the Exception
	 */
	public void prepare() throws Exception {
		
		createCommonServiceOrderCriteria();
		ServiceOrderNoteDTO soNoteDTO = (ServiceOrderNoteDTO)getSession().getAttribute(Constants.SESSION.SOD_NOTE_DTO);
		if (soNoteDTO == null)
			soNoteDTO = (ServiceOrderNoteDTO)getModel();
		else
			setModel(soNoteDTO);
		getSession().removeAttribute(Constants.SESSION.SOD_NOTE_DTO);
	}

	public String execute() throws Exception {
		
		return SUCCESS;
	}

	public void setModel(Object x){
		soNoteDTO = (ServiceOrderNoteDTO) x;	
	}
	
	
	public Object getModel() {
		return soNoteDTO;
	}
	
}
