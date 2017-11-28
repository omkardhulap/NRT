$(document).ready(function() {
		
	$("#addEffort").click(function(event) {
		validateForm();
	});

	$("#editEffort").click(function(event) {
		validateForm();
	});
	
	$("#resetEffort").click(function(event){
    	resetForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
	
	$("#category").click(function(event) {
		validateCategory();
	});
	
	$(window).load(function() {
		validateCategory();
	});
	
	
});


function validateCategory(){
	var cateforyValidationMsg = ($("#category").val());
	//Check SNOW ID only for incident or defect management. Similarly check Priority & Complexity valid values
	if(cateforyValidationMsg == "101" || cateforyValidationMsg == "102"){
		$('#snowNumber').attr("readonly", false);
		$('#priority').attr("disabled", false);
		$('#complexity').attr("disabled", false);
	}else{
		$('#snowNumber').val('');		
		$('#priority').val('5'); 
		$("#complexity").val('106');
		
		$('#snowNumber').attr("readonly", true);
		$('#priority').attr("disabled", true);
		$('#complexity').attr("disabled", true);
	}
}
	
function validateForm() {
	var inValidElements = [];			
	var validElements = [];
	
	var cateforyValidationMsg = ($("#category").val());
	//Check SNOW ID only for incident or defect management. Similarly check Priority & Complexity valid values
	if(cateforyValidationMsg == "101" || cateforyValidationMsg == "102"){
		if($("#priority").val() == "5"){
			inValidElements.push("priority");
		}else{
			validElements.push("priority");
		}
		
		if($("#complexity").val() == "106"){
			inValidElements.push("complexity");
		}else{
			validElements.push("complexity");
		}
		
		if(cateforyValidationMsg == "101"){
			if(!(isValideIncidentId($("#snowNumber").val()))){
				inValidElements.push("snowNumber");
			}else{
				validElements.push("snowNumber");
			}
		} else if(cateforyValidationMsg == "102"){
			if(!(isValideDfctEnhcId($("#snowNumber").val()))){
				inValidElements.push("snowNumber");
			}else{
				validElements.push("snowNumber");
			}			
		} 
		
		//Validation in layout.js
		if($("#snowNumber").val()){
			if(isValidSetofSnowIds($("#snowNumber").val()))
				validElements.push("snowNumber");
			else
				inValidElements.push("snowNumber");
		}else{
			inValidElements.push("snowNumber");
		}
		
		
	}else{
		validElements.push("priority");
		validElements.push("complexity");
		validElements.push("snowNumber");
	}
	
	($("#effortDescription").val()) ?validElements.push("effortDescription"):inValidElements.push("effortDescription");
	($("#effortHours").val()) ?validElements.push("effortHours"):inValidElements.push("effortHours");
	
	if($("#effortDate").val()){
		var effortDateUi = $("#effortDate").val();
		var dateValidationMsg = validateSingleDate(effortDateUi);
		
		if(dateValidationMsg.indexOf("correctDate|") >= 0){
			
			var effortDate =  new Date(effortDateUi);
			var today = new Date();
			
//			alert(effortDate + "  " +today);
			
			if(effortDate > today){
				inValidElements.push("effortDate");
			}else{
				validElements.push("effortDate");
			}
		}else{
			inValidElements.push("effortDate");
			$("span", $('#effortDate').parent()).text(dateValidationMsg);
		}
	}else{
		inValidElements.push("effortDate");
	}
	
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
        $('INPUT:text, INPUT:password, INPUT:file, SELECT, TEXTAREA', '#manageEffortFormId').val('');  
        $("#manageEffortFormId").find('input:radio, input:checkbox').removeAttr('checked').removeAttr('selected');
        $('#application').val('1'); 
        $('#priority').val('1'); 
        $('#complexity').val('101');
        $('#category').val('101');
        $('#snowNumber').attr("readonly", false);
	    var validElements = [];
        validElements.push('application','priority','complexity','category','snowNumber','effortDescription','effortHours','effortDate');
        hideErrorMessages(validElements);
        
        $("#id").val('New Effort');
    }