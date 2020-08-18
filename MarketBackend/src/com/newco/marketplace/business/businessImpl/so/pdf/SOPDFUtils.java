package com.newco.marketplace.business.businessImpl.so.pdf;

import java.io.ByteArrayInputStream;
import java.sql.Timestamp;
import java.text.CharacterIterator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;
import java.util.regex.Pattern;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.awt.Color;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.List;
import com.lowagie.text.ListItem;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.newco.marketplace.dto.vo.serviceorder.ServiceOrder;
import com.newco.marketplace.util.constants.SOPDFConstants;
import com.newco.marketplace.utils.DateUtils;
import com.servicelive.common.BusinessServiceException;

public class SOPDFUtils {
	
	private static final Logger LOGGER = Logger.getLogger(SOPDFUtils.class);
	
	/**
	 * Method to get appointment dates from Service Order
	 * @param ServiceOrder so
	 * @return String appointmentDates
	 */
	public static String appointmentDates(ServiceOrder so){
		String appointmentDates = "";
		if(so.getServiceDate1()!=null && so.getServiceDate1().toString().trim().length() > 1) {		    	
			if(so.getServiceDate2()!=null && so.getServiceDate2().toString().trim().length() > 1) {
				if(StringUtils.isNotEmpty(so.getServiceDate1().toString()) && StringUtils.isNotEmpty(so.getServiceDate2().toString())){
					Date appointDate1 = Timestamp.valueOf(getStringFromTimeStamp(so.getServiceDate1())+" 00:00:00.0");
					Date appointDate2 = Timestamp.valueOf(getStringFromTimeStamp(so.getServiceDate2())+" 00:00:00.0");					
					appointmentDates = DateUtils.getFormatedDate(appointDate1, "MMM. d, yyyy")
							+" - "+ DateUtils.getFormatedDate(appointDate2, "MMM. d, yyyy");
				}
			} else {
				if(StringUtils.isNotEmpty(so.getServiceDate1().toString())) {
					Date appointDate1 = Timestamp.valueOf(getStringFromTimeStamp(so.getServiceDate1())+" 00:00:00.0");
					appointmentDates = DateUtils.getFormatedDate(appointDate1, "MMM. d, yyyy");
				}
			}
		}
		return appointmentDates;
	}
	
	/**
	 * Method to get the service winodw details for a given service order
	 * @param  ServiceOrder so
	 * @return String serviceWindow
	 */
	public static String serviceWindow(ServiceOrder so){
		String serviceWindow = "";
		if(so.getServiceTimeStart()!=null){
			if(so.getServiceTimeEnd()!=null){
				if(!so.getServiceTimeStart().equals("[HH:MM]") && !so.getServiceTimeEnd().equals("[HH:MM]")){
					serviceWindow = StringUtils.removeStart(so.getServiceTimeStart(), "0") + " - " + StringUtils.removeStart(so.getServiceTimeEnd(), "0");
				}
			}else{
				if(!so.getServiceTimeStart().equals("[HH:MM]")){
					serviceWindow = StringUtils.removeStart(so.getServiceTimeStart(), "0");
				}
			}			
		}
		return serviceWindow;
	}
	
	/**
	 * Method to get date string from given time stamp
	 * @param timeStamp
	 * @return String
	 */
	public static String getStringFromTimeStamp(Timestamp timeStamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date = null;
		try {
			date = sdf.parse(timeStamp.toString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sdf.format(date);
	}

	/**
	 *
	 *
	 */
	public static class Checker { 
		Object obj;
		String delimit = "delimit!0";

		Checker(Object pObj) {
			this.obj = pObj;
		}

		public String appendWithParentheses(String child) {
			String build = appendWith(child);
			build = build.replaceAll(delimit, "").replaceFirst(child,
					"(" + child + ")");
			return build;
		}

		String formatDate() {
			if (obj != null) {
				SimpleDateFormat outDate = new SimpleDateFormat(
						"EEE, d MMM yyyy");
				return outDate.format(obj);
			}
			return null;
		}

		String formatDateAsEmpty() {
			if (obj != null) {
				SimpleDateFormat outDate = new SimpleDateFormat(
						"EEE, d MMM yyyy");
				return outDate.format(obj);
			}
			return "";
		}

		boolean isNull() {
			return obj == null ? true : false;
		}

		boolean isNotNull() {
			return obj != null ? true : false;
		}

		String returnValueAsEmpty() {
			return (String) (obj == null ? "" : obj);
		}

		String returnValueAsNull() {
			return obj == null ? null : (String) obj;
		}

		String prependWith(String child, String append) {
			String swap = null;
			if (obj == null){
				swap = "";
			}
			else{
				swap = obj.toString();
			}
			obj = child;
			String build = appendWith(swap);
			build = build.replaceAll(delimit, append);
			return build;
		}

		String appendWith(String child, String append) {
			String build = appendWith(child);
			build = build.replaceAll(delimit, append);
			return build;
		}

		private String appendWith(String child) {
			String build = "";
			if (obj == null){
				build = "";
			}
			else if (obj.toString().trim().equals("")){
				build = "";
			}
			else{
				build = (String) obj + " ";
			}

			if (StringUtils.isNotBlank(child)) {
				if (build.equals("")) {
					build = child;
				}
				else {
					build = build + delimit + child;
				}
			}
			return build;
		}
		
		private String appendWithForPickup(String child) {
			String build = "";
			if (obj == null){
				build = "";
			}
			else if (obj.toString().trim().equals("")){
				build = "";
			}
			else{
				build = (String) obj;
			}

			if (StringUtils.isNotBlank(child)) {
				if (build.equals("")) {
					build = child;
				}
				else {
					build = build + delimit + child;
				}
			}
			return build;
		}

		String appendWithComma(String child) {
			String build = appendWith(child);
			build = build.replaceAll(delimit, ",");
			return build;
		}
		
		String appendWithCommaForPickup(String child) {
			String build = appendWithForPickup(child);
			build = build.replaceAll(delimit, ",");
			return build;
		}
		
		
	}
	
	/**
	 * Method to append strings
	 * @param param1
	 * @param param2
	 * @param app
	 * @return String
	 */
	public static String append(String param1, String param2, String app) {
		if (param1 != null && param2 != null) {
			return param1 + app + param2;
		} else if (param1 != null && param2 == null) {
			return param1;
		} else if (param1 == null && param2 != null) {
			return param2;
		} else {
			return null;
		}
	}
	
	/**
	 * Method to add logo image to the Provider Instructions PDF header
	 * @param proInstMainTable
	 * @param logoImage
	 * @param cell
	 */
	public static void addLogoImage(PdfPTable proInstMainTable,Image logoImage){
		PdfPCell cell = new PdfPCell(logoImage);
		cell.setBorder(0);
		proInstMainTable.addCell(cell);
	}
	
	
	/**
	 * Method to add cells to the main table
	 * @param elementList
	 * @param border
	 * @param colspan
	 * @param height
	 * @param proInstMainTable
	 */
	public static void addCellToTable(List elementList,int border,int colspan,int height,PdfPTable proInstMainTable,PdfPTable subTable){
		PdfPCell cell = new PdfPCell();
		if(border >= 0){
			cell.setBorder(border);
		}
		if(colspan >= 0){
			cell.setColspan(colspan);
		}
		if(height >= 0){
			cell.setMinimumHeight(height);
		}
		cell.addElement(elementList);
		if(null!= subTable){
			subTable.addCell(cell);
			proInstMainTable.addCell(subTable);
		}else{
			proInstMainTable.addCell(cell);
		}
	}
	
	/**
	 * @param elementList
	 * @param image
	 * @param border
	 * @param colspan
	 * @param height
	 * @param proInstMainTable
	 * @param subTable
	 */
	public static void addListAndImageToTable(List elementList,Image image, int border,int colspan,int height,PdfPTable proInstMainTable,PdfPTable subTable){
		PdfPCell cell = new PdfPCell();
		if(border >= 0){
			cell.setBorder(border);
		}
		if(colspan >= 0){
			cell.setColspan(colspan);
		}
		if(height >= 0){
			cell.setMinimumHeight(height);
		}
		cell.addElement(elementList);
		if(null!= subTable){
			subTable.addCell(cell);
			if(null != image){
				PdfPCell provImageCell = new PdfPCell(image);
				provImageCell.setBorder(0);
				provImageCell.setColspan(1);
				provImageCell.setHorizontalAlignment(Element.ALIGN_CENTER);
				subTable.addCell(provImageCell);
			}			
			proInstMainTable.addCell(subTable);
		}else{
			proInstMainTable.addCell(cell);
		}
	}

	/**
	 * Method to add empty row to table
	 * @param colspan
	 * @param border
	 * @param minHeight
	 * @param tableMain
	 */
	 public static void addEmptyRow(int colspan, int border, int minHeight, PdfPTable tableMain){	            
		 PdfPCell cell = new PdfPCell();           
	     	if(colspan >= 0){
	     		cell.setColspan(colspan);
	        }
	        if(border >= 0){
	            cell.setBorder(border);
	        }
	        if(minHeight >= 0){
	            cell.setMinimumHeight(minHeight);
	        }           
	        tableMain.addCell(cell);
	 }
	/**
	 * Method to format the phone number(0000000000) to 000-000-0000 
	 * @param phoneNumber
	 * @return
	 */
	public static String formatPhoneNumber(String phoneNumber){
			String formattedPhone = StringUtils.EMPTY;
			if(null!=phoneNumber){
				if(phoneNumber.length()>2 && !phoneNumber.contains(SOPDFConstants.HYPHEN)){
					formattedPhone=phoneNumber.substring(0, 3);
					if(phoneNumber.length()>5){
						formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(3, 6);
					}else if(phoneNumber.length()>3){
						formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(3);
					}
					if(phoneNumber.length()>6){
						formattedPhone=formattedPhone+SOPDFConstants.HYPHEN +phoneNumber.substring(6);
					}
				}else{
					formattedPhone=phoneNumber;
				}
			}
			return formattedPhone;
	}
	/**Description:Method to format the phone number(000-000-0000) to  0000000000
	 * @param phoneNumber
	 * @return
	 */
	public static String removeHyphenFromPhoneNumber(String phoneNumber){
		String formattedPhone = StringUtils.EMPTY;
		if(StringUtils.isNotBlank(phoneNumber)){
			formattedPhone=StringUtils.remove(phoneNumber, SOPDFConstants.HYPHEN);
		}
		return formattedPhone;
	}
	public static void drawLine(PdfPTable containerTable, int colSpan) {
		PdfPCell lineCell = new PdfPCell();
		lineCell.setBorder(0);
		lineCell.setColspan(colSpan);
		lineCell.setBorderWidthTop(1.0f); // Default line thickness
		lineCell.setBorderWidthBottom(0);
		lineCell.setBorderWidthLeft(0);
		lineCell.setBorderWidthRight(0);
		containerTable.addCell(lineCell);
	}
	public static void drawMiddleLine(Document document,PdfWriter writer) {


		PdfContentByte cb = writer.getDirectContent();
		cb.moveTo(70f,writer.getVerticalPosition(true));

		cb.lineTo(530f,writer.getVerticalPosition(true));

		cb.stroke();
	}
	
	/**SL-20728
	 * This method split the string at "." and return each sentence as a list item
	 * @param htmlContent 
	 * @return String
	 * @throws BusinessServiceException 
	 */

	public static String getTaskComments(String htmlContent) throws BusinessServiceException {
		
		final StringBuilder builder = new StringBuilder();
		
		try{
			if(htmlContent.contains(SOPDFConstants.PERIODS)){
				builder.append(SOPDFConstants.UL_TAG);
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser saxParser = factory.newSAXParser();
				
				DefaultHandler handler = new DefaultHandler() {
					
					Stack<String> elementStack = new Stack<String>();
					java.util.List<String> startList = new ArrayList<String>();
					Stack<String> endStack = new Stack<String>();
					
					boolean liOpen = false;
					
					public void startElement(String uri, String localName,String tagName, 
							Attributes attributes) throws SAXException {
						
						String startTags = "";
						String endTags = "";
						
						startTags = startTags + SOPDFConstants.START_LT + tagName ;
						//If the tag has attributes
						if(null != attributes && attributes.getLength() > 0){
							
							for(int i=0; i<attributes.getLength(); i++){
								if(null != attributes.getQName(i) && null != attributes.getValue(i)){
		   					 		startTags =  startTags + SOPDFConstants.SPACE + attributes.getQName(i) + "=\""+ attributes.getValue(i)+ "\" ";
	   					 		}
							}
						}
						startTags = startTags + SOPDFConstants.START_GT;
						endTags = SOPDFConstants.END_LT + tagName + SOPDFConstants.START_GT;
	    				elementStack.push(tagName);
	    				startList.add(startTags);
	    				endStack.push(endTags);
					}
					
					public void characters(char ch[], int start, int length) throws SAXException {

						String messageContent = new String(ch, start, length);
						String[] soComments =  messageContent.split(SOPDFConstants.PERIOD); 
						String tagName = elementStack.peek();
						String startTags ="";
						String endTags ="";
						for(String s : startList){
							startTags = startTags + (s);
						}
						for(String s : endStack){
							endTags = (s) + endTags;
						}
							if (!SOPDFConstants.ANCHOR_TAG.equalsIgnoreCase(tagName)) {
								if(messageContent.contains(SOPDFConstants.PERIODS)){

									String lastLetter = messageContent.substring((messageContent.length()-1), messageContent.length());	

									for(int i=0;i<soComments.length;i++){

										String comment = soComments[i];
										if(!liOpen){
											builder.append(SOPDFConstants.LI_TAG);
											comment = soComments[i].replaceAll("^\\s+", "");
											comment = SOPDFConstants.SPACE + comment;
											liOpen = true;
										}
										builder.append(startTags + SOPDFUtils.encode(comment));
										if(i == soComments.length-1){
											if(SOPDFConstants.PERIODS.equals(lastLetter)){
												builder.append(SOPDFConstants.PERIODS + endTags);
												if(liOpen){
													builder.append(SOPDFConstants.LI_END_TAG);
													liOpen = false;
												}
											}
											else{
												builder.append(endTags);
												liOpen = true;
											}
										}else{
											builder.append(SOPDFConstants.PERIODS);
											builder.append(endTags);
											if(liOpen && soComments.length>1){
												builder.append(SOPDFConstants.LI_END_TAG);
												liOpen = false;
											}
										}
									}
								}else{
									if(!liOpen){
										messageContent = messageContent.replaceAll("^\\s+", "");
										messageContent = SOPDFConstants.SPACE + messageContent;
										builder.append(SOPDFConstants.LI_TAG);
										liOpen = true;
									}
									builder.append(startTags + SOPDFUtils.encode(messageContent) + endTags);
								}
							}
							else{
								if(!liOpen){
									builder.append(SOPDFConstants.LI_TAG);
									liOpen = true;
								}
								builder.append(startTags + SOPDFUtils.encode(messageContent) + endTags);
							}
					}
					
					public void endElement(String uri, String localName,
							String tagName) throws SAXException {

						
						if(!elementStack.isEmpty()){
							elementStack.pop();
						}
						if(!startList.isEmpty()){
							startList.remove(startList.size()-1);
						}
						String endTags ="";
						
						if(!endStack.isEmpty()){
							for(String s : endStack){
								endTags = (s)+endTags;
							}
							endStack.pop();
						}
						if(elementStack.isEmpty() && liOpen){
							builder.append(SOPDFConstants.LI_END_TAG);
							liOpen = false;
						}
					}
				};
				
				saxParser.parse(new ByteArrayInputStream(htmlContent.getBytes("UTF-8")), handler);
				builder.append(SOPDFConstants.UL_END_TAG);
			}
			else{
				return htmlContent;
			}
		}
		catch(Exception e){
			throw new BusinessServiceException("Exception while converting rich text for task comments:" + e);
		}
		return builder.toString();
	}
	
	/**SL-20728
	 * Description:This method is used to display the rich text area content with all the styles applied
	 * @param defaultFont 
	 * @param strTermsCond 
	 * @param checkBox
	 * @return
	 * @throws BusinessServiceException 
	 */

	public static List getRichText(String htmlContent, final Font defaultFont, final Image checkBox, final String listSymbol) throws BusinessServiceException {
		
		final List finalList = new List();
		finalList.setListSymbol(listSymbol);
		if(null != checkBox && 	!htmlContent.contains(SOPDFConstants.LI_END_TAG)){
			finalList.setListSymbol(new Chunk(checkBox,3,-2));
		}
		try{
			
	        SAXParserFactory factory = SAXParserFactory.newInstance();
	    	SAXParser saxParser = factory.newSAXParser();
	    	
	    	DefaultHandler handler = new DefaultHandler() {
	    		
	    		Stack<String> innerListIdntfrStack = new Stack<String>();
	    		Stack<ListItem> listItemStack = new Stack<ListItem>();
	    		Stack<List> orderedStack = new Stack<List>();
	    		Stack<List> bulletStack = new Stack<List>();
	    		Stack<ListItem> phraseItemStack = new Stack<ListItem>();
	    		Stack<String> attributeStack = new Stack<String>();
	    		Stack<Integer> countStack = new Stack<Integer>();
	    		
	    		Anchor anchor = null;
	    		List numberedList = null;
	    		List bulletList = null;
	    		String allign = null;
	    		float fontInt = 0;
	    		float paddingPix = 0;
	    		StringBuilder anchorContent = new StringBuilder();
	    		Font font = null;
	    		boolean listSymbolNotRequired = false;
	    		boolean listEmpty = false;
	    		
	    		boolean strong = false;
	    		boolean italics = false;
	    		boolean underline = false;
	    		
	    		boolean h1 = false;
	    		boolean h2 = false;
	    		boolean h3 = false;
	    		boolean h4 = false;
	    		boolean h5 = false;
	    		boolean h6 = false;
	    		
	    		public void startElement(String uri, String localName,String tagName, 
	                    Attributes attributes) throws SAXException {
	    			
	    			
	    			setAttributes(tagName, attributes);
	    				    			
					if (SOPDFConstants.STRONG_TAG.equalsIgnoreCase(tagName) 
							|| SOPDFConstants.BOLD_TAG.equalsIgnoreCase(tagName)) {
	    				strong = true;
	    			}
	    			else if (SOPDFConstants.ITALICS_TAG.equalsIgnoreCase(tagName) 
	    					|| SOPDFConstants.I_TAG.equalsIgnoreCase(tagName)) {
	    				italics = true;
	    			}
	    			else if (SOPDFConstants.UNDERLINE_TAG.equalsIgnoreCase(tagName)) {
	    				underline = true;
	    			}
	    			else if (SOPDFConstants.PARA_TAG.equalsIgnoreCase(tagName)) {

	    				//<span>, <strong>,<em> and <a> tags will come under <p> tag
	    				//So for <p> tag, create a new list item and add it to phrase stack
	    				phraseItemStack.push(new ListItem());
	    			}
	    			else if(SOPDFConstants.HEADING_1.equalsIgnoreCase(tagName)){
	    				h1 = true;
	    			}
	    			else if(SOPDFConstants.HEADING_2.equalsIgnoreCase(tagName)){
	    				h2 = true;
	    			}
	    			else if(SOPDFConstants.HEADING_3.equalsIgnoreCase(tagName)){
	    				h3 = true;
	    			}
	    			else if(SOPDFConstants.HEADING_4.equalsIgnoreCase(tagName)){
	    				h4 = true;
	    			}
	    			else if(SOPDFConstants.HEADING_5.equalsIgnoreCase(tagName)){
	    				h5 = true;
	    			}
	    			else if(SOPDFConstants.HEADING_6.equalsIgnoreCase(tagName)){
	    				h6 = true;
	    			}
	    			else if(SOPDFConstants.UNORDERED_LIST_TAG.equalsIgnoreCase(tagName)){
	    				
	    				//This is to maintain the order of nested lists
	    				innerListIdntfrStack.push(SOPDFConstants.UNORDERED_LIST_TAG);
	    				
	    				//create a new unordered list and add it to unordered list stack
	    				bulletList = new List(List.UNORDERED);
	    				bulletStack.push(bulletList);
	    				
	    			}
	    			else if(SOPDFConstants.ORDERED_LIST_TAG.equalsIgnoreCase(tagName)){
	    				
	    				//This is to maintain the order of nested lists
	    				innerListIdntfrStack.push(SOPDFConstants.ORDERED_LIST_TAG);
	    				
	    				//create a new ordered list and add it to ordered list stack
	    				numberedList = new List();
	    				orderedStack.push(numberedList);
	    				countStack.push(1);
	    			}
	    			else if(SOPDFConstants.LIST_TAG.equalsIgnoreCase(tagName)){
	    				
	    				//<span>, <strong>,<em> and <a> tags will come under <li> tag
	    				//So for <li> tag, create a new list item and add it to list stack
	    				listItemStack.push(new ListItem());
	    			}
	    								
					if((h1 ||h2 || h3 || h4 || h5 || h6) && phraseItemStack.isEmpty()){
						//Heading tags may or may not be enclosed by other tags
	    				//Create a new list item and add it to phrase stack
	    				phraseItemStack.push(new ListItem());
					}
	    		}

				/**This method gets the attributes of the tag and set the styles
				 * @param tagName
				 * @param attributes
				 */
				private void setAttributes(String tagName, Attributes attributes) {
					//If the tag is having style attribute, get the styles from the attribute value
	    			//and push it to the stack
	    			StringBuffer styleList = new StringBuffer();
	    			
	    			if(null != attributes && attributes.getLength() > 0){
	    				
	    				//If the anchor tag has attributes, initialize an anchor object
	    				if(SOPDFConstants.ANCHOR_TAG.equalsIgnoreCase(tagName)){
	    					anchor = new Anchor();
	    				}
	    				
	    				for(int i=0; i<attributes.getLength(); i++){
	    					
	    					String attribute = attributes.getValue(i).trim();
	    					
	    					if(SOPDFConstants.STYLE.equalsIgnoreCase(attributes.getQName(i)) && null != attributes.getValue(i)){
	    						
	    						//check whether the style tag has underline
	    						if(attribute.contains(SOPDFConstants.UNDERLINE_ATT)){
			    					underline = true;
			    					styleList.append(SOPDFConstants.UNDERLINE_ATT).append(SOPDFConstants.COMMA);
			    				}
	    						
	    						//check whether the style tag has font-size
	    						if(attribute.contains(SOPDFConstants.FONT_SIZE)){
		    						String fontSize = attribute.substring(attribute.indexOf(SOPDFConstants.FONT_SIZE) + 
		    								SOPDFConstants.FONT_SIZE.length(), attribute.indexOf(SOPDFConstants.FONT_PT));
		    						if(StringUtils.isNumeric(fontSize)){
		    							fontInt = Float.parseFloat(fontSize);
		    						}
		    						styleList.append(SOPDFConstants.FONT_SIZE).append(SOPDFConstants.COMMA);
		    					}
	    						
	    						//check whether the style tag has text-align
	    						if(attribute.contains(SOPDFConstants.ALLIGN)){
    								if(attribute.contains(SOPDFConstants.ALLIGN_RIGHT)){
    									allign = SOPDFConstants.ALLIGN_RIGHT;
    								}
    								else if(attribute.contains(SOPDFConstants.ALLIGN_LEFT)){
    									allign = SOPDFConstants.ALLIGN_LEFT;
    								}
    								else if(attribute.contains(SOPDFConstants.ALLIGN_MEDIUM)){
    									allign = SOPDFConstants.ALLIGN_MEDIUM;
    								}
    								else if(attribute.contains(SOPDFConstants.ALLIGN_JUSTIFY)){
    									allign = SOPDFConstants.ALLIGN_JUSTIFY;
    								}
    								styleList.append(SOPDFConstants.ALLIGN).append(SOPDFConstants.COMMA);
    							}
	    						
	    						//check whether the style tag has padding
	    						if(attribute.contains(SOPDFConstants.INDENT)){
    								String padding = attribute.substring(attribute.indexOf(SOPDFConstants.INDENT) + 
    										SOPDFConstants.INDENT.length(), attribute.indexOf(SOPDFConstants.FONT_PX));
    								if(StringUtils.isNumeric(padding)){
    									paddingPix = Float.parseFloat(padding);
		    						}
    								styleList.append(SOPDFConstants.INDENT).append(SOPDFConstants.COMMA);
    							}
	    					}
	    					//This is only for anchor tag
	    					else if(SOPDFConstants.ANCHOR_TAG.equalsIgnoreCase(tagName)){
	    						
	    						if(SOPDFConstants.TEXT_TITLE.equalsIgnoreCase(attributes.getQName(i))){
			    					anchor.setName(attribute);
			    				}
	   					 		else if(SOPDFConstants.HREF.equalsIgnoreCase(attributes.getQName(i))){
			    					anchor.setReference(attribute);
			    				}
	    					}
	    				}
	    				
	    				attributeStack.push(styleList.toString());
	    			}
	    			//If no style attributes
	    			if(StringUtils.isBlank(styleList.toString())){
	    				attributeStack.push(SOPDFConstants.NO_ATTRIBUTE);
	    			}
				}
	    		
	    		public void characters(char ch[], int start, int length) throws SAXException {
	    			
	    			String messageContent = new String(ch, start, length);
	    				    			
	    			int style = 0;
	    			int allignment = 0;
	    			float spacingBefore = 0;
	    			
	    			if(fontInt !=0){
		    			font = FontFactory.getFont(FontFactory.HELVETICA, fontInt);
		    			spacingBefore = fontInt*50/100;
	    			}
	    			else{
	    				font = defaultFont;
	    			}
	    			
	    			if(h1){
	    				font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
	    				style = Font.BOLD;
	    			}
	    			else if(h2){
	    				font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 13);
	    				style = Font.BOLD;
	    			}
	    			else if(h3){
	    				font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
	    				style = Font.BOLD;
	    			}
	    			else if(h4){
	    				font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
	    				style = Font.BOLD;
	    			}
	    			else if(h5){
	    				font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
	    				style = Font.BOLD;
	    			}
	    			else if(h6){
	    				font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10);
	    				style = Font.BOLD;
	    			}
	    			
	    			if(strong){	   
	    				style = Font.BOLD;
	    			}
	    			if(italics){
	    				style = style|Font.ITALIC;
	    			}	    			
	    			if(underline){
	    				style = style|Font.UNDERLINE;
	    			}
	    			
	    			if(SOPDFConstants.ALLIGN_LEFT.equalsIgnoreCase(allign)){
	    				allignment = Element.ALIGN_LEFT;
	    			}
	    			else if(SOPDFConstants.ALLIGN_MEDIUM.equalsIgnoreCase(allign)){
	    				allignment = Element.ALIGN_CENTER;
	    			}
	    			else if(SOPDFConstants.ALLIGN_RIGHT.equalsIgnoreCase(allign)){
	    				allignment = Element.ALIGN_RIGHT;
	    			}
	    			else if(SOPDFConstants.ALLIGN_JUSTIFY.equalsIgnoreCase(allign)){
	    				allignment = Element.ALIGN_JUSTIFIED;
	    			}
	    				    			
	    			font = FontFactory.getFont(font.getFamilyname(), font.getSize(), style);
	    			
	    			//If the messageContent is part of a list, 
	    			//append it to the list item in the list stack
	    			//else add it to phrase in the phrase stack
	    			if(!listItemStack.empty()){
	    				 
	    				ListItem item = listItemStack.peek();
	    				//Check whether the item is empty,
	    				//only then list symbol has to be added
	    				if(item.isEmpty()){
	    					if(null != allign){
	    						listSymbolNotRequired = true;
	    						listEmpty = true;
	    					}
	    					
	    					//Add a space at the beginning of the list
		    				//only if the list symbol is check box
	    					if(null != checkBox){
	    						String indent = SOPDFConstants.SPACE;
	    						if(null == anchor){
	    							indent = indent + SOPDFConstants.SPACE;
	    						}
		    					//To give indentation to sub lists
			    				for(int i = 0; i < innerListIdntfrStack.size()-1; i++){
			    					indent = indent + SOPDFConstants.SPACE;
			    				}
		    					item.add(indent);
		    				}
	    				}
	    				
	    				if(allignment!=0){
	    					item.setAlignment(allignment);
	    				}
	    				if(spacingBefore>8){
	    					item.setSpacingBefore(spacingBefore);
	    					spacingBefore=0;
	    				}
	    				if(paddingPix!=0){
	    					item.setIndentationLeft(paddingPix);
	    					paddingPix=0;
	    				}
	    				
	    				//To move the list symbol along with the text in case of text-align, 
    					//the symbol has to be added in the list item
	    				if(null != allign && listSymbolNotRequired && listEmpty){
	    					
	    					listEmpty = false;
	    					
	    					//Check whether ordered or unordered list
    						String element = innerListIdntfrStack.peek();
    						String indent = SOPDFConstants.BLANK;
		    				int imgWidth = 3;
		    				//To give indentation to sublists
		    				for(int i = 0; i < innerListIdntfrStack.size()-1; i++){
		    					indent = indent + SOPDFConstants.ALLIGN_SPACE;
		    					imgWidth += 4;
		    				}
		    				if(SOPDFConstants.UNORDERED_LIST_TAG.equalsIgnoreCase(element)){
		    					if(null != checkBox){
		    						//This is only for task comments of RI
		    						item.add(new Chunk(checkBox,imgWidth,-2));
		    					}
		    					else{
		    						item.add(new Chunk(indent + SOPDFConstants.SPACE + SOPDFConstants.SPACE + SOPDFConstants.BULLET + 
		    								SOPDFConstants.SPACE, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
		    					}
		    				}else if(SOPDFConstants.ORDERED_LIST_TAG.equalsIgnoreCase(element)){
		    					if(null != checkBox){
		    						//This is only for task comments of RI
		    						item.add(new Chunk(checkBox,imgWidth,-2));
		    					}
		    					else{
		    						Integer count = countStack.peek();
		    						item.add(new Chunk(indent + SOPDFConstants.SPACE + SOPDFConstants.SPACE + SOPDFConstants.SPACE + SOPDFConstants.SPACE + 
			    							count.toString() + SOPDFConstants.END_OF_LINE + SOPDFConstants.SPACE, defaultFont));
		    						countStack.push(count + 1);
		    					}
		    				}
		    				item.add(new Chunk(SOPDFConstants.SPACE + SOPDFConstants.SPACE));
	    				}
	    				
	    				//Form the content of the anchor tag
	    				if(null != anchor){
	    					anchorContent.append(messageContent);
	    				}
	    				else{
		    				item.add(new Chunk(messageContent, font));
	    				}
	    			}
	    			else if(!phraseItemStack.isEmpty() && listItemStack.empty()){
	    				
	    				ListItem item = phraseItemStack.peek();
	    				
	    				String indent = "";
	    				if(null != checkBox && item.isEmpty()){
	    					indent = SOPDFConstants.MEDIUM_SPACE;
	    				}
	    				
	    				if(allignment!=0){
	    					item.setAlignment(allignment);
	    				}
	    				if(paddingPix!=0){
	    					item.setIndentationLeft(paddingPix);
	    					paddingPix=0;
	    				}
	    				if(spacingBefore>8){
	    					item.setSpacingBefore(spacingBefore);
	    					spacingBefore=0;
	    				}
	    				
	    				//Form the content of the anchor tag
	    				if(null != anchor){
	    					anchorContent.append(indent).append(messageContent);
	    				}
	    				else{
		    				item.add(new Chunk(indent + messageContent, font));
	    				}
	    			}
	    			else{
	    				
	    				ListItem lItem = new ListItem(messageContent, font);
	    				lItem.setAlignment(allignment);
	    				if(paddingPix!=0){
	    					lItem.setIndentationLeft(-30);
	    					paddingPix=0;
	    				}
	    				if(spacingBefore>8){
	    					lItem.setSpacingBefore(spacingBefore);
	    					spacingBefore=0;
	    				}
	    				finalList.add(lItem);
	    			}
	    		}
	    		
	    		public void endElement(String uri, String localName,
	    				String tagName) throws SAXException {
	    			
	    			
	    			if (SOPDFConstants.STRONG_TAG.equalsIgnoreCase(tagName) 
	    					|| SOPDFConstants.BOLD_TAG.equalsIgnoreCase(tagName)) {
	    				strong = false;
	    			}
	    			else if (SOPDFConstants.ITALICS_TAG.equalsIgnoreCase(tagName) 
	    					|| SOPDFConstants.I_TAG.equalsIgnoreCase(tagName)) {
	    				italics = false;
	    			}
	    			else if (SOPDFConstants.UNDERLINE_TAG.equalsIgnoreCase(tagName)){
	    				underline = false;
	    			}
	    			else if(SOPDFConstants.HEADING_1.equalsIgnoreCase(tagName)){
	    				h1 = false;
	    			}
	    			else if(SOPDFConstants.HEADING_2.equalsIgnoreCase(tagName)){
	    				h2 = false;
	    			}
	    			else if(SOPDFConstants.HEADING_3.equalsIgnoreCase(tagName)){
	    				h3 = false;
	    			}
	    			else if(SOPDFConstants.HEADING_4.equalsIgnoreCase(tagName)){
	    				h4 = false;
	    			}
	    			else if(SOPDFConstants.HEADING_5.equalsIgnoreCase(tagName)){
	    				h5 = false;
	    			}
	    			else if(SOPDFConstants.HEADING_6.equalsIgnoreCase(tagName)){
	    				h6 = false;
	    			}

	    			else if(SOPDFConstants.ANCHOR_TAG.equalsIgnoreCase(tagName)){
	    				
	    				ListItem item = null;
	    				if(!listItemStack.empty()){
	    					item = listItemStack.peek();
	    				}
	    				else if(!phraseItemStack.isEmpty() && listItemStack.empty()){
	    					item = phraseItemStack.peek();
	    				}
	    				//Set the anchor tag and add it to list item or phrase
	    				anchorContent.append(SOPDFConstants.SPACE);
	    				Font anchorFont = font;
	    				anchorFont.setColor(Color.BLUE);
	    				Anchor hyperLink = new Anchor(anchorContent.toString(), anchorFont);
	    				hyperLink.setName(anchor.getName());
	    				hyperLink.setReference(anchor.getReference());    				
	    				item.add(hyperLink);
	    				anchor = null;
	    				anchorContent.replace(0, anchorContent.length(), SOPDFConstants.SPACE);
	    			}
	    			
	    			else if(SOPDFConstants.LIST_TAG.equalsIgnoreCase(tagName)){
	    				
	    				//Remove the list item from the stack and add it to the inner most list
	    				if(!listItemStack.isEmpty()){
	    					
	    					ListItem item = listItemStack.pop();	
	    					//Get the inner most list to which the list item needs to be added 
		    				String element = innerListIdntfrStack.peek();
		    				
		    				String indent = SOPDFConstants.BLANK;
		    				int imgWidth = 3;
		    				//To give indentation to sublist
		    				for(int i = 0; i < innerListIdntfrStack.size()-1; i++){
		    					indent = indent + SOPDFConstants.ALLIGN_SPACE;
		    					imgWidth += 4;
		    				}
		    				
		    				if(SOPDFConstants.UNORDERED_LIST_TAG.equalsIgnoreCase(element)){
		    					List uList = bulletStack.peek();
		    					
		    					if(!listSymbolNotRequired){
		    						//This is only for task comments of RI
		    						if(null != checkBox){
			    						uList.setListSymbol(new Chunk(checkBox,imgWidth,-2));
			    					}
			    					else{
			    						uList.setListSymbol(new Chunk(indent + SOPDFConstants.SPACE + SOPDFConstants.SPACE + SOPDFConstants.BULLET + 
			    								SOPDFConstants.SPACE, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
			    					}
		    					}
		    					else{
		    						uList.setListSymbol(new Chunk(SOPDFConstants.EMPTY));
		    					}
		    					uList.add(item);
		    					listSymbolNotRequired = false;
		    				}
		    				
		    				else if(SOPDFConstants.ORDERED_LIST_TAG.equalsIgnoreCase(element)){
		    					List oList = orderedStack.peek();
		    					if(!listSymbolNotRequired){
		    						if(null != checkBox){
			    						//This is only for task comments of RI
			    						oList.setListSymbol(new Chunk(checkBox,imgWidth,-2));
			    					}
			    					else{
			    						Integer count = countStack.peek();
				    					oList.setListSymbol(new Chunk(indent + SOPDFConstants.SPACE + SOPDFConstants.SPACE + SOPDFConstants.SPACE + SOPDFConstants.SPACE + 
				    							count.toString() + SOPDFConstants.END_OF_LINE + SOPDFConstants.SPACE, defaultFont));
				    					if(!item.isEmpty()){
				    						countStack.pop();
				    						countStack.push(count + 1);
				    					}
			    					}
		    					}
		    					else{
		    						oList.setListSymbol(new Chunk(SOPDFConstants.EMPTY));
		    					}
		    					oList.add(item);
		    					listSymbolNotRequired = false;
		    				}
	    				}
	    			}
	    			else if(SOPDFConstants.UNORDERED_LIST_TAG.equalsIgnoreCase(tagName)){
	    				
	    				//On reaching the end of list, remove the list from the identifier stack
	    				innerListIdntfrStack.pop();
	    				
	    				//If the innerListIdntfrStack is empty, then the list is not nested
	    				//So it can be directly added to the final list
	    				if(innerListIdntfrStack.isEmpty()){
	    					finalList.add(bulletStack.pop());
	    					
	    				}
	    				//If innerListIdntfrStack is not empty
	    				else {
	    					
	    					//Get the parent list to which the current list needs to be added 
	    					String innerListIdentifier = (String)innerListIdntfrStack.peek();
	    					
	    					List childList = null;
	    					List parentList = null;
	    					//Remove the current list from the respective stack and add it to the parent list
	    					if(SOPDFConstants.ORDERED_LIST_TAG.equalsIgnoreCase(innerListIdentifier) && !orderedStack.isEmpty()){
	    						childList = bulletStack.pop();
	    						parentList = orderedStack.peek();
	    						
	    					}
	    					else if(SOPDFConstants.UNORDERED_LIST_TAG.equalsIgnoreCase(innerListIdentifier) && !bulletStack.isEmpty()){
	    						childList = bulletStack.pop();
	    						parentList = bulletStack.peek();
	    						
	    					}
	    					//Before adding the child list, if there is any text before the child list, 
	    					//add that too to the parent list
	    					if(!listItemStack.isEmpty()){
	    						parentList.add(listItemStack.pop());
	    						listItemStack.push(new ListItem());
	    					}
    						parentList.add(childList);
	    				}
	    			}
	    			else if(SOPDFConstants.ORDERED_LIST_TAG.equalsIgnoreCase(tagName)){
	    				
	    				//On reaching the end of list, remove the list from the identifier stack
	    				innerListIdntfrStack.pop();
	    				countStack.pop();
	    				
	    				//If the innerListIdntfrStack is empty, then the list is not nested
	    				//So it can be directly added to the final list
	    				if(innerListIdntfrStack.isEmpty()){
	    					finalList.add(orderedStack.pop());
	    					
	    				}
	    				//If innerListIdntfrStack is not empty
	    				else {
	    					//Get the parent list to which the current list needs to be added 
	    					String innerListIdentifier = (String)innerListIdntfrStack.peek();
	    					
	    					List childList = null;
	    					List parentList = null;
	    					//Remove the current list from the respective stack and add it to the parent list
	    					if(SOPDFConstants.UNORDERED_LIST_TAG.equalsIgnoreCase(innerListIdentifier) && !bulletStack.isEmpty()){
	    						childList = orderedStack.pop();
	    						parentList = bulletStack.peek();
	    						
		    				}
		    				else if(SOPDFConstants.ORDERED_LIST_TAG.equalsIgnoreCase(innerListIdentifier) && !orderedStack.isEmpty()){
		    					childList = orderedStack.pop();
		    					parentList = orderedStack.peek();
		    					
		    				}
	    					//Before adding the child list, if there is any text before the child list, 
	    					//add that too to the parent list
	    					if(!listItemStack.isEmpty()){
	    						parentList.add(listItemStack.pop());
	    						listItemStack.push(new ListItem());
	    					}
    						parentList.add(childList);
	    				}
	    			}
	    			if (SOPDFConstants.PARA_TAG.equalsIgnoreCase(tagName)
							|| SOPDFConstants.HEADING_1
									.equalsIgnoreCase(tagName)
							|| SOPDFConstants.HEADING_2
									.equalsIgnoreCase(tagName)
							|| SOPDFConstants.HEADING_3
									.equalsIgnoreCase(tagName)
							|| SOPDFConstants.HEADING_4
									.equalsIgnoreCase(tagName)
							|| SOPDFConstants.HEADING_5
									.equalsIgnoreCase(tagName)
							|| SOPDFConstants.HEADING_6
									.equalsIgnoreCase(tagName)) {
	    				
						if (listItemStack.isEmpty() && !phraseItemStack.isEmpty()){
							
							ListItem item = phraseItemStack.pop();
							if(null != item && item.isEmpty()){
								finalList.add(SOPDFConstants.NEW_LINE);
							}
							else{
								finalList.add(item);
							}
						}
					}
					
					else if (SOPDFConstants.BREAK_TAG.equalsIgnoreCase(tagName)){
						
						ListItem item = null;
						if(!listItemStack.isEmpty() && phraseItemStack.isEmpty()){
							item = listItemStack.peek();
						}
						else if(!phraseItemStack.isEmpty() && listItemStack.isEmpty()){
							item = phraseItemStack.peek();
						}
						if(null != item){
							item.add(SOPDFConstants.NEW_LINE_FEED);
						}
	    			}
	    			
	    			//get all the style attributes of the tag and reset
	    			String attributes = attributeStack.pop();
	    			if(!SOPDFConstants.NO_ATTRIBUTE.equalsIgnoreCase(attributes)){
	    				String[] attributeArray = attributes.split(",");
	    				if(null != attributeArray && attributeArray.length > 0){
	    					for(String style : attributeArray){
	    						//reset underline
	    						if(SOPDFConstants.UNDERLINE_ATT.equalsIgnoreCase(style)){
	    							underline = false;
	    						}
	    						//reset font-size
	    						if(SOPDFConstants.FONT_SIZE.equalsIgnoreCase(style)){
	    							fontInt = 0;
	    						}
	    						//reset text-align
	    						if(SOPDFConstants.ALLIGN.equalsIgnoreCase(style)){
	    							allign = null;
	    						}
	    						//reset padding
	    						if(SOPDFConstants.INDENT.equalsIgnoreCase(style)){
	    							paddingPix = 0;
	    						}
	    					}
	    				}
	    			}
				}
	    	};
	    	
	    	saxParser.parse(new ByteArrayInputStream(htmlContent.getBytes("UTF-8")), handler);
						
		}catch(Exception e){
			e.printStackTrace();
			throw new BusinessServiceException("Exception while converting rich text:" + e);
		}
	
		return finalList;
	}
	
	/**SL-20728
	 * This method append encloses the rich text content with html tags
	 * @param htmlString
	 * @return ByteArrayInputStream
	 */
	public static String getHtmlContent(String htmlString) {
		
		try{
			//Replacing the html characters with xml characters
			htmlString = replaceHTMLWithXmlChars(htmlString);
			
			//Removing the spaces and line breaks between tags
			htmlString = htmlString.replaceAll(">\\s+<", "><").trim();
			
			//Enclosing the html content within <html> and </html> tags for converting it to xml
			htmlString = SOPDFConstants.HTML_TAG.concat(htmlString).concat(SOPDFConstants.HTML_END_TAG);
			
	    } catch (Exception e) {  
	    	LOGGER.error("Exception in getHtmlContent() due to :"+e);
	    } 
	    return htmlString;
	}
	
	/**SL-20728
	 * This method replaces the html characters with corresponding xml characters
	 * @param htmlString
	 * @return String
	 */
	private static String replaceHTMLWithXmlChars(String htmlString){
		
		//Encode xml characters in html content
		htmlString = encode(htmlString);
		
		//Decode the encoded string so that all the user entered & 
		//(which are not part of character entities) are retained
		htmlString = decode(htmlString);
		
		
		//Replace the html entities with corresponding xml entities
		for(String htmlChar : SOPDFConstants.htmlToXmlCharMap.keySet()){
			if(htmlString.contains(htmlChar)){
				htmlString = htmlString.replaceAll(htmlChar, SOPDFConstants.htmlToXmlCharMap.get(htmlChar));
			}
		}
		return htmlString;
		
	}
	
	/**SL-20728
	 * This method Encode xml characters in html content
	 * Xml characters are &, ", <, >. /
	 * @param text
	 * @return String
	 */
	public static String encode( String text ) {
        if (text == null) {
        	return null;
        }
        StringBuilder sb = new StringBuilder();
        CharacterIterator iter = new StringCharacterIterator(text);
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            switch (c) {
                case '&':
                    sb.append("&amp;");
                    break;
                case '"':
                    sb.append("&quot;");
                    break;
                case '<':
                    sb.append("&lt;");
                    break;
                case '>':
                    sb.append("&gt;");
                    break;
                case '\'':
                    sb.append("&#039;");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }
	
	/**SL-20728
	 * Decode the encoded string so that all the user entered & 
		(which are not part of character entities) are retained in html string
	 * @param encodedText
	 * @return String
	 */
	public static String decode( String encodedText ) {
        if (encodedText == null){
        	return null;
        }
        StringBuilder sb = new StringBuilder();
        CharacterIterator iter = new StringCharacterIterator(encodedText);
        for (char c = iter.first(); c != CharacterIterator.DONE; c = iter.next()) {
            if (c == '&') {
                int index = iter.getIndex();
                
                do {
                    c = iter.next();
                }
                while (c != CharacterIterator.DONE && c != ';');

                // We found a closing semicolon
                if (c == ';') {
                    String s = encodedText.substring(index + 1, iter.getIndex());
                    
                    if (SOPDFConstants.SPECIAL_ENTITIES.containsKey(s)) {
                        sb.append(SOPDFConstants.SPECIAL_ENTITIES.get(s));
                        continue;
                        
                    }
                    
                    if (s.length() > 0 && s.charAt(0) == '#') {
                        try {
                            sb.append((char) Short.parseShort(s.substring(1, s.length())));
                            continue;
                        }
                        catch (NumberFormatException nfe) {
                            // This is possible in malformed encodings, but let it fall through
                        }
                    }
                }
                
                // Malformed encoding, restore state and pass poorly encoded data back
                c = '&';
                iter.setIndex(index);                            
            }

            sb.append(c);

        }
        return sb.toString();
    }
	
	
	/**SL-20728
	 * Returns true if the text contain any html tags
	 * @param text
	 * @return boolean
	 */
	public static boolean hasHTMLTags(String text){
		
		final String tagStart = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)\\>";
		final String tagEnd = "\\</\\w+\\>";
		final String tagSelfClosing = "\\<\\w+((\\s+\\w+(\\s*\\=\\s*(?:\".*?\"|'.*?'|[^'\"\\>\\s]+))?)+\\s*|\\s*)/\\>";
		final String htmlEntity= "&[a-zA-Z][a-zA-Z0-9]+;";
		final Pattern htmlPattern = Pattern.compile("("+tagStart+".*"+tagEnd+")|("+tagSelfClosing+")|("+htmlEntity+")",Pattern.DOTALL);
		boolean ret = false;
		  
		if (text != null) {
			ret = htmlPattern.matcher(text).find();
		}
		return ret;
	}
	
}
