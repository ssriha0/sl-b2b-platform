<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

<c:set var="BUYER_PROVIDES_PART" value="<%= OrderConstants.SOW_SOW_BUYER_PROVIDES_PART%>" />
<c:set var="PROVIDER_PROVIDES_PART" value="<%= OrderConstants.SOW_SOW_PROVIDER_PROVIDES_PART%>" />
<c:set var="PARTS_NOT_REQUIRED" value="<%= OrderConstants.SOW_SOW_PARTS_NOT_REQUIRED%>" />

<c:set var="BUYER_ROLEID" value="<%= new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID" value="<%= new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="ROUTED_STATUS" value="<%= new Integer(OrderConstants.ROUTED_STATUS)%>" />
<c:set var="CLOSED_STATUS" value="<%= new Integer(OrderConstants.CLOSED_STATUS)%>" />
<c:set var="SIMPLE_BUYER_ROLEID" value="<%= new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />
<c:set var="PENDING_CANCEL_STATUS" value="<%= new Integer(OrderConstants.PENDING_CANCEL_STATUS) %>" />
<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<style type="text/css">
#taskCmts ul{
	padding-left:10px;
}
#taskCmts ol{
	padding-left:15px;
}
</style>
     <script type="text/javascript">
      jQuery(document).ready(function () {
     
		jQuery(".priceHistory").hide();
 		jQuery('.priceHistoryIcon').mouseover(function(e)
			{
			var x = e.pageX;
			var y = e.pageY;
			var idVal=this.id;
				jQuery("#"+idVal+"priceHistory").css('top',y-360);
				jQuery("#"+idVal+"priceHistory").css('left',x-180);
				jQuery("#"+idVal+"priceHistory").show();
			});
		jQuery('.priceHistoryIcon').mouseout(function(e){
			jQuery(".priceHistory").hide();
		});	
	
 });
  function expandScope(id,path){
var hidId="group"+id;
var testGroup=document.getElementById(hidId).value;
var divId="secondpane"+id;
var bodyId="secondpane_menu_body"+id;
if(testGroup=="menu_list"){
jQuery("#"+divId+" p.menu_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}else{
jQuery("#"+divId+" p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
}
var ob=document.getElementById('scopeImage'+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('scopeImage'+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('scopeImage'+id).src=path+"/images/widgets/arrowRight.gif";
}
}
function expandSubMenu(count,id,path){
var divId="subName"+count+id;
var bodyId="subBody"+count+id;
jQuery("#"+divId+" div.submenu_head").css({backgroundImage:"url("+path+"/images/widgets/subtitleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
var ob=document.getElementById('subscopeImage'+count+id).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('subscopeImage'+count+id).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('subscopeImage'+count+id).src=path+"/images/widgets/arrowRight.gif";
}
}

</script>

</head>
<body>
<c:set var="divName" value="secondpane"/>
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
<div id="${divName}" class="${groupVal}">
<c:set var="bodyName" value="secondpane_menu_body"/>
 <c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
  <c:set var="scopeImage" value="scopeImage"/>
    <c:set var="scopeImage" value="${scopeImage}${summaryDTO.id}"/>
 <p class="${headClass}" onclick="expandScope('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${scopeImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Scope of work</p>
    <div id="${bodyName}" class="${bodyClass}">

	<table cellpadding="0" cellspacing="0" class="contactInfoTable">
		<tr>
			<td class="column1">
				<p class="text11px">
					<strong>Service Location Information</strong>
					<br />
					${summaryDTO.locationContact.type}
					<br />		
					<c:if test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID || (roleType == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS)}">						
						<c:if test="${summaryDTO.locationContact.businessName!=null}">
							${summaryDTO.locationContact.businessName}
							<br />
						</c:if>
						${summaryDTO.locationContact.individualName}
						<br />
					</c:if>
					
					<c:choose>
						<c:when test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID || (roleType == PROVIDER_ROLEID && (summaryDTO.status != ROUTED_STATUS && summaryDTO.status != CLOSED_STATUS))}">
							${summaryDTO.locationContact.streetAddress}&nbsp;&nbsp;
							<!--  <span class="mapThis" onmouseout="popUp(event,'mapThis')"
								onmouseover="popUp(event,'mapThis')"><img
									src="${staticContextPath}/images/icons/mapThis.gif"
									alt="Map This Location" class="inlineBtn" /> </span>-->
							<br />
							<c:if test="${not empty summaryDTO.locationContact.streetAddress2}">
								${summaryDTO.locationContact.streetAddress2}&nbsp;
							</c:if>
							<br/>
						</c:when>
					</c:choose>
					${summaryDTO.locationContact.cityStateZip}
				</p>
			</td>

			<c:choose>
			<c:when test="${roleType == BUYER_ROLEID || roleType == SIMPLE_BUYER_ROLEID || (roleType == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS)}">						
				<td class="column2">
					<p class="text11px">
						<c:choose>
							<c:when test="${summaryDTO.locationContact != null}">
								<c:if test="${not empty summaryDTO.locationContact.phoneWork}">
											<strong>Work Phone</strong><br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.phoneHome}">
											<strong>Home Phone</strong><br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.phoneMobile}">
											<strong>Mobile Phone</strong><br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.pager}">
											<strong>Pager</strong><br/>
								</c:if>						
								<c:if test="${not empty summaryDTO.locationContact.other}">
											<strong>Other</strong><br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.fax}">
											<strong>Fax</strong><br/>
								</c:if>																		
								<c:if test="${not empty summaryDTO.locationContact.email}">
											<strong>Email</strong><br/>
								</c:if>
							</c:when>
						</c:choose>
					</p>
				</td>																			
				
				<td class="column3">
					<p class="text11px">
						<c:choose>
							<c:when test="${summaryDTO.locationContact != null}">
								<c:if test="${not empty summaryDTO.locationContact.phoneWork}">
									${summaryDTO.locationContact.phoneWork}<br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.phoneHome}">
									${summaryDTO.locationContact.phoneHome}<br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.phoneMobile}">
									${summaryDTO.locationContact.phoneMobile}<br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.pager}">
									${summaryDTO.locationContact.pager}<br/>
								</c:if>						
								<c:if test="${not empty summaryDTO.locationContact.other}">
									${summaryDTO.locationContact.other}<br/>
								</c:if>
								<c:if test="${not empty summaryDTO.locationContact.fax}">
									${summaryDTO.locationContact.fax}<br/>
								</c:if>																		
								<c:if test="${not empty summaryDTO.locationContact.email}">
									${summaryDTO.locationContact.email}<br/>
								</c:if>
							</c:when>
						</c:choose>																		
					</p>
				</td>
			</c:when>
			</c:choose>
		</tr>
	</table>
	<p>
	<c:if test="${roleType == BUYER_ROLEID || (roleType == PROVIDER_ROLEID && summaryDTO.status != ROUTED_STATUS && summaryDTO.status != CLOSED_STATUS)}">	
		<strong>Service Location Notes</strong>
		<br />		
		${summaryDTO.locationNotes}
	</c:if>
	</p>
	<br />
	<strong>Job Information</strong>
	<hr />
	<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
		<tr>
			<td width="200">
				<strong>Main Service Category</strong>
			</td>
			<td width="300">
				${summaryDTO.mainServiceCategory}
			</td>
		</tr>

	</table>
	
		<c:if test="${summaryDTO.partsSupplier==PROVIDER_PROVIDES_PART}">
		<p>${summaryDTO.jobInfo}</p><br/>
		</c:if>
	
	
		<c:if test="${summaryDTO.partsSupplier==BUYER_PROVIDES_PART}">
		<p>${summaryDTO.jobInfoOptional1}</p><br/>
		</c:if>
	
	
		<c:if test="${summaryDTO.partsSupplier==PARTS_NOT_REQUIRED}">
		<p>${summaryDTO.jobInfoOptional2}</p><br/>
		</c:if>
			
		<br/>
	
	<!-- NEW NESTED MODULE - Task List -->	
	<c:set var = "seqNum" value  = "-1"/>
	<c:forEach var="task" items="${summaryDTO.taskList}">
	<c:if test="${!(roleType == PROVIDER_ROLEID &&(task.taskStatus!=null &&( task.taskStatus=='CANCELED' ||task.taskStatus=='DELETED')))}">
	
	<c:set var="count" value="${count+1}"/>
	<c:set var="subName" value="subName"/>
	<c:set var="subBody" value="subBody"/>
	<c:set var="subName" value="${subName}${count}${summaryDTO.id}"/>
	<c:set var="subBody" value="${subBody}${count}${summaryDTO.id}"/>
	<div  class="submenu_list" id="${subName}">
	<c:set var="subscopeImage" value="subscopeImage"/>
    <c:set var="subscopeImage" value="${subscopeImage}${count}${summaryDTO.id}"/>

						<div class="submenu_head" style="height:auto;"
							onClick="expandSubMenu('${count}','${summaryDTO.id}','${staticContextPath}');">
							<table style="table-layout: fixed; width: 100%" >
								<tr>
									<td id="taskImage1"align="left" width="5%">
										<img id="${subscopeImage}"
											src="${staticContextPath}/images/widgets/arrowDown.gif" />
									</td>
									<td id="taskTitle1" align="justify" style="word-wrap: break-word;" width="60%">
										${task.title} <c:if test="${task.taskStatus!=null && task.taskStatus!='' && roleType != PROVIDER_ROLEID}">
										- ${task.taskStatus}
										</c:if>
									</td>
									<td align="right" width="30%>
									<div style="margin-right:30px;">
										<c:if
											test="${(empty task.finalPrice && summaryDTO.taskLevelPriceInd == true )  && roleType == BUYER_ROLEID}">Task Not Priced</c:if>
										<c:if
											test="${task.taskType==0  && task.sequenceNumber!=0 && summaryDTO.taskLevelPriceInd == true && roleType == BUYER_ROLEID &&  ! empty task.finalPrice}">Price: $ <fmt:formatNumber
												value="${task.finalPrice}" type="NUMBER"
												minFractionDigits="2" maxFractionDigits="2" />
										</c:if>
																				<c:if test="${task.taskStatus!='CANCELED'}">
										
										<c:if test="${task.taskType==1}">PERMIT PRICE PRE-PAID BY CUSTOMER: $ <fmt:formatNumber
												value="${task.sellingPrice}" type="NUMBER"
												minFractionDigits="2" maxFractionDigits="2" />
										</c:if>
										</c:if>
										<c:if test="${task.taskStatus=='CANCELED'}">
										
										<c:if test="${task.taskType==1}">PERMIT PRICE PRE-PAID BY CUSTOMER: $ <fmt:formatNumber
												value="${task.finalPrice}" type="NUMBER"
												minFractionDigits="2" maxFractionDigits="2" />
										</c:if>
										</c:if>
										</div>
									</td>
										<td align="right" id="historyIcon1" width="5%">
										
										<c:if
											test="${task.taskType==0 && fn:length(task.priceHistoryList)>1 && roleType == BUYER_ROLEID && task.sequenceNumber != seqNum}">
											
											
											<a
												id="${count}" class="priceHistoryIcon"
												href="javascript:void(0);"><img
													src="${staticContextPath}/images/widgets/dollaricon.gif" />
											</a>
											
										</c:if>
									</td>
								</tr>
							</table>
						</div>
						<c:if test="${! empty task.sequenceNumber}">
						<c:set var = "seqNum" value  = "${task.sequenceNumber}"/>
						</c:if> 
    <div  class="submenu_body" id="${subBody}">

			<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
				<tr>
					<td width="200">
						<strong>Category</strong>
					</td>
					<td width="300">
						${task.category}
					</td>
				</tr>
				<tr>
					<td>
						<strong>Sub-Category</strong>
					</td>
					<td>
						${task.subCategory}
					</td>
				</tr>
				<tr>
					<td>
						<strong>Skill</strong>
					</td>
					<td>
						${task.skill}
					</td>
				</tr>
			</table>
			<!-- 
			<div class="hrText">
				Assessment Questions
			</div>
			 -->
			<table cellpadding="0" cellspacing="0">
				<c:forEach var="question" items="${task.questionList}">
					<tr>
						<td width="400">
							${question.label}
						</td>
						<td width="200">
							${question.value}
						</td>
					</tr>
				</c:forEach>
			</table>
			<p>
				<strong>Task Comments</strong>
				<br />
				<c:choose>
					<c:when test="${fn:length(task.comments) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
						<div id="taskCmts" class="inputArea" style="height: 200px;fixed-width: 600px;background:#EDEDED;word-wrap:break-word;" >
							${task.comments}
						</div>
					</c:when>
					<c:otherwise>
					<div id="taskCmts" style="fixed-width: 600px;word-wrap:break-word;">
						${task.comments}
					</div>
					</c:otherwise>
				</c:choose>
			</p>
		</div>
	<div id ="${count}priceHistory" class="priceHistory" style="display: none; border: 4px outset grey;">
    	<table cellspacing="0">
 	<tr>
 		<div class="priceHistory_head" style="float: left;">
			<tr>
    		<th colspan="3">Price History for Task ${count}</th>
    		</tr>
    		<tr>
    		<td width=60px; style="border-bottom: 1px solid #ccc;">
    			Price
    		</td>
    		 <td width=80px; style="border-bottom: 1px solid #ccc;">
    			Date
    		</td>
    		<td width=90px; style="border-bottom: 1px solid #ccc;">
    			Changed By
    		</td> 
    		</tr> 
    </div>
    </tr>
    	 <div class="priceHistory_body" align="left" >
    	<c:forEach var="history" items="${task.priceHistoryList}">
     	<tr>

    		<td width=60px;>
    		$<fmt:formatNumber value="${history.price}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
    		</td>
    		
    		 <td width=80px;>
    		 ${history.modifiedDate}
    		</td>
    		
    		<td width=90px;>
    		${history.modifiedByName}
    		<c:if test="${history.modifiedByName!='SYSTEM'}">
    		(ID#${history.modifiedBy})
    		</c:if>
    		</td>
 
  </tr>
  	</c:forEach>
    </div>
  	
    </tr>
    			</table>
    </div>
		</div>
		</c:if>
	</c:forEach>
	

	<%-- 
	<div class="hrText">
		General Scope of Work Comments
	</div>
	<p>
		${summaryDTO.generalComments}
	</p>
	--%>
</div>
</div>