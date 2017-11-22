	var eventsPath = _all ? "/panel/eventsdatajson" : "/panel/myeventsdatajson";
	init();
	window.onload = function() {
		removeStyle();
	};

	function init() {
		scheduler.config.xml_date="%Y-%m-%d %H:%i";
		scheduler.init('scheduler_here',new Date(),"day");

		scheduler.load(eventsPath + "?dt=" + fmtDate(startMonth(new Date())) + "&qty=1000", 'json',function(){
			var calendar = scheduler.renderCalendar({
				container:"mini_here",
				date:scheduler._date,
				navigation:true,
				handler:function(date,calendar){
					removeStyle();
					fillDayEvent(date);
				}
			});
			window.calendar = calendar;
		});
		scheduler.afterRender = function(d) {
			fillMonthEvents(startMonth(d));
		};
	}

	function fillDayEvent(day) {
		$.ajax({
			type: "GET",
			url: "/panel/eventjson",
			data: {
				"dt": fmtDate(day)
			},
			success: function (data) {
				$('#eventDate').text(data.start_date);
				$('#eventName').text(data.text);
				$('#eventDescription').text(data.description);
				$('#assignButton').toggleClass('hidden', JSON.parse(data.assignedToMe) || !_all);
				$('#assignButton').on('click', function() {
					assignToMe(data.id, new Date(data.start_date));
				})
			}
		});
	}

	function fillMonthEvents(d) {
		$.ajax({
			type: "GET",
			url: eventsPath,
			data: {
				"dt": fmtDate(d),
				"qty": 5
			},
			success: function (data) {
				$('#close5events').empty();
				for (var i=0;i<data.length;i++) {
					if (new Date(data[i].start_date).getMonth() <= d.getMonth()) $('#close5events').append('<li class="event-li ' + colorsByTypes(data[i].eventtype) + '" onClick="fillDayEvent(new Date(' + new Date(data[i].start_date).getTime() + '))">' + data[i].text + '</li>');
					scheduler.markCalendar(calendar, new Date(data[i].start_date), colorDate(data[i].eventtype));
				}
				if (data.length > 0) {
					fillDayEvent(new Date(data[0].start_date));
				}
			}
		});
	}

	function assignToMe(eventid, dt) {
		$.ajax({
		    type: "POST",
		    url: "/home/myWay/assignToMe",
		    data: {
		        "eventId": eventid
		    },
			success: function (data) {
				fillDayEvent(dt);
			}
		})
	}

	function colorsByTypes(eventtype) {
		switch(eventtype) {
			case "COMPETITION":
				return "red";
			case "CELEBRATION":
				return "yellow";
			case "DEMONSTARTION":
				return "violet";
			case "APPRAISAL":
				return "grey";
			case "WORKSHOP":
				return "darkgrey";
			case "TEASCHOOL":
				return "lightgreen";
			case "HIKECAMPMOVIE":
				return "green";
			default:
				return "red";
		}
	}

	function colorDate(eventtype) {
		return "bg" + colorsByTypes(eventtype);
	}

	function removeStyle() {
		$('#mini_here').find(".dhx_calendar_click").removeClass('dhx_calendar_click')
	}

	function d2(v) {
		return v<=9 ? "0"+v : ""+v
	}

	function fmtDate(d) {
		return d2(d.getMonth()+1) + "/" + d2(d.getDate()) + "/" + d.getFullYear()
	}

	function startMonth(d) {
		res = new Date(d.getTime());
		res.setDate(1);
		res.setHours(0);
		res.setMinutes(0);
		res.setSeconds(0);
		res.setMilliseconds(0);
		return res;
	}
	