package com.newco.marketplace.test;

public class RetrieveDocumentTest {

	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//ContextValue.setContextFile("resources/spring/applicationContext.xml");	
		ContextValue.setContextFile("/applicationContext.xml");
		RetrieveDocumentTest retrieveObj = new RetrieveDocumentTest();
		//retrieveObj.testretriveDocumentDelegate();
		retrieveObj.testretrieveDocumentbyId();
	}
	
	
	*/
	/*
	private void testretriveDocumentDelegate(){
		SOWizardFetchDelegateImpl x = (SOWizardFetchDelegateImpl)BeanFactory.getBean("SOWizardFetchBean");
		try{
		ArrayList docList = (ArrayList)x.retrieveDocumentsByEntityIdAndDocumentId(new Integer(110), new Integer(1));
		System.out.println("------------------------->"+docList.size());
		for(int i=0;i<docList.size();i++){
			SOWBrandingInfoDTO dvo = (SOWBrandingInfoDTO)docList.get(i);
			System.out.println("------>"+dvo.getCompanyName());
			System.out.println("------->"+dvo.getLogoId());
			System.out.println("----->"+dvo.getDocumentId());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void testretrieveDocumentbyId(){
		SOWizardFetchDelegateImpl x = (SOWizardFetchDelegateImpl)BeanFactory.getBean("SOWizardFetchBean");
		try{
		DocumentVO documentVO = x.retrieveDocumentByDocumentId(new Integer(29));
		System.out.println("------"+documentVO.getTitle());
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
*/
}
