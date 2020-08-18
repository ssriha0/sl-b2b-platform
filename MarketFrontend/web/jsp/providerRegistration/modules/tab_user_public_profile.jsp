<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>

<c:set var="isAdminauth" scope="request" value="false" />


<tags:security actionName="etmSearch" >
	<c:set var="isAdminauth" scope="request" value="true" />
</tags:security>


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
		<s:form name="profilePublic" action="publicProfileAction"
			theme="simple">
			<div id="main">
				<s:hidden name="resourceId" value="${publicProfileDto.resourceId}"></s:hidden>
				<s:hidden name="vendorId" value="${publicProfileDto.vendorId}"></s:hidden>
				<s:hidden name="nodeId" value=""></s:hidden>
				<!-- NEW MODULE/ WIDGET-->

				<h3>
				
			
					<s:property value="publicProfileDto.firstName" />&nbsp;<c:if test="${isAdminauth==true}"> ${publicProfileDto.lastName} </c:if> <c:if test="${isAdminauth==false}">${fn:substring(publicProfileDto.lastName,0,1)}</c:if>	
					(User Id# <s:property value="publicProfileDto.resourceId"/>)
				</h3>

				<p>
					The public profile is below. When buyers are selecting service pros
					to do business with, this is the information.
					
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
								<c:if test="${isAdminauth==true}"><s:property value="publicProfileDto.userId" /></c:if><c:if test="${isAdminauth==false}"><s:property value="publicProfileDto.resourceId"/></c:if>
								
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
								<b>Company ID#</b>
								<br />
								<s:property value="publicProfileDto.vendorId" />
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
								&nbsp;
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
					</table>
				</div>

				<div class="clear"></div>

			<!-- 	<div class="darkGrayModuleHdr">
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
				<div class="darkGrayModuleHdr">
					Skill Categories
				</div>
				<div class="grayModuleContent mainWellContent">
					<s:if test="%{publicProfileDto.resourceSkillList.size !=0}">
						<s:iterator id="node" value="publicProfileDto.resourceSkillList"
							status="status">
							<div>
								-
								<a href="javascript:openpublicSkillTree('${node.nodeId}',${publicProfileDto.resourceId})"><s:property
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
				<s:if test="%{publicProfileDto.role =='Provider'}">
<%
				//<s:submit value="" action="completeProfileActiondoEdit" type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(${staticContextPath}/images/images_registration/btn/editProfile.gif);width:90px;height:20px;"  cssClass="btn20Bevel" />
%>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="200" height="20" style="background-image:url(${staticContextPath}/images/btn/returnToProfilePrefs.gif);"class="btn20Bevel" onclick="javascript:returnToListPublic()"/>
				</s:if>
			</div>
		</s:form>
	</body>
</html>
