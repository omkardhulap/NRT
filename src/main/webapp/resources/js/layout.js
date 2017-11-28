// Time Format MM/dd/yyyy HH:mm:ss regular expression
var timeFormatRegExp = /^(0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])[\/\-]\d{4} \d{2}:\d{2}:\d{2}$/;

$(document).ready(function(){

/*For left navigation bar*/
$('div#customAccordion').accordion({
	collapsible: true,
    active: false,
    heightStyle: 'content'
}); 
	
/*For all datepickers*/
$('.datepicker').datetimepicker({
		dateFormat: 'mm/dd/yy', 
		timeFormat: 'HH:mm:ss',
		stepHour: 1,
		stepMinute: 1,
		stepSecond: 1
	});

//To Scroll Animation
$.fn.scrollView = function () {
	return this.each(function () {
		$('html, body').animate({
			scrollTop: $(this).offset().top
		}, 10);
	});
};

});

//Snow Id validation to contain one or more values comma separated and starting with INC/DEF/ENH
function isValidSetofSnowIds(snowIds){
	//alert("isValidateSetofSnowIds" + snowIds);
	
	if(!snowIds) {
		return false;
	} else {
    	var snowIdTokens = snowIds.split(",");
 		
    	for(var i=0 ; i<snowIdTokens.length; ++i) {
 			
 			var snowIdToken = snowIdTokens[i].trim();
 			
 			if(isValideIncidentId(snowIdToken)) {
 				continue;
 			} else if(isValideDfctEnhcId(snowIdToken)) {
 				continue;		 				
 			} else {
 				return false;
 			} 
		}
    	
    	return true;
	}
}

//Validate SNOW Incident Id
function isValideIncidentId(incidentId){
	//alert("isValide IncidentId "+incidentId);

	if(incidentId  && (incidentId.startsWith("INC") || incidentId.startsWith("TKT") || incidentId.startsWith("SUB"))){
		var tailValue = incidentId.substring(3);

		if(tailValue.length != 7 || isNaN(tailValue))
			return false;
		else
			return true;
	}else if(incidentId  && (incidentId.startsWith("ITASK"))){
        tailValue = incidentId.substring(5);

        if(tailValue.length != 7 || isNaN(tailValue))
            return false;
        else
            return true;
	}else{
		return false;
	}
}

//Validate SNOW Defect Id
function isValideDfctEnhcId(defectEnhcId){
	//alert("isValide DfctEnhcId"+defectEnhcId);

	if(defectEnhcId  && (defectEnhcId.startsWith("ENHC") || defectEnhcId.startsWith("DFCT"))){
		var tailValue = defectEnhcId.substring(4);

		if(tailValue.length != 7 || isNaN(tailValue))
			return false;
		else
			return true;
	}else{
		return false;
	}
}

//Datepicker Start Date and End Date validation for all pages
function isValideStartEndDate(startDate, endDate){
	//alert("isValide StartEndDate " + startDate + "	"+ startDate);
	
	var tokensStartDate, tokensEndDate, startDateFormated, endDateFormated;
	
	if(startDate && endDate){
		tokensStartDate = startDate.split(new RegExp('[-+()*/:? ]', 'g'));
		tokensEndDate = endDate.split(new RegExp('[-+()*/:? ]', 'g'));
		
//		alert("tokensStartDate>> "+tokensStartDate);
//		alert("tokensEndDate>> "+tokensEndDate);

		// converting to new Date(Year, Month, Date, Hr, Min, Sec)
		startDateFormated = new Date(tokensStartDate[2],tokensStartDate[0]-1,tokensStartDate[1],tokensStartDate[3],tokensStartDate[4],tokensStartDate[5]);
		endDateFormated = new Date(tokensEndDate[2],tokensEndDate[0]-1,tokensEndDate[1],tokensEndDate[3],tokensEndDate[4],tokensEndDate[5]);

//		alert("startDateFormated>> "+startDateFormated);
//		alert("endDateFormated>> "+endDateFormated);
		
		if(!timeFormatRegExp.test(startDate) && !timeFormatRegExp.test(endDate)){
			return ["startDate|Please enter a valid Date","endDate|Please enter a valid Date"];
		}else if(timeFormatRegExp.test(startDate) && !timeFormatRegExp.test(endDate)){
			return ["endDate|Please enter a valid Date"];
		}else if(!timeFormatRegExp.test(startDate) && timeFormatRegExp.test(endDate)){
			return ["startDate|Please enter a valid Date"];
		}else if(timeFormatRegExp.test(startDate) && timeFormatRegExp.test(endDate)){
			if(startDateFormated >= endDateFormated){
				return ["endDate|End Date should be later than Start Date"];
			}else{
				return [""];
			}
		}

	}else if(!startDate && !endDate){
		
		return ["startDate|Please enter Start Date","endDate|Please enter End Date"];
	
	}else if(!startDate && endDate){
		
		tokensEndDate = endDate.split(new RegExp('[-+()*/:? ]', 'g'));
		// converting to new Date(Year, Month, Date, Hr, Min, Sec)
		endDateFormated = new Date(tokensEndDate[2],tokensEndDate[1]-1,tokensEndDate[0],tokensEndDate[3],tokensEndDate[4],tokensEndDate[5]);
		
		if(timeFormatRegExp.test(endDate)){
			return ["startDate|Please enter Start Date"];
		}else{
			return ["startDate"|"Please enter Start Date","endDate|Please enter valid End Date"];
		}
		
	}else if(startDate && !endDate){
		
		tokensStartDate = startDate.split(new RegExp('[-+()*/:? ]', 'g'));
		// converting to new Date(Year, Month, Date, Hr, Min, Sec)
		startDateFormated = new Date(tokensStartDate[2],tokensStartDate[1]-1,tokensStartDate[0],tokensStartDate[3],tokensStartDate[4],tokensStartDate[5]);
		
		if(timeFormatRegExp.test(startDate)){
			return ["endDate|Please enter End Date"];
		}else{
			return ["startDate|Please enter valid Start Date","endDate|Please enter End Date"];
		}
		
	}

}

//Validate Single Date
function validateSingleDate(dateValue){
	if(dateValue){
		if(timeFormatRegExp.test(dateValue)){
			return "correctDate|";
		}else{
			return "Please enter a valid date";
		}
	}else{
		return "Please enter date";
	}
}

function isFirstDateOlder(date1, date2){
	var tokensDate1, tokensDate2, date1Formated, date2Formated;
	tokensDate1 = date1.split(new RegExp('[-+()*/:? ]', 'g'));
	tokensDate2 = date2.split(new RegExp('[-+()*/:? ]', 'g'));

	date1Formated = new Date(tokensDate1[2],tokensDate1[0]-1,tokensDate1[1],tokensDate1[3],tokensDate1[4],tokensDate1[5]);
	date2Formated = new Date(tokensDate2[2],tokensDate2[0]-1,tokensDate2[1],tokensDate2[3],tokensDate2[4],tokensDate2[5]);

	if(date1Formated >= date2Formated)
		return true;
	
	return false;
}

function displayError(validElements, inValidElements){
	hideErrorMessages(validElements);
	displayErrorMessages(inValidElements);
}

function hideErrorMessages(validElements){
	if (typeof validElements !== 'undefined' && validElements.length > 0) {
		for (var i = 0; i < validElements.length; i++) {
 			$("span", $('#' + validElements[i]).parent()).removeClass("has-error").addClass("hide");	
		}
	}
}

function displayErrorMessages(inValidElements){
	if (typeof inValidElements !== 'undefined' && inValidElements.length > 0) {
		for (var i = 0; i < inValidElements.length; i++) {
 			$("span", $('#' + inValidElements[i]).parent()).removeClass("hide").addClass("has-error");
		}
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
	}
}

function isInt(value) {
	  return !isNaN(value) && 
	         parseInt(Number(value)) == value && 
	         !isNaN(parseInt(value, 10));
}

function getFormatedDate(dateStr){
	var date = new Date(dateStr);
	var str = lpad(date.getMonth(), 2, 0) + '/' + lpad(date.getDate(), 2, 0) + '/' + date.getFullYear() + ' ' +	
			  lpad(date.getHours(), 2, 0) + ':' + lpad(date.getMinutes(), 2, 0) +':' + lpad(date.getSeconds(), 2, 0);
	return str;
}

function lpad(str, len, char) {
    str += '';
    if (str.length >= len) {
        return str;
    }
    return new Array(len - str.length + 1).join(char) + str;
}

if ( typeof String.prototype.startsWith != 'function' ) {
	  String.prototype.startsWith = function( str ) {
	    return this.substring( 0, str.length ) === str;
	  };
};


