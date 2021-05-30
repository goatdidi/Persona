<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户通信记录统计</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
</head>
<body>
	<h1 style="text-align: center;">用户月度通信记录统计</h1>
	<hr>
	<div>
		电话号码：
		<input type="text" id="telnum" value="13511111111">
		年：<input type="text" id="year" value="2020">
		月：
		<select id="month">
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
			<option value="6">6</option>
			<option value="7">7</option>
			<option value="8">8</option>
			<option value="9">9</option>
			<option value="10">10</option>
			<option value="11">11</option>
			<option value="12">12</option>
		</select>
		<input type="button" value="查询" onclick="getcharts();">
	</div>
	<hr>
	<div style="width:100%;height:400px;" id="chart">
	
	</div>
<script>
	var dom = document.getElementById("chart");
	var app = echarts.init(dom);
	var posList = [
		'left', 'right', 'top', 'bottom',
		'inside',
		'insideTop', 'insideLeft', 'insideRight', 'insideBottom',
		'insideTopLeft', 'insideTopRight', 'insideBottomLeft', 'insideBottomRight'
	];

	app.configParameters = {
		rotate: {
			min: -90,
			max: 90
		},
		align: {
			options: {
				left: 'left',
				center: 'center',
				right: 'right'
			}
		},
		verticalAlign: {
			options: {
				top: 'top',
				middle: 'middle',
				bottom: 'bottom'
			}
		},
		position: {
			options: echarts.util.reduce(posList, function (map, pos) {
				map[pos] = pos;
				return map;
			}, {})
		},
		distance: {
			min: 0,
			max: 100
		}
	};

	app.config = {
		rotate: 90,
		align: 'left',
		verticalAlign: 'middle',
		position: 'insideBottom',
		distance: 15,
		onChange: function () {
			var labelOption = {
				normal: {
					rotate: app.config.rotate,
					align: app.config.align,
					verticalAlign: app.config.verticalAlign,
					position: app.config.position,
					distance: app.config.distance
				}
			};
			myChart.setOption({
				series: [{
					label: labelOption
					}, {
					label: labelOption
					}, {
					label: labelOption
					}, {
					label: labelOption
					}, {
					label: labelOption
					}, {
					label: labelOption
				}]
			});
		}
	};


	var labelOption = {
		show: true,
		position: app.config.position,
		distance: app.config.distance,
		align: app.config.align,
		verticalAlign: app.config.verticalAlign,
		rotate: app.config.rotate,
		formatter: '{c}  {name|{a}}',
		fontSize: 16,
		rich: {
			name: {
				textBorderColor: '#fff'
			}
		}
	};
	//获取月份的天数
	var getdays = function(month){
		var days = 0;
		switch (parseInt(month)) {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
        	days = 31;
            break;
        case 4:
        case 6:
        case 9:
        case 11:
        	days = 30;
            break;
        case 2:
            days = ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) ? 29 : 28;
            break;
        default:
            days = 0;
        	break;
    	}
		return days;
	}
	//获取日列表
	var getDayList=function(){
		var month = $("#month").val();
		var days = getdays(month);
		var date = [];
		for(var i=1;i<=days;i++){
			date[i-1] = i+"日";
		}
		return date;
	}
	
	var getcharts = function(){
		var tel = $("#telnum").val();
		var year = $("#year").val();
		var month = $("#month").val();
		var date = getDayList();
		$.ajax({
			type: "GET",
			url: "${pageContext.request.contextPath}/calllog/customer/month/data/"+tel+"/"+year+"/"+month,
			dataType:"json",
			success: function(msg){
				var count=[];
				var callingcount=[];
				var calledcount=[];
				var sum=[];
				var callingsum=[];
				var calledsum=[];
				//初始化数据为0
				var days = getdays(month);
				for(var i=1;i<=days;i++){
					count[i-1] = 0;
					callingcount[i-1] = 0;
					calledcount[i-1] = 0;
					sum[i-1] = 0;
					callingsum[i-1] = 0;
					calledsum[i-1] = 0;
				}
				for(var j =0;j<msg.length;j++){
					var m = msg[j].day-1; //日期
					count[m] = msg[j].telcount;
					callingcount[m] = msg[j].telcallingcount;
					calledcount[m] = msg[j].telcalledcount;
					sum[m] = parseInt(msg[j].telsum/60);
					callingsum[m] = parseInt(msg[j].telcallingsum/60);
					calledsum[m] = parseInt(msg[j].telcalledsum/60);
				}
				app.setOption({
				    color: ['#003366', '#006699', '#4cabce', '#e5323e','#e5543e','#e5333e'],
				    tooltip: {
				        trigger: 'axis',
				        axisPointer: {
				            type: 'shadow'
				        }
				    },
				    legend: {
				        data: ['总通话次数', '主叫次数', '被叫次数', '总通话时长', '主叫时长', '被叫时长']
				    },
				    toolbox: {
				        show: true,
				        orient: 'vertical',
				        left: 'right',
				        top: 'center',
				        feature: {
				            mark: {show: true},
				            dataView: {show: true, readOnly: false},
				            magicType: {show: true, type: ['line', 'bar', 'stack', 'tiled']},
				            restore: {show: true},
				            saveAsImage: {show: true}
				        }
				    },
				    xAxis: [
				        {
				            type: 'category',
				            axisTick: {show: false},
				            data: date
				        }
				    ],
				    yAxis: [
				        {
				            type: 'value'
				        }
				    ],
				    series: [
				        {
				            name: '总通话次数',
				            type: 'bar',
				            barGap: 0,
				            label: labelOption,
				            data: count
				        },
				        {
				            name: '主叫次数',
				            type: 'bar',
				            label: labelOption,
				            data: callingcount
				        },
				        {
				            name: '被叫次数',
				            type: 'bar',
				            label: labelOption,
				            data: calledcount
				        },
				        {
				            name: '总通话时长',
				            type: 'bar',
				            label: labelOption,
				            data: sum
				        },
				        {
				            name: '主叫时长',
				            type: 'bar',
				            label: labelOption,
				            data: callingsum
				        },
				        {
				            name: '被叫时长',
				            type: 'bar',
				            label: labelOption,
				            data: calledsum
				        }
				    ]
				});
			}
		});
	}

</script>
</body>
</html>