
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />





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
	href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
<script language="JavaScript"
	src="${staticContextPath}/javascript/js_registration/tooltip.js"
	type="text/javascript"></script>


<s:form name="skillAssignGeneralAction" theme="simple">
<body>
<div id="content_right_header_text">
    <%@ include file="../message.jsp"%>
</div>

<h3 class="paddingBtm">
	<s:property value="skillAssignDto.resourceName"/>
	(User Id# <s:property value="skillAssignDto.resourceId"/>)
</h3>
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Language Fluency
</div>

<div class="grayModuleContent mainWellContent clearfix">
	<p>
		Speaking more than one language may provide a competitive advantage.
		Let us know if your service pro is fluent in any of the following:
	</p>
	<!-- <p>
		<input type="checkbox" />
		English - Service provider can speak, read and write in English.
	</p>
	<p>
		Check all others that apply.
	</p> -->
	 
	
	<s:iterator value="skillAssignDto.languageList" id="skill"
				status="status">
		<tr >
		<td>
		<s:if test="%{descr == 'English'}">
				<p><input type="checkbox" name="lan_<s:property value="languageId"/>"  value="<s:property value="descr" />"  checked="checked"/>
				<s:property value="descr" />- Service provider can speak, read and write in English.<span class="req">*</span>
		</p>
		<p>	Check all others that apply.
		</p>
		</s:if ></td>
		</tr>
		</s:iterator>
		<p>
		<s:iterator value="skillAssignDto.languageList" id="skill"
				status="status">
		<tr id="Group2">
			<s:if test="%{languageType == 'Group 2'}">
			<td >
			<s:if test="%{active}">
				<div class="lang_item"> 
				<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"   checked="checked" />
				<s:property value="descr" />
				</div>
			</s:if >
			<s:else>
				<div class="lang_item">
				<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"    />
				<s:property value="descr" />
				</div>
			</s:else>
			</td>
			</s:if>
		 </tr>
		</s:iterator>
		</p>
		<br/>
		<p>
		<s:iterator value="skillAssignDto.languageList" id="skill"
				status="status"> 		
		<tr id="Group3">
			<s:if test="%{languageType == 'Group 3'}">
				<td >
				<s:if test="%{active}">
					<div class="lang_item">
					<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"   checked="checked" />
					<s:property value="descr" />
					</div>
			</s:if >
			<s:else>
				<div class="lang_item">
				<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"    />
				<s:property value="descr" />
				</div>
			</s:else>
			</td>
			</s:if>
		</tr>
		</s:iterator> 
			</p>	
			<br/>
			<p>
		<s:iterator value="skillAssignDto.languageList" id="skill"
				status="status"> 		
		<tr id="Group4">
			<s:if test="%{languageType == 'Group 4'}">
				<td>
				<s:if test="%{active}">
					<div class="lang_item"> 
					<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"   checked="checked" />
					<s:property value="descr" />
					</div>
			</s:if >
			<s:else>
				<div class="lang_item"> 
				<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"    />
				<s:property value="descr" />
				</div> 
			</s:else>
			</td>
			</s:if>
		</tr>
		</s:iterator> 
			</p>
			<br/>
			<p>
		<s:iterator value="skillAssignDto.languageList" id="skill"
				status="status"> 		
		<tr id="Group5">
			<s:if test="%{languageType == 'Group 5'}">
				<td>
				<s:if test="%{active}">
					<div class="lang_item">
					<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"   checked="checked" />
					<s:property value="descr" />
					</div>
			</s:if >
			<s:else>
				<div class="lang_item">
				<input type="checkbox" name="lan_<s:property value="languageId"/>" value="<s:property value="descr" />"    />
				<s:property value="descr" />
				</div>
			</s:else>
			</td>
			</s:if>
		</tr>
		</s:iterator> 
			</p>				

	
	<br clear="all" />
</div>

<div class="darkGrayModuleHdr">
	Skill Categories
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		We've grouped skill sets into categories that can be easily browsed by
		buyers. Choose from the categories below, then click 'build service
		skills' to further define the service pro's capabilities.
	</p>
	<p>
		Check all that apply <span class="req">*</span>
	</p>
	<div class="provider_content_right_row_odd">
		<div class="provider_content_right_row_box">
			<s:iterator value="skillAssignDto.skillTreeList" id="skill"
				status="status">
				<tr id="node_<s:property value="nodeId"/>">
					<td>

						<s:if test="%{active}">
						<input type="checkbox" name="chk_<s:property value="nodeId"/>_<s:property value="sortOrder"/>"  checked="checked" />
							
							<s:property value="nodeName" />
							<br />
						</s:if>
						<s:else>
							<input type="checkbox" name="chk_<s:property value="nodeId"/>_<s:property value="sortOrder"/>" />
							<s:property value="nodeName" />
							<br />
						</s:else>
						<label>

						</label>
					</td>
				</tr>
			</s:iterator>
		</div>
	</div>

	<br clear="all" />
	
		
	<div class="clearfix">
			<div class="formNavButtons">	
		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="130"
			height="20"
			style="background-image: url(${staticContextPath}/images/images_registration/btn/buildServiceSkills.gif);"
			class="btn20Bevel"
			onclick="javascript:submitSkillsPage('skillAssignGeneralActionsaveSkills.action?type=buildSkill')" />
	
		</div>
		</div>
			
		
</div>
<div class="clearfix">
			<div class="formNavButtons">
		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="70"
			height="20"
			style="background-image: url(${staticContextPath}/images/btn/previous.gif);"
			class="btn20Bevel"
			onclick="javascript:submitSkillsPage('skillAssignGeneralActionsaveSkills.action?type=back')" />
		<!-- 
		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="50"
			height="20"
			style="background-image: url(${staticContextPath}/images/images_registration/btn/next.gif);"
			class="btn20Bevel"
			onclick="javascript:submitServicePage('skillAssignGeneralActionsaveSkills.action?type=forward')" />
			 -->
		
			</div>
			<br/>
			<div class="formNavButtons">
				<c:choose>
			  	<c:when test="${sessionScope.userStatus=='editUser'}">
    	 		<c:if test="${sessionScope.resourceStatus == 'complete' && sessionScope.providerStatus == 'complete'}">
    			<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="120"
						height="20"
						style="background-image: url(${staticContextPath}/images/btn/updateProfile.gif);"
						class="btn20Bevel"
						onclick="javascript:submitSkillsPage('skillAssignGeneralActionsaveSkills.action?type=updateProfile')" />
      			</c:if>
      			</c:when>
      			<c:otherwise>
      			<c:if test="${sessionScope.resourceStatus == 'complete' && sessionScope.providerStatus == 'complete'}">
      			<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="120"
						height="20"
						style="background-image: url(${staticContextPath}/images/btn/updateProfile.gif);"
						class="btn20Bevel"
						onclick="javascript:submitSkillsPage('skillAssignGeneralActionsaveSkills.action?type=updateProfile')" />
      			</c:if>
      			</c:otherwise>
      			</c:choose>
			</div>
			<div class="bottomRightLink">
				<a href="javascript:cancelSkillAssignGen('jsp/providerRegistration/skillAssignGeneralActiondoCancel.action');">Cancel</a>
			</div>
		</div>


</body>
</s:form>
