<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- Omniture tag - do not remove --%>
<c:import url="/jsp/public/common/omitInclude.jsp"><c:param name="PageName" value="HomePage"/></c:import>


<c:if test="${showTags == 1}">
	<!-- Start of DoubleClick Spotlight Tag: Please do not remove-->
	<!-- Activity Name for this tag is:ServiceLive-Universal-Tag -->
	<!-- Web site URL where tag should be placed: http://www.servicelive.com  Global Footer-->
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

<%-- Google Analytics - do not remove --%>	
<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
	</script>
<script type="text/javascript">
		var pageTracker = _gat._getTracker("UA-2653154-6");
		pageTracker._initData();
		pageTracker._trackPageview();
	</script>
<%-- Do not remove the above script --%>
</c:if>