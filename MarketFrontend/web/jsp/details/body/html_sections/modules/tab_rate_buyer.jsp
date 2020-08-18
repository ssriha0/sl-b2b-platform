<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyVO" %>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
 <script type="text/javascript">
      $("#ratebuyerlink").load("soDetailsQuickLinks.action");
    
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
 	ArrayList surveyResults = (ArrayList)request.getAttribute("surveyResultsFromProviderToBuyer");
 	if(surveyResults != null) {
 		comments = ((SurveyQuesAnsVO)surveyResults.get(0)).getComments();
 		if(comments == null)
 			comments="";
 		overallScore = ((SurveyQuesAnsVO)surveyResults.get(0)).getOverallScore();
 	}
 	String errMsg="";
 	if(session.getAttribute("detailsErrorMsg")!=null) {
	 	errMsg = session.getAttribute("detailsErrorMsg").toString();
	 	session.removeAttribute("detailsErrorMsg");
	 }
%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.rateBuyer"/>
            </jsp:include>
            
            <div class="soNote">
             <div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         
         <p id="ratebuyerlink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
         
      
       
    </div>
<form name="rateProvider" action="<s:url action="soDetailsRateProviderSave.action" />" method="post">

<div style="width: 670px;">
  <%if(errMsg.length()>0) { %>
  	<p><b><font color="red"><%=errMsg%></font></b></p>
  <% } %>
  <p class="paddingBtm">Let others know what it was like to work with your service provider. Click stars to rate your experience from one-star to five-stars. Detailed comments can be entered in the field provided.</p>
  <table cellpadding="0" cellspacing="0" class="ratingTable">
  
  <c:choose>
  <c:when test="${questionList!=null}">
  	<c:forEach var="question" items="${questionList}">
    <tr valign="top" class="alt">
      <td><p><b><c:out value="${question.questionText}"></c:out></b></p>
        <p><c:out value="${question.questionDescription}"></c:out></p></td>
      <td align="left">
      	<!--  
      	<p>Highly Statisfied</p>
        <p> <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')"><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/full_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/half_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /><img src="${staticContextPath}/images/common/empty_star_gbg.gif" border="0" /></span></p></td>
        -->
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
<%--    <tr class="darkGray">--%>
<%--      <td><p><b>Overall</b></p></td>--%>
<%--      <td class="column2"><p><strong>3.8 Stars</strong></p></td>--%>
<%--    </tr>--%>
    <tr>
    	<td align="center" colspan="2">
    	<hr width="80%"/>
    	</td>
    </tr>
    <tr>
      <td colspan="2"><b>Additional Comments</b> <br/>
        <div align="center">
          <textarea class="shadowboxedTextarea" name="surveyComments" style="width:675px;height:75px;"></textarea>
        </div></td>
    </tr>
    </c:when>
    <c:when test="${surveyResultsFromProviderToBuyer!=null}">
    	<c:forEach var="srvyResults" items="${surveyResultsFromProviderToBuyer}">
    	<tr class="alt">
	      	<td colspan="10"><b><c:out value="${srvyResults.questionText}"></c:out></b></td>
	      	<td colspan="10" align="left"><b><c:out value="${srvyResults.score}"></c:out> - <c:out value="${srvyResults.answerText}"></c:out></b></td>
	      	<br/>
    	</tr>
    	<tr>
    		<td align="center" colspan="2">
    		</td>
    	</tr>
    	</c:forEach>
    	<tr>
    		<td align="left" colspan="2"><b>Overall Score: 
    			<%=overallScore%> </b>
    		</td>
    	</tr>
    	<tr>
    		<td align="center" colspan="2">
    		</td>
    	</tr>
    </c:when>
    <c:otherwise>
    	<tr>
	      	<td colspan="2"><b>Error !</b> <br/>
    	</tr>
    </c:otherwise>
    </c:choose>
  </table>
  <div class="clearfix">
  <div class="floatLeft">
  <p>
    <input width="72" height="20" type="image" class="btnBevel" style="background-image: url(${staticContextPath}/images/btn/submit.gif);" src="${staticContextPath}/images/common/spacer.gif"/>
  </p>
  </div>
  <div class="floatRight">
  <p>
    <a href="">Cancel</a>
  </p>
  </div>
</div>
</form>
</div>
