
$(document).ready(function() {
	var searchBtnClicked = true;
	$("#searchButton").click(function(event) {
		//$(".se-pre-con").fadeOut("slow");
		//alert("here1");
		validateForm();
		searchBtnClicked = true;
		validElements.push("exportBtn");
	});
	
	$("#fromDate").click(function(event) {
		searchBtnClicked = false;
	});
	
	$("#toDate").click(function(event) {
		searchBtnClicked = false;
	});
	
	$("#exportBtn").click(function(event) {
		//alert("test: " + searchBtnClicked);
		if (searchBtnClicked == false){
			alert("Dates are modified. Please click GO first.");
			//return;
		}
	});
});

$(window).load(function() {
	// Animate loader off screen
	//alert("here");
	//alert("here2");
	$(".se-pre-con").fadeOut("slow");
	searchBtnClicked = false;
});


function validateForm(){
	
	var inValidElements = [];			
	var validElements = [];
	
//	Validation in layout.js. If condition will make it non mandatory field
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
	
	displayError(validElements, inValidElements);
}

