<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Verify Email</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="./css/other.css">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</head>

<body class="vh-100"
      style="background-image: url('./UPLOADED_FOLDER/newspaper-aesthetic-background-6zy4tj738voyh9fx.jpg');">
<div class="container h-100">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-lg-12 col-xl-11">
            <div class="card text-black shadow-lg" style="border-radius: 25px;">
                <div class="card-body p-md-5">
                    <div class="row justify-content-center">
                        <div class="col-md-10 col-lg-6 col-xl-5 order-2 order-lg-1 pt-3">
                            <div class="mx-1 mx-md-4 text-center">
                                <h1>Email Verification</h1>
                                <p>Please enter the verification code that was sent to your email address.</p>
                                <%--@elvariable id="user" type="com"--%>
                                <form:form action="/activateaccount" method="post" modelAttribute="user">
                                    <div class="mb-3">
                                        <form:label path="verificationCode" for="verification-code" class="form-label">Verification Code</form:label>
                                        <div class="input-group">
                                            <input type="text" class="form-control mx-2 rounded-3"
                                                   id="verification-code-1" name="verification_code[]" maxlength="1"
                                                   autofocus>
                                            <input type="text" class="form-control mx-2 rounded-3"
                                                   id="verification-code-2" name="verification_code[]" maxlength="1">
                                            <input type="text" class="form-control mx-2 rounded-3"
                                                   id="verification-code-3" name="verification_code[]" maxlength="1">
                                            <input type="text" class="form-control mx-2 rounded-3"
                                                   id="verification-code-4" name="verification_code[]" maxlength="1">
                                            <input type="text" class="form-control mx-2 rounded-3"
                                                   id="verification-code-5" name="verification_code[]" maxlength="1">
                                            <input type="text" class="form-control mx-2 rounded-3   "
                                                   id="verification-code-6" name="verification_code[]" maxlength="1">
                                            <form:input type="hidden" name="verificationCode" value="" id="result" path="verificationCode"></form:input>
                                        </div>
                                    </div>
                                    <form:errors path="verificationCode" cssClass="text-danger"></form:errors>
                                    <button type="submit" onclick="concat()" class="btn btn-primary">Verify Email</button>
                                </form:form>

                                <div class="mt-3">
                                    <p>If you did not receive the verification code, you can <a href="/resendemail">resend it</a>.</p>
                                </div>
                            </div>

                        </div>
                        <div class="col-md-10 col-lg-6 col-xl-7 d-flex align-items-center order-1 order-lg-2">

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
<script>
    // Get all of the input fields in the form.
    const inputFields = document.querySelectorAll('input[type="text"]');

    // Add an event listener to each input field.
    inputFields.forEach(inputField => {
        inputField.addEventListener('input', () => {
            // Get the next input field in the form.
            const nextInputField = inputField.nextElementSibling;

            // If the next input field exists, focus it.
            if (nextInputField) {
                nextInputField.focus();
            }
        });
    });
    function concat() {
        const inputFields = document.querySelectorAll('input[type="text"]');

        // Create a variable to store the concatenated value.
        let concatenatedValue = '';

        // Iterate over the input fields and concatenate their values.
        inputFields.forEach(inputField => {
            concatenatedValue += inputField.value;
        });

        // Set the value of the hidden field.
        const hiddenField = document.querySelector('#result');
        hiddenField.value = concatenatedValue;
    }
</script>
</body>

</html>