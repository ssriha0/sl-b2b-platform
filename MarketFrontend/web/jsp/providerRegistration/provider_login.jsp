<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ServiceLive [Welcome to ServiceLive]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dojo.parser");
		dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
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
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/css/buttons.css" />
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
</head>
<body class="tundra">

<div id="page_margins">
   <div id="page" class="clearfix">
    <!-- BEGIN HEADER -->
    <div id="headerSPReg">
		<tiles:insertDefinition name="newco.base.topnav"/>
		<tiles:insertDefinition name="newco.base.blue_nav"/>		
		
	  <jsp:include flush="true" page="header/reg_hdr_welcome.jsp"></jsp:include>
		
     </div>
    <!-- BEGIN RIGHT PANE -->
    <div class="colRight255 clearfix"><img src="${staticContextPath}/images/images_registration/sp_firm_registration/promoDidYouKnow_latin.gif" alt="Did You Know" title="Did You Know" /></div>
    <div class="colLeft711">
    <div id="content_right_header_text">
    	<%@ include file="message.jsp"%>
    </div>
    
      <div class="content">
      <form action="<s:url action="doLogin" scheme="https"/>"  method="post" name="doLogin">
      
      <h3>Please log in to finish building your profile and complete your registration.</h3>
        <p>You've been assigned a temporary password that you can use to log into ServiceLive to finish building your provider firm's profile. Once you create a profile for at least one service pro, your company will be able to accept and manage service orders online</p>
       	
       	<c:choose><c:when test="${fieldErrors.username == null}"> 
       		<p class="paddingBtm">
       	</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise></c:choose>
       	<label>User Name</label>
        <br>
		<s:textfield name="username" key="login.username" cssClass="shadowBox grayText" cssStyle="width: 240px;" onfocus="clearTextbox(this)"></s:textfield>
		</p>
        <c:choose><c:when test="${fieldErrors.password == null}"> 
       		<p class="paddingBtm">
       	</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise></c:choose>
         <label>Temporary Password</label><br>
         <s:password name="password" cssClass="shadowBox grayText" cssStyle="width: 240px;" onfocus="clearTextbox(this)"/>
         </p>
        <p>
          <s:submit type="image" value="" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/login.gif);width:56px;height:20px;"  cssClass="btn20Bevel" />
          
        </p>
        </form>
      </div>
    </div>
  </div>
  <div class="clear"></div>
  <!-- START FOOTER -->
	<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
  <!-- END FOOTER -->
</div>
</body>
</html>
