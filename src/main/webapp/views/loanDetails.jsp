<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="org.nucleus.utility.enums.RecordStatus" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Loan Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>

    <style>
        body {
            font-family: Arial, sans-serif;
            font-size: 14px;
            margin: 20px;
        }

        .container {
            max-width: 800px;
            margin: auto;
        }

        .btn-container {
           margin-bottom: 20px;
           display: flex;
           justify-content: center;
        }

        .btn-container .btn {
           margin-right: 10px;
        }

        .btn-container .btn:first-child {
           margin-right: 20px;
        }

        .btn-container .btn-secondary {
           background-color: #6c757d;
           border-color: #6c757d;
        }

        .btn-container .btn-info {
           background-color: #17a2b8;
           border-color: #17a2b8;
        }

        .table-div {
            margin-top: 20px;
        }

        .table-div table {
            width: 100%;
            font-size: 14px;
        }

        .table-div th,
        .table-div td {
            text-align: center;
            vertical-align: middle;
        }

        #repayScheduleModal {
            display: none;
            position: fixed;
            z-index: 1;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: auto;
            margin-top: 20px;
            padding: 0 20px 20px 20px;
            border: 1px solid #888;
            width: 90%;
            max-height: calc(100vh - 40px); /* Set maximum height of modal content */
            overflow-y: auto; /* Enable vertical scrolling */
            position: relative; /* Ensure absolute positioning of close button */
        }

        .modal-content table {
            width: 100%;
        }

        .modal-content table thead {
            position: sticky;
            top: 0;
            background-color: #fff;
            z-index: 1;
        }

        .modal-content table thead th {
            text-align: center;
            background-color: #343a40;
            color: white;
        }

        .modal-content table tbody {
            max-height: calc(100% - 40px); /* Adjust height to fit remaining space */
            overflow-y: auto; /* Enable vertical scrolling */
        }

        .modal-content table tbody tr td {
            text-align:right;
            white-space: nowrap;
            width:auto;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            position: absolute;
            top: 0;
            right: 0;
            padding: 5px 10px;
            background-color: #fefefe; /* Match modal background */
            z-index: 2; /* Ensure close button is above tbody content */
            cursor:pointer;
        }



        .spinner {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background */
            z-index: 9999;
        }

        #loading {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            border: 5px solid #f3f3f3; /* Light grey */
            border-top: 5px solid #3498db; /* Blue */
            border-radius: 50%;
            width: 50px;
            height: 50px;
            animation: spin 1s linear infinite; /* Spin animation */
        }

        @keyframes spin {
            0% { transform: rotate(0deg); }
            100% { transform: rotate(360deg); }
        }
    </style>
</head>
<body>

<%@ include file="navbar.jsp"%>
<div class="container mt-5" style="display:flex; justify-content:space-between;align-items: baseline;flex-wrap: wrap;">
    <h2>Loan Details</h2>
    <a class="btn btn-secondary" href="javascript:history.back()">Back</a>
</div>

<div class="container my-4 card shadow border rounded py-3 px-3">
    <div class="table-div">
        <table class="table table-bordered">
            <tbody>
                <tr>
                    <th>Loan Application Id</th>
                    <td style='text-align:right;'>${loanApplication.loanApplicationId}</td>
                </tr>
                <tr>
                    <th>Application Creation Date</th>
                    <td style='text-align:right;'>${loanApplication.applicationCreationDate}</td>
                </tr>
                <tr>
                    <th>Loan Amount</th>
                    <td style='text-align:right;'>${loanApplication.loanAmount}</td>
                </tr>
                <tr>
                    <th>Tenure</th>
                    <td style='text-align:right;'>${loanApplication.tenure}</td>
                </tr>
                <tr>
                    <th>Loan Account Number</th>
                    <td style='text-align:right;'>${loanApplication.loanAccountNumber}</td>
                </tr>
                <tr>
                    <th>Branch</th>
                    <td style='text-align:right;'>${loanApplication.branch}</td>
                </tr>
                <tr>
                    <th>Tenure In</th>
                    <td style='text-align:right;'>${loanApplication.tenureIn}</td>
                </tr>
                <tr>
                    <th>Rate</th>
                    <td style='text-align:right;'>${loanApplication.loanProductDTO.rateOfInterest}</td>
                </tr>
                <tr>
                    <th>Loan Status</th>
                    <td style='text-align:right;'>${loanApplication.loanStatus}</td>
                </tr>
                <tr>
                    <th>Customer Id</th>
                    <td style='text-align:right;'>${loanApplication.customerDTO.customerId}</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div class="btn-container">
        <button class="btn btn-info" id="repayScheduleBtn">Repay Schedule</button>
        <a class="btn btn-primary" id="approveBtn"
            href="${pageContext.request.contextPath}/checker/approve-loan-application/${loanApplication.loanApplicationId}"
            disabled="true" style="cursor:no-drop">Approve
        </a>
        <a class="btn btn-danger"
            href="${pageContext.request.contextPath}/checker/reject-loan-application/${loanApplication.loanApplicationId}">Reject
        </a>
    </div>
</div>

<!-- Repay Schedule Modal -->
<div id="repayScheduleModal" class="modal">
    <div class="modal-content" style="width: 90%;">
        <span class="close">&times;</span>
        <h3><spring:message code="RepaymentSchedule"/></h3>
        <br>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th><spring:message code="InstallmentNumber"/></th>
                    <th><spring:message code="DueDate"/></th>
                    <th><spring:message code="OpeningBalance"/></th>
                    <th><spring:message code="Installment"/></th>
                    <th><spring:message code="Principal"/></th>
                    <th><spring:message code="Interest"/></th>
                    <th><spring:message code="EffectiveRate"/></th>
                    <th><spring:message code="OutstandingBalance"/></th>
                </tr>
            </thead>
            <tbody id="repayScheduleTableBody">
                <!-- Repayment schedule table rows will be dynamically added here -->
            </tbody>
        </table>
    </div>
</div>

<!-- Search Modal -->
<div class="spinner">
    <div id="loading"></div> <!-- Loading spinner -->
</div>



<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<script>

    $(document).ready(function() {
        $('.spinner').hide();
        $("#repayScheduleBtn").click(function() {
            $('.spinner').show();
            $.ajax({
                url: "${pageContext.request.contextPath}/checker/generate-repay-schedule",
                type: "GET",
                data: {loanId: ${loanApplication.loanId} },
                success: function(repayScheduleData) {
                    $('.spinner').hide();
                    $('#approveBtn').css('cursor', 'pointer');
                    $("#repayScheduleTableBody").empty();
                    repayScheduleData.forEach(function(schedule) {
                    var date1 = new Date(schedule.dueDate);
                    date1.setDate(date1.getDate() + 1);
                        var row = "<tr>" +
                            "<td>" + schedule.installmentNumber + "</td>" +
                            "<td>" + date1.toISOString().split('T')[0] + "</td>" +
                            "<td>" + parseFloat(schedule.openingBalance).toFixed(2) + "</td>" +
                            "<td>" + parseFloat(schedule.installmentAmount).toFixed(2) + "</td>" +
                            "<td>" + parseFloat(schedule.principalComponent).toFixed(2) + "</td>" +
                            "<td>" + parseFloat(schedule.interestComponent).toFixed(2) + "</td>" +
                            "<td>" + parseFloat(schedule.effectiveInterestRate).toFixed(2) + "</td>" +
                            "<td>" + parseFloat(schedule.outstandingBalancePrincipal).toFixed(2) + "</td>" +
                            "</tr>";
                        $("#repayScheduleTableBody").append(row);
                    });

                    $("#repayScheduleModal").css("display", "block");
                },
                error: function(xhr, status, error) {
                    $('.spinner').hide();
                    console.error("Error fetching repay schedule:", error);
                }
            });
        });

        $(".close").click(function() {
            $("#repayScheduleModal").css("display", "none");
            $("#approveBtn").prop("disabled", false);
        });
    });
</script>

</body>
</html>
