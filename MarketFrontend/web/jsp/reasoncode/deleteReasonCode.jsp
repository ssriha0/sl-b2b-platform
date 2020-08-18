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
		<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
		<script src="${staticContextPath}/javascript/jquery.simplemodal.1.4.4.min.js"
		      type="text/javascript"></script>
		<style type="text/css" media="screen">
		      .modal{
		        padding: 0px 0px 0px 0px;
		        width: 100%;
		      }
		    </style> 
	</head>         
<body>	
	
	
		<c:if test="${action == 'delete'}">
  		<div class="menugroup_list" id="delete_popup" style="border: 0px;">
  			<div id="generalcompletionId">
  				<div class="modalHomepage" style="background-color: black;background-image:url(${staticContextPath}/images/reason_code_manager/reason_code_delete_hdr.png);">
  					<span style="color:white;font-size:14px;float:left;">Delete Reason Code</span><a id="closeLink" style="color:white;font-size:13px;cursor: pointer;" onclick="jQuery.modal.impl.close(true);fnReturnFocus();">Close</a>
  				</div>
      	</div>
      
    			<div class="mainWellContent" style="text-align: center;">
    				<br /><br />
    				<span style="font-size:14px;">
    					<b style="text-align: center;">
    						Are you sure you want to delete the following<br>
    						Reason Code?<br><br>
    						<table style="table-layout: fixed;"><tr><td style="word-break: break-all;word-wrap:break-word;text-align:center;">
    							<b>&quot;${reasonCode}&quot;</b></td></tr></table>
    					</b>
    				</span><br /><br />
    				<table cellpadding="0" cellspacing="0" style="padding-left: 60px;">
    					<tr>
    						<td width="65%">
    							<a id="closeLink" style="color: red;cursor: pointer;" 
    								onclick="jQuery.modal.impl.close(true);fnReturnFocus();"><b><u>Back</u></b></a>
    						</td>  							
    						<td>  							
    							<input id="deleteButton" class="deleteReasonButton" type="button" onclick="deleteReason('${action}','${reasonCode}','${reasonCodeId}','${reasonCodeType}','${reasonTypeId}');"/>
    						</td>
    					</tr>
    				</table>				
    				<br />
  			</div> 
  		</div>
		</c:if>
		
		<c:if test="${action == 'archive'}">
  		<div class="menugroup_list" id="delete_popup" style="border: 0px;">
  			<div id="generalcompletionId">
  				<div class="modalHomepage" style="background-color: black;" >
  					<span style="color:white;font-size:14px;float:left;">Archive Reason Code</span><a id="closeLink" style="color:white;font-size:13px;cursor: pointer;" onclick="jQuery.modal.impl.close(true);fnReturnFocus();">Close</a>
  				</div>
      		</div>
      
    			<div class="mainWellContent" style="text-align: center;">
    				<br><br>
    				<span style="font-size:14px;">
    					<b style="text-align: center;">
    						There are existing orders with this reason code.<br>
    						Are you sure you want to archive this reason code?<br><br>
    						<table style="table-layout: fixed;"><tr><td style="word-break: break-all;word-wrap:break-word;text-align:center;">
    							<b>&quot;${reasonCode}&quot;</b></td></tr></table>
    					</b>
    				</span><br><br>
    				<table cellpadding="0" cellspacing="0" style="padding-left:60px;">
    					<tr>
    						<td width="65%">
    							<a id="closeLink" style="color: red;cursor: pointer;" 
    								onclick="jQuery.modal.impl.close(true);fnReturnFocus();"><b><u>Back</u></b></a>
    						</td>  							
    						<td>  							
    							<input id="archiveButton" class="archiveReasonButton" type="button" onclick="deleteReason('${action}','${reasonCode}','${reasonCodeId}','${reasonCodeType}','${reasonTypeId}');"/>  							
    						</td>
    					</tr>
    				</table>	
    				<br />
  			</div> 
  		</div>
		</c:if>		
</body>
</html>
