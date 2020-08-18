<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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

<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
     <script type="text/javascript">
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
jQuery("#"+divId+" p.submenu_head").css({backgroundImage:"url("+path+"/images/widgets/subtitleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
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
	<c:forEach var="task" items="${summaryDTO.taskList}">
	<c:set var="count" value="${count+1}"/>
	<c:set var="subName" value="subName"/>
	<c:set var="subBody" value="subBody"/>
	<c:set var="subName" value="${subName}${count}${summaryDTO.id}"/>
	<c:set var="subBody" value="${subBody}${count}${summaryDTO.id}"/>
	<div  class="submenu_list" id="${subName}">
	<c:set var="subscopeImage" value="subscopeImage"/>
    <c:set var="subscopeImage" value="${subscopeImage}${count}${summaryDTO.id}"/>
  <p class="submenu_head" onClick="expandSubMenu('${count}','${summaryDTO.id}','${staticContextPath}');" >&nbsp;<img id="${subscopeImage}" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;${task.title}</p>
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
						<div class="inputArea" style="height: 200px;width: 600px;background:#EDEDED" >
							${task.comments}
						</div>
					</c:when>
					<c:otherwise>
						${task.comments}
					</c:otherwise>
				</c:choose>
			</p>
		</div>
		</div>
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