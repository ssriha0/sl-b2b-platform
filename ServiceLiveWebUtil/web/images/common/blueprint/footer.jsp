<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div id="footer" class="span-24 clearfix">


	<ul class="landscape">
		<li><a href="${contextPath}/jsp/public/common/footer/terms_of_use.jsp">Terms of Use</a> </li>
		<li><a href="https://transformco.com/privacy" target="_blank">Privacy Policy</a> </li>
		<li><a href="termsAndConditions_displayProviderAgreement.action">Provider Agreement</a> </li>
		<li><a href="termsAndConditions_displayBuyerAgreement.action">Buyer Agreement</a></li>
	</ul>
	<p class="copyright">
		ServiceLive is a Transform Holdco Company. &copy; 2019 Transform ServiceLive LLC. | v 3.0
	</p>

	<s:if test="${not empty blackoutStates}">
	<p class="blackout">
		<strong>We are unable to fulfill buyer requests in the following states/U.S. Territories: </strong>
		<s:iterator value="blackoutStates" status="statePos">
			<s:property/><s:if test="!#statePos.last">,</s:if>
		</s:iterator>						
	</p>
	</s:if>
	
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
</c:if>