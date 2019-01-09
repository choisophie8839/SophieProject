<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<script src="/SophieProject/script.js"></script>

<script type="text/javascript">
//<!--
	function showcomment() { 
		var commentdata = new Object();
		commentdata.re_f = '${boardDto.re_f}';
		commentdata.re_step = '${boardDto.re_step}';
		commentdata.re_type = 1;
		commentdata = JSON.stringify(commentdata);
		$.ajax({
			async : true,
			type : 'POST',
			data : commentdata,
			url : "showComment.eh",
			dataType : "json",
			success : function(data) {
				if( data == 'no comment') {
					console.log(data);
				}else if(data != 'no comment'){
					var jsoncomments = JSON.parse(data);
					var myHTMLStr='<div class="row"><div class="col-md-12"><div class="table-responsive"><table class="table table-bordered"><tbody>';
					for( var i in jsoncomments) {
						myHTMLStr += '<tr><td><b>' + jsoncomments[i]['id'] +'</b>'+'<div style="float:right;"><a id="commentdelete" name="'+ jsoncomments[i]['num']+','+ jsoncomments[i]['id'] +'" href="#">삭제</a>&nbsp;&nbsp;<a href="#" id="commentmodify" name="'+ jsoncomments[i]['num']+','+ jsoncomments[i]['id']+'">수정</a></div>'+
									'<br><pre>' + jsoncomments[i]['content'] + '</pre>'+'<div style="float:right;font-size:0.8em;color:darkgray;">' + jsoncomments[i]['reg_date'] + '</div>'+
									'</td></tr></tbody>';	
					}
				}
				$('#commentlist').html(myHTMLStr);
			},
			error : function(error) {
				alert("error : " + error);
			}
		});
	}
	
	function addpost() {
		var reg_date = $('#commentform [name="reg_date"]').val();
		if( reg_date == '' || reg_date == null) {		
			var commentform = new Object();
			commentform.num = $('#commentform [name="num"]').val();
			commentform.id = $('#commentform [name="memid"]').val();
			commentform.re_f = $('#commentform [name="re_f"]').val();
			commentform.re_step = $('#commentform [name="re_step"]').val();
			commentform.re_type = 1;
			commentform.content = $('#commentform [name="commentcontent"]').val();
			commentform.movienm = $('#commentform [name="movienm"]').val();	
			commentform = JSON.stringify(commentform);
		 		$.ajax({
					async : true,
					type : 'POST',
					data : commentform,
					url : "addPost.eh",
					dataType : "json",
					success : function(data) {
						alert('post success');
						showcomment();
						$('textarea[name="commentcontent"]').val("");
					},
					error : function(error) {
						alert("error : " + error);
					}
				});			
		} else {
			var commentform = new Object();
			commentform.num = $('#commentform [name="num"]').val();
			commentform.content = $('textarea[name="commentcontent"]').val();
			commentform = JSON.stringify(commentform);
			$.ajax({
				async : true,
				type : 'POST',
				data : commentform,
				url : "commentModify.eh",
				dataType : "json",
				success : function(data) {
					if( data === 1 ){
						alert('modify success');
						location.reload();
					}	
				},
				error : function(error) {
					alert("error : " + error);
				}
			});	
		}

	}
	
	$(document).ready(
		showcomment()
	);
	
	$(document).on("click","#commentdelete", function(e){
		var targetnum = e.target.name;
		var targetarray = targetnum.split(",", 2);
		//alert(targetarray[0] + " : " + targetarray[1]);
		var id = sessionStorage.getItem('memid');
		if(id == '' || id == null) {
			loginfirst();
		} else if( id != targetarray[1]){
			alert( "can't delete others' comment! ");
		} else {
			var deletenum = JSON.stringify(targetarray[0]);
			$.ajax({
				async : true,
				type : 'POST',
				data : deletenum,
				url : "commentDelete.eh",
				dataType : "json",
				success : function(data) {
					if( data === 1 ){
						alert('comment delete success!');
						showcomment();
					}		
				},
				error : function(error) {
					alert("error : " + error);
				}
			});
		}
	 });
	
	$(document).on("click","#commentmodify", function(e){ 
		var targetnum = e.target.name;
		var targetarray = targetnum.split(",", 2);
		var id = sessionStorage.getItem('memid');
		if(id == '' || id == null) {
			loginfirst();
		} else if( id != targetarray[1]){
			alert( "can't modify others' comment! ");
		} else {
			var modifynum = JSON.stringify(targetarray[0]);
			$.ajax({
				async : true,
				type : 'POST',
				data : modifynum,
				url : "commentModifyForm.eh",
				dataType : "json",
				success : function(data) {
					var jsoncomments = JSON.parse(data);
					console.log(jsoncomments);
					var content = jsoncomments['content'];
					var reg_date = jsoncomments['reg_date'];
					var num = jsoncomments['num'];
					//alert(reg_date);
					$('textarea[name="commentcontent"]').val(content);
					$('form[name="commentform"]').append('<input type="hidden" name="reg_date" value="'+ reg_date +'">');
					$('#commentform input[name="num"]').attr('value', num );
				},
				error : function(error) {
					alert("error : " + error);
				}
			});
		}
	 });
//-->
</script>

<main class="main">
<div class="grid">
	<h1>${title}</h1>
	<h2> Board Content </h2>
	<div class="container">
	<div class="row">
	<div class="col-md-12">
	<div class="table-responsive">
		<table class="table table-bordered">
			<caption class="text-center">
			    <div class="container">
			        <div class="row">
			            <div class="col-sm-3">
			                <button class="butn" onclick="location.href='BoardModifyForm.eh?num=${boardDto.num}&pageNum=${pageNum}'">Modify</button>
			            </div>
			            <div class="col-sm-3">
			                <button class="butn" onclick="location.href='BoardDeleteForm.eh?num=${boardDto.num}&pageNum=${pageNum}'">Delete</button>
			            </div>
			            <div class="col-sm-3">
			                <button class="butn" onclick="location.href='BoardWriteForm.eh?num=${boardDto.num}&pageNum=${pageNum}&re_f=${boardDto.re_f}&re_step=${boardDto.re_step}&re_level=${boardDto.re_level}&re_type=${boardDto.re_type}'">Reply</button>
			            </div>
			            <div class="col-sm-3">
			                <button class="butn" onclick="location.href='BoardList.eh';return false;">List</button>
			            </div>			            
			        </div>
			    </div>	
			</caption>
		<thead class="text-center">
			<tr>
				<th style="width : 20%">글번호</th>
				<td>${number}</td>
				<th style="width : 20%">조회수</th>
				<td>${boardDto.readcount}</td>
			</tr>
			<tr>
				<th>작성자</th>
				<td>${boardDto.id}</td>
				<th>작성일</th>
				<td><fmt:formatDate value="${boardDto.reg_date}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
			<tr>
				<th>영화제목</th>
				<td>${boardDto.movienm}</td>
				<th>평점</th>
				<td>${boardDto.score}</td>
			</tr>
			<tr>
				<th>글제목</th>
				<td colspan="3">${boardDto.subject}</td>
			</tr>
		</thead>
		<tbody>
		<tr>
		<c:if test="${fileDto ne null}">
		<tr>
			<td colspan="4">
				<c:forEach items="${fileDto}" var="filelist">
					<img alt="${filelist.org_filenm}" style="width:100%" src="\SophieProject\boardupload\<c:out value="${filelist.std_filenm}"/>">
				</c:forEach>
			</td>
		</tr>
		</c:if>
			<td colspan="4"><pre>${boardDto.content}</pre></td>
		</tr>
		</tbody>
		</table>
	</div>
	</div>
	</div>
	<!-- comment list -->
	<div id="commentlist"></div>	
	<!-- comment form -->
		<div class="row">
		<div class="col-md-12">
		<form method="post" name="commentform" id="commentform">
		<input type="hidden" name="memid" value="${sessionScope.memid}">
		<input type="hidden" name="num" value="${boardDto.num}">
		<input type="hidden" name="re_f" value="${boardDto.re_f}">
		<input type="hidden" name="re_step" value="${boardDto.re_step}">
		<input type="hidden" name="pageNum" value="${pageNum}">
		<input type="hidden" name="movienm" value="${boardDto.movienm}">
		<div class="table-responsive">
		<table class="table table-bordered">
		<tbody>
			<tr>
				<td>
				<div class="form-group">
		            <textarea name="commentcontent" cols="60" rows="4" class="form-control"></textarea>           
	        	</div>
				</td>
		
				<td>
					<button class="butn" type="button" name="post" style="position:relative;height:120px;" onclick="addpost()">Post</button>
				</td>
			</tr>
		</tbody>
		</table>
		</div>
		</form>	
		</div>
		</div>
	</div>
</div>
</main>