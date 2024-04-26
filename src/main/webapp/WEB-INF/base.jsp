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
    <title>News App</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/6.4.2/mdb.min.css" rel="stylesheet" />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
    <script src="https://kit.fontawesome.com/97eca8769e.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="./css/other.css">
</head>

<body style="background-image: url('./UPLOADED_FOLDER/1000_F_300852306_pBgkn3EmALrtLLTSwCACu1yjIdjXy99d.jpg');">
<nav class="navbar navbar-light bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="/">
            <svg xmlns="http://www.w3.org/2000/svg" height="2em"
                 viewBox="0 0 512 512"><!--! Font Awesome Free 6.4.2 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2023 Fonticons, Inc. -->
                <style>
                    svg {
                        fill: #f8f9fa
                    }
                </style>
                <path
                        d="M168 80c-13.3 0-24 10.7-24 24V408c0 8.4-1.4 16.5-4.1 24H440c13.3 0 24-10.7 24-24V104c0-13.3-10.7-24-24-24H168zM72 480c-39.8 0-72-32.2-72-72V112C0 98.7 10.7 88 24 88s24 10.7 24 24V408c0 13.3 10.7 24 24 24s24-10.7 24-24V104c0-39.8 32.2-72 72-72H440c39.8 0 72 32.2 72 72V408c0 39.8-32.2 72-72 72H72zM176 136c0-13.3 10.7-24 24-24h96c13.3 0 24 10.7 24 24v80c0 13.3-10.7 24-24 24H200c-13.3 0-24-10.7-24-24V136zm200-24h32c13.3 0 24 10.7 24 24s-10.7 24-24 24H376c-13.3 0-24-10.7-24-24s10.7-24 24-24zm0 80h32c13.3 0 24 10.7 24 24s-10.7 24-24 24H376c-13.3 0-24-10.7-24-24s10.7-24 24-24zM200 272H408c13.3 0 24 10.7 24 24s-10.7 24-24 24H200c-13.3 0-24-10.7-24-24s10.7-24 24-24zm0 80H408c13.3 0 24 10.7 24 24s-10.7 24-24 24H200c-13.3 0-24-10.7-24-24s10.7-24 24-24z" />
            </svg>
        </a>
        <h1 class="text-center text-light">
            News App
        </h1>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <img class="profile-pic" src="./UPLOADED_FOLDER/defaultprofilepic.jpg" alt="">
        </button>
        <div class="collapse navbar-collapse text-light" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0 ">
                <c:choose>
                    <c:when test="${empty user}">
                    <li class="nav-item text-center">
                        <a class="nav-link text-light" href="/profile/${user.id}">Profile</a>
                    </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item text-center">
                            <a class="nav-link text-light" href="/dashboard">Dashboard</a>
                        </li>
                    </c:otherwise>
                </c:choose>
                <li>
                    <hr class="dropdown-divider">
                </li>
                <li class="nav-item text-center">
                    <a class="nav-link text-light" href="/logout">Log Out</a>
                </li>
            </ul>
        </div>
    </div>
</nav>
<layout:block name="content">
</layout:block>
</body>
<footer class="text-center">
    <!-- Grid container -->
    <div class="container p-4">
        <!-- Linkedin -->
        <a class="btn text-white btn-floating m-1" style="background-color: #0082ca;"
           href="https://www.linkedin.com/in/xhoni-d%C3%ABrvishi-1a5844293/" role="button"><i
                class="fab fa-linkedin-in"></i></a>
        <!-- Github -->
        <a class="btn text-white btn-floating m-1" style="background-color: #333333;"
           href="https://github.com/dervishixhoni" role="button"><i class="fab fa-github"></i></a>
    </div>
    <!-- Grid container -->

    <!-- Copyright -->
    <div class="text-center p-3 bg-primary text-light">
        © 2023 Copyright:
        <a class="text-white" href="https://github.com/dervishixhoni/SoloProjectNewsApp"> News App</a>
    </div>
    <!-- Copyright -->
</footer>
</html>