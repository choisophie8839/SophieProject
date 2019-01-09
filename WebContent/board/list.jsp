<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="/header.jsp"%>

<main class="main">

  <div class="grid">
<h1>${title}</h1>
    <h2> Board List ( 전체글 : ${count} ) </h2>
	<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="table-responsive">
        <table summary="This table shows how to create responsive tables using Bootstrap's default functionality" class="table table-bordered table-hover">
<c:if test="${count ne 0}">
	<caption class="text-center">
		<c:if test="${startPage gt pageBlock}">
			<a href="BoardList.eh">&nbsp;<i class="fas fa-angle-double-left"></i>&nbsp;</a>
			<a href="BoardList.eh?pageNum=${startPage-pageBlock}">&nbsp;<i class="fas fa-angle-left"></i>&nbsp;</a>
		</c:if>
		<c:forEach	var="i" begin="${startPage}" end="${endPage}">
			<c:if test="${i eq currentPage}">
				&nbsp;<b>${i}</b>&nbsp;
			</c:if>
			<c:if test="${i ne currentPage}">
				&nbsp;<a href="BoardList.eh?pageNum=${i}">${i}</a>&nbsp;
			</c:if>
		</c:forEach>
		<c:if test="${pageCount gt endPage}">
			<a href="BoardList.eh?pageNum=${startPage+pageBlock}">&nbsp;<i class="fas fa-angle-right">&nbsp;</i></a>
			<a href="BoardList.eh?pageNum=${pageCount}">&nbsp;<i class="fas fa-angle-double-right"></i>&nbsp;</a>
		</c:if>
	</caption>
</c:if>
          <thead class="text-center">
            <tr>
              <th style="width : 6%">번호</th>
              <th style="width : 15%">영화</th>
              <th>글제목</th>
              <th style="width : 6%">평점</th>
              <th style="width : 10%">작성자</th>
              <th style="width : 15%">작성일</th>
              <th style="width : 7%">조회수</th>
            </tr>
          </thead>
          <tbody>
<c:if test="${count eq 0}">
		<tr>
			<td colspan="7" align="center">
				"작성 된 글이 없습니다."
			</td>
		</tr>
</c:if>
<c:if test="${count ne 0}">
	<c:forEach var="article" items="${articles}">
		<tr>
			<td align="center">
				${number}
				<c:set var="number" value="${number-1}"/>
			</td>
			<td>
				${article.movienm}
			</td>
			<td>
			<!--답글의 답글이면-->
			<c:if test="${article.re_level gt 1}">
				<c:set var="wid" value="${(article.re_level-1)*10}"/>
					<i class="fas fa-angle-right"></i>
			</c:if>
			<!--답글이면 -->
			<c:if test="${article.re_level gt 0}">		
				<i class="fab fa-replyd"></i>
			</c:if>			
			<a href="BoardContent.eh?num=${article.num}&pageNum=${pageNum}&number=${number+1}">
				${article.subject}
			</a>
			<c:if test="${article.readcount gt 5}">
				<i class="fas fa-crown"></i>
			</c:if>
			</td>
			<td align="center">
				${article.score}
			</td>
			<td align="center">
				${article.id}
			</td>
			<td align="center">
				<fmt:formatDate value="${article.reg_date}" pattern="yyyy-MM-dd HH:mm"/>
			</td>
			<td align="center">
				${article.readcount}
			</td>
		</tr>
	</c:forEach>
</c:if>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="7" class="text-right"><a href="BoardWriteForm.eh">글작성</a></td>
            </tr>
          </tfoot>
        </table>
      </div><!--end of .table-responsive-->
    </div>
  </div>
</div>

  </div>

</main>