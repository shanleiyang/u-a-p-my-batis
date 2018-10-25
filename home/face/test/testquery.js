
var grid;
$(function() {
		var options = {};
		var url = context+"/rest/test/query";
		grid = new L.FlexGrid("dataList",url); 
		grid.init(options); //初始化datatable
		
 		// 修改
		$(document).on("click",".modify",function() {
			var data = grid.oTable.row($(this).parents("tr")).data();
			modify(data);
		});	
		// 删除
		$(document).on("click",".delete",function() {
			var data = grid.oTable.row($(this).parents("tr")).data();
			del(data);
		});
		//条件查询
	    $("#query").bind("click", query);
	    
	});
	//条件查询
	function query() {
		var name = $("#name").val();

		var url = context + "/rest/test/query";
		var param={"name":name};
		url=encodeURI(url,"utf-8"); 
	    grid.reload(url,param);
	}	
	//添加-修改
	function modify(data){
		var url = context + "/rest/test/edit";
		if(data.id!=undefined&&data.id!=""){
			url += "?"+"id="+data.id;
		}
		window.location.href = url;
	}
	//删除
	function del(data){
		var url = context + "/rest/test/delete";
		if(data.id!=undefined&&data.id!=""){
			url += "?"+"id="+data.id;
		}
		$.dialog({
			type: 'confirm',
			content: '确认删除该数据?',
			autofocus: true,
			ok: function(){window.location.href=url;},
			cancel: function(){}
		});
	}
	
	
	function rendercheckbox(data, type, full) {
		return '<input type="radio" value="' + data + '"  id="checkbox" name="checkboxlist"/>';
	};
	
	
	function renderoptions(data, type, full) {
		var editBtn = "";
		var delBtn = "";
		if(permissionHas("/rest/test/update")){
			editBtn = "<a class=\"modify\">修改</a>";
		}
 		if(permissionHas("/rest/test/delete")){
 			delBtn = "<a class=\"delete\">删除</a>";
 		}
 		if(editBtn&&delBtn){
			return editBtn+" | "+delBtn;
		}else if(editBtn){
			return editBtn;
		}else if(delBtn){
			return delBtn;
		}else{
			return "";
		}
    }
	//checkbox全选 
	function selectAll(obj,iteName){
		  if (obj.checked) {
	    	$("input[name='checkList']").each(function(){this.checked=true;}); 
	    } else {
	    	$("input[name='checkList']").each(function(){this.checked=false;}); 
	    }
	}