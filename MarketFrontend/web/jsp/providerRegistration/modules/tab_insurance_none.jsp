<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>
<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css" />
<script language="JavaScript"
	src="${staticContextPath}/javascript/js_registration/tooltip.js"
	type="text/javascript" />

<script language="JavaScript"
	src="${staticContextPath}/javascript/js_registration/formfields.js"
	type="text/javascript" />
	
<script type="text/javascript"
	src="${staticContextPath}/javascript/js_registration/utils.js"></script>


<head>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
	<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
</head>
<!-- NEW MODULE/ WIDGET-->
<body>
	<h3>
		Verify Your Coverage
	</h3>

	<s:form action="addInsuranceAction" theme="simple">
		<input type="hidden" name="method">
		<s:hidden name="credId" />
		<s:hidden name="category" />
		<p class="paddingBtm">
			ServiceLive verification shows buyers that your company is properly
			insured and ready to complete their service orders. Upload copies of
			your certificates of insurance and your account status will be
			upgraded to 'verified.'
		</p>
		<div class="darkGrayModuleHdr">
			Insurance Policies on File
		</div>
		<div class="grayModuleContent mainContentWell">
			<s:if test="%{addPolicy}">
				<p>
					Your firm does not have any insurance information on file. Use the
					box below to select the policies you'd like to add.
				</p>
			</s:if>
			<table cellpadding="0" cellspacing="0">
				<tr class="scrollerTableHdr insurancePolicyTableHdr">
					<td class="column1">
						<img
							src="${staticContextPath}/images/images_registration/common/spacer.gif"
							width="60" height="20" title="spacer" />
					</td>
					<td class="column3">
						Policy Type
					</td>
					<td class="column2">
						Carrier
					</td>
					<td class="column2">
						Expiration
					</td>
					<td class="column2">
						Documents
					</td>
					<td class="column3">
						Verified by ServiceLive
					</td>
				</tr>
				<s:iterator value="insuranceList" id="credential" status="status">
					<tr
						class="<s:if test="#status.even">even</s:if><s:else>odd</s:else>">

						<td class="column1">

							<s:if test="%{buttonType !='ADD'}">
								<s:url action="saveInsuranceActionloadInsuranceTypePage.action"
									id="url">
									<s:param name="credId" value="vendorCredentialId" />
									<s:param name="catId" value="categoryName" />
								</s:url>

								<input type="image"
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									width="72" height="20"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/edit.gif);"
									class="btn20Bevel"
									onclick="javascript:submitInsTypeInfo('<s:property value="#url"/>')" />

							</s:if>
							<s:elseif test="%{buttonType=='ADD'}">
								<s:url action="saveInsuranceActionloadInsuranceTypePage.action"
									id="url">
									<s:param name="credId" value="0" />
									<s:param name="catId" value="categoryName" />
								</s:url>
								<input type="image"
									src="${staticContextPath}/images/images_registration/common/spacer.gif"
									width="72" height="20"
									style="background-image: url(${staticContextPath}/images/images_registration/btn/add.gif);"
									class="btn20Bevel"
									onclick="javascript:submitInsTypeInfo('<s:property value="#url"/>')" />
							</s:elseif>

						</td>
						<td class="column2">
							<s:property value="categoryName" />
						</td>
						<td class="column3">
							<s:property value="source" />
						</td>
						<td class="column4">
							<s:property value="expirationDate" />
						</td>
						<td class="column5">
							<s:if
								test="%{currentDocumentId != null && currentDocumentId != 0}">
								<s:url
									action="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action"
									id="docUrl">
									<s:param name="docId" value="currentDocumentId" />
								</s:url>
								<a
									href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=<s:property value="currentDocumentId"/>"
									target="blank"> <img
										src="${staticContextPath}/images/images_registration/icons/pdf.gif"
										title="Click to view document" /> </a>
							</s:if>
							<s:else>
								N/A
							</s:else>
						</td>
						<td class="column2">
							<s:if test="%{wfStateId =='13'}">
								Pending Approval
							</s:if>
							<s:elseif test="%{wfStateId =='14'}">
								<img
									src="${staticContextPath}/images/icons/greenCheck.gif"
									width="10" height="10" alt="">
							</s:elseif>
							<s:elseif test="%{wfStateId =='-1' || wfStateId ==''} ">
					        	N/A
					        </s:elseif>
						</td>

					</tr>
				</s:iterator>
			</table>


			<s:if test="%{status != 1}">
				<p>

					<s:checkbox label="checkbox test" name="status"
						onclick="javascript:changeStatus()" />
					I do not wish to add any insurance information at this time.
				</p>
			</s:if>
		</div>
		<div class="clearfix">
			<div class="formNavButtons2">
				<input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="72" height="20"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/previous.gif);"
					class="btn20Bevel"
					onclick="javascript:submitPage('jsp/providerRegistration/listInsuranceActionloadInsurance.action')" />
				<input type="image"
					src="${staticContextPath}/images/images_registration/common/spacer.gif"
					width="50" height="20"
					style="background-image: url(${staticContextPath}/images/images_registration/btn/next.gif);"
					class="btn20Bevel"
					onclick="javascript:submitPage('jsp/providerRegistration/listInsuranceActionloadTerms.action')" />
			</div>

		</div>
		
		<!-- END TAB PANE -->
	</s:form>
	<br/>

</body>
