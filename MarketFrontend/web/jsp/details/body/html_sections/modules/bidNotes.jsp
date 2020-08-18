<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery.modal.min.css" type="text/css"></link>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqueryui/jquery-ui-1.10.4.custom.min.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
		$(".replyForm").hide();

		$(".limitChars").bind("keyup blur", function() {
			lettersRemaining = 750 - $(this).val().length;
			if (lettersRemaining >= 0) {
				if (lettersRemaining == 1) {
					$(this).parent().find(".remainingNotice").html("1 character remaining");
				} else {
					$(this).parent().find(".remainingNotice").html(lettersRemaining + " characters remaining");
				}
			} else {
				$(this).parent().find(".remainingNotice").html("0 characters remaining");
				$(this).val($(this).val().substr(0,750));
			}
		});

		$(".replyLink").click(function() {
		  	$(".replyForm").hide();
		 	$(this).parent().parent().find(".replyForm").show();
		 	return false;
		});

		$(".cancelReply").click(function() {
			$(this).parent().parent().find("textarea").val("");
			$(this).parent().parent().find(".remainingNotice").html("750 characters remaining");
			$(this).parent().parent().find(".errorBox").hide();
			$(".replyForm").hide();
			return false;
		});


		$("#addNoteButtonDiv").click(function()
		{
			
		 	var postText = $.trim($('#postText').val());
			if('' == postText){
				$("#addNoteButtonError").show();
				return false;
			}else{
				$("#addNoteButtonError").hide();
			}

			$('#bidNotesDiv').load("providerUtilsAddNoteAjax.action?soId=${SERVICE_ORDER_ID}",
			{
				postText: $('#postText').val()
			});
		});

		$(".addReplyButtonClass").click(function()
		{
			
		});



	});
	var postReplydDef = "Use this space to communicate about the project, ask questions and share information. Messages are visible to all providers the buyer has selected.";
	postReplydDef += " Sharing contact information, names, etc. at this point, violates the terms and conditions of the marketplace.";
	var reportPostDef ="Please report inappropriate language, personal information sharing (name,phone, address, email) or other offensive messages.";
	var arrow = '<div id="pointerArrow" style="position:absolute; float:left; top:35%; left:-13px; width:16px; height:19px; background: url(/ServiceLiveWebUtil/images/icons/explainerArrow.gif) no-repeat 0 0;" />';

	function doExplainer(e,def){
	var x = e.pageX;
	      	var y = e.pageY;
	      	$("#explainer").css("top",y-60);
	      	$("#explainer").css("left",x+20);
	      	$("#explainer").css("position","absolute");
	      	$("#explainer").html(arrow + def);
	      	$("#explainer").css("font-size","11px").css("line-height","120%");
	      	$("#explainer").show();
	}


	$(".replyLink").mouseover(function(e){
	      	doExplainer(e,postReplydDef);
     	});
 		$(".replyLink").mouseout(function(e){
 			$("#explainer").hide();
 		});
 		$(".replyLink").click(function(e){
 			$("#explainer").hide();
 		});
 	var explainerViewed = false;
	$("#postText").mouseover(function(e){
	      	if(!explainerViewed) {doExplainer(e,postReplydDef);}
     	});
 		$("#postText").mouseout(function(e){
 			$("#explainer").hide();
 		});
 		$("#postText").click(function(e){
 			$("#explainer").hide();
 		});
 		$("#postText").focus(function(e){
 			$("#explainer").hide();
 			explainerViewed = true;
 		});


	$(".reportLink").mouseover(function(e){
	      	doExplainer(e,reportPostDef);
     	});
 		$(".reportLink").mouseout(function(e){
 			$("#explainer").hide();
 		});
 		$(".reportLink").click(function(e){
 			$("#explainer").hide();
 		});


</script>

<c:if test="${!readOnly}">
	<div class="directions">
		<c:choose>
		<c:when test="${userRole == 'Provider'}">
			Post a note or question about this order. Information is visible to ServiceLive Support Team, all providers selected for the project and the buyer who posted the project.
		</c:when>
		<c:otherwise>
			<label><strong>Add a Comment</strong></label>
			<p>Use the form below to communicate with the providers you routed your service order to.</p>
		</c:otherwise>
		</c:choose>
	</div>




	<div id="postQuestionForm">
		<div id="questionFormArea">
			<label for="questionText">
				<c:if test="${userRole == 'Provider'}">
					<a name="viewComments">Request Information</a>
				</c:if>
			</label>

			<c:if test="${logErrorMsg != null}">
				<div style="color: red">${logErrorMsg}</div>
			</c:if>


			<input type="hidden" name="orderId" value="%{orderId}" />
			<input type="hidden" name="providerId" value="%{providerId}" />
			<input type="hidden" name="filterFollowup" value="%{filterFollowup}" />
			<s:hidden></s:hidden>
			<s:hidden></s:hidden>
			<s:hidden></s:hidden>
			<div id="addNoteButtonError" class="errorBox" style="display:none;background-position:3px;background-repeat: no-repeat;
								background-image: url('/ServiceLiveWebUtil/images/icons/errorIcon.gif');padding-left:20px">
				Please enter a note or question to post
			</div><br>
			<textarea  id="postText" name="postText" class="limitChars" rows="2" cols="20" />
			<div class="submitarea">
				<span class="remainingNotice">750 characters remaining</span>
				<input type="button" value="SUBMIT" id="addNoteButtonDiv"
					name="addNoteButtonDiv"
					style="float: right; font-size: 9px; font-weight: bold;" />
			</div>
			<br style="clear: both;" />
		</div>
	</div>
</c:if>
<c:if test="${userRole != 'Provider'}">
	<h2><a name="comments">Comments</a></h2>

	<s:set name="showCommunication" value="#session.showCommunication" />
	<s:set name="newCommentCnt" value="#session.newCommentCnt" />

	<c:if test="${showCommunication == true || filterFollowupPosts == true  }">
		<div style="text-align: right;">
			<c:choose>
			<c:when test="${filterFollowupPosts}">
				<a href="" style="font-weight: bold;" onclick="filterFollowupPosts(false); return false;">View All Items</a>
			</c:when>

		    <c:when test="${newCommentCnt > 0 || newCommentCnt == null}">
				<a href="" style="font-weight: bold;" onclick="filterFollowupPosts(true); return false;">View Items Needing Followup</a>
			</c:when>
			</c:choose>

		</div>
	</c:if>
</c:if>
<s:iterator value="bidNotes" status="stat">
	<c:choose>
	<c:when test="${filterFollowup && viewStatusName != 'REQUIRES_FOLLOW_UP'}">
		<!-- show nothing because this item does not require followup -->
	</c:when>
	<c:otherwise>
	<div class="postWrap">
		<div style="float: left">
			<img src="${staticContextPath}/<s:property value="icon"/>"/> <br/>
		</div>
		<div class="post">
			<div class="byline">
				<strong>
					<s:property value="createdByName"/>
					<c:if test="${posterName != null}">
						(<s:property value="posterName"/>,
						ID# <s:property value="posterId"/>)
					</c:if>
				 Asked:
				</strong>
				<s:property value="createdOn"/>
				<c:if test="${showFollowupFlag && !readOnly}">
				<div style="text-align:right;float:right;width:300px;line-height:1.2em;">
					<c:choose>
				    	<c:when test="${viewStatusName == 'REQUIRES_FOLLOW_UP'}">
				    		Needs Followup<br />
				    	</c:when>
				    	<c:when test="${viewStatusName == 'UNREAD'}"></c:when>
				    	<c:otherwise>
				    		<a href="" style="font-weight: bold;font-size:0.9em;"
				    				onclick="flagPostForFollowup(${activityId}, '${associationType}'); return false;">
								Flag for Followup<br />
							</a>
				    	</c:otherwise>
				    </c:choose>
					<c:choose>
				    	<c:when test="${viewStatusName == 'UNREAD' || viewStatusName == 'REQUIRES_FOLLOW_UP'}">
				    		<a href="" style="font-weight: bold;font-size:0.9em;"
				    				onclick="markPostAsRead(${activityId}, '${filterFollowup}', '${associationType}'); return false;">
								Mark as Read
							</a>
				    	</c:when>
				    </c:choose>
				</div>
				</c:if>
			</div>
			<c:choose>
			<c:when test="${status == 'REPORTED_FOR_REVIEW'}">
				<div style="color: red">
					<c:choose>
					<c:when test="${isAdmin}">
						<s:property value="post"/>
					</c:when>
					<c:otherwise>
						${REPORTED_POST_TEXT}
					</c:otherwise>
					</c:choose>
				</div>
			</c:when>
			<c:when test="${status == 'DISABLED'}">
				<div style="color: red">
					<s:property  value="post"/>
				</div>
			</c:when>
			<c:otherwise>
				<s:property  value="post"/>
			</c:otherwise>
			</c:choose>
			<br/>
			<c:if test="${currentResourceId != posterId && status == 'ENABLED' && !readOnly}">
				<a href="" class="reportLink" style="font-weight: bold; color:red" onclick="if (confirm ('Are you sure you want to report inappropriate language, personal information sharing (name,phone, address, email) or other offensive messages?')) { reportPostToActivityLog(${activityId}, '${filterFollowup}'); }; return false;">
					Report This
				</a>
			</c:if>
			<%--
			<s:if test="${isAdmin && status == 'REPORTED_FOR_REVIEW'}">
				<a href="" class="makePostViewableLink" style="font-weight: bold; color:red" onclick="makePostViewableActivityLog(${activityId});">
					Make this post viewable
				</a>
				&nbsp;
				<a href="" class="hidePostLink" style="font-weight: bold; color:red" onclick="hidePostActivityLog(${activityId});">
					Hide this post
				</a>
			</s:if>
			--%>

			<s:iterator value="replies" id="reply">
				<div class="replyWrap">
					<img src="${staticContextPath}/<s:property value="icon"/>"/>
					<div class="reply">
						<div class="byline">
						<strong>
							<s:property value="createdByName"/>
							<c:if test="${posterName != null}">
								(<s:property value="posterName"/>,
								ID# <s:property value="posterId"/>)
							</c:if>
							Replied:</strong> <s:property value="createdOn"/><br />
						</div>
						<c:choose>
						<c:when test="${status == 'REPORTED_FOR_REVIEW'}">
							<div style="color: red">
							<c:choose>
								<c:when test="${isAdmin}">
									<s:property  value="post"/>
								</c:when>
								<c:otherwise>
									${REPORTED_POST_TEXT}
								</c:otherwise>
							</c:choose>
							</div>
						</c:when>
						<c:when test="${status == 'DISABLED'}">
							<div style="color: red">
								<s:property  value="post"/>
							</div>
						</c:when>
						<c:otherwise>
							<s:property  value="post"/>
						</c:otherwise>
						</c:choose>
						<br/>
						<c:if test="${currentResourceId != posterId && status == 'ENABLED' && !readOnly}">
							<a href="" class="reportLink" style="font-weight: bold; color:red" onclick="if (confirm ('Are you sure you want to report inappropriate language, personal information sharing (name,phone, address, email) or other offensive messages?')) { reportPostToActivityLog(${activityId}, '${filterFollowup}'); }; return false;">
								Report This
							</a>
						</c:if>
						<%--
						<s:if test="${isAdmin && status == 'REPORTED_FOR_REVIEW'}">
							<a href="" class="makePostViewableLink" style="font-weight: bold; color:red" onclick="makePostViewableActivityLog(${activityId});">
								Make this post viewable
							</a>
							&nbsp;
							<a href="" class="hidePostLink" style="font-weight: bold; color:red" onclick="hidePostActivityLog(${activityId});">
								Hide this post
							</a>
						</s:if>
						--%>
					</div>
				</div>
			</s:iterator>

			<c:if test="${!readOnly}">
				<div style="clear:both;">
				<c:if  test="${status != 'REPORTED_FOR_REVIEW'}">
				<a href="" class="replyLink" style="font-weight: bold; float: left">Reply</a><br>
				</c:if>
				<%--
				<div style="float:right; padding-right: 20px;">
				 	<a href="" class="reportLink" style="color: red" onclick="if (confirm ('Are you sure you want to report inappropriate language, personal information sharing (name,phone, address, email) or other offensive messages?')) { reportPostToActivityLog(${activityId}); }; return false;">Report This</a>
				</div>

				<%--
				<s:form id="reply%{activityId}form" action="providerUtilsAddReply" theme="simple">
				--%>
					<input type="hidden" name="orderId" value="%{orderId}"/>
					<input type="hidden" name="providerId" value="%{providerId}"/>
					<input type="hidden" name="parentActivityId" value="%{activityId}"/>
					<s:hidden></s:hidden>
			        <s:hidden></s:hidden>
			        <s:hidden></s:hidden>
					<div class="replyForm">
						<div id="addReplyError${activityId}" class="errorBox" style="display:none;background-position:3px;background-repeat: no-repeat;
								background-image: url('/ServiceLiveWebUtil/images/icons/errorIcon.gif');padding-left:20px;width:200px">
										Please enter a reply
						</div><br>
						<div class="replyTextArea">
							<input type="text" name="replyText${activityId}" id="replyText${activityId}" class="limitChars" /><br />
							<span class="remainingNotice">750 characters remaining</span>
						</div>
						<div class="submitArea">

							<%--<s:submit value="SUBMIT"/><br />
							<input type="button" value="SUBMIT" id="addReplyButtonID" name="addReplyButtonID" class="addReplyButtonClass"/>
							--%>
							<input type="button" value="SUBMIT" onclick="addBidNoteReply('fubah', ${activityId}, '${filterFollowup}');" /><br/>
							<a href="" class="cancelReply">Cancel</a>
						</div>
					</div>
				<%--
				</s:form>
				--%>
			</c:if>
		</div>
		<br style="clear:both;"/>
	</div>
	</c:otherwise>
	</c:choose>
</s:iterator>

