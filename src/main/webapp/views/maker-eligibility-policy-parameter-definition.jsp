<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="org.nucleus.entity.permanent.EligibilityPolicyParameterDefinition, java.util.*" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
     <title>Eligibility Parameters</title>
        <!-- Include jQuery and DataTables CSS and JS files -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <script src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css">

</head>
<body>
<div>
<%@ include file="navbar.jsp"%>
</div>
<style>



    </style>
<div class="container mt-5" style="display:flex; justify-content:space-between;align-items: baseline;flex-wrap: wrap;margin-top: 20px;">
    <h2> Eligibility Parameter</h2>
    <button type="submit" class="btn btn-primary fixbutton"><a href="${pageContext.request.contextPath}/maker/new-paramater" style="color:white; text-decoration:none;">New Parameter</a></button>
</div>
<hr>
<!--
<h1 style="color:red">${message}</h1>--!>
<div class="container" style="margin-top: 20px;">
    <div class="table-container" style="overflow-x: auto;">
         <table id="parameterTable" class="table table-striped table-bordered" style="width:100%; font-size: small;">
                <caption>List for Makers</caption>

                <thead class="thead-dark">
                    <tr>

                        <th>Parameter Code</th>
                        <th>Parameter Name</th>
                        <th>Parameter Description</th>
                        <th>Record Status</th>
                        <th>Creation Date</th>
                        <th>Created By</th>
                        <th>Modified Date</th>
                        <th>Modified By</th>
                        <th>Authorized Date</th>
                        <th>Authorized By</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>

                        <c:forEach items="${tempParameters}" var="parameter" varStatus="status">
                            <tr>

                                <td>${parameter.eligibilityParameterCode}</td>
                                <td>${parameter.eligibilityParameterName}</td>
                                <td>${parameter.eligibilityParameterDescription}</td>
                                <td>${parameter.tempMetaData.recordStatus}</td>
                                <td>${parameter.tempMetaData.creationDate}</td>
                                <td>${parameter.tempMetaData.createdBy}</td>
                                <td>${parameter.tempMetaData.modifiedDate}</td>
                                <td>${parameter.tempMetaData.modifiedBy}</td>
                                <td>${parameter.tempMetaData.authorizedDate}</td>
                                <td>${parameter.tempMetaData.authorizedBy}</td>
                                <td>
                                    <div style="display:flex">
                                        <a class="btn btn-primary btn-sm mr-1" href="${pageContext.request.contextPath}/maker/update/${parameter.eligibilityParameterId}">Edit</a>
                                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/maker/delete/${parameter.eligibilityParameterId}">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        <c:forEach items="${perParameters}" var="parameter">
                            <tr>

                                <td>${parameter.eligibilityParameterCode}</td>
                                <td>${parameter.eligibilityParameterName}</td>
                                <td>${parameter.eligibilityParameterDescription}</td>
                                <td>${parameter.tempMetaData.recordStatus}</td>
                                <td>${parameter.tempMetaData.creationDate}</td>
                                <td>${parameter.tempMetaData.createdBy}</td>
                                <td>${parameter.tempMetaData.modifiedDate}</td>
                                <td>${parameter.tempMetaData.modifiedBy}</td>
                                <td>${parameter.tempMetaData.authorizedDate}</td>
                                <td>${parameter.tempMetaData.authorizedBy}</td>
                                <td>
                                    <div class="btn-group" role="group">
                                        <a class="btn btn-primary btn-sm mr-1" href="${pageContext.request.contextPath}/maker/update/per${parameter.eligibilityParameterId}">Edit</a>
                                        <a class="btn btn-danger btn-sm" href="${pageContext.request.contextPath}/maker/delete/per${parameter.eligibilityParameterId}">Delete</a>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>


                </tbody>
            </table>



        </div>

        <div>



        </div>
<c:if test="${not empty message}">
        <script>
           
            window.onload = function() {
                alert("${message}");
            }
        </script>
    </c:if>


</body>
</html>


