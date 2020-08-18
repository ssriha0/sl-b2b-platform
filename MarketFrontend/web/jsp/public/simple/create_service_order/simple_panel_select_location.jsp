<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="validateLocation" value="<%=request.getAttribute("validateLocation")%>"/>
<c:set var="valLocationSelect" value="validateLocation"/>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />




<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"></script>

<script language="javascript" type="text/javascript">
			var djConfig = {
				isDebug: true, 
				parseOnLoad: true
			};		
			
</script>


<c:if test="${errors[0] != null}">
<c:if test="${validateLocation!= valLocationSelect}">
	<div class="errorBox clearfix" width="100%">
		<script language="javascript" type="text/javascript">
					jQuery(document).ready(function($){	
					//show address box if there are errors.
						$('#newAddress').show();
					});					
		</script>
	</div>
	</c:if>
 </c:if>


<div id="hpWrap" class="shaded clearfix">

	<div id="hpContent">

		<jsp:include page="/jsp/buyer_admin/validationMessages.jsp" />

		<div id="existingAddress" class="hpDescribe clearfix">


			<p>
				Choose one of your saved addresses below, or create a new location
				to find the service professionals in your area.
			</p>

			<h3>
				Your Saved Locations
			</h3>

			<table width="100%" border=0>
				<tr>
					<td width="100%">
						<s:iterator value="locations" status="status">
							<c:choose>
								<c:when test="${status.index % 2 <= 0}">
									<div class="left addresslabel">
										<input name="existingAddr" id="existingAddr" type="radio"
											style="display: none;"
											value="${locations[status.index].locnId}" />
										<strong>${locations[status.index].locName}</strong>
										<br />
										${locations[status.index].street1}
										<c:if test="${locations[status.index].street2 != ''}">
											<br />
												${locations[status.index].street2}
												</c:if>
										<c:if test="${locations[status.index].aptNo != ''}">
											<br />
												apt: ${locations[status.index].aptNo}
												</c:if>
										<br />
										${locations[status.index].city},
										${locations[status.index].state}
										${locations[status.index].zip}
									</div>
								</c:when>
								<c:otherwise>
									<div class="right addresslabel">
										<input name="existingAddr" id="existingAddr" type="radio"
											style="display: none;"
											value="${locations[status.index].locnId}" />
										<strong>${locations[status.index].locName}</strong>
										<br />
										${locations[status.index].street1}
										<c:if test="${locations[status.index].street2 != ''}">
											<br />
												${locations[status.index].street2}
												</c:if>
										<c:if test="${locations[status.index].aptNo != ''}">
											<br />
												apt: ${locations[status.index].aptNo}
												</c:if>
										<br />
										${locations[status.index].city},
										${locations[status.index].state}
										${locations[status.index].zip}
									</div>
								</c:otherwise>
							</c:choose>

						</s:iterator>
					</td>
				</tr>
				<tr>
					<td>
						<a id="createNewAddress" href="#createNewAddress"
							style="font-weight: bold; font-size: 12px;">Add New Location</a>
					</td>
					<%-- 
							<td>
								<a id="createNew" href="#"
									style="font-weight: bold; font-size: 12px;">Delete Selected
									Location</a>								
							</td>
							--%>

				</tr>
			</table>
			
			
			
			<div id="newAddress" class=" hidden clearfix">
		
				<!-- <h3>Add New Location</h3>	-->


				<label>
					<span>Location Name <em class="req">*</em> </span>
					<s:textfield id="newLocation.locName" name="newLocation.locName"
						cssClass="shadowBox" cssStyle="width: 200px" /> <em>(ex. Vacation Home or Moms House)</em>
				</label>
				<label>
					<span>Street Address 1 <em class="req">*</em> </span>
					<s:textfield id="newLocation.street1" name="newLocation.street1"
						cssClass="shadowBox" cssStyle="width: 200px" />
				</label>
				<label>
					<span>Street Address 2 <em class="req">&nbsp;</em>
					</span>
					<s:textfield id="newLocation.street2" name="newLocation.street2"
						cssClass="shadowBox" cssStyle="width: 200px" />
				</label>
				<label>
					<span>Apartment Number <em class="req">&nbsp;</em>
					</span>
					<s:textfield id="newLocation.aptNo" name="newLocation.aptNo"
						cssClass="shadowBox" cssStyle="width: 200px" />
				</label>
				<label>
					<span>City <em class="req">*</em> </span>
					<s:textfield id="newLocation.city" name="newLocation.city"
						cssClass="shadowBox" cssStyle="width: 200px" />
				</label>
				<label>
					<span>State <em class="req">*</em> </span>
					<s:select id="newLocation.state" name="newLocation.state"
						headerKey="-1" headerValue="Select One"
						value="%{newLocation.state}" list="#application['stateCodes']"
						listKey="type" listValue="descr" cssStyle="width: 200px;"
						cssClass="shadowBox grayText">
					</s:select>
				</label>

				<label>
					<span>Zip <em class="req">*</em> </span>
					<s:textfield id="newLocation.zip" name="newLocation.zip"
						cssClass="shadowBox blackText" cssStyle="width: 100px"
						maxlength="5" />
				</label>

				<s:submit type="image" method="addNewLocation"
					src="%{#request['staticContextPath']}/images/common/spacer.gif"
					cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/add.gif); width:35px; height:20px;"
					cssClass="btn20Bevel" theme="simple" value=""/>

			</div>
		</div>
	</div>
</div>
						

			
				


<!-- acquity: empty divs to ajax the modal content into -->

<div id="serviceFinder" class="jqmWindow"></div>
<div id="modal123" class="jqmWindowSteps"></div>
<div id="zipCheck" class="jqmWindow"></div>
