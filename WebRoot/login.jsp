<%@ page language="java" import="java.util.*" pageEncoding="utf-8" errorPage="error.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>ForFuture login</title>
		<!-- Favicons -->
		<link rel="shortcut icon" href="img/favicon.ico">
		
		<link rel="stylesheet" type="text/css" href="bootstrap/3.3.4/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="bootstrap/3.3.4/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="css/reset.css"/>
		<style type="text/css">
			#error{
				list-style:none;
				color:red;
				text-align:center;
				font-size:16px;
			}
		</style>
	</head>
	
	<body style="background:url('img/background.jpg') repeat;width:100%;">
		<nav class="navbar navbar-inverse">
			<div class="container">
				
				<!-- top start -->
				<%-- 动态包含 --%>
				<jsp:include page="top.jsp"></jsp:include>
			</div>
		</nav>
		<div class="container">
			<form class="form-horizontal col-md-4 col-md-offset-4" method="post" action="<%=basePath%>UserServlet.action?flag=login">
				<!-- 
					<center style="font-size:18px;color:red;font-weight:bold;">${msg}</center>
				 -->
				<h2 class="text-center text">ForFuture login</h2>
				
				<div class="form-group form-group-lg">
					<div class="input-group">
						<span class="input-group-addon">账号</span>
						<input type="text" class="form-control" name="username" required="required" value="${username }"/>
					</div>
				</div>
				
				<div class="form-group form-group-lg">
					<div class="input-group">
						<span class="input-group-addon">密码</span>
						<input type="password" class="form-control" name="password" required="required"/>
					</div>
				</div>
				
				<li id="error">
                    <%
                    	String msg=(String)request.getAttribute("msg");
                    	if(msg!=null && !msg.equals("")){
                    		out.print(msg);
                    	}
                    %>
                </li>
                
				<div class="form-group checkbox text-right">
					<label>
						<input type="checkbox" name="remember" value="1"/>记住密码
					</label>
				</div>
					
				<div class="form-group">
					<button class="btn btn-primary btn-lg btn-block">登录</button>
				</div>
				
			</form>
		</div>
		
		
		<footer class="navbar navbar-fixed-bottom text-center">
			<span><a href="#">ForFuture</a>科技集团&nbsp;版权所有&copy; since 2017</span>
		</footer>
		
		<script src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="bootstrap/3.3.4/js/bootstrap.min.js" ></script>
	</body>
</html>
