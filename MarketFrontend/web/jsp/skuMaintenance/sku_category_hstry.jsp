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
	
	<div class="modalHomepage" style="padding-bottom:20px;">
	<div style="float:left;">SKU Category History</div>
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
		<td width="25%" style="text-align:center; border-bottom: 0px;background-color:#00A0D2;" height="16px">
			<font color="white" style="font-weight: bold;">SKU Category Name</font>
		</td>
		<td width="25%" style="text-align:center; border-bottom: 0px;background-color:#1569C7;" height="16px">
			<font color="white" style="font-weight: bold;">Modified By</font>
		</td>
		<td width="25%" style="text-align:center; border-bottom: 0px;background-color:#00A0D2;" height="16px">
			<font color="white" style="font-weight: bold;">Action</font>
		</td>
		<td width="25%"  style="text-align:center; border-bottom: 0px;background-color:#1569C7;" height="16px">
			<font color="white" style="font-weight: bold;"> Modified On </font>
		</td>
		</tr>
		<c:if test="${not empty skuCategoryHistoyList}">
		<c:forEach var="skuCategoryHistoy" items="${skuCategoryHistoyList}">
		<tr>
		<td style="text-align:center; border-bottom: 1px solid #ccc; border-right: 1px solid #ccc;border-left: 1px solid #ccc;
		border-top: 1px solid #ccc; padding-bottom: 6px; padding-top: 6px;">
			${skuCategoryHistoy.skuCategoryName}
		</td>
		<td BGCOLOR="#D8D8D8" style="text-align:center; border-bottom: 1px solid #ccc; border-right: 1px solid #ccc; 
		border-left: 1px solid #ccc;
		border-top: 1px solid #ccc;padding-bottom: 6px; padding-top: 6px;">
			Buyer
			<br>
			(${skuCategoryHistoy.modifiedBy})
		</td>
		<td style="text-align:center; border-bottom: 1px solid #ccc; border-right: 1px solid #ccc; padding-bottom: 6px; 
		border-left: 1px solid #ccc;
		border-top: 1px solid #ccc;padding-top: 6px;">
			${skuCategoryHistoy.action} 	
		</td>
		<td BGCOLOR="#D8D8D8" style="text-align:center; border-right: 1px solid #ccc; border-bottom: 1px solid #ccc;
		border-left: 1px solid #ccc;
		border-top: 1px solid #ccc;padding-bottom: 6px; padding-top: 6px;">
		<fmt:formatDate value="${skuCategoryHistoy.modifiedOn}" pattern="MM/dd/yyyy h:mm a"  />
		(CST)
		</td>
		</tr>
		</c:forEach>
		</c:if>
		<c:if test="${empty skuCategoryHistoyList}">
        <tr>
		   <td><br><i><b>No History Available</b></i></td>
		</tr>
		</c:if>
		</table>
	   </div>
	   </div>
	   </div>

</body>
</html>
	