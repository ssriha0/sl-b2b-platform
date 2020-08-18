<%@ taglib prefix="s" uri="/struts-tags" %>

<s:include value="/jsp/header.jsp" />

<div class="directions">
<h1>Step 3/3: View output</h1>
<p>The full batch results have been output to <strong><s:property value="fullPath" /></strong>.</p>
<s:include value="/jsp/error.jsp" />
</div>

<s:if test="transactionMap.size() > 0">

<h2>Transaction Details</h2>
<s:iterator value="transactionMap.entrySet()">
<h3><s:property value="%{key}" /></h3>
<s:if test="%{value.size() > 0}">
<table class="transactions">
	<thead>
		<tr>
			<th>ID</th>
			<th>Type</th>
			<th>External Order #</th>
			<th>Status</th>
			<th>Exception</th>
			<th>SL Order ID</th>
		</tr>
	</thead>
	<tbody>
	<s:iterator value="%{value}" status="rowstatus">
	<s:if test="#rowstatus.odd == true">
	<tr class="odd">
	</s:if><s:else>
	<tr>
	</s:else>
		<th><s:property value="transactionId" /></th>
		<td><s:property value="typeName" /></td>
		<td><s:property value="externalOrderNumber" /></td>
		<td><s:property value="statusName" /></td>
		<td>
		<s:if test="exception != null">
		<s:property value="exception" />
		</s:if><s:else>
		<em>(No exception)</em>
		</s:else>
		</td>
		<td><s:if test="serviceLiveOrderId != null">
		<s:property value="serviceLiveOrderId" />
		</s:if><s:else>
		<em>(No ID)</em>
		</s:else></td>
	</tr>
	</s:iterator>
	</tbody>
</table>
</s:if>
<s:else>
(Error: None of the transactions from this file were found in the database.)
</s:else>
</s:iterator>

</s:if>

<p>&nbsp;</p>
<p><a href="selectFiles.action">Start Over</a></p>

<s:include value="/jsp/footer.jsp" />