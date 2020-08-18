<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<script type="text/javascript">
	$(document).ready(function() {
		$('.toggle, .vchecktoggle, .wchecktoggle, .cchecktoggle, dl.collapse dd, span.open, div.filter-option, tr.info, a.dateedit').hide();

		$('.subTabs > ul').tabs();

		$("tr.info").hide();
	});
</script>

<div class="left">
	<c:if test="${showCount > campaignCountAll}">
		<strong>
			Showing ${campaignCountAll} of ${campaignCountAll} (${activeCount} Active / ${inactiveCount} Inactive / ${pendingCount} Unapproved)
		</strong>
	</c:if>
	<c:if test="${showCount <= campaignCountAll}">
		<strong>
			Showing ${showCount} of ${campaignCountAll} (${activeCount} Active / ${inactiveCount} Inactive / ${pendingCount} Unapproved)
		</strong>
	</c:if>
</div>


<c:if test="${showDisplayOptions}">
	<div id="numCampaignResultsDiv" class="right">${viewString}</div>
</c:if>
<br style="clear:both;"/>



<div class="tableWrap">

<table id="spn-monitor" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th rowspan="2" class="br textcenter">
				Status
				<br />
				<!--   <a href="#" title="Sort"><img src="/ServiceLiveWebUtil/images/common/filter-green.png" alt="Sort" /></a><a href="#" title="Sort"><img src="/ServiceLiveWebUtil/images/common/filter-yellow.png" alt="Sort" /></a><a href="#" title="Sort"><img src="/ServiceLiveWebUtil/images/common/filter-red.png" alt="Sort" /></a> -->
			</th>
			<th rowspan="2" class="br textleft">
				Campaign
			</th>
			<th rowspan="2" class="br">
				<abbr title="Select Provider Network">SPN</abbr>
			</th>
			<th colspan="4" class="br textcenter">
				Invited
			</th>

		</tr>
		<tr>
			<th class="highlight">
				Firms / Providers
			</th>
		</tr>
		<tr>
	</thead>
	<tbody>
		<c:choose>
			<c:when test="${empty campaignList}">
				<tr>
					<td class="highlight" colspan="8">
						No Campaign Found
					</td>
				</tr>
			</c:when>
			<c:otherwise>
				<s:iterator id="campaign" value="campaignList" status="status">
					<jsp:include page="campaign_table_row.jsp" />
				</s:iterator>
			</c:otherwise>
		</c:choose>

	</tbody>
</table>
</div>