<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>   
<%@ include file="/header.jsp"%>
<script src="/SophieProject/script.js"></script>

<main class="main">
<div class="grid">
<h1>${title}</h1>
<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<br>
			<h2>Member InputForm</h2>
			<div class="tabs">
	            <span class="tab"><a href="LoginForm.eh">LOG IN</a></span>
	            <span class="tab">SIGN UP</span>
	        </div>
	        <form action="InputPro.eh" method="post">
	            <input type="text" name="id" id="id" class="inpt" required="required" placeholder="Your id">
	            <input type="password" name="passwd" id="passwd" class="inpt" required="required" placeholder="Your password">
	            <input type="password" name="repasswd" id="repasswd" class="inpt" required="required" placeholder="Your repassword">
	            <input type="email" name="email" id="email" class="inpt" required="required" placeholder="Your email">
	            <input type="tel" name="tel" id="tel" class="inpt" required="required" placeholder="Your telephone number">
	            <input type="date" name="birth" id="birth" class="inpt" required="required" placeholder="Your birth date">
	            <label class="radio-inline">
		            	Gender : &nbsp;
		                <input type="radio" name="gender" value="F"> Female
		            </label>
					&nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="gender" value="M"> Male
		            </label>
		       <!-- 다음 지도 api 사용할 것!-->
		       <input type="text" name="address1" id="address1" class="inpt" required="required" placeholder="Your address1" onclick="searchPostcode()">
		       <input type="text" name="address2" id="address2" class="inpt" required="required" placeholder="Your address2">
	           <button type="submit" class="butn">SIGN UP</button>
      		</form>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
	
</div>
</main>