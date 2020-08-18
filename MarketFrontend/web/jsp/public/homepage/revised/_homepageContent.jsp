<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div id="hpContent">
    <s:if test="hasActionErrors()">
		<div style="margin: 10px 0pt;" id="actionError" class="errorBox clearfix">
		<s:actionerror />
		</div>
	</s:if>
    <div id="hpHero"></div><%-- hpHero --%>
	<div id="hpEnterZip" class="clearfix">
        <form id="homepage_submitZip" name="homepage_submitZip" action="" method="post" onSubmit="setNewZipFields('xxxx', '200', '', '', '', '1'); return false;">
          <div id="hpEnterZipFieldWrapper">
            <input type="text" class="hpEnterZipZip" id="zipCode1" name="zipCode1" value="" maxlength="5" />
            <img class="hpEnterZipSibmit" id="hpEnterZipSibmit" src="${staticContextPath}/images/homepage/search-simple.png" 
            onclick="setNewZipFields('xxxx', '200', '', '', '', '1')" />
          </div>
        </form>
      </div>
      <div id="hpSteps">
        <div id="hpStepsInside">
        <ol>
            <li class="stepNum1">
              <h2>Find providers</h2>
			<div>Choose <a href="#" onClick="setDefineTermsCandidatesFields('xxxx', '200', '', '', '', '1')" title="Read what we mean by &ldquo;candidates.&rdquo;" class="modalDefineCandidates">candidates</a> from our <a href="#" onClick="setDefinePreScreenedFields('xxxx', '200', '', '', '', '1')" title="Read what we mean by &ldquo;pre-screened.&rdquo;" class="modalDefinePreScreened">pre-screened</a> service providers.</div>
            </li>
            <li class="stepNum2">
              <h2>Set your price</h2>
              <div><a href="#" onClick="setDefineTermsFundPayFields('xxxx', '200', '', '', '', '1')" title="Read about our &ldquo;fund&rdquo; and &ldquo;pay&rdquo; system." class="modalDefineFundPay">Fund</a> your account.<br /> 
				<a href="#" onClick="setDefineTermsFundPayFields('xxxx', '200', '', '', '', '1')" title="Read about our &ldquo;fund&rdquo; and &ldquo;pay&rdquo; system." class="modalDefineFundPay">Pay</a> when it's done.</div>
            </li>
            <li class="stepNum3">
              <h2>Providers compete</h2>
              <div>The first <a href="#" onClick="setDefineTermsCandidatesFields('xxxx', '200', '', '', '', '1')" title="Read what we mean by &ldquo;candidate.&rdquo;" class="modalDefineCandidates">candidate</a> to accept your price wins your project.</div>
              <div class="learnmore"><a href="http://servicelive.com" title="">Learn more</a></div>
            </li>
          </ol>
          <div style="clear:both;"></div>
        </div>
      </div>
      <div id="stepsShadow"></div>
     <c:import url="_homepageCategories.jsp" />
</div>