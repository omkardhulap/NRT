$(document).ready(function() {
		
	$("#addPM").click(function(event) {
		validateForm();
	});

	$("#editPM").click(function(event) {
		validateForm();
	});
	
	$("#resetPM").click(function(event){
    	resetForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
});
	
function validateForm() {
	var inValidElements = [];			
	var validElements = [];
	
	($("#innovationTitle").val()) ?validElements.push("innovationTitle"):inValidElements.push("innovationTitle");
	($("#problemStatement").val()) ?validElements.push("problemStatement"):inValidElements.push("problemStatement");
	($("#solution").val()) ?validElements.push("solution"):inValidElements.push("solution");	
	($("#itBenefit").val()) ?validElements.push("itBenefit"):inValidElements.push("itBenefit");
	($("#businessBenefit").val()) ?validElements.push("businessBenefit"):inValidElements.push("businessBenefit");
	($("#ideatedBy").val()) ?validElements.push("ideatedBy"):inValidElements.push("ideatedBy");
	($("#implementedBy").val()) ?validElements.push("implementedBy"):inValidElements.push("implementedBy");	
	
	var effortHours = $("#effortHours").val();
	if(!effortHours) {
		inValidElements.push("effortHours");
	} else if(isNaN(effortHours) || effortHours < 0) {
		inValidElements.push("effortHours");
		$("span", $('#effortHours').parent()).text("Please enter valid number of Hours");
	} else {
		validElements.push("effortHours");
	}
	
	var complPercentage = $("#completionPercentage").val();
	if(!complPercentage) {
		inValidElements.push("completionPercentage");
	} else if(!isInt(complPercentage) || complPercentage < 0 || complPercentage > 100) {
		inValidElements.push("completionPercentage");
		$("span", $('#completionPercentage').parent()).text("Please enter valid percentage value");
	} else {
		validElements.push("completionPercentage");
	}
	
	var dollarSaving = $("#dollarSaving").val();
	if(dollarSaving && (!isInt(dollarSaving) || dollarSaving < 0)) {
		inValidElements.push("dollarSaving");
	} else {
		validElements.push("dollarSaving");
	}

	var incidentReduction = $("#incidentReduction").val();
	if(incidentReduction && (!isInt(incidentReduction) || incidentReduction < 0)) {
		inValidElements.push("incidentReduction");
	} else {
		validElements.push("incidentReduction");
	}

	var effortSaving = $("#effortSaving").val();
	if(effortSaving && (!isInt(effortSaving) || effortSaving < 0)) {
		inValidElements.push("effortSaving");
	} else {
		validElements.push("effortSaving");
	}


	var ErrorArray = isValideStartEndDate($("#initiationDate").val(), $("#completionDate").val());
	ErrorArray.forEach(function(item) {
		//alert("Hi "+ item);
		if(item.startsWith("startDate|")){
			inValidElements.push("initiationDate");
			$("span", $('#initiationDate').parent()).text(item.substr("startDate|".length,item.length).replace("Start","Initiation"));
		} else {
			validElements.push("initiationDate");
		}
		if(item.startsWith("endDate|")){
			inValidElements.push("completionDate");
			$("span", $('#completionDate').parent()).text(item.substr("endDate|".length,item.length).replace("End","Completion").replace("Start","Initiation"));
		} else {
			validElements.push("completionDate");
		}
	});
	
	($("select[id='benefitTypesMultiselect'] option:selected").length >0) ?validElements.push("benefitTypesMultiselect"):inValidElements.push("benefitTypesMultiselect");
	//In layout.js
	displayError(validElements, inValidElements);
}

	function resetForm() {
        $('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA', '#problemManagementFormId').val('');  
        $("#problemManagementFormId").find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
        $('#application').val('1');        
        $('#priority').val('1'); 
        $('#status').val('1');
        
    	$('.inputFieldErrors').each(function(i, obj) {
    		$(this).removeClass("has-error").addClass("hide");
    	});

        $("#id").val('New Problem Management');
    }