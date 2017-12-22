var $allPopups ={};

$(document).ready(function() {

	$('a#openPopupLink').each(function() {
		var $link = $(this);
		var $href=$link.attr('href');
		var $target = $link.attr('target');
		var $title = $link.text();
		if( $title === ""){
			$title='';
		}
		        
		var $dialog = $('<div id="'+$target+'" title="'+$title+'">&nbsp;</div>')
		.dialog({
			autoOpen: false,
			
			width: 800,
			height: 470,
			modal: true,
			open: function(event,ui){
				var url = $href;
				if (typeof(popupAppendFunction) == "function") {
					url = url + popupAppendFunction();
				}
				$(this).load(url);
				$(this).parents('.ui-dialog').attr('tabindex', -1)[0].focus();
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

