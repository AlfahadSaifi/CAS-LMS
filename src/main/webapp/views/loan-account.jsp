<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Loan Application Form</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4">
  <h3>Search Account Number</h3>
  <form action="disburse-form" method="post" modelAttribute="loanAccount">

    <div class="row">
        <div class="form-group col-md-6">
            <label for="loanAccountNumber" >Enter Loan Account Number</label>
            <div class="input-group">
                <input type="text" class="form-control" placeholder="Search Loan Account" id="loanAccountNumberSearch" name="loanAccountNumberSearch" >
                <div class="input-group-append">
                    <button type="submit" name="action" value="search" class="btn btn-primary">Search</button>
                </div>
            </div>
        </div>
    </div>


    <%-- Display error message if present --%>
        <c:if test="${not empty errorMessage}">
            <div class="row">
                <div class="form-group col-md-6">
                    <p style="color: red;">${errorMessage}</p>
                </div>
            </div>
        </c:if>


 <c:if test="${not empty loanApplication}">
    <h2>Loan Account Details</h2>

    <div class="row">

            <div class="form-group col-md-6">
                <label for="loanAccountNumber">Loan Account Number</label>
                <div class="input-group">
                    <input type="text" class="form-control" id="loanAccountNumber" name="loanAccountNumber" value="${loanApplication.loanAccountNumber}" readonly >
                </div>
            </div>
            <div class="form-group col-md-6" >
                  <label for="loanSanctionDate">Loan Sanction Date</label>
                  <input type="date" class="form-control" id="loanSanctionDate" name="loanSanctionDate" value="${requiredField.loanSanctionDate}" readonly >
            </div>
    </div>


    <div class="row">

    <div class="form-group col-md-6">
      <label for="disbursalDate">Disbursal Date</label>
      <input type="date" class="form-control" id="disbursalDate" name="disbursalDate" value="${requiredField.disbursalDate}" readonly>
    </div>


    <div class="form-group col-md-6">
      <label for="cifNumber">CIFNumber</label>
      <input type="text" class="form-control" id="cifNumber" name="cifNumber" value="${requiredField.cifNumber}" readonly>
    </div>
    </div>

    <div class="row">
    <div class="form-group col-md-6">
      <label for="finalSanctionedAmount">Final Sanctioned Amount</label>
      <input type="number" step="any" class="form-control" id="finalSanctionedAmount" name="finalSanctionedAmount" value="${requiredField.sanctionedAmt}" readonly>
    </div>

    <div class="form-group col-md-6">
      <label for="loanAmountDisbursed">Loan Amount Disbursed</label>
      <input type="number" step="any" class="form-control" id="loanAmountDisbursed" name="loanAmountDisbursed" value="${requiredField.disbursedAmt}" readonly>
    </div>
    </div>

    <div class="row">
        <div class="form-group col-md-6">
          <label for="loanProduct">Product</label>
          <input type="number" class="form-control" id="loanProduct" name="loanProduct" value="${requiredField.product}" readonly>
        </div>

        <div class="form-group col-md-6">
          <label for="productType">Product Type</label>
          <input type="number" class="form-control" id="productType" name="productType" value="${requiredField.productType}" readonly>
        </div>
    </div>


    <div class="row">

    <div class="form-group col-md-6">
      <label for="numberOfInstallmentBilled">Number of Installments Billed</label>
      <input type="number" class="form-control" id="numberOfInstallmentBilled" name="numberOfInstallmentBilled" value="${requiredField.noOfInstallmentsBilled}" readonly>
    </div>

    <div class="form-group col-md-6">
      <label for="numberOfInstallmentUnbilled">Number of Installments Unbilled</label>
      <input type="number" class="form-control" id="numberOfInstallmentUnbilled" name="numberOfInstallmentUnbilled" value="${requiredField.noOfInstallmentsUnbilled}" readonly>
    </div>
    </div>

    <div class="row">

    <div class="form-group col-md-6">
      <label for="originalEmiAmount">Original EMI Amount</label>
      <input type="number" step="any" class="form-control" id="originalEmiAmount" name="originalEmiAmount" value="${requiredField.emiAmt}" readonly>
    </div>

    <div class="form-group col-md-6">
      <label for="originalTenure">Original Tenure</label>
      <input type="number" class="form-control" id="originalTenure" name="originalTenure" value="${loanApplication.tenure}" readonly>
    </div>

    </div>

    <div class="row">
    <div class="form-group col-md-6">
      <label for="interestRate">Interest Rate</label>
      <input type="number" step="any" class="form-control" id="interestRate" name="interestRate" value="${requiredField.roi}" readonly>
    </div>
    <div class="form-group col-md-6">
    <label for="repaymentFrequency">Repayment Frequency</label>
    <input type="text" class="form-control" id="repaymentFrequency" name="repaymentFrequency" value="${requiredField.repaymentFrequency}" readonly>
           <%-- <select class="form-control" id="repaymentFrequency" name="repaymentFrequency">
                <option value="MONTHLY">Monthly</option>
                <option value="QUARTERLY">Quarterly</option>
                <option value="HALFYEARLY">Half-Yearly</option>
                <option value="ANNUALLY">Annually</option>
            </select> --%>
    </div>
    </div>

    <div class="row">

    <div class="form-group col-md-6">
        <label for="loanStatus">Loan Status</label>
          <select class="form-control" id="loanStatus" name="loanStatus">
               <option value="ACTIVE">ACTIVE</option>
               <option value="CLOSED">CLOSED</option>
          </select>
    </div>

    <div class="form-group col-md-6">
          <label for="numberOfInstallment">Number of Installments</label>
          <input type="number" class="form-control" id="numberOfInstallment" name="numberOfInstallment" value="${requiredField.numberOfInstallment}" readonly>
        </div>

    </div>

    <button type="submit" name="action" value="submit" class="btn btn-primary">Submit</button>
    </c:if>
  </form>
</div>

</body>
</html>
