<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.nucleus.dto.LoanClosureDTO, java.util.*" isELIgnored="false"%>
<%@ page import="org.nucleus.dto.ReceivablePayableDTO, java.util.*" isELIgnored="false"%>
<html>
<head>
<title>Loan Closed List</title>
<!-- Include jQuery and DataTables CSS and JS files -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
<style>
/* Resetting default styles */
body, html, h3{
margin: 0;
padding: 0;
border: 0;
outline: 0;
font-size: 100%;
vertical-align: baseline;
background: transparent;
}
/* General styling */
body {
font-family: Arial, sans-serif;
color: #333;
}
h3 {
text-align: center;
font-weight: bold;
}

</style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div>
<h3> Could not perform Loan Closure, Kindly refer Log Files. </h3>
</div>
</body>
</html>