<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp" %>
	
<main class="container py-5">
	<div>
		<form action="/story/image/upload" enctype="multipart/form-data" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			<div class="input-group mt-3">
				<input type="file" name="file" class="custom-file-input" id="customFile" required="required"> 
				<label class="custom-file-label" for="customFile"></label>
			</div>
			
			<div class="input-group mt-3">
		      <div class="input-group-prepend">
		        <span class="input-group-text">스토리 설명</span>
		      </div>
		      <input type="text" class="form-control" name="storyText" required="required">
		    </div>
			
			<div class="input-group mt-3">
				<button type="submit" class="btn btn-info">스토리 등록</button>
			</div>
		</form>
	</div>
</main>
	
	

<script>
// Add the following code if you want the name of the file appear on select
$(".custom-file-input").bind("change", function() {
	console.log($(this).val());
  let fileName = $(this).val().split("\\").pop();
  $(this).siblings(".custom-file-label").addClass("selected").html(fileName);
});
</script>
	
<%@ include file="../layout/footer.jsp" %>
