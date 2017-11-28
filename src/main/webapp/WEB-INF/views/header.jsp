<style type="text/css">
a.disabled {
   pointer-events: none;
   cursor: default;
   color: black; 
   font-style: italic;
}
</style>
<nav class="navbar navbar-inverse navbar-fixed-top" >
  <div class="container-fluid">
    <div class="navbar-header">
      <img alt="" class="navbar-brand" src="${pageContext.request.contextPath}/resources/images/swoosh_small_blk_back.jpg"  width="75" height="50">
      <a class="navbar-brand" href="${pageContext.request.contextPath}/home">Nike Reporting</a>
    </div>
    <div id="navbar" class="navbar-collapse collapse">    
      <ul class="nav navbar-nav navbar-right">
            <!-- <li><a href="#" class="disabled">Settings</a></li>  -->
            <li><a href="#" class="disabled">Connected To:&nbsp;PRODUCTION</a></li>
            <li><a href="#" class="disabled">Welcome,&nbsp;${AuthenticatedUser}</a></li>
            <li><a href='${pageContext.request.contextPath}/logout'>Logout</a></li>      
      </ul>
      <!--
      <form class="navbar-form navbar-right">
         <input type="text" class="form-control" placeholder="Search...">  
      </form>
      -->
    </div>
  </div>
</nav>