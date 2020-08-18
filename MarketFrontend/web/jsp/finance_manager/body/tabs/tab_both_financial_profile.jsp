<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.finantialProfile"/>
	</jsp:include>	
	
<s:form action="fmFinancialProfileSave_save" id="fmOverview" theme="simple"
	enctype="multipart/form-data" method="POST">
<jsp:include page="validationMessages.jsp" />
	<div class="content">
		<h3>
			<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.bankingpayment"/>
		</h3>
		<p class="paddingBtm">
			<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.bankingpayment.msg"/>
		</p>

		<div class="darkGrayModuleHdr">
			<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.accountsreceivable"/>
		</div>
		<div class="grayModuleContent mainWellContent clearfix">
			<p>
				<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.accountsreceivable.msg"/>
			</p>

			<table cellpadding="0" cellspacing="0" >
				<tr>
					<td width="70">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.title"/>
							</label>
							<br />
							<select class="grayText" onclick="changeDropdown(this)" name="account.title"
								style="width: 50px;">
								<c:choose>
								<c:when test="%{account.title == 'Mr.'}">
									<option selected="selected">Mr.</option>
								</c:when>
								<c:otherwise>
									<option>Mr.</option>
								</c:otherwise>
								</c:choose>
								<c:choose>
								<c:when test="%{account.title == 'Mrs.'}">
									<option selected="selected">Mrs.</option>
								</c:when>
								<c:otherwise>
									<option>Mrs.</option>
								</c:otherwise>
								</c:choose>
								<c:choose>
								<c:when test="%{account.title == 'Ms.'}">
									<option selected="selected">Ms.</option>
								</c:when>
								<c:otherwise>
									<option>Ms.</option>
								</c:otherwise>
								</c:choose>
								<c:choose>
								<c:when test="%{account.title == 'Dr.'}">
									<option selected="selected">Dr.</option>
								</c:when>
								<c:otherwise>
									<option>Dr.</option>
								</c:otherwise>
								</c:choose>
							</select>
						</p>
					</td>
					<td width="215">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.firstname"/>
							</label>
							<br />
							<s:textfield theme="simple" name="account.firstName" id="account.firstName"
										 cssClass="shadowBox grayText" cssStyle="width: 200px;"
										 value="%{account.firstName}"
										 maxlength="50"/>
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.middlename"/>
							</label>
							<br />
							<s:textfield theme="simple" name="account.middleName"
								id="account.middleName" cssClass="shadowBox grayText"
								cssStyle="width: 270px;" value="%{account.middleName}" maxlength="50" />
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.lastname"/>
							</label>
							<br />

							<s:textfield theme="simple" name="account.lastName"
								id="account.lastName" cssClass="shadowBox grayText"
								cssStyle="width: 270px;" value="%{account.lastName}" maxlength="50" />

						</p>
					</td>
					<td width="200">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.suffix"/>
							</label><br/>
							<s:textfield theme="simple" name="account.suffix"
								id="account.suffix" cssClass="shadowBox grayText"
								cssStyle="width: 100px;" value="%{account.suffix}" maxlength="10" />
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.mainphone"/>
							</label>
							<br />
							<tags:fieldError id="phone" oldClass="paddingBtm">
							<s:textfield theme="simple" name="account.phones[0].areaCode"
								id="account.phones[0].areaCode" cssClass="shadowBox grayText"
								cssStyle="width: 40px;" value="%{account.phones[0].areaCode}" maxlength="3" />
							-
							<s:textfield theme="simple" name="account.phones[0].phonePart1"
								id="account.phones[0].phonePart1" cssClass="shadowBox grayText"
								cssStyle="width: 40px;" value="%{account.phones[0].phonePart1}" maxlength="3" />
							-
							<s:textfield theme="simple" name="account.phones[0].phonePart2"
								id="account.phones[0].phonePart2" cssClass="shadowBox grayText"
								cssStyle="width: 50px;" value="%{account.phones[0].phonePart2}" maxlength="4" />
							</tags:fieldError>
						</p>
					</td>
					<td>
						<p>
							<br />
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.ext"/>:
							<s:textfield theme="simple" name="account.phones[0].ext"
								id="account.phones[0].ext" cssClass="shadowBox grayText"
								cssStyle="width: 60px;" value="%{account.phones[0].ext}" maxlength="5" />
						</p>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<p>
							<label>
								<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.email"/>
							</label>
							<br />
							<tags:fieldError id="Email" oldClass="paddingBtm">
							<s:textfield theme="simple" name="account.email"
								id="account.email" cssClass="shadowBox grayText"
								cssStyle="width: 270px;" value="%{account.email}" maxlength="255" />
							</tags:fieldError>
							<fmt:message bundle="${serviceliveCopyBundle}" key="fm.financialprofile.optional"/>
						</p>
					</td>

				</tr>
			</table>
		</div>
		<div class="clearfix">
			<div class="formNavButtons">
				<s:submit type="input"
						  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/saveInformation.gif);width:109px; height:20px;"
						  cssClass="btn20Bevel"
						  method="save"
						  theme="simple"
						  value=""/>
			</div>
		</div>
	</div>

</s:form>
