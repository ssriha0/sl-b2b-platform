<%@ attribute name="serviceOrderDTO" type="com.newco.marketplace.web.dto.ServiceOrderDTO" required="true"%>
<%@ attribute name="actionName" required="true"%>
<%
	if (actionName.equals("StripResourceIdFromGroupId")) {
		String groupId = serviceOrderDTO.getParentGroupId();
		if (groupId.endsWith("#")) {
			serviceOrderDTO.setParentGroupId(groupId.substring(0, groupId.indexOf("#")));
		}
	}
%>
