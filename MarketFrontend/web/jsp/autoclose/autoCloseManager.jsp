<%@ page language="java" import="java.util.*, com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
   
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="maxComSize" scope="session" value="85" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
    <head>
    	<title>ServiceLive - Manage Auto Close & Pay Rules</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>

        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/autocloserules.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection" />

<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/autoclose-driver.js"></script>
  <script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		<style type="text/css">
	.ie7 .bannerDiv{margin-left:-1150px;}  
		</style>
    </head>
    <body>
	<div id="wrap" class="container">
		<tiles:insertDefinition name="blueprint.base.header"/>
		<tiles:insertDefinition name="blueprint.base.navigation"/>
		<div id="content" class="span-24 clearfix">		
        
        <div id="wrapper">
            <h1>Administrator Office</h1>
            <h2>Manage Auto Close & Pay Rules</h2>
            <br><br><br><br><br>
        <h3>&nbsp;Manage Rules</h3><br>
               Only service orders that do NOT meet ANY of the rules selected below will be Auto Closed.
       <br><br>
	<div id="autocloseSearchValidationError" class="error errorMsg">
		<p style="padding: 3px;" class="errorText"></p>
	</div>	   
	   <div id="successPriceUpdate" style="display: none;" ></div>
        </div>

			<table><tr><td width="25%" style="vertical-align: top;">
				<div class="" style="width: auto;padding-left: 1%;">
					<table id="autoCloseRules" border="0" cellpadding="0" cellspacing="0" >
						<c:forEach var="autoCloseRule" items="${model.autoCloseRuleDTOList}">
						<c:set var="exclusionListInd" value="false"  ></c:set>
						<c:if test="${ autoCloseRule.autoCloseRuleName == 'FIRM LISTED'}">
							<c:set var="exclusionListInd" value="true"  ></c:set>
							<input type="hidden" id="firmRuleHdrId" value="${autoCloseRule.autoCloseRuleHdrId}" />
						</c:if>
						<c:if test="${ autoCloseRule.autoCloseRuleName == 'PRO LISTED'}">
							<c:set var="exclusionListInd" value="true" ></c:set>
							<input type="hidden" id="proRuleHdrId" value="${autoCloseRule.autoCloseRuleHdrId}" />
						</c:if>
						
						<c:if test="${!exclusionListInd}">
							<tr border="0" >
								<td border="0" style="width: auto;padding-left: 2%;padding-bottom: 3%;"><img alt="" src="${staticContextPath}/images/s_icons/tick.png"/></td> 
								<td border="0" style="width: auto;padding-left: 2%">${autoCloseRule.autoCloseRuleName} - ${autoCloseRule.autoCloseRuleDescription}
									
									<c:if test="${ autoCloseRule.autoCloseRuleName == 'MAX PRICE'}">
										<div ><input type="hidden" id="criteriaId" value="${autoCloseRule.autoCloseRuleCriteriaId}" />
											$ <input type="text" class="numbersOnly" name="maxPrice" maxlength="9" id="maxPrice" value="${autoCloseRule.autoCloseRuleCriteriaValue}" style="width: 60px;"></input>
											<a id = "updatePrice" href="javascript:void(0);"><u>Update Amount</u></a><br>
											
											<c:if test="${autoCloseRule.autoCloseRuleCriteriaHistoryList != null}"> 
												<c:if test="${not empty autoCloseRule.autoCloseRuleCriteriaHistoryList}"> 
												<c:set var="criteriaHistory" value="${autoCloseRule.autoCloseRuleCriteriaHistoryList}" ></c:set>
													<a style="padding-left: 82px;" id = "viewHistoryLink" href="javascript:void(0);"><u>View History</u></a>
												</c:if>
												<c:if test="${empty autoCloseRule.autoCloseRuleCriteriaHistoryList}">
													<a style="padding-left: 82px;display:none;" id = "viewHistoryLink" href="javascript:void(0);"><u>View History</u></a>
												</c:if>
											</c:if>
										</div>	
									</c:if>
								</td>

							</tr>
						</c:if>
						</c:forEach>
					</table>
				</div>
				</td><td  style="vertical-align: top;">
				<b>Add to Auto Close Exclusion List</b><br>
Orders completed by firms or providers you select will not be auto-closed.
				<div class=""><jsp:include page="autoCloseSearchPanel.jsp" /></div><br />
				</td></tr></table>
				
				<div id="wrapper" style="border-top: solid 1px gray;">
				
					<h4 id="listHead" style="display: inline;padding-right: 2%;">Provider Firm Exclusion List</h4>

					<h6 style="display: inline;">
						<a href="javascript:void(0);" >
							<img id="prevList" src="${staticContextPath}/javascript/dojo/dojox/presentation/resources/icons/prev.png" alt="Prev" />
						</a>
						<a id="listedIdLink" href="javascript:void(0);"><u>LISTED</u></a> | <a id="removedIdLink" href="javascript:void(0);" ><u>REMOVED</u></a>
						<a href="javascript:void(0);" >
							<img id="nextList" src="${staticContextPath}/javascript/dojo/dojox/presentation/resources/icons/next.png" alt="Next" />
						</a>

						<a href="javascript:void(0);" >
							<img id="prevType" src="${staticContextPath}/javascript/dojo/dojox/presentation/resources/icons/prev.png" alt="Prev" />
						</a>
					  	<a id="firmsIdLink" href="javascript:void(0);" ><u>FIRMS</u></a> | <a id="providersIdLink" href="javascript:void(0);" ><u>PROVIDERS</u></a>
					  	<a href="javascript:void(0);" >
							<img id="nextType" src="${staticContextPath}/javascript/dojo/dojox/presentation/resources/icons/next.png" alt="Next" />
						</a>
					</h6>
					<br><div id="addnMsg" style="font-family: arial;font-size: 12px;font-weight: bold;"></div>					
					<div id="successMsg" style="display: none;"></div>
					<div id="autocloseExcludeValidationError" style="margin-top: 10px;" class="error errorMsg errorMessage">
						<p class="errorText"></p>
					</div>					
					<div id="exclusionList"> 
						<jsp:include page="autoCloseFirmExclusionList.jsp" />
					</div>	

						<div id="layer1">
			<div id="layer1_handle" >
				<img id="close" src="${staticContextPath}/images/icons/grayCloseX.png" alt="Close" />
				<B> History for Maximum Price Limit </B>
			</div>
			<div id="layer1_content_head">
				<table style="border-bottom: solid 1px #ccc;">
					<tr >
						<td style="width:70px">
							<B>Price</B>
						</td>
						<td style="width:70px">
							<B>Date</B>
						</td>
						<td style="width:100px">
							<B>Changed By</B>
						</td>
					</tr>

				</table>
				</div>
			<div id="layer1_content_body" class="scroll">
				<table>
<c:forEach var="criteria" items="${criteriaHistory}">
<tr >
	<td style="width:70px;padding: 2px;" valign="top" >$${criteria.autoCloseRuleCriteriaValue}</td>
	<td style="width:70px;padding: 2px;" valign="top">${criteria.modifiedDateFormatted}</td>
	<td style="width:100px;padding: 2px;" valign="top">${criteria.modifiedBy}</td>
</tr>
</c:forEach>
					</table>
			<c:if test="${fn:length(criteriaHistory) > 10 }">
				<script>$("#layer1_content_body").height(300);</script>
			</c:if>
			</div>
	</div>
				
				
	</div>
		
       <tiles:insertDefinition name="blueprint.base.footer"/>
       </div>
       </div>
		<!--  end of tabs div -->
		<div id="loadSearchSpinner" class="jqmWindow">
			<div class="modal-content">
				<!-- <label>
					<span>Gathering search results, please wait...</span>
				</label> -->
				<div>
					<img src="${staticContextPath}/images/simple/searchloading.gif" />
				</div>
				<div class="clearfix">
					<a class="cancel jqmClose left" href="#">Cancel</a>
				</div>
			</div>
		</div>       
    </body>
</html>