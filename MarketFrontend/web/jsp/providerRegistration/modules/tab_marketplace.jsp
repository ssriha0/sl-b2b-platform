<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<html>
	<head>
<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${contextPath}/dijitTabPane-serviceLive.css">
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
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css" />
			
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/buttons.css" />
			
		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		
	</head>
	<body>
	<h3 class="paddingBtm">
	<s:property value="marketPlaceDTO.fullResoueceName"/>
	(User Id# <s:property value="marketPlaceDTO.resourceID"/>)
	</h3>
		<div id="content_right_header_text">
			<%@ include file="../message.jsp"%>
		</div>
		<s:form action="marketPlaceAction" method="post" theme="simple"
			validate="true">
			<s:hidden name="marketPlaceDTO.serviceCall"></s:hidden>
			<s:hidden name="marketPlaceDTO.primaryIndicator"></s:hidden>
			<s:hidden id="marketPlaceDTOAction" name="marketPlaceDTO.action" value=""></s:hidden>
			
			
			<!-- NEW MODULE/ WIDGET-->

			<div class="grayModuleContent mainWellContent clearfix">
				<p>
					As the administrator and primary contact, you have access to all
					ServiceLive activities. You'll be able to define access and user
					preferences for each user you add to your account.
				</p>
				<p>
					Define user access to your ServiceLive account from this page.
					You'll be able to define access and user preferences for each user
					you add to your account.
				</p>
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
				<div class="darkGrayModuleHdr">
					Provider Permissions
				</div>

				<div class="grayModuleContent mainWellContent">
					<p class="paddingBtm">
						Check all that apply.
					</p>
					<table cellpadding="0" cellspacing="0" style="width=300px;">
						<tr>
						<td >
						<div >
								<c:choose>
								<c:when
									test="${primaryIndFlag &&  marketPlaceDTO.primaryIndicator == '1'}">
									
									<s:iterator id="userList" status="inner"
										value="marketPlaceDTO.activityList">
										
									<s:checkbox disabled="true"  name="isCheckedactivity_%{marketPlaceDTO.activityList[#inner.index].index}"
										 	value="marketPlaceDTO.activityList[#inner.index].checked" />
										 	
										<s:property
											value="marketPlaceDTO.activityList[#inner.index].activityName" />
											<c:if test="${marketPlaceDTO.activityList[inner.index].activityName != null && marketPlaceDTO.activityList[inner.index].activityName == 'Manage SPN Invitations'}">
											- This access allows the Team Member to accept or reject SPN invitations on behalf of Primary Administrator/Owner. The action taken on your behalf is legally binding, ensure this permission is assigned only to the appropriate personnel.
											</c:if>
										<br/>
									</s:iterator>
								</c:when>
								<c:otherwise>
								
									<s:iterator id="userList" status="inner"
										value="marketPlaceDTO.activityList">
										
										<c:if test="${marketPlaceDTO.businessPhone1 != null}"> 
											<c:set var="radioValue" value="${marketPlaceDTO.activityList[inner.index].checked}"/>											
										</c:if>
										 
									<s:checkbox
											name="isCheckedactivity_%{marketPlaceDTO.activityList[#inner.index].index}"
											value="%{#attr['radioValue']}" />
										<s:property
											value="marketPlaceDTO.activityList[#inner.index].activityName" />
											<c:if test="${marketPlaceDTO.activityList[inner.index].activityName != null && marketPlaceDTO.activityList[inner.index].activityName == 'Manage SPN Invitations'}">
											- This access allows the Team Member to accept or reject SPN invitations on behalf of Primary Administrator/Owner. The action taken on your behalf is legally binding, ensure this permission is assigned only to the appropriate personnel.
											</c:if>
										<br/>

									</s:iterator>
									
								</c:otherwise>
								</c:choose>
								</div>
							</td>
						</tr>
					</table>
				</div>

				<div class="clear"></div>
			</div>

			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">
					Communication Preferences
				</div>
				<p>
					Please enter contact information below for the service pro you are
					registering. All service providers must have at least two ways by which
					they can be reached.
				</p>
				<div style="float: left; width: 200px;">
					<c:choose>
					<c:when test="${fieldErrors['marketPlaceDTO.businessPhone1'] != null or fieldErrors['marketPlaceDTO.businessPhone2'] != null or fieldErrors['marketPlaceDTO.businessPhone3'] != null}">
					 	<p class="errorBox">
					</c:when>
					<c:otherwise>
						<p class="paddingBtm">
					</c:otherwise>
					</c:choose>
					  
					<label> Business Phone <span class="req">*</span>	</label>
					<br/>
					<s:textfield 
						name="marketPlaceDTO.businessPhone1" maxlength="3"
						cssStyle="width: 30px;" cssClass="shadowBox grayText" />
					-
					<s:textfield 
						name="marketPlaceDTO.businessPhone2" maxlength="3"
						cssStyle="width: 30px;" cssClass="shadowBox grayText" />
					-
					<s:textfield 
						name="marketPlaceDTO.businessPhone3" maxlength="4"
						cssStyle="width: 45px;" cssClass="shadowBox grayText" />
					</div>
					<div>
					<p class="paddingBtm">
					<label> Extension </label>	 <br/>
					<s:textfield 
						name="marketPlaceDTO.businessExtn" maxlength="5"
						cssStyle="width: 45px;" cssClass="shadowBox grayText" />
					<div>
				</div>
				<br clear="all" />
				<div style="width: 200px;">
					Mobile Phone
					<br />
					<s:textfield 
						name="marketPlaceDTO.mobilePhone1" maxlength="3"
						cssStyle="width: 30px;" cssClass="shadowBox grayText" />
					-
					<s:textfield 
						name="marketPlaceDTO.mobilePhone2" maxlength="3"
						cssStyle="width: 30px;" cssClass="shadowBox grayText" />
					-
					<s:textfield 
						name="marketPlaceDTO.mobilePhone3" maxlength="4"
						cssStyle="width: 45px;" cssClass="shadowBox grayText" />
				</div>
				<br clear="all" />
				<div style="float: left; width: 300px;">
					<c:choose>
					<c:when test="${fieldErrors['marketPlaceDTO.email'] == null}">
						<p class="paddingBtm">
					</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>
					</c:choose>
					<label> Primary E-mail Address <span class="req">*</span></label>
					<br />
					<s:textfield name="marketPlaceDTO.email" cssStyle="width: 250px;"
						cssClass="shadowBox grayText" />
				</div>
				<div>
					<c:choose>
					<c:when test="${fieldErrors['marketPlaceDTO.confirmEmail'] == null}">
						<p class="paddingBtm">
					</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>
					</c:choose>
					<label> Confirm Primary E-mail Address <span class="req">*</span></label>
					<br />
					<s:textfield name="marketPlaceDTO.confirmEmail" cssStyle="width: 250px;"
						cssClass="shadowBox grayText" />
				</div>
				<br clear="all" />
				<div style="float: left; width: 300px;">
					<c:choose>
					<c:when test="${fieldErrors['marketPlaceDTO.altEmail'] == null}">
						<p class="paddingBtm">
					</c:when>
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>
					</c:choose>
					<label> Alternate E-mail Address </label>
					<br />
					<s:textfield 
						name="marketPlaceDTO.altEmail" cssStyle="width: 250px;"
						cssClass="shadowBox grayText" />
				</div>
				<div>
					<c:choose>
					<c:when test="${fieldErrors['marketPlaceDTO.confirmAltEmail'] == null}">
						<p class="paddingBtm">
					</c:when> 
					<c:otherwise>
						<p class="errorBox">
					</c:otherwise>
					</c:choose>
					<label> Confirm Alternate E-mail Address </label>
					<br />
					<s:textfield 
						name="marketPlaceDTO.confirmAltEmail" cssStyle="width: 250px;"
						cssClass="shadowBox grayText" />
				</div>
				<br clear="all" />
				<c:choose>
				<c:when
					test="${marketPlaceDTO.serviceCall == null || marketPlaceDTO.serviceCall == '0' || marketPlaceDTO.serviceCall == ''}">
					<div style="float: left; width: 230px;">
						SMS Address
						<br />
						<s:textfield disabled="true" name="marketPlaceDTO.smsAddress1"
							maxlength="3" cssStyle="width: 30px;"
							cssClass="shadowBox grayText" />
						-
						<s:textfield disabled="true" name="marketPlaceDTO.smsAddress2"
							maxlength="3" cssStyle="width: 30px;"
							cssClass="shadowBox grayText" />
						-
						<s:textfield disabled="true" name="marketPlaceDTO.smsAddress3"
							maxlength="4" cssStyle="width: 45px;"
							cssClass="shadowBox grayText" />
						Optional
					</div>
					<div>
						Confirm SMS Address
						<br />
						<s:textfield disabled="true"
							name="marketPlaceDTO.confirmSmsAddress1" maxlength="3"
							cssStyle="width: 30px;" cssClass="shadowBox grayText" />
						-
						<s:textfield disabled="true"
							name="marketPlaceDTO.confirmSmsAddress2" maxlength="3"
							cssStyle="width: 30px;" cssClass="shadowBox grayText" />
						-
						<s:textfield disabled="true"
							name="marketPlaceDTO.confirmSmsAddress3" maxlength="4"
							cssStyle="width: 45px;" cssClass="shadowBox grayText" />
						Optional
					</div>
				</c:when>
				<c:otherwise>
			
				<div style="float: left; width: 300px">
			<c:choose>
			<c:when test="${fieldErrors['marketPlaceDTO.smsAddress1'] != null or fieldErrors['marketPlaceDTO.smsAddress2'] != null or fieldErrors['marketPlaceDTO.smsAddress3'] != null}">
					<p class="errorBox">
					</c:when>
					<c:otherwise>
						<p class="paddingBtm">
					</c:otherwise>
			</c:choose>
								
						SMS Address
						<br />
						<s:textfield 
							name="marketPlaceDTO.smsAddress1" maxlength="3"
							cssStyle="width: 30px;" cssClass="shadowBox grayText" />
						-
						<s:textfield 
							name="marketPlaceDTO.smsAddress2" maxlength="3"
							cssStyle="width: 30px;" cssClass="shadowBox grayText" />
						-
						<s:textfield 
							name="marketPlaceDTO.smsAddress3" maxlength="4"
							cssStyle="width: 45px;" cssClass="shadowBox grayText" /> 
							Optional
						
					</div>
					<div style="float: left; width: 300px">
		<c:choose>
		<c:when test="${fieldErrors['marketPlaceDTO.confirmSmsAddress1'] != null or fieldErrors['marketPlaceDTO.confirmSmsAddress2'] != null or fieldErrors['marketPlaceDTO.confirmSmsAddress3'] != null}">
		<p class="errorBox">
		</c:when>
		<c:otherwise>
			<p class="paddingBtm">
		</c:otherwise>
		</c:choose>
						Confirm SMS Address
						<br />
						<s:textfield 
							name="marketPlaceDTO.confirmSmsAddress1" maxlength="3"
							cssStyle="width: 30px;" cssClass="shadowBox grayText" />
						-
						<s:textfield 
							name="marketPlaceDTO.confirmSmsAddress2" maxlength="3"
							cssStyle="width: 30px;" cssClass="shadowBox grayText" />
						-
						<s:textfield 
							name="marketPlaceDTO.confirmSmsAddress3" maxlength="4"
							cssStyle="width: 45px;" cssClass="shadowBox grayText" />
					Optional	
						
					</div>
				</c:otherwise>
				</c:choose>
				<br clear="all" />
				<p>
				    Text messages are sent whenever you receive a posted Service Order from ServiceLive buyers. Frequency of text messages
		            varies based on the number of Service Orders received. Message and data rates may apply. Must be at least 18 years old to
		            participate.  
				</p>
				<!-- R16_1: SL-21236: Removing text -->
				<br clear="all" />
				<p>
					Service orders will be sent to the primary e-mail address listed
					above. If you'd also like service orders to be sent by a secondary
					contact method, please select your preference from the drop down
					menu.
				</p>
				<div style="float: left; width: 300px;">
				<c:choose>
				<c:when test="${fieldErrors['marketPlaceDTO.secondaryContact1'] != null}">
						<p class="errorBox">
					</c:when>
				<c:otherwise>
					<p class="paddingBtm">
				</c:otherwise>
				</c:choose>
					
					
						Secondary Contact Method
						<br />
						<s:select name="marketPlaceDTO.secondaryContact1"
							value="%{marketPlaceDTO.secondaryContact1}"
							list="marketPlaceDTO.secondaryContList" headerKey=""
							headerValue="-- Please Select --" listKey="id" listValue="descr"
							cssStyle="width: 256px;" cssClass="shadowBox grayText">
						</s:select>
						
				
					</div>

				<br clear="all" />
			
				<br clear="all" />
			</div>
			<div class="clearfix" id="passwordBT">
				<div class="formNavButtons">
 					<s:submit								        
						action="marketPlaceActiondoPrevious"
						cssClass="button action submit"
						theme="simple" value="Previous" onClick="setAction('Previous','marketPlaceDTO');"/>
						
						<s:submit								        
						action="marketPlaceActiondoSave"
						cssClass="button action submit"
						theme="simple" value="Save" onClick="setAction('Save','marketPlaceDTO');"/>
						
						<s:submit								        
						action="marketPlaceActiondoNext"
						cssClass="button action submit"
						theme="simple" value="Next %{adminAddEditUserDTO.jobRole}" onClick="setAction('Next','marketPlaceDTO');"/>				
					
				</div>
				<br/>
				<div class="formNavButtons">
				<c:choose>
				<c:when test="${sessionScope.userStatus=='editUser'}">
    	 		<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    	 			<s:submit								        
						action="marketPlaceActionupdateProfile"
						cssClass="button action submit"
						theme="simple" value="Update Profile" onClick="setAction('Update','marketPlaceDTO');"/>
				</c:if>								
    	 		
				
				<c:choose>  
					  <c:when test="${adminAddEditUserDTO.jobRole == '21'}"> <!-- This section is only for loggedin super Admin --> <% // Role Admin is 21 %>
						   <c:if test="${passwordResetForSLAdmin}">
							 <c:choose>
							  <c:when test="${adminAddEditUserDTO.neverLoggedIn == true}">
							    <s:submit								        
								action="marketPlaceActiondoWelcome"
								cssClass="button action submit"
								theme="simple" value="Resend Registration" onClick="setAction('Welcome','marketPlaceDTO');"/>
							  </c:when>
							  <c:otherwise>
							    <s:submit								        
								action="marketPlaceActiondoReset"
								cssClass="button action submit"
								theme="simple" value="Reset Password" onClick="setAction('Reset','marketPlaceDTO');"/>
							  </c:otherwise>
							 </c:choose>
						    </c:if>
					  </c:when>
					  <c:otherwise> <% // This section is for loggedin SL/Provider Admin  %>
						  <c:if test="${passwordResetForAllExternalUsers}">
							 <c:choose>
							  <c:when test="${adminAddEditUserDTO.neverLoggedIn == true}">
							    <s:submit								        
								action="marketPlaceActiondoWelcome"
								cssClass="button action submit"
								theme="simple" value="Resend Registration " onClick="setAction('Welcome','marketPlaceDTO');"/>
							  </c:when>
							  <c:otherwise>
							    <s:submit								        
								action="marketPlaceActiondoReset"
								cssClass="button action submit"
								theme="simple" value="Reset Password" onClick="setAction('Reset','marketPlaceDTO');"/>
							  </c:otherwise>
							 </c:choose>
						    </c:if>
					   </c:otherwise>									  
					</c:choose>				
      			
      			</c:when>
      			<c:otherwise>
      			<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="marketPlaceActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitRegistration.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onClick="setAction('Update','marketPlaceDTO');">
				</s:submit>
      			</c:if>
      			</c:otherwise>
      			</c:choose>
				</div>
				<div class="bottomRightLink">
					<a 	href="javascript:cancelMarketPlace('jsp/providerRegistration/marketPlaceAction!doCancel.action');">
						Cancel</a>
				</div>
			</div>
		</s:form>
		<br/>
				
	</body>
</html>

