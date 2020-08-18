<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />

<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/bulletinBoard/main.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css">
<style type="text/css">
input.button {
	cursor: pointer;
}

#status tr:nth-child(even) {
	background: #ededed;
}

table#status td {
	word-wrap: break-word;
}

table#headings td {
	text-align: center;
}

img.disabled {
	opacity: 0.4;
	filter: alpha(opacity =   40); /* For IE8 and earlier */
}
</style>
<c:set var="numberOfReports" value="${fn:length(exportStatus)}" />
<c:choose>
<c:when test="${numberOfReports >0 && numberOfReports <6}">
	<c:set var="height" value="${ numberOfReports * 40  }"></c:set>
</c:when>
<c:when test="${numberOfReports == 0}">
	<c:set var="statusMsg" value=""></c:set>
	<c:set var="height" value="40"></c:set>
</c:when>
<c:otherwise>
	<c:set var="height" value="200"></c:set>
</c:otherwise>
</c:choose>
<div style="padding-left: 5px; padding-top: 2px; padding-bottom: 2px;">
<c:if test="${formType == formInd }">
	<c:if test="${not empty errorMessage}">
		<div id="errReport" class="errorBox"style="display: block;">
			<p class="errorMsg">${errorMessage}</p>
		</div>
	</c:if>
</c:if>
	<div id="errReportCount_${formInd}" class="errorBox"style="display: none;"></div>
</div>
<div style="font-weight: bold; padding-bottom: 5px; padding-top: 2px;">
	${statusMsg}
</div>
<div class="balanceTableContainer"
	style="height: 23px; overflow-y: hidden; width: 680px;">
	<table class="gridTable balanceTableHeader" id="headings" width="678px"
		cellspacing="0px" cellpadding="0px">
		<tr class="tblHead">
			<td width="7%">
				ID #
			</td>
			<td width="8%">
				Delete
			</td>
			<td width="40%">
				Report Name
			</td>
			<td width="11%">
				Status
			</td>
			<td width="30%">
				User Action
			</td>
			<td width="2%">
				&nbsp;
			</td>
		</tr>
	</table>
</div>
<div class="balanceTableContainer" style="height: ${height}px; width: 679px;">
	<table class="gridTable balanceTableHeader" id="status" cellspacing="0"
		cellpadding="0" width="670px" style="table-layout: fixed;">
		<c:choose>
		<c:when test="${numberOfReports >0}">
			<c:forEach items="${exportStatus}" var="reports"
				varStatus="reportIndex">
				<c:choose>
				<c:when test="${reportIndex.count % 2 == 1}">
	             <tr >				
				</c:when>
	            <c:otherwise>
	                 <tr bgcolor="#ededed">
	        	</c:otherwise>
				</c:choose>
					<td width="8%">
						${reports.resourceId}
						<input type="hidden" value="${reports.totalRecords}"
							id="recCount${reports.reportId}" />
					</td>
					<td width="5%">
					<c:choose>
						<c:when test="${reports.reportStatus != 'In Process'}">
							<img alt="Delete"
								src="${staticContextPath}/provider/images/cross.png"
								id="deleteBtn_${reports.reportId}" name="delete"
								formInd='${formInd}'
								style="cursor: pointer;" report="${reports.reportId}"
								onclick="displayDelete(this);">
							<div id="deletePopUp_${reports.reportId}" class="criteriaLayer"
								style="display: none; z-index: 999999999; width: 270px; height: auto; overflow: auto; border: solid 1px #ccc; background: #fff; position: absolute;">							
								<div style="background-color: #00A0D2;color: #FFFFFF;text-align: center;font-weight: bold;">
									<table width="100%" border="0">
										<tr>
											<td style="font-size:13px;border:0;padding: 1px;">Confirm Delete</td>
											<td width="10%" style="border:0;padding: 1px;">
											<div style="padding-left: 5px;">
											<img alt="Close"
												src="${staticContextPath}/images/s_icons/cancel.png"
												id="close_${reports.reportId}" name="close"
												onclick="closePopUp(this);" style="cursor: pointer;">
											</div>
											</td>
										</tr>
									</table>
								</div>
								<div style="padding-left: 5px;">
									<span>Are you sure you want to delete this report/request?</span>
								</div>
								<div>
									<table width="100%">
										<tr>
											<td colspan="2" width="60%" style="border:0"> &nbsp; </td>
											<td style="border:0" style="border:0"><input type="button" class="popUpBtn" value="Yes" id="delete_${reports.reportId}" onclick="return deleteReport(this)"
													formInd='${formInd}' style="cursor: pointer;width: 55px;" report="${reports.reportId}" /></td>
											<td style="border:0"><input type="button" class="popUpBtn" value="No" id="no_${reports.reportId}" onclick="return closeBtn(this)" style="cursor: pointer;width: 55px;"/></td>
										</tr>
									 </table>
								 </div>
							</div>
							
						</c:when>
						<c:otherwise>
							<img alt="Delete" class="disabled"
								src="${staticContextPath}/provider/images/cross.png"
								id="deleteBtn_${reports.reportId}" name="delete" onclick=""
								formInd='${formInd}'
								style="cursor: default; opacity: 0.2; filter: alpha(opacity =   20);"
								report="${reports.reportId}">
						</c:otherwise>
						</c:choose>
					</td>
					<td width="42%">
						<a id="link_${reports.reportId}" onmouseout="hideCriteria(this);"
							onmouseover="displayCriteria(this);" href="javascript:void(0);" style="cursor: default;">${reports.reportName}</a>
						<div id="div_${reports.reportId}" class="criteriaLayer"
							style="display: none; z-index: 999999999; width: 270px; height: auto; overflow: auto; border: solid 1px #ccc; background: #fff; position: absolute;">
							<div style="background-color: #00A0D2;color: #FFFFFF;text-align: left;font-weight: bold;height: 20px;font-size: 12px;">
							  <span style="padding:3px;"> Request Criteria  </span>
							</div>
							<div style="padding: 5px;">
								<c:if test="${not empty reports.buyerIds}">
									<b>Buyer IDs : </b>${reports.buyerIds}<br>
								</c:if>
								<c:if test="${empty reports.buyerIds}">
									<c:if test="${reports.allBuyers}">
										<b>Buyer IDs : </b>All buyers<br>
									</c:if>
								</c:if>
	
								<c:if test="${not empty reports.providerIds}">
									<b>Provider Firm IDs : </b>${reports.providerIds}<br>
								</c:if>
								<c:if test="${empty reports.providerIds}">
									<c:if test="${reports.allProviders}">
										<b>Provider Firm IDs : </b>All provider firms<br>
									</c:if>
								</c:if>
								<b>From Date : </b>${reports.fromDate}
								<br>
								<b>To Date : </b>${reports.toDate}
								<br>
							</div>
						</div>
					</td>
					<td width="11%">
						<span id="span_${reports.reportId}" style="border-bottom-color: -moz-use-text-color;border-bottom-style: dotted;border-bottom-width: 2px;" onmouseout="hideStatus(this);" onmouseover="displayStatus(this);"> 
							${reports.reportStatus} 
						</span>
						<div id="status_${reports.reportId}" class="criteriaLayer"
							style="display: none; z-index: 999999999; width: 220px; height: auto; overflow: auto; position: absolute;border: 2px solid #adaaaa; background:#fcfae6; border-radius:10px">
							<div id = "line_${reports.reportId}" style="padding: 5px;">
								
							</div>
						</div>
					</td>
					<c:choose>
					<c:when test="${reports.reportStatus != 'Completed'}">						
							<td width="15%">
								<input type="submit" id="download_${reports.reportId}"
									report="${reports.reportId}" class="btn_status button action"
									name="download" value="Download" onclick="return process(this);"
									formInd='${formInd}' disabled="disabled"
									style="cursor: default; opacity: 0.2; filter: alpha(opacity =   20);" />
							</td>
							<td width="15%">
							<c:choose>
								<c:when test="${formInd == 'pay_admn' }"> &nbsp; </c:when>
								<c:otherwise>
									<input type="submit" id="display_${reports.reportId}"
										report="${reports.reportId}" class="btn_status button action"
										name="display" value="Display" onclick="return process(this);"
										formInd='${formInd}' disabled="disabled"
										style="cursor: default; opacity: 0.2; filter: alpha(opacity =   20);" />
								</c:otherwise>
							</c:choose>
							</td>						
					</c:when>
					<c:otherwise>
						<c:choose>
						<c:when test="${reports.totalRecords > 0 }">
							<td width="15%">
								<input type="submit" id="download_${reports.reportId}"
									report="${reports.reportId}" class="btn_status button action"
									name="download" value="Download" onclick="return process(this);"
									formInd='${formInd}' />
							</td>
							<td width="15%">
							<c:choose>
								<c:when test="${formInd == 'pay_admn' }"> &nbsp; </c:when>
								<c:otherwise>
									<input type="submit" id="display_${reports.reportId}"
										report="${reports.reportId}" class="btn_status button action"
										name="display" value="Display" onclick="return process(this);"
										formInd='${formInd}' />
								</c:otherwise>
							</c:choose>
							</td>
						</c:when> 
						<c:otherwise>
							<td colspan="2" > No records available for submitted criteria.</td>
						</c:otherwise>
						</c:choose>
					</c:otherwise>
					</c:choose>
				</tr>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<tr>
				<td colspan="5">
					<label style="color: black;">
						No reports are available to display/download.
					</label>
				</td>
			</tr>
		</c:otherwise>
		</c:choose>
	</table>
</div>

