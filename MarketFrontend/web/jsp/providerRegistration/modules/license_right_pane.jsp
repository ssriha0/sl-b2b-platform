<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div>
	<table width="255" border="0" cellspacing="10" cellpadding="0" bgcolor="#FFF6BF" style="color: #222">
		<tr><td>
			<img src="${staticContextPath}/images/getverified.png" style="float: left; margin: 0 5px 5px 0;" />
			<p><strong>Get Verified.Get Noticed.Get Work!</strong></p>
			<p>Getting "Verified" allows you and your firm to stand out among other Provider Firms on ServiceLive.</p>
			<p>In addition, some ServiceLive Buyers may require proof of certain licenses or certifications prior to routing you a service order.</p>
			<p><strong>Get Verified steps:</strong></p>
			<ul style="margin-left: 15px;">
				<li><p>Read and follow the instructions in the <a href="http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf" target="_insRqmtGuide">ServiceLive Verification Guide</a>.</p></li>
				<li><p>Upload proof of each credential.</p></li> 				
			</ul>
			<p>Once each uploaded document has been verified by the ServiceLive Compliance team, a "<strong>Verified</strong>" ServiceLive icon will appear on your profile.</p>
		</td></tr>
	</table>
</div>

