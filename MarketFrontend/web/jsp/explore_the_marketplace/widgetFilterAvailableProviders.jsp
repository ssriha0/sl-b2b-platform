<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script>jQuery.noConflict();</script>
<script type="text/javascript">
	jQuery(document).ready(function($)
	{
		jQuery.noConflict();
		
		
		var filterdisable='${filtersDisabled}';		
		
		if(filterdisable=='false')
			{
			$('#ratings').removeAttr('disabled');
			}

		if($('#spn').val() != "" && $("#spn").val() > 0)
		{			
			$('#performanceLevelsDiv').show();
		}
		else
		{		
			$('#performanceLevelsDiv').hide();
		}
		
		
		$("#spn").change(function()
		{
     		if($('#spn').val() == '')
     		{
     			//alert('disable');	
     			$('#perfLevel').attr('disabled', 'disabled');
     		}
     		else
     		{
     			$('#perfLevel').removeAttr('disabled');
				$('.perfLevelClass').attr("checked", false);     
				 $('#selectedNetworkCheckboxList > *').attr("checked",false);			
     			//alert('enable');
     		}
     	});
     	
		$('#spn').change( function()
		{
			//alert($('#spn').val());
			
			if($('#spn').val() > 0)
			{
				$('#performanceLevelsDiv').show();
			}
			else
			{
				$('#performanceLevelsDiv').hide();
				 $('#selectedNetworkCheckboxList > *').attr("checked",false);
			}
			
		});
		if($('#credentials').val() > 0)
		{
			$('#specifyCredentialDiv').show();
		}
		$('#credentials').change(function() {
			
			if($('#credentials').val() > 0)
			{
				$('#specifyCredentialDiv').show();
			}
			else
			{
				$('#specifyCredentialDiv').hide();
				$('.subcredentialSelect').attr("selected","-1")
			}
		});	
	
	
	});	
</script>
<s:form action="etmSearch_applyFilter" id="filterAvailableProvidersID" name="filterAvailableProvidersID" theme="simple">
<div class="rightRailWidget;" style="width:100%;">
	<div class="darkGrayModuleHdr">Filter Available Providers</div>
	<div class="grayModuleContent" style="padding:5px 10px; display:block;">
			
			<strong>Minimum Rating</strong>
			<br />
			<%-- <s:select name="ratings" id="ratings" headerKey="-1"
				headerValue="-- Select One --" list="%{ratingsList}" listKey="dId"
				listValue="descr" size="1" theme="simple"
				cssStyle="width: 90%; margin-bottom:15px; margin-bottom:15px; background-color: #ffffff;"
				value="%{ratings}"
				disabled="%{filtersDisabled}" /> --%>
				
		<select style="width: 90%; margin-bottom:15px; margin-bottom:15px; background-color: #ffffff;" id="ratings" size="1" name="ratings" disabled="${filtersDisabled}">
				    	<option selected="selected" value="-1">-- Select One --</option>
 					<c:forEach items="${ratingsList}" var="rating">
				  
			           <c:choose>
				    <c:when test="${ratings!='null' && ratings == rating.dId}">

				    	<option value="${rating.dId}" selected="selected">${rating.descr}</option>
				    </c:when>
				<c:otherwise>

						<option value="${rating.dId}">${rating.descr}</option>
				</c:otherwise>
				</c:choose>
					
   				 </c:forEach>
				</select> 


		<br />
		<c:if test="${fn:length(spnetworkList) > 0}" >
			<strong>My Networks (SPNs)</strong>
				<br/>
				
				<s:select name="spn" id="spn" headerKey="0"
			headerValue="-- Select One --" list="%{spnList}" listKey="spnId"
			listValue="spnName" size="1" theme="simple"
			cssStyle="width: 90%; margin-bottom:15px; background-color: #ffffff;"
			value="%{spn}"
			disabled="%{#session['filtersDisabled']}" />
				

			<div id="performanceLevelsDiv" style="display:none">		
		
					<strong>Group</strong> (for selected network)
					<br/>
					<div id="selectedNetworkCheckboxList">
					<s:checkboxlist list="%{performanceLevelList}" name="perfLevels" listKey="id" listValue="descr" cssClass="perfLevelClass" ></s:checkboxlist>
					<br style="clear:both;" />
					</div>
				</div>
		</c:if>
	
		
		<br />
		<input type="hidden" value="${contextPath}" id="cntxtPath"/>
		
		<div style="clear:both;"><strong>Distance (miles)</strong></div>
		<s:select name="distance" id="distance" headerKey="0"
			headerValue="-- Select One --" list="%{distanceList}" listKey="id"
			listValue="descr" size="1" theme="simple"
			cssStyle="width: 90%; margin-bottom:15px; background-color: #ffffff;"
			value="%{distance}"
			disabled="%{#session['filtersDisabled']}" />
			<strong>Credentials</strong>
			<br/>
			<s:select name="credentials" id="credentials" headerKey="-1"
				headerValue="-- Select One --" list="%{credentialsList}"
				listKey="id" listValue="descr" size="1" theme="simple"
				cssStyle="width: 90%; margin-bottom:15px; background-color: #ffffff;"
				onchange="javascript:fnCredentialCategory('credentials','filterAvailableProvidersID',document.getElementById('cntxtPath').value,'etmSearch_credentialCategory')"
				value="%{credentials}"
				disabled="%{#session['filtersDisabled']}" />
			<br/>
				<div id="specifyCredentialDiv" style="display:none">
				
	
			<select style="width: 90%; margin-bottom:15px; background-color: #ffffff;" 
					id="credentialCategory" name="credentialCategory" 
					size="1" class="subcredentialSelect" >
				<c:forEach var="lookupVO" items="${credentailCategoryList}">
					<c:choose>
						<c:when
							test="${lookupVO.id == credentialCategory}">
							<option selected value="${lookupVO.id}">
								${lookupVO.descr}
							</option>
						</c:when>
						<c:otherwise>
							<option value="${lookupVO.id}">
								${lookupVO.descr}
							</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</select>	
			</div>	
			

			<strong>Language Spoken</strong>
			<br/>
			<s:select name="languages" id="languages" headerKey="-1"
				headerValue="-- Select One --" list="%{languagesList}" listKey="id"
				listValue="descr" theme="simple"
				cssStyle="width: 90%; margin-bottom:15px; background-color: #ffffff;"
				value="%{languages}"
				size="1" disabled="%{#session['filtersDisabled']}" />

		
		<input type="hidden" id="checkedProvidersList" 	name="checkedProvidersList" />

		<c:if test="${filtersDisabled == 'false'}">
	 			<input class="button action" type="submit" value="Apply Filter"/>
	 			</c:if>
		
		
		<br/>&nbsp;
	</div>
	
	
	
</div>
<div class="clearfix">
</div>
</s:form>
