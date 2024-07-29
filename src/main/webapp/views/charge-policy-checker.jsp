<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page isELIgnored = "false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Charge Policies</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.5/css/dataTables.dataTables.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/2.0.5/js/dataTables.js"></script>

</head>
<body>
    <%@ include file="navbar.jsp"%>



    <div class="container mt-5" style="display:flex; justify-content:flex-end;align-items: baseline;flex-wrap: wrap;margin-top: 20px;">
        <br>
           <h2>List Of Charge Policies</h2>
        <hr>
    </div>
<hr>

    <div class="container">
        <div class="table-container">
            <table class="table table-striped table-bordered" style="width:100%; font-size: small;" id="myDataTable" border="1">
                <thead class="thead-dark">
                    <tr>
                        <th>Policy Code</th>
                        <th>Policy Name</th>
                        <th>Record Status</th>
                        <th>Created by</th>
                        <th>Creation Date</th>
                        <th>Modified By</th>
                        <th>Modified Date</th>
                        <th>Authorized By</th>
                        <th>Authorization Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="policy" items="${chargePolicies}" varStatus="loop">
                        <tr>
                            <td><a  href="<c:url value='getDetails?code=${policy.policyCode}'/>">${policy.policyCode}</a></td>
                            <td>${policy.policyName}</td>
                            <td>${policy.recordStatus}</td>
                            <td>${policy.createdBy}</td>
                            <td>${policy.creationDate}</td>
                            <td>${policy.modifiedBy}</td>
                            <td>${policy.modifiedDate}</td>
                            <td>${policy.authorizedDate}</td>
                            <td>${policy.authorizedBy}</td>
                             <td>
                             <div class="btn-group" role="group">
                                 <button type="button" class="btn btn-primary">
                                    <a href="<c:url value='authorizePolicy?code=${policy.policyCode}'/>" style="color: white; text-decoration:none;">Authorize</a>
                                 </button>
                                 <button type="button" class="btn btn-danger">
                                    <a href="<c:url value='rejectPolicy?code=${policy.policyCode}'/>" style="color: white; text-decoration:none;">Reject</a>
                                 </button>
                             </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

    <script>
           $(document).ready(function() {
               $('#myDataTable').DataTable({
                   "scrollX": true
               });
           });
    </script>




</body>
</html>