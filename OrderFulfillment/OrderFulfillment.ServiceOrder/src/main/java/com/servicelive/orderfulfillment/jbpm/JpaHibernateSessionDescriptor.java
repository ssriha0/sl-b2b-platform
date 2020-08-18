package com.servicelive.orderfulfillment.jbpm;

import org.hibernate.Session;
import org.hibernate.impl.SessionImpl;
import org.jbpm.pvm.internal.wire.WireContext;
import org.jbpm.pvm.internal.wire.WireDefinition;
import org.jbpm.pvm.internal.wire.WireException;
import org.jbpm.pvm.internal.wire.descriptor.AbstractDescriptor;

//
// A bridge object used to inject JPA session to jBPM
// jBPM doesn't integrate with JPA out of the box and therefore this is needed
// Implementation notes:
//  - jBPM calls this object every time it requires a Hibernate session
//      - note that getType returns Hibernate SessionImpl type
//      - this associates our bridge with Hibernate session type in jBPM object factory
//  - we receive the call and use JPASessionHandler object to get the session from JPA
//  - bootstrapping of this object requires several steps:
//    - binding object JpaHibernateSessionBinding is provided to instantiate this object
//    - binding object registers name jpa-hibernate-session with jBPM
//    - binding object is injected into jBPM by using jbpm.user.wire.bindings.xml
//    - jbpm.4.3.cfg.xml then bootstraps the entire thing by referencing jpa-hibernate-session
//
//
public class JpaHibernateSessionDescriptor extends AbstractDescriptor {
  
  private static final long serialVersionUID = 1L;

  public Object construct(WireContext wireContext) {
	  // get Hibernate session from JPA
      try {
        JPASessionHandler jpaSessionHandler = JPASessionHandler.getHandlerInstance();
	    if (jpaSessionHandler==null) {
	        throw new WireException("unable to get valid jpaSessionHandler instance to create jpa-hibernate-session");
	    }    	
	    
	    Session session = jpaSessionHandler.getJpaSession();
	    if (session==null) {
	      throw new WireException("couldn't get hibernate-session to create jpa-hibernate-session");
	    }
	    
	    return session;	  
      }
      catch (Exception e) {
	        throw new WireException("unable to get valid jpaSessionHandler instance to create jpa-hibernate-session");
      }
  }
  
  public Class<?> getType(WireDefinition wireDefinition) {
    return SessionImpl.class;
  }

}
