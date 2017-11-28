/**
    Document   : user signup
    Created on : July 1, 2015, 5:22:56 PM
    Author     : Omkar_Dhulap
 */

var contextRoot;
var alphaExp = /^[a-zA-Z]+$/;
var alphaNumExp = /^[0-9a-zA-Z_]+$/;

$(document).ready(function() {
	
	contextRoot = window.location.pathname.substring(0, window.location.pathname.indexOf("/",2));
	
	$("#resetUser").click(function(event){
		resetAddForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
	$("#cancel").click(function(event){
		cancelSignUp();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
	$("#registerUser").click(function(event) {
		if(! validateInputForm()){
			registerUser();
		}
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
	});
	
	$("#checkAvailability").click(function(event) {
		if(! validateUserName()){
			checkUserAvailibility();
		}
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
	});
	
});

function resetAddForm(){
	$('.inputFieldErrors').each(function(i, obj) {
		$(this).removeClass("has-error").addClass("hide");
	});
	
	$(":input[class~='addField']").each(function() {
		this.value = "";
	});
}

function cancelSignUp(){
	document.location.href =  contextRoot;
}

function validateInputForm() {
	var inValidElements = [];			
	var validElements = [];
	
	$(":input[class~='addField']").each(function() {
		(this.value.trim().length > 0)?validElements.push(this.name):inValidElements.push(this.name);
	});
	
	($("#fname").val() && ($("#fname").val()).match(alphaExp))?validElements.push("fname"):inValidElements.push("fname");
	
	($("#lname").val() && ($("#lname").val()).match(alphaExp))?validElements.push("lname"):inValidElements.push("lname");
	
	($("#nikeid").val() && ($("#nikeid").val()).match(alphaNumExp))?validElements.push("nikeid"):inValidElements.push("nikeid");
	
	($("#repassword").val() == $("#password").val())?validElements.push("repassword"):inValidElements.push("repassword");
	
	(isValidEmail($("#nikeEmail").val()))?validElements.push("nikeEmail"):inValidElements.push("nikeEmail");
	
	(isValidEmail($("#infyEmail").val()))?validElements.push("infyEmail"):inValidElements.push("infyEmail");
	
	var nikeEmail = $("#nikeEmail").val();
	nikeEmail = nikeEmail.substring(nikeEmail.indexOf("@") + 1);
	if(nikeEmail.toLowerCase() != "nike.com"){
		inValidElements.push("nikeEmail");
	}else{
		validElements.push("nikeEmail");
	}
	
	var infyEmail = $("#infyEmail").val();
	infyEmail = infyEmail.substring(infyEmail.indexOf("@") + 1);
	if(infyEmail.toLowerCase() != "infosys.com"){
		inValidElements.push("infyEmail");
	}else{
		validElements.push("infyEmail");
	}
	
//	($("#nikeEmail").val() != $("#infyEmail").val())?validElements.push("nikeEmail"):inValidElements.push("nikeEmail");
//	($("#nikeEmail").val() != $("#infyEmail").val())?validElements.push("infyEmail"):inValidElements.push("infyEmail");
	
	displayError(validElements, inValidElements);
	
	if(inValidElements.length > 0)
		return true;
	else
		return false;
}

function validateUserName(){
	var inValidElements = [];			
	var validElements = [];
	
	($("#nikeid").val() && ($("#nikeid").val()).match(alphaNumExp))?validElements.push("nikeid"):inValidElements.push("nikeid");
	
	displayError(validElements, inValidElements);
		
		if(inValidElements.length > 0)
			return true;
		else
			return false;
}

function isValidEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}

function checkUserAvailibility(){
	var masterData = {};
	
	$(":input[id='nikeid']").each(function() {
//		alert(this.type +"  "+ this.name +"  >>"+ this.value + "<<");
		masterData[this.name] = this.value;
	});
	
	$.ajax({
	    
	    url:  contextRoot + "/user/checkUserAvailibility",
	    type: 'GET',
	    data: JSON.stringify(masterData),
	    contentType: 'application/json',
	    async: true,
	    success: function(data) {
	    	if(data.indexOf("successMessage|") >= 0){
			    $('#availabilitySuccessMessage').removeClass("hide").addClass("has-success");
			    $('#availabilityErrorMessage').removeClass("has-error").addClass("hide");
	    	}else if(data.indexOf("errorMessage|") >= 0){
	    		$('#availabilityErrorMessage').removeClass("hide").addClass("has-error");
			    $('#availabilitySuccessMessage').removeClass("has-success").addClass("hide");
	    	}else{
	    		$('#availabilitySuccessMessage').removeClass("has-success").addClass("hide");
	    		$('#availabilityErrorMessage').removeClass("has-error").addClass("hide");
	    		$('#signUpMessage').text(data);
			    $('#signUpMessage').removeClass("hide").addClass("text-error");
	    	}
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	    	$('#availabilitySuccessMessage').removeClass("has-success").addClass("hide");
    		$('#availabilityErrorMessage').removeClass("has-error").addClass("hide");
	        $('#signUpMessage').text("Error occured while checking username availability. Please contact support team.");
		    $('#signUpMessage').removeClass("hide").addClass("text-error");
	    }
	});
}

function registerUser(){
	var masterData = {};
	$(":input[class~='form-control']").each(function() {
		masterData[this.name] = this.value;
	});
	
	$.ajax({
		    
		    url:  contextRoot + "/user/signUp",
		    type: 'GET',
		    data: JSON.stringify(masterData),
		    contentType: 'application/json',
		    async: true,
		    success: function(data) {
		    	if(data.indexOf("successMessage|") >= 0){
		    		$('#signUpMessage').text(data.substr("successMessage|".length,data.length));
				    $('#signUpMessage').removeClass("hide").addClass("text-success bg-success");
				    resetAddForm();
		    	}else if(data.indexOf("errorMessage|") >= 0){
		    		 $('#signUpMessage').text(data.substr("errorMessage|".length,data.length));
				     $('#signUpMessage').removeClass("hide").addClass("text-error");
		    	}else{
		    		$('#signUpMessage').text(data);
				    $('#signUpMessage').removeClass("hide").addClass("text-error");
		    	}
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		        $('#signUpMessage').text("Error occured while sign up. Please contact support team.");
			    $('#signUpMessage').removeClass("hide").addClass("text-error");
		    }
		});
}



