<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<link href="${staticContextPath}/css/ui-15642.css" rel="stylesheet"/>
<style>

.expDetailsDiv{
 border: 3px solid #A8A8A8!important; 
  border-color:#A8A8A8 #A8A8A8 #A8A8A8 #A8A8A8!important;
 -webkit-border-radius: 6px 6px 6px 6px;
	   -moz-border-radius: 6px 6px 6px 6px;
	        border-radius: 6px 6px 6px 6px;
width:210px;
height: auto;
display:none; 
background: #FFFFFF;
z-index: 99999; 
margin-left: 2% !important;
margin-top: 0.5%;
position: absolute;
font-family: Verdana; font-size: 10px; font-weight: normal; font-style: normal; text-decoration: none;
}	
.arrowAddNoteHist{
    			    border-color: transparent transparent #A8A8A8!important;
 					border: 10px solid; 
				    height: 0;
				    width:0;
				    left: 80%;
				    margin-top: -1%;
				    margin-left:-17.5% !important; 
				    z-index: 99999; 
				    display: none;	
				    position: absolute;			     
		}
a:hover,a:active,a:link,a:visited{color:#00a0d2;text-decoration: none;}


.histTable td{
	border-bottom:1px #D8D8D8 solid !important;
}

.histTable{
	border-left:1px #D8D8D8 solid !important;
	border-right:1px #D8D8D8 solid !important;
}
</style>
<script>

function loadDetails(e,spnId,count) {
	var lastId = $("#countH_"+spnId).attr("value");
	if(lastId!=""&& lastId!='undefine'){
		$("#expDetails_"+lastId).css("display","none");
		$("#arrow_"+lastId).css("display","none");
	}
	var click = $("#detailsLink_"+spnId+"_"+count);
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

	$("#countH_"+spnId).attr("value",spnId+"_"+count);
	
/*	$("#expDetails_"+count).css("height", "auto");
/* 	$("#expDetails_"+count+" .arrowAddNote").css("border-bottom-color","#A8A8A8");
	$("#expDetails_"+count+" .arrowAddNote").css({"left": "80%"});
	var ht = $("body").height();
	var diff = ht-y;
		$("#expDetails_"+count).css({"top" : ht-462,"marginLeft":"-15px"});
/* 		$("#expDetails_"+count+" .arrowAddNote").css("border-left-color","#A8A8A8");
 */		// Assuming the height of the widget is 253 
/* 		$("#expDetails_"+count+" .arrowAddNote").css({"left": "100%","margin-left":"5px","top":"-55px"}); */
	if (jQuery.browser.mozilla)
	{
	$("#expDetails_"+spnId+"_"+count).css("margin-left",x-807);
	$("#arrow_"+spnId+"_"+count).css("margin-left",x-1071);
	}
	if (jQuery.browser.msie)
	{	
		$("#expDetails_"+spnId+"_"+count).css("left",x-50);
		 $("#expDetails_"+spnId+"_"+count).css("margin-top",y);
		$("#arrow_"+spnId+"_"+count).css("left",x);
		// $("#arrow_"+spnId+"_"+count).css("margin-left","-60px");
		// $("#arrow_"+spnId+"_"+count).css("margin-top","0px");
 		$(".arrowAddNoteHist").css("border-color","transparent");
		$(".arrowAddNoteHist").css("border-bottom-color","#A8A8A8");
		$(".arrowAddNoteHist").css("border-left-color","transparent"); 
	}
	$("#expDetails_"+spnId+"_"+count).show();
	$("#arrow_"+spnId+"_"+count).show();
}
function closeDetails(count,spnId){
	$("#expDetails_"+spnId+"_"+count).css("display","none");
	$("#arrow_"+spnId+"_"+count).css("display","none");
}
</script>
<div class="history">
	<table border="0" cellspacing="0" class= "histTable">
		<thead><tr><th>Updated Date</th><th>Updated By</th><th>Action</th></tr></thead>
		<tbody>
			<c:forEach items="${networkHistory}" var="row" varStatus="status">
				<input id="countH_${row.spnId}" name="countH_${row.spnId}" type="hidden" value=""/>
				<tr><td width="35%" style="border-left: none!important;border-right:none!important;"><fmt:formatDate value="${row.archiveDate}"
									pattern="EEE MM/dd/yy hh:mm aa z" /></td>
				<td width="20%" style="border-left: none!important;border-right:none!important;">${row.firstName} ${row.lastName}</td>
				<td width="55%" style="border-left: none!important;border-right:none!important;">${row.statusDescription}
				<c:if test="${null!=row.credential}">
				<br/>(${row.credential})<span>&nbsp;<a class="link" style="text-align: left;" href="javascript:void(0);"
												onclick="loadDetails(event,${row.spnId},${status.count});"
												id="detailsLink_${row.spnId}_${status.count}">Details</a></span>
						<div class="arrowAddNoteHist" id="arrow_${row.spnId}_${status.count}"></div>
				<div class="expDetailsDiv" id="expDetails_${row.spnId}_${status.count}">
		<div class="preCallHistoryHead" style="height: 20px;width: 100%px;background: #EEEEEE;-moz-border-radius: 8px 8px 0 0;">							
					<span style="float:left;"><b>&nbsp;&nbsp;Exception Details</b>
					</span>
															<span style="font-family: Arial; float:right; font-size: 13px; font-weight: bold; font-style: normal; text-decoration: none; color: rgb(51, 51, 51); cursor: pointer;" onclick="closeDetails(${status.count},${row.spnId})" id="cross">X</span>
					
							</div>
								<div
											style="margin-bottom: 5px; padding-left: 5px; border-top: 1px solid #CCCCCC;">
									<div style="margin-top: 3px;padding-top: 5px;">
								 		<span><b>${row.credential}</b></span>
								 	</div>
								 	<div style="">
								 		<span style="font-weight: bolder;">.&nbsp;</span><span style="word-wrap:break-word;">${row.comments}</span>
								 	</div>
							</div>
				</div>
				</c:if>
				</td></tr>
			</c:forEach>	
		</tbody>
	</table>
		
</div>