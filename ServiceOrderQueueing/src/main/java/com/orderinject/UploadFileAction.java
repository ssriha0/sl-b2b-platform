package com.orderinject;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import javax.jms.Destination;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate102;

import com.opensymphony.xwork2.ActionSupport;


public class UploadFileAction  extends ActionSupport {

   private static final long serialVersionUID = 481408055815002785L;
   private File file;
   private String contentType;
   private String filename;
   private final String PRODUCTION = "prod";
   private String environmentQue;
   private String correlationId;
   private String environment;
   private JmsTemplate102 testServiceOrderSender;
   private JmsTemplate102 prodServiceOrderSender;
   private Map<String, ? extends Destination> queueDestinations;
   private String errorOutMsg;
   protected final Log logger = LogFactory.getLog(this.getClass());

   private final String ENV1 = "env1";
   private final String ENV2 = "env2";
   private final String ENV3 = "env3";
   private final String ENV4 = "env4";
   private final String QA1 = "qa1";
   private final String QA2 = "qa2";
   private final String QA3 = "qa3";
   private final String QA4 = "qa4";
   
   private JmsTemplate102 env1ServiceOrderSender;
   private JmsTemplate102 env2ServiceOrderSender;
   private JmsTemplate102 env3ServiceOrderSender;
   private JmsTemplate102 env4ServiceOrderSender;
   private JmsTemplate102 qa1ServiceOrderSender;
   private JmsTemplate102 qa2ServiceOrderSender;
   private JmsTemplate102 qa3ServiceOrderSender;
   private JmsTemplate102 qa4ServiceOrderSender;

public String getEnvironmentQue() {
	return environmentQue;
}

public void setEnvironmentQue(String release) {
	this.environmentQue = release;
}

public void setUpload(File file) {
      this.file = file;
   }

   public void setUploadContentType(String contentType) {
      this.contentType = contentType;
   }

   public void setUploadFileName(String filename) {
      this.filename = filename;
   }

   public String execute() {
	   String result = SUCCESS;
	   BufferedReader br = null;
	   try
	   {
			br = new BufferedReader( new FileReader(file));
			StringBuffer sb = new  StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				    //Process the data, here we just print it out
					sb.append(line);
			}
			br.close();
			br = null;
			String orderXML = sb.toString();
			String correlationid = getCorrelationId();
			MyMessageCreator msgCrt = new MyMessageCreator(orderXML, correlationid);
			if (environmentQue.equalsIgnoreCase(PRODUCTION)) {
				 logger.debug("Calling Prod Message Sender with following information \n " + prodServiceOrderSender.getConnectionFactory().toString() + " \n " + prodServiceOrderSender.getDefaultDestination().toString());
				 prodServiceOrderSender.getDefaultDestination().toString();
				 prodServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(ENV1)){
				logger.debug("Calling ENV1 Message Sender with following information \n " + env1ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				env1ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				env1ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(ENV2)){
				logger.debug("Calling ENV2 Message Sender with following information \n " + env2ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				env2ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				env2ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(ENV3)){
				logger.debug("Calling ENV3 Message Sender with following information \n " + env3ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				env3ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				env3ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(ENV4)){
				logger.debug("Calling ENV4 Message Sender with following information \n " + env4ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				env4ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				env4ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(QA1)){
				logger.debug("Calling QA1 Message Sender with following information \n " + qa1ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				qa1ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				qa1ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(QA2)){
				logger.debug("Calling QA2 Message Sender with following information \n " + qa2ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				qa2ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				qa2ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(QA3)){
				logger.debug("Calling QA3 Message Sender with following information \n " + qa3ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				qa3ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				qa3ServiceOrderSender.send(msgCrt);
			}
			else if (environmentQue.equalsIgnoreCase(QA4)){
				logger.debug("Calling QA4 Message Sender with following information \n " + qa4ServiceOrderSender.getConnectionFactory().toString() + " \n " + env1ServiceOrderSender.getDefaultDestination().toString());
				qa4ServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				qa4ServiceOrderSender.send(msgCrt);
			}
			else {
				logger.debug("Calling Test Message Sender with following information \n " + testServiceOrderSender.getConnectionFactory().toString() + " \n " + testServiceOrderSender.getDefaultDestination().toString());
				testServiceOrderSender.setDefaultDestination(queueDestinations.get(environmentQue));
				testServiceOrderSender.send(msgCrt);

			}
			sb = null;

	   }
	   catch (IOException ioe)
	   {
		   ioe.printStackTrace();
		   errorOutMsg = ioe.getMessage();
	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
		   errorOutMsg = e.getMessage();
		   result =  ERROR;
	   }
	   finally {
		   if (br != null) {
				try {
					br.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		   }
	   }
	   return result;
   }


   public  void validate()
   {
	   this.clearErrorsAndMessages();

	   if (getEnvironmentQue().equals("-1"))
	   {
		   addFieldError("environmentQue", "Invalid environment queue selection.");
	   }

	   if (this.getCorrelationId().equals("-1"))
	   {
		   addFieldError("correlationId", "Invalid correlation id selection.");
	   }

	   if (getUploadFileName().equals(""))
	   {
		    addFieldError("upload", "No file is selected");
	   }

	}

   public String getUploadFileName()
   {
	   return this.filename;
   }

	/**
	 * @return the queueDestinations
	 */
	public Map<String, ? extends Destination> getQueueDestinations() {
		return queueDestinations;
	}

	/**
	 * @param queueDestinations the queueDestinations to set
	 */
	public void setQueueDestinations(
			Map<String, ? extends Destination> queueDestinations) {
		this.queueDestinations = queueDestinations;
	}

	public void setEnvironment(String env) {
		this.environment = env;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setErrorOutMsg(String errorMsg) {
		this.errorOutMsg = errorMsg;
	}

	public String getErrorOutMsg() {
		return errorOutMsg;
	}

	/**
	 * @return the testServiceOrderSender
	 */
	public JmsTemplate102 getTestServiceOrderSender() {
		return testServiceOrderSender;
	}

	/**
	 * @param testServiceOrderSender the testServiceOrderSender to set
	 */
	public void setTestServiceOrderSender(JmsTemplate102 testServiceOrderSender) {
		this.testServiceOrderSender = testServiceOrderSender;
	}

	/**
	 * @return the prodServiceOrderSender
	 */
	public JmsTemplate102 getProdServiceOrderSender() {
		return prodServiceOrderSender;
	}

	/**
	 * @param prodServiceOrderSender the prodServiceOrderSender to set
	 */
	public void setProdServiceOrderSender(JmsTemplate102 prodServiceOrderSender) {
		this.prodServiceOrderSender = prodServiceOrderSender;
	}

	public void setCorrelationId(String releaseQue) {
		this.correlationId = releaseQue;
	}

	public String getCorrelationId() {
		return correlationId;
	}

	public JmsTemplate102 getEnv1ServiceOrderSender() {
		return env1ServiceOrderSender;
	}

	public void setEnv1ServiceOrderSender(JmsTemplate102 env1ServiceOrderSender) {
		this.env1ServiceOrderSender = env1ServiceOrderSender;
	}

	public JmsTemplate102 getEnv2ServiceOrderSender() {
		return env2ServiceOrderSender;
	}

	public void setEnv2ServiceOrderSender(JmsTemplate102 env2ServiceOrderSender) {
		this.env2ServiceOrderSender = env2ServiceOrderSender;
	}

	public JmsTemplate102 getEnv3ServiceOrderSender() {
		return env3ServiceOrderSender;
	}

	public void setEnv3ServiceOrderSender(JmsTemplate102 env3ServiceOrderSender) {
		this.env3ServiceOrderSender = env3ServiceOrderSender;
	}

	public JmsTemplate102 getEnv4ServiceOrderSender() {
		return env4ServiceOrderSender;
	}

	public void setEnv4ServiceOrderSender(JmsTemplate102 env4ServiceOrderSender) {
		this.env4ServiceOrderSender = env4ServiceOrderSender;
	}

	public JmsTemplate102 getQa1ServiceOrderSender() {
		return qa1ServiceOrderSender;
	}

	public void setQa1ServiceOrderSender(JmsTemplate102 qa1ServiceOrderSender) {
		this.qa1ServiceOrderSender = qa1ServiceOrderSender;
	}

	public JmsTemplate102 getQa2ServiceOrderSender() {
		return qa2ServiceOrderSender;
	}

	public void setQa2ServiceOrderSender(JmsTemplate102 qa2ServiceOrderSender) {
		this.qa2ServiceOrderSender = qa2ServiceOrderSender;
	}

	public JmsTemplate102 getQa3ServiceOrderSender() {
		return qa3ServiceOrderSender;
	}

	public void setQa3ServiceOrderSender(JmsTemplate102 qa3ServiceOrderSender) {
		this.qa3ServiceOrderSender = qa3ServiceOrderSender;
	}

	public JmsTemplate102 getQa4ServiceOrderSender() {
		return qa4ServiceOrderSender;
	}

	public void setQa4ServiceOrderSender(JmsTemplate102 qa4ServiceOrderSender) {
		this.qa4ServiceOrderSender = qa4ServiceOrderSender;
	}
		
}

