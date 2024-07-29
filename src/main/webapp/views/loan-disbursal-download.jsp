<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Disbursal Report</title>
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<!-- DataTables CSS -->
<link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
<style>
.customer-heading {
margin-top: 40px;
}
.customer-table {
margin-bottom: 40px;
width: 100%;
}
.customer-table tr:nth-child(odd) {
background-color: #f2f2f2; /* Grey background for odd rows */
}
.customer-table tr:nth-child(even) {
background-color: #ffffff; /* White background for even rows */
}
.value-cell {
font-weight: bold;
}
.exclude-from-print {
margin-top: 20px;
}
.btn-group {
margin-bottom: 10px;
}
.btn-container {
display: flex;
justify-content: center;
align-items: center;
}
</style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>
<div class="container" style="margin-bottom:15px;">
<h1 class="customer-heading"><spring:message code="DisbursalReport.label" /></h1>
<h2 class="customer-heading"><spring:message code="loanDetails.label" /></h2>
<table class="table customer-table">
<!-- Your table content here -->
<tr>
<td class="bold-entry"><spring:message code="loanAccount.label" /></td>
<td class="value-cell">${loanAccountNumber}</td>
<td class="bold-entry"><spring:message code="branch.label" /></td>
<td class="value-cell">${branch}</td>
</tr>
<tr>
<td class="bold-entry"><spring:message code="disbursalDate.label" /></td>
<td class="value-cell">${disbursalDate}</td>
<td class="bold-entry"><spring:message code="loanProduct.label" /></td>
<td class="value-cell">${loanProduct}</td>
</tr>
<tr>
<td class="bold-entry"><spring:message code="disbursedAmount.label" /></td>
<td class="value-cell">${loanAmountDisbursed}</td>
<td class="bold-entry"><spring:message code="finalSanctionedAmount.label" /></td>
<td class="value-cell">${finalSanctionedAmount}</td>
</tr>
<tr>
<td class="bold-entry"><spring:message code="originalEMi.label" /></td>
<td class="value-cell">${originalEmiAmount}</td>
<td class="bold-entry"><spring:message code="loanSanctionedDate.label" /></td>
<td class="value-cell">${loanSanctionDate}</td>
</tr>
<tr>
<td class="bold-entry"><spring:message code="originalTenure.label" /></td>
<td class="value-cell">${originalTenure}</td>
<td class="bold-entry"><spring:message code="repaymentFrequency.label" /></td>
<td class="value-cell">${repaymentFrequency}</td>
</tr>
<tr>
<td class="bold-entry"><spring:message code="interestRate.label" /></td>
<td class="value-cell">${interestRate}</td>
<td class="bold-entry"><spring:message code="noOFInstalments.label" /></td>
<td class="value-cell">${numberOfInstallment}</td>
</tr>
<tr>
<td class="bold-entry"><spring:message code="loanAmountPaid.label" /></td>
<td class="value-cell">${loanAmountPaidByCustomer}</td>
<td class="bold-entry"><spring:message code="productType.label" /></td>
<td class="value-cell">${productType}</td>
</tr>
</table>
<h2 class="customer-heading"><spring:message code="customerDetails.label" /></h2>
<table class="table customer-table">
<!-- Your table content here -->
<tr>
<th><spring:message code="customerName.label" /></th>
<td class="value-cell1">${name1}</td>
</tr>
<tr>
<th><spring:message code="dateOfBirth.label" /></th>
<td class="value-cell1">${dateOfBirth}</td>
</tr>
<tr>
<th><spring:message code="emailAddress.label" /></th>
<td class="value-cell1">${emailAddress}</td>
</tr>
<tr>
<th><spring:message code="cifNumber.label" /></th>
<td class="value-cell1">${cifNumber}</td>
</tr>
</table>
<h2 class="customer-heading"><spring:message code="addressDetails.label" /></h2>
<table class="table customer-table">
<!-- Your table content here -->
<tr>
<th><spring:message code="address.label" /></th>
<td class="value-cell1">${address}</td>
</tr>
<tr>
<th><spring:message code="city.label" /></th>
<td class="value-cell1">${city}</td>
</tr>
<tr>
<th><spring:message code="state.label" /></th>
<td class="value-cell1">${state}</td>
</tr>
<tr>
<th><spring:message code="country.label" /></th>
<td class="value-cell1">${country}</td>
</tr>
<tr>
<th><spring:message code="zipCode.label" /></th>
<td class="value-cell1">${zipCode}</td>
</tr>
<tr>
<th><spring:message code="contactNumber.label" /></th>
<td class="value-cell1">${contactNumber}</td>
</tr>
</table>
<div class="btn-container" style="width:100%;">
<div class="download-buttons" style="align-items:center;">
<a href="./getPDF.do?loanAccountNumber=${loanAccountNumber}" class="btn btn-primary"><spring:message code="downloadPDF.label" /></a>
<a href="./disbursal-report-xls.do" class="btn btn-primary"><spring:message code="downloadEXCEL.label" /></a>
<a href="./disbursal-report-csv.do" class="btn btn-primary"><spring:message code="downloadCSV.label" /></a>
</div>
</div>
</div>
<!-- Bootstrap JS and DataTables JS (if needed) can be included here -->
<script>
function printScreen() {
window.print();
}
function goBack() {
window.history.back();
}
</script>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<button onclick="printScreen()" class="btn btn-primary" ><spring:message code="print.label" /></button>
<!-- Bootstrap JS and DataTables JS (if needed) can be included here -->
<script>
function printScreen() {
    window.print();
}
</script>
<script>
$(document).ready(function() {
// Initialize DataTables for all tables
$('.customer-table').DataTable({
"paging": false, // Disable paging
"searching": false, // Disable searching
"ordering": true, // Enable sorting
"info": false // Disable table information
});
});
</script>
</body>
</html>