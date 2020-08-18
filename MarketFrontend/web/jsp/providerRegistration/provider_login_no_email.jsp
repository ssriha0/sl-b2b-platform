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
		<title>ServiceLive [Service Pro Profile]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
			href="${staticContextPath}/css/modules.css" />
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
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"
			type="text/javascript"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"
			type="text/javascript"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/nav.js"
			type="text/javascript"></script>

	</head>
	<body class="tundra"><br><br>
	
		<s:form action="loginAction" theme="simple">
			<div id="page_margins">
				<div id="page">
					<!-- START HEADER -->
					<div id="headerShort-rightRail">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />										
					
						<div id="rightRail">
							<jsp:include flush="true" page="/jsp/public/homepage/body/modules/corpHQ_top.jsp"></jsp:include>							
							<div class="content">
							</div>
						</div>
					</div>
					<!-- END HEADER -->
					<div class="colRight255 clearfix">
					</div>
					<div class="colLeft711" style="padding-bottom: 50px;">
						<div class="content">
							<h4>
								That e-mail address is not in our system
							</h4>
							<p>
								Lorem ipsum dolor sit amet, consectetuer adipiscing elit.
								Phasellus tempor justo et elit. Nulla eget quam. Quisque id leo
								et lacus molestie semper. Mauris sollicitudin, tellus vel
								posuere tincidunt, lectus leo suscipit orci, in consectetuer mi
								nisi sit amet nisi. Sed non libero. Phasellus vulputate dui eget
								sapien. Sed sed tellus. Duis risus sapien, feugiat condimentum,
								scelerisque vel, vestibulum eget, purus. Mauris eu dui.
								Curabitur nulla diam, sagittis in, tincidunt sit amet, volutpat
								eu, neque. Sed in ipsum quis nibh tempus tempor. Suspendisse
								potenti.
								<a href="">Made a mistake? Please try again."</a>
							</p>
						</div>
						<!--
                <div id="landing_subhead">
                	Quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Nam liber tempor cum soluta.
                </div>
                -->	
                	<p align="center">
						<%
                			String forwParam = request.getParameter("forward");
                			if (forwParam != null && forwParam.equalsIgnoreCase("forgotUsername"))
                			{%>
		                	<s:a href="join_now.jsp"> 
								<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="69" height="26" style="background-image:url(${staticContextPath}/images/images_registration/btn/btn_join_now.gif);" class="btn26Bevel"/>
							</s:a>
						<% 	}else{
								if (forwParam != null && forwParam.equalsIgnoreCase("forgotPassword"))
								{%>
								 	<s:a href="provider_login.jsp"> 
										<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="69" height="26" style="background-image:url(${staticContextPath}/images/images_registration/btn/btn_join_now.gif);" class="btn26Bevel"/>
									</s:a>
							  <%}else{
							 %>	
							 		<s:a href="join_now.jsp"> 
										<img src="${staticContextPath}/images/images_registration/common/spacer.gif" width="69" height="26" style="background-image:url(${staticContextPath}/images/images_registration/btn/btn_join_now.gif);" class="btn26Bevel"/>
									</s:a>
							<%} //End of second IF
							} //End of First IF%>	
						
					</p>

						<div class="landingContent">
							<div class="landingBucket marginRight">
								<img
									src="${staticContextPath}/images/icons/serviceProviders.gif"
									width="230" height="30" alt="">
								<div class="landing_cell_text">
									ServiceLive can help you maximize profitability while
									minimizing advertising expenses. Log on when work is slow to
									connect with homeowners and other service buyers who need your
									specific set of skills. Accept work when you need it, pass on
									it when you don't.
								</div>
								<ul class="inlineList bucketLinks clearfix">
									<li>
										<a href="">> Join Now</a>
									</li>
									<li>
										<a href="">> Learn More</a>
									</li>
								</ul>
							</div>

							<div class="landingBucket marginRight">
								<img
									src="${staticContextPath}/images/icons/homeowners.gif"
									width="230" height="29" alt="">
								<div class="landing_cell_text">
									ServiceLive is a one-stop source for finding service providers
									who come highly recommended by other members of the ServiceLive
									community. Fill out a service order and we'll recommend
									providers who can do the job on your schedule and within your
									budget.
								</div>
								<ul class="inlineList bucketLinks clearfix">
									<li>
										<a href="">> Join Now</a>
									</li>
									<li>
										<a href="">> Learn More</a>
									</li>
								</ul>
							</div>
							<div class="landingBucket">
								<img
									src="${staticContextPath}/images/icons/Businesses.gif"
									width="230" height="29" alt="">
								<div class="content">
									Whether you own a small business or are a services buyer for a
									large corporation, ServiceLive can help you find service
									providers who have background checks, insurance and the exact
									set of skills you need. Hire the workforce you need as you need
									them.
									<ul class="inlineList bucketLinks clearfix">
										<li>
											<s:a href="providerRegistrationAction.do">>
											 	Join Now
											</s:a>
										</li>
										<li>
											<a href="">> Learn More</a>
										</li>
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="clear"></div>
				</div>
				<!-- START FOOTER -->
				<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
				<!-- END FOOTER -->
			</div>
		</s:form>
	</body>
</html>
