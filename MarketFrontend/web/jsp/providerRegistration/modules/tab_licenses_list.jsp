<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
 @import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/utils.js" type="text/javascript"></script>
<div id="content_right_header_text">
    <%@ include file="../message.jsp"%>
</div>
<s:form action="licensesAndCertAction" method="POST" theme="simple" id="licensesAndCerts">
<br/>
If your company has licenses or certifications, upload them here. These credentials will allow you to accept orders on projects 
that require licenses and certifications. Once uploaded, your credentials may be verified by the ServiceLive team.
<br/>
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>

<div class="darkGrayModuleHdr">Licenses &amp; Certifications on File</div>
<div class="grayModuleContent mainContentWell">
<b>Licenses & Certifications</b><span class="req">*</span><br/>

<s:if test="%{licensesAndCertDto.size !=0}">
  <table class="scrollerTableHdr licensesTableHdr" cellpadding="0" cellspacing="0" style="width:687px;">
    <tr>
      <td class="column1"><img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="60" height="20" title="spacer" /></td>
      <td class="column2"> Credential Type </td>
      <td class="column3"> Name </td>
      <td class="column4"> Expiration </td>
      
      <td class="column5">Verified by ServiceLive</td>
    </tr>
  </table>
  <table class="licensesTable" cellpadding="0" cellspacing="0" style="table-layout:fixed; width:685px;">
   <s:iterator value="licensesAndCertDto.credentials">
    <tr>
       <!-- SL-21142 -->
     <c:choose>
		<c:when test="${securityContext.slAdminInd==true || !(isFileUploaded) || isFileUploaded==null}">
			 <td class="column1" style="word-wrap:break-word">
      <!--  
      <s:url id="listUrl" action="licensesAndCertActiondoLoad.action">
  		<s:param name="id" value="%{vendorCredId}" />
  	  </s:url>
  	  -->
		<s:a href="allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action?id=%{vendorCredId}" theme="simple"> 
    		<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="48" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/edit.gif);"  class="btn20Bevel" /></td>
    	</s:a>
		</c:when>
		<c:otherwise>
				<td class="column1" style="word-wrap:break-word"></td>
		</c:otherwise>
	</c:choose>
    	<!--  SL-21142-->
      <td class="column2" style="word-wrap:break-word"><s:property value="credTypeDesc"/></td>
      <td class="column3" style="word-wrap:break-word"><s:property value="licenseName"/></td>
      <td class="column4" style="word-wrap:break-word"><s:property value="expirationDate"/></td>
      <td class="column5" style="word-wrap:break-word"><s:property value="marketPlaceInd"/></td>
    </tr>
    </s:iterator>
    </table>
</s:if>
<s:else>
	    <p>Your company does not have any licenses or certifications on file.</p>	
		<p><s:checkbox onclick="javascript:checkCredential(this.form);" label="I do not wish to add any licenses or certifications at this time." name="noCredIndString" value="%{noCredIndString}" />I do not wish to add any licenses or certifications at this time.</p>
		<input type="hidden" name="hiddenCheck" value="true">
</s:else>
   <p>
   	<s:if test="%{noCredIndString == 'false'}">
    	<s:a href="allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action" theme="simple">
    	<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="103" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/addCredential.gif);"class="btn20Bevel" />
    	</s:a>
    </s:if>
    <s:else>
    	<img onclick="return false;" src="${staticContextPath}/images/images_registration/common/spacer.gif" width="103" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/addCredential.gif);"class="btn20Bevel" />
    </s:else>
    </p>

</div>
<div class="clearfix">
  <div class="formNavButtons2">
  
<!-- javascript function assignAction()  defined in utils.js page for Next and Previous buttons -->
	<s:submit value="" action="licensesAndCertActiondoPrevLicenses" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/previous.gif);width:72px;height:20px;"  cssClass="btn20Bevel" onclick="assignAction('Prev')"/>
  	<s:submit value="" action="licensesAndCertActiondoNextLicenses" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/next.gif);width:50px;height:20px;"  cssClass="btn20Bevel" onclick="assignAction('Next')"/>
</div>

</div>
</s:form>
<br/>
	 
