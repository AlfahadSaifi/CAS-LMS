<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Receipt Form</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        /* Custom styles */
        #loading {
            display: none;
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent black background */
            z-index: 9999;
        }

        .spinner {
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

        .dark-table thead th {
            background-color: #343a40; /* Dark background color */
            color: #fff; /* Light text color */
        }

        /* Hidden by default */
        .conditional-field {
            display: none;
        }
    </style>

    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="org.nucleus.dto.ReceiptDTO, java.util.*" %>
    <%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container my-4 card shadow">
        <form:form id="receiptForm" action="receipt-Submit" method="post" modelAttribute="receiptMaker">
            <div class="container-receipt">
                <div class="row justify-content-between align-items-center mb-3 my-4">
                    <div class="col-auto">
                        <h2><spring:message code="receivableReceiptForm"/></h2>
                    </div>
                    <div class="col-auto">
                        <button type="submit" class="btn btn-primary" name="action" value="submit"><spring:message code="submit"/></button>
                        <button type="button" class="btn btn-secondary" onclick="clearForm()"><spring:message code="clear"/></button>
                        <button type="button" class="btn btn-secondary" onclick="goToMainPage()"><spring:message code="cancel"/></button>
                    </div>
                </div>
                <c:if test="${not empty errorMessage}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMessage}
                    </div>
                </c:if>

                <c:if test="${not empty successMessage}">
                    <div class="alert alert-success" role="alert">
                        ${successMessage}
                    </div>
                </c:if>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="receivablePayableId"><spring:message code="receivablePayableId"/></label>
                        <span style="color: red;">*</span>
                        <div class="input-group">
                            <form:input type="number" class="form-control" id="receivablePayableId" path="receivablePayable.receivablePayableId" placeholder="Enter Receivable ID number" required="true" min="0"/>
                            <div class="input-group-append">
                                <button type="button" class="btn btn-primary" id="searchButton"><spring:message code="search"/></button>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="loanAccountNumber"><spring:message code="loanAccountNumber"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="text" class="form-control" id="loanAccountNumber" path="loanAccountNumber" placeholder="Loan account number" required="true" readonly="true"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                         <label for="currency"><spring:message code="currency"/></label>
                            <form:select class="form-control" id="currency" path="currency">
                                <form:option value="INR">INR</form:option>
                            </form:select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="transactionDate"><spring:message code="transactionDate"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="date" class="form-control" id="transactionDate" path="transactionDate" required="true" readonly="true"/>
                        <form:errors path="transactionDate" style="color: red;"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="paymentMode"><spring:message code="paymentMode"/></label>
                        <form:select class="form-control" id="paymentMode" path="paymentMode" onchange="toggleConditionalFields()">
                            <form:option value="CASH">Cash</form:option>
                            <form:option value="DRAFT">Draft</form:option>
                        </form:select>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="dateOfReceipt"><spring:message code="dateOfReceipt"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="date" class="form-control" id="dateOfReceipt" path="dateOfReceipt" required="true" readonly="true"/>
                        <form:errors path="dateOfReceipt" style="color: red;"/>
                    </div>
                </div>
                <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="requiredAmount"><spring:message code="requiredAmount"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="number" class="form-control" id="requiredAmount" path="requiredAmount" step="0.01" required="true" readonly="true" min="0"/>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="transactionAmount"><spring:message code="transactionAmount"/></label>
                        <span style="color: red;">*</span>
                        <form:input type="number" step="0.01" class="form-control" id="transactionAmount" path="transactionAmount" min="0" required="true"/>
                        <form:errors path="transactionAmount" style="color: red;"/>
                    </div>
                </div>
                <div class="form-row">
                <div class="col-md-6 mb-3">
                    <label for="bankName"><spring:message code="bankName"/></label>
                    <form:select class="form-control" id="bankName" path="bankName">
                        <form:option value="SBI">SBI</form:option>
                        <form:option value="HDFC">HDFC</form:option>
                        <form:option value="ICICI">ICICI</form:option>
                    </form:select>
                </div>
                <div class="col-md-6 mb-3">
                  <label for="receiptbasis"><spring:message code="receiptBasis"/></label>
                    <form:select class="form-control" id="receiptbasis" path="receiptbasis">
                        <form:option value="SINGLE_ACCOUNT">Single Account</form:option>
                    </form:select>
               </div>
              </div>
                <div class="conditional-field" id="conditionalFields">
                    <div class="form-row">
                    <div class="col-md-6 mb-3">
                        <label for="instrumentNumber"><spring:message code="instrumentNumber"/></label>
                        <form:input type="number" class="form-control" id="instrumentNumber" path="instrumentNumber" placeholder="Instrument number" min="0"/>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="instrumentDate"><spring:message code="instrumentDate"/></label>
                        <form:input type="date" class="form-control" id="instrumentDate" path="instrumentDate" readonly="true" min="0"/>
                        <form:errors path="instrumentDate" style="color: red;"/>
                      </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/js/all.min.js"></script>
    <script>
        function clearForm() {
            document.getElementById("receiptForm").reset();
        }

        function goToMainPage() {
            window.location.href = "../"; // Change "mainpage.html" to your actual main page URL
        }

        document.addEventListener("DOMContentLoaded", function() {
            var currentDate = new Date().toISOString().split('T')[0];
            document.getElementById('transactionDate').value = currentDate;
            document.getElementById('dateOfReceipt').value = currentDate;
            document.getElementById('instrumentDate').value = currentDate;
        });

        $(document).ready(function() {
            $('#searchButton').click(function() {
                var receivablePayableId = $('#receivablePayableId').val();
                if (receivablePayableId > 0) {
                    $('#loading').show();
                    $.ajax({
                        url: '../maker/receipt-Search/' + receivablePayableId,
                        method: 'GET',
                        success: function(data) {
                            if (data) {
                                console.log(data);
                                document.getElementById("loanAccountNumber").value = data.loanAccountNumber;
                                document.getElementById("requiredAmount").value = data.requiredAmount;
                            }
                        },
                        error: function() {
                            alert('Failed to fetch data for the provided Receivable Payable ID. Please try again');
                        },
                        complete: function() {
                            // Hide loading spinner
                            $('#loading').hide();
                        }
                    });
                } else {
                    alert('Please enter a valid Receivable Payable ID.');
                }
            });

            toggleConditionalFields(); // Call it on page load to set the initial state
        });

        function toggleConditionalFields() {
            var paymentMode = document.getElementById('paymentMode').value;
            var conditionalFields = document.getElementById('conditionalFields');
            if (paymentMode === 'DRAFT') {
                conditionalFields.style.display = 'block';
            } else {
                conditionalFields.style.display = 'none';
            }
        }
    </script>
    <div id="loading">
        <div class="spinner"></div> <!-- Loading spinner -->
    </div>
</body>
</html>
