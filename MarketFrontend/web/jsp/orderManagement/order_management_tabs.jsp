<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="selectedTab" scope="request" value="${omDisplayTab}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	

</script>
<style>
.omTabCount{padding-right: 2px;}
.selectedOMTabCount{background-color:#FF8F45; -moz-border-radius:5px; border-radius:5px;-webkit-border-radius:5px;text-align: center;}
.defaultOMTabCount{background-color:#FFFFFF;-moz-border-radius:5px; border-radius:5px;-webkit-border-radius:5px;text-align: center;}
.refresh{padding: 3px;}
.icon_refresh{background: none;font-size: 14px;}
.orderTabDiv{-moz-border-radius: 10px 10px 10px 10px;border-radius:10px 10px 10px 10px;-webkit-border-radius:10px;width: 134px;height: 41px;}
</style>
</head>
<body class="tundra acquity">
	<div id="omMainTab" style="padding-left: 0px;">
		<c:set var="inboxDisplayFlag" value="true"></c:set>
		<c:if test="${tabTitleCounts!=null && fn:length(tabTitleCounts) > 0}">
		<c:forEach var="tabList" items="${tabTitleCounts}">
		<ul class="om-tabs">
			<li>
				<a href="javascript:void(0);" class="tab" id="tab${tabList.tabTitle}">
				<i id="icon${tabList.tabTitle}" style="font-size: small" class=""></i> 
				<c:choose>
				<c:when test="${tabList.tabTitle == 'Confirm Appt window' }">
					<b>Confirm Appt.</b>
				</c:when>
				<c:otherwise>
					<b>${tabList.tabTitle}</b> 
				</c:otherwise>
				</c:choose>
				<div class="tab-count">
					<i class='' title='Refresh current view'></i>
					<span class="span-count" id="count_${fn:replace(tabList.tabTitle, ' ', '')}">${tabList.tabCount}</span>
				</div>
				</a>
			</li>
		</ul>
		</c:forEach>
		</c:if>
		<c:if test="${tabTitleCounts==null || fn:length(tabTitleCounts) == 0}">
		<div>
		<strong>No Data</strong>
		</div>
		</c:if>
	</div>
	<a id="fdbk_tab" href="javascript:void(0);">FEEDBACK</a>
</body>
</html>
