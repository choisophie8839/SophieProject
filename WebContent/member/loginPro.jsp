<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>

<main class="main">
<div class="grid">
<h1>${title}</h1>
<c:if test="${result eq 1}">
	<div class="container">
	<div class="row">
		<div class="col-md-3"></div>
		<div class="col-md-6">
			<h2>Welcome ${sessionScope.memid = id}</h2>
		</div>
		<div class="col-md-3"></div>
	</div>
	</div>
	<script type="text/javascript">
	//<!--
		var memid = '${id}';
		sessionStorage.setItem('memid', memid );
	//-->
	</script>
</c:if>

<c:if test="${result lt 0}">
	<script type="text/javascript">
	//<!--
		alert("wrong password");
		window.location.href = 'Hi.eh';
	//-->
	</script>
</c:if>
<c:if test="${result eq 0}">
	<script type="text/javascript">
	//<!--
		alert("no id");
		window.location.href = 'Hi.eh';
	//-->
	</script>
</c:if>
</div>
</main>