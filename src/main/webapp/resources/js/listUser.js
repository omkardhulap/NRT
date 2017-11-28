/**
 * @author omkar_dhulap
 */

$(document).ready(function() {
	fetchUsers() ;
});

function fetchUsers(){
	var dataSet = [];
	var columnHeaders = [];
	var contextRoot = $('#contextRoot').val();

	$.ajax({

		url:  contextRoot + "/fetchUsers",
		type: 'GET',
		data: '',
		contentType: 'application/json',
		async: true,
		success: function(data) {
//			alert('Data Size >>'+data.length);

			//Filling Table Data from server via ajax call
			for(var indexNo = 0; indexNo < data.length; indexNo++){
//				alert("indexNo >> " + data[indexNo].id+ data[indexNo].capabilities.toString()+ data[indexNo].description+ data[indexNo].fromDate+data[indexNo].toDate+ 'omkar', '10/25/2015 00:00:00');

				dataSet[indexNo] = [data[indexNo].id,
				                    data[indexNo].nikeid,
				                    data[indexNo].fname, 
				                    data[indexNo].lname,
				                    data[indexNo].role, 
				                    data[indexNo].status,
				                    data[indexNo].nikeEmail,
				                    data[indexNo].infyEmail];
			}
//			alert("dataSet length>>" +dataSet.length + " dataSet >>" +dataSet);

			//Definig Columns
			columnHeaders = [{ "title": "Id","width": "5%", "render": function (data, type, row, meta) {return '<a href="'+ contextRoot + '/updateUser/' + data + '">' + data + '</a>'; }},
			                 { "title": "User Id","width": "10%" },
			                 { "title": "First Name","width": "10%" },
			                 { "title": "Last Name","width": "10%" },
			                 { "title": "Role","width": "10%" },
			                 { "title": "Active","width": "5%" },
			                 { "title": "Nike Email","width": "25%" },
			                 { "title": "Infosys Email","width": "25%" }];


			//Table creation logic
			$('#listusersTableDiv').html( '<table cellpadding="0" cellspacing="0" border="0" width="100%" class="display" id="listusersTable"></table>' );

			$('#listusersTable').dataTable( {
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

		},
		error: function(jqXHR, textStatus, errorThrown) {
			alert("Error >> "+jqXHR.status + ' ' + jqXHR.responseText);
		}
	});
}