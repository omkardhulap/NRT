$(document).ready(function() {
		
		$("#addUnplannedOutage").click(function(event) {
			validateForm();			
		});
		
		$("#editUnplannedOutage").click(function(event) {
			validateForm();
		});
		
		$("#resetUnplannedOutage").click(function(event){
	    	resetForm();
	    });
		
		$("#vendorAccountable").buttonset();
		
		if($("input[name='vendorAccountable']:checked").val() == "Yes"){
	    	$("#vendorAccountableName").prop("readonly", false);
	    }else{
	    	$("#vendorAccountableName").val("");
	    	$("#vendorAccountableName").prop("readonly", true);
	    }

		$("input[id='vendorAccountable']").on("change", function () {
		    if(this.value == "Yes"){
		    	$("#vendorAccountableName").prop("readonly", false);

		    }else{
		    	$("#vendorAccountableName").val("");
		    	$("#vendorAccountableName").prop("readonly", true);
		    }
		});
		
});
	
function validateForm() {
	var inValidElements = [];			
	var validElements = [];
	
	var isStartDateCorrect = true;
	var isEndDateCorrect = true;
	
	//Validation in layout.js
	isValideIncidentId($("#snowId").val().trim())?validElements.push("snowId"):inValidElements.push("snowId");
	
	var ErrorArray = isValideStartEndDate($("#deploymentStartDate").val(), $("#deploymentEndDate").val());
	ErrorArray.forEach(function(item) {
		//alert("Hi "+ item);
		if(item.startsWith("startDate|")){
			inValidElements.push("deploymentStartDate");
			isStartDateCorrect = false; 
			$("span", $('#deploymentStartDate').parent()).text(item.substr("startDate|".length,item.length));
		}
		if(item.startsWith("endDate|")){
			inValidElements.push("deploymentEndDate");
			isEndDateCorrect = false;
			$("span", $('#deploymentEndDate').parent()).text(item.substr("endDate|".length,item.length));
		}
	});
	
	var dateValidationMsg = validateSingleDate($("#reportedOn").val());
	if(dateValidationMsg.indexOf("correctDate|") >= 0){
		validElements.push("reportedOn");
	}else{
		inValidElements.push("reportedOn");
		$("span", $('#reportedOn').parent()).text(dateValidationMsg);
	}
	
	var aarDateValidationMsg = validateSingleDate($("#aarDate").val());
	if(aarDateValidationMsg.indexOf("correctDate|") >= 0){
		validElements.push("aarDate");
	}else{
		inValidElements.push("aarDate");
		$("span", $('#aarDate').parent()).text(aarDateValidationMsg);
	}
	
	//Validate reported on is before than deploymentStartDate
	if(validElements.indexOf("reportedOn") > -1 && isStartDateCorrect){
		if(! isFirstDateOlder($("#deploymentStartDate").val(), $("#reportedOn").val())){
			inValidElements.push("reportedOn");
			$("span", $('#reportedOn').parent()).text("Reporting Time should be earlier than Start Time.");
		}else{
			validElements.push("reportedOn");
		}
	}
	
	//Validate aarDate on is after than deploymentEndDate
	if(validElements.indexOf("aarDate") > -1 && isEndDateCorrect){
		if(! isFirstDateOlder($("#aarDate").val(), $("#deploymentEndDate").val())){
			inValidElements.push("aarDate");
			$("span", $('#aarDate').parent()).text("AAR date should be after end of outage.");
		}else{
			validElements.push("aarDate");
		}
	}
	
	
	($("#description").val()) ?validElements.push("description"):inValidElements.push("description");
	
	($("#reportedBy").val()) ?validElements.push("reportedBy"):inValidElements.push("reportedBy");
	
	($("#businessAffected").val()) ?validElements.push("businessAffected"):inValidElements.push("businessAffected");
	
	($("#stOwner").val()) ?validElements.push("stOwner"):inValidElements.push("stOwner");
	
	($("#dueTo").val()) ?validElements.push("dueTo"):inValidElements.push("dueTo");
	
	($("#executiveSummary").val()) ?validElements.push("executiveSummary"):inValidElements.push("executiveSummary");
	
	($("#technicalIssues").val()) ?validElements.push("technicalIssues"):inValidElements.push("technicalIssues");
	
	($("#resolution").val()) ?validElements.push("resolution"):inValidElements.push("resolution");
	
	($("#rootCause").val()) ?validElements.push("rootCause"):inValidElements.push("rootCause");
	
	($("input[name='vendorAccountable']:checked").length > 0) ?validElements.push("vendorAccountable"):inValidElements.push("vendorAccountable");
	
	
    if($("input[name='vendorAccountable']:checked").val() == "Yes"){
    	$("#vendorAccountableName").prop("readonly", false);
    	($("#vendorAccountableName").val()) ?validElements.push("vendorAccountableName"):inValidElements.push("vendorAccountableName");
    }else{
    	$("#vendorAccountableName").val("");
    	$("#vendorAccountableName").prop("readonly", true);
    	validElements.push("vendorAccountableName");
    }
	
	($("#aarOwner").val()) ?validElements.push("aarOwner"):inValidElements.push("aarOwner");

	($("#database").val()) ?validElements.push("database"):inValidElements.push("database");
	
	($("#platform").val()) ?validElements.push("platform"):inValidElements.push("platform");

	($("select[id='plannedMultiselect'] option:selected").length >0) ?validElements.push("plannedMultiselect"):inValidElements.push("plannedMultiselect");
	
	($("select[id='pointOfFailuresMultiselect'] option:selected").length >0) ?validElements.push("pointOfFailuresMultiselect"):inValidElements.push("pointOfFailuresMultiselect");
	
	//Display in layout.js
	displayError(validElements, inValidElements);
}
	
function resetForm() {
    $('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA', '#unPlannedOutageFormId').val('');
    $("#unPlannedOutageFormId").find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
    $('#priority').val('1'); 
    $('#severity').val('101');
    var validElements = [];
    validElements.push('snowId','description','priority.id','severity.id','reportedOn','reportedBy','businessAffected','stOwner','dueTo','executiveSummary','technicalIssues','resolution','rootCause','vendorAccountable','vendorAccountableName','aarOwner','aarDate','database','platform','plannedMultiselect','pointOfFailuresMultiselect','deploymentStartDate','deploymentEndDate');
    hideErrorMessages(validElements);
    
    $("#id").val('New Outage');
    
    event.preventDefault ? event.preventDefault() : event.returnValue = false;
}

	
// Remove spaces from INCIDENT ID --%>
function removeSpaces(string) {
	return string.split(' ').join('');
}
if ( typeof String.prototype.startsWith != 'function' ) {
	  String.prototype.startsWith = function( str ) {
	    return this.substring( 0, str.length ) === str;
	  };
};