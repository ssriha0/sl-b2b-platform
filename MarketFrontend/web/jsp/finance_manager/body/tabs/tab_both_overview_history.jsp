<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.marketplace.web.dto.FMOverviewHistoryTabDTO" %>
<%
String ua = request.getHeader( "User-Agent" );
boolean isFirefox = ( ua != null && ua.indexOf( "Firefox/" ) != -1 );
boolean isMSIE = ( ua != null && ua.indexOf( "MSIE" ) != -1 );
response.setHeader( "Vary", "User-Agent" );
%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="roleId" scope="request" value="${roleType}" />
<c:set var="provider_role" scope="request" value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="bucksContent" scope="request" value="<%=session.getAttribute("bucksContent")%>" />
<c:set var="licenseContent" scope="request" value="<%=session.getAttribute("licenseContent")%>" />
<c:set var="maxNoOfDays" scope="request" value="<%=session.getAttribute("maxNoOfDays")%>" />
	
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.overviewHistory"/>
	</jsp:include>	
<c:if test="${tab == 'History'}">
	<c:choose>

	<c:when test="${fmOverHistDTO != null}">
	    <c:set var="exportLimitMessageCheck" value="${fmOverHistDTO.exportMessageCheck}" />
	    <c:set var="exportLimitMessage" value="${fmOverHistDTO.message}" />
		<c:set var="message" value="${fmOverHistDTO.dateRangeMessage}" />
		<c:set var="transaction" value="${fmOverHistDTO.transaction}" />
		<c:set var="totalCount" value="${fmOverHistDTO.totalCount}" />
		<c:set var="recordSize" value="${fmOverHistDTO.recordSize}" />
		
		<c:choose>
		<c:when test="${fmOverHistDTO.intervalChecked != null}">
			<c:set var="intChecked" value="${fmOverHistDTO.intervalChecked}" />
		</c:when>	
		<c:otherwise>
			<c:set var="drChecked" value="${fmOverHistDTO.dateRangeChecked}" />
		</c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<c:set var="message" value="${dateRangeMessage}" />
		<c:set var="transaction" value="${transaction}"/>

		<c:choose>
		<c:when test="${intervalChecked != null}">
			<c:set var="intChecked" value="${intervalChecked}"/>
		</c:when>	
		<c:otherwise>
			<c:set var="drChecked" value="${dateRangeChecked}"/>
		</c:otherwise>
		</c:choose>
	</c:otherwise>
	</c:choose>
</c:if>


	

	<div class="content">
	
	<c:choose>
	<c:when test="${tab == 'History'}">
			<c:if test="${fmOverHistDTO.errorMsg != null}">	
				<div  style="margin: 10px 0pt;" id="errorMsg" class="errorBox clearfix">
					${fmOverHistDTO.errorMsg}
				</div>
			</c:if>
		
		<p style="padding: 10px;">This is a complete, searchable record of your ServiceLive transaction history. Search within a set period of time, or within a range of dates (maximum interval of 1 year) that you choose. You can export transactions and reports to the file format of your choice.</p>
		
		<h4 style="padding: 0 10px"> Search By </h4>
		
		<s:form action="fmOverviewHistory_search.action" id="historySearch" theme="simple" method="post" name="historySearch">		
		<table cellpadding="0" cellspacing="0" style="margin: 10px;">
			<tr>
				<td width="100">
					<p class="formFieldOffset"><input type="radio" class="antiRadioOffsets" id="radioButton" ${intChecked} name="radioButton" value="1"  />Interval</p>
				</td>
				<td>
					<div  style="width:800px;background-color: #ffffff; display:inline;" id="intervalDropDown" >
						<p>
						<select style="width:800px%;background-color: #ffffff;" size="1" name="selectedCalInterval" id="selectedCalInterval">
							    <c:forEach var="lookupVO" items="${calendarList}" >            		
				                     <c:choose>
				            			<c:when test="${lookupVO.key == fmOverHistDTO.selectedCalInterval}"> 
						                    <option selected value="${lookupVO.key}">
						                         ${lookupVO.value}
						                  	</option>
				               			</c:when>
				               			<c:otherwise>
				               				 <option value="${lookupVO.key}">
						                         ${lookupVO.value}
						                  	</option>
				               			</c:otherwise>
				              		</c:choose>                              
					             </c:forEach>
							</select>
						</p>
					</div>		
				</td>	
			</tr>
	
			<tr>
				<td>
					<p class="formFieldOffset">
						<input type="radio"
						class="antiRadioOffsets" id="radioButton" ${drChecked} name="radioButton" value="2"
						/>
						Date Range
					</p>
				</td>
				<td>
					<div  style="margin: 10px 0pt; display:inline;" id="calendars" >
						<p>
							<input type="hidden" id="noOfDays" value="${maxNoOfDays}" />
							<input type="text" dojoType="dijit.form.DateTextBox" class="shadowBox" id="dojoCalendarFromDate" name="dojoCalendarFromDate" value="${fmOverHistDTO.dojoCalendarFromDate}" required="true" lang="en-us" />
							to
							<input type="text" dojoType="dijit.form.DateTextBox" class="shadowBox" id="dojoCalendarToDate" name="dojoCalendarToDate" value="${fmOverHistDTO.dojoCalendarToDate}" required="true" lang="en-us" />
						</p>
					</div>
				</td>
			</tr>
			<tr><td colspan="2">
			
			<div style="margin: 10px 0pt;" id="limitalert">
				&nbsp;&nbsp;&nbsp;The date range you choose should not exceed 1 year.
			</div>
			<div style="margin: 10px 1pt;display:none" class="errorBox clearfix" id="datelimitalert" >
				 &nbsp;&nbsp;<img src="${staticContextPath}/images/icons/incIcon.gif">&nbsp;
				<div  id="datelimitalertmessage" style="width: 450px;display: inline"></div>
			</div>
			</td></tr>
			<tr>
				<td></td>
				<td>
					<p>
						<input type="button" id="removeDocumentBtn" onclick="search('historySearch')" class="btn20Bevel" style="background-image: url(${staticContextPath}/images/btn/submit.gif); width:70px; height:22px;" src="${staticContextPath}/images/common/spacer.gif" />
					</p>
				</td>
			</tr>
		</table>
		</s:form>
		<h4>
		${message}
		</h4>
</c:when>

<c:when test="${tab == 'Overview'}">
	<h3 style="padding: 10px;">
		Welcome to your ServiceLive Wallet
	</h3>
	<p style="padding: 10px;">
		<c:choose>
		<c:when test="${SecurityContext.roleId == provider_role}">
			<%-- Provider--%>
	  		Think of this page as your ServiceLive Wallet. Get the current status of all recent transactions to your ServiceLive account, along with your current balance.<br>
	  		Currently, we are beta testing with select users. More functionality will be available in the near future.
		</c:when>
		<c:otherwise>
			<%-- Buyer--%>
			Think of this page as your ServiceLive Wallet. Get the current status of
			all recent transactions to your ServiceLive account, including service
			orders that are currently active.
		</c:otherwise>
		</c:choose>
	</p>	
	<h4>
		Recent Activity
	</h4>
</c:when>
</c:choose>
		<div class="balanceTableContainer" style="height:23px;overflow-y:hidden;">
			<table cellpadding="0" cellspacing="0" class="balanceTableHeader" width="100%" >
				<tr class="tblHead">
					<th class="id">ID #</th>
					<th class="date">Date/Time</th>
					<th class="desc">Description</th>
					<th class="serv">Service Order #</th>
					<th class="stat">Status</th>
					<th class="amnt">Amount</th>
					<th class="bal">Balance</th>
					<c:if test="${recordSize > 24}">
						<th width="7px"> </th>
					</c:if>
				</tr>
			</table>
		</div>
		<div class="balanceTableContainer" style="height: 1024px">
			<table cellpadding="0" cellspacing="0" class="gridTable balanceTableHeader">
	     	<c:forEach var="history" items="${transaction}" varStatus="historyIndex">
     	
     			<c:choose>
				<c:when test="${historyIndex.count % 2 == 1}">
	                	<tr >
				</c:when>
	            <c:otherwise>
	                 <tr bgcolor="#ededed">
	        	</c:otherwise>
	        	</c:choose>
				<c:set var="titleStr" value=""/>
	        	<c:if test="${SecurityContext.slAdminInd || buyerAdminInd}">
	        		<c:set var="titleStr" value="${history.bankName} ${history.accountNumber}"/>
	        	</c:if>
		    			<td class="id">${history.transactionNumber}</td>
						<td class="date">${history.modifiedDate}</td>
						<td class="desc" title="${titleStr}">${history.type}</td>
						<td class="serv">
						<c:choose>
							<c:when test="${SecurityContext.roleId == 5}">
								<a href="monitor/soDetailsController.action?soId=${history.soId}&amp;displayTab=Posted"">
							</c:when>
							<c:otherwise>	
								<a href="monitor/soDetailsController.action?soId=${history.soId}">
							</c:otherwise>
						</c:choose>
								${history.soId}
							</a>
						</td>
						<td class="stat">${history.status}</td>
						<td class="amnt">${history.amount}</td>
						<td class="bal">${history.availableBalance}</td>	
			    	</tr>
			</c:forEach>

			</table>
			 <c:if test="${tab == 'History'}">
				<table><tr><td><c:choose>
					<c:when test="${empty overviewHistoryDTO}">
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="no_records_found" />
					</c:when>
					<c:otherwise>
						<jsp:include page="/jsp/paging/historypagingsupport.jsp"/>
					</c:otherwise>
			    </c:choose>
			    </td></tr></table>
			 </c:if>
		</div>
		<c:if test="${tab == 'History'}">
        <div class="clearfix">
        	<br/>	
			<p style="padding-left: 5px;">
				
		<c:if test="${exportMessageCheck == 'limitExceed' } && ${exportLimitMessage != null }">
				<table><tr><td>
				<div class="warningBox clearfix"  style="height:auto;" >
					<p class="warningMsg contentWellPane"><img src="${staticContextPath}/images/icons/incIcon.gif">
						<span>  ${exportLimitMessage}</span>
					</p>
				</div>
				</td></tr></table>
				<br>
			</c:if>
				
			<c:if test="${totalCount != 0}">
				<a href="fmOverviewHistory_exportToExcel.action">
				    <img width="101"  height="20" class="btn20Bevel inlineBtn"  style="background-image: url(${staticContextPath}/images/btn/exportToFile.gif);" src="${staticContextPath}/images/common/spacer.gif" >
				</a>
			</c:if>		
			</p>
		</div>

		</c:if>
		
	</div>
	