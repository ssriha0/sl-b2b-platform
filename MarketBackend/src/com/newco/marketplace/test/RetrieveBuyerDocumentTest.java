package com.newco.marketplace.test;

import java.util.ArrayList;

import com.newco.marketplace.action.MPSpringLoaderPlugIn;
import com.newco.marketplace.business.businessImpl.document.DocumentBO;
import com.newco.marketplace.dto.vo.DocumentVO;
import com.newco.marketplace.persistence.daoImpl.document.ServiceOrderDocumentDao;
import com.sears.os.context.ContextValue;
public class RetrieveBuyerDocumentTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ContextValue.setContextFile("resources/spring/applicationContext.xml");	
		RetrieveBuyerDocumentTest retrieveObj = new RetrieveBuyerDocumentTest();
		retrieveObj.testretrieveDocumentbyIdDao();
	}
	
	private void testretrieveDocumentbyId(){
		DocumentBO x = (DocumentBO)MPSpringLoaderPlugIn.getCtx().getBean("idocumentBO");
		try{
		DocumentVO documentVO = x.retrieveBuyerDocumentByDocumentId(new Integer(29));
		System.out.println("------"+documentVO.getTitle());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	private void testretrieveDocumentbyIdDao(){
		ServiceOrderDocumentDao x = (ServiceOrderDocumentDao)MPSpringLoaderPlugIn.getCtx().getBean("serviceOrderDocumentDAO");
		try{
		DocumentVO documentVO = x.retrieveDocumentByDocumentId(new Integer(29));
		System.out.println("------"+documentVO.getTitle());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	private void testretriveDocumentBO(){
		DocumentBO x = (DocumentBO)MPSpringLoaderPlugIn.getCtx().getBean("idocumentBO");
		try{
		ArrayList docList = (ArrayList)x.retrieveDocumentsByEntityIdAndDocumentId(new Integer(110), new Integer(1));
		System.out.println("------------------------->"+docList.size());
		for(int i=0;i<docList.size();i++){
			DocumentVO dvo = (DocumentVO)docList.get(i);
			System.out.println("------>"+dvo.getFileName());
			System.out.println("------->"+dvo.getTitle());
			System.out.println("----->"+dvo.getDocumentId());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
