/**
 * 
 */
package com.newco.marketplace.business.iBusiness.serviceorder;

import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderOutFileTrackingVO;

/**
 * @author hoza
 *
 */
public interface IServiceOrderOutFileTrackingBO {
	public  Boolean insertTrackingRecord(ServiceOrderOutFileTrackingVO trackingVO) throws Exception;
	public ServiceOrderOutFileTrackingVO getSOOutFileTrackingInfo(String soId) throws Exception;
}
