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
		<title>ForFuture 注册</title>
		<!-- Favicons -->
		<link rel="shortcut icon" href="img/favicon.ico">
		
		<link rel="stylesheet" type="text/css" href="bootstrap/3.3.4/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="bootstrap/3.3.4/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="css/reset.css"/>
		
		<script src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="bootstrap/3.3.4/js/bootstrap.min.js" ></script>
		<style type="text/css">
			#SMS{
				    font-weight: 550;
    				text-decoration: none;
   					margin-left: 45px;
			}
			#SMS:hover{
				text-decoration: underline;
    			color: #50b354;
			}
			
			.right{
				float:right;
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
			<form onsubmit="return checkReg()" class="form-horizontal col-md-4 col-md-offset-4" method="post" action="<%=basePath%>UserServlet.action?flag=register">
				<h2 class="text-center" style="color: white;font-family: -webkit-pictograph;">ForFuture 注册</h2>
				<!-- 账号 -->
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">账<span style="visibility: hidden;">账号</span>号</span>
						<input type="text" class="form-control" name="username" id="username" placeholder="账号" onblur="checkName(this)" required="required"/>
					</div>
						<span id="sid" class="right"></span>
				</div>
				
				<!--密码 -->
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">密<span style="visibility: hidden;">密码</span>码</span>
						<input type="password" class="form-control" placeholder="密码" name="password" id="password1" required="required"/>
					</div>
						<span id="password1Check" class="right"></span>
				</div>
				
				<!-- 重复密码 -->
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">重复密码</span>
						<input type="password" class="form-control" placeholder="确认密码" required="required" name="password2" id="password2" onblur="checkPassword()"/>
					</div>
						<span id="passwordCheck" class="right"></span>
				</div>
				
				<!--手机号码 -->
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">手<span style="visibility: hidden;">手机</span>机</span>
						<input type="text" class="form-control" style="width:70%" placeholder="手机号码" required="required" name="phone"  id="phone" onblur="isPoneAvailable()" />
						<input type="button" style="width:30%;float:left;height: 34px;" id="btn"  value="获取验证码" onclick="send(this)" />
					 
					</div>
						<span id="phoneCheck" class="right"></span>
					<br/>
					<div class="input-group">
						<span class="input-group-addon">手机验证码<span style="visibility: hidden;">码</span></span>
						<input type="text" class="form-control" placeholder="手机验证码" required="required" id="SMSVerifyCode" name="SMSVerifyCode"/>
					</div>
				</div>
				
				<!-- 验证码 -->
				<div class="form-group">
					<div class="input-group">
						<span class="input-group-addon">图片验证码<span style="visibility: hidden;">码</span></span>
						<input type="text" class="form-control" style="width:69%" placeholder="验证码" required="required" id="VerifyCode" name="VerifyCode"/>
						<!-- 验证码图片-->
						<a onclick="javascript:refresh();" style="width:30%;float:left;height: 34px;">
							<img alt="验证码的图片" src="VerifyCodeServlet.action" id="VerifyCodeImg"  />
						</a>
					</div>
				</div>  
				
				<!-- 提交注册 -->
				<div class="form-group">					
					<input class="btn btn-primary btn-lg btn-block" type="reset" name="reset" value="重置" /> 
					<input type="submit" class="btn btn-primary btn-lg btn-block" value="注册"/>
				</div>
			</form>
		</div>
		
		<footer class="navbar navbar-fixed-bottom text-center">
			<span><a href="#">ForFuture</a>Design&nbsp;版权所有&copy; 2018-2020</span>
		</footer>
	</body>
	
	
	
	<script type="text/javascript">
			//定义全局变量，标志注册是否合法
			var success=0;
			var phoneflag=false;
			//用户名检查
			function checkName(username){
				var username=document.getElementById("username").value;
				if(username=="" || username==null){
					success=0;//标志不成功
			   		document.getElementById("sid").innerHTML="用户名不能为空！";
			   		document.getElementById("sid").style.color="red";
			   		return ;
				}
				
				//alert(username.value);
				/*  1.
					XMLHttpRequest 对象
					所有现代浏览器均支持 XMLHttpRequest 对象（IE5 和 IE6 使用 ActiveXObject）。
					XMLHttpRequest 用于在后台与服务器交换数据。这意味着可以在不重新加载整个网页的情况下，对网页的某部分进行更新。
				*/
				var xmlhttp;//创建xmlhttp对象
				if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
				  	xmlhttp=new XMLHttpRequest();
				}else{// code for IE6, IE5
				  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				
				//  2.如需将请求发送到服务器，我们使用 XMLHttpRequest 对象的 open() 和 send() 方法：
				//打开请求 post请求  url请求地址 
				xmlhttp.open("POST","UserServlet.action?flag=checkName&x="+Math.random(),true);
				//如果需要像 HTML 表单那样 POST 数据，请使用 setRequestHeader() 来添加 HTTP 头
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("username="+username);
				  
				//3.函数 onreadystatechange事件
				/**
					onreadystatechange 事件
					当请求被发送到服务器时，我们需要执行一些基于响应的任务。
					每当 readyState 改变时，就会触发 onreadystatechange 事件。
					readyState 属性存有 XMLHttpRequest 的状态信息。
				**/  
				xmlhttp.onreadystatechange=function()
				{
					 if (xmlhttp.readyState==4 && xmlhttp.status==200)
					   {
					   		//alert(xmlhttp.responseText);
						    //来自服务器的响应
						    //如需获得来自服务器的响应，请使用 XMLHttpRequest 对象的 responseText 或 responseXML 属性。
						    if(xmlhttp.responseText==1){
						    	success=0;//标志不成功
						   		document.getElementById("sid").innerHTML="该用户名已被使用！！请更换";
						   		document.getElementById("sid").style.color="red";
						    }else{
						    	success=1;//标志成功
						   		document.getElementById("sid").innerHTML="恭喜，改用户名可以被您使用";
						   		document.getElementById("sid").style.color="green";
						    }
					   }
				 }
			}
						
			// 密码验证		
			function checkPassword(){
				var password1=document.getElementById("password1").value;
				var password2=document.getElementById("password2").value;
				if(password1==""){
					document.getElementById("passwordCheck").innerHTML="密码不能为空！！";
					document.getElementById("passwordCheck").style.color="red";
					document.getElementById("password1").focus(); 
					return ;
				}else{
					if(password1!=password2){
						//两次密码不一致
						document.getElementById("passwordCheck").innerHTML="两次密码不一致！！";
						document.getElementById("passwordCheck").style.color="red";
						return false;
					}else{
						//两次密码一致
						document.getElementById("passwordCheck").innerHTML="密码通过√√√√√√√√√";
						document.getElementById("passwordCheck").style.color="green";
					}
				}
			}
			
			//手机号码正则表达式验证
			function isPoneAvailable() { 
				  var phone=document.getElementById("phone").value;
		          var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
		          if (!myreg.test(phone)) { 
		        	  document.getElementById("phoneCheck").innerHTML="手机号码格式不对";
					  document.getElementById("phoneCheck").style.color="red";
					  phoneflag=false;
		              return false;  
		          } else {  
		        	  document.getElementById("phoneCheck").innerHTML=null;
					  phoneflag=true;
		              return true;  
		          }  
		     } 
			
			//定义一个60秒计时器变量
			var countdown = 120;
			//构造一个倒计时函数叫settime
			function settime(obj) {
					if(countdown == 0) {
						obj.removeAttribute("disabled");
						obj.value = "获取验证码";
						countdown = 120;
						
						//立即跳出settime函数，不再执行函数后边的步骤
						return;
					} else {
						obj.setAttribute("disabled", true);
						obj.value = "重新发送(" + countdown + ")";
						countdown--;
					}
					//过1秒后执行倒计时函数
					setTimeout(function(){settime(obj)}, 1000);
			}
			
			function send(obj){
				if(phoneflag){
					sendSMS();//发送短信
					settime(obj);
				}
			}
			
			//发送验证码
			function sendSMS(){
				var phone=document.getElementById("phone").value;
				var xmlhttp;//创建xmlhttp对象
				if (window.XMLHttpRequest){// code for IE7+, Firefox, Chrome, Opera, Safari
				  	xmlhttp=new XMLHttpRequest();
				}else{// code for IE6, IE5
				  	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
				}
				xmlhttp.open("POST","UserServlet.action?flag=SMSVerify",true);//发送验证码
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("phone="+phone);
				  
				xmlhttp.onreadystatechange=function()
				{
					 if (xmlhttp.readyState==4 && xmlhttp.status==200)
					   {
					   		//alert(xmlhttp.responseText);
						    //来自服务器的响应
						    //如需获得来自服务器的响应，请使用 XMLHttpRequest 对象的 responseText 或 responseXML 属性。
						    if(xmlhttp.responseText==1){
						   		document.getElementById("phoneSMS").innerHTML="验证码已发送321..";
						   		document.getElementById("phoneSMS").style.color="green";
						    }else{
						   		document.getElementById("phoneSMS").innerHTML="验证码发送失败××";
						   		document.getElementById("phoneSMS").style.color="red";
						    }
					   }
				 };
			}
			
			//刷新验证码：
			function refresh(){
				//alert('刷新验证码');
				var src=$("#VerifyCodeImg").attr("src");
				//给VerifyCodeServlet.action添加一个时间参数，保证每一次的src不一样，防止浏览器的缓存			
				$("#VerifyCodeImg").attr("src",src+"?"+new Date());			
			}
			
			//全局校验
			function checkReg(){
				if(success==0){
					document.getElementsByName("username")[0].select();
					return false;
				}
			}
			
		</script>
</html>
