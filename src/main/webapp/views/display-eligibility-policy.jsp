<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.List,org.nucleus.entity.temporary.EligibilityPolicyParameterTemp,java.util.ArrayList"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Eligibility Policies Page</title>
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="http://datatables.net/release-datatables/extensions/TableTools/css/dataTables.tableTools.css">
    <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.13.5/js/jquery.dataTables.min.js"></script>
    <script>
        function verifyDeletion(event) {
            var result = window.confirm("Are you sure you want to delete the record?");
            if (!result) {
                event.preventDefault();
                alert("Action canceled.");
            }
        }
        $(document).ready(function() {
            $('#table').dataTable({"scrollX":true});
            $(".row").click(function() {
                var item = $(this).closest("tr");
                var cell = item.children("td");
                $("#policyCode").val(cell.eq(0).text());
                $("#policyName").val(cell.eq(1).text());
                $("#eligibilityCriteria").val(cell.eq(10).text());
                $("#myrow").val(cell.eq(11).text());
                console.log(cell.eq(11).text());
            });
            $('#select-unselect').click(function(){
                $('input[type="checkbox"]').prop('checked', $(this).prop('checked'));
            });
            $('.checkbox').change(function(){
                var isChecked = $(this).prop('checked');
                        var row = $(this).closest('tr');
                        if(isChecked){
                            row.css('background-color', 'LightGray');
                        } else {
                            row.css('background-color', '');
                        }
                console.log("ld");
            });
        });
    </script>
    <style>
        .hidden{
            display:none
        }

    </style>
</head>
<body>
    <%@ include file="navbar.jsp"%>
    <div class="container mt-5" style="display: flex; justify-content: space-between; align-items: baseline; flex-wrap: wrap; margin-top: 20px;">
        <h2 style="margin: 0;">List of Eligibility Policies</h2>
        <form action="create-eligibility-policy">
            <button type="submit" class="btn btn-primary fixbutton">Create new Eligibility Policy</button>
        </form>
        <hr style="width: 100%;">
    </div>

    <div class="container">
        <div class="table-container" style="overflow-x: auto;">

    <div style="color:blue">${message}</div>
    <table id="table" class="table table-striped table-bordered" style="width:100%; font-size: small;">
            <thead class="thead-dark">
            <tr>
                <%--<th><input type="checkbox" id="select-unselect"></th>--%>
                <th>Policy Code</th>
                <th>Policy Name</th>
                <th>Status</th>
                <th>Created By</th>
                <th>Creation Date</th>
                <th>Modified By</th>
                <th>Modification Date</th>
                <th>Authorized By</th>
                <th>Authorization Date</th>
                <th>Action</th>
                <th class="hidden">EligibilityCriteria</th>
                <th class="hidden">EligibilityParameters</th>
            </tr>
        </thead>
        <tbody>
            <c:if test="${allPolicies!=null}">
            <c:forEach items="${allPolicies}" var="policy" >
                <tr >
                    <%--<td><input type="checkbox" class="checkbox"></td>--%>
                    <td><a data-toggle="modal" data-target="#myModal"  class="row" style="margin-left:4px">${policy.eligibilityPolicyCode}</a></td>
                    <td>${policy.eligibilityPolicyName}</td>
                    <td>${policy.metaData.recordStatus}</td>
                    <td>${policy.metaData.createdBy}</td>
                    <td>${policy.metaData.creationDate}</td>
                    <td>${policy.metaData.modifiedBy}</td>
                    <td>${policy.metaData.modifiedDate}</td>
                    <td>${policy.metaData.authorizedBy}</td>
                    <td>${policy.metaData.authorizedDate}</td>
                    <td>
                        <div class="btn-group" role="group">
                            <a class="btn btn-primary btn-sm mr-1" href="update-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" style="color: white;">Edit</a>
                            <a class="btn btn-danger btn-sm" onclick="verifyDeletion(event)" href="delete-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" style="color: white;">Delete</a>
                        </div>
                    </td>
                    <%--<td><a href="update-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" class="update">Update</a><a href="delete-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" class="delete">Delete</a></td>--%>
                    <td class="hidden">${policy.eligibilityCriteria}</td>
                    <td class="hidden"><c:forEach items="${policy.eligibilityParameterMappingList}" var="parameters" varStatus="loop"><${loop.index+1}. ${parameters.parameter} ${parameters.operator} ${parameters.value}>&nbsp<br></c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </c:if>
            <c:if test="${allPermPolicies!=null}">
            <c:forEach items="${allPermPolicies}" var="policy" >
                <tr >
                    <%--<td><input type="checkbox" class="checkbox"></td>--%>
                    <td><a data-toggle="modal" data-target="#myModal"  class="row" style="margin-left:4px">${policy.eligibilityPolicyCode}</a></td>
                    <td>${policy.eligibilityPolicyName}</td>
                    <td>${policy.metaData.recordStatus}</td>
                    <td>${policy.metaData.createdBy}</td>
                    <td>${policy.metaData.creationDate}</td>
                    <td>${policy.metaData.modifiedBy}</td>
                    <td>${policy.metaData.modifiedDate}</td>
                    <td>${policy.metaData.authorizedBy}</td>
                    <td>${policy.metaData.authorizedDate}</td>
                    <td>
                        <div class="btn-group" role="group">
                            <a class="btn btn-primary btn-sm mr-1" href="update-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" style="color: white;">Edit</a>
                            <a class="btn btn-danger btn-sm" onclick="verifyDeletion(event)" href="delete-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" style="color: white;">Delete</a>
                        </div>
                    </td>
                    <%--<td><a href="update-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" class="update">Update</a><a href="delete-eligibility?policyId=${policy.eligibilityPolicyId}&flag=${policy.metaData.recordStatus}" class="delete">Delete</a></td>--%>
                    <td class="hidden">${policy.eligibilityCriteria}</td>
                    <td class="hidden"><c:forEach items="${policy.eligibilityParameterMappingList}" var="parameters" varStatus="loop"><${loop.index+1}. ${parameters.parameter} ${parameters.operator} ${parameters.value}>&nbsp<br></c:forEach>
                    </td>
                </tr>
            </c:forEach>
            </c:if>
        </tbody>
    </table>
    <div class="modal" id="myModal" role="dialog">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">Details</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                <div class="modal-body">
                    <label for="policyCode">Policy Code</label><br>
                    <input type="text" id="policyCode" disabled><br><br>
                    <label for="policyName">Policy Name</label><br>
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