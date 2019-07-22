<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ForFuture ${sessionScope.user.username }</title>
	<!-- Favicons -->
	<link rel="shortcut icon" href="img/favicon.ico">
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="css/index.css">
	
  </head>
  
  <body style="background:url('img/background.jpg');opacity:0.7;">  	
  	<center style="font-size:30px;color:skyblue;padding-top:20px;">用户名： ${user.username}</center>
  	<center style="font-size:20px;color:skyblue;"> 手机号码：${user.phone}</center>
  	<center style="font-size:20px;color:skyblue;"> 积分：${user.integral}</center>
  	<center style="font-size:20px;color:skyblue;"> 注册时间：${user.regtime}</center>
  	
  	<!-- 打赏 -->
  	<jsp:include page="dashang.jsp"></jsp:include>
  	
  	<div class="wrap">
		<!-- 企鹅头部 -->
		<div class="headtop">
			<div class="headbottom"></div>
			<!-- 眼睛部分 -->
			<h1 class="lefteye">
				<p class="lefteye_in">
					<strong class="eyeshow"></strong>
				</p>
			</h1>
			<h1 class="righteye">
				<p class="righteye_in">
					<strong class="eyebai"></strong>
				</p>
			</h1>
			<!-- 嘴巴部分 -->
			<h1 class="mouth"></h1>
			<p class="mouth_bar">
				<strong class="mouth_bar1"></strong>
			</p>
		</div>
		
		<!-- 企鹅头部 -->

		<!-- 企鹅身体 -->
		<div class="body">
			<div class="tummy">
				<div class="pocket">
					<div class="pocket_line1">
						<div class="pocket_line2"></div>
					</div>
				</div>
			</div>
			<div class="hand">
				<div class="lefthand"></div>
				<div class="righthand"></div>
			</div>
			<div class="body_1">
				<div class="body_2">
					<div class="body_3"></div>
				</div>	
			</div>	
		</div>	
		<!-- 企鹅身体 -->

		<!-- 企鹅脚丫 -->
		<div class="footer">
			<div class="left_footer"></div>
			<div class="right_footer"></div>
		</div>
		<!-- 企鹅脚丫 -->
	</div>
  </body>
</html>
