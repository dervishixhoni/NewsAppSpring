<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="base">
    <layout:put block="content">
        <div class="m-auto p-3" style="max-width: 540px;">
            <div class="input-group bg-light">
                <div class="form-outline border border-2 border-primary py-2">
                    <input id="searchbar" type="text" id="form1" class="form-control" />
                    <label class="form-label my-2" for="form1">Search</label>
                </div>
                <button type="button" onclick="getNews('search')" class="btn btn-primary">
                    <i class="fas fa-search"></i>
                </button>
            </div>
        </div>
        <div id="top-bar" class="scrollbar scrollmenu m-auto py-2 my-3 justify-content-center" style="max-width: 540px;">
            <c:forEach var="keyword" items="${keywords}">
                <button onclick="getNews(this)" class="btn btn-outline-primary mx-1 rounded-pill bg-light"
                    type="button">${keyword}</button>
            </c:forEach>
        </div>
        <div id="results">
            <c:forEach var="article" items="${articles}">
            <div class="d-flex justify-content-center">
                <div class="card mb-3 mx-3 my-3 shadow-lg" style="max-width: 540px;">
                    <div class="row no-gutters">
                        <div class="col-md-5 d-flex align-items-center justify-content-center">
                            <img src="${article.urlToImage}" class="card-img" alt="Article's image">
                        </div>
                        <div class="col-md-7 p-0">
                            <div class="card-body p-3">
                                <h5 class="card-title">${article.title}</h5>
                                <p class="card-text">${article.description}</p>
                                <p class="card-text"><small class="text-muted">${article.publishedAt}</small></p>
                                <div class="d-flex justify-content-evenly w-100">
                                    <a href="${article.url}" target="_blank" class="btn btn-primary">Read The Full Story</a>
                                    <c:choose>
                                        <c:when test="${savedArticles.contains(article.url)}">
                                            <form action="/unsaveArticle" method="post">
                                                <input type="hidden" name="title" value="${article.title}">
                                                <input type="hidden" name="url" value="${article.url}">
                                                <input type="submit" class="btn btn-success" value="Unsave">
                                            </form>
                                        </c:when>
                                        <c:otherwise>
                                            <form action="/saveArticle" method="post">
                                                <input type="hidden" name="title" value="${article.title}">
                                                <input type="hidden" name="url" value="${article.url}">
                                                <input type="submit" class="btn btn-outline-success" value="Save">
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </c:forEach>
        </div>
        <script>
            var input = document.getElementById('searchbar')
            input.addEventListener("keydown",function(e){
                if (e.keyCode === 13) {
                    getNews('search')
                }
            })
            async function getNews(e) {
                const buttons = document.querySelectorAll(".btn.btn-outline-primary.mx-1.rounded-pill");
                if (e == 'search') {
                    query = document.getElementById('searchbar').value;
                    document.getElementById('searchbar').value=''
                }
                else {

                    const buttons = document.querySelectorAll(".btn.btn-outline-primary.mx-1.rounded-pill");

                    buttons.forEach(button => {
                        button.addEventListener('click', () => {
                            // Enable the clicked button
                            button.classList.add('active');
                            button.classList.remove('bg-light');

                            // Disable all other buttons
                            buttons.forEach(otherButton => {
                                if (otherButton !== button) {
                                    otherButton.classList.remove('active');
                                    otherButton.classList.add('bg-light');
                                }
                            });
                        });
                    });
                    query = e.innerText
                }

                var url = "https://newsapi.org/v2/everything?q="+query+"&language=en&apiKey=bf1cb8dc1df74505a310b9dc5301942e";
                const response = await fetch(url);
                console.log(response);
                // Check the response status code.
                if (response.status !== 200) {
                    throw new Error("Failed to query News API: " + response.status);
                }

                // Parse the JSON response.
                const data = await response.json();

                // Get the articles.
                const articles = data.articles;
                const savedArt = {{ savedArticles | d([]) | tojson | safe}}

                // Display the articles on the page.
                const resultsContainer = document.querySelector("#results");
                resultsContainer.innerHTML = "";
                // Display the articles once the response has been received.
                for (const article of articles) {
                    const articleElement = document.createElement("article");
                    if (article['title'] != '[Removed]' && article['urlToImage'] != null) {

                        if (savedArt.includes(article['url'])) {
                            articleElement.innerHTML =
                                `<div class="d-flex justify-content-center">
                        <div class="card mb-3 mx-3 my-3 shadow-lg" style="max-width: 540px;">
                            <div class="row no-gutters">
                                <div class="col-md-5 d-flex align-items-center justify-content-center">
                                    <img src="${article['urlToImage']}" class="card-img" alt="Article's image">
                                </div>
                                <div class="col-md-7 p-0">
                                    <div class="card-body p-3">
                                        <h5 class="card-title">${article['title']}</h5>
                                        <p class="card-text">${article['description']}</p>
                                        <p class="card-text"><small class="text-muted">${article['publishedAt']}</small></p>
                                        <div class="d-flex justify-content-evenly w-100">
                                            <a href="${article['url']}" target="_blank" class="btn btn-primary">Read The Full Story</a>
                                            <form action="/unsaveArticle" method="post">
                                                <input type="hidden" name="title" value="${article['title']}">
                                                <input type="hidden" name="url" value="${article['url']}">
                                                <input type="submit" class="btn btn-success" value="Unsave">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>`;
                        }
                        else {
                            articleElement.innerHTML = `
            <div class="d-flex justify-content-center">
                <div class="card mb-3 mx-3 my-3 shadow-lg" style="max-width: 540px;">
                    <div class="row no-gutters">
                        <div class="col-md-5 d-flex align-items-center justify-content-center   ">
                            <img src="${article['urlToImage']}" class="card-img" alt="Article's image">
                        </div>
                        <div class="col-md-7 p-0">
                            <div class="card-body p-3">
                                <h5 class="card-title">${article['title']}</h5>
                                <p class="card-text">${article['description']}</p>
                                <p class="card-text"><small class="text-muted">${article['publishedAt']}</small></p>
                                <div class="d-flex justify-content-evenly w-100">
                                    <a href="${article['url']}" target="_blank" class="btn btn-primary">Read The Full Story</a>
                                    <form action="/saveArticle" method="post">
                                        <input type="hidden" name="title" value="${article['title']}">
                                        <input type="hidden" name="url" value="${article['url']}">
                                        <input type="submit" class="btn btn-outline-success" value="Save">
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>`;
                        }

                        resultsContainer.appendChild(articleElement);
                    }
                }
            }
        </script>
    </layout:put>
</layout:extends>