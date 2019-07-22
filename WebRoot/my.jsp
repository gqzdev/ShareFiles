<%@ page language="java" import="java.util.*" pageEncoding="utf-8" errorPage="error.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>ForFuture 我的资源</title>
		<!-- Favicons -->
		<link rel="shortcut icon" href="img/favicon.ico">
		<link rel="stylesheet" type="text/css" href="bootstrap/3.3.4/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="bootstrap/3.3.4/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="css/reset.css"/>
	</head>
	
	<body style="background:url('img/background.jpg');opacity:0.9;">
		<nav class="navbar navbar-inverse">
			<div class="container">
				
				<!-- top start -->
				<%-- 动态包含 --%>
				<jsp:include page="top.jsp"></jsp:include>			
			</div>
		</nav>
		
		<div class="container">
			<div class="btn-group btn-group-sm col-md-5">
				<button class="btn btn-info active">按上传时间</button>
				<button class="btn btn-info">按下载量</button>
				<button class="btn btn-info">按大小</button>
			</div>
			
			<form class="form-inline col-md-5 text-right col-md-offset-2">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="请输入需要查询关键字"/>
				</div>
				<button class="btn btn-primary">搜索</button>
			</form>
			<hr />
			
			<!-- 资源列表 start -->
			<table class="table table-striped table-hover">
				<tr>
					<th>序号</th>
					<th>资源名</th>
					<th>标签</th>
					<th>大小</th>
					<th>上传时间</th>
					<th>需要积分</th>
					<th>下载量</th>
					<th>操作</th>
				</tr>
					<!-- 动态  列表 start -->
				<c:forEach items="${sourceList }" var="source" varStatus="vs" >
					<tr>
						<td>${vs.count+(pageNumber-1)*10 }</td>
						<td style="text-align: center">${source.sname }</td>
						<td style="text-align: center">${source.tag }</td>
						<td style="text-align: center">
							<fmt:formatNumber value="${source.size/1024/1024}" pattern="0.00"></fmt:formatNumber>MB
						</td>
						<td style="text-align: center">${source.uploadtime }</td>
						<td style="text-align: center">${source.integral }</td>
						<td style="text-align: center">${source.dcount }</td>
						<td>
							<a href="SourceServlet.action?flag=showSource&id=${source.id }&pageNumber=${pageNumber}">详情</a>
							<a href="SourceServlet.action?flag=delete&id=${source.id }&pageNumber=${pageNumber}">删除</a>
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- 动态  资源列表 end -->
			
			<!-- 页码 start -->
			<nav class="text-center">
			
				<ul class="pagination">
					<!-- 上一页 -->
					<li class="" id="up">
				      <a id="upa" href="SourceServlet.action?flag=mySource&pageNumber=${pageNumber-1 }">
				        <span>&laquo;</span>
				      </a>
				    </li>
				    <c:forEach begin="1" end="${maxPage}" var="i">
						<li name="liName" title="${i}">
							<a href="SourceServlet.action?flag=mySource&pageNumber=${i}">${i }</a>
						</li>
				    </c:forEach>
				    
				    <!-- 下一页 -->
					<li class="" id="down">
				      <a id="downa" href="SourceServlet.action?flag=mySource&pageNumber=${pageNumber+1 }">
				        <span>&raquo;</span>
				      </a>
				    </li>
				</ul>		
			</nav>
			<!-- footer end-->
			
		</div>
		
		<!-- footer start -->
		<footer class="navbar navbar-fixed-bottom text-center">
			<span>ForFuture科技集团&nbsp;版权所有&copy; since 2017</span>
		</footer>
		<!-- footer end -->
		
		<script type="text/javascript">
			function init(){
				var liobjs=document.getElementsByName("liName");//获取多个li元素 
				for ( var i = 0; i < liobjs.length; i++) {
					if(liobjs[i].title == ${pageNumber})
						liobjs[i].className="active";
				}

				if( ${pageNumber}==1 ){
					document.getElementById("up").className="disabled";
					document.getElementById("upa").href="javascript:void(0)";
				}
				
				if(${pageNumber}==${maxPage}){
					document.getElementById("down").className="disabled";
					document.getElementById("downa").href ="javascript:void(0)";
				}
				if(${maxPage}==1 || ${maxPage}==0 ){
					document.getElementById("up").className="disabled";
					document.getElementById("down").className="disabled";

					document.getElementById("upa").href="javascript:void(0)";
					document.getElementById("downa").href="javascript:void(0)";
				}
				
			}
		</script>
		<script src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="bootstrap/3.3.4/js/bootstrap.min.js" ></script>
	</body>
</html>
