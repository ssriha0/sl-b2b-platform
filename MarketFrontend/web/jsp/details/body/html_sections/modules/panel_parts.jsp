<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="partsInd" value="<%=request.getAttribute("partsIndicator")%>" />


<c:set var="CLOSED_STATUS" value="<%= new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="CANCELLED_STATUS" value="<%= new Integer(OrderConstants.CANCELLED_STATUS)%>" />
<c:set var="VOIDED_STATUS" value="<%= new Integer(OrderConstants.VOIDED_STATUS)%>" />
<c:set var="DELETED_STATUS" value="<%= new Integer(OrderConstants.DELETED_STATUS)%>" />


	<html xmlns="http://www.w3.org/1999/xhtml">
<head>

     <script type="text/javascript">
       function expandParts(id,path)
       {var hidId="group"+id;var testGroup=document.getElementById(hidId).value;
       var divId="parts"+id;var bodyId="parts_menu_body"+id;
       if(testGroup=="menu_list")
       {
       jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
       }
       else{
       jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);}var ob=document.getElementById('partsImage'+id).src;
       if(ob.indexOf('arrowRight')!=-1){
       document.getElementById('partsImage'+id).src=path+"/images/widgets/arrowDown.gif";
       }
       if(ob.indexOf('arrowDown')!=-1)
       {
       document.getElementById('partsImage'+id).src=path+"/images/widgets/arrowRight.gif";
       }
       }function expandSubMenuParts(count,id,path){var divId="subNameParts"+count+id;var bodyId="subBodyParts"+count+id;jQuery("#"+divId+" p.submenu_head").css({backgroundImage:"url("+path+"/images/widgets/subtitleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);var ob=document.getElementById('subpartsImage'+count+id).src;if(ob.indexOf('arrowRight')!=-1){document.getElementById('subpartsImage'+count+id).src=path+"/images/widgets/arrowDown.gif";}if(ob.indexOf('arrowDown')!=-1){document.getElementById('subpartsImage'+count+id).src=path+"/images/widgets/arrowRight.gif";}}
      
       </script>
       </head>
       <body>
       <c:set var="divName" value="parts"/> 
       <c:set var="divName" value="${divName}${summaryDTO.id}"/>  
       <c:set var="group" value="group"/> <c:if test="${checkGroup==group}">
       <c:set var="groupVal" value="menu_list"/><c:set var="bodyClass" value="menu_body"/>
       <c:set var="headClass" value="menu_head"/></c:if><c:if test="${checkGroup!=group}">
       <c:set var="groupVal" value="menugroup_list"/><c:set var="bodyClass" value="menugroup_body"/>
       <c:set var="headClass" value="menugroup_head"/></c:if>
       <div id="${divName}" class="${groupVal }">
       <c:set var="bodyName" value="parts_menu_body"/> <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
         <c:set var="partsImage" value="partsImage"/>    <c:set var="partsImage" value="${partsImage}${summaryDTO.id}"/> 
        <p class="${headClass}" onclick="expandParts('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${partsImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Parts</p>  
          <div id="${bodyName}" class="${bodyClass}">  
           <c:if test="${summaryDTO.partsCount==0}">
          Buyer has not specified parts for this service order. Either parts are not required or provider will supply them.
		 </c:if>
	
		<c:if test="${summaryDTO.partsCount!=0}">
			
		Please note the parts information below. Detailed pick-up location
		information is included if pick-up is required.
 <form action="<s:url action='soDetailsPartsTrackingChange.action' />" name="editPartsShippingInfo${soCounter}" id="editPartsShippingInfo${soCounter}" enctype="multipart/form-data" method="POST">
 <input type="hidden" name="soId" id="soId" value="${summaryDTO.id}"/>
 <input type="hidden" name="resId" id="resId" value="${routedResourceId}"/>
 	<c:forEach var="part" items="${summaryDTO.partsList}" varStatus="partCounter">	
 	<c:set var="countParts" value="${countParts+1}"/>
	<c:set var="subNameParts" value="subNameParts"/>
	<c:set var="subBodyParts" value="subBodyParts"/>
	<c:set var="subNameParts" value="${subNameParts}${countParts}${summaryDTO.id}"/>
	<c:set var="subBodyParts" value="${subBodyParts}${countParts}${summaryDTO.id}"/>
	<div id="${subNameParts}" class="submenu_list">
	 <c:set var="subpartsImage" value="subpartsImage"/>
    <c:set var="subpartsImage" value="${subpartsImage}${countParts}${summaryDTO.id}"/>
  <p onClick="expandSubMenuParts('${countParts}','${summaryDTO.id}','${staticContextPath}');" class="submenu_head">&nbsp;<img id="${subpartsImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;${part.title}</p>
    <div id="${subBodyParts}" class="submenu_body">
		<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
			<tr>
				<td width="170">
					<strong>Manufacturer</strong>
				</td>
				<td width="170">
					${part.manufacturer}
				</td>
				<td width="170">
					<strong>Size</strong>
				</td>
				<td width="170">
					${part.size}
				</td>
			</tr>
			<tr>
				<td>
					<strong>Model Number</strong>
				</td>
				<td>
					${part.modelNumber}
				</td>
				<td>
					<strong>Weight</strong>
				</td>
				<td>
					${part.weight}
				</td>
			</tr>
			<tr>
				<td>
					<strong>Serial Number</strong>
				</td>
				<td>
					${part.serialNumber}
				</td>
				<td>
					<strong>Vendor Part Number</strong>
				</td>
				<td>
					${part.vendorPartNumber}
				</td>
			</tr>
			<tr>
				<td>
					<strong>Manufacturer OEM Part Number</strong>
				</td>
				<td>
					${part.manufacturerPartNumber}
				</td>
				<td>
					<strong>Part Type</strong>
				</td>
				<td>
					${part.partType}
				</td>
			</tr>
			<tr>
			<td>
				<strong>Order Number</strong>
				</td>
				<td>
					${part.orderNumber}
				</td>
				<td>
					<strong>Quantity</strong>
				</td>
				<td>
					${part.qty}
				</td>
			</tr>
			<tr>
				<td>
					<strong>Purchase Order Number</strong>
				</td>
				<td>
					${part.purchaseOrderNumber}
				</td>
				<td>
					<strong>Part Status</strong>
				</td>
				<td>
		          <c:forEach var="lookupVO" items="${partStatus}" >
             		<c:if test="${lookupVO.id == part.partStatusId }">
							 ${lookupVO.type}
	                                	
					</c:if>	
		           </c:forEach>
				</td>
			</tr>
		</table>
		<strong>Description</strong>
		<p>
			${part.description}
		</p>
		
		<strong>Additional Part Info</strong>
		<p>
			${part.additionalPartInfo}
		</p>
		
		<c:choose>
			<%-- Show the Shipping Information section to Buyer all the time and to Provider (only when an order is in Accept state and beyond states) --%>
			<c:when test="${roleType == BUYER_ROLEID || (roleType == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS)}"> <%-- Don't show the section for Posted Orders for Providers --%>		
				<p>
					<strong>Shipping Information</strong>
				</p>
				<hr class="noSpace" />
				<div id="partsShippingInfo${soCounter}[${partCounter.index}]" style="display:block">
				<table cellpadding="0" cellspacing="0">
		
					<tr>
						<td width="200">
							<strong>Shipping Carrier</strong>
						</td>
						<td>
							${part.shippingCarrier}
						</td>
					</tr>
					<tr>
						<td width="200">
							<strong>Shipping Tracking Number</strong>
						</td>
						<td>
							${part.shippingTrackingNumber}
						</td>
					</tr>
					<tr>
						<td width="200">
							<strong>Ship Date</strong>
						</td>
						<td>
							${part.shipDate}
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							&nbsp;
		
						</td>
					</tr>
		
		
					<tr>
						<td>
							<strong>Core Return Carrier</strong>
						</td>
						<td>
							${part.coreReturnCarrier}
						</td>
					</tr>
					<tr>
						<td>
							<strong>Core Return Tracking Number</strong>
						</td>
						<td>
							${part.coreReturnTrackingNumber}
						</td>
					</tr>
					<tr>
						<td width="200">
							<strong>Core Return Date</strong>
						</td>
						<td>
							${part.returnTrackDate}
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							&nbsp;
		
						</td>
					</tr>
				</table>
                </div>
               <div id="partsShippingEdit${soCounter}[${partCounter.index}]" style="display:none">
					<table>
					<tr>
						<td>
							<label><strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.carrier"/></strong></label>
						</td>
						<td>	
							<div style="width: 250px" align="left">
							<input type="hidden" name="partsList[${partCounter.index}].partId" id="partsList[${partCounter.index}].partId" value="${part.partId}"/>
								<tags:fieldError id="partsList[${partCounter.index}]shippingCarrierId">
								<select name="partsList[${partCounter.index}].shippingCarrierId" id="partsList[${partCounter.index}].shippingCarrierId" style="width: 200px;" onchange="checkSelectType('partsList[${partCounter.index}].shippingCarrierId','partsList[${partCounter.index}].otherShippingCarrier')" >
									<option value="">
										Select Shipping ${part.otherShippingCarrier}
									</option>
									<c:forEach var="lookupVO" items="${shippingCarrier}">
										<c:choose>
										<c:when test="${lookupVO.id == part.shippingCarrierId }">
											<option selected="selected" value="${lookupVO.id}">
												${lookupVO.descr}
											</option>
										</c:when>
										<c:otherwise>
											<option value="${lookupVO.id}">
												${lookupVO.descr}
											</option>
										</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
								</tags:fieldError>
							</div>
						</td>
					</tr>						
					<tr>
						<td>
							<label>
								<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.track.number"/></strong>
							</label>
							</td>
							<td>
							<s:textfield name="partsList[%{#attr['partCounter'].index}].shippingTrackingNumber" id="partsList[%{#attr['partCounter'].index}].shippingTrackingNumber" size="50" maxlength="50"
							 			value="%{#attr['part'].shippingTrackingNumber}" 
							 			theme="simple" 
							 			cssStyle="width: 150px;"
							  			cssClass="shadowBox grayText"  />						
						</td>
					  	<td>
						<label><strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.label.shipDate"/></strong></label></td>
						<td>
						<input type="hidden" name="shipDateOld[${partCounter.index}]" id="shipDateOld[${partCounter.index}]" value="${part.shipDateFormatted}"/>
						<input type="hidden" name="partsList[${partCounter.index}].shipDate" id="partsList${soCounter}[${partCounter.index}].shipDate"/>
									<input type="text" 
										class="shadowBox" id="shipDate[${partCounter.index}]"
										name="shipDate[${partCounter.index}]"
										value=""
										onfocus="showCalendarControl(this,'partsList${soCounter}[${partCounter.index}].shipDate');"
										required="true" 
										lang="en-us"/>
						</td>					
					</tr>
	                <tr>
	                <td>	
					<label>
						<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.carrier"/></strong>
					</label>
					</td>
					<td>
						<div style="width: 250px" align="left">
						<tags:fieldError id="partsList[${partCounter.index}]coreReturnCarrierId">
							<select name="partsList[${partCounter.index}].coreReturnCarrierId" id="partsList[${partCounter.index}].coreReturnCarrierId" style="width: 200px;"	onchange="checkSelectType('partsList[${partCounter.index}].coreReturnCarrierId','partsList[${partCounter.index}].coreReturnCarrier')">
							<%--select id="part[${partCounter.index}].returnCarrierId" style="width: 200px;"--%>
								<option value="">
									Select Shipping
								</option>
								<c:forEach var="lookupVO" items="${shippingCarrier}">
									
									<c:choose>
									<c:when
										test="${lookupVO.id == part.coreReturnCarrierId }">
										<option selected="selected" value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:when>
									<c:otherwise>
										<option value="${lookupVO.id}">
											${lookupVO.descr}
										</option>
									</c:otherwise>
									</c:choose>
								</c:forEach>
							</select>
							</tags:fieldError>
						</div>
					</td>
					</tr>
					<tr>					
					<td align="left">
							<label>
								<strong><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.parts.shipping.label.core.return.track.number"/></strong>
							</label></td>
							<td>
							<s:textfield name="partsList[%{#attr['partCounter'].index}].coreReturnTrackingNumber" id="partsList[%{#attr['partCounter'].index}].coreReturnTrackingNumber" size="50" maxlength="50"
							 			value="%{#attr['part'].coreReturnTrackingNumber}" 
							 			theme="simple" 
							 			cssStyle="width: 150px;"
							  			cssClass="shadowBox grayText"  />	
					</td>
				</tr>
			</table>
			</div>			
			</c:when><%-- WHEN for checking whether shipping section should be displayed --%>
		</c:choose>	
		<c:if test="${roleType == BUYER_ROLEID || (roleType == PROVIDER_ROLEID)}" >
			<p>
				<strong>Pick-up/Merchandise Location Information</strong>
			</p>
			<hr class="noSpace" />
			<c:choose>
				<c:when test="${part.contact != null}">
					<table cellpadding="0" cellspacing="0" class="noMargin">
						<tr>
							<td width="120">
								<p>
								    <c:if test="${(null == part.contact.streetAddress || '' == part.contact.streetAddress) && (null == part.contact.streetAddress2 || '' == part.contact.streetAddress2) && ( null == part.contact.cityStateZip || '' == part.contact.cityStateZip)}"> 
										Product At Job Site
									</c:if>			
									<c:if test="${part.contact.individualName!=null}">
									${part.contact.individualName}
									<br />
									</c:if>
									<c:if test="${part.contact.companyName!=null}">
									${part.contact.companyName}
									<br />									
									</c:if>
									<c:if test="${part.contact.streetAddress!=null}">
									${part.contact.streetAddress}
									<br />
									</c:if>							
									<c:if test="${part.contact.streetAddress2!=null}">
									${part.contact.streetAddress2}
									<br />
									</c:if>
					    			<c:if test="${part.contact.cityStateZip!=null}">
									${part.contact.cityStateZip}
									</c:if>								
								</p>
							</td>
							<td>
								<br />
								<br />
								<!--  <span class="mapThis" onmouseout="popUp(event,'mapThis')"
									onmouseover="popUp(event,'mapThis')"><img
										src="${staticContextPath}/images/icons/mapThis.gif"
										alt="Map This Location" class="inlineBtn" /> </span>-->
							</td>
						</tr>
					</table>
					<p>
						<c:if test="${not empty part.contact.phoneWork}">
							<strong>Work Phone</strong> ${part.contact.phoneWork}
						</c:if>						
					</p>
				</c:when>
				<%-- WHEN for checking if part.contact is NULL--%>
			</c:choose>
		</c:if>
</div>
</div>
</c:forEach>
</form>

	
		<c:if test="${(roleType == BUYER_ROLEID) && (summaryDTO.status != CANCELLED_STATUS && summaryDTO.status != VOIDED_STATUS && summaryDTO.status != CLOSED_STATUS && summaryDTO.status != DELETED_STATUS)}">		
		<div id="editPartsShipping${soCounter}" style="display:block">
			<input type="image" src="${staticContextPath}/images/common/spacer.gif"
										width="62" height="20"
										style="background-image: url(${staticContextPath}/images/btn/change.gif);"
										class="btn20Bevel inlineBtn" 
										onclick="editPartsShipping('${summaryDTO.partsCount}','${soCounter}')"/>
		</div>
		
		 <div id="savePartsShipping${soCounter}" style="display:none">
		<input type="image" src="${staticContextPath}/images/common/spacer.gif"
									width="70" height="20"
									style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
									class="btn20Bevel inlineBtn" 
									onclick="submitPartsShippingInfo('${soCounter}')"/>
								&nbsp;&nbsp;&nbsp;
								<input type="image" src="${staticContextPath}/images/common/spacer.gif"
									width="70" height="20"
									style="background-image: url(${staticContextPath}/images/btn/cancel.gif);"
									class="btn20Bevel inlineBtn" 
									onclick="cancelEditPartsShipping('${summaryDTO.partsCount}','${soCounter}')"/>
		
		</div>
	</c:if>

</c:if>
         <%--Common section which display invoice parts if exists --%>   
          <input type="hidden"  id="partExistInd" value="${partExistInd}"  >        
	    <c:choose>
			<c:when test="${showInvoicePartsEdit}">
			     <p onClick="expandInvoiceParts('invoicePartsEdit','invoicePartssummaryEdit','${staticContextPath}')" class="submenu_head" style="width:680px;">&nbsp;<img id="invoicePartsEdit" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
			     <div id="invoicePartssummaryEdit" style="margin-top:5px;margin-left:1px;width:99%;"> 



				         <jsp:include page="panel_parts_hsr_summary_edit.jsp" />
			     </div>
			    
			 </c:when>
			 <c:when test="${showInvoicePartsView}">
			      <p onClick="expandInvoiceParts('invoicePartsView','invoicePartssummaryView','${staticContextPath}')" style="width:680px;" class="submenu_head">&nbsp;<img id="invoicePartsView" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
			      <div id="invoicePartssummaryView" style="margin-top:5px;margin-left:1px;width:99%;"> 
			            <jsp:include page="panel_parts_hsr_summary_view.jsp" />
			      </div>
			      
			  </c:when>
			  <c:otherwise></c:otherwise>
	  </c:choose>
</div>
		
</div>  
  
</body>
</html>

