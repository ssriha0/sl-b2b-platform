<%@page import="java.util.Date"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<c:set var="NEWCO_DISPLAY_SYSTEM" value="<%= OrderConstants.NEWCO_DISPLAY_SYSTEM %>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="SO_NOTE_MESSAGE_MAX_LENGTH" value="<%= OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH%>" />
<c:set var="SO_NOTE_SUBJECT_MAX_LENGTH" value="<%= OrderConstants.SO_NOTE_SUBJECT_MAX_LENGTH%>" />

<!-- SL-19050 -->
<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome-ie7.min.css" rel="stylesheet" />
<style>

.waitLayer{
			display: none;
			z-index: 999999999;
			height: 40px; 
			overflow: auto; 
			position: fixed;
			top: 450px;
			left: 35%;
			border-style:double;
			background-color: #EEEEEE;
			border-color: #BBBBBB;
			width: 125px;
			border-width: 4px;
			-webkit-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			-moz-box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);
			box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.2);}
.refreshMsg{
			background-image: url(${staticContextPath}/images/ajax-loader.gif);
			background-position: 20px center;
			background-repeat: no-repeat;
			padding-left: 50px; 
			padding-top: 5px;
			height: 20px;
			}
/*Flag Toggle
--------------------*/
.icon-flag.on {
	color:#DB3330;
	cursor:pointer;
	font-size: large;
}
.icon-flag.off {
	color:#ddd;
	cursor:pointer;
	text-shadow: 0px -1px rgba(0, 0, 0, 0.50);
	font-size: large;
}
 .widget a {
	background:"";
    }
</style>

     <script type="text/javascript">
    
      jQuery("#noteslink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&resId=${routedResourceId}&groupId=${groupOrderId}");
     
      // SL-19050 
      /*Function to Show and Hide thee Waiting/Refresh pop up*/
  	function fnWaitForResponseShow(){
  		jQuery("#overLay").show();
  		jQuery("#waitPopUp").show();
  	}
  	function fnWaitForResponseClose(){
  		jQuery("#overLay").hide();
  		jQuery("#waitPopUp").hide();
  	}

    // SL-19050 
	function markAsUnRead(noteId)
	{
		fnWaitForResponseShow();
		
	    	jQuery('#markAsRead').load('soDetailsNoteClick_markAsUnRead.action?noteId='+noteId, function() {
	    		fnWaitForResponseClose();
	    		jQuery('#new'+noteId).hide();
	    		jQuery('#'+noteId).show();
	
	    		jQuery('#noteCreatedBy'+noteId).css('font-weight','bold');
	    		jQuery('#noteSubject'+noteId).css('font-weight','bold');
	    		jQuery('#noteDate'+noteId).css('font-weight','bold');
	   
	    	});
		
	}
	
    // SL-19050 
	function markAsRead(noteId)
	{
		fnWaitForResponseShow();
		
	    	jQuery('#markAsRead').load('soDetailsNoteClick_markAsRead.action?noteId='+noteId, function() {
	    		fnWaitForResponseClose();
			jQuery('#'+noteId).hide();
	    		jQuery('#new'+noteId).show();
	    		
	    	
	    		jQuery('#noteCreatedBy'+noteId).css('font-weight','normal');
	    		jQuery('#noteSubject'+noteId).css('font-weight','normal');
	    		jQuery('#noteDate'+noteId).css('font-weight','normal');
	    
	    	});
		
	}
	

     function clickNoteInfo(path)
{
    jQuery("#notespane p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
    var ob=document.getElementById('noteImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('noteImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('noteImg').src=path+"/images/widgets/arrowRight.gif";
	}
}

     function disableButtonAndSubmitAddNote() {
    	 document.getElementById('submitNoteButton').style.display = "none";	
		 document.getElementById('submitNoteImage').style.display = "inline";
    	 SubmitAddNote('soDetailsAddGeneralNote.action');
     }
      </script>
 
     
 <div class="soNote">


      <div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         <p id="noteslink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
         
      
       
    </div>
	
	
<a id="#" href="#"></a>
<div class="contentPane">
	<div style="margin left:5px;">		
	   <!--  Display Validation error messages here -->
		<jsp:include page="/jsp/details/body/html_sections/modules/detailsValidationMessages.jsp" />
		<div style="color: blue">
	 		<p>${msg}</p>
	 		<%request.setAttribute("msg",""); %>
		</div>
	    <p>Stay in contact with your service 
	        <c:choose>
	            <c:when test="${roleId == 5}">
	                PROVIDER
	            </c:when>
	            <c:when test="${roleId == 3}">
	                PROVIDER
	            </c:when>
	            <c:otherwise>
	                BUYER
	            </c:otherwise>
	        </c:choose>
	        and keep an ongoing record of decisions about this order.  Use Service Order notes as the primary means of communication between buyer and provider to minimize confusion and document any decisions made about the service order for future reference.
	    </p>
	
	</div>
	    <c:if test="${groupNotes != null}">  
	    	<c:if test="${not empty groupNotes}">   
			    <div class="grayTableContainer" style="height: 200px; width: 663px; margin-bottom: 20px; margin left: 5px;">
				    <table class="globalTableLook" cellpadding="0" cellspacing="0" style="margin-bottom: 0; vertical-align: middle;">
				        <tr>
				            <th class="col1 first odd" style="width:30px">Created By</th>
				            <th class="col2 even textleft">Details</th>
				            <th class="col3 odd last">Date/Time</th>
				        </tr>
				        <c:forEach items="${groupNotes}"  var="note" varStatus="noteIndex">
				            <tr>
				                <td class="col1 first odd">&nbsp;
				                <c:choose>
				                	<c:when test="${note.createdByName == NEWCO_DISPLAY_SYSTEM}">
				                		${NEWCO_DISPLAY_SYSTEM}
				                	</c:when>
				                	<c:otherwise>
					                	${fn:substringAfter(note.createdByName, ",")}&nbsp;${fn:substring(fn:substringBefore(note.createdByName, ","),0,1)}.<br>
				                		(User Id# <c:out value="${note.entityId}"/>)
							        </c:otherwise>
							      </c:choose>
				                </td>
				                <td class="col2 even textleft">
				                    <strong>
				                    	<c:if test="${note.privateId == 1}">(Private)</c:if>
				                    	<c:if test="${note.privateId == 0}">(Public)</c:if>
				                    	<c:out value="${note.subject}"/>
				                    </strong>
				                    <br/>
				                	<div id="div_${noteIndex.count}">
				                        <c:out value="${note.note}"/>
				                	</div>
				                </td>
				                <td class="col3 odd last">
				                    <fmt:formatDate value="${note.createdDate}" dateStyle="short"/>
				                    <br/>
				                    <fmt:formatDate value="${note.createdDate}" type="time" timeStyle="short"/>
				                </td>
				            </tr>
					    </c:forEach>
				    </table>
			    </div>
		    </c:if>
	    </c:if>
	        
	    <c:if test="${notes != null}">   
	    	<c:if test="${not empty notes}">
	    	<c:choose>
	    	<c:when test="${noteFeatureInd}">
	    	  <!-- Code added for Jira SL-19050  --> 
			  <div id="mark" class="grayTableContainer" style="height: 200px; width: 663px; margin-bottom: 20px; margin left: 5px;">  
			  <table class="globalTableLook" cellpadding="0" cellspacing="0" style="margin-bottom: 0; vertical-align: middle;">
				        <tr>
				        	<th class="col1 first odd" style="width:30px">New</th>
				            <th class="col2 even">Created By</th>
				            <th class="col3 odd textleft">Details</th>
				            <th class="col4 last even">Date/Time</th>
				        </tr>
				        <c:forEach items="${notes}"  var="note" varStatus="noteIndex">
				            <tr>
				            <td class="col1 first odd">
				                 
				                 <c:choose>
				                 <c:when test="${note.providerInd == 1}">
				                 <c:choose>
				                    	<c:when test="${note.readInd == 0}">
				                   			<span id="${note.noteId}" title ="Mark as Read" onclick="markAsRead(${note.noteId})" class="icon-flag on"></span>
				            				<span id="new${note.noteId}" title ="Mark as Unread" onclick="markAsUnRead(${note.noteId})" class="icon-flag off" style="display:none"></span>
				                    	</c:when>
				                    	<c:otherwise>
				                    	<span id="new${note.noteId}" title ="Mark as Unread" onclick="markAsUnRead(${note.noteId})" class="icon-flag off"></span>
				                    	<span id="${note.noteId}" title ="Mark as Read" onclick="markAsRead(${note.noteId})" class="icon-flag on" style="display:none"></span>
			
				                    	</c:otherwise> 
				                    	</c:choose>
				                 </c:when>
				                 <c:otherwise>
	
				                 </c:otherwise>
				                 </c:choose>	
				              </td>
				                <td class="col2 even">&nbsp;
				                 <c:choose>
				                 <c:when test="${note.providerInd == 1}">
				                 
				                  <c:choose>
				                    	<c:when test="${note.readInd == 0}">
				                    	<c:choose>
				                	<c:when test="${note.createdByName == NEWCO_DISPLAY_SYSTEM}">
				                		
				                	<span id="noteCreatedBy${note.noteId}" style="font-weight: bold">	${NEWCO_DISPLAY_SYSTEM}</span>
				                		
				                	</c:when>
				                	<c:otherwise>
				                	
					               <span id="noteCreatedBy${note.noteId}" style="font-weight: bold"> 	${fn:substringAfter(note.createdByName, ",")}&nbsp;${fn:substring(fn:substringBefore(note.createdByName, ","),0,1)}.<br>
				                		(User Id# <c:out value="${note.entityId}"/>)
				                	</span>
							        </c:otherwise>
							        </c:choose>
				                    	</c:when>
				                    	<c:otherwise>
				                    	<c:choose>
				                	<c:when test="${note.createdByName == NEWCO_DISPLAY_SYSTEM}">
				                	<span id="noteCreatedBy${note.noteId}">	${NEWCO_DISPLAY_SYSTEM} </span>
				               
				                		
				                	</c:when>
				                	<c:otherwise>
					                <span id="noteCreatedBy${note.noteId}">	${fn:substringAfter(note.createdByName, ",")}&nbsp;${fn:substring(fn:substringBefore(note.createdByName, ","),0,1)}.<br>
				                		(User Id# <c:out value="${note.entityId}"/>)
				                		</span>
							        </c:otherwise>
							        </c:choose>
				                    	</c:otherwise> 
				                    	
							      </c:choose>
				                 
				                 </c:when>
				                 
				                 <c:otherwise>
				                 
				                 <c:choose>
				                	<c:when test="${note.createdByName == NEWCO_DISPLAY_SYSTEM}">
				                		${NEWCO_DISPLAY_SYSTEM}
				                		
				                	</c:when>
				                	<c:otherwise>
					                	${fn:substringAfter(note.createdByName, ",")}&nbsp;${fn:substring(fn:substringBefore(note.createdByName, ","),0,1)}.<br>
				                		(User Id# <c:out value="${note.entityId}"/>)
							        </c:otherwise>
							        </c:choose>
				                 
				                 </c:otherwise>
				                 
				                 </c:choose>
				               
				                </td>
				                <td class="col3 odd textleft">
				                
				                 <c:choose>
				                 <c:when test="${note.providerInd == 1}">
				                 <c:choose>
				                <c:when test="${note.readInd == 0}">
				                    <span  id="noteSubject${note.noteId}" style="font-weight: bold">
				                    	<c:if test="${note.privateId == 1}">(Private)</c:if>
				                    	<c:if test="${note.privateId == 0}">(Public)</c:if>
				                    	${note.subject}
				                   
				                    <br/>
				                	<div id="div_${noteIndex.count}">${note.note}</div></span>
				                </c:when>
				                <c:otherwise>
				                  <span  id="noteSubject${note.noteId}">
				                    	<c:if test="${note.privateId == 1}">(Private)</c:if>
				                    	<c:if test="${note.privateId == 0}">(Public)</c:if>
				                    	${note.subject} 
				                    
				                    <br/>
				                	<div id="div_${noteIndex.count}">${note.note}</div>
				                
				                	</span>
				                </c:otherwise>
				                </c:choose>
				                 </c:when>
				                 <c:otherwise>
				                 
				                 <c:if test="${note.privateId == 1}">(Private)</c:if>
				                    	<c:if test="${note.privateId == 0}">(Public)</c:if>
				                    	${note.subject} 
				                    
				                    <br/>
				                	<div id="div_${noteIndex.count}">${note.note}</div>
				                 
				                 </c:otherwise>
				                 </c:choose>
				                
				                
				               
				                </td>
				                <td class="col4 last even">
				                
				                 <c:choose>
				                 <c:when test="${note.providerInd == 1}">
				                  <c:choose>
				                  <c:when test="${note.readInd == 0}">
				                  <span id="noteDate${note.noteId}"  style="font-weight: bold">
				                  <fmt:formatDate value="${note.createdDate}" dateStyle="short"/>
				                    <br/>
				                    <fmt:formatDate value="${note.createdDate}" type="time" timeStyle="short"/>
				                    </span>
				                    </c:when>
				                    <c:otherwise>
				                     <span id="noteDate${note.noteId}">
				                    <fmt:formatDate value="${note.createdDate}" dateStyle="short"/>
				                    <br/>
				                    <fmt:formatDate value="${note.createdDate}" type="time" timeStyle="short"/>
				                    </span>
				                    </c:otherwise>
				                    </c:choose>
				                 </c:when>
				                 <c:otherwise>
				                 	  
				                  <fmt:formatDate value="${note.createdDate}" dateStyle="short"/>
				                    <br/>
				                    <fmt:formatDate value="${note.createdDate}" type="time" timeStyle="short"/>
				                 
				                 </c:otherwise>
				                 </c:choose>
				                 
				                
				               
				                </td>
				            </tr>
				        </c:forEach>
					</table>
			</div>
			<div id="markAsRead" style="display:none"></div>				
	    	</c:when>
	    	<c:otherwise>
			    <div class="grayTableContainer" style="height: 200px; width: 663px; margin-bottom: 20px; margin left: 5px;">  
				    <table class="globalTableLook" cellpadding="0" cellspacing="0" style="margin-bottom: 0; vertical-align: middle;">
				        <tr>
				            <th class="col1 first odd">Created By</th>
				            <th class="col2 even textleft">Details</th>
				            <th class="col3 last odd">Date/Time</th>
				        </tr>
				        <c:forEach items="${notes}"  var="note" varStatus="noteIndex">
				            <tr>
				                <td class="col1 first odd">&nbsp;
				                <c:choose>
				                	<c:when test="${note.createdByName == NEWCO_DISPLAY_SYSTEM}">
				                		${NEWCO_DISPLAY_SYSTEM}
				                	</c:when>
				                	<c:otherwise>
					                	${fn:substringAfter(note.createdByName, ",")}&nbsp;${fn:substring(fn:substringBefore(note.createdByName, ","),0,1)}.<br>
				                		(User Id# <c:out value="${note.entityId}"/>)
							        </c:otherwise>
							      </c:choose>
				                </td>
				                <td class="col2 even textleft">
				                    <strong>
				                    	<c:if test="${note.privateId == 1}">(Private)</c:if>
				                    	<c:if test="${note.privateId == 0}">(Public)</c:if>
				                    	${note.subject} 
				                    </strong>
				                    <br/>
				                	<div id="div_${noteIndex.count}">${note.note}</div>
				                </td>
				                <td class="col3 last odd">
				                    <fmt:formatDate value="${note.createdDate}" dateStyle="short"/>
				                    <br/>
				                    <fmt:formatDate value="${note.createdDate}" type="time" timeStyle="short"/>
				                </td>
				            </tr>
				        </c:forEach>
					</table>
				</div>
				</c:otherwise>
				</c:choose>
			</c:if>
	    </c:if>
	    
	    <c:if test="$(deletedNotes != null}">
	    	<c:if test="${not empty deletedNotes}">
		    	<br/>
		    	<br/>
			    <div class="grayTableContainer" style="height: 200px; width: 663px; margin-bottom: 20px; margin left: 5px;">
			    	<table class="globalTableLook" cellpadding="0" cellspacing="0" style="margin-bottom: 0; vertical-align: middle;">
				        <tr>
				            <th class="col1 first odd">Created By</th>
				            <th class="col2 even textleft">Details</th>
				            <th class="col3 odd last">Deleted Date/Time</th>
				        </tr>
				        <c:forEach items="${deletedNotes}"  var="deletedNote" varStatus="noteIndex">
				            <tr>
				                <td class="col1 first odd"><c:out value="${deletedNote.createdByName}"/></td>
				                <td class="col2 even textleft">
				                    <strong><c:out value="${deletedNote.subject}"/></strong>
				                    <br/>
				                	<div id="div_${noteIndex.count}">
				                        <c:out value="${deletedNote.note}"/>
				                	</div>
				                </td>
				                <td class="col3 odd last">
				                    <fmt:formatDate value="${deletedNote.deletedDate}" dateStyle="short"/>
				                    <br/>
				                    <fmt:formatDate value="${deletedNote.deletedDate}" type="time" timeStyle="short"/>
				                </td>
				            </tr>
				        </c:forEach>
				    </table>
			    </div>
			</c:if>   
	    </c:if>
    
    <s:form action="/MarketFrontend/soDetailsAddGeneralNote.action" method="post" id="noteForm">
    <div id="notespane" class="menugroup_list" style="width:627px; ">
    	<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}" />
    	<input type="hidden" id="groupId" name="groupId" value="${groupOrderId}" />
    	<input type="hidden" id="resId" name="resId" value="${routedResourceId}" />
  	<p class="menugroup_head" onClick="clickNoteInfo('${staticContextPath}');">&nbsp;<img id="noteImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Add Notes</p>
    <div class="menugroup_body">      		
          	<p><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.notes.message"/></p>
            <p><strong>Subject</strong></p>
            <input type="text" name="subject" id="subject" style="width: 600px;" cssClass="shadowBox grayText" onfocus="clearTextboxDefaultValue(this,'[Subject]')" />
            
            <p><strong>Message</strong></p>
            <textarea name="message" id="message" style="width: 590px;" cssClass="shadowBox grayText" onfocus="clearTextboxDefaultValue(this,'[Message]')" 
              	onkeydown="countAreaChars(this.form.message, this.form.message_leftChars, ${SO_NOTE_MESSAGE_MAX_LENGTH}, event);"
				onkeyup="countAreaChars(this.form.message, this.form.message_leftChars, ${SO_NOTE_MESSAGE_MAX_LENGTH}, event);" /><br/>
				<div class="clearfix">
				<input type="text" name="message_leftChars" maxlength="3" value="${SO_NOTE_MESSAGE_MAX_LENGTH}"
				style="border-top-width: 0 px; border-right-width: 0 px; border-bottom-width: 0px; border-left-width: 0 px; width: 22px;">
					<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>&nbsp;&nbsp;
					
					<div class="listRadioButton" style="margin-top:-18px; margin-left:165px;">
						<input type="radio" id="radioId" name="radioSelection"  value="0">Send email alert</input><br/>
						<input type="radio" id="radioId" name="radioSelection" value="2" checked> No email alert</input><br/>
						<input type="radio" id="radioId" name="radioSelection"  value="1" >Private note (visible to your company and ServiceLive only)</input>
						<br/>
						<br/>
							<input id="submitNoteButton" type="image" src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" 
													style="background-image:url(${staticContextPath}/images/btn/submit.gif); cursor:pointer; vertical-align:middle;"  
													class="btnBevel" onclick="disableButtonAndSubmitAddNote();"/>
							
							<img id ="submitNoteImage"src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" 
													style="display: none; vertical-align:middle; 
													background-image:url(${staticContextPath}/images/btn/submit.gif);"
													/>
							&nbsp;&nbsp;<a href="javascript:void(0);" onClick="ClearFieldsNotesTab(); document.getElementById('noteForm').reset();" 
			        	style="color:red; padding-bottom:10px">Cancel</a><br/>
					</div>	
			    </div>
        </div>
        </div>
    </s:form>
</div>
 <!-- SL-19050 -->
<div id="overLay" class="overLay"
		style="display: none; z-index: 1000; width: 100%; height: 100%; position: fixed; opacity: 0.2; filter: alpha(opacity =   20); 
		top: 0px; left: 0px; background-color: #E8E8E8; cursor: wait;"></div>
	<div id="waitPopUp" class="waitLayer">
		<div style="padding-left: 0px; padding-top: 5px; color: #222222;">
			<div class="refreshMsg">
				<span>Loading...</span>
			</div>
		</div>
	</div>
<script type="text/javascript" src="${staticContextPath}/javascript/animatedcollapse.js">	</script>
<script language="JavaScript" type="text/javascript">
loadDivs(${fn:length(dtoList)});
</script>
