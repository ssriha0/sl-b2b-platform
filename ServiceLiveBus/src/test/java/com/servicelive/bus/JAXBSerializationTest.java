package com.servicelive.bus;

import java.io.StringWriter;
import java.util.Calendar;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.servicelive.bus.event.ServiceLiveEvent;
import com.servicelive.bus.util.TestEventSource;
import junit.framework.JUnit4TestAdapter;
import org.junit.Assert;
import org.junit.Test;

/**
 * User: Mustafa Motiwala
 * Date: Mar 26, 2010
 * Time: 6:07:01 PM
 */
public class JAXBSerializationTest {
    public JAXBSerializationTest(){
    }

    @Test
    public void serializeServiceLiveEvent() throws Exception{
        ServiceLiveEvent testTarget = new ServiceLiveEvent(new TestEventSource(), Calendar.getInstance());
        JAXBContext context = JAXBContext.newInstance(ServiceLiveEvent.class, testTarget.getEventSource().getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        marshaller.marshal(testTarget, stringWriter);
        Assert.assertTrue(stringWriter.getBuffer().length() > 0);
        System.out.println(stringWriter.toString());
    }

    public static junit.framework.Test suite() {
        return new JUnit4TestAdapter(JAXBSerializationTest.class);
    }
}
