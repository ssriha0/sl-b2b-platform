<%@ page language="java"  pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<style>
.credentials{
	background:#CCCCCC; 
	float: left;
	text-align: left;
}
.expDetailsDiv{
 border: 3px solid #A8A8A8!important; 
  border-color:#A8A8A8 #A8A8A8 #A8A8A8 #A8A8A8!important;
 -webkit-border-radius: 6px 6px 6px 6px;
	   -moz-border-radius: 6px 6px 6px 6px;
	        border-radius: 6px 6px 6px 6px;
width:230px;
height: auto;
display:none; 
background: #FFFFFF;
z-index: 99999; 
margin-left:13.9%;
margin-top: -3.5%;
position: absolute;
font-family: Verdana; font-size: 10px; font-weight: normal; font-style: normal; text-decoration: none;
text-indent: 0px;
}	
.arrowExpDetails{
    			    border-color: transparent #A8A8A8 transparent transparent !important;
 					border: 10px solid; 
				    height: 0;
				    width:0;
				    left: 80%;
				    margin-top: 0%;
				    margin-left:-64.5%;
				    z-index: 99999; 
				    display: none;	
				    position: absolute;			    
		}
a:hover,a:active,a:link,a:visited{color:#00a0d2;text-decoration: none;}
.spill{
    border-bottom: none!important;
}
dd.subitem span{
padding: 0!important;
}
</style>
<script>
function showExpDetails(e,key,type,spnId) {
	jQuery(".expDetailsDiv").css("display","none");
	jQuery(".arrowExpDetails").css("display","none");
	var click = jQuery("#cred"+type+"_"+key+"_"+spnId);
	var x = e.pageX;
	var y = e.pageY;
	if (jQuery.browser.mozilla)	{
		var offs = click.position();
		try{
			x= offs.left;
			y = offs.top;
		}catch (e) {
			x = e.offsetX;
			y=e.offsetY;
		} 
	}
	if (jQuery.browser.msie){
		var offs = click.position();
		try{
			x= offs.left+(click.width());
			x1 = click.width();
			y = click.height();
		}catch (e) {
			x = e.offsetX;
			y = e.offsetY;
		}
	}

		
	
/*	jQuery("#expDetails_"+count).css("height", "auto");
/* 	jQuery("#expDetails_"+count+" .arrowAddNote").css("border-bottom-color","#A8A8A8");
	jQuery("#expDetails_"+count+" .arrowAddNote").css({"left": "80%"});
	var ht = jQuery("body").height();
	var diff = ht-y;
		jQuery("#expDetails_"+count).css({"top" : ht-462,"marginLeft":"-15px"});
/* 		jQuery("#expDetails_"+count+" .arrowAddNote").css("border-left-color","#A8A8A8");
 */		// Assuming the height of the widget is 253 
/* 		jQuery("#expDetails_"+count+" .arrowAddNote").css({"left": "100%","margin-left":"5px","top":"-55px"}); */
if (jQuery.browser.mozilla){
	jQuery("#arrow"+type+"_"+key).css("margin-left", x-595);
	var height = jQuery("#expDetails"+type+"_"+key).height();
	/* if(height<=90){
		jQuery("#expDetails"+type+"_"+key).css("margin-top",y+height-820);
	}
	else{
		jQuery("#expDetails"+type+"_"+key).css("margin-top",y+height-860);
	} */
	}
 if (jQuery.browser.msie)
	{	jQuery("#expDetails"+type+"_"+key+"_"+spnId).css("margin-left",x1);
		jQuery("#expDetails"+type+"_"+key+"_"+spnId).css("margin-top",y-50);
		jQuery("#arrow"+type+"_"+key+"_"+spnId).css("left","0%");
		jQuery("#arrow"+type+"_"+key+"_"+spnId).css("margin-left",x-8);
		jQuery("#arrow"+type+"_"+key+"_"+spnId).css("margin-top","-3px");
		jQuery(".arrowExpDetails").css("border-color","transparent");
		jQuery(".arrowExpDetails").css("border-bottom-color","transparent");
		jQuery(".arrowExpDetails").css("border-top-color","transparent");
		jQuery(".arrowExpDetails").css("border-right-color","#A8A8A8");
	} 

	jQuery("#expDetails"+type+"_"+key+"_"+spnId).show();
	jQuery("#arrow"+type+"_"+key+"_"+spnId).show();
}
function closeExpDetails(key,type,spnId){
	jQuery("#expDetails"+type+"_"+key+"_"+spnId).css("display","none");
	jQuery("#arrow"+type+"_"+key+"_"+spnId).css("display","none");
}
</script>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
  	<form name='buyeragreemodal'>
  		<div id="errMsg"></div> 
  		  			<c:if test="${not empty companyRequirementsList && (not empty companyRequirementsList.insuranceMap ||  not empty companyRequirementsList.credentialMap)}">
  		<div id="companyReqsDiv" class="clearfix" style="display:''" >  			
			<span><h4 class="header" style="float: left; width: 500px;"><b>Company Requirements</b></h4></span><span>
				<h4 class="header">
					<b>Requirement Status</b>
				</h4>
				</span>
			<p class="note">An asterix <span class="req">*</span> Indicates credentials that must be verified by ServiceLive for membership. See the <a href="http://community.servicelive.com/docs/ServiceLive-Verification-Guide.pdf" target="_slVerificationGuide">ServiceLive Verification Guide</a>.</p>
  			<div class="spill" align="left"  style="width:558px;float:left;margin-left:-65px;"> 
  					<c:if test="${not empty companyRequirementsList.insuranceMap}">	
  					  	<dl>  
  					  	<dt class="title" style="background-color:#eeeeee">Insurance</dt>  							
  							<c:forEach items="${companyRequirementsList.insuranceMap}" var="item">
  								<c:choose>  
   									<c:when test="${item.value =='1'}"> 			
  										<dd class="subitem" >
  											<span style="border-left:none;">  
  												<a  style="margin-left:-55px;"  title="At least one provider meets this requirement."><img src="${staticContextPath}/images/common/status-green.png" /></a>
  											</span>
  											<strong>${item.key} </strong>
  										</dd> 
  									</c:when>
  									<c:otherwise>  											
  										<dd class="subitem" ><span style="border-left:none;">										
  										<a style="margin-left:-55px;"   title="No providers meets this requirement."><img src="${staticContextPath}/images/common/status-yellow.png" /></a></span>
  										<strong>${item.key} </strong></dd> 
  									</c:otherwise>
  								</c:choose>	 					 
							</c:forEach>  							
  						</dl>
  					</c:if>
  					<c:if test="${not empty companyRequirementsList.credentialMap}">	
  					  	<dl>
  					  	<dt class="title" style="background-color:#eeeeee">Credentials</dt>  							
  							<c:forEach items="${companyRequirementsList.credentialMap}" var="item" varStatus="var">  														
  								<c:if test="${item.value.matchCriteria =='1'}">  										
  									<dd class="subitem"><span style="border-left:none;">  <a style="margin-left:-55px;"  title="At least one provider meets this requirement."><img src="${staticContextPath}/images/common/status-green.png" /></a></span>
  										<strong>${item.key}</strong>
  									</dd>  
  								</c:if>
  								<c:if test="${item.value.matchCriteria =='2'}">  										
  								<dd class="subitem">
  									<span style="border-left:none;">  
  										<a style="margin-left:-55px;"  title="In compliance due to buyer override"><img src="${staticContextPath}/images/common/status-blue.png" /></a>
  									</span>
  									<strong id="credc_${var.count}_${spnId}" style="border-bottom : 1px dotted;" onmouseover="showExpDetails(event,'${var.count}','c',${spnId})" onmouseout="closeExpDetails(${var.count},'c',${spnId})">${item.key}</strong>
  									<div class="arrowExpDetails" id="arrowc_${var.count}_${spnId}"></div>
									<div class="expDetailsDiv" id="expDetailsc_${var.count}_${spnId}">
										<div class="preCallHistoryHead"
											style="height: 20px; width: 100% px; background: #EEEEEE; -moz-border-radius: 8px 8px 0 0;">
											<span class="" style="float:left; border:none!important; "><b>&nbsp;&nbsp;Exception Details</b> </span> <span
												style="border:none!important;  font-family: Arial; float: right; font-size: 13px; font-weight: bold; font-style: normal; text-decoration: none; color: rgb(51, 51, 51); cursor: pointer;"
												onclick="closeExpDetails(${var.count},'c')" id="cache1">X</span>

										</div>
										<div
											style="margin-bottom: 5px; padding-left: 8px; border-top: 1px solid #CCCCCC; padding-top: 5px;">
											<div style="margin-top: 3px; padding-top: 5px;border:none!important; float:left!important;">
												<span style="border:none!important; float:left!important;"><b>${item.value.exclusionsVO[0].credentialType}</b></span>
											</div>
											<c:forEach items="${item.value.exclusionsVO}" var="vendCredExpIter">
										<c:if test="${vendCredExpIter.exceptionTypeId ==2}">
											<div style="border:none!important; float:left!important;">
												<span style="font-weight: bolder;border:none!important; float:left!important;">.&nbsp;&nbsp;&nbsp;</span>
												<span style=" border: medium none ! important; float:left! important; margin-left:-10px;">Dispatch Location : ${item.value.state}
												</span>
											</div>
											<div style="border:none!important; float:left!important;">
												<span style="font-weight: bolder;border:none!important; float:left!important;">.&nbsp;&nbsp;&nbsp;</span>
												<span style=" border: medium none ! important; float:left! important; margin-left:-10px;width:90%!important;word-wrap:break-word;">
												<c:forEach items="${vendCredExpIter.excepmtedStates}" var="vendCredExpStates" varStatus="varStat">
												<c:choose>
												<c:when test="${(vendCredExpStates ==  item.value.state)}">
												<b>${vendCredExpStates}</b>
												</c:when>
												<c:otherwise>
												${vendCredExpStates}
												</c:otherwise>
												</c:choose>
												<c:if test="${!varStat.last}">
												,
												</c:if>
												</c:forEach>
												</span>
											</div>
											</c:if>
											<c:if test="${vendCredExpIter.exceptionTypeId ==1}">
											<div style="border:none!important; float:left!important;">
												<span style="font-weight: bolder;border:none!important; float:left!important;">.&nbsp;</span>Expiration date on file : <fmt:formatDate value="${item.value.expirationDate}"
													pattern="MM/dd/yy" />
											</div>
											<div style="border:none!important; float:left!important;">
												<span style="font-weight: bolder;border:none!important; float:left!important;">.&nbsp;</span>${vendCredExpIter.exceptionValue}
											</div>
											</c:if>
											</c:forEach>
										</div>
									</div>
  								</dd>  
  								</c:if> 
  								<c:if test="${item.value.matchCriteria =='0'}">  										
  									<dd class="subitem">
  										<span style="border-left:none;">											
  											<a style="margin-left:-55px;"  title="No providers meets this requirement."><img src="${staticContextPath}/images/common/status-yellow.png" /></a>
  										</span>
  									<strong>${item.key}</strong>
  									</dd>  
  								</c:if>  														 
							</c:forEach>  							
  						</dl>
  					</c:if>  					
  		</div>
  	</div>
  	<br />
  	<c:if test="${null != complFirmUpdatedDate}"><span id="complFirmUpdated" style="float:right; margin-right:-136px;">Compliance status last updated: <fmt:formatDate value="${complFirmUpdatedDate}" pattern="hh:mm a MM/dd/yy" /></span></c:if>
  		  	<span id="complFirmUpdatedAudit" style="float:right;display:none;">Compliance status last updated: <fmt:formatDate value="${complFirmUpdatedDate}" pattern="hh:mm a MM/dd/yy" /></span>
  			<br/>			
	  			</c:if>
	</form>
