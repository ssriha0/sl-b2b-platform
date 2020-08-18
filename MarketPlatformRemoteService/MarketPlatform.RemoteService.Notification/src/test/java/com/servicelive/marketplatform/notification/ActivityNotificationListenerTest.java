package com.servicelive.marketplatform.notification;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.junit.Test;
import static org.mockito.Mockito.*;

import com.servicelive.domain.common.Contact;
import com.servicelive.marketplatform.service.MarketPlatformNotificationBO;
import com.servicelive.marketplatform.service.MarketPlatformProviderBO;
import com.servicelive.marketplatform.service.MarketPlatformServiceOrderBO;
import com.servicelive.marketplatform.serviceorder.domain.ServiceOrder;
import com.servicelive.marketplatform.serviceorder.domain.ServiceOrderRoutedProvider;



public class ActivityNotificationListenerTest {
	private ActivityNotificationListener listener;
	private MarketPlatformServiceOrderBO serviceOrderBO;
	private MarketPlatformProviderBO providerBO;
	private MarketPlatformNotificationBO notificationBO;
	
	@Test
	public void testOnMessage(){
		listener = new ActivityNotificationListener();
		serviceOrderBO = mock(MarketPlatformServiceOrderBO.class);
		providerBO = mock(MarketPlatformProviderBO.class);
		notificationBO = mock(MarketPlatformNotificationBO.class);
		
		listener.setServiceOrderBO(serviceOrderBO);
		listener.setProviderBO(providerBO);
		listener.setNotificationBO(notificationBO);
		
		Message message = new Message() {
			
			public void setStringProperty(String arg0, String arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setShortProperty(String arg0, short arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setObjectProperty(String arg0, Object arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setLongProperty(String arg0, long arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSType(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSTimestamp(long arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSReplyTo(Destination arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSRedelivered(boolean arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSPriority(int arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSMessageID(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSExpiration(long arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSDestination(Destination arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSDeliveryMode(int arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSCorrelationIDAsBytes(byte[] arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setJMSCorrelationID(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setIntProperty(String arg0, int arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setFloatProperty(String arg0, float arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setDoubleProperty(String arg0, double arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setByteProperty(String arg0, byte arg1) throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void setBooleanProperty(String arg0, boolean arg1)
					throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public boolean propertyExists(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public String getStringProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public short getShortProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Enumeration getPropertyNames() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Object getObjectProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getLongProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getJMSType() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getJMSTimestamp() throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Destination getJMSReplyTo() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public boolean getJMSRedelivered() throws JMSException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public int getJMSPriority() throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public String getJMSMessageID() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public long getJMSExpiration() throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public Destination getJMSDestination() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getJMSDeliveryMode() throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public byte[] getJMSCorrelationIDAsBytes() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getJMSCorrelationID() throws JMSException {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getIntProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public float getFloatProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public double getDoubleProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public byte getByteProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public boolean getBooleanProperty(String arg0) throws JMSException {
				// TODO Auto-generated method stub
				return false;
			}
			
			public void clearProperties() throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void clearBody() throws JMSException {
				// TODO Auto-generated method stub
				
			}
			
			public void acknowledge() throws JMSException {
				// TODO Auto-generated method stub
				
			}
		};
		String soId = "541-5430-5650-17";
		try {
			message.setStringProperty("orderId", "541-5430-5650-17");
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ServiceOrder so = new ServiceOrder();
		
		List<ServiceOrderRoutedProvider> proList = new ArrayList<ServiceOrderRoutedProvider>();
 		ServiceOrderRoutedProvider pro = new ServiceOrderRoutedProvider();
		pro.setResourceId(10254L);
		proList.add(pro);
		so.setRoutedProviders(proList);
		
		Contact ci = new Contact();
		ci.setEmail("a@b.com");
		
		when(providerBO.retrieveProviderResourceContactInfo(pro.getResourceId())).thenReturn(ci);
		
		try {
			when(serviceOrderBO.retrieveServiceOrder(message.getStringProperty("orderId"))).thenReturn(so);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		listener.onMessage(message);
	}
	
}
