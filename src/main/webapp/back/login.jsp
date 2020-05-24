<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"></c:set>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>诗歌检索系统后台登录</title>
	<link rel="stylesheet" type="text/css" href="${app}/boot/css/login.css" />
	<script src="${app}/boot/js/jquery-2.2.1.min.js"></script>
	<script>
		$(function () {
			$("#form1").submit(function () {
				var username=$("#login_name").val()
				var password=$("#login_pwd").val()
				if(username==""||password==""){
					alert("不能为空!");
				}else {
					$.ajax({
						url:"${app}/user/login",
						type:"post",
						data:{username:username,password:password},
						success:function (data) {
							var message = data.message;
							console.log(message);
							if(message=="登录成功"){
								console.log("正在跳转 ...");
								location.href="${app}/back/main.jsp"
								// onclick="window.open('/back/main.jsp','_self')"
							}else{
								alert(message)
							}
						}
					})
				}

			})
		})
	</script>
</head>
<body>
<form method="post" action="" id="form1" onsubmit="return false">
	<div class="login">
		<h2>登录管理后台</h2>
		<div class="form">
			<div class="check_msg"></div>
			<div class="login_name">
				<input name="login_name" id="login_name" value="" name="login_name" type="text" class="form_input" placeholder="请输入用户名" />
			</div>
			<div class="login_pwd">
				<input name="login_pwd" id="login_pwd" name="login_pwd" type="password" class="form_input" placeholder="请输入密码" />
			</div>
			<input name="btn_Login" type="submit" id="btn_Login" class="btn_Login" value="登 录" />

		</div>
	</div>
</form>
</body>
</html>