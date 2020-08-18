<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

		</div>
			<div id="theFooter">
				<div id="footer">
					<div class="footericons">
						<%@ include file="/html/BBB_Image.html"%>
                                                        
						
						
						<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
						<a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
           				  <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;position:relative;top:3px;">
           				 </a><br>
						<!-- <a title="ServiceLive is Secured by Verisign" target="_blank" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=${ref}&lang=en" class="verisign" ><img title="ServiceLive is Secured by Verisign" border="0" src="${staticContextPath}/images/common/veriSign.jpg" alt="ServiceLive is Secured by Verisign" /></a> -->
					</div>
					<p class="lightBlue">
						<a href="${contextPath}/jsp/public/common/footer/terms_of_use.jsp">Terms
							of Use</a>
						<span class="PIPES">|</span>
						<a href="https://transformco.com/privacy" target="_blank">Privacy
							Policy</a>
						<span class="PIPES">|</span>
						<a href="https://transformco.com/privacy#_Toc31123888" target="_blank">California Privacy
							Policy</a>
						<span class="PIPES">|</span>
						<a href="${contextPath}/termsAndConditions_displayProviderAgreement.action">Provider
							Agreement</a>
						<span class="PIPES">|</span>
						<a href="${contextPath}/termsAndConditions_displayBuyerAgreement.action">Buyer
							Agreement</a>
					</p>
					<p class="faintgrey">
						ServiceLive is a Transform Holdco Company. &copy; <c:import url="/jsp/public/common/copyrightYear.jsp" /> Transform ServiceLive LLC.
					</p>
					<p class="faintgrey">
        <!-- FRONT END DEV NOTE 12/6: Changed "states" to "U.S. territories"-->
        <!-- SL-21161: Removed "VT" from list of U.S. territories unable to fulfill buyer requests-->
        <b>>We are unable to fulfill buyer requests in the following states/U.S. Territories: </b> AS,
        
        FM,
        
        GU,
        
        MH,
        
        MP,
        
        PW,
        
        VI </p>
				</div>
			</div> <%-- div 'theFooter' close --%>
			
		</div> <%-- page_margins div tag is in globalFooter.jsp --%>
		
	</body> <%-- body open tag is in globalFooter.jsp --%>

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
<%-- Do not remove the following script --%>	
<script type="text/javascript">
if(typeof(_gat)!='object')document.write('<sc'+'ript src="http'+
(document.location.protocol=='https:'?'s://ssl':'://www')+
'.google-analytics.com/ga.js"></sc'+'ript>')</script>
<script type="text/javascript">
try {
var pageTracker=_gat._getTracker("UA-2653154-10");
pageTracker._trackPageview("/1637751076/test");
}catch(err){}</script>
<%-- Do not remove the above script --%>
</c:if>
	
<html> <%-- html open tag is in globalFooter.jsp --%>