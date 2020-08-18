import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

import com.servicelive.domain.common.Contact;
import com.servicelive.domain.routingrules.*;

import flexjson.JSONSerializer;

public class FlexJsonTest {

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");

	@Test
	public void testFlexJson() throws Exception {

		List<RoutingRuleHdr> lst = new ArrayList<RoutingRuleHdr>();

		RoutingRuleHdr hdr = new RoutingRuleHdr();
		hdr.setRuleName("Rule One");
		hdr.setModifiedBy("Howard");
		hdr.setRuleComment("Beware");
		hdr.setContact(createNewContact());
		hdr.setCreatedDate(sdf.parse("2008-03-15 12:00:00:00"));
		hdr.setModifiedDate(sdf.parse("2008-03-15 12:00:00:00"));
		lst.add(hdr);

		hdr = new RoutingRuleHdr();
		hdr.setRuleName("Rule Too");
		hdr.setModifiedBy("Rodney Amadeus Anonymous");
		hdr.setRuleComment("Mites are living in your eyelashes");
		hdr.setContact(createNewContact());
		hdr.setCreatedDate(sdf.parse("2008-06-15 12:00:00:00"));
		hdr.setModifiedDate(sdf.parse("2008-06-15 12:00:00:00"));
		lst.add(hdr);

		JSONSerializer serializer = new JSONSerializer();
		//Note: you can do an exclude from the serializer.
		String articleJson = serializer.serialize(lst);

		//assertEquals("[{\"class\":\"com.servicelive.domain.routingrules.RoutingRuleHdr\",\"contact\":{\"class\":\"com.servicelive.domain.common.Contact\",\"contactGroup\":null,\"contactId\":8675309,\"contactMethodId\":null,\"createdDate\":1213549200000,\"dob\":null,\"email\":null,\"emailAlt\":null,\"faxNo\":null,\"firstName\":null,\"honorific\":null,\"lastName\":null,\"mi\":null,\"mobileNo\":null,\"modifiedBy\":null,\"modifiedDate\":1213549200000,\"pagerText\":null,\"phoneNo\":null,\"phoneNoExt\":null,\"smsNo\":null,\"suffix\":null,\"title\":null},\"createdDate\":1205600400000,\"modifiedBy\":\"Howard\",\"modifiedDate\":1205600400000,\"routingRuleBuyerAssoc\":null,\"routingRuleHdrId\":null,\"ruleComment\":\"Beware\",\"ruleName\":\"Rule One\",\"ruleStatus\":null},{\"class\":\"com.servicelive.domain.routingrules.RoutingRuleHdr\",\"contact\":{\"class\":\"com.servicelive.domain.common.Contact\",\"contactGroup\":null,\"contactId\":8675309,\"contactMethodId\":null,\"createdDate\":1213549200000,\"dob\":null,\"email\":null,\"emailAlt\":null,\"faxNo\":null,\"firstName\":null,\"honorific\":null,\"lastName\":null,\"mi\":null,\"mobileNo\":null,\"modifiedBy\":null,\"modifiedDate\":1213549200000,\"pagerText\":null,\"phoneNo\":null,\"phoneNoExt\":null,\"smsNo\":null,\"suffix\":null,\"title\":null},\"createdDate\":1213549200000,\"modifiedBy\":\"Rodney Amadeus Anonymous\",\"modifiedDate\":1213549200000,\"routingRuleBuyerAssoc\":null,\"routingRuleHdrId\":null,\"ruleComment\":\"Mites are living in your eyelashes\",\"ruleName\":\"Rule Too\",\"ruleStatus\":null}]", articleJson);
	}

	private Contact createNewContact() throws ParseException {
		Contact contact = new Contact();
		contact.setContactId(Integer.valueOf(8675309));
		contact.setCreatedDate(sdf.parse("2008-06-15 12:00:00:00"));
		contact.setModifiedDate(sdf.parse("2008-06-15 12:00:00:00"));
		return contact;
	}

}
