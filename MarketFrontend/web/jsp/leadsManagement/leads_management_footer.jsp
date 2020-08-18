<!-- ==================================================================================================
  Begin Footer Template Code
  =================================================================================================== -->
  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
 </div><!-- /.content -->
      <div class="footer">     
        <div class="secondary-nav clearfix">
          <ul>
            <li><a href="/MarketFrontend/jsp/public/common/footer/terms_of_use.jsp">Terms of Use</a></li>
            <li><a href="https://transformco.com/privacy" target="_blank">Privacy Policy</a></li>
            <li><a href="https://transformco.com/privacy#_Toc31123888" target="_blank">California Privacy Policy</a></li>
            <li><a href="${contextPath}/termsAndConditions_displayProviderAgreement.action">Provider Agreement</a></li>
            <li><a href="${contextPath}/termsAndConditions_displayBuyerAgreement.action?paramTermsandCond=true">Buyer Agreement</a></li>
         	<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && providerLeadManagementPermission == 'true' && newLeadsTCIndicator != null && showLeadsSignUp == 1}">
				<li><a href="${contextPath}/termsAndConditions_displaySLLeadsAddendum.action">SL Leads Addendum</a></li>
			</c:if> 
          </ul>
        </div>

        <p>ServiceLive is a Transform Holdco Company. &copy; 2019 Transform ServiceLive LLC.</p>
        <!-- SL-21161: Removed "VT" from list of U.S. territories unable to fulfill buyer requests-->
        <p><b>We are unable to fulfill buyer requests in the following states/U.S. Territories:</b> AS, FM, GU, MH, MP, PW, VI</p>

        <div class="footericons">
          <a title="ServiceLive, Inc, Referral - Contractor, Hoffman Estates, IL" href="http://www.bbb.org/chicago/business-reviews/referral-contractor/servicelive-in-hoffman-estates-il-88258074#bbblogo" class="sehzbas" id="bbblink" target="_blank">
            <img width="200" height="38" alt="ServiceLive, Inc, Referral - Contractor, Hoffman Estates, IL" src="https://seal-chicago.bbb.org/logo/sehzbas/servicelive-88258074.png" id="bbblinkimg">
          </a>
          <script type="text/javascript">var bbbprotocol = ( ("https:" == document.location.protocol) ? "https://" : "http://" ); document.write(unescape("%3Cscript src='" + bbbprotocol + 'seal-chicago.bbb.org' + unescape('%2Flogo%2Fservicelive-88258074.js') + "' type='text/javascript'%3E%3C/script%3E"));</script>
         <!--  Changes for SL-21155 starts  --> 
         <!-- script type="text/javascript" src="https://seal-chicago.bbb.org/logo/servicelive-88258074.js"></script-->
           <script type="text/javascript" src="${staticContextPath}/images/servicelive-BBB.png"></script>
                <!--  Changes for SL-21155 ends  --> 
          	 <a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;color:#5C5753">
                <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;border: 0px;position:relative;top:3px;"><br>
             </a><br>
             <!-- <a class="verisign" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;sealid=1&amp;dn=provider.servicelive.com&amp;lang=en" target="_blank" title="ServiceLive is Secured by Verisign">
            <img alt="ServiceLive is Secured by Verisign" src="https://provider.servicelive.com:443/ServiceLiveWebUtil/images/common/veriSign.jpg" title="ServiceLive is Secured by Verisign">
          </a>-->
        </div>

      </div><!-- /.footer-->

    </div> <!-- /.container -->

    <!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
   
  </body>
</html>