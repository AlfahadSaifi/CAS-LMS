

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>update Eligibility Parameter</title>
    <style>

    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow">

    <form:form action="${pageContext.request.contextPath}/maker/update-paramater-per" method="post" modelAttribute="eligibilityParameterPer">
        <div class="row justify-content-between align-items-center mb-3 my-4">
            <div class="col-auto">
                <h2>Update Eligibility Parameter</h2>
            </div>
            <div class="col-auto">
                <input type="submit" value="Update"  class="btn btn-primary" />
                <input type="button" value="Cancel" class="btn btn-secondary" onclick="window.history.back();" />
            </div>
        </div>
                    <hr>

        <div class="form-container" >
            <div class="row mb-3">
            <form:hidden id="eligibilityParameterId" path="eligibilityParameterId"   /><br/>
                <div class="form-left col-md-6 mb-3">
                    <div class="mb-3">
                        <label for="eligibilityParameterCode" >Eligibility Parameter Code:</label><br/>
                        <form:input id="eligibilityParameterCode" path="eligibilityParameterCode"  readOnly="true" style="background-color: lightgray; border:1px;"/><br/>
                    </div>
                    <div class="mb-3">
                        <label for="eligibilityParameterDescription">Eligibility Parameter Description:</label><br/>
                        <form:input id="eligibilityParameterDescription" path="eligibilityParameterDescription"  />
                    </div>
                </div>

                <div class="form-right col-md-6 mb-3">
                    <div class="mb-3">
                        <label for="eligibilityParameterName" >Eligibility Parameter Name:</label><br/>
                        <form:input id="eligibilityParameterName" path="eligibilityParameterName"  readOnly="true" style="background-color: lightgray; border:1px;"/>
                    </div>
                </div>
            </div>
         </div>
    </form:form>
</div>
</body>
</html>
