<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/movie/movie.css">
	<script src="${pageContext.request.contextPath}/js/movie/movie.js?v=<%=System.currentTimeMillis()%>" defer></script>
</head>
<body>
	<div class="movieContainer">
	<header>
		<h3 class="jt">
			<span class="jt__row">
			    <span class="jt__text">SoeulIT Movie Theater</span>
			  </span>
			  <span class="jt__row jt__row--sibling" aria-hidden="true">
			    <span class="jt__text">SoeulIT Movie Theater</span>
			  </span>
			  <span class="jt__row jt__row--sibling" aria-hidden="true">
			    <span class="jt__text">SoeulIT Movie Theater</span>
			  </span>
			  <span class="jt__row jt__row--sibling" aria-hidden="true">
			    <span class="jt__text">SoeulIT Movie Theater</span>
		  </span>
		</h3>
		<form id='form'>
			<input type="text" placeholder="Search" id="search" class="search"/>
		</form>
	</header>
	<main id="main">
	</main>
	</div>
</body>
</html>