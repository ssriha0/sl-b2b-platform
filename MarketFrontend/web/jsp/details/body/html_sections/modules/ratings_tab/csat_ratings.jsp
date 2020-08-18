<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="com.newco.marketplace.dto.vo.survey.CSATSurveyQuestionAnswers"%>
<%
	CSATSurveyQuestionAnswers csatDetails = (CSATSurveyQuestionAnswers) request.getAttribute("csatDetails");
%>
<style>
	/** Ratings Styles **/
	.rating {
		unicode-bidi: bidi-override;
		display: inline-block;
		margin: 20px 0 0;
	}
	.rating > span {
		display: inline-block;
		position: relative;
		width: 1.1em;
		color: #d3d3d3;
		font-size: 2em;
		margin-left: 1.3rem;
	}

	.rating > span.stars-fill:before {
		content: "\2605";
   		color: #F8C248;
	}

	.rating > span.stars-empty:before {
		content: "\2605";
   		color: #d3d3d3;
	}

	/** Option Boxes Styles **/
	.border-box {
		border: 3px solid rgba(54,54,54,.6);
		font-size: 13px;
		text-align: center;
		min-height: 50px;
		width: 100%;
		border-radius: 5px;
		background: 0 0;
		margin: 10px;
		position: relative;
		font-weight: 700;
		text-transform: uppercase;
		color: #545454;
		padding-top: 30px;
	}

	.border-box > span {
		display: none;
	}

	.border-box.selected {
		border: 3px solid #E87B14;
		color: #E87B14;
	}

	.border-box.selected > span {
		display: inline-block;
		position: absolute;
		top: 5px;
		left: 5px;
	}

</style>
<div class="container-fluid mx-auto">
<div class="row">
	<h2>Service Pro's rating by customer:</h2>
	<div class="rating">
		<c:forEach begin="1" end="${csatDetails.ratingSelected}" step="1">
			<span class="stars-fill"></span>
		</c:forEach>
		<c:forEach begin="1" end="${5 - csatDetails.ratingSelected}" step="1">
			<span class="stars-empty"></span>
		</c:forEach>
	</div>
</div>
<hr />
<div class="row">
	<h2>Survey options selected by customer:</h2>
	<c:forEach items="${csatDetails.options}" var="option">
	<div class="col-lg-3 col-6">
		<div class="border-box ${option.selected ? 'selected' : ''}">
			<span>&#10004;</span> <c:out value="${option.text}"></c:out>
		</div>
	</div>
	</c:forEach>
</div>
<hr />
<div class="row">
	<label for="comments">Comments</label>
	<textarea id="comments" class="col-10 offset-1 submenu_body" disabled><c:out value="${csatDetails.comments}"></c:out></textarea>
</div>