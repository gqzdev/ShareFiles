<%@ page language="java" import="java.util.*" pageEncoding="utf-8"
	errorPage="error.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8" />
<title>ForFuture ${sessionScope.user.username }</title>
<!-- Favicons -->
<link rel="shortcut icon" href="img/favicon.ico">
<link rel="stylesheet" type="text/css"
	href="bootstrap/3.3.4/css/bootstrap.min.css" />
<link rel="stylesheet"
	href="bootstrap/3.3.4/css/bootstrap-theme.min.css" />


<script src="js/jquery-1.11.0.js"></script>
<script type="text/javascript" src="bootstrap/3.3.4/js/bootstrap.min.js"></script>
<style type="text/css">
.content {
	width: 80%;
	margin: 10px auto;
}

.hide_box {
	z-index: 999;
	filter: alpha(opacity = 50);
	background: #666;
	opacity: 0.5;
	-moz-opacity: 0.5;
	left: 0;
	top: 0;
	height: 99%;
	width: 100%;
	position: fixed;
	display: none;
}

.shang_box {
	width: 540px;
	height: 540px;
	padding: 10px;
	background-color: #fff;
	border-radius: 10px;
	position: fixed;
	z-index: 1000;
	left: 50%;
	top: 50%;
	margin-left: -280px;
	margin-top: -280px;
	border: 1px dotted #dedede;
	display: none;
}

.shang_box img {
	border: none;
	border-width: 0;
}

.dashang {
	display: block;
	width: 100px;
	margin: 65px auto;
	height: 44px;
	line-height: 25px;
	padding: 10px;
	background-color: #E74851;
	color: #fff;
	text-align: center;
	text-decoration: none;
	border-radius: 10px;
	font-weight: bold;
	font-size: 16px;
	transition: all 0.3s;
}

.dashang:hover {
	opacity: 0.8;
	padding: 15px;
	font-size: 18px;
}

.shang_close {
	float: right;
	display: inline-block;
}

.shang_logo {
	display: block;
	text-align: center;
	margin: 20px auto;
}

.shang_tit {
	width: 100%;
	height: 75px;
	text-align: center;
	line-height: 66px;
	color: #a3a3a3;
	font-size: 16px;
	background: url('/dsimg/cy-reward-title-bg.jpg');
	font-family: 'Microsoft YaHei';
	margin-top: 7px;
	margin-right: 2px;
}

.shang_tit p {
	color: #a3a3a3;
	text-align: center;
	font-size: 16px;
}

.shang_payimg {
	width: 175px;
	height: 175px;
	padding: 10px;
	border: 6px solid #EA5F00;
	margin: 0 auto;
	border-radius: 3px;
}

.shang_payimg img {
	display: block;
	text-align: center;
	width: 140px;
	height: 140px;
}

.pay_explain {
	text-align: center;
	margin: 10px auto;
	font-size: 12px;
	color: #545454;
}

.radiobox {
	width: 16px;
	height: 16px;
	background: url('https://static.runoob.com/images/dashang/radio2.jpg');
	display: block;
	float: left;
	margin-top: 5px;
	margin-right: 14px;
}

.checked .radiobox {
	background: url('https://static.runoob.com/images/dashang/radio1.jpg');
}

.shang_payselect {
	text-align: center;
	margin: 0 auto;
	margin-top: 40px;
	cursor: pointer;
	height: 60px;
	width: 280px;
}

.shang_payselect .pay_item {
	display: inline-block;
	margin-right: 10px;
	float: left;
}

.shang_info {
	clear: both;
}

.shang_info p, .shang_info a {
	color: #C3C3C3;
	text-align: center;
	font-size: 12px;
	text-decoration: none;
	line-height: 2em;
}

#footer .footer {
	text-align: center;
	font-size: 16px;
	font-weight: 4;
	maring-top: 70px;
}

a:hover {
	color: red;
	font-weight: bold;
	text-decoration: none;
	maring-left: 2px;
}
</style>
</head>

</head>

<body onload="init()"
	style="background: url('img/background.jpg'); opacity: 0.9;">
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
	<!-- 菜单导航栏 end -->

	<!-- 资源详情表 start -->
	<table class="table table-striped table-hover">

		<tr>
			<th style="text-align: center">资源名</th>
			<th style="text-align: center">路径</th>
			<th style="text-align: center">标签</th>
			<th style="text-align: center">大小</th>
			<th style="text-align: center">上传时间</th>
			<th style="text-align: center">需要积分</th>
			<th style="text-align: center">下载量</th>
			<th style="text-align: center">资源作者</th>
		</tr>

		<tr>
			<td style="text-align: center">${source.sname }</td>
			<td style="text-align: center">${source.spath }</td>
			<td style="text-align: center">${source.tag }</td>

			<td style="text-align: center"><fmt:formatNumber
					value="${source.size/1024/1024}" pattern="0.00"></fmt:formatNumber>MB
			</td>
			<td style="text-align: center">${source.uploadtime }</td>
			<td style="text-align: center">${source.integral }</td>
			<td style="text-align: center">${source.dcount }</td>
			<td style="text-align: center">${source.author }</td>
		</tr>

	</table>

	<table class="table table-striped table-hover">
		<tr>
			<th style="text-align: center;color:red;">资源描述</th>
		</tr>

		<tr>
			<td style="text-align:center;text-indent:2em;">${source.summary }</td>
		</tr>


	</table>
	<!-- 资源详情表 end -->

	<!-- 打赏 -->
	<div class="content">
		<p>
			<a href="javascript:void(0)" onclick="dashangToggle()"
				class="dashang" title="打赏，支持一下">打赏</a>
		</p>
		<div class="hide_box"></div>
		<div class="shang_box">
			<!-- 关闭 -->
			<a class="shang_close" href="javascript:void(0)"
				onclick="dashangToggle()" title="关闭"> <img
				src="https://static.runoob.com/images/dashang/close.jpg" alt="取消" />
			</a>

			<div class="shang_tit">
				<p>感谢您的支持，我会继续努力的!</p>
			</div>

			<!-- 默认付款 -->
			<div class="shang_payimg">
				<img src="img/alipay.png" alt="扫码支持" title="扫一扫" />
			</div>

			<div class="pay_explain"
				style="color:#fa10108f;font-weight:bold;margin-top: 25px;">${sessionScope.user.username }
				您好 ！扫码打赏，你说多少就多少</div>

			<!-- 更换支付方式 -->
			<div class="shang_payselect">
				<div class="pay_item checked" data-id="alipay">
					<span class="radiobox"></span> <span class="pay_logo"> <img
						src="img/alipay.jpg" alt="支付宝" />
					</span>
				</div>
				<div class="pay_item" data-id="weipay">
					<span class="radiobox"></span> <span class="pay_logo"> <img
						src="img/wechat.jpg" alt="微信" />
					</span>
				</div>
			</div>

			<div class="shang_info">
				<p>${sessionScope.user.username }
					您好 ！ 打开<span id="shang_pay_txt">支付宝</span>扫一扫，即可进行扫码打赏哦！
				</p>
				<p>
					Powered by <a href="#" target="_blank" title="ForFuture Design">ForFuture</a>，Design
					ForFuture
				</p>
			</div>
		</div>
	</div>

	<!-- 返回 -->
	<p style="text-align:center" class="dashang">
		<a href="javascript:history.go(-1)">返回</a>
	</p>

	<!-- footer 备案详情 start-->
	<footer class="navbar navbar-fixed-bottom text-center"> <span>
		<a href="http://www.ganquanzhong.top/ShareFiles/" target="blank">ForFuture</a>
		科技集团&nbsp;版权所有&copy;2018
	</span> <span>湖北省通信管理局 <img alt="公网安备" src="img/icp.png" width="20px"
		height="20px"> <a
		href="http://www.beian.gov.cn/portal/registerSystemInfo"
		target="_blank">鄂ICP备18005830号</a>
	</span> </footer>
	<!-- footer 备案详情 end -->
	<!-- 打赏javascript脚本 -->

	<script type="text/javascript">
		$(function() {
			$(".pay_item").click(
					function() {
						$(this).addClass('checked').siblings('.pay_item')
								.removeClass('checked');
						var dataid = $(this).attr('data-id');
						$(".shang_payimg img").attr("src",
								"img/" + dataid + ".png");
						$("#shang_pay_txt").text(
								dataid == "alipay" ? "支付宝" : "微信");
					});
		});
		function dashangToggle() {
			$(".hide_box").fadeToggle();
			$(".shang_box").fadeToggle();
		}
	</script>
</body>
</html>
