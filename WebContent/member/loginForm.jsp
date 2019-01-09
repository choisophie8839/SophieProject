<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<main class="main">
<div class="grid">
<h1>${title}</h1>
<c:if test="${sessionScope.memid ne null}">
<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<h2>Welcome ${sessionScope.memid}</h2>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
</c:if>

<c:if test="${sessionScope.memid eq null}">
<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<br>
			<h2>Member LoginForm</h2>
			<div class="tabs">
	            <span class="tab">LOG IN</span>
	            <span class="tab"><a href="InputForm.eh">Sign up</a></span>
	        </div>
	        <form action="LoginPro.eh" method="post">
	            <input type="text" name="id" id="id" class="inpt" required="required" placeholder="Your id">
	            <input type="password" name="passwd" id="passwd" class="inpt" required="required" placeholder="Your password">
	            <button type="submit" value="Sign in" class="butn">LOG IN</button>
      		</form>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
</c:if>

</div>
</main>