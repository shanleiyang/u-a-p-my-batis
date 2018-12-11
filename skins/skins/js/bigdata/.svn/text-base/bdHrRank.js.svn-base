/**
 * 大屏单位健康度排名柱状图
 */
BdHrRank = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
BdHrRank.prototype = {
		optionFunc : function(){ return {     
			title : {
				show : false
			},
			tooltip : {
				show: true,
				trigger : 'item'
			},
			grid : {
				borderWidth : 0,
				top : 40,
				bottom : 100,
				left : 80,
				right : 50
			},
		    legend: {
		    	show:false
		    },
		    xAxis : [
		        {
		            type : 'category',
		            splitLine : false,
					axisLine : {
						lineStyle : {
							color : '#032C63',
							width : 1
						}
					},
		            axisLabel : {
						show : true,
						rotate:45,
						textStyle : {
							color : '#FFF',
							fontFamily: '方正兰亭黑简体',
							fontSize: 21,
							fontWeight : 300
						}
					},
		            data : []
		        }
		    ],
		    yAxis : [
		        {
		            name : '健康度',
		            type : 'value',
				    axisLine : {
						lineStyle : {
							color : '#032C63',
							width : 1
						}
					},
					axisLabel : {
						show : true,
						interval : 'auto',
						textStyle : {
							color : '#FFF',
							fontFamily: '方正兰亭黑简体',
							fontSize: 21,
							fontWeight : 300
						}
					},
					nameTextStyle : {
						color : '#FFF',
						fontFamily: '方正兰亭黑简体',
						fontSize: 21,
						fontWeight : 300
					},
					splitLine : {
						show:true,
						lineStyle:{
							color:'#05234F'
						}
					}
		        }
		    ],
		    series : [
		        {
		            name:'健康度',
		            type:'bar',
		            barMaxWidth:50,
		            itemStyle : {
						normal : {
							 color: function(params){
								 var colorList = ['#069C7F','#00A276','#00A66F',
								                  '#31A862','#72AA57','#A8AC49',
								                  '#C5AD3D','#DFAB23','#F3A902','#FFA500'];
								 return colorList[(params.dataIndex%colorList.length)];
							 }
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
						//x轴数据
						var xData = new Array();
						//故障数数据
						var datas_1 = new Array();
						for(var i=0;i<data.data.length;i++){
							xData.push(data.data[i].NAME);
							datas_1.push({
								name:data.data[i].NAME,
								value:data.data[i].VALUE
							});
						}
						cur.option.xAxis[0].data = xData;
						cur.option.series[0].data = datas_1;//设置系列数据
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
