<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<div id="search_by_skill" style="display: none">
	<div class="darkGrayModuleHdr">
		Find Skill
	</div>
	<div class="grayModuleContent mainWellContent clearfix">
		<table cellspacing="5" cellpadding="0" border=0>
			<tbody>
				<tr>
					<td width="250" colspan="2">
						<p>
							
						</p>
					</td>
					<td width="250">					
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<label>
								Market
							</label>
							<br>
							<s:select id="market" name="market" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="%{marketList}" listKey="type" listValue="descr" />
						</p>
					</td>
					<td>
						&nbsp;					
					</td>
				</tr>
				<tr>
					<td width="55">
						<p>
							<label>
								State
							</label>
							<br />
							<s:select id="state2" name="state2" headerKey="-1"
								headerValue="Select One" cssStyle="width: 110px;" size="1"
								theme="simple" list="#application['stateCodes']" listKey="type"
								listValue="descr" />

						</p>
					</td>
					<td>
						<p>
							<label>
								ZIP
							</label>
							<br />
							<s:textfield theme="simple" name="zipPart2" id="zipPart2"
								cssClass="shadowBox grayText" cssStyle="width: 60px;"
								value="%{zipPart2}" maxlength="5" />
						</p>
					</td>
					<td>
						<p>
							<label>
								Primary Skill
							</label>
							<br />
							<s:select id="primarySkill" name="primarySkill" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="%{primarySkillList}" listKey="type" listValue="descr" />

						</p>
					
						<%-- 
						<p>
							<label>
								Auditable Items
							</label>
							<br />
							<s:radio id="auditableItemsSelection" name="auditableItemsSelection" value="1"
								list="#{1:'Yes'}" />
							&nbsp;&nbsp;
							<s:radio id="auditableItemsSelection" name="auditableItemsSelection"
								value="1" list="#{0:'No'}" />

						</p>
						--%>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<label>
								District
							</label>
							<br />
							<s:select id="district" name="district" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="%{districtList}" listKey="type" listValue="descr" />

						</p>
					</td>
					<td>
						<p>
							<label>
								Background Status Check
							</label>
							<br />
							<s:select id="backgroundCheckStatus" name="backgroundCheckStatus" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="%{backgroundStatusCheckList}" listKey="type" listValue="descr" />

						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<label>
								Region
							</label>
							<br />
							<s:select id="region" name="region" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="%{regionList}" listKey="type" listValue="descr" />

						</p>
					</td>
					<td>
						<p>
							<label>
								Select Provider Network
							</label>
							<br />
							<s:select id="selectProviderNetwork" name="selectProviderNetwork" headerKey="-1" headerValue="Select One" cssClass="select" size="1" theme="simple" list="%{selectProviderNetworkList}" listKey="type" listValue="descr" />

						</p>
					</td>
				</tr>
			</tbody>
		</table>

	</div>
</div>

