/**
 * 当月故障率或累计故障率环形图
 */
var MalfunctionRate = function(dom,tagNo,theme,opts) {
	this.dom = dom;
	this.theme = theme;
	this.opts = opts;
	this.chart = null;
	this.option;
	this.tagNo = tagNo;
};
MalfunctionRate.prototype = {
		optionFunc:function(){
			return {
				graphic: {
			        elements: [{
			            type: 'circle',
			            left:'center',
			            top: 'center',
			            shape: {
			                r: 45
			            },
			            style: {
			                fill: '#5886f0'
			            }
			        }]
			    },

			    series: [
			        {
			            center: ['50%','50%'],
			            radius: ['83%','85%'],
			            clockWise: false,
			            hoverAnimation: false,
			            avoidLabelOverlap:false,
			            type: 'pie',
			            itemStyle: {
			                normal: {
			                    label: {
			                        show: true,
			                        formatter:'{c}\n%',
			                        textStyle: {
			                            fontSize: 26,
			                            fontWeight: 'bold'
			                        },
			                        position: 'center'
			                    },
			                    labelLine: {
			                        show: false
			                    },
			                    color: '#FFF',
			                    borderColor: '#5886f0',
			                    borderWidth: 15
			                },
			                emphasis: {
			                    label: {
			                        textStyle: {
			                            fontSize: 28,
			                            fontWeight: 'bold'
			                        }
			                    },
			                    color: '#FFF',
			                    borderColor: '#5886f0',
			                    borderWidth: 16
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
							cityCode:GlobalParma.cityCode,
							countyCode:GlobalParma.countyCode
							};
					$.fn.postSubmit(GlobalParma.dataUrl,JSON.stringify(jsonData), function(data){
						if(data.success){
							if(data.data&&data.data.length>0){
								var datas=[
											
											{
											    name:  'other',
											    value: (100-(data.data[0]['CFR']||0)),
											    label: {
									                show: false
									            },
									            labelLine: {
									                show: false
									            },
											    itemStyle: {
											        normal: {
											            color: '#5886f0',
											            borderColor: '#5886f0',
											            borderWidth: 0
											        },
											        emphasis: {
											            color: '#5886f0',
											            borderColor: '#5886f0',
											            borderWidth: 0
											        }
											    }
											},
											{
											    value: (data.data[0]['CFR']||0),
											    name: '故障率'
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