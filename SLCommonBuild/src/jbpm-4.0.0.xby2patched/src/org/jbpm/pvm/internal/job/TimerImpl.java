/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
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
 */
package org.jbpm.pvm.internal.job;

import java.text.ParseException; // PATCH
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jbpm.api.JbpmException;
import org.jbpm.api.job.Timer;
import org.jbpm.internal.log.Log;
import org.jbpm.pvm.internal.cal.BusinessCalendar;
import org.jbpm.pvm.internal.cal.Duration;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.EnvironmentDefaults;
import org.jbpm.pvm.internal.env.Transaction;
import org.jbpm.pvm.internal.jobexecutor.JobAddedNotification;
import org.jbpm.pvm.internal.jobexecutor.JobExecutor;
import org.jbpm.pvm.internal.model.ObservableElement;
import org.jbpm.pvm.internal.script.ScriptManager;
import org.jbpm.pvm.internal.session.DbSession;
import org.jbpm.pvm.internal.session.TimerSession;
import org.jbpm.pvm.internal.util.Clock;

/** a runtime timer instance.
 * 
 * <p>Timers can be
 * </p>
 * 
 * @author Tom Baeyens
 * @author Pascal Verdage
 * @author Alejandro Guizar
 */
public class TimerImpl extends JobImpl<Boolean> implements Timer {

  private static final long serialVersionUID = 1L;
  private static final Log log = Log.getLog(TimerImpl.class.getName());

  private final static String dateFormat = "yyyy-MM-dd HH:mm:ss,SSS";

  protected String signalName;
  protected String eventName;
  protected String repeat;
  
  public static final String EVENT_TIMER = "timer";

  public TimerImpl() {
  }
  
  public void schedule() {
    TimerSession timerSession = Environment.getFromCurrent(TimerSession.class);
    timerSession.schedule(this);
  }

  public void setDueDateDescription(String dueDateDescription) {

    ScriptManager scriptManager = EnvironmentDefaults.getScriptManager();
    dueDateDescription = (String) scriptManager.evaluateExpression(dueDateDescription, null);
    
	// BEGIN PATCH
	// when timers are defined jBPM is supposed to support both durations and absolute dates
	// durations are supported by using attribute "duedate" and absolute dates by using "duedatetime"
	// we need to be able to specify absolute dates and we need them to be dynamic (using variables)
	// unfortunately "duedatetime" feature is broken on two fronts:
	//  - conversion to date format happens during jPDL parsing and it's way early to be able to use variables
	//  - when timer implementation is instantiated there is no check to verify that we want to use absolute date instead of duration
	//    which causes either duration to override our value or if we don't have duration at all it fails
	// trying to use "duedatetime" requires too many changes to be made and we want to keep changes to a minimum
	// we are using a workaround approach which amounts to the following
	// - continue to use "duedate"
	// - in the value use prefix "date=" to indicate that we want to use absolute dates
	// - for example: <timer duedate="date=1/1/2010" /> or more importantly we now can use
	//   <timer duedate="#{mytimervar}" /> where mytimervar is "date=1/1/2010"
	// we continue to use the same approach to specify date format, which is through environment variable
	// jbpm.duedatetime.format
	//
    if (dueDateDescription.startsWith("date=")) {
        String dueDateTimeFormatText = (String) Environment.getFromCurrent("jbpm.duedatetime.format");
        if (dueDateTimeFormatText==null) {
          dueDateTimeFormatText = "HH:mm dd/MM/yyyy";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(dueDateTimeFormatText);
        try {
          dueDate = dateFormat.parse(dueDateDescription.substring(5));
        } catch (ParseException e) {
	        throw new JbpmException("Error parsing date "+dueDateDescription);
        }
    }
    else {
	// END PATCH
	    Duration duration = new Duration(dueDateDescription);
	    Date now = Clock.getCurrentTime();
	    
	    if ( duration.isBusinessTime()
	         || duration.getMonths()>0 
	         || duration.getYears()>0
	       ) {
	      Environment environment = Environment.getCurrent();
	      if (environment==null) {
	        throw new JbpmException("no environment to get business calendar for calculating duedate "+dueDateDescription);
	      }
	      BusinessCalendar businessCalendar = environment.get(BusinessCalendar.class);
	      dueDate = businessCalendar.add(now, duration);
	
	    } else {
	      long millis = duration.getMillis() + 
	                    1000*( duration.getSeconds() + 
	                           60*( duration.getMinutes() + 
	                                60*( duration.getHours() + 
	                                     24*( duration.getDays() + 
	                                          7*duration.getWeeks()))));
	      dueDate = new Date(now.getTime() + millis);
	    }
    } // PATCH
  }

  public Boolean execute(Environment environment) throws Exception {
    if (log.isDebugEnabled()) log.debug("executing " + this);

    if (environment==null) {
      throw new JbpmException("environment is null");
    }
    
    if (signalName!=null) {
      if (log.isDebugEnabled()) log.debug("feeding timer signal "+signalName+" into "+execution);
      execution.signal(signalName);
    }
    
    if (eventName!=null) {
      ObservableElement eventSource = execution.getActivity();
      if (log.isDebugEnabled()) log.debug("firing event "+signalName+" into "+eventSource);
      execution.fire(eventName, eventSource);
    }
    
    boolean deleteThisJob = true;
    // if there is no repeat on this timer
    if (repeat==null) {
      // delete the job
      if (log.isDebugEnabled()) log.debug("deleting " + this);
      DbSession dbSession = environment.get(DbSession.class);
      if (dbSession==null) {
        throw new JbpmException("no "+DbSession.class.getName()+" in environment"); 
      }
      dbSession.delete(this);

    } else { // there is a repeat on this timer
      deleteThisJob = false;
      // suppose that it took the timer runner thread a very long time to execute the timers
      // then the repeat action duedate could already have passed
      do {
        setDueDateDescription(repeat);
      } while (dueDate.getTime() <= Clock.getCurrentTime().getTime());

      if (log.isDebugEnabled()) log.debug("rescheduled "+this+" for "+formatDueDate(dueDate));
      
      // release the lock on the timer
      release();
      
      // notify the jobExecutor at the end of the transaction
      JobExecutor jobExecutor = environment.get(JobExecutor.class);
      if (jobExecutor!=null) {
        Transaction transaction = environment.get(Transaction.class);
        if (transaction==null) {
          throw new JbpmException("no transaction in environment");
        }
        JobAddedNotification jobNotificator = new JobAddedNotification(jobExecutor);
        transaction.registerSynchronization(jobNotificator);
      }
    }

    return deleteThisJob;
  }

  public String toString() {
    StringBuilder text = new StringBuilder();
    text.append("timer[");
    text.append(dbid);
    if (dueDate!=null) {
      text.append("|");
      text.append(formatDueDate(dueDate));
    }
    if (signalName!=null) {
      text.append("|");
      text.append(signalName);
    }
    if (eventName!=null) {
      text.append("|");
      text.append(eventName);
    }
    text.append("]");
    return text.toString();
  }

  public static String formatDueDate(Date date) {
      return new SimpleDateFormat(dateFormat).format(date);
  }

  public String getSignalName() {
    return signalName;
  }
  public void setSignalName(String signalName) {
    this.signalName = signalName;
  }
  public String getEventName() {
    return eventName;
  }
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }
  public String getRepeat() {
    return repeat;
  }
  public void setRepeat(String repeat) {
    this.repeat = repeat;
  }
}
