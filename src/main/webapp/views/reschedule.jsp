<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Loan Form</title>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.5/css/dataTables.dataTables.css">  <style>
  .body{margin:0; padding:0;}
  .form-group {
      margin-bottom: 20px;
      margin-left:20px;
  }
  .form-group label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
  }
  /* Styling for loading screen */
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
    <!-- Other contents -->
    <!-- Add a loading spinner element -->
    <!-- Search Modal -->

    <!-- Search Modal -->
    <div class="spinner">
        <div id="loading"></div> <!-- Loading spinner -->
    </div>


  <div>
    <br>
    <form id="searchForm">
      <div class="row">
      <div class="col-md-6">
        <h2><spring:message code="label.rescheduleLoan"/></h2>
        <br>
          <div class="form-group">
            <label for="loanAccountNo"><h5><spring:message code="label.loanAccountNo"/></h5></label>
            <div class="input-group">
              <input type="text" class="form-control" id="loanAccountNo" placeholder="Enter Loan Account No." required>
              <div class="input-group-append">
                <button class="btn btn-outline-secondary" type="button" id="searchBtn"><spring:message code="button.search"/></button>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label for="date"><h5><spring:message code="label.date"/></h5></label>
            <input type="date" class="form-control" id="date" disabled>
          </div>
        </div>
        <div class="col-md-6">
        <br><br><br>
          <div class="form-group">
            <label for="customerName"><h5><spring:message code="label.customerName"/></h5></label>
            <input type="text" class="form-control" id="customerName" placeholder="" disabled required>
          </div>
          <div class="form-group">
            <label><h5><spring:message code="label.changeType"/></h5></label><br>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="changeType" id="dueDateChange" value="dueDateChange" required>
              <label class="form-check-label" for="dueDateChange"><spring:message code="label.dueDateChange"/></label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="changeType" id="installmentChange" value="installmentChange">
              <label class="form-check-label" for="installmentChange"><spring:message code="label.installmentChange"/></label>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="radio" name="changeType" id="tenureChange" value="tenureChange">
              <label class="form-check-label" for="tenureChange"><spring:message code="label.tenureChange"/></label>
            </div>
          </div>
          <button type="submit" class="btn btn-primary" id="submitButton" disabled><spring:message code="button.search"/></button>
          <button type="button" class="btn btn-secondary" id="clearBtn"><spring:message code="button.clear"/></button>
        </div>
      </div>
    </form>


    <form id="searchResultForm" style="display: none;">
      <div class="row">
      <div class="col-md-6">
        <h4>Search Result</h4>
        <br>
          <div class="form-group">
            <label for="productCode"><h5><spring:message code="label.productCode"/></h5></label>
            <div class="input-group">
              <input type="text" class="form-control" id="productCode" disabled required>
            </div>
          </div>
          <div class="form-group">
            <label for="effectiveDate"><h5><spring:message code="label.reschedulingEffectiveDate"/></h5></label>
            <input type="date" class="form-control" id="effectiveDate" disabled>
          </div>
          <div class="form-group">
            <label for="currentDueDate"><h5><spring:message code="label.currentDueDate"/></h5></label>
            <input type="text" class="form-control" id="currentDueDate" disabled>
          </div>
          <div class="form-group">
            <label for="currentInstallment"><h5><spring:message code="label.currentInstallmentAmount"/></h5></label>
            <input type="text" class="form-control" id="currentInstallment" disabled>
          </div>
          <div class="form-group">
              <label for="currentTenure"><h5><spring:message code="label.currentTenure"/></h5></label>
              <div class="input-group">
                      <input type="text" class="form-control" id="currentTenure" disabled>
                      <input type="text" class="form-control" id="currentTenureIn" disabled>
              </div>
          </div>

          <button type="button" class="btn btn-secondary" id="check"><spring:message code="button.check"/></button>
        </div>
        <div class="col-md-6">
        <br><br><br>
          <div class="form-group">
            <label for="frequency"><h5><spring:message code="label.frequency"/></h5></label>
            <input type="text" class="form-control" id="frequency" disabled required>
          </div>
          <div class="form-group">
            <label for="rate"><h5><spring:message code="label.rate"/></h5></label>
            <input type="text" class="form-control" id="rate" disabled required>
          </div>
          <div class="form-group">
            <label for="enteredDueDate" style="display: none;"><h5><spring:message code="label.enterDueDate"/></h5></label>
            <input type="number" class="form-control" id="enteredDueDate" min="1" max="28" style="display: none;">
            <span id="errorMessage" style="color: red; display: none;">Please enter a value between 1 and 28.</span>
          </div>
          <div class="form-group" >
            <label for="enteredInstallment" style="display: none;"><h5><spring:message code="label.enterInstallmentAmount"/></h5></label>
            <input type="number" class="form-control" id="enteredInstallment" style="display: none;">
          </div>
          <div class="form-group">
            <label for="enteredTenure" style="display: none;"><h5><spring:message code="label.enterTenure"/></h5></label>
              <div class="input-group">
                  <input type="number" class="form-control" id="enteredTenure" style="display: none;">
                  <select class="form-control" id="enteredTenureType">
                      <option value="Months">Months</option>
                      <option value="Weeks">Weeks</option>
                      <option value="Years">Years</option>
                  </select>
              </div>
          </div>
        </div>
      </div>
    </form>

    <div id="repayScheduleDiv" style="margin:20px; display: none;">
    <table id="repayScheduleTable" border="1" style="width:100%; ">
      <caption>This Will Be New Repay Schedule --</caption>
    <thead>
        <tr>
            <th><spring:message code="th.installmentNumber"/></th>
            <th><spring:message code="th.dueDate"/></th>
            <th><spring:message code="th.openingBalance"/></th>
            <th><spring:message code="th.installment"/></th>
            <th><spring:message code="th.principal"/></th>
            <th><spring:message code="th.interest"/></th>
            <th><spring:message code="th.effectiveRate"/></th>
            <th><spring:message code="th.outstandingBalance"/></th>
        </tr>
    </thead>
    <tbody>
    </tbody>
    </table>
          <button type="button" class="btn btn-primary" id="save"><spring:message code="button.update"/></button>
    </div>




  </div>
  <%@ include file="footer.jsp"%>

  <!-- Bootstrap JS and jQuery -->
    <script src="https://cdn.datatables.net/2.0.5/js/dataTables.js"></script>

<script>
  $(document).ready(function() {
              $('.spinner').hide();
  var x = '';
  var y = '';


  $('#check').click(function() {
              $('.spinner').show();
    if (x === 'dueDate') {
        y = $('#enteredDueDate').val();
    } else if (x === 'tenure') {
        y = $('#enteredTenure').val() + ',' + $('#enteredTenureType').val();
    } else if (x === 'installment') {
        y = $('#enteredInstallment').val();
    }
      $.ajax({
          type: 'GET',
          url: '../reschedule/checkRepaySchedule',
          data: {
              key: x,
              value: y
          },
          dataType: 'json',
          success: function(response) {
              $('.spinner').hide();
              if (response && Array.isArray(response) && response.length > 0) {
                  $('#repayScheduleTable tbody').empty();
                  $.each(response, function(index, repaySchedule) {
                      $('#repayScheduleTable tbody').append(
                          '<tr>' +
                          '<td>' + repaySchedule.installmentNumber + '</td>' +
                          '<td>' + repaySchedule.dueDate + '</td>' +
                          '<td>' + repaySchedule.openingBalance + '</td>' +
                          '<td>' + repaySchedule.installmentAmount + '</td>' +
                          '<td>' + repaySchedule.principalComponent + '</td>' +
                          '<td>' + repaySchedule.interestComponent + '</td>' +
                          '<td>' + repaySchedule.effectiveInterestRate + '</td>' +
                          '<td>' + repaySchedule.outstandingBalancePrincipal + '</td>' +
                          '</tr>'
                      );
                  });

                  // Show the table
                  $('#repayScheduleDiv').show();

                  if ($.fn.DataTable.isDataTable('#repayScheduleTable')) {
                      $('#repayScheduleTable').DataTable().destroy();
                  }

                  // Initialize DataTable
                  $("#repayScheduleTable").DataTable({
                      searching: true,
                      paging: true,
                      ordering: true
                  });

              } else {
                  $('.spinner').hide();
                  console.error("Response data is empty or not in the expected format.");
              }
          },
          error: function (xhr, textStatus, errorThrown) {
                if (xhr.status === 404) {
              $('.spinner').hide();
                    var responseText = xhr.responseText; // Get the response text
                    alert(responseText);
                } else {
              $('.spinner').hide();
                  console.error("AJAX error");
                }
            }
      });
  });


    // Function to check if all required fields are filled
    function checkFields() {
      var isValid = true;
      $('#searchForm input[required]').each(function() {
        if ($(this).val() === '') {
          isValid = false;
          return false;
        }
      });
      return isValid;
    }

     // Get the radio buttons
    var dueDateChangeRadio = document.getElementById("dueDateChange");
    var tenureChangeRadio = document.getElementById("tenureChange");
    var installmentChangeRadio = document.getElementById("installmentChange");

    // Get the input fields and their corresponding labels in the second form
    var enteredDueDateInput = document.getElementById("enteredDueDate");
    var enteredDueDateLabel = document.querySelector("label[for='enteredDueDate']");

    var enteredTenureInput = document.getElementById("enteredTenure");
    var enteredTenureTypeInput = document.getElementById("enteredTenureType");
    var enteredTenureLabel = document.querySelector("label[for='enteredTenure']");

    var enteredInstallmentInput = document.getElementById("enteredInstallment");
    var enteredInstallmentLabel = document.querySelector("label[for='enteredInstallment']");

    // Function to show input field and its corresponding label
    function showInputField(input, label) {
        input.style.display = "block";
        label.style.display = "block";
    }

    // Function to hide input field and its corresponding label
    function hideInputField(input, label) {
        input.style.display = "none";
        label.style.display = "none";
    }

    // Add event listeners to the radio buttons
    dueDateChangeRadio.addEventListener("change", function() {
        if (dueDateChangeRadio.checked) {
            x = 'dueDate';
            hideInputField(enteredTenureInput, enteredTenureLabel);
            hideInputField(enteredTenureTypeInput, enteredTenureLabel);
            hideInputField(enteredInstallmentInput, enteredInstallmentLabel);
            showInputField(enteredDueDateInput, enteredDueDateLabel);
        }
    });

    tenureChangeRadio.addEventListener("change", function() {
        if (tenureChangeRadio.checked) {
            x = 'tenure';
            hideInputField(enteredDueDateInput, enteredDueDateLabel);
            hideInputField(enteredInstallmentInput, enteredInstallmentLabel);
            showInputField(enteredTenureInput, enteredTenureLabel);
            showInputField(enteredTenureTypeInput, enteredTenureLabel);
        }
    });

    installmentChangeRadio.addEventListener("change", function() {
        if (installmentChangeRadio.checked) {
            x = 'installment';
            hideInputField(enteredDueDateInput, enteredDueDateLabel);
            hideInputField(enteredTenureInput, enteredTenureLabel);
            hideInputField(enteredTenureTypeInput, enteredTenureLabel);
            showInputField(enteredInstallmentInput, enteredInstallmentLabel);
        }
    });

    // Function to check if at least one radio button is checked
    function checkRadio() {
      return $('input[name="changeType"]:checked').length > 0;
    }

    // Populate current date
    var currentDate = new Date().toISOString().slice(0, 10);
    $('#date').val(currentDate);

    // Enable submit button when all required fields are filled
    $('input').on('keyup change', function() {
      $('#submitButton').prop('disabled', !(checkFields() && checkRadio()));
    });

    // Search button click event
    $('#searchBtn').click(function() {
    $('.spinner').show();
    var loanAccountNumber = $('#loanAccountNo').val();

    // Make AJAX call to fetch customer name
    $.ajax({
        url: '../reschedule/getCustomerNameByLoanAccountNumber',
        method: 'GET',
        data: { loanAccountNumber: loanAccountNumber }, // Pass loan account number as parameter
        success: function(response) {
              $('.spinner').hide();
            response = response.replace(/"/g, ''); // This will remove all quotes from the string
            $('#customerName').val(response);
            $('#submitButton').prop('disabled', !(checkFields() && checkRadio()));
        },
        error: function (xhr, textStatus, errorThrown) {
              $('.spinner').hide();
              if (xhr.status === 404) {
                  var responseText = xhr.responseText; // Get the response text
                  alert(responseText);
              } else {
                  alert('Error fetching search results');
                  // You can handle other error cases here
              }
          }
    });
    });

    // Submit button click event
    $('#searchForm').submit(function(event) {
              $('.spinner').show();

          var loanAccountNumber = $('#loanAccountNo').val();
          $.ajax({
              url: '../reschedule/getRescheduleResponse',
              method: 'GET',
              data: { loanAccountNumber: loanAccountNumber },
              success: function (response) {
              $('.spinner').hide();
                  // Autofill form fields with response data
                  $('#productCode').val(response.productCode);
                  $('#effectiveDate').val(response.effectiveDate);
                  $('#currentDueDate').val(response.currentDueDate);
                  $('#currentInstallment').val(response.currentInstallment);
                  $('#currentTenure').val(response.currentTenure);
                  $('#currentTenureIn').val(response.tenureIn);
                  $('#frequency').val(response.frequency);
                  $('#rate').val(response.rate);
              },
              error: function (xhr, textStatus, errorThrown) {
              $('.spinner').hide();
                  if (xhr.status === 404) {
                      var responseText = xhr.responseText; // Get the response text
                      alert(responseText);
                  } else {
                      alert('Error fetching search results');
                      // You can handle other error cases here
                  }
              }
          });
      event.preventDefault(); // Prevent form submission
      // Disable loanAccountNo input
      $('#loanAccountNo').prop('disabled', true);
      // Remove unselected change type options
      $('input[name="changeType"]:not(:checked)').parent().hide();
      // Disable submit button
      $('#submitButton').prop('disabled', true);
      $('#searchBtn').prop('disabled', true);
      const forms = document.querySelectorAll('form');
      forms.forEach((form) => {
      if (form.id == "searchResultForm")
      form.style.display = "inline";
      })
    });

    // Clear button click event
    $('#clearBtn').click(function() {
      $('#loanAccountNo').val('');
      $('#customerName').val('');
      $('#loanAccountNo').prop('disabled', false);
      $('.form-check-input').parent().show();
      $('input[name="changeType"]').prop('checked', false);
      $('#submitButton').prop('disabled', true);
      $('#searchBtn').prop('disabled', false);
      const forms = document.querySelectorAll('form');
      forms.forEach((form) => {
      if (form.id == "searchResultForm")
      form.style.display = "none";
      });
      $('#repayScheduleDiv').hide();
      $('#repayScheduleTable').DataTable().destroy();

    });

    $('#save').click(function() {
              $('.spinner').show();
        $.ajax({
            url: '../reschedule',
            method: 'PUT',
            success: function(response) {
              $('.spinner').hide();
                if (confirm("Previous Repay Schedule was Rescheduled Successfully. Press 'ok' to reschedule more?")) {
                    location.reload();
                } else {
                    var currentUrl = window.location.href;
                    var parts = currentUrl.split('/');
                    var newUrl = parts.slice(0, -2).join('/');
                    window.location.href = newUrl;
                }
            },
            error: function(xhr, textStatus, errorThrown) {
              $('.spinner').hide();
                  if (xhr.status === 404) {
                      var responseText = xhr.responseText; // Get the response text
                      alert(responseText);
                  } else {
                      alert('Error fetching update results');
                  }
            }
        });
    });


    document.getElementById("enteredDueDate").addEventListener("input", function() {
        var enteredValue = parseInt(this.value, 10);
        if (enteredValue < 1 || enteredValue > 28) {
            document.getElementById("errorMessage").style.display = "block";
            this.setCustomValidity("Invalid input");
        } else {
            document.getElementById("errorMessage").style.display = "none";
            this.setCustomValidity("");
        }
    });
  });
</script>
</body>
</html>