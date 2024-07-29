<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Payment </title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<style>
    /* Custom styles */
      body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh; /* Changed height to min-height */
        }
        .container {
            width: 80%;
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            min-height: 80vh; /* Added min-height to ensure container height */
        }
        h2 {
            margin-top: 0;
            margin-bottom: 30px;
            color: #007bff;
            font-weight: bold;
            text-align: center;
            position: relative; /* Added position relative */
            z-index: 1; /* Added z-index */
        }
        .alert {
            margin-top: 20px; /* Added margin top for alerts */
        }
        label {
            font-weight: bold;
        }
    .form-control {
        width: calc(100% - 20px);
        padding: 8px;
        border: 1px solid #ccc;
        border-radius: 3px;
        box-sizing: border-box;
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
</style>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.nucleus.dto.PaymentDTO, java.util.*" %>
</head>
<body>
<div class="container">
<h2><spring:message code="payableForm"/></h2>
<form:form id="paymentForm" action="payment-Submit" method="post" modelAttribute="paymentMaker">
<div class="form-row">
<div class="col-md-6 mb-3">
<label for="paidTo"><spring:message code="paidTo"/></label>
<form:input type="text" class="form-control" id="paidTo" path="paidTo" placeholder="Customer" readonly="true" required="true"/>
</div>
<div class="col-md-6 mb-3">
<label for="payeeName"><spring:message code="payeeName"/></label>
<form:select class="form-control" id="payeeName" path="payeeName" required="true">
<form:option value="SBI">SBI</form:option>
<form:option value="HDFC">HDFC</form:option>
<form:option value="ICICI">ICICI</form:option>
</form:select>
</div>
</div>
<div class="form-row">
<div class="col-md-6 mb-3">
<label for="receivablePayableId"><spring:message code="receivablePayableId"/></label>
<div class="input-group">
<form:input type="text" class="form-control" id="receivablePayableId" path="receivablePayable.receivablePayableId" placeholder="Enter payable id number" required="true"/>
<button type="submit" class="btn btn-primary" name="action" value="search"><spring:message code="search"/></button>
</div>
</div>
<div class="col-md-6 mb-3">
<label for="paymentAmount"><spring:message code="paymentAmount"/></label>
<form:input type="number" class="form-control" id="paymentAmount" path="paymentAmount" step="0.01" />
<form:errors path="paymentAmount" style="color: red;"/>
</div>
</div>
<div class="form-row">
<div class="col-md-6 mb-3">
 <label for="paymentCurrency"><spring:message code="paymentCurrency"/></label>
<form:select class="form-control" id="paymentCurrency" path="paymentCurrency" required="true">
<form:option value="INR">INR</form:option>
</form:select>
</div>
<div class="col-md-6 mb-3">
<label for="paymentMode"><spring:message code="paymentMode"/></label>
<form:select class="form-control" id="paymentMode" path="paymentMode" required="true">
<form:option value="CASH">Cash</form:option>
<form:option value="DRAFT">Draft</form:option>
</form:select>
</div>
</div>
<div class="form-row">
<div class="col-md-6 mb-3">
<label for="loanAccountNumber"><spring:message code="loanAccountNumber"/></label>
<form:input type="text" class="form-control" id="loanAccountNumber" path="loanAccountNumber" placeholder="Loan account number" readonly="true" />
</div>
<div class="col-md-6 mb-3">
<label for="paymentDate"><spring:message code="paymentDate"/></label>
<form:input type="date" class="form-control" id="paymentDate" path="paymentDate" required="true" />
<form:errors path="paymentDate" style="color: red;"/>
</div>
</div>
<div class="form-row">
<div class="col-md-6 mb-3">
<label for="payoutBankAccount"><spring:message code="payoutBankAccount"/></label>
<form:input type="text" class="form-control" id="payoutBankAccount" path="payoutBankAccount" placeholder="Enter payout bank account number" />
</div>
</div>
<div class="btn-group">
<button type="submit" class="btn btn-primary" name="action" value="submit"><spring:message code="submit"/></button>
<button type="button" class="btn btn-clear" onclick="clearForm()"><spring:message code="clear"/></button>
<button type="button" class="btn btn-cancel" onclick="goToMainPage()"><spring:message code="cancel"/></button>
</div>
</form:form>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger" role="alert">
        ${errorMessage}
    </div>
</c:if>

<c:if test="${not empty successMessage}">
    <div class="alert alert-success" role="alert">
        ${successMessage}
    </div>
</c:if>

</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
<script>
    function clearForm() {
        document.getElementById("paymentForm").reset();
    }
    function goToMainPage() {
        window.location.href = "../"; // Change "mainpage.html" to your actual main page URL
    }
    document.addEventListener("DOMContentLoaded", function() {
            var currentDate = new Date().toISOString().split('T')[0];
            document.getElementById('paymentDate').value = currentDate;
        });
</script>
</body>
</html>
