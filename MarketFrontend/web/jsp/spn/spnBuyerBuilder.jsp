<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<title><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.title" /></title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />


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

	</head>
	<body class="tundra">
	    
		<s:form action="spnBuyerBuilderAction_displayPage.action"
			theme="simple">
			<input type="hidden" name="spnID" value="${param.spnID}" />
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
									<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.header"/>
								</h2>
							</div>
						</div>
					</div>

					<!-- END HEADER -->


					<div class="colLeft711">
						<div class="content">

							<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />

							<table width="100%" border=0>
								<tr>
									<td width="50%">
										<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.network_name"/></b>
										<br />
										<s:textfield id="networkName" name="networkName"
											value="%{networkName}" cssStyle="width:200px" theme="simple" />
									</td>
									<td>
										&nbsp;
									</td>
								</tr>

								<tr height="15px">
									<td colspan=2></td>
								</tr>

								<tr>
									<td width="50%">
										<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.network_contact_name"/></b>
										<br>
										<s:textfield id="contactName" name="contactName"
											value="%{contactName}" cssStyle="width:200px" theme="simple" />
									</td>
									<td align="right">
										<table>
											<tr>
												<td align="left">
													<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.network_contact_email"/></b>
													<br>
													<s:textfield id="contactEmail" name="contactEmail"
														value="%{contactEmail}" cssStyle="width:200px"
														theme="simple" />
												</td>
										</table>
									</td>
								</tr>
								<tr height="15px">
									<td colspan=2></td>
								</tr>

								<tr>
									<td width="100%" colspan=2>
										<c:choose>
											<c:when test="${theCriteria.mainServiceCategoryName != null}">
												<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.criteria"/></b>
												<br>
												<table border=0 width=100%>
													<tr>
														<td>
															<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.main_cat_name"/></b>
															${theCriteria.mainServiceCategoryName}
															<br />
														</td>
														<td>
															<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.so_closed"/></b>
															${theCriteria.totalNumOfServiceOrdersClosed}
														</td>
													</tr>
													<c:if test="${theCriteria.cgliInsurance}">
														<tr>
															<td colspan=2>
																<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.gen_ins_amt"/></b> $
																<fmt:formatNumber
																	value="${theCriteria.cgliInsuranceAmount}"
																	type="NUMBER" minFractionDigits="2"
																	maxFractionDigits="2" />
															</td>
														</tr>
													</c:if>

													<c:if test="${theCriteria.wciInsurance}">
														<tr>
															<td colspan=2>
																<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.workmans_comp_amt"/></b> $
																<fmt:formatNumber
																	value="${theCriteria.wciInsuranceAmount}" type="NUMBER"
																	minFractionDigits="2" maxFractionDigits="2" />
															</td>
														</tr>
													</c:if>

													<c:if test="${theCriteria.vliInsurance}">
														<tr>
															<td colspan=2>
																<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.auto_ins_amt"/></b> $
																<fmt:formatNumber
																	value="${theCriteria.vliInsuranceAmount}" type="NUMBER"
																	minFractionDigits="2" maxFractionDigits="2" />
															</td>
														</tr>
													</c:if>

													<%-- Optional Stars/Ratings criteria --%>
													<c:choose>
														<c:when test="${theCriteria.ratings != null && theCriteria.ratings > 0.0}">
															<tr>
																<td>
																	<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.min_rating"/></b> ${theCriteria.ratings}
																</td>
																<td>
																	<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.include_non_rated"/></b> ${theCriteria.includeNonRated}
																</td>																
															</tr>
														</c:when>
													</c:choose>
													
													<tr>
														<td>
															<c:choose>
																<c:when test="${theCriteria.languageDescr != null}">
																	<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.language"/></b> ${theCriteria.languageDescr}
																</c:when>
																<c:otherwise>
																	&nbsp;
																</c:otherwise>
															</c:choose>
														</td>
														<td>
															&nbsp;
														</td>
													</tr>
													<tr>
														<td>
															<c:if
																test="%{theCriteria != null  && theCriteria.tasks[0] != null}">
																<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.task"/> <s:iterator value="theCriteria.tasks"
																		id="task" status="status">
																		<tr>
																			<td>
																				${status.index +1}) ${task.category} ${task.skill}
																				<br>
																			</td>
																		</tr>
																	</s:iterator>
															</c:if>
														</td>
													</tr>
												</table>
											</c:when>
											<c:otherwise>
												<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.criteria"/></b><br/>
												<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.msg.no_criteria"/>
											</c:otherwise>
										</c:choose>

									</td>
								</tr>
								<tr>
									<td>
										<c:if test="${!spnLocked}">
											<s:submit type="input" method="buttonAddCriteria"
												src="${staticContextPath}/images/common/spacer.gif"
												cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/edit.gif); width:49px; height:20px;"
												cssClass="btn20Bevel" theme="simple" value="" />
										</c:if>
									</td>
								</tr>

								<tr height="15px">
									<td colspan=2>
									</td>
								</tr>

								<tr>
									<td colspan=2>
										<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.network_desc"/></b>
										<br>
										<s:textarea id="networkDescription" name="networkDescription"
											value="%{networkDescription}"
											cssStyle="width:665px; height:30px" theme="simple" />

									</td>
								</tr>
								<tr height="15px">
									<td colspan=2></td>
								</tr>
								<tr>
									<td colspan=2>
										<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.approval_instr"/></b>
										<br>
										<s:textarea id="approvalInstructions"
											name="approvalInstructions" value="%{approvalInstructions}"
											cssStyle="width:665px; height:50px" theme="simple" />
									</td>
								</tr>

								<tr height="15px">
									<td colspan=2></td>
								</tr>

								<tr>
									<td colspan=2>
										&nbsp;
									</td>
								</tr>

								<tr height="15px">
									<td colspan=2></td>
								</tr>

								<tr>
									<td colspan=2>
										<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.docs"/></b>
										<br />

										<table border=0 class="scrollerTableHdr spnBuilderHdr">
											<tr>
												<td class="column2" align="center">
													<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.doc_attach"/>
												</td>
												<td class="column3">
													<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.doc_name"/>
												</td>
												<td class="column4">
													<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.label.doc_desc"/>
												</td>
											</tr>
										</table>
										<div class="grayTableContainer" style="width: 668px;">
											<table border="0" cellpadding="0" cellspacing="0"
												class="gridTable spnBuilder">
												<s:iterator value="documentsDetailsList" status="status"
													id="dto">
													<tr>
														<td class="column2" align="center">
															<s:checkbox
																name="documentsDetailsList[%{#status.index}].checked"
																theme="simple"
																value="%{documentsDetailsList[#status.index].checked}" />
														</td>
														<td class="column3">
															${dto.name} &nbsp;
														</td>
														<td class="column4">
															${dto.description} &nbsp;
														</td>
													</tr>
												</s:iterator>
											</table>
										</div>
									</td>
								</tr>

								<tr height="15px">
									<td colspan=2></td>
								</tr>

								<tr>
									<td colspan="2">
										<s:checkbox name="checkboxNetworkRequiresDocuments"
											value="%{checkboxNetworkRequiresDocuments}" theme="simple" />
										<fmt:message bundle="${serviceliveCopyBundle}" key="spn.builder.msg.doc_required"/>
									</td>
								</tr>
								<tr>
									<td>
										<s:submit type="input" method="buttonSave"
											src="${staticContextPath}/images/common/spacer.gif"
											cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/save.gif); width:49px; height:20px;"
											cssClass="btn20Bevel" theme="simple" value="" />

										<c:if test="${!spnLocked}">
											<s:submit type="input" id="DeleteButton"
												method="buttonDelete"
												onclick="return confirmDeleteSPN()"
												src="${staticContextPath}/images/common/spacer.gif"
												cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/delete.gif); width:71px; height:20px; "
												cssClass="btnBevel" theme="simple" value="" />
										</c:if>
									</td>
									<td align="right">
										<a href="spnBuyerLanding_displayPage.action"><fmt:message bundle="${serviceliveCopyBundle}" key="label.cancel"/></a>
									</td>
								</tr>
							</table>

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
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
			
		<script type="text/javascript">
			function confirmDeleteSPN()
			{
				if ( window.confirm('Do you really want to delete this SPN?') )
		        	return true;
		        else
					return false;
			}
		</script>
	</body>
</html>
