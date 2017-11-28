$(document).ready(function() {
	$("#searchButton").click(function(event) {
		validateForm();
	});
	
	$("#resetOutageSearch").click(function(event){
    	resetForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    }); 
});

function validateForm(){
   	var inValidElements = [];			
	var validElements = [];
	
	(!($('#chkPlanned').is(":checked") || $('#chkUnPlanned').is(":checked"))) ?inValidElements.push("chkPlanned"):validElements.push("chkPlanned");
	
	//Validation in layout.js. If condition will make it non mandatory field
	if($("#fromDate").val() || $("#toDate").val()){
		var ErrorArray = isValideStartEndDate($("#fromDate").val(), $("#toDate").val());
		ErrorArray.forEach(function(item) {
			//alert("Hi 1"+ item);
			if(item.indexOf("startDate|") >= 0){
				inValidElements.push("fromDate");
				$("span", $('#fromDate').parent()).text(item.substr("startDate|".length,item.length));
			}
			if(item.indexOf("endDate|") >= 0){
				inValidElements.push("toDate");
				$("span", $('#toDate').parent()).text(item.substr("endDate|".length,item.length));
			}
		});
	}
	
	if($("#outageId").val()){
		if(isNaN($("#outageId").val())){
			inValidElements.push("outageId");
			$("span", $('#outageId').parent()).text("Invalid Outage Id.");
		}else{
			validElements.push("outageId");
		}
	}
	
	if($("#snowId").val()){
		if(isValideIncidentId($("#snowId").val())){
			validElements.push("snowId");
		}else if(isValideDfctEnhcId($("#snowId").val())){
			validElements.push("snowId");
		}else{
			inValidElements.push("snowId");
			$("span", $('#snowId').parent()).text("Invalid SNOW Id.");
		}
	}
    
	displayError(validElements, inValidElements);
	
}

function resetForm() {
    $('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA', '#formId').val('');  
    $("#chkPlanned").prop("checked", true);
    $("#chkUnPlanned").prop("checked", true);
    var validElements = [];
    validElements.push('fromDate','toDate','plannedMultiselect','chkPlanned','chkUnPlanned', 'outageId');
    for (var i = 0; i < validElements.length; i++) {
			$("span", $('#' + validElements[i]).parent()).removeClass("has-error").addClass("hide");	
	}
}
