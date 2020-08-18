<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="BuyerAdmin.companyProfile.companyCompleteProfile"/>
		</jsp:include>	

<h3 class="paddingBtm">
	[Papanek Inc.]
</h3>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">
	Business Information
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<div>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="350">
					<p>
						<strong>Business Name</strong>
						<br />
						[Previously entered company name]
					</p>
				</td>
				<td>
					<p>
						<strong>Doing Business As (DBA)</strong>
						<br />
						[Previously entered DBA name]
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Main Business Phone</strong>
						<br />
						[404-426-7100]
					</p>
				</td>
				<td>
					<p>
						<strong>Business Fax</strong>
						<br />
						[404-426-7200]
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Taxpayer ID (EIN or SSN)</strong>
						<br />
						[000-00-0000]
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Business Structure</strong>
						<br />
						[Limited Liability Company (LLC)]
					</p>
				</td>
				<td>
					<p>
						<strong>Business Started</strong>
						<br />
						July 2002
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Primary Industry</strong>
						<br />
						Plumbing
					</p>
				</td>
				<td>
					<p>
						<strong>Website Address</strong>
						<br />
						www.companyname.com
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Size of Company</strong>
						<br />
						20-30 Employees
					</p>
				</td>
				<td>
					<p>
						<strong>Annual Sales Revenue</strong>
						<br />
						Less than $100,000
					</p>
				</td>
			</tr>
			<tr>
				<td>
					<p>
						<strong>Is the business foreign owned?</strong>
						<br />
						Yes
					</p>
				</td>
				<td>
					<p>
						<strong>Foreign Owned Percentage</strong>
						<br />
						Less than 15%
					</p>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="darkGrayModuleHdr">
	Business Address
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<p>
		123 Huff St., Suite 400
		<br />
		Atlanta, GA 30306
		<br />
		<br />
		<img src="${staticContextPath}/images/icons/greenCheckMark.gif" width="7" height="7"
			alt="Check" />
		The Mailing Address is the same as the Business Address.
	</p>
</div>
<div class="darkGrayModuleHdr">
	Primary Contact Information - Buyer Administrator
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="350">
				<p>
					<strong>Name</strong>
					<br />
					Addison Sarah Montgomery
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<strong>Role Within Company</strong>
					<br />
					Owner/Principal
			</td>
			<td>
				<p>
					<strong>Job Title</strong>
					<br />
					Owner
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<strong>Business Phone</strong>
					<br />
					404-426-7100
			</td>
			<td>
				<p>
					<strong>Mobile Phone</strong>
					<br />
					404-426-7106
			</td>
		</tr>
		<tr>
			<td>
				<p>
					<strong>E-mail</strong>
					<br />
					<a href="">addison.montgomery@companyname.com</a>
			</td>
			<td>
				<p>
					<strong>Alternate E-mail</strong>
					<br />
					<a href="">addison.montgomery2@companyname.com</a>
			</td>
		</tr>
	</table>
</div>
<div class="darkGrayModuleHdr">
	Logos on File
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0"
		class="scrollerTableHdr compLogosBuyerHdr">
		<tr>
			<td class="column1"></td>
			<td class="column2">
				Company
			</td>
			<td class="column3">
				Logo ID
			</td>
			<td class="column4">
				File Size
			</td>
		</tr>
	</table>
	<div class="grayTableContainer" style="width: 667px; height: 100px;">
		<table cellpadding="0" cellspacing="0"
			class="gridTable compLogosBuyer">
			<tr>
				<td class="column1">
					<img src="${staticContextPath}/images/icons/pdf.gif" />
				</td>
				<td class="column2">
					ABC Inc.
				</td>
				<td class="column3">
					ABCinc.jpg
				</td>
				<td class="column4">
					178 kb
				</td>
			</tr>
			<tr>
				<td class="column1">
					<img src="${staticContextPath}/images/icons/pdf.gif" />
				</td>
				<td class="column2">
					ABC Lawn & Garden Services
				</td>
				<td class="column3">
					ABCinc.jpg
				</td>
				<td class="column4">
					178 kb
				</td>
			</tr>
		</table>
	</div>
</div>
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
<div class="clearfix">
	<div class="formNavButtons">
		<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="86"
			height="20"
			style="background-image: url(${staticContextPath}/images/btn/editProfile.gif);"
			class="btn20Bevel" />
	</div>
</div>
