<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="leftTileItem" id="dbTile_recvdServiceOrders">
	<div class="titleContainer">
		<div class="titleBar">
			<c:choose>
				<c:when test="${provider}"><h2>Performance & Profile Statistics</h2></c:when>
				<c:otherwise>
				<h2>Received Service Orders</h2>
				<a class="moreLink" href="serviceOrderMonitor.action?displayTab=Received">
					View &raquo;
				</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
<div class="contentContainer">
<c:choose>
	<c:when test="${provider}">
	<div class="content" style="padding-left:0; width:240px;">
	<script>function loadDefaultPixel(img){
		img.src = '/ServiceLiveWebUtil/images/pixel.gif';
	}</script>
	<div id="proImageContainer">
	<img src="${contextPath}/providerProfileInfoAction_displayPhoto.action?max_width=65&max_height=65&resourceId=<c:out value="${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}"/>" onerror="loadDefaultPixel(this);" class="left" />
	</div>
	
	
		<%-- <strong>Ratings (${dashboardDTO.numRatingsReceived})</strong><br/> --%>
		 <c:choose>
				<c:when test="${dashboardDTO.numRatingsReceived!=null}">
					<strong>Ratings (${dashboardDTO.numRatingsReceived})</strong>
				</c:when>
				<c:otherwise>
					<strong>Ratings (0)</strong>
				</c:otherwise>
			</c:choose><br/>
<c:choose>
	<c:when test="${dashboardDTO.lifetimeStarsNumber == 0}">
	<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
	</c:when>
	<c:otherwise>
	<img src="${staticContextPath}/images/common/stars_<c:out value="${dashboardDTO.lifetimeStarsNumber}"/>.gif" border="0" />
	</c:otherwise>
</c:choose> <span id="lifetimeRating" class="glossaryItem">Lifetime</span> Rating<br/>

<c:choose>
	<c:when test="${dashboardDTO.currentStarsNumber == 0}">
		<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
	</c:when>
	<c:otherwise>
	<img src="${staticContextPath}/images/common/stars_<c:out value="${dashboardDTO.currentStarsNumber}"/>.gif" border="0" />
	</c:otherwise>
</c:choose><span id="currentRating" class="glossaryItem">Current</span> Rating<br/>
<br style="clear:both;"/>
	</div>
	
		</c:when>
		<c:otherwise>
			<div class="content">
				<div class="largeDollarSign">
				<a href="serviceOrderMonitor.action?displayTab=Received">
					<img src="${staticContextPath}/images/dashboard/icoLargeDollarSign.gif"
						width="36" height="36" border="0" />
				</a>
				</div>
			<p>
				<strong>Total orders:
					<a href="serviceOrderMonitor.action?displayTab=Received">
						<span id="total_orders">
							${dashboardDTO.totalOrders}
						</span>
						</a>
				</strong>
			</p>
			<p class="last">
				<strong>
					Total dollars: $<span id="total_dollars">${totalAmtInDecimal}</span>
				</strong>
			</p>

			</div>
		</c:otherwise>
</c:choose>
</div>
	<div class="shadowBottom"></div>
</div>
