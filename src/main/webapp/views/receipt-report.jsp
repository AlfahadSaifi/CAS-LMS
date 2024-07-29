<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="org.nucleus.entity.permanent.Receipt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Receipt Report</title>
  <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.5/css/dataTables.dataTables.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://cdn.datatables.net/2.0.5/js/dataTables.js"></script>
    <script>
        $(document).ready(function() {
           $("#myDataTable").DataTable({
           searching: false,
           paging: false,
           ordering: false,
           info: false,
           });



            function validateForm() {
            var currentDate = new Date();
            var toDate = $('#todate').val().trim();
                        var fromDate = $('#fromdate').val().trim();

                var receiptNo = $('#ReceiptNo').val().trim();
                var loanAcc = $('#loanacc').val().trim();
                var toDateObj = new Date(toDate);
                   if (toDateObj > currentDate) {
                $('#dateErrorMessage').text('Date must be smaller or equal to current date');
                $('#dateErrorMessage').show();                    return false; // Prevent form submission
                }
                else{$('#dateErrorMessage').hide();}

                if (fromDate > toDate) {
                                   $('#dategMessage').text('Date must be smaller than or equal to To Date');
                                   $('#dategMessage').show();
                                    return false; // Prevent form submission
                                }  else{$('#dategMessage').hide();}
                if (receiptNo === '' && loanAcc === '') {
                    alert('Please enter either Receipt Number or Loan Account Number.');
                    return false; // Prevent form submission
                }
                return true; // Allow form submission
            }


            $('#form').submit(function() {
                return validateForm();
            });

            $('#ReceiptNo').on('input', function() {
                if ($(this).val().trim() !== '') {
                    $('#loanacc').val('');
                    $("#fromdate").removeAttr("required");
                    $("#todate").removeAttr("required");
                    $('#fromdate').val('');
                    $('#todate').val('');
                }

            });

            // When Loan Account Number input is filled, disable Receipt Number input
            $('#loanacc').on('input', function() {
                if ($(this).val().trim() !== ''){
                    $('#ReceiptNo').val('');
                    $("#fromdate").attr("required", "true");
                    $("#todate").attr("required", "true");
                }
            });
        });
    </script>
    <style>
        .mainHeading {
            font-size: 2rem;
            font-weight: 700;
        }



        .container {
            display: flex;
            justify-content: space-between;

        }
        .rightFormFields{
        margin-right:100px;
        }
        .formContent {
            display: flex;
            justify-content:space-between;
            position: relative;
        }
        .formHeading {
            margin-left: 4rem;
            font-size: 20px;
        }
        .upperFields {
            display: flex;
            gap:8rem;
        }
        .leftFormFields {
            margin-left:50px
        }


        .formBtns{
            display: flex;
            flex-direction: row;
            margin-left: 35rem;
        }
        .formBtns button{
            margin: 5px;
        }
    </style>
</head>

<body>
 <%@ include file="navbar.jsp"%>

     <div class="container my-4 card shadow">
        <h2 style="margin-left:3rem;"><label for="receiptreport"><spring:message code="receiptreport"/></label>
        </h2>
    <form id="form" action="form" method="post">
        <div class="formContainer">
            <div class="formContent">
                <hr class="loanInfoHr">
                <div class="mainForm">
                    <form class="loanForm">
                        <div class="upperFields">
                            <div class="leftFormFields">

                                <label for="loanAccountNumber"><spring:message code="label.loanAccountNumber"/></label><br>
                                <input type="text" placeholder="LoanAccNo" class="form-control" id="loanacc" name="loanAcc" style="width: 350px"> <br>
                        <label for="fromDate"><spring:message code="fromDate"/></label><br>
                                <input type="date" id="fromdate" class="form-control" name="fromdate"> <span id="dategMessage"style="color: red; display: none;"></span> <br><br>
                        <label for="toDate"><spring:message code="toDate"/></label><br>

                                <input type="date" id="todate" class="form-control" name="todate">
                                <span id="dateErrorMessage"style="color: red; display: none;"></span><br>
                            </div>
                            <div>

                                <h3><label for="or"><spring:message code="or"/></label></h3>
                            </div>

                            <div class="rightFormFields">
                             <label for="receiptId"><spring:message code="receiptId"/></label><br>

                                <input type="number" placeholder="Receipt Number" min=1 class="form-control" id="ReceiptNo" name="receiptNo" style="width:350px"> <br>
                            </div>
                        </div>

                        <div class="rightLowerFields">
                            <div class="formBtns">
                                        <button class="btn btn-primary" type="submit" style="margin-left:250px;margin-bottom:20px;"><spring:message code="button.generate"/></button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </form>
    </div>
    <div class="container my-4 card shadow">
            <c:if test="${show}">
            <table id="myDataTable">
                <thead class="thead-dark" style="background-color: black;">
                    <tr>

                        <td><center><label for="receiptId" style="color: white; font-weight:bolder"><spring:message code="receiptId"/></label></center></td>
                        <td><center><label for="loanAccountNumber" style="color: white; font-weight:bolder"><spring:message code="label.loanAccountNumber"/></label></center></td>
                        <td><center><label for="transactionDate" style="color: white; font-weight:bolder"><spring:message code="transactionDate"/></label></center></td>
                        <td><center><label for="options" style="color: white; font-weight:bolder"><spring:message code="options"/></label></center></td>

                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr>
                            <td><center>${item.receiptId}</center></td>
                            <td><center>${item.loanAccountNumber}</center></td>
                            <td><center>${item.transactionDate}</center></td>
                            <td><center>
                                <form action="getPDF/${item.receiptId}" method="post">
                                                <button class="btn btn-primary" type="submit"><spring:message code="button.download"/></button>

                             </center>   </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>
