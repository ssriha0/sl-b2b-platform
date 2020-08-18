<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<tags:security actionName="buyerAdminDocManager" >
	<c:set var="hasadminoffice" value="true" />
</tags:security>
	
<c:if test="${hasadminoffice eq 'true'}">
	<div class="leftTileItem" id="dbTile_adminOfc">
		<div class="titleContainer">
    		<div class="titleBar">
				<h2>Administrator Office</h2>
        		<%-- <a href=""><img src="${staticContextPath}/images/dashboard/lnkMore.gif" width="41" height="23" border="0" align="right" /></a> --%>
    		</div>
    	</div>
    	<div class="contentContainer">
    		<div class="content">
				<tags:security actionName="buyerAdminDocManager" >
					<table width="100%" border=0>
						<tr>
							<td align="left">
								<p>
									<strong>
										<a style="" href="${contextPath}/buyerAdminDocManager_execute.action">
											Document Manager
										</a>
									</strong>
								</p>
							<td align="right">
								<%-- 
								<p>
									<strong>
										<a style="" href="${contextPath}/buyerAdminManageUsers_execute.action">
											Manage Team
										</a>
									</strong>
								</p>
								--%>
							</td>
						</tr>
					</table>
				</tags:security>
      		</div>
    	</div>
		<div class="shadowBottom"></div>
	</div>
</c:if>
  
