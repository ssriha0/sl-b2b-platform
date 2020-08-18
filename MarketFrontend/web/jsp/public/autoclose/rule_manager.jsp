<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive : Auto Close Rule Manager</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/auto-close.css" media="screen, projection">
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript">
			$(function() {
				// insert your javascript here
			});
		</script>
	</head>

<body id="autoclose-rulemanager">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
		<div id="autoclose">
			<ul class="ui-tabs-nav">
				<li class="ui-tabs-selected"><a href="rule_manager.jsp">Manage Rules</a></li>
				<li><a href="rule_history.jsp">Rule History</a></li>
			</ul>
			<div class="ui-tabs-panel">
			<h3>General Rules</h3>
			
			<p>General rules apply to all service orders in all categories.</p>
			<div class="box">
				<p>Exclude service orders in all categories with the following criteria.</p>
				<div class="table-wrap">
					<table border="0">
						<thead>
							<tr>
								<th class="br">Type</th>
								<th colspan="2">Rules</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="br"><strong>Service Order Criteria</strong></td>
								<td><strong>Maximum Order Value $</strong><input type="text" class="text short"></td>
								<td><input type="checkbox" class="checkbox"> <strong>Add-on Orders</strong> <small>(Paid by Check)</small></td>
							</tr>
							<tr>
								<td class="br"><strong>Provider Criteria</strong></td>
								<td><strong>Minimum Completed Orders: </strong><select><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select></td>
								<td><input type="checkbox" class="checkbox"> <strong>No Completion Document on file</strong></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="clearfix">
					<p class="buttonmsg left">Click "Update Rule" to save changes.</p>
					<input type="submit" value="Update Rule" class="button right action">
				</div>
			</div>
			
			<h3>New Category Rule</h3>
			<h3>Manage Category Rules</h3>
			
			<div class="table-wrap">
				<table border="0">
					<thead>
						<tr>
							<th class="br tc" style="width: 100px;">Status<br />- -</th>
							<th class="br">Rule</th>
							<th class="tc br">Main Service Category</th>
							<th class="tc">Activate Rule</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="tc br"><img src="/ServiceLiveWebUtil/images/common/status-red.png"><br /><small>Inactive</small></td>
							<td class="br">Rule Name 1</td>
							<td class="tc br">Automotive</td>
							<td class="tc"><input type="checkbox" class="checkbox">Apply Rule</td>
						</tr>
						<tr>
							<td class="tc br"><img src="/ServiceLiveWebUtil/images/common/status-green.png"><br /><small>Active</small></td>
							<td class="br">Rule Name 1</td>
							<td class="tc br">Automotive</td>
							<td class="tc"><input type="checkbox" class="checkbox">Apply Rule</td>
						</tr>
					</tbody>
				</table>				
			</div>
			
			</div>
		</div>
	</div>
	<tiles:insertDefinition name="blueprint.base.footer"/>
</div>
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="autoclose:rule manager"/>
	</jsp:include>
	</body>
</html>
