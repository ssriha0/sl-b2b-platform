package com.newco.marketplace.business.iBusiness.ach;

import java.util.Date;

public interface INachaFile {

	public void writeNachaRecordsToFile(long nachaProcessLogId,Date nachaDate) throws Exception;
	
}
