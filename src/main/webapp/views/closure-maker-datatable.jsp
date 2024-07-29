<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page import="org.nucleus.dto.LoanClosureDTO, java.util.*" isELIgnored="false"%>
<%@ page import="org.nucleus.dto.ReceivablePayableDTO, java.util.*" isELIgnored="false"%>
<html>
<head>
<title>Loan Closed List</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">

<style>
 .buttonClose{
 border: 10px;
 color: black;
 font-size: 20px;
 font-weight: bold;
 padding: 10px;
 margin: 10px;
}

</style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<br>
<div class="buttonClose">
    <button id="toggleClosed" ><spring:message code="label.buttonClosed"/></button>
    <button id="toggleNotClosed" ><spring:message code="label.buttonNotClosed"/></button>
</div>
<br>
<div id="closedLoans" style="display: none;">
    <h4><spring:message code="label.closureHeadClosed"/></h4>
    <hr>
    <table id="loanClosureTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
        <thead class="thead-dark">
        <tr>
        <th><spring:message code="th.tableheadA"/></th>
        <th><spring:message code="th.tableheadB"/></th>
        <th><spring:message code="th.tableheadC"/></th>
        <th><spring:message code="th.tableheadD"/></th>
        <th><spring:message code="th.tableheadE"/></th>
        <th><spring:message code="th.tableheadF"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${loanClosureTempList}" var="parameter">
        <tr>
        <td>${parameter.loanClosureId}</td>
        <td>${parameter.loanAccountDTO.loanAccountNumber}</td>
        <td>${parameter.closureStatus}</td>
        <td>${parameter.loanClosureDate}</td>
        <td><sec:authentication property="principal.username" /></td>
        <td>${parameter.metaData.creationDate}</td>
        </div>
        </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<br>
<div id="notClosedLoans" style="display: none;">
    <h4><spring:message code="label.closureHeadNotClosed"/></h4>
    <hr>
    <table id="loanClosureTable1" class="table table-striped table-bordered" style="width:100%; font-size: small;">
        <thead class="thead-dark">
        <tr>
        <th><spring:message code="th.tableheadB"/></th>
        <th><spring:message code="th.tableheadG"/></th>
        <th><spring:message code="th.tableheadH"/></th>
        <th><spring:message code="th.tableheadI"/></th>
        <th><spring:message code="th.tableheadJ"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${loanNotClosedList}" var="parameter">
        <tr>
        <td>${parameter.loanAccount.loanAccountNumber}</td>
        <td>${parameter.receivablePayableId}</td>
        <td>${parameter.receivablePayableDueDate}</td>
        <td>${parameter.receivablePayableAmount}</td>
        <td>${parameter.penalty}</td>
        </div>
        </td>
        </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<script>

$(document).ready(function() {
    $('#toggleClosed').click(function() {
        $('#closedLoans').toggle();
        $('#loanClosureTable').DataTable();
    });

    $('#toggleNotClosed').click(function() {
        $('#notClosedLoans').toggle();
        $('#loanClosureTable1').DataTable();
    });
});
</script>

</body>
</html>



