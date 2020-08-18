<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyVO" %>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.SurveyQuesAnsVO" %>

<%@ taglib uri="/struts-tags" prefix="s" %>
<html>
<head>
<link href="${staticContextPath}/javascript/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery-ui-personalized-1.5.2.packed.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
<script type="text/javascript">
      $("#viewRatingslink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}");     
      </script>
      </head>
</html>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
            <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
                  <jsp:param name="PageName" value="ServiceOrderDetails.viewRatings"/>
            </jsp:include>
<%
	String buyerToProvidercomments="B2P Test Comments";
	String providerToBuyercomments="P2B Test Comments";
	String buyerToProviderOverallScore="0.0";
	String providerToBuyerOverallScore="0.0";
 	ArrayList surveyResultsBuyerToProvider = (ArrayList)request.getAttribute("surveyResultsFromBuyerToProvider");
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
 	//if(surveyResultsProviderToBuyer.size() == 0)
 		//session.setAttribute("surveyResultsFromProviderToBuyer", null);
 	if(surveyResultsBuyerToProvider!=null) {
 		if(surveyResultsBuyerToProvider.size() == 0) {
 			request.setAttribute("surveyResultsFromBuyerToProvider", null);
 			surveyResultsBuyerToProvider = null;
 		}
 	} 		
 	if(surveyResultsBuyerToProvider != null) {
 		buyerToProvidercomments = ((SurveyQuesAnsVO)surveyResultsBuyerToProvider.get(0)).getComments();
 		if(buyerToProvidercomments == null)
 			buyerToProvidercomments="";
 		buyerToProviderOverallScore = ((SurveyQuesAnsVO)surveyResultsBuyerToProvider.get(0)).getOverallScore();
 	}
%>

<!-- START RIGHT SIDE MODULES -->
<!--  Commenting the dojo part
<div id="rightsidemodules" dojoType="dijit.layout.ContentPane"
	href="soDetailsQuickLinks.action"
	class="colRight255 clearfix"> </div>
	-->
	<div class="soNote">
<div id="rightsidemodules" class="colRight255 clearfix">
	<p id="viewRatingslink"
		style="color: #000; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px;">
		<span> </span>
	</p>
</div>
<!-- END RIGHT SIDE MODULES -->
<div class="contentPane">
<!-- NEW MODULE/ WIDGET-->
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

 <c:if test="${surveyResultsFromBuyerToProvider!=null}">
<div class="grayModuleHdr">
	<div class="floatLeft">Service Provider</div>
	<div class="floatRight"><strong><fmt:formatNumber value="<%=buyerToProviderOverallScore%>" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/> Stars</strong></div>
	</div>

    <table cellpadding="0" cellspacing="0" class="viewRatingTable">
    <c:forEach var="srvyResults" items="${surveyResultsFromBuyerToProvider}">
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
      <td colspan="2"><p><strong>Additional Comments</strong><br>
    	<%=buyerToProvidercomments%></p> 
        </td>
    </tr>
  	</table>


    </div>
    </div>
    
<%--   extra end tag </div>--%>
<%-- extra end tag </div>--%>
</c:if> 
<!-- END TAB PANE -->
