<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<!-- acquity: modified meta tag to set charset -->
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

		<title>ServiceLive - Additional Info</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />



		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"></script>

		<script language="javascript" type="text/javascript">
			var djConfig = {
				isDebug: true, 
				parseOnLoad: true
			};
		</script>

		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>

		<!-- acquity: escape script tags with a forward slash, make sure they all have relationship and type set -->
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />

		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />

		<!-- acquity: here is the new stylesheet, rename to whatever you'd like -->
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<!-- acquity: make sure they all have language and type set -->
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>


		<!-- acquity: here is the new js, please minify/pack the toolbox and rename as you wish -->
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/vars.js"></script>


		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/mootools.v1.11.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>


	</head>
	<body class="tundra acquity">
       
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="OFAC.additionalInfoRequired"/>
		</jsp:include>	

			<div id="page_margins">
				<div id="page">

					<!-- START HEADER -->

					<div id="headerSPReg">
						<tiles:insertDefinition name="newco.base.topnav" />
						<tiles:insertDefinition name="newco.base.blue_nav_blank" />
						<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					</div>

					<!-- END HEADER -->

					<!--  START BODY -->

					<s:form action="loginAdditionalInfo_buttonSubmit"
						id="loginAdditionalInfo_buttonSubmit"
						name="loginAdditionalInfo_buttonSubmit" method="post"
						enctype="multipart/form-data" theme="simple">

					<table width="500px" border=0>
										
					
						<tr>
							<td colspan=2>
								<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />
							</td>
						</tr>
						<tr>
							<td colspan=2>
								We need further information to complete your profile.
							</td>
						</tr>
						<tr>
							<td height=20px colspan=2>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td align="right">
								<b>Taxpayer ID (EIN Or SSN): 
							</td>
							<td align="left">
								<s:textfield id="taxNumber" name="taxNumber" maxLength="9" theme="simple" />
							</td>
						</tr>
						<tr>
							<td height=20px colspan=2>
								&nbsp;
							</td>
						</tr>
						
						<tr>
							<td colspan=2>
								<b>Date of Birth (Account Administrator Only):</b>
								<br />



							</td>
						</tr>
						<tr>
							<td align="right">
								Day:
							</td>
							<td align="left">
							 
								<s:select list="dayOptions" headerKey="-1"
									headerValue="Select Day" listKey="value" listValue="label"
									theme="simple" cssStyle="width: 100px;" cssClass="grayText"
									id="selectedDay" name="selectedDay" />
								

							</td>
						</tr>
						<tr>
							<td align="right">
								Month:
							</td>
							<td align="left">
								
								<s:select list="monthOptions" headerKey="-1"
									headerValue="Select Month" listKey="value" listValue="label"
									theme="simple" cssStyle="width: 100px;" cssClass="grayText"
									id="selectedMonth" name="selectedMonth" />
									
							</td>
						</tr>
						<tr>
							<td align="right">
								Year:
							</td>
							<td align="left">
								<s:textfield id="year" maxLength="4" name="year" theme="simple" />
							</td>
						</tr>

						<tr>
							<td height=20px colspan=2>
								&nbsp;
							</td>
						</tr>
						<tr>
							<td>
								<s:submit type="input"
									cssStyle="background-image: url(${staticContextPath}/images/btn/submit.gif);width:72px; height:20px;"
									cssClass="btn20Bevel" method="" theme="simple" value="" />
							</td>
							<td>
								<a href="doLogout.action">cancel and logout</a>
							</td>
						</tr>
					</table>

					<!--  START BODY -->

				</s:form>

				</div>
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->

	</body>
</html>
