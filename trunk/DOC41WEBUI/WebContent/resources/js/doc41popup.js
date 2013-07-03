$(document).ready(function() {

//the ID is used in the anchor tag on the page where the dialog will be loaded, which can be used on multiple links for multiple external files
$('a#openPopupLink').each(function() {
var $link = $(this);
        
var $dialog = $('<div id="'+$link.attr('target')+'"></div>')
.dialog({
autoOpen: false,

width: 700,
height: 470,
close: function(event, ui) {
			$(this).html('<div id="'+$link.attr('target')+'"></div>');
		}
});

$link.click(function() {
	$dialog.load($link.attr('href'));
	$dialog.dialog('open');
	return false;
});

});

});

