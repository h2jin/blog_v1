let index = {
	
	init: function() {
		$("#btn-save").bind("click", () => {
			this.save();
		});
		// document.queryselect - java , $() - Jquery
	},
	
	save: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		// console.log(data);
		
		// ajax 호출
		$.ajax({
			// 서버측에 회원가입 요청
			type: "POST",
			url: "/blog/api/user",
			// 오브젝트 형식 java, javascript 서로 다름 중간언어가 필요 (xml, json 등)
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json" // 응답이 왔을 때 기본 데이터 타입(Buffered  문자열을 받아서 javascript 오브젝트 자동 변환)
		}).done(function(data, textStatus, xhr){
			// 통신 성공 시
			console.log("xhr : " + xhr);
			console.log(xhr);
			console.log("textStatus : " + textStatus);
			console.log("data : " + data);
			alert("회원가입 성공");
			location.href = "/blog";
		}).fail(function(error){
			// 통신 실패 시
			console.log(error);
			alert("회원가입 실패");
		});
		
	}
	
}

index.init();

