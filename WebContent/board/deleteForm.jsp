<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<main class="main">
<div class="grid">
<h1>${title}</h1>
<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<br>
			<h2>Board DeleteForm</h2>
			<div class="tabs">
	            <span class="tab">PASSWORD CHECK</span>
	        </div>
	        <form action="BoardDeletePro.eh" method="post">
	        <input type="hidden" name="num" value="${num}">
	        <input type="hidden" name="pageNum" value="${pageNum}">
	            <input type="password" name="passwd" id="passwd" class="inpt" required="required" placeholder="Your password">
	            <button type="submit" value="Sign in" class="butn">SUBMIT</button>
      		</form>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
	
</div>
</main>