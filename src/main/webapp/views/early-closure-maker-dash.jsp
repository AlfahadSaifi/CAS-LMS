<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType ="text/html;charset=UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Early Closure Form</title>
  <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
      #hiddenDiv {
        margin-top: 20px;
        padding: 20px;
        background-color: #f0f0f0;
        border-radius: 10px;
        display: none;
      }
  </style>

    <script>
        function setClosureDateToToday()
        {
            var today = new Date().toISOString().split('T')[0]; // Get today's date in yyyy-mm-dd format
            document.getElementById("closureDate").value = today; // Set the value of closure date input field
        }
        function toggleDivVisibility() {
          var div = document.getElementById("hiddenDiv");
          if (div.style.display === "none") {
            div.style.display = "block";
          } else {
            div.style.display = "none";
          }
        }
         document.addEventListener("DOMContentLoaded", function() {
            setClosureDateToToday(); // Call the function to set closure date to today's date
          });
    </script>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow">
  <form:form id="closureForm" action="closure-form" modelAttribute="earlyClosureResponseDTO">
    <div class="row justify-content-between align-items-center mb-3 my-4">
      <div class="col-auto">
        <h3><spring:message code="label.earlyClosure"/></h3>
      </div>
    </div>

    <div class="form-container">
          <div class="row mb-3">
            <div class="col-md-6 mb-3">
              <div class="form-group">
                <label for="loanAccountSearch">
                  <spring:message code="label.loanAccountSearch" />
                </label>
                <div class="input-group">
                  <input type="text" id="loanAccountSearch" name="loanAccountSearch" class="form-control" placeholder="Enter Loan Account No."/>
                  <div class="input-group-append">
                    <button type="submit" class="btn btn-primary" name="action" value="search"><spring:message code="button.search" /></button>
                  </div>
                </div>
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


    <c:if test="${empty errorMessage}">
    <br>
    <h3>Details</h3>
    <div class="row">
          <div class="form-group col-md-6">
            <label for="loanAccountNumber">
            <spring:message code="label.loanAccountNumber" />
            </label>
              <div class="input-group">
                <form:input type="text" id="loanAccountNumber" class="form-control" path="loanAccountNumber"  readonly="true"/>
              </div>
          </div>
          <div class="form-group col-md-6">
                    <label for="closureDate">
                    <spring:message code="label.closureDate" />
                    <span class="text-danger">*</span></label>
                    <form:input type="date" class="form-control" id="closureDate" path="closureDate" required="true" min="<fmt:formatDate pattern='yyyy-MM-dd' value='${now}'/>"/>
          </div>
    </div>

    <div class="row">
    <div class="form-group col-md-6">
      <label for="customerName">
      <spring:message code="label.customerName" />
      </label>
      <form:input type="text" class="form-control" id="customerName" path="customerName" readonly="true"/>
    </div>

    <div class="form-group col-md-6">
          <label for="loanAmount">
          <spring:message code="label.loanAmount" />
          </label>
          <form:input type="text" class="form-control" id="loanAmount" path="loanAmount" readonly="true"/>
        </div>
    </div>

    <div class="row">
    <div class="form-group col-md-6">
      <label for="balancePrinciple">
      <spring:message code="label.balancePrinciple" />
      </label>
      <form:input type="number" step="any" class="form-control" id="balancePrinciple" path="balancePrinciple" readonly="true"/>
    </div>

    <div class="form-group col-md-6">
      <label for="balanceInstallment">
      <spring:message code="label.balanceInstallment" />
      </label>
      <form:input type="number" step="any" class="form-control" id="balanceInstallment" path="balanceInstallment" readonly="true"/>
    </div>
    </div>

    <div class="row justify-content-center">
            <div>
                <button type="button" class="btn btn-primary" id="save" onclick="toggleDivVisibility()"><spring:message code="button.getDetails" /></button>
             </div>
    </div>
    <br>
    <div id="hiddenDiv" style="display: none;">
        <div class="row">
            <div class="form-group col-md-6">
                <label for="totalDuePrincipal">
                <spring:message code="label.totalDuePrincipal" />
                </label>
                <form:input type="number" class="form-control" id="totalDuePrincipal" path="totalDuePrincipal" readonly="true"/>
            </div>

            <div class="form-group col-md-6">
                <label for="dueCharges">
                <spring:message code="label.dueCharges" />
                </label>
                <form:input type="number" class="form-control" id="dueCharges" path="dueCharges" readonly="true"/>
            </div>
        </div>

        <div class="row">
            <div class="form-group col-md-6">
                <label for="totalClosureAmount">
                <spring:message code="label.totalClosureAmount" />
                </label>
                <form:input type="number" step="any" class="form-control" id="totalClosureAmount" path="totalClosureAmount" readonly="true"/>
            </div>

            <div class="form-group col-md-6">
                <label for="closureReason">
                <spring:message code="label.closureReason" />
                <span class="text-danger">*</span>
                </label>
                <select class="form-control" id="closureReason" path="closureReason" required="true"/>
                    <option value="FinancialSurplus">Financial Surplus</option>
                    <option value="ReducingInterestBurden">Reducing Interest Burden</option>
                    <option value="LifestyleAndPlanning">Lifestyle and Planning</option>
                    <option value="ImprovingFinancialHealth">Improving Financial Health</option>
                </select>
            </div>
        </div>
        <button type="submit" class="btn btn-primary" name="action" value="submit"><spring:message code="label.subReq" /></button>
    </div>
    </c:if>
  </form:form>
</div>

<!-- Bootstrap JS and jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
