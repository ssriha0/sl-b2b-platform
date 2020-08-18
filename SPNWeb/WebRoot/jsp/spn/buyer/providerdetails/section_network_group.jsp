<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form id="formChangeGroup" name="formChangeGroup">

	<%-- <div class="routingPriority">
		spnCreateNetwork_viewRoutingTiersAjax will load this div
	</div> --%>

	<div class="changeNetworkGroupPane">
		<div class="error errorMsg errorMessage">
			<span>Error test</span>
		</div>
		<p>
			(<span class="req">*</span> required)
		</p>
		<h5>
			Change Network Group: 
		</h5>
		<table border="0" cellpadding="0" cellspacing="0"
			class="networkGroupSelector">
			
			<tr>
				<c:forEach items="${performanceLevels}" var="pf">
					<td>
						<img title="${pf.description}" alt="${pf.description}" src="/ServiceLiveWebUtil/images/spn/priority${pf.id}small.gif" />
					</td>
				</c:forEach>
			</tr>
			<tr>
				<c:forEach items="${performanceLevels}" var="pf">
					<td>
					<c:choose>
						<c:when test="${networkGroup == pf.id}">
							<input type="radio" name="networkGroupSelect" value="${pf.id}" checked="checked"/>
						</c:when>
						<c:otherwise>
							<input type="radio" name="networkGroupSelect" value="${pf.id}" />
						</c:otherwise>
					</c:choose>
					</td>
				</c:forEach>
			</tr>
			
			<tr>
				<td colspan="${fn:length(performanceLevels)}">
					<p class="infoLabel">Select an option above to change the group.</p>
				</td>
			</tr>
		</table>

		<input type="hidden" id="vendorId" name="vendorId"
			value="${ProviderInfo.providerPublicInfo.vendorId}" />
		<input type="hidden" id="vendorResourceId" name="vendorResourceId"
			value="${ProviderInfo.providerPublicInfo.resourceId}" />
		<input type="hidden" id="networkId" name="networkId"
			value="${network.networkId}" />


		<label>
			Comments <span class="req">*</span>
		</label>
		<textarea rows="4" cols="20" class="addNoteArea" name="comments"></textarea>
		<div id="buttons">
			<input type="button" class="action" value="Submit"
				onclick="submitGroupChanges(this);" />
			<br />
			<a href="#" class="cancelLink" onclick="return false;">Cancel</a>
		</div>
		<br style="clear:both;"/>
		<p class="characterCount infoLabel">
			250 characters remaining
		</p>

	</div>
	<br style="clear: both;" />
</form>