<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

		</div>
			<div id="theFooter">
				<div id="footer" style="height:60px">
					<div class="footericons" style="padding-top:6px!important";>
						<%@ include file="/html/BBB_Image.html"%>
                                                       
						<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
						<a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
           				  <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;position:relative;top:3px;"><br>
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
						<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && providerLeadManagementPermission == 'true' && newLeadsTCIndicator != null && showLeadsSignUp == 1}">
							<span class="PIPES">|</span>
							<a href="${contextPath}/termsAndConditions_displaySLLeadsAddendum.action">SL Leads Addendum
							</a>
						</c:if> 
					</p>
					<p class="faintgrey">
						ServiceLive is a Transform Holdco Company. &copy; <c:import url="/jsp/public/common/copyrightYear.jsp" /> Transform ServiceLive LLC.
					</p>
					<c:if test="${not empty blackoutStates}">
					<p class="faintgrey">
						<b>We are unable to fulfill buyer requests in the following states/U.S. Territories: </b>
						<s:iterator value="blackoutStates" status="statePos">
							<s:property/><s:if test="!#statePos.last">,</s:if>
						</s:iterator>						
					</p>
					</c:if>
				</div>
			</div> <%-- div 'theFooter' close --%>
			
		</div> <%-- page_margins div tag is in globalFooter.jsp --%>


<c:if test="${showTags == 1}">
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

		
	</body> <%-- body open tag is in globalFooter.jsp --%>
	
<html> <%-- html open tag is in globalFooter.jsp --%>
