<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ko">
<head>
    <!-- 메타 정보, 스타일 등 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/static/css/board/board2.css">
    <script src="//code.jquery.com/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function() {
            $("#navbar").load("${path}/resources/common_jsp/navbar.jsp");
        });
    </script>
</head>
<body>
<div id="navbar"></div>


<!-- 네비게이션 바 -->
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <nav class="navbar navbar-expand-lg navbar-light bg-light" id="boardnav">

                <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
                        aria-controls="navbarNav" aria-expanded="false" aria-label="">
                    <span class= "navbar-toggler-icon"></span>
                </button>
                <div class= "collapse navbar-collapse" id= "navbarNav">
                    <ul class= "navbar-nav mr-auto">
                        <li><a href= "popular">인기글</a></li>
                        <li><a href= "free?type=F">자유게시판</a></li>
                        <li><a href= "free?type=C">인증방</a></li>
                    </ul>

                    <!-- ml-auto 클래스로 오른쪽 정렬 -->
                    <ul class = "nav navbar-nav ml-auto ">

                        	<li><a href="/starroad/board/write"class ="btn btn-primary nav-link text-black">글쓰기</a></li>
                     </ul>
                 </div>
            </nav>
        </div>
    </div>
</div>

<!-- 게시판 내용 -->
<div id="boardcontent" class="menu-content2">
    <div class="row no-gutters">
        <c:forEach items="${freeBoardPage.content}" var="board">
            <div class="col-md-6">
                <div class="board-item">
                    <div class ="detailTypeStyle">
                        <a> ${board.detailType}</a>
                    </div>
                    <div class="titleStyle">
                    <a href="/starroad/board/detail?no=${board.no}">${board.title}</a>
                    </div>
                    <div class = "contentStyle">
                    <a>${board.content}</a>
                    </div>
                    <div class="idStyle">
                        <a> ID : ${board.member.id} </a>
                    </div>

                    <div class="icons">
                        <div class left="left-icons">
                        <i class="far fa-thumbs-up"></i> ${board.likes} <i class="far fa-comment"></i> ${board.commentNum}
                        </div>

                        <span class="timeStyle"><i class="far fa-calendar-alt"></i><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd" /></span>

                    </div>

                </div>
            </div>
        </c:forEach>
    </div>

    <nav  aria-label="Page navigation" id="btm_pagi2">
        <ul class="pagination justify-content-center">
            <c:if test="${freeBoardPage.totalPages > 1}">
                <c:if test="${freeBoardPage.number != 0}">
                    <li class="page-item">

                        <a class="page-link" href="?page=0&type=${type}" aria-label="처음">
                            <span aria-hidden="true">&laquo;&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="?page=${freeBoardPage.number - 1}&type=${type}" aria-label="이전">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach begin="0" end="${freeBoardPage.totalPages - 1}" varStatus="loop">
                    <li class="page-item ${loop.index == freeBoardPage.number ? 'active' : ''}">
                        <a class="page-link" href="?page=${loop.index}&type=${type}">${loop.index + 1}</a>
                    </li>
                </c:forEach>
                <c:if test="${freeBoardPage.number + 1 < freeBoardPage.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${freeBoardPage.number + 1}&type=${type}" aria-label="다음">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="?page=${freeBoardPage.totalPages - 1}&type=${type}" aria-label="끝">
                            <span aria-hidden="true">&raquo;&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </c:if>
        </ul>
    </nav>
</div>
<!-- 자유게시판 내용 -->
<div id="popular" class="menu-content">
    <div class="row no-gutters">
        <c:forEach items="${popularBoardPage.content}" var="board">
            <div class="col-md-6">
                <div class="board-item">
                    <div class ="detailTypeStyle">
                        <a> ${board.detailType}</a>
                    </div>
                    <div class="titleStyle">
                    <h3><a href="/starroad/board/detail?no=${board.no}">${board.title}</a></h3>
                    </div>
                    <div class = "contentStyle">
                    <a>${board.content}</a>
                    </div>
                    <div class="idStyle">
                        <a>ID :  ${board.member.id}</a>
                    </div>
                    <div class="icons">
                        <div class left="left-icons">
                            <i class="far fa-thumbs-up"></i> ${board.likes} <i class="far fa-comment"></i> ${board.commentNum}
                        </div>

                        <i class="timeStyle"><i class="far fa-calendar-alt"></i><fmt:formatDate value="${board.regdate}" pattern="yyyy-MM-dd" /></i>

                    </div>

                </div>
            </div>
        </c:forEach>
    </div>
    <nav aria-label="Page navigation" id="btm_pagi">
        <ul class="pagination justify-content-center">
            <c:if test="${popularBoardPage.totalPages > 1}">
                <c:if test="${popularBoardPage.number != 0}">
                    <li class="page-item">
                        <a class="page-link" href="?page=0&type=popular" aria-label="처음">
                            <span aria-hidden="true">&laquo;&laquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="?page=${popularBoardPage.number - 1}" aria-label="이전">
                            <span aria-hidden="true">&laquo;</span>
                        </a>
                    </li>
                </c:if>
                <c:forEach begin="0" end="${popularBoardPage.totalPages - 1}" varStatus="loop">
                    <li class="page-item ${loop.index == popularBoardPage.number ? 'active' : ''}">
                        <a class="page-link" href="?page=${loop.index}">${loop.index + 1}</a>
                    </li>
                </c:forEach>
                <c:if test="${popularBoardPage.number + 1 < popularBoardPage.totalPages}">
                    <li class="page-item">
                        <a class="page-link" href="?page=${popularBoardPage.number + 1}" aria-label="다음">
                            <span aria-hidden="true">&raquo;</span>
                        </a>
                    </li>
                    <li class="page-item">
                        <a class="page-link" href="?page=${popularBoardPage.totalPages - 1}" aria-label="끝">
                            <span aria-hidden="true">&raquo;&raquo;</span>
                        </a>
                    </li>
                </c:if>
            </c:if>
        </ul>
    </nav>
</div>


<script>
    function showContent(menu, type) {
        // 모든 메뉴 내용 숨기기
        document.querySelectorAll('.menu-content').forEach(function (content) {
            content.style.display = 'none';
        });

        // 선택한 메뉴 내용 표시
        var menuContent = document.getElementById(menu);
        menuContent.style.display = 'block';

        // AJAX 요청 URL 정의
        let url;
        if (menu === 'free') {
            url = '/starroad/board?type=' + type;
        } else if (menu === 'authentication') {
            url = '/starroad/board?type=' + type;
        } else if (menu === 'popular') {
            url = '/starroad/popular';
        }

        // AJAX 요청 시작
        $.ajax({
            url: url,
            method: 'GET',
            success: function (data) {
                var content = $(data).find('.board').html();
                menuContent.querySelector('.board').innerHTML = content;
            },
            error: function (error) {
                console.error('Failed to load content:', error);
            }
        });
    }


</script>
</body>
</html>