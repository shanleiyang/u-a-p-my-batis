/**
 * 故障历史变化情况折线图
 */
BdMalfunctionHistoryStat = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
BdMalfunctionHistoryStat.prototype = {
		optionFunc : function(){ return {     
			title : {
				show : false
			},
			tooltip : {
				trigger : 'axis',
				//formatter:'{b}<br/>{a}: {c}<br/>{a1}: {c1}%',
				axisPointer : {
					type : 'none'
				}
			},
			grid : {
				borderWidth : 0,
				top : 60,
				bottom : 80,
				left : 80,
				right : 60
			},
		    legend: {
		    	top : 10,
		    	textStyle : {
		    		color : '#FFF',
		    		fontSize : 21,
		    		fontFamily : '方正兰亭黑简体'
		    	},
		        data:['故障数','故障率']
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
		            //boundaryGap : true,
		            axisLabel : {
						show : true,
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
		            name : '故障数(只)',
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
		        },
		      	{
		        	name : '故障率(%)',
		            type : 'value',
		            position: 'right',
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
						show:false,
						lineStyle:{
							color:'#05234F'
						}
					}
		        }
		    ],
		    dataZoom: [
		               {
		                   type: 'slider',
		                   show: true,
		                   showDataShadow:true,
		                   showDetail:false,
		                   xAxisIndex: [0],
		                   bottom:10,
		                   borderColor:'#033F83',
		                   backgroundColor:'#12263B',
		                   fillerColor:'rgba(9,65,116,0.6)' ,
		                   dataBackground:{
		                	   lineStyle:{
		                		   color:'#07538B',
		                		   opacity:1
		                	   },
		                	   areaStyle:{
		                		   color:'#094879',
		                		   opacity:1
		                	   }
		                   },
		                   handleStyle:{
		                	   color:'#0075C1'
		                   },
		                   startValue: '2016-07-01'
		               }
		          ],
		    series : [
		        {
		            name:'故障数',
		            type:'line',
		            yAxisIndex:0,
		            symbolSize:6,
		            itemStyle : {
						normal : {
							color : 'RGB(0,160,233)'
						}
					},
		            data:[]            
		        },
		        {
		            name:'故障率',
		            type:'line',
		            yAxisIndex:1,
		            symbolSize:6,
		            itemStyle : {
						normal : {
							color : 'RGB(243,152,0)'
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
						//故障率数据
						var datas_2 = new Array();
						for(var i=0;i<data.data.length;i++){
							xData.push(data.data[i].YM);
							datas_1.push({
								name:data.data[i].YM,
								value:data.data[i].FQTY
							});
							datas_2.push({
								name:data.data[i].YM,
								value:data.data[i].FP
							});
						}
						cur.option.xAxis[0].data = xData;
						cur.option.series[0].data = datas_1;//设置系列数据
						cur.option.series[1].data = datas_2;
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
