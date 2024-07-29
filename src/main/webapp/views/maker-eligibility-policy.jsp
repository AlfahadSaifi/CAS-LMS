<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page isELIgnored="false"%>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.List,org.nucleus.entity.temporary.EligibilityPolicyParameterTemp,java.util.ArrayList"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Eligibility Policies Page</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <%@ include file="navbar.jsp"%>
    <script>
        $(document).ready(
            function(){
                var count=$('#tableBody tr').length-1;
                console.log(count);
                $("#add").click(function(){
                    count++;
                    var newRow ='<tr>'+
                        '<td>'+
                        '<select name="parameter" required class="form-control">';
                    <c:forEach items="${listParameters}" var="parameter">
                        newRow+='<option value="${parameter.eligibilityParameterCode}-${parameter.eligibilityParameterName}">${parameter.eligibilityParameterCode}-${parameter.eligibilityParameterName}</option>';
                    </c:forEach>
                    console.log("ere");
                    newRow +='</select>'+
                        '</td>'+
                        '<td>'+
                        '<select name="operator" required class="form-control">';
                    <c:forEach items="${listOperators}" var="operator">
                       newRow+='<option value="${operator}">${operator}</option>';
                    </c:forEach>
                    newRow +='</select required>'+
                        '</td>'+
                        '<td>'+
                            '<input type="number" placeholder="Value" name="value" required class="form-control">'+
                        '</td>'+
                        '<td>'+
                            '<center><div class="remove">-</div></center>'+
                        '</td>'+
                        '</tr>';
                        $("#tableBody").append(newRow);
                });
                $("#tableBody").on("click",".remove", function(){

                        if(count<=1){
                            alert("At least one entry in table is required");
                        }
                        else{
                            count--;
                            $(this).closest("tr").remove();
                        }

                });
                $("#eligibilityCriteria").on('keyup', function(event){
                   var textArea = document.getElementById("eligibilityCriteria");
                   var counter = document.getElementById("char-count");
                   var maxLength = 100;
                   var currentLength = textArea.value.length;
                   counter.textContent="Remaining characters: " +(maxLength - currentLength);
                });
            }
        );
    </script>
    <style>
        .mandatory {
           color:red
        }
        form select{
            width:200px;
            height:30px
        }
        .error{
            color:red
        }
        #add{
            padding:8px;
            color:white;
            background-color:black;
        }
        .remove{
            padding:5px;
            color:white;
            background-color:black;
        }
        a:hover{
            color:white;
            text-decoration:none
        }
        a:active{
            color:white;
            text-decoration:none
        }
        a{
            color:white;
            text-decoration:none
        }
    </style>
</head>
<body>
<div class="container my-4 card shadow">
    <form:form action="add-eligibility" modelAttribute="eligibilityPolicyTempDTO" method="post">
        <div class="row justify-content-between align-items-center mb-3 my-4">
            <div class="col-auto">
                <h2>Create Eligibility Policies</h2>
            </div>
            <div class="col-auto">
                <input type="submit" name="submit" value="Save" class="btn btn-primary"/>
                <input type="submit" name="submit" value="Save And Request Approval" class="btn btn-primary"/>
                <button type="button" class="btn btn-secondary"><a href="../">Cancel</a></button>
            </div>
        </div>
        <form:input path ="id" type="text" hidden="true"/>
        <hr style="color:black"/>
        <div class="form-container">
            <div class="row mb-3">
                <div class="form-left col-md-6 mb-3">
                    <div class="mb-3">
                        <label>Policy Code<span class="mandatory">*</span></label>
                        <c:choose>
                            <c:when test="${parameters!=null}">
                                <form:input path ="policyCode" type="text" required="true" readOnly="true" class="form-control" style="background-color:LightGray;border:1px solid black"/>
                            </c:when>
                            <c:otherwise>
                                <form:input path ="policyCode" type="text" placeholder="Policy Code" class="form-control" required="true"/>
                                <form:errors path="policyCode" class="error"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="form-right col-md-6 mb-3">
                    <div class="mb-3">
                        <label>Policy Name<span class="mandatory">*</span></label>
                        <c:choose>
                            <c:when test="${parameters!=null}">
                                <form:input path ="policyName" type="text" required="true" readOnly="true" class="form-control" style="background-color:LightGray;border:1px solid black"/>
                            </c:when>
                            <c:otherwise>
                                <form:input path ="policyName" type="text" placeholder="Policy Name" class="form-control" required="true"/>
                                <form:errors path="policyName" class="error"/>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        <hr>
      <div class="container">
          <div id="add" style="float:right">Add Eligibility</div>
          <table id="tableBody" style="width:80%"class="table">
              <thead>
                  <tr>
                      <th>Parameter</th>
                      <th>Operator</th>
                      <th>Value</th>
                      <th>Remove</th>
                  </tr>
              </thead>
              <tbody>
                  <c:choose>
                      <c:when test="${parameters!=null}">
                          <c:forEach items="${parameters}" var="parameters">
                                <tr class="tr">
                                   <td>
                                       <select name="parameter" required class="form-control">
                                           <option value="${parameters.parameter}" selected>${parameters.parameter}</option>
                                           <c:forEach items="${listParameters}" var="parameter">
                                               <%--<c:if test="${parameters.parameter.equalsIgnoreCase(parameter.eligibilityParameterCode+'-'+parameter.eligibilityParameterName)}">--%>
                                                <option value="${parameter.eligibilityParameterCode}-${parameter.eligibilityParameterName}" >${parameter.eligibilityParameterCode}-${parameter.eligibilityParameterName}</option>
                                               <%--</c:if>--%>
                                           </c:forEach>
                                       </select>
                                   </td>
                                   <td>
                                       <select name="operator" required class="form-control">
                                           <option value="${parameters.operator}" selected>${parameters.operator}</option>
                                           <c:forEach items="${listOperators}" var="operator">
                                               <c:if test="${parameters.operator!=parameter.operator}">
                                                    <option value="${operator}">${operator}</option>
                                               </c:if>
                                           </c:forEach>
                                       </select>
                                   </td>
                                   <td><input type="number" value="${parameters.value}" name="value" class="form-control" required></td>
                                   <td><center><div class="remove">-</div></center></td>
                               </tr>
                          </c:forEach>
                      </c:when>
                      <c:otherwise>
                      <tr class="tr">
                          <td>
                              <select name="parameter" required class="form-control">
                                  <c:forEach items="${listParameters}" var="parameter">
                                      <option value="${parameter.eligibilityParameterCode}-${parameter.eligibilityParameterName}">${parameter.eligibilityParameterCode}-${parameter.eligibilityParameterName}</option>
                                  </c:forEach>
                              </select>
                          </td>
                          <td>
                              <select name="operator" required class="form-control">
                                  <c:forEach items="${listOperators}" var="operator">
                                      <option value="${operator}">${operator}</option>
                                  </c:forEach>
                              </select>
                          </td>
                          <td><input type="number" placeholder="Value" name="value" class="form-control" required></td>
                          <td><center><div class="remove">-</div></center></td>
                      </tr>
                      </c:otherwise>
                  </c:choose>
              </tbody>

          </table>
      </div>
        <hr>
      <div class="row mb-3">
        <div class="form-left col-md-6 mb-3">
            <div class="mb-3">
                <label>Eligibility Criteria: </label>
               <form:textarea path="eligibilityCriteria" id="eligibilityCriteria" class="form-control" placeholder="Enter eligibility Criteria" rows="5" onkeyup="updateCharacterCount()"/>
               <small id="char-count" class="form-text text-muted">Remaining characters: 100</small>
            </div>
        </div>
      </div>
        <hr>
      <form:input path ="flag" type="text" hidden="true"/>
      <form:input path ="metaData.createdBy" type="text" hidden="true"/>
      <form:input path ="metaData.authorizedBy" type="text" hidden="true"/>
      <form:input path ="metaData.modifiedBy" type="text" hidden="true"/>
      <form:input path ="metaData.creationDate" type="text" hidden="true"/>
      <form:input path ="metaData.modifiedDate" type="text" hidden="true"/>
      <form:input path ="metaData.authorizedDate" type="text" hidden="true"/>
  </form:form>
</div>
</body>
</html>