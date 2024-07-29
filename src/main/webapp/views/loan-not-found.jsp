<!DOCTYPE html>
<html>
<head>
    <title>Customer Not Found</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f5f5f5;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 80%;
            margin: 50px auto;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            padding: 40px;
            text-align: center;
        }

        h1 {
            color: #333;
        }

        p {
            color: #666;
        }

        .btn {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            text-decoration: none;
            display: inline-block;
            margin-top: 20px;
            transition: background-color 0.3s;
        }

        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Loan Summary Not Found</h1>
    <p>Sorry, we couldn't find any existing Loan corresponding to the provided loan account number.</p>
    <a href="loan-summary" class="btn" onclick="goBack()">Go Back</a>
</div>
<style>
function goBack() {
    window.history.back();
    }
</style>
</body>
</html>
