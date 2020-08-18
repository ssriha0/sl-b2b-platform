
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">

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

<html>
    <head>
    	<title>ServiceLive - Manage Auto Close & Pay Rules</title>
		<tiles:insertDefinition name="blueprint.base.meta"/>

        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/autocloserules.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/modals.css" media="screen, projection" />

<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/inhomeautoclose-driver.js"></script>
 
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
            <br /><br/><br/><br/>
	<div id="autocloseSearchValidationError" class="error errorMsg">
		<p style="padding: 3px;" class="errorText"></p>
	</div>	   
        </div>

			<table><tr><td style="padding-left: 129px;">
				<br/><b>The default reimbursement rate is ${defaultReimursementRate} %</b>
				<div class=""><jsp:include page="inHomeAutoCloseSearchPanel.jsp" /></div><br />
				
				
				</td></tr></table>
				
				<div id="wrapper" style="border-top: solid 1px gray;">
				
					<h4 id="listHead" style="display: inline;padding-right: 2%;">Provider Firm Override List</h4>

					<h6 style="display: inline;">
						<a href="javascript:void(0);" >
							<img id="prevList" src="${staticContextPath}/javascript/dojo/dojox/presentation/resources/icons/prev.png" alt="Prev" />
						</a>
						<a id="listedLink" href="javascript:void(0);"><u>LISTED</u></a> | <a id="removedLink" href="javascript:void(0);" ><u>REMOVED</u></a>
						<a href="javascript:void(0);" >
							<img id="nextList" src="${staticContextPath}/javascript/dojo/dojox/presentation/resources/icons/next.png" alt="Next" />
						</a>

					</h6>
					<br/><div id="addnMsg" style="font-family: arial;font-size: 12px;font-weight: bold;"></div>					
					<div id="successMsg" style="display: none;"></div>
					<div id="autocloseExcludeValidationError" style="margin-top: 10px;" class="error errorMsg errorMessage">
						<p class="errorText"></p>
					</div>					
					<div id="overrideList"> 
						<jsp:include page="inhomeAutoCloseFirmOverridedList.jsp" />
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

