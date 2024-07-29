<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
<%@ include file="navbar.jsp"%>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Repayment Policy Form</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <style>
        /* Add custom CSS here if needed */
        .installment-buttons .btn {
            width: calc(16.666% - 10px); /* 100% divided by 6 buttons per row minus margins */
            margin: 5px; /* Margin between buttons */
        }
    </style>

</head>
<body>


    <form:form modelAttribute="repaymentPolicy" method="post" action="${pageContext.request.contextPath}/maker/updaterepaymentpolicy/${repaymentPolicy.metaData.recordStatus}">
    <div class="container mt-5">
      <div class="container mt-5">
        <div class="row">
          <div class="col-md-12 d-flex justify-content-between align-items-center">
            <h1>update Repayment Policies</h1>
            <button type="submit" name="submit" value="save"class="btn btn-primary">Save </button>
            <button type="submit" name="submit" value="Save & Request Approval"class="btn btn-primary">Save & Request Approval</button>
            <button type="button" name="submit" class="btn btn-secondary"  onclick="window.history.back();">Cancel</button>
          </div>
        </div>
        <hr>
       <div class="row">
                  <!-- Permanent Parameters - Left Container -->
                  <div class="col-md-6">
                      <h3><spring:message code="form.permanentParameters"/></h3>
                      <div class="form-group">
                          <label for="repaymentPolicyCode"><spring:message code="label.repaymentPolicyCode"/></label>
                          <form:input path="repaymentPolicyCode" class="form-control" id="repaymentPolicyCode" placeholder="<spring:message code='placeholder.enterRepaymentPolicyCode'/>" required="true"/>
                          <form:errors path="repaymentPolicyCode" cssStyle="color:red"/>
                      </div>
                      <div class="form-group">
                          <label for="contractType"><spring:message code="label.contractType"/></label>
                          <form:select path="contractType" class="form-control" id="contractType">
                              <form:option value="LEASE"><spring:message code="option.lease"/></form:option>
                              <form:option value="LOAN"><spring:message code="option.loan"/></form:option>
                          </form:select>
                      </div>
                  </div>
                  <!-- Permanent Parameters - Right Container -->
                  <div class="col-md-6">
                      <h3><spring:message code="form.permanentParameters"/></h3>
                      <div class="form-group">
                          <label for="repaymentPolicyName"><spring:message code="label.repaymentPolicyName"/></label>
                          <form:input path="repaymentPolicyName" class="form-control" id="repaymentPolicyName" placeholder="<spring:message code='placeholder.enterRepaymentPolicyName'/>"/>
                          <form:errors path="repaymentPolicyName" cssStyle="color:red"/>
                      </div>
                  </div>
              </div>
              <div class="row">
                  <!-- Generic Parameters - Left Container -->
                  <div class="col-md-6">
                      <h3><spring:message code="form.genericParameters"/></h3>
                      <div class="form-group">
                          <label for="recoveryType"><spring:message code="label.recoveryType"/></label>
                          <form:select path="recoveryType" class="form-control" id="recoveryType">
                              <form:option value="INSTALLMENT"><spring:message code="option.installment"/></form:option>
                              <form:option value="NONINSTALLMENT"><spring:message code="option.noninstallment"/></form:option>
                          </form:select>
                      </div>
                      <label for="installmentDueDate"><spring:message code="label.installmentDueDate"/></label>
                      <br>
                      <form:select path="installmentDueDate" items="${buttonValues}" class="form-control"/>
                  </div>
                  <!-- Generic Parameters - Right Container -->
                  <div class="col-md-6">
                      <h3><spring:message code="form.genericParameters"/></h3>
                      <div class="form-group">
                          <label for="repaymentFrequency"><spring:message code="label.repaymentFrequency"/></label>
                          <form:select path="repaymentFrequency" class="form-control" id="repaymentFrequency">
                              <form:option value="MONTHLY"><spring:message code="option.monthly"/></form:option>
                              <form:option value="QUARTERLY"><spring:message code="option.quarterly"/></form:option>
                              <form:option value="HALF_YEARLY"><spring:message code="option.halfyearly"/></form:option>
                              <form:option value="YEARLY"><spring:message code="option.yearly"/></form:option>
                          </form:select>
                      </div>
                      <div class="form-group">
                          <label for="repaymentType"><spring:message code="label.repaymentType"/></label>
                          <form:select path="repaymentType" class="form-control" id="repaymentType">
                              <form:option value="INTEREST_ONLY"><spring:message code="option.interestOnly"/></form:option>
                              <form:option value="PRINCIPAL_AND_INTEREST"><spring:message code="option.principalAndInterest"/></form:option>
                          </form:select>
                      </div>
                  </div>
              </div>
          </form:form>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <script type="text/javascript" charset="utf8" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    </body>
    </html>