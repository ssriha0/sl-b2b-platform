package com.newco.marketplace.dto.vo.logging;

import com.sears.os.vo.ABaseVO;


public class SoChangeTypeVo extends ABaseVO {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 3744777339648634783L;
	String changeType = null;
    Integer changeId = null;
    String changeDescription = null;
    
    @Override
	public String toString() {
        return ("<SoChangeVo>" 
                + changeType+ "   |  "
                + changeId + "   |  "
                + changeDescription + "   |  "
                + "</SoChangeVo>");
    } // soChangeTypeVo
}//end class SecretQuectionVO