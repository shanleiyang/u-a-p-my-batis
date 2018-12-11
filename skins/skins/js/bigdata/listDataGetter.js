/**
 * 使用ajax获取列表数据
 */
var ListDataGetter = function(){
}
ListDataGetter.prototype = {
		getData:function(url,param,success){
			if(!url){
				return [];
			}
			var rsltData=[];
			$.fn.postSubmit(url,JSON.stringify(param), function(rslt){
				if(rslt.success){
					rsltData = rslt.data;
				}else{
					$.dialog({
						 type:"alert",
						 content:rslt.msg,
						 autofocus: true
					 });
				}
			}, { async : false});
			return rsltData;
		},
		getDatas:function(url,param,success){
			if(!url){
				return [];
			}
			var success = success ? success : function(rslt){};
			$.fn.postSubmit(url,JSON.stringify(param),function(rslt){
				if(rslt.success){
					success(rslt);
				}else{
					$.dialog({
						 type:"alert",
						 content:rslt.msg,
						 autofocus: true
					 });
				}
			});
		}
};