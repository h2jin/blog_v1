let index = {
	
	init: function() {
		$("#btn-save").bind("click", () => {
			this.save();
		});
		// document.queryselect - java , $() - Jquery
		
		// 전통적인 로그인 방식 일 때 사용한 부분
		/*
		 $("#btn-login").bind("click", () => {
			 this.login();
		 });
		 */
		
		$("#btn-update").bind("click", () => {
			this.update();
		});
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
			url: "/auth/joinProc",
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
			location.href = "/";
		}).fail(function(error){
			// 통신 실패 시
			console.log(error);
			alert("회원가입 실패");
		});
		
	},
	/*
	login: function() {
		let data = {
			username: $("#username").val(),
			password: $("#password").val()
		}
		
		// ajax 호출
		$.ajax({
			// 회원 로그인 요청 (GET은 히스토리가 남아서 유출될 수 있음. POST로 가져올 것임)
			type: "POST",
			url: "/api/user/login",
			data: JSON.stringify(data), //데이터 타입 다르기 때문에 JSON으로 바꿔줘야 함
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data, textStatus, xhr) {
			alert("로그인이 완료되었습니다.")
			location.href = "/";
			console.log(data);
		}).fail(function(error) {
			alert("로그인이 실패했습니다.")
			console.log(error);
		});
	},
	*/
	update: function() {
		let data = {
			username: $("#username").val(),
			id: $("#id").val(),
			password: $("#password").val(),
			email: $("#email").val()
		}
		
		$.ajax({
			type: "PUT",
			url: "/user",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data) {
			if(data.status) {
				alert("회원정보 수정이 완료되었습니다.")
				location.href = "/";
			}			
		}).fail(function(error) {
			alert("회원정보 수정 실패")
		});
	} // end of update
	
}

index.init();

