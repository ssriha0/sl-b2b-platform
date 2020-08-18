<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="role" value="${roleType}" />

<c:set var="RECEIVED" value="Received" />
<c:set var="noJs" value="true" scope="request"/> <%-- tell header not to insert any JS --%>
<c:set var="noCss" value="true" scope="request"/><%-- tell header not to insert any CSS --%>
<c:set var="currentMenu" scope="request" value="orderManagement" /> <%--to be consumed by the header nav to highlight the SOM tab --%>
<c:set var="provider" value="false"  scope="request"/><%-- ss: needed for presentation logic brevity --%>
<c:set var="sladmin" value="false"  scope="request"/>
<c:if test="${roleType == 1}"><c:set var="provider" value="true" scope="request" /></c:if>
<c:if test="${SecurityContext.slAdminInd}"><c:set var="sladmin" value="true" scope="request" /></c:if>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8;no-cache" />
	<link href="${staticContextPath}/css/ui-15642.css" rel="stylesheet"/>
	<style type="text/css">
			.tilteFlyOut{background-color: #F9FDB1;display: none;z-index:99999;position: relative;height: auto; overflow: auto;}
			.omHdrSort hover{text-decoration: underline;cursor: pointer;color: red;}
			.upArrow{background-image: url(${staticContextPath}/images/grid/arrow-up-white.gif);background-repeat: no-repeat;}
			.downArrow{background-image: url(${staticContextPath}/images/grid/arrow-down-white.gif);background-repeat: no-repeat;}
			 td.non-sortable:hover {background: none;}
		 </style>
	<script type="text/javascript" >
   //Initial load of page
    $(document).ready(sizeContent);
    //Every resize of window
    $(window).resize(sizeContent);
    // Dynamically assign height
    function sizeContent() {
        var currentTab = '${omDisplayTab}';
    	var reduceHeight = 0;
    	if(currentTab == 'Inbox' 
    		|| currentTab == 'Respond' 
    		|| currentTab == 'Schedule' 
    		|| currentTab == 'Confirm Appt window' 
    		|| currentTab == 'Job Done' 
        	|| currentTab == 'Revisit Needed' 
			|| currentTab == 'Resolve Problem'){
    			reduceHeight = 75;
    	}else{
    		reduceHeight = 25;
    	}

        var viewMoreHeight = $("#viewMoreLink").height(); 
        
        if(!$.isNumeric(viewMoreHeight)){
            viewMoreHeight =0;
        }
        var newHeight =  $("html").height() - $("#header").height() - reduceHeight - $("#filterForm").height() -
        $(".omscrollerTableHdr > tbody > tr:first-child").height() - viewMoreHeight;
        $("#omDataGridMainDiv").css("height", newHeight+ "px");
        var contentDivHeight = $("#omSubDiv").height();
        var footerHeight = $("#footer").height();
        
        if(contentDivHeight < newHeight){
        	//contentDivHeight = newHeight-50;
        	$("#omSubDiv").css("height",  newHeight-footerHeight-35 + "px");
        }
        
  	 }

  	 // var bbbprotocol = ( ("https:" == document.location.protocol) ? "https://" : "http://" ); 
	 // document.write(unescape("%3Cscript src='" + bbbprotocol + 'seal-chicago.bbb.org' + unescape('%2Flogo%2Fservicelive-88258074.js') + "' type='text/javascript'%3E%3C/script%3E"));
    </script>
		</head>
		<body>
				<jsp:include page="order_management_filters.jsp"/>
				<c:if test="${omDisplayTab == 'Inbox' || omDisplayTab == 'Respond' || omDisplayTab == 'Schedule' || omDisplayTab == 'Confirm Appt window' || omDisplayTab == 'Job Done' || omDisplayTab == 'Revisit Needed' || omDisplayTab == 'Resolve Problem'}">
				<jsp:include page="order_management_grid.jsp"/>
				</c:if>
				<c:if test="${omDisplayTab == 'Assign Provider' || omDisplayTab == 'Awaiting Payment' || omDisplayTab == 'Print Paperwork'}">
				<jsp:include page="order_management_second_grid.jsp"/>
				</c:if >
				<c:if test="${omDisplayTab == 'Cancellations' || omDisplayTab == 'Manage Route' || omDisplayTab == 'Current Orders'}">
				<jsp:include page="order_management_third_grid.jsp"/>
				</c:if>
		</body>
</html>
