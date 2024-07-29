<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="org.nucleus.dto.LoanApplicationDTO, java.util.*" isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>List of Loan Applications</title>
</head>
<body>


<%@ include file="navbar.jsp"%>
<div class="container mt-5" style="display:flex; justify-content:space-between;align-items: baseline;flex-wrap: wrap;margin-top: 20px;">
    <h2>List of Loan Applications</h2>
    <a class="btn btn-primary fixbutton" href="${pageContext.request.contextPath}/maker/applicant" style="color:white; text-decoration:none;">Create New Loan Application</a>
</div>
<hr>
<div class="container" style="margin-top: 20px;">
  <div class="row">
    <div class="col-md-12">
      <div class="table-div" class="table-container" style="max-width: 100%; overflow-x: auto;">
        <table id="table" class="table table-bordered table-striped" style="width:100%; font-size: small;">
          <thead class="thead-dark">
            <tr>
              <th>Loan Application Id</th>
              <th>Loan Account Number</th>
              <th>CIF Number</th>
              <th>Loan Status</th>
              <th>Record Status</th>
              <th>Created By</th>
              <th>Creation Date</th>
              <th>Modified By</th>
              <th>Modified Date</th>
              <th>Authorized By</th>
              <th>Authorized Date</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${loanApplicationDTOS}" var="loanApplication">
              <tr>
                <td>
                     <a href="javascript:void(0);" class="open-loan-modal" data-parameter="${loanApplication}">
                        ${loanApplication.loanApplicationId}
                     </a>
                </td>
                <td>${loanApplication.loanAccountNumber}</td>
                <td>
                     <a href="javascript:void(0);" class="open-modal" data-parameter="${loanApplication.customerDTO}">
                        ${loanApplication.customerDTO.cifNumber}
                     </a>
                </td>
                <td>${loanApplication.loanStatus}</td>
                <td>${loanApplication.recordStatus}</td>
                <td>${loanApplication.createdBy}</td>
                <td>${loanApplication.creationDate}</td>
                <td>${loanApplication.modifiedBy}</td>
                <td>${loanApplication.modifiedDate}</td>
                <td>${loanApplication.authorizedBy}</td>
                <td>${loanApplication.authorizedDate}</td>
                <td>
                    <div class="btn-group" role="group">
                        <a class="btn btn-primary btn-sm mr-1" href="${pageContext.request.contextPath}/maker/update-loan-application/${loanApplication.loanApplicationId}">Edit</a>
                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/maker/delete-loan-application/${loanApplication.loanApplicationId}">Delete</a>
                    </div>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>



<!-- Modal Structure -->
<div class="modal fade" id="modal" tabindex="-1" role="dialog" aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="modalLabel"></h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <tbody id="modal-body"></tbody>
                </table>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

<script>
  $(document).ready(function() {
      $('#table').DataTable({
          "scrollX": true
      });

      $('.open-loan-modal').on('click', function() {
            $('#modalLabel').text('Loan Application Details');
            var parameter = $(this).data('parameter');
            var modalBody = $('#modal-body');

            modalBody.empty();

            var loanApplicationInfo=parameter.substring(parameter.indexOf("loanApplicationId"), parameter.indexOf("applicationPurpose"))
                            +parameter.substring(parameter.indexOf("branch"), parameter.indexOf('customerDTO')-2)+", "
                            +parameter.substring(parameter.indexOf("productName"), parameter.indexOf("productTypeCode")-2);


            var loanApplicationInfoArray = loanApplicationInfo.split(", ");
            var tenureFreq=loanApplicationInfo.substring(loanApplicationInfo.indexOf("tenureIn"), loanApplicationInfo.indexOf("rate")-2).split("=")[1].trim().replace(/['"]+/g, '');

            $.each(loanApplicationInfoArray, function(index, value) {
                var pair = value.split("=");
                var attributeName = pair[0].trim();

                if (attributeName === "rate" || attributeName==="tenureIn")return;
                var attributeValue = pair[1].trim().replace(/['"]+/g, '');
                if(attributeName==='tenure'){
                    attributeValue+=' '+tenureFreq;
                }

                // Capitalize the first letter of attribute name and replace underscores with spaces
                attributeName = attributeName.replace(/([A-Z])/g, ' $1').trim();
                attributeName = attributeName.charAt(0).toUpperCase() + attributeName.slice(1);

                if(attributeValue==='null')attributeValue='-';


                modalBody.append('<tr><th>' + attributeName + '</th><td>' + attributeValue + '</td></tr>');
            });

            $('#modal').modal('show');
        });

      $('.open-modal').on('click', function() {
          $('#modalLabel').text('Customer Details');
          var parameter = $(this).data('parameter');
          var modalBody = $('#modal-body');

          modalBody.empty();

          var customerInfo = parameter.substring(parameter.indexOf("customerId"), parameter.indexOf("personInfoDTO")-2);
          var personInfo = parameter.substring(parameter.indexOf("personId"), parameter.indexOf("}, occupationInfoDTO"));


          var customerInfoArray = customerInfo.split(", ");
          var personInfoArray = personInfo.split(", ");

          modalBody.append("<tr><th colspan='2' style='text-align:center' class='text-primary'>Personal Information</th></tr>");

          $.each(personInfoArray, function(index, value) {
              var pair = value.split("=");
              var attributeName = pair[0].trim();

              if (attributeName === "personId" || attributeName==="firstName" || attributeName==="middleName" || attributeName==="lastName")return;
              if (attributeName === "fullName")attributeName="Name";

              var attributeValue = pair[1].trim().replace(/['"]+/g, '');

              // Capitalize the first letter of attribute name and replace underscores with spaces
              attributeName = attributeName.replace(/([A-Z])/g, ' $1').trim();
              attributeName = attributeName.charAt(0).toUpperCase() + attributeName.slice(1);
              if(attributeValue==='null')attributeValue='-';

              modalBody.append('<tr><th>' + attributeName + '</th><td>' + attributeValue + '</td></tr>');
          });
          $.each(customerInfoArray, function(index, value) {
              var pair = value.split("=");
              var attributeName = pair[0].trim();
              if (attributeName === "customerId")return;

              var attributeValue = pair[1].trim().replace(/['"]+/g, '');

              // Capitalize the first letter of attribute name and replace underscores with spaces
              attributeName = attributeName.replace(/([A-Z])/g, ' $1').trim();
              attributeName = attributeName.charAt(0).toUpperCase() + attributeName.slice(1);
              if(attributeValue==='null')attributeValue='-';

              modalBody.append('<tr><th>' + attributeName + '</th><td>' + attributeValue + '</td></tr>');
          });

            modalBody.append("<tr><th colspan='2' style='text-align:center' class='text-primary'>Occupation Information</th></tr>");

            var occupationInfo = parameter.substring(parameter.indexOf("ageOfRetirement"), parameter.indexOf("}, financialInfoDTO"));
            var occupationInfoArray = occupationInfo.split(", ");

            $.each(occupationInfoArray, function(index, value) {
                var pair = value.split("=");
                var attributeName = pair[0].trim();

                var attributeValue = pair[1].trim().replace(/['"]+/g, '');

                // Capitalize the first letter of attribute name and replace underscores with spaces
                attributeName = attributeName.replace(/([A-Z])/g, ' $1').trim();
                attributeName = attributeName.charAt(0).toUpperCase() + attributeName.slice(1);
                if(attributeValue==='null')attributeValue='-';

                modalBody.append('<tr><th>' + attributeName + '</th><td>' + attributeValue + '</td></tr>');
            });

            modalBody.append("<tr><th colspan='2' style='text-align:center' class='text-primary'>Financial Information</th></tr>");

            var financialInfo = parameter.substring(parameter.indexOf("monthlyIncome"), parameter.indexOf("}}"));
            var financialInfoArray = financialInfo.split(", ");

            $.each(financialInfoArray, function(index, value) {
                var pair = value.split("=");
                var attributeName = pair[0].trim();
                var attributeValue = pair[1].trim().replace(/['"]+/g, '');

                // Capitalize the first letter of attribute name and replace underscores with spaces
                attributeName = attributeName.replace(/([A-Z])/g, ' $1').trim();
                attributeName = attributeName.charAt(0).toUpperCase() + attributeName.slice(1);
                if(attributeValue==='null')attributeValue='-';

                modalBody.append('<tr><th>' + attributeName + '</th><td>' + attributeValue + '</td></tr>');
            });


          $('#modal').modal('show');
      });
  });
</script>

</body>
</html>
