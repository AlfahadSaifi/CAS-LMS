<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Charge Definition Requests</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
            <!-- Bootstrap CSS -->
            <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        /* CSS for aligning text to the right */
        .right-align {
            text-align: right;
        }
    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container mt-5">
    <h2>Charge Definition Requests</h2>
<hr>
<c:if test="${not empty errorMessage}">
    <p style="color: red;font-size: 16px;">${errorMessage}</p>
</c:if>
<c:if test="${not empty rejectMessage}">
    <p style="color: red;font-size: 16px;">${rejectMessage}</p>
</c:if>
<c:if test="${not empty approvedMessage}">
    <p style="color: red;font-size: 16px;">${approvedMessage}</p>
</c:if>
</div>

<!-- DataTable -->
<div class="container">
<div class="table-container">
<table id="chargeTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
    <thead class="thead-dark">
    <tr>
        <th>Charge Code</th>
        <th>Charge Name</th>
        <th>Maximum Amount</th>
        <th>Minimum Amount</th>
        <th>Description</th>
        <th>Record Status</th>
        <th>Created By</th>
        <th>Created Date</th>
        <th>Action</th>
    </tr>
    </thead>
    <tbody>
    <!-- Iterate over the chargeDefinitionData and populate the table -->
    <c:forEach var="charge" items="${chargeDefinitionData}">
        <tr>
            <td>${charge.chargeDefinitionCode}</td>
            <td>${charge.chargeName}</td>
            <td class="right-align">${charge.maximumAmount}</td>
            <td class="right-align">${charge.minimumAmount}</td>
            <td>${charge.description}</td>
            <td>${charge.getMetaData().getRecordStatus()}</td>
            <td>${charge.getMetaData().getCreatedBy()}</td>
            <td>${charge.getMetaData().getCreationDate()}</td>
            <td>
                <div class="btn-group" role="group">
                    <a href="../checker/approveChargeDefinitionRequest?code=${charge.chargeDefinitionCode}" class="btn btn-primary modify-link clickable" onclick="return confirm('Are you sure you want to approve?')">Approve</a>
                    <a href="../checker/rejectChargeDefinitionRequest?code=${charge.chargeDefinitionCode}" class="btn btn-danger delete-link clickable" onclick="return confirm('Are you sure you want to reject?')">Reject</a>
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
<script>
    $(document).ready(function () {
        // Initialize DataTable
        $('#chargeTable').DataTable();
    });
</script>
</body>
</html>
