/**
 * 预测准确率柱状图
 */
var PredRate = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
PredRate.prototype={
		optionFunc: function(){
			return {
				grid: {
					top: 100,
					bottom: 30
				},
				xAxis: {
					name: '月份',
			    	nameLocation: 'end',
			    	nameTextStyle: {
			    		color: '#fff',
			    		fontFamily: 'Microsoft YaHei',
			    		fontSize: 22
			    	},
					axisTick: {
						show: false
					},
					axisLine: {
						lineStyle: {
							color: '#033F7F',
							shadowColor: '#033F7F',
						    shadowBlur: 0,
						    opacity:1,
						    shadowOffsetY:20,
						    shadowOffsetX:-20
						}
					},
					axisLabel: {
						show: false
					},
			        data: []
			    },
			    yAxis: {
			    	name: '预测准\n确率(%)',
			    	nameLocation: 'end',
			    	nameTextStyle: {
			    		color: '#fff',
			    		fontFamily: 'Microsoft YaHei',
			    		fontSize: 22
			    	},
			    	axisTick: {
						show: false
					},
			    	axisLine: {
						lineStyle: {
							color: '#033F7F',
							shadowColor: '#033F7F',
						    shadowBlur: 0,
						    opacity:1,
						    shadowOffsetY:20,
						    shadowOffsetX:-20
						}
					},
					splitLine: {
						lineStyle: {
							color: '#033F7F'
						}
					},
					axisLabel: {
						color: '#FFF',
						fontFamily: 'Microsoft YaHei',
			    		fontSize: 18
					}
			    },
			    series: [{
			            name:'a',
			            tooltip:{
			               show:false 
			            },
			            type: 'pictorialBar',
			            animation: false,
			            symbolOffset: [0,'-50%'],
			            itemStyle: {
			                 normal: {
			                    color: new echarts.graphic.LinearGradient(
			                        0, 0, 1, 0,
			                        [
			                            {offset: 0, color: '#00AC9C'},
			                            {offset: 1, color: '#006F6B'}
			                        ]
			                    ), 
			                    borderWidth:1,
			                    borderColor:'#006F6B'
			                }
			            },
			            symbol: 'circle',
			            symbolSize: ['70', '25'],
			            symbolPosition: 'end',
			            markPoint : {
				               symbol:'image://data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAFgAAABuCAYAAACjttSEAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyFpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNS1jMDE0IDc5LjE1MTQ4MSwgMjAxMy8wMy8xMy0xMjowOToxNSAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bWxuczp4bXBNTT0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL21tLyIgeG1sbnM6c3RSZWY9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9zVHlwZS9SZXNvdXJjZVJlZiMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIChXaW5kb3dzKSIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDo2MEI0OTA5RUY4MzIxMUU4ODIwOUZFMjBBNDI2MTFGNSIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDo2MEI0OTA5RkY4MzIxMUU4ODIwOUZFMjBBNDI2MTFGNSI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjYwQjQ5MDlDRjgzMjExRTg4MjA5RkUyMEE0MjYxMUY1IiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjYwQjQ5MDlERjgzMjExRTg4MjA5RkUyMEE0MjYxMUY1Ii8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+8kBElgAAKeBJREFUeNrkfQu0XFd5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',
			                   symbolSize: 70,
			                   symbolOffset: [0,'-50%'],
			                   label: {
				                    offset: [0, -5],
				                    color: '#006F6B',
				                    fontWeight: 'bold',
				                    fontFamily: 'Microsoft YaHei',
				                    fontSize: 18
				                },
				               itemStyle:{
				                  color: 'green' 
				                },
				                data:[]
				            },
			            data: [],
			            z:3
			        }
			    ,{
		            name:'b',
		            tooltip:{
		               show:false 
		            },
		            type: 'pictorialBar',
		            animation: false,
		            //symbolOffset: [0,'-16%'],
		            itemStyle: {
		                 normal: {
		                    color: '#00AC9C', 
		                    borderWidth:1,
		                    borderColor:'#00AC9C'
		                }
		            },
		            symbol: 'circle',
		            symbolSize: ['70', '25'],
		            symbolRepeat:true,
		            symbolMargin:'-50%!',
		            data: [],
		            z:2
		        }
			    ]
			};
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
			cur.option.xAxis.data = ['201801', '201802', '201803'];
			cur.option.series[0].data = [99.22, 91.82, 91.91];
			cur.option.series[1].data = [99.22, 91.82, 91.91];
			cur.option.series[0].markPoint.data = [{coord: [0,99.22],value:99.22},
			       			                    {coord: [1,91.82],value:91.82},
			    			                    {coord: [2,91.91],value:91.91}];
			cur.chart = echarts.init(cur.dom,cur.theme,cur.opts);
			cur.chart.setOption(cur.option);
		}
};
