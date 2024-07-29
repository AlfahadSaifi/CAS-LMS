<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Requested Record Policies</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <style>
        .table-container {
            max-width: 100%; /* Adjust as needed */
            overflow-x: auto;
        }
    </style>
</head>
<body>
    <%@ include file="navbar.jsp"%>

    <div class="container">
        <div class="container mt-5" style="display:flex; justify-content:space-between;align-items: baseline;flex-wrap: wrap;margin-top: 20px; margin-bottom: 20px;">
            <h2>Repayment Policies</h2>
        </div>
        <h4 style="color: red">${message}</h4>
        <hr style="margin-bottom: 20px;">
        <div class="table-container" style="overflow-x: auto;">
            <table id="policyTable" class="table table-striped table-bordered policyTable" style="font-size: small;">
                <thead class="thead-dark">
                  <tr>
                    <th>RepaymentPolicyCode</th>
                    <th>Repayment Policy Name</th>
                    <th>contractType</th>
                    <th>repaymentType</th>
                    <th>recoveryType</th>
                    <th>installmentDueDate</th>
                    <th>Status</th>
                    <th>Action</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${repaymentPolicies}" var="policy">
                    <tr>

                      <td>${policy.repaymentPolicyCode}</td>
                      <td>${policy.repaymentPolicyName}</td>
                      <td>${policy.contractType}</td>
                      <td>${policy.repaymentType}</td>
                      <td>${policy.recoveryType}</td>
                      <td>${policy.installmentDueDate}</td>
                      <td>${policy.metaData.recordStatus}</td>
                      <td>
                      <div style="display:flex"><button class="btn btn-primary btn-sm mr-1"><a href="${pageContext.request.contextPath}/checker/approverepaymentpolicies/${policy.repaymentPolicyCode}">Approve</a></button>|<button class="btn btn-danger btn-sm"><a href="${pageContext.request.contextPath}/checker/rejectedrepaymentpolicytemp/${policy.repaymentPolicyCode}">Reject</a></button>  </div>
                      </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
       <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
       <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
       <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
       <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#policyTable').DataTable({
           });
        });
    </script>
</body>
</html>