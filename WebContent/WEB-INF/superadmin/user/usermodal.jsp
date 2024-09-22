<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
       <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html>
<html lang="ro" prefix="og: http://ogp.me/ns#" class="translated-ltr">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<title>Modal User</title>


<style>
* {
  box-sizing: border-box;
}

input[type=text], select, textarea {
  width: 100%;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
}

label {
  padding: 12px 12px 12px 0;
  display: inline-block;
}

input[type=submit] {
  background-color: #04AA6D;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  float: right;
}

input[type=submit]:hover {
  background-color: #45a049;
}

.container {
  border-radius: 5px;
  background-color: #f2f2f2;
  padding: 20px;
}

.col-25 {
  float: left;
  width: 25%;
  margin-top: 6px;
}

.col-75 {
  float: left;
  width: 75%;
  margin-top: 6px;
}

/* Clear floats after the columns */
.row:after {
  content: "";
  display: table;
  clear: both;
}

/* Responsive layout - when the screen is less than 600px wide, make the two columns stack on top of each other instead of next to each other */
@media screen and (max-width: 600px) {
  .col-25, .col-75, input[type=submit] {
    width: 100%;
    margin-top: 0;
  }
}
</style>

</head>
<body>
<%@page import="java.io.OutputStream"%>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>



<!-- Modal Add User -->
  <div class="modal fade" id="AddUser" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Add User</h4>
        <span class="nav-profile-name"><c:out value = "${ model.msgError}" ></c:out></span>
       <c:if test="${not empty model.msgError}">
        <a href="javascript:window. history. back();">Back</a>
        </c:if>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="adduser.htm" metod="post">
        <h6>User Name</h6>
         <input type="text" name="fullName" id="fullName" value="" required />
        
        
         <span id="wrong"></span>
          <br>
          <h6>Phone Number</h6>
         <input type="tel" name="phoneNumber" id="phoneNumber" placeholder="0740/12-45-67" value="" required/>
          <br>
          <h6>Email</h6>
         <input type="email" name="email" id="email" value="" required/>
          <br>
           <h6>Password</h6>
         <input type="text" name="password" id="password"  placeholder="Enter Password" required/>
          <br>
          <h6>ReTypePassword</h6>
         <input type="text" name="retypePassword" id="retypePassword" placeholder="Confirm Password" required onkeyup="validate_password()"/>
          <span id="wrong_pass_alert"></span>
          <br>
          <h6>Role</h6>
         <select name="role" id="role" required>
        <option value="SUPERADMIN">SUPERADMIN</option>
         <option value="ADMIN">ADMIN</option>
         <option value="USER">USER</option>
        </select>
          <br>
          <h6>Statut</h6>     
       <select name="statute" id="statute" required>
     <option value="ACTIVE">ACTIVE</option>
    <option value="FORBIDDEN">FORBIDDEN</option>
      </select>
        <!-- Modal footer -->
        <div class="modal-footer">     
        <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        </div>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
     
     
       <p><c:out value='${document.getParameter(fullName) }'></c:out> </p>
        </div>
         </div>
         </div>
      </div>
      
      
          
       
<script type="text/javascript">
function validate_password() {

	var pass = document.getElementById('password').value;
	var confirm_pass = document.getElementById('retypePassword').value;
	if (pass != confirm_pass) {
		document.getElementById('wrong_pass_alert').style.color = 'red';
		document.getElementById('wrong_pass_alert').innerHTML
			= 'Use same password';
		document.getElementById('create').disabled = true;
		document.getElementById('create').style.opacity = (0.4);
	} else {
		document.getElementById('wrong_pass_alert').style.color = 'green';
		document.getElementById('wrong_pass_alert').innerHTML =
			'Password Matched';
		document.getElementById('create').disabled = false;
		document.getElementById('create').style.opacity = (1);
	}
}

</script> 
<!-- End Modal&Script Add User -->
<!-- Script for modal User -->
 <script type="text/javascript">
$(document).on("click", ".button", function () {
	 var locationUserId = $(this).data('id0');
     $(".modal-body #userId").val(locationUserId);
     var myId = $(this).data('id');
     $(".modal-body #id").val( myId);
     console.log(myId);
     var fullName = $(this).data('id1');
     $(".modal-body #fullName").val(fullName);
     var phoneNumber = $(this).data('id2');
     $(".modal-body #phoneNumber").val(phoneNumber);
     var email = $(this).data('id3');
     $(".modal-body #email").val(email);
     var role = $(this).data('id4');
     $(".modal-body #role").val(role);
     var statute = $(this).data('id5');
     $(".modal-body #statute").val(statute);
     
});
</script>

<!-- Modal AddCompay -->
  <div class="modal fade" id="AddCompany" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Add Company</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="addcompany.htm" metod="post">
        <h6>Denumire Companie</h6>
         <input type="text" name="denumire" id="denumire"  placeholder ="SC Exemplu SRL" value="" required/>
          <br>
          <h6>Nr.Inreg.ONRC</h6>
         <input type="text" name="nrONRC" id="nrONRC" placeholder="J05/123/23.23.23" value="" required/>
          <br>
          <h6>CIF </h6>
         <input type="text" name="cui" id="cui" placeholder="RO123456789" value="" required/>
          <br>
           <h6>Adresa Companie</h6>
         <input type="text" name="adress" id="adress"  placeholder="Enter Adress" required/>
          <br>
        <!-- Modal footer -->
        <div class="modal-footer">     
        <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        </div>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
         </div>
         </div>
      </div>

<!-- Script for modal CompanyEdit -->
 <script type="text/javascript">
$(document).on("click", ".button", function () {
	 var CompanyId = $(this).data('id');
     $(".modal-body #id").val(CompanyId);
     var denumireCompany = $(this).data('companyname');
     $(".modal-body #denumire").val(denumireCompany);
     var nrOnrc = $(this).data('nronrc');
     $(".modal-body #nrONRC").val( nrOnrc);
     var cui = $(this).data('cui');
     $(".modal-body #cui").val(cui);
     var adress = $(this).data('adress');
     $(".modal-body #adress").val(adress);
    
});
</script>


<!-- Modal Edit Compay -->
  <div class="modal fade" id="EditComp" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Edit Company</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
        <form:form action="editcompany.htm" metod="post">
        <h6>Id</h6>
         <input type="hidden" name="id" id="id"  value="" required/>
          <br>
        <h6>Denumire Companie</h6>
         <input type="text" name="denumire" id="denumire"  placeholder ="SC Exemplu SRL" value="" required/>
          <br>
          <h6>Nr.Inreg.ONRC</h6>
         <input type="text" name="nrONRC" id="nrONRC" placeholder="J05/123/23.23.23" value="" required/>
          <br>
          <h6>CIF </h6>
         <input type="text" name="cui" id="cui" placeholder="RO123456789" value="" required/>
          <br>
           <h6>Adresa Companie</h6>
         <input type="text" name="adress" id="adress"  placeholder="Enter Adress" required/>
          <br>
        <!-- Modal footer -->
        <div class="modal-footer">     
        <input type="submit"  class="buttons btn btn-info add-new" value="Save"/>
        </div>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
         </div>
         </div>
      </div>
      
      <!-- Script for modal DeleteChasReg -->
 <script type="text/javascript">
$(document).on("click", ".button", function () {
	 var id = $(this).data('id');
     $(".modal-body #id").val(id);
   
});
</script>
   <!-- Modal DeleteRowRegChas -->
  <div class="modal fade" id="DeleteRowChasReg" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Sterge Ultima Inregistrare</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="deleteRowChasReg.htm" >
        <input type="hidden"  name="id" id="id" value=""/>
        <br>
        <!-- Modal footer -->
        <div class="modal-footer">
        <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        <input type="submit"  class="btn btn-info add-new" value="Delete"/>
          </div>
         </form:form>
          
        </div>
      </div>
    </div>
  </div>
  <!-- End ModalDeleteRowChasReg -->
   <!-- Modal DeleteRegChas -->
  <div class="modal fade" id="DeleteChasReg" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Sterge Registru</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="deleteChasReg.htm" >
        <input type="hidden"  name="id" id="id" value=""/>
        <br>
        <!-- Modal footer -->
        <div class="modal-footer">
        <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        <input type="submit"  class="btn btn-info add-new" value="Delete"/>
          </div>
         </form:form>
          
        </div>
      </div>
    </div>
  </div>
  <!-- End ModalDeleteChasReg -->
  
    <!-- Script for modal DeleteTaxPayment -->
 <script type="text/javascript">
$(document).on("click", ".button", function () {
	 var id = $(this).data('id');
     $(".modal-body #id").val(id);
    
});
</script>
  
   <!-- Modal DeleteTaxPayment -->
  <div class="modal fade" id="DeleteTaxPayment" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Delete Record</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="deleteTaxPayment.htm" metod="post" commandname="id">
        <input type="hidden"  name="id" id="id" value=""/>
        <br>
      
        <!-- Modal footer -->
        <div class="modal-footer">
        <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        <input type="submit"  class="btn btn-info add-new" value="Delete"/>
          </div>
         </form:form>
          
        </div>
      </div>
    </div>
  </div>
  <!-- End DeleteTaxPayment -->

   <!-- Modal myModalAddPayment -->
  <div class="modal fade" id="AddPayment" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Adauga Plata</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="addPayment.htm" >
        <input type="hidden"  name="id" id="id" value=""/>
        <br>
          <h6>Valoare Plata</h6>
         <input type="number" name="payment" id="payment" value="1"  min="0" 
        max="500000" step="0.01" />
          <br> 
           <h6>Descriere Plata</h6>
         <textarea id="explanation" name="explanation" rows="4" cols="50" placeholder="Introdu aici datele despre plata(factura,explicatii,etc)"> </textarea>
          <br> 
        <!-- Modal footer -->
        <div class="modal-footer">
        <input type="submit"  class="btn btn-info add-new" value="Save"/>
          </div>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <!-- End ModalAddPyment -->

 <!-- Modal myModalAddEncashament -->
  <div class="modal fade" id="AddEncashment" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Adauga Depunere</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="addPayment.htm" >
          <h6>Valoare Depunere</h6>
         <input type="number" name="encashment" id="encashment" value="1"  min="0" 
        max="500000" step="0.01" />
          <br> 
           <h6>Descriere Depunere</h6>
         <textarea id="explanation" name="explanation" rows="4" cols="50" placeholder="Introdu aici datele despre depunere(factura,explicatii,etc)"> </textarea>
          <br> 
        <!-- Modal footer -->
        <div class="modal-footer">
        <input type="submit"  class="btn btn-info add-new" value="Save"/>
          </div>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
  <!-- End ModalAddEncashament -->
     <!-- Modal Delete User -->
  <div class="modal fade" id="DeleteProduct" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Delete User</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="delete-user.htm" >
        <input type="hidden"  name="id" id="id" value=""/>
        <h3>UserName</h3>
        <br>
         <input type="text" name="fullName" id="fullName" value=""/>
         
    </div>
        <!-- Modal footer -->
        <div class="modal-footer">
        <input type="submit"  class="btn btn-info add-new" value="Delete"/>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  <!-- End Modal&Script Delete User -->
   <!-- Modal Edit User  -->
  <div class="modal fade" id="EditUser" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Edit User</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="updateUser.htm" >
        <input type="hidden"  name="id" id="id" value=""/>
        <br>
        <h6>User Name</h6>
         <input type="text" name="fullName" id="fullName" value=""/>
          <br>
          <h6>Phone Number</h6>
         <input type="text" name="phoneNumber" id="phoneNumber" value=""/>
          <br>
          <h6>Email</h6>
         <input type="text" name="email" id="email" value=""/>
         <br>
           <h6>Password</h6>
         <input type="text" name="password" id="password"  value=""/>
          <br>
          <h6>Role</h6>
         <select name="role" id="role" >
        <option value="SUPERADMIN">SUPERADMIN</option>
         <option value="ADMIN">ADMIN</option>
         <option value="USER">USER</option>
        </select>
          <br>
          <h6>Statut</h6>     
       <select name="statute" id="statute" >
     <option value="ACTIVE">ACTIVE</option>
    <option value="FORBIDDEN">FORBIDDEN</option>
      </select>
       </div>
   
        <!-- Modal footer -->
        <div class="modal-footer">
        <input type="submit"  class="btn btn-info add-new" value="Save"/>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  <!-- End Modal Edit User -->
  
  
  <!-- Modal Edit TotalPay -->
  <div class="modal fade" id="EditTotalPay" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
        <h4 class="modal-title">Edit Payment</h4>
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
        <form:form action="updateTotalPayment.htm" >
        <input type="hidden"  name="id" id="id" value=""/>
        <br>
        <h6>User Name</h6>
         <input type="text" name="fullName" id="fullName" value=""/>
          <br>
         <h6>Valoare Plata</h6>
         <input type="decimal" name="totalpayment" id="totalpayment" value="1"  min="0" 
        max="500000" step="100.00" />
          <br>
         <h6>Nr SlotMachine</h6>
         <input type="decimal" name="nrSlotMachine" id="nrSlotMachine"  
        max="1000" />
         <br>
         <h6>Nr AWP</h6>
         <input type="decimal" name="nrAwp" id="nrAwp" value="0"  
        max="1000"  />
       </div>
   
        <!-- Modal footer -->
        <div class="modal-footer">
        <input type="submit"  class="btn btn-info add-new" value="Save"/>
         </form:form>
          <button type="button" class="btn btn-info add-new" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  <!-- End Modal&Script Edit User -->
 
</body>
</html>