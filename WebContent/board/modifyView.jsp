<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/header.jsp"%>
<script src="/SophieProject/script.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("input:radio[name=score][value=" + '<c:out value="${boardDto.score}"/>' + "]").attr("checked","checked");
	
	var id = sessionStorage.getItem('memid');
	if(id == '' || id == null) {
		loginfirst();
		history.back();
	} else {
		$('input[name="id"]').val(id);
	}


	var fileName = "";
    $('.custom-file-input').change(function (e){
    	for( var i=0; i<e.target.files.length; i++) {
    		fileName += e.target.files[i].name + " ";
    	}
    	$('.custom-file-label').text(fileName);
    });
    
    $('button[type="reset"]').click(function (e){
    	$('.custom-file-label').text("");
    });
});
</script>

<main class="main">
<div class="grid">
	<h1>${title}</h1>
	<c:if test="${result eq 1}">
		    <h2> Board Modify Form </h2>
<div class="container">
	  <div class="row">
	  	<div class="col-md-2"></div>
	    <div class="col-md-8">
			<form method="post" action="BoardModifyPro.eh" name="writeform" enctype="multipart/form-data">
			<input type="hidden" name="num" value="${num}">
			<input type="hidden" name="re_f" value="${re_f}">
			<input type="hidden" name="re_step" value="${re_step}">
			<input type="hidden" name="re_level" value="${re_level}">
			<input type="hidden" name="re_type" value="${re_type}">
			
		        <div class="form-group">
		            <label for="id"> 작성자 : </label>
		            <input type="text" class="form-control" name="id" value="${boardDto.id}" disabled>
		        </div>
		        <div class="form-group">
		            <label for="movienm"> 영화 제목 : </label>
		            <input type="text" class="form-control" name="movienm" value="${boardDto.movienm}">           
		        </div>
		        <div class="form-group">
		            <label for="subject"> 글제목 : </label>
		            <input type="text" class="form-control" name="subject" value="${boardDto.subject}">           
		        </div>
		        <div class="form-group">
		            <label for="content"> 글쓰기 : </label>
		            <textarea name="content" cols="30" rows="10" class="form-control">${boardDto.content}</textarea>           
		        </div>
 		        <div class="form-group">
		            <label class="radio-inline">
		            	영화 평점 : &nbsp;
		                <input type="radio" name="score" value="1"> 1점
		            </label>
					&nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="score" value="2"> 2점
		            </label>
					&nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="score" value="3"> 3점
		            </label>
		            &nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="score" value="4"> 4점
		            </label>
		            &nbsp;
		            <label class="radio-inline">
		                <input type="radio" name="score" value="5"> 5점
		            </label>		            
		        </div>
		        <div class="form-group">
		        	<c:if test="${fileDto ne null}">
						<div class="custom-file">
						  <input type="file" class="custom-file-input" id="customFile" multiple="multiple" name="files">
						  <c:forEach items="${fileDto}" var="filelist" varStatus="cnt">
						  	<c:out value="${filelist.org_filenm}"/>
						  <label class="custom-file-label" for="customFile">Change files</label> 				  
						  </c:forEach>  
						</div>		        	
		        	</c:if>
		        	<c:if test="${fileDto eq null}">
		        		<div class="custom-file">
					  	<input type="file" class="custom-file-input" id="customFile" multiple="multiple" name="files">
					  	<label class="custom-file-label" for="customFile" >Choose files</label>
					</div>
		        	</c:if>       
				</div>
    			<br>
			    <div class="container">
			        <div class="row">
			            <div class="col-sm-4">
			                <button class="butn" type="submit">Submit</button>
			            </div>
			            <div class="col-sm-4">
			                <button class="butn" type="reset">Cancel</button>
			            </div>
			            <div class="col-sm-4">
			                <button class="butn" onclick="location.href='BoardList.eh';return false;">List</button>
			            </div>			            
			        </div>
			    </div>		        
			</form>
	    </div>
	    <div class="col-md-2"></div>
	  </div>
	</div>
	
	</c:if>
	<c:if test="${result eq 0}">
		<script type="text/javascript">
		//<!--
			alert("Wrong passwd!");
		//-->
		</script>
	</c:if>
	<c:if test="${result eq 2}">
		<script type="text/javascript">
		//<!--
			alert("can't modify others' article!");
		//-->
		</script>
	</c:if>
</div>
</main>