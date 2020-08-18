<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>


<c:set var="contextPath" scope="request" value="/MarketFrontend" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<div id="footer" class="span-24 clearfix">
					<div class="footericons" >
					
						<%@ include file="/html/BBB_Image.html"%>						
						
						<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
						<a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
           				  <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;position:relative;top:3px;">
           		     </a><br>
							<!--  <a title="ServiceLive is Secured by Verisign" target="_blank" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=${ref}&lang=en" class="verisign" ><img title="ServiceLive is Secured by Verisign" border="0" src="${staticContextPath}/images/common/veriSign.jpg" alt="ServiceLive is Secured by Verisign" /></a> -->
					</div>
	<ul class="landscape">
		<li><a href="/MarketFrontend/jsp/public/common/footer/terms_of_use.jsp">Terms of Use</a> </li>
		<li><a href="https://transformco.com/privacy" target="_blank">Privacy Policy</a> </li>
		<li><a href="https://transformco.com/privacy#_Toc31123888" target="_blank">California Privacy Policy</a> </li>
		<li><a href="/MarketFrontend/termsAndConditions_displayProviderAgreement.action">Provider Agreement</a> </li>
		<li><a href="/MarketFrontend/termsAndConditions_displayBuyerAgreement.action">Buyer Agreement</a></li>
	</ul>
	<p class="copyright">&copy; <c:import url="/jsp/spn/common/copyrightYear.jsp" /> Transform ServiceLive LLC.</p>
</div>


