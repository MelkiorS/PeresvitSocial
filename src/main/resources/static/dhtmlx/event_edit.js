$(function(){
    function init() {
       scheduler.init('scheduler_here', new Date(),"month");
       scheduler.config.details_on_dblclick = true;
       scheduler.config.details_on_create = true;

       scheduler.config.xml_date="%Y%m%d %H%i";
       scheduler.templates.xml_date = function(value){ return new Date(value); };
       scheduler.load('/admin/myeventsperiod?' + current_month_period(new Date()),'json');

        scheduler.attachEvent("onViewChange", function (new_mode , new_date){
            scheduler.load('/admin/myeventsperiod?' + current_month_period(new_date),'json');
        });
        scheduler.attachEvent("onEventAdded", function(id,ev){
           dtFrom = new Date(ev.date + ' ' + ev.from);
           dtTo = new Date(ev.date + ' ' + ev.to);
           data = {
               "start_date" : dtFrom.getTime(),
               "end_date" : dtTo.getTime(),
               "place" : ev.place,
               "title" : ev.text,
               "description":  ev.description,
               "connectall":   ev.connectall,
               "eventtype":   ev.eventtype,
               "groups":   ev.groups,
               "friends":  ev.friends
           };
           $.ajax({
               type : "POST",
               url : "/admin/addEvent",
               data : data,
               success: function(data){
                   scheduler.load('/admin/myeventsperiod?' + current_month_period(dtFrom),'json');
                   scheduler.deleteEvent(id);
               },
               fail: function() {
                   console.log('oops');
               }
           });
        });

        scheduler.attachEvent("onEventChanged", function(id, ev) {
           dtFrom = new Date(ev.date + ' ' + ev.from);
           dtTo = new Date(ev.date + ' ' + ev.to);
           $.ajax({
               type : "POST",
               url : "/admin/updEvent",
               data : {
                   "id" : ev.id,
                   "start_date" : dtFrom.getTime(),
                   "end_date" : dtTo.getTime(),
                   "place" : ev.place,
                   "title" : ev.text,
                   "description":  ev.description,
                   "connectall":   ev.connectall,
                   "eventtype":   ev.eventtype,
                   "groups":   ev.groups,
                   "friends":  ev.friends
               },
               success: function(data){
               }
           });
        });
        scheduler.attachEvent("onEventDeleted", function(id) {
           $.ajax({
               type : "POST",
               url : "/admin/delEvent",
               data : {
                   "id" : id
               },
               success: function(data){
               }
           });
        });

        var html = function(id) { return document.getElementById(id); };
        var t = function (v, def) { return v === undefined ? def : v }
        var n = function (v, def) { return v === null ? def : v }

        scheduler.showLightbox = function(id) {
           var ev = scheduler.getEvent(id);
           scheduler.startLightbox(id, html("my_form"));

           html("place").focus();
           html("place").value =  t(ev.place, "");
           html("description").value = t(ev.description, "");
           html("connectall").value = t(ev.connectall, true);
           html("eventtype").value = ev.eventtype;
           html("text").value = t(ev.text, "");

           html("from").value = t(ev.from, fmtTime(ev.start_date));
           html("to").value = t(ev.to, fmtTime(ev.end_date));
           html("date").value = t(ev.date, fmtDateScheduler(ev.start_date));


            setFlags(ev.groups, $("#groups"))
            setFlags(ev.users, $("#friends"))
        };

    }

    function setFlags(arr, box) {
        if (!(arr===null || arr===undefined)) {
            for (var el in arr) {
                box.children("li").children("input[value=" + arr[el] + "]").prop('checked', true)
            }
        }
    }

    function fmtNum(num, size) {
       var s = num+"";
       while (s.length < size) s = "0" + s;
       return s;
   }

   function fmtDate(dt) {
       return '' + dt.getFullYear() + fmtNum(dt.getMonth()+1, 2) + fmtNum(dt.getDate(), 2);
   }

   function fmtDateScheduler(dt) {
       return "" + dt.getFullYear() + "-" + fmtNum(dt.getMonth()+1, 2) + "-" + fmtNum(dt.getDate(), 2);
   }

   function fmtTime(tm) {
       return "" + fmtNum(tm.getHours(), 2) + ":" + fmtNum(tm.getMinutes(), 2);
   }

   function current_month_period(dt) {
       var date = dt;
       var firstDay = new Date(date.getFullYear(), date.getMonth(), -1);
       var lastDay = new Date(date.getFullYear(), date.getMonth() + 2, 0);

       return "start=" + fmtDate(firstDay) + "&finish=" + fmtDate(lastDay);
   }

    init();
});