<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<script type="text/javascript">
		$(document).ready(function() {
			$('span.open').hide();
			$("tr.info").hide();
		});
	</script>

<table id="spn-monitor" border="0" cellpadding="0" cellspacing="0"
	style="width: 100%;">
	<thead>
		<tr>
			<th class="textleft" rowspan="2">
				<abbr title="Select Provider Network">SPN</abbr>
			</th>
			<th class="blr" rowspan="2">
				Total Active
				<br />
				Campaigns
			</th>
			<th class="br" colspan="6">
				Invited Firms&nbsp;|&nbsp;Providers
			</th>
			<th class="br" colspan="3">
				SPN Approved
			</th>
		</tr>
		<tr>
			<th class="br highlight" style="width: 70px; padding: 0;">
				<br />
				Invited
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br highlight" style="width: 60px; padding: 0;">
				<br />
				Interested
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br highlight" style="width: 60px; padding: 0;">
				Not
				<br />
				Interested
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br highlight" style="width: 60px; padding: 0;">
				<br />
				Applied
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br highlight" style="width: 60px; padding: 0;">
				<br />
				Declined
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br highlight" style="width: 60px; padding: 0;">
				Application
				<br />
				Incomplete
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br" style="width: 90px; padding: 0;">
				<abbr title="Out Of Compliance">Inactive</abbr>
				<br />
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br" style="width: 90px; padding: 0;">
				Active
				<br />
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>
			<th class="br" style="width: 60px; padding: 0;">
				Removed
				<br />
				<div class="left smaller subcellLeft">
					Firms
				</div>
				<div class="left smaller subcellRight">
					Pros
				</div>
			</th>

		</tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty spnSummary}">
				<tr>
					<td class="highlight" colspan="11">
						No Select Provider Network Found
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<s:iterator value="spnSummary" status="rowstatus" id="spnitr">
					<jsp:include page="monitor-table-loop.jsp" />
				</s:iterator>
			</c:otherwise>
		</c:choose>
	</tbody>
</table>
