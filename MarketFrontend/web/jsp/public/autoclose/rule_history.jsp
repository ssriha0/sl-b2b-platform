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
		<title>ServiceLive : Auto Close Rule History</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/auto-close.css" media="screen, projection">
		<!-- include any plugin javascripts and css here -->
		<script type="text/javascript">
			$(function() {
				// insert your javascript here
			});
		</script>
	</head>

<body id="autoclose-rulehistory">
<div id="wrap" class="container">
	<tiles:insertDefinition name="blueprint.base.header"/>
	<tiles:insertDefinition name="blueprint.base.navigation"/>
	<div id="content" class="span-24 clearfix">		
		<div id="autoclose">
			<ul class="ui-tabs-nav">
				<li><a href="rule_manager.jsp">Manage Rules</a></li>
				<li class="ui-tabs-selected"><a href="rule_history.jsp">Rule History</a></li>
			</ul>
			<div class="ui-tabs-panel">
				<div class="table-wrap">
				<table>
					<thead>
						<tr>
							<th class="tc br">Date</th>
							<th>Name</th>
							<th class="tc">Action</th>
							<th>Rule</th>
							<th class="tr">Rule Type</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="tc br"><small>01/21/2009 3:39 p.m. CST</small></td>
							<td><strong>Firstname Lastname</strong> <small>(ID# 1234)</small></td>
							<td class="tc cancelled"><span>Cancelled</span></td>
							<td>&nbsp;</td>
							<td class="tr">General Rule</td>
						</tr>
						<tr>
							<td class="tc br"><small>01/21/2009 3:39 p.m. CST</small></td>
							<td><strong>Firstname Lastname</strong> <small>(ID# 1234)</small></td>
							<td class="tc cancelled"><span>Cancelled</span></td>
							<td>Rule Name 1</td>
							<td class="tr">Category Rule</td>
						</tr>
						<tr>
							<td class="tc br"><small>01/21/2009 3:39 p.m. CST</small></td>
							<td><strong>Firstname Lastname</strong> <small>(ID# 1234)</small></td>
							<td class="tc updated"><span>Updated</span></td>
							<td>Rule Name 2</td>
							<td class="tr">Category Rule</td>
						</tr>
						<tr class="comment">
							<td class="tc br">&nbsp;</td>
							<td colspan="4" class="addicon">
								Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
							</td>
						</tr>
						<tr>
							<td class="tc br"><small>01/21/2009 3:39 p.m. CST</small></td>
							<td><strong>Firstname Lastname</strong> <small>(ID# 1234)</small></td>
							<td class="tc new"><span>New</span></td>
							<td>Rule Name 2</td>
							<td class="tr">Category Rule</td>
						</tr>			
						<tr class="comment">
							<td class="tc br">&nbsp;</td>
							<td colspan="4" class="addicon">
								Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore.
							</td>
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
		 <jsp:param name="PageName" value="autoclose:rule history"/>
	</jsp:include>
	</body>
</html>
