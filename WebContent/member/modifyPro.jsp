<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<script src="/SophieProject/script.js"></script>

<main class="main">
<div class="grid">
<h1>${title}</h1>
<c:if test="${result eq 0}">
	<script type="text/javascript">
	//<!--
		alert('updateerror');
	//-->
	</script>
</c:if>
<c:if test="${result ne 0}">
	<c:redirect url="Hi.eh"/>
</c:if>
</div>
</main>