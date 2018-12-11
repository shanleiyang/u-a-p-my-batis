<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>控件查询SQL映射编辑</title>
<uap:asset path="bootstrap.css,font-awesome.css,form.css" />
	<style>
		textarea {border:1px solid #ddd;}
		
		/* :样式             */
		.span_style{
			color: gray;
			line-height: 20px;
			font-size: 20px;
			position: absolute;
			top: 5px;
			right: 4px;
		}
		
		.myclass {
			width:80px;
		}
		
		.checkMsg{
			height:20px;
			min-height:20px;
			line-height:20px;
		}
	</style>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="<uap:ui js='html5shiv.js'/>"></script>
  <script src="<uap:ui js='respond.js'/>"></script>
<![endif]-->

<script  type="text/javascript" src="<uap:ui js='jquery.js'/>"></script>
<script  type="text/javascript" src="<uap:ui js='bootstrap.js'/>"></script>
<script  type="text/javascript" src="<uap:ui js='form.js'/>"></script>
<script  type="text/javascript" src="<uap:ui js='ui.js'/>"></script>
<script  type="text/javascript" src="<uap:ui js='jquery.blockUI.min.js'/>"></script>
<script  type="text/javascript" src="<uap:ui js='vue.min.js'/>"></script>
<!-- <script  type="text/javascript" src="<uap:ui js='ajaxsubmit.js'/>"></script> -->
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/bigdata/sqlmapping/sqlmappingedit.js"></script>
</head>
<body>
	<div class="container" id="sandbox-container">
	  <div class="col-xs-12 col-md-12" >	  
		<form class="form-horizontal" id="saveForm" name="saveForm"  onsubmit="return false" role="form">
		<div class="form-group" >
			<div class="btn-toolbar pull-right" role="toolbar">
				<div class="btn-group btn-group-sm" role="group">
					<button type="button" class="btn ue-btn myclass" id="saveBtn" >保存</button>
				</div>
				<div class="btn-group btn-group-sm" role="group">
					<button type="button" class="btn ue-btn myclass" id="returnBtn">返回</button>
				</div>
			</div>
		</div>
			<input type="hidden" v-model="id" id="id" name="id"/>
			<div class="form-group" >
				<label class="col-xs-3 col-md-3 control-label">控件编号<span class="required">*</span></label>
				<div class="col-xs-9 col-md-9" >
   					<input name="tagNo" class="form-control ue-form" type="text" v-model="tagNo" placeholder="请输入控件编号" datatype="tagNo" errormsg="不能超过16个字符！" nullmsg="请输入控件编号！" >
					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>
			<div class="form-group" >
				<label class="col-xs-3 col-md-3 control-label">控件名称<span class="required">*</span></label>
				<div class="col-xs-9 col-md-9" >
   					<input name="mappingName" class="form-control ue-form" type="text" v-model="tagName" placeholder="请输入控件名称" datatype="tagName" errormsg="不能超过64个字符！" nullmsg="请输入控件名称！" >
					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>
			<div class="form-group" >
				<label class="col-xs-3 col-md-3 control-label">查询SQL<span class="required">*</span></label>
				<div class="col-xs-9 col-md-9" >
   					<textarea name="querySql" class="form-control ue-form" rows="15" cols="100" placeholder="请输入查询SQL（勿附带分号）" v-model="querySql"  datatype="querySql" errormsg="不能超过4000个字符！" nullmsg="请输入查询SQL！">
   					</textarea>
					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>
			<div class="form-group" >
				<label class="col-xs-3 col-md-3 control-label">SQL参数描述</label>
				<div class="col-xs-9 col-md-9" >
   					<textarea name="sqlParamsDesc" class="form-control ue-form" rows="10" cols="100" placeholder="请输入SQL参数描述" v-model="sqlParamsDesc" datatype="sqlParamsDesc" errormsg="不能超过256个字符！" >
   					</textarea>
   					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>
			<div class="form-group" >
				<label class="col-xs-3 col-md-3 control-label">备注</label>
				<div class="col-xs-9 col-md-9" >
   					<textarea name="remark" class="form-control ue-form" rows="10" cols="100" placeholder="请输入备注" v-model="remark" datatype="remark" errormsg="不能超过256个字符！" >
   					</textarea>
   					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>		
		</form>
		<input type="hidden" name="_querySql_" id="_querySql_"  value='${fn:escapeXml(data.querySql)}'/>
		<input type="hidden" name="_sqlParamsDesc_" id="_sqlParamsDesc_" value="${data.sqlParamsDesc}"/>
		<input type="hidden" name="_remark_" id="_remark_" value="${data.remark}"/>
		</div>
	</div>
	<script type="text/javascript">
var saveFormApp = new Vue({
	el:"#saveForm",
	data:{
		id:"${data.id}",
		tagNo:"${data.tagNo}",
		tagName:"${data.tagName}",
		querySql:$("#_querySql_").val(),
		sqlParamsDesc:$("#_sqlParamsDesc_").val(),
		remark:$("#_remark_").val()
	}
});
	
</script>
</body>

</html>