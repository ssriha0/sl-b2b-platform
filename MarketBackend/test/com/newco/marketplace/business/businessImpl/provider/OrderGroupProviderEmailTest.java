package com.newco.marketplace.business.businessImpl.provider;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

import junit.framework.TestCase;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.newco.marketplace.Fixtures;
import com.newco.marketplace.aop.AOPMapper;
import com.newco.marketplace.dto.vo.ordergroup.ChildServiceOrderVO;

public class OrderGroupProviderEmailTest extends TestCase {

	private static final String EMAIL_TEXT_OLD = "<HTML>\r\n" + 
			" <BODY>\r\n" + 
			" <p>\r\n" + 
			"  * This is a system generated email - Please do not reply to this message *\r\n" + 
			"</p>\r\n" + 
			"IMPORTANT: YOU MUST LOGIN TO SERVICELIVE.COM TO ACCEPT THIS SERVICE ORDER.  Also, please read the Service Order Acceptance instructions at the bottom of this message. <br />\r\n" + 
			"<p>\r\n" + 
			"Service Order #$SO_ID has been posted to you and other service providers in your area and is available to accept as of $SO_ROUTED_DATE CST.  The first service provider to accept it gets the job!  An overview of the service order is provided below.  Please login to ServiceLive.com to read the entire details of this service order before deciding to accept.  Thank you.\r\n" + 
			"</p>\r\n" + 
			"<p>\r\n" + 
			"<b>** SERVICE ORDER DETAILS **</b>  <br />\r\n" + 
			"=====================================================================  <br />\r\n" + 
			"<b> SERVICE ORDER #:</b> $SO_ID  <br />\r\n" + 
			"<b> MAIN SERVICE CATEGORY:</b> $SO_MAIN_SERVICE_CATEGORY  <br />\r\n" + 
			"<b> REQUESTED ARRIVAL DATE:</b> $SERVICE_DATE1 - $SERVICE_DATE2  $SERVICE_START_TIME -  $SERVICE_END_TIME $SO_SERVICE_LOC_TIMEZONE  <br />\r\n" + 
			"<b> LABOR BID PRICE:</b> $$SPEND_LIMIT_LABOR  <br />\r\n" + 
			"<b> PARTS BID PRICE:</b> $$SPEND_LIMIT_PARTS  <br />\r\n" + 
			"<b> TITLE:</b> $SO_TITLE  <br />\r\n" + 
			"<b> OVERVIEW:</b> $SO_DESC  <br />\r\n" + 
			"=====================================================================  <br />\r\n" + 
			"<b> ** LOCATION ** </b>  <br />\r\n" + 
			"=====================================================================  <br />\r\n" + 
			"<b> CITY, STATE, ZIP:</b> $SO_SERVICE_CITY, $SO_SERVICE_STATE $SO_SERVICE_ZIP  <br />\r\n" + 
			"Note: The contact person, number, and exact address will be provided after acceptance of the service order  <br />\r\n" + 
			"=====================================================================  <br />\r\n" + 
			"</p>\r\n" + 
			"<b> ** SERVICE ORDER ACCEPTANCE INSTRUCTIONS **  </b>  <br />\r\n" + 
			"<b> ** VERY IMPORTANT - READ BELOW BEFORE ACCEPTING **  </b>  <br />\r\n" + 
			"=====================================================================  <br />\r\n" + 
			"* Please remember, only accept a Service Order if you are capable of performing the work as specified in the time frame specified.  <br />\r\n" + 
			"* When accepting a Service Order, be sure that the Labor and Parts Spend Limit Prices are sufficient to complete the job, including sales taxes, service taxes, and labor charges.  It is the sole discretion of the buyer to increase the spend limits for unspecified materials or work outside the scope of the Service Order.  <br />\r\n" + 
			"* After you accept a Service Order on-line, read the remaining attachments and details and, if requested, contact the end user to confirm the appointment.  <br />\r\n" + 
			"* Please note the buyer\'s terms and documents to understand their closing procedures and perform all necessary tasks.  <br />\r\n" + 
			"* When the Buyer closes the Service Order, you will be paid through ServiceLive.com.  DO NOT invoice the end user or buyer directly.  <br />  <br />\r\n" + 
			" \r\n" + 
			"=====================================================================\r\n" + 
			" </BODY>\r\n" + 
			"</HTML>";
	
	
	private static final String EMAIL_TEXT2 = "<table>\r\n" + 
	"#foreach( $serviceOrder in $chidlList)\r\n" + 
	"    <tr><td>$velocityCount</td><td>$serviceOrder.soId</td></tr>\r\n" + 
	"#end\r\n" + 
	"</table>";

	public void testShouldGenerateEmailText2() throws Exception {
		VelocityContext ctx = new VelocityContext();
		VelocityEngine engine = new VelocityEngine();
		List<ChildServiceOrderVO> chidlList = Fixtures.getServiceOrderGroupChild();
		ctx.put("chidlList", chidlList);
		
		Writer writer = new StringWriter();
		engine.evaluate(ctx, writer , "Provider Email", EMAIL_TEXT2);
		System.out.println(writer.toString());
		Object test = null;
		System.out.println( (String)(test));
	}
	
	/*public void testChildOrders(){
		String groupId = "179-4594-6687-20";
		Object[] args = null;
		AOPMapper aopMapper = new AOPMapper(args);
		List<ChildServiceOrderVO> childList = aopMapper.getListOfChildSO(groupId);
		assertEquals("category name", childList.get(0).getTasks().get(0).getCategoryName());
	}*/
	
	
}
