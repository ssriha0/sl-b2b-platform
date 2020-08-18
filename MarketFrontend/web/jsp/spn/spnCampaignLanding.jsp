<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page import="org.apache.commons.lang.StringEscapeUtils" %>
<%@ page import="org.owasp.esapi.ESAPI"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
String vSpnID = request.getParameter("spnID");
String vSpnIDNew=ESAPI.encoder().canonicalize(vSpnID);
String vulnSpnID=ESAPI.encoder().encodeForHTML(vSpnIDNew);
%>
<c:set var="VarSpnID" scope="request" value="<%=vulnSpnID%>" />
<html>
	<head>
		<title>Select Provider Network [Campaign Landing]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />


		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css" />


		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script> 
		
		function submitForm(method){
		
			var loadFormTest = document.getElementById('spnCampaignForm');
						
			if (method=='viewDocument'){
				loadFormTest.action = '${contextPath}' + "/BuyerDocumentView"; 
			} else {
							
				loadFormTest.action = '${contextPath}' + "/spnCampaignLandingAction_" + method + ".action";
			}
			try {
			
				loadFormTest.submit();
				} catch (error) {
				alert ('An error occurred while processing your document request. Check the filename and filepath.');
				}
			}
			
		function validateDocSelection(method){
			var failedValidation = false;
			var exists = document.getElementById("documentSelection");
				
			if (exists){
				submitForm(method);
			}else{
				document.getElementById('documentSelectionMsg').style.display = "inline";
				failedValidation = true;
				return;
			}
				
		}	
		
		function validateFileName(field, method){
			var failed = false;
			var fieldVal = document.getElementById(field).value;
			if (fieldVal == null || fieldVal.length == 0){
				alert("came here");
				document.getElementById('fileNameSelectionMsg').style.display = "inline";
			}else{
				submitForm(method);
			}

		}
		
		</script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="SPN - Campaign Landing"/>
		</jsp:include>	
	
		<s:form action="spnCampaignLandingAction_displayPage"
			id="spnCampaignForm" name="spnCampaignForm" theme="simple"
			enctype="multipart/form-data">
			<input type="hidden" name="spnID" value="${param.spnID}" />
			<input type="hidden" name="spnNetworkID" value="${param.spnNetworkID}" />
			<div id="page_margins">

				<div id="page">
					<!-- START HEADER -->
					<div id="headerShort">
						<tiles:insertDefinition name="newco.base.topnav" />
						<tiles:insertDefinition name="newco.base.blue_nav" />
						<tiles:insertDefinition name="newco.base.dark_gray_nav" />

						<div id="pageHeader">
							<div>
								<h2>
									Select Provider Network | Campaign Landing
								</h2>
							</div>
						</div>
					</div>

					<!-- END HEADER -->


					<div class="colLeft711">

						<div class="content">

							<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />

							<c:if test="${interested == null}">
								<c:choose>
									<c:when test="${inviteeName != null}">
										${inviteeName}, you have
									</c:when>
									<c:otherwise>
										You have
									</c:otherwise>
								</c:choose>
								  been pre-screened for an opportunity to be part of the
								Select Provider Network described below. Please read the details
								of this Buyer's network and indicate your response below.
								<a href="/MarketFrontend/jsp/public/support/support_faq.jsp">What is a Select Provider
									Network? </a>
								<br>
							</c:if>

							<c:if test="${msg != null}">
								<div style="color: green">
									<p>
										<fmt:message bundle="${serviceliveCopyBundle}" key="${msg}" />
									</p>
								</div>
							</c:if>
							<div
								style="border-color: #9f9f9f; border-width: 1px 1px 1px 1px; border-style: solid; padding-left: 15px; padding-right: 15px">


								<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />

								<jsp:include
									page="/jsp/details/body/html_sections/modules/detailsValidationMessages.jsp" />



								<table width="100%">
									<tr>
										<td width="30%" valign="bottom">
											<c:choose>
												<c:when test="${logoDoc == null}">
													<img src="${staticContextPath}/images/artwork/common/logo_notagline.png" alt="ServiceLive"/>
												</c:when>
												<c:otherwise>
													<img
														src="${contextPath}/spnCampaignLandingAction_displayLogoDoc.action?spnID=${VarSpnID}"
														alt="Custom Logo" />
												</c:otherwise>
											</c:choose>
										</td>
										<td style="color: blue; vertical-align: center">
											<h2>
												${spnInfo.networkName}
											</h2>
										</td>
									</tr>
									<tr>
										<td height="20px">
											&nbsp;
										</td>
									</tr>
								</table>


								<div>
									<table width="100%">
										<tr>
											<td>
												<h3>
													What is the ${spnInfo.networkName} Network?
												</h3>
											</td>
										</tr>
										<tr>
											<td>
												${spnInfo.networkDescription}
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>

										<tr>
											<td>
												<h3>
													How do I become a Member of this Network?
												</h3>
											</td>
										</tr>
										<tr>
											<td>
												${spnInfo.approvalInstructions}
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>

										<tr>
											<td>
												<h3>
													Who do I contact if I have questions about this Network?
												</h3>
											</td>
										</tr>
										<tr>
											<td>
												${spnInfo.contactName} - ${spnInfo.contactEmail}
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;
											</td>
										</tr>

										<c:if test="${spnInfo.numDocs > 0}">
											<tr>
												<td>
													<h3>
														For more information, click the following documents to
														view:
													</h3>
												</td>
											</tr>
	
											<tr>
												<td style="padding-left: 25px">
													<ul>
														<c:forEach items="${spnRelatedDoc}" var="dto">
															<input type="hidden" name="documentSelection" id="documentSelection"
																value="${dto.id}" />
															<li>
																<a
																	href="javascript:validateDocSelection('viewDocument');">
																	${dto.name} </a>
															</li>
														</c:forEach>
													</ul>
												</td>
											</tr>
										</c:if>
									</table>

								</div>



								<c:if test="${interested == null}">
									<div class="clearfix"
										style="text-align: center; padding-top: 15px">
										<h2>
											Are interested in becoming a member of this Network?
										</h2>

									
											<input type="button" 
												src="${staticContextPath}/images/common/spacer.gif"
												style="background-image: url(${staticContextPath}/images/btn/yes.gif); width:43px; height:20px;"
												class="btn20Bevel" value="" onclick="submitForm('buttonInterested')"/>

											<input type="button" id="DeleteButton"
												src="${staticContextPath}/images/common/spacer.gif"
												style="background-image: url(${staticContextPath}/images/btn/no.gif); width:39px; height:20px; "
												class="btn20Bevel" value="" onclick="submitForm('buttonNotInterested')"/>


										

									</div>
								</c:if>

								<c:if test="${interested}">

									<div>

										<s:if test="hasActionErrors()">
											<div style="margin: 10px 0pt;" id="actionError"
												class="errorBox clearfix">
												<s:actionerror />
											</div>
										</s:if>

										<c:if test="${spnInfo.checkboxNetworkRequiresDocuments == true}">
											<tr>
												<td colspan=2>
													<b>The Buyer has indicated you need to attach a document
														as part of your application<br>
														(Maximum 5MB in size)</b>
													<br />
													<p>
														<div style="margin: 10px 0pt; display: none;"
															id="fileNameSelectionMsg" class="errorBox clearfix">
															<label>
																<fmt:message bundle="${serviceliveCopyBundle}"
																	key="document.select.file" />
															</label>
														</div>
														<br />
														<s:file name="upload" id="upload" />
													</p>
												</td>
	
											</tr>										
	
											<tr>
												<td>
													<input type="button" id="attachDocumentBtn"
														onclick="validateFileName('upload', 'documentUpload')"
														class="btn20Bevel"
														style="background-image: url(${staticContextPath}/images/btn/attach.gif); width: 60px; height: 20px;"
														src="${staticContextPath}/images/common/spacer.gif" />
												</td>
											</tr>
										</c:if>

									</div>
								</c:if>

								<table width="100%">
									<tr>
										<td align="right">
											<a href="dashboardAction.action">back to dashboard</a>
										</td>
									</tr>
								</table>
							</div>

						</div>


					</div>

					<!-- END TAB PANE -->
					<div class="colRight255 clearfix">

					</div>

					<div class="clear"></div>
				</div>
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->
			</div>
		</s:form>
	</body>
</html>
