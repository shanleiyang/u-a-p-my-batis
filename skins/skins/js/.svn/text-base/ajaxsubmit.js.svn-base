jQuery.fn.extend({
	/**get方式实现ajax交互
	 * url:请求地址
	 * paramData：请求参数，json格式
	 * success：请求成功后执行的回调方法，functon(data){}
	 * options：ajax的属性配置，json格式
	 */
		getSubmit:function(url,paramData,success, options){
			var success = success ? success : function(resdata){};
			var defaultConf = {
			          url : url,
			          type : "GET",
			          async : true,
			          contentType: "application/json",
			          dataType: "json",
			          data: paramData,
			          success : success,
			          error : function(data, textstatus){
			            $.dialog({
			               type:"alert",
			               content:data.responseText,
			               autofocus: true
			             });
			          }
			        };
			jQuery.extend(defaultConf, options);
			jQuery.ajax(defaultConf);
		},
		/**post方式实现ajax交互
		 * url:请求地址
		 * paramData：请求参数，json格式
		 * success：请求成功后执行的回调方法，functon(data){}
		 * options：ajax的属性配置，json格式
		 */
		postSubmit:function(url,paramData,success, options){
			var success = success ? success : function(resdata){};
			var defaultConf = {
			          url : url,
			          type : "POST",
			          async : true,
			          contentType: "application/json",
			          dataType: "json",
			          data: paramData,
			          success : success,
			          error : function(data, textstatus){
			            $.dialog({
			               type:"alert",
			               content:data.responseText,
			               autofocus: true
			             });
			          }
			        };
			jQuery.extend(defaultConf, options);
			jQuery.ajax(defaultConf);
		}
});
