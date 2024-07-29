<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.6/css/dataTables.bootstrap4.min.css">
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- DataTables JS -->
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.6/js/jquery.dataTables.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Custom CSS -->
    <style>
        /* Custom styles */
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
        }
        .container {
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            padding: 30px;
            margin-top: 20px;
            width: 80%; /* Adjusted width */
            margin-left: auto; /* Center the container */
            margin-right: auto; /* Center the container */
        }
        h2 {
            color: #007bff;
            font-weight: bold;
            text-align: center;
            margin-bottom: 30px;
        }
        label {
            font-weight: bold;
        }
        .form-control {
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 3px;
            width: 100%;
        }
        .form-row {
            margin-bottom: 20px;
        }
        .btn-group {
            margin-top: 20px;
            text-align: center;
        }
        button {
            padding: 10px 20px;
            cursor: pointer;
            border: none;
            border-radius: 3px;
            margin: 5px;
        }
        .btn-primary {
            background-color: #007bff;
            color: #fff;
        }
        .btn-primary:hover {
            background-color: #0056b3;
        }
        .btn-clear,
        .btn-cancel {
            background-color: #808080;
            color: #fff;
        }
        /* Adjustments for input group */
        .input-group {
            position: relative;
            width: 100%;
        }
        .input-group .input-group-append {
            position: absolute;
            right: 0;
            top: 0;
            bottom: 0;
            z-index: 1;
            padding: .375rem .75rem;
            background-color: #007bff; /* Change background color */
            border: 1px solid #007bff; /* Change border color */
            border-radius: 0 .25rem .25rem 0;
            cursor: pointer;
        }
        .input-group .input-group-append:hover {
            background-color: #0056b3; /* Change hover background color */
        }
        .input-group .input-group-append button {
            border: none;
            background-color: transparent;
            color: #fff; /* Change button text color */
        }
        .input-group .form-control {
            border-radius: .25rem 0 0 .25rem;
        }
        /* DataTables style adjustments */
        #paymentTable_wrapper {
            margin-top: 30px;
        }
        #paymentTable_length label,
        #paymentTable_filter label {
            margin-bottom: 0;
        }
        .dataTables_info {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .dataTables_paginate {
            margin-top: 10px;
        }
        .table th, .table td {
            vertical-align: middle;
        }
        .table-responsive {
            margin-top: 20px;
        }
    </style>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page isELIgnored="false" %>
    <%@ page import="org.nucleus.dto.PaymentDTO, java.util.*" %>
</head>
<body>
<div class="container">
    <h2><spring:message code="payableApprovalScreen"/></h2>
    <c:if test="${not empty successMessage}">
        <div class="alert alert-success" role="alert">
            ${successMessage}
        </div>
    </c:if>
    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
            ${errorMessage}
        </div>
    </c:if>

    <form:form method="post" action="payment-Search" modelAttribute="paymentChecker">
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="receivablePayableId"><spring:message code="receivablePayableId"/></label>
                <form:input type="text" class="form-control" id="receivablePayableId" path="receivablePayable.receivablePayableId" placeholder="Enter ReceivablePayable ID" />
            </div>
        </div>
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="loanAccountNumber"><spring:message code="loanAccountNumber"/></label>
                <form:input type="text" class="form-control" id="loanAccountNumber" path="loanAccountNumber" placeholder="Loan Account Number" readonly="true"/>
            </div>
            <div class="col-md-6 mb-3">
                <label for="createdBy"><spring:message code="createdBy"/></label>
                <form:input type="text" class="form-control" id="createdBy" path="tempMetaData.createdBy" placeholder="Customer" required="true" readonly="true"/>
            </div>
        </div>
        <!-- Add fields for fromDate and dueDate -->
        <div class="form-row">
            <div class="col-md-6 mb-3">
                <label for="fromDate"><spring:message code="fromDate"/></label>
                <input type="date" class="form-control" id="fromDate" name="fromDate" required/>
            </div>
            <div class="col-md-6 mb-3">
                <label for="dueDate"><spring:message code="dueDate"/></label>
                <input type="date" class="form-control" id="dueDate" name="dueDate" required/>
            </div>
        </div>
        <!-- Move the search button to the bottom of the form -->
        <div class="form-row">
            <div class="col-md-12">
                <button type="submit" class="btn btn-primary" name="action" value="search"><spring:message code="search"/></button>
                <button type="button" class="btn btn-cancel" onclick="goToMainPage()"><spring:message code="cancel"/></button>
            </div>
        </div>
    </form:form>

    <!-- DataTables for Payment List -->
    <div class="table-responsive">
        <table id="paymentTable" class="table">
            <thead>
                <tr>
                    <th>RECEIPT ID</th>
                    <th>RECEIVABLE PAYABLE ID</th>
                    <th>LOAN ACCOUNT NUMBER</th>
                    <th>PAID TO</th>
                    <th>PAYEE NAME</th>
                    <th>PAYMENT AMOUNT</th>
                    <th>PAYMENT CURRENCY</th>
                    <th>PAYMENT DATE</th>
                    <th>PAYMENT MODE</th>
                    <th>PAYOUT BANK ACCOUNT</th>
                    <th>STATUS</th>
                    <th>ACTIVE/INACTIVE FLAG</th>
                    <th>AUTHORIZED BY</th>
                    <th>AUTHORIZED DATE</th>
                    <th>CREATED BY</th>
                    <th>CREATION DATE</th>
                    <th>MODIFIED BY</th>
                    <th>MODIFIED DATE</th>
                    <th>RECORD STATUS</th>
                    <th>SAVE FLAG</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${paymentList}" var="pc">
                    <tr>
                        <td>${pc.receiptId}</td>
                        <td>${pc.receivablePayable.receivablePayableId}</td>
                        <td>${pc.loanAccountNumber}</td>
                        <td>${pc.paidTo}</td>
                        <td>${pc.payeeName}</td>
                        <td>${pc.paymentAmount}</td>
                        <td>${pc.paymentCurrency}</td>
                        <td>${pc.paymentDate}</td>
                        <td>${pc.paymentMode}</td>
                        <td>${pc.payoutBankAccount}</td>
                        <td>${pc.status}</td>
                        <td>${pc.tempMetaData.activeInactiveFlag}</td>
                        <td>${pc.tempMetaData.authorizedBy}</td>
                        <td>${pc.tempMetaData.authorizedDate}</td>
                        <td>${pc.tempMetaData.createdBy}</td>
                        <td>${pc.tempMetaData.creationDate}</td>
                        <td>${pc.tempMetaData.modifiedBy}</td>
                        <td>${pc.tempMetaData.modifiedDate}</td>
                        <td>${pc.tempMetaData.recordStatus}</td>
                        <td>${pc.tempMetaData.saveFlag}</td>
                        <td>
                            <a href="approve-Payment/${pc.receiptId}">Approve</a>
                            <a href="reject-Payment/${pc.receiptId}">Reject</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
    <div class="form-row">
                <div class="col-md-12">
                    <button type="button" class="btn btn-cancel" onclick="goToMainPage2()"><spring:message code="back"/></button>
                </div>
            </div>
</div>
<script>
    $(document).ready(function() {
        $('#paymentTable').DataTable();
    });
    function goToMainPage() {
        window.location.href = "../"; // Change "mainpage.html" to your actual main page URL
    }
    function goToMainPage2() {
            window.location.href = "../../"; // Change "mainpage.html" to your actual main page URL
        }
</script>
</body>
</html>
