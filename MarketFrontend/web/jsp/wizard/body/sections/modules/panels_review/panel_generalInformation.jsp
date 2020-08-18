<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />

<style type="text/css">
#termsCondReview ul{
	padding-left:10px;
}
#termsCondReview ol{
	padding-left:15px;
}
#overviewReview ul{
	padding-left:10px;
}
#overviewReview ol{
	padding-left:15px;
}
#spInstrReview ul{
	padding-left:10px;
}
#spInstrReview ol{
	padding-left:15px;
}
</style>
<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="General Information"
	class="contentWellPane">
	<table cellpadding="0" cellspacing="0" class="noMargin">
		<tr>
			<td width="300">
				<p>				
					<c:if test="${reviewDTO.logodocumentId != null}">
						<img src="${contextPath}/displayBuyerDocument.action?docId=${reviewDTO.logodocumentId}" alt="image" onerror="this.src='${staticContextPath}/images/pixel.gif';"/>
					</c:if>
				</p>
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<tr>
						<td width="120">
							<b>Service&nbsp;Order&nbsp;#&nbsp;</b>
						</td>
						<td width="160">
							${reviewDTO.id}
						</td>
					</tr>
					
				  <tr>
						<c:if test="${reviewDTO.statusString!=null}">
						<td>
							<b>Primary&nbsp;Status&nbsp;</b>
						</td>
						<td>${reviewDTO.statusString}
						<c:if test="${reviewDTO.statusString=='Posted'}">
							<c:if test="${not empty reviewDTO.methodOfRouting && reviewDTO.methodOfRouting=='EXCLUSIVE'}">
								(Exclusive)
							</c:if>
							<c:if test="${not empty reviewDTO.methodOfRouting && reviewDTO.methodOfRouting=='GENERAL'}">
								(General)
							</c:if>	
						</c:if>
						</td>
					    </c:if>
					</tr>
				 <tr>
						<td>
							<b>Substatus</b>
						</td>
						<td>
						<div id="subStatusString" style="display:block">
							<c:if test="${not empty reviewDTO.subStatus}">
								<fmt:message bundle="${serviceliveCopyBundle}"
									key="workflow.substate.${reviewDTO.subStatus}" />
							</c:if>
														
							<c:if test="${reviewDTO.showSubStatusChange==true}" >
								<input type="button" src="${staticContextPath}/images/common/spacer.gif"
									style="background-image: url(${staticContextPath}/images/btn/change.gif);
									width:60px; height:20px;"
									class="btn20Bevel inlineBtn" 
									onclick="displaySubStatuses()"/>
							</c:if>	
						</div>
						<div id="subStatusDropdown" style="display:none">
							<select style="width: 150px;" class="grayText" id="subStatusId" >
								<c:forEach var="subStatusVO" items="${SOStatusSubStatuses}" >									
									<option value="${subStatusVO.subStatusId}" <c:if test="${reviewDTO.subStatus==subStatusVO.subStatusId}" > selected </c:if>>
										${subStatusVO.subStatusName}
									</option>
								</c:forEach>
							</select>
							<br/><br/>
							<input type="button" src="${staticContextPath}/images/common/spacer.gif"
								style="background-image: url(${staticContextPath}/images/btn/submit.gif);
								width:70px; height:20px;"
								class="btn20Bevel inlineBtn" 
								onclick="submitSummarySubStatus()"/>
							&nbsp;&nbsp;&nbsp;
							<input type="button" src="${staticContextPath}/images/common/spacer.gif"
								style="background-image: url(${staticContextPath}/images/btn/cancel.gif);
								width:55px; height:20px;"
								class="btn20Bevel inlineBtn" 
								onclick="cancelSummarySubStatus()"/>
								
						</div>
						</td>
					</tr>
				
				
				</table>
			</td>
			<td>
				<table cellpadding="0" cellspacing="0"
					class="adjustedTableRowPadding">
					<c:if test="${reviewDTO.createdDateString != null && reviewDTO.modifiedDate != null}">
					<tr>
						<td>
							<b>Created</b>
						</td>
						<td width="15px"></td>
						<td align="left">
							${reviewDTO.createdDateString}
						</td>
					</tr>

					<tr>
						<td>
							<b>Last Updated</b>
						</td>
						<td width="15px"></td>
						<td align="left">
							${reviewDTO.modifiedDate}
						</td>
					</tr>
					</c:if>
					<tr>
						<td colspan="3">
							&nbsp;

						</td>
					</tr>
					<tr>
						<td valign="top">
							<b>Appointment&nbsp;Dates</b>
						</td>
						<td width="15px"></td>
						<td align="left">
							${reviewDTO.appointmentDates}
						</td>
					</tr>
					<tr>
						<td>
							<b>Service Window</b>
						</td>
						<td width="15px"></td>
						<td align="left">
							${reviewDTO.serviceWindow}
						</td>
					</tr>
					<tr>
						<td colspan="3">
							[ ${reviewDTO.serviceWindowComment} ]
						</td>
					</tr>				
				</table>
			</td>
	</table>
	<p style="word-wrap: break-word;">
		<strong>Title</strong>
		<br />
		${reviewDTO.title}
	</p>
	<p>
		<strong>Overview</strong>
		<br />
		<c:choose>
			<c:when test="${fn:length(reviewDTO.overview) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
				<div id="overviewReview" class="inputArea" style="height: 200px;width: 600px;word-wrap: break-word;">
					${reviewDTO.overview}
				</div>
			</c:when>
			<c:otherwise>
			<div id="overviewReview" style="width: 600px;word-wrap: break-word;">${reviewDTO.overview}</div>
			</c:otherwise>
		</c:choose>
		
	</p>
	<p>
		<strong>Buyer's Terms & Conditions</strong>
		<br />
		<c:choose>
			<c:when test="${fn:length(reviewDTO.buyersTerms) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
				<div id="termsCondReview" class="inputArea" style="height: 200px;width: 600px;word-wrap: break-word;" >
					${reviewDTO.buyersTerms}
				</div>
			</c:when>
			<c:otherwise>
				<div id="termsCondReview" style="width: 600px;word-wrap: break-word;">${reviewDTO.buyersTerms}</div>
			</c:otherwise>
		</c:choose>
	</p>
	<p>
		<strong>Special Instructions</strong>
		<br />
		<c:choose>
			<c:when test="${fn:length(reviewDTO.specialInstructions) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
				<div id="spInstrReview" class="inputArea" style="height: 200px;width: 600px;word-wrap: break-word;" >
					${reviewDTO.specialInstructions}
				</div>
			</c:when>
			<c:otherwise>
				<div id="spInstrReview" style="width: 600px;word-wrap: break-word;">${reviewDTO.specialInstructions}</div>
			</c:otherwise>
		</c:choose>
	</p>
	<hr />
	<table cellpadding="0" cellspacing="0" class>
		
			<tr>
				<td colspan="2">
					<strong>Buyer References </strong>
				</td>
			</tr>
			
		     <s:if test="%{reviewDTO.selByerRefDTO.size == 0}">
			   <tr>
			 	<td colspan="2">
					[Information Not Entered] 
				</td>
			  </tr>
	        </s:if>
	        <s:else>
				<tr>
					<td >
					  <table cellpadding="0" cellspacing="0" class>
				    	 <c:forEach var="buyerRef" items="${reviewDTO.selByerRefDTO}" varStatus="rowCounter">
					     	<c:if test="${rowCounter.count % 2 == 1}">	
					        	<tr>
					            	<td width="150" >	   
							        	<strong>${buyerRef.refType}</strong>
							        </td>
							        <td width="80">
								        ${buyerRef.refValue}
							        </td>
						        </tr>
						     </c:if>		
						 </c:forEach>
				     </table>
				  </td>	 
					<td width="180"></td>   
 					<td>
						 <table cellpadding="0" cellspacing="0"  class>
					    	<c:forEach var="buyerRef" items="${reviewDTO.selByerRefDTO}" varStatus="rowCounter">
					     		<c:if test="${rowCounter.count % 2 == 0}">	
					        		<tr>
					            		<td width="150" >	   
							        		<strong>${buyerRef.refType}</strong>
							       	 	</td>
							       		<td width="80">
								        	${buyerRef.refValue}
							        	</td>
						        	</tr>
						     	</c:if>		
						 	</c:forEach>
				         </table>
					</td>	
				</tr>
			
		</s:else>
	</table>	
		 	  	
	    
</div>
