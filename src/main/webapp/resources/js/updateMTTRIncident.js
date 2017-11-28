$(document).ready(function() {
	$("#updateMTTR").click(function(event) {
		validateForm();
	});

	/*$("#category").click(function(event) {
		validateCategory();
	});
	
	$(window).load(function() {
		validateCategory();
	});*/
	
});

function validateForm() {
	var inValidElements = [];			
	var validElements = [];
	//alert($("#mttrBreachReason").val());
	if($("#mttrBreachReason").val() == "Reason Not Yet Defined"){
		inValidElements.push("mttrBreachReason");
	}else{
		validElements.push("mttrBreachReason");
	}
	
	if($("#mttrBreachReason").val() == "Other Reasons"){
		if($("#remarks").val() == ""){
			inValidElements.push("remarks");
		}
	}else{
		validElements.push("remarks");
	}
	displayError(validElements, inValidElements);
}


/*function validateCategory(){
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
}*/
	
	// Remove spaces from SNOW ID 
	/*function removeSpaces(string) {
		return string.split(' ').join('');
	}
	if ( typeof String.prototype.startsWith != 'function' ) {
		  String.prototype.startsWith = function( str ) {
		    return this.substring( 0, str.length ) === str;
		  };
	};*/
	
	/*function resetForm() {
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
    }*/