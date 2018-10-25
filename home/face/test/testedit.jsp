<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/tags/web-ui" prefix="uap"%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加/修改</title>
<uap:asset path="bootstrap.css,font-awesome.css,form.css" />
	<style>
		/*表单中日期组件图标样式配覆盖width:auto*/
		.date-input-btn {
			width: 1%;
		}
		
		/*设置标题的文字样式*/
		.htext {
			font-size: 14px;
			font-weight: bold;
		}
		textarea {border:1px solid #ddd;}
		/*设置分割线的上下间距*/
		hr.fenge{
			margin-top: 10px;
			margin-bottom: 10px;
		}
		
		/* :样式             */
		.span_style{
			color: gray;
			line-height: 20px;
			font-size: 20px;
			position: absolute;
			top: 5px;
			right: 4px;
		}
		
		/*表单中表单组间距*/
		.form-group {
			margin-bottom: 10px !important;
		}
		
		.myclass {
			width:80px;
		}
		
		.Validform_checktip {
			font-size: 14px;
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
<script type="text/javascript">
	var context = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="./testedit.js"></script>
</head>
<body>
	<div class="container" id="sandbox-container">
	<br/> 
	  <div class="col-xs-12 col-md-12">	  
		<form styleClass="form-horizontal" id="saveForm" name="saveForm"  onsubmit="return false">
			<input type="hidden" value="${data.id}" id="id" name="id"/>
			<div class="form-group" >
				<label class="col-xs-3 col-md-3 control-label">姓名<span class="required">*</span></label>
				<div class="col-xs-9 col-md-9">	
					<c:choose>
				 		<c:when test="${empty data.id}">			
							<input type="text" class="ue-form" id="name"
								name="name" value="${data.name}" datatype="name" placeholder="姓名" errormsg="不能超过32个字符！" nullmsg="请填写姓名！"/>						
						</c:when>
				 		<c:otherwise>
				 			<input type="text" class="ue-form" id="name" name="name" datatype="name" placeholder="姓名" value="${data.name}" 
				 				errormsg="不能超过32个字符！" nullmsg="请填写姓名！" />	
				 	    </c:otherwise>
					</c:choose>	
					<span class="Validform_checktip Validform_span"></span>
				</div>
			</div>
			
			<div class="form-group" style="padding-bottom: 40px">
			  <label class="col-xs-2 col-md-2 control-label"></label>
			  <div class="col-xs-8 col-md-8">
					<button type="button" class="btn ue-btn-primary myclass" id="saveBtn" >
						保存
					</button>
					<button type="button" class="btn ue-btn myclass" id="returnBtn">
						 返回
					</button>
			  </div>
			</div>
		</form>
		</div>
	</div>
</body>
</html>