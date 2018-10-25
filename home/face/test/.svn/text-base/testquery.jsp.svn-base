<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>列表信息</title>
	<!-- 引入css文件 -->
	<uap:asset path="bootstrap.css,font-awesome.css,form.css,datatables.css"/>
	<style>
		.container {
			width: 100%;
		};
		/*距离顶部的外边距*/
		.topdist {
			margin-top: 10px;
		}
		/*设置条件查询区域的样式*/
		.form-inline{
			margin-left: 0px;
			margin-bottom: 5px;
		}
		/*重写页面中row的样式*/
		.row {
			margin: 0px;
		}
		/*datatables无记录时的显示样式*/
		.table>tbody>tr>td.dataTables_empty {
			height: 200px;
			vertical-align: middle;
		}
		/*datatables表头中单元格底部边框的粗细设置*/
		.table-bordered>thead>tr>td, .table-bordered>thead>tr>th{
			border-bottom-width: 1px;
		}		
		/*datatables表头中单元格底部边框的粗细设置*/
		.dbtn {
			border-style: none;
			background: transparent;
		}
		/*多条件搜索文字与图标距离的设置*/
		a > i {
			margin-left: 5px;
		}
		/*高级搜索弹出框的大小*/
		div.popover {
			min-width: 500px;
		}		
		/*表单中表单组间距*/
		.form-group {
			margin-bottom: 10px !important;
		}
		/*重写分页插件的背景颜色*/
		.pagination>.active>a,
		.pagination>.active>a:focus,
		.pagination>.active>a:hover, 
		.pagination>.active>span, 
		.pagination>.active>span:focus, 
		.pagination>.active>span:hover {
			background-color: #32a5e6;
			border-color: #32a5e6;
		}
	</style>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<uap:ui js='html5shiv.js'/>"></script>
      <script src="<uap:ui js='respond.js'/>"></script>
    <![endif]-->
	<!-- 引入js文件 -->
	<uap:asset path="jquery.js,bootstrap.js,form.js,datatables.js,my-framework.js,ui.js"/>
	<script type="text/javascript">	
		var context="<%=request.getContextPath()%>";
	</script>
	<script type="text/javascript" src="./testquery.js"></script>
</head>
<body>
	<div class="topdist"></div>
	<div class="container">
		<div class="row">
			<form class="form-inline" onsubmit="return false;">											
				<div class="input-group pull-left" style="margin-right: 5px">
			    	<input class="form-control ue-form" type="text" id="name" placeholder="搜索 姓名"/>
				</div>
				<div class="input-group pull-left">
					<button id="search" class="btn ue-btn pull-left">搜索</button>
				</div>
			</form>
		</div>
		<div class="row">
			<table id="dataList" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th width="3%" data-field="id" data-sortable= "false" data-render="rendercheckbox">
						</th>	
						<th width="5%" data-field="name" data-sortable="false">姓名</th>
						<th width="5%" data-field="id" data-render="renderoptions" data-sortable="false">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>