<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
  <%@ page import = "java.io.*,java.util.*,javax.servlet.*" %>

<!DOCTYPE html>
<html lang="ro">

<head>
  <!-- Required meta tags -->
 <meta http-equiv="content-type" content="text/html; charset=UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>TaxarApp Admin</title>
</head>
<body>
 <center>
         <h2>Auto Refresh Header Example</h2>
          <%
         Date date = new Date();
         out.print( "<h2 align = \"center\">" +date.toString()+"</h2>");
      %>
         <%
            // Set refresh, autoload time as 5 seconds
            response.setIntHeader("Refresh", 5);
            
            // Get current time
            Calendar calendar = new GregorianCalendar();
            String am_pm;
            
            int hour = calendar.get(Calendar.HOUR);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            
            if(calendar.get(Calendar.AM_PM) == 0)
               am_pm = "AM";
            else
               am_pm = "PM";
            String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
            out.println("Current Time: " + CT + "\n");
          
            
         %>
      </center>
<H1>Creating Client/Server Applications</H1>
        <p>Mac:<c:out value="${loc.locationMacAddress}"></c:out></p>
        <p>ContorZilnicFara Credit : <c:out value="${loc.dayContor}"></c:out></p>
        <p>Mac:<c:out value="${newLoc.locationMacAddress}"></c:out></p>
        <p>ContorZilnicCu Credit : <c:out value="${newLoc.dayContor}"></c:out></p>
</body>
</html>