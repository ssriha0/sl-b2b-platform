package com.servicelive.serviceordercreation;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;

import com.servicelive.serviceordercreation.util.FileUtil;

/**
 * 
 * @author svanloon
 *
 */
public class MainApp {

	private String propertyFileName;
	private String outputDirectory;
	private String templateFileName;
	private PropertyManagerUtil propertyManagerUtil;
	private String outputFileNameTemplate;
	private int numberOfServiceOrdersToCreate;
	private int nextSequence;
	private Boolean sendToMQ = Boolean.FALSE;
	private Calendar cal = Calendar.getInstance();

	private Map<String, String> params = new HashMap<String, String>();
	private Map<String, List<String>> multiplierParams = new HashMap<String, List<String>>();
	private JmsTemplate jmsTemplateSoMessageSender;
	private MessageCorrelationIDBaseDistributor distributor;
	private static final String UNIT_NUM_KEY = "%%%ServUnNum%%%";
	private static final String ORDER_NUM_KEY = "%%%ServOrdNum%%%";
	
	private static ApplicationContext ctx =  null;
	static {
		ctx = new ClassPathXmlApplicationContext("app-context_so.xml");
	}
		

	/**
	 * 
	 */
	public MainApp() {
		super();
	}

	private static final String KEY_NEXT_SEQUENCE = "sequence.next_val";

	private void initialize() {
		propertyManagerUtil = new PropertyManagerUtil(propertyFileName);
		outputDirectory = propertyManagerUtil.getProperty("outputDirectory");
		templateFileName = propertyManagerUtil.getProperty("templateFileName");
		outputFileNameTemplate = propertyManagerUtil.getProperty("outputFileName");

		numberOfServiceOrdersToCreate = Integer.parseInt(propertyManagerUtil.getProperty("numberOfServiceOrdersToCreate"));

		nextSequence = Integer.parseInt(propertyManagerUtil.getProperty(KEY_NEXT_SEQUENCE));
		sendToMQ = Boolean.valueOf(propertyManagerUtil.getProperty("send.to.mq"));

		Set<String> keySet = propertyManagerUtil.keySet();
		for(String key:keySet) {
			if(key.startsWith("var.") && key.endsWith(".template")) {
				String variableName = key.substring("var.".length(), key.length() - ".template".length());
				String template = propertyManagerUtil.getProperty(key);
				System.out.println(""+"\t"+ variableName + "->" + template);
				params.put("%%%" + variableName + "%%%", template);
			}

			if(key.startsWith("multiplier.") && key.endsWith(".template")) {
				String variableName = key.substring("multiplier.".length(), key.length() - ".template".length());
				String template = propertyManagerUtil.getProperty(key);
				multiplierParams.put("%%%" + variableName + "%%%", createList(template));
			}
		}

		if(outputDirectory == null ||
		   templateFileName == null ||
		   outputFileNameTemplate == null) {
			throw new RuntimeException("outputDirectory, templateFileName, and outputFileNameTemplate should all have values in the property file");
		}
		
		if(sendToMQ){
		  jmsTemplateSoMessageSender =
				(JmsTemplate)ctx.getBean("jmsTemplateSOSender");
		  distributor = (MessageCorrelationIDBaseDistributor) ctx.getBean("messageCorrelationDistributor");
		  if(jmsTemplateSoMessageSender == null) {
			  throw new RuntimeException("JmsTemplate to Send So is NULL");
		  }
		  if(distributor == null ) { 
			  throw new RuntimeException(" EVEN Distributor is NULL");
		  }
		}
	}

	private List<String> createList(String template) {
		String[] split = template.split(",");
		List<String> results = new ArrayList<String>();
		for(String temp:split) {
			if(temp == null || temp.trim().equals("")) {
				continue;
			}
			temp = temp.trim();
			results.add(temp);
		}
		return results;
	}
	/**
	 * @throws MalformedURLException 
	 * 
	 */
	public void execute() throws MalformedURLException {
		initialize();
		

		URL url = new File(templateFileName).toURL();
		try {
			StringBuilder templateStringBuilder = FileUtil.readFile(url);
			StringBuilder header = new StringBuilder("<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n");
			header.append("<InstallationServiceOrders>\n");
			StringBuilder footer = new StringBuilder("</InstallationServiceOrders>\n");

			
			String fileNameEtx = sendToMQ.booleanValue() ? ".MQ" : null;
			String outputFileNameInstance = substituteVariables(outputFileNameTemplate) ;
			if(fileNameEtx != null) {
				outputFileNameInstance = outputFileNameInstance + fileNameEtx;
			}
			String fileName = outputDirectory + "/" + outputFileNameInstance;
			File outputFile = new File(fileName);
			OutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
			System.out.println("writting to " + outputFile.getAbsolutePath());
			writeToFile(os, header);
			for(int i = 0; i < numberOfServiceOrdersToCreate; i++) {
				StringBuilder orderNumUnitNumbertemplate= new StringBuilder(params.get(ORDER_NUM_KEY) + params.get(UNIT_NUM_KEY));
				String orderNumUnitNumber = null;
				StringBuilder copy = copy(templateStringBuilder);
				for(String key:params.keySet()) {
					replace(copy, key, substituteVariables(params.get(key)));
				}
				for(String key:multiplierParams.keySet()) {
					List<String> values = multiplierParams.get(key);
					String value = values.get(nextSequence%values.size());
					replace(copy, key, substituteVariables(value));
					/*if(key.equalsIgnoreCase("#index#")) {
						orderNumUnitNumber = replace(orderNumUnitNumbertemplate,key,substituteVariables(value)).toString();
					}*/
				}
				if(sendToMQ) {
					if(params.get(UNIT_NUM_KEY) == null || params.get(ORDER_NUM_KEY) == null) {
						System.out.println(" Found NULL Order Number Unit nuber for ");
						continue;
					}
					else {
						orderNumUnitNumber = substituteVariables(orderNumUnitNumbertemplate.toString());
						if(orderNumUnitNumber != null) {
							sendMQMessage(copy,header.toString(),footer.toString(),orderNumUnitNumber);
						}
						else {
							System.out.println("OrderNumber Template replacement is failing ");
						}
					}
					
				}
				writeToFile(os, copy);
				incrementVariables();
			}
			
			writeToFile(os, footer);
			os.flush();
			os.close();
			if(sendToMQ) {
				deleteFile(fileName);
				distributor.spitPoolReport();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception ee) {
			ee.printStackTrace();
		}
	}

	
	private void deleteFile(String fileName) throws IOException{
		File outputFile = new File(fileName);
		outputFile.delete();
	}
	private void incrementVariables() {
		cal = Calendar.getInstance();
		nextSequence++;
		propertyManagerUtil.setProperty(KEY_NEXT_SEQUENCE, String.valueOf(nextSequence));
	}

	private String substituteVariables(String template) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");

		Date date = cal.getTime();
		Calendar clone = (Calendar) cal.clone();
		clone.add(Calendar.DATE, 1);
		Date tomorrow = clone.getTime();
		String result = template;
		result = result.replaceAll("#date#", sdf.format(date));
		result = result.replaceAll("#today#", sdf2.format(date));
		result = result.replaceAll("#tomorrow#", sdf2.format(tomorrow));
		result = result.replaceAll("#index#", String.valueOf(nextSequence));
		return result;
	}
	
	private void sendMQMessage(StringBuilder copy,String header, String footer,String orderUnitNumValue) throws Exception {
		StringBuilder message = new StringBuilder(header);
		message.append(copy);
		message.append(footer);
		String corid = distributor.getCorrelationId(orderUnitNumValue);
		//jmsTemplateSoMessageSender.convertAndSend(message.toString());
		MyMessageCreator mc = new MyMessageCreator(message.toString(),corid);
		jmsTemplateSoMessageSender.send(mc);
		System.out.println(" Sending Message with Message  with OrderNumber  "+orderUnitNumValue + "  with CorrelationId  =   "+ corid + "    SENT");
		distributor.increasePoolbyOne(corid);
		
	}
	private void writeToFile(OutputStream os, StringBuilder sb) throws IOException {
		char[] copyChars = new char[sb.length()];
		sb.getChars(0, sb.length(), copyChars, 0);
		
		for(char sChar:copyChars) {
			os.write(sChar);
		}
	}


	private StringBuilder copy(StringBuilder originalStringBuilder) {
		char[] copyChars = new char[originalStringBuilder.length()];
		originalStringBuilder.getChars(0, originalStringBuilder.length(), copyChars, 0);

		StringBuilder sb = new StringBuilder();
		sb.append(copyChars);
		return sb;
	}


	private StringBuilder replace(StringBuilder pSb, String findString, String replaceValue) {
		//
		StringBuilder sb = pSb;
		int i = sb.indexOf(findString);
		while(i >= 0) {
			sb = sb.replace(i, i + findString.length(), replaceValue);
			i = sb.indexOf(findString);
		}
		
		return sb;
	}

	public static void main(String...args) throws MalformedURLException {
		System.out.println("start...");
		try {
			if(args.length != 1) {
				System.out.println("need the serviceOrderCreation.properties file as the first argument");
				return;
			}
	
			String propertyFileName = args[0];
	
			MainApp mainApp = new MainApp();
			mainApp.setPropertyFileName(propertyFileName);
			mainApp.execute();
		} finally {
			System.out.println("end...");
		}
	}

	/**
	 * @param propertyFileName the propertyFileName to set
	 */
	public void setPropertyFileName(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}
}
