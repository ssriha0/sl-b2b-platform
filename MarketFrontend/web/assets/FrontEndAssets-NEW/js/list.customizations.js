$(function(){
  
  // List.js functions; search, filter, etc.
  //==================================================================================

  // initializes list with emptyView plugin
  var options = {
    valueNames: [ 
      'leadStatus',
      'leadid',
      'skill',
      'projectType',
      'urgency',
      'name',
      'phone',
      'city',
      'zip'
    ],
    plugins: [
        [ 'emptyView', {
            emptyViewId: "emptyView"
        }]
    ]
  };

  var featureList = new List('leads-list', options);

  // Filters!
  // Checks value of list items & filters them
  function filterItems(tabStatus) {
    featureList.filter(function(item) {
      var itemStatus = item.values().leadStatus.toLowerCase();
      if (itemStatus == tabStatus) {
        return true;
      } else {
        return false;
      }
    });
  }

  // When any of the filter tabs are clicked, initiate filterItems()
 $('.filter label').click(function() {
    // Get that <label>'s text (which is the tabStatus)
    thisTab = $(this).find('span').text().toLowerCase();
    // Reset the filter if All was clicked
    if (thisTab == 'all') {
      featureList.filter();
    }
    else {
      // Filter on the corresponding list items
      filterItems(thisTab);
    }
  });

  //filter on page load from external link
  // ex: leads-dashboard.php#scheduled (MUST BE lower CASE)
  var statusOnload =  (location.hash).replace('#','');
  // only run if there IS a # as part of the link
  if ( typeof statusOnload !== 'undefined' && statusOnload) {
    filterItems(statusOnload);
    // remove all .active classes
    $('.filter label').removeClass('active');
    // add .active class to the filter that's applied
    $( '.filter label span' ).filter(function() {
      return $( this ).text().toLowerCase() === statusOnload;
    })
    .parent('label').addClass('active');
  }
  

  // Collapsed search bar 
  //==================================================================================

  // toggles icon on search collapse
  $('header .glyphicon-search.visible-xs').click(function() {
    $(this).toggleClass('glyphicon-chevron-down');
  });

  // BUG - Focus brought to search bar on expand -- iOS Bug makes this not work :(
  $('#search-wrap').on('shown.bs.collapse', function(){
    $('input',this).focus();
  });

  /*
  // Swaps search icon for X close icon
  var searchInput = $('#search-wrap .search');
  var searchIcon = searchInput.next();
  function icon_swap(){
    if (searchInput.val() == '') {
      searchIcon.removeClass('glyphicon-remove-circle');
      featureList.filter();
    }
    else {
      searchIcon.addClass('glyphicon-remove-circle');
    }
  }
  
  // when typing in the search field
  searchInput.keyup(icon_swap);
  // when clicking the X icon
  searchIcon.click(function() {
    if (searchInput.val() != '') {
      searchInput.val('');
      icon_swap();
      // BUG - LIST IS NOT UPDATED WHEN YOU CLEAR THE SEARCH FIELD.
      //featureList.filter();
    }
  });*/
  /*  Testing the above bug, this works outside the searchIcon click event, but not within. Why??
      //filterItems('new');
      //alert('about to show all?');
      //featureList.filter();
  */


  // Mobile LIs - Make each li a link target (but also let regular <a> links do their thing)
  //==================================================================================

  // check width of screen (Long term: use Modernizr to check for touch capability instead of screen width)
  var viewportWidth = $(window).width();
  //var viewportHeight = $(window).height();

  var clickSrc = function dashboardLinks(event) {
    // If the target was a link, 
    if (event.target.nodeName == 'A') {
      //stop the event from bubbling up the DOM
      event.stopPropagation();
    }
    // If target was anything else
    else {
      // Get the Lead ID
      var leadId = $(this).attr('data-leadid');
      // load the lead details page
      window.location.href = '${contextPath}/leadsManagementController_viewLeadDetails.action?leadId=' + leadId;
    }
  }

  // On page load set the links
  if (viewportWidth < '900') {
    $('#leads-list li.list-group-item').click(clickSrc);
  }

  // Listen for orientation changes, Mobile/Tablets
  window.addEventListener("orientationchange", function() {
    $('#leads-list li.list-group-item').click(clickSrc);
  }, false);




});

