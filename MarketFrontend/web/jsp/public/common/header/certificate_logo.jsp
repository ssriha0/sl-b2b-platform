<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div id="entrustLogo">
	<table width="135" border="0" cellpadding="2" cellspacing="0"
		title="Click to Verify - This site chose Entrust SSL for secure e-commerce and confidential communications.">
	
		<tr>
	
			<td width="135" align="left" valign="top">
				
						<c:set var="ref" scope="request" value="<%=request.getServerName()%>" />
						<a href="http://www.instantssl.com/wildcard-ssl.html"  style="text-decoration: none;">
           				  <img src="${staticContextPath}/images/common/comodo_secure_76x26_white.png" alt="Free SSL Certificate" width="76" height="26" style="border: 0px;position:relative;top:3px;">
           				  </a><br>
							<!-- <a title="ServiceLive is Secured by Verisign" target="_blank" href="https://sealinfo.verisign.com/splash?form_file=fdf/splash.fdf&sealid=1&dn=${ref}&lang=en" class="verisign" ><img title="ServiceLive is Secured by Verisign" border="0" src="${staticContextPath}/images/common/veriSign.jpg" alt="ServiceLive is Secured by Verisign" /></a> -->
				<br />
	
				<a href="http://www.entrust.net/" target="_blank"
					style="color: #000000; text-decoration: none; font: bold 7px verdana, sans-serif; letter-spacing: .5px; text-align: center; margin: 0px; padding: 0px;">
					<img src="${staticContextPath}/images/common/entrust_logo.jpg" alt="Entrust" title="Entrust" />						
				</a>
			</td>
	
		</tr>
	
	</table>
</div>
