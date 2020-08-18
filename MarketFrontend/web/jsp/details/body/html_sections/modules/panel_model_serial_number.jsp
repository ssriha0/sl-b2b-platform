<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
<head>
	<!-- Priority 5B changes -->
	<style>
		.customRefError {background-color:#FFF8BF;border:1px solid #FFD324;width:660px;padding:5px;color:#FF8000;}
		#modelSerialEdit {border:solid 1px #ddd;width:680px;}
		.panel-heading{background-image: linear-gradient(to bottom, #f5f5f5 0px, #e8e8e8 100%);border-bottom: solid 1px #ddd;padding:5px;font-weight:bold;}
	</style>
	
	<script type="text/javascript">

		//show edit option
		function toggleEdit(refType){
			jQuery("#custRefErr").html("");
			jQuery("#custRefErr").hide();
			jQuery("#"+refType+"Display").toggle();
			jQuery("#"+refType+"Edit").toggle();
		}
	</script>

</head>
	
<body>

	<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
	<c:if test="${null != modelSerialValues && not empty modelSerialValues}">
	
		<div id="modelSerialEdit">
		
			<div class="panel-heading"> Model &amp; Serial Number </div>
			
			<div style="padding:5px">
				<c:if test="${null != modelSerialErrorMsgs && not empty modelSerialErrorMsgs}">	
					<p></p>
					<div class="customRefError">
						
						<input type="hidden" id="modelSerialInd" value="${modelSerialInd}" />
						
						<b>The Provider has entered a model or serial number that needs to be reviewed and potentially updated.</b>
							
						<ul>
							<c:forEach items="${modelSerialErrorMsgs}" var="errorMsg">
								<li style="margin-left:20px">${errorMsg}</li>
							</c:forEach>
						</ul>
						
					</div>
				</c:if>
			
				<p></p>	
				Please review the Provider&apos;s submission and edit the model and serial numbers if needed.<br>
				
				<div id="custRefErr" class="errorBox" style="padding:5px;display:none;">
					<span class="errorMsg"></span>
				</div>
				<p></p>
			
			
				<c:forEach items="${modelSerialValues}" var="reference">
						
					<div>
						<c:if test="${null != reference && 'MODEL' == reference.refType}">
							<b>Model Number:</b>
						</c:if>
								
						<c:if test="${null != reference && 'SERIALNUMBER' == reference.refType}">
							<b>Serial Number:</b>
						</c:if>
											
						<span id="${reference.refType}Display">
						
							<span id="${reference.refType}Old">${reference.refValue}</span>
							&nbsp;&nbsp; 
							<span onclick="toggleEdit('${reference.refType}');">
								<a href="javascript:void(0);" > Edit </a>
							</span>
							
						</span>											
								
						<input type="hidden" id="${reference.refType}Req" value="${reference.requiredInd}"/>
											
						<span id="${reference.refType}Edit" style="display:none;">
							<input type="text" id="${reference.refType}" value="${reference.refValue}" /> &nbsp;&nbsp;
												
							<input type="image" src="${staticContextPath}/images/common/spacer.gif" 
									height="20" width="49" class="btn20Bevel inlineBtn" id="${reference.refType}Save"
									style="background-image: url(${staticContextPath}/images/btn/save.gif);"
									onclick="saveCustRef('${reference.refType}');"/>
													
							&nbsp;&nbsp; <span id="${reference.refType}Disable" style="display:none;">Saving...</span>
												
							&nbsp;&nbsp; 
							<span onclick="toggleEdit('${reference.refType}');">
								<a id="${reference.refType}Cancel" href="javascript:void(0);" > Cancel </a>
							</span>
						</span>
					</div>
							
					<p></p>	
				</c:forEach>
			</div>
		</div>
	</c:if>
	
</body>
</html>