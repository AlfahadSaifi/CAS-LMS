<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@page isELIgnored = "false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Charge Policies</title>
    <!-- Include jQuery -->

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
            <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">


      <style>
            .table-container {
                max-width: 100%; /* Adjust as needed */
                overflow-x: auto;
            }
      </style>
</head>
<body>

<%@ include file="navbar.jsp"%>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
window.onload = function() {
  // Remove query parameters from URL
  clearQueryParameters();
};

function clearQueryParameters() {
  // Get the current URL
  var url = window.location.href;

  // Check if there are query parameters in the URL
  if (url.indexOf('?') !== -1) {
    // Remove query parameters
    var cleanUrl = url.substring(0, url.indexOf('?'));
    // Replace the current URL in the browser history
    history.replaceState({}, document.title, cleanUrl);
  }
}

  $(document).ready(function() {
        $('#myDataTable').DataTable({
            "scrollX": true
        });
    });


</script>

<c:if test="${not empty param.successMessage}">
    <div >
      <!-- Alert with Success Message -->
      <div class="alert alert-success" role="alert">
       ${param.successMessage}
       <button type="button" class="close" data-dismiss="alert" aria-label="Close">
             <span aria-hidden="true">&times;</span>
       </button>
    </div>
</c:if>

<c:if test="${not empty successMessage}">
    <div >
      <!-- Alert with Success Message -->
      <div class="alert alert-success" role="alert">
       ${successMessage}
       <button type="button" class="close" data-dismiss="alert" aria-label="Close">
             <span aria-hidden="true">&times;</span>
       </button>
    </div>
</c:if>


<div class="container mt-4" style="display:flex; justify-content:flex-end;align-items: baseline;flex-wrap: wrap;">
    <br>
    <h2>List Of Charge Policies</h2>
    <hr />
    <button class="btn btn-primary fixbutton">
        <a href="${pageContext.request.contextPath}/maker/charge-policy" style="color: white; text-decoration:none;">Create a charge police</a>
    </button>

</div>
<hr>
<div class="container"  style="margin-top: 20px;">
    <div class="table-container">

        <table  class="table table-striped table-bordered" style="width:100%; font-size: small;" id="myDataTable" border="1">
        <thead class="thead-dark">
            <tr>
                <th>Policy Code</th>
                <th>Policy Name</th>
                <th>Created by</th>
                <th>Creation Date</th>
                <th>Modified By</th>
                <th>Modified Date</th>
                <th>Authorize By</th>
                <th>Authorize Date</th>
                <th>Record Status</th>
                <th>Action</th>
            </tr>
        </thead>


        <tbody>
            <c:forEach var="policy" items="${chargePolicies}" varStatus="loop">
                <tr>
                    <td><a href="<c:url value='getMakerDetails?code=${policy.policyCode}'/>" id="policyCode" value="${policy.policyCode}">${policy.policyCode}</a></td>
                    <td>${policy.policyName}</td>
                    <td>${policy.createdBy}</td>
                    <td>${policy.creationDate}</td>
                    <td>${policy.modifiedBy}</td>
                    <td>${policy.modifiedDate}</td>
                    <td>${policy.authorizedBy}</td>
                    <td>${policy.authorizedDate}</td>
                    <td>${policy.recordStatus}</td>
                    <td>
                        <div class="btn-group" role="group">
                            <button type="button" class="btn btn-primary">
                                <a href="edit-charge-policy-form?policy=${policy.policyCode}&record=${policy.recordStatus}" style="color: white; text-decoration:none;">Update</a>
                            </button>
                            <button type="button" class="btn btn-danger">
                                <a href="delete-charge-policy?policyCode=${policy.policyCode}&record=${policy.recordStatus}" style="color: white; text-decoration:none;">Delete</a>
                            </button>
                        </div>
                     </td>
                </tr>
            </c:forEach>

            <c:forEach var="policyPerm" items="${chargePoliciesPerm}" varStatus="loop">
                <tr>
                    <td><a href="<c:url value='getMakerDetails?code=${policyPerm.policyCode}'/>" id="policyCode" value="${policyPerm.policyCode}" >${policyPerm.policyCode}</a></td>
                    <td>${policyPerm.policyName}</td>
                    <td>${policyPerm.createdBy}</td>
                    <td>${policyPerm.creationDate}</td>
                    <td>${policyPerm.modifiedBy}</td>
                    <td>${policyPerm.modifiedDate}</td>
                    <td>${policyPerm.authorizedBy}</td>
                    <td>${policyPerm.authorizedDate}</td>
                    <td>${policyPerm.recordStatus}</td>
                    <td>
                     <div class="btn-group" role="group">
                        <button type="button" class="btn btn-primary">
                            <a href="edit-charge-policy-form?policy=${policyPerm.policyCode}&record=${policyPerm.recordStatus}" style="color: white; text-decoration:none;">Update</a>
                        </button>
                        <button type="button" class="btn btn-danger">
                            <a href="delete-charge-policy?policyCode=${policyPerm.policyCode}&record=${policyPerm.recordStatus}" style="color: white; text-decoration:none;">Delete</a>
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
        </table>
    </div>
</div>
</body>
</html>