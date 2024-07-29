<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.css">
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container">
<div class="container">
     <div class="alert alert-success" role="alert">
        Successfully disbursed loan!
    </div>

     <div class="row">
        <div class="col-md-6"> <!-- This column will take up the left half of the page -->
            <div class="table-container">
                <h2>Loan Account Details</h2>
                <table class="table table-striped table-bordered">
                    <tr>
                        <th>Loan Account Number</th>
                        <td class="text-right">${loanAccount.loanAccountNumber}</td>
                    </tr>
                    <tr>
                        <th>Loan Sanction Date</th>
                        <td class="text-right">${loanAccount.loanSanctionDate}</td>
                    </tr>
                    <tr>
                        <th>Loan Disbursal Date</th>
                        <td class="text-right">${loanAccount.disbursalDate}</td>
                    </tr>
                    <tr>
                        <th>CIF Number</th>
                        <td class="text-right">${loanAccount.cifNumber}</td>
                    </tr>
                    <tr>
                        <th>Final Sanctioned Amount</th>
                        <td class="text-right">${loanAccount.finalSanctionedAmount}</td>
                    </tr>
                    <tr>
                        <th>Final Disbursed Amount</th>
                        <td class="text-right">${loanAccount.loanAmountDisbursed}</td>
                    </tr>
                    <tr>
                        <th>Product</th>
                        <td class="text-right">${loanAccount.loanProduct}</td>
                    </tr>
                    <tr>
                        <th>Product Type</th>
                        <td class="text-right">${loanAccount.productType}</td>
                    </tr>
                    <tr>
                        <th>Tenure</th>
                        <td class="text-right">${loanAccount.originalTenure}</td>
                    </tr>
                    <tr>
                        <th>Repayment Frequency</th>
                        <td class="text-right">${loanAccount.repaymentFrequency}</td>
                    </tr>
                    <tr>
                        <th>Loan Status</th>
                        <td class="text-right">${loanAccount.loanStatus}</td>
                    </tr>
                    <tr>
                        <th>Interest Rate</th>
                        <td class="text-right">${loanAccount.interestRate}</td>
                    </tr>
                    <tr>
                        <th>Number Of Installments</th>
                        <td class="text-right">${loanAccount.numberOfInstallment}</td>
                    </tr>
                    <tr>
                        <th>Number Of Installments Billed</th>
                        <td class="text-right">${loanAccount.numberOfInstallmentBilled}</td>
                    </tr>
                    <tr>
                        <th>Number Of Installments Unbilled</th>
                        <td class="text-right">${loanAccount.numberOfInstallmentUnbilled}</td>
                    </tr>
                    <tr>
                        <th>Original EMI Amount</th>
                        <td class="text-right">${loanAccount.originalEmiAmount}</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>


    <button type="button" class="btn btn-primary mt-3"> <!-- Added margin from the top -->
        <a href="loan-account" style="text-decoration: none; color: inherit;">Disburse Another Loan</a>
    </button>
    <div class="mb-5"></div> <!-- Added margin from the bottom -->
</div>

<script src="https://cdn.datatables.net/1.10.25/js/jquery.dataTables.js"></script>
</body>
</html>
