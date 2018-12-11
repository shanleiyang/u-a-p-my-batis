/**
 * 故障率健康度一致性环图
 */
var BdFrHrConsistency = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
BdFrHrConsistency.prototype={
		optionFunc : function(){ return {
			//color:['#f6ab00','#ed6d00','#a9cf52','#008974','#6c9bd2','#7fbe25','#7f1085','#e60012','#009c84'],
			color:['#069C7F','#FFA500'],
			
		    tooltip: {
		        trigger: 'item'
		    },
		    
		    series: [
		        {
		            name:'故障率健康度一致性',
		            type:'pie',
		            center:['50%','50%'],
		            radius: ['60%', '80%'],
		            startAngle:0,
		            label : {
		            	 normal: {
		            		 show:true,
		            		 position: 'outside',
		            		 formatter:'{d}\n{b}',
		            		 textStyle:{
		 						fontSize:21,
		 						fontWeight:600,
		 						fontFamily : '方正兰亭黑简体'
		 					}
		            	 }
					},
		            labelLine: {
		                normal: {
		                    show: true
		                }
		            },
		            data:[]
		        }
		    ]
		};
},
reRender:function(){
	var cur = this;
	if(cur.chart && cur.chart.dispose){
		cur.chart.dispose();
	}
	cur.chart = echarts.init(cur.dom,cur.theme,cur.opts);
	cur.chart.setOption(cur.option);
	
},
		showChart:function(param){
			var cur = this;
			if(!cur.option){
				cur.option = cur.optionFunc();
			}
			if(cur.chart && cur.chart.dispose){
				cur.chart.dispose();
			}
			var jsonData = {
					"_TAGNO":this.tagNo,
					year:GlobalParma.queryYear,
					month:GlobalParma.queryMonth,
					provinceNo:GlobalParma.provinceNo,
					cityCode:GlobalParma.cityCode,
					countyCode:GlobalParma.countyCode
					};
			$.fn.postSubmit(GlobalParma.dataUrl,JSON.stringify(jsonData), function(data){
				if(data.success){
					if(data.data&&data.data.length>0){
						//图表数据
						var datas = new Array();
						datas.push({
							name:'一致',
							value:data.data[0].CONS_NUM
						});
						datas.push({
							name:'不一致',
							value:data.data[0].UNCONS_NUM
						});
						
						cur.option.series[0].data = datas;//设置系列数据
						cur.chart = echarts.init(cur.dom,cur.theme,cur.opts);
						cur.chart.setOption(cur.option);
					}
				}else{
					$.dialog({
						 type:"alert",
						 content:data.msg,
						 autofocus: true
					 });
				}
			});
		}
};
