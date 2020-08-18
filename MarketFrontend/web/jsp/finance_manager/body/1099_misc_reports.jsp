<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="numberOfRecords" value="${fn:length(reportContentVO.commonBody)}"/> 
<c:set var="numberOfColumns" value="${fn:length(reportContentVO.headerList)}"/> 
<c:set var="numberOfFooters" value="${fn:length(reportContentVO.footerList)}" /> 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
	.reportBody {
		border-left: 0 none;
		border-right: 0 none;
		border-top: 0 none;
		font-size: 10px;
		padding: 10px;
	    font-family: Verdana;
	}
	
	.reportHeader {
		font-size: 10px;
		font-weight: bold;
		background: #666;
		color: #FFF;
		padding: 5px;
		text-align: center;
		border-right: 2px solid #FFFFFF;
		font-family: Verdana;
	}
	.reportFooter {
	    border-left: 0 none;
		border-right: 0 none;
		border-top: 0 none;
		border-bottom: 0 none;
		font-size: 10px;
		padding: 10px;
		font-family: Verdana;
	}
	#newnotice {
	    background: none repeat scroll 0 0 #FBE3E4;
	    border-bottom: 2px solid #FBC2C4;
	    color: #8A1F11;
	    margin-bottom: 1em;
	    padding: 10px 10px 10px 30px;
	}
</style>
<title>${reportContentVO.pageTitle}</title>
	</head>

	<body class="tundra acquity">
	<div id="page_margins">
		<div id="page">
		<c:choose>
		<c:when test="${not empty reportErrors}">
		<div class="grayModuleContent mainWellContent">
		<c:forEach varStatus="err" var="reportErr" items="${reportErrors}" >
			<div style="width: 500px;background-color: #FBE3E4" id="newnotice">
				<c:out value="${reportErr.msg}"></c:out>
			</div>
			
		</c:forEach>
		</div>
		</c:when>
		<c:otherwise>
			<table id="reports" class="paymentReports" border="1px"
				align="center" cellspacing="0" width="100%">
				<thead>
					<tr class="reportHeader">
						<s:iterator value="reportContentVO.headerList" status="status">
							<td class="reportHeader" background="../images/tabs/tab_on.gif">
								<s:property />
							</td>
						</s:iterator>
					</tr>
				</thead>
		<c:choose>
		<c:when test="${numberOfRecords >0}">	
				<tbody>		
				 	<s:iterator value="reportContentVO.commonBody" status="status">
						<tr>
							<s:iterator>
								<td class="reportBody">
									<s:property />
								</td>
							</s:iterator>
						</tr>
					</s:iterator> 
				</tbody>
				<tfoot>
					<tr>
					   <td colspan="${numberOfColumns - numberOfFooters }" class="reportFooter">
					   ${reportContentVO.infoMessage} 
					   </td>
						<s:iterator value="reportContentVO.footerList" status="status">
							<td  class="reportFooter">
								<s:property />
							</td>
						</s:iterator>
					</tr>
				</tfoot>
		</c:when>
		<c:otherwise>
				<tbody>	
						<tr>
						<td class="reportBody" colspan="${numberOfColumns}">
						<div style="height: 20px;">
							<label>
								No records available.
							</label>
						</div>									
						</td>
						</tr>
				</tbody>
		</c:otherwise>
		</c:choose>	
			</table>
			 <jsp:include page="/jsp/paging/paging_support_reports.jsp" />
		</c:otherwise>
		</c:choose>
		</div>
		</div>
	</body>
</html>
