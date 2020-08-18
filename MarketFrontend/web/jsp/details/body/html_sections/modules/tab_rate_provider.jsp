<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyVO" %>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s" %>

<script type="text/javascript">
      jQuery("#rateproviderlink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");
</script>
<script language="JavaScript" type="text/javascript">
	//To check for the special chars('<' and '>') - for XSS
	function checkSpecialKeys(e) {			
		var evtobj=window.event? event : e ;
		var keyVal =(window.event) ? event.keyCode : e.which;		
		if(evtobj.shiftKey){
			if (keyVal == 188 || keyVal == 190 || keyVal == 60 || keyVal == 62 ){//188 - <,190 - > --- In IE & 60 - <,62 - > --- in Mozilla
            	return false;
        	}else{
            	return true;
       		 }
		}       
     }
     function checkSpecialChars(e){     	
     	var pattern= new RegExp("[<>{}\\[\\]\\&()]");
     	var el = document.getElementById('surveyComments');
	    if(el){
	        var commentsVal = el.value;
			var isMatch= commentsVal.match(pattern);
			if(isMatch == null){
				document.getElementById('commentsError').style.visibility = "hidden";
				return true;
			}else{
				if(document.getElementById('commentsError')){
					//if(document.getElementById('commonError'))
						//document.getElementById('commonError').style.visibility = "hidden";
					document.getElementById('commentsError').style.visibility = "visible";
					el.value = "";//If there is a match then clear the input.
					return false;
				}
			}		
		}
     }  
</script>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%
	String comments="Test Comments";
	String overallScore="0.0";
	SurveyVO vo = (SurveyVO)request.getAttribute("surveyvo");
 	ArrayList questionList=vo.getQuestions() ;
 	request.setAttribute("questionList",questionList);
 	ArrayList surveyResults = (ArrayList)request.getAttribute("surveyResultsFromBuyerToProvider");
 	if(surveyResults != null) {
 		comments = ((SurveyQuesAnsVO)surveyResults.get(0)).getComments();
 		if(comments == null)
 			comments="";
 		overallScore = ((SurveyQuesAnsVO)surveyResults.get(0)).getOverallScore();
 	}
 	String errMsg="";
 	if(request.getAttribute("detailsErrorMsg")!=null) {
	 	errMsg = request.getAttribute("detailsErrorMsg").toString();
	 	request.removeAttribute("detailsErrorMsg");
	 }
%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.rateProvider"/>
            </jsp:include>
            
<!-- START RIGHT SIDE MODULES -->
<div class="soNote">
<div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         
         <p id="rateproviderlink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
         
      
       
    </div>
<form name="rateProvider" id="rateProvider" action="<s:url action="soDetailsRateProviderSave.action" />" method="post">	

<input type="hidden" name="soId" id="soId" value="${SERVICE_ORDER_ID}" />
<div style="width:680px; float:left;">
	<div id="commentsError" class="errorBox clearfix" style="width: 675px; visibility:hidden;">
		<p class="errorMsg">
			&nbsp;&nbsp;&nbsp;&nbsp;Additional Comments - Enter Valid Comments 
		</p>
	</div>
	<div style="color: blue">
	${msg}
	</div>
 		  <%request.setAttribute("msg",""); %>
 <c:if test="${questionList!=null || surveyResultsFromBuyerToProvider == null}">
  <%if(errMsg.length()>0) { %>
  	<p><b><font color="red"><%=errMsg%></font></b></p>
  <% } %>
     <p class="paddingBtm">Let others know what it was like to work with your service provider. Click stars to rate your experience from one-star to five-stars. Detailed comments can be entered in the field provided.</p>
  </c:if>
  <table cellpadding="0" cellspacing="0" class="ratingTable">
  
  <c:choose>
  <c:when test="${questionList!=null}">
  	<c:forEach var="question" items="${questionList}">
    <tr valign="top" class="alt">
      <td><p><b><c:out value="${question.questionText}"></c:out></b></p>
        <p><c:out value="${question.questionDescription}"></c:out></p></td>
      <td align="left">
      
        <c:forEach var="answer" items="${question.answers}">
        	<nobr>
        	<input type="radio" name="q-<c:out value="${question.questionId}"></c:out>" value="<c:out value="${answer.answerId}"></c:out>"> <c:out value="${answer.answerText}"></c:out><br/>
        	</nobr>
        </c:forEach>

	  </td>
    </tr>
    <tr>
    	<td align="center" colspan="2">
    	</td>
    </tr>
    </c:forEach>
    <tr>
    	<td align="center" colspan="2">
    	<hr width="80%"/>
    	</td>
    </tr>
    <tr>

      <td colspan="2">
      <b>Additional Comments</b>
      <br/>
	<%-- Fixing Sears00051299: Additional Comments - Truncated (Mark J. 5/13/08) --%>
        <div>
          <textarea name="surveyComments" id="surveyComments" class="shadowboxedTextarea" style="width:660px;height:75px;"
          onkeydown="countAreaChars(this.form.surveyComments, this.form.surveyComments_leftChars, 998, event);"
          onkeyup="countAreaChars(this.form.surveyComments, this.form.surveyComments_leftChars, 998, event);"
          ></textarea>
          <input type="text" name="surveyComments_leftChars" readonly size="4" maxlength="4" > chars left
        </div></td>
    </tr> 
    </c:when>
    <c:when test="${surveyResultsFromBuyerToProvider!=null}">
    	<p class="paddingBtm"> 
    		<fmt:message bundle="${serviceliveCopyBundle}" key="surveyRatings.imported.message"/><br/>
    		<a href="monitor/soDetailsController.action?defaultTab=View Ratings&soId=${SERVICE_ORDER_ID}">Click here</a> 
    		<fmt:message bundle="${serviceliveCopyBundle}" key="surveyRatings.view.message"/>
    	</p> 
    </c:when>
    <c:otherwise>
    	<tr>
	      	<td colspan="2"><b>Error !</b> <br/>
    	</tr>
    </c:otherwise>
    </c:choose>
  </table>
  <c:if test="${questionList!=null || surveyResultsFromBuyerToProvider == null}">
  	<div class="clearfix">
    	<div class="floatLeft">
 		 <p>
    		<input width="72" height="20" type="image" class="btnBevel" 
    		style="background-image: url(${staticContextPath}/images/btn/submit.gif);" 
    		src="${staticContextPath}/images/common/spacer.gif" />
 		 </p>
  		</div>
  		<div class="floatRight">
  			<p>
   				<a href="#" onClick="javascript:document.getElementById('rateProvider').reset();">
	    			<img src="${staticContextPath}/images/common/spacer.gif" width="72" height="20" 
	    			style="background-image:url(${staticContextPath}/images/btn/cancel.gif);" class="btnBevel" "/>
    			</a>
  			</p>
 		</div>
	</div>
</c:if>
</form>
</div>