<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div class="slStats_hdr"></div>
<div class="slStatsBody">
	<div class="slStatsCell slStatsSeparator">
		<h4>
			Service Order Trend Graph
		</h4>
		<img src="${staticContextPath}/images/dashboard/graph.gif" />
		<p>
			<img src="${staticContextPath}/images/dashboard/blue_key.gif" />
			 
			<b>Blue Trend</b>
			<img src="${staticContextPath}/images/dashboard/red_key.gif" />
			 
			<b>Red Trend</b>
		</p>
		<h4>
			Value of Service Orders:
		</h4>
		<table cellpadding="0" cellspacing="0">
			<tbody>
				<tr>
					<td width="130">
						Today:
					</td>
					<td>
						$147,000
					</td>
				</tr>
				<tr>
					<td>
						Last 30 Days:
					</td>
					<td>
						$635,000
					</td>
				</tr>
				<tr>
					<td>
						Last 60 Days:
					</td>
					<td>
						$847,000
					</td>
				</tr>
				<tr>
					<td>
						Last 90 Days:
					</td>
					<td>
						$1,154,398
					</td>
				</tr>
				<tr>
					<td>
						Year to Date:
					</td>
					<td>
						$1,731,857
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="slStatsCell slStatsSeparator">
		<h4>
			Membership Statistics
		</h4>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="150">
					Overall Firm Count
				</td>
				<td align="right">
					1,041
				</td>
			</tr>
			<tr>
				<td>
					Buyer Company
				</td>
				<td align="right">
					41
				</td>
			</tr>
			<tr>
				<td>
					Provider Firms
				</td>
				<td align="right">
					1,000
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td></td>
			</tr>
			<tr>
				<td>
					Overall User Count
				</td>
				<td align="right">
					1,700
				</td>
			</tr>
			<tr>
				<td>
					Buyers
				</td>
				<td align="right">
					200
				</td>
			</tr>
			<tr>
				<td>
					Providers
				</td>
				<td align="right">
					1,500
				</td>
			</tr>
			<tr>
				<td>
					Service Providers
				</td>
				<td align="right">
					1,400
				</td>
			</tr>
			<tr>
				<td>
					&nbsp;
				</td>
				<td align="right"></td>
			</tr>
			<tr>
				<td>
					New Today
				</td>
				<td align="right">
					1
				</td>
			</tr>
			<tr>
				<td>
					New Yesterday
				</td>
				<td align="right">
					3
				</td>
			</tr>
		</table>
	</div>
	<div class="slStatsCell">

		<h4>
			Newest Members
		</h4>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="130">
					Buyer Company
				</td>
				<td>
					Barclay's True Value Hardware Store
					<br>
					Springfield, MO 07856
				</td>
			</tr>
			<tr>
				<td colspan="2">
					&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					Provider Firm
				</td>
				<td>
					Acme Plumbing & Tile Services
					<br>
					Lubboch, TX 75286
				</td>
			</tr>
		</table>



		<h4>
			Online Now
		</h4>
		<table cellpadding="0" cellspacing="0">
			<tr>
				<td width="100">
					Visitors
				</td>
				<td align="right">
					240
				</td>
			</tr>
			<tr>
				<td>
					Members
				</td>
				<td align="right">
					34
				</td>
			</tr>
			<tr>
				<td>
					Total
				</td>
				<td align="right">
					574
				</td>
			</tr>
		</table>
	</div>
</div>
