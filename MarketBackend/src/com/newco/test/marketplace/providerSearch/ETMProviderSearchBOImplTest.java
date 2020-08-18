package com.newco.test.marketplace.providerSearch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.providersearch.IETMProviderSearch;
import com.newco.marketplace.criteria.PagingCriteria;
import com.newco.marketplace.criteria.SortCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceFilterCriteria;
import com.newco.marketplace.dto.vo.ExploreMktPlace.ExploreMktPlaceSearchCriteria;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderRecord;
import com.newco.marketplace.dto.vo.providerSearch.ETMProviderSearchResults;
import com.newco.marketplace.dto.vo.serviceorder.CriteriaMap;
import com.newco.marketplace.exception.core.BusinessServiceException;
import com.newco.marketplace.interfaces.OrderConstants;
import com.newco.marketplace.vo.PaginationVO;

/**
 *
 * $Revision: 1.5 $ $Author: glacy $ $Date: 2008/04/26 00:40:34 $
 * 
 */
public class ETMProviderSearchBOImplTest implements OrderConstants{
    private  static ApplicationContext ctx = null;
	private  PagingCriteria pc = null;
	private  SortCriteria sc = null;
	private  ExploreMktPlaceSearchCriteria searchCriteria = null;
	private  ExploreMktPlaceFilterCriteria fc = null; 
	private  CriteriaMap newMap = null;
	
    public ETMProviderSearchBOImplTest() {
    }

    public static void main(String []args) throws Exception{
    	setUpClass();
    	ETMProviderSearchBOImplTest x = new ETMProviderSearchBOImplTest();
    	x.setUp();
    	//x.getETMProviderList();
    	x.filterProvider();
    	//x.cleanETMTempTable();
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
    	ctx = new ClassPathXmlApplicationContext ("resources/spring/applicationContextJunit.xml");
		//ServiceOrderDaoImpl serv = (ServiceOrderDaoImpl) ctx.getBean(MPConstants.SERVICE_BUSINESSBEAN);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    	 //pc = new PagingCriteria();
		 //sc = new SortCriteria();
		 //sc.setSortColumnName(OrderConstants.SORT_ETM_COLUMN_AS_TOTAL_ORDERS);
		 //sc.setSortOrder(OrderConstants.SORT_ORDER_DESC);
		 searchCriteria = new ExploreMktPlaceSearchCriteria();
		 searchCriteria.setMktReady(new Integer(1));
		 searchCriteria.setBuyerZipCode(60156);
		 searchCriteria.setServiceCategoryId(new Integer(1));
		 
		 //fc = new ExploreMktPlaceFilterCriteria(null,null);
		 
		 newMap = new CriteriaMap();
		 newMap.put(ETM_FILTER_CRITERIA_KEY, fc);
		 newMap.put(ETM_SORT_CRITERIA_KEY, sc);
		 newMap.put(ETM_PAGING_CRITERIA_KEY, pc);
		 newMap.put(ETM_SEARCH_CRITERIA_KEY, searchCriteria);
		 newMap.put(ETM_SEARCH_KEY, null);
    }

    @After
    public void tearDown() throws Exception {
    }

//    @Test
//    public void findProvidersByCriteria() throws Exception {
//        System.out.println("findProvidersByCriteria");
//        CriteriaMap critera = newMap;
//        ETMProviderSearchBOImpl instance = new ETMProviderSearchBOImpl();
//        ETMProviderSearchResults expResult = null;
//        ETMProviderSearchResults result = instance.findProvidersByCriteria(critera);
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    } /* Test of findProvidersByCriteria method, of class ETMProviderSearchBOImpl. */

    @Test
    public void getETMProviderList() throws Exception {
        System.out.println("getETMProviderList");
        CriteriaMap critera = newMap;
        String userName = "";
        IETMProviderSearch instance = (IETMProviderSearch)ctx.getBean("etmProviderSearchBO");
        ETMProviderSearchResults expResult = null;
        ETMProviderSearchResults result = instance.getETMProviderList(critera, userName);
        
        if(result != null && result.getSearchResults() != null){
        	List<ETMProviderRecord> list = result.getSearchResults();
        	
        	for(Iterator it=list.iterator();it.hasNext();){
        		ETMProviderRecord provider = (ETMProviderRecord)it.next();
        		System.out.println(" Resource ID "+provider.getResourceId());
        	}
        	
//        	pc = new PagingCriteria();
//        	pc.setStartIndex(50);
//        	pc.setPageSize(25);
//        	pc.setEndIndex(75);
//        	
//        	fc = new ExploreMktPlaceFilterCriteria(null,null);
//        	
//        	fc.setDistance(10);
//        	//critera.put(ETM_PAGING_CRITERIA_KEY, pc);
//        	critera.put(ETM_FILTER_CRITERIA_KEY, fc);
//        	critera.put(ETM_SEARCH_KEY,result.getSearchQueryKey());
//        	
//        	System.out.println(" ************************************************* ");
//        	ETMProviderSearchResults result1 = instance.findProvidersByCriteria(critera);
//        	
//        	 if(result1 != null && result1.getSearchResults() != null){
//             	List<ETMProviderRecord> list1 = result1.getSearchResults();
//             	
//             	for(Iterator it=list1.iterator();it.hasNext();){
//             		ETMProviderRecord provider = (ETMProviderRecord)it.next();
//             		System.out.println(" Resource ID "+provider.getResourceId());
//             	}
//        	 }
        }
        
        
       // assertEquals(expResult, result);
        //fail("The test case is a prototype.");
    } /* Test of getETMProviderList method, of class ETMProviderSearchBOImpl. */

    public void filterProvider(){
    	System.out.println("filterProvider");
        CriteriaMap critera = newMap;
        IETMProviderSearch instance = (IETMProviderSearch)ctx.getBean("etmProviderSearchBO");
        ETMProviderSearchResults expResult = null;
    	String userName = "";
        
        pc = new PagingCriteria();
    	pc.setStartIndex(1);
    	pc.setPageSize(25);
    	pc.setEndIndex(25);
    	
    	fc = new ExploreMktPlaceFilterCriteria(null,null);
//    	fc.setDistance(50);
    	
//    	fc.setRating(2.0);
    	
    	List<Integer> selectedLangs = new ArrayList<Integer>();
    	selectedLangs.add(1);
    	selectedLangs.add(5);
    	fc.setSelectedLangs(selectedLangs);
    	
//    	fc.setSelectedCredential(55);
//    	fc.setCredentialCategory(6);
    	
    	sc = new SortCriteria();
    	sc.setSortColumnName("distance_in_miles");
    	sc.setSortOrder("asc");
    	
    	critera.put(ETM_PAGING_CRITERIA_KEY, pc);
    	critera.put(ETM_FILTER_CRITERIA_KEY, fc);
    	critera.put(ETM_SORT_CRITERIA_KEY, sc);
    	critera.put(ETM_SEARCH_KEY,"204953779");
        
        try {
			ETMProviderSearchResults result = instance.findProvidersByCriteria(critera, userName,true);
			
			  if(result != null && result.getSearchResults() != null){
		        	List<ETMProviderRecord> list = result.getSearchResults();
		        	System.out.println(" The size of the result set is "+list.size());
		        	
		        	for(Iterator it=list.iterator();it.hasNext();){
		        		ETMProviderRecord provider = (ETMProviderRecord)it.next();
		        		System.out.println(" Resource ID "+provider.getResourceId());
		        	}
			  }
			  
			  if(result != null && result.getPaginationVo() != null){
				  PaginationVO pagination = result.getPaginationVo();
				  
				  System.out.println("Paginet Name "+pagination.getCurrentPaginet().getDisplayName()
						  +" Start index "+pagination.getCurrentPaginet().getStartIndex()
						  +" TotalPaginets "+pagination.getTotalPaginets());
			  }
		} catch (BusinessServiceException e) {
			e.printStackTrace();
		}
        
    }
    
    @Test
    public void cleanETMTempTable() throws Exception {
        System.out.println("cleanETMTempTable");
        String searchQueryKey = "911835108";
        IETMProviderSearch instance = (IETMProviderSearch)ctx.getBean("etmProviderSearchBO");
        instance.cleanETMTempTable(searchQueryKey);
        //fail("The test case is a prototype.");
    } /* Test of cleanETMTempTable method, of class ETMProviderSearchBOImpl. */

//    @Test
//    public void getEtmDAO() {
//        System.out.println("getEtmDAO");
//        IETMProviderSearch instance = (IETMProviderSearch)ctx.getBean("etmProviderSearchBO");
//        ETMProviderSearchDao expResult = null;
//        ETMProviderSearchDao result = instance.getEtmDAO();
//        assertEquals(expResult, result);
//        fail("The test case is a prototype.");
//    } /* Test of getEtmDAO method, of class ETMProviderSearchBOImpl. */
//
//    @Test
//    public void setEtmDAO() {
//        System.out.println("setEtmDAO");
//        ETMProviderSearchDao etmDAO = null;
//        IETMProviderSearch instance = (IETMProviderSearch)ctx.getBean("etmProviderSearchBO");
//        instance.setEtmDAO(etmDAO);
//        fail("The test case is a prototype.");
//    } /* Test of setEtmDAO method, of class ETMProviderSearchBOImpl. */
    
}
