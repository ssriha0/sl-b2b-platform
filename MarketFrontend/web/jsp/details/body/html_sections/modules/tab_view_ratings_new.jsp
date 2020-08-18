<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java"
	import="com.newco.marketplace.dto.vo.survey.SurveyQuestionsAnswersResponse"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyVO" %>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO" %>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<link href="${staticContextPath}/css/bootstrap/4.1.3/bootstrap.css"
	rel="stylesheet" type="text/css" />
<link href="${staticContextPath}/javascript/style.css" rel="stylesheet"
	type="text/css" />
<style>
/** General Styles **/
h2 {
	display: block;
	width: 100%;
	font-size: 14px;
	font-weight: bold;
}

.row {
	width: 100%;
	padding: 25px 0;
}

label {
	width: 100%;
	padding: 10 0 0;
	font-size: 12px;
	font-weight: bold;
	color: #676767;
}

hr {
	border: 1px solid #07a3bc;
	margin: 0;
}

.survey-form {
	width: 90%;
}
</style>
<script type="text/javascript"
	src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript">
	$("#viewRatingslink").load(
			"soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");
</script>
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	<jsp:param name="PageName" value="ServiceOrderDetails.viewRatings" />
</jsp:include>
<%
	String surveyStatus = (String) request.getAttribute("surveyStatus");
	SurveyQuestionsAnswersResponse surveyResults = (SurveyQuestionsAnswersResponse) request
			.getAttribute("surveyResults");
	
	String providerToBuyercomments="P2B Test Comments";
	String providerToBuyerOverallScore="0.0";
  	ArrayList surveyResultsProviderToBuyer = (ArrayList)request.getAttribute("surveyResultsFromProviderToBuyer");
 	if(surveyResultsProviderToBuyer!=null) {
 		if(surveyResultsProviderToBuyer.size() == 0) {
 			request.setAttribute("surveyResultsFromProviderToBuyer", null);
 			surveyResultsProviderToBuyer = null;
 		}
 	}
 	if(surveyResultsProviderToBuyer!=null) {		
 		providerToBuyercomments = ((SurveyQuesAnsVO)surveyResultsProviderToBuyer.get(0)).getComments();
 		if(providerToBuyercomments == null)
 			providerToBuyercomments="";
 		providerToBuyerOverallScore = ((SurveyQuesAnsVO)surveyResultsProviderToBuyer.get(0)).getOverallScore();
 	}
%>

<div class="soNote">
	<div id="rightsidemodules" class="colRight255 clearfix">
		<p id="viewRatingslink"
			style="color: #000; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px;">
			<span> </span>
		</p>
	</div>
	<!-- END RIGHT SIDE MODULES -->
	<div class="contentPane">
		<div class="darkGrayModuleHdr">Ratings</div>
		
		<c:if test="${surveyResultsFromProviderToBuyer!=null}">
			<div class="grayModuleContent mainWellContent clearfix">
			    <div class="grayModuleHdr"><div class="floatLeft">
			    	Service Buyer
			    </div> 
			    <div class="floatRight">
			    	<strong><fmt:formatNumber value="<%=providerToBuyerOverallScore%>" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/> Stars</strong>
			    </div>
		    </div>
		
			
		    <table cellpadding="0" cellspacing="0" class="viewRatingTable">
			    <c:forEach var="srvyResults" items="${surveyResultsFromProviderToBuyer}">
				    <tr>
				      <td class="column1"><p><b><c:out value="${srvyResults.questionText}"></c:out></b></p>
				        <p><c:out value="${srvyResults.questionDescription}"></c:out></p></td>
				      <td class="column2"><p><c:out value="${srvyResults.answerText}"></c:out></p>
				        <p>
			<%--  Commented out for 12/15/07 release
			Note: Removed tooltip code
			COMMENT START-
				        <span class="RATE" onmouseout="popUp(event,'userRatings')" onmouseover="popUp(event,'userRatings')">
			COMMENT END-	        
			--%>	        
				        <span class="RATE"><img src="${staticContextPath}/images/common/stars_<c:out value="${srvyResults.scoreNumber}"/>.gif" border="0" /></span></p></td>
				    </tr>
			    </c:forEach>
			    
			    
			    <tr class="alt">
			      <td colspan="2"><p><strong>Additional Comments:</strong><br>
				    <%=providerToBuyercomments %></p> 
			      </td>
			    </tr>
		  	</table>
	  	</c:if>
	  	
	  	<div class="grayModuleHdr">
			<div class="floatLeft">Service Provider</div>
			<div class="floatRight">
			<c:if test="${surveyResults.rating > 0}">
				<strong><fmt:formatNumber value="${surveyResults.rating}" type="NUMBER" /> Ratings</strong>
			</c:if>
			</div>
		</div>
  		<!-- NEW MODULE/ WIDGET-->			
		<div class="survey-form mx-auto">
		<c:choose>
			<c:when test="${'NOT_CONFIGURED'.equalsIgnoreCase(surveyStatus)}">
				<p>Buyer has not configured any survey type.</p>
			</c:when>
			<c:when test="${'NOT_RATED'.equalsIgnoreCase(surveyStatus)}">
				<p>No Ratings submitted yet.</p>
			</c:when>
			<c:when test="${'NO_TAB'.equalsIgnoreCase(surveyStatus)}">
				<p>&nbsp;</p>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when
						test="${'csat_nps'.equalsIgnoreCase(surveyResults.getSurveyType())}">
						<%
							request.setAttribute("csatDetails", surveyResults.getCsat());
											request.setAttribute("npsDetails", surveyResults.getNps());
						%>
						<jsp:include
							page="/jsp/details/body/html_sections/modules/ratings_tab/csat_nps_ratings.jsp"
							flush="true"></jsp:include>
					</c:when>
					<c:when
						test="${'nps_csat'.equalsIgnoreCase(surveyResults.getSurveyType())}">
						<%
							request.setAttribute("csatDetails", surveyResults.getCsat());
											request.setAttribute("npsDetails", surveyResults.getNps());
						%>
						<jsp:include
							page="/jsp/details/body/html_sections/modules/ratings_tab/nps_csat_ratings.jsp"
							flush="true"></jsp:include>
					</c:when>
					<c:when
						test="${'nps'.equalsIgnoreCase(surveyResults.getSurveyType())}">
						<%
							request.setAttribute("npsDetails", surveyResults.getNps());
						%>
						<jsp:include
							page="/jsp/details/body/html_sections/modules/ratings_tab/nps_ratings.jsp"
							flush="true"></jsp:include>
					</c:when>
					<c:when
						test="${'csat'.equalsIgnoreCase(surveyResults.getSurveyType())}">
						<%
							request.setAttribute("csatDetails", surveyResults.getCsat());
						%>
						<jsp:include
							page="/jsp/details/body/html_sections/modules/ratings_tab/csat_ratings.jsp"
							flush="true"></jsp:include>
					</c:when>
					<c:otherwise>
						<p>No Ratings submitted yet.</p>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>
<!-- END TAB PANE -->
