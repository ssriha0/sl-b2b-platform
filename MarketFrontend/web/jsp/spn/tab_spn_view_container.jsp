<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
<head>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
</head>
<body>

	 
		<table  border=0>
			<tr>
				<td colspan=2>
				<form>
					Market Filter:
					<s:select name="filterCriteriaId" id="marketId" headerKey="-1"
								headerValue="-- All Markets --" list="#attr.all_markets"
								listKey="type" listValue="descr" size="1" theme="simple"
								cssStyle="width: 200px; background-color: #ffffff;" 
								disabled="false"
								onchange="newco.jsutils.doFiltering(this)" />
					 </form>
				<br></td>
			</tr>

			<tr>
				<td colspan=2>
					<table class="scrollerTableHdr spnTableHdr" cellpadding="0"
						cellspacing="0">
						<tr>
							<td class="column1">
								Select
							<br></td>
							<td class="column2" style="padding-left: 5px">
								Provider
							<br></td>
							<td class="column3">
								Status
							<br></td>
							<td class="column4" style="text-align:left">
								Attachment
							<br></td>
							<td class="column5">
								Total Orders
							<br></td>
						</tr>
					</table>
				</td>
			</tr>
			
			<tr>
				<td colspan=2>
						<iframe width="100%" height="800" marginwidth="0"
						marginheight="0" frameborder="0"
						src="${contextPath}/spnMemberManager_showView.action?type=${type}&spnId=${spnId}"
						name="spn_type_${type}" frameborder="0"
						id="spn_type_${type}">
					</iframe>
				<br></td>
			</tr>
		
		</table>
	 
<htm>
