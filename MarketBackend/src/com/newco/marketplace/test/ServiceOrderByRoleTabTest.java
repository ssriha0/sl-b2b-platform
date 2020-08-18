package com.newco.marketplace.test;


import java.util.ArrayList;
import java.util.Iterator;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.so.order.ServiceOrderRoleBO;
import com.newco.marketplace.dto.vo.ajax.AjaxCacheVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderWfStatesCountsVO;

public class ServiceOrderByRoleTabTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			ServiceOrderRoleBO soBO = (ServiceOrderRoleBO)MPSpringLoaderPlugIn.getCtx().getBean("soRoleBo");
			ArrayList<ServiceOrderWfStatesCountsVO> stateCounts = new ArrayList();
			ServiceOrderWfStatesCountsVO singleStateCount = new ServiceOrderWfStatesCountsVO();
			AjaxCacheVO acv = new AjaxCacheVO();
			
			acv.setCompanyId(2);
			acv.setRoleType("buyer");
			System.out.println("\n\n********* Buyer Tabs ***********");
			stateCounts = soBO.getTabs(acv);
			Iterator<ServiceOrderWfStatesCountsVO> i = stateCounts.iterator();
			while(i.hasNext()){
				singleStateCount = i.next();
				System.out.println(singleStateCount.getTabType() + 
						"\t" + singleStateCount.getSoCount());
			}
			System.out.println("\n\n******** Provider SOs ***********");
			acv.setRoleType("provider");
			acv.setCompanyId(2);
			stateCounts = soBO.getTabs(acv);
			i = stateCounts.iterator();
			while(i.hasNext()){
				singleStateCount = i.next();
				System.out.println(singleStateCount.getTabType() + 
						"\t" + singleStateCount.getSoCount());
			}
			
			
		} catch (Throwable t) {
			t.printStackTrace();
			System.out.println(t.getMessage());
		}	

	}

}
