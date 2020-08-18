<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<title>ServiceLive [Buyer Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
<script language="javascript">
	selectedNav = function (){ $("mktplaceTools").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
</head>
<c:choose>
	<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
		<body class="tundra acquity simple" onload="${onloadFunction}">
	</c:when>
	<c:otherwise>
		<body class="tundra acquity" onload="${onloadFunction}">
	</c:otherwise>
</c:choose>
   
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="Admin - Audit Insurance"/>
	</jsp:include>	


<div id="page_margins">
  <div id="page">
    <!-- START HEADER -->
	<div id="headerSPReg">
		<tiles:insertDefinition name="newco.base.topnav"/>
		<tiles:insertDefinition name="newco.base.blue_nav"/>
		<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
		
      <div id="pageHeader">
        <div><img src="${staticContextPath}/images/sl_admin/hdr_mktplace_auditableItems.gif" width="274" height="17" alt="Marketplace | Auditable Items" title="Marketplace | Auditable Items" /></div>
      </div>
    </div>
    <!-- END HEADER -->
    <div class="colRight255 clearfix">
	<jsp:include page="/jsp/admin/modules/member_info.jsp" />				
	<jsp:include page="/jsp/admin/modules/admin_actions.jsp" />				
      <!-- #INCLUDE file="html_sections/modules/auditor_actions.jsp" -->
      </div>
        </div>
    </div>
    <div class="colLeft711">
      <div class="content">
        
        <p>Consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna
aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit
lobortis nisl ut aliquip ex ea commodo consequat.</p>
        <div class="darkGrayModuleHdr">Audit Insurance Policy</div>
        <div class="grayModuleContent mainWellContent clearfix">
          <table cellpadding="0" cellspacing="0">
            <tr height="35">
              <td colspan="2"> Policy Category<br />
                <select class="grayText" onclick="changeDropdown(this)"  style="width:208px;">
                  <option value="">General Liability</option>
                </select>
              </td>
              <td> Policy Number<br />
                <input type="text" style="width: 200px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[Select file]" />
              </td>
            </tr>
            <tr height="35">
              <td colspan="2"> Carrier Name<br />
                <input type="text" style="width: 200px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[Carrier Name]" />
              </td>
              <td> Agency Name<br />
                <input type="text" style="width: 200px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[Agency Name]" />
              </td>
            </tr>
            <tr height="35">
              <td width="80"> Agency State<br />
                <select class="grayText" onclick="changeDropdown(this)"  style="width:70px;">
                  <option value="">AL</option>
                </select>
              </td>
              <td width="160"> Agency County<br />
                <input type="text" style="width: 120px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[County]" />
              </td>
              <td> Amount $ (Each Occurence)<br />
                <input type="text" style="width: 200px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[###.##]" />
              </td>
            </tr>
            <tr height="35">
              <td colspan="2"> Issue Date<br />
                <input type="text" style="width: 100px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[MM/DD/YY]" />
              </td>
              <td> Expiration Date (if applicable)<br />
                <input type="text" style="width: 100px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[MM/DD/YY]" />
              </td>
            </tr>
          </table>
        </div>
        <div class="darkGrayModuleHdr">Review Insurance Document</div>
        <div class="grayModuleContent mainWellContent clearfix">
          <p>Please upload proof of insurance documentation. You may remove any policies you add during this
            session, but you may not remove any previously uploaded files. A maximum of two files may be
            uploaded. Individual file size is limited to 2 MB.</p>
          <div>
            <div style="float: left;">
              <p>
                <label>Select file to upload</label>
                <br />
                <input type="text" style="width: 170px;" class="shadowBox grayText" onfocus="clearTextbox(this)" value="[Select file]" />
                <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/browse.gif);"class="btnBevel uploadBtn" />
                <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="61" height="20" style="background-image:url(${staticContextPath}/images/btn/attach.gif);"class="btn20Bevel uploadBtn" />
              </p>
              <br clear="all" />
              <div style="float: left;width:400px;">
                <table style="border:1px #9f9f9f solid;" cellpadding="0" cellspacing="0" width="378">
                  <tr style="background:#4cbcdf;color:#ffffff;height:22px;">
                    <td style="padding:5px 0px 0px 10px;" width="275"><strong>File Name</strong></td>
                    <td style="padding:5px 0px 0px 0px;"><strong>File Size</strong></td>
                  </tr>
                  <tr>
                    <td style="padding:10px 0px 10px 10px;"><input type="checkbox" /> Policy01.pdf</td>
                    <td style="padding:10px 0px 10px 0px;">725kb</td>
                  </tr>
                </table>
              </div>
              <div class="filetypes" style="margin-right: 90px; _margin-right: 45px; ">
                <p>Preferred file types include:<br />
                  JPG | PDF | DOC | GIF</p>
              </div>
              <br clear="all" />
              <br />
              <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/viewOff.gif);"  />
              <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" style="background-image:url(${staticContextPath}/images/btn/removeOff.gif);"  />
            </div>
          </div>
        </div>
        <div class="darkGrayModuleHdr">Minimum Requirements for Insurance</div>
        <div class="grayModuleContent mainWellContent clearfix">
          <p>Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea
            commodo consequat.</p>
          <p> <strong>General Liability Insurance</strong><br />
            Consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat
            volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip
            ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie
            consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui
            blandit praesent luptatum. </p>
          <p> <strong>Worker's Compensation Insurance</strong><br />
            Consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat
            volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip
            ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie
            consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui
            blandit praesent luptatum. </p>
          <p> <strong>Automobile Insurance</strong><br />
            Consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat
            volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip
            ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie
            consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui
            blandit praesent luptatum. </p>
        </div>
        <div class="clearfix">
          <div class="formNavButtons">
            <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="96" height="20" style="background-image:url(${staticContextPath}/images/btn/updatepolicy.gif);"class="btn20Bevel" />
            <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="96" height="20" style="background-image:url(${staticContextPath}/images/btn/removepolicy.gif);"class="btn20Bevel" />
          </div>
        </div>
      </div>
    </div>
    <div class="colRight255 clearfix"></div>
    <div class="clear">
    </div>
  <!-- START FOOTER -->
  	<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
  <!-- END FOOTER -->
</body>
</html>
