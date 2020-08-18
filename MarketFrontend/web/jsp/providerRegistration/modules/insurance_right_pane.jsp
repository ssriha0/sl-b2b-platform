<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div>
	<table width="255" border="0" cellspacing="10" cellpadding="0" bgcolor="#EEFFDD" style="color: #222">
		<tr><td>
			<img src="${staticContextPath}/images/getverified.png" style="float: left; margin: 0 5px 5px 0;" />
			<p><strong>Get Verified.Get Noticed.Get Work!</strong></p>
			<p>You have a choice to self-declare your coverage, or upload your coverage information to become a <i>Verified Provider</i>.</p>
			<p>Most large buyers require verifications,<br> so to work with them, you will need to take these important and simple steps toward success.</p>
			<p><strong>Get Verified steps:</strong></p>
			<ul style="margin-left: 15px;">
				<li><p>Read and follow the instructions in the <a href="http://community.servicelive.com/docs/ServiceliveInsuranceRequirements.pdf" target="_insRqmtGuide">ServiceLive Insurance Requirement Guide</a>.</p></li>
				<li><p>Verification requires your insurance carrier is rated A- or better in the current Best's Insurance Reports. Please visit and register at <a href="http://www3.ambest.com/ratings/default.asp" target="_bestInsRpts">A.M. Best Company</a> to check your carrier's rating.</p></li> 
				<li>
					<p>Be sure to add <strong>Transform Midco LLC and its subsidiaries and affiliates.</strong> as the additional insured and assign as the certificate holder.</p>
					<p>The address must read:</p>
					<p>7353 NW Loop 410<br>
					San Antonio, TX 78245</p>
					</li> 
				<li><p>Upload your certificates on ServiceLive.</p></li>
			</ul>
		</td></tr>
	</table>
</div>
