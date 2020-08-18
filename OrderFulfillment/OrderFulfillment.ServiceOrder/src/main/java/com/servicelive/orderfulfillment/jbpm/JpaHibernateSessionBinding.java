package com.servicelive.orderfulfillment.jbpm;

import org.jbpm.pvm.internal.wire.binding.WireDescriptorBinding;
import org.jbpm.pvm.internal.xml.Parse;
import org.jbpm.pvm.internal.xml.Parser;
import org.w3c.dom.Element;

public class JpaHibernateSessionBinding extends WireDescriptorBinding {

  public JpaHibernateSessionBinding() {
    super("jpa-hibernate-session");
  }

  public Object parse(Element element, Parse parse, Parser parser) {
    return new JpaHibernateSessionDescriptor();
  }

}
