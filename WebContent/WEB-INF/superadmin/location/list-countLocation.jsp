<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="ro" prefix="og: http://ogp.me/ns#" class="translated-ltr">

<head>
  <!-- Required meta tags -->
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>WRZone AdminUserList</title>
<!-- base:css -->
  <link rel="stylesheet" href="adminresources/vendors/typicons/typicons.css">
  <!-- inject:css -->
  <link rel="stylesheet" href="adminresources/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="adminresources/images/logo-image.png" />


        <style>
      .fixed_header {
        overflow-y: auto; 
      height: 110px;
      }
      .fixed_header thead th { 
      position: sticky; 
      top: 0; 
    } 
      .fixed_header tbody {
        display: block;
        width: 100%;
        overflow: auto;
        height: 400px;
      }
      .fixed_header thead tr {
        display: block;
      }
      .fixed_header thead {
        background: green;
        color: #fff;
         
      }
      .fixed_header th,
      .fixed_header td {
      border: 1px solid #797878;
        padding: 5px;
        text-align: left;
        width: 200px;
      }
    </style>
</head>
<body>
<%
//allow access only if session exists
String user = null;
if(session.getAttribute("email") == null){
	response.sendRedirect("index.jsp");
}else user = (String) session.getAttribute("email");
String userName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
	if(cookie.getName().equals("email")) userName = cookie.getValue();
	if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}else{
	sessionID = session.getId();
}
%>

  <%@page import="java.io.OutputStream"%>
        

    <div class="container-scroller">
<!-- NavBar -->
    <!-- partial:partials/_navbar.html -->
    <nav class="navbar col-lg-12 col-12 p-0 fixed-top d-flex flex-row">
        <div class="navbar-brand-wrapper d-flex justify-content-center">
          <div class="navbar-brand-inner-wrapper d-flex justify-content-between align-items-center w-100">
            <a class="navbar-brand brand-logo" href="index.jsp"><img src="adminresources/images/logo-image.png" alt="logo"/></a>
            <a class="navbar-brand brand-logo-mini" href="index.jsp"><img src="adminresources/images/logo-image.png" alt="logo"/></a>
            <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-toggle="minimize">
              <span class="typcn typcn-th-menu"></span>
            </button>
          </div>
        </div>
        <div class="navbar-menu-wrapper d-flex align-items-center justify-content-end">
          <ul class="navbar-nav mr-lg-2">
            <li class="nav-item nav-profile dropdown">
              <a class="nav-link" href="#" data-toggle="dropdown" id="profileDropdown">
                <img src="adminresources/images/logo-image.png" alt="profile"/>
          
                 <span class="nav-profile-name"><c:out value="${nameUser }"></c:out></span>
              </a>
              <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="profileDropdown">
               
                <a class="dropdown-item" href="LoginOut.htm">
                  <i class="typcn typcn-eject text-primary"></i>
                  Logout
                </a>
              </div>
            </li>
            <li class="nav-item nav-user-status dropdown">
                <p id="demo"><p> / </p></p><p id="month"><p> / </p></p><p id="year">
            </li>
          </ul>
          <ul class="navbar-nav navbar-nav-right">
           
           
             <script type="text/javascript">
              var days = ['Duminica', 'Luni', 'Marti', 'Miercuri', 'Joi', 'Vineri', 'Sambata'];
       var d = new Date();
       var dayName =d.getDate()+" " + days[d.getDay()];
       var year = new Date().getFullYear();
       var monthName = d.toLocaleString('default', { month: 'long' });
       document.getElementById("demo").innerHTML = dayName;
       document.getElementById("month").innerHTML = monthName;
       document.getElementById("year").innerHTML = year;
       </script>   
          </ul>
          <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-toggle="offcanvas">
            <span class="typcn typcn-th-menu"></span>
          </button>
        </div>
      </nav>
      <!-- partial -->  
	<!-- NavBar -->
	 <div class="container-fluid page-body-wrapper">
  <!-- partial:partials/_settings-panel.html -->
        <div class="theme-setting-wrapper">
            <div id="settings-trigger"><i class="typcn typcn-cog-outline"></i></div>
            <div id="theme-settings" class="settings-panel">
              <i class="settings-close typcn typcn-times"></i>
              <p class="settings-heading">SIDEBAR SKINS</p>
              <div class="sidebar-bg-options selected" id="sidebar-light-theme"><div class="img-ss rounded-circle bg-light border mr-3"></div>Light</div>
              <div class="sidebar-bg-options" id="sidebar-dark-theme"><div class="img-ss rounded-circle bg-dark border mr-3"></div>Dark</div>
              <p class="settings-heading mt-2">HEADER SKINS</p>
              <div class="color-tiles mx-0 px-4">
                <div class="tiles success"></div>
                <div class="tiles warning"></div>
                <div class="tiles danger"></div>
                <div class="tiles info"></div>
                <div class="tiles dark"></div>
                <div class="tiles default"></div>
              </div>
            </div>
          </div>
          
          <!-- partial -->
          <!-- partial:partials/_sidebar.html -->
          <nav class="sidebar sidebar-offcanvas" id="sidebar" >
            <ul class="nav">
              <li class="nav-item">
                <a class="nav-link" data-toggle="collapse" href="#ui-basic" aria-expanded="false" aria-controls="ui-basic">
                  <i class="typcn typcn-document-text menu-icon"></i>
                  <span class="menu-title">Contul Meu</span>
                  <i class="menu-arrow"></i>
                </a>
              <div class="collapse" id="ui-basic">
                  <ul class="nav flex-column sub-menu">
                    <li class="nav-item"> <a class="nav-link" href="<c:url value="view-company.htm"></c:url>">Profil Societate</a></li>
                  <li class="nav-item"> <a class="nav-link" href="<c:url value="user-list.htm"></c:url>">Utilizatori Sistem</a></li>
                    <li class="nav-item"> <a class="nav-link" href="home-superadmin.htm"> ListaLocatii</a></li>
                </ul>
                </div>
              </li>
              <li class="nav-item">
                <a class="nav-link"  href="LoginOut.htm" aria-expanded="false" aria-controls="auth">
                  <i class="typcn typcn-user-add-outline menu-icon"></i>
                  <span class="menu-title">LoginOut</span>
                  <i class="menu-arrow"></i>
                </a>
               
              </li>  
            </ul>
          </nav>
          <!-- partial -->
  <!-- endNavbar -->
  


     <div class="main-panel">
      
       <div class="content-wrapper">
          <div class="row">
  <div class="col-lg-12 grid-margin stretch-card">
                   <div class="card">
       <p style="color:red">Selectati din lista, luna pe care doriti sa o vizualizati :</p>             
                   <table class="fixed_header">
                      <thead>
                        <tr>
                          <th >Id</th>
                        </tr>
                      </thead>
                      <tbody>                     
                      <c:forEach var= "loc" items="${locationList}">
                        <tr>                                                   
                          <td>
                          <a class="button badge badge-success" href ="<c:url value='view-countList.htm?id=${loc.idLocation}&month=${loc.month}&year=${loc.year}'/>">
                        <c:out value="${loc.month}"></c:out>/<c:out value="${loc.year}"></c:out></a>												
                          </td>                     
                        </tr>
                         </c:forEach>          
                      </tbody>
                      <tfoot>
                      <tr>                      
                      </tr>
                      </tfoot>
                    </table>
                   
                 </div>                  
                </form>         
</div>
<div class="card">
                   
                   
                 </div>

  </div> 
  </div>
  </div>        
  </div>
<!-- Footer -->
         <footer class="footer">
            <div class="card">
                <div class="card-body">
                    <div class="d-sm-flex justify-content-center justify-content-sm-between">
                        <div class="container copyright">
                       <p >© Copyrights <span id="year1"></span> <a href="#">WRZoneAPP</a>. All rights reserved.</p>
                           </div>
                    </div>
                </div>    
            </div>        
        </footer>
        </div>
  <!-- endinject -->
 <!-- Plugin js for this page-->
 <script type="text/javascript">
  document.getElementById("year1").innerHTML = new Date().getFullYear();
  </script>
  <script src="adminresources/vendors/chart.js/Chart.min.js"></script>
  <!-- End plugin js for this page-->
  <!-- base:js -->
  <script src="adminresources/vendors/js/vendor.bundle.base.js"></script>
  <!-- inject:js -->
  <script src="adminresources/js/off-canvas.js"></script>
  <script src="adminresources/js/hoverable-collapse.js"></script>
  <script src="adminresources/js/template.js"></script>
  <script src="adminresources/js/settings.js"></script>
  <script src="adminresources/js/todolist.js"></script>
  <!-- endinject -->
   <!-- Custom js for this page-->
   <script src="adminresources/js/dashboard.js"></script>
   <!-- End custom js for this page-->
   
</body>
</html>
