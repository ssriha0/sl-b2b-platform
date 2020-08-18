<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.security.ActivityMapper"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="frontEndContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/MarketFrontend"%>" />	
	
	
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.manageUsers"/>
	</jsp:include>	
		<title>ServiceLive - Administrator Office - Manage Users</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<script language="JavaScript"src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js" type="text/javascript"></script>
				<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	 .ie7 .bannerDiv{margin-left:-1150px;} 
	 .explainer {
	display: none; 
	width:220px;
	height:auto;
	border: 3px solid #adaaaa; 
	background:#fcfae6;
	border-radius:10px;
	-moz-border-radius:10px;
	-webkit-border-radius:10px; 
	padding:10px;} 
		</style>			
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/buttons.css" />
		<link rel="stylesheet" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" type="text/css"></link>
		<link rel="stylesheet" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" type="text/css"></link>
		
		<script type="text/javascript" src="${staticContextPath}/javascript/pwdReset.js"></script>
		

<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modalPassword.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/tablePassword.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/buttons.css" />
<script type="text/javascript" src="${staticContextPath}/javascript/pwdReset.js"></script>


<script>
	function editUser(resourceId){
		document.manageUser.resourceId.value=resourceId;
		document.manageUser.action="<s:url action="serviceProAllTab" includeParams="none" method="doLoad"/>"
		
		document.manageUser.submit();		
	}
	function recertifyUser(resourceId){
		document.manageUser.resourceId.value=resourceId;
		// document.manageUser.action="<s:url action="serviceProAllTab" includeParams="tabView=tab5"  method="doLoad"/>"
		document.manageUser.action="${frontEndContextPath}/serviceProAllTab!doLoad.action?tabView=tab5";
		document.manageUser.submit();		
	}
	
	function showTooltip(resourceId){
		
		$("#explainer"+resourceId).css("position","absolute");
	     	var def = "Certification Due. Click here to apply now."
	     	$("#explainer"+resourceId).html(def);
		//$("#explainer").css("margin-left",205);
    	//$("#explainer").css("margin-top",-700);
    	$("#explainer"+resourceId).css("font-weight","bold");
    	$("#explainer"+resourceId).css("border","1px solid black");
    	$("#explainer"+resourceId).css("background-color","lightyellow"); 
    	$("#explainer"+resourceId).show();  
	}
	function hideTooltip(resourceId){
		$("#explainer"+resourceId).hide();
	}

	function openUserSummary(url){
		// alert("This should open the Manage User - Individual Profile page - Part if iterartion 14");
		// To Do add method to call summary user Functionality
		// this will be done later - meanwhile forwarding to edit user page
		document.manageUser.resourceId.value=url;
		document.manageUser.action="<s:url action="profileTabAction_execute" includeParams="none" />"
		document.manageUser.submit();		

	}
	
	function submitResetPassword(rid){	   	
	   document.manageUser.resourceId.value = rid;
	   document.manageUser.action="<s:url action="manageUserAction!resetPassword.action"/>";
	   document.manageUser.submit();
	}
		
	
</script>
<script type="text/javascript"
	src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
	
<script type="text/javascript">
	dojo.require("dijit.TitlePane");
	dojo.require("newco.jsutils");
	dojo.require("newco.servicelive.DashboardRealTimeManager");
		
</script>
</head>

		
	</head>
	

<body id="manage-users">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
			
			<div class="content padd">
				
			<h2 id="page-title">Manage Users</h2>
			


      <div class="content">
        <p class="paddingBtm">Manage the accounts of all of the dispatchers, administrators and providers that you have registered on
          ServiceLive. Clicking on a team member's name will take you to that person's profile, where you can review
          and edit their public and private information. Click 'add new user' to add new members to your team.<br>
        </p>
        
        
        <div class="table-wrap">
        <s:form name="manageUser" id="mUsers" action="manageUserAction" theme="simple">
<s:hidden name="resourceId" value=""></s:hidden>
<input type="hidden" id="selectedusername" name="selectedusername" value="23" />
        
      
        
        <s:if test="hasActionErrors() or hasActionMessages()">
			<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true" />
		</s:if>	
		<s:else>					
			<p class="success">
				<fmt:message bundle="${serviceliveCopyBundle}" key="manage_users.users" >
						<fmt:param value="${fn:length(teamProfileDTO.teamMemberList)}"/>
				</fmt:message>
			</p>
		</s:else>
        <%-- 
        <p class="success"> There are currently <s:property value="teamProfileDTO.count"/> users for your company.</p>
	 	 --%>	
	 		
 <table class="passwordReset" cellpadding="0" cellspacing="0">
          <tr>
            <th class="col1 first odd action">Administration</th>
            <th class="col2 even">Name</th>
            <th class="col3 even">Market Status</th>
            <th class="col4 even">Member Status</th>
            <th class="col5 even"><strong>Background Check</strong></th>
            <th class="col6 even"><strong>Phone Number</strong></th>
            <th class="col7 even">Title</th>
  
		    <s:iterator id="teamMemberObj" value="teamProfileDTO.teamMemberList" status="status">
           		
			<tr>
            <td class="col1 first even textleft">
               <div id="tablePasswordMenu">
				 
				      <div id="link1"><a id="#action${teamMemberObj.resourceId}" 
						href="#action${teamMemberObj.resourceId}" onmouseover="pwdMenuMouseOver('l1${teamMemberObj.resourceId}')" onmouseout="pwdMenuMouseOut('l1${teamMemberObj.resourceId}')">Take Action &gt;&gt;</a></div>
				      <div class="tablePasswordMenu" onmouseover="pwdMenuMouseOver('l1${teamMemberObj.resourceId}')" onmouseout="pwdMenuMouseOut('l1${teamMemberObj.resourceId}')">
				        <ul id='l1${teamMemberObj.resourceId}'>
		                  	<s:if test="%{teamProfileDTO.primaryInd}"> <!-- shekhar -->
	         	    		
	         	    			<li> <a href="javascript:editUser('${teamMemberObj.resourceId}')">Edit Profile</a> </li>
	         	    			<li> <a id='${teamMemberObj.resourceId}'  href='javascript:void(0)' onClick="showResetModal(this, '${teamMemberObj.resourceId}');">Reset Password</a> </li>
        	    											
	            			</s:if>
	            			<s:else>
	            			<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">
	            		
	            			</s:if>
	            			<s:else>
	            				<li> <a href="javascript:editUser('${teamMemberObj.resourceId}')"> Edit</a> </li>
	         	    			<li> <a id='${teamMemberObj.resourceId}' href='javascript:void(0)' onClick="showResetModal(this, '${teamMemberObj.resourceId}');">Reset Password</a> </li>
        	    				</s:else>
	            			</s:else>
				        </ul>
				      </div>				   
				</div>
               
	            <jsp:include page="/jsp/admin/passwordModal.jsp" flush="true">
		 			<jsp:param name="modalId" value="${teamMemberObj.resourceId}"/>
		 			<jsp:param name="name" value="${teamMemberObj.firstName} ${teamMemberObj.lastName}"/>
				</jsp:include>			
           </td>
           
            <td>
            <table border="0">
            <tr>
                 <td class="col2 even textleft" style="border: none;" width="65px">
            	
				<script>function loadDefaultPixel(img){
					img.src = '/ServiceLiveWebUtil/images/dynamic/default-pro_addPhoto_Thumb.png';
				}</script>
				<div id="proImageContainer">
					<img border="1" src="${contextPath}/providerProfileInfoAction_displayPhoto.action?max_width=65&max_height=65&resourceId=<c:out value="${teamMemberObj.resourceId}"/>" onerror="loadDefaultPixel(this);" class="left" />
				</div>
				
	            </td>
            <td class="col2 even textleft"  style="border: none;">

	            
	            
	            <s:if test="%{teamProfileDTO.primaryInd}">
	            	<a href="${contextPath}/providerProfileInfoAction_execute.action?resourceId=${teamMemberObj.resourceId}&popup=true">
	           			  <s:property value="firstName"/> <s:property value="lastName"/>
	           			  <br>
	           		</a>	  
	           			  (User Id# <s:property value="resourceId"/>)
	            </s:if>
	            <s:else>
	            		<s:if test ="%{teamProfileDTO.adminResourceId == resourceId or teamProfileDTO.loggedResourceId == teamMemberObj.resourceId}">
	          			  <s:property value="firstName"/> <s:property value="lastName"/>
	          			  <br>
	          			  (User Id# <s:property value="resourceId"/>)
	          			  </s:if>
	          			  <s:else>
	          			  	<a href="${contextPath}/providerProfileInfoAction_execute.action?resourceId=${teamMemberObj.resourceId}&popup=true">
	          			  		<s:property value="firstName"/> <s:property value="lastName"/>
	          			  		<br>
	          			  		(User Id# <s:property value="resourceId"/>)
	          			  	</a>
	          			  </s:else>
	            </s:else>
	            
	            
            </td>
            </tr>
            </table>
            </td>
            <td class="col3 even textleft"><s:if test="%{marketStatus == 0 || marketStatus == null}">
		            	InActive</s:if>
		            	<s:else>
		            	Active
		             	</s:else></td>
            <td class="col4 even textleft">
				<s:if test="%{resourceState !=''}"><s:property value="resourceState"/></s:if>
		            	<s:else>
		            		N/A
		             	</s:else>

			</td>
			 <s:if test="%{backgroundCheckRecertify == true}"> 
            <td onclick="recertifyUser('${teamMemberObj.resourceId}');"  onmouseover="showTooltip('${teamMemberObj.resourceId}')"  onmouseout="hideTooltip('${teamMemberObj.resourceId}')" class="col5 even textleft">
        
            <s:if test="%{backgroundCheckState !='' && backgroundCheckState != 'Not Started'}"><s:property value="backgroundCheckState"/>
            </s:if>
		            	<s:else>
		            		N/A
		             	</s:else>  <img alt=""  height="22" width="22" src="/ServiceLiveWebUtil/images/dynamic/manage_team_warning_icon.png">   
		             	<div id="explainer${teamMemberObj.resourceId}" class="explainer" style="display:blaock;z-index: 1000"></div>
		             	</td>
		       </s:if>
		       <s:else> 
		       
		                   <td class="col5 even textleft"><s:if test="%{backgroundCheckState !='' && backgroundCheckState != 'Not Started'}"><s:property value="backgroundCheckState"/>
            </s:if>
		            	<s:else>
		            		N/A
		             	</s:else></td>
		       </s:else>      	
            <td class="col6 even textleft">
            <s:if test="%{phoneNumber !='' || phoneNumber != null}">
            	<s:property value="phoneNumber"/>&nbsp;
            </s:if>
            	<s:else>
            		&nbsp;
             	</s:else></td>
            <td class="col7 even textleft">
            	<s:if test="%{title !=''}"><s:property value="title"/></s:if>
            	<s:else>&nbsp; </s:else>
            
            </td>
          </tr>

			</s:iterator>
        </table>
        </div>
          
   
    
    </s:form>
    <div class="clearfix">
			 <div class="formNavButtons padd">
					<input type="submit" class="button action" value="Add A New User" onClick="javascript:window.location.href=('<s:url action="serviceProAllTab" method="new" includeParams="none" />');" />
			 </div>			
	</div>
   </div>
	</div>
<c:if test="${SecurityContext.slAdminInd}">    
	<div class="tundra" style="width:255px;float:right;">
		<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
	</div>

</c:if>

	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	 <jsp:param name="PageName" value="Provider Registration - Manage Users "/>
	</jsp:include>
	</body>
</html>
