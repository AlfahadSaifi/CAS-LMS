<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <style>
    .warning-message {
            background-color: #ffc107;
            color: #333;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #333;
            width: 300px;
            margin: 0 auto;
            text-align: center;
        }
        .go-back-button {
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            padding: 10px 20px;
            margin-top: 10px;
            cursor: pointer;
        }

        .go-back-button:hover {
            background-color: #0056b3; /* Darker blue background on hover */
        }

    </style>
    <script>function goBack() {
                    window.history.back();
                }
                </script>
</head>

<body>

<div class="warning-message">
    <p>There is no such Loan Account having this Loan Account Number</p>
    <button class="go-back-button" onclick="goBack()">Go Back</button>
</div>
</body>
</html>
