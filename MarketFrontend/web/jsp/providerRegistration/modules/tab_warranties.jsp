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

<script type="text/javascript">
function setAction(action,dto){
	jQuery("#"+dto+"Action").val(action);
}
</script>
<div id="content_right_header_text">
   	<%@ include file="../message.jsp"%>
</div>
    
<p class="paddingBtm">Some of our buyers require service warranties on the work you do. Others have workplace standards that contractors they hire must adhere to. Your answers to the questions below help buyers determine if your workplace standards match theirs.  </p>

<s:form name="doWarranty" action="doWarranty" theme="simple" method="POST">

<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Warranty Information</div>
<div class="grayModuleContent mainContentWell">
<input id="wdtoAction" type="hidden" name="wdto.action" value="${wdto.action}" />
  <table cellpadding="0" cellspacing="0" width="679">
    <tr>
      <td width="340">
      <p><label>Do you charge for project estimates?<span class="req">*</span></label><br />
         <c:choose>
         <c:when test="${fieldErrors['wdto.freeEstimate'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>      
         <span class="formFieldOffset">      		
      		<!--<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.freeEstimate"/> -->
      		<s:radio id="testvalue" labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.freeEstimate"/>
      	</span></p>        									
	</td>
      <td width="339">
       </td>
    </tr>
    <tr><td>
    <p><label>Do you offer a warranty on labor?<span class="req">*</span></label><br />
         <c:choose>
         <c:when test="${fieldErrors['wdto.warrOfferedLabor'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>  
         <span class="formFieldOffset">	      
	      <s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.warrOfferedLabor" onclick="enableSelectByName(this, 'wdto.warrPeriodLabor');showHideStar(this, 'wdto.warrPeriodLabor.star');"/>
      </span></p>
      
       <p><label>Do you offer a warranty on parts?<span class="req">*</span></label><br />
         <c:choose>
         <c:when test="${fieldErrors['wdto.warrOfferedParts'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>     
         <span class="formFieldOffset">
      		<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.warrOfferedParts" onclick="enableSelectByName(this, 'wdto.warrPeriodParts');showHideStar(this, 'wdto.warrPeriodParts.star');"/>
      	</span></p>    
        </td>
        <!-- Enable/disable dependent radio buttons on load with prepopulated values -->      	
	
	<c:choose>
	<c:when test="${wdto.warrOfferedLabor == 1}">		
       		<s:set name="warrPeriodLaborStatus" value="false"></s:set>				
       	</c:when>
       	<c:otherwise>		
       		<s:set name="warrPeriodLaborStatus" value="true"></s:set>			    
       	</c:otherwise>	
     </c:choose>
        <td>
		<label>How long is your labor warranty?	
		<c:choose>
		<c:when
		 test="${wdto.warrOfferedLabor == 1}">		
       			<span id="wdto.warrPeriodLabor.star" class="req" style="" >*</span>       	       	
		</c:when>
       		<c:otherwise>	
			<span id="wdto.warrPeriodLabor.star" class="req" style="display:none;" >*</span> 
		</c:otherwise>
		</c:choose>			
		</label><br/>
		<c:choose>
         <c:when test="${fieldErrors['wdto.warrPeriodLabor'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
          <s:select          			
          			name="wdto.warrPeriodLabor"
          			headerKey="" headerValue="Select One"
		        	list="luWarrantyPeriods"		        
		        	disabled="%{warrPeriodLaborStatus}"		        	
		        	/>		  
		<br/><br/>
		<label>How long is your parts warranty?
			<c:choose>
			<c:when test="${wdto.warrOfferedParts == 1}">
       			<span class="req" id="wdto.warrPeriodParts.star" style="" >*</span>     
       		</c:when>
       		<c:otherwise>
       			<span class="req" id="wdto.warrPeriodParts.star" style="display:none;" >*</span>     
       		</c:otherwise>
       		</c:choose>	
		</label>
		<c:choose>
         <c:when test="${fieldErrors['wdto.warrPeriodParts'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>		
			<c:choose>
        	<c:when test="${wdto.warrOfferedParts == 1}">
       			<s:set name="warrOfferedPartsStatus" value="false"></s:set>
       		</c:when>
       		<c:otherwise>
       			<s:set name="warrOfferedPartsStatus" value="true"></s:set>
       		</c:otherwise>
       		</c:choose>       	
        	<s:select        			
		        	name="wdto.warrPeriodParts"
		        	headerKey="" headerValue="Select One"
					list="luWarrantyPeriods"
					disabled="%{warrOfferedPartsStatus}"
					/>    
        
        </td>
        </tr>
  </table>  
</div>

<!-- NEW MODULE/ WIDGET-->
<div class="darkGrayModuleHdr">Policies & Procedures</div>
<div class="grayModuleContent mainContentWell">
  
  <table cellpadding="0" cellspacing="0" width="679">
    <tr>
      <td width="340">
      <p><label>Have you implemented a drug testing policy?<span class="req">*</span></label><br />
         <c:choose>
         <c:when test="${fieldErrors['wdto.conductDrugTest'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>

         <span class="formFieldOffset">
      		<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.conductDrugTest"  onclick="enableWRadioButtonByName(this, 'wdto.considerDrugTest');hideShowStar(this, 'wdto.considerDrugTest.star');"/>
      </span></p>
      <c:choose>
      	<c:when test="${wdto.conductDrugTest == 0}">
       			<s:set name="conductDrugTestStatus" value="false"></s:set>
       	</c:when>
       	<c:otherwise>
       			<s:set name="conductDrugTestStatus" value="true"></s:set>
       	</c:otherwise>
		</c:choose>
 <p><label>Do you have written policies promoting a lawful and ethical work environment?<span class="req">*</span></label><br />
         <c:choose>
         <c:when test="${fieldErrors['wdto.hasEthicsPolicy'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>     
         <span class="formFieldOffset">
         	<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.hasEthicsPolicy" onclick="enableWRadioButtonByName(this, 'wdto.considerEthicPolicy');hideShowStar(this, 'wdto.considerEthicPolicy.star'); "/>
         </span></p>
         	<c:choose>
        	 <c:when test="${wdto.hasEthicsPolicy == 0}">
       			<s:set name="hasEthicsPolicyStatus" value="false"></s:set>
       		</c:when>
       		<c:otherwise>
       			<s:set name="hasEthicsPolicyStatus" value="true"></s:set>
       		</c:otherwise>
       		</c:choose>
      
       <p><label>Do you require your employees to supply proof of citizenship?<span class="req">*</span></label><br/>
         <c:choose>
         <c:when test="${fieldErrors['wdto.requireUsDoc'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>
         <span class="formFieldOffset">
      		<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.requireUsDoc" onclick="enableWRadioButtonByName(this, 'wdto.considerImplPolicy');hideShowStar(this, 'wdto.considerImplPolicy.star');"/>
      	</span></p>
		<c:choose>
		<c:when test="${wdto.requireUsDoc == 0}">
       			<s:set name="requireUsDocStatus" value="false"></s:set>
       	</c:when>
       	<c:otherwise>
       			<s:set name="requireUsDocStatus" value="true"></s:set>
       	</c:otherwise>
       	</c:choose>     	
     	
       <p><label>Do you require your crews to wear badges?<span class="req">*</span></label><br />
       	<!-- error filed highlighting logic -->
        <c:choose>
        <c:when test="${fieldErrors['wdto.requireBadge'] == null}">  
       		<p class="paddingBtm">
       	</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>        
         <span class="formFieldOffset">
      		<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.requireBadge" onclick="enableWRadioButtonByName(this, 'wdto.considerBadge');hideShowStar(this, 'wdto.considerBadge.star');"/>
      	</span></p>
      	<c:choose>
      	<c:when test="${wdto.requireBadge == 0}">
       			<s:set name="requireBadgeStatus" value="false"></s:set>
       	</c:when>
       	<c:otherwise>
       			<s:set name="requireBadgeStatus" value="true"></s:set>
       	</c:otherwise>
       	</c:choose>
        </td>
        
        <td width="339">
        <p><label>Would you consider implementing a drug testing policy?
			<c:choose>
			<c:when test="${wdto.conductDrugTest == 0}">		
				<span id="wdto.considerDrugTest.star" class="req" style="">*</span>        	
			</c:when>
			<c:otherwise>
				<span id="wdto.considerDrugTest.star" class="req" style="display:none;">*</span>        	
			</c:otherwise>
			</c:choose>	
		</label><br />
       		<!-- error filed highlighting logic -->
        <c:choose>
        <c:when test="${fieldErrors['wdto.considerDrugTest'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise> 
		</c:choose>       
         <span class="formFieldOffset">
      			<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.considerDrugTest" disabled="%{conductDrugTestStatus}"/>
      	</span></p>

 <p><label>Would you consider implementing these policies?	
	
	<c:choose>
	<c:when test="${wdto.hasEthicsPolicy == 0}">	
		<span id="wdto.considerEthicPolicy.star" class="req" style="">*</span>
	</c:when>
	<c:otherwise>
		<span id="wdto.considerEthicPolicy.star" class="req" style="display:none;">*</span>
	</c:otherwise>
	</c:choose>	
	</label><br />
        		<!-- error filed highlighting logic -->
       <c:choose>
        <c:when test="${fieldErrors['wdto.considerEthicPolicy'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>  
         <span class="formFieldOffset">
      			<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.considerEthicPolicy" disabled="%{hasEthicsPolicyStatus}"/>
         </span><br />&nbsp;</p>
      
       <p><label>Would you consider implementing this policy?
	   <c:choose>
	   <c:when test="${wdto.requireUsDoc == 0}">	
		<span id="wdto.considerImplPolicy.star" class="req" style="">*</span>
		</c:when>
		<c:otherwise>
			<span id="wdto.considerImplPolicy.star" class="req" style="display:none;">*</span>
		</c:otherwise>
		</c:choose>	
	   
	    </label><br/>
       <!-- error filed highlighting logic -->
        <c:choose>
        <c:when test="${fieldErrors['wdto.considerImplPolicy'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>        
         <span class="formFieldOffset">
      		<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.considerImplPolicy" disabled="%{requireUsDocStatus}"/>
      	</span><br/>&nbsp;</p>
       
       <p><label>Would you consider implementing this policy?
	      <c:choose>
	      <c:when test="${wdto.requireBadge == 0}">	
		 <span id="wdto.considerBadge.star" class="req" style="">*</span> 
		</c:when>
		<c:otherwise>
			 <span id="wdto.considerBadge.star" class="req" style="display:none;">*</span> 
		</c:otherwise>
		</c:choose>
	  
	   </label><br />
       <!-- error filed highlighting logic -->
       	<c:choose>
        <c:when test="${fieldErrors['wdto.considerBadge'] == null}">  
       		<p class="paddingBtm">
       		</c:when>
       	<c:otherwise>
    		<p class="errorBox">
		</c:otherwise>
		</c:choose>          
         <span class="formFieldOffset">
      		<s:radio labelposition="top" list="#{'1':'Yes', '0':'No'}" name="wdto.considerBadge" disabled="%{requireBadgeStatus}"/>
      </span></p>
                   
        </td>
        </tr>
        
  </table>
  
</div>

<div class="clearfix">
  <div class="formNavButtons2">
  	
  	<s:submit value="" action="doWarrantydoPrev" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/previous.gif);width:72px;height:20px;"  cssClass="btn20Bevel" onclick="setAction('Prev','wdto');"/>
  	<s:submit value="" action="doWarrantydoSave" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/save.gif);width:49px;height:20px;"  cssClass="btn20Bevel" />
  	<s:submit value="" action="doWarrantydoNext" type="image" src="%{#request['staticContextPath']}/images/images_registration/common/spacer.gif" theme="simple" cssStyle="background-image:url(%{#request['staticContextPath']}/images/images_registration/btn/next.gif);width:50px;height:20px;"  cssClass="btn20Bevel" onclick="setAction('Next','wdto');"/>
  	</div>
  	
   	<!--
  	<input width="72" type="image" height="20" class="btn20Bevel" style="background-image: url(${staticContextPath}/images/btn/previous.gif);" src="${staticContextPath}/images/common/spacer.gif"/> 
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="49" height="20" style="background-image:url(${staticContextPath}/images/btn/save.gif);"  class="btn20Bevel" />
    <input type="image" src="${staticContextPath}/images/common/spacer.gif" width="50" height="20" style="background-image:url(${staticContextPath}/images/btn/next.gif);"  class="btn20Bevel" />
    -->
  	
  
</div>
	
</s:form>
<br/>
				 
