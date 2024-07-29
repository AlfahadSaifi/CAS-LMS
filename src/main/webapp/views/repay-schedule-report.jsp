<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page isELIgnored="false" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Repay Schedule Report</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/2.0.5/css/dataTables.dataTables.css">
   <style>
       table {
           border-collapse: collapse;
           width: 100%;
       }
       th, td {
           border: 1px solid #dddddd;
           text-align: left;
           padding: 8px;
       }

       button a {
               text-decoration: none;
               color: white;
           }
       td{
        text-align:right;
       }
       #errorMsg {
           display: none;
       }
   </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<br>

        <form action="loan-input" method="post">
        <div style="display: flex; height:37px">
            <label style="margin-left: 110px; margin-top: 10px;"><spring:message code="LoanId"/></label> &nbsp
            <input type="text" id="loanId" name="loanId" class="form-control" style="width: 250px" placeholder="Enter Loan Application Id">
            <button id="showtext" type="submit" class="btn btn-primary fixbutton" ><spring:message code="Submit"/></button>
            </div>
        </form>
        <br>

    <c:if test="${not empty repaySchedule}">
        <div id="generatedSchedule">
                <h2 style="margin-left: 100px;"><spring:message code="GeneratedRepaySchedule"/></h2>


                <div style="margin-left:100px; margin-right:100px;">
                    <table id="myDataTable" border="1" class="table table-striped table-bordered">
                        <thead class="thead-dark">
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
                            <c:forEach var="repaySchedule" items="${repaySchedule}">
                                <tr>
                                    <td>${repaySchedule.installmentNumber}</td>
                                    <td style="text-align:left">${repaySchedule.dueDate}</td>
                                    <td><fmt:formatNumber value="${repaySchedule.openingBalance}" pattern="#,##0.00"/></td>
                                    <td><fmt:formatNumber value="${repaySchedule.installmentAmount}" pattern="#,##0.00"/></td>
                                    <td><fmt:formatNumber value="${repaySchedule.principalComponent}" pattern="#,##0.00"/></td>
                                    <td><fmt:formatNumber value="${repaySchedule.interestComponent}" pattern="#,##0.00"/></td>
                                    <td><fmt:formatNumber value="${repaySchedule.effectiveInterestRate}" pattern="#,##0.00"/></td>
                                    <td><fmt:formatNumber value="${repaySchedule.outstandingBalancePrincipal}" pattern="#,##0.00"/></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <br><br>

        <div style="display: flex; margin-bottom:30px; justify-content: center">
            <button class="btn btn-primary fixbutton"><a href="repay-schedule-pdf"><spring:message code="DownloadPDF"/></a></button>&nbsp;&nbsp;
            <button class="btn btn-primary fixbutton"><a href="repay-schedule-xls"><spring:message code="DownloadXLS"/></a></button>&nbsp;&nbsp;
            <button class="btn btn-primary fixbutton"><a href="repay-schedule-csv"><spring:message code="DownloadCSV"/></a></button>&nbsp;&nbsp;
        </div>
    </c:if>
    <script src="https://cdn.datatables.net/2.0.5/js/dataTables.js"></script>

    <script>
        $(document).ready(function() {
                $("#showtext").click(function() {
                    var loanId = $("#loanId").val().trim(); // Get the value of the text box

                    if (loanId === "") {
                        alert("Loan Id cannot be empty") // Show errorMsg div if text box is empty
                    }
                });
        });
    </script>

</body>
</html>
