/**
 * 
 */

 
 function RowDateMousedown(pNamespace, pMatNo, pDate, pSource, pEvent)
{
	if(pEvent == "click")
	{	
		if (pSource == "TD")
		{
			resetCellsRows(pNamespace);
			var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelection");
			if(pDate == field.value)
			{
				// do nothing - unselect
				var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelection");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelectionObject");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "SelFieldName");
				field.value = "";
			}
			else
			{
				color = "#FFE0CB";
				var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
				field.value = pDate;
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
				field.value = pMatNo;
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelection");
				field.value = pDate;
				//field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelectionObject");
				//field.value = pSelectedObject;
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "SelFieldName");
				field.value = "1";
				renderColorOnClick(pMatNo, pDate);
			}
		}
		if (pSource == "TR")
		{
			resetCellsRows(pNamespace);
			var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelection");
			if(pMatNo == field.value)
			{
				// do nothing - unselect
				var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelection");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelectionObject");
				field.value = "";
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "SelFieldName");
				field.value = "";
				
			}
			else
			{
				color = "#FFE0CB";
				var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
				field.value = pDate;
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
				field.value = pMatNo;
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelection");
				field.value = pMatNo;
				//field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "isLastSelectionObject");
				//field.value = pSelectedObject;
				field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "SelFieldName");
				field.value = "1";
				renderColorOnClick(pMatNo, pDate);
			}
		}
	}
	else if(pEvent == "over")
	{	
		renderColor(pNamespace, pMatNo + "," + pDate, pEvent, pSource);
	}
	else if(pEvent == "out")
	{
		renderColor(pNamespace, pMatNo + "," + pDate, pEvent, pSource);
	}
}

function resetCellsRows(pNamespace)
{
	var color = "#FFFFFF";
	var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
	var tmpMatNo = field.value;
	if(tmpMatNo != "")
	{
		if(tmpMatNo.indexOf(',') > 0)
		{
			while (tmpMatNo.indexOf(',') > 0)
			{
				var tmpCell = document.getElementById(tmpMatNo.substr(0,tmpMatNo.indexOf(',')));
				if (tmpCell) 
				{	
					var theDateRow = tmpCell.cells;
					for (var c = 0; c < theDateRow.length; c++) 
					{
				        theDateRow[c].style.backgroundColor = color;
				    } 
				} 
				else 
				{
					//alert("datacell undefined");
				}
				tmpMatNo = tmpMatNo.substr(tmpMatNo.indexOf(',') + 1, tmpMatNo.len);
			}
		}
		var theDateCell = document.getElementById(tmpMatNo);
		if (theDateCell) 
		{			
			var theDateRow = theDateCell.cells;
			for (c = 0; c < theDateRow.length; c++) 
			{
		        theDateRow[c].style.backgroundColor = color;
		    } 
		} else {
			//alert("datacell undefined");
		}
	}
	field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
	var tmpCellDate = field.value;
	if(tmpCellDate != "")
	{	
		if(tmpCellDate.indexOf(',') > 0)
		{
			while (tmpCellDate.indexOf(',') > 0)
			{
				var tmpCell = document.getElementById(tmpCellDate.substr(0,tmpCellDate.indexOf(',')));
				if (tmpCell) 
				{	
					tmpCell.style.backgroundColor = color;
				} 
				else 
				{
					//alert("datacell undefined");
				}
				tmpCellDate = tmpCellDate.substr(tmpCellDate.indexOf(',') + 1, tmpCellDate.len);
			} 
		}
		
		var tmpCell = document.getElementById(tmpCellDate);
		if (tmpCell) 
		{
			tmpCell.style.backgroundColor=color;
		} 
		else 
		{
			//alert("datacell undefined");
		}
	}
	field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
	field.value = "";
	field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
	field.value = "";
}

function renderColorOnClick(pMatNo, pDate)
{
	var color = "#FFE0CB"; // hellrot
	
	// select the material rows
	if(pMatNo.indexOf(',') > 0)
	{
		while (pMatNo.indexOf(',') > 0)
		{
		
			var tmpCell = document.getElementById(pMatNo.substr(0,pMatNo.indexOf(',')));
	
			if (tmpCell) 
			{	
				var theDateRow = tmpCell.cells;
				for (var c = 0; c < theDateRow.length; c++) 
				{
			        theDateRow[c].style.backgroundColor = color;
	
			    } 
			} 
			else 
			{
				//alert("datacell undefined");
			}
			pMatNo = pMatNo.substr(pMatNo.indexOf(',') + 1, pMatNo.len);
		} 
	}
	
	var tmpCell = document.getElementById(pMatNo);
	if (tmpCell) 
	{	
		var theDateRow = tmpCell.cells;
		for (c = 0; c < theDateRow.length; c++) 
		{
	        theDateRow[c].style.backgroundColor = color;
	    } 
	} 
	else 
	{
		//alert("datacell undefined");
	}

	// select the date cell
	if(pDate.indexOf(',') > 0)
	{
		while (pDate.indexOf(',') > 0)
		{
			var tmpCell = document.getElementById(pDate.substr(0,pDate.indexOf(',')));
			if (tmpCell) 
			{	
				tmpCell.style.backgroundColor = color;
			} 
			else 
			{
				//alert("datacell undefined");
			}
			pDate = pDate.substr(pDate.indexOf(',') + 1, pDate.len);
		} 
	}
	var tmpCell = document.getElementById(pDate);
	if (tmpCell) 
	{	
		tmpCell.style.backgroundColor = color;
	} 
	else 
	{
		//alert("datacell undefined");
	}
}

function highlightCell(pNamespace, pEvent, pCellId, pSource){
	if(pEvent == "over")
	{
		renderColorCellRow(pNamespace, pCellId, "#E0C0A0", pSource);
	} 
	else if(pEvent == "out")
	{
		renderColorCellRow(pNamespace, pCellId, "#FFE0CB", pSource);
	} 
	else 
	{
		alert("unknown event: " + pEvent);
	}
}

function highlightCellWithoutClick(pNamespace, pEvent, pCellId, pSource){
	if(pEvent == "over"){

		renderColorCellRow(pNamespace, pCellId, "#BBE3E3", pSource);
	} else if(pEvent == "out"){

		renderColorCellRow(pNamespace, pCellId, "#FFFFFF", pSource);
	} else {
		alert("unknown event: " + pEvent);
	}
}

function renderColor(pNamespace, bHighlightIdListStr, event, source)
{	
	var bSelectedIdListStr;
	var field = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedCellDate");
	var field2 = eval("document.forms[\"" + pNamespace + "formNameDown\"]." + pNamespace + "selectedRowMatNo");
	bSelectedIdListStr = field.value + "," + field2.value;

	var bSelectedIdList = new Array();
	var commaPos = bSelectedIdListStr.indexOf(',');
	
	while (commaPos >= 0) {
		bSelectedIdList[bSelectedIdListStr.substr(0,commaPos)] = "1";
		bSelectedIdListStr = bSelectedIdListStr.substr(commaPos+1, bSelectedIdListStr.len);
		commaPos = bSelectedIdListStr.indexOf(',');
	}
	bSelectedIdList[bSelectedIdListStr] = "1";
	
	commaPos = bHighlightIdListStr.indexOf(','); 
	while (commaPos >= 0)	
	{
		if (bSelectedIdList[pNamespace + bHighlightIdListStr.substr(0,commaPos)] == "1") 
		{
			highlightCell(pNamespace, event, bHighlightIdListStr.substr(0,commaPos), source);		
		}
		else
		{	
			highlightCellWithoutClick(pNamespace, event, bHighlightIdListStr.substr(0,commaPos), source);
		}
		bHighlightIdListStr = bHighlightIdListStr.substr(commaPos+1, bHighlightIdListStr.len);
		commaPos = bHighlightIdListStr.indexOf(',');
	}
	
	if (bSelectedIdList[pNamespace + bHighlightIdListStr] == "1") 
	{
		highlightCell(pNamespace, event, bHighlightIdListStr, source);
	}
	else
	{	
		highlightCellWithoutClick(pNamespace, event, bHighlightIdListStr, source);
	}
}	

function renderColorCellRow(pNamespace, id, color, source)
{
	var theDateCell = document.getElementById(pNamespace + id);
	if (theDateCell) 
	{
		if(id.substr(0,4) == "CalD")
		{
			theDateCell.style.backgroundColor = color;
		}
		if(id.substr(0,4) == "CalM")
		{		
			var theDateRow = theDateCell.cells;
			for (var c = 0; c < theDateRow.length; c++) 
			{
		        theDateRow[c].style.backgroundColor = color;
		    } 
		}
	} else {
		//alert("datacell undefined");
	}
	
}