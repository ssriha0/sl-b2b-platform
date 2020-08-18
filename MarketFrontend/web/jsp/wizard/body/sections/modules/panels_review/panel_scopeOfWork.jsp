<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>

 <style>
.submenu_list {
margin-bottom: 4px;
margin-left: 25px;
margin-right: 35px;
position:relative;
	
}
.submenu_head {
	
	margin:1px;
    background: #9f9f9f url(../images/widgets/subtitleBarBg.gif)  no-repeat scroll 0 0;
    background-position:right; 
	padding: 1px 4px 2px 4px;
	cursor: pointer;
	font-weight: bolder;
	height:12px;
	color:#FFFFFF;

	
	
	 
}
	
.submenu_body {
	display:block;
	background: #cccccc;
	background:#EDEDED none repeat scroll 0 0;
border-width:0;
padding-left:10px;
position:relative;
}
.submenu_body a {
  display:block;
	
  padding-left:0px;
  font-weight:bold;
  text-decoration:none;

   
}
.submenu_body a:hover {

  text-decoration:underline;

}

 .priceReviewHistory
		{
		
			left:400px;
			top:800px;
		   width:215px;
		   position:absolute;
			background-color:white;
			border: 4px outset grey;
			z-index: 50;
			display:none;
		}
		.priceHistory_head
		{
			padding:0px;
			font-family: arial;
			font-size: 11px;
			font-weight:bold;
		}
		.priceHistory_body
		{
			padding:5px;
			font-family: arial;
			font-size: 10px;
			/*font-weight:bold;*/
		}
		#taskCmtsReview ul{
			padding-left:10px;
		}
		#taskCmtsReview ol{
			padding-left:15px;
		}		

 </style>

 <script type="text/javascript">
      
 function expandSubMenu(count,path){
var divId="subName"+count;
var bodyId="subBody"+count;
jQuery("#"+bodyId).slideToggle(300);
var ob=document.getElementById('subscopeImage'+count).src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('subscopeImage'+count).src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('subscopeImage'+count).src=path+"/images/widgets/arrowRight.gif";
}
}
 function showHistory(obj){

 var idVal=obj.id;
 var curleft = curtop = 0;
	if (obj.offsetParent) {
		curleft = obj.offsetLeft;
		curtop = obj.offsetTop;
		while (obj = obj.offsetParent) {
			curleft += obj.offsetLeft
			curtop += obj.offsetTop
		}
	}
curtop=curtop-260;
				
				document.getElementById(idVal+"priceReviewHistory").style.top=curtop+"px";
				document.getElementById(idVal+"priceReviewHistory").style.zIndex=99999;
				document.getElementById(idVal+"priceReviewHistory").style.display='block';
			

 }
 function hidehistory(obj){
 var idVal=obj.id;
document.getElementById(idVal+"priceReviewHistory").style.display='none';

 }
 </script>
<c:set var="MAX_CHARACTERS_WITHOUT_SCROLLBAR" value="<%= OrderConstants.MAX_CHARACTERS_WITHOUT_SCROLLBAR%>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Scope of Work"
	class="contentWellPane">
	<table cellpadding="0" cellspacing="0" class="contactInfoTable">
		<tr>
			<td class="column1">
				<p class="text11px">
					<strong>Service Location Information</strong>
					<br />
					${reviewDTO.locationContact.type}
					<br />
					${reviewDTO.locationContact.individualName}
					<br />
					${reviewDTO.locationContact.streetAddress}
					<br />
					<!-- <span class="mapThis" onmouseout="popUp(event,'mapThis')"
						onmouseover="popUp(event,'mapThis')"><img
							src="${staticContextPath}/images/icons/mapThis.gif"
							alt="Map This Location" class="inlineBtn" /> </span> -->
					${reviewDTO.locationContact.streetAddress2}
					<br />
					${reviewDTO.locationContact.cityStateZip}
				</p>
			</td>
		  	
			<td class="column2">
			  <p class="text11px">
			   <br />
			   <s:if test="%{reviewDTO.locationContact.phoneWork!=null}">	
				<strong>Work Phone </strong> <br />					
			   </s:if>
                <s:if test="%{reviewDTO.locationContact.phoneMobile!= null}">	
				<strong>Mobile Phone</strong><br />	
				</s:if>
                <s:if test="%{reviewDTO.locationContact.phoneHome!= null}">	
				<strong>Home Phone</strong><br />						
                </s:if>
                <s:if test="%{reviewDTO.locationContact.pager!= null}">	
				<strong>Pager</strong><br />	
                </s:if>
                <s:if test="%{reviewDTO.locationContact.other!= null}">	
				<strong>other </strong><br />		
                </s:if>
                <s:if test="%{reviewDTO.locationContact.fax!= null}">	
				<strong>Fax</strong><br />					
                </s:if>
                 <s:if test="%{reviewDTO.locationContact.email!= null}">	
				<strong>Email</strong><br />					
                </s:if>
               </p>
		     </td>
		     <td class="column3">
			  <p class="text11px">
			   <br />
			   <s:if test="%{reviewDTO.locationContact.phoneWork!=null}">	
				
						${reviewDTO.locationContact.phoneWork} <br />
			    </s:if>
                <s:if test="%{reviewDTO.locationContact.phoneMobile!= null}">	
				
				${reviewDTO.locationContact.phoneMobile}
				<br />	
                </s:if>
                <s:if test="%{reviewDTO.locationContact.phoneHome!= null}">	
				
					${reviewDTO.locationContact.phoneHome}
				<br />	
                </s:if>
                <s:if test="%{reviewDTO.locationContact.pager!= null}">	
				
					 ${reviewDTO.locationContact.pager}
				<br />	
                </s:if>
                <s:if test="%{reviewDTO.locationContact.other!= null}">	
				
				${reviewDTO.locationContact.other}
				<br />		
                </s:if>
                <s:if test="%{reviewDTO.locationContact.fax!= null}">	
				${reviewDTO.locationContact.fax}
			    <br />
			     <s:if test="%{reviewDTO.locationContact.email!= null}">
			     ${reviewDTO.locationContact.email}	
				<br />					
                </s:if>	
                </s:if>
               </p>
		     </td>
		     
		</tr>
	</table>
	<p>
		<strong>Service Location Notes</strong>
		<br />
		${reviewDTO.locationNotes}
	</p>
	<div class="hrText">Service Categories & Tasks</div>
	<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
		<tr>
			<td width="200">
				<strong>Main Service Category</strong>
			</td>
			<td width="300">
				${reviewDTO.mainServiceCategory}
			</td>
		</tr>
		<tr>
			<td>
				<strong>Categories Required</strong>
			</td>
			<td>
				${reviewDTO.categoriesRequired}
			</td>
		</tr>
		<tr>
			<td>
				<strong>Sub-Categories Required</strong>
			</td>
			<td>
				${reviewDTO.subcategoriesRequired}
			</td>
		</tr>
		<tr>
			<td>
				<strong>Skills Required</strong>
			</td>
			<td>
				${reviewDTO.skillsRequired}
			</td>
		</tr>
	</table>
	<div class="clear"></div>
	<br/>
	<c:set var="count" value="0"/>
	<c:set var = "seqNum" value  = "-1"/>
	<!-- NEW NESTED MODULE -->
	<c:forEach var="task" items="${reviewDTO.taskList}">
	<c:set var="count" value="${count+1}"/>
	<c:set var="subName" value="subName"/>
	<c:set var="subBody" value="subBody"/>
	<c:set var="subName" value="${subName}${count}"/>
	<c:set var="subBody" value="${subBody}${count}"/>
	<div  class="submenu_list" id="${subName}">
	<c:set var="subscopeImage" value="subscopeImage"/>
    <c:set var="subscopeImage" value="${subscopeImage}${count}"/>
			<div class="submenu_head" style="height:auto;"
				onClick="expandSubMenu('${count}','${staticContextPath}');">
				<table style="table-layout: fixed; width: 100%">
    		<tr>
						<td  id="taskImage" align="left" width="5%">
						   <img id="${subscopeImage}"
								src="${staticContextPath}/images/widgets/arrowDown.gif" />
						</td>
						<td id="taskTitle" align="justify" style="word-wrap: break-word;">
							     ${task.title}
    		            </td>
						<td id="taskPrice"align="right" width="20%">
    						<c:choose>
							<c:when test="${task.taskType==1 && reviewDTO.taskLevelPriceInd == true}">PERMIT PRICE PRE-PAID BY CUSTOMER: $${task.sellingPrice}</c:when>
							<c:when test="${(task.sequenceNumber==0|| empty task.finalPrice || task.finalPrice==0|| task.finalPrice==0.00) && reviewDTO.taskLevelPriceInd == true }">Task Not Priced</c:when>
							<c:when test="${task.taskType==0 && ! empty task.finalPrice && task.sequenceNumber!=0 && reviewDTO.taskLevelPriceInd == true}">Price: $ <fmt:formatNumber value='${task.finalPrice}' type='NUMBER' minFractionDigits='2' maxFractionDigits='2'/></c:when>
							</c:choose>
						</td>
						<td id="taskHistoryIcon" align="right" width="5%">	
							<c:if
								test="${ task.taskType==0 && fn:length(task.priceHistoryList)>1 && task.sequenceNumber != seqNum && reviewDTO.taskLevelPriceInd == true}"><a
									id="${count}" class="priceHistoryIcon"
									onmouseover="showHistory(this)" onmouseout="hidehistory(this)"
									href="javascript:void(0);"><img
										src="${staticContextPath}/images/widgets/dollaricon.gif" />
								</a>
							</c:if>
    		            </td>
  </tr>
				</table>
    </div>
			<c:set var="cntHist" value="0"/>
			<c:if test="${! empty task.sequenceNumber}">
				<c:set var = "seqNum" value  = "${task.sequenceNumber}"/> 
			</c:if>
  	
   <div  class="submenu_body" id="${subBody}">
			<table cellpadding="0" cellspacing="0" class="adjustedTableRowPadding">
				<tr>
					<td width="200">
						<strong>Category</strong>
					</td>
					<td width="300">
						${task.category}
					</td>
				</tr>
				<tr>
					<td>
						<strong>Sub-Category</strong>
					</td>
					<td>
						${task.subCategory}
					</td>
				</tr>
				<tr>
					<td>
						<strong>Skill</strong>
					</td>
					<td>
						${task.skill}
					</td>
				</tr>
			</table>
		 <%--
		     <div class="hrText">
				Assessment Questions
			</div>
			
			<table cellpadding="0" cellspacing="0">
				<c:forEach var="question" items="${task.questionList}">
					<tr>
						<td width="400">
							${question.label}
						</td>
						<td width="200">
							${question.value}
						</td>
					</tr>
				</c:forEach>
			</table>
			--%>	
			<p>
				<strong>Task Comments</strong>
				<br />
				<c:choose>
					<c:when test="${fn:length(task.comments) > MAX_CHARACTERS_WITHOUT_SCROLLBAR}">
						<div id="taskCmtsReview" class="inputArea" style="height: 200px;width: 600px;background:#EDEDED;word-wrap: break-word;text-wrap: normal;" >
							${task.comments}
						</div>
					</c:when>
					<c:otherwise>
						<div id="taskCmtsReview" style="width: 600px; word-wrap: break-word;text-wrap: normal;">${task.comments}</div>
					</c:otherwise>
				</c:choose>
				
			</p>
		</div>
		</div>
	</c:forEach>
	
	<%-- <div class="hrText">General Scope of Work Comments</div>
	<p>
		Nulla aliquet suscipit tortor. Nulla accum san nibh at eros. Nullam
		fringilla orci at tortor. Sed a sapien. Curabitur a velit. In
		malesuada tortor eget nulla. Ut commodo dolor a lorem. Nunc ante urna
		laoreet nisi, eget congue ante sapien non libero.
	</p>
	--%>
</div>
<c:set var="countTask" value="0"/>
<c:forEach var="task" items="${reviewDTO.taskList}" varStatus="rowCounter">
<c:set var="count" value="${rowCounter.count}"/>
<c:set var="cntList" value="0"/>
<c:forEach var="history" items="${task.priceHistoryList}">
<c:set var="cntList" value="${cntList+1}"/>
</c:forEach>
<c:if test="${(task.taskType==null ||task.taskType!=1)}">
<c:set var="countTask" value="${countTask+1}"/>
</c:if>
<c:if test="${cntList>1}">

<div id ="${count}priceReviewHistory" class="priceReviewHistory" >
    	<table cellspacing="0">
	
 		<div class="priceHistory_head" style="float: left;">

    		<th colspan="3">Price History for Task ${countTask}</th>
    		<tr>
    		<th width="25%" style="border-bottom: 1px solid #ccc;">
    			Price
    		</th>
    		 <th width="35%" style="border-bottom: 1px solid #ccc;">
    			Date
    		</th>
    		<th width="40%" style="border-bottom: 1px solid #ccc;">
    			Changed By
    		</th> 
    		</tr> 
    </div>
    
    	 <div class="priceHistory_body" align="left" >
     	<c:set var="cntHist" value="0"/>
    		<c:forEach var="history" items="${task.priceHistoryList}">
    		<c:set var="cntHist" value="${cntHist+1}"/>
    		<tr>
    		
    		<td width="25%">
    		$<fmt:formatNumber	value='${history.price}' type='NUMBER'
					minFractionDigits='2' maxFractionDigits='2' /> 
    		</td>
    		
    		 <td  width="35%">
    		 ${history.modifiedDate}
    		</td>
    		
    		<td width="40%">
    		${history.modifiedByName}
    		<c:if test="${history.modifiedByName!='SYSTEM'}">
    		(ID#${history.modifiedBy})
    		</c:if>
    		
    		</td>
    		  </tr>

  </c:forEach>
    </div>
  	
    </tr>
    			</table>
    </div>

</c:if>
</c:forEach>
	

