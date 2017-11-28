/**
 * @author sachin_ainapure
 */
var gridData;
$(document).ready(function() {
	
	if(event != null){
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
	}
	if(!validateForm()){
		searchMTTRIncidents();
	}
	
	$("#searchButton").click(function(event) {
		if(event != null){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}
		if(!validateForm()){
			searchMTTRIncidents();
		}
	});
	
	$("#exportBtn").click(function(event) {	
		if(event != null){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}
		if(validateForm()){
		}else{
			searchMTTRIncidents();
			download2Excel();
		}
	});
});

function validateForm(){
   	var inValidElements = [];			
	var validElements = [];
	
	//Validation in layout.js. If condition will make it non mandatory field
	if($("#fromDate").val() || $("#toDate").val()){
		var ErrorArray = isValideStartEndDate($("#fromDate").val(), $("#toDate").val());
		ErrorArray.forEach(function(item) {
			//alert("Hi 1"+ item);
			if(item.indexOf("startDate|") >= 0){
				inValidElements.push("fromDate");
				$("span", $('#fromDate').parent()).text(item.substr("startDate|".length,item.length));
			}else{
				validElements.push("fromDate");
			}
			if(item.indexOf("endDate|") >= 0){
				inValidElements.push("toDate");
				$("span", $('#toDate').parent()).text(item.substr("endDate|".length,item.length));
			}else{
				validElements.push("toDate");
			}
		});
	}
	
	displayError(validElements, inValidElements);
	
	if(inValidElements.length > 0){
		return true;
	}
	else{
		return false;
	}
}





function searchMTTRIncidents(){	
	var dataSet = [];
	var columnHeaders = [];
	var contextRoot = $('#contextRoot').val();
	
	var masterData = {};
	$(":input[class~='datepicker']").each(function() {
			masterData[this.name] = this.value;
			//alert(this.value);
	});
	
	$.ajax({

		url:  contextRoot + "/searchMTTRIncidents/search",
		type: 'GET',
		data: JSON.stringify(masterData),
		contentType: 'application/json',
		async: true,
		success: function(data) {
			gridData = data;
			//alert('Data Size >>'+data.length);
			//alert('Data Size >>'+data.length);
			//Filling Table Data from server via ajax call
			for(var indexNo = 0; indexNo < data.length; indexNo++){
				//alert("indexNo >> " + data[indexNo].assignmentgroup.toString()+ data[indexNo].priority.toString()+ data[indexNo].pes_sla+ data[indexNo].average_hrs+data[indexNo].total_incidents+ 'sachin', '10/25/2015 00:00:00');
				//alert("indexNo >> " + data[indexNo].assignmentgroup+ data[indexNo].priority+ data[indexNo].pes_sla+ data[indexNo].average_hrs+data[indexNo].total_incidents+ 'sachin', '10/25/2015 00:00:00');

				dataSet[indexNo] = [data[indexNo].mttrId,
				                    data[indexNo].snowId,
				                    data[indexNo].priority,
				                    data[indexNo].assignmentGroup, 
				                    data[indexNo].assignedTo,
				                    data[indexNo].createToResolveInHours, 
				                    data[indexNo].mttrBreachReason,
				                    data[indexNo].remarks];
			}
			//alert("dataSet length>>" +dataSet.length + " dataSet >>" +dataSet);

			//Definig Columns
			//"title": "Assignment Group","width": "20%", "render": function (data, type, row, meta) {return '<a href="'+ contextRoot + '/updateUser/' + data + '">' + data + '</a>'; }},
			columnHeaders = [{ "title": "Id #","width": "5%", "render": function (data, type, row, meta) {return '<a href="'+ contextRoot + '/searchMTTRIncident/' + data + '">' + data + '</a>'; }},
			                 { "title": "SNOW #","width": "5%"},
			                 { "title": "Priority","width": "7%" },
			                 { "title": "Assignment Group","width": "10%"},
			                 { "title": "Assigned To","width": "20%"},
			                 { "title": "MTTR (Hrs)","width": "7%" },
			                 { "title": "High MTTR Reason","width": "14%"},
			                 { "title": "Remarks (if any)","width": "15%" }];


			//Table creation logic
			$('#searchMTTRIncidentsTableDiv').html( '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="display" id="searchMTTRIncidentsTable"></table>' );

			$('#searchMTTRIncidentsTable').dataTable( {
				"data": dataSet,
				"columns":columnHeaders,
				"bAutoWidth": true,
				"sScrollX": "100%",   
				"sScrollXInner": "100%",
				dom: 'T<"clear">lfrtip',
				tableTools: {
					"sSwfPath": contextRoot + "/resources/js/copy_csv_xls_pdf.swf",
					"aButtons": [  ]
				}
			} );

		},
		error: function(jqXHR, textStatus, errorThrown) {
			//alert("Error >> "+jqXHR.status + ' ' + jqXHR.responseText);
			$('#mttrMessage').text("Error occured while searching MTTR Incidents. Please contact support team.");
		    $('#mttrMessage').removeClass("hide").addClass("text-error");
		}
	});
}

function download2Excel(){
	var contextRoot = $('#contextRoot').val();
	var http = new XMLHttpRequest();
	var filename;
	var blob;
	
	//http.onreadystatechange = hand;
	var url = contextRoot + "/searchMTTRIncidents/searchExport";
	//alert("here1");
	var params = "fromDate="+$('#fromDate').val()+"&toDate="+$('#toDate').val();
	//alert(params);
	http.open("GET", url+"?"+params, true);
	http.responseType = 'blob';
	
	//Send the proper header information along with the request
	http.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	//alert("here2");
	http.onreadystatechange = function() {//Call a function when the state changes.
		//alert(http.readyState + http.status);
	    if(http.readyState == 4 && http.status == 200) {
	    	var disposition = http.getResponseHeader('Content-Disposition');
	        //alert(disposition);
	        if (disposition && disposition.indexOf('attachment') !== -1) {
	            var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
	            var matches = filenameRegex.exec(disposition);
	            if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
	        }
	    	
	        blob = new Blob([http.response], { type: 'application/vnd.ms-excel' });
	        var URL = window.URL || window.webkitURL;
            var downloadUrl = URL.createObjectURL(blob);
            //alert(downloadUrl);
            //window.location = downloadUrl;
            
            if (filename) {
                // use HTML5 a[download] attribute to specify filename
                var a = document.createElement("a");
                //alert(typeof a.download);
                // safari doesn't support this yet
                if (typeof a.download === 'undefined') {
                	alert("Not supported on IE");
                    window.location = downloadUrl;                	
                } else {
                	//alert("2.2");
                    a.href = downloadUrl;
                    a.download = filename;
                    //alert(downloadUrl);
                    //alert(filename);
                    //alert(a);
                    document.body.appendChild(a);
                    a.click();
                }
            } else {
            	//alert("3");
                window.location = downloadUrl;
            }
            setTimeout(function () { URL.revokeObjectURL(downloadUrl); }, 100); // cleanup
	    }
	};
	http.send(null);
}
