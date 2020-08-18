<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
		<%@ taglib uri="/struts-tags" prefix="s"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
		<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
		<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

		<c:set var="contextPath" scope="request"
			value="<%=request.getContextPath()%>" />
		<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />			

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.LayoutContainer");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.form.Button");
			dojo.require("dijit.TitlePane");	
			dojo.require("dojo.parser");
			
			function doAction(){
				document.getElementById('handler').submit();
			
			}
		</script>
		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/hideShow.js"
			djConfig="isDebug: false, parseOnLoad: true">
		</script>

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/grid.css" />
	</head>
	<body>
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="SOM - Data Grid"/>
		</jsp:include>	
	
		<form action="updateDataGrid.action" method="post" id="handler">
					
		</form>
		
		<table width="100%" border=0>
			<tr>
				<td valign="top">
					<table>
    <c:choose>
        <c:when test="${isFiltered != null && isFiltered == true}">
		<c:set var="newdto" scope="request" value="${filteredList}" />
        </c:when>
        
        <c:otherwise>
			<c:set var="newdto" scope="request" value="${dtoList}" />
        </c:otherwise>
    </c:choose>
						<c:forEach var="dto" items="${newdto}" varStatus="dtoIndex">
							<tr onclick="hideActionWidgets('_oem', ${dtoIndex.count})">
								<td class="column1" valign="top" width="5%">
									<img id="img_${dtoIndex.count}" name="img_${dtoIndex.count}"
										src="${staticContextPath}/images/artwork/grid/right-arrow.gif"
										alt="Expand" onclick="collapseExpand(${dtoIndex.count -1})" />
								</td>
								<td width="95%">
									<table width="100%" border=0>
										<tr height="25px">
											<td width="100%">
												<table width="100%" border=0>
													<tr class="selected">
														<td width="15%">
															${dto.statusString}
														</td>
														<td width="20%">
															${dto.id}
														</td>
														<td width="35%">
															<a href="#">${dto.title}</a>
														</td>
														<td width="10%">
															${dto.serviceOrderDate.month}/${dto.serviceOrderDate.date}/0${dto.serviceOrderDate.year
															- 100}
														</td>
														<td width="20%">
															<a href="#"> ${dto.city}, ${dto.state} ${dto.zip} </a>
														</td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<div id="div_${dtoIndex.count}" name="div_${dtoIndex.count}">
													<table border=0 width="100%">
														<tr>
															<td width="15%">
															</td>
															<td width="20%">
															</td>
															<td width="35%">
															</td>
															<td width="10%">
															</td>
															<td width="20%">
															</td>
			
														</tr>
														<tr class="selected">
															<td colspan="2">
																<c:if test="${dto.subStatusString != null}">
																	<strong>Sub-status:</strong>
																	<br />
																		${dto.subStatusString} 
																</c:if>
			
															</td>
															<td>
																Phasellus egestas cursus leo. Fusce accumsan feugiat
																lectus. Quisque id quam ac nisi blandit iaculis. Sed congue
																commodo elit. Sed dignissim odio quis mauris. Duis...
															</td>
															<td colspan="2">
																<a href="#"><img
																		src="${staticContextPath}/images/artwork/grid/btn_view_complete_service_order.gif"
																		alt="View Order" />
															</td>
														</tr>
														<tr>
															<td colspan=2>
																&nbsp;
															</td>
															<td colspan="4">
																<strong>Order Quick Links:<br />
																</strong>
																<a href="#">Create Continuation Order</a> |
																<a href="#">Change Scope</a> |
																<a href="#">Reschedule</a>
															</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</c:forEach>
					</table>
				</td>
				<td valign="top">
					<c:if test="${not empty dtoList}">
					<div dojoType="dijit.layout.ContentPane" layoutAlign="right"
						style="border: 1px solid black; padding-top: 20px; width:220px">
						
							<c:forEach var="dto" items="${dtoList}" varStatus="dtoIndex">
								<div id="${dtoIndex.count}_oem" style="display:none">
									<b>Service Order #:</b>${dto.id}<br/>
									<b>Title:</b>${dto.title}<br/>
									<b>Status:</b>${dto.statusString}<br/>
									<b>Conditionally Accepted:</b> ${dto.providersConditionalAccept}<br/>
									<b>Rejected:</b> ${dto.providersDeclined}<br/>
									<b>Maximum Price:</b>$${dto.spendLimitTotal}<br/>
									<b>Buyer:</b> ${dto.buyerName}<br/>
									<b>Provider:</b> N/A<br/>
									<b>End Customer:</b> Barbara Haberman<br/>
									<b>Location:</b>${dto.city}, ${dto.state} ${dto.zip}
									</div>
							</c:forEach>
					</div>
					</c:if>
				</td>
			</tr>
		</table>




		<script type="text/javascript"
			src="${staticContextPath}/javascript/animatedcollapse.js">				
		</script>

	<script language="JavaScript" type="text/javascript">
			 var collapsableDivsArray = new Array();
			 var lastOpenDivIndex = -1;
			 for(i=0;i<${fn:length(dtoList)};i++){
				collapsableDivsArray[i] = new animatedcollapse('div_' +(i+1), 250, false);
			 }
			function doSubmit(theFilter, theSubFilter)
			{
				if(theFilter != null)
				{
					document.forms[0].filterBy.value = theFilter;
					document.forms[0].subFilterBy.value = theSubFilter;
					document.forms[0].submit();
				}	
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
	</body>
</html>
