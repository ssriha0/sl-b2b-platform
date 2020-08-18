<div class="clearfix tableWrap"
	style="border-left: 0px; border-right: 0px;">
	<table border="0" cellpadding="6" cellspacing="0" class="left doctable">
		<thead>
			<tr>
				<th class="tc bl" style="width: 100px">
					Status
				</th>
				<th>
					Required Documents
				</th>
				<th class="tl" style="width: 100px;">
					&nbsp;
				</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="documents">
				<tr>
					<td class="tc bl">
						<img
							src="${staticContextPath}/images/common/<s:property value='statusIcon' />"
							alt="Incomplete" />
						<br />
						<p class="sm">
							<s:property value="status" />
						</p>
					</td>
					<td>
						<strong> <a href="#"> <s:property value="title" /> </a>
						</strong>
						<br />

						<small>(Microsoft Word File)</small>

						<s:iterator value="actions">
							<div class="comment">
								<strong class="req"><s:property value="action" />:</strong>
								<s:property value="comments" />
							</div>
						</s:iterator>

					</td>
					<td class="tl carr br">
						<input type="submit" class="default button" value="Select">
					</td>
				</tr>
			</s:iterator>

		</tbody>
	</table>
	<div class="documents left">
		<div class="content">
			<h5>
				Upload Documents
			</h5>
			<div class="error">
				Error Message
			</div>
			<p>
				<select name="select" class="select">
					<option>
						Select A Document
					</option>
					<option>
						Hello World Document
					</option>
					<option>
						Some Insurance Form
					</option>
				</select>
			</p>
			<p>
				<input type="file">
			</p>
			<p>
				<input type="submit" class="button action" value="Attach File" />
			</p>
			<p>
				<small>Only files that have not been approved may be
					updated.<br /> Uploading a new file will automatically replace an
					existing one.</small>
			</p>
			<p class="note">
				<small><strong>Accepted File Types:</strong>.jpg | .pdf |
					.doc | .gif | .tiff | .png | .bmp<br /> <strong>Max. file
						size:</strong> 100 MB</small>
			</p>
		</div>
	</div>
</div>
