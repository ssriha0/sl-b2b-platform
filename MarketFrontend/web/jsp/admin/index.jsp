<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Untitled Document</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
</head>
<body>
ServiceLive Admin
<ul>
  <li> <a href="sl_admin_dashboard.jsp">SL Admin Dashboard</a></li>
  <li><a href="member_search_portal.jsp">Member Search Portal</a></li>
  <li> <a href="member_search_results.jsp">Member Search Portal (with results)</a></li>
  <li> <a href="edit_finance_mgr_all_tabs.jsp">Member ServiceLive Wallet</a></li>
  <li> <a href="edit_company_profile_all_tabs.jsp">ServiceLive Edit Company Profile</a></li>
  <li> <a href="manage_users.jsp">ServiceLive Manage Users</a></li>
  <li> <a href="service_pro_profile.jsp">ServiceLive Service Pro Profile</a></li>
  <br />
<br />

 
  <li> <a href="edit_service_pro_all_tabs.jsp">ServiceLive Edit Service Pro Profile</a></li>
  <li> <a href="view_ratings.jsp">ServiceLive Service Order Details - Ratings (BUYER View)</a></li>
  
  <li> <a href="som_buyer_all_tabs.jsp">ServiceLive Service Order Monitor (BUYER View)</a></li>
  <li> <a href="ratings_buyer.jsp">ServiceLive - Ratings (BUYER View)</a></li>
  <li> <a href="audit_main.jsp">ServiceLive - Audit</a></li>
  <li> <a href="audit_insurance.jsp">ServiceLive - Audit (Insurance)</a></li>
  <li> <a href="audit_service_pro.jsp">ServiceLive - Audit (Service Pro)</a></li>
  <li> <a href="service_market_monitor.jsp">ServiceLive Service Market Monitor</a></li>
  <li> <a href="ratings.jsp">ServiceLive - Ratings (System Wide)</a></li>
  <li> <a href="buyer_manage_users.jsp">ServiceLive Manage Users - Buyer</a></li>
  <li> <a href="manage_users_add_edit.jsp">ServiceLive Manage Users (add/ edit)</a></li>
  <li> <a href="finance_mgr_all_tabs.jsp">ServiceLive - ServiceLive Wallet</a></li>
  <li> <a href="reports.jsp">ServiceLive - Enterprise Reports</a></li>
  <li> <a href="create_report.jsp">ServiceLive - Create Enterprise Reports</a> </li>
 <!--  -->
</ul>
</body>
</html>
