<%@ page language="java" import="java.util.*" pageEncoding="utf-8" errorPage="error.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<title>ForFuture 资源下载</title>
		<!-- Favicons -->
		<link rel="shortcut icon" href="img/favicon.ico">
		
		<link rel="stylesheet" type="text/css" href="bootstrap/3.3.4/css/bootstrap.min.css"/>
		<link rel="stylesheet" href="bootstrap/3.3.4/css/bootstrap-theme.min.css" />
		<link rel="stylesheet" type="text/css" href="css/reset.css"/>
		<style type="text/css">
			#protocolRegular {
				font-size: 16px;
			}
			
			#myModalLabel {
				text-align: center;
				font-family: '楷体';
				color: blue;
			}
			
			#myModalContext {
				text-indent: 2em;
				font-size: 16px;
			}
			
			#myModalContext span {
				font-weight: bolder;
				color: black;
			}
			
			#myModalContext p {
				font-weight: 400;
				color: #7e7eec;
			}
		</style>		
	</head>
	<body style="background:url('img/background.jpg');opacity:0.9;">
		<nav class="navbar navbar-inverse">
			<div class="container">
				<!-- top start -->
				<%-- 动态包含 --%>
				<jsp:include page="top.jsp"></jsp:include>			
			</div>
		</nav>
		
		<!-- 文件上传
			提供form表单，method必须是post
			form表单的enctype必须是multipart/form-data
			提供input type=”file”
			
			其实form表单在你不写enctype属性时，也默认为其添加了enctype属性值，默认值是enctype=”application/x- www-form-urlencoded”
		 -->
		 
		<center style="font-size:18px;color:red;font-weight:bold;">${msg}</center>
		<div class="container">
			<form onsubmit="return checkProtocol()" class="form-horizontal form-upload" method="post" 
					enctype="multipart/form-data" action="<%=basePath%>UploadFileServlet.action">
				<h3>资源上传</h3>
				<hr />
				<div class="form-group">
					<input type="file" name="files" id="files" style="display: none;" />
					<div class="col-md-6 col-md-offset-2">
						<button type="button" id="selectFile" class="btn btn-info btn-lg">
							<span class="glyphicon glyphicon-plus"></span>
							选择文件
						</button>
						<small id="fname-box">
							未选择文件,可以上传
							<span class="text-danger">50M</span>
							以内的文件
						</small>
					</div>
				</div>	
				<div class="form-group">
					<label class="col-md-2 control-label">资源名称</label>
					<div class="col-md-6">
						<input type="text" class="form-control" name="sname" required="required" placeholder="资源名称"/>
					</div>
				</div>	
				<div class="form-group">
					<label class="col-md-2 control-label">关键词(标签)</label>
					<div class="col-md-6">
						<input type="text" class="form-control" name="tag" required="required" placeholder="多个关键词使用&quot;,&quot;号隔开"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">下载积分</label>
					<div class="col-md-4">
						<input type="number" placeholder="积分规则0-20分" required="required" class="form-control" min="0" max="20" name="integral"/>
					</div>
				</div>
				<div class="form-group">
					<label class="col-md-2 control-label">资源描述</label>
					<div class="col-md-6">
						<textarea class="form-control" rows="3" name="summary" required="required"
							placeholder="请输入资源描述，不支持HTML标签，请详细填写以便于作为推荐资源以赚取更多积分，如描述不清可能会无法审核通过!【ForFuture Share】"></textarea>
					</div>
				</div>	
				<div class="form-group">
					<div class="col-md-6 col-md-offset-2">
						<div class="checkbox">
						  <label>
						    <input type="checkbox" value="" id="protocol">
						  	 	同意<a href="#" id="protocolRegular" data-toggle="modal" data-target="#myModal">ForFuture Share资源共享规则</a>
						  </label>
						</div>
					</div>
				</div>	
				<div class="form-group">
					<div class="col-md-8 text-right">
						<input type="submit" class="btn btn-primary" value="上传" />
					</div>
				</div>	
			</form>
		</div>
		
		<!-- 弹窗协议 -->
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false">
		    <div class="modal-dialog">
		        <div class="modal-content">
		            <div class="modal-header">
		                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
		                <h4 class="modal-title" id="myModalLabel">ForFuture Share资源共享规则</h4>
		            </div>
		            <div class="modal-body" id="myModalContext">
		            <p>
		            1、ForFuture Share（包括ForFuture Share网站与相应客户端、自媒体平台、主运营网站等）是为广大用户提供资源
					            （包括但不限于文章、文档、音频、视频、图片、课程、软件、源代码等相关资源）共享的网络存储平台，为确保平台依法合规运营，
					            保证广大用户能够正常便捷地分享、使用频道资源，用户上传的所有资源都不得违反国家法律法规。具体如下：
     					 不得上传中华人民共和国法律、法规、规章、条例以及国家政策所禁止或限制的资源或内容，包括但不限于以下情形：
     					<br>
						<span>1）反对宪法所确定的基本原则；</span><br>
						<span>2）危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的；</span><br>
						<span>3）损害国家荣誉和利益的；</span><br>
						<span>4）煽动民族仇恨、民族歧视、破坏民族团结的；</span><br>
						<span>5）破坏国家宗教政策，宣扬邪教和封建迷信的；</span><br>
						<span>6）散布谣言，扰乱社会秩序，破坏社会稳定的；</span><br>
						<span>7）散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；</span><br>
						<span>8）侮辱或者诽谤他人，侵犯他人合法权利的；</span><br>
						<span>9）煽动非法集会、结社、游行、示威、聚众扰乱社会秩序的；</span><br>
						<span>10）以非法民间组织名义活动的；</span><br>
						<span>11）含有虚假、有害、胁迫、侵犯他人隐私、骚扰、侵权、中伤、粗俗、猥亵、或其它道德上令人反感的内容的；</span><br>
						<span>12）含有中国法律、法规、规章、条例以及国家政策禁止或限制内容的。</span><br>
					</p>
					<p>2、ForFuture Share经营者有权根据自身及互联网的发展和中华人民共和国法律、法规及规范性文件的变化，不断修改和完善本规则的相关条款。
					ForFuture Share经营者保留随时修改本规则的权利。用户继续使用ForFuture Share及其提供的服务，
					即视为同意并自愿遵守本规则条款及其最新版本，否则，用户应终止使用ForFuture Share及其提供的相关服务。</p>
					<p>3、用户在ForFuture Share注册，并在ForFuture Share上传或下载相源的，即视为用户已详细阅读本规则，并同意完全遵守本规则的全部内容。</p>
					<p>4、本规则的最终解释权归ForFuture Group有限公司所有。</p>
					
					</div>
		            <div class="modal-footer">
		                <button type="button" id="readRegular" class="btn btn-primary" data-dismiss="modal">阅读并同意</button>
		            </div>
		        </div><!-- /.modal-content -->
		    </div><!-- /.modal -->
		</div>
		
		
		<footer class="navbar navbar-fixed-bottom text-center">
			<span><a href="http://ganquanzhong.top/mynews/" target="blank">ForFuture News</a></span>
			<br>
			<span><a href="#" target="blank">ForFuture</a>科技集团&nbsp;版权所有&copy;2018-2020</span>
		</footer>
		<script src="js/jquery-1.11.0.js"></script>
		<script type="text/javascript" src="bootstrap/3.3.4/js/bootstrap.min.js" ></script>
		<script>
			var regularFlag=false;//标记是否阅读协议
		
			$(function(){
				$('#selectFile').click(function(){
					$('#files').click();
				});
				
				$('#files').change(function(){
					var file = $(this).val();
					$('#fname-box').text(file);
				});
				
				
			})
			
			$('#readRegular').click(function(){
				document.getElementById("protocol").checked=true;
				regularFlag=true;
			});
			
			
			function checkProtocol(){
				var inputobj=document.getElementById("protocol");
				if(!inputobj.checked){
					
					alert("请[agree]同意ForFuture资源共享协议!");
					return false;
				}
				if(!regularFlag){
					alert("请[read]阅读ForFuture资源共享协议!");
					return false;
				}
			}
		</script>
	</body>
</html>
