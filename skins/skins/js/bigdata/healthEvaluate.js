/**
 * 健康度评价饼图
 */
var HealthEvaluate = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
HealthEvaluate.prototype={
		optionFunc: function(){
			return {
				//color:['#f6ab00','#ed6d00','#a9cf52','#008974','#6c9bd2','#7fbe25','#7f1085','#e60012','#009c84'],
				color:['RGB(246,171,0)','RGB(237,108,0)','RGB(170,207,82)',
				       'RGB(0,137,116)','RGB(108,155,210)','RGB(127,190,38)',
				       'RGB(127,16,132)','RGB(230,0,18)','RGB(0,157,133)'],
			    tooltip: {
			        trigger: 'item'
			    },
			    legend: {
			        type: 'scroll',
			        orient: 'vertical',
			        right: 60,
			        top: 20,
			        bottom: 20,
			        textStyle : {
			    		color : '#FFF',
			    		fontSize : 19,
			    		fontFamily : '方正兰亭黑简体'
			    	},
			        data: []
			    },
			    series: [
			        {
			            name:'健康度评价',
			            type:'pie',
			            center:['30%','50%'],
			            radius: [0, '80%'],
			            label : {
			            	 normal: {
			            		 show:true,
			            		 position: 'inner',
			            		 formatter:'{d}%',
			            		 textStyle:{
			 						fontSize:19,
			 						fontWeight:600//,
	//		 						fontFamily : 'agency fb'
			 					}
			            	 }
						},
			            labelLine: {
			                normal: {
			                    show: false
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
		showChart:function(){
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
					cityCode:GlobalParma.cityCode};
//			$.fn.postSubmit(GlobalParma.dataUrl,JSON.stringify(jsonData), function(data){
//				if(data.success){
//					alert(JSON.stringify(data.data));
//				}else{
//					$.dialog({
//						 type:"alert",
//						 content:data.msg,
//						 autofocus: true
//					 });
//				}
//			});
			var datas=[
			           [
			            ["电池没电","时钟错误","显示单元故障","时钟芯片损坏","过负荷","485接口损坏","外观损坏","时钟单元","其它（总和）"],
			            [{name:'电池没电',value:50},{name:'时钟错误',value:30},{name:'显示单元故障',value:80}]],
			            [["没电","时钟","单元故障","时钟芯片损坏","过负荷","485接口损坏","外观损坏","时钟单元","其它（总和）"],
			             [{name:'没电',value:34},{name:'时钟',value:68},{name:'单元故障',value:92}]]];
			cur.option.legend.data=datas[param][0];
			cur.option.series[0].data = datas[param][1];//设置系列数据
			cur.chart = echarts.init(cur.dom,cur.theme,cur.opts);
			cur.chart.setOption(cur.option);
		}
};
