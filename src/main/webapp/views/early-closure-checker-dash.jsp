<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Loan Closure Details</title>
    <!-- Bootstrap CSS -->
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Include jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <!-- Include DataTables library -->
     <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.24/js/jquery.dataTables.js"></script>
<style>
        .table-container
        {
             max-width:
             100
             %; /* Adjust as needed */
             overflow-x: auto;
        }
        /* Styling for loading screen */
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

    <div class="container mt-4">
        <h3 class="mb-4">Early Closure Details</h3>
        <form id="loanForm" action="fetchLoanClosureData" method="post" >
            <div class="form-group col-md-6">
              <label for="loanAccountNumber">Enter Loan Account Number:</label>
                <div class="input-group">
                  <input type="text" id="loanAccountNumber" name="loanAccountNumber" class="form-control" placeholder="Enter Loan Account No." required="true"/>
                  <div class="input-group-append">
                    <button type="submit" class="btn btn-primary" name="action" value="search">Search</button>
                  </div>
                </div>
            </div>

            <%-- Display error message if present --%>
                <c:if test="${not empty errorMessage}">
                    <div class="row">
                        <div class="form-group col-md-6">
                            <p style="color: red;">${errorMessage}</p>
                        </div>
                    </div>
                 </c:if>
        </form>

        <c:if test="${empty errorMessage}">
        <div class="mt-4">
        <h5>Pending Request</h5>
        <div class="table-container" style="overflow-x: auto;">
            <!-- Table to display loan closure from temp table fetched from model attribute -->
            <table id="loanDataTable" class="table table-striped" style="width:100%; font-size: small;">
                <thead class="thead-dark">
                    <tr>
                        <th>Loan Account Number</th>
                        <th>Loan Closure ID</th>
                        <th>Loan Closure Date</th>
                        <th>Closure Status</th>
                        <th>Amount Paid By Customer</th>
                        <th>Final Sanctioned Amount</th>
                        <th>Due Date</th>
                        <th>Record Status</th>
                        <th>Creation Date</th>
                        <th>Created By</th>
                        <th>Modified Date</th>
                        <th>Modified By</th>
                        <th>Action</th> <!-- New column for buttons -->
                    </tr>
                </thead>
                <tbody>
                    <c:if test="${not empty checkerEarlyClosureDTO}">
                        <tr>
                            <td>${checkerEarlyClosureDTO.loanAccountNumber}</td>
                            <td>${checkerEarlyClosureDTO.loanClosureId}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${checkerEarlyClosureDTO.loanClosureDate}" /></td>
                            <td>${checkerEarlyClosureDTO.closureStatus}</td>
                            <td>${checkerEarlyClosureDTO.loanAmountPaidByCustomer}</td>
                            <td>${checkerEarlyClosureDTO.finalSanctionedAmount}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${checkerEarlyClosureDTO.dueDate}" /></td>
                            <td>${checkerEarlyClosureDTO.metaData.recordStatus}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${checkerEarlyClosureDTO.metaData.creationDate}" /></td>
                            <td>${checkerEarlyClosureDTO.metaData.createdBy}</td>
                            <td><fmt:formatDate pattern="dd/MM/yyyy" value="${checkerEarlyClosureDTO.metaData.modifiedDate}" /></td>
                            <td>${checkerEarlyClosureDTO.metaData.modifiedBy}</td>
                            <td class="text-center">
                                <div class="btn-group">
                                    <button class="btn btn-success acceptBtn" id="acceptBtn">Accept</button>
                                    <button class="btn btn-danger rejectBtn" id="rejectBtn" >Reject</button>
                                </div>
                            </td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
           </div>
        </div>

    </c:if>
    </div>

   <script>
       $(document).ready(function()
       {
            $('#rejectBtn').click(function()
            {
                $('#loading').show();
                $.ajax(
                {
                    url: 'delete-request',
                    method: 'POST',
                    data: {loanClosureId: ${checkerEarlyClosureDTO.loanClosureId}},
                    success: function(response)
                    {
                      alert("Loan closure rejected successfully.");
                      $('#loanDataTable tbody').empty();
                      $('#loanDataTable').hide();
                    },
                    error: function(xhr, status, error)
                    {
                        console.error(xhr.responseText);
                        $('#generatedSchedule').html('<p>Some error occurred.</p>').show();
                    },
                    complete: function()
                    {
                        // Hide loading spinner
                        $('#loading').hide();
                    },
                });
            });

            $('#acceptBtn').click(function()
             {
             var amountPaid = parseFloat('${checkerEarlyClosureDTO.loanAmountPaidByCustomer}');
                                  var finalAmount = parseFloat('${checkerEarlyClosureDTO.finalSanctionedAmount}');

                                  // Check if the amounts are not equal
                                  if (amountPaid !== finalAmount)
                                  {
                                      alert("Amount Paid By Customer and Final Sanctioned Amount in not equal! Loan Amount is not recovered till date");
                                      return;
                                  }
                $('#loading').show();
                $.ajax({
                    url: 'approve-request',
                    method: 'POST',
                    data: {loanClosureId: ${checkerEarlyClosureDTO.loanClosureId}},
                    success: function(response)
                    {
                      alert("Loan closure approved successfully.");
                      $('#loanDataTable tbody').empty();
                      $('#loanDataTable').hide();
                    },
                    error: function(xhr, status, error)
                    {
                        console.error(xhr.responseText);
                        $('#generatedSchedule').html('<p>Some error occurred.</p>').show();
                    },
                    complete: function()
                    {
                        // Hide loading spinner
                        $('#loading').hide();
                    },
                });
             });
       });
   </script>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


<div id="loading">
    <div class="spinner"></div> <!-- Loading spinner -->
</div>
</body>
</html>


