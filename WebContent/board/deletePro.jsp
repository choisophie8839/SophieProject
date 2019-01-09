<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<main class="main">
<div class="grid">
<h1>${title}</h1>
	<c:if test="${result eq 1}">
		<script type="text/javascript">
			alert("delete complete");
		</script>
	</c:if>
	<c:if test="${result eq -1}">
		<script type="text/javascript">
			alert("delete fail; You have reply!!");
		</script>	
	</c:if>
	<c:if test="${result eq 0}">
		<script type="text/javascript">
			alert("Wrong passwd!");
		</script>
	</c:if>
	<c:if test="${result eq 2}">
		<script type="text/javascript">
			alert("can't delete others' article!");
		</script>
	</c:if>
	<meta http-equiv="refresh" content="0; url=BoardList.eh?pageNum=${pageNum}">
</div>
</main>