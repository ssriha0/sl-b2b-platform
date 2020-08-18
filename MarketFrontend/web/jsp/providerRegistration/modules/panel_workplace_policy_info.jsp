<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<div class="darkGrayModuleHdr">
	Workplace Policy Information
</div>
<div class="grayModuleContent mainWellContent clearfix">
	<table cellpadding="0" cellspacing="0" width="679">
		<tr>
			<td width="340">

				<p>
					<label>
						<b>Have you implemented a drug testing policy?</b>
					</label>
					<br />
					<c:choose>
					<c:when test="%{companyProfileDto.conductDrugTest ==1}">  
						       		Yes
						       		</c:when>
					<c:otherwise>
						    		No
								</c:otherwise>
					</c:choose>
				</p>

				<p>
					<label>
						<b>Do you have written policies promoting a lawful and ethical
							work environment?</b>
					</label>
					<br />
					<c:choose>
					<c:when test="%{companyProfileDto.hasEthicsPolicy == 1}">  
					       		Yes
					       		</c:when>
					<c:when test="%{companyProfileDto.hasEthicsPolicy ==0}">
					    		No
							</c:when>
					</c:choose>
				</p>
				<p>
					<label>
						<b>Do you require your employees to supply proof of
							citizenship?</b>
					</label>
					<br />
					<c:choose> 
					<c:when test="%{companyProfileDto.requireUsDoc ==1}">  
						       		Yes
						       		</c:when>
					<c:otherwise>
						    		No
								</c:otherwise>
					</c:choose>
				</p>


				<p>
					<label>
						<b>Do you require your crews to wear badges?</b>
					</label>
					<br />
					<!-- error filed highlighting logic -->
					<c:choose> 
					<c:when test="%{companyProfileDto.requireBadge== 1}">  
				       		Yes
				       	</c:when>
					<c:otherwise>
				    		No
						</c:otherwise>
					</c:choose>
				</p>

			</td>

			<td width="339">

				<c:choose> 
				<c:when test="%{companyProfileDto.conductDrugTest == 0}">
					<p>
						<label>
							<b>Would you consider implementing a drug testing policy?</b>
						</label>
						<br />
						<c:choose>
						<c:when test="%{companyProfileDto.considerDrugTest == 1}">  
		       		Yes
		       		</c:when>
						<c:otherwise>
		    		No
				</c:otherwise>
				</c:choose>
					</p>
				</c:when>
				<c:otherwise>
					<p>
						&nbsp;
					</p>
				</c:otherwise>
				</c:choose>

				<c:choose>
				<c:when test="%{companyProfileDto.hasEthicsPolicy == 0}">
					<p>
						<label>
							<b>Would you consider implementing these policies?</b>
						</label>
						<br />
						<c:choose>
						<c:when test="%{companyProfileDto.considerEthicPolicy == 1}">  
		       		Yes
		   		</c:when>
						<c:otherwise>
		    		No
				</c:otherwise>
				</c:choose>
					</p>
				</c:when>
				<c:otherwise>
					<p>
						&nbsp;
					</p>
				</c:otherwise>
				</c:choose>
				
				<c:choose>
				<c:when test="%{companyProfileDto.requireUsDoc == 0}">
					<p>
						<label>
							<b>Would you consider implementing this policy?</b>
						</label>
						<br />
						<c:choose>
						<c:when test="%{companyProfileDto.considerImplPolicy == 1}">  
       			Yes
       			</c:when>
						<c:otherwise>
		    		No
				</c:otherwise>
				</c:choose>
					</p>
				</c:when>
				<c:otherwise>
					<p>
						&nbsp;
					</p>
				</c:otherwise>
				</c:choose>
				
				<c:choose>
				<c:when test="%{companyProfileDto.requireBadge == 0}">
					<p>
						<label>
							<b>Would you consider implementing this policy?</b>
						</label>
						<br />
						<c:choose>
						<c:when test="%{companyProfileDto.considerBadge ==1}">  
	       						Yes
	       					</c:when>
						<c:otherwise>
	    						No
							</c:otherwise>
						</c:choose>
					</p>
				</c:when>
				<c:otherwise>
					<p>
						&nbsp;
					</p>
				</c:otherwise>
				</c:choose>
			</td>
		</tr>

	</table>

</div>

