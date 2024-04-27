<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body class="vh-100"
      style="background-image: url('./UPLOADED_FOLDER/newspaper-aesthetic-background-6zy4tj738voyh9fx.jpg');"
>
<div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-lg-12 col-xl-11">
            <div class="card text-black" style="border-radius: 25px;">
                <div class="card-body p-md-5">
                    <nav>
                        <div class="nav nav-tabs nav-justified " id="nav-tab" role="tablist">
                            <button class="nav-link active p-3" id="nav-signup-tab" data-bs-toggle="tab"
                                    data-bs-target="#nav-signup" type="button" role="tab" aria-controls="nav-signup"
                                    aria-selected="true">
                                <h1>Sign Up</h1>
                            </button>
                            <button class="nav-link p-3" id="nav-login-tab" data-bs-toggle="tab"
                                    data-bs-target="#nav-login" type="button" role="tab" aria-controls="nav-login"
                                    aria-selected="false">
                                <h1>Log In</h1>
                            </button>
                        </div>
                    </nav>
                    <div class="tab-content">
                        <div class="tab-pane fade show active" id="nav-signup" role="tabpanel"
                             aria-labelledby="nav-signup-tab">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1 pt-3">
<%--                                    &lt;%&ndash;@elvariable id="user" type="com"&ndash;%&gt;--%>
<%--                                    <form:form action="/register" method="post" class="mx-1 mx-md-4"--%>
<%--                                               id="formReg" modelAttribute="user">--%>

<%--                                    <div class="d-flex flex-row align-items-center mb-4">--%>
<%--                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>--%>
<%--                                        <div class="form-outline flex-fill mb-0 form-floating">--%>
<%--                                            <form:input type="text" id="form3Example1c" class="form-control"--%>
<%--                                                        placeholder="name@example.com" path="firstName"></form:input>--%>
<%--                                            <form:label class="form-label" for="form3Example1c"--%>
<%--                                                        path="firstName">First Name</form:label>--%>
<%--                                            <form:errors path="firstName" cssClass="text-danger"></form:errors>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>

<%--                                    <div class="d-flex flex-row align-items-center mb-4">--%>
<%--                                        <i class="fas fa-user fa-lg me-3 fa-fw"></i>--%>
<%--                                        <div class="form-outline flex-fill mb-0 form-floating">--%>
<%--                                            <form:input type="text" id="form3Example1c" class="form-control"--%>
<%--                                                        placeholder="name@example.com" path="lastName"></form:input>--%>
<%--                                            <form:label path="lastName" class="form-label"--%>
<%--                                                        for="form3Example1c">Last Name</form:label>--%>
<%--                                            <form:errors path="lastName" cssClass="text-danger"></form:errors>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>

<%--                                    <div class="d-flex flex-row align-items-center mb-4">--%>
<%--                                        <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>--%>
<%--                                        <div class="form-outline flex-fill mb-0 form-floating">--%>
<%--                                            <form:input type="email" class="form-control"--%>
<%--                                                        placeholder="name@example.com" path="email"></form:input>--%>
<%--                                            <form:label path="email" class="form-label"--%>
<%--                                                        for="form3Example3c">Email</form:label>--%>
<%--                                            <form:errors path="email" cssClass="text-danger"></form:errors>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>

<%--                                    <div class="d-flex flex-row align-items-center mb-4">--%>
<%--                                        <i class="fas fa-lock fa-lg me-3 fa-fw"></i>--%>
<%--                                        <div class="form-outline flex-fill mb-0 form-floating">--%>
<%--                                            <form:input type="password" id="form3Example4c" class="form-control"--%>
<%--                                                        path="password"></form:input>--%>
<%--                                            <form:label path="password" class="form-label"--%>
<%--                                                        for="form3Example4c">Password</form:label>--%>
<%--                                            <form:errors path="password" cssClass="text-danger"></form:errors>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>

<%--                                    <div class="d-flex flex-row align-items-center mb-4">--%>
<%--                                        <i class="fas fa-key fa-lg me-3 fa-fw"></i>--%>
<%--                                        <div class="form-outline flex-fill mb-0 form-floating">--%>
<%--                                            <form:input path="passwordConfirmation" type="password" id="form3Example4cd" class="form-control"></form:input>--%>
<%--                                            <form:label path="passwordConfirmation" class="form-label" for="form3Example4cd">Confirm password</form:label>--%>
<%--                                            <form:errors path="passwordConfirmation" cssClass="text-danger"></form:errors>--%>
<%--                                        </div>--%>
<%--                                    </div>--%>


<%--                                    <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">--%>
<%--                                        <button type="submit" class="btn btn-primary btn-lg">Register</button>--%>
<%--                                    </div>--%>
<%--                                    </form:form>--%>
                                </div>
                                <div
                                        class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                         class="img-fluid" alt="Sample image">

                                </div>
                            </div>
                        </div>
                        <div class="tab-pane fade" id="nav-login" role="tabpanel" aria-labelledby="nav-login-tab">
                            <div class="row justify-content-center">
                                <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-2 pt-3">
                                    <%--@elvariable id="loginUser" type="com"--%>
                                    <form:form action="/login" method="post" class="mx-1 mx-md-4"
                                          id="formLog" modelAttribute="loginUser">

                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-envelope fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0 form-floating">
                                                <form:input type="email" id="form3Example3c" class="form-control"
                                                            path="email" placeholder="name@example.com" ></form:input>
                                                <form:label path="email" class="form-label" for="form3Example3c">Email</form:label>
                                                <form:errors path="email" cssClass="text-danger"></form:errors>
                                            </div>
                                        </div>

                                        <div class="d-flex flex-row align-items-center mb-4">
                                            <i class="fas fa-lock fa-lg me-3 fa-fw"></i>
                                            <div class="form-outline flex-fill mb-0 form-floating">
                                                <form:input type="password" class="form-control"
                                                            path="password" ></form:input>
                                                <form:label path="password" class="form-label" for="form3Example4c">Password</form:label>
                                                <form:errors path="password" cssClass="text-danger"></form:errors>
                                            </div>
                                        </div>


                                        <div class="d-flex justify-content-center mx-4 mb-3 mb-lg-4">
                                            <button type="submit" class="btn btn-primary btn-lg">Log In</button>
                                        </div>
                                    </form:form>

                                </div>
                                <div
                                        class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-1">

                                    <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-registration/draw1.webp"
                                         class="img-fluid" alt="Sample image">

                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>