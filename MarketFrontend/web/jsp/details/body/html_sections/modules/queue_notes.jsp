<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<c:set var="SO_NOTE_MESSAGE_MAX_LENGTH" value="<%= OrderConstants.SO_NOTE_MESSAGE_MAX_LENGTH%>" />
<c:set var="SO_NOTE_SUBJECT_MAX_LENGTH" value="<%= OrderConstants.SO_NOTE_SUBJECT_MAX_LENGTH%>" />
<c:set var="DISPLAY_MSG_MAX_LENGTH" value="<%= OrderConstants.DISPLAY_MSG_MAX_LENGTH%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="tabToShow" value="<%=session.getAttribute("tabToShow")%>" />
<c:set var="disableSubmitNotes" value="false" />
<c:set var="defaultMsgLen" value="250" />
<%int randval = ((int)(Math.random() * 10000000));%>
<c:set var="randomNum" value="<%=""+randval%>"/>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/workflow-monitor.css" media="screen, projection">

<div>
	    
<%-- Display the widgets only if it came from work flow monitor Check is made in quick_links.jsp --%>
	    
		<s:form id="backToWFM%{#attr['randomNum']}" name="backToWFM" action="/MarketFrontend/pbController_execute.action" method="POST">
		<div><input type="hidden" name="hidden">
		</div>
	</s:form>	
	<%-- begin loop of queue --%>
 	<c:forEach items="${wfmBuyerQueueDTOList}" var="wfmBuyerQueueDTOObj" varStatus="status">
 	<%-- If we have note from past display it --%>
		 	   <c:if test="${wfmBuyerQueueDTOObj.note != null}">
			 	   	<c:choose> 
						<c:when test="${fn:length(wfmBuyerQueueDTOObj.note) > DISPLAY_MSG_MAX_LENGTH}">
							${fn:substring(wfmBuyerQueueDTOObj.note,0,DISPLAY_MSG_MAX_LENGTH)} <strong>...</strong>
						</c:when> 
						<c:otherwise> 
					 			${wfmBuyerQueueDTOObj.note}
						</c:otherwise> 
						</c:choose> 
				
		 	   </c:if>
		 	   <c:if test="${wfmBuyerQueueDTOObj.completedInd != null}">
		 	   			<c:choose> 
							<c:when test="${wfmBuyerQueueDTOObj.completedInd == 1}">
								<c:set var="disableSubmitNotes" value="true"/>
							</c:when> 
							<c:otherwise> 
						 			<c:set var="disableSubmitNotes" value="false"/>
							</c:otherwise> 
						</c:choose> 
				</c:if>
		 	   
		 	   
 	   
			<form id="form${wfmBuyerQueueDTOObj.queueId}" name="form${wfmBuyerQueueDTOObj.queueId}" class="queueForms">
				<div style="margin-bottom: 10px; font-weight: bold;">${wfmBuyerQueueDTOObj.queueName}</div>
				<div style="margin-bottom: 10px;"> 
				<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.queueAction"/><span class="req">*</span></label>
					<select name="queueAction" style="width: 180px;"
					id="wfmBuyerQueueDTO${wfmBuyerQueueDTOObj.queueId}" 
					onchange="populateQueueNotes(this.form, this.id,'noteText')">
										<option value="">Select One</option>
						<c:forEach var="wfmTask" items="${wfmBuyerQueueDTOObj.wfmSOTasks}">
						     <c:if test="${wfmTask.queueId==wfmBuyerQueueDTOObj.queueId}">
						            <option value="${wfmTask.taskDesc}|~|${wfmTask.soTaskId}|~|${wfmTask.taskState}|~|${wfmTask.taskCode}|~|${wfmTask.requeueHours}|~|${wfmTask.requeueMins}|~|${wfmBuyerQueueDTOObj.queueId}">
											${wfmTask.taskCode}
									</option>
							</c:if>		
						</c:forEach>
		
					</select>
					</div>
				<div class="clearfix" style="margin-bottom: 10px;">
				<div class="left" style="margin-right: 10px"><input type="radio" id="pub" name="pripub" style="float: left; margin-right: 5px"> <label>Public</label></div>
				<div class="left" style="margin-right: 25px"><input type="radio" id="pri" name="pripub" style="float: left; margin-right: 5px" checked> <label>Private</label></div>
				</div>	
			
			<div style="margin-bottom: 10px;">
				<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.queueNote"/> <span class="req">*</span></label><br />
				 <textarea name="noteText" style="width: 200px" id="noteText" onkeydown="countAreaChars(this.form.noteText, this.form.message_leftChars, ${SO_NOTE_MESSAGE_MAX_LENGTH}, event);"
				onkeyup="countAreaChars(this.form.message, this.form.message_leftChars, ${SO_NOTE_MESSAGE_MAX_LENGTH}, event);" ></textarea>
				
				
						
			</div>
			<div>
			<input type="text" style="font-size: 8px;"	name="message_leftChars" readonly="readonly" size="4" maxlength="4" value="${SO_NOTE_MESSAGE_MAX_LENGTH}"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
			</div>
			
			<div class="clearfix" style="margin-bottom: 10px;">
				<label>
				<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.requeueDateTime"/><span class="req">*</span>
				</label><br />
				
					<div class="left" style="margin-right: 10px;">
						<tags:fieldError id="Re-queue Date/Time" oldClass="paddingBtm">
							<input type="text" onFocus="showCalendarControl(this, 'requeueDate');"
										class="shadowBox" style="width: 80px; position: relative"
										id="requeueDate"
										name="requeueDate"
										value=""
										constraints="{min: '${todaysDate}'}"
										required="true"
										lang="en-us"									
										/>
									&nbsp;&nbsp;&nbsp;
									<s:select cssStyle="width: 90px;" id="requeueTime" emptyOption="true"  
										name="requeueTime"
										value=""
										list="#application['time_intervals']" listKey="descr" 
										listValue="descr" multiple="false" size="1" theme="simple" />
						</tags:fieldError>
					</div>
			</div>
			
			 <%--  First queue in the list is always the primary queue.  --%>
				<c:choose> 
				<c:when test="${wfmBuyerQueueDTOObj.queueId == wfmBuyerQueueDTOObj.claimedFromQueueId}"> 
				
					<input type="hidden" id="primaryQueue" name="primaryQueue" value="true">
				</c:when> 
				<c:otherwise> 
			 		<input type="hidden" id="primaryQueue" name="primaryQueue" value="false">
				</c:otherwise> 
				</c:choose> 
			
			<input type="hidden" id="randomNumUnclaim" name="randomNumUnclaim" value="${randomNum}">
			<input type="hidden" id="tabToShow" name="tabToShow" value="<%=session.getAttribute("tabToShow")%>">
			<input type="hidden" id="soTaskId" name="soTaskId" value="">
			<input type="hidden" id="taskState" name="taskState" value="">
			<input type="hidden" id="taskCode" name="taskCode" value="">
			<input type="hidden" id="queueSeq" name="queueSeq" value="${wfmBuyerQueueDTOObj.queueSeq}">
			<input type="hidden" id="queueID" name="queueID" value="${wfmBuyerQueueDTOObj.queueId}">
			<input type="hidden" id="buyerId" name="buyerId" value="${wfmBuyerQueueDTOObj.buyerId}">
			<input type="hidden" id="soQueueId" name="soQueueId" value="${wfmBuyerQueueDTOObj.soQueueId}">
			<input type="hidden" id="uniqueNumber" name="uniqueNumber" value="${wfmBuyerQueueDTOObj.uniqueNumber}">
			<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}">
			<input type="hidden" id="groupId" name="groupId" value="${groupOrderId}">
			<%--  Grey out the button and disable it if the queue has reached the end state--%>
			
			<div style="margin-bottom: 0px; margin-top: 0px; font-size: 10px;">
			<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.queuedDate"/><br />
		
						 			<input name="queuedDate${wfmBuyerQueueDTOObj.queueId}${wfmBuyerQueueDTOObj.uniqueNumber}" id="queuedDate${wfmBuyerQueueDTOObj.queueId}${wfmBuyerQueueDTOObj.uniqueNumber}" 
						 			style="margin-bottom: 10px; width: 105px;  font-size: 10px;" readonly="readonly" value="<fmt:formatDate value="${wfmBuyerQueueDTOObj.queuedDate}" pattern="M/d/yyyy h:mm a" />"
						 			 type="text" class="short text date pfdate2">
			
			</div>			
			<c:choose> 
							<c:when test="${disableSubmitNotes}">
									<input style="margin-bottom: 0px;margin-top: 0px;" type="image" class="btnSubmitNoteDisabled right btntopmargin" src="${staticContextPath}/images/btn/spacer.gif" 
								name="Submit Notes"  onclick="return false;">
									
							</c:when> 
							<c:otherwise> 
						 			<input style="margin-bottom: 0px;margin-top: 0px;"  type="image" class="btnSubmitNote right btntopmargin" src="${staticContextPath}/images/btn/spacer.gif" 
								name="Submit Notes"  onclick="submitFormUsingAjax(this.form, '/MarketFrontend/soDetailsQueueNote.action?ss=${securityToken}',${randomNum});return false;">
							</c:otherwise> 
			</c:choose> 
				<div id="messageSpan${wfmBuyerQueueDTOObj.queueId}${wfmBuyerQueueDTOObj.uniqueNumber}" style="color: red; font-size: 10px;"></div>
				<div class="clearfix" style="border-bottom: 3px solid #CCC; margin-top: 20px;padding-bottom: 10px;"></div>
			</form>
	</c:forEach>
	
	
	<%-- end loop of queue --%>

	
	
	<%-- The new Follow Up (Call Back) queue starts here --%>

				<form id="form${wfmBuyerCBQueueDTO.queueId}" name="form${wfmBuyerCBQueueDTO.queueId}" class="queueForms">
				<div style="margin-bottom: 10px; font-weight: bold;">Create New ${wfmBuyerCBQueueDTO.queueName} Action</div>
				<div style="margin-bottom: 10px;"> 
				<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.queueAction"/><span class="req">*</span></label>
					<select name="queueAction" style="width: 180px;"
					id="wfmBuyerQueueDTO${wfmBuyerCBQueueDTO.queueId}" 
					onchange="populateQueueNotes(this.form, this.id,'noteText')">
										<option value="">Select One</option>
						<c:forEach var="wfmTask" items="${wfmBuyerCBQueueDTO.wfmSOTasks}">
										<option value="${wfmTask.taskDesc}|~|${wfmTask.soTaskId}|~|${wfmTask.taskState}|~|${wfmTask.taskCode}|~|${wfmTask.requeueHours}|~|${wfmTask.requeueMins}|~|${wfmBuyerCBQueueDTO.queueId}">
											${wfmTask.taskCode}
										</option>
						</c:forEach>
		
					</select>
					</div>
				<div class="clearfix" style="margin-bottom: 10px;">
				<div class="left" style="margin-right: 10px"><input type="radio" id="pub" name="pripub" style="float: left; margin-right: 5px"> <label>Public</label></div>
				<div class="left" style="margin-right: 25px"><input type="radio" id="pri" name="pripub" style="float: left; margin-right: 5px" checked> <label>Private</label></div>
				</div>	
			
			<div style="margin-bottom: 10px;">
				<label><fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.queueNote"/> <span class="req">*</span></label><br />
				 <textarea name="noteText" style="width: 200px" id="noteText" onkeydown="countAreaChars(this.form.noteText, this.form.noteText_leftChars, ${SO_NOTE_MESSAGE_MAX_LENGTH}, event);"
				onkeyup="countAreaChars(this.form.noteText, this.form.noteText_leftChars, ${SO_NOTE_MESSAGE_MAX_LENGTH}, event);" ></textarea>
			</div>
			<div>
			<input type="text" style="font-size: 8px;"	name="noteText_leftChars" readonly="readonly" size="4" maxlength="4" value="${SO_NOTE_MESSAGE_MAX_LENGTH}"> <fmt:message bundle="${serviceliveCopyBundle}" key="wizard.scopeofwork.chars.left"/>
			</div>
			
			<div class="clearfix" style="margin-bottom: 10px;">
				<label>
				<fmt:message bundle="${serviceliveCopyBundle}" key="wizard.queue_notes.requeueDateTime"/><span class="req">*</span>
				</label><br />
				
					<div class="left" style="margin-right: 10px;">
						<tags:fieldError id="Re-queue Date/Time" oldClass="paddingBtm">
							<input type="text" onFocus="showCalendarControl(this, 'requeueDate');"
										class="shadowBox" style="width: 80px; position: relative"
										id="requeueDate"
										name="requeueDate"
										value=""
										constraints="{min: '${todaysDate}'}"
										required="true"
										lang="en-us"									
										/>
									&nbsp;&nbsp;&nbsp;
									<s:select cssStyle="width: 90px;" id="requeueTime" emptyOption="true"
										name="requeueTime"
										value=""
										list="#application['time_intervals']" listKey="descr"
										listValue="descr" multiple="false" size="1" theme="simple" />
						</tags:fieldError>
					</div>
			</div>
			
			 
			<input type="hidden" id="tabToShow" name="tabToShow" value="<%=session.getAttribute("tabToShow")%>">
			<input type="hidden" id="soTaskId" name="soTaskId" value="">
			<input type="hidden" id="taskState" name="taskState" value="">
			<input type="hidden" id="taskCode" name="taskCode" value="">
			<c:if test="${wfmBuyerCBQueueDTO.buyerId != 4000 && wfmBuyerCBQueueDTO.buyerId != 512353}">
			<input type="hidden" id="queueID" name="queueID" value="2">
			</c:if>
			<c:if test="${wfmBuyerCBQueueDTO.buyerId == 4000 || wfmBuyerCBQueueDTO.buyerId == 512353}">
			<input type="hidden" id="queueID" name="queueID" value="72">
			</c:if>
			<input type="hidden" id="newCallBackQueue" name="newCallBackQueue" value="true">
			
			<input type="hidden" id="buyerId" name="buyerId" value="${wfmBuyerCBQueueDTO.buyerId}">
			<input type="hidden" id="uniqueNumber" name="uniqueNumber" value="${wfmBuyerCBQueueDTO.uniqueNumber}">
			<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}">
			<input type="hidden" id="groupId" name="groupId" value="${groupOrderId}">
			<input style="margin-top: 0px;" type="image" class="btnSubmitNote right btntopmargin" src="${staticContextPath}/images/btn/spacer.gif" name="Submit Notes"  onclick="submitFormUsingAjax(this.form, '/MarketFrontend/soDetailsQueueNote.action?ss=${securityToken}',${randomNum});return false;">
			</form>
			<div style="margin-bottom: 10px; margin-top: 0px; font-size: 10px;">
			</div>
			<span id="messageSpan${wfmBuyerCBQueueDTO.queueId}${wfmBuyerCBQueueDTO.uniqueNumber}" style="color: red; font-size: 10px;"></span>
			<div class="clearfix" style="border-bottom: 3px solid #CCC; margin-top: 10px;padding-bottom: 10px;"></div>
	
	<%-- The new Follow Up (Call Back) queue ends here --%>
	
	
	<%-- Unclaim button starts here --%>
	
		<div id="Un-claim1" style='top:200'>
		<button type="button" name="Un-claim" class="btnUnClaim right btntopmargin" value="Un-claim" 
			onclick="findPosY(this);submitUnClaim('/MarketFrontend/submitUnClaim.action?soId=${SERVICE_ORDER_ID}', 'verifyIfPendingQueuesNeedAction', ${randomNum});return false;"
			id="Un-claim${randomNum}"><img id="Un-claim-image" alt="disabled" src="${staticContextPath}/images/btn/spacer.gif" /></button>
			
		<input type="hidden" name="primary_queue_action" id="primary_queue_action">
		<span id="messageSpan" style="color: red; font-size: 10px;"></span>
	</div>
	
	<%-- Unclaim button ends here --%>
	
	
	<%-- modalContent --%>
    <div  class="jqmWindowUnclaim modalDefineTerms" id="modalDefineQueueNeedActionMessage${randomNum}">
      <div class="modalHomepage"> <a href="#" class="jqmClose" onclick="closeModal('modalDefineQueueNeedActionMessage${randomNum}');">Close</a> </div>
      <div class="modalContent">
        <h2>Are you sure you want to un-claim this service order?</h2>
        <p>There are still additional items that require your attention at this time.</p>
        <img src="${staticContextPath}/images/btn/spacer.gif" alt="Close"  class="jqmClose btnReturnToSO right" onclick="closeModal('modalDefineQueueNeedActionMessage${randomNum}');"/>
        <img src="${staticContextPath}/images/btn/spacer.gif" alt="Continue"  class="jqmClose btnUnClaim right" onClick="submitUnClaim('/MarketFrontend/submitUnClaim.action?soId=${SERVICE_ORDER_ID}', '', ${randomNum});"/>
        <br /><br />
      </div>
     </div>
     <%-- modalContent --%>
    <div  class="jqmWindowUnclaim modalDefineTerms" id="modalDefinePrimaryQueueNeedActionMessage${randomNum}">
      <div class="modalHomepage"> <a href="#" class="jqmClose" onclick="closeModal('modalDefinePrimaryQueueNeedActionMessage${randomNum}');">Close</a> </div>
      <div class="modalContent">
        <h2>Primary Queue requires an action</h2>
        <p>I'm sorry, but you are not able to un-claim this service order at this time. The Primary Queue requires an action.</p>
        <img src="${staticContextPath}/images/simple/button-close2.png" alt="Close" align="right" class="jqmClose" onclick="closeModal('modalDefinePrimaryQueueNeedActionMessage${randomNum}');"/>
  
        <br /><br />
      </div>
     </div>
   
</div>
	<%-- end loop of queue --%>
	
<script type="text/javascript">
var floatingMenuId = 'modalDefineQueueNeedActionMessage<%=randval%>';
var floatingMenu =
{
    targetX: 390,
    targetY: 100,

    hasInner: typeof(window.innerWidth) == 'number',
    hasElement: typeof(document.documentElement) == 'object'
        && typeof(document.documentElement.clientWidth) == 'number',

    menu:
        document.getElementById
        ? document.getElementById(floatingMenuId)
        : document.all
          ? document.all[floatingMenuId]
          : document.layers[floatingMenuId]
};

floatingMenu.move = function ()
{
    floatingMenu.menu.style.left = floatingMenu.nextX + 'px';
    floatingMenu.menu.style.top = floatingMenu.nextY + 'px';
}

floatingMenu.computeShifts = function ()
{
    var de = document.documentElement;

    floatingMenu.shiftX =  
        floatingMenu.hasInner  
        ? pageXOffset  
        : floatingMenu.hasElement  
          ? de.scrollLeft  
          : document.body.scrollLeft;  
    if (floatingMenu.targetX < 0)
    {
        floatingMenu.shiftX +=
            floatingMenu.hasElement
            ? de.clientWidth
            : document.body.clientWidth;
    }

    floatingMenu.shiftY = 
        floatingMenu.hasInner
        ? pageYOffset
        : floatingMenu.hasElement
          ? de.scrollTop
          : document.body.scrollTop;
    if (floatingMenu.targetY < 0)
    {
        if (floatingMenu.hasElement && floatingMenu.hasInner)
        {
            // Handle Opera 8 problems
            floatingMenu.shiftY +=
                de.clientHeight > window.innerHeight
                ? window.innerHeight
                : de.clientHeight
        }
        else
        {
            floatingMenu.shiftY +=
                floatingMenu.hasElement
                ? de.clientHeight
                : document.body.clientHeight;
        }
    }
}

floatingMenu.calculateCornerX = function()
{
    if (floatingMenu.targetX != 'center')
        return floatingMenu.shiftX + floatingMenu.targetX;

    var width = parseInt(floatingMenu.menu.offsetWidth);

    var cornerX =
        floatingMenu.hasElement
        ? (floatingMenu.hasInner
           ? pageXOffset
           : document.documentElement.scrollLeft) + 
          (document.documentElement.clientWidth - width)/2
        : document.body.scrollLeft + 
          (document.body.clientWidth - width)/2;
    return cornerX;
};

floatingMenu.calculateCornerY = function()
{
    if (floatingMenu.targetY != 'center')
        return floatingMenu.shiftY + floatingMenu.targetY;

    var height = parseInt(floatingMenu.menu.offsetHeight);

    // Handle Opera 8 problems
    var clientHeight = 
        floatingMenu.hasElement && floatingMenu.hasInner
        && document.documentElement.clientHeight 
            > window.innerHeight
        ? window.innerHeight
        : document.documentElement.clientHeight

    var cornerY =
        floatingMenu.hasElement
        ? (floatingMenu.hasInner  
           ? pageYOffset
           : document.documentElement.scrollTop) + 
          (clientHeight - height)/2
        : document.body.scrollTop + 
          (document.body.clientHeight - height)/2;
    return cornerY;
};

floatingMenu.doFloat = function()
{
    // Check if reference to menu was lost due
    // to ajax manipuations
    if (!floatingMenu.menu)
    {
        menu = document.getElementById
            ? document.getElementById(floatingMenuId)
            : document.all
              ? document.all[floatingMenuId]
              : document.layers[floatingMenuId];

        initSecondary();
    }

    var stepX, stepY;

    floatingMenu.computeShifts();

    var cornerX = floatingMenu.calculateCornerX();

    var stepX = (cornerX - floatingMenu.nextX) * .07;
    if (Math.abs(stepX) < .5)
    {
        stepX = cornerX - floatingMenu.nextX;
    }

    var cornerY = floatingMenu.calculateCornerY();

    var stepY = (cornerY - floatingMenu.nextY) * .07;
    if (Math.abs(stepY) < .5)
    {
        stepY = cornerY - floatingMenu.nextY;
    }

    if (Math.abs(stepX) > 0 ||
        Math.abs(stepY) > 0)
    {
        floatingMenu.nextX += stepX;
        floatingMenu.nextY += stepY;
        floatingMenu.move();
    }

    setTimeout('floatingMenu.doFloat()', 20);
};

// addEvent designed by Aaron Moore
floatingMenu.addEvent = function(element, listener, handler)
{
    if(typeof element[listener] != 'function' || 
       typeof element[listener + '_num'] == 'undefined')
    {
        element[listener + '_num'] = 0;
        if (typeof element[listener] == 'function')
        {
            element[listener + 0] = element[listener];
            element[listener + '_num']++;
        }
        element[listener] = function(e)
        {
            var r = true;
            e = (e) ? e : window.event;
            for(var i = element[listener + '_num'] -1; i >= 0; i--)
            {
                if(element[listener + i](e) == false)
                    r = false;
            }
            return r;
        }
    }

    //if handler is not already stored, assign it
    for(var i = 0; i < element[listener + '_num']; i++)
        if(element[listener + i] == handler)
            return;
    element[listener + element[listener + '_num']] = handler;
    element[listener + '_num']++;
};

floatingMenu.init = function()
{
    floatingMenu.initSecondary();
    floatingMenu.doFloat();
};

 
floatingMenu.initSecondary = function()
{
    floatingMenu.computeShifts();
    floatingMenu.nextX = floatingMenu.calculateCornerX();
    floatingMenu.nextY = floatingMenu.calculateCornerY();
    floatingMenu.move();
}

if (document.layers)
    floatingMenu.addEvent(window, 'onload', floatingMenu.init);
else
{
    floatingMenu.init();
    floatingMenu.addEvent(window, 'onload',
        floatingMenu.initSecondary);
}

var floatingMenuId1 = 'modalDefinePrimaryQueueNeedActionMessage<%=randval%>';
var floatingMenu1 =
{
    targetX: 390,
    targetY: 100,

    hasInner: typeof(window.innerWidth) == 'number',
    hasElement: typeof(document.documentElement) == 'object'
        && typeof(document.documentElement.clientWidth) == 'number',

    menu:
        document.getElementById
        ? document.getElementById(floatingMenuId1)
        : document.all
          ? document.all[floatingMenuId1]
          : document.layers[floatingMenuId1]
};

floatingMenu1.move = function ()
{
    floatingMenu1.menu.style.left = floatingMenu1.nextX + 'px';
    floatingMenu1.menu.style.top = floatingMenu1.nextY + 'px';
}

floatingMenu1.computeShifts = function ()
{
    var de = document.documentElement;

    floatingMenu1.shiftX =  
        floatingMenu1.hasInner  
        ? pageXOffset  
        : floatingMenu1.hasElement  
          ? de.scrollLeft  
          : document.body.scrollLeft;  
    if (floatingMenu1.targetX < 0)
    {
        floatingMenu1.shiftX +=
            floatingMenu1.hasElement
            ? de.clientWidth
            : document.body.clientWidth;
    }

    floatingMenu1.shiftY = 
        floatingMenu1.hasInner
        ? pageYOffset
        : floatingMenu1.hasElement
          ? de.scrollTop
          : document.body.scrollTop;
    if (floatingMenu1.targetY < 0)
    {
        if (floatingMenu1.hasElement && floatingMenu1.hasInner)
        {
            // Handle Opera 8 problems
            floatingMenu1.shiftY +=
                de.clientHeight > window.innerHeight
                ? window.innerHeight
                : de.clientHeight
        }
        else
        {
            floatingMenu1.shiftY +=
                floatingMenu1.hasElement
                ? de.clientHeight
                : document.body.clientHeight;
        }
    }
}

floatingMenu1.calculateCornerX = function()
{
    if (floatingMenu1.targetX != 'center')
        return floatingMenu1.shiftX + floatingMenu1.targetX;

    var width = parseInt(floatingMenu1.menu.offsetWidth);

    var cornerX =
        floatingMenu1.hasElement
        ? (floatingMenu1.hasInner
           ? pageXOffset
           : document.documentElement.scrollLeft) + 
          (document.documentElement.clientWidth - width)/2
        : document.body.scrollLeft + 
          (document.body.clientWidth - width)/2;
    return cornerX;
};

floatingMenu1.calculateCornerY = function()
{
    if (floatingMenu1.targetY != 'center')
        return floatingMenu1.shiftY + floatingMenu1.targetY;

    var height = parseInt(floatingMenu1.menu.offsetHeight);

    // Handle Opera 8 problems
    var clientHeight = 
        floatingMenu1.hasElement && floatingMenu1.hasInner
        && document.documentElement.clientHeight 
            > window.innerHeight
        ? window.innerHeight
        : document.documentElement.clientHeight

    var cornerY =
        floatingMenu1.hasElement
        ? (floatingMenu1.hasInner  
           ? pageYOffset
           : document.documentElement.scrollTop) + 
          (clientHeight - height)/2
        : document.body.scrollTop + 
          (document.body.clientHeight - height)/2;
    return cornerY;
};

floatingMenu1.doFloat = function()
{
    // Check if reference to menu was lost due
    // to ajax manipuations
    if (!floatingMenu1.menu)
    {
        menu = document.getElementById
            ? document.getElementById(floatingMenuId1)
            : document.all
              ? document.all[floatingMenuId1]
              : document.layers[floatingMenuId1];

        initSecondary();
    }

    var stepX, stepY;

    floatingMenu1.computeShifts();

    var cornerX = floatingMenu1.calculateCornerX();

    var stepX = (cornerX - floatingMenu1.nextX) * .07;
    if (Math.abs(stepX) < .5)
    {
        stepX = cornerX - floatingMenu1.nextX;
    }

    var cornerY = floatingMenu1.calculateCornerY();

    var stepY = (cornerY - floatingMenu1.nextY) * .07;
    if (Math.abs(stepY) < .5)
    {
        stepY = cornerY - floatingMenu1.nextY;
    }

    if (Math.abs(stepX) > 0 ||
        Math.abs(stepY) > 0)
    {
        floatingMenu1.nextX += stepX;
        floatingMenu1.nextY += stepY;
        floatingMenu1.move();
    }

    setTimeout('floatingMenu1.doFloat()', 20);
};

// addEvent designed by Aaron Moore
floatingMenu1.addEvent = function(element, listener, handler)
{
    if(typeof element[listener] != 'function' || 
       typeof element[listener + '_num'] == 'undefined')
    {
        element[listener + '_num'] = 0;
        if (typeof element[listener] == 'function')
        {
            element[listener + 0] = element[listener];
            element[listener + '_num']++;
        }
        element[listener] = function(e)
        {
            var r = true;
            e = (e) ? e : window.event;
            for(var i = element[listener + '_num'] -1; i >= 0; i--)
            {
                if(element[listener + i](e) == false)
                    r = false;
            }
            return r;
        }
    }

    //if handler is not already stored, assign it
    for(var i = 0; i < element[listener + '_num']; i++)
        if(element[listener + i] == handler)
            return;
    element[listener + element[listener + '_num']] = handler;
    element[listener + '_num']++;
};

floatingMenu1.init = function()
{
    floatingMenu1.initSecondary();
    floatingMenu1.doFloat();
};

floatingMenu1.initSecondary = function()
{
    floatingMenu1.computeShifts();
    floatingMenu1.nextX = floatingMenu1.calculateCornerX();
    floatingMenu1.nextY = floatingMenu1.calculateCornerY();
    floatingMenu1.move();
}

if (document.layers)
    floatingMenu1.addEvent(window, 'onload', floatingMenu1.init);
else
{
    floatingMenu1.init();
    floatingMenu1.addEvent(window, 'onload',
        floatingMenu1.initSecondary);
}
</script>
