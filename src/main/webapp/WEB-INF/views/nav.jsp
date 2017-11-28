<style type="text/css">
a.disabled {
   pointer-events: none;
   cursor: default;
   color: black; 
   font-style: italic;
}
</style>
<div class="container-fluid">
      <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
          <ul class="nav nav-sidebar">
            
            <li><a href="${pageContext.request.contextPath}/home">Dashboard</a></li>
			
			<li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>Outage Management</h3>
					<div>
						<p><a href="${pageContext.request.contextPath}/plannedOutage">Add Planned Outage</a></p>
						<p><a href="${pageContext.request.contextPath}/unplannedOutage">Add Unplanned Outage</a></p>
						<p><a href="${pageContext.request.contextPath}/searchOutage">Search/Edit Outage</a></p>
					</div>
				</div>
			</li>
			
			<li>
				<div id="customAccordion">					
					<h3>Effort Management</h3>
					<div>						
						<p><a href="${pageContext.request.contextPath}/manageEffort">Add Effort</a></p>
						<p><a href="${pageContext.request.contextPath}/uploadEffort">Upload Effort</a></p>
						<p><a href="${pageContext.request.contextPath}/searchEffort">Search/Edit Effort</a></p>
					</div>
				</div>
			</li>
			<li>
				<div id="customAccordion">					
					<h3>MTTR Management</h3>
					<div>						
						<p><a href="${pageContext.request.contextPath}/uploadMTTR">Upload SNOW Data</a></p>
						<p><a href="${pageContext.request.contextPath}/searchMTTR">MTTR Analysis</a></p>
						<p><a href="${pageContext.request.contextPath}/searchMTTRIncidents">Incident Analysis</a></p>						
					</div>
				</div>
			</li>
			<li>
				<div id="customAccordion">
					<h3>Reports</h3>
					<div>						
						<p><a href="${pageContext.request.contextPath}/effortReports">All Reports</a></p>
					</div>
				</div>
			</li>
			
			
			<%-- 
			
			<li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>Problem Management</h3>
					<div>						
						<p><a href="${pageContext.request.contextPath}/manageProblem">Add PM</a></p>						
						<p><a href="${pageContext.request.contextPath}/searchPM">Search/Edit PM</a></p>
					</div>
				</div>
			</li>
			
			<li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>Highlights</h3>
					<div>						
						<p><a href="#" class="disabled">Highlights Management</a></p>						
						<!-- <p><a href="${pageContext.request.contextPath}/manageHighlights">Highlights Management</a></p>-->						
					</div>
				</div>
			</li>
			
			<li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>Incident Analysis</h3>
					<div>
						<p><a href="#" class="disabled">Upload Data</a></p>						
						<p><a href="#" class="disabled">Generate Metrics</a></p>
					</div>
				</div>
			</li>
			
			<li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>Defect Analysis</h3>
					<div>
						<p><a href="#" class="disabled">Upload Data</a></p>						
						<p><a href="#" class="disabled">Generate Metrics</a></p>
					</div>
				</div>
			</li>
			
            <li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>Reports</h3>
					<div>
						<p><a href="#" class="disabled">Generate Report</a></p>
					</div>
				</div>
			</li>
			
			--%>
			
            <li>
				<div id="customAccordion">
					<!-- First accordion menu item-->
					<h3>User Management</h3>
					<div>
						<p><a href="${pageContext.request.contextPath}/AddUser">Add User</a></p>
						<p><a href="${pageContext.request.contextPath}/listusers">View Users</a></p>
						<!-- <p><a href="#" class="disabled">Role Management</a></p> -->
					</div>
				</div>
			</li>
			
			
          </ul>
        </div>
   </div>
   </div>
        