<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="VOIDED_STATUS" value="<%= new Integer(OrderConstants.VOIDED_STATUS)%>" />
<c:set var="EXPIRED_STATUS" value="<%= new Integer(OrderConstants.EXPIRED_STATUS)%>" />
<c:set var="CLOSED_STATUS" value="<%= new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="ACCEPTED_STATUS" value="<%= new Integer(OrderConstants.ACCEPTED_STATUS)%>" />
<c:set var="ACTIVE_STATUS" value="<%= new Integer(OrderConstants.ACTIVE_STATUS)%>" />
<c:set var="COMPLETED_STATUS" value="<%= new Integer(OrderConstants.COMPLETED_STATUS)%>" />
<c:set var="PROBLEM_STATUS" value="<%= new Integer(OrderConstants.PROBLEM_STATUS)%>" />
<c:set var="PENDING_CANCEL_STATUS" value="<%= new Integer(OrderConstants.PENDING_CANCEL_STATUS)%>" />

<!-- NEW MODULE/ WIDGET-->
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<script type="text/javascript">
function expandContact(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="contact"+id;
var bodyId="contact_menu_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('contactImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('contactImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('contactImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}


</script>

</head>
<body>
<c:set var="divName" value="contact"/>
 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
  <c:set var="group" value="group"/>
 <c:if test="${checkGroup==group}">
<c:set var="groupVal" value="menu_list"/>
<c:set var="bodyClass" value="menu_body"/>
<c:set var="headClass" value="menu_head"/>
</c:if>
<c:if test="${checkGroup!=group}">
<c:set var="groupVal" value="menugroup_list"/>
<c:set var="bodyClass" value="menugroup_body"/>
<c:set var="headClass" value="menugroup_head"/>
</c:if>
 <c:set var="group" value="${group}${summaryDTO.id}"/>
<input type="hidden" id="${group}" value="${groupVal}"/>
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="contact_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
  <c:set var="contactImage" value="contactImage"/>
    <c:set var="contactImage" value="${contactImage}${summaryDTO.id}"/>
  <p class="${headClass }" onclick="expandContact('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${contactImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Contact Information</p>
    <div id="${bodyName}" class="${bodyClass }">
    
      <c:choose>
	<%-- Don't show the below contact information when order status is Posted or Closed and when user role is Provider. --%>
		<c:when test="${roleType == PROVIDER_ROLEID && (summaryDTO.status == ROUTED_STATUS || summaryDTO.status == CLOSED_STATUS) && (summaryDTO.shareContactInd == null || summaryDTO.shareContactInd == false)}">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<p>
							<strong>Buyer Support Contact</strong>
							<br />
						</p>
						<table cellpadding="0" cellspacing="0"
							class="adjustedTableRowPadding">
							<tr>
								<td width="100">
									<strong>Buyer ID#</strong>
								</td>
								<td width="200">
									${summaryDTO.buyerSupportContact.individualID}
								</td>
							</tr>
							<tr>
								<td>
									<strong>Company ID#</strong>
								</td>
								<td>
									${summaryDTO.buyerContact.companyID}
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</c:when>
	<c:otherwise>
		<p>
			All contact information is outlined below. The service location
			contact information includes the address for the job site. The primary
			contacts for the provider will be the service location contact and the
			buyer unless alternate contacts have been provided.
		</p>
			
		<table cellpadding="0" cellspacing="0"> <!-- Contact Table -->
			<tr>
				<td width="50%" class="contactTableCell" style="padding-left: 5px">  <!-- Service Location Contact Information Cell -->
					<p style="padding-top: 0px;">
						<strong>Service Location Information<c:if test="${showContactInfoShared}"> <span class="locationAsterisks">***</span></c:if></strong>
						<br />
						<c:if test="${summaryDTO.locationContact != null}">
							<c:if test="${summaryDTO.locationContact.type!=null}">
								${summaryDTO.locationContact.type}
								<br />
							</c:if>							
							<c:if test="${summaryDTO.locationContact.businessName!=null}">
								${summaryDTO.locationContact.businessName}
								<br />
							</c:if>
							${summaryDTO.locationContact.individualName}
							<br />
							${summaryDTO.locationContact.streetAddress}
							<br />
							${summaryDTO.locationContact.streetAddress2}
							<br />
							${summaryDTO.locationContact.cityStateZip}
							</c:if>
					</p>
					<table cellpadding="0" cellspacing="0"
						class="adjustedTableRowPadding">  <!-- Service Location Contact Information Phone -->
						<c:choose>
							<c:when test="${summaryDTO.locationContact != null}">
								<c:if test="${not empty summaryDTO.locationContact.phoneWork}">
									<tr>
										<td width="100">
											<strong>Work Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.phoneWork}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.phoneHome}">
									<tr>
										<td width="100">
											<strong>Home Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.phoneHome}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.phoneMobile}">
									<tr>
										<td width="100">
											<strong>Mobile Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.phoneMobile}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.pager}">
									<tr>
										<td width="100">
											<strong>Pager</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.pager}
										</td>
									</tr>
								</c:if>						
								<c:if test="${not empty summaryDTO.locationContact.other}">
									<tr>
										<td width="100">
											<strong>Other</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.other}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.fax}">
									<tr>
										<td width="100">
											<strong>Fax</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.fax}
										</td>
									</tr>
								</c:if>																		
								<c:if test="${not empty summaryDTO.locationContact.email}">
									<tr>
										<td width="100">
											<strong>Email</strong>
										</td>
										<td width="200">
											${summaryDTO.locationContact.email}
										</td>
									</tr>
								</c:if>
							</c:when>
						</c:choose>																		
					</table>  <!-- Service Location Contact Information Phone -->
				</td>  <!-- Service Location Contact Information Cell -->
				<td>&nbsp;&nbsp;&nbsp;</td>
				<!-- column 2 -->
				<td width="50%" class="contactTableCell" style="padding-left: 5px">  <!-- Service Location Alternate Contact Cell -->
	
					<p style="padding-top: 0px;">
						<strong>Service Location Alternate Contact <c:if test="${showContactInfoShared}"> <span class="locationAsterisks">***</span></c:if></strong>
						<br />
						<c:if test="${summaryDTO.locationAlternateContact != null}">
							<c:if test="${summaryDTO.locationAlternateContact.individualName!=null}">
						<c:choose>
							<c:when test="${summaryDTO.locationAlternateContact != null}">
								${summaryDTO.locationAlternateContact.individualName}<br/><br/><br/><br/>
							</c:when>
							<c:otherwise>
								[Information Not Selected by User]<br/><br/><br/><br/>
							</c:otherwise>
						</c:choose>		
						</c:if>
						</c:if>				
					</p>
					
					<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
						<c:choose>
							<c:when test="${summaryDTO.locationAlternateContact != null}">
								<c:if test="${not empty summaryDTO.locationAlternateContact.phoneWork}">
									<tr>
										<td width="100">
											<strong>Work Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.locationAlternateContact.phoneWork}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationAlternateContact.phoneHome}">
									<tr>
										<td width="100">
											<strong>Home Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.locationAlternateContact.phoneHome}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationAlternateContact.phoneMobile}">
									<tr>
										<td width="100">
											<strong>Mobile Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.locationAlternateContact.phoneMobile}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationAlternateContact.pager}">
									<tr>
										<td width="100">
											<strong>Pager</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerContact.pager}
										</td>
									</tr>
								</c:if>						
								<c:if test="${not empty summaryDTO.locationAlternateContact.other}">
									<tr>
										<td width="100">
											<strong>Other</strong>
										</td>
										<td width="200">
											${summaryDTO.locationAlternateContact.other}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.locationAlternateContact.fax}">
									<tr>
										<td width="100">
											<strong>Fax</strong>
										</td>
										<td width="200">
											${summaryDTO.locationAlternateContact.fax}
										</td>
									</tr>
								</c:if>																		
								<c:if test="${not empty summaryDTO.locationAlternateContact.email}">
									<tr>
										<td width="100">
											<strong>Email</strong>
										</td>
										<td width="200">
											${summaryDTO.locationAlternateContact.email}
										</td>
									</tr>
								</c:if>
							</c:when>
						</c:choose>																		
					</table>
				</td>  <!-- Service Location Alternate Contact Cell -->
			</tr>
			
			<tr>
				<td colspan="3">
     				<p style="padding-bottom: 20px"><strong><c:if test="${showContactInfoShared}"><span class="locationAsterisks">***</span> You've chosen to share this contact information with the providers you are sending this service order to.</c:if></strong></p>
     			</td>
     		</tr>
      		
			<tr>
				<td class="contactTableCell" style="padding-left: 5px">	<!-- Buyer Contact Information Cell -->
					<p style="padding-top: 0px;">
						<strong>Buyer Contact Information</strong>
						<br />
						${summaryDTO.buyerSupportContact.individualName}
						(User Id# ${summaryDTO.buyerSupportContact.individualID})
						<br />
						${summaryDTO.buyerContact.companyName}
						(ID# ${summaryDTO.buyerContact.companyID})
						<br />
						<c:if test="${not empty summaryDTO.buyerSupportContact.streetAddress}">
						${summaryDTO.buyerSupportContact.streetAddress}
						<br />
						</c:if>
						<c:if test="${not empty summaryDTO.buyerSupportContact.streetAddress}">
						${summaryDTO.buyerSupportContact.streetAddress2}
						<br/>
						</c:if>
						${summaryDTO.buyerSupportContact.cityStateZip}
					</p>
					<table cellpadding="0" cellspacing="0"
						class="adjustedTableRowPadding">  <!-- Buyer Contact Information Phone -->
						<c:choose>
							<c:when test="${summaryDTO.buyerSupportContact != null}">
								<c:if test="${not empty summaryDTO.buyerSupportContact.phoneWork}">
									<tr>
										<td width="100">
											<strong>Work Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.phoneWork}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.buyerSupportContact.phoneHome}">
									<tr>
										<td width="100">
											<strong>Home Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.phoneHome}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.buyerSupportContact.phoneMobile}">
									<tr>
										<td width="100">
											<strong>Mobile Phone</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.phoneMobile}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.buyerSupportContact.pager}">
									<tr>
										<td width="100">
											<strong>Pager</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.pager}
										</td>
									</tr>
								</c:if>						
								<c:if test="${not empty summaryDTO.buyerSupportContact.other}">
									<tr>
										<td width="100">
											<strong>Other</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.other}
										</td>
									</tr>
								</c:if>
								<c:if test="${not empty summaryDTO.buyerSupportContact.fax}">
									<tr>
										<td width="100">
											<strong>Fax</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.fax}
										</td>
									</tr>
								</c:if>																		
								<c:if test="${not empty summaryDTO.buyerSupportContact.email}">
									<tr>
										<td width="100">
											<strong>Email</strong>
										</td>
										<td width="200">
											${summaryDTO.buyerSupportContact.email}
										</td>
									</tr>
								</c:if>
							</c:when>
						</c:choose>																		
					</table>  <!-- Buyer Contact Information Phone -->
				</td>	<!-- Buyer Contact Information Cell -->
			</tr>
	
				  <c:choose>

					  <c:when test="${summaryDTO.providerContact != null && not empty summaryDTO.providerContact.individualID}">
					  <tr><td><p></p></td></tr>
					  <tr>
					  	<td class="contactTableCell" style="padding-left: 5px">  <!-- Provider Contact Information Cell -->
						<p style="padding-top: 0px;">
							<strong>Service Provider</strong>
							
							<br />
							<c:choose>
							<c:when test="${summaryDTO.status > ROUTED_STATUS}">
								<c:choose>
								<c:when test="${assignmentType=='PROVIDER'}">
									<c:if test="${not empty summaryDTO.providerContact.individualName}">							
									${fn:substringAfter(fn:substringBefore(summaryDTO.providerContact.individualName, "("),",")} 
									${fn:substringBefore(fn:substringBefore(summaryDTO.providerContact.individualName, "("),",")}
									(User Id# ${summaryDTO.providerContact.individualID})
									<br />
									</c:if>
								</c:when>
								<c:otherwise>
								<div>
									<div style="float: left;">Unassigned</div>
									<div style="float: left;padding-left: 3px;">
									 	<img src="${staticContextPath}/images/icons/icon-unassigned.png" title="Assign a provider prior to service start" style="height: 50%; width: 50%;"></img>
									</div>
								</div>
								</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
									<c:if test="${not empty summaryDTO.providerContact.individualName}">							
									${fn:substringAfter(fn:substringBefore(summaryDTO.providerContact.individualName, "("),",")} 
									${fn:substringBefore(fn:substringBefore(summaryDTO.providerContact.individualName, "("),",")}
									(User Id# ${summaryDTO.providerContact.individualID})
									<br />
									</c:if>
							</c:otherwise>
							</c:choose>
							
							<%-- Show below details for Provider only when order is in one of the status (Accepted/Active/Completed/Problem/Pending Cancel) --%>
							<c:if test="${assignmentType=='PROVIDER'&&(summaryDTO.status == ACCEPTED_STATUS || summaryDTO.status == ACTIVE_STATUS || summaryDTO.status == COMPLETED_STATUS
											|| summaryDTO.status == PROBLEM_STATUS || summaryDTO.status == PENDING_CANCEL_STATUS)}">
								${summaryDTO.providerContact.companyName}
								(ID# ${summaryDTO.providerContact.companyID})
								<br />
								<c:if test="${not empty summaryDTO.providerContact.streetAddress}">
									${summaryDTO.providerContact.streetAddress}
									<br/>
								</c:if>
								<c:if test="${not empty summaryDTO.providerContact.streetAddress2}">
									${summaryDTO.providerContact.streetAddress2}
									<br />
								</c:if>
								<c:if test="${not empty summaryDTO.providerContact.cityStateZip}">
									${summaryDTO.providerContact.cityStateZip}
								</c:if>
							</c:if>
							
						</p>
						<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">  <!-- Provider Contact Information Phone -->
							<c:choose>
								<c:when test="${summaryDTO.buyerContact != null && assignmentType=='PROVIDER'&&(summaryDTO.status == ACCEPTED_STATUS || summaryDTO.status == ACTIVE_STATUS 
									|| summaryDTO.status == COMPLETED_STATUS || summaryDTO.status == PROBLEM_STATUS)}">
									<c:if test="${not empty summaryDTO.providerContact.phoneWork}">
										<tr>
											<td width="100">
												<strong>Main Ph</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.phoneWork}
											</td>
										</tr>
									</c:if>
									<c:if test="${not empty summaryDTO.providerContact.phoneAlternate}">
										<tr>
											<td width="100">
												<strong>Pro Ph</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.phoneAlternate}
											</td>
										</tr>
									</c:if>
									<c:if test="${not empty summaryDTO.providerContact.phoneHome}">
										<tr>
											<td width="100">
												<strong>Home Ph</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.phoneHome}
											</td>
										</tr>
									</c:if>
									<c:if test="${not empty summaryDTO.providerContact.phoneMobile}">
										<tr>
											<td width="100">
												<strong>Mob Ph</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.phoneMobile}
											</td>
										</tr>
									</c:if>
									<c:if test="${not empty summaryDTO.providerContact.pager}">
										<tr>
											<td width="100">
												<strong>Pager</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.pager}
											</td>
										</tr>
									</c:if>						
									<c:if test="${not empty summaryDTO.providerContact.other}">
										<tr>
											<td width="100">
												<strong>Other</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.other}
											</td>
										</tr>
									</c:if>
									<c:if test="${not empty summaryDTO.providerContact.fax}">
										<tr>
											<td width="100">
												<strong>Fax</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.fax}
											</td>
										</tr>
									</c:if>																		
									<c:if test="${not empty summaryDTO.providerContact.email}">
										<tr>
											<td width="100">
												<strong>Email</strong>
											</td>
											<td width="200">
												${summaryDTO.providerContact.email}
											</td>
										</tr>
									</c:if>
								</c:when>
							</c:choose>
							
						</table>  <!-- Provider Contact Information Phone -->
						</td>  <!-- Provider Contact Information Cell -->
						</tr>
						</c:when>
					</c:choose>			
			</table>
		</c:otherwise>
	</c:choose>
			
</div>
</div>
</body>
</html>
