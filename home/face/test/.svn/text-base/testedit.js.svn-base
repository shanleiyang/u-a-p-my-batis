$(function() {
		//时间选择器插件：格式   年-月-日 ，具体设置请百度：datetimepicker
		$('#birthdate').datetimepicker({
		    language: "zh-CN",
		    autoclose: 1,
		    startView: 3,
		    minView: 2,
		    maxView: 4,
		    endDate: new Date(),
		    bootcssVer:3,
		    format: "yyyy-mm-dd"
		});
		$("#saveForm").uValidform({
			btnSubmit:"#saveBtn",
			datatype:{//传入自定义datatype类型;
			      "name":function(gets, obj, curform, regxp){
			    	  if(gets.length<1||gets.length>32){
				    		 return "名字为1-32个字符!"; 
				    	  }
				    	  var reg1 = /^[\w\u4e00-\u9fa5]+$/g;
				    	  if(!String(gets).match(reg1)){
				    		  return "请输入汉字、英文字母、数字!";
				    	  }
			      }
			},
			callback:function(form){
				$.dialog({
					type: 'confirm',
					content: '您确定要提交表单吗？',
					ok: function(){save();},
					cancel: function(){}
				});
			}
		});				 
		//返回列表页面
		$("#returnBtn").click(function() {
			window.location = context + "/home/rest/test";
		});	
	});
	
	//保存实例
	function save(){
		saveForm.action = context + "/home/rest/test/save";
		saveForm.method = "POST";
		saveForm.submit();
	}
