<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive - Order Group Management</title>
		<script>
			function validate() {
				
				// Get form data to validate
				var status = document.getElementById("status");
				var searchType = document.getElementById("searchType");
				
				var searchTerm = jQuery.trim(document.getElementById("searchTerm").value);
				document.getElementById("searchTerm").value = searchTerm;
				
				// Validate
				var validInd = true;
				var invalidMsg = "Please select search criteria.\n\n";
				if (status.selectedIndex == 0) {
					invalidMsg += "Status is required.\n";
					validInd = false;
				}
				if (searchType.selectedIndex == 0) {
					invalidMsg += "'Search By' is required.\n";
					validInd = false;
				}
				if (searchTerm.length == 0) {
					invalidMsg += "'Search Term' is required.\n";
					validInd = false;
				}
				if (searchType.selectedIndex == 2) { // Search on phone number, remove all non-numeric characters
					searchTerm = searchTerm.replace(/[^0-9]/g, "");
					document.getElementById("searchTerm").value = searchTerm;
				}
				
				// Display validation messages if any
				if (!validInd) {
					alert(invalidMsg);
				}
				
				return validInd;
			}
		</script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-1010px;}
		</style>
	</head>
	<body class="tundra acquity">
	
			<div id="page_margins">
				<div id="page">
					<!-- START HEADER -->
					<div id="header">
						<tiles:insertDefinition name="newco.base.topnav" />
						<tiles:insertDefinition name="newco.base.blue_nav" />
						<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					</div>
					<!-- END HEADER -->
					
					<div id="hpWrap" class="clearfix wide">
						<s:form action="orderGroupManager_" theme="simple">
						<div id="hpContent" class="pdtop20">
							<div id="hpIntro">
								<h2>Order Group Management</h2>
								<span>Use the search below to find orders that can be grouped.</span>
							</div>
							<div><%@ include file="message.jsp"%></div>
							<div class="ogSearch">
								<h3>Find orders that can be grouped</h3>
								<label class="ogStatus">Status: 
									<s:select id="status" name="status"
										headerKey="-1" headerValue="Select One" cssClass="ogStatus"
										size="1" theme="simple" list="%{statusDropdowns}"
										listKey="value" listValue="label" />									
								</label>
								<label>Search by:									
									<s:select id="searchType" name="searchType"
										headerKey="-1" headerValue="Select One" 
										size="1" theme="simple" list="%{searchTypeDropdowns}"
										listKey="value" listValue="label" />
								</label>
								<label>Search Term: 
									<s:textfield name="searchTerm" id="searchTerm" cssStyle="width: 100px;" theme="simple"/>
								</label>
						       <s:submit type="input" method="searchButton" theme="simple" value="Search" onclick="return validate()" />
							</div>
							
							<div class="hpDescribe clearfix">
								<div class="clearfix">
								<s:submit cssClass="right butn newGroup" value="Create New Group" theme="simple" method="createNewGroupButton" />
								<div id="ugControl" class="clearfix">
									<a id="checkAll" href="#">Check All</a>
									<a id="uncheckAll" href="#">Uncheck All</a>
								</div>
								</div>
								<table width="100%" cellpadding="0" cellspacing="0" border="0"><tr>
									<td width="82%"><p class="ogmSectionHeading">Select orders to group:</p></td>
									<td><p class="ogmSectionHeading">Select group:</p></td>
								</tr></table>
								<%--table class="ungroupedOrdersHeading">
								<tr><th width="22%">&nbsp;&nbsp;Service Order #</th><th width="54%">Title</th><th width="8%">Zip</th><th width="16%">Service&nbsp;Date</th></tr>
								</table --%>
								<div id="ungroupedOrders" class="left">
									<table border="0" cellpadding="0" cellspacing="5">
										<s:iterator value="ungroupedOrders" status="status" id="foo">
										<tr class="singleItem">
											<td width="25%"><s:checkbox name="ugo_%{ungroupedOrders[#status.index].id}" id="bar" theme="simple" value="false" />
												<a href="orderGroupManager_jumpToSODorSOW.action?soId=<s:property value='%{ungroupedOrders[#status.index].id}' />&action=edit&status=<s:property value='%{ungroupedOrders[#status.index].status}' />">
													<s:property value="%{ungroupedOrders[#status.index].id}" /></a></td>
											<td width="55%"><s:property value="%{ungroupedOrders[#status.index].titleWidget}" /><br/>
												<strong>Customer: </strong><s:property value="%{ungroupedOrders[#status.index].endCustomerWidget}" /></td>
											<td width="8%"><s:property value="%{ungroupedOrders[#status.index].zip5}" /></td>
											<td width="12%"><s:property value="%{ungroupedOrders[#status.index].serviceOrderDateString}" /></td>
										</tr>
										</s:iterator>
									</table>
								</div>

								<div id="groupWith" class="left">
									<s:submit value="Group With >" theme="simple" method="groupSelectedButton" />
								</div>

								<div id="availableGroups" class="left">
									<s:iterator value="orderGroups" status="">
										<div class="singleItem">
											<input type="radio" class="checkbox" name="radioOrderGroup"
												id="radioOrderGroup" value="<s:property value="id" />" />
											<s:property value="id" />
										</div>
									</s:iterator>
								</div>
							</div>

							<div class="hpDescribe clearfix">
								<h2>Previously Grouped Orders</h2>

								<s:submit cssClass="right butn" value="Ungroup Selected Order Groups" theme="simple" method="ungroupSelectedButton" />

								<div id="treecontrol">
									<a title="Collapse the entire tree below" href="#"><img src="${staticContextPath}/images/minus.gif" />Collapse All</a>
									<a title="Expand the entire tree below" href="#"><img src="${staticContextPath}/images/plus.gif" />Expand All</a>
									<a title="Toggle the tree below, opening closed groups, closing open groups" href="#">Toggle All</a>
								</div>

								<ul id="browser" class="filetree treeview-ico">
								
									<c:forEach items="${orderGroups}" var="group">
									<!-- start order group -->
									<li class="closed soGroup">

										<span class="folder">
											<table border="0" width="98%" cellpadding="0" cellspacing="0">
												<tr>
													<td class="ogTitle">
														<input type="checkbox" class="checkbox" name="checkbox_group_${group.id}" id=""/>
														<strong>${group.id}</strong>
													</td>
													<td class="ogDesc">
														${group.title}<br/>
														<strong>Customer: </strong>${group.endCustomer}
													</td>
													<td class="ogTime">
														${group.createdDateString}
													</td>
													<td class="ogLocation">
														${group.city}, ${group.state}<br/>
														${group.zip}
													</td>
												</tr>
											</table>
										</span>

										<!-- start service orders in group -->
										<ul>
											<c:forEach items="${group.orders}" var="so">
												<!-- start service order  -->
												<li>
													<span class="file">
														<table border="0" width="100%" cellpadding="0" cellspacing="0">
															<tr>
																<td class="ogTitle">
																	${so.id}
																</td>
																<td class="ogDesc">
																	${so.titleWidget}
																</td>
																<td class="ogLocation">
																	&nbsp;
																</td>
															</tr>
														</table>
													</span>
												</li>											
												<!-- end service order  -->
											</c:forEach>
										</ul>
										<!-- end service orders in group -->
									</li>
									<!-- end order group -->
									</c:forEach>
								</ul>
								<s:submit cssClass="right butn" value="Ungroup Selected Order Groups" theme="simple" method="ungroupSelectedButton" />
							</div>
						</div>
						</s:form>
					</div>
					<!-- START FOOTER -->
					<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
					<!-- END FOOTER -->
				</div>
			</div>
	</body>
</html>