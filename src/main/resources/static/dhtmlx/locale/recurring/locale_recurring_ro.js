/*
@license
dhtmlxScheduler v.4.3.1 

This software is covered by GPL license. You also can obtain Commercial or Enterprise license to use it in non-GPL project - please contact sales@dhtmlx.com. Usage without proper license is prohibited.

(c) Dinamenta, UAB.
*/
/*
	Traducere de Ovidiu Lixandru: http://www.madball.ro
 */

 scheduler.__recurring_template='<div class="dhx_form_repeat"> <form> <div class="dhx_repeat_left"> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="day" />Zilnic</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="week"/>Saptamanal</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="month" checked />Lunar</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="repeat" value="year" />Anual</label> </div> <div class="dhx_repeat_divider"></div> <div class="dhx_repeat_center"> <div style="display:none;" userId="dhx_repeat_day"> <label><input class="dhx_repeat_radio" type="radio" roleName="day_type" value="d"/>La fiecare</label><input class="dhx_repeat_text" type="text" roleName="day_count" value="1" />zi(le)<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="day_type" checked value="w"/>Fiecare zi lucratoare</label> </div> <div style="display:none;" userId="dhx_repeat_week"> Repeta la fiecare<input class="dhx_repeat_text" type="text" roleName="week_count" value="1" />saptamana in urmatoarele zile:<br /> <table class="dhx_repeat_days"> <tr> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="1" />Luni</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="4" />Joi</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="2" />Marti</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="5" />Vineri</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="3" />Miercuri</label><br /> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="6" />Sambata</label> </td> <td> <label><input class="dhx_repeat_checkbox" type="checkbox" roleName="week_day" value="0" />Duminica</label><br /><br /> </td> </tr> </table> </div> <div userId="dhx_repeat_month"> <label><input class="dhx_repeat_radio" type="radio" roleName="month_type" value="d"/>Repeta in</label><input class="dhx_repeat_text" type="text" roleName="month_day" value="1" />zi la fiecare<input class="dhx_repeat_text" type="text" roleName="month_count" value="1" />luni<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="month_type" checked value="w"/>In a</label><input class="dhx_repeat_text" type="text" roleName="month_week2" value="1" />zi de<select roleName="month_day2"><option value="1" selected >Luni<option value="2">Marti<option value="3">Miercuri<option value="4">Joi<option value="5">Vineri<option value="6">Sambata<option value="0">Duminica</select>la fiecare<input class="dhx_repeat_text" type="text" roleName="month_count2" value="1" />luni<br /> </div> <div userId="dhx_repeat_year"> <label><input class="dhx_repeat_radio" type="radio" roleName="year_type" value="d"/>In</label><input class="dhx_repeat_text" type="text" roleName="year_day" value="1" />zi a lunii<select roleName="year_month"><option value="0" selected >Ianuarie<option value="1">Februarie<option value="2">Martie<option value="3">Aprilie<option value="4">Mai<option value="5">Iunie<option value="6">Iulie<option value="7">August<option value="8">Septembrie<option value="9">Octombrie<option value="10">Noiembrie<option value="11">Decembrie</select><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="year_type" checked value="w"/>In</label><input class="dhx_repeat_text" type="text" roleName="year_week2" value="1" />zi de<select roleName="year_day2"><option value="1" selected >Luni<option value="2">Marti<option value="3">Miercuri<option value="4">Joi<option value="5">Vineri<option value="6">Sambata<option value="7">Duminica</select>a lunii<select roleName="year_month2"><option value="0" selected >Ianuarie<option value="1">Februarie<option value="2">Martie<option value="3">Aprilie<option value="4">Mai<option value="5">Iunie<option value="6">Iulie<option value="7">August<option value="8">Septembrie<option value="9">Octombrie<option value="10">Noiembrie<option value="11">Decembrie</select><br /> </div> </div> <div class="dhx_repeat_divider"></div> <div class="dhx_repeat_right"> <label><input class="dhx_repeat_radio" type="radio" roleName="end" checked/>Fara data de sfarsit</label><br /> <label><input class="dhx_repeat_radio" type="radio" roleName="end" />Dupa</label><input class="dhx_repeat_text" type="text" roleName="occurences_count" value="1" />evenimente<br /> <label><input class="dhx_repeat_radio" type="radio" roleName="end" />La data</label><input class="dhx_repeat_date" type="text" roleName="date_of_end" value="'+scheduler.config.repeat_date_of_end+'" /><br /> </div> </form> </div> <div style="clear:both"></div>';
