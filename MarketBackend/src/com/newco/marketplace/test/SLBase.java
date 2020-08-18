package com.newco.marketplace.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.newco.marketplace.business.iBusiness.powerbuyer.IPowerBuyerBO;
import com.newco.marketplace.business.iBusiness.security.ISecurityBO;
import com.newco.marketplace.business.iBusiness.serviceorder.ISOEventBO;
import com.newco.marketplace.business.iBusiness.serviceorder.IServiceOrderBO;
import com.newco.marketplace.persistence.iDao.powerbuyer.ISOClaimDao;
import com.newco.marketplace.persistence.iDao.security.SecurityDAO;
import com.newco.marketplace.persistence.iDao.so.order.ServiceOrderDao;
import com.newco.marketplace.webservices.base.response.ProcessResponse;



public class SLBase extends TestCase {

	private static ApplicationContext applicationContext =  null;
	
	static {
		applicationContext = new ClassPathXmlApplicationContext(new String[]{"resources/spring/applicationContextJunit.xml"});
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		init();
		for(Iterator<String> iter=name.keySet().iterator();iter.hasNext();){
			String key=iter.next();
			beanMap.put(key,applicationContext.getBean(name.get(key)));
		}
		map();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		name.clear();beanMap.clear();processResponse=null;
	}

	protected void init(){
/*		name.put(ServiceOrderDao.class.getName(),MPConstants.SERVICE_BUSINESSBEAN);
		name.put(IServiceOrderBO.class.getName(),"soBOAOP");
		name.put(SecurityDAO.class.getName(), "securityDAO");
		name.put(ISecurityBO.class.getName(), "securityBO");
		name.put(ISOClaimDao.class.getName(), "soClaimDAO");
		name.put(IPowerBuyerBO.class.getName(), "powerBuyerBO");*/
		name.put(ISOEventBO.class.getName(), "soEventBO");
	}
	
	protected void map(){
		_iServiceOrderBO=(IServiceOrderBO) beanMap.get(IServiceOrderBO.class.getName());
		_iServiceOrderDao=(ServiceOrderDao) beanMap.get(ServiceOrderDao.class.getName());
		_iSecurityDAO=(SecurityDAO)beanMap.get(SecurityDAO.class.getName());
		_iSecurityBO=(ISecurityBO)beanMap.get(ISecurityBO.class.getName());
		_iSOClaimDao=(ISOClaimDao)beanMap.get(ISOClaimDao.class.getName());
		_iPowerBuyerBO=(IPowerBuyerBO)beanMap.get(IPowerBuyerBO.class.getName());
		_iSOEventBO=(ISOEventBO)beanMap.get(ISOEventBO.class.getName());

	}
	
	protected void display(Object message){
		System.out.println("Value: " + (message));
	}
	
	public ProcessResponse processResponse;
	
	public IServiceOrderBO _iServiceOrderBO;
	
	public ServiceOrderDao _iServiceOrderDao;
	
	public SecurityDAO _iSecurityDAO;
	
	public ISecurityBO _iSecurityBO;
	
	public ISOClaimDao _iSOClaimDao;
	
	public IPowerBuyerBO _iPowerBuyerBO;
	
	public ISOEventBO _iSOEventBO;
	
	protected Map<String, Object> beanMap= new HashMap<String, Object>();
	
	protected Map<String, String> name=new  HashMap<String, String>();
}
