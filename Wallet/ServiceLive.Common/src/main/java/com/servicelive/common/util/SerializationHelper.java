package com.servicelive.common.util;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

public class SerializationHelper {
    public static void forceOrmLoadUsingJAXBSerialization(Object obj) throws JAXBException {
        if (null == obj) return;
        JAXBContext jc = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(obj, new StringWriter());
    }
//
//    public static <T> T cloneObjectUsingJAXBSerialization(T obj, Class additionalClassesToBeBound[]) throws JAXBException {
//        Class[] classesToBeBound = prepClassesToBeBound(obj, additionalClassesToBeBound);
//        JAXBContext jc = JAXBContext.newInstance(classesToBeBound);
//        Marshaller marshaller = jc.createMarshaller();
//        StringWriter stringWriter = new StringWriter();
//        marshaller.marshal(obj, stringWriter);
//        Unmarshaller unmarshaller = jc.createUnmarshaller();
//        StringReader stringReader = new StringReader(stringWriter.toString());
//        return (T) unmarshaller.unmarshal(stringReader);
//    }
//
//    private static Class[] prepClassesToBeBound(Object obj, Class additionalClassesToBeBound[]) {
//        Class[] classesToBeBound;
//        if (additionalClassesToBeBound !=null) {
//            classesToBeBound = new Class[additionalClassesToBeBound.length + 1];
//            classesToBeBound[0] = obj.getClass();
//            System.arraycopy(additionalClassesToBeBound, 0, classesToBeBound, 1, additionalClassesToBeBound.length);
//        }
//        else{
//            classesToBeBound = new Class[] {obj.getClass()};
//        }
//        return classesToBeBound;
//    }
}