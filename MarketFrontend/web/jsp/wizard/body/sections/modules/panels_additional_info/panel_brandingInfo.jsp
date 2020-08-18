<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<!-- NEW MODULE/ WIDGET-->

<div dojoType="dijit.TitlePane" title="Branding Information (Optional)" id="" class="contentWellPane">
  <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.branding.msg"/>
  <div class="clear"></div>
  <table class="logoTableSOWhdr" cellpadding="0" cellspacing="0">
    <tr>
      <td class="column1"><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.branding.select.label"/></td>
      <td class="column2">&nbsp;</td>
      <td class="column3"><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.branding.company.label"/></td>
      <td class="column4"><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.addinfo.branding.logo.label"/></td>
    </tr>
  </table>
  <div class="gridTableContainer" style="height: 105px;">
   <table class="logoTableSOW" cellpadding="0" cellspacing="0">
     	<s:if test="%{logodocumentId == null }">
			<s:iterator value="brandingInfoList" status="brandingInfo">
				<tr>
                    	<td class="column2"align="center">
                    	<s:if test="%{#brandingInfo.index == 0 }">
							<input type="radio"  
									   name="logodocumentId" 
									   id="logoSelectCheckbox"
										checked="checked"
	                    			   class="antiRadioOffsets" 
	                    			   value="<s:property value="%{brandingInfoList[#brandingInfo.index].documentId}"/>"/>
							</s:if>
							<s:else>
								<input type="radio"  
									   name="logodocumentId" 
									   id="logoSelectCheckbox"
	                    			   class="antiRadioOffsets" 
	                    			   value="<s:property value="%{brandingInfoList[#brandingInfo.index].documentId}"/>"/>
							</s:else>
                    	</td>
                    	<td class="column2">&nbsp;</td>
						<td class="column3"><s:property value="%{brandingInfoList[#brandingInfo.index].companyName}"/>&nbsp;</td>
						<td class="column4"><s:property value="%{brandingInfoList[#brandingInfo.index].logoId}"/>&nbsp;</td>
		
				</tr>
			</s:iterator>
		</s:if>
		<s:else>
			<s:iterator value="brandingInfoList" status="brandingInfo">
				<tr>
                    	<td class="column2"align="center">
                    	
                    	<s:if test="%{logodocumentId == brandingInfoList[#brandingInfo.index].documentId }">
							<input type="radio"  
									   name="logodocumentId" 
									   id="logoSelectCheckbox"
										checked="checked"
	                    			   class="antiRadioOffsets" 
	                    			   value="<s:property value="%{brandingInfoList[#brandingInfo.index].documentId}"/>"/>
						</s:if>
						<s:else>
								<input type="radio"  
									   name="logodocumentId" 
									   id="logoSelectCheckbox"
	                    			   class="antiRadioOffsets" 
	                    			   value="<s:property value="%{brandingInfoList[#brandingInfo.index].documentId}"/>"/>
						</s:else>
                    	</td>
                    	<td class="column2">&nbsp;</td>
						<td class="column3"><s:property value="%{brandingInfoList[#brandingInfo.index].companyName}"/>&nbsp;</td>
						<td class="column4"><s:property value="%{brandingInfoList[#brandingInfo.index].logoId}"/>&nbsp;</td>
		
				</tr>
			</s:iterator>
		</s:else>	
    </table>
  </div>
<br>
  <div class="clearfix">
  	<input type="button" id="applyBrandingLogo" 
		  		onclick="javascript:previousButton('soWizardAdditionalInfoCreate_applyBrandingLogo.action','soWizardAdditionalInfoCreate','tab2');"
		  		class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/apply.gif);width:68px; height:20px;"
		  		src="${staticContextPath}/images/common/spacer.gif"
	/>
	<input type="button" id="viewBrandingLogo" 
		  		onclick="javascript:previousButton('soWizardAdditionalInfoCreate_viewBrandingLogo.action','soWizardAdditionalInfoCreate','tab2');"
		  		class="btn20Bevel"
				style="background-image: url(${staticContextPath}/images/btn/view.gif);width:68px; height:20px;"
		  		src="${staticContextPath}/images/common/spacer.gif"
	/>
	</div>
  
</div>

	
