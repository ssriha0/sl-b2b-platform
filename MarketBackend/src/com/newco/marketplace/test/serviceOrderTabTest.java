package com.newco.marketplace.test;

import java.util.List;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderSearchBO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;
import com.newco.marketplace.dto.vo.serviceorder.serviceOrderTabsVO;

public class serviceOrderTabTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			IServiceOrderSearchBO soBO = (IServiceOrderSearchBO) MPSpringLoaderPlugIn.getCtx()
//					.getBean("serviceOrderBO");
//
//			serviceOrderTabsVO request = new serviceOrderTabsVO();
//
//			request.setBuyerId("2");
//			request.setSoStatus("110");
//
//			List<ServiceOrderSearchResultsVO> searchList = soBO
//					.findservicebyuser(request);
//
//			System.out.println("The Result for 1st search is ");
//			System.out.println(searchList.get(0).getSoId() + "  "
//					+ searchList.get(0).getAcceptedResourceId() + " "
//					+ searchList.get(0).getBuyerID() + " "
//					+ searchList.get(0).getSoStatus() + " "
//					+ searchList.get(0).getZip() + " "
//					+ searchList.get(0).getBuyerFirstName() + " "
//					+ searchList.get(0).getBuyerLastName() + " "
//					+ searchList.get(0).getProviderFirstName() + " "
//					+ searchList.get(0).getProviderLastName() + " "
//					+ searchList.get(0).getEndCustomerFirstName() + " "
//					+ searchList.get(0).getEndCustomerLastName() + " "
//					+ searchList.get(0).getSoStatus() + " "
//					+ searchList.get(0).getSoSubStatus() + "  "
//					+ searchList.get(1).getSoTitle() + " ");
//
//			searchList = soBO.findservicebyuser(request);
//			System.out.println("The Result for 2nd search is ");
//			for (int i = 0; i < searchList.size(); i++) {
//				System.out.println(searchList.get(i).getSoId() + "  "
//						+ searchList.get(i).getAcceptedResourceId() + " "
//						+ searchList.get(i).getBuyerID() + " "
//						+ searchList.get(i).getSoStatus() + " "
//						+ searchList.get(i).getZip() + " "
//						+ searchList.get(i).getBuyerFirstName() + " "
//						+ searchList.get(i).getBuyerLastName() + " "
//						+ searchList.get(i).getProviderFirstName() + " "
//						+ searchList.get(i).getProviderLastName() + " "
//						+ searchList.get(i).getEndCustomerFirstName() + " "
//						+ searchList.get(i).getEndCustomerLastName() + " "
//						+ searchList.get(0).getSoStatus() + " "
//						+ searchList.get(0).getSoSubStatus());
//			}
//		} catch (Throwable t) {
//			t.printStackTrace();
//			System.out.println(t.getMessage());
//		}

	}

}