/**
 * 初始化联动的datetimepicker
 * @param startTime
 * @param endTime
 * @param timeFormat
 * @param minview
 */
function initDateTimePicker(startTime, endTime, timeFormat, minview) {
	$(startTime).datetimepicker("remove");
	$(startTime).datetimepicker({
		language: "zh-CN",
		autoclose : true,
		todayHighlight : true,
		endDate : new Date(),
		format : timeFormat,
		startView : minview,
		minView : minview,
	}).on("changeDate", function() {
		var value = $(startTime).val();
		$(endTime).datetimepicker("remove");
		$(endTime).datetimepicker({
			language: "zh-CN",
			autoclose : true,
			todayHighlight : true,
			endDate : new Date(),
			startDate : value,
			format : timeFormat,
			startView : minview,
			minView : minview,
		})
	});
	$(endTime).datetimepicker("remove");
	$(endTime).datetimepicker({
		language: "zh-CN",
		autoclose : true,
		todayHighlight : true,
		endDate : new Date(),
		format : timeFormat,
		startView : minview,
		minView : minview,
	}).on("changeDate", function() {
		var value = $(endTime).val();
		$(startTime).datetimepicker("remove");
		$(startTime).datetimepicker({
			language: "zh-CN",/* sessionStorage.getItem("lang"),*/
			autoclose : true,
			todayHighlight : true,
			endDate : value,
			format : timeFormat,
			startView : minview,
			minView : minview,
		})
	});
}