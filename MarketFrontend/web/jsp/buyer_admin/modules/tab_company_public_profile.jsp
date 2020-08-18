<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.companyProfile.companyPublicProfile"/>
		</jsp:include>	


<h3 class="paddingBtm">
	[Papanek Inc.]
</h3>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Business Information
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		<strong>Company ID#</strong>
		<br />
		0000000000
	</p>
</div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Business Address
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		Atlanta, GA 30306
	</p>
</div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Vital Statistics
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0" width="575" align="center"
		style="margin: auto;">
		<tr>
			<td align="center" width="100">
				<strong>Ratings (Overall)</strong>
			</td>
			<td align="center" width="90">
				<strong>Quality</strong>
			</td>
			<td align="center" width="100">
				<strong>Communication</strong>
			</td>
			<td align="center" width="95">
				<strong>Timeliness</strong>
			</td>
			<td align="center" width="90">
				<strong>Value</strong>
			</td>
			<td align="center" width="100">
				<strong>Professionalism</strong>
			</td>
		</tr>
		<tr>
			<td align="center">
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
			</td>
			<td align="center">
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
			</td>
			<td align="center">
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
			</td>
			<td align="center">
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
			</td>
			<td align="center">
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
					alt="" />
			</td>
			<td align="center">
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
				<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
					alt="" />
			</td>
		</tr>
	</table>
	<br clear="all" />
	<table style="border: 1px #9f9f9f solid;" cellpadding="0"
		cellspacing="0" width="669">
		<tr style="background: #4cbcdf; color: #ffffff; height: 22px;">
			<td width="184"
				style="padding-top: 5px; padding-left: 10px; text-align: center;">
				<strong>Service Orders (Overall)</strong>
			</td>
			<td width="245" style="padding-top: 5px; text-align: center;">
				<strong>Service Orders (Last 30 Days)</strong>
			</td>
			<td width="240" style="padding-top: 5px; text-align: center;">
				<strong>Service Orders (Last 60 Days)</strong>
			</td>
		</tr>
		<tr>
			<td style="padding: 5px 0px 5px 10px; text-align: center;">
				1,012
			</td>
			<td style="padding: 5px 0px 5px 10px; text-align: center;">
				73
			</td>
			<td style="padding: 5px 0px 5px 10px; text-align: center;">
				135
			</td>
		</tr>
	</table>
</div>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Provider Relations
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		<strong>Average Rating given to Service Providers</strong>
		<br />
		<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
			alt="" />
		<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
			alt="" />
		<img src="${staticContextPath}/images/common/full_star.gif" width="11" height="11"
			alt="" />
		<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
			alt="" />
		<img src="${staticContextPath}/images/common/empty_star.gif" width="11" height="11"
			alt="" />
	</p>
</div>

