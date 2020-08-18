		  <section class="schedule col-md-5">
            <h2><?php echo $scheduleHeading; ?></h2>
            <p>Tuesday Dec 3, 2013 <br/> 8:00 AM-12:00 PM <?php if (isset($reScheduleLink)) { echo $reScheduleLink; } ?></p>
            <?php if (isset($assignedPro)) { include 'includes/details/assigned-pro.php'; } ?>
            
			<?php include 'includes/details/widget-schedule.php'; ?>

            <?php if ( $leadStatus == '4' ) { include 'includes/details/completed-info.php'; } elseif ( $leadStatus == '5' ) { include 'includes/details/cancelled-info.php'; } ?>
            
          </section> <!-- /.schedule -->

        </div><!-- /.row -->

        <!-- Cancel Order Widget -->
        <?php if ( $leadStatus != '4' && $leadStatus != '5' ){ include 'includes/details/widget-cancel.php'; } ?>