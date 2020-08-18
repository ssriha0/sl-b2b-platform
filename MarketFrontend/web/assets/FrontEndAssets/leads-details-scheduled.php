<?php 

include 'templates/header.php'; 
include 'includes/details/hero-cust-info.php';            
  
$leadStatus = '3';
$leadStatusText = 'Scheduled';
include 'includes/details/hero-secondary-info.php';

$ctaBtn1 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#assignWidget">Assign Provider</a>';
$ctaBtn2 = '<a role="button" href="#" class="btn btn-default" data-toggle="modal" data-target="#completeWidget">Complete Order</a>';
include 'includes/details/cta-buttons.php';

include 'includes/details/mobile-cust-info.php';

include 'includes/details/lead-info.php';

$scheduleHeading = 'Scheduled Service Date';
$reScheduleLink = '<a href="#" data-toggle="modal" data-target="#scheduleWidget">Reschedule</a>';
include 'includes/details/schedule.php';
include 'includes/details/widget-assign.php';
include 'includes/details/widget-complete.php';

include 'includes/details/utility-tabs.php';

include 'includes/details/utility-content-header.php';
  $historyScheduled = '
    <tr>
      <td><time datetime="2013-11-21T13:37:17">12/02/13 10:33 AM</time></td>
      <td>Status Changed: <span class="label leadStatus-3"><i class="icon-leadStatus-3"></i> Scheduled</span></td>
      <td>Bob F.</td>
    </tr>
  ';
  include 'includes/details/utility-history.php';
  include 'includes/details/utility-notes.php';
  include 'includes/details/utility-attachments.php';
include 'includes/details/utility-content-footer.php';

include 'templates/footer.php';

 ?>