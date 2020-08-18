<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", -1);
%>

<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
 @import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/utils.js" type="text/javascript"></script>

<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
        <meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
 <script type="text/javascript">
	function setAction(action,dto){
		jQuery("#"+dto+"Action").val(action);
	}
</script>


</head>

<div id="content_right_header_text">
   	<%@ include file="../message.jsp"%>
</div>

<s:form name="businessInfo" action="businessinfoAction" theme="simple" method="post" validate="true">
<input type="hidden" name="businessinfoDto.locnIdB" value="${businessinfoDto.locnIdB}" />
<input type="hidden" name="businessinfoDto.locnId" value="${businessinfoDto.locnId}" />
<input type="hidden" name="businessinfoDto.businessName" value="${businessinfoDto.businessName}" />
<input type="hidden" name="businessinfoDto.dbaName" value="${businessinfoDto.dbaName}" />
<input type="hidden" name="businessinfoDto.einNo" value="${businessinfoDto.einNo}" />


<input type="hidden" name="businessinfoDto.firstName" value="${businessinfoDto.firstName}" />
<input type="hidden" name="businessinfoDto.middleName" value="${businessinfoDto.middleName}" />
<input type="hidden" name="businessinfoDto.lastName" value="${businessinfoDto.lastName}" />
<input type="hidden" name="businessinfoDto.nameSuffix" value="${businessinfoDto.nameSuffix}" />
<input id="businessinfoDtoAction" type="hidden" name="businessinfoDto.action" value="${businessinfoDto.action}" />

    <!-- Changes Starts for Admin Name Change -->
     <input type="hidden" name="businessinfoDto.newAdminResourceId" id="newAdminResourceId"
		value="${businessinfoDto.newAdminResourceId}" />
	<input type="hidden" name="businessinfoDto.newAdminName" id="newAdminName"
		value="${businessinfoDto.newAdminName}" />
	<s:set var="resourceId" value="%{businessinfoDto.resID}" scope="session"> </s:set>
     <!-- Changes Ends for Admin Name Change -->
<s:set name="addUserInd" value="false"></s:set>




<p class="paddingBtm">Use the fields below to edit your business and contact information. Some fields are locked for your protection. If you need to make changes, contact your ServiceLive administrator.

 </p>

<!-- NEW MODULE/ WIDGET-->
<p><b>An asterix (<span class="req">*</span>) indicates a required field</b></p> <br/>
<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Business Information</div>
<div class="grayModuleContent mainContentWell">
	<div id="descriptionError" class="errorBox clearfix" style="width: 675px; visibility:hidden;">
		<p class="errorMsg">
			&nbsp;&nbsp;&nbsp;&nbsp;Description - Enter Valid Description 
		</p>
	</div>

  <table cellpadding="0" cellspacing="0" >
  							<tr>
							<td width="340">
							<p>	Legal Business Name<br />
                   				 <s:property value="businessinfoDto.businessName"/> </p> </td>

                   			<td width="340">
							<p>
                				Doing Business As (DBA)<br />
                  				 <s:property value="businessinfoDto.dbaName"/></p>
                			</td>
							</tr>
							<tr>
									<c:choose>
									<c:when test="${fieldErrors['businessinfoDto.mainBusiPhoneNo1'] != null or fieldErrors['businessinfoDto.mainBusiPhoneNo2'] != null or fieldErrors['businessinfoDto.mainBusiPhoneNo3'] != null}">
												<td class="errorBox">
											</c:when>
											<c:otherwise>
												<td class="paddingBtm">
											</c:otherwise>
											</c:choose>
											<label>
												Main Business Phone<span class="req">*</span>
											</label>
											<br />
											<s:textfield name="businessinfoDto.mainBusiPhoneNo1"
												value="%{businessinfoDto.mainBusiPhoneNo1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />

											<s:textfield name="businessinfoDto.mainBusiPhoneNo2"
												value="%{businessinfoDto.mainBusiPhoneNo2}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />


											<s:textfield name="businessinfoDto.mainBusiPhoneNo3"
												value="%{businessinfoDto.mainBusiPhoneNo3}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />

											<c:choose>
											<c:when test="${fieldErrors['businessinfoDto.mainBusinessExtn'] != null }">
											<td class="errorBox">&gt;
											</c:when>
											<c:otherwise>



												<label> Ext.
												</label>
												<s:textfield name="businessinfoDto.busPhoneExtn"
												value="%{businessinfoDto.busPhoneExtn}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
												</c:otherwise>
											  </c:choose>
										<td width="650" >
										<c:choose>
										<c:when
												test="${fieldErrors['businessinfoDto.businessFax1'] != null or fieldErrors['businessinfoDto.businessFax2'] != null or fieldErrors['businessinfoDto.businessFax3'] != null}">
												<p class="errorBox">
											</c:when>
											<c:otherwise>
												<p class="paddingBtm">
											</c:otherwise>
										</c:choose>
											<label>
												Business Fax
											</label>
											<br />
											<s:textfield name="businessinfoDto.businessFax1"
												value="%{businessinfoDto.businessFax1}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />

											<s:textfield name="businessinfoDto.businessFax2"
												value="%{businessinfoDto.businessFax2}"
												 maxlength="3"
												cssStyle="width: 30px;" cssClass="shadowBox grayText" />

											<s:textfield name="businessinfoDto.businessFax3"
												value="%{businessinfoDto.businessFax3}"
												 maxlength="4"
												cssStyle="width: 45px;" cssClass="shadowBox grayText" />
											Optional

										</td>


									</tr>



    	<tr>
    	<td width="340">
             <p>	Taxpayer ID (EIN or SSN)<br />
         <s:property value="businessinfoDto.einNo"/>
         </p></td>
      <td width="340">
        <p>
        <label>Dun & BradStreet (DUNS) Number/Optional</label><br />
        <s:textfield name="businessinfoDto.dunsNo" value="%{businessinfoDto.dunsNo}" theme="simple" cssStyle="width: 250px;" cssClass="shadowBox grayText"/>
        </p></td>

    </tr>
    <tr><td>
       <c:choose>
       <c:when test="${fieldErrors['businessinfoDto.busStructure'] == null}">
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
    <label>Business Structure</label><br />
      <s:select name="businessinfoDto.busStructure"
    headerValue="-- Please Select --"
    headerKey=""
    value="businessinfoDto.busStructure"
	list="businessinfoDto.mapBusStructure"
	listKey="id" listValue="descr"
	theme="simple" cssStyle="width: 250px;" cssClass="grayText" disabled="false" ></s:select>

		<c:choose>
       <c:when test="${fieldErrors['businessinfoDto.isForeignOwned'] == null}">
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
        Is the business foreign owned?<br />

<span class="formFieldOffset">
<s:radio  onclick="performBusForeignOwned(this,'businessinfoDto.foreignOwnedPct');" name="businessinfoDto.isForeignOwned" value="%{businessinfoDto.isForeignOwned}" theme="simple" cssClass="antiRadioOffsets" list="#{1:'Yes',0:'No'}"  />
    	</span>
		
	   <c:choose>
       <c:when test="${fieldErrors['businessinfoDto.companySize'] == null}">
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
      <label>Size of Company<span class="req">*</span></label><br />
       <s:select name="businessinfoDto.companySize"
       headerValue="-- Please Select --"
        headerKey=""
        value="businessinfoDto.companySize"
		list="businessinfoDto.mapCompanySize"
		listKey="id" listValue="descr"
		theme="simple" cssStyle="width: 250px;" cssClass="grayText"></s:select>

		<c:choose>
		<c:when
				test="${fieldErrors['businessinfoDto.primaryIndustry'] == null}">
				<p class="paddingBtm">
			</c:when>
			<c:otherwise>
				<p class="errorBox">
			</c:otherwise>
		</c:choose>
			<label>
				Industry<span class="req">*</span>
			</label>
			<br />
			<s:select name="businessinfoDto.primaryIndustry"
				value="businessinfoDto.primaryIndustry"
				list="businessinfoDto.primaryIndList" headerKey=""
				headerValue="---------- Please Select ---------"
				listKey="id" listValue="descr" cssStyle="width:250px;"
				cssClass="grayText" onclick="changeDropdown(this)"></s:select>

      <!--
          <select style="width: 156px;" class="grayText" onclick="changeDropdown(this)">
            <option>Select One</option>
          </select>
           -->

        </td>
        <td>
        <c:choose>
         <c:when test="${fieldErrors['businessinfoDto.busStartDt'] == null}">
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
        <label>Business Started <span class="req">*</span></label><br /> 

        <input type="text" dojoType="dijit.form.DateTextBox"
        					constraints="{datePattern:'MM/dd/yyyy'}"
									class="shadowBox" id="modal2ConditionalChangeDate1"
									name="businessinfoDto.busStartDt"
	 								value="<s:property value="%{businessinfoDto.busStartDt}"/>"
	 								theme="simple" cssStyle="width: 75px;" cssClass="shadowBox grayText"/>




		<c:choose>
       <c:when test="${fieldErrors['businessinfoDto.foreignOwnedPct'] == null}">
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
        <label>Foreign Owned Percentage</label><br />
        <c:if test="${businessinfoDto.isForeignOwned == 0}">
        	<s:set name="percentDisable" value="true"></s:set>
        </c:if>
            <s:select name="businessinfoDto.foreignOwnedPct"
            headerValue="-- Please Select --"
            headerKey="" value="businessinfoDto.foreignOwnedPct"
            list="businessinfoDto.mapForeignOwnedPct"
            listKey="id" listValue="descr"
            theme="simple" cssClass="grayText" cssStyle="width: 250px;"
            disabled="%{#attr['percentDisable']}" />


	   <c:choose>
       <c:when test="${fieldErrors['businessinfoDto.annualSalesRevenue'] == null}">
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
        <label>Annual Sales Revenue<span class="req">*</span></label><br />
       <p> <s:select name="businessinfoDto.annualSalesRevenue" headerValue="-- Please Select --"
        headerKey="" value="businessinfoDto.annualSalesRevenue"
        listKey="id" listValue="descr"
		list="businessinfoDto.mapAnnualSalesRevenue" theme="simple" cssClass="grayText" cssStyle="width: 250px;" ></s:select></p>
		<p>
		<label>
				Website Address
			</label></br>

			<s:textfield name="businessinfoDto.webAddress"
				value="%{businessinfoDto.webAddress}"
				 maxlength="255" cssStyle="width: 250px;"
				cssClass="shadowBox grayText" />optional
			</p>
         </td>
         </tr>


  </table>





<table>
<tr>
<td width="650">
		<div>
		<c:choose>
		<c:when test="${fieldErrors['businessinfoDto.description'] == null}">
     		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
<label><b>Business Description<span class="req">*</span></b></label><br />
 <label>A well-written description of your company can be a powerful sales tool on ServiceLive. You can use up to 1,000 words to describe your company in the field below. Remember, this description will be part of your public profile. Do not include sensitive information such as names, phone numbers or Web addresses.
</label>
 <br />
 <textarea name="businessinfoDto.description" id="businessinfoDto.description" theme="simple" cssClass="shadowBox grayText" style="width: 650px; height: 150px;"  >${businessinfoDto.description} </textarea>
</td>
</tr>
</table>
</div>
<!-- SL-21446 -->
<div class="darkGrayModuleHdr">Company Logo</div>
<div class="grayModuleContent mainContentWell">
	<div id="descriptionErrorLogo" class="errorBox clearfix" style="width: 675px; visibility:hidden;">
		<p class="errorMsg">
		</p>				
	</div>
	<iframe id="uploadPics" name="uploadPicsiframeID"
			src="firmLogoAction_display.action?vendorId=${vendorId}" 
			width="690" height="125" FRAMEBORDER="0" scrolling="no" marginwidth="0" marginheight="0">
	</iframe>
</div>
</div>
<div class="darkGrayModuleHdr">
								Business Address
							</div>

							<div class="grayModuleContent">
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.businessStreet1'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Street Name<span class="req">*</span>
											</label>
											<br />
											<s:textfield id="businessStreet1"
												name="businessinfoDto.businessStreet1"
												value="%{businessinfoDto.businessStreet1}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.businessStreet2'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<s:textfield id="businessStreet2"
												name="businessinfoDto.businessStreet2"
												value="%{businessinfoDto.businessStreet2}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

										</td>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.businessAprt'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Apt. #
											</label>
											<br />
											<s:textfield id="businessAprt"
												name="businessinfoDto.businessAprt"
												value="%{businessinfoDto.businessAprt}"
												 maxlength="10" cssStyle="width: 100px;"
												cssClass="shadowBox grayText" />

											<p style="line-height: 14px;">
												&nbsp;
											</p>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td width="205">
													<c:choose>
														<c:when
															test="${fieldErrors['businessinfoDto.businessCity'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
													</c:choose>
														<label>
															City<span class="req">*</span>
														</label>
														<br />
														<s:textfield id="businessCity"
															name="businessinfoDto.businessCity"
															value="%{businessinfoDto.businessCity}"
															 maxlength="30" cssStyle="width: 190px;"
															cssClass="shadowBox grayText" />

													</td>
													<td width="110">
														<label>
															State<span class="req">*</span>
														</label>
														<br />

														<p><s:select id="businessState"
															name="businessinfoDto.businessState"
															value="%{businessinfoDto.businessState}"
															list="#application['stateCodes']" listKey="type"
															listValue="descr" cssStyle="width: 100px;"
															cssClass="shadowBox grayText">
														</s:select><p>

													</td>
													<td width="75">
														<c:choose>
														<c:when
															test="${fieldErrors['businessinfoDto.businessZip'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
														</c:choose>
														<label>
															ZIP<span class="req">*</span>
														</label>
														<br />

														<s:textfield id="businessZip"
															name="businessinfoDto.businessZip"
															value="%{businessinfoDto.businessZip}"
															 maxlength="10" cssStyle="width: 70px;"
															cssClass="shadowBox grayText" />

													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>


		<div class="darkGrayModuleHdr">
								Mailing Address
							</div>
							<div class="grayModuleContent">
								<p>
									<s:checkbox name="businessinfoDto.mailAddressChk"
										onclick="javascript:copyValue1();" />
									The mailing address is the same as the business address.
								</p>
								<table cellpadding="0" cellspacing="0" width="650">
									<tr>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.mailingStreet1'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Street Name
											</label>
											<br />
											<s:textfield id="mailingStreet1"
												name="businessinfoDto.mailingStreet1"
												value="%{businessinfoDto.mailingStreet1}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.mailingStreet2'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<s:textfield id="mailingStreet2"
												name="businessinfoDto.mailingStreet2"
												value="%{businessinfoDto.mailingStreet2}"
												 maxlength="40" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

										</td>
										<td width="325">
											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.mailingAprt'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Apt. #
											</label>
											<br />
											<s:textfield id="mailingAprt"
												name="businessinfoDto.mailingAprt"
												value="%{businessinfoDto.mailingAprt}"
												 maxlength="10" cssStyle="width: 100px;"
												cssClass="shadowBox grayText" />

											<p style="line-height: 14px;">
												&nbsp;
											</p>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<table cellpadding="0" cellspacing="0">
												<tr>
													<td width="205">
														<c:choose>
														<c:when
															test="${fieldErrors['businessinfoDto.mailingCity'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
														</c:choose>
														<label>
															City<span class="req">*</span>
														</label>
														<br />
														<s:textfield id="mailingCity"
															name="businessinfoDto.mailingCity"
															value="%{businessinfoDto.mailingCity}"
															 maxlength="30" cssStyle="width: 190px;"
															cssClass="shadowBox grayText" />

													</td>
													<td width="110">
														<p>
															<label>
																State<span class="req">*</span>
															</label>
															<br />
															<s:select id="mailingState"
																name="businessinfoDto.mailingState"
																value="%{businessinfoDto.mailingState}"
																list="#application['stateCodes']" listKey="type"
																listValue="descr" cssStyle="width: 100px;"
																cssClass="shadowBox grayText">
															</s:select>
														</p>
													</td>
													<td width="75">
													<c:choose>
														<c:when
															test="${fieldErrors['businessinfoDto.mailingZip'] == null}">
															<p class="paddingBtm">
														</c:when>
														<c:otherwise>
															<p class="errorBox">
														</c:otherwise>
													</c:choose>
														<label>
															ZIP<span class="req">*</span>
														</label>
														<br />
														<s:textfield id="mailingZip"
															name="businessinfoDto.mailingZip"
															value="%{businessinfoDto.mailingZip}"
															 maxlength="10" cssStyle="width: 70px;"
															cssClass="shadowBox grayText" />

													</td>
												</tr>
											</table>
										</td>
									</tr>
								</table>
							</div>
							<div class="darkGrayModuleHdr">
								Primary Contact Information - Administrator
							</div>
							<div class="grayModuleContent">
								<p>
										The following person is registered as the ServiceLive administrator
										for your company. Contact ServiceLive support if you would like to
										make another person your administrator.
								</p>

						<table cellpadding="0" cellspacing="0" width="650">
								<tr>
								<td width="350">
							<label>Role Within Company (Check all that apply)</label>
                			<table cellpadding="0" cellspacing="0">	                	<tr>
                    	<td width="150">
                    		<p><input type="checkbox" name="businessinfoDto.ownerInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${businessinfoDto.ownerInd == 1}"> checked="checked" </c:if> /> Owner/Principal</p>
            		 		<p><input type="checkbox" name="businessinfoDto.managerInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${businessinfoDto.managerInd == 1}"> checked="checked" </c:if> /> Manager</p>
            		   		<p> <input type="checkbox" name="businessinfoDto.adminInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${businessinfoDto.managerInd == 1}"> checked="checked" </c:if> /> Administrator</p>

                	    </td>
                        <td>
                       	<p> <input type="checkbox" name="businessinfoDto.dispatchInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${businessinfoDto.dispatchInd == 1}"> checked="checked" </c:if> /> Dispatcher/Scheduler</p>
   						<p> <input type="checkbox" name="businessinfoDto.sproInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${businessinfoDto.sproInd== 1}"> checked="checked" </c:if> /> Service Provider</p>
   						<p> <input type="checkbox" name="businessinfoDto.otherInd" value="1" <c:if test="${addUserInd}"> disabled="disabled" </c:if> <c:if test="${businessinfoDto.otherInd== 1}"> checked="checked" </c:if> /> Other</p>
                     </td>
                    	</tr>
               		 </table>


							<td>		<p>
												<label>
													Job Title/Optional
												</label>
												<br />
												<s:textfield name="businessinfoDto.jobTitle"
													value="%{businessinfoDto.jobTitle}"
													 maxlength="50" cssStyle="width: 250px;"
													cssClass="shadowBox grayText" />

											</p>
										</td>



									</tr>

               		 					<tr>
											<td>

											<label><b>
												First Name</b>
											</label>
											<br />
										<p>	<s:property value="businessinfoDto.firstName"/> </p>

										</td>

										<td>
											<p>
												<label>
												<b>	Middle Name</b>
												</label>
												<br />
												<s:property value="businessinfoDto.middleName"/> </p>


										</td>
									</tr>
									<tr>
										<td>


											<label>
												<b>Last Name</b>
											</label>
											<br />
										<p>	<s:property value="businessinfoDto.lastName"/> </p>
											<br>

										</td>
										<td>
											<p>
												<label>
													<b>Suffix (Jr., II, etc.)</b>
												</label>
												<br />
												<s:property value="businessinfoDto.nameSuffix"/> </p>



										</td>
										<tr>
										<td>
										<p>
											<label>
											 <b> Resource ID#</b>
											</label>
											<br/>
											<s:property value="businessinfoDto.resID"/> </p>
										</td>
									</tr>

									<tr>
										<td>
											<c:choose>
											<c:when test="${fieldErrors['businessinfoDto.email'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												E-mail Address<span class="req">*</span>
											</label>
											<br />
											<s:textfield name="businessinfoDto.email"
												value="%{businessinfoDto.email}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

										</td>
										<td>
											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.confirmEmail'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Confirm E-mail Address<span class="req">*</span>
											</label>
											<br />
											<s:textfield name="businessinfoDto.confirmEmail"
												value="%{businessinfoDto.confirmEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

										</td>
									</tr>
													<tr>
										<td>
											<!--  p class="errorBox"-->
											<c:choose>
											<c:when
												test="${fieldErrors['businessinfoDto.altEmail'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Alternate E-mail Address
											</label>
											<br />
											<s:textfield name="businessinfoDto.altEmail"
												value="%{businessinfoDto.altEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />
											<br>
											<!-- span class="errorMsg">Invalid E-mail Address.  Please try again.</span-->

										</td>
										<td>
											<c:choose> 
											<c:when
												test="${fieldErrors['businessinfoDto.confAltEmail'] == null}">
												<p class="paddingBtm">
											</c:when>
											<c:otherwise>
												<p class="errorBox">
											</c:otherwise>
											</c:choose>
											<label>
												Confirm Alternate E-mail Address
											</label>
											<br />
											<s:textfield name="businessinfoDto.confAltEmail"
												value="%{businessinfoDto.confAltEmail}"
												 maxlength="255" cssStyle="width: 250px;"
												cssClass="shadowBox grayText" />

										</td>
									</tr>


<!-- Changes Starts for Admin Name Change -->
               <c:choose>
                <c:when test="${isPermissionForAdminNameChange == 'true'}">                     
					<tr>
					<td></td>
					<td>
						<s:checkbox onclick="enableProviderListForAdminChange(this);" id="changeAdmin" name="changeAdmin"  />
						<label>
							<b> Change Admin Name#</b>
						</label>
					</td>

				</tr>

				<tr>
					<td></td>
					<td>
						<s:select name="providerListForAdminChange"
							id="providerListForAdminChange"
							headerValue="-- Please Select New Admin --" headerKey=""
							list="businessinfoDto.providerList" theme="simple"
							onchange="changeResourseIdForAdminChange(this);"
							cssStyle="width: 260px;" cssClass="grayText" disabled="true"></s:select>
					</td>
				</tr>
				<tr>
					<td>
						<p></p>
					</td>
					<td>
						<p></p>
					</td>
				</tr>

                </c:when>
              </c:choose>
                <!-- Changes Ends for Admin Name Change -->
				
				<tr></tr>

			</table>

						</div>

<div class="clearfix">
  <div class="formNavButtons2">
  	<s:submit value="" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/save.gif);width:49px;height:20px;"  cssClass="btn20Bevel" />
  	<s:submit value="" type="image" action="businessinfoActiondoNext"  src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/next.gif);width:50px;height:20px;"  cssClass="btn20Bevel" onclick="setAction('Next','businessinfoDto');"/>
  </div>
  <br/>

</div>

<br/>


</s:form>
<!-- END TAB PANE -->
<div class="clear"></div>


