<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <body>
    	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    	<script type="text/javascript" charset="utf-8">
    		function populate(id){
    			
    			if(document.getElementById("category"+id).style.display=="block"){
    				document.getElementById("category"+id).style.display = "none";	
    			}else{
    				document.getElementById("category"+id).style.display = "block";
    			}
    			
    		}
    	</script>
			<div id="fragment-1" class="clearfix">
				<div class="hpDescribe clearfix">
					<div>
						<h3 style="margin: 0px;">
											Company Overview <span>ID#${ProviderInfo.companyPublicInfo.vendorId}</span>
						</h3>
										<p>${ProviderInfo.companyPublicInfo.businessDesc}</p>
					</div>
				</div>

								<div class="hpDescribe clearfix">
									<h2>Availability</h2>
									<div class="clearfix weekcal">
										<table cellspacing="1">
											<tr>
												<td>Sun</td>
												<td>Mon</td>
												<td>Tue</td>
												<td>Wed</td>
												<td>Thur</td>
												<td>Fri</td>
												<td>Sat</td>
											</tr>
											<tr class="weekdays">
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.sunStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.sunEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.sunStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.sunEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.sun24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.monStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.monEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.monStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.monEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.mon24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.tueStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.tueEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.tueStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.tueEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.tue24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.wedStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.wedEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.wedStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.wedEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.wed24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.thuStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.thuEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.thuStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.thuEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.thu24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.friStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.friEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.friStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.friEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.fri24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
												<td>
													<c:choose>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.satStart != null && ProviderInfo.providerPublicInfo.generalScheduleInfoVO.satEnd != null}">
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.satStart}"
																pattern="h:mm a" />
															<br />to<br />
															<fmt:formatDate
																value="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.satEnd}"
																pattern="h:mm a" />
														</c:when>
														<c:when
															test="${ProviderInfo.providerPublicInfo.generalScheduleInfoVO.sat24Ind == 1}">
Any Time
</c:when>
														<c:otherwise>											N/A
</c:otherwise>
													</c:choose>
												</td>
											</tr>
										</table>
									</div>
								</div>
								<a id='Details' ></a>
								<div class="hpDescribe clearfix">
									<h2>Details</h2>

									<h3 class="clearfix" style="display: block; margin: 0px;">Services</h3>

									<ul class="accList ui-accordion-container">

										
										<c:choose >
										<c:when test="${ProviderInfo.providerPublicInfo.resourceSkillList != null}">
											<c:forEach  items="${ProviderInfo.providerPublicInfo.resourceSkillList}" var="node" varStatus="rowCounter">
											<c:set var="count" value="${rowCounter.count}" />
											<li id="list${count}">
													<a onClick="populate(${count})" href='#Details' class="ui-accordion-link"><c:out value="${node.nodeName}" /></a>
													<div id="category${count}" style="height: auto;">
													<c:forEach var="entry" items="${ProviderInfo.skillsInfo}">
														<c:if test="${entry.key == node.nodeId}">
																	<table class="offerings" border="0" cellspacing="0">
																		<c:choose>
																			<c:when
																				test="${not empty entry.value.serviceTypes }">
																				<tr>
																					<th>
																						&nbsp;
																					</th>
																					<c:forEach items="${entry.value.serviceTypes}"
																						var="stype">
																						<th>
																							${stype.description}
																						</th>
																					</c:forEach>
																				</tr>
																			</c:when>
																			<c:otherwise>
																				<p>
																					Currently, there are no skills categories are
																					available for this service provider.
																				</p>
																			</c:otherwise>
																		</c:choose>



																		<c:choose>
																			<c:when
																				test="${not empty entry.value.skillTreeList }">

																				<c:forEach items="${entry.value.skillTreeList}"
																					var="snode">
																					<c:if test="${snode.level > 1 && snode.active == true}">
																						<tr>
																							<c:if test="${snode.level == 2 }">
																								<td class="title">
																							</c:if>
																							<c:if test="${snode.level  > 2 }">
																								<td class="title">&nbsp;&nbsp;&nbsp;&nbsp;
																							</c:if>
																							${snode.nodeName}
																							</td>
																							<c:forEach items="${snode.serviceTypes}"
																								var="snodetype">
																								<c:choose>
																								<c:when test="${snodetype.active == true}">
																									<td>
																										<img
																											src="${staticContextPath}/images/icons/02.png"
																											alt="" />
																									</td>
																								</c:when>
																								<c:when test="${snodetype.active == false && snode.overriderSkillType == false}">
																									<td>
																										<img
																											src="${staticContextPath}/images/icons/blank.png"
																											alt="" />
																									</td>
																								</c:when>
																									<c:otherwise>
																										<td>
																										&nbsp;
																									</td>
																									</c:otherwise>
																								</c:choose>
																							</c:forEach>
																						</tr>
																					</c:if>
																				</c:forEach>

																			</c:when>
																			<c:otherwise>
																				<p>
																					Currently, there are no skills categories are
																					available for this service provider.
																				</p>
																			</c:otherwise>
																		</c:choose>
																	</table>
															</c:if>
													</c:forEach>
												</div>
											</li>
											</c:forEach>
										</c:when>
										<c:otherwise>
											 <p>Currently, there are no skills categories for this service provider.</p>
										 </c:otherwise>
										</c:choose >
									</ul>

									<h3 style="margin-top: 0px;">Statistics</h3>

									<div class="creds clearfix">
										<div class="credlist">
											<h4>Approved Since: <fmt:formatDate value="${ProviderInfo.providerPublicInfo.bgVerificationDate}" pattern="MMMM dd, yyyy" /></h4>
										</div>
										<div class="credlist">
											<h4>
											<c:choose >
												<c:when test="${ProviderInfo.providerPublicInfo.generalInfoVO.totalSoCompleted >= 0}">
													${ProviderInfo.providerPublicInfo.generalInfoVO.totalSoCompleted }
												</c:when>
												<c:otherwise>
											 		0
										 		</c:otherwise>
											</c:choose>
											Jobs Completed with ServiceLive</h4>
										</div>
									</div>

										<h3> Credentials </h3>

										<div class="creds clearfix ">
										<c:choose >
											<c:when test="${not empty ProviderInfo.providerPublicInfo.credentialsList}">
													<c:forEach items="${ProviderInfo.providerPublicInfo.credentialsList}" var="credinfo">

														<div class="credlist">

															<h4><c:out value="${credinfo.licenseName}" />
															<c:if test="${credinfo.isVerified == true}" >
																<img alt="Verified Credential" src="${staticContextPath}/images/common/verified.png" />
															    <!-- SL20014-to view provider firm uploaded documents for buyer -->
																<c:if test="${buyerSPNViewDocPermission == true && credinfo.currentDocumentID != null && credinfo.currentDocumentID > 0}">
																	<c:set var="docId" value="${credinfo.currentDocumentID}">
																	</c:set>
																	<a target="blank" href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=${docId}">
																	<img src="${staticContextPath}/images/images_registration/icons/pdf.gif" title="Click to view document" width="13px;" />
																	</a>
																</c:if>
																<!-- SL20014-end -->
															</c:if>
															</h4>
															<c:out value="${credinfo.issuerName}" /><br/>
															<c:if test="${credinfo.isVerified == true}" >
															Date Verified: <fmt:formatDate value="${credinfo.modifiedDate}" pattern="MMMM dd, yyyy" />
															<br />
															</c:if>
															<fmt:formatDate value="${credinfo.issueDate}" pattern="yyyy" /> - <fmt:formatDate value="${credinfo.expirationDate}" pattern="yyyy" />
															<br />&nbsp;
														</div>
														</c:forEach>
												</c:when>
										<c:otherwise>
											 <p>Currently, there are no licenses or certificates on file for this service provider.</p>
										 </c:otherwise>
										</c:choose >
										</div>

								</div>
							</div>
				
  </body>
</html>
