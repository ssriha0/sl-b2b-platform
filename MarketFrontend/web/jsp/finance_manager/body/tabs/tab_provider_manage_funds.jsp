
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevents caching at the proxy server
%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
		
 <s:form action="fmManageFunds_withdrawFunds" theme="simple"
	 method="POST" name="fmWithdrawFunds" id="fmWithdrawFunds">
<jsp:include page="validationMessages.jsp" />

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="FianaceManager.providerManageFunds"/>
	</jsp:include>	

<font color="red" size="2">
		<c:out value="${withdrawsuccess}" />
</font>
<s:hidden name="providerMaxWithdrawalLimit" id="providerMaxWithdrawalLimit" value="%{#session.providerMaxWithdrawalLimit}" />
<s:hidden name="providerMaxWithdrawalNo" id="providerMaxWithdrawalNo" value="%{#session.providerMaxWithdrawalNo}" />
<p>

<s:if test="%{#session.WithdrawStatus == 'success'}" >
	<font color="blue" size="1">
		<fmt:message bundle="${serviceliveCopyBundle}"
		key="fm.managefunds.success" />
    </font>   
    <%session.removeAttribute("WithdrawStatus"); %>
</s:if>

</p>

<s:if test="%{#session.AccountStatus == 'invalidAccount'}" >
	<font color="red" size="2">
		<fmt:message bundle="${serviceliveCopyBundle}"
		key="fm.managefunds.invalidaccount" />
</font>
</s:if>
<!-- 
<p class="paddingBtm">
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.msg" />
</p>  -->
<div class="darkGrayModuleHdr">
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.withdraw" />
</div>
<div class="grayModuleContent mainWellContent clearfix">

	<table cellpadding="0" cellspacing="0">
		<tr>
			<td width="350">
				<p>
					<label>
						<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.withdrawtoaccount" />
					</label>
					<br />
				<tags:fieldError id="Withdraw to this Account." oldClass="paddingBtm">
				<s:select name="manageFundsTabDTO.accountId" headerKey="-1"
					headerValue="Select One" list="%{#session.accountList}"
					listKey="accountId" listValue="accountNameNum" cssStyle="width: 240px;"
					size="1" theme="simple" value="manageFundsTabDTO.accountId" />
				</tags:fieldError>
					<%-- <a href="#">Add New Bank Account</a> --%>
				</p>
			</td>
			<td width="350">
				<p>
					<label>
						Withdrawal Amount (max $<s:property value="%{#session.providerMaxWithdrawalLimit}"/> in a 24-hour period)
					</label>
					<br />
					<tags:fieldError id="Withdraw Amount" oldClass="paddingBtm">
					<s:if test="%{manageFundsTabDTO.withdrawAmount != null}" >
						<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
						name="manageFundsTabDTO.withdrawAmount"
						value="%{manageFundsTabDTO.withdrawAmount}"
						id="manageFundsTabDTO.withdrawAmount" cssStyle="width: 160px;"
						maxlength="10"/>
					</s:if>
					<s:else>
						<s:textfield  onfocus="clearTextbox(this)"  cssClass="shadowBox"
						name="manageFundsTabDTO.withdrawAmount"
						value="0.00"
						id="manageFundsTabDTO.withdrawAmount" cssStyle="width: 160px;"
						maxlength="10"/>
					</s:else>
					</tags:fieldError>
				</p>
			</td>
		</tr>
	</table>
</div>

<div class="clearfix">
<input type="hidden" id="walletControlId" value="${EntityWalletControlId}"/>
		<div id="submitDiv" class="formNavButtons" >
			<s:if test="%{#session.AccountStatus == 'invalidAccount'}">
				<s:submit type="input" 
					  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/withdrawFunds.gif);width:108px; height:20px;"
					  cssClass="btn20Bevel" 
					  theme="simple"
					  value=" "
					  disabled="true"				  
				/>	
			</s:if>
			<s:else>
				<input onClick="pop_w9modal();" type="button" style="border: 0px; background-image: url(${staticContextPath}/images/btn/withdrawFunds.gif); width: 108px; height: 20px;" >
				<!--
				<s:submit type="input" 
					  cssStyle="background-image: url(%{#request['staticContextPath']}/images/btn/withdrawFunds.gif);width:108px; height:20px;"
					  cssClass="btn20Bevel" 
					  theme="simple"
					  value="" />
				-->
			</s:else>
				
	</div>
	<div id="disabledSubmitDiv" style="display: none">
	Submitting Request ....
	</div>
	</div>
<s:hidden name="w9isExist" id="w9isExist" value="%{#session.w9isExist}" />
<s:hidden name="w9isExistWithSSNInd" id="w9isExistWithSSNInd" value="%{#session.w9isExistWithSSNInd}" />
<s:hidden name="w9isExistWithValidVal" id="w9isExistWithValidVal" value="%{#request['w9isExistWithValidVal']}" />
<s:hidden name="manageFundsTabDTO.nonce" id="nonce" value=""/>

</s:form>
<p>
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.note" />: 
	<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.note.msg" />
</p>
<input type="hidden" name="formW9" id="formW9" value="withdraw" />

<div class="jqmWindow2" id="w9modal" style>
	<div id="fillWithW9"></div>
</div>

 <div style="margin-left: 20px;margin-top: 20px;">
 	<div id="errDivForWallet"></div>

 <form action="" method="POST"  enctype="multipart/form-data" name="walletControl_upload_form"  id="walletControl_upload_form"> 
	<c:if test="${legalHoldPermission == true}">
	<input type="hidden" id="walletControlType" value="${manageFundsTabDTO.walletControlType}"/>
	<input type="hidden" id="walletAmount" value="${manageFundsTabDTO.walletControlAmount}"/>
	<input type="hidden" id="entityWalletControlID" value="${manageFundsTabDTO.entityWalletControlID}"/>
	<input type="hidden" name="manageFundsTabDTO.entityWalletControlID" id="manageFundsTabDTO.entityWalletControlID" value=""/> 	
	<input type="hidden" id="walletControlRadioValue" value="${EntityWalletControlId}"/>

	<iframe id="my_iframe" style="display: none;" onload="displayFileUpload();">
	</iframe>
	
		<div>
			<label><font color="red">
				<fmt:message bundle="${serviceliveCopyBundle}" key="fm.managefunds.irs" /></font>
			</label>
			<br />
			<br/>
			  <input type="radio" class="antiRadioOffsets" style="margin-left: 20px;" id="irsLevyRadioButton" name="manageFundsTabDTO.walletControlType" value="irsLevy" <c:if test='${manageFundsTabDTO.walletControlType =="irsLevy"}'>checked </c:if> onchange="enableFileUpload();"/>
			  <span style="margin-left: 5px;font-weight: bold;">Account is Under IRS Levy</span>
			  <input type="radio" class="antiRadioOffsets" style="margin-left: 40px;" id="legalHoldRadioButton" name="manageFundsTabDTO.walletControlType" value="legalHold" <c:if test='${manageFundsTabDTO.walletControlType =="legalHold"}'>checked</c:if> onchange="enableFileUpload();"/>
			  <span style="margin-left: 5px;font-weight: bold;">Account is Under Legal Hold</span>
		</div>
		
		<div id="irsFileUplod" style="display:none;margin-left: 40px;margin-top: 20px;">
		<input type="hidden" name="manageFundsTabDTO.onHold" id="manageFundsTabDTO.onHold" value=""/> 
		
			<div>
					<label class="offset-1 col-3 col-form-label"><span style="margin-left: 1px;font-weight: bold;"><fmt:message bundle="${serviceliveCopyBundle}" key="irs.levy.notification" /></span><font color="red">*</font></label>
					<input type="text" class="form-control form-control-sm" name="manageFundsTabDTO.walletControlAmount" size="8" value="${manageFundsTabDTO.walletControlAmount}" id="walletControlAmount" placeholder="00.00" required value="">
			</div>
			<div id="fileList">
				<c:if test="${(manageFundsTabDTO.walletControldocuments != null)}">
					<c:forEach items="${manageFundsTabDTO.walletControldocuments}" var="doc" varStatus="statusCounter">
							<div id="${doc.documentId}" style="margin-top: 20px;">
							<label>
							<a href="#" onclick="downloadDocumentForWalletControl('${doc.documentId}')">														
								${doc.name}
							</a>
						   <img id="removeDocforirs"
											onclick="removeDocumentForWalletControl('${doc.documentId}');" class="btn20Bevel"
											style="visibility: visible;cursor: pointer;"
											src="${staticContextPath}/images/response_icon_red.gif" title="Delete" >
								</label>
								</div>
								
					</c:forEach>
				</c:if>
			</div>
			<div style="margin-top: 20px;">		
			<label><font color="red">
				<fmt:message bundle="${serviceliveCopyBundle}" key="document.upload.irs" />
				</font>	
			</label>
			<br/>
			<s:file name="manageFundsTabDTO.walletControlFiles" id="manageFundsTabDTO.walletControlFiles" multiple="multiple" cssStyle="margin-top: 20px;"/>
				
				<div style="font-weight:bold;margin-top: 20px;">Accepted File Types : <label style="font-weight:normal"><fmt:message bundle="${serviceliveCopyBundle}" key="document.file.accepted.file.formats.irs" /> </label></div>
			<br />
			<b>Max. file size: 2MB</b>
			</div>
			
			<input type="button" class="btn20Bevel" onClick="return blockWallet();"
				style="background-image: url(/ServiceLiveWebUtil/images/btn/saveInformation.gif); width: 110px; height: 20px;margin-top: 20px;"
				src="/ServiceLiveWebUtil/images/common/spacer.gif" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
				
			<input type="button" class="btn20Bevel"  onClick="return releaseWallet();"
				style="background-image: url(/ServiceLiveWebUtil/images/btn/release.gif); width: 62px; height: 20px;margin-top: 20px;" />
					
					
		</div>
	</c:if>
	
	<c:if test="${legalHoldPermission == false}">	       
	       
			<c:if test="${EntityWalletControlId == 3}">
				<center> <font size="2" color="red" >  <b> IRS LEVY NOTICE </b>  </font> </center>
				<p> <font size="2" color ="red"> ${EntityWalletControlBanner} </font> </p>
			</c:if>
			<c:if test="${EntityWalletControlId == 4}">
				<center> <font size="2" color="red" > <b> LEGAL HOLD NOTICE </b>  </font> </center>
				<p> <font size="2" color ="red"> ${EntityWalletControlBanner} </font> </p>
			</c:if>			
		
		
	</c:if>
	
		</form>
		</div>		
			<div  id="popupirs" style="display: none;  border: 3px solid #CCCCCC; color: #000000;height: auto;">
				<div style=" font-family: Arial,Helvetica,sans-serif; font-size: 12px">
					 <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;" id="modalTitleBlock">
						<span style="font-size: 17px; font-weight: bold;">Are you sure you want to do this?</span> 
					</div>
					<br/>
					<span class="popText" style="padding-left ;position: relative; left: 6px; line-height: 1.5;">
						   You are about to take control of the Provider's e-wallet, due to an IRS Levy/Legal Hold. <br/>
						   Are you sure you want to do this?
					</span>
				</div>
					<br>
					
				<div style="padding-right ;position: relative; left: 8px;" width="100%">
				<input id="yesButton" type="button" style="margin-left: 40px;" value="Yes" onclick="successForBlockWallet();"/>
				<input id="noButton" class="button simplemodal-close" type="button" value="Cancel" />
						
					
				</div>
					<br>
			</div>			
		    <!-- releasewallet-->		    
		    <div  id="popuprelease" style="display: none;  border: 3px solid #CCCCCC; color: #000000;height: auto;">
				<div style=" font-family: Arial,Helvetica,sans-serif; font-size: 12px">
					 <div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;" id="modalTitleRelease">
						<span style="font-size: 17px; font-weight: bold;">Are you sure you want to do this?</span> 
					</div>
					<br/>
					<span class="popText" style="padding-left ;position: relative; left: 7px; line-height: 1.5;">
						   You are about to Release control of the Provider's e-wallet. Are you sure you want to do this?   <br/>
						   
					</span>
				</div>
					<br>
					
				<div style="padding-right ;position: relative; left: 8px;" width="100%">
				<input id="yesButton" type="button" style="margin-left: 40px;" value="Yes" onclick="successForReleaseWallet();"/>
				<input id="noButton" class="button simplemodal-close" type="button" value="Cancel" />
						
					
				</div>
					<br>
			</div>	
			
			
			<div  id="popupwalletnotice" style="display: none;  border: 3px solid #CCCCCC; color: #000000;height: auto;">
				<div style=" font-family: Arial,Helvetica,sans-serif; font-size: 12px">	
				<div style="background: none repeat scroll 0% 0% rgb(88, 88, 90); border-bottom: 2px solid black; color: rgb(255, 255, 255); text-align: left; height: 25px; padding-left: 8px; padding-top: 5px;" id="modalTitleNotice">
				<span style="font-size: 17px; font-weight: bold;">Manage Funds</span> 		 
				</div>				 
					<br/>
					<span class="popText" style="padding-left ;position: relative; left: 6px; line-height: 1.5;">
						   Withdrawal of Funds is currently not available.  Please refer to notice for additional details.
					</span>
				</div>
					<br>
					
				<div style="padding-right ;position: relative; left: 8px;" width="100%">
				<input id="okButton" class="button simplemodal-close" type="button" value="OK" />						
					
				 </div>
				 <br>
			</div>				
			
