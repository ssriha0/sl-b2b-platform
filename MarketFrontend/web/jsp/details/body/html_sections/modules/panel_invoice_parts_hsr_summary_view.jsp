<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<html>
<head>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dashboard.css" />
<style type="text/css">
 .warrantyParts{
   width:5%;
  }

</style>
<script type="text/javascript">
function expandCompInvParts(path){
	jQuery("#sodRestricted p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#compInvPartsid").slideToggle(300);
	 var ob=document.getElementById('compInvPartsImg').src;
      if(ob.indexOf('arrowRight')!=-1){
         document.getElementById('compInvPartsImg').src=path+"/images/widgets/arrowDown.gif";
      }
     if(ob.indexOf('arrowDown')!=-1){
       document.getElementById('compInvPartsImg').src=path+"/images/widgets/arrowRight.gif";
       }
   }
</script>
</head>
<body>
<div id="sodRestricted" class="menugroup_list">
   <p class="menugroup_head" onclick="expandCompInvParts('${staticContextPath}')">&nbsp;
      <img id="compInvPartsImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts
   </p>
   <div class="menugroup_body" id="compInvPartsid">
        <span  class="partSummaryViewDescription" id="partSummaryViewDescription">
              The Parts listed below are added to the service order by  provider
       </span>
   <c:if test = "${null != summaryDTO.invoiceParts && not empty summaryDTO.invoiceParts}">
     <table id="addtable" width="80%" class="installed_parts" cellpadding="0" border="1" bordercolor="grey" style="display: block;margin-left: 50px;margin-top:10px;">
         <thead>
			<tr>
				<td class="installed_parts_odd" align="center" style="width:5%;"><b>Part Number</b></td>
				<td class="installed_parts_odd" align="center" style="width:5%;"><b>Part Name</b></td>
				<td class="installed_parts_odd" align="center" style="width:5%;"><b>Part Status</b></td>
			</tr>
		</thead>
		<tbody>
		   <c:forEach items="${summaryDTO.invoiceParts}" var="invoicePart" varStatus="status" >
		    <tr id="tablerowEdit${status.count}">
				<td class="warrantyParts" id="partNoEdit_${status.count}"  align="center">
				    ${invoicePart.partNo}
				</td>
				<td class="warrantyParts" id="partnameEdit_${status.count}" align="center" >
				  <div id="partEditPart_${status.count}" style='width: 100px; word-wrap: break-word'>
					${invoicePart.description}
                 </div>
				</td>
				<td class="warrantyParts" id="partStatusEdit_${status.count}" align="center">
				  <div style="word-wrap: break-word">
						 ${invoicePart.partStatus}
				  </div>
				</td>
			</tr>
		</c:forEach>
	   </tbody> 
	   </table>
    </c:if>
</div>
</div>
</body>
</html>