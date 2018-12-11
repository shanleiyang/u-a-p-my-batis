$(function() {
		$("#saveForm").uValidform({
			btnSubmit:"#saveBtn",
			tiptype:function(msg,o,cssctl){
				if(!o.obj.is("form")){
					var objtip=o.obj.siblings(".Validform_checktip");
					//根据单复选框的DOM结构查找其验证结果框
					if(objtip.length == 0){
						if(o.obj.parent("div").length != 0){   //普通文本框
							objtip=o.obj.parents("div").siblings(".Validform_checktip");
						}else if(o.obj.parent("label").length != 0){
							objtip=o.obj.parent("label").siblings(".Validform_checktip"); //单复选框
					    }
					}
					 cssctl(objtip,o.type);
					 objtip.text(msg);
				} else{
					 var objtip=o.obj.find("#msgdemo");
					cssctl(objtip,o.type);
					objtip.text(msg);
				} 
			},
			datatype:{//传入自定义datatype类型;
				"tagNo":function(gets, obj, curform, regxp){
					var getsLen = String(gets).replace(/[^\x00-\xff]/g,'**');
			    	  if(getsLen.length<1||getsLen.length>16){
				    		 return "控件编号为1-16个字符!"; 
				    	  }
			    	  var reg_isQuoteIn = /\"|\'|\\u201c|\\u201d|\\u2018|\\u2019|\%|\$|\\uffe5|\\u2026\u2026|\\u00b7|\\u2014|\-|\\u2014\u2014|\!|\\uff01|\#|\@|\~|\^|\*|\,|\.|\\uff0c|\\u3002|\?|\\uff1f|\;|\\uff1b|\:|\\uff1a|\{|\}|\\|\/|\[|\]|\(|\)|\+|\-|\\u3001|\||\<|\>/;
			    	  if(String(gets).match(reg_isQuoteIn)){
			    		  return "控件编号不能包含引号或单引号!";
			    	  }
			    	  var reg_isChinese =  /[\u4E00-\u9FA5]/;
			    	  if(String(gets).match(reg_isChinese)){
			    		  return "控件编号不能包含汉字!";
			    	  }
			    	  return true;
			      },
				"tagName":function(gets, obj, curform, regxp){
					var getsLen = String(gets).replace(/[^\x00-\xff]/g,'**');
			    	  if(getsLen.length<1||getsLen.length>64){
				    		 return "控件名称为1-64个字符!"; 
				    	  }
			    	  var reg_isQuoteIn = /\"|\'|\\u201c|\\u201d|\\u2018|\\u2019|\%|\$|\\uffe5|\\u2026\u2026|\\u00b7|\\u2014|\-|\\u2014\u2014|\!|\\uff01|\#|\@|\~|\^|\*|\,|\.|\\uff0c|\\u3002|\?|\\uff1f|\;|\\uff1b|\:|\\uff1a|\{|\}|\\|\/|\[|\]|\(|\)|\+|\-|\\u3001|\||\<|\>/;
			    	  if(String(gets).match(reg_isQuoteIn)){
			    		  return "控件名称不能包含引号或单引号!";
			    	  }
			      },
				"querySql":function(gets, obj, curform, regxp){
					var getsLen = String(gets).replace(/[^\x00-\xff]/g,'**');
			    	  if(getsLen.length<1||getsLen.length>4000){
				    		 return "查询SQL为1-4000个字符!"; 
				    	  }
//			    	  if(String(gets).match( /\“|\"/)){
//			    		  return "查询SQL不能包含双引号!";
//			    	  }
			    	  if(String(gets).match( /\;|\；/)){
			    		  return "查询SQL不能包含分号!";
			    	  }
			      },
				"sqlParamsDesc":function(gets, obj, curform, regxp){
					var getsLen = String(gets).replace(/[^\x00-\xff]/g,'**');
					if(getsLen.length>256){
			    		 return "SQL参数描述不能超过256个字符!"; 
			    	  }
			    	if(String(gets).match(/\“|\"/)){
			    		return "SQL参数描述不能包含双引号!";
			    	}
			      },
				"remark":function(gets, obj, curform, regxp){
					var getsLen = String(gets).replace(/[^\x00-\xff]/g,'**');
					if(getsLen.length>256){
			    		 return "备注不能超过256个字符!"; 
			    	  }
					var reg_isQuoteIn = /\"|\'|\\u201c|\\u201d|\\u2018|\\u2019|\%|\$|\\uffe5|\\u2026\u2026|\\u00b7|\\u2014|\-|\\u2014\u2014|\!|\\uff01|\#|\@|\~|\^|\*|\,|\.|\\uff0c|\\u3002|\?|\\uff1f|\;|\\uff1b|\:|\\uff1a|\{|\}|\\|\/|\[|\]|\(|\)|\+|\-|\\u3001|\||\<|\>/;
			    	if(String(gets).match(reg_isQuoteIn)){
			    		return "备注不能包含引号或单引号!";
			    	}
			    	return true;
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
			window.location = context + "/rest/bigdata/sqlMapping";
		});	
	});
	
	//保存实例
	function save(){
		saveForm.action = context + "/rest/bigdata/sqlMapping/save";
		saveForm.method = "POST";
		saveForm.submit();
//		var url=context + "/rest/bigdata/sqlMapping/save";
//		$.fn.postSubmit(url,saveFormApp._data, function(data){
//		});
	}
