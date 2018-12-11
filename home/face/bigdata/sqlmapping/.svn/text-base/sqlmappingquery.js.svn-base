
var grid;
$(function() {
		var options = {};
		var url = context+"/rest/bigdata/sqlMapping/query";
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
	    $("#search").bind("click", query);
	    $("#addBtn").bind("click", modify);
	    $("#updateBtn").bind("click", modifyChk);
	    $("#delBtn").bind("click", delChk);
	    $("#refreshBtn").bind("click",refreshCache);
	});
	//条件查询
	function query() {
		var tagNo = $("#tagNo").val();
		var tagName = $("#tagName").val();

		var url = context + "/rest/bigdata/sqlMapping/query";
		var param={"tagNo":tagNo,"tagName_$like":tagName};
		url=encodeURI(url,"utf-8"); 
	    grid.reload(url,param);
	}	
	//添加-修改
	function modify(data){
		var url = context + "/rest/bigdata/sqlMapping/edit";
		if(data!=undefined&&data.id!=undefined&&data.id!=""){
			url += "?"+"id="+data.id;
		}
		window.location.href = url;
	}
	//删除
	function del(data){
		var url = context + "/rest/bigdata/sqlMapping/delete";
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
		return '<input type="checkbox" value="' + data + '"  id="checkbox" name="checkboxlist"/>';
	};
	
	
	function renderoptions(data, type, full) {
		var editBtn = "";
		var delBtn = "";
		editBtn = "<a class=\"modify\">修改</a>";
		delBtn = "<a class=\"delete\">删除</a>";
//		if(permissionHas("/home/rest/bigdata/sqlMapping/edit")){
//		}
// 		if(permissionHas("/home/rest/bigdata/sqlMapping/delete")){
// 		}
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

	//修改选中记录
	function modifyChk(){
		var data = grid.getSelectedData();
		if(data.length != 1){
			$.dialog({
				 type:"alert",
				 content:"请选择一条记录！",
				 autofocus: true
			 });
			return false;
		}
		var url = context + "/rest/bigdata/sqlMapping/edit";
		if(data[0].id!=undefined&&data[0].id!=""){
			url += "?"+"id="+data[0].id;
		}
		window.location.href = url;
	}
	
	//删除
	function delChk(){
		var data = grid.getSelectedData();
		if(data.length < 1){
			$.dialog({
				 type:"alert",
				 content:"请至少选择一条记录！",
				 autofocus: true
			 });
			return false;
		}
		var url = context + "/rest/bigdata/sqlMapping/batchDelete";
		var ids = new Array();
		for(var i=0;i<data.length;i++){
			ids.push(data[i].id);
		}
		if(ids.length>0){
			url += "?id="+ids.join("&id=");
		}
		$.dialog({
			type: 'confirm',
			content: '确认删除数据,删除后无法恢复?',
			autofocus: true,
			ok: function(){window.location.href=url;},
			cancel: function(){}
		});
	}
	//刷新缓存
	function refreshCache(){
		$.fn.postSubmit(context + "/rest/bigdata/sqlMapping/refreshCache",null, function(data){
			if(data.success){
				$.dialog({
					type:"alert",
					content:"刷新缓存已完成",
					autofocus: true
				});
			}
		});
	}