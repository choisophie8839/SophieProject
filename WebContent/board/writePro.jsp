<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%-- <jsp:include page="/header.jsp" flush="false"/> --%>
<%@ include file="/header.jsp"%>
<h2>${title}</h2>

<c:if test="${result eq 0}">
		<script type="text/javascript">
			//<!--
				erroralert( inserterror );
			//-->
		</script>
</c:if>
<c:if test="${result eq 1}">
	<c:redirect url="BoardList.eh"/>
</c:if>
<c:if test="${fileresult eq 0}">
		<script type="text/javascript">
			//<!--
				erroralert( inserterror );
			//-->
		</script>
</c:if>
<c:if test="${fileresult ge 1}">
	<c:redirect url="BoardList.eh"/>
</c:if>
