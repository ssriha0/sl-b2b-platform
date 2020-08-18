<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<html>
	<head>
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3" />
		<meta http-equiv="description" content="this is my page" />
		<meta http-equiv="content-type" content="text/html; charset=ISO-8859-1" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/advanced_grid.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
	</head>
<body>
	<c:choose>
		<c:when test="${isFiltered != null && isFiltered == true}">
			<c:set var="newdto" scope="request" value="${filteredList}" />
		</c:when>

		<c:otherwise>
			<c:set var="newdto" scope="request" value="${dtoList}" />
		</c:otherwise>
	</c:choose>
	<table class="grid-table" cellpadding="0" cellspacing="0">
		<c:forEach var="dto" items="${newdto}" varStatus="dtoIndex">
			<tr>
				<!-- Right Arrow -->
				<td class="sl_grid_col_1">
					<img id="img_${dtoIndex.count}" name="img_${dtoIndex.count}"
						src="${staticContextPath}/images/grid/right-arrow.gif" alt="Expand"
						onclick="collapseExpand(${dtoIndex.count -1})" />
				</td>
				<!-- Status -->
				<td class="sl_grid_col_2">${dto.statusString}</td>
				<!-- SO # -->
				<td class="sl_grid_col_3">${dto.id}</td>
				<!-- Title with href to SO detail -->
				<td class="sl_grid_col_4"><a href="">${dto.title}</a></td>
				<!-- Maximum Price -->
				<td class="sl_grid_col_5 align-right"></td>
				<!-- Time to Appointment -->
				<td class="sl_grid_col_6 offset"><a href=""></a></td>
				<!-- Age of order -->
				<td class="sl_grid_col_7"></td>
			</tr>
			<tr>
				<td colspan="7">
					<div>
						<table class="grid-table" cellpadding="0" cellspacing="0">
							<tr>
								<td class="sl_grid_col_1">&nbsp;</td>
								<!-- Substatus -->
								<td colspan="2" class="sl_grid_col_2_sub">
									<strong>Sub-status:</strong>
									<br/>
									Team Member Not Qualified to Complete Work (Provider Side) 
								</td>
								<!-- SO Description/Details -->
								<td class="sl_grid_col_4">
									Phasellus egestas cursus leo. Fusce accumsan feugiat lectus.
									Quisque id quam ac nisi blandit iaculis. Sed congue commodo elit.
									Sed dignissim odio quis mauris. Duis...
								</td>
								<td colspan="3" class="align-center">
									<a href=""> <img class="btn20Bevel"
										src="${staticContextPath}/images/common/spacer.gif" alt="View Order" width="164"
										height="20"
										style="background-image: url( ${staticContextPath}/images/buttons/btn_view_complete_service_order.gif);" />
									</a>
								</td>
							</tr>
							<tr class="selected">
								<td class="sl_grid_col_1 last">
									&nbsp;
								</td>
								<td class="last" colspan="6">
									<strong>Order Quick Links:</strong>&nbsp;
									<a href="">Create Continuation Order</a>|
									<a href="">Change Scope</a>|
									<a href="">Reschedule</a>
								</td>
							</tr>
						</table>			
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js">	</script>

<script language="JavaScript" type="text/javascript">
	var collapsableDivsArray = new Array();
	var lastOpenDivIndex = -1;
	for(i=0;i<${fn:length(dtoList)};i++){
		collapsableDivsArray[i] = new animatedcollapse('div_' +(i+1), 250, false);
	}
	function collapseExpand(divIndex){
	if(lastOpenDivIndex > -1){
		collapsableDivsArray[lastOpenDivIndex].slideup();
		document.getElementById("img_" + (lastOpenDivIndex+1)).src = "${staticContextPath}/images/artwork/grid/right-arrow.gif";	
	}
	collapsableDivsArray[divIndex].slideit();
	document.getElementById("img_" + (divIndex + 1)).src = "${staticContextPath}/images/artwork/grid/down-arrow.gif";	
	if(divIndex == lastOpenDivIndex){
		document.getElementById("img_" + (divIndex + 1)).src = "${staticContextPath}/images/artwork/grid/right-arrow.gif";
		lastOpenDivIndex = -1;
		return;
	}
lastOpenDivIndex = divIndex;
}
</script>
</html>
