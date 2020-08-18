<?php 

include 'templates/header.php'; 
include 'includes/details/hero-cust-info.php';            
  
$leadStatus = '5';
$leadStatusText = 'Cancelled';

include 'includes/details/hero-secondary-info.php';

include 'includes/details/mobile-cust-info.php';

include 'includes/details/lead-info.php';

$scheduleHeading = 'Preferred Start Date';

include 'includes/details/schedule.php';

include 'includes/details/utility-tabs.php';

include 'includes/details/utility-content-header.php';
  $historyScheduled = '
    <tr>
      <td><time datetime="2013-11-21T13:37:17">12/02/13 10:33 AM</time></td>
      <td>Status Changed: <span class="label leadStatus-5"><i class="icon-leadStatus-5"></i> Cancelled</span> Work no longer needed</td>
      <td>Bob F.</td>
    </tr>
  ';
  include 'includes/details/utility-history.php';
  include 'includes/details/utility-notes.php';
  include 'includes/details/utility-attachments.php';
include 'includes/details/utility-content-footer.php';

include 'templates/footer.php';

 ?>