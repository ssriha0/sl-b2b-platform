<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<head>
</head>
<body>
<c:set var="staticContextPath" value="/ServiceLiveWebUtil" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<style>

.histDetailsDiv{
 border: 3px solid #A8A8A8!important; 
  border-color:#A8A8A8 #A8A8A8 #A8A8A8 #A8A8A8!important;
 -webkit-border-radius: 6px 6px 6px 6px;
	   -moz-border-radius: 6px 6px 6px 6px;
	        border-radius: 6px 6px 6px 6px;
width:170px;
height: auto;
display:none; 
background: #FFFFFF;
z-index: 99999; 
margin-left:18.4%;
margin-top: -4%;
position: absolute;
font-family: Verdana; font-size: 10px; font-weight: normal; font-style: normal; text-decoration: none;
}	
.arrowAddNoteDetails{
    			    border-color: transparent #A8A8A8 transparent transparent !important;
 					border: 10px solid; 
				    height: 0;
				    width:0;
				    left: 80%;
				    margin-top: -2.0%;
				    margin-left:-11.7%;
				    z-index: 99999; 
				    display: none;	
				    position: absolute;			    
		}
</style>

<%-- list will be generated through iteration --%>

<ul class="expandableList">


	<input type="hidden" id="networkSize" name="networkSize" value="${spnCount}" />
	<c:forEach items="${networks}" var="network">
		<li>
			<h4>
			<c:if test="${network.overrideInd}">
				<span style="color:#FF0000; font-weight:bold;">Buyer Override - </span>
			</c:if>
				${network.networkName} - <span>
				<c:choose>
					<c:when test="${network.status == 'SPN Approved'}">Network Provider</c:when>
					<c:when test="${network.status == 'SPN Removed'}">Removed</c:when>
					<c:when test="${network.status == 'SPN Out Of Compliance'}">Inactive</c:when>
				 	<c:otherwise>${network.status}</c:otherwise>
				</c:choose>				
				 </span>
				
			</h4>
			<input type="hidden" id="existingNetworkStatus_${network.networkId}" name="existingNetworkStatus" value="${network.statusId}" />
			<input type="hidden" id="overrideInd_${network.networkId}" name="overrideInd" value="${network.overrideInd}" />
			<input type="hidden" id="hasAliasInd_${network.networkId}" name="hasAliasInd" value="${network.hasAliasInd}" />
			<input type="hidden" id="spnEditEffectiveDate_${network.networkId}" name="spnEditEffectiveDate" value="${network.spnEditEffectiveDate}" />
			<input type="hidden" id="spnOverrideEffectiveDate_${network.networkId}" name="spnOverrideEffectiveDate" value="${network.spnOverrideEffectiveDate}" />
			<input type="hidden" id="networkId" name="networkId" value="${network.networkId}" />
			<input type="hidden" id="networkGroup" name="networkGroup" value="${network.networkGroup}" />
			<div class="networkDetail" id="${network.networkId}">
				<h5>
					Network Status:
					<span>${network.status}</span>
					<tags:authorities authorityName="Edit Member Manager" >
						<img class="editNetworkStatus"
							src="${staticContextPath}/images/icons/pencil-transp.gif"
							alt="Click to Edit" title="Click to Edit" />
					</tags:authorities>
				</h5>
				<div class="editNetworkStatusForm"></div>
				<c:if test="${showNetworkGroupSection}">
					<h5>
						Network Group
						<img title="${network.networkDescription}" src="${staticContextPath}/images/spn/priority${network.networkGroup}small.gif">
						<tags:authorities authorityName="Edit Member Manager" >
							<img class="editNetworkGroup"
								src="${staticContextPath}/images/icons/pencil-transp.gif"
								alt="Click to Edit" title="Click to Edit" />
						</tags:authorities>
					</h5>
				</c:if>
				<div class="editNetworkGroupForm"></div>
				
				<div style="background-color: gray;color: white;">
				<span>
				<h6 style="width:400px; float:left;">
					Company Requirements&nbsp;<img class="toggleCompanyRequirements"
						src="${staticContextPath}/images/common/plus-big.png" />
				</h6>
				</span>
				<span>
				<h6>
					Requirement Status
				</h6>
				</span>
				</div>
				<div class="companyRequirements"><%-- html comes from existing jsp --%></div>
				<br/>
				<div style="background-color: gray;color: white;">
				<span>
				<h6 style="width:200px; float:left;">
					Provider Requirements&nbsp;
					<img class="toggleProviderRequirements"
						src="${staticContextPath}/images/common/plus-big.png" />
				</h6>
				</span>
				<span>
				<img style="margin-left:120px;" title="In Compliance" src="${staticContextPath}/images/common/status-green-shade.png"
								alt="In Compliance" />
				</span>
				<span><img  style="margin-left:68px;" title="In compliance due to buyer override" src="${staticContextPath}/images/common/status-blue-shade.png"
								alt="In compliance due to buyer override" /></span>
						<span>
						<img style="margin-left:73px;"  title="Out of compliance" src="${staticContextPath}/images/common/status-yellow-shade.png"
								alt="Out of compliance" /></span>
				</div>
				<div class="providerRequirements" style="width: 100%"><%-- html comes from existing jsp --%></div>
			</div>
		</li>
	</c:forEach>
</ul></body>