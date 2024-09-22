<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
       <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="ro" prefix="og: http://ogp.me/ns#" class="translated-ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Modal Location</title>
 <!-- base:css -->
  <link rel="stylesheet" href="adminresources/vendors/typicons/typicons.css">
  <link rel="stylesheet" href="adminresources/vendors/css/vendor.bundle.base.css">
  <!-- endinject -->
  <!-- inject:css -->
  <link rel="stylesheet" href="adminresources/css/vertical-layout-light/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="adminresources/images/favicon.png" />
</head>
<body>
<%@page import="java.io.OutputStream"%>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<!-- Modal edit Location -->
  <div class="modal fade" id="EditLocation" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Edit Location</h4>
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="editlocation.htm" metod="post">
         <input type="hidden"  name="id" id="id" value=""/>
          <br>      
         <span id="wrong"></span>
          <br>
          <h6>Denumire Locatie</h6>
         <input type="text" name="locationName" id="locationName" value="" />
          <br>
          <h6>Adresa Locatie</h6>
         <input type="text" name="locationAdress" id="locationAdress" value="" />
          <br>
           <h6>NumarDeMasini</h6>
         <input type="text" name="numberOfMachine" id="numberOfMachine" value="" />
          <br>
          <label for="pasContor"><h6>Contor Divider:</h6></label>
  			<select name="pasContor" id="pasContor"  >
  			<option value="1">1(X1)</option>
             <option value="2">1(X2)</option>
             <option value="3">1(X3)</option>
             <option value="4">1(X4)</option>
              <option value="5">1(X5)</option>
              </select>
                <br><br>
        <!-- Modal footer -->
        <div class="modal-footer"> 
            <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        <button type="button" class="button badge badge-danger" data-dismiss="modal">Close</button>
        </div>
         </form:form>
          
        </div>
         </div>
         </div>
      </div>
 
      <!-- Script for modal Edit Location -->
 <script type="text/javascript">
$(document).on("click", ".button", function editLocation () {
	 var id = $(this).data('id');
     $(".modal-body #id").val(id);
     var locName = $(this).data('locname');
     $(".modal-body #locationName").val(locName);
     var locAddress = $(this).data('locationadress');
     $(".modal-body #locationAdress").val( locAddress);
     var nrOfMachine = $(this).data('numberofmachine');
     $(".modal-body #numberOfMachine").val( nrOfMachine);
     var pasContor = $(this).data('contordivider');
     $(".modal-body #pasContor").val( pasContor);
});
</script>
      
<!-- End EditLocation Modal -->

<!-- ContorCorection Modal -->
<!-- Modal edit Location -->
  <div class="modal fade" id="CorectionContor" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">CorectionContorLocation</h4>
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="contorcorection.htm" metod="post">
         <input type="hidden"  name="locationMacAddress" id="locationMacAddress" value=""/>
          <br>      
           <input type="hidden"  name="permanentContor" id="permanentContor" value=""/>
          <br>      
         <span id="wrong"></span>
          <br>
          <h6>Contor Lunar</h6>
         <input type="text" name="monthContor" id="monthContor" value="" />
          <br>
          <h6>Contor Zilnic</h6>
         <input type="text" name=dayContor id="dayContor" value="" />
          <br>
          
         <input type="hidden" name="signalLevel" id="signalLevel" value="" />
          <br>
        <!-- Modal footer -->
        <div class="modal-footer"> 
            <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        <button type="button" class="button badge badge-danger" data-dismiss="modal">Close</button>
        </div>
         </form:form>
          
        </div>
         </div>
         </div>
      </div>
 
      <!-- Script for modal Edit Location -->
 <script type="text/javascript">
$(document).on("click", ".button", function editLocation () {
	 var macAddress = $(this).data('mac');
     $(".modal-body #locationMacAddress").val(macAddress);
     var permanentContor = $(this).data('permanentcontor');
     $(".modal-body #permanentContor").val(permanentContor);
     var monthContor = $(this).data('monthcontor');
     $(".modal-body #monthContor").val( monthContor);
     var dayContor = $(this).data('daycontor');
     $(".modal-body #dayContor").val( dayContor);
     var signalLevel = $(this).data('signal');
     $(".modal-body #signalLevel").val( signalLevel);
});
</script>
<!-- End ContorCorection Modal -->

<!-- Modal delete Location -->
  <div class="modal fade" id="deleteLocation" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Sterge Locatia</h4>
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="deletelocation.htm" metod="post">
         <input type="hidden"  name="id" id="id" value=""/>
          <br>      
         <span id="wrong"></span>
          <br>
          <h6>Denumire Locatie</h6>
         <input type="text" name="locationName" id="locationName" value="" />
          <br>
        <!-- Modal footer -->
        <div class="modal-footer"> 
            <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        <button type="button" class="button badge badge-danger" data-dismiss="modal">Close</button>
        </div>
         </form:form>
          
        </div>
         </div>
         </div>
      </div>
 
      <!-- Script for modal Edit Location -->
 <script type="text/javascript">
$(document).on("click", ".button", function deleteLocation () {
	 var id = $(this).data('id');
     $(".modal-body #id").val(id);
     var locName = $(this).data('locname');
     $(".modal-body #locationName").val(locName);
});
</script>
      
<!-- End deleteLocation Modal -->


</body>
</html>