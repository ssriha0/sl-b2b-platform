<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<div id="search_by_company" style="display: block">
	<div class="darkGrayModuleHdr">
		Find by Company
	</div>
	<div class="grayModuleContent mainWellContent clearfix">
		<table cellspacing="5" cellpadding="0" border=0>
			<tbody>
				<tr>
					<td width="350">
						<p>
							<label>
								Company ID Number
							</label>
							<br>
							<s:textfield theme="simple" name="companyId" id="companyId"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{companyId}" maxlength="25" />
						</p>
					</td>
					<td colspan="2">
						<p>
							<label>
								Service Order Number
							</label>
							<br />
							<s:textfield theme="simple" name="orderNumber" id="orderNumber"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{orderNumber}" maxlength="25" />

						</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>
							<label>
								Provider Firm Status
							</label>
							<br />
							<s:select id="providerFirmStatus" name="providerFirmStatus"
								headerKey="-1" headerValue="Select One" cssStyle="width: 250px;"
								size="1" theme="simple" list="%{providerFirmStatusList}"
								listKey="type" listValue="descr" />
						</p>
						
					</td>
					<td colspan="2">
						<p>
							<label>
								Business Name
							</label>
							<br>
							<s:textfield theme="simple" name="businessName" id="businessName"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{businessName}" maxlength="25" />
						</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>
							<label>
								City
							</label>
							<br>
							<s:textfield theme="simple" name="city" id="city"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{city}" maxlength="25" />
						</p>
					</td>
					<td colspan="2">
						<p>
							<label>
								User Name
							</label>
							<br>
							<s:textfield theme="simple" name="username" id="username"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{username}" maxlength="25" />
						</p>
					</td>
				</tr>
				<tr>
					<td>
						<p>
							<label>
								Phone Number
							</label>
							<br>
							<s:textfield theme="simple" name="phone" id="phone"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{phone}" maxlength="25" />
						</p>
						
					</td>
					<td width="55">
						<p>
							<label>
								State
							</label>
							<br />



							<s:select id="state1" name="state1" headerKey="-1"
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
							<s:textfield theme="simple" name="zipPart1" id="zipPart1"
								cssClass="shadowBox grayText" cssStyle="width: 60px;"
								value="%{zipPart1}" maxlength="5" />
						</p>
					</td>
				</tr>
				<tr>
					<td>
						
					</td>
					<td colspan="2">
						<p>
							<label>
								Email
							</label>
							<br>
							<s:textfield theme="simple" name="email" id="email"
								cssClass="shadowBox grayText" cssStyle="width: 250px;"
								value="%{email}" maxlength="40" />
						</p>
					</td>
				</tr>

			</tbody>
		</table>
	</div>
</div>

