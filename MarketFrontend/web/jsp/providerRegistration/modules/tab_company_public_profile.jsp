<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
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
<html>
<head>
<title>ServiceLive [Provider Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
			
<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			var contextPath = '${pageContext.request.contextPath}';
			
		
			
		</script>

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
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/utils.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/calendar.css?random=20051112" media="screen"></link>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>


</head>
	<body>
	
		<div id="content_right_header_text">
			<%@ include file="../message.jsp"%>
		</div>
		<s:form id="companyProfileAction" name="companyProfileAction" action="companyPublicAction_doEdit" theme="simple">
			<s:hidden name="resourceId" value=""></s:hidden>
			<!-- NEW MODULE/ WIDGET-->

			<div class="grayModuleContent mainWellContent clearfix">
				<h3>
					<s:property value="companyProfileDto.businessName"/>  </h3>
				
				<p>
					Your public profile is below. When buyers are selecting companies to do business with, this is the information that they'll see. 
				</p>
				<div class="darkGrayModuleHdr">
					Business Information	
				</div>
				
				<div class="grayModuleContent mainWellContent">
				
					
					<table cellpadding="0" cellspacing="0" width="679">
						<tr>
							<td width="340">
							<p>	<b>Company ID#</b><br />
                   				 <s:property value="companyProfileDto.vendorId"/> </p> </td>
                   			<td width="339">	 
							
                			</td>
						</tr>
						<tr>
            				<td width="340">
                				<p><b>Business Structure</b><br />
                  				 <s:property value="companyProfileDto.businessType"/>
               				 </p></td>
                   			<td width="339">	 <p>
                				<b>Business Started</b><br />
                 				  <s:property value="companyProfileDto.businessStartDate"/></p>
              				  </td>
             			</tr>
			            <tr>
            				<td width="340">
                			<p>	<b>ServiceLive Member Since</b><br />
                  				 <s:property value="companyProfileDto.memberSince"/>
               				              				 </p></td>
                   			<td width="339">	 <p><b>Size of the Company</b><br />
                  				 <s:property value="companyProfileDto.companySize"/>
               				 </p>
              				  </td>
             			</tr>
             			<tr>
            				<td width="340">
                			<p>	<b>Primary Industry</b><br />
                  				 <s:property value="companyProfileDto.primaryIndustry"/>
               				</p> </td>
                   			<td width="339">	
            
              				  </td>
             			</tr>
             			
					</table>
				</div>

				<div class="clear"></div>
			</div>

			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">
					Company Overview
				</div>
					
			</div>
			<div class="grayModuleContent mainWellContent">
				<p> <s:property value="companyProfileDto.businessDesc"/></p>
			</div>
			
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">
					Business Address
				</div>
					
			</div>
			<div class="grayModuleContent mainWellContent">
				<p><s:property value="companyProfileDto.busCity"/>, <s:property value="companyProfileDto.busStateCd"/>&nbsp;<s:property value="companyProfileDto.busZip"/></p>
				
			</div>
			
		 
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">Warranty Information</div>
				<div>
				
					<s:if test="%{companyProfileDto.freeEstimate==1}">
						<p><label><b>Do you charge for project estimates?</b></label><br />
							Yes 
						</p>
	    			</s:if>
	    			
	    			<s:if test="%{companyProfileDto.warrOfferedLabor==1}">
						<p><label><b>Do you offer a warranty on labor?</b></label><br />
							Yes - <s:property value="companyProfileDto.warrPeriodLabor"/>
						</p>
	    			</s:if>
	    			
	    			<s:if test="%{companyProfileDto.warrOfferedParts==1}">
						<p><label><b>Do you offer a warranty on parts?</b></label><br />
							Yes - <s:property value="companyProfileDto.warrPeriodParts"/>
						</p>
	    			</s:if>
	    			
				</div>
			</div>
			 
			
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">Licenses &amp; Certifications on File</div>
				<s:if test="%{companyProfileDto.licensesList.size !=0}">
					 <table class="scrollerTableHdr licensesTableHdr" cellpadding="0" cellspacing="0">
					    <tr>
					      <td class="column2">Credential Type</td>
					      <td class="column3">Name</td>
					      <td class="column4">Expiration</td>
					      <td class="column5">Verified by ServiceLive</td>
					    </tr>
					  </table>
						<table class="licensesTable" cellpadding="0" cellspacing="0" style="table-layout:fixed; width:625px;">
					<s:iterator value="companyProfileDto.licensesList" status="status">
					 
						<tr>
						  <td class="column2" style="word-wrap:break-word"><s:property value="credTypeDesc"/></td>
						  <td class="column3" style="word-wrap:break-word"><s:property value="licenseName"/></td>
						  <td class="column4" style="word-wrap:break-word"><s:property value="expirationDate"/></td>
						  <td class="column5" style="word-wrap:break-word">
						  	<s:if test="%{wfStateId =='13'}">
								Pending Approval
							</s:if>
					        <s:elseif test="%{wfStateId =='14'}">
					            		<img src="${staticContextPath}/images/icons/greenCheck.gif" width="10" height="10" alt="">
					        </s:elseif>
					        <s:elseif test="%{wfStateId =='25'}"> Out of Compliance</s:elseif>
							<s:elseif test="%{wfStateId =='210'}">Reviewed</s:elseif>
						 </td>
						</tr>
				</s:iterator>
			  </table>
			  </s:if>
			  <s:else>
			  <p>Currently, there are no licenses or certificates on file for this provider.</p>
			  </s:else>
		</div>	
		
		<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">Insurance Policies on File</div>
				
				<s:if test="%{companyProfileDto.insuranceList.size !=0}">
					 <table class="scrollerTableHdr licensesTableHdr" cellpadding="0" cellspacing="0"
					 	style="table-layout: fixed; width: 100%">
						<tr>
							
							<td class="column1" style="width: 160px;">
								Policy Type
							</td>
							<td class="column2" style="width: 140px;">
								Carrier
							</td>
							<td class="column3" style="width: 60px;">
								Expiration
							</td>
							<td class="column4" style="width: 130px;">
								Documents
							</td>
							<td class="column5" style="width: 105px;">
								Verified by ServiceLive
							</td>
						</tr>
					</table>
					<table class="licensesTable" cellpadding="0" cellspacing="0"
					 style="table-layout: fixed; width: 100%">
					 <s:iterator value="companyProfileDto.insuranceList" id="credential" status="status">
   						<tr >

						<td class="column1" style="word-wrap:break-word;width: 160px;">
							<s:property value="credTypeDesc" />
						</td>
						<td class="column2" style="word-wrap:break-word;width: 140px;">
							<s:property value="source" />
						</td>
						<td class="column3" style="width: 60px;">
								<s:property value="expirationDate" />
						</td>
						<td class="column4" style="width: 130px;">
						<s:if test="%{currentDocumentID != null && currentDocumentID > 0}">
								<s:url action="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action"
										id="docUrl">
									<s:param name="docId" value="currentDocumentID" />
								</s:url>
								<a href="jsp/providerRegistration/saveInsuranceTypeActiondisplayTheDocument.action?docId=<s:property value="currentDocumentID"/>"
									target="blank">
									<img
									src="${staticContextPath}/images/images_registration/icons/pdf.gif"
									title="Click to view document" />
								</a>
							</s:if>
							<s:else>
								N/A
							</s:else>
							
						</td>
						<td class="column5" style="width: 125px;">
							<s:if test="%{wfStateId == '13'}">
								Pending Approval
							</s:if>
					        <s:elseif test="%{wfStateId =='14'}">
					           	<img src="${staticContextPath}/images/icons/greenCheck.gif" width="10" height="10" alt="">
					        </s:elseif>
					        <s:elseif test="%{wfStateId =='25'}"> Out of Compliance</s:elseif>
							<s:elseif test="%{wfStateId =='210'}">Reviewed</s:elseif>
						 </td>
					
				</tr>
				</s:iterator>
			</table>
			  </s:if>
			  <s:else>
			  <p>Currently, there are no insurance policies on file for this provider.</p>
			  </s:else>
			</div>
			<!-- 
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">
					Vital Statistics
				</div>
							
			</div>
			
			<div class="grayModuleContent mainWellContent clearfix">
				<div class="darkGrayModuleHdr">Buyer Relations</div>
			</div>  -->
			<div class="clearfix">
  <div class="formNavButtons2">
  	<s:submit value="" action="companyPublicAction_doEdit" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/editProfile.gif);width:90px;height:20px;"  cssClass="btn20Bevel" />
  	
  </div>
</div>
			
		</s:form>
		
	</body>
</html>
