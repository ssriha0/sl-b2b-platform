package com.newco.marketplace.business.businessImpl.so.order;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.newco.marketplace.criteria.SearchCriteria;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderCustomRefVO;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrderSearchResultsVO;

/**
 * This is the test class to test setAdvanceSearchCriteria() method of ServiceOrderSearchBO class
 *
 */
public class ServiceOrderSearchBOTest {

	private ServiceOrderSearchBO soSearchBO = null;
	
	@Test
	public void testSetAdvanceCriteria() throws Exception{
		
		 soSearchBO =new ServiceOrderSearchBO();
		 
		 ServiceOrderSearchResultsVO soSearchVO =new ServiceOrderSearchResultsVO();
		 
	     Class<? extends ServiceOrderSearchBO> classObj=soSearchBO.getClass();
		 
		 SearchCriteria searchCriteria =new SearchCriteria();
		 Map<String, ArrayList<String>> selectedCustomRef =new HashMap<String, ArrayList<String>>();
		 ArrayList<String> values =new ArrayList<String>();
		 values.add("1234");
		 values.add("5678");
		 values.add("1598");
		 selectedCustomRef.put("651", values);
		 searchCriteria.setSelectedCustomRefs(selectedCustomRef);
		 
		 ServiceOrderSearchResultsVO soSearchVOParam =new ServiceOrderSearchResultsVO();
		 List<ServiceOrderCustomRefVO> selectedCustomRefs =new ArrayList<ServiceOrderCustomRefVO>();
		 ServiceOrderCustomRefVO custRefs =new ServiceOrderCustomRefVO();
		 custRefs.setRefTypeId(651);
		 custRefs.setRefValue("1234");
		 selectedCustomRefs.add(custRefs);
		 custRefs.setRefTypeId(651);
		 custRefs.setRefValue("5678");
		 selectedCustomRefs.add(custRefs);
		 custRefs.setRefTypeId(651);
		 custRefs.setRefValue("1598");
		 selectedCustomRefs.add(custRefs);
		 soSearchVOParam.setSelectedCustomRefs(selectedCustomRefs);
		 
		 try{
			 Method setAdvanceCriteria = classObj.getDeclaredMethod("setAdvanceSearchCriteria", SearchCriteria.class,ServiceOrderSearchResultsVO.class);
			 setAdvanceCriteria.setAccessible(true);
			 soSearchVO=(ServiceOrderSearchResultsVO)setAdvanceCriteria.invoke(soSearchBO,searchCriteria,soSearchVOParam);
			 
			 Assert.assertEquals(soSearchVO, soSearchVOParam);
		 }catch(Exception e)
		 {
			 e.printStackTrace(); 
		 }
			
	}
}
