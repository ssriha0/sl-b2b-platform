<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/main.css">
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/top-section.css">
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/iehacks.css">
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/buttons.css">
<link rel="stylesheet" type="text/css" href="/ServiceLiveWebUtil/css/sl_admin.css" />

<script language="JavaScript" type="text/javascript">
			function showProvDetails(vendorId)
			{
				
				var loadSrchForm = document.getElementById('adminSearch_result_frame');
				loadSrchForm.hidVendorId.value=vendorId;
				loadSrchForm.action="adminSearch_navigateToProviderPage.action";
				loadSrchForm.submit();
			}
		
			function showBuyerDetails(buyerId,resourceId,roleId,userName,companyname,citystate,adminname)
			{
				var loadSrchForm = document.getElementById('adminSearch_result_frame');
				loadSrchForm.hidBuyerId.value=buyerId;
				loadSrchForm.hidResourceId.value=resourceId;
				loadSrchForm.hidRoleId.value=roleId;
				loadSrchForm.hidBuyerAdmin.value=adminname;
				loadSrchForm.hidUserName.value=userName;
				loadSrchForm.hidCompanyName.value=companyname;
				loadSrchForm.hidBuyerCityState.value=citystate;
				loadSrchForm.action="adminSearch_navigateToBuyerPage.action";
				loadSrchForm.submit();
			}
		</script>
		
		<s:form id="adminSearch_result_frame" name="adminSearch_result_frame"
	theme="simple" target="_top">
	<s:hidden name="hidVendorId"></s:hidden>
	<s:hidden name="hidResourceId"></s:hidden>
	<s:hidden name="hidUserName"></s:hidden>
	<s:hidden name="hidBuyerId"></s:hidden>
	<s:hidden name="hidRoleId"></s:hidden>
	<s:hidden name="hidBuyerAdmin"></s:hidden>
	<s:hidden name="hidCompanyName"></s:hidden>
	<s:hidden name="hidBuyerCityState"></s:hidden>
</s:form>



<div style="border: 1px solid silver; border-bottom: 0px;">
<c:if test="${not empty providersList}">
<c:if test="${buyerProviderSelection == 1}">

<table class="globalTableLook" cellspacing="0" cellpadding="0" border="0" width="100%">
		<tr>
			<th class="col1 odd">User ID</th>
			<th class="col2 even">Status</th>
			<th class="col3 odd textleft">Name</th>
			<th class="col4 even">Primary Industry</th>
			<th class="col5 odd">Latest Activity</th>
			<th class="col6 even">Market</th>
			<th class="col7 odd">State</th>
		</tr>
		<c:forEach items="${providersList}" var="provider">
			<tr>
				<td class="col1 odd">
					<a href="javascript:showProvDetails(${provider.id});">${provider.id}
					</a>
				</td>
				<td class="col2 even">
					${provider.status}
					<c:if test="${provider.resourceStatus != null}">
						<br>${provider.resourceStatus}
							</c:if>
				</td>
				<td class="col3 odd textleft">
					${provider.name}
					<c:if test="${provider.memberName != null}">
						<br>${provider.memberName}
							</c:if>
				</td>
				<td class="col4 even">
					${provider.primaryIndustry}
					<c:if test="${provider.primaryIndustry == null}">
							&nbsp;
							</c:if>
				</td>
				<td class="col5 odd">
					<fmt:formatDate value="${provider.lastActivityDate}"
						pattern="MM/dd/yy" />
				</td>
				<td class="col6 even">
					${provider.market}
					<c:if test="${provider.market == null}">
							&nbsp;
							</c:if>
				</td>
				<td col="col7 odd">
					${provider.state}
					<c:if test="${provider.state == null}">
							N/A
							</c:if>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${buyerProviderSelection == 0}">
<table class="globalTableLook" cellspacing="0" cellpadding="0" border="0" width="100%">
	<tr>
	<th class="col1 odd">User ID</th>
	<th class="col2 even">Funding Type</th>
	<th class="col3 odd textleft">Name</th>
	<th class="col4 even">Primary Industry</th>
	<th class="col5 odd">Last Activity</th>
	<th class="col6 even">Market</th>
	<th class="col7 odd">State</th>
	</tr>
		<c:forEach items="${providersList}" var="buyer">
			<tr>
				<td class="col1 odd">					
					<a href="javascript:showBuyerDetails(${buyer.id},${buyer.resourceId},${buyer.roleId},'${buyer.userName}','${buyer.name}','${buyer.city}, ${buyer.state}','${buyer.buyerName}');">${buyer.id}
					</a>
				</td>
				<td class="col2 even">
					${buyer.fundingType}
					<c:if test="${buyer.fundingType == null}">
							&nbsp;
					</c:if>
				</td>
				<td class="col3 odd textleft">
					${buyer.name}
					<c:if test="${buyer.name == null}">
							&nbsp;
					</c:if></td>
				<td class="col4 even">
					${buyer.primaryIndustry}
					<c:if test="${buyer.primaryIndustry == null}">
							&nbsp;
					</c:if></td>
				<td class="col5 odd">
					<fmt:formatDate value="${buyer.lastActivityDate}" pattern="MM/dd/yy" /></td>
				<td class="col6 even">
					${buyer.market}
					<c:if test="${buyer.market == null}">
							&nbsp;
					</c:if>
				</td>
				<td class="col7 odd">
					${buyer.state}
					<c:if test="${buyer.state == null}">
						N/A
					</c:if></td>
			</tr>
		</c:forEach>
</table>
</c:if>

</c:if>
</div>