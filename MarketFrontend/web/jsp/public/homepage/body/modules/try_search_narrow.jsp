<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<div class="modNarrow mktMod trySearch_narrow">
	<div class="content">
		<p>
			Type a keyword or select a category to find service providers in your
			area.
		</p>
		<p>
			<input type="text" class="shadowBox grayText" value="Keyword"
				onfocus="clearTextbox(this)" style="width: 180px;" />
		</p>
		<p class="tinyText paddingBtm">
			Example: "kitchen sink"
		</p>
		<p>
			<select onclick="changeDropdown(this)" style="width: 165px;">
				<option>
					Service Category
				</option>
			</select>
		</p>
		<p>
			<input type="text" class="shadowBox grayText" value="Zip Code"
				style="width: 80px;" />
			<input type="image" img width="76" height="20"
				style="background-image: url(${staticContextPath}/images/btn/search.gif);"
				src="${staticContextPath}/images/common/spacer.gif" class="btnBevel inlineBtn" />
		</p>
		<p>
			<a href="">Advanced search</a>
		</p>
	</div>
</div>
