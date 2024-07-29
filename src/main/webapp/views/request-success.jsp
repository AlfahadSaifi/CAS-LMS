<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Update Successful</title>
  <!-- Bootstrap CSS -->
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container">
  <div class="alert alert-success mt-5" role="alert">
    <h4 class="alert-heading">Request Successful!</h4>
    <p>Early Closure Request Has Been Submitted Successfully!</p>
    <p>Your Loan Closure Request ID : ${loanClosureId}</p>
    <p>Total OverDue Amount to be Paid : ${dueAmount}</p>
    <p>Please Click Below to Generate Receivable</p>
    <hr>
    <button type="submit" class="btn btn-primary" id="clearBtn">
      <a href="receivable-payable" style="text-decoration: none; color: inherit;">Generate Receivable</a>
    </button>
  </div>
</div>
<!-- Bootstrap JS (optional) -->
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
