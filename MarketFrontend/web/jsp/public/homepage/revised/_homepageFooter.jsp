<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<style type="text/css">
	.footericons
	{
		padding: 0px !important;

	}
	#comodoimg
	{
		text-decoration:none;
	}
	#comodoimg span
	{
		 font-weight:bold;
		 font-size:7pt; 
		 padding:24px;
	}
</style>
<div id="theFooter" class="clearfix">
    <div id="footer">
   		<div class="footericons">
   		
			<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
		    <table style="padding-left:150px;">
		      	<tr>
		      		<td style="padding-top: 20px;"> 			
						<%@ include file="/html/BBB_Image.html"%>
			  		</td>
			  		<td style="padding-top: 20px;">
			  	        <a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
                        <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;"><br>
                        </a><br>
						<!-- <a title="ServiceLive is Secured by Verisign" target="_blank" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=${ref}&lang=en" class="verisign" ><img title="ServiceLive is Secured by Verisign" border="0" src="${staticContextPath}/images/common/veriSign.jpg" alt="ServiceLive is Secured by Verisign" /></a> -->
			  		</td>
				</tr>
			  </table>

			<table border="0">
				<tr>
					<td style="text-align: right; padding-top: 5px; padding-right: 10px;">
						<strong>ServiceLive is optimized<br>for the following<br>browsers:</strong>			
					</td>
					<!-- Removing IE
					<td>
						<center>
							<img title="IE" border="0" src="${staticContextPath}/images/common/ie.png" alt="" />
							<br>
							<strong>IE 8</strong>
						</center>
					</td>-->
				<td>
						<center>
							<img title="Firefox" border="0" src="${staticContextPath}/images/common/firefox.png" alt="" />
							<br>
							<strong>Firefox</strong>
						</center>			
					</td>
					<td style ="padding-left: 15px;">
						<center>
							<img border="0" alt=""
								src="${staticContextPath}/images/common/chrome.png"
								title="Chrome"> <br> <strong>Chrome</strong>
						</center>
					</td>
				</tr>
			</table>
	</div>
	<p class="lightBlue">
		<a href="${contextPath}/jsp/public/common/footer/terms_of_use.jsp">Terms of Use</a> <span class="PIPES">|</span> 
		<a href="https://transformco.com/privacy" target="_blank"	>Privacy Policy</a> <span class="PIPES">|</span> 
		<a href="https://transformco.com/privacy#_Toc31123888" target="_blank">California Privacy Policy</a> <span class="PIPES">|</span> 
		<a href="${contextPath}/termsAndConditions_displayProviderAgreement.action">Provider Agreement</a> <span class="PIPES">|</span> 
		<a href="${contextPath}/termsAndConditions_displayBuyerAgreement.action">Buyer Agreement</a>
	</p>
		<p class="faintgrey"> ServiceLive is a Transform Holdco Company. &copy; <c:import url="/jsp/public/common/copyrightYear.jsp" /> Transform ServiceLive LLC.</p>
 		<!-- SL-21161: Removed "VT" from list of U.S. territories unable to fulfill buyer requests-->
      <p class="faintgrey"><b>We are unable to fulfill buyer requests in the following states/U.S. Territories: </b> AS, FM, GU, MH, MP, PW, VI</p>
    </div>
    
    	<div style="clear: both;">&nbsp;</div>
    
</div>