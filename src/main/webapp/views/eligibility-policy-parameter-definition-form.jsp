

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Add Eligibility Parameter</title>
    <style>

    </style>
</head>
<body>
<%@ include file="navbar.jsp"%>
<div class="container my-4 card shadow">
    <form:form action="${pageContext.request.contextPath}/maker/save-paramater" method="post" modelAttribute="eligibilityParameter">
    <div class="row justify-content-between align-items-center mb-3 my-4">
        <div class="col-auto">
            <h2>Add Eligibility Parameter</h2>
        </div>
        <div class="col-auto">
            <input type="submit" value="Save"  name = "save" class="btn btn-primary"/>
            <input type="submit" value="Save & Request Approval" name = "save" class="btn btn-primary" />
            <input type="button" value="Cancel" class="btn btn-secondary" onclick="window.history.back();" />
        </div>
    </div>
    <hr>


    <div class="form-container" >
        <div class="row mb-3">
            <div class="form-left col-md-6 mb-3">
                <div class="mb-3">
                    <label for="eligibilityParameterCode" >Eligibility Parameter Code: <span style="color: red;">*</span></label><br/>
                    <form:input path="eligibilityParameterCode" id="eligibilityParameterCode" required="true" style="margin-bottom: 10px;" class="form-control"/><br/>
                    <form:errors path="eligibilityParameterCode" style="color: red"/>
                </div>
                <div class="mb-3">
                    <label for="eligibilityParameterDescription">Eligibility Parameter Description:</label><br/>
                    <form:input path="eligibilityParameterDescription" id="eligibilityParameterDescription" class="form-control"/>

                </div>
            </div>

            <div class="form-right col-md-6 mb-3">
                <div class="mb-3">
                    <label for="eligibilityParameterName" >Eligibility Parameter Name:<span style="color: red;">*</span></label><br/>
                    <form:input path="eligibilityParameterName" id="eligibilityParameterName" required="true" class="form-control"/>
                    <form:errors path="eligibilityParameterName" style="color: red"/>

                </div>
            </div>
        </div>
    </div>
    <br/>

    </form:form>

    <h2 style="color:red">${message}</h2>
</div>
</body>
</html>
