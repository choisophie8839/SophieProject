<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<main class="main">
<div class="grid">
	<h1>${title}</h1>
	<c:if test="${result eq 1}">
		<script type="text/javascript">
		//<!--
			alert("modify complete");
		//-->
		</script>
	</c:if>
</div>
</main>