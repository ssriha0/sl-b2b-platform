<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
$(document).click(function(e){
		$('#sucessDiv').hide();
        $('#sucessResponseDiv').hide();
    });
    

</script>
<div class="row">
	<section class="lead-info col-md-7">
	<div id="sucessDiv" style="display: none" class="successBox"></div>
	
	<c:if test="${respMsg!= null}">
		<div id="sucessResponseDiv" class="successBox" >${respMsg}</div>
	</c:if>
	<c:if test="${errorMsg!= null}">
		<div id="errorDiv" class="buyerLeadError">${errorMsg}</div>
	</c:if>
	<%
		session.removeAttribute("respMsg");
		session.removeAttribute("errorMsg");
	%>
	<h2>
		Lead Info
	</h2>
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Industry
			</h3>
		</div>
		<div class="col-sm-9">
			<p>
				<c:choose><c:when test="${not empty lmTabDTO.lead.leadCategory}">${lmTabDTO.lead.leadCategory}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
			<p>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Project
			</h3>
		</div>
		<div class="col-sm-9">
			<p>
				<c:choose><c:when test="${not empty lmTabDTO.lead.projectType}">${lmTabDTO.lead.projectType}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
			<p>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Skill
			</h3>
		</div>
		<div class="col-sm-9">
			<p>
			<c:choose><c:when test="${not empty lmTabDTO.lead.skill}">
				${lmTabDTO.lead.skill}</c:when>
				<c:otherwise>Not specified </c:otherwise></c:choose>
			<p>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Service Details
			</h3>
		</div>
		<div class="col-sm-9">
			<p>
				<c:choose><c:when test="${not empty lmTabDTO.lead.leadDescription}">${lmTabDTO.lead.leadDescription}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
			<p>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Lead Type
			</h3>
		</div>
		<div class="col-sm-9">
			<!-- TODO Display 1 icon if exclusive, 2 icons if competitive 2, 3 icons if competitive 3 -->
			
			<c:if test="${lmTabDTO.lead.leadType =='EXCLUSIVE'}">
				Exclusive
			<i class="glyphicon glyphicon-user"></i>
			</c:if>  
			<c:if test="${lmTabDTO.lead.leadType =='COMPETITIVE'}">
				Competitive
				<i class="glyphicon glyphicon-user"></i>
				<i class="glyphicon glyphicon-user"></i>
				<i class="glyphicon glyphicon-user"></i>
			</c:if>  			
			<p>
			</p>
		</div>
	</div>

	<!-- TODO - Check whether the user is admin - Check the role BEGIN Only viewable by admins -->
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Source
			</h3>
		</div>
		
		<div class="col-sm-9">
			<p>
				<c:choose><c:when test="${not empty lmTabDTO.lead.leadSource}">${lmTabDTO.lead.leadSource}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
			</p>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-3">
			<h3>
				Lead Price
			</h3>
		</div>
		<div class="col-sm-9">
			<p>
				<c:choose><c:when test="${not empty lmTabDTO.lead.leadPrice}">${lmTabDTO.lead.leadPrice}
						</c:when>
						<c:otherwise>Not specified </c:otherwise></c:choose>
			</p>
		</div>
	</div>
	<!-- END Only viewable by admins -->
	</section>
	<!-- /.lead-info -->
