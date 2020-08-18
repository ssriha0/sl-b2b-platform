<%@page import="com.newco.marketplace.interfaces.OrderConstants,com.newco.marketplace.auth.SecurityContext;"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%> 
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theTab" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<c:set var="buyerRoleId" scope="page" 
value="<%=new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="simpleBuyerRoleId" scope="page" 
value="<%=new Integer(OrderConstants.SIMPLE_BUYER_ROLEID)%>" />
<c:set var="posted_tab_val" value="Posted" />
<c:if test="${theId != posted_tab_val && (role == buyerRoleId || role == simpleBuyerRoleId)}">
	<div dojoType="dijit.TitlePane" title="Increase Maximum Price"
		id="widget_increase_spend_limit_${theTab}"
		style="padding-top: 1px; width: 249px;" open="false">

		<span class="dijitInfoNodeInner"></span>

		<div class="dijitReset">
			<div class="dijitTitlePaneContentInner">
				<!-- nested divs because wipeIn()/wipeOut() doesn't work right on node w/padding etc.  Put padding on inner div. -->
				<table cellpadding="0" cellspacing="0" border="0">
					<tr>
						<td class="labelLeft">
							Current Maximum Price:
						</td>
						<td class="alignRight">
							<div id="currentAmt${theTab}" />
						</td>
					</tr>
					<tr>
						<td class="labelLeft alignRight">
							Labor Limit:
						</td>
						<td class="alignRight">
							<input type="hidden" id="currentLimitLabor${theTab}" name="currentLimitLabor"/>
							$<input type="text" id="increaseLimit${theTab}" size="8"
								name="increaseLimit" class="textbox-60 alignRight shadowBox" onkeyup="roundToSecondDecimal(this);" />
							<input type="hidden" id="maxSpendLimit${theTab}" name="maxSpendLimit" value="${SecurityContext.maxSpendLimitPerSO}"/>
						</td>
					</tr>
					<tr>
						<td class="labelLeft alignRight">
							Parts Limit:
						</td>
						<td class="alignRight">
							<input type="hidden" id="currentLimitParts${theTab}" name="currentLimitParts"/>
							
							$<input type="text" id="increaseLimitParts${theTab}" size="8"
								name="increaseLimitParts" class="textbox-60 alignRight shadowBox" onkeyup="roundToSecondDecimal(this);" />
						</td>
					</tr>					
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr class="error">
						<td colspan="2" class="errMsg alignRight">
							<div id="increaseSPendLimitResponseMessage${theTab}"></div>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td class="labelLeft">
							New Maximum Price:
						</td>
						<td class="alignRight">
							&nbsp;&nbsp;<div id="totalAmt${theTab}" />
						</td>
					</tr>
					<tr>
					<td colspan="2"><br></td>
					</tr>
					<tr>
					<td class="labelLeft">
					Reason <font color="red">*</font>  :
					</td>
					</tr>
					<c:choose><c:when test="${fn:length(spendLimitReasonCodes) > 0}">
					<tr>
					<td colspan="2">
					<select name="reason_widget" id="reason_widget${theTab}" width="300px"
																style="width: 150px;border:2px solid #CCCCCC" onchange="javascript:fnEnable();">
																<option selected="selected" value="-1">
																- Select a reason -
																</option>
																
																<c:forEach var="reasonCode" items="${spendLimitReasonCodes}">
																<option value="${reasonCode.reasonCodeId}">
																		${reasonCode.reasonCode}
																</option>
																</c:forEach>
																<option id="Other" value="-2">
																Other
																</option>
															</select>
					</td>
					
					</tr>
					<tr>
					<td colspan="2"><br></td>
					</tr>
					<tr>
						<td class="labelLeft" colspan="2">
							Notes <i>(Required if 'Other' is selected above)</i> :
						</td>

					</tr>
					<tr>
						<td colspan="2">
							<textarea id="comment_widget${theTab}"
								name="comment_widget" class="shadowBox" style="width: 200px; background: #E3E3E3;"onkeyup="limitText(this,255);" onkeydown="limitText(this,255);" disabled="disabled"
								></textarea>
						</td>
					</tr>
					</c:when>
					<c:otherwise>
					<tr>
						<td colspan="2">
							<textarea id="comment_widget${theTab}"
								name="comment_widget" class="shadowBox" style="width: 200px;" onkeyup="limitText(this,255);" onkeydown="limitText(this,255);" 
								></textarea>
						</td>
					</tr>
					</c:otherwise></c:choose>
					
				</table>
				<div style="display:block" id="titlePaneBtnsDiv">
					<ul class="titlePaneBtns" id="titlePaneBtns">
						<li>
							<a href="javascript:fnCalcSpendLimit()"> <img
									src="${staticContextPath}/images/spacer.gif" width="72" height="22"
									style="background-image: url(${staticContextPath}/images/btn/calculate.gif);"
									class="btnBevel" />
							</a>
						</li>
						<li>
							<a id="incrSpendLimitButton"
								onclick="fnSubmitIncreaseSpendLimit('${contextPath}/incSpendLimitAction.action?ss=${securityToken}',incSpendLimitCallBackFunction,null,'formHandler','comment_widget${theTab}');">
								<img src="${staticContextPath}/images/spacer.gif" width="72"
									height="22"
									id="incrSpendLimitImage"
									style="background-image: url(${staticContextPath}/images/btn/submit.gif);"
									class="btnBevel" />
							</a>
						</li>
					</ul>
				</div>
				<div id="disabledDepositFundsDiv" style="display: none">
					Depositing funds ....
				</div>
			</div>
		</div>
	</div>
</c:if>

