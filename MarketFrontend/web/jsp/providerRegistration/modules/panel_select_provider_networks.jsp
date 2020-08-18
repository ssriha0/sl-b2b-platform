<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	<fmt:message bundle="${serviceliveCopyBundle}" key="spn.label.spns"/>
</div>
<div class="grayModuleContent mainWellContent clearfix">
<c:choose>
	<c:when test="${spnList != null}">
		<c:forEach items="${spnList}"  var="table">
		
			<c:choose>
				<c:when test="${table.logoFileName != null}">
					<img
						src="companyProfileAction_displayLogo.action?buyerID=${table.companyID}"
						alt="Custom Logo" />
				</c:when>
				<c:otherwise>
					<img src="${staticContextPath}/images/artwork/common/logo_notagline.png" alt="ServiceLive"/>
				</c:otherwise>
			</c:choose>
			<b><fmt:message bundle="${serviceliveCopyBundle}" key="spn.profile.label.buyer"/>${table.companyName}</b> 
			<table class="scrollerTableHdr licensesTableHdr" cellpadding="0"
				cellspacing="0">
				<tr>
					<td class="column2" style="padding-left: 5px">
						<fmt:message bundle="${serviceliveCopyBundle}" key="spn.buyer.landing.label.title"/>
					</td>
					<td class="column3">
						<fmt:message bundle="${serviceliveCopyBundle}" key="spn.profile.label.status"/>
					</td>
					<td class="column4">
						<fmt:message bundle="${serviceliveCopyBundle}" key="spn.profile.label.mbr_date"/>
					</td>
					<td class="column5">
						<fmt:message bundle="${serviceliveCopyBundle}" key="spn.profile.label.info"/>
					</td>
				</tr>
			</table>
			<table class="licensesTable" cellpadding="0" cellspacing="0">
				<c:forEach items="${table.spnList}" var="row">
					<tr>
						<td class="column2" style="padding-left: 5px">
							${row.spnName}
						</td>
						<td class="column3">
							<fmt:message bundle="${serviceliveCopyBundle}" key="spn.status.${row.status}"/>
						</td>
						<td class="column4">
							<c:choose>
								<c:when test="${null != row.membershipDate}">
									<fmt:formatDate value="${row.membershipDate}" pattern="MM/dd/yy" />
								</c:when>
								<c:otherwise><fmt:message bundle="${serviceliveCopyBundle}" key="not_applicable"/></c:otherwise>
							</c:choose>
						</td>
						<td class="column5">
							<a href="spnCampaignLandingAction_displayPage.action?spnID=${row.spnId}&spnNetworkID=${row.spnNetworkId}&inviteeName=${publicProfileDto.firstName}&nbsp;${publicProfileDto.lastName}"><fmt:message bundle="${serviceliveCopyBundle}" key="spn.profile.label.view"/></a>
						</td>
					</tr>
				</c:forEach>
			</table>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<p>
			<fmt:message bundle="${serviceliveCopyBundle}" key="spn.profile.label.no_spns"/>
		</p>
</c:otherwise>
</c:choose>
</div>

