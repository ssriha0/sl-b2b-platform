package com.servicelive.marketplatform.dao;

import java.util.List;

import com.servicelive.domain.common.ApplicationFlags;
import com.servicelive.domain.common.ApplicationProperties;
import com.servicelive.domain.common.InHomeOutBoundMessages;
/**
 * Created by IntelliJ IDEA.
 * User: yburhani
 * Date: Jun 18, 2010
 * Time: 4:28:49 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IApplicationPropertyDao {
    public List<ApplicationProperties> getAllApplicationProperties();
    public List<ApplicationFlags> getAllApplicationFlags();
	public List<InHomeOutBoundMessages> getOutBoundStatusMessages();
	
}
