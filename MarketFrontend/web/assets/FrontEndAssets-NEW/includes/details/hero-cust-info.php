<header class="details-hero">
	<?php if ( $leadStatus == '6' ) { include 'includes/details/stale-info.php'; } ?>
    <div class="col-sm-8">
		<h1>Bob Loblaw</h1>
		              
		<!-- hidden on mobile screens -->
		<div class="cust-info hidden-xs">
		<p>
		  <?php if ( $leadStatus != '1' ) { echo '1234 Cornball Blvd<br>'; } ?>
		  Sudden Valley, CA 90645<br>
		  847-434-9986<br>
		  <a href="mailto:bob@bobloblawslawblog.com">bob@bobloblawslawblog.com</a>
		</p>
		</div><!--/ .cust-info -->
	</div><!--/ .col-sm-8 -->