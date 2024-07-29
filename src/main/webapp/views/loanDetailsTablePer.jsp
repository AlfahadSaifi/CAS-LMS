<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.nucleus.dto.LoanApplicationDTO, java.util.*" isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>All Loan Applications</title>


  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css"/>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>

</head>
<body>

<%@ include file="navbar.jsp"%>
<div class="container mt-5" style="display:flex; justify-content:space-between;align-items: baseline;flex-wrap: wrap;">
    <h2>List of Loan Applications</h2>
</div>

<hr>
<div class="container" style="margin-top: 20px;">
  <div class="row">
    <div class="col-md-12">
      <div class="table-div" style="max-width: 100%; overflow-x: auto;">
        <table id="table" class="table table-bordered table-striped" style="width:100%; font-size: small;">
          <thead class="thead-dark">
            <tr>
              <th>Loan Application Id</th>
              <th>Loan Account Number</th>
              <th>Application Creation Date</th>
              <th>Loan Amount</th>
              <th>Loan Status</th>
              <th>Creation Date</th>
              <th>Created By</th>
              <th>Modified Date</th>
              <th>Modified by</th>
              <th>Record Status</th>
              <th>Action</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${loanApplicationDTOS}" var="loanApplication" varStatus="loop">
              <tr>
                <td>${loanApplication.loanApplicationId}</td>
                <td>${loanApplication.loanAccountNumber}</td>
                <td>${loanApplication.applicationCreationDate}</td>
                <td>${loanApplication.loanAmount}</td>
                <td>${loanApplication.loanStatus}</td>
                <td>${loanApplication.creationDate}</td>
                <td>${loanApplication.createdBy}</td>
                <td>${loanApplication.modifiedDate}</td>
                <td>${loanApplication.modifiedBy}</td>
                <td>${loanApplication.recordStatus}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/checker/loanDetails/${loanApplication.loanApplicationId}" class="btn btn-primary btn-sm">View</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>


<script>
  $(document).ready(function() {
      $('#table').DataTable({
          "scrollX": true
      });
  });
</script>

</body>
</html>
