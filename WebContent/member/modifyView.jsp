<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<script src="/SophieProject/script.js"></script>

<main class="main">
<div class="grid">
<h1>${title}</h1>
<c:if test="${result eq 0}">
	<script type="text/javascript">
	<!--
		erroralert( inserterror );
	//-->
	</script>	
</c:if>
<c:if test="${result ne 0}">
	<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<br>
			<h2>Member ModifyView</h2>
			<div class="tabs">
	            <span class="tab">MODIFY</span>
	        </div>
	        <form action="ModifyPro.eh" method="post">
	            <input type="text" name="id" id="id" class="inpt" required="required" placeholder="Your id" value="${memberDto.id}" disabled>
	            <input type="password" name="passwd" id="passwd" class="inpt" required="required" placeholder="Your password" value="${memberDto.passwd}">
	            <input type="password" name="repasswd" id="repasswd" class="inpt" required="required" placeholder="Your repassword" value="${memberDto.passwd}">
	            <input type="email" name="email" id="email" class="inpt" required="required" placeholder="Your email" value="${memberDto.email}">
	            <input type="tel" name="tel" id="tel" class="inpt" required="required" placeholder="Your telephone number" value="${memberDto.tel}">
	            <input type="date" name="birth" id="birth" class="inpt" required="required" placeholder="Your birth date" value="${memberDto.birth}" disabled>
	            	<c:if test="${memberDto.gender eq 'F'.charAt(0)}">
	            	<label class="radio-inline">
		            	Gender : &nbsp;
		            	<input type="radio" name="gender" value="F" checked> Female
		            </label>
					&nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="gender" value="M" disabled> Male
		            </label>
	            	</c:if>
	            	<c:if test="${memberDto.gender eq 'M'.charAt(0)}">
		            	Gender : &nbsp;
		            	<input type="radio" name="gender" value="F" disabled> Female
		            </label>
					&nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="gender" value="M" checked> Male
		            </label>	            	
	            	</c:if>
		       <input type="text" name="address1" id="address1" class="inpt" required="required" placeholder="Your address1" value="${memberDto.address1}" onclick="searchPostcode()">
		       <input type="text" name="address2" id="address2" class="inpt" required="required" placeholder="Your address2" value="${memberDto.address2}">
	           <button type="submit" class="butn">MODIFY</button>
      		</form>
		</div>
		<div class="col-md-3"></div>
	</div>
</div>
</c:if>
</div>
</main>