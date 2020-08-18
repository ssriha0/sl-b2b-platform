package com.newco.marketplace.test;

import java.util.Iterator;
import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.so.order.ServiceOrderBO;
import com.newco.marketplace.dto.vo.logging.SoChangeDetailVo;
import com.newco.marketplace.exception.core.DataServiceException;

public class TestSOBO {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServiceOrderBO sob = (ServiceOrderBO)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderBOTarget");
		try {
			List<SoChangeDetailVo> list=sob.getSoChangeDetailVoList("001-6436333046-11");
			Iterator it=list.iterator();
			while(it.hasNext())
				
			System.out.println("ClassName:" +  it.next());
		
			System.out.println("list:"+ list.size());
		} catch (DataServiceException e) {
			e.printStackTrace();
		}

	}

}
