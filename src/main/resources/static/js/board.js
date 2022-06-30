
let index = {
	init: function() {
		$("#btn-save").bind("click", () => {
			this.save();
		});	
		
		$("#btn-delete").bind("click", () => {
			this.deleteById();
		});	
		
		$("#btn-update").bind("click", () => {
			this.update();
		});	
		
		$("#btn-reply-save").bind("click", () => {
			this.replySave();
		});
			
	},
	
	save: function() {
		// 데이터 가져오기
		// json 변환해야 하기 떄문에 오브젝트로 만들것임
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}
		
		console.log("데이터 확인");
		console.log(data);
		
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(data, textStatus, xhr) {
			if(data.status) {
				alert("글쓰기가 완료되었습니다.");
				location.href="/";
			}
		}).fail(function(error) {
			alert("글쓰기에 실패하였습니다.")
		});
	},
	
	deleteById: function() {
		let id = $("#board-id").text();
		
		$.ajax({
			type: "DELETE",
			url: "/api/board/" + id
		})
		.done(function(data) {
			if(data.status) {
				alert("삭제가 완료되었습니다.");
				location.href = "/";
			}
		})
		.fail(function() {
			alert("삭제 실패");
		});
	},
	
	update: function() {
		let boardId = $("#id").val();
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		}
		
		$.ajax({
			type: "PUT",
			url: "/api/board/" + boardId,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json",
			async: false
		}).done(function(data) {
			if(data.status) {
				alert("글 수정이 완료되었습니다.")
				location.href = "/";
			}
		}).fail(function(error) {
			alert("글쓰기 실패!")
		});
	},
	
	// 댓글 등록 (boardId : 해단 게시글의 Id)
	replySave: function() {
		// 데이터 가져오기
		// json 변환해야 하기 떄문에 오브젝트로 만들것임
		let data = {
			boardId: $("#board-id").text(),
			content: $("#reply-content").val()
		}
		
		console.log("데이터 확인");
		console.log(data);
		
		// (``)백틱 -> 자바 스크립트 변수를 문자열 안에 넣어서 사용할 수 있다.
		$.ajax({
			type: "POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType: "application/json; charset=utf-8",
			dataType: "json"
		}).done(function(response) {
			if(response.status) {
				// response - int status, T data
				console.log(response.data)
				addReplyElement(response.data);
				// location.href = `/board/${data.boardId}`;
			}
		}).fail(function(error) {
			alert("댓글작성에 실패하였습니다.")
		});

	},	//end of reply save
	
	replyDelete: function(boardId, replyId) {
		// 댓글의 id 값, 해당 게시글의 boardId,
		
		$.ajax({
			type: "DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json"
		}).done(function(response) {
			console.log(response.data);
			alert("댓글 삭제 성공")
			location.href = `/board/${boardId}`;
			// 비동기로 만들기 도전과제
			// addReplyElement(response.data);
		}).fail(function(error) {
			console.log(error);
			alert("댓글 삭제 실패")
		});
	}
	
}

function addReplyElement(reply) {
	let principalId = $("#principal--id");
	let childElement = `<li class="list-group-item d-flex justify-content-between" id="reply--${reply.id}">
				<div>${reply.content}</div>
				<div class="d-flex">
					<div>작성자 : ${reply.user.username}&nbsp;&nbsp;</div>
					<c:if test="${reply.user.id} == principalId ">
						<button class="badge badge-danger" onclick="index.replyDelete(${reply.board.id} ,${reply.id});">삭제</button>
					</c:if>
				</div>
			</li>`;
	$("#reply--box").prepend(childElement);
	$("#reply-content").val("");
}



index.init();

