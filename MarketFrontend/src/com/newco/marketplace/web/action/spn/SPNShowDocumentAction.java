package com.newco.marketplace.web.action.spn;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.newco.marketplace.business.iBusiness.spn.ISelectProviderNetworkBO;
import com.newco.marketplace.dto.vo.spn.SPNDocumentVO;
import com.newco.marketplace.interfaces.SPNConstants;
import com.newco.marketplace.web.action.base.SLBaseAction;

/**
 * The action class is used to display the Buyer agreement documents in the Buyer Agreement Modal popup
 */
public class SPNShowDocumentAction extends SLBaseAction implements SPNConstants {

	private static final long serialVersionUID = -2149257391267980098L;
	
	private ISelectProviderNetworkBO spnCreateUpdateBO;
	 private static final Logger logger = Logger.getLogger(SPNBuyerAgreeModalAction.class.getName());
	 
	 public SPNShowDocumentAction(ISelectProviderNetworkBO spnCreateUpdateBOArg){
			this.spnCreateUpdateBO = spnCreateUpdateBOArg;			
	 }
	
	/* (non-Javadoc)
	 * @see com.opensymphony.xwork2.ActionSupport#execute()
	 * The method retrieves the document contents in bytes and displays the same in the jsp
	 * using StreamReader.
	 */
	public String execute() throws Exception {
		SPNDocumentVO documentvo=new SPNDocumentVO();
		String documentID = getParameter(SPNConstants.DOCUMENT_ID);
		documentID = StringUtils.isBlank(documentID) ? "-1" : documentID;
		int docId=Integer.decode(documentID);
		List<SPNDocumentVO> resultList=  new ArrayList<SPNDocumentVO>();
			try{			
				resultList=spnCreateUpdateBO.getSPNBuyerAgreeModalDocument(docId);
				documentvo=(SPNDocumentVO)resultList.get(ZERO);
				InputStream in = new ByteArrayInputStream(documentvo.getDocumentContent());
				ServletOutputStream outs = ServletActionContext.getResponse().getOutputStream();
				ServletActionContext.getResponse().setContentType("text/html");
				//ServletActionContext.getResponse().setContentType("text/plain");
				int bit = BIT_VAL;				
				while ((bit) >= 0) {
					bit = in.read();
					if(bit != -1){
						outs.write(bit);
					}
				}
				outs.flush();
				outs.close();
				in.close();
			}catch(Exception e){
				logger.error("Error returned trying to get Document Content for the document ID:" + documentID,e);
			}
		return SUCCESS;
		
	}
	public ISelectProviderNetworkBO getSpnCreateUpdateBO() {
		return spnCreateUpdateBO;
	}
	public void setSpnCreateUpdateBO(ISelectProviderNetworkBO spnCreateUpdateBO) {
		this.spnCreateUpdateBO = spnCreateUpdateBO;
	}

}
