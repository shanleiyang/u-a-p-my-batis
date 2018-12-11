/**
 * this javascript toolkit class
 * by sh_leiyang
 * date 2017/12/18
 */
 
 /**
  * simulate java's StringBuffer
  * by sh_leiyang
  * date 2017/12/18
  */
function StringBuffer(){
	this._strings_ = new Array();
}
StringBuffer.prototype.append = function(str){
	this._strings_.push(str);
};
StringBuffer.prototype.toString = function(){
	return this._strings_.join("");
};

/**
 * simulate java's HashMap
 * by sh_leiyang
 * date 2017/12/18
 */
function HashMap(){
	this.size=0;
	this.entry = new Object();
}
HashMap.prototype.put = function(key,value){
	if(!this.containsKey(key)){
		this.size++;
	}
	this.entry[key]=value;
};
HashMap.prototype.get=function(key){
	return this.containsKey(key)?this.entry[key]:null;
};
HashMap.prototype.remove=function(key){
	if(this.containsKey(key)&&(delete this.entry[key])){
		this.siz--;
	}
};
HashMap.prototype.containsKey=function(key){
	return (key in this.entry);
};
HashMap.prototype.size=function(){
	return this.size;
};
HashMap.prototype.clear=function(){
	this.size=0;
	this.entry=new Object();
};
HashMap.prototype.keys=function(){
	var keys=new Array();
	for(var prop in this.entry){
		keys.push(prop);
	}
	return keys;
};
//存储字典项的值
var toolkitDictItemMap = new HashMap();

var utils = {
		/**
		 * 时间格式化，long转datestring
		 * @param timestamp
		 * @param formatstr
		 */
		dateformat_long2string : function (timestamp, formatstr) {
			if(!timestamp || !formatstr || isNaN(timestamp)) {
				return timestamp;
			}
			var date = new Date(parseInt(timestamp));
			var o = {  
			        "M+": date.getMonth() + 1,  
			        "d+": date.getDate(), 
			        "h+": (date.getHours() % 12 == 0 ? 12 : date.getHours() % 12), //12小时
			        "H+": date.getHours(), //24小时
			        "m+": date.getMinutes(),  
			        "s+": date.getSeconds(),  
			        "q+": Math.floor((date.getMonth() + 3) / 3),  
			        "S": date.getMilliseconds()  
			    }
			//周
			var week = {
			    "0": "日",
			    "1": "一",
			    "2": "二",
			    "3": "三",
			    "4": "四",
			    "5": "五",
			    "6": "六"
			};
			// 年
			if (/(y+)/.test(formatstr)) {  
				formatstr = formatstr.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));  
			}  
			// 星期
			if (/(E+)/.test(formatstr)) {
				formatstr = formatstr.replace(RegExp.$1, ((RegExp.$1.length > 1) ? (RegExp.$1.length > 2 ? "星期" : "周") : "") + week[date.getDay() + ""]);
			}
			for (var k in o) {  
			    if (new RegExp("(" + k + ")").test(formatstr)) {  
			    	formatstr = formatstr.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
			    }  
			}  
			return formatstr;  
		}
		/**
		 * 字典转换
		 * @param data:需转换的值
		 * @param code:字典项
		 */
		,dictItem : function(data, code) {
			if (null != data && ""!=(''+data)) {
				var dictdata;
				// 判断是否已存在字典项
				if (toolkitDictItemMap.get("toolkit.framework.dictitems." + code)) {
					// 获取字典项
					dictdata = toolkitDictItemMap.get("toolkit.framework.dictitems." + code);
					// 遍历字典项 获取字典code和value
					for ( var i in dictdata) {
						var code = dictdata[i].itemCode;
						var text = dictdata[i].itemValue;
						// 判断当前表格的值在字典项中是否存在 如果存在返回当前code的value
						if (code == data) {
							return text;
						}
					}
				} else {
					$.ajax({
				          url : context + "/../home/rest/dictitem/data",
				          type : "POST",
				          async : false,
				          contentType: "application/json",
				          dataType: "json",
				          data: "{\"dictCode\":\""+code+"\"}",
				          success : function(data){
				        	  dictdata = data;
				        	  // 将结果集保存到全局toolkitDictItemMap对象中
				        	  toolkitDictItemMap.put("toolkit.framework.dictitems." + code, dictdata);
				          },
				          error : function(data, textstatus){
				        	  console.log(data);
				          }
				        });
					
					for ( var i in dictdata) {
						var code = dictdata[i].itemCode;
						var text = dictdata[i].itemValue;
						if (code == data) {
							return text;
						}
					}
				}
				return data;
			}
			return "";
		}
		/**
		 * 鼠标移动上时的提示文字
		 * @param obj:需要提示的对象
		 */
		,tooltipText : function(obj){
		    var x = 10;
		    var y = 20;
		    jQuery(obj).mouseover(function(e){
		        this.myTitle = jQuery(this).attr("titledata");
		        if(!this.myTitle)
		        	return;
		        var tooltip = "<div id='tooltip_top' style='position:absolute;border:0px solid #e6e6e6;background:#000;padding:5px;color:#e6e6e6;display:none;font-size: 22px;z-index:9999;'>"+ this.myTitle +"<\/div>"; //创建 div 元素 文字提示
		        jQuery("body").append(tooltip);    //把它追加到文档中
		        jQuery("#tooltip_top").css({
					                "top": (e.pageY+20) + "px",
					                "left": (e.pageX+10)  + "px"
				            	}).show("fast");      //设置x坐标和y坐标，并且显示
		    }).mouseout(function(){
		    	jQuery("#tooltip_top").remove();    //移除 
		    }).mousemove(function(e){
		    	jQuery("#tooltip_top").css({
					                "top": (e.pageY+20) + "px",
					                "left": (e.pageX+10)  + "px"
					            });
		    });
		},
};