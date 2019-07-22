<%@ page language="java" import="java.util.*" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>ForFuture Share</title>
		<!-- Favicons -->
		<link rel="shortcut icon" href="img/favicon.ico">
		<link rel="stylesheet" type="text/css" href="bootstrap/3.3.4/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="bootstrap/3.3.4/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="css/reset.css"/>
		<link rel="stylesheet" href="css/dashang.css" />
	    
	    <script src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="http://ganquanzhong.top/Web/js/dashang.js"></script>
		<script type="text/javascript" >
			$(function() {
				$(".pay_item").click(function() {
					$(this).addClass('checked').siblings('.pay_item').removeClass('checked');
					var dataid = $(this).attr('data-id');
					$(".shang_payimg img").attr("src", "img/" + dataid + ".png");
					$("#shang_pay_txt").text(dataid == "alipay" ? "支付宝" : "微信");
				});
			});
		
			function dashangToggle() {
				$(".hide_box").fadeToggle();
				$(".shang_box").fadeToggle();
			}
		</script>
		
		<style type="text/css">
			#footer .footer{
				text-align:center;
				font-size:16px;
				font-weight:4;
				maring-top:70px;
			}
			a:hover {
				color: red;
				font-weight: bold;
				text-decoration: none;
				maring-left: 2px;
			}
		</style>
	</head>
	
	<body onload="init()" style="background:url('img/background.jpg');opacity:0.9;">
		<!-- 菜单导航栏 start -->
		<nav class="navbar navbar-inverse">
			<div class="container">
								
				<!-- top start -->
				<%-- 动态包含 --%>
				<jsp:include page="top.jsp"></jsp:include>
				
				<%-- 静态包含 --%>
				<%--<%@include file="top.jsp" --%>
				
			</div>
		</nav>
	    <!-- 菜单导航栏 start -->
		
		<center style="font-size:18px;color:red;font-weight:bold;">${msg}</center>
		
		<div class="container">
			<div class="btn-group btn-group-sm col-md-5">
				<button class="btn btn-info active">按上传时间</button>
				<button class="btn btn-info">按下载量</button>
			</div>
			<form class="form-inline col-md-5 text-right col-md-offset-2">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="请输入需要查询关键字"/>
				</div>
				<a class="btn btn-primary">搜索</a>
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
					<th>提供者</th>
					<th>需要积分</th>
					<th>下载量</th>
					<th>操作</th>
				</tr>
				
				<c:forEach var="source" items="${sourceList}" varStatus="vs">
				
					<tr>
						<td>${vs.count+(pageNumber-1)*10}</td>
						<td>${source.sname }</td>
						<td>${source.tag }</td>
						<td>
							<fmt:formatNumber value="${source.size/1024/1024}" pattern="0.00"></fmt:formatNumber>MB
						</td>
						<td>${source.uploadtime }</td>
						<td>${source.author }</td>
						<td>${source.integral }</td>
						<td>${source.dcount }</td>
						<td>
							<a href="SourceServlet.action?flag=showSource&id=${source.id }&pageNumber=${pageNumber}">详情</a>
							<a onclick="dashangToggle()" href="SourceServlet.action?flag=download&id=${source.id}">下载</a>
							<c:if test="${user.id==source.uid}">
								<a href="SourceServlet.action?flag=delete&id=${source.id }&pageNumber=${pageNumber}">删除</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</table>
			<!-- 资源列表 end -->
			
			<!-- 页码 start -->
			<nav class="text-center">
				<ul class="pagination">
					<!-- 上一页 -->
					<li class="" id="up">
				      <a id="upa" href="SourceServlet.action?flag=pagingAll&pageNumber=${pageNumber-1 }">
				        <span>&laquo;</span>
				      </a>
				    </li>
				    <c:forEach begin="1" end="${maxPage}" var="i">
						<li name="liName" title="${i}">
							<a href="SourceServlet.action?flag=pagingAll&pageNumber=${i }">${i }</a>
						</li>
				    </c:forEach>
				    
				    <!-- 下一页 -->
					<li class="" id="down">
				      <a id="downa" href="SourceServlet.action?flag=pagingAll&pageNumber=${pageNumber+1 }">
				        <span>&raquo;</span>
				      </a>
				    </li>
				</ul>
			</nav>
			<!-- 页码 end -->
		</div>
		
		<!-- 打赏 start -->   
		<div class="dashang_content">			
			<div class="hide_box"></div>

			<div class="shang_box">
				<a class="shang_close" href="javascript:void(0)" onclick="dashangToggle()" title="关闭">
					<img src="img/close.jpg" alt="取消" /></a>
				<div class="shang_tit">
					<p>感谢您的支持，我会继续努力的!</p>
				</div>
				<div class="shang_payimg">
					<img src="img/alipay.png" alt="扫码支持" title="扫一扫" />
				</div>
				<div class="pay_explain">扫码打赏，你说多少就多少</div>
				<div class="shang_payselect">
					<div class="pay_item checked" data-id="alipay">
						<span class="radiobox"></span>
						<span class="pay_logo"><img src="img/alipay.jpg" alt="支付宝" /></span>
					</div>
					<div class="pay_item" data-id="weipay">
						<span class="radiobox"></span>
						<span class="pay_logo"><img src="img/wechat.jpg" alt="微信" /></span>
					</div>
				</div>
				<div class="shang_info">
					<p>打开<span id="shang_pay_txt">支付宝</span>扫一扫，即可进行扫码打赏哦</p>
					<p>Powered by
						<a href="http:ganquanzhong.top" target="_blank" title="ForFuture Design">ForFuture</a>，Design ForFuture</p>
				</div>
			</div>
		</div>
		<!---打赏 end-->
	
		<!-- footer  start-->
		<footer class="navbar navbar-fixed-bottom text-center">
			<span>
				<a href="http://www.ganquanzhong.top/Web/" target="blank">ForFuture</a>
				科技集团&nbsp;版权所有&copy;2018
			</span>
			<span>湖北省通信管理局 
			      <img alt="公网安备" src="img/icp.png" width="20px" height="20px">	
				  <a href="http://www.beian.gov.cn/portal/registerSystemInfo" target="_blank">鄂ICP备18005830号</a>
			</span>
		</footer>
		<!-- footer  end -->	
		
		
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
