<!-- ==================================================================================================
  Begin Footer Template Code
  =================================================================================================== -->

      <div class="footer">     

        <div class="secondary-nav clearfix">
          <ul>
            <li><a href="/MarketFrontend/jsp/public/common/footer/terms_of_use.jsp">Terms of Use</a></li>
            <li><a href="/MarketFrontend/jsp/public/common/footer/privacy_policy.jsp">Privacy Policy</a></li>
            <li><a href="/MarketFrontend/jsp/public/common/footer/ca_policy.jsp">California Privacy Policy</a></li>
            <li><a href="termsAndConditions_displayProviderAgreement.action">Provider Agreement</a></li>
            <li><a href="termsAndConditions_displayBuyerAgreement.action?paramTermsandCond=true">Buyer Agreement</a></li>
          </ul>
        </div>

        <p>ServiceLive is a Sears Holdings Company. &copy; 2013 ServiceLive, Inc.</p>
        <p><b>We are unable to fulfill buyer requests in the following states/U.S. Territories:</b> AS, FM, GU, MH, MP, PW, VI, VT</p>

        <div class="footericons">
          <a title="ServiceLive, Inc, Referral - Contractor, Hoffman Estates, IL" href="http://www.bbb.org/chicago/business-reviews/referral-contractor/servicelive-in-hoffman-estates-il-88258074#bbblogo" class="sehzbas" id="bbblink" target="_blank">
            <img width="200" height="38" alt="ServiceLive, Inc, Referral - Contractor, Hoffman Estates, IL" src="https://seal-chicago.bbb.org/logo/sehzbas/servicelive-88258074.png" id="bbblinkimg">
          </a>
          <script type="text/javascript">var bbbprotocol = ( ("https:" == document.location.protocol) ? "https://" : "http://" ); document.write(unescape("%3Cscript src='" + bbbprotocol + 'seal-chicago.bbb.org' + unescape('%2Flogo%2Fservicelive-88258074.js') + "' type='text/javascript'%3E%3C/script%3E"));</script>
          <script type="text/javascript" src="https://seal-chicago.bbb.org/logo/servicelive-88258074.js"></script>
          <a href="http://ssl.comodo.com/free-ssl-certificate.php"  style="text-decoration: none;">
            <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;"></a><br>
						<!-- <a class="verisign" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&amp;sealid=1&amp;dn=provider.servicelive.com&amp;lang=en" target="_blank" title="ServiceLive is Secured by Verisign">
            <img alt="ServiceLive is Secured by Verisign" src="https://provider.servicelive.com:443/ServiceLiveWebUtil/images/common/veriSign.jpg" title="ServiceLive is Secured by Verisign">
          </a>  -->
        </div>

      </div><!-- /.footer-->

    </div> <!-- /.container -->

    <!-- Grab Google CDN's jQuery, with a protocol relative URL; fall back to local if offline -->
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    <script>window.jQuery || document.write('<script src="js/jquery.js"><\/script>')</script>

    <!-- Full Bootstrap JS for development; optimize for production-->
    <script src="js/bootstrap.min.js"></script>
    <!-- 
    jQuery List.js by Jonny Strömberg, @javve
    Documentation: https://github.com/javve/list.js -->
    <script src="js/list.min.js"></script>
    <script src="js/list.emptyView.min.js"></script>
    <script src="js/list.customizations.js"></script>
    <!-- 
    jQuery Timeago by Ryan McGeary, @rmm6t
    Documentation: http://timeago.yarp.com/ -->
    <script src="js/jquery.timeago.js"></script>
    <script>
      $(function(){

        $('.tooltip-target').tooltip();
        $('time.timeago').timeago();
        
      });
    </script>
  </body>
</html>
