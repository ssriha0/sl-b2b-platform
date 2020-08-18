package com.newco.marketplace.web.utils;

/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2007, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 * 
 * modified by spate05
 * we want to see all activity for the session
 * 
 * 
 * 
 * SETUP:
 * 1. update web.xml. Add this class as a listener. i.e:
 * 	<listener>
		<listener-class>
			com.newco.marketplace.web.utils.SessionTester
		</listener-class>
	</listener>	
	
   2. (optional but recommended). Add logger config to the log4j.xml:
    <category name="com.newco.marketplace.web.utils.SessionTester">
      <priority value="INFO"/>
      <appender-ref ref="CONSOLE"/>
      <appender-ref ref="FILE"/>
   </category>  
   
   IMPORTANT: Please remember (at this time) not to check-in the updated web.xml. We do
   not want this listener yet. This is just a developer's helper utility class.
 */
 
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.log4j.Logger;


/**
 * Diagnostic tool to help identify when non-serializable objects are
 * placed in a web session.  To use, place this class on the webapp's
 * classpath and add the following to <code>web.xml</code>:
 * <p>
 * <code><listener><listener-class>com.newco.marketplace.web.utils.NonSerializableAttributeTester</listener-class></listener></code>
 * </p>
 * 
 * @author <a href="brian.stansberry@jboss.com">Brian Stansberry</a>
 * @version $Revision: 1.4 $
 */
public class SessionTester 
   implements HttpSessionAttributeListener
{

	private static final Logger logger = Logger.getLogger(SessionTester.class.getName());
   public SessionTester()
   {
      super();
   }

   public void attributeAdded(HttpSessionBindingEvent event)
   {
      logger.info("Added: " + event.getName() + " " + event.getValue());
      System.out.println("Added: " + event.getName() + " " + event.getValue());
   }

   public void attributeRemoved(HttpSessionBindingEvent event)
   {
	   logger.info("Removed: " + event.getName() + " " + event.getValue());
	   System.out.println("Removed: " + event.getName() + " " + event.getValue());
   }

   public void attributeReplaced(HttpSessionBindingEvent event)
   {
	   logger.debug("Replaced: " + event.getName() + " " + event.getValue());
	 //  System.out.println("Replaced: " + event.getName() + " " + event.getValue());
   }

}
