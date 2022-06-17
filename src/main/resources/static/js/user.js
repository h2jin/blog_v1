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
		
		console.log(data);
		
	}
	
}

index.init();

