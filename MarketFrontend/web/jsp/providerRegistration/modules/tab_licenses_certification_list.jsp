<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

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
<title>ServiceLive [Provider Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
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
		</script>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
 @import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_provider_profile.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/formfields.js"></script>

<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="/js/nav.js"></script>
</head>
<s:form action="teamCredentialAction" theme="simple">
<s:hidden name="teamCredentialsDto.size" />
<s:hidden id="teamCredentialsDtoAction" name="marketPlaceDTO.action" value=""></s:hidden>
<body class="tundra">
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
         <jsp:param name="PageName" value="Provider Registration - Add/Edit User - Licenses and Certification "/>
</jsp:include>
    <!-- START HEADER -->
    <!-- END HEADER -->
    <style>
	 .mngProvUsersHdr { width: 668px; }
	 .mngProvUsersHdr td { padding: 5px 0; }
	 .mngProvUsers { width: 648px; }
	 .mngProvUsersHdr .column1, .mngProvUsers .column1 { width: 56px; padding-left: 14px; }
	 .mngProvUsersHdr .column2, .mngProvUsers .column2 { width: 178px; }
	 .mngProvUsersHdr .column3, .mngProvUsers .column3 { width: 80px; text-align: center  }
	 .mngProvUsersHdr .column4, .mngProvUsers .column4 { width: 85px; text-align: center }
	 .mngProvUsersHdr .column5, .mngProvUsers .column5 { width:85px; text-align: center }
	 .mngProvUsersHdr .column6, .mngProvUsers .column6{ width:85px; text-align: center }
	 .mngProvUsersHdr .column7, .mngProvUsers .column7 { width:65px; text-align: center }
	 .mngProvUsersHdr .column7 { padding-right: 20px }
	</style>

	<div id="content_right_header_text">
		<%@ include file="../message.jsp"%>
	</div>
	
	<h3 class="paddingBtm">
	<s:property value="teamCredentialsDto.resourceName"/>
	(User Id# <s:property value="teamCredentialsDto.resourceId"/>)
	</h3>
	<h4>Individual Licenses &amp; Certifications</h4>
		<p>While they are not always required, licenses and certifications can give your service pro a competitive
 		 advantage by showing <br/>that he or she has taken advanced training or has government authorizations. Add as
 		 many credentials as you would like or<br/> click 'next' to move to the next page. </p>
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
		<!-- NEW MODULE/ WIDGET-->
		<div class="darkGrayModuleHdr">Licenses &amp; Certifications on File</div>
<div class="grayModuleContent mainWellContent clearfix">
  <b>Licenses & Certifications</b><span class="req">*</span><br/>
<s:if test="%{teamCredentialsDto.size !=0}">
 <p>The credentials you've uploaded are listed below. Click 'add credential' to add more or 'edit' to make changes to the,<br/> credentials you have listed. Certifications that have been verified by ServiceLive are indicated in the right column. </p>
  <table class="scrollerTableHdr licensesTableHdr" cellpadding="0" cellspacing="0">
    <tr>
      <td class="column1"><img src="${staticContextPath}/images/common/spacer.gif" width="60" height="20" title="spacer" /></td>
      <td class="column2">Credential Type</td>
      <td class="column3">Name</td>
      <td class="column4">Expiration</td>
      <td class="column5">Verified by ServiceLive</td>
    </tr>
  </table>
  <table class="licensesTable" cellpadding="0" cellspacing="0">
	<s:iterator value="teamCredentialsDto.credentialsList" status="status">
		 <s:url action="teamCredentialAction!loadCredentialDetails" id="url">
			<s:param name="resourceCredId" value="resourceCredId"/>
			<s:param name="nexturl" value="teamCredentialAction!loadCredentialDetails.action"/>
		</s:url>
			<tr>
			  <!-- SL-21142 -->
			     <c:choose>
					<c:when test="${securityContext.slAdminInd==true || !(isFileUploaded) || isFileUploaded==null}">
						 <td class="column1"><input type="image" src="${staticContextPath}/images/common/spacer.gif" width="48" height="20" style="background-image:url(${staticContextPath}/images/btn/edit.gif);"  class="btn20Bevel" onclick="javascript:editCredentials('<s:property value='url'/>')"/></td>
					</c:when>
					<c:otherwise>
							<td class="column1"></td>
					</c:otherwise>
				</c:choose>
    	<!--  SL-21142-->
			  <td class="column2"><s:property value="credType"/></td>
			  <td class="column3"><s:property value="licenseName"/></td>
			  <td class="column4"><s:property value="expirationDate"/></td>
			  <td class="column5">
				<s:if test="%{wfStateId =='11'}">Pending Approval
					<!--<img src="${staticContextPath}/images/icons/hourglass.bmp" width="10" height="10" alt="">-->
				</s:if>
		        <s:elseif test="%{wfStateId =='12'}">
		            		<img src="${staticContextPath}/images/icons/greenCheck.gif" width="10" height="10" alt="">
		        </s:elseif>
		        <s:elseif test="%{wfStateId =='24'}"> Out of Compliance</s:elseif>
				<s:elseif test="%{wfStateId =='200'}">Reviewed</s:elseif>
			 </td>
			</tr>
	</s:iterator>
  </table>
  <input type="hidden"  name="checkboxExists" value="false"/>
  <p>
 	<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="103" height="20" style="background-image:url(${staticContextPath}/images/btn/addCredential.gif);"class="btn20Bevel" onclick="javascript:addCredentials('teamCredentialAction!loadCredentialDetails.action')"/>
  </p>
 </s:if>
	<s:else>
	<p>This service pro does not have any licenses or certifications on file.</p>
	 <input type="hidden"  name="checkboxExists" value="true"/>
		<s:if test="%{!teamCredentialsDto.passCredentials}">
		  <p>
			<input type="checkbox"  name="chkDonotWant" onclick="javascript:checkTeamCredential(this.form);"/>
			I do not wish to add any licenses or certifications at this time.</p>
		 </s:if>
		 <s:else>
		  <p>
			<input type="checkbox"  name="chkDonotWant" onclick="javascript:checkTeamCredential(this.form);" checked/>
			I do not wish to add any licenses or certifications at this time.</p>
		 </s:else>
		 <p>
		 	<s:if test="%{!teamCredentialsDto.passCredentials}">
		 		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="103" height="20" style="background-image:url(${staticContextPath}/images/btn/addCredential.gif);"class="btn20Bevel" onclick="javascript:addCredentials('teamCredentialAction!loadCredentialDetails.action')"/>
		    </s:if>
		    <s:else>
				<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="103" height="20" style="background-image:url(${staticContextPath}/images/btn/addCredential.gif);"class="btn20Bevel" onclick="return false;"/>		    	
		    </s:else>
		 </p>
  </s:else>
  <div class="clear"></div>
</div>
<div class="clearfix">
  <div class="formNavButtons">
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="74" height="20" style="background-image:url(${staticContextPath}/images/btn/previous.gif);"class="btn20Bevel" onclick="javascript:preCredentilList();"/>
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="51" height="20" style="background-image:url(${staticContextPath}/images/btn/next.gif);"class="btn20Bevel" onclick="javascript:nextCredentilList();"/>
  </div>
  <br/>
  <div class="formNavButtons">
  <c:choose>
  <c:when test="${sessionScope.userStatus=='editUser'}">
    	 		<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="teamCredentialActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/updateProfile.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onClick="setAction('Update','teamCredentialsDtoAction');">
				</s:submit>
				
   </c:if>
  </c:when>
  <c:otherwise>
   			<c:if test="${sessionScope.resourceStatus == 'complete' and sessionScope.providerStatus == 'complete'}">
    			<s:submit action="teamCredentialActionupdateProfile" type="image"   
    			src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" 
     			cssStyle="background-image:url(%{#request['staticContextPath']}/images/btn/submitRegistration.gif);width:120px;height:20px;"
				cssClass="btn20Bevel" onClick="setAction('Update','teamCredentialsDtoAction');">
				</s:submit>
			
   			</c:if>
    </c:otherwise>
   </c:choose>
  </div>
</div>

  <!-- END FOOTER -->
</body>
</s:form>
<br/>

</html>
