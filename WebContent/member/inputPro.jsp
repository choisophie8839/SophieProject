<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

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
	<c:redirect url="LoginForm.eh"/>
</c:if>
</div>
</main>