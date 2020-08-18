<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div id="footer" class="span-24 clearfix" style="height:85px">

	<div class="footericons" >
		<%@ include file="/html/BBB_Image.html"%>
                                                        		
						
						<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
						<a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
                            <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;position:relative;top:3px;"><br>
                        </a><br>
						<!--<a title="ServiceLive is Secured by Verisign" target="_blank" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=${ref}&lang=en" class="verisign" ><img title="ServiceLive is Secured by Verisign" border="0" src="${staticContextPath}/images/common/veriSign.jpg" alt="ServiceLive is Secured by Verisign" /></a> -->
	</div>


	<ul class="landscape">
		<li><a href="${contextPath}/jsp/public/common/footer/terms_of_use.jsp">Terms of Use</a> </li>
		<li><a href="https://transformco.com/privacy" target="_blank">Privacy Policy</a> </li>
		<li><a href="${contextPath}/termsAndConditions_displayProviderAgreement.action">Provider Agreement</a> </li>
		<li><a href="${contextPath}/termsAndConditions_displayBuyerAgreement.action">Buyer Agreement</a></li>
		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && providerLeadManagementPermission == 'true' && newLeadsTCIndicator != null && showLeadsSignUp == 1}">
			<li><a href="${contextPath}/termsAndConditions_displaySLLeadsAddendum.action">SL Leads Addendum</a></li>
		</c:if> 
	</ul>
	<p class="copyright">
		ServiceLive is a Transform Holdco Company. &copy; <c:import url="/jsp/public/common/copyrightYear.jsp" /> Transform ServiceLive LLC.
	</p>

	<c:if test="${not empty blackoutStates}">
	<p class="blackout">
		<strong>We are unable to fulfill buyer requests in the following states/U.S. Territories: </strong>
		<s:iterator value="blackoutStates" status="statePos">
			<s:property/><s:if test="!#statePos.last">,</s:if>
		</s:iterator>						
	</p>
	</c:if>
	
</div>
<c:if test="${showTags == 1}">
<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
<!-- Activity Name for this tag is:ServiceLive-Universal-Tag -->
<!-- Web site URL where tag should be placed: http://www.servicelive.com  Global Footer-->
<!-- This tag must be placed within the opening <body> tag, as close to the beginning of it as possible-->
<!-- Creation Date:12/4/2008 -->
<SCRIPT language="JavaScript">
var axel = Math.random()+"";
var a = axel * 10000000000000;
document.write('<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num='+ a + '?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>');
</SCRIPT>
<NOSCRIPT>
<IFRAME SRC="<%=request.getScheme()%>://fls.doubleclick.net/activityi;src=2077836;type=landi583;cat=servi443;ord=1;num=1?" WIDTH=1 HEIGHT=1 FRAMEBORDER=0></IFRAME>
</NOSCRIPT>
<!-- End of DoubleClick Spotlight Tag: Please do not remove-->

<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
	</script>
<script type="text/javascript">
		var pageTracker = _gat._getTracker("UA-2653154-6");
		pageTracker._initData();
		pageTracker._trackPageview();
	</script>


</c:if>