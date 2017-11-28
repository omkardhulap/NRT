/**
 * @author : omkar_dhulap
 */
var dataSet = [];
var contextRoot;

$(document).ready(function() {
	
	contextRoot = $('#contextRoot').val();
	$("#Search_Heading").click();
	
	$("#Add").click(function(event) {
		if(validateInputForm()){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}else{
			addHighlight();
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}
	});
	
	$("#resetInput").click(function(event){
		resetAddForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
	$("#Search").click(function(event){
		if(validateSearchCriteria()){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}else{
    		searchHighlights();
    		event.preventDefault ? event.preventDefault() : event.returnValue = false;
    	}
    });
	
	$("#Edit").click(function(event){
		populateSelectedHighlight();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
	$("#Delete").click(function(event){
		removeSelectedHighlight();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
	$("#Generate").click(function(event) {
		//TODO: call PPT generation flow from here
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
	});
	
	$("#resetSearch").click(function(event){
		resetSearchForm();
    	event.preventDefault ? event.preventDefault() : event.returnValue = false;
    });
	
});

//To Collapse Add and Search Panels
$(document).on('click', '.panel-heading', function(e){
    var $this = $(this);
	if(!$this.hasClass('panel-collapsed')) {
		$this.parents('.panel').find('.panel-body').slideUp();
		$this.addClass('panel-collapsed');
		//$this.find('i').removeClass('glyphicon-chevron-up').addClass('glyphicon-chevron-down');
	} else {
		$this.parents('.panel').find('.panel-body').slideDown();
		$this.removeClass('panel-collapsed');
		//$this.find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
	}
});

function validateInputForm() {
	var inValidElements = [];			
	var validElements = [];
	
	var ErrorArray = isValideStartEndDate($("#fromDate").val(), $("#toDate").val());
	ErrorArray.forEach(function(item) {
		//alert("Hi "+ item);
		if(item.indexOf("startDate|") >= 0){
			inValidElements.push("fromDate");
			$("span", $('#fromDate').parent()).text(item.substr("startDate|".length,item.length));
		}
		if(item.indexOf("endDate|") >= 0){
			inValidElements.push("toDate");
			$("span", $('#toDate').parent()).text(item.substr("endDate|".length,item.length));
		}
	});
	
	($("select[id='capabilityMultiselect'] option:selected").length >0) ?validElements.push("capabilityMultiselect"):inValidElements.push("capabilityMultiselect");
	
	($("#description").val()) ?validElements.push("description"):inValidElements.push("description");
	
	displayError(validElements, inValidElements);
	
	if(inValidElements.length > 0)
		return true;
	else
		return false;
		
}

function validateSearchCriteria() {
	var inValidElements = [];			
	var validElements = [];
	var isSearchCriteriaError = false;
	
	//Validation in layout.js. If condition will make it non mandatory field
	if($("#fromDateSearch").val() || $("#toDateSearch").val()){
		var ErrorArray = isValideStartEndDate($("#fromDateSearch").val(), $("#toDateSearch").val());
		ErrorArray.forEach(function(item) {
			//alert("Hi "+ item);
			if(item.indexOf("startDate|") >= 0){
				inValidElements.push("fromDateSearch");
				isSearchCriteriaError = true;
				$("span", $('#fromDateSearch').parent()).text(item.substr("startDate|".length,item.length));
			}
			if(item.indexOf("endDate|") >= 0){
				inValidElements.push("toDateSearch");
				isSearchCriteriaError = true;
				$("span", $('#toDateSearch').parent()).text(item.substr("endDate|".length,item.length));
			}
		});
	}
	
	displayError(validElements, inValidElements);
	
	return isSearchCriteriaError;
}

function resetAddForm(){
	$("#Add").val("Add");
	
	$('.inputFieldErrors').each(function(i, obj) {
		$(this).removeClass("has-error").addClass("hide");
	});
	
	$(":input[class~='addField']").each(function() {
		this.value = "";
	});
	
}

function resetSearchForm(){
	$('.searchFieldErrors').each(function(i, obj) {
		$(this).removeClass("has-error").addClass("hide");
	});
	
	$(":input[class~='searchField']").each(function() {
		this.value = "";
	});
}

function populateSelectedHighlight(){

	$('#highlightTableMessage').text("");
	$('#highlightTableMessage').removeClass("text-error").addClass("hide");

	var table = $('#highlightsTable').DataTable();

	var numberOfSelected = table.rows('.selected').data().length;
	
	if(numberOfSelected == 0){
		$('#highlightTableMessage').text("Please select a highlight to modify");
		$('#highlightTableMessage').removeClass("hide").addClass("text-error");
		$('#Add').scrollView();
	}else if(numberOfSelected > 1){
		$('#highlightTableMessage').text("Only one highlight can be modified at a time");
		$('#highlightTableMessage').removeClass("hide").addClass("text-error");
		$('#Add').scrollView();
	}else if(numberOfSelected == 1){
		resetAddForm();
		$("#Add").val("Update");
		window.scrollTo(0,0);
		if($("#Add_Heading").hasClass('panel-collapsed')){
			$("#Add_Heading").parents('.panel').find('.panel-body').slideDown();
			$("#Add_Heading").removeClass('panel-collapsed');
			//$("#Add_Heading").find('i').removeClass('glyphicon-chevron-down').addClass('glyphicon-chevron-up');
		}
		
		var selectedRowData = table.rows('.selected').data();
		selectedRowData.each(function(value, index) {
			
			$("#highlight_id").val(value[0]);
			$("#description").val(value[2]);
			
			$("#fromDate").val(value[3]);
			$("#toDate").val(value[4]);
			
			var res = value[1].split(',');
			res.forEach(function(item) {
				$('[id=capabilityMultiselect] option').filter(function() {
				    return ($(this).text().trim() == item.trim()); 
				}).prop('selected', true);
			});
		});
	}
}

function removeSelectedHighlight(){
	var table = $('#highlightsTable').DataTable();

	var numberOfSelected = table.rows('.selected').data().length;

	if(numberOfSelected == 0){
		$('#highlightTableMessage').text("Please select a highlight to delete");
		$('#highlightTableMessage').removeClass("hide").addClass("text-error");
		$('#Add').scrollView();
	}else if(numberOfSelected > 1){

		var highlightIdArray = [];
		var masterData = {};

		var selectedRowData = table.rows('.selected').data();
		selectedRowData.each(function(value, index) {
			highlightIdArray.push(value[0]);
		});

		//alert("highlightIdArray size "+highlightIdArray.length);

		masterData['highlightIdArray'] = highlightIdArray;

		$.ajax({

			url:  contextRoot + "/manageHighlights/delete",
			type: 'GET',
			data: JSON.stringify(masterData),
			contentType: 'application/json',
			async: true,
			success: function(data) {
				//alert("data "+data);
				if(data.indexOf("successMessage|") >= 0){
					table.row('.selected').remove().draw( false );//remove rows from UI table in case of success
					
					$('#highlightTableMessage').text(data.substr("successMessage|".length,data.length));
					$('#highlightTableMessage').removeClass("hide").addClass("text-success bg-success");
				}else if(data.indexOf("errorMessage|") >= 0){
					$('#highlightTableMessage').text(data.substr("errorMessage|".length,data.length));
					$('#highlightTableMessage').removeClass("hide").addClass("text-error");
				}else{
					$('#highlightTableMessage').text(data);
					$('#highlightTableMessage').removeClass("hide").addClass("text-error");
				}
				$('#Add').scrollView();
			},
			error: function(jqXHR, textStatus, errorThrown) {
				alert("Error >> "+jqXHR.status + ' ' + jqXHR.responseText);
				$('#highlightTableMessage').text("Error occured while deleting Highlights. Please contact support team.");
				$('#highlightTableMessage').removeClass("hide").addClass("text-error");
				$('#Add').scrollView();
			}
		});
	}

}

function addHighlight(){
	
	$('#highlightEditMessage').removeClass("has-error").addClass("hide");
	
	var masterData = {};
	$(":input[class~='addField']").each(function() {
		var elementType = this.type;
		if(elementType == "select-multiple" || elementType == "textarea"){
			masterData[this.name] = $(this).val();
		}else{
			masterData[this.name] = this.value;
		}			
	});
	
	$.ajax({
	    
	    url:  contextRoot + "/manageHighlights/add",
	    type: 'GET',
	    data: JSON.stringify(masterData),
	    contentType: 'application/json',
	    async: true,
	    success: function(data) {
	    	//alert("data "+data);
	    	if(data.indexOf("successMessage|") >= 0){
	    		$('#highlightEditMessage').text(data.substr("successMessage|".length,data.length));
			    $('#highlightEditMessage').removeClass("hide").addClass("text-success bg-success");
	    	}else if(data.indexOf("errorMessage|") >= 0){
	    		 $('#highlightEditMessage').text(data.substr("errorMessage|".length,data.length));
			     $('#highlightEditMessage').removeClass("hide").addClass("text-error");
	    	}else{
	    		$('#highlightEditMessage').text(data);
			    $('#highlightEditMessage').removeClass("hide").addClass("text-error");
	    	}
	    	
	    	resetAddForm();
	    },
	    error: function(jqXHR, textStatus, errorThrown) {
	        alert("Error >> "+jqXHR.status + ' ' + jqXHR.responseText);
	        $('#highlightEditMessage').text("Error occured while searching Highlights. Please contact support team.");
		    $('#highlightEditMessage').removeClass("hide").addClass("text-error");
	    }
	});
}

function searchHighlights(){
	
	$('#highlightTableMessage').removeClass("has-error").addClass("hide");
	 
	var masterData = {};
	$(":input[class~='searchField']").each(function() {
		var elementType = this.type;
		if(elementType == "select-multiple"){
			masterData[this.name] = $(this).val();
		}else{
			masterData[this.name] = this.value;
		}				
	});
	
	var columnHeaders = [];
	dataSet.length = 0;

	$.ajax({
		    
		    url:  contextRoot + "/manageHighlights/search",
		    type: 'GET',
		    data: JSON.stringify(masterData),
		    contentType: 'application/json',
		    async: true,
		    success: function(data) {
		    	//alert('Data Size >>'+data.length);
		    	
		    	//Filling Table Data from server via ajax call
		    	for(var indexNo = 0; indexNo < data.length; indexNo++){
		    		//alert("indexNo >> " + data[indexNo].id+ data[indexNo].capabilities.toString()+ data[indexNo].description+ data[indexNo].fromDate+data[indexNo].toDate+ 'omkar', '10/25/2015 00:00:00');
		    		
		    		dataSet[indexNo] = [data[indexNo].id, data[indexNo].capabilities.toString() , data[indexNo].description, 
		    		                    getFormatedDate(data[indexNo].fromDate), getFormatedDate(data[indexNo].toDate), data[indexNo].updatedBy, getFormatedDate(data[indexNo].updatedDate)];
		    	}
		    	//alert("dataSet length>>" +dataSet.length + " dataSet >>" +dataSet);
		    	
		    	//Definig Columns
		    	columnHeaders = [{ "title": "Id","width": "3%" },{ "title": "Capability","width": "13%" },{ "title": "Description","width": "42%" },
		    	                 { "type": "date", "title": "Start Date","width": "11%" },
				                 { "type": "date", "title": "End Date","width": "11%"  },
				                 { "title": "Modified By","width": "9%" },
				                 { "type": "date", "title": "Modified On","width": "11%" }];
		    	
		    	
		    	//Table creation logic
		    	$('#highlightsTableDiv').html( '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="display" id="highlightsTable"></table>' );
		    	
		        $('#highlightsTable').dataTable( {
		            "data": dataSet,
		            "columns":columnHeaders,
		            "bAutoWidth": true,
		            "sScrollX": "100%",   
		            "sScrollXInner": "100%",
		            dom: 'T<"clear">lfrtip',
		            tableTools: {
		                "sSwfPath": contextRoot + "/resources/js/copy_csv_xls_pdf.swf",
		                "aButtons": [ "print" ]
		            }
		        } );
		        
		        $('#highlightsTable tbody').on( 'click', 'tr', function () {
		             if ( $(this).hasClass('selected') ) {
		                 $(this).removeClass('selected');
		             }else {
		                 $(this).addClass('selected');
		             }
		         } );
		        
		        $('#Edit').removeClass("hide");
		        $('#Delete').removeClass("hide");
		        $('#Add').scrollView();
		    },
		    error: function(jqXHR, textStatus, errorThrown) {
		        //alert("Error >> "+jqXHR.status + ' ' + jqXHR.responseText);
		        $('#highlightTableMessage').text("Error occured while searching Highlights. Please contact support team.");
		        $('#highlightTableMessage').removeClass("hide").addClass("text-error");
		        $('#Add').scrollView();
		    }
	});
}




