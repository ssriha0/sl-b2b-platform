<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theTab" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<form>
<input type="hidden" id="toLoc" name="toLoc">
<input type="hidden" id= "fromLoc" name="fromLoc">
<input type="hidden" id= "zip" name="zip">
<input type="hidden" id= "orderStatus" name="orderStatus">


<div dojoType="dijit.TitlePane" title="Order Express Menu"
	id="widget_oem_${theTab}"
	style="padding-top: 1px; width: 249px;" open="false">

	<span class="dijitInfoNodeInner"> <a href="#"> </a> </span>

	<table border="0" cellpadding="0" cellspacing="0" class="" width="100%" style="table-layout: fixed;">
		<tr>
			<td width="45%">			
			  	<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.soid" />  
			</td>
			<td width="55%">
				<div id="soid${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.title" />
			</td>
			<td style="word-wrap: break-word;">
				<div id="title${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.status" />
			</td>
			<td>
				<div id="status${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>	
		<div id="cancellationDiv${theTab}" style="display: block;">
		
			<b>Cancel Amount:</b>
				<!--<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.status" />-->
			
			</div>
			</td>
			<td>
			<div id="amountCancelled${theTab}"></div>
		</td>
			
			</tr>			
				
		
		<tr>
			<td>
				<div id="spendLimitDiv${theTab}" style="display: block;">
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.posted.price" />
				</div>
			</td>
			<td>
				<div id="spendLimit${theTab}"></div>
			</td>
		</tr>	
		<c:if test="${viewOrderPricing==true && roleType==1  ||  roleType==3}">	
		<tr>
			<td>
				<div id="totalFinalPriceDiv${theTab}" style="display: block;">
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.total.final.price" />
				</div>
			</td>
			<td>
				<div id="totalFinalPrice${theTab}" ></div>									
			</td> 
		</tr>
		</c:if>
		<tr>
			<td>
			<c:if test="${viewOrderPricing==true && roleType==1  ||  roleType==3}">
				<div id="priceRangeDiv${theTab}" style="display: block;">
				Counter Offer Range:
				</div>
				</c:if>
			</td>
			<td>
				<div id="priceRange${theTab}" ></div>									
			</td> 
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.buyer" />
			</td>
			<td>
				<div id="buyer${theTab}"></div>
			</td>
		</tr>
		<c:if test="${roleType==3}">
		<tr>
			<td>
			    <b><div id="providerFirmLabel${theTab}"></div></b>
			</td>
			<td><div id="providerFirm${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<div id="firmBusinessNumber${theTab}"></div>
			</td>
		</tr>
		</c:if>
		
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.provider" />
			</td>
			<td style="word-wrap: break-word;">
				<div id="provider${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<div id="providerPrimaryPhoneNumberWidget${theTab}"></div>
			</td>
		</tr>
		<tr>
		<tr>
			<td>
			</td>
			<td>
				<div id="providerAltPhoneNumberWidget${theTab}"></div>
			</td>
		</tr>
		<tr>				
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.end.customer" />
			</td>
			<td>
				<div id="endCustomer${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>
			</td>
			<td>
				<div id="endCustomerPrimaryPhoneNumberWidget${theTab}"></div>
			</td>
		</tr>
		<tr>
			<td>
				<fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.location" />
			</td>
			<td style="word-wrap: break-word;">
				<div id="location${theTab}"></div>
				<c:if test="${role == PROVIDER_ROLEID}">
					<div id="distanceInMiles${theTab}"></div> 
				</c:if>
			</td>
		</tr>
		 <c:if test="${role == PROVIDER_ROLEID && isPrimaryInd == false}">
			<tr>		
				<td colspan='2'><a href="#" onClick="parent.newco.jsutils.openMap();">Google Direction</a>
				<%--<span id="drivingDistanceWidget${theTab}"></span>--%></td>
			</tr>
		</c:if> 
	</table>
	 <br />
	
	<c:if test="${wfFlag==1}"> 
	  
	     
       <table border="1" cellpadding="0" cellspacing="0"  width=210 class="">  
	   <tr>
	       <td>
	       <table border="0" cellpadding="0" cellspacing="0"  class="">
	            <tr>
			       <td>
			          <img id="img_redinfo_claim" name="img_redinfo_claim"
								     src="${staticContextPath}/images/PowerBuyer/red_info.gif"  />
					</td>
					 
					<td>			     
                    <fmt:message bundle="${serviceliveCopyBundle}"	key="widget.label.claimedby" /> 
				    
				   <div id="claimedByResource${theTab}"></div>
			       </td>  
		       </tr>
	        </table>
	        </td>
	     </tr>
	      
	</table>  
	   
	 </c:if>
</div>

</form>
