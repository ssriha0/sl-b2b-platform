<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<style>
	.supportBody {
	    border: 0.03cm solid #ccc;
	    margin-left: 10px;
	    margin-right: 20px;
	    Padding-right: 0px;
	    padding-bottom: 10px;
	}
</style>
     <script type="text/javascript">
       
       jQuery("#supportlink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&resId=${routedResourceId}&groupId=${groupOrderId}");
    function clickSupportInfo(path)
{
    jQuery("#supportpane p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("div.menugroup_body").slideToggle(300);
    var ob=document.getElementById('supImg').src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('supImg').src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('supImg').src=path+"/images/widgets/arrowRight.gif";
	}
}
    function disableButtonAndSubmitAddNoteSupport() {
   	 	document.getElementById('submitNoteSupportButton').style.display = "none";	
		document.getElementById('submitNoteSupportImage').style.display = "inline";
   		SubmitAddNoteSupport('soDetailsSupportNote.action');
    }
    
    function clearFieldsSupport(){
		document.getElementById("messageSupport").value = '';
		document.getElementById("subjectSupport").value = '';
		document.getElementById("messageSupport").className="shadowBox grayText";
		document.getElementById("subjectSupport").className ="shadowBox grayText";
		document.getElementById("subjectLabelMsgSupport").style.display = "none";
		document.getElementById("messageLabelMsgSupport").style.display = "none";
		//Clear Error Messages 
		document.getElementById('validationResponseMessage').style.display = 'block';
		document.getElementById('validationResponseMessage').style.visibility = 'hidden';
		document.getElementById('validationResponseMessage').innerHTML = "";
	}
      </script>

       <div class="soNote">
<div id="rightsidemodules" class="colRight255 clearfix" >
         <p id="supportlink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
    </div>

<a id="#" href="#"></a>
<div class="contentPane">
	<!--  Display Validation error messages here -->
	<jsp:include page="/jsp/details/body/html_sections/modules/detailsValidationMessages.jsp" />
	
	 <div style="color: blue">
 		<p>${msg}</p>
 		<%request.setAttribute("msg",""); %>
	</div>
	
	<p class="paddingBtm">
		Do you have comments or technical questions about ServiceLive? Submit them here and our ServiceLive support staff will respond as quickly as possible. Please be as detailed as possible in your message.
	</p>
	<div class=supportBody>
	
	<div class="grayTableContainer" style="height: auto; overflow: visible;">
	    <table class="globalTableLook" cellpadding="0" cellspacing="0" style="table-layout:fixed; width: 663px;">
        <tr>
            <th class="col1 odd first">
            		Created By
            </th>
            <th class="col2 even textleft">
            		Details
            </th>
            <th class="col3 odd last">
                	Date/Time
            </th>
        </tr>
	        <c:forEach items="${notes}"  var="note" varStatus="noteIndex">
	            <tr>
	                <td class="col1 odd first" style="word-wrap:break-word">&nbsp;
		                ${fn:substringAfter(note.createdByName, ",")}&nbsp;${fn:substringBefore(note.createdByName, ",")}&nbsp;
		                (User Id# <c:out value="${note.entityId}"/>)
	                </td>

	                <td class="col2 even textleft" style="word-wrap:break-word">
	                    <strong><c:out value="${note.subject}"/></strong>
	                    <br/>
	                	<div id="div_${noteIndex.count}">
	                        <c:out value="${note.note}"/>
	                	</div>
	                </td>
	                <td class="col3 odd last" style="word-wrap:break-word">
	                    <fmt:formatDate value="${note.createdDate}" dateStyle="short"/>
	                    <br/>
	                    <fmt:formatDate value="${note.createdDate}" type="time" timeStyle="short"/>
	                </td>
	            </tr>
	        </c:forEach>
	    </table>
    </div>


<s:form action="/MarketFrontend/soDetailsSupportNote.action" method="POST" id="supportNoteForm">
	<!--  <div dojoType="dijit.TitlePane" title="General Information" class="contentWellPane" style="width: 665px;"> -->
	 <div id="supportpane" class="menugroup_list" style="width:625px;height:500px">
	 	<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}" />
	 	<input type="hidden" id="groupId" name="groupId" value="${groupOrderId}" />
    	<input type="hidden" id="resId" name="resId" value="${routedResourceId}" />
  	<p class="menugroup_head" onClick="clickSupportInfo('${staticContextPath}');">&nbsp;<img id="supImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;General Information</p>
    <div class="menugroup_body">
		<label style="display:none; color:red" id="subjectLabelMsgSupport">Enter Note Subject.</label><br>		 
		<label style="display:none; color:red" id="messageLabelMsgSupport">Enter Note Message.</label><br>		 
		
		<p>
			Subject
			<br />
	    	
	    	 
		<input type="text" name="subjectSupport" id="subjectSupport" style="width: 610px;" cssClass="shadowBox grayText" onfocus="clearTextboxDefaultValue(this,'[Subject]')" 
            	onkeydown="countAreaChars(this.form.subjectSupport, this.form.subjectSupport_leftChars, 30, event);"
				onkeyup="countAreaChars(this.form.subjectSupport, this.form.subjectSupport_leftChars, 30, event);" /><br />
		<input type="text" name="subjectSupport_leftChars" readonly size="4" maxlength="4" value="30"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
		
		</p>
		<p>
			Message (Please be as detailed as possible)<br />
		
		<textarea style="width: 600px;" name="messageSupport" id="messageSupport" cssClass="shadowBox grayText" onfocus="clearTextboxDefaultValue(this,'[Message]')" 
              	onkeydown="countAreaChars(this.form.messageSupport, this.form.messageSupport_leftChars, 750, event);"
				onkeyup="countAreaChars(this.form.messageSupport, this.form.messageSupport_leftChars, 750, event);" ></textarea><br />
		<input type="text" name="messageSupport_leftChars" readonly size="4" maxlength="4" value="750"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
		</p>
		<br><br>
	
	<div class="clearfix">
		<p>
			<input id="submitNoteSupportButton" type="button" src="${staticContextPath}/images/common/spacer.gif"
				style="background-image:url(${staticContextPath}/images/btn/submit.gif); width:70px; height:20px; vertical-align:middle;"  
				class="btnBevel" onclick="disableButtonAndSubmitAddNoteSupport();"/>
			
			<img id ="submitNoteSupportImage"src="${staticContextPath}/images/common/spacer.gif" width="70" height="20" 
				style="display: none; vertical-align:middle; 
				background-image:url(${staticContextPath}/images/btn/submit.gif);" />
			
			<input type="button" 
				  onclick="clearFieldsSupport()"
				  class="btn20Bevel"
				  style="background-image: url(${staticContextPath}/images/btn/cancel.gif);
				            width:55px; height:20px; vertical-align:middle;"
				  src="${staticContextPath}/images/common/spacer.gif"
			/>
		</p>
	</div>
		
	</div>
	</div>
   </s:form>
</div>
</div>
</div>
