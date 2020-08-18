<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.overview"/>
	</jsp:include>	

<s:form action="fmOverview_exportToFile" id="fmOverview" theme="simple" enctype="multipart/form-data" method="POST">
<div class="content">
	<h3>
		Welcome to your ServiceLive Wallet
	</h3>
	<p class="paddingBtm">
		<%-- Buyer--%>
		Think of this page as your ServiceLive Wallet. Get the current status of
		all recent transactions to your ServiceLive account, including service
		orders that are currently active.

		<%-- Provider
  		Think of this page as your ServiceLive Wallet. Get the current status of all recent transactions to your ServiceLive account, along with your current balance.
  		--%>

	</p>
	<h4>
		Recent Activity
	</h4>
	<table cellpadding="0" cellspacing="0"
		class="scrollerTableHdr acctOverviewHdr">
		<tr>
			<td class="column1">
				Transaction #
			</td>
			<td class="column2">
				Date/Time
			</td>
			<td class="column3">
				Type
			</td>
			<td class="column4">
				Service Order #
			</td>
			<td class="column5">
				Amount
			</td>
			<td class="column6">
				Balance
			</td>
		</tr>
	</table>
<div class="grayTableContainer" style="width: 698px; height: 600px;">
	<!-- <table cellpadding="0" cellspacing="0" class="gridTable acctOverview">
			<c:forEach items="${transactionList}" var="transaction">
				<tr>
					<td class="column1">
						${transaction.transactionNumber}
					</td>
					<td class="column2">
						${transaction.date}
						<br />
						${transaction.time}
					</td>
					<td class="column3">
						${transaction.type}
					</td>
					<td class="column4">
						<a href="soDetailsController.action?soId=${transaction.soId}">${transaction.soId}</a>
					</td>
					<td class="column5">
						<c:choose>
							<c:when test="${transaction.amountRed}">
								<span class="red">${transaction.amount}</span>
							</c:when>
							<c:otherwise>
								${transaction.amount}
							</c:otherwise>
						</c:choose>
					</td>
					<td class="column6">
						<c:choose>
							<c:when test="${transaction.balanceRed}">
								<span class="red">${transaction.balanceDesc}</span>
							</c:when>
							<c:otherwise>
								${transaction.balanceDesc}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</c:forEach>
		</table>  -->	
	</div>
	<div class="clearfix">
		<p style="padding-left: 5px;">
			<span> <select class="grayText" onclick="changeDropdown(this)"
					style="width: 115px;">
					<option>
						Select One
					</option>
				</select> </span>
				
			<input width="101" type="image" height="20"
				class="btn20Bevel inlineBtn"
				style="background-image: url(${staticContextPath}/images/btn/exportToFile.gif);"
				src="${staticContextPath}/images/common/spacer.gif" />
		</p>
	</div>
</div>
</s:form>
