/*
@license
dhtmlxScheduler v.4.3.1 

This software is covered by GPL license. You also can obtain Commercial or Enterprise license to use it in non-GPL project - please contact sales@dhtmlx.com. Usage without proper license is prohibited.

(c) Dinamenta, UAB.
*/
scheduler.__recurring_template='<div class="dhx_form_repeat"> <form> <div class="dhx_repeat_left"> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="day" />День</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="week"/>Тиждень</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="month" checked />Місяць</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="year" />Рік</label> </div> <div class="dhx_repeat_divider"></div> <div class="dhx_repeat_center"> <div style="display:none;" userId="dhx_repeat_day"> <label><input class="dhx_repeat_radio" type="radio" roleName="day_type" value="d"/>Кожний</label><input class="dhx_repeat_text" type="text" roleName="day_count" value="1" />день<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="day_type" checked value="w"/>Кожний робочий день</label> </div> <div style="display:none;" userId="dhx_repeat_week"> Повторювати кожен<input class="dhx_repeat_text" type="text" roleName="week_count" value="1" />тиждень , по:<br /> <table class="dhx_repeat_days"> <tr> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="1" />Понеділкам</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="4" />Четвергам</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="2" />Вівторкам</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="5" />П\'ятницям</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="3" />Середам&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="6" />Суботам</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="0" />Неділям</label><br /><br /> </td> </tr> </table> </div> <div userId="dhx_repeat_month"> <label><input class="dhx_repeat_radio" type="radio" roleName="month_type" value="d"/>Повторювати</label><input class="dhx_repeat_text" type="text" roleName="month_day" value="1" /> числа кожний <input class="dhx_repeat_text" type="text" roleName="month_count" value="1" />місяць<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="month_type" checked value="w"/></label><input class="dhx_repeat_text" type="text" roleName="month_week2" value="1" /><select roleName="month_day2"><option value="1" selected >Понеділок<option value="2">Вівторок<option value="3">Середа<option value="4">Четвер<option value="5">П\'ятниця<option value="6">Субота<option value="0">Неділя</select>кожен <input class="dhx_repeat_text" type="text" roleName="month_count2" value="1" />місяць<br /> </div> <div style="display:none;" userId="dhx_repeat_year"> <label><input class="dhx_repeat_radio" type="radio" roleName="year_type" value="d"/></label><input class="dhx_repeat_text" type="text" roleName="year_day" value="1" />день<select roleName="year_month"><option value="0" selected >січня<option value="1">лютого<option value="2">березня<option value="3">квітня<option value="4">травня<option value="5">червня<option value="6">липня<option value="7">серпня<option value="8">вересня<option value="9">жовтня<option value="10">листопада<option value="11">грудня</select><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="year_type" checked value="w"/></label><input class="dhx_repeat_text" type="text" roleName="year_week2" value="1" /><select roleName="year_day2"><option value="1" selected >понеділок<option value="2">вівторок<option value="3">середа<option value="4">четвер<option value="5">п\'ятниця<option value="6">субота<option value="0">неділя</select><select roleName="year_month2"><option value="0" selected >січня<option value="1">лютого<option value="2">березня<option value="3">квітня<option value="4">березня<option value="5">червня<option value="6">липня<option value="7">серпня<option value="8">вересня<option value="9">жовтня<option value="10">листопада<option value="11">грудня</select><br /> </div> </div> <div class="dhx_repeat_divider"></div> <div class="dhx_repeat_right"> <label><input class="dhx_repeat_radio" type="radio" roleName="end" checked/>Без дати закінчення</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="end" /></label><input class="dhx_repeat_text" type="text" roleName="occurences_count" value="1" />повторень<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="end" />До </label><input class="dhx_repeat_date" type="text" roleName="date_of_end" value="'+scheduler.config.repeat_date_of_end+'" /><br /> </div> </form> </div> <div style="clear:both"> </div>';
