$(function(){
	$.tablesorter.addParser({
		id: "moment",
		is: function(s) {
			return false;
		},
		format: function(s, table) {
            return moment(s, "MM-DD-YYYY");
		},
		format: function(s, table, cell, cellIndex) {
			if (s) {
				var c = table.config, ci = c.headerList[cellIndex],
				format = ci.dateFormat || $.tablesorter.getData( ci, c.headers[cellIndex], 'dateFormat') || c.dateFormat;
				s = s.replace(/\s+/g," ").replace(/[\-.,]/g, "/"); // escaped - because JSHint in Firefox was showing it as an error
				return moment(s, format);
			}
			return s;
		},
		type: "numeric"
	});
	
	//tsfilters can be used to configure special filters like select boxes
	if(typeof tswidgets === "undefined"){
		tswidgets=['uitheme','zebra', 'filter'];
	}
	
	  // Extend the themes to change any of the default class names ** NEW **
	  $.extend($.tablesorter.themes.jui, {
	    // change default jQuery uitheme icons - find the full list of icons here: http://jqueryui.com/themeroller/ (hover over them for their name)
	    table      : 'ui-widget ui-widget-content ui-corner-all', // table classes
	    header     : 'ui-widget-header ui-corner-all ui-state-default', // header classes
	    footerRow  : '',
	    footerCells: '',
	    icons      : 'ui-icon', // icon class added to the <i> in the header
	    sortNone   : 'ui-icon-carat-2-n-s',
	    sortAsc    : 'ui-icon-carat-1-n',
	    sortDesc   : 'ui-icon-carat-1-s',
	    active     : 'ui-state-active', // applied when column is sorted
	    hover      : 'ui-state-hover',  // hover class
	    filterRow  : '',
	    even       : 'ui-widget-content', // odd row zebra striping
	    odd        : 'ui-state-default inheritcolor'   // even row zebra striping
	  });

  // define pager options
  var pagerOptions = {
    // target the pager markup - see the HTML block below
    container: $(".pager"),
    // output string - default is '{page}/{totalPages}'; possible variables: {page}, {totalPages}, {startRow}, {endRow} and {totalRows}
    output: '{startRow} - {endRow} / {filteredRows} ({totalRows})',
    // if true, the table will remain the same height no matter how many records are displayed. The space is made up by an empty
    // table row set to a height to compensate; default is false
    fixedHeight: true,
    // remove rows from the table to speed up the sort of large tables.
    // setting this to false, only hides the non-visible rows; needed if you plan to add/remove rows with the pager enabled.
    removeRows: false,
    // go to page selector - select dropdown that sets the current page
    cssGoto:   '.gotoPage',
    
 // css class names of pager arrows
    cssNext        : '.next',  // next page arrow
    cssPrev        : '.prev',  // previous page arrow
    cssFirst       : '.first', // go to first page arrow
    cssLast        : '.last',  // go to last page arrow
    cssPageDisplay : '.pagedisplay', // location of where the "output" is displayed
    cssPageSize    : '.pagesize', // page size selector - select dropdown that sets the "size" option
    cssErrorRow    : 'tablesorter-errorRow', // error information row

    // class added to arrows when at the extremes (i.e. prev/first arrows are "disabled" when on the first page)
    cssDisabled    : 'disabled' // Note there is no period "." in front of this class name
    
  };

  // Initialize tablesorter
  // ***********************
  $("#doc41table")
    .tablesorter({
      theme: 'jui',
      headerTemplate : '{content} {icon}', // new in v2.7. Needed to add the bootstrap icon!
      widthFixed: true,
      widgets: tswidgets
    })

    // initialize the pager plugin
    // ****************************
    .tablesorterPager(pagerOptions);
});
