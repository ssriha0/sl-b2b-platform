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

<div id="content_right_header_text">
    <%@ include file="../message.jsp"%>
</div>

<p class="paddingBtm">If your company has licenses or certifications, upload them here. These credentials will allow you to accept orders on projects that require licenses and certifications. Once uploaded, your credentials may be verified by the ServiceLive team.  </p>
<!-- NEW MODULE/ WIDGET-->
<s:form action="/doLicenses.action?type=save" method="POST" theme="simple">
<div class="darkGrayModuleHdr">Licenses &amp; Certifications on File</div>
<div class="grayModuleContent mainContentWell">
	
	  	<p>Your company does not have any licenses or certifications on file.</p>	  	
	  	<p><s:checkbox label="I do not wish to add any licenses or certifications at this time." name="noCredIndString" value="%{noCredIndString}"/></p>	  	
		<p class="paddingBtm"> 
		<s:a href="allTabView.action?tabView=tab3&nexturl=licensesAndCertActiondoLoad.action" cssClass="btn20Bevel"> 
		<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="107" height="20" style="background-image:url(${staticContextPath}/images/images_registration/btn/addCredential.gif);" class="btn20Bevel"/>

		</s:a>
		</p>

</div>

<div class="clearfix">
  <div class="formNavButtons2">
  
	<s:submit value="" action="doLicensesdoPrev" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/previous.gif);width:72px;height:20px;"  cssClass="btn20Bevel" />
  	<s:submit value="" action="doLicensesdoNext" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/next.gif);width:50px;height:20px;"  cssClass="btn20Bevel" />
  	
	 
</div></div>
</s:form>


