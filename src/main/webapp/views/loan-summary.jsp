<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Loan Summary</title>
<style>
.container-fluid {
background-color: #fff;
padding: 20px;
border-radius: 10px;
box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
width: 400px;
font-family: Arial, sans-serif;
margin: 0;
padding: 0;
display: flex;
justify-content: center;
align-items: center;
min-height: 90vh;
}
.form-group {
margin-bottom: 20px;
}
label {
display: block;
margin-bottom: 5px;
font-weight: bold;
}
input[type="text"] {
width: 90%;
padding: 10px;
border: 1px solid #ccc;
border-radius: 5px;
}
input[type="submit"] {
background-color: #007bff;
color: #fff;
border: none;
padding: 10px 20px;
cursor: pointer;
border-radius: 5px;
}
input[type="submit"]:hover {
background-color: #0056b3;
}
.title{
text-align:center;
padding:10px;
}
.required::after {
content: "*";
color: red;
}
</style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container-fluid">
<h2 class="title">Loan Summary</h2>
<form id="loanForm" action="get-loan-summary">
<div class="form-group">
<label for="loanAccountNumber" class="required">Loan Account Number </label>
<input type="text" id="loanAccountNumber" name="loanAccountNumber" placeholder="Enter loan Account number" required>
</div>
<div class="form-group">
<input type="submit" value="Generate Loan Summary">
</div>
</form>
</div>
</body>
</html>