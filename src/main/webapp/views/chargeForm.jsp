<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>


<%@ page isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Charge Form</title>

        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <!-- Bootstrap CSS -->
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <style>
        /* CSS for aligning text to the right */
        .right-align {
            text-align: right;
        }
        .table-container {
                    max-width: 100%; /* Adjust as needed */
                    overflow-x: auto;
                }
    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container mt-5" style="display:flex; justify-content:space-between;align-items: baseline;flex-wrap: wrap;margin-top: 20px;">
    <h2>Charge Definition Requests</h2>
    <form action="../maker/addNewChargeDefinition" method="get">
            <button type="submit" class="btn btn-primary" style="font-size: 20px;border: 1px solid black; ">New Charge Definition</button>
        </form>
</div>
<div class="container mt-5">


<hr>

<c:if test="${not empty messageWhileUpdate}">
    <p style="color: red;font-size: 20px;">${messageWhileUpdate}</p>
</c:if>
<c:if test="${not empty messageDeleteRequest}">
    <p style="color: red;font-size: 20px;">${messageDeleteRequest}</p>
</c:if>
<c:if test="${not empty chargeNameError}">
    <p style="color: red; font-size: 20px;">${chargeNameError}</p>
</c:if>
<c:if test="${not empty chargeDefinitionAmountError}">
    <p style="color: red; font-size: 20px;">${chargeDefinitionAmountError}</p>
</c:if>
</div>

<!-- DataTable -->
<div class="container">
<div class="table-container">
<table id="chargeTable" class="table table-striped table-bordered" style="width:100%; font-size: small;" >
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
        <th>Authorized Date</th>
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
            <td>${charge.getMetaData().getAuthorizedDate()}</td>
            <td>
              <div class="btn-group" role="group">
                <c:choose>
                    <c:when test="${charge.getMetaData().getRecordStatus() ne 'M' and charge.getMetaData().getRecordStatus() ne 'D'
                                   and charge.getMetaData().getRecordStatus() ne 'DR' and charge.getMetaData().getRecordStatus() ne 'MR'}">
                        <a href="../maker/editCharge?code=${charge.chargeDefinitionCode}" class="btn btn-primary modify-link clickable">Modify</a>
                    </c:when>
                    <c:otherwise>
                        <span class="disabled-edit btn btn-default disabled" style="border: 2px solid grey;">Modify</span>
                    </c:otherwise>
                </c:choose>
                <a href="../maker/deleteCharge?code=${charge.chargeDefinitionCode}" class="btn btn-danger delete-link clickable" onclick="return confirm('Are you sure you want to Delete Charge Request?')">Delete</a>
              </div>
            </td>
        </tr>
    </c:forEach>
    <c:forEach var="charge" items="${chargeDefinitionsDataFromMasterTable}">
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
            <td>
            <div class="btn-group" role="group">
                <a href="../maker/modifyCharge?code=${charge.chargeDefinitionCode}" class="btn btn-primary modify-link clickable">Modify</a>
                <a href="../maker/deleteChargeFromRecords?code=${charge.chargeDefinitionCode}" class="btn btn-danger delete-link clickable" onclick="return confirm('Are you sure you want to request for Deletion of Charge?')">Delete</a>
            </div>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</div>

<!-- Buttons -->
<<div class="container mt-5">

</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
    $(document).ready(function() {
        // Initialize DataTable
        $('#chargeTable').DataTable();
    });
</script>

</body>
</html>
