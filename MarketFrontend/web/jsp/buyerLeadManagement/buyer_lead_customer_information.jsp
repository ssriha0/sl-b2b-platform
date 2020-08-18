<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<form id="leadEdit" method="post">

	<input type="hidden" name="leadId" value="${leadsDetails.slLeadId}" />
	<input type="hidden" id="selectedId" value=""/>
	<table cellpadding="10" cellspacing="15" width="100%" border="0">

		<tr>
			<td width=50%><span id="nameDiv"><b>Name:</b><b>
			<c:choose>
 						<c:when test="${not empty leadsDetails.firstName || not empty leadsDetails.lastName}">${leadsDetails.firstName}
						${leadsDetails.lastName}</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
						</b>&nbsp; <span id="editLink"><a
						id="editname" href="javascript:void(0);"
						onclick="ProjectNew.editField(this.id);">Edit</a></span>
						<input id="editedfirstName" type="hidden" value="${leadsDetails.firstName}"/>
						  <input id="editedlastName" type="hidden" value="${leadsDetails.lastName}"/>
						   <input id="editedmembershipId" type="hidden" value="${leadsDetails.membershipId}"/>
						    <input id="editedemail" type="hidden" value="${leadsDetails.email}"/>
						     <input id="editedstreet1" type="hidden" value="${leadsDetails.street1}"/>
						      <input id="editedstreet2" type="hidden" value="${leadsDetails.street2}"/>
						      <input id="editedphone" type="hidden" value="${leadsDetails.phoneNo}"/>
			</span>


				<div id="editnameDiv" style="display: none">
					<b> First Name:</b> <input id="firstName" type="text"
						name="firstName" value="${leadsDetails.firstName}"
						onblur="ProjectNew.reset(this.id,'${leadsDetails.firstName}');" /><br />
					<br /> <b> Last Name:</b> <input id="lastName" type="text"
						name="lastName" value="${leadsDetails.lastName}"
						onblur="ProjectNew.reset(this.id, '${leadsDetails.lastName}');" /><br />
					<br /> <input class="btnSubmit" type="button" value="Submit"
						onclick="submitLead();" /> | <input id="cancelname"
						class="btnSubmit" type="button" value="Cancel"
						onclick="ProjectNew.resetName(this.id,'${leadsDetails.firstName}','${leadsDetails.lastName}');ProjectNew.hideEditableFields(this.id);" />
				</div></td>

			<td><span id="membershipDiv"> <b> Shop Your Way
						MemberShip #: </b><c:choose><c:when test="${not empty leadsDetails.membershipId}">${leadsDetails.membershipId}</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>&nbsp; <span
					id="editLink"><a id="editmembership"
						href="javascript:void(0);"
						onclick="ProjectNew.editField(this.id);">Edit</a></span>
						<input type="hidden" id="memShipId" value="${leadsDetails.membershipId}"/>
						<input type="hidden" id="leadID" value="${leadsDetails.slLeadId}"/>
			</span>

				<div id="editmembershipDiv" style="display: none">
					<b> Shop Your Way MemberShip#:</b> <input id="membershipId"
						type="text" name="shopYourWayId"
						value="${leadsDetails.membershipId}"
						onblur="ProjectNew.reset(this.id, '${leadsDetails.membershipId}');" maxlength="50"/><br />
					<br /> <input class="btnSubmit" type="button" value="Submit"
						onclick="submitLead();" /> | <input id="cancelmembership"
						class="btnSubmit" type="button" value="Cancel"
						onclick="ProjectNew.resetMembershipId(this.id,'${leadsDetails.membershipId}');ProjectNew.hideEditableFields(this.id);" />
				</div></td>
		</tr>

		<tr>
			<td width=50%><span id="addressDiv"><b>Address:</b>
					${leadsDetails.street1} <c:if
						test="${not empty leadsDetails.street2 }">, ${leadsDetails.street2}&nbsp;</c:if>
					<span id="editLink"><a id="editaddress"
						href="javascript:void(0);"
						onclick="ProjectNew.editField(this.id);">Edit</a></span>
			</span>

				<div id="editaddressDiv" style="display: none">

					<b> Street 1:</b> <input id="street1" type="text" name="street1"
						value="${leadsDetails.street1}"
						onblur="ProjectNew.reset(this.id, '${leadsDetails.street1}');" /><br />
					<br /> <b> Street 2:</b> <input id="street2" type="text"
						name="street2" value="${leadsDetails.street2}"
						onblur="ProjectNew.reset(this.id, '${leadsDetails.street2}');" /><br />
					<br /> <input class="btnSubmit" type="button" value="Submit"
						onclick="submitLead();" /> | <input id="canceladdress"
						class="btnSubmit" type="button" value="Cancel"
						onclick="ProjectNew.resetAddress(this.id,'${leadsDetails.street1}','${leadsDetails.street2}');ProjectNew.hideEditableFields(this.id);" />
				</div></td>

			<td style="padding-left: 82px;"><b>Points Awarded: </b>
			<c:choose><c:when test="${not empty leadsDetails.reward}">${leadsDetails.reward}</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose></td>

		</tr>

		<tr>
			<td width=50%><span id="stateDiv"> ${leadsDetails.city}<c:if
						test="${not empty leadsDetails.state}">,${leadsDetails.state}</c:if>
					<c:if test="${not empty leadsDetails.zip}">,${leadsDetails.zip}&nbsp;</c:if>
					<%-- <span id="editLink"><a id="editstate"
														href="javascript:void(0);"
														onclick="ProjectNew.editField(this.id);">Edit</a></span>--%>
			</span>


				<div id="editstateDiv" style="display: none">

					<b> City:</b> <input id="city" type="text" name="city"
						value="${leadsDetails.city}"
						onblur="ProjectNew.reset(this.id, '${leadsDetails.city}');" /> <br />
					<%-- <br /> <b>State:</b> <select id="state" name="state"
														style="width: 38%">
														<c:forEach items="${application['stateCodes']}"
															var="state">
															<c:choose>
																<c:when test="${state == leadsDetails.state}">
																	<option value="${state.type}" selected="selected">${state.descr}</option>
																</c:when>
																<c:otherwise>
																	<option value="${state.type}">${state.descr}</option>
																</c:otherwise>
															</c:choose>
														</c:forEach>
													</select><br />
													<br /> <b> Zip:</b> <input id="zip" type="text" name="zip"
														value="${leadDetails.zip}"
														onblur="ProjectNew.reset(this.id, '${leadDetails.zip}');" /><br />
													<br /> <input class="btnSubmit" type="submit"
														value="Submit" onclick="submitLead()" /> | <input
														id="cancelstate" class="btnSubmit" type="button"
														value="Cancel"
														onclick="ProjectNew.hideEditableFields(this.id);" /> --%>
				</div></td>
		</tr>

		<tr>
			<td width=50%><span id="phoneDiv"><b>Phone:</b>
					${leadsDetails.phoneNo}&nbsp; <span id="editLink"><a
						id="editphone" href="javascript:void(0);"
						onclick="ProjectNew.editField(this.id);">Edit</a></span> </span>
			


				<div id="editphoneDiv" style="display: none">
					<b>Phone:</b>
					<c:forEach items="${phoneNoList}" var="phone" varStatus="i">
					<c:if test="${i.count<=2}">
					<input id="phone_${i.count}" type="text" name="phoneNo${i.count}"
						maxlength="3" style="width:30px" value="${phone}"
						onblur="ProjectNew.reset(this.id, '${phone}');" />
						-
					</c:if>
					<c:if test="${i.count==3}">
					<input id="phone_${i.count}" type="text" name="phoneNo${i.count}"
						maxlength="4" style="width:40px" value="${phone}"
						onblur="ProjectNew.reset(this.id, '${phone}');" />
					</c:if>
					
					</c:forEach>
					<br />
					<br /> <input class="btnSubmit" value="Submit" type="button"
						onclick="validate();" /> | <input id="cancelphone"
						class="btnSubmit" type="button" value="Cancel"
						onclick="ProjectNew.resetValue(this.id,'${leadsDetails.phoneNo}');ProjectNew.hideEditableFields(this.id);" />
				</div></td>
			<td></td>
		</tr>

		<tr>
			<td width=50%><span id="emailDiv"><b>Email:</b>
			<c:choose><c:when test="${not empty leadsDetails.email}"><a href='mailto:${leadsDetails.email}'>
			           ${leadsDetails.email}</a></c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
					&nbsp; <span id="editLink"><a
						id="editemail" href="javascript:void(0);"
						onclick="ProjectNew.editField(this.id);">Edit</a></span> </span>

				<div id="editemailDiv" style="display: none">
					<b>Email:</b> <input id="email" type="text" name="email"
						value="${leadsDetails.email}"
						onblur="ProjectNew.reset(this.id, '${leadsDetails.email}');" /><br />
					<br /> <input class="btnSubmit" type="button" value="Submit"
						onclick="validateEmail();" /> | <input id="cancelemail"
						class="btnSubmit" type="button" value="Cancel"
						onclick="ProjectNew.resetValue(this.id,'${leadsDetails.email}');ProjectNew.hideEditableFields(this.id);" />
				</div></td>
			<td></td>
		</tr>
	</table>
</form>

