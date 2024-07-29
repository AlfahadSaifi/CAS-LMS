<%@ page import="java.util.*, org.nucleus.dto.ReceivablePayableDTO, org.nucleus.dto.LoanAccountDTO" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Receivable/Payable Author</title>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

        <style>


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
    </head>
    <body>
        <%@ include file="navbar.jsp"%>
        <div class="container my-4 card shadow">
             <div id="loading">
                        <div class="spinner"></div> <!-- Loading spinner -->
             </div>
             <div class = "row justify-content-between align-items-center mb-3 my-4">
                <div class = "col-auto">
                    <h2>Receivable Payable Checker</h2>
                </div>
                <div class="col-auto">
                    <button type="submit" onclick="fetchData()" class="btn btn-primary">Search</button>
                    <button type="button" class="btn btn-secondary" onclick="clearForm()">Cancel</button>
                </div>
             </div>

            <div class="form-row">
                <div class="col-md-6 mb-3">
                    <label for="loanAccountNumber">Loan Account</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="loanAccountNumber" name="loanAccountNumber" placeholder="Loan Account" required>
                        <div class="input-group-append">
                            <button type="button" class="btn btn-primary" id="searchButton" onclick="searchLoan()" >Search</button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-6 mb-3">
                    <label for="branch">Branch<span style="color: red;">*</span></label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="branch" name="branch" required disabled="true">
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md-6 mb-3">
                    <label for="fromDate">Transaction From Date</label>
                    <input type="date" class="form-control" id="fromDate" name="fromDate" step="0.01" value="0.00" required>
                </div>
                <div class="col-md-6 mb-3">
                    <label for="toDate">Transaction To Date</label>
                    <input type="date" class="form-control" id="toDate" name="toDate" required>
                </div>
            </div>

            <div class="container">
                <div class="table-container">

                    <table id="dataTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
                        <thead class="thead-dark">
                            <tr>
                                <th>No.</th>
                                <th>Receivable Payable Id</th>
                                <th>Loan Id</th>
                                <th>Principal Component</th>
                                <th>Interest Component</th>
                                <th>Receivable Payable Date</th>
                                <th>Amount</th>
                                <th>Due Date</th>
                                <th>Receivable Payable Status</th>
                                <th>Record Status</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </body>
    <script>
        $(document).ready(function() {
            $('#receivablePayablesTable').dataTable();
            $('#dataTable').hide();
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
                $('#receivableForm').submit();
            });
            $('#clearButton').click(function() {
                clearForm();
            });
        });
        function searchLoan() {
            var loanAccountNumber = document.getElementById("loanAccountNumber").value;
            // AJAX request
            $.ajax({
                url: "../searchDetails?loanAccountNumber="+loanAccountNumber,
                type: "GET",
                dataType: "json",
                success: function(data) {
                    // Populate form fields
                    document.getElementById("branch").value = data.branch;
                    // Populate other fields as needed
                    },
                error: function(_, status, error) {
                    console.error("Error occurred while fetching data: " + error);
                    alert("Loan account Number doesn't exist");
                }
            });
        }
        $(document).on('click', '#dataTable button.approve', function(event) {
            event.preventDefault(); // Prevent default link behavior
            var approveUrl = $(this).attr('data-approve-url'); // Get the approval URL
            $('#loading').show();
            // Send AJAX request to approve the entry
            $.ajax({
                url: approveUrl,
                type: 'GET',
                success: function() {
                    $('#loading').hide();
                    // Wait for 2 seconds and then refresh the table
                    fetchData();
                },
                error: function(xhr, status, error) {
                    console.error('Error approving:', error);
                }
            });
        });

        $(document).on('click', '#dataTable button.reject', function(event) {
            event.preventDefault(); // Prevent default link behavior
            var rejectUrl = $(this).attr('data-reject-url'); // Get the approval URL
             $('#loading').show();

            $.ajax({
                url: rejectUrl,
                type: 'GET',
                success: function() {
                     $('#loading').hide();
                    fetchData();
                },
                error: function(xhr, status, error) {
                    console.error('Error rejecting:', error);
                }
            });
        });
        function fetchData() {

            $.ajax({
                url: '${pageContext.request.contextPath}/receivable-generate-table?loanAccountNumber='+ document.getElementById("loanAccountNumber").value +'&fromDate='+document.getElementById("fromDate").value+'&toDate='+document.getElementById("toDate").value,
                type: 'GET',
                dataType: 'json',
                success: function(data) {
                    console.log(data);
                    data.forEach(function(item) {
                        var date1 = new Date(item.receivablePayableDueDate);
                        var date2 = new Date(item.receivablePayableDate);

                        date1.setDate(date1.getDate() + 1);
                        item.receivablePayableDate = date1.toISOString().split('T')[0];

                        date2.setDate(date1.getDate() + 1);
                        item.receivablePayableDueDate = date2.toISOString().split('T')[0];
                    });
                    populateDataTable(data);
                },
                error: function(xhr, status, error) {
                    console.error('Error fetching data:', error);
                }
            });
        }
        function populateDataTable(data) {  console.log(data);

            $('#dataTable').show();
            $('#dataTable').DataTable({
                data: data,

                columns: [
                    { data: null, render: function (data, type, row, meta) { return meta.row + 1; } }, // index column
                    { data: 'receivablePayableId' },
                    { data: 'loanAccount.loanAccountId' },
                    { data: 'principalComponent' },
                    { data: 'interestComponent' },
                    { data: 'receivablePayableDueDate' },
                    { data: 'receivablePayableAmount' },
                    { data: 'receivablePayableDate' },
                    { data: 'receivablePayableStatus' },
                    { data: 'tempMetaData.recordStatus' },
                    { data: null, render: function (data, type, row) {
                            return '<td>' +
                            '<div class="btn-group" role="group">' +
                            '<button class="btn btn-primary approve" data-approve-url="../approve-receivable-payable/id/'+ row.receivablePayableId + '">Approve</button> | ' +
                            '</button>'+
                            '<button class="btn btn-danger reject" data-reject-url="../reject-receivable-payable/id/' + row.receivablePayableId + '">Reject</button>' +
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
</html>