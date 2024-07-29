<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Allocation</title>

<style>

        /* General styles */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f0f0; /* Light grey background */
        }

                /* Right-align all cells in the table */
                #responseTable td {
                    text-align: right;
                }

        .container-allocation {
            min-width: 800px;
            max-width: 90vw;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff; /* White background for container */
            border-radius: 10px;
            //box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); /* Drop shadow */
        }

        h1 {
            text-align: center;
        }

        /* Button styles */
        #getRequestBtn {
            display: block;
            margin: 20px auto; /* Center the button */
            padding: 10px 20px;
            background-color: #007bff; /* Blue button color */
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        #getRequestBtn:hover {
            background-color: #0056b3; /* Darker blue on hover */
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

        .dark-table thead th {
                background-color: #343a40; /* Dark background color */
                color: #fff; /* Light text color */
            }

            .table-container {
                margin: 10px; /* Center the container horizontally */
                padding: 5px; /* Add some padding */
                border: 1px solid #ccc; /* Add a border */
                border-radius: 5px; /* Add border radius for rounded corners */
                box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Add a very light shadow */
            }
            .heading {
                font-size: 30px; /* Adjust font size as needed */
                margin-bottom: 20px; /* Add some space below the heading */
                color: #333; /* Heading text color */
            }

            .btn {
                padding: 10px 20px; /* Add padding to the button */
                font-size: 16px; /* Button font size */
                border: none; /* Remove default button border */
                background-color: #000; !/* Black background color */
                color: #fff; /* White text color */
                border-radius: 5px; /* Button border radius for rounded corners */
                cursor: pointer; /* Show pointer cursor on hover */
                transition: background-color 0.3s ease; /* Smooth transition on hover */
            }


            .btn:hover {
                background-color: #0056b3; /* Darker background color on hover */
            }

             .modal-container {
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

                /* Modal content */
                .modal-content {
                    background-color: #fefefe;
                    margin: 15% auto;
                    padding: 20px;
                    border: 1px solid #888;
                    width: 80%;
                    max-width: 400px;
                    border-radius: 5px;
                }

                /* Close button */
                .close {
                    color: #aaa;
                    float: right;
                    font-size: 28px;
                    font-weight: bold;
                }

                .close:hover,
                .close:focus {
                    color: black;
                    text-decoration: none;
                    cursor: pointer;
                }
                /* CSS for the penalty table */
                .penalty-table {
                    width: 100%;
                    border-collapse: collapse;
                    background-color: #f2f2f2; /* Greyish background */
                    border: 2px solid #000; /* Black border */
                }

                .penalty-table th, .penalty-table td {
                    border: 1px solid #000; /* Black border for header and data cells */
                    padding: 8px;
                }

                .penalty-table th {
                    background-color: #ccc; /* Light grey background for header cells */
                }

                /* Right-align penalty amount */
                .penalty-table td:last-child {
                    text-align: right;
                }
                #responseTable td:nth-child(1), /* Allocation ID column */
                #responseTable td:nth-child(2) { /* Deposit Date column */
                    text-align: left;
                }
    </style>



</head>
<body>
<%@ include file="navbar.jsp"%>
    <div class="container-allocation">
        <h1 class="heading"><spring:message code="label.allocationHeading"/></h1>
        <button id="getRequestBtn" class="btn"><spring:message code="label.allocation"/></button>
        <div id="loading">
            <div class="spinner"></div> <!-- Loading spinner -->
        </div>

        <table id="responseTable" class="display table-container" style="width:100%" ></table>

        <div id="myModal" class="modal-container">
            <div class="modal-content">
                <span class="close" onclick="closeModal()">&times;</span>
                <h2>Penalty Information</h2>
                <p><strong>Description:</strong> Late penalty charge</p>
                <p><strong>Amount:</strong> $1000</p>
            </div>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
      <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.13.5/css/jquery.dataTables.min.css"/>
    <script>
        $(document).ready(function(){
            var table;
            $('#getRequestBtn').click(function(){
                $('#loading').show(); // Show loading screen

                $.ajax({
                    type: 'GET',
                    url: '${pageContext.request.contextPath}/checker/allocation-details',
                    success: function(response){
                        console.log(response);
                        $('#loading').hide(); // Hide loading screen
                       response.forEach(function(item) {
                                   // Assuming 'depositDate' is the property containing the date
                                   var depositDate = new Date(item.depositDate);
                                   depositDate.setDate(depositDate.getDate() + 1);
                                   item.depositDate = depositDate.toISOString().split('T')[0]; // Format as 'YYYY-MM-DD'
                               });

                        response.forEach(function(item) {
                                                   item.interestComponentReceived = item.interestComponentReceived.toFixed(2);
                                                   item.principalComponentReceived = item.principalComponentReceived.toFixed(2);

                                                         console.log(item.penaltyDTOS);
                                                         var penalty = item.penaltyDTOS;
                                                         var functionCall = JSON.stringify(penalty);
                                                         item.penaltyButton = "<button class='view-penalty btn btn-primary' data-toggle='modal' data-target='#penaltyModal' onclick=\'viewPenalties("+functionCall+")\'>View</button>";

                                               });
                        // Initialize DataTable with response data
                        if (!table) {
                            table = $('#responseTable').DataTable({
                                data: response,
                                columns: [
                                    { title: 'Allocation ID', data: 'allocationId' },
                                    { title: 'Deposit Date', data: 'depositDate' },
                                    { title: 'Interest Received', data: 'interestComponentReceived' },
                                     { title: 'Principal Received', data: 'principalComponentReceived' },
                                    { title: 'Loan ID', data: 'loanAccount.loanAccountNumber' },
                                    { title: 'Penalty', data: 'penaltyButton', orderable: false, searchable: false }

                                ],
                                initComplete: function () {
                                $("#responseTable").addClass('dark-table');
                                 }
                            });
                        } else {
                            table.clear().rows.add(response).draw();
                        }
                    },
                    error: function(xhr, status, error){
                        $('#loading').hide(); // Hide loading screen

                    }
                });
            });



        });

function viewPenalties(penalties) {
    var modalContent = document.querySelector('.modal-content');
    modalContent.innerHTML = ''; // Clear previous content
var table = document.createElement('table');
    table.classList.add('penalty-table');


    var headerRow = table.insertRow();
    var descriptionHeader = headerRow.insertCell();
    descriptionHeader.textContent = 'Penalty Description';
    var amountHeader = headerRow.insertCell();
    amountHeader.textContent = 'Penalty Amount';

    // Populate table rows with penalty data
    penalties.forEach(function(penalty) {
        var row = table.insertRow();
        var descriptionCell = row.insertCell();
        descriptionCell.textContent = penalty.description;
        var amountCell = row.insertCell();
        amountCell.textContent = penalty.penaltyAmount;
    });


    modalContent.appendChild(table);
    openModal();
}


function openModal() {
    document.getElementById("myModal").style.display = "block";
}


function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

window.onclick = function(event) {
    var modal = document.getElementById("myModal");
    if (event.target == modal) {
        modal.style.display = "none";
    }
}
    </script>
</body>
</html>
