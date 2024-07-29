<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Loan Report Generator</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <!-- Custom CSS for creative adjustments -->
    <style>
        .loandisbursal {
            margin: auto;
            width: 50%;
            padding: 20px;
            background-color: #fff; /* White background */
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Soft shadow effect */
        }
        h2 {
            text-align: center;
            margin-bottom: 30px;
        }
        label {
            font-weight: bold;
        }
        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ced4da;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        input[type="submit"] {
            width: 40%;
            padding: 10px;
            background-color: #007bff; /* Bootstrap primary color */
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #0056b3; /* Darker shade of primary color on hover */
        }
    </style>
</head>
<body>
<!-- Navbar inclusion -->
<%@ include file="navbar.jsp"%>
<div class="container" style="margin-top:5%">
    <div class="loandisbursal">
        <form action="loanForm.do" class="text-center">
            <h2>Disbursal Report Generator</h2>
            <div class="form-group">
                <label for="loanAccountNumber">Enter Loan Account Number:</label>
                <input type="text" id="loanAccountNumber" name="loanAccountNumber" class="form-control" required>
            </div>
            <input type="submit" name="submit" value="Generate" class="btn btn-primary">
        </form>
    </div>
</div>
<!-- Bootstrap JS and DataTables JS (if needed) can be included here -->
</body>
</html>