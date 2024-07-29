<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Font Awesome CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
    <!-- DataTables CSS -->
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.6/css/dataTables.bootstrap4.min.css">
    <!-- jQuery -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <!-- DataTables JS -->
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <!-- Bootstrap JS -->
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <!-- Custom CSS -->
    <style>

        #paymentTable td {
            white-space: nowrap;
        }

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
    </style>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
    <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    <%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="org.nucleus.dto.ReceiptDTO, java.util.*" %>
    <%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow">
<div class="container-receipt">
    <div class = "row justify-content-between align-items-center mb-3 my-4">
        <div class="col-auto">
            <h2><spring:message code="receivableReceiptApprovalScreen"/></h2>
        </div>
        <div class="col-auto">
            <div class="col-md-12">
               <button type="submit" class="btn btn-primary" name="action" id="searchButton" value="search"><spring:message code="search"/></button>
               <button type="button" class="btn btn-secondary" onclick="goToMainPage()"><spring:message code="cancel"/></button>
             </div>
        </div>
    </div>
       <div class="form-row">
                   <div class="col-md-6 mb-3">
                   <label for="receivablePayableId"><spring:message code="receivablePayableId"/></label>
                   <span style="color: red;">*</span>
                       <div class="input-group">
                           <input type="number" class="form-control" id="receivablePayableId" name="receivablePayableId" placeholder="Enter payable id number" required="true" min="0"/>
                       </div>
                   </div>
                   <div class="col-md-6 mb-3">
                       <!-- Empty column -->
                   </div>
               </div>
                <div class="form-row">
                   <div class="col-md-6 mb-3">
                       <label for="fromDate"><spring:message code="fromDate"/></label>
                       <span style="color: red;">*</span>
                       <input type="date" class="form-control" id="fromDate" name="fromDate" required ="true"/>
                   </div>
                   <div class="col-md-6 mb-3">
                       <label for="toDate"><spring:message code="toDate"/></label>
                       <span style="color: red;">*</span>
                       <input type="date" class="form-control" id="toDate" name="toDate" required="true"/>
                   </div>
               </div>
               <div class="form-row">
                   <div class="col-md-6 mb-3">
                       <label for="loanAccountNumber"><spring:message code="loanAccountNumber"/></label>
                       <input type="text" class="form-control" id="loanAccountNumber" name="loanAccountNumber" placeholder="Loan Account Number" readonly="true"/>
                   </div>
                   <div class="col-md-6 mb-3">
                       <label for="createdBy"><spring:message code="createdBy"/></label>
                       <input type="text" class="form-control" id="createdBy" name="createdBy" placeholder="Maker name" required="true" readonly="true"/>
                   </div>
               </div>

    <!-- DataTables for Payment List -->
            <div class="container">
               <div class="table-container">
                   <table id="paymentTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
                       <thead class="thead-dark">
                <tr>
                    <th><spring:message code="number"/></th>
                    <th><spring:message code="receiptId"/></th>
                    <th><spring:message code="bankName"/></th>
                    <th><spring:message code="dateOfReceipt"/></th>
                    <th><spring:message code="instrumentNumber"/></th>
                    <th><spring:message code="paymentMode"/></th>
                    <th><spring:message code="requiredAmount"/></th>
                    <th><spring:message code="status"/></th>
                    <th><spring:message code="transactionAmount"/></th>
                    <th><spring:message code="recordStatus"/></th>
                    <th><spring:message code="actions"/></th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
   </div>
   </div>
</div>
<script>
function goToMainPage() {
        window.location.href = "../"; // Change "mainpage.html" to your actual main page URL
    }
    $(document).ready(function() {

                $('#paymentTable').hide();
                $('#searchButton').click(function() {
                    fetchData();
                });
                $('#searchButton').click(function() {
                    var fromDate = $('#fromDate').val();
                    var toDate = $('#toDate').val();
                    if (new Date(fromDate) >= new Date(toDate)) {
                        alert("Start date must be less than end date");
                        return;
                    }
                     var receivablePayableId = $('#receivablePayableId').val();
                            if(receivablePayableId<0){
                                alert('Please enter a valid Receivable Payable ID.');
                                return;
                            }
                });
                });
             $(document).on('click', '#paymentTable button.approve', function(event) {
                        event.preventDefault(); // Prevent default link behavior
                        var approveUrl = $(this).attr('data-approve-url'); // Get the approval URL
                        $('#loading').show();
                        // Send AJAX request to approve the entry
                        $.ajax({
                            url: approveUrl,
                            type: 'GET',
                            success: function() {
                                // Wait for 2 seconds and then refresh the table
                                fetchData();
                            },
                            error: function() {
                                  alert('Failed to fetch data for the provided Receivable Payable ID.Please try again');
                            },
                            complete: function()
                                 {
                                    // Hide loading spinner
                                     $('#loading').hide();
                                 },
                        });
                    });

                    $(document).on('click', '#paymentTable button.reject', function(event) {
                        event.preventDefault(); // Prevent default link behavior
                        var rejectUrl = $(this).attr('data-reject-url'); // Get the approval URL
                         $('#loading').show();
                        $.ajax({
                            url: rejectUrl,
                            type: 'GET',
                            success: function() {
                                fetchData();
                            },
                            error: function() {
                                alert('No more receipt found against provided Receivable Payable ID');
                            },
                            complete: function()
                                 {
                                    // Hide loading spinner
                                     $('#loading').hide();
                                 },
                        });
                    });
           function fetchData() {
                $('#loading').show();
                 $.ajax({
                     url: '${pageContext.request.contextPath}/checker/receipt-Generate-table?receivablePayableId='+ document.getElementById("receivablePayableId").value +'&fromDate='+document.getElementById("fromDate").value+'&toDate='+document.getElementById("toDate").value,
                     type: 'GET',
                     dataType: 'json',
                     success: function(data) {
                         console.log(data);
                         data.forEach(function(item) {
                           document.getElementById("loanAccountNumber").value = item.loanAccountNumber;
                           document.getElementById("createdBy").value = item.tempMetaData.createdBy;
                             item.dateOfReceipt = formatDate(new Date(item.dateOfReceipt));
                         });
                         populateDataTable(data);
                     },
                    error: function() {
                           alert('Failed to fetch data for the provided Receivable Payable ID.Please try again');
                     },
                     complete: function(){
                             // Hide loading spinner
                              $('#loading').hide();
                          },
                 });
             }
              function formatDate(date) {
                     var day = ("0" + date.getDate()).slice(-2);
                     var month = ("0" + (date.getMonth() + 1)).slice(-2);
                     var year = date.getFullYear();
                     return day + '-' + month + '-' + year;
                 }
              function populateDataTable(data) {
                         $('#paymentTable').show();
                         $('#paymentTable').DataTable({
                             data: data,
                             columns: [
                                  { data: null, render: function (data, type, row, meta) { return meta.row + 1; } },
                                  { data: 'receiptId' },
                                  { data: 'bankName' },
                                  { data: 'dateOfReceipt' },
                                  { data: 'instrumentNumber' },
                                  { data: 'paymentMode' },
                                  { data: 'requiredAmount' },
                                  { data: 'status' },
                                  { data: 'transactionAmount' },
                                  { data: 'tempMetaData.recordStatus' },
                                  { data: null, render: function (data, type, row) {
                                         return '<td>' +
                                         '<div class="btn-group" role="group">' +
                                         '<button class="approve btn btn-primary" data-approve-url="../checker/approve-Receipt/' + row.receiptId + '">Approve</button> ' +
                                         '</button>'+
                                         '<button class="reject btn btn-danger" data-reject-url="../checker/reject-Receipt/' + row.receiptId + '">Reject</button>' +
                                         '</button>'+
                                         '</div>'+
                                         '</td>';
                                     }
                                 }
                             ],
                             stateSave: true,
                             "bDestroy": true
                         });
                     }
                 </script>
 <div id="loading">
  <div class="spinner"></div> <!-- Loading spinner -->
</div>
</body>
</html>



