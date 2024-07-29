<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Policy Checker</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
            .actionButtons {
                display: flex;
                gap: 10px; /* Adjust the gap between buttons */
            }
            .hidden{
                display:none
            }
    </style>

    <script type="text/javascript">
        $(document).ready( function () {
            $('#policyTable').DataTable({
                 "scrollX": true
             });

            $(".row").click(function() {
                var item = $(this).closest("tr");
                var cell = item.children("td");
                $("#policyCode").val(cell.eq(1).text());
                $("#policyName").val(cell.eq(2).text());
                $("#eligibilityCriteria").val(cell.eq(3).text());
                $("#myrow").val(cell.eq(11).text());
                console.log(cell.eq(8).text());
            });

        });
    </script>
</head>

<body>
<%@ include file="navbar.jsp"%>
<div class="container mt-5">
    <h2>List of Eligibility Policies</h2>
    <hr>
</div>

<div class="container">
   <div class="table-container" style="overflow-x: auto;">
    <table id="policyTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
        <thead class="thead-dark">
            <tr>
                <%-- <th><spring:message code="label.SLNo"/></th> --%>
                <th><spring:message code="label.policyId"/></th>
                <th><spring:message code="label.PolicyCode"/></th>
                <th><spring:message code="label.EligibilityCriteria"/></th>
                <th><spring:message code="label.Status"/></th>

                <th><spring:message code="label.CreatedBy"/></th>
                <th><spring:message code="label.CreatedDate"/></th>
                <th><spring:message code="label.ModifiedBy"/></th>
                <th><spring:message code="label.ModifiedDate"/></th>
                <th><spring:message code="label.AuthorizedBy"/></th>
                <th><spring:message code="label.AuthorizedDate"/></th>
                <th><spring:message code="label.Actions"/></th>

                <th class="hidden">param</th>
            </tr>
        </thead>
        <tbody>
        <c:if test="${parameters!=null}">
            <c:forEach items="${parameters}" var="policy" varStatus="status">
                <tr>
                   <%-- <td>${status.index + 1}</td> --%>
                    <td>${policy.eligibilityPolicyId}</td>
                    <td><a data-toggle="modal" data-target="#myModal" class="row" style="margin-left:4px">${policy.eligibilityPolicyCode}</a></td>
                    <td>${policy.eligibilityCriteria}</td>
                    <td>${policy.metaData.recordStatus}</td>
                    <td>${policy.metaData.createdBy}</td>
                    <td>${policy.metaData.creationDate}</td>
                    <td>${policy.metaData.modifiedBy}</td>
                    <td>${policy.metaData.modifiedDate}</td>
                    <td>${policy.metaData.authorizedBy}</td>
                    <td>${policy.metaData.authorizedDate}</td>
                    <td>
                    <div class="actionButtons">
                        <form action="approve-aditya" method="post">
                            <input type="hidden" name="eligibilityPolicyId" value="${policy.eligibilityPolicyId}">
                            <button type="submit" class="btn btn-primary">Approve</button>
                        </form>
                        <form action="reject-aditya">
                            <input type="hidden" name="eligibilityPolicyId" value="${policy.eligibilityPolicyId}">
                            <button type="submit" class="btn btn-danger">Reject</button>
                        </form>
                        <br>

                    </div>
                    </td>
                    <td class="hidden"><c:forEach items="${policy.eligibilityParameterMappingList}" var="parameters" varStatus="loop"><${loop.index+1}. ${parameters.parameter} ${parameters.operator} ${parameters.value}>&nbsp<br></c:forEach>
                </tr>
            </c:forEach>
            </c:if>
        </tbody>
    </table>
    </div>
    </div>

    <div class="modal" id="myModal" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Details</h4>

                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <label for="policyCode">Policy Id</label><br>
                    <input type="text" id="policyCode" disabled><br><br>
                    <label for="policyName">Policy Code</label><br>
                    <input type="text" id="policyName" disabled><br><br>
                    <label for="eligibilityParameters">Eligibility Parameters</label><br>
                    <input type="text" id="myrow" disabled><br><br>
                    <label for="eligibilityCriteria">Eligibility Criteria</label><br>
                    <textarea id="eligibilityCriteria" disabled></textarea><br><br>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
