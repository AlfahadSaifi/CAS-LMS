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
            <h2 style="margin-right: auto;">Requested Record Policies</h2>
            <button class="btn btn-primary fixbutton"><a href="${pageContext.request.contextPath}/maker/formRepaymentPolicy" style="color:white; text-decoration:none;">New Policy</a></button>
        </div>
        <h4 style="color: red">${message}</h4>
        <hr style="margin-bottom: 20px;">
        <div class="table-container" style="overflow-x: auto;">
            <table class="table table-striped table-bordered policyTable" id="policyTable" style="font-size: small;">
                <thead class="thead-dark">
                    <tr>
                        <th>Repayment Policy Code</th>
                        <th>Repayment Policy Name</th>
                        <th>Contract Type</th>
                        <th>Repayment Type</th>
                        <th>Recovery Type</th>
                        <th>Installment Due Date</th>
                        <th>Status</th>
                        <th>Creation Date</th>
                        <th>Creation By</th>
                        <th>Modified By</th>
                        <th>Modified Date</th>
                        <th>Authorized By</th>
                        <th>Authorized Date</th>
                        <th>Active/Inactive Flag</th>
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
                            <td>${policy.metaData.creationDate}</td>
                            <td>${policy.metaData.createdBy}</td>
                            <td>${policy.metaData.modifiedBy}</td>
                            <td>${policy.metaData.modifiedDate}</td>
                            <td>${policy.metaData.authorizedBy}</td>
                            <td>${policy.metaData.authorizedDate}</td>
                            <td>${policy.metaData.activeInactiveFlag}</td>
                            <td>
                                <div class="btn-group" role="group">
                                    <button class="btn btn-primary btn-sm mr-1">
                                        <a href="${pageContext.request.contextPath}/maker/editrepaymentpolicytemp/${policy.repaymentPolicyCode}" style="color: white;">Edit</a>
                                    </button>
                                    |
                                    <button class="btn btn-danger btn-sm">
                                        <a href="${pageContext.request.contextPath}/maker/deleterepaymentpolicytemp/${policy.repaymentPolicyCode}" style="color: white;">Delete</a>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('#policyTable').DataTable({
               "scrollX": true
           });
        });
    </script>
</body>
</html>
