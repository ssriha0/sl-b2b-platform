<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<style type="text/css"> 
.provTable td  {
	border: 0;
	float:left;
}
.provTable span{
border-left: none!important;
}
#providerReqsDiv dd{
height:auto!important;
}
</style>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

		
<div id="errMsg"></div>   				 
		  			<c:if test="${not empty providerRequirementsList && (not empty providerRequirementsList.ratingMap || not empty providerRequirementsList.credentialMap || not empty providerRequirementsList.languageMap || not empty providerRequirementsList.locationMap || not empty providerRequirementsList.marketMap || not empty providerRequirementsList.completedSOMap || not empty providerRequirementsList.servicesList || not empty providerRequirementsList.backgroundCheckMap)}">
		<div id="providerReqsDiv" class="clearfix" style="display:''" > 
  			<div class="header" style="background-color: #33393C"><span><h4 class="header" style="width:221px; float:left;"><b>Provider Requirements</b></h4></span>
  			<span class="header">
				<img style="margin-left:60px;" title="In Compliance" src="${staticContextPath}/images/common/status-green-shade.png"
								alt="In Compliance" />
				</span>
				<span><img  style="margin-left:100px;" title="Buyer override active" src="${staticContextPath}/images/common/status-blue-shade.png"
								alt="Buyer override active" /></span>
						<span>
						<img style="margin-left:80px;"  title="Out of compliance" src="${staticContextPath}/images/common/status-yellow-shade.png"
								alt="Out of compliance" /></span></div>
			<p class="note" align="left">An asterix <span class="req">*</span> Indicates credentials that must be verified by ServiceLive for membership. See the <a href="http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf" target="_slVerificationGuide">ServiceLive Verification Guide</a>.</p>
  			<div class="spill" align="left" style="width:558px;float:left;margin-left:-65px; border-bottom: none">
  				<c:if test="${not empty providerRequirementsList.ratingMap}">	
  					<dl >  							
  						<c:forEach items="${providerRequirementsList.ratingMap}" var="item">  
   							<c:if test="${item.value !=null}">  										
  								<dd class="subitem">
  									<table class="provTable">
  									<tr>
  									<td width="41%">
  									<span style="border-left:none!important;float:left;"><strong>Minimum Rating</strong>: <small>${item.key} or Higher</small></span>
  									</td>
  									<td width="19%">
									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount}  									
									</span>
  									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										- 									
									</span>
									</td>
									<td width="17%">
  									<span style="border-left:none;!important">
  										<c:choose>
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise></c:choose>
										
									</span>
									</td>
  									</tr>
  									</table>
  								</dd>  
  							</c:if>										 					 
						</c:forEach>  							
  					</dl>
  				</c:if>
  				<c:if test="${not empty providerRequirementsList.backgroundCheckMap}">	
  					<dl>   					 			
  							<c:forEach items="${providerRequirementsList.backgroundCheckMap}" var="item">    								 								
  								<c:if test="${item.value !=null}">  										
  								<dd class="subitem">
  									<table class="provTable"> 
  									<tr><td width="41%"> <span style="border-left:none!important;float:left;"><strong>${item.key}</strong></span>
  									</td>
  									<td width="19%">
  									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount}  									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										- 									
									</span>
									</td>
									<td width="17%">
									<span style="border-left:none;!important">
										<c:choose>  
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  	 	
										</c:choose>								
									</span>
									</td>
									</tr>
									</table>
  								</dd>  
  								</c:if>													 
							</c:forEach>  							
  						</dl>
  				</c:if>
  				<c:if test="${not empty providerRequirementsList.credentialMap}">	
  					<dl>
  					  <dt class="title" style="background-color:#eeeeee">Resource Credentials</dt>  							
  						<c:forEach items="${providerRequirementsList.credentialMap}" var="item" varStatus="var">  														
  							<c:if test="${item.value !=null}">  										
  								<dd class="subitem">
  									<table class="provTable">
  									<tr><td width="41%">
  								
									 <span style="border-left:none!important;float:left;"><strong>${item.key} <c:if test="${item.value.parentNode!=null && item.value.parentNode!='' && item.value.parentNode!=' '}">
									 > ${item.value.groupValue}</c:if></strong></span>
									</td>
									<td width="19%">
									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount}  									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										${item.value.overridedProvidersCount}									
									</span>
									</td>
									<td width="17%">
  									<span style="border-left:none;!important">  
  										<c:choose>
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  	
										</c:choose>								
									</span>
									</td>
  									</table>
  								</dd>
  								</c:if>  
						</c:forEach>  							
  					</dl>
  				</c:if>
  				<c:if test="${not empty providerRequirementsList.languageMap}">	
  					<dl> 
  					 	<dt class="title">Languages</dt>  							
  							<c:forEach items="${providerRequirementsList.languageMap}" var="item">    								 								
  								<c:if test="${item.value !=null}">  										
  								<dd class="subitem">
  									<table class="provTable"> 
  									<tr><td width="41%"> <span style="border-left:none!important;float:left;"><strong>${item.key}</strong></span>
  									</td>
  									<td width="19%">
  									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount}  									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										- 									
									</span>
									</td>
									<td width="17%">
									<span style="border-left:none;!important">
										<c:choose>  
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  	 	
										</c:choose>								
									</span>
									</td>
									</tr>
									</table>
  								</dd>  
  								</c:if>													 
							</c:forEach>  							
  						</dl>
  					</c:if>
  					<c:if test="${not empty providerRequirementsList.locationMap}">	
  					  	<dl> 
  					  	<dt class="title" >States</dt>  							
  							<c:forEach items="${providerRequirementsList.locationMap}" var="item">    								 								
  									<c:if test="${item.value !=null}">  										
  									<dd class="subitem">
  									 
  									 <table class="provTable"> 
  									<tr><td width="41%"> 
  									 <span style="border-left:none!important;float:left;"><strong>${item.key}</strong></span></td>
  									 <td width="19%">
  									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount}  									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										- 									
									</span>
									</td>
									<td width="17%">
									<span style="border-left:none;!important"> 
										<c:choose> 
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  		
										</c:choose>							
									</span>
  									</td>
  									</tr>
  									</table>
  									</dd>  
  									</c:if>														 
							</c:forEach>  							
  						</dl>
  					</c:if>
  					<c:if test="${not empty providerRequirementsList.marketMap}">	
  					  	<dl> 
  					  	<dt class="title">Markets</dt>  							
  							<c:forEach items="${providerRequirementsList.marketMap}" var="item">  															
  									<c:if test="${item.value !=null}">  										
  									<dd class="subitem">
									<table class="provTable"> 
  									<tr><td width="41%"> 
  									<span style="border-left:none!important;float:left;"><strong>${item.key}</strong></span>
  									</td>
  									<td width="19%">
  									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount}  									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										- 									
									</span>
									</td>
									<td width="17%">
									<span style="border-left:none;!important">  
										<c:choose>
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  	
										</c:choose>								
									</span>
  									</td>
  									</tr>
  									</table>
  									</dd>  
  									</c:if>														 
							</c:forEach>  							
  						</dl>
  					</c:if>
  					<c:if test="${not empty providerRequirementsList.completedSOMap}">	
  					  	<dl>   					  						
  							<c:forEach items="${providerRequirementsList.completedSOMap}" var="item">   																
  									<c:if test="${item.value !=null}">  										
  									<dd class="subitem">
  									<table class="provTable"> 
  									<tr><td width="41%"> 
  									<span style="border-left:none!important;float:left;"><strong>Minimum Completed Service Orders</strong>: <small>${item.key}</small></span>
  									</td>
  									<td width="19%">
  									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount} 									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">   
										- 									
									</span>
									</td>
									<td width="17%">
									<span style="border-left:none;!important">  
										<c:choose>
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  	
										</c:choose>									
									</span>
  									</td>
  									</tr>
  									</table>
  									</dd>  
  									</c:if>									 
							</c:forEach>  							
  						</dl>
  					</c:if>  
  					<c:set var="count" value="0"/>					
  					<c:if test="${not empty providerRequirementsList.servicesList}">	
  					  	<dl>   					  						
  							<c:forEach items="${providerRequirementsList.servicesList}" var="itemList">
  								<dt class="title" style="background-color:#eeeeee">Services &amp; Skills:&nbsp;<span class="sm">${itemList.mainService}</span></dt>
  								<c:forEach items="${itemList.skills}" var="item">  										
	  								<c:if test="${item.value !=null}">  										
  									<dd class="subitem">
  									<table class="provTable"><tr><td width="41%">
  									<span style="border-left:none!important;float:left;"><strong>${item.key}</strong></span>
  									</td>
  									<td width="19%">
  									<span style="border-left:none;!important">  
										${item.value.matchedProvidersCount} 									
									</span>
									</td>
									<td width="15%">
									<span style="border-left:none;!important">  
										- 									
									</span>
									</td>
									<td width="17%">
									<span style="border-left:none;!important">  
										<c:choose>
										<c:when test="${item.value.matchCriteria =='0'}">  
										${providerRequirementsList.totalResourceCount} 
										</c:when>
										<c:otherwise>${item.value.outOfCompliantProvidersCount} 									
										</c:otherwise>  	
										</c:choose> 									
									</span>
  									</td>
  									</tr>
  									</table>
  									</dd>  
  									</c:if>													 
								</c:forEach> 
							</c:forEach>   							
  						</dl>
  					</c:if>
  			</div>
  			</div>	

<br/>
<c:if test="${null != complProvUpdatedDate}"><span id="complProvUpdated" style="float:right;">Compliance status last updated: <fmt:formatDate value="${complProvUpdatedDate}" pattern="hh:mm a MM/dd/yy" /></span></c:if>
  			<br/></c:if>