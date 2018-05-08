$(document).ready(function(){
	$("#login-btn").click(function(){
		var username = $("#username").val();
		var password = $("#password").val();
		$.ajax({
			type: 'post',
            url: "/doLogin",
            data:'account='+username+'&password='+password,
            statusCode: {
                200: function(data){
                    window.open("user","_self");
                },
                500: function(data){
                    alert("登陆失败");
                }
            }
		});
	});
	$("#register-btn").click(function(){
		var username = $("#username").val();
		var email = $("#email").val();
		var sex = $("#sex").val();
		var password = $("#password").val();
		var repassword = $("#repassword").val();
		var code = $("#code").val();
		console.log(username+" "+email+" "+sex+" "+password+" "+repassword+" "+code);
		if (password!=repassword) {
			alert("两次密码不一样");
		} else {
			$.ajax({
			type: 'post',
            url: "/doRegister",
            data:'account='+username+'&email='+email+'&sex='+sex+'&password='+password+'&verificateCode='+code,
            statusCode: {
                200: function(data){
                    window.open("login","_self");
                },
                500: function(data){
                    alert("注册失败");
                }
            }
		});
		}
		
	});
});