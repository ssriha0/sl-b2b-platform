<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
	<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<html>
	<head>
		<title>ServiceLive - Select Provider Network - Member Management</title>
		<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/jquery.tablesorter.min.js"></script>
	</head>
	<body id="select-provider-network">
		<div id="autocloseContainer" class="content">

			<div id="tab-content" class="clearfix">

				<div id="autocloseSearchForm" class="clearfix">
					<label for="autocloseSearchBy">
						Search by
					</label>
					<select id="autocloseSearchBy">
						<option value="-1">
							- Select One -
						</option>
						<option value="2">
							Provider ID
						</option>
						<option value="1">
							Provider Name
						</option>
						<option value="4">
							Provider Firm ID
						</option>
						<option value="3">
							Provider Firm Name
						</option>
					</select>
					<fieldset id="autocloseSearchInput">
						<input style="margin-top: 0px;" type="text" id="autocloseSearchTextId"  />
						<label>
							Enter
							<span>Provider Name</span>
						</label>
					</fieldset>
					<input style="margin-top: 0px;" id="searchSubmit" type="button" value="FIND" class="action" />
					<br />
				</div>

				<div id="searchresults" ></div>
			</div>
		</div>
		<!--  end of tabs div -->
	</body>
</html>