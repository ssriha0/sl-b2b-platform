<%@ taglib prefix="s" uri="/struts-tags" %>

<s:include value="/jsp/header.jsp" />

<div class="directions">
<h1>Step 2/3: Choose a batch name</h1>
<p>The test files have been sent to the ESB. Choose a name for your batch, and continue after the files have been processed (~10 seconds).</p>
<s:include value="/jsp/error.jsp" />
</div>

<s:form action="outputReport" theme="simple">

<s:iterator value="hsrFiles" status="status">
	<s:hidden name="hsrFileName" value="%{top}" />
	<s:hidden name="hsrOrderNum" value="%{hsrOrders[#status.index]}" />
</s:iterator>
<s:iterator value="omsFiles" status="status">
	<s:hidden name="omsFileName" value="%{top}" />
	<s:hidden name="omsOrderNum" value="%{omsOrders[#status.index]}" />
</s:iterator>
<s:iterator value="assurantFiles" status="status">
	<s:hidden name="assurantFileName" value="%{top}" />
	<s:hidden name="assurantOrderNum" value="%{assurantOrders[#status.index]}" />
</s:iterator>

<h2>How should the batch be named?</h2>
<input type="radio" name="reportNameType" value="datetime" id="reportNameDatetime" />
<label for="reportNameDatetime">Current time stamp</label><br />
<input type="radio" name="reportNameType" value="custom" id="reportNameCustom"/>
<label for="reportNameCustom">Custom name:</label> <s:textfield name="customName" />

<p><s:submit value="Continue" /></p>

</s:form>

<p>&nbsp;</p><hr /><p>&nbsp;</p>

<p>The following test files were sent:</p>

<s:if test="omsFiles.size() > 0">
<h3>OMS Files</h3>
<ul>
<s:iterator value="omsList">
	<li><s:property /></li>
</s:iterator>
</ul>
</s:if>

<s:if test="hsrFiles.size() > 0">
<h3>HSR Files</h3>
<ul>
<s:iterator value="hsrList">
	<li><s:property /></li>
</s:iterator>
</ul>
</s:if>

<s:if test="assurantFiles.size() > 0">
<h3>Assurant Files</h3>
<ul>
<s:iterator value="assurantList">
	<li><s:property /></li>
</s:iterator>
</ul>
</s:if>

<s:include value="/jsp/footer.jsp" />