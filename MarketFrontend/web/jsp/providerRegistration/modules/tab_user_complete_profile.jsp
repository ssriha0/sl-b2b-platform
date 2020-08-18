<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<html>
	<head>
		<title>ServiceLive [Buyer Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>

		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			var contextPath = '${pageContext.request.contextPath}';
			
		

		function openUserSummary(url){
			// alert("This should open the Manage User - Individual Profile page - Part if iterartion 14");
			// To Do add method to call summary user Functionality
			// this will be done later - meanwhile forwarding to edit user page
			alert(url);
			document.manageUser.resourceId.value=url;
			document.manageUser.action="<s:url action="providerProfileInfoAction_execute" includeParams="none"/>"
			document.manageUser.submit();		
	
		}
	
		
			
		</script>

		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />

		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/utils.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/calendar.css?random=20051112"
			media="screen"></link>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>


	</head>
	<body>
		<div id="content_right_header_text">
			<%@ include file="../message.jsp"%>
		</div>
		<s:form name="publicProfile" action="publicProfileAction"
			theme="simple">
			<div id="main">
				<input type="hidden" name="resourceId" value="${publicProfileDto.resourceId}"/>
				<input type="hidden" name="vendorId" value="${publicProfileDto.vendorId}"/>
				<input type="hidden" name="nodeId" value=""/>
				<!-- NEW MODULE/ WIDGET-->

				
				<h3>
					<s:property value="publicProfileDto.firstName" />&nbsp;<s:property value="publicProfileDto.lastName" />
					(User Id# <s:property value="publicProfileDto.resourceId"/>)
				</h3>

				<p>
					The complete user profile is below. Some of this information is private and cannot be seen by buyers. 
					<br/>To view the public profile, click on the 'public profile' tab above. 
				</p>
				<div class="darkGrayModuleHdr">
					Identification
				</div>

				<div class="grayModuleContent mainWellContent">

					<table cellpadding="0" cellspacing="0" style="">
						<tr>
							<td>
								<b>User ID#</b>
								<br />
								<s:property value="publicProfileDto.userId" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<b>ServiceLive Member Since</b>
								<br />
								<s:property value="publicProfileDto.joinDate" />
							</td>
						</tr>
						<tr>
							<td>
								<b>User Name</b>
								<br />
								<s:property value="publicProfileDto.userName" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<b>Business Size</b>
								<br />
								<s:property value="publicProfileDto.companySize" />
							</td>
						</tr>
						<tr>
							<td>
								<b>Social Security Number</b>
								<br />
								XXX-XX-<s:property value="publicProfileDto.ssnLast4" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<b>Business Origin</b>
								<br />
								<s:property value="publicProfileDto.busStartDt" />
							</td>
						</tr>
						<tr>
							<td>
								<b>Job Title</b>
								<br />
								<s:property value="publicProfileDto.otherJobTitle" />
							</td>
							<td>
								&nbsp;
							</td>
							<td>
								<b>Roles</b>
							    <c:if test="${publicProfileDto.ownerInd =='1'}">
								<br/>-Owner/Principal
								</c:if>
								<c:if test="${publicProfileDto.dispatchInd =='1'}">
								<br/>-Dispatcher/Scheduler
								</c:if>
								<c:if test="${publicProfileDto.managerInd =='1'}">
								<br/>-Manager
								</c:if>
								<c:if test="${publicProfileDto.sproInd=='1'}">
								<br/>-Service Provider
								</c:if>
								<c:if test="${publicProfileDto.adminInd =='1'}">
								<br/>-Administrator
								</c:if>
								<c:if test="${publicProfileDto.otherInd =='1'}">
								<br/>-Other
								</c:if>
							</td>
						</tr>
					</table>
				</div>

				<div class="clear"></div>
				<!--ServiceLive Status  -->
				<div class="darkGrayModuleHdr">
					ServiceLive Status
				</div>
				<div class="grayModuleContent mainWellContent">
					Profile Status:<b><s:property value="publicProfileDto.serviceLiveStatus" /></b>
				</div>
				<!--Role in Marketplace -->
				<div class="darkGrayModuleHdr">
					Role in Marketplace
				</div>
				<div class="grayModuleContent mainWellContent">
					Performs service calls in the marketplace: <b><c:choose><c:when test="${publicProfileDto.resourceInd == '1'}">Yes</c:when>
																 <c:when test="${publicProfileDto.resourceInd != '1'}">No</c:when></c:choose></b> 
				</div>
				<!--Marketplace Status-->
				<div class="darkGrayModuleHdr">
					Marketplace Status   
				</div>
				<div class="grayModuleContent mainWellContent">
					Preferred Marketplace Status: <b><c:choose><c:when test="${publicProfileDto.marketPlaceInd == '1'}">Active</c:when>
												<c:when test="${publicProfileDto.marketPlaceInd != '1'}">Inactive</c:when></c:choose></b>
				</div>


			<!-- <div class="darkGrayModuleHdr">
					Vital Statistics
				</div>
				<div class="grayModuleContent mainWellContent">
					<table cellpadding="0" cellspacing="0" style="">
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div>
				<div class="darkGrayModuleHdr">
					Buyer Relations
				</div>
				<div class="grayModuleContent mainWellContent">
					<table cellpadding="0" cellspacing="0" style="">
						<tr>
							<td>
								&nbsp;
							</td>
						</tr>
					</table>
				</div> -->
				<div class="darkGrayModuleHdr">
					Dispatch Address &amp; Coverage Area
				</div>
				<div class="grayModuleContent mainWellContent">
					<div>
						<s:property value="publicProfileDto.dispAddCity" />
						,
						<s:property value="publicProfileDto.dispAddState" />
						&nbsp;
						<s:property value="publicProfileDto.dispAddZip" />
					</div>
					<div>
						<b>Range:</b> &lt;
						<s:property value="publicProfileDto.dispAddGeographicRange" />
						Mile Radius
					</div>
				</div>
					<!--Billing Information-->
				<div class="darkGrayModuleHdr">
					Billing Information
				</div>
				<div class="grayModuleContent mainWellContent">
					<b>Preferred Billing Rate</b>
					<br/>$<s:property value="publicProfileDto.hourlyRate"/> Per Hour
				</div>
				<!--Work  Schedule-->
				<div class="darkGrayModuleHdr">
					<b>Work Schedule</b>
				</div>
				<div class="grayModuleContent mainWellContent">
					<table cellpadding="0" cellspacing="0">
    <tr height="24">
      <td width="112">Monday</td>
      
      <c:choose>
      <c:when test="${publicProfileDto.monNaInd =='1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.monStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
     <td width="175"><c:choose><c:when test="${ publicProfileDto.monStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.monStart"/>
      					&nbsp;to&nbsp;
     					<s:property value="publicProfileDto.monEnd"/>
      					   				    </c:when>
      				     <c:when test="${publicProfileDto.monNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose>
      				    </td> 
 
    </tr>
    <tr height="24">
      <td>Tuesday</td>
      <c:choose>
       <c:when test="${publicProfileDto.tueNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.tueStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
     <td width="175"><c:choose><c:when test="${ publicProfileDto.tueStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.tueStart"/>
      					&nbsp;to&nbsp;
     					<s:property value="publicProfileDto.tueEnd"/>
      					</c:when>
      				     <c:when test="${publicProfileDto.tueNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose>
      				        </td>    				        
    
    </tr>
    <tr height="24">
      <td>Wednesday</td>
      	<c:choose>
       <c:when test="${publicProfileDto.wedNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.wedStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      <td width="175"><c:choose><c:when test="${ publicProfileDto.wedStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.wedStart"/>
      					&nbsp;to&nbsp;
     					<s:property value="publicProfileDto.wedEnd"/>
      					  </c:when>
      				     <c:when test="${publicProfileDto.wedNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose> 
      				        </td>  
    </tr>
    <tr height="24">
      <td>Thursday</td>
      <c:choose>
       <c:when test="${publicProfileDto.thuNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.thuStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
     <td width="175"><c:choose><c:when test="${ publicProfileDto.thuStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.thuStart"/>
      					&nbsp;to&nbsp;
     					<s:property value="publicProfileDto.thuEnd"/>
      					</c:when>
      				     <c:when test="${publicProfileDto.thuNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose>
      				        </td>  
    </tr>
    <tr height="24">
      <td>Friday</td>
       <c:choose>
       <c:when test="${publicProfileDto.friNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.friStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      <td width="175"><c:choose><c:when test="${ publicProfileDto.friStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.friStart"/>
      					&nbsp;to&nbsp;
     					<s:property value="publicProfileDto.friEnd"/>
      					
      				    </c:when>
      				     <c:when test="${publicProfileDto.friNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose>
      				        </td>  
    </tr>
    <tr height="24">
      <td>Saturday</td>
      <c:choose>
      <c:when test="${publicProfileDto.satNaInd == '1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.satStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
      <td width="175"><c:choose><c:when test="${ publicProfileDto.satStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.satStart"/>
      					&nbsp;
      					to&nbsp;
     					<s:property value="publicProfileDto.satEnd"/>
      					</c:when>
      				     <c:when test="${publicProfileDto.satNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose>
      				        </td>  
    </tr>
    <tr height="24">
      <td>Sunday</td>
      <c:choose>
       <c:when test="${publicProfileDto.sunNaInd =='1'}">
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value="checked='checked'"/>
      </c:when>
      <c:when test="${publicProfileDto.sunStart != null}"> 
      		<s:set name="comboDisableStatus" value="false"/>
      		<s:set name="checkedStatusFrom" value="checked='checked'"/>
      		<s:set name="checkedStatus24" value=""/>
      		<s:set name="checkedStatusNa" value=""/>
      </c:when>
      <c:otherwise>
      		<s:set name="comboDisableStatus" value="true"/>
      		<s:set name="checkedStatusFrom" value=""/>
      		<s:set name="checkedStatus24" value="checked='checked'"/>
      		<s:set name="checkedStatusNa" value="'"/>
      </c:otherwise>
      </c:choose>
       <td width="175"><c:choose><c:when test="${ publicProfileDto.sunStart != null}">
      				    From
      					&nbsp;
      					<s:property value="publicProfileDto.sunStart"/>
      					&nbsp;to&nbsp;
     					<s:property value="publicProfileDto.sunEnd"/>
      					 </c:when>
      				     <c:when test="${publicProfileDto.sunNaInd =='1'}">
      				        not available
      				    </c:when>  
      				    <c:otherwise> 24 hrs/day
      				   
      				    </c:otherwise>
      				    </c:choose> 
      				        </td>  
       
    </tr>
  </table>
  <br clear="all" />

				</div>
				<!--ServiceLive Activities & Permissions-->
				<div class="darkGrayModuleHdr">
					ServiceLive Activities & Permissions..
				</div>
				<br/><b><u>Administrator Activities</u></b>
				<div class="grayModuleContent mainWellContent">
					<s:iterator id="userList" status="inner"
										value="publicProfileDto.activityList">
					<c:if test="${checked == true}">
					- <s:property
								value="activityName" />
										<br/>
										</c:if>
					</s:iterator>
				</div>
				
				<!--Contact-->
				<div class="darkGrayModuleHdr">
					Contact
				</div>
				<div class="grayModuleContent mainWellContent">
					<table><tr>
					<td width="112"><b>Work Phone</b></td>
					<td><s:property value="publicProfileDto.businessPhone1" />-<s:property value="publicProfileDto.businessPhone2" />-<s:property value="publicProfileDto.businessPhone3" /></td>
					<td>&nbsp;&nbsp;<b>Ext.</b></td>
					<td>&nbsp;<s:property value="publicProfileDto.businessExtn" /></td>
					</tr>
					<tr>
					<td><b>Mobile Phone</b></td>
					<td><s:property value="publicProfileDto.mobilePhone1" />-<s:property value="publicProfileDto.mobilePhone2" />-<s:property value="publicProfileDto.mobilePhone3" /></td>
					</tr>
					<tr>
					<td><b>E-mail</b></td>
					<td><s:property value="publicProfileDto.email" /></td>
					</tr>
					<tr>
					<td><b>Alternate E-mail</b></td>
					<td><s:property value="publicProfileDto.altEmail" /></td>
					</tr>
					<tr>
					<td><b>SMS</b></td>
					<td><s:property value="publicProfileDto.smsAddress1" />-<s:property value="publicProfileDto.smsAddress1" />-<s:property value="publicProfileDto.smsAddress1" /></td>
					</tr>
					</table>
				</div>
				<!--Service Order Notification-->
				<div class="darkGrayModuleHdr">
					Service Order Notification
				</div>
				<div class="grayModuleContent mainWellContent">
					<table><tr>
					<td><b>Primary</b></td>
					<td></td>
					</tr>
					<tr>
					<td><b>Secondary</b></td>
					<td></td>
					</tr>
					</table>
				</div>
				<!--Service Order Support Notification-->
				<div class="darkGrayModuleHdr">
					Service Order Support Notification
				</div>
				<div class="grayModuleContent mainWellContent">
					<table><tr>
					<td><b>Primary</b></td>
					<td></td>
					</tr>
					<tr>
					<td><b>Secondary</b></td>
					<td></td>
					</tr>
					</table>
				</div>
				<div class="darkGrayModuleHdr">
					Skill Categories
				</div>
				<div class="grayModuleContent mainWellContent">
					<s:if test="%{publicProfileDto.resourceSkillList.size !=0}">
						<s:iterator id="node" value="publicProfileDto.resourceSkillList"
							status="status">
							<div>
								-
								<a href="javascript:openSkillTree('${node.nodeId}')"><s:property
										value="nodeName" />&nbsp;</a>
								<br />
							</div>
						</s:iterator>
					</s:if>
					<s:else>
						 <p>
							Currently, there are no skills categories for this
							service provider.
						</p>
					 </s:else>
				</div>
				<div class="darkGrayModuleHdr">
					Service Skills
				</div>
				<div class="grayModuleContent mainWellContent">
					<div class="skills_header_row">
						<div class="skills_header_item_blank">
							&nbsp;
						</div>
						<s:if test="%{skillAssignDto.serviceTypes.size !=0}">
						<s:iterator value="skillAssignDto.serviceTypes">
							<div class="skills_header_item">
								<s:property value="description" />
							</div>
						</s:iterator>
						</s:if>
						
					</div>
					<s:if test="%{skillAssignDto.skillTreeList.size != 0}">
						<s:iterator value="skillAssignDto.skillTreeList" id="skill">
							<div class="skills_row">
								<div class="skills_label">
									<label onClick="nodeClick('node_<s:property value="nodeId"/>')">
										<s:if test="level==2">
											<img
												src="${staticContextPath}/images/images_registration/widgets/arrow1.gif"
												border="0">
										</s:if>
										<s:if test="level==3">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</s:if>
										<s:if test="level==4">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</s:if>
										<s:if test="level==5">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</s:if>
										<s:if test="level==6">
												&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											</s:if>
										<s:if test="level==1">
											<b><s:property value="nodeName" />
											</b>
										</s:if>
										<s:else>
											<s:property value="nodeName" />
										</s:else>
									</label>
								</div>

								<s:iterator value="serviceTypes" id="servicTypes">

									<s:if test="(active)">
										<div class="skills_item">
											<input type="checkbox" disabled
												name="chk_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="skill.parentNodeId"/>_<s:property value="skill.level"/>"
												checked="checked"
												onClick="javascript:checkChlidObjects(this);javascript:checkCheckBox()" />
										</div>
									</s:if>
									<s:else>
										<div class="skills_item">
											<input type="checkbox" disabled
												name="chk_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="parentNodeId"/>_<s:property value="level"/>"
												onClick="javascript:checkChlidObjects(this);javascript:checkCheckBox()" />
										</div>
									</s:else>
								</s:iterator>
							</div>
							<!--Skill Row-->
						</s:iterator>
					</s:if>
					 <s:else>
					 <p>
							Currently, there are no service skills for this
							service provider.
						</p>
					 </s:else>
					
					<br />
					<div style="clear: both;"></div>
					
				</div>
				
				<div class="darkGrayModuleHdr">
					Language Fluency
				</div>
				<div class="grayModuleContent mainWellContent">
					<s:if test="%{publicProfileDto.languageList.size !=0}">
						<div>
							<p>
								<b>Primary: </b>
								<s:iterator value="publicProfileDto.languageList"
									status="status">
									<s:if test="%{descr == 'English'}">
										<s:property value="descr" />
									</s:if>
								</s:iterator>
							</p>
						</div>
						<div>
							<p>
								<b> Secondary: </b>
								<s:iterator value="publicProfileDto.languageList"
									status="status">
									<s:if test="%{descr != 'English'}">
										<s:property value="descr" />
									</s:if>
								</s:iterator>
							</p>
						</div>
					</s:if>
					<s:else>
						<p>
							Currently, there are no languages for this
							service provider.
						</p>
					</s:else>
				</div>

				<div class="darkGrayModuleHdr">
					Licenses &amp; Certifications
				</div>
				<div class="grayModuleContent mainWellContent">

					<s:if test="%{publicProfileDto.credentialsList.size !=0}">
						<table class="scrollerTableHdr licensesTableHdr" cellpadding="0"
							cellspacing="0">
							<tr>
								<td class="column2">
									Credential Type
								</td>
								<td class="column3">
									Name
								</td>
								<td class="column4">
									Expiration
								</td>
								<td class="column5">
									Verified by
										ServiceLive
								</td>
							</tr>
						</table>
						<table class="licensesTable" cellpadding="0" cellspacing="0">
							<s:iterator value="publicProfileDto.credentialsList"
								status="status">

								<tr>
									<td class="column2">
										<s:property value="credType" />
									</td>
									<td class="column3">
										<s:property value="licenseName" />
									</td>
									<td class="column4">
										<s:property value="expirationDate" />
									</td>
									<td class="column5">
										<s:if test="%{wfStateId =='11'}">
											Pending Approval
										</s:if>
										<s:elseif test="%{wfStateId =='12'}">
											<img
												src="${staticContextPath}/images/icons/greenCheck.gif"
												width="10" height="10" alt="">
										</s:elseif>
									</td>
								</tr>
							</s:iterator>
						</table>
					</s:if>
					<s:else>
						<p>
							Currently, there are no licenses or certificates on file for this
							service provider.
						</p>
					</s:else>
				</div>
				<div class="darkGrayModuleHdr">
					Background Check
				</div>
				<div class="grayModuleContent mainWellContent">
					<div>
						<p>
							<b>Requested Date</b>
							<s:property value="publicProfileDto.bgChkReqDate" />
						</p>
						<p>
							<b>Approved Date</b>
							<s:property value="publicProfileDto.bgChkAppDate" />
						</p>
					</div>
				</div>
				
				<jsp:include
					page="/jsp/providerRegistration/modules/panel_select_provider_networks.jsp" />
				
<%				 
 //				<s:submit value="" action="completeProfileActiondoEdit" type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(${staticContextPath}/images/images_registration/btn/editProfile.gif);width:90px;height:20px;"  cssClass="btn20Bevel" /> -->
 %>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="200" height="20" style="background-image:url(${staticContextPath}/images/btn/returnToProfilePrefs.gif);"class="btn20Bevel" onclick="javascript:returnToList()"/>
				
			</div>
		
		</s:form>
	</body>
</html>
