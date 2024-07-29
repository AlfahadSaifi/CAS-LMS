<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.nucleus.entity.permanent.Receipt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Receipt Report | Download</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <style>
        .div1 {
            justify-content: center;
            align-items: center;
            width: 35%;
            margin: 0 auto;
        }

        .receipt-heading {
            text-align: center;
            margin-top: 40px;
        }

        .receiptTable {
            margin-bottom: 40px;
            width: 100%;
        }

        .receiptTable tbody {
            border-collapse: collapse;
        }

        .receiptTable tbody tr:nth-child(odd) {
            background-color: #f2f2f2; /* Grey background for odd rows */
        }

        .receiptTable tbody tr:nth-child(even) {
            background-color: #ffffff; /* White background for even rows */
        }

        th, td {
            text-align: left;
            padding: 8px;
            border: none; /* Remove borders */
        }

        th {
            background-color: #f2f2f2; /* Grey background for table header */
        }

        #button-container {
            text-align: center;
            margin-top: 20px;
        }

        .btn-primary {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 8px;
            padding: 15px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type=submit] {
            background-color: black;
            border: none;
            color: white;
            padding: 12px 25px;
            text-decoration: none;
            margin: 4px 2px;
            cursor: pointer;
        }

        #button-container form {
            display: inline;
            margin: 0 10px; /* Adjust the spacing between buttons */
        }
    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>

<div class="container" style="margin-bottom:15px;">
    <h1 class="receipt-heading"><spring:message code="receipt"/></h1>
    <c:set var="item" value="${list[0]}" />
    <table class="table receiptTable">
        <!-- Your table content here -->
        <tr>
            <td class="bold-entry"><spring:message code="loanAccountNumber"/></td>
            <td class="value-cell">${item.loanAccountNumber}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="receiptBasis"/></td>
            <td class="value-cell">${item.receiptbasis}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="currency"/></td>
            <td class="value-cell">${item.currency}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="transactionDate"/></td>
            <td class="value-cell">${item.transactionDate}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="transactionAmount"/></td>
            <td class="value-cell">${item.transactionAmount}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="dateOfReceipt"/></td>
            <td class="value-cell">${item.dateOfReceipt}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="instrumentNumber"/></td>
            <td class="value-cell">${item.instrumentNumber}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="instrumentDate"/></td>
            <td class="value-cell">${item.instrumentDate}</td>
        </tr>
        <tr>
            <td class="bold-entry"><spring:message code="bankName"/></td>
            <td class="value-cell">${item.bankName}</td>
        </tr>
    </table>
</div>

<div id="button-container">
    <form action="../back" method="post">
        <button class="btn btn-primary" type="submit"><spring:message code="button.back"/></button>
    </form>
    <form action="../getPDFDownload/${item.receiptId}" method="post">
        <button class="btn btn-primary" type="submit"><spring:message code="button.downloadpdf"/></button>
    </form>
    <form action="../getCSV/${item.receiptId}" method="post">
        <button class="btn btn-primary" type="submit"><spring:message code="button.downloadcsv"/></button>
    </form>
    <form action="../getXLS/${item.receiptId}" method="post">
        <button class="btn btn-primary" type="submit"><spring:message code="button.downloadxls"/></button>
    </form>
</div>

<!-- Bootstrap JS and DataTables JS (if needed) can be included here -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function() {
        $('#receiptTable').DataTable({
            "paging": false, // Disable paging
            "searching": false, // Disable searching
            "ordering": true, // Enable sorting
            "info": false // Disable table information
        });
    });
</script>
</body>
</html>