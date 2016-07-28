//Javascript name: My Date Time Picker
//Date created: 16-Nov-2003 23:19
//Scripter: TengYong Ng
//Website: http://www.rainforestnet.com
//Copyright (c) 2003 TengYong Ng
//FileName: DateTimePicker.js
//Version: 0.8
//Contact: contact@rainforestnet.com
// Note: Permission given to use this script in ANY kind of applications if
//       header lines are left unchanged.

//Global variables
var winCal;
var dtToday=new Date();
var Cal;
var docCal;
var MonthName=["January", "February", "March", "April", "May", "June","July", 
	"August", "September", "October", "November", "December"];
var MonatsName=["Januar", "Februar", "Maerz", "April", "Mai", "Juni","Juli", 
	"August", "September", "Oktober", "November", "Dezember"];
var MonthNameChinese=["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", " 十一月", "十二月"];
var WeekDayName=["Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"];	
var WochentagsName=["Sonntag","Montag","Dienstag","Mittwoch","Donnerstag","Freitag","Samstag"];
var WeekDayNameChinese=["主日", "周一", "周二", "周三", "周四", "周五", "周六"];
var DateSeparatorDE=".";//Date Separator
var DateSeparatorEN="-";
var DateSeparatorZH="-";

var exDateTime;//Existing Date and Time

//Configurable parameters
var cnTop="200";//top coordinate of calendar window.
var cnLeft="500";//left coordinate of calendar window
var WindowTitle ="Date Picker";//Date Time Picker title.
var WeekChar=2;//number of character for week day. if 2 then Mo,Tu,We. if 3 then Mon,Tue,Wed.
var CellWidth=20;//Width of day cell.
var TimeMode=24;//default TimeMode value. 12 or 24
var ShowLongMonth=true;//Show long month name in Calendar header. example: "January".
var ShowMonthYear=true;//Show Month and Year in Calendar header.

var MonthYearColor="#009899";//Font Color of Month and Year in Calendar header.
var WeekHeadColor="#009899";//Background Color in Week header.
var SundayColor="#C3EBEB";//Background color of Sunday.
var SaturdayColor="#F2F9FC";//Background color of Saturday.
var WeekDayColor="white";//Background color of weekdays.
var FontColor="#009899";//color of font in Calendar day cell.
var TodayColor="#FF6600";//Background color of today.
var SelDateColor="#CCCCCC";//Backgrond color of selected date in textbox.
var YrSelColor="#009899";//color of font of Year selector.
var ThemeBg="";//Background image of Calendar window.
//end Configurable parameters
//end Global variable

function NewCal(pCtrl,pDisplayFormat)
{ 
	Cal=new Calendar(dtToday, pCtrl, pDisplayFormat);
	
	exDateTime=document.getElementById(pCtrl).value;

	if (exDateTime!="")//Parse Date String
	{
		var strMonth;
		var strDate;
		var strYear;
		var intMonth;
		var YearPattern;

		//parse month
		if (Cal.LocaleValue =="us")	{
			strYear=exDateTime.substring(6,10);
			strMonth=exDateTime.substring(0,2);
			strDate=exDateTime.substring(3,5);
		} else if (Cal.LocaleValue =="zh")	{
			strYear=exDateTime.substring(0,4);
			strMonth=exDateTime.substring(5,7);
			strDate=exDateTime.substring(8, 10);
		} else{
			strYear=exDateTime.substring(6,10);
			strMonth=exDateTime.substring(3,5);
			strDate=exDateTime.substring(0,2);
		}
		
		if (isNaN(strMonth)){
			intMonth=Cal.GetMonthIndex(strMonth);
		}else{
			intMonth=parseInt(strMonth,10)-1;	
		}
		if ((parseInt(intMonth,10)>=0) && (parseInt(intMonth,10)<12))
			Cal.Month=intMonth;
		//end parse month
		
		//parse Date
		if ((parseInt(strDate,10)<=Cal.GetMonDays()) && (parseInt(strDate,10)>=1))
			Cal.Date=strDate;
		//end parse Date
		
		//parse year
		YearPattern=/^\d{4}$/;
		if (YearPattern.test(strYear))
			Cal.Year=parseInt(strYear,10);
		//end parse year
		
	}
	winCal=window.open("about:blank","DatePicker","toolbar=0,status=0,menubar=0,fullscreen=no,width=195,height=245,resizable=0,top="+cnTop+",left="+cnLeft);
	docCal=winCal.document;
	RenderCal();
}

function RenderCal()
{
	var vCalHeader;
	var vCalData;
	var vCalTime;
	var i;
	var j;
	var SelectStr;
	var vDayCount=0;
	var vFirstDay;

	docCal.open();
	docCal.writeln("<html><head><title>"+WindowTitle+"</title>");
	docCal.writeln("<script>var winMain=window.opener;</script>");
	docCal.writeln("</head><body background='"+ThemeBg+"' link="+FontColor+" vlink="+FontColor+"><form name='Calendar'>");

	vCalHeader="<table border=1 cellpadding=1 cellspacing=1 width='100%' align=\"center\" valign=\"top\">\n";
	//Month Selector
	vCalHeader+="<tr>\n<td colspan='7'><table border=0 width='100%' cellpadding=0 cellspacing=0><tr><td align='left'>\n";
	vCalHeader+="<select name=\"MonthSelector\" onChange=\"javascript:winMain.Cal.SwitchMth(this.selectedIndex);winMain.RenderCal();\">\n";
	for (i=0;i<12;i++)
	{
		if (i==Cal.Month)
			SelectStr="Selected";
		else
			SelectStr="";	
		if (Cal.LocaleValue == "en"){
			vCalHeader+="<option "+SelectStr+" value >"+MonthName[i]+"\n";
		}else if (Cal.LocaleValue == "us"){
			vCalHeader+="<option "+SelectStr+" value >"+MonthName[i]+"\n";
		}else if (Cal.LocaleValue == "de") {
			vCalHeader+="<option "+SelectStr+" value >"+MonatsName[i]+"\n";
		}else if (Cal.LocaleValue == "zh") {
			vCalHeader+="<option "+SelectStr+" value >"+MonthNameChinese[i]+"\n";
		}else{
			vCalHeader+="<option "+SelectStr+" value >"+MonthName[i]+"\n";
		}
	}
	vCalHeader+="</select></td>";
	//Year selector
	vCalHeader+="\n<td align='right'><a href=\"javascript:winMain.Cal.DecYear();winMain.RenderCal()\"><b><font color=\""+YrSelColor+"\"><</font></b></a><font face=\"Verdana\" color=\""+YrSelColor+"\" size=2><b> "+Cal.Year+" </b></font><a href=\"javascript:winMain.Cal.IncYear();winMain.RenderCal()\"><b><font color=\""+YrSelColor+"\">></font></b></a></td></tr></table></td>\n";	
	vCalHeader+="</tr>";
	//Calendar header shows Month and Year
	if (ShowMonthYear)
		vCalHeader+="<tr><td colspan='7'><font face='Verdana' size='2' align='center' color='"+MonthYearColor+"'><b>"+Cal.GetMonthName(ShowLongMonth)+" "+Cal.Year+"</b></font></td></tr>\n";
	//Week day header
	vCalHeader+="<tr bgcolor="+WeekHeadColor+">";
	for (i=0;i<7;i++) {
		if(Cal.LocaleValue == "en")
			vCalHeader+="<td align='center'><font face='Verdana' size='2'>"+WeekDayName[i].substr(0,WeekChar)+"</font></td>";
		else if (Cal.LocaleValue == "us")
			vCalHeader+="<td align='center'><font face='Verdana' size='2'>"+WeekDayName[i].substr(0,WeekChar)+"</font></td>";
		else if (Cal.LocaleValue == "de")
			vCalHeader+="<td align='center'><font face='Verdana' size='2'>"+WochentagsName[i].substr(0,WeekChar)+"</font></td>";
		else if (Cal.LocaleValue == "zh")
			vCalHeader+="<td align='center'><font face='Verdana' size='2'>"+WeekDayNameChinese[i].substr(0,WeekChar)+"</font></td>";
		else 
			vCalHeader+="<td align='center'><font face='Verdana' size='2'>"+WeekDayName[i].substr(0,WeekChar)+"</font></td>";
	}
	vCalHeader+="</tr>";	
	docCal.write(vCalHeader);
	
	//Calendar detail
	CalDate=new Date(Cal.Year,Cal.Month);
	CalDate.setDate(1);
	vFirstDay=CalDate.getDay();
	vCalData="<tr>";
	for (i=0;i<vFirstDay;i++){
		vCalData=vCalData+GenCell();
		vDayCount=vDayCount+1;
	}
	for (j=1;j<=Cal.GetMonDays();j++){
		var strCell;
		vDayCount=vDayCount+1;
		if ((j==dtToday.getDate())&&(Cal.Month==dtToday.getMonth())&&(Cal.Year==dtToday.getFullYear()))
			strCell=GenCell(j,true,TodayColor);//Highlight today's date
		else{
			if (j==Cal.Date)			{
				strCell=GenCell(j,true,SelDateColor);
			}else{	 
				if (vDayCount%7==0)
					strCell=GenCell(j,false,SaturdayColor);
				else if ((vDayCount+6)%7==0)
					strCell=GenCell(j,false,SundayColor);
				else
					strCell=GenCell(j,null,WeekDayColor);
			}		
		}						
		vCalData=vCalData+strCell;

		if((vDayCount%7==0)&&(j<Cal.GetMonDays())){
			vCalData=vCalData+"</tr>\n<tr>";
		}
	}
	docCal.writeln(vCalData);	
	docCal.writeln("\n</table>");
	docCal.writeln("</form></body></html>");
	docCal.close();
}

function GenCell(pValue,pHighLight,pColor)//Generate table cell with value
{
	var PValue;
	var PCellStr;
	var vColor;
	var vHLstr1;//HighLight string
	var vHlstr2;
	var vTimeStr;
	
	if (pValue==null)
		PValue="";
	else
		PValue=pValue;
	
	if (pColor!=null)
		vColor="bgcolor=\""+pColor+"\"";
	else
		vColor="";	
	if ((pHighLight!=null)&&(pHighLight))
		{vHLstr1="color='red'><b>";vHLstr2="</b>";}
	else
		{vHLstr1=">";vHLstr2="";}	
	
	PCellStr="<td "+vColor+" width="+CellWidth+" align='center'><font face='verdana' size='2'"+vHLstr1+"<a href=\"#\" onclick=\"javascript:winMain.document.getElementById('"+Cal.Ctrl+"').value='"+Cal.FormatDate(PValue)+"';window.close();\">"+PValue+"</a>"+vHLstr2+"</font></td>";
	return PCellStr;
}

function Calendar(pDate, pCtrl, pDisplayFormat){
	//Properties
	this.Date=pDate.getDate();//selected date
	this.Month=pDate.getMonth();//selected month number
	this.Year=pDate.getFullYear();//selected year in 4 digits
	this.Hours=pDate.getHours();
	
	if (pDate.getMinutes()<10)
		this.Minutes="0"+pDate.getMinutes();
	else
		this.Minutes=pDate.getMinutes();
	
	if (pDate.getSeconds()<10)
		this.Seconds="0"+pDate.getSeconds();
	else		
		this.Seconds=pDate.getSeconds();
		
	this.MyWindow=winCal;
	this.Ctrl=pCtrl;
	this.Format="ddMMyyyy";
	// format to display date in control	
	if (pDisplayFormat!=null)
		this.DisplayFormat=pDisplayFormat;

	// displayformat corresponds with the used locale 
	if (pDisplayFormat.toUpperCase()=="DD/MM/YYYY"){
		this.LocaleValue = 'en';
	}else if (pDisplayFormat.toUpperCase()=="MM/DD/YYYY"){
		this.LocaleValue = 'us';
	}else if (pDisplayFormat.toUpperCase()=="YYYY-MM-DD"){
		this.LocaleValue = 'zh';	
	}else if (pDisplayFormat.toUpperCase()=="DD.MM.YYYY"){
		this.LocaleValue = 'de';	
	}
}

function GetMonthIndex(shortMonthName){
	if(shortMonthName){
		if(Cal.LocaleValue == "de"){
			for (i=0;i<12;i++){
				if (MonatsName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
				{	return i;}
			}
		}else if (Cal.LocaleValue == "en") {
			for (i=0;i<12;i++){
				if (MonthName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
				{	return i;}
			}
		}else if (Cal.LocaleValue == "us") {
			for (i=0;i<12;i++){
				if (MonthName[i].substring(0,3).toUpperCase()==shortMonthName.toUpperCase())
				{	return i;}
			}
		}else if (Cal.LocaleValue == "zh") {
			for (i=0;i<12;i++){
				if (MonthNameChinese[i].substring(0,3)==shortMonthName)
				{	return i;}
			}
		}
	}
}
Calendar.prototype.GetMonthIndex=GetMonthIndex;

function IncYear()
{	Cal.Year++;}
Calendar.prototype.IncYear=IncYear;

function DecYear()
{	Cal.Year--;}
Calendar.prototype.DecYear=DecYear;
	
function SwitchMth(intMth)
{	Cal.Month=intMth;}
Calendar.prototype.SwitchMth=SwitchMth;

function GetMonthName(IsLong, locale){
	if(Cal.LocaleValue == "de")
		var Month=MonatsName[this.Month];
	else if (Cal.LocaleValue == "en")
		var Month=MonthName[this.Month];
	else if (Cal.LocaleValue == "us")
		var Month=MonthName[this.Month];
	else if (Cal.LocaleValue == "zh")
		var Month=MonthNameChinese[this.Month];

	if (IsLong)
		return Month;
	else
		return Month.substr(0,3);
}
Calendar.prototype.GetMonthName=GetMonthName;

function GetMonDays(){
	var DaysInMonth=[31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
	if (this.IsLeapYear())	{
		DaysInMonth[1]=29;
	}	
	return DaysInMonth[this.Month];	
}
Calendar.prototype.GetMonDays=GetMonDays;

function IsLeapYear(){
	if ((this.Year%4)==0){
		if ((this.Year%100==0) && (this.Year%400)!=0){
			return false;
		}else{
			return true;
		}
	}else{
		return false;
	}
}
Calendar.prototype.IsLeapYear=IsLeapYear;

function FormatDate(pDate){
	dayValue = pDate;
	if(dayValue < 10){
		dayValue = "0" + pDate;
	}
	monthValue = this.Month+1;
	if(monthValue < 10){
		monthValue = "0" + monthValue;
	}
	
	if (Cal.LocaleValue =="us")
		return (monthValue+'/'+dayValue+'/'+this.Year);
	else if (Cal.LocaleValue =="en")
		return (dayValue+'/'+monthValue+'/'+this.Year);	
	else if (Cal.LocaleValue =="zh")
		return (this.Year+'-'+monthValue+'-'+dayValue);	
	else if (Cal.LocaleValue =="de")
		return (dayValue+'.'+monthValue+'.'+this.Year);					
}
Calendar.prototype.FormatDate=FormatDate;	