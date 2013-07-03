var $allPopups ={};

$(document).ready(function() {

$('a#openPopupLink').each(function() {
var $link = $(this);
var $href=$link.attr('href');
var $target = $link.attr('target');
        
var $dialog = $('<div id="'+$target+'">&nbsp;</div>')
.dialog({
autoOpen: false,

width: 700,
height: 470,
open: function(event,ui){
	$(this).load($href);
},
close: function(event, ui) {
			$(this).html('&nbsp;');
		}
});

$link.click(function() {
	$dialog.dialog('open');
	return false;
});

$allPopups[$target] = $dialog;

});

});

