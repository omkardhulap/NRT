$(document).ready(function() {
		
	$("#addPlannedOutage").click(function(event) {
		validateForm();
	});

	$("#editPlannedOutage").click(function(event) {
		validateForm();
	});
	
	$("#resetPlannedOutage").click(function(event){
    	resetForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
});
	
function validateForm() {
	var inValidElements = [];			
	var validElements = [];
	
	//Validation in layout.js
	isValidSetofSnowIds($("#snowId").val())?validElements.push("snowId"):inValidElements.push("snowId");
	
	var ErrorArray = isValideStartEndDate($("#deploymentStartDate").val(), $("#deploymentEndDate").val());
	ErrorArray.forEach(function(item) {
		//alert("Hi "+ item);
		if(item.startsWith("startDate|")){
			inValidElements.push("deploymentStartDate");
			$("span", $('#deploymentStartDate').parent()).text(item.substr("startDate|".length,item.length));
		}
		if(item.startsWith("endDate|")){
			inValidElements.push("deploymentEndDate");
			$("span", $('#deploymentEndDate').parent()).text(item.substr("endDate|".length,item.length));
		}
	});
	
	var dateValidationMsg = validateSingleDate($("#approvalDate").val());
	if(dateValidationMsg.indexOf("correctDate|") >= 0){
		validElements.push("approvalDate");
	}else{
		inValidElements.push("approvalDate");
		$("span", $('#approvalDate').parent()).text(dateValidationMsg);
	}
	
	($("select[id='plannedMultiselect'] option:selected").length >0) ?validElements.push("plannedMultiselect"):inValidElements.push("plannedMultiselect");
	
	($('input[name=outageRequired]:checked').length > 0) ?validElements.push("outageRequired"):inValidElements.push("outageRequired");

	($("#description").val()) ?validElements.push("description"):inValidElements.push("description");
	
   	($("input[name='approvedBy.id']:checked").length > 0) ?validElements.push("approvedBy"):inValidElements.push("approvedBy");
 	
	($("#scope").val()) ?validElements.push("scope"):inValidElements.push("scope");

	displayError(validElements, inValidElements);
}
	
	// Remove spaces from SNOW ID 
	function removeSpaces(string) {
		return string.split(' ').join('');
	}
	if ( typeof String.prototype.startsWith != 'function' ) {
		  String.prototype.startsWith = function( str ) {
		    return this.substring( 0, str.length ) === str;
		  };
	};
	
	function resetForm() {
        $('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA', '#plannedOutageFormId').val('');  
        $("#plannedOutageFormId").find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
	    var validElements = [];
        validElements.push('plannedMultiselect','outageRequired','snowId','deploymentStartDate','deploymentEndDate','description','approvalDate','approvedBy','scope');
        hideErrorMessages(validElements);
        
        $("#id").val('New Outage');
    }