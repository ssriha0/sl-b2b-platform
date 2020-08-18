
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="leftTileItem" id="dbTile_serviceOrders">
	<div class="titleContainer">
		<div class="titleBar">
			<h2>Service Orders</h2>
			<a class="moreLink" href="serviceOrderMonitor.action">View &raquo;
			</a>
		</div>
	</div>
	<div class="contentContainer">
		<div class="content">
			<p>
				<a href="soWizardController.action?action=create">
					<img src="${staticContextPath}/images/dashboard/icoPencilAdd.gif" width="28"
						height="26" border="0" align="left" class="icon" />
				</a>
				<strong>
					<c:choose>
					<c:when test="${userRole == 'SimpleBuyer' }">
						<a href="ssoController_execute.action?appMode=CREATE&view=ssoSelectLocation">
							Create a Service Order
						</a>
					</c:when>
					<c:otherwise>
						<a href="soWizardController.action?action=create">
							Create a Service Order
						</a>
					</c:otherwise>
					</c:choose>	
				</strong>
			</p>
			<c:choose>
			<c:when test="${userRole != 'SimpleBuyer'}">
			<p>
				<a href="serviceOrderMonitor.action?displayTab=Today">
					<img src="${staticContextPath}/images/dashboard/icoDollarSign.gif" width="28"
						height="26" border="0" align="left" class="icon" />
				</a>
				<strong>
					Today's Orders:	
				  <c:choose>
					<c:when test="${dashboardDTO.todays == 0}">						
							<span id="todays">
								${dashboardDTO.todays}
							</span>						
					</c:when>	
					<c:otherwise>
						<a href="serviceOrderMonitor.action?displayTab=Today">
							<span id="todays">
								${dashboardDTO.todays}
							</span>
						</a>
					</c:otherwise>
				  </c:choose>	
				</strong>
			</p>
			</c:when>
			</c:choose>
			<p>
				<a href="serviceOrderMonitor.action?displayTab=Posted">
					<img src="${staticContextPath}/images/dashboard/icoPin.gif"
						width="28" height="26" border="0" align="left" class="icon" />
				</a>
				<strong>				 
					Posted Service Orders:
					<c:choose>
					  <c:when test="${dashboardDTO.posted == 0}">						
							<span id="posted">
								${dashboardDTO.posted}
							</span>
					  </c:when>	
					  <c:otherwise>
					    <a href="serviceOrderMonitor.action?displayTab=Posted" >
							<span id="posted">
								${dashboardDTO.posted}
							</span>
						</a>
					  </c:otherwise>
 				    </c:choose>
				</strong>
			</p>
		   
		</div>
	</div>
	<div class="shadowBottom"></div>
</div>
