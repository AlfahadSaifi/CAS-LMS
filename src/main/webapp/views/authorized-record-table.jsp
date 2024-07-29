<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page isELIgnored = "false" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Charge Form</title>
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.css">
  <link rel = "stylesheet" href  = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
       <script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
       <script src = "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <style>
        /* CSS for aligning text to the right */
        .right-align {
          text-align: right;
        }
      </style>
</head>
<body>
 <%@ include file="navbar.jsp"%>
<h1>Charge Form</h1>
 <c:if test="${not empty errorDeleteRequest}">
            <p style="color: red;">${errorDeleteRequest}</p>
        </c:if>
<!-- DataTable -->
<table id="chargeTable" class="display" style="width:100%" border="1">
  <thead>
  <tr>
    <th>Charge Code</th>
    <th>Charge Name</th>
    <th>Maximum Amount</th>
    <th>Minimum Amount</th>
    <th>Description</th>
    <th>Record Status</th>
    <th>Created By </th>
    <th>Created Date </th>
    <th>Authorized Date </th>
    <th>Modify</th>
    <th>Delete </th>
  </tr>
  </thead>
  <tbody>
  <!-- Iterate over the chargeDefinitionData and populate the table -->

  <%

  %>
  <c:forEach var="charge" items="${chargeDefinitionDTOList}" >
    <tr>
      <td>${charge.chargeDefinitionCode}</td>
      <td>${charge.chargeName}</td>
      <td class="right-align">${charge.maximumAmount}</td>
      <td class="right-align">${charge.minimumAmount}</td>
      <td>${charge.description}</td>
      <td>${charge.getMetaData().getRecordStatus()}</td>
      <td>${charge.getMetaData().getCreatedBy()}</td>
      <td>${charge.getMetaData().getCreationDate()}</td>
      <td>${charge.getMetaData().getAuthorizedDate()}</td>
      <td><a href="../maker/modifyCharge?code=${charge.chargeDefinitionCode}" class="btn btn-primary modify-link clickable">Modify</a> </td>
      <td><a href="../maker/deleteChargeFromRecords?code=${charge.chargeDefinitionCode}" class="btn btn-danger delete-link clickable">Delete</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>


<div>
  <form action="../maker/charge-definition" method="get">
    <button type="submit" class="btn btn-primary" style="font-size: 20px;border: 1px solid black;" >Unauthorized Records</button>
  </form>

</div>



<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.js"></script>
<script>
        $(document).ready(function() {
            // Initialize DataTable
            $('#chargeTable').DataTable();
        });
    </script>

</body>
</html>

