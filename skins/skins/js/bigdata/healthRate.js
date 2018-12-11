/**
 * 到货批次详情-健康度环形图
 */
var HealthRate = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
HealthRate.prototype = {
		optionFunc:function(){
			return {
//				graphic: {
//					  elements:[
//					            {
//					            type: 'group',
//					            bounding: 'all',
//					            left: '28%',
//					            bottom: 'center',
//					            z: 0,
//					            
//					            children: [
//					                {
//					                    type: 'rect',
//					                   left: 'center',
//					                   top: 'center',
//					                    shape: {
//					                    	r:[1,10,10,1],
//					                        width: 150,
//					                        height: 60
//					                    },
//					                    style: {
//					                        fill: '#069C7F'
//					                    }
//					                },
//					                {
//					                    type: 'text',
//					                    left: 'center',
//					                   top: 'center',
//					                    style: {
//					                        fill: '#fff',
//					                        text: '健康度（%）',
//					                        textAlign:'right',
//					                        textVerticalAlign:'middle',
//					                        font: 'bold 18px Microsoft YaHei'
//					                    }
//					                }
//					            ]
//					        }
//					            ]
//			    },

			    series: [
			        {
			            center: ['50%','50%'],
			            radius: ['81%','82%'],
			            clockWise: false,
			            hoverAnimation: false,
			            avoidLabelOverlap:false,
			            type: 'pie',
			            z:100,
			            itemStyle: {
			                normal: {
			                    label: {
			                        show: true,
			                        formatter:'{d}',
			                        textStyle: {
			                            fontSize: 24,
			                            fontWeight: 'bold'
			                        },
			                        position: 'center'
			                    },
			                    labelLine: {
			                        show: false
			                    },
			                    color: '#F6AB00',
			                    borderColor: '#F6AB00',
			                    borderWidth: 5
			                },
			                emphasis: {
			                    label: {
			                        textStyle: {
			                            fontSize: 24,
			                            fontWeight: 'bold'
			                        }
			                    },
			                    color: '#F6AB00',
			                    borderColor: '#F6AB00',
			                    borderWidth: 5
			                }
			            },
			            data: []
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
				showChart:function(param,flag){
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
							arriveBatchNo:GlobalParma.bidBatchNo
							};
					$.fn.postSubmit(GlobalParma.dataUrl,JSON.stringify(jsonData), function(data){
						if(data.success){
							if(data.data&&data.data.length>0){
								var datas=[
											{
											    value: data.data[0].HR,
											    name: '健康度'
											},
											{
											    name:  'other',
											    value: (100-data.data[0].HR),
											    label: {
									                show: false
									            },
									            labelLine: {
									                show: false
									            },
											    itemStyle: {
											        normal: {
											            color: '#009E86',
											            borderColor: '#009E86',
											            borderWidth: 0
											        },
											        emphasis: {
											            color: '#009E86',
											            borderColor: '#009E86',
											            borderWidth: 0
											        }
											    }
											}
									           ];
									cur.option.series[0].data = datas;
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