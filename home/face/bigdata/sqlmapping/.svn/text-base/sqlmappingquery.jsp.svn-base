<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>控件查询SQL映射查询 </title>
	<!-- 引入css文件 -->
	<uap:asset path="bootstrap.css,font-awesome.css,form.css,datatables.css"/>
	<style>
		a{cursor:pointer;}
	</style>
	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="<uap:ui js='html5shiv.js'/>"></script>
      <script src="<uap:ui js='respond.js'/>"></script>
    <![endif]-->
	<!-- 引入js文件 -->
	<uap:asset path="jquery.js,bootstrap.js,form.js,datatables.js,my-framework.js,ui.js"/>
	<script  type="text/javascript" src="<uap:ui js='ajaxsubmit.js'/>"></script>
	<script type="text/javascript">	
		var context="<%=request.getContextPath()%>";
	</script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/bigdata/sqlmapping/sqlmappingquery.js"></script>
</head>
<body>
	<div class="topdist"></div>
	<div class="container">
		<div class="row">
			<div class="btn-group pull-right" role="group">
					<button id="refreshBtn" class="btn ue-btn-primary" ><span class="fa fa-refresh"></span>刷新缓存</button>
					<button id="addBtn" class="btn ue-btn-primary" ><span class="fa fa-plus"></span>增加</button>
					<button id="updateBtn" class="btn ue-btn-primary"><span class="fa fa-pencil"></span>修改</button>
					<button id="delBtn" class="btn ue-btn-warning "><span class="fa fa-minus"></span>删除</button>
			</div>
		</div>
		<div class="row">
			<form class="form-inline" onsubmit="return false;">		
				<div class="input-group pull-left" style="margin-right: 5px">
			    	<input class="form-control ue-form" type="text" id="tagNo" placeholder="搜索 控件编号"/>
				</div>
				<div class="input-group pull-left" style="margin-right: 5px">
			    	<input class="form-control ue-form" type="text" id="tagName" placeholder="搜索 控件名称"/>
				</div>
				<div class="input-group pull-left">
					<button id="search" class="btn ue-btn pull-left"><span class="fa fa-search"></span> 搜索</button>
				</div>
			</form>
		</div>
		<div class="row">
			<table id="dataList" class="table table-bordered table-hover">
				<thead>
					<tr>
						<th width="3%" data-field="id" data-sortable= "false" data-render="rendercheckbox">
						<input type="checkbox">
						</th>	
						<th width="5%" data-field="tagNo" data-sortable="true">控件编号</th>
						<th width="5%" data-field="tagName" data-sortable="true">控件名称</th>
						<th width="5%" data-field="querySql" data-sortable="true">查询SQL</th>
						<th width="5%" data-field="sqlParamsDesc" data-sortable="true">SQL参数描述</th>
						<th width="5%" data-field="id" data-render="renderoptions" data-sortable="false">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
</html>