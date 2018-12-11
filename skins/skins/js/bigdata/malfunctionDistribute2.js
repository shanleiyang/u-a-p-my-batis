/**
 * 故障类型故障分布环图
 */
var MalfunctionDistribute = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
MalfunctionDistribute.prototype={
optionFunc : function(){ return {
	color:['RGB(246,171,0)','RGB(237,108,0)','RGB(170,207,82)',
	       'RGB(0,137,116)','RGB(108,155,210)','RGB(127,190,38)',
	       'RGB(127,16,132)','RGB(230,0,18)','RGB(0,157,133)'],
	title:{
		show:false
	},
    tooltip: {
        trigger: 'item'
    },
    legend: {
        type: 'scroll',
        orient: 'vertical',
        right: 10,
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
            name:'故障类型故障分布',
            type:'pie',
            center:['33%','50%'],
            radius: ['30%', '60%'],
            label : {
            	 normal: {
            		 show:true,
            		 position: 'outside',
            		 formatter:'{d}%',
            		 textStyle:{
 						fontSize:19,
 						fontWeight:600//,
// 						fontFamily : 'agency fb'
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
					countyCode:GlobalParma.countyCode,
					arriveBatchNo:GlobalParma.bidBatchNo
					};
			$.fn.postSubmit(GlobalParma.dataUrl,JSON.stringify(jsonData), function(data){
				if(data.success){
					if(data.data&&data.data.length>0){
						//图例数据
						var legends = new Array();
						//图表数据
						var datas = new Array();
						for(var i=0;i<data.data.length;i++){
							legends.push(data.data[i].NAME);
							datas.push({
								name:data.data[i].NAME,
								value:data.data[i].VALUE
							});
						}
						cur.option.legend.data=legends;
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
