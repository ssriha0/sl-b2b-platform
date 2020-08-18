<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

   <script type="text/javascript">
   </script>

<h1>tab_bid_notes.jsp</h1>