/**
 * @author sachin_ainapure
 */
var gridData;
$(document).ready(function() {
	if(event != null){
		event.preventDefault ? event.preventDefault() : event.returnValue = false;
	}
	if(validateForm()){
	}else{
		searchMTTR();
	}
	
	$("#searchButton").click(function(event) {	
		if(event != null){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}
		if(validateForm()){
		}else{
			searchMTTR();
		}
	});
	
	$("#exportBtn").click(function(event) {	
		if(event != null){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}
		if(validateForm()){
		}else{
			//exportMTTR();
			//alert($('#contextRoot').val());
			//var filename = "admin_MTTRAnalysisReport.xls";			
			//downloadFile("/searchMTTR/search", filename);
			searchMTTR();
			download2Excel();
		}
	});
	
	
	
	
	/*$("#exportBtn").click(function(event) {	
		if(event != null){
			event.preventDefault ? event.preventDefault() : event.returnValue = false;
		}
		JSONToCSVConvertor('searchMTTRTable','MTTRAnalysis', true);
	});
	*/
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
		//alert("true");
		return true;
	}
	else{
		//alert("false");
		return false;		
	}
}


function searchMTTR(){	
	var dataSet = [];
	var columnHeaders = [];
	var contextRoot = $('#contextRoot').val();
	
	var masterData = {};
	$(":input[class~='datepicker']").each(function() {
			masterData[this.name] = this.value;
			//alert(this.value)
	});
	
	$.ajax({

		url:  contextRoot + "/searchMTTR/search",
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

				dataSet[indexNo] = [data[indexNo].assignmentgroup,
				                    data[indexNo].priority,
				                    data[indexNo].pes_sla, 
				                    data[indexNo].average_hrs,
				                    data[indexNo].total_incidents, 
				                    data[indexNo].lt_sla,
				                    data[indexNo].gt_sla,
				                    data[indexNo].percentSLA_achieved];
			}
			//alert("dataSet length>>" +dataSet.length + " dataSet >>" +dataSet);

			//Definig Columns
			//"title": "Assignment Group","width": "20%", "render": function (data, type, row, meta) {return '<a href="'+ contextRoot + '/updateUser/' + data + '">' + data + '</a>'; }},
			columnHeaders = [{ "title": "Assignment Group","width": "30%"},
			                 { "title": "Priority","width": "10%" },
			                 { "title": "PES SLA","width": "10%"},
			                 { "title": "Average Hrs","width": "10%"},
			                 { "title": "Total Incidents","width": "10%" },
			                 { "title": "<= SLA","width": "10%" },
			                 { "title": "> SLA","width": "10%" },
			                 { "title": "% MTTR SLA Achieved","width": "10%" }];


			//Table creation logic
			$('#searchMTTRTableDiv').html( '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="display" id="searchMTTRTable"></table>' );

			$('#searchMTTRTable').dataTable( {
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
			$('#mttrMessage').text("Error occured while searching MTTR. Please contact support team.");
		    $('#mttrMessage').removeClass("hide").addClass("text-error");
		}
	});
}


function exportMTTR(){	
	var dataSet = [];
	var columnHeaders = [];
	var contextRoot = $('#contextRoot').val();
	var filename = "";
	
	var masterData = {};
	$(":input[class~='datepicker']").each(function() {
			masterData[this.name] = this.value;
			//alert(this.value);
	});
	
	$.ajax({

		url:  contextRoot + "/searchMTTR/searchExport",
		type: 'GET',
		dataType : 'text',
		data: JSON.stringify(masterData),
		contentType: 'application/octet-stream;base64', //TODO: CHECK THIS OUT
		async: true,
		success: function(response, status, data) {
			//alert(data);
			//alert("success >> "+data.status);
			// check for a filename
			
	        var disposition = data.getResponseHeader('Content-Disposition');
	        //alert(disposition);
	        if (disposition && disposition.indexOf('attachment') !== -1) {
	            var filenameRegex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
	            var matches = filenameRegex.exec(disposition);
	            if (matches != null && matches[1]) filename = matches[1].replace(/['"]/g, '');
	        }

	        var type = data.getResponseHeader('Content-Type');
	        var blob = new Blob([response], { type: type });
	        
	        if (typeof window.navigator.msSaveBlob !== 'undefined') {
	            // IE workaround for "HTML7007: One or more blob URLs were revoked by closing the blob for which they were created. These URLs will no longer resolve as the data backing the URL has been freed."
	        	window.navigator.msSaveBlob(blob, filename);
	        } else {
	            var URL = window.URL || window.webkitURL;
	            var downloadUrl = URL.createObjectURL(blob);
	            
	            
	            //alert("downloadUrl: " + downloadUrl);
	            //alert("downloadUrl: " + $.base64Decode(downloadUrl));
	            
	            
	            if (filename) {
	            	//alert("2");
	                // use HTML5 a[download] attribute to specify filename
	                var a = document.createElement("a");
	                // safari doesn't support this yet
	                if (typeof a.download === 'undefined') {
	                	//alert("2.1");
	                    window.location = downloadUrl;
	                } else {
	                	alert("2.2");
	                    a.href = downloadUrl;	                    
	                    a.download = filename;
	                    //alert(downloadUrl);
	                    //alert(filename);
	                    alert(a);
	                    
	                    document.body.appendChild(a);
	                    a.click();
	                }
	            } else {
	            	alert("3");
	                window.location = downloadUrl;
	            }

	            setTimeout(function () { URL.revokeObjectURL(downloadUrl); }, 100); // cleanup
	        }
		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("Error >> "+jqXHR.status);
			alert("Error >> " + jqXHR.responseText);
			$('#mttrMessage').text("Error occured while exporting MTTR. Please contact support team.");
		    $('#mttrMessage').removeClass("hide").addClass("text-error");
		}
	});
	
	
}

function JSONToCSVConvertor(JSONData, ReportTitle, ShowLabel) {
	JSONData = gridData;
	
    //If JSONData is not an object then JSON.parse will parse the JSON string in an Object
    var arrData = typeof JSONData != 'object' ? JSON.parse(JSONData) : JSONData;
    
    var CSV = '';    
    //Set Report title in first row or line
    
    //CSV += ReportTitle + '\r\n\n';

    //This condition will generate the Label/Header
    if (ShowLabel) {
        var row = "";
        
        //This loop will extract the label from 1st index of on array
        for (var index in arrData[0]) {
            //Now convert each value to string and comma-seprated
        	//row += index + ',';
        	if(index == "mttrId"){
        		//row += index + ',';
        	} else if(index == "assignmentgroup"){
        		row += "Assignment Group" + ',';
        	} else if(index == "priority"){
        		row += "Priority" + ',';
        	} else if(index == "pes_sla"){
        		row += "PES SLA" + ',';
        	} else if(index == "average_hrs"){
        		row += "Average Hrs" + ',';
        	} else if(index == "total_incidents"){
        		row += "Total Incidents" + ',';
        	} else if(index == "lt_sla"){
        		row += "<= SLA" + ',';
        	} else if(index == "gt_sla"){
        		row += "> SLA" + ',';
        	}  else if(index == "percentSLA_achieved"){
        		row += "% MTTR SLA Achieved" + ',';
        	} else{
        		row += index + ',';
        	}  	
        }

        row = row.slice(0, -1);
        
        //append Label row with line break
        CSV += row + '\r\n';
    }
    
    //1st loop is to extract each row
    for (var i = 0; i < arrData.length; i++) {
        var row = "";
        
        //2nd loop will extract each column and convert it in string comma-seprated
        //alert(arrData[i]);
        for (var index in arrData[i]) {
            //row += '"' + arrData[i][index] + '",';
        	if(index != "mttrId"){
        		row += '"' + arrData[i][index] + '",';
        	}
        }

        row.slice(0, row.length - 1);
        
        //add a line break after each row
        CSV += row + '\r\n';
    }

    if (CSV == '') {        
        alert("Invalid data");
        return;
    }   
    
    //Generate a file name
    var fileName = ReportTitle.replace(/ /g,"_");
    //this will remove the blank-spaces from the title and replace it with an underscore
    //fileName += ReportTitle.replace(/ /g,"_");   
    
    //Initialize file format you want csv or xls
    //var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);
    var uri = 'data:text/csv;charset=utf-8,' + escape(CSV);
    // Now the little tricky part.
    // you can use either>> window.open(uri);
    // but this will not work in some browsers
    // or you will not get the correct file extension
    
	//this trick will generate a temp <a /> tag
    var link = document.createElement("a");
    link.href = uri;
    
    //set the visibility hidden so it will not effect on your web-layout
    link.style = "visibility:hidden";
    link.download = fileName + ".csv";
    
    //this part will append the anchor tag and remove it after automatic click
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
	 
}


function downloadFile(url, filename){
	var xhr = new XMLHttpRequest();
	xhr.responseType = 'blob';
	$.each(SERVER.authorization(), function(k, v) {
	    xhr.setRequestHeader(k, v);
	});
	xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');
	//alert(xhr);
	xhr.onreadystatechange = function(e) {
		//alert("here2");
	    preloader.modal('hide');
	    if (xhr.status == 200) {
	        var blob = new Blob([this.response], {type: 'application/vnd.ms-excel'});
	        var downloadUrl = URL.createObjectURL(blob);
	        var a = document.createElement("a");
	        a.href = downloadUrl;
	        a.download = filename;
	        document.body.appendChild(a);
	        a.click();
	    } else {
	        alert('Unable to download excel.')
	    }
	};
	xhr.open('GET', url, true);
	xhr.send(JSON.stringify(jsonData));
}




function download2Excel(){
	var contextRoot = $('#contextRoot').val();
	var http = new XMLHttpRequest();
	var filename;
	var blob;
//	alert(contextRoot);
//	try {
//		http.responseType = 'blob';
//	} catch (e) {
//		alert(e.message);
//	}
	
	
	//http.onreadystatechange = hand;
	var url = contextRoot + "/searchMTTR/searchExport";
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