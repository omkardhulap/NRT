$(document).ready(function() {
	$("#searchButton").click(function(event) {
		validateForm();
	});
	
	$("#resetPMSearch").click(function(event){
    	resetForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    }); 
});

function validateForm(){
   	var inValidElements = [];			
	var validElements = [];
	
	//Validation in layout.js. If condition will make it non mandatory field
	if($("#fromDate").val() || $("#toDate").val()){
		var ErrorArray = isValideStartEndDate($("#fromDate").val(), $("#toDate").val());
		ErrorArray.forEach(function(item) {
			//alert("Hi 1"+ item);
			if(item.indexOf("startDate|") >= 0){
				inValidElements.push("fromDate");
				$("span", $('#fromDate').parent()).text(item.substr("startDate|".length,item.length));
			} else {
				validElements.push("fromDate");
			}
			if(item.indexOf("endDate|") >= 0){
				inValidElements.push("toDate");
				$("span", $('#toDate').parent()).text(item.substr("endDate|".length,item.length));
			} else {
				validElements.push("toDate");
			}
		});
	}
	
	displayError(validElements, inValidElements);
	
}

function resetForm() {
    $('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA', '#formId').val('');  
    var validElements = [];
    validElements.push('fromDate','toDate');
    for (var i = 0; i < validElements.length; i++) {
			$("span", $('#' + validElements[i]).parent()).removeClass("has-error").addClass("hide");	
	}
}
