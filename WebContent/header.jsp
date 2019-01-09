<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/SophieProject/script.js"></script>

<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">

<!-- jQuery library -->
<script src="/SophieProject/jquery-3.3.1.js"></script>

<!-- Popper JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"></script>

<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"></script>

<link rel="stylesheet" type="text/css" href="theme.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.5.0/css/all.css" integrity="sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU" crossorigin="anonymous">
<header class="header sticky sticky--top js-header fixed-top">

  <div class="grid">

    <nav class="navigation">
      <ul class="navigation__list navigation__list--inline">
        <li class="navigation__item"><a href="Hi.eh" class="is-active">Home</a></li>
        <li class="navigation__item">
		  <div class="subnav">
		  
		    <button class="subnavbtn">Member<i class="fa fa-caret-down"></i></button>
		    <div class="subnav-content">
		      <a href="LoginForm.eh">LoginForm</a>
		      <a href="InputForm.eh">InputForm</a>
		      <a href="ModifyForm.eh">ModifyForm</a>
		      <a href="DeleteForm.eh">DeleteForm</a>
		      <c:if test="${sessionScope.memid ne null}">
		      	<a href="Logout.eh" onclick="logout()">Logout</a>     
		      </c:if>
		    </div>
		  </div> 
        </li>
        <li class="navigation__item">
		  <div class="subnav">
		    <button class="subnavbtn">Board<i class="fa fa-caret-down"></i></button>
		    <div class="subnav-content">
		      <a href="BoardList.eh">BoardList</a>
		      <a href="BoardWriteForm.eh">WriteForm</a>
		      <a href="BoardModifyForm.eh">ModifyForm</a>
		    </div>
		  </div> 
        </li>
        <li class="navigation__item"><a href="#">Album</a></li>
        <li class="navigation__item">
        	<div class="subnav">
        	<button class="subnavbtn">Film<i class="fa fa-caret-down"></i></button>
        	<div class="subnav-content">
        		<a href="DailyBoxOfficeList.eh">DailyBoxOfficeList</a>
        		<a href="MovieInfo.eh">MovieInfo</a>
        	</div>
        	</div>
      </ul>
    </nav>

  </div>

</header>