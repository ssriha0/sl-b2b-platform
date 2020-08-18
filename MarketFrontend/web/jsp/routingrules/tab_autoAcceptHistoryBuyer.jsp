<%@ page language="java"
	import="java.util.*,com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table id="autoAccpetBuyerTable">
					<thead>
					<th id="column1" style="width: 18%;">
					AutoAcceptance
					</th>
					<th id="column2" style="width: 20%;">
					Updated By
					</th>
					<th id="column3" style="width: 20%;">
					Updated On
					</th>
					<th id="column2"  style="width: 30%;">
					Comments
					</th>
					</thead>
					<tbody>
					<c:forEach items="${autoAcceptHistoryListForBuyer}" var="autoAccpetlist">
					<tr>
								<td>${autoAccpetlist.autoAcceptance}
								</td>
								<td>${autoAccpetlist.updatedBy}
								</td>
								<td><fmt:formatDate value="${autoAccpetlist.updatedOn}" pattern="MM-dd-yyyy hh:mm a" />
								</td>
								<td>${autoAccpetlist.comments}
								</td>
					</tr>
					
					</c:forEach>
					</tbody>
</table>
</body>
</html>