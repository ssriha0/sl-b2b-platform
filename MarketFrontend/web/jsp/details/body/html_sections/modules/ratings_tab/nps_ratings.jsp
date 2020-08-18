<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.NPSSurveyQuestionAnswers"%>
<%
	NPSSurveyQuestionAnswers npsDetails = (NPSSurveyQuestionAnswers) request.getAttribute("npsDetails");
%>
<style>
.nps-rating-container {
  display: inline-block;
  width: 100%;
  margin-left: 25px;
  margin-bottom: 25px;
}

.nps-rating-container .row {
	padding: 10px 0;
}

.nps-legend {
  font-size: 0.7rem;
  padding: 10px 0;
}

@media (max-width: 576px) {
  .nps-rating-container {
    overflow-x: scroll;
    height: 4rem;
  }

  .nps-legend {
    width: 130%;
    white-space: nowrap;
  }
}
/*

	Ratings Stars
	(with as little code as possible)
*/
.nps-rating {
  unicode-bidi: bidi-override;
  display: inline-block;
  white-space: nowrap;

}
.nps-rating > span {
	background: none;
	border-radius: 1em;
	-moz-border-radius: 1em;
	-webkit-border-radius: 1em;
	color: #666;
	display: inline-block;
	margin-right: 15px;
	text-align: center;
	width: 24px;
	border: 1px solid #a3a3a3;
	height: 20px;
	padding: 3px 0 0;
}

.nps-rating > span.selected {
  	background: orange;
	border: 1px solid orange;
	color: #fff;
}
</style>
<h2><c:out value = "${npsDetails.question}"></c:out></h2>
<div class="nps-rating-container">

	<div class="nps-legend row">
		<div class="col-3">0 = Not at all likely</div>
		<div class="col-3">5 = Neutral</div>
		<div class="col-3">10 = Extremely likely</div>
	</div>
	<div class="row">
		<div class="nps-rating">
			<c:forEach var = "i" begin = "0" end = "10">
					<span class="${i == npsDetails.ratingSelected ? 'selected' : ''}"><c:out value = "${i}"></c:out></span>
			</c:forEach>
		</div>
	</div>
</div>
<hr />
<div class="row">
	<label for="comments">Comments</label>
	<textarea id="comments" class="col-10 offset-1 submenu_body" disabled><c:out value="${npsDetails.comments}"></c:out></textarea>
</div>