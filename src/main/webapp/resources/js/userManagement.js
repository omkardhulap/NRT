$(document).ready(function() {
		
		$("#addUser").click(function(event) {
			validateForm();
		});
		
		$("#editUser").click(function(event) {
			validateForm();
		});
		
		$("#resetUser").click(function(event){
	    	resetForm();
	    	try {
				event.preventDefault ? event.preventDefault()
						: event.returnValue = false;
			} catch (e) {
				
			}
	    });
	});
	
function validateForm() {
	var alphaExp = /^[a-zA-Z]+$/;
	var alphaNumExp = /^[0-9a-zA-Z_]+$/;
	var inValidElements = [];			
	var validElements = [];
	
	if($("#fname").val() && ($("#fname").val()).match(alphaExp)) validElements.push("fname");
	else inValidElements.push("fname");
	
	if($("#lname").val() && ($("#lname").val()).match(alphaExp)) validElements.push("lname");
	else inValidElements.push("lname");
	
	/* if($("#infyid").val() && ($("#infyid").val()).match(alphaNumExp)) validElements.push("infyid");
	else inValidElements.push("infyid"); */

	if($("#nikeid").val() && ($("#nikeid").val()).match(alphaNumExp)) {
		validElements.push("nikeid");
		document.getElementById("infyid").value = $("#nikeid").val();
	}
	else inValidElements.push("nikeid");
	
	if($("#password").val()) validElements.push("password");
	else inValidElements.push("password");
	
	if($("#repassword").val()) validElements.push("repassword");
	else inValidElements.push("repassword");
		
	if($("#repassword").val() == $("#password").val()){ 
		validElements.push("password");
		validElements.push("repassword");
    }else{
    	inValidElements.push("repassword");
    }
	
	if(isValidEmail($("#nikeEmail").val())) validElements.push("nikeEmail");
	else inValidElements.push("nikeEmail");
	
	if(isValidEmail($("#infyEmail").val())) validElements.push("infyEmail");
	else inValidElements.push("infyEmail");
	
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
	
	var roleSelectlength = $("select[id='role'] option:selected").length;
	if(roleSelectlength >0) validElements.push("role");
	else inValidElements.push("role");
	
	var statusSelectlength = $("select[id='status'] option:selected").length;
	if(statusSelectlength >0) validElements.push("status");
	else inValidElements.push("status");
	
	displayError(validElements, inValidElements);
}

function isValidEmail(email) {
  var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
  return regex.test(email);
}

function resetForm() {
	$('INPUT:text, INPUT:password', 'INPUT:email').val('');  
    var validElements = [];
    validElements.push('nikeid','fname','lname','password','repassword','nikeEmail','infyEmail');
    hideErrorMessages(validElements);
    
    $('.inputFieldErrors').each(function(i, obj) {
		$(this).removeClass("has-error").addClass("hide");
	});
    
    $("#id").val('New User');
}