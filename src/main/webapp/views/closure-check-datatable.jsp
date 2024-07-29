<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.nucleus.dto.LoanClosureDTO, java.util.*" isELIgnored="false"%>
<html>
<head>
<title>Loan Closed List</title>
<!-- Include jQuery and DataTables CSS and JS files -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
</head>
<body>
<div>
<h2> List of All Closed Loans (To Approve) </h2>
<hr>
</div>
<div>
<table id="loanClosureTable" cellpadding="10" border="1" style="width:100%; font-size: small;">
<thead>
<tr>
<th> Loan Closure ID </th>
<th>Loan Account Number</th>
<th>Closure Status</th>
<th>Loan Closure Date</th>
<th>Status of Record</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
<c:forEach items="${loanClosureTempList}" var="parameter">
<tr>
<td>${parameter.loanClosureId}</td>
<td>${parameter.loanAccountDTO.loanAccountNumber}</td>
<td>${parameter.closureStatus}</td>
<td>${parameter.loanClosureDate}</td>
<td>${parameter.metaData.recordStatus}</td>
<td> <div style="display:flex">
<button><a href="approveLoanClosure/${parameter.loanClosureId}">Approve</a></button>|
<button><a href="rejectLoanClosure/${parameter.loanClosureId}">Reject</a></button>
</div>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<div>
</div>
<script>
$(document).ready(function() {
$('#loanClosureTable').DataTable();
});
</script>

<div>
<h2> List of All Closed Loans (Permanent Table)</h2>
<hr>
</div>
<div>
<table id="loanClosureTable" cellpadding="10" border="1" style="width:100%; font-size: small;">
<thead>
<tr>
<th> Loan Closure ID </th>
<th>Loan Account Number</th>
<th>Closure Status</th>
<th>Loan Closure Date</th>
<th>Status of Record</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
<c:forEach items="${loanClosurePerList}" var="parameter">
<tr>
<td>${parameter.loanClosureId}</td>
<td>${parameter.loanAccountDTO.loanAccountNumber}</td>
<td>${parameter.closureStatus}</td>
<td>${parameter.loanClosureDate}</td>
<td>${parameter.metaData.recordStatus}</td>
<td> <div style="display:flex">
</div>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<div>
</div>
<script>
$(document).ready(function() {
$('#loanClosureTable').DataTable();
});
</script>


<div>
<h2> List of All Closed Loans to be deleted(Temporary Table)</h2>
<hr>
</div>
<div>
<table id="loanClosureTable" cellpadding="10" border="1" style="width:100%; font-size: small;">
<thead>
<tr>
<th> Loan Closure ID </th>
<th>Loan Account Number</th>
<th>Closure Status</th>
<th>Loan Closure Date</th>
<th>Status of Record</th>
<th>Actions</th>
</tr>
</thead>
<tbody>
<c:forEach items="${loanClosureTempListDeleted}" var="parameter">
<tr>
<td>${parameter.loanClosureId}</td>
<td>${parameter.loanAccountDTO.loanAccountNumber}</td>
<td>${parameter.closureStatus}</td>
<td>${parameter.loanClosureDate}</td>
<td>${parameter.metaData.recordStatus}</td>
<td> <div style="display:flex">
<button><a href="approveLoanClosureDel/${parameter.loanClosureId}">Approve</a></button>|
<button><a href="rejectLoanClosureDel/${parameter.loanClosureId}">Reject</a></button>
</div>
</td>
</tr>
</c:forEach>
</tbody>
</table>
</div>
<div>
</div>
<script>
$(document).ready(function() {
$('#loanClosureTable').DataTable();
});
</script>

</body>
</html>