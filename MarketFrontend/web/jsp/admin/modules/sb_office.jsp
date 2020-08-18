<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<div id="sbOffice" class="box">

	<tags:security actionName="adminManageUsersAction">
		<c:set var="manageUsersPerm" value="true" />
	</tags:security>
	<tags:security actionName="financeManagerController">
		<c:set var="finManagerPerm" value="true" />
	</tags:security>
	<tags:security actionName="fullfillmentAdminAction">
		<c:set var="fulfillmentAdminToolPerm" value="true" />
	</tags:security>
	<c:if test="${manageUsersPerm == 'true' || finManagerPerm == 'true' || fulfillmentAdminToolPerm == 'true'}">
		<h3>Administrative Office</h3>
		<ul>
			<c:if test="${ manageUsersPerm == 'true'}">
			
				<li class="sb-musers"><a href="adminManageUsers_execute.action">Manage Users</a></li>
			</c:if>
			<c:if test="${finManagerPerm == 'true'}">
				<li class="sb-wallet"><a href="financeManagerController_execute.action">ServiceLive Wallet</a></li>
			</c:if>
			<c:if test="${fulfillmentAdminToolPerm == 'true'}">
				<li class="sb-wallet"><a href="fullfillmentAdminAction_execute.action">Fulfillment Adjustment</a></li>
			</c:if>
		</ul>
	</c:if>
	</div>
