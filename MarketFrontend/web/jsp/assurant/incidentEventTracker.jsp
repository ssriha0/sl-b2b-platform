<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Buyer.incidentEventTracker"/>
		</jsp:include>	


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive - Assurant Incident Event Tracker</title>
		
		<script>
		</script>
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if IE]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<!-- acquity: here is the new js, please minify/pack the toolbox and rename as you wish -->
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
		<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>	
		
		<style type="text/css">
			div.overScroll { overflow: auto; width: 980px;}
			table#inTracker {color: #222; width: 4000px;}
			table#inTracker td, table#inTracker th { border-bottom: 1px solid #CCC; border-right: 1px solid #CCC; padding: 2px; vertical-align: middle;}
			table#inTracker th { background: #EEE; border-top: 1px solid #CCC;}
			table#inTracker td.status, table#inTracker th.status { border-left: 1px solid #CCC; font-weight: bold; text-align: center;}
			table#inTracker tr.update td { background: #fdf9bf;}
			table#inTracker tr.new td { background: #b6dcab;}
			table#inTracker tr.cancel td { background: #f5d7d7;}
			table#inTracker tr.info td { background: #dad9ff;}
			table#inTracker td textarea { font-size: 10px; width: 300px; height: 40px;}
			table#inTracker th.ccode, table#inTracker td.ccode, table#inTracker th.pline, table#inTracker td.pline, table#inTracker th.qty, table#inTracker td.qty { text-align: center;}
			
			table#inParts {color: #222; width: 100%;}
			table#inParts td, table#inTracker th { border-bottom: 1px solid #CCC; border-right: 1px solid #CCC; padding: 2px; vertical-align: middle;}
			table#inParts th { background: #EEE; border-top: 1px solid #CCC;}
			table#inParts td.status, table#inTracker th.status { border-left: 1px solid #CCC; font-weight: bold; text-align: center;}
			table#inParts tr.update td { background: #fdf9bf;}
			table#inParts tr.new td { background: #b6dcab;}
			table#inParts tr.cancel td { background: #f5d7d7;}
			table#inParts tr.info td { background: #dad9ff;}
			table#inParts td textarea { font-size: 10px; width: 300px; height: 40px;}
			table#inParts th.ccode, table#inTracker td.ccode, table#inTracker th.pline, table#inTracker td.pline, table#inTracker th.qty, table#inTracker td.qty { text-align: center;}
			
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

				<div id="hpWrap" class="clearfix">
					<div id="hpContent" class="pdtop20" style="width: auto">
						<div class="hpDescribe clearfix" style="width: 680px">
							<h2>
								Incident Event Tracker
							</h2>

							<h3>
								Respond To This Change
							</h3>
							<p>
								Choose the reason for your response below, and attach any
								details that may be useful in the description text field.
							</p>

							<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />

							<c:if test="${successMsg != null}">
								<div class="success">
									${successMsg}
								</div>
							</c:if>

							<s:form action="assurantEditIncident_" id="assurantEditIncident_">
								<div class="clearfix" style="padding: 20px 0;">


									<s:select id="dropdownSelection" name="dropdownSelection"
										cssStyle="width: 280px;" size="1" theme="simple"
										list="%{dropdownList}" listKey="value" listValue="label" />

									<label style="display: block; margin-top: 10px;">
										Description
									</label>
									<s:textarea id="reasonText" name="reasonText"
										cssStyle="width: 98%;" cssClass="text" theme="simple" />
								</div>

								<input type="hidden" name="soID" value="${soID}" />
								<input type="hidden" name="incidentID" value="${incidentID}" />
								<input type="hidden" name="clientIncidentID" value="${clientIncidentID}" />
								<input type="hidden" name="monitorTab" value="${monitorTab}" />

								<table>
								<tr>
								<td><s:submit type="input" method="submitRequest" theme="simple"
									value="Send Response" /></td>
								<c:choose>
								<c:when test="${monitorTab == 'soDetailsTab'}" >
								<td align="right">	
									<div style="text-align: right;" >								
										<a style="align:right;" href="soDetailsController.action?displayTab=${monitorTab}&soId=${soID}" >Return to Service Order Details</a>
									</div>
								</td>
								</c:when>
								<c:otherwise>
								<td  align="right">
									<div style="text-align: right;" >								
										<a style="align:right;" href="serviceOrderMonitor.action?displayTab=${monitorTab}" >return to Service Order Monitor</a>
									</div>
								</td>
								</c:otherwise>
								</c:choose>
								</tr>
								</table>
							</s:form>

						</div>


						<h3 style="color: #F9B020; font-size: 14px; margin-top: 20px;">
							Change History for Client Incident ID #${clientIncidentID} (Service Order ID
							#${soID})
						</h3>

						<div class="overScroll">
							<table border="0" id="inTracker">
								<tr>
									<th class="status">
										Date
									</th>
									<th class="status">
										Status
									</th>
									<th class="comments">
										Comments
									</th>
									<th class="ccode">
										Parts/Labor Flag
									</th>
									<th class="contact">
										Contact Name
									</th>
									<th class="contact">
										Address
									</th>
									<th class="contact">
										Phone #
									</th>
									<th class="pline">
										Product Line
									</th>
									<th class="qty">
										Qty.
									</th>								
									<th class="ccode">
										Manufacturer
									</th>
									<th class="ccode">
										Model Number
									</th>
									<th class="ccode">
										Serial Number
									</th>
									<th class="ccode">
										Contract Date
									</th>
									<th class="ccode">
										Associated Incident #
									</th>
									<th class="ccode">
										Part/Warranty SKU
									</th>
									<th class="ccode">
										Special Coverage Flag
									</th>
									<th class="ccode">
										Warranty Status
									</th>
									<th class="ccode">
										Contract Number
									</th>
									<th class="ccode">
										Expiration Date
									</th>
									<th class="ccode">
										Shipping Method
									</th>
									<th class="ccode">
										Service Provider Location
									</th>
									<th class="ccode">
										Vendor Claim Number
									</th>
									<th class="ccode">
										Authorized Amount
									</th>
									<th class="ccode">
										Support Group
									</th>
									<th class="ccode">
										Servicer ID
									</th>
									<th class="ccode">
										Authorization Code
									</th>
									<th class="ccode">
										Contract Type Code
									</th>
									<th class="ccode">
										Retailer
									</th>
								</tr>
								<c:choose>
								<c:when test="${empty incidentEvents or fn:length(incidentEvents) == 0}">
									<tr>
										<td colspan="33" class="error">
											No records found
										</td>
									</tr>
								</c:when>
								<c:otherwise>

									<c:forEach items="${incidentEvents}" var="event">
	
										<tr class="${event.clientStatus}">
											<td class="status">												
												<fmt:formatDate value="${event.createdDate}" pattern="M/d/yyyy" />
											</td>
											<td class="status">
												${event.clientStatus}
											</td>
											<td class="comments">
												<textarea class="expand" readonly="readonly">${event.comment}</textarea>
											</td>
											<td class="ccode">
												${event.partLaborInd}
											</td>
											<td class="contact">
												${event.lastName}, ${event.firstName}
											</td>
											<td class="contact">
												<c:if test="${event.street1 != null}">
													${event.street1}<br />
												</c:if>
												<c:if test="${event.street2 != null}">
													${event.street2}<br />
												</c:if>
												${event.city}, ${event.state} ${event.zipCode}
											</td>
											<td class="contact">
												<strong>Phone: </strong>
												<c:if test="${event.phone1 != null}">
													${event.phone1}
													<c:if test="${event.phone1Ext != null}">
														#${event.phone1Ext}<br />
													</c:if>
												</c:if>
												<c:if test="${event.phone2 != null}">
													<strong>Alt Phone: </strong>
													${event.phone2}
													<c:if test="${event.phone2Ext != null}">
													#${event.phone2Ext}
													</c:if>
												</c:if>
											</td>
											<td class="pline">
												${event.productLine}
											</td>
											<td class="qty">
												${event.numberOfParts}
											</td>
											<td class="ccode">
												${event.manufacturer}
											</td>
											<td class="ccode">
												${event.modelNumber}
											</td>
											<td class="ccode">
												${event.serialNumber}
											</td>
											<td class="ccode">
												<fmt:formatDate value="${event.contractDate}" pattern="M/d/yyyy" />
											</td>
											<td class="ccode">
												${event.associatedIncidentNumber}
											</td>
											<td class="ccode">
												${event.partsWarrentySKU}
											</td>
											<td class="ccode">
												${event.specialCoverageFlag}
											</td>
											<td class="ccode">
												${event.warrantyStatus}
											</td>
											<td class="ccode">
												${event.contractNumber}
											</td>
											<td class="ccode">												
												<fmt:formatDate value="${event.expirationDate}" pattern="M/d/yyyy" />
											</td>
											<td class="ccode">
												${event.shippingMethod}
											</td>
											<td class="ccode">
												${event.serviceProviderLocation}
											</td>
											<td class="ccode">
												${event.vendorClaimNumber}
											</td>
											<td class="ccode">
												$${event.authorizedAmount}
											</td>
											<td class="ccode">
												${event.supportGroup}
											</td>
											<td class="ccode">
												${event.servicerID}
											</td>
											<td class="ccode">
												${event.authorizingCode}
											</td>
											<td class="ccode">
												${event.contractTypeCode}
											</td>
											<td class="ccode">
												${event.retailer}
											</td>
										</tr>
									</c:forEach>
								</c:otherwise>
								</c:choose>

								<tr>
									<th class="status">
										Date
									</th>
									<th class="status">
										Status
									</th>
									<th class="comments">
										Comments
									</th>
									<th class="ccode">
										Parts/Labor Flag
									</th>
									<th class="contact">
										Contact Name
									</th>
									<th class="contact">
										Address
									</th>
									<th class="contact">
										Phone #
									</th>
									<th class="pline">
										Product Line
									</th>
									<th class="qty">
										Qty.
									</th>
									<th class="ccode">
										Manufacturer
									</th>
									<th class="ccode">
										Model Number
									</th>
									<th class="ccode">
										Serial Number
									</th>
									<th class="ccode">
										Contract Date
									</th>
									<th class="ccode">
										Associated Incident #
									</th>
									<th class="ccode">
										Part/Warranty SKU
									</th>
									<th class="ccode">
										Special Coverage Flag
									</th>
									<th class="ccode">
										Warranty Status
									</th>
									<th class="ccode">
										Contract Number
									</th>
									<th class="ccode">
										Expiration Date
									</th>
									<th class="ccode">
										Shipping Method
									</th>
									<th class="ccode">
										Service Provider Location
									</th>
									<th class="ccode">
										Vendor Claim Number
									</th>
									<th class="ccode">
										Authorized Amount
									</th>
									<th class="ccode">
										Support Group
									</th>
									<th class="ccode">
										Servicer ID
									</th>
									<th class="ccode">
										Authorization Code
									</th>
									<th class="ccode">
										Contract Type Code
									</th>
									<th class="ccode">
										Retailer
									</th>
								</tr>
							</table>
				</div>


				<h3 style="color:#F9B020; font-size:14px; margin-top:20px;">Parts for Client Incident ID #${clientIncidentID} (Service Order ID #${soID})</h3>					
					<div class="overScroll">
							<table border="0" id="inParts">
								<tr>
									<th >Class Code</th>
									<th class="ccomments">Class Comments</th>
									<th class="ccode">Part Number</th>
									<th class="ccode">OEM Number</th>
									<th class="ccomments">Parts Comments</th>
								</tr>
								<c:choose>
								<c:when test="${empty incidentParts or fn:length(incidentParts) == 0}">
									<tr>
										<td colspan="33" class="error">
											No Parts found
										</td>
									</tr>
								</c:when>
								<c:otherwise>
								<c:forEach items="${incidentParts}" var="part">
									
									<tr class="new" >
										<td >
												${part.classCode}<br/>
										</td>
										<td class="ccomments">
											<textarea class="expand" readonly="readonly">${part.classComments}</textarea>
										</td>
										<td class="ccode">
												${part.partNumber}<br/>
										</td>
										<td class="ccode">
												${part.oemNumber}<br/>
										</td>
										<td class="ccomments">										
											<textarea class="expand" readonly="readonly">${part.partComments}</textarea>
										</td>
									</tr>
								</c:forEach>
								</c:otherwise>
								</c:choose>
								
								<tr>
									<th >Class Code</th>
									<th class="ccomments">Class Comments</th>
									<th class="ccode">Part Number</th>
									<th class="ccode">OEM Number</th>
									<th class="ccomments">Parts Comments</th>
								</tr>
							</table>
					</div>



					
					
					
					<!-- START FOOTER -->
					<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
					<!-- END FOOTER -->
				</div>
			</div>
	</body>
</html>