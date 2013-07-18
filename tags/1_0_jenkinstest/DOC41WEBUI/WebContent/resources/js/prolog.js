function sendGet(target, params) {
	var CONTEXT_PATH = readCookie("IV_JCT") + ctx + "/";
	window.location.href= CONTEXT_PATH + target + "?" + params;
}

function sendPost(target, params) {
	var paramArray = new Array();
	parseParams(params, paramArray);
	sendPost2Target(target, paramArray);
}

function sendPost2Target(path, params) {
    var form = document.createElement("form");

    //move the submit function to another variable
    //so that it doesn't get overwritten
    form._submit_function_ = form.submit;

    
  	//var CONTEXT_PATH = readCookie("IV_JCT")+"<%=response.encodeURL(request.getContextPath()+ "/")%>";
	//var CONTEXT_PATH = "<%=response.encodeURL(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath() + "/")%>";
	
    //form.setAttribute("action", CONTEXT_PATH + path);
    form.setAttribute("action", path);
    form.setAttribute("method", "POST");

    for(var key in params) {
    	if (params[key] instanceof Array){
    		for (var value in params[key]) {
        		var hiddenField = document.createElement("input");
        		hiddenField.setAttribute("type", "hidden");
        		hiddenField.setAttribute("name", key);
        		hiddenField.setAttribute("value", params[key][value]);			
        		form.appendChild(hiddenField);       			
    		}
    	}
    	else {
        		var hiddenField = document.createElement("input");
        		hiddenField.setAttribute("type", "hidden");
        		hiddenField.setAttribute("name", key);
        		hiddenField.setAttribute("value", params[key]);			
		//alert(key);
		//alert(params[key]);
        		form.appendChild(hiddenField);
    		}
    }

    document.body.appendChild(form);
    form._submit_function_(); //call the renamed function
    document.body.removeChild(form);
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	
	for(var i=0;i < ca.length;i++) {
	    var c = ca[i];
	    while (c.charAt(0)==' ') c = c.substring(1,c.length);
	    if (c.indexOf(nameEQ) == 0) return decodeURIComponent(c.substring(nameEQ.length,c.length));
	}
	
	return "";
}

function sendGetAfterPopup(infoText, target, params) {
	alert(infoText);
	sendGet(target, params);
}

function sendGetAfterCheck(infoText, target, params) {
	var confirmed = confirm(infoText);
	if (confirmed == true) {
		sendGet(target, params);
	}
}

function sendGetAfterCheckAndDisable(infoText, target, params, button) {
	var confirmed = confirm(infoText);
	if (confirmed == true) {
		button.disabled = true;
		sendGet(target, params);
	}
}

function sendPostAfterCheck(infoText, target, params) 
{
	var confirmed = confirm(infoText);
	if (confirmed == true) {
		sendPost(target, params);
	}
}

function sendPostAfterCheckAndDisable(infoText, target, params, button)
{
	var confirmed = confirm(infoText);
	if (confirmed == true) {
		button.disabled = true;
		sendPost(target, params);
	}
}
	
function hide(id) {
	var elem = document.getElementById(id);
	if (elem != null) {
		elem.style.display = 'none';
	}
}

function show(id) {
	var elem = document.getElementById(id);
	if (elem != null) {
		elem.style.display = 'block';
	}
}

function showRow(id) {
	var elem = document.getElementById(id);
	if (elem != null) {
		elem.style.display = 'table-row';
	}
}

function toggleVisibility(id) {
	var elem = document.getElementById(id);
	if (elem != null) {
		if (elem.style.display == 'none') {
			elem.style.display = 'block';
		} else {
			elem.style.display = 'none';
		}
	}
}

function toggleRowVisibility(id) {
	if ($("#"+id).is(':visible')) {
		$("#"+id).hide();
	} else {
		$("#"+id).show();
	}
}


function parseParams(params, paramArray) {
	var endKey = params.indexOf('=');
	
	if (endKey != -1) {
		var key = params.substring(0, endKey);
		var endValue = params.indexOf('&');
		var value;
		
		if (endValue != -1) {
			
			value= params.substring(endKey+1, endValue);
			parseParams(params.substring(endValue+1), paramArray);
		} else {
			value= params.substring(endKey+1);
		}
		if (paramArray[key]) {
			paramArray[key]=[value].concat(paramArray[key]);
		}
		else {
			paramArray[key]=value;
		}
//		alert(paramArray[key] instanceof Array);
	}
}

function strPad(i,l,s) {
	var o = i.toString();
	if (!s) { s = '0'; }
	while (o.length < l) {
		o = s + o;
	}
	return o;
}