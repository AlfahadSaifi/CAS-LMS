<%@ page import="java.util.List" %>
<%@ page import="org.nucleus.dto.RepayScheduleDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Repay Schedule</title>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.5/css/dataTables.dataTables.css">
    <style>

        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        .container-fluid {
            max-width: 1200px;
            margin: 20px auto;
            padding: 0 20px;
        }

        /* Header styling */
        .mainHeading {
            font-weight: 500;
            margin-bottom: 20px;
        }

        /* Form styling */
        .formContainer {
            margin-bottom: 20px;
        }

        .formContent {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
        }

        .formHeading {
            margin-right: 20px;
            margin-bottom: 0;
        }

        .fieldContainer {
            padding: 5px;
            display:flex;

        }

        .inputField {
            flex: 1;
            height: 30px;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 0;
        }

        /* Table styling */
        #myDataTable {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        #myDataTable th,
        #myDataTable td {
            border: 1px solid #ccc;
            padding: 12px;
            text-align: center;
        }

        .loader {
        border: 4px solid #f3f3f3;
        border-top: 4px solid #3498db;
        border-radius: 50%;
        width: 40px;
        height: 40px;
        animation: spin 2s linear infinite;
        margin: 20px auto;
        }
        @keyframes spin {
        0% { transform: rotate(0deg); }
        100% { transform: rotate(360deg); }
        }

        #myDataTable td
        {
            text-align:right;
        }

    </style>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div id="loadingSpinner" style="display: none;">
        <div class="loader"></div>
    </div>
    <div class="container-fluid">
        <h2 class="mainHeading"><spring:message code="RepaymentSchedule"/></h1>
        <div class="formContainer">
            <div class="formContent">
                <form id="generateForm">

                    <div class="fieldContainer">
                        <span style="white-space: nowrap; margin-top: 10px;">
                                <spring:message code="LoanApplicationId"/>
                            </span> &nbsp
                            <input type="text" id="loanApplicationId" class="form-control" placeholder="LoanApplicationId" required>
                        <button type="button" id="generateBtn" class="btn btn-primary fixbutton" style="height: 37px;"><spring:message code="Generate"/></button>
                    </div>
                    <span id="errorMsg" style="color: red;"></span>


                </form>
            </div>
        </div>
        <hr/>

        <div id="generatedSchedule" style="padding:10px">

            <h2 class="mainHeading"><spring:message code="GeneratedRepaySchedule"/></h1>
            <table id="myDataTable" border="1" class="table table-striped table-bordered">
                <thead class= "thead-dark">
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
                <tbody>

                </tbody>
            </table>
            </div>
    </div>

    <script src="https://cdn.datatables.net/2.0.5/js/dataTables.js"></script>

    <script>
        $(document).ready(function() {
            $("#myDataTable").DataTable({
                searching: false,
                paging: false,
                ordering: false,
                info: false,
            });
            $("#generatedSchedule").hide();


            $('#generateBtn').click(function(){
                var loanApplicationId=$('#loanApplicationId').val();

                if (loanApplicationId.trim() === '') {
                    $('#errorMsg').text('Please enter loanApplicationId');
                }
                else{
                    $('#errorMsg').text('');
                    $('#loadingSpinner').show();

                    $.ajax({
                        url: 'getRepay',
                        method: 'POST',
                        data: {loanApplicationId: loanApplicationId},
                        success: function(response) {
                            $('#myDataTable tbody').empty();

                            if (response && response.length > 0) {
                                $.each(response, function(index, repaySchedule) {

                                var dueDate = new Date(repaySchedule.dueDate);
                                dueDate.setDate(dueDate.getDate() + 1);
                                    $('#myDataTable tbody').append(
                                        '<tr>' +
                                        '<td>' + repaySchedule.installmentNumber + '</td>' +
                                        '<td style="text-align:left">' + dueDate.toISOString().split('T')[0] + '</td>' +
                                        '<td>' + parseFloat(repaySchedule.openingBalance).toFixed(2) + '</td>' +
                                        '<td>' + parseFloat(repaySchedule.installmentAmount).toFixed(2)+ '</td>' +
                                        '<td>' + parseFloat(repaySchedule.principalComponent).toFixed(2) + '</td>' +
                                        '<td>' + parseFloat(repaySchedule.interestComponent).toFixed(2) + '</td>' +
                                        '<td>' + parseFloat(repaySchedule.effectiveInterestRate).toFixed(2) + '</td>' +
                                        '<td>' + parseFloat(repaySchedule.outstandingBalancePrincipal).toFixed(2) + '</td>' +
                                        '</tr>'
                                    );
                                });
                                $('#generatedSchedule').show();
                            } else {
                                // Show message if response is null or empty
                                $('#generatedSchedule').html('<p>No data available.</p>').show();
                            }
                        },
                        error: function(xhr, status, error){
                            console.error(xhr.responseText);
                            $('#generatedSchedule').html('<p>Some error occurred.</p>').show();
                        },
                        complete: function(){
                            // Hide loading spinner
                            $('#loadingSpinner').hide();
                        }
                    });
                }
            });
        });
    </script>
</body>
</html>
