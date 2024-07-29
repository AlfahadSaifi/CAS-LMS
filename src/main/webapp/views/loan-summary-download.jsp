<!DOCTYPE html>
<html lang="en">
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@page isELIgnored = "false" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<head>
<meta charset="UTF-8">
<title>Title</title>
<style>
.loanSummary table {
width: 90%;
border-collapse: collapse;
margin-bottom: 20px;
margin: auto;
padding:20px;
}
.loanSummary th, td {
padding: 10px;
border: 1px solid #ddd;
text-align: left;
<!--border:1px solid black;-->
}
.loanSummary th {
background-color: #F8F9FA;
}
.loanSummary tr:nth-child(odd) {
background-color: #F8F9FA;
}
.loanSummary tr:nth-child(even) {
background-color: white;
}
.customerContainer table {
width: 90%;
border-collapse: collapse;
margin-bottom: 20px;
margin: auto;
border: none;
background: #F8F9FA;
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.customerContainer td,th{
padding: 10px;
width: 25%;
}
.newTBL table {
width: 90%;
border-collapse: collapse;
margin-bottom: 20px;
margin: auto;
border: none;
background: #FED392;
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.newTBL td,th{
padding: 10px;
width: 25%;
}
.text-right{
text-align: right;
}
.text-left{
text-align: left;
}
.text-center{
text-align: center;
}
input{
padding:6px;
}
.header-container
{
display: flex;
}
.download-button {
position: fixed;
bottom: 20px;
right: 20px;
padding: 10px 20px;
background-color: #007bff;
color: #fff;
border: none;
border-radius: 5px;
cursor: pointer;
}
.goback-button {
position: fixed;
bottom: 20px;
left: 20px;
padding: 10px 20px;
background-color: #007BFF;;
color: white;
border: none;
border-radius: 5px;
cursor: pointer;
}
.goback-button:hover {
background-color: #0056b3;
}
header{
width: 90%;
margin:auto
}
.button {
display: inline-block;
padding: 10px 20px;
background-color: #007bff;
color: #fff;
border: none;
border-radius: 5px;
cursor: pointer;
text-decoration: none;
margin: 5px;
}
.button:hover {
background-color: #0056b3;
}
.button-row {
display: flex;
justify-content: space-between;
padding:30px;
}
.button-row form {
margin-right: 10px;
}
.left-button {
margin-right: auto;
}
.center-buttons {
display: flex;
}
.center-buttons button {
margin-right: 10px;
}
.right-button {
margin-left: auto;
}
.loanSummary table {
width: 90%;
border-collapse: collapse;
margin-bottom: 20px;
margin: auto;
padding: 20px;
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}
.loanSummary th,
.loanSummary td {
padding: 10px;
border: 1px solid #ddd;
text-align: left;
}
/* Remove borders for table with class loanSummaryTbl */
.loanSummaryTbl {
box-shadow: 0 2px 4px rgba(0,0,0,0.1);
border: none;
background-color: #F8F9FA;
}
</style>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container">
<div class="customerContainer">
<header>
<br>
<h1><spring:message code="loanSummary.viewer" /></h1>
<br>
</header>
<table>
<tr>
<th class="text-center">
<img src="${pageContext.request.contextPath}/static/customer-img.jpg" alt="customerimg" style="width: 40px; height: 40px;">
&nbsp ${loanSummaryEntity.fullName}</th>
<th class="text-right"><spring:message code="loanSummary.contactNumber" /> ${loanSummaryEntity.contactNumber}</th>
<th class="text-right"><spring:message code="loanSummary.loanAccountNumber" /></th>
<th class="text-left">
<form>
<input type="text" id="loanAccountNumber" value="${loanSummaryEntity.loanAccountNumber}" disabled>
</form>
</th>
</tr>
</table>
<br>
</div>
<div class="newTBL">
<table class="loanSummaryTbl">
<tr>
<th class="text-left"><spring:message code="loanSummary.loanAmount" /></th>
<th class="text-left"><spring:message code="loanSummary.maturityDate" /></th>
<th class="text-left"><spring:message code="loanSummary.nextInstallmentDate" /></th>
<th class="text-left"><spring:message code="loanSummary.outstandingBalancePrincipal" />/<spring:message code="loanSummary.totalInstallments" /></th>
<th class="text-left"><spring:message code="loanSummary.principalOutstanding" /></th>
</tr>
<tr>
<td>Rs.${loanSummaryEntity.loanAmount}</td>
<td>${loanSummaryEntity.maturityDate}</td>
<td>${loanSummaryEntity.nextInstallmentDate}</td>
<td>${loanSummaryEntity.outstandingBalancePrincipal}/${loanSummaryEntity.totalInstallments}</td>
<td>${loanSummaryEntity.outstandingBalancePrincipal}</td>
</tr>
</table>
</div>
<br>
<div class="loanSummary">
<table border="1">
<tr>
<th><spring:message code="loanSummary.customerDues" /></th>
<th><spring:message code="loanSummary.paid" /></th>
<th><spring:message code="loanSummary.dueAsOnDate" /></th>
</tr>
<tr>
<td><spring:message code="loanSummary.numInstallments" /></td>
<td class="text-right">${loanSummaryEntity.installmentsPaid}</td>
<td class="text-right">${loanSummaryEntity.installmentsRemaining}</td>
</tr>
<tr>
<td><spring:message code="loanSummary.emiAmount" /></td>
<td class="text-right">${loanSummaryEntity.installmentAmountPaid}</td>
<td class="text-right">${loanSummaryEntity.installmentAmountRemaining}</td>
</tr>
<tr>
<td><spring:message code="loanSummary.lppCharges" /></td>
<td class="text-right">${loanSummaryEntity.penaltyChargesPaid}</td>
<td class="text-right">${loanSummaryEntity.penaltyChargesRemaining}</td>
</tr>
<tr>
<td><spring:message code="loanSummary.otherCharges" /></td>
<td class="text-right">${loanSummaryEntity.otherChargesPaid}</td>
<td class="text-right">${loanSummaryEntity.getOtherChargesRemaining}</td>
</tr>
<tr>
<td><strong><spring:message code="loanSummary.total" /></strong></td>
<td class="text-right">${loanSummaryEntity.totalPaid}</td>
<td class="text-right">${loanSummaryEntity.totalOverdue}</td>
</tr>
</table>
</div>
<div class="button-row">
<button onclick="goBack()" class="button left-button"><spring:message code="loanSummary.goBack" /></button>
<form action="getPDF" modelAttribute="loanSummary" method="post">
<input type="hidden" name="loanAccountNumber" value="${loanSummaryEntity.loanAccountNumber}" />
<input type="hidden" name="fullName" value="${loanSummaryEntity.fullName}" />
<input type="hidden" name="contactNumber" value="${loanSummaryEntity.contactNumber}" />
<input type="hidden" name="loanAmount" value="${loanSummaryEntity.loanAmount}" />
<input type="hidden" name="emiAmount" value="${loanSummaryEntity.emiAmount}" />
<input type="hidden" name="maturityDate" value="${loanSummaryEntity.maturityDate}" />
<input type="hidden" name="totalInstallments" value="${loanSummaryEntity.totalInstallments}" />
<input type="hidden" name="nextInstallmentDate" value="${loanSummaryEntity.nextInstallmentDate}" />
<input type="hidden" name="outstandingBalancePrincipal" value="${loanSummaryEntity.outstandingBalancePrincipal}" />
<input type="hidden" name="installmentsPaid" value="${loanSummaryEntity.installmentsPaid}" />
<input type="hidden" name="installmentsRemaining" value="${loanSummaryEntity.installmentsRemaining}" />
<input type="hidden" name="installmentAmountPaid" value="${loanSummaryEntity.installmentAmountPaid}" />
<input type="hidden" name="installmentAmountRemaining" value="${loanSummaryEntity.installmentAmountRemaining}" />
<input type="hidden" name="penaltyChargesPaid" value="${loanSummaryEntity.penaltyChargesPaid}" />
<input type="hidden" name="penaltyChargesRemaining" value="${loanSummaryEntity.penaltyChargesRemaining}" />
<input type="hidden" name="otherChargesPaid" value="${loanSummaryEntity.otherChargesPaid}" />
<input type="hidden" name="getOtherChargesRemaining" value="${loanSummaryEntity.getOtherChargesRemaining}" />
<input type="hidden" name="totalPaid" value="${loanSummaryEntity.totalPaid}" />
<input type="hidden" name="totalOverdue" value="${loanSummaryEntity.totalOverdue}" />
<input type="submit" value="<spring:message code="loanSummary.downloadPdf" />" class="button center-buttons"/>
</form>
<form action="getCSV" modelAttribute="loanSummary" method="post" >
<input type="hidden" name="loanAccountNumber" value="${loanSummaryEntity.loanAccountNumber}" />
<input type="hidden" name="fullName" value="${loanSummaryEntity.fullName}" />
<input type="hidden" name="contactNumber" value="${loanSummaryEntity.contactNumber}" />
<input type="hidden" name="loanAmount" value="${loanSummaryEntity.loanAmount}" />
<input type="hidden" name="emiAmount" value="${loanSummaryEntity.emiAmount}" />
<input type="hidden" name="maturityDate" value="${loanSummaryEntity.maturityDate}" />
<input type="hidden" name="totalInstallments" value="${loanSummaryEntity.totalInstallments}" />
<input type="hidden" name="nextInstallmentDate" value="${loanSummaryEntity.nextInstallmentDate}" />
<input type="hidden" name="outstandingBalancePrincipal" value="${loanSummaryEntity.outstandingBalancePrincipal}" />
<input type="hidden" name="installmentsPaid" value="${loanSummaryEntity.installmentsPaid}" />
<input type="hidden" name="installmentsRemaining" value="${loanSummaryEntity.installmentsRemaining}" />
<input type="hidden" name="installmentAmountPaid" value="${loanSummaryEntity.installmentAmountPaid}" />
<input type="hidden" name="installmentAmountRemaining" value="${loanSummaryEntity.installmentAmountRemaining}" />
<input type="hidden" name="penaltyChargesPaid" value="${loanSummaryEntity.penaltyChargesPaid}" />
<input type="hidden" name="penaltyChargesRemaining" value="${loanSummaryEntity.penaltyChargesRemaining}" />
<input type="hidden" name="otherChargesPaid" value="${loanSummaryEntity.otherChargesPaid}" />
<input type="hidden" name="getOtherChargesRemaining" value="${loanSummaryEntity.getOtherChargesRemaining}" />
<input type="hidden" name="totalPaid" value="${loanSummaryEntity.totalPaid}" />
<input type="hidden" name="totalOverdue" value="${loanSummaryEntity.totalOverdue}" />
<input type="submit" value="<spring:message code="loanSummary.downloadCsv" />" class="button center-buttons"/>
</form>
<form action="getXLS" modelAttribute="loanSummary" method="post" >
<input type="hidden" name="loanAccountNumber" value="${loanSummaryEntity.loanAccountNumber}" />
<input type="hidden" name="fullName" value="${loanSummaryEntity.fullName}" />
<input type="hidden" name="contactNumber" value="${loanSummaryEntity.contactNumber}" />
<input type="hidden" name="loanAmount" value="${loanSummaryEntity.loanAmount}" />
<input type="hidden" name="emiAmount" value="${loanSummaryEntity.emiAmount}" />
<input type="hidden" name="maturityDate" value="${loanSummaryEntity.maturityDate}" />
<input type="hidden" name="totalInstallments" value="${loanSummaryEntity.totalInstallments}" />
<input type="hidden" name="nextInstallmentDate" value="${loanSummaryEntity.nextInstallmentDate}" />
<input type="hidden" name="outstandingBalancePrincipal" value="${loanSummaryEntity.outstandingBalancePrincipal}" />
<input type="hidden" name="installmentsPaid" value="${loanSummaryEntity.installmentsPaid}" />
<input type="hidden" name="installmentsRemaining" value="${loanSummaryEntity.installmentsRemaining}" />
<input type="hidden" name="installmentAmountPaid" value="${loanSummaryEntity.installmentAmountPaid}" />
<input type="hidden" name="installmentAmountRemaining" value="${loanSummaryEntity.installmentAmountRemaining}" />
<input type="hidden" name="penaltyChargesPaid" value="${loanSummaryEntity.penaltyChargesPaid}" />
<input type="hidden" name="penaltyChargesRemaining" value="${loanSummaryEntity.penaltyChargesRemaining}" />
<input type="hidden" name="otherChargesPaid" value="${loanSummaryEntity.otherChargesPaid}" />
<input type="hidden" name="getOtherChargesRemaining" value="${loanSummaryEntity.getOtherChargesRemaining}" />
<input type="hidden" name="totalPaid" value="${loanSummaryEntity.totalPaid}" />
<input type="hidden" name="totalOverdue" value="${loanSummaryEntity.totalOverdue}" />
<input type="submit" value="<spring:message code="loanSummary.downloadXls" />" class="button center-buttons" />
</form>
<button class="button right-button" id="downloadButton"><spring:message code="loanSummary.print" /></button>
</div>
</div>
<script>
// Function to navigate back to the previous page
function goBack() {
window.history.back();
}
</script>
<script>
// Function to handle the click event on the download button
document.getElementById('downloadButton').addEventListener('click', function() {
// Print the page
window.print();
});
</script>
</body>
</html>