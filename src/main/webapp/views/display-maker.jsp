<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page isELIgnored = "false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Charge policy Details</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
     <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
     <style>
         .table-container
         {
              max-width: 100%; /* Adjust as needed */
              overflow-x: auto;
         }
         .btnClass
         {
            display:flex;
            justify-content: flex-end;
            margin-right:120px;
         }
     </style>
</head>
<body>
<%@ include file="navbar.jsp"%>

    <div class="container">
        <br>
        <h2 style="margin-left:20px; margin-top:10px;">Charge Policy details</h2>
        <hr/>
        <h4 style="margin-left:20px; margin-top:20px;">Policy Code: ${selectedPolicy.policyCode}</h4>
        <h4 style="margin-left:20px; margin-top:20px;">Policy Name: ${selectedPolicy.policyName}</h4>

         <hr/>
        <br>
        <br>
        <div class="table-container">
            <table class="table table-striped table-bordered" style="width:100%; font-size: small;" id="myDataTable" border="1">
                <thead class="thead-dark">
                    <tr>
                        <th>Charge Code</th>
                        <th>Charge Name</th>
                        <th>Operator</th>
                        <th>Value</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="parameter" items="${selectedPolicy.chargePolicyParameterList}" varStatus="loop">
                        <tr>
                            <td>${parameter.chargeDefinitionCode}</td>
                            <td>
                                <c:forEach var="chargeDef" items="${chargeList}">
                                    <c:if test="${chargeDef.chargeDefinitionCode eq parameter.chargeDefinitionCode}">
                                        ${chargeDef.chargeName}
                                    </c:if>
                                </c:forEach>
                            </td>
                            <td>${parameter.operator}</td>
                            <td>${parameter.value}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <br>

     <div class="btnClass">
          <button type="button" class="btn btn-primary" >
            <a href="charge-policy-table" style="color: white; text-decoration:none;">Go Back</a><br>
          </button>

          &nbsp;
          <button type="button" class="btn btn-primary" >
            <a href="edit-charge-policy-form?policy=${selectedPolicy.policyCode}&record=${selectedPolicy.recordStatus}" style="color: white; text-decoration:none;">Update</a><br>
          </button>
          &nbsp;
          <button type="button" class="btn btn-primary" style="color: white;>
            <a href="delete-charge-policy?policyCode=${selectedPolicy.policyCode}&record=${selectedPolicy.recordStatus}" style="color: white; text-decoration:none;">Delete</a>
          </button>
     </div>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script>
           $(document).ready(function() {
                   $('#myDataTable').DataTable({
                       "scrollX": true
                   });
               });
        </script>
</body>
</html>