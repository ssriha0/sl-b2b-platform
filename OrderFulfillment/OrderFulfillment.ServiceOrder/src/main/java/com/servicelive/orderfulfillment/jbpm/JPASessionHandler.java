package com.servicelive.orderfulfillment.jbpm;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.ejb.HibernateEntityManagerFactory;

//
// this class helps bridging of JPA and jBPM
// Spring injects JPA EntityManager and Factory into this class
// then we inject Hibernate session into jBPM so it can join Spring initiated transactions
// (see also JpaHibernateSessionDescriptor)
//
public class JPASessionHandler 
{
	// JPASessionHandler is a Spring managed singleton
	// we need access to it from jBPM to get sessions
	// jBPM doesn't use Spring to instantiate it's internal objects so we can't inject this object "Spring way"
	// we need another way to get to this instance
	// therefore we are defining a static list and providing a static access method to accomplish it
	static private List<JPASessionHandler> handlers = new ArrayList<JPASessionHandler>();
	
	// add instance to a static list upon instantiation
	public JPASessionHandler(){
		handlers.add(this);
	}
	// it's a singleton and therefore there we expect there to be only one element in the list
	static public JPASessionHandler getHandlerInstance() throws Exception {
		if (handlers.size()<1)
			throw new Exception("JPASessionHandler is not instantiated. Please add it to your Spring context");
		if (handlers.size()>1)
			throw new Exception("There is more than one instance of JPASessionHandler. Please ensure that in your Spring context this object is a singleton");
		return handlers.get(0);
	}
	
	// EntityManager and EntityManagerFactory are injected by Spring
	// (similar pattern as used by all of the DAOs of the sub-system)
	private EntityManagerFactory entityManagerFactory;
    private EntityManager em;
    
    // this is where Spring's "magic" occurs
    // manages a pool of EntityManagers for each thread behind the scenes
    @PersistenceContext()
    public void setEntityManager(EntityManager em) {
    	this.em = em;
    }
    
    // this is where we get the Hibernate session from
    // it will be associated to current thread and Spring will manage it for us
	public Session getJpaSession(){
		return (Session)em.getDelegate();
	}
	
	// getter/setter for EntityManager factory - we need it to get to the underlying session
	public SessionFactory getJpaSessionFactory(){
		return ((HibernateEntityManagerFactory)entityManagerFactory).getSessionFactory();
	}
	
	public void setEntityManagerFactory(EntityManagerFactory emf) {
		entityManagerFactory = emf;
	}
}
