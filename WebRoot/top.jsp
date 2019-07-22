<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

		<script type="text/javascript">
			function init(){
				var liobj=document.getElementById("tid"+${param.id});
				liobj.className="active";
			}
		</script>

	

	<body onload="init()">
		<div class="navbar-header">
			<span class="navbar-brand">
				<a href="http://ganquanzhong.top/ShareFiles/" target="_blank" style="text-decoration:none;">ForFuture资源共享系统</a>
				<a href="http://ganquanzhong.top/Web/" target="_blank" style="text-decoration:none;">MORE</a>
			</span>
		</div>
	
		<div class="collapse navbar-collapse">
			<ul class="nav navbar-nav navbar-right">
				<li id="tid1">
					<a href="SourceServlet.action?id=1&flag=pagingAll">所有资源</a>
				</li>
				<li id="tid2">
					<a href="upload.jsp?id=2">上传资源</a>
				</li>
				
				<c:choose>
				
					<c:when test="${sessionScope.user.username!=null}">
					<li id="tid3">
						<a href="SourceServlet.action?flag=mySource&id=3">我的资源</a>
					</li>
					</c:when>
				</c:choose>
				
				<li id="tid4">
					<c:choose>
						<c:when test="${sessionScope.user.username==null}">
							<a href="login.jsp?id=4">登录</a>
						</c:when>
						<c:otherwise>
							<a href="UserServlet.action?flag=userInfo" target="blank"> ${sessionScope.user.username} </a>
						</c:otherwise>
					</c:choose>
				</li>
				
				<li id="tid5">
					<c:choose>
						<c:when test="${user==null}">
							<a href="register.jsp?=5">注册</a>
						</c:when>
					</c:choose>
				</li>
				<li>
					<c:choose>
						<c:when test="${user!=null}">
							<a href="UserServlet.action?flag=exit">注销</a>
						</c:when>
					</c:choose>
				</li>
			</ul>
		</div>
	</body>

