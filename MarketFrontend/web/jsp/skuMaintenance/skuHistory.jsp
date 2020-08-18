<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />	
<html>
<body>
<!-- div for displaying sku History -->	

	 <div class="modalHomepage" style="padding-bottom:20px;">
	    <div style="float:left;">SKU History</div>
	        <a href="javascript: void(0)" id="closeButton"
				class="btnBevel simplemodal-close" style="color: white; float: right;">
				<img src="${staticContextPath}/images/widgets/tabClose.png" alt="X">
			</a>
	    </div>
	 <div class="modalheaderoutline" style="padding-left: 0px; padding-right: 0px;">
	 <div class="rejectServiceOrderFrame" style="width: 600px; border: 0px; padding-right: 0px;">
	 <div class="rejectServiceOrderFrameBody" style="width: 600px; padding-left: 4.5px; padding-right: 0px;">
	
		<table width="100%" cellspacing="0" align="center">
		<tr>
		<td width="25%" style="text-align:center; border-bottom: 0px;background-color:#00A0D2;"  height="16px">
			<font color="white" style="font-weight: bold;" > SKU Name</font>
		</td>
		<td width="25%" style="text-align: center; border-bottom: 0px;background-color:#1569C7;" height="16px">
			<font color="white" style="font-weight: bold;">Modified By</font>
		</td>
		<td width="25%" style="text-align:center; border-bottom: 0px;background-color:#00A0D2;"  height="16px">
			<font color="white" style="font-weight: bold;">Action</font>
		</td>
		<td width="25%" style="text-align: center; border-bottom: 0px;background-color:#1569C7;" height="16px">
			<font color="white" style="font-weight: bold;">Modified On</font>
		</td>
		</tr>
		<c:if test="${not empty skuHistoryList}">
		<c:set var="count" value="0"></c:set>
		<c:forEach var="skuHistory" items="${skuHistoryList}">
		<tr>
		<td style="text-align:center; border-bottom: 1px solid #ccc; border-right: 1px solid #ccc;
		border-left: 1px solid #ccc;border-top: 1px solid #ccc; padding-bottom: 6px; padding-top: 6px;">
			${skuHistory.skuName}
		</td>
		<td BGCOLOR="#D8D8D8" style="text-align:center; border-bottom: 1px solid #ccc; border-right: 1px solid #ccc;
		border-left: 1px solid #ccc;
		border-top: 1px solid #ccc; padding-bottom: 6px; padding-top: 6px;">
			<c:if test="${skuHistory.roleId == 3}">
			Buyer
			</c:if>
			<c:if test="${skuHistory.roleId == 1}">
			SL Admin
			</c:if>			
			<br>
			(${skuHistory.modifiedBy})
		</td>
		<td style="text-align:center; border-bottom: 1px solid #ccc; border-right: 1px solid #ccc; 
		border-left: 1px solid #ccc;
		border-top: 1px solid #ccc;padding-bottom: 6px; padding-top: 6px;">
			${skuHistory.action}
			<c:if test="${not empty skuHistory.actionDetails}">
			<br><span id="${count}"  class="moreLink" style="padding-left:98px;cursor:pointer;color:#00BFFF;">More..</span>
			</c:if>
			<div id="${count}moreDetails" class="moreDetails"  style="-moz-border-radius: 10px 10px 10px 10px; word-wrap: break-word;
             text-align: left; background: none repeat scroll 0 0 #FCFAE6;border: 2px solid #ADAAAA;padding: 3px;display: none;
             z-index: 10 text-wrap: normal; width:300px;">
          <c:forEach var="list" items="${skuHistory.actionDetailsList}">
          <c:out value="${list}"></c:out><br>
          </c:forEach>
          	<br>
			</div>
		</td>
		<td BGCOLOR="#D8D8D8" style="text-align:center; border-bottom: 1px solid #ccc;border-right: 1px solid #ccc;
		border-left: 1px solid #ccc;
		border-top: 1px solid #ccc;padding-bottom: 6px; padding-top: 6px;">
		<fmt:formatDate value="${skuHistory.modifiedOn}" pattern="MM/dd/yyyy h:mm a"  />
		(CST)
		</td>
		</tr>
		<c:set var="count" value="${count+1}"></c:set>
		</c:forEach>
		</c:if>
		<c:if test="${empty skuHistoryList}">
        <tr>
		   <td><br><i><b>No History Available</b><i><td>
		</tr>
		</c:if>
		</table>
	
	 </div>
	 </div>
	 </body>
     </html>