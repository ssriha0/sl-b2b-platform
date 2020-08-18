<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<!-- Display Server Side Validation Messages -->


<s:if test="%{errors.size > 0}">
	<div class="errorBox clearfix"
		style="width: 675px; visibility: visible">
		<s:iterator value="errors" status="error">
			<p class="errorMsg">
				&nbsp;&nbsp;&nbsp;&nbsp;${errors[error.index].fieldId} -
				${errors[error.index].msg}
			</p>
		</s:iterator>
	</div>
	<br>
</s:if>


<form id="etmSearch" onsubmit="return false;" >
	<table width="73%">
		<tr>
			<td align="center">
				<table width="97%">
					<tr>
						<td>
							<h3 style="margin-left:12px">
								<strong>Search Service Providers in your area!</strong>
							</h3>
						</td>
					</tr>
					<tr>
						<td>
							<div class="content" style="margin-left:12px;">
								<div class="lightGrayBox">
									<table width="100%" border=0>
										<tr>
											<td width="25%" style="padding-left: 20px">
												<strong>Main Service Category</strong>
												<br>


												<select name="skillTreeMainCat" id="skillTreeMainCat"
													style="width: 190px;" size="1" theme="simple"
													<c:if test="${mainServiceCategoryId != -1 && scopeOfWorkDTO.tasks.size  > 0}">disabled="true"</c:if>>
													<option value="-1">
														-- Select One --
													</option>
												<c:forEach var="skillTreeMainList"
														items="${skillTreeMainCatList}">
														<option value="${skillTreeMainList.nodeId}">
															${skillTreeMainList.nodeName}
														</option>
												</c:forEach>
												</select>

											</td>
											<td width="20%" style="padding-left: 10px">
												<strong>Market Ready?</strong>
												<br>
											<input id="marketReadySelection" 	name="marketReadySelection" 
															type="radio" theme="simple"   onclick="setMarketFlag(this)" value="1"
															  checked="checked"   >Yes
											</input>
											<%--  Commenting as per SLT-2544
											<input id="marketReadySelection" 	name="marketReadySelection" 
															type="radio" theme="simple"  onclick="setMarketFlag(this)" value="0"
															<c:if test="${marketReadySelection == '0'}"> checked="checked"  </c:if> >No
											</input> --%>
															
														
												
																					
											</td>
											<td width="15%" style="padding-left: 10px">
												<strong>ZIP code</strong>
												<br>
																					
													
											<input type="text" name="zipCd" id="zipCd" class="shadowBox grayText" style="width: 100px;" > </input>
													
											</td>
											<td style="padding-left: 15px" >
												<br>
													<input type="image" src="${staticContextPath}/images/common/spacer.gif" width="76px"
													height="20px" class="btnBevel inlineBtn" alt="Search"
													theme="simple"
													style="background-image: url(${staticContextPath}/images/btn/search.gif); "
													onclick="sendETMSearch()"
													 />
											</td>
										</tr>
										<tr>
											<td colspan=4 height=8></td>
										</tr>
									</table>
								</div>
							</div>
						</td>
					</tr>
					<tr>
						<td  >
						 <div style="margin-left: 12px; text-align:left;">
							Below are the providers in your area that match your search. To narrow your results, use the filters on the right or sort by name. Click on provider name to view profile.
							<br>
						</div>
						</td>
					</tr>

				</table>
			</td>
		</tr>

	</table>
</form>






<table width="100%">
	<tr>
		<td align="center">			
			<iframe id="etmResultsIframeID" name="etmResultsIframeID"
				src="${contextPath}/etmSearch_showResultsIframe.action" width="970"
				height="550" FRAMEBORDER=0>
			</iframe>
		</td>
	</tr>
</table>
