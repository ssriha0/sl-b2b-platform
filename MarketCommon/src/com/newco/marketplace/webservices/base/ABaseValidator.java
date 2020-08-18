package com.newco.marketplace.webservices.base;

import java.util.List;

import com.newco.marketplace.webservices.base.response.ValidatorResponse;
import com.sears.os.service.ServiceConstants;
import com.sears.os.vo.SerializableBaseVO;

/**
 * $Revision: 1.7 $ $Author: glacy $ $Date: 2008/04/26 00:51:53 $
 *
 */
public abstract class ABaseValidator extends SerializableBaseVO implements ServiceConstants {

    public ABaseValidator() {
        super();
    }

    protected void setError(ValidatorResponse resp, String msg) {
        resp.setCode(USER_ERROR_RC);
        resp.getMessages().add(msg);
    }

    protected boolean isEmpty(Integer obj) {
        if (obj == null || obj.intValue() == 0)    return true;

        return false;
    }

    protected boolean isEmpty(Double obj) {
        if (obj == null || obj.intValue() == 0)      return true;
        return false;
    }

    protected boolean isEmpty(List list) {
        if (list == null || list.isEmpty())
        	return true;
        return false;
    }
}