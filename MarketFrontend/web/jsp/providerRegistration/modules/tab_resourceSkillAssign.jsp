<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>
<head>
</head>
<body onLoad="javascript:checkCheckBox();">
<div id="skillInformationTab" style="height:1839px;overflow-x: hidden;overflow-y:auto">

<div id="content_right_header_text">
    <%@ include file="../message.jsp"%>
</div>
<h3 class="paddingBtm">
	<s:property value="skillAssignDto.resourceName"/>
	(User Id# <s:property value="skillAssignDto.resourceId"/>)
</h3>
<s:form  name="resourceskillAssignAction" theme="simple">

<div id="main">
		<div id="content_right">
				<div id="content_right_header"><img src="${staticContextPath}/images/images_registration/widgets/txt_title_teammembers.gif" width="301" height="19" alt="" /></div>
				<div id="content_right_subheader">Skills
				    <c:if test="${!empty sessionScope.resourceName}">
				        for <c:out value="${sessionScope.resourceName }"/>
				    </c:if>
				</div>
				<div id="content_right_header_text">ServiceLive will match our buyers' needs with your service provider's skills.  Please select all applicable Skill Specializations <br> and Skill Types for your service provider.  When finished, click Next.  You can review and update this information at any time by returning to Team Profile and editing a Team Member.</div>
				<div id="content_right_header_text"><!--%@ include file="/inc/message.jsp"%--></div>
				<div class="content_right_section">
					<div class="content_right_section_header">Assign Skills</div>
					<div class="content_right_section_divider"></div>
					<br clear="all" />
					<div id="skills">

					   <div class="skills_header_row">
							<div class="skills_label" align="left">Check All</div>
							<s:iterator value="skillAssignDto.serviceTypes">
									<div class="skills_item">
									<s:if test="(active)">
										<input type="checkbox" checked="checked" name="ckeckAll_<s:property value="serviceTypeId"/>" onClick="javascript:doCheckAll(this)"/></div>
									</s:if>
									<s:else>
										<input type="checkbox" name="ckeckAll_<s:property value="serviceTypeId"/>" onClick="javascript:doCheckAll(this)"/></div>
									</s:else>
									<input type="hidden" name="hiddHeaders" value="<s:property value="serviceTypeId"/>" />
							</s:iterator>
					    </div>

					 <div class="skills_header_row">
							<div class="skills_header_item_blank">&nbsp;</div>
							<s:iterator  value="skillAssignDto.serviceTypes"  >
								<div class="skills_header_item"><s:property value="description"/>	</div>
							</s:iterator>
						</div>

					<s:iterator  value="skillAssignDto.skillTreeList" id="skill">
								<div class="skills_row">
										<div class="skills_label">
											<label onClick="nodeClick('node_<s:property value="nodeId"/>')">
												<s:if test="level==2">
													<img src="${staticContextPath}/images/images_registration/widgets/arrow1.gif" border="0">
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
													<b><s:property value="nodeName"/></b>
												</s:if>
												<s:else>
													<s:property value="nodeName"/>
												</s:else>
											</label>
										</div>
									
									<s:iterator  value="serviceTypes" id="servicTypes">
								  		
								  		<s:if test="(active)">								  		
							  				<div class="skills_item">
							  					<input type="checkbox" name="chk_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="skill.parentNodeId"/>_<s:property value="skill.level"/>" checked="checked" onClick="javascript:checkChlidObjects(this);javascript:checkCheckBox()"/>
							  					<input type="hidden" name="lbl_<s:property value="nodeId"/>" id="lbl_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="skill.parentNodeId"/>_<s:property value="skill.level"/>" value="chk_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="parentNodeId"/>_<s:property value="level"/>"  />
											</div>
								  		</s:if>
								  		<s:else>
											<div class="skills_item">
												<input type="checkbox" name="chk_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="parentNodeId"/>_<s:property value="level"/>" onClick="javascript:checkChlidObjects(this);javascript:checkCheckBox()"/>
												<input type="hidden" name="lbl_<s:property value="nodeId"/>" id="lbl_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="parentNodeId"/>_<s:property value="level"/>"  value="chk_<s:property value="nodeId"/>_<s:property value="serviceTypeId"/>_<s:property value="parentNodeId"/>_<s:property value="level"/>"/>
											</div>
								  		</s:else>
								  		
								  	</s:iterator>
								  </div>
								
							
							
					</s:iterator>
					</div> <!-- Skills end -->
					
					<br clear="all" />
				<!-- buttons -->
									<p>
		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="70"
			height="20"
			style="background-image: url(${staticContextPath}/images/images_registration/btn/previous.gif);"
			class="btn20Bevel"
			onclick="javascript:submitServicePage('resourceSkillAssignActionassignSkills.action?type=back')" />

		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="50"
			height="20"
			style="background-image: url(${staticContextPath}/images/images_registration/btn/save.gif);"
			class="btn20Bevel"
			onclick="javascript:submitServicePage('resourceSkillAssignActionassignSkills.action?type=stay')" />

		<input type="image" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="50"
			height="20"
			style="background-image: url(${staticContextPath}/images/images_registration/btn/next.gif);"
			class="btn20Bevel"
			onclick="javascript:submitServicePage('resourceSkillAssignActionassignSkills.action?type=forward')" />

	</p>
				</div> <!-- content_right_section -->
			</div> 
	
	 <!-- main -->
	
	
	<div class="bottomRightLink">
		<a href="javascript:cancelResourceSkillAssign('jsp/providerRegistration/resourceSkillAssignActiondoCancel.action');">Cancel</a>
	</div>
</s:form>
	<br/>
	<s:if test="%{skillAssignDto.lastServicePage == true}">
 <div id="content_right_header_text">
    <%@ include file="../modules/reg_submit.jsp"%>
	</div>
	</s:if>
	</div>
</body>

