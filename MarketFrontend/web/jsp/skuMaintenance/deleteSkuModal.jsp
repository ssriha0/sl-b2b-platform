<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<style>
	.modal{
		padding: 0px 0px;
	}
	
</style>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


  	<div align="center" style="font-weight: bold;" id="popUpModal" class="modal">	
  	<div class="modalHomepage">
	        <div style="float:left;">Delete SKU</div>
	        <a href="javascript: void(0)" id="closeButton"
				class="btnBevel simplemodal-close" style="color: white; float: right;">
				<img src="${staticContextPath}/images/widgets/tabClose.png" alt="X">
			</a>
	        <br>	
	</div>
		  	<div class="modalheaderoutline" style="padding: 0px;overflow-x: hidden;">
	        <div class="rejectServiceOrderFrame" style="width: 100%; border: 0px">
			<div class="rejectServiceOrderFrameBody" style="width: 100%;">
		
			<div id="rspnseFrmBkEnd" style="width:100%;display: block;text-align: center">
		      ${resultMessage}
		    </div>
		    <div id="inactiveSkus" style="display:none;">
		      ${deleteSkuList}
		    </div> 
		    <table style="padding-top:9px"><br>
		     <tr>
		        <c:if test="${yesNoFlag}">		            
					<td style="padding-left:150px;"><input  id="noDeletion" type="button"  style="width: 60px;" value="No" class="button action" onclick="jQuery.modal.impl.close(true); return false;"></td>
					<td style="padding-left:10px;"><input class="button action" id="yesDeletion" type="button"  style="width: 60px;" value="Yes" onclick="confirmDelete();"></td>
				</c:if>
				<c:if test="${continueFlag}">
                    <td style="padding-left:180px;"><input class="button action" id="yesDeletion" type="button"  style="width: 60px;" value="Continue" onclick="jQuery.modal.impl.close(true); return false;"></td>				
                </c:if>	
			</tr>	
			</table>
	</div>
	</div>
	</div>
	</div>

   <!-- <div id="showContinueBtn" style="display: none;">
      <c:out value="${continueFlag}"></c:out>
   </div>
   <div id="showYesAndNo" style="display: none;">
     <c:out value="${yesNoFlag}"></c:out>
   </div> -->
