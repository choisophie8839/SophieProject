<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<script src="/SophieProject/script.js"></script>

<main class="main">
<div class="grid">
<h1>${title}</h1>
	<c:if test="${result eq 1}">
		<script type="text/javascript">
			alert("delete complete");
		</script>
		<meta http-equiv="refresh" content="0; url=Hi.eh">
	</c:if>
	<c:if test="${result eq -1}">
		<script type="text/javascript">
			alert("delete fail");
		</script>		
	</c:if>
	
</div>
</main>